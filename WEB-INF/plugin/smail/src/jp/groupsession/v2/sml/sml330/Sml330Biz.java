package jp.groupsession.v2.sml.sml330;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlFilterDao;
import jp.groupsession.v2.sml.dao.SmlFilterSortDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlFilterSortModel;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml330Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml330Biz.class);

    /**
     * <br>[機  能] フィルターの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteFilter(Connection con, Sml330ParamModel paramMdl) throws SQLException {
        boolean commitFlg = false;

        try {
            //フィルターを削除する
            SmlFilterDao sfDao = new SmlFilterDao(con);
            sfDao.delete(paramMdl.getSmlEditFilterId());

            //フィルター条件を削除する
            Sml330Dao dao = new Sml330Dao(con);
            dao.deleteFilterCondition(paramMdl.getSmlEditFilterId());

            //フィルター条件を削除する
            dao.deleteFilterSortCondition(paramMdl.getSmlEditFilterId());

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
                             Sml330ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {


        //アカウント名取得
        int selectSacSid = paramMdl.getSmlAccountSid();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(selectSacSid);
        paramMdl.setSml330accountName(sacMdl.getSacName());

        //フィルター情報を取得
        int dspCnt = paramMdl.getDspCount();
        if (dspCnt == 0) {
            paramMdl.setDspCount(1);
        }

        Sml330Dao sml330dao = new Sml330Dao(con);
        List<Sml330FilterDataModel> fdMdlList = sml330dao.getFlterList(selectSacSid, userSid);
        Sml330FilterDataModel sfdDspMdl = null;

        //フィルター情報を画面表示用に加工する
        List<Sml330FilterDataModel> filDspList = new ArrayList<Sml330FilterDataModel>();

        for (Sml330FilterDataModel sfdSetMdl : fdMdlList) {
            sfdDspMdl = new Sml330FilterDataModel();
            sfdDspMdl.setFilterSid(sfdSetMdl.getFilterSid());
            sfdDspMdl.setFilterName(sfdSetMdl.getFilterName());
            sfdDspMdl.setFilterSort(sfdSetMdl.getFilterSort());
            sfdDspMdl.setFilValue(String.valueOf(sfdSetMdl.getFilterSid()));
            filDspList.add(sfdDspMdl);
        }
        paramMdl.setFilList(filDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getSml330SortRadio())
            && fdMdlList.size() > 0) {

            Sml330FilterDataModel sldMdl = fdMdlList.get(0);
            paramMdl.setSml330SortRadio(String.valueOf(sldMdl.getFilterSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Sml330ParamModel paramMdl, int changeKbn, int userSid)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getSml330sortLabel();
        if (keyList == null || keyList.length < 1) {
            return;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getSml330SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return;
        }
        Sml330Dao smlDao = new Sml330Dao(con);
        SmlFilterSortDao sortDao = new SmlFilterSortDao(con);
        SortChangeBiz<SmlFilterSortModel> sortBiz =
                SortChangeBiz.<SmlFilterSortModel> builder()
                .setFuncTargetList(() -> {
                    List<SmlFilterSortModel> sortList = new ArrayList<SmlFilterSortModel>();
                    List<Sml330FilterDataModel> filterList
                                      = smlDao.getFlterList(paramMdl.getSmlAccountSid(), userSid);
                    for (Sml330FilterDataModel mdl : filterList) {
                        SmlFilterSortModel sortMdl = new SmlFilterSortModel();
                        sortMdl.setSacSid(paramMdl.getSmlAccountSid());
                        sortMdl.setSftSid(mdl.getFilterSid());
                        sortMdl.setSfsSort(mdl.getFilterSort());
                        sortList.add(sortMdl);
                    }
                    return sortList;
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getSftSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getSfsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getSftSid() == m2.getSftSid()) {
                        return 0;
                    } else {
                        return (m1.getSftSid() - m2.getSftSid())
                                / Math.abs(m1.getSftSid() - m2.getSftSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    sortDao.updateOrder(m.getSftSid(), newSort);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
