package jp.groupsession.v2.wml.wml220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterSortDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlFilterSortModel;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml220Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml220Biz.class);

    /**
     * <br>[機  能] フィルターの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteFilter(Connection con, Wml220ParamModel paramMdl) throws SQLException {
        boolean commitFlg = false;

        try {
            //フィルターを削除する
            WmlFilterDao wfDao = new WmlFilterDao(con);
            wfDao.delete(paramMdl.getWmlEditFilterId());

            //フィルター_転送先メールアドレスを削除する
            WmlFilterFwaddressDao wfFwadrDao = new WmlFilterFwaddressDao(con);
            wfFwadrDao.delete(paramMdl.getWmlEditFilterId());

            //フィルター条件を削除する
            Wml220Dao dao = new Wml220Dao(con);
            dao.deleteFilterCondition(paramMdl.getWmlEditFilterId());

            //フィルター条件を削除する
            dao.deleteFilterSortCondition(paramMdl.getWmlEditFilterId());

            //コミット実行
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                             Wml220ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {


        //アカウント名取得
        int selectWacSid = paramMdl.getWmlAccountSid();
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(selectWacSid);
        paramMdl.setWml220accountName(wacMdl.getWacName());

        //フィルター情報を取得
        int dspCnt = paramMdl.getDspCount();
        if (dspCnt == 0) {
            paramMdl.setDspCount(1);
        }

        Wml220Dao wml200dao = new Wml220Dao(con);
        List<Wml220FilterDataModel> fdMdlList = wml200dao.getFlterList(selectWacSid, userSid);
        Wml220FilterDataModel wfdDspMdl = null;

        //フィルター情報を画面表示用に加工する
        List<Wml220FilterDataModel> filDspList = new ArrayList<Wml220FilterDataModel>();

        for (Wml220FilterDataModel wfdSetMdl : fdMdlList) {
            wfdDspMdl = new Wml220FilterDataModel();
            wfdDspMdl.setFilterSid(wfdSetMdl.getFilterSid());
            wfdDspMdl.setFilterName(wfdSetMdl.getFilterName());
            wfdDspMdl.setFilterSort(wfdSetMdl.getFilterSort());
            wfdDspMdl.setFilValue(String.valueOf(wfdSetMdl.getFilterSid()));
            filDspList.add(wfdDspMdl);
        }
        paramMdl.setFilList(filDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getWml220SortRadio())
            && fdMdlList.size() > 0) {

            Wml220FilterDataModel wldMdl = fdMdlList.get(0);
            paramMdl.setWml220SortRadio(String.valueOf(wldMdl.getFilterSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return result ソート順変更結果情報
     * @throws SQLException SQL実行時例外
     */
    public SortResult<WmlFilterSortModel> updateSort(
            Connection con, Wml220ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getWml220sortLabel();
        if (keyList == null || keyList.length < 1) {
            return null;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getWml220SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return null;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return null;
        }

        SortResult<WmlFilterSortModel> result = null;
        WmlFilterSortDao sortDao = new WmlFilterSortDao(con);
        SortChangeBiz<WmlFilterSortModel> sortBiz =
                SortChangeBiz.<WmlFilterSortModel> builder()
                .setFuncTargetList(() -> {
                    return sortDao.selectList(paramMdl.getWmlAccountSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getWftSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getWfsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getWftSid() == m2.getWftSid()) {
                        return 0;
                    } else {
                        return (m1.getWftSid() - m2.getWftSid())
                                / Math.abs(m1.getWftSid() - m2.getWftSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setWfsSort((long) newSort);
                    sortDao.delete(paramMdl.getWmlAccountSid(), m.getWftSid());
                    sortDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        return result;
    }
}
