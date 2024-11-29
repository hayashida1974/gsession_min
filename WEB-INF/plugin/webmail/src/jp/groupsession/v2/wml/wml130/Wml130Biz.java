package jp.groupsession.v2.wml.wml130;

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
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterSortDao;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlFilterModel;
import jp.groupsession.v2.wml.model.base.WmlFilterSortModel;
import jp.groupsession.v2.wml.wml110.Wml110Dao;

/**
 * <br>[機  能] WEBメール フィルタ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml130Biz.class);

    /**
     * <br>[機  能] フィルターの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteFilter(Connection con, Wml130ParamModel paramMdl) throws SQLException {
        boolean commitFlg = false;

        try {
            //フィルターを削除する
            WmlFilterDao wfDao = new WmlFilterDao(con);
            wfDao.delete(paramMdl.getWmlEditFilterId());

            //フィルター_転送先メールアドレスを削除する
            WmlFilterFwaddressDao wfFwadrDao = new WmlFilterFwaddressDao(con);
            wfFwadrDao.delete(paramMdl.getWmlEditFilterId());

            //フィルター条件を削除する
            Wml130Dao dao = new Wml130Dao(con);
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
                             Wml130ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        WmlBiz wmlBiz = new WmlBiz();
        Wml110Dao wml110dao = new Wml110Dao(con);

        //アカウントリストを取得
        List<AccountDataModel> adMdlList = wml110dao.getAccountList(userSid);
        paramMdl.setAcntList(wmlBiz.getAcntCombo(reqMdl, adMdlList));

        //フィルター情報を取得
        int dspCnt = paramMdl.getDspCount();
        if (dspCnt == 0 && adMdlList.size() != 0) {
            paramMdl.setWml130accountSid(adMdlList.get(0).getAccountSid());
            paramMdl.setDspCount(1);
        }

        Wml130Dao wml130dao = new Wml130Dao(con);
        int selectAccountNum = paramMdl.getWml130accountSid();
        List<Wml130FilterDataModel> fdMdlList = wml130dao.getFlterList(selectAccountNum, userSid);
        Wml130FilterDataModel wfdDspMdl = null;

        //フィルター情報を画面表示用に加工する
        List<Wml130FilterDataModel> filDspList = new ArrayList<Wml130FilterDataModel>();

        for (Wml130FilterDataModel wfdSetMdl : fdMdlList) {
            wfdDspMdl = new Wml130FilterDataModel();
            wfdDspMdl.setFilterSid(wfdSetMdl.getFilterSid());
            wfdDspMdl.setFilterName(wfdSetMdl.getFilterName());
            wfdDspMdl.setFilterSort(wfdSetMdl.getFilterSort());
            wfdDspMdl.setFilValue(String.valueOf(wfdSetMdl.getFilterSid()));
            filDspList.add(wfdDspMdl);
        }
        paramMdl.setFilList(filDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getWml130SortRadio())
            && fdMdlList.size() > 0) {

            Wml130FilterDataModel wldMdl = fdMdlList.get(0);
            paramMdl.setWml130SortRadio(String.valueOf(wldMdl.getFilterSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return updateフラグ
     * @throws SQLException SQL実行時例外
     */
    public boolean updateSort(Connection con, Wml130ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getWml130sortLabel();
        if (keyList == null || keyList.length < 1) {
            return false;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getWml130SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return false;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return false;
        }
        SortResult<WmlFilterSortModel> result = null;

        WmlFilterSortDao sortDao = new WmlFilterSortDao(con);
        SortChangeBiz<WmlFilterSortModel> sortBiz =
                SortChangeBiz.<WmlFilterSortModel> builder()
                .setFuncTargetList(() -> {
                    return sortDao.selectList(paramMdl.getWml130accountSid());
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
                    sortDao.delete(paramMdl.getWml130accountSid(), m.getWftSid());
                    sortDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }

        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] アカウント名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSid アカウントSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getAccountName(Connection con,
                             int accountSid) throws SQLException {

        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(accountSid);
        return wacMdl.getWacName();
    }

    /**
     * <br>[機  能] フィルター名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getFilterName(Connection con,
                             int targetSid) throws SQLException {

        WmlFilterDao filterDao = new WmlFilterDao(con);
        WmlFilterModel filterMdl = filterDao.select(targetSid);
        return filterMdl.getWftName();
    }

}
