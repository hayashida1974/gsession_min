package jp.groupsession.v2.wml.wml110;

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
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelRelationDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataSortDao;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <br>[機  能] WEBメール ラベルの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml110Biz.class);

    /**
     * <br>[機  能] ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteLabel(Connection con, Wml110ParamModel paramMdl) throws SQLException {

        boolean commitFlg = false;

        try {
            //ラベルを削除する
            WmlLabelDao wlDao = new WmlLabelDao(con);
            wlDao.delete(paramMdl.getWmlEditLabelId());

            //メール - ラベルを削除する
            WmlLabelRelationDao wlrDao = new WmlLabelRelationDao(con);
            wlrDao.delete(paramMdl.getWmlEditLabelId());

            //メール情報表示順を削除する
            WmlMaildataSortDao mailSortDao = new WmlMaildataSortDao(con);
            mailSortDao.deleteMailSortOfLabel(paramMdl.getWmlEditLabelId());

            //コミット実行
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
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
                             Wml110ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        List<AccountDataModel> adMdlList = new ArrayList<AccountDataModel>();
        List<WmlLabelModel> ldMdlList = new ArrayList<WmlLabelModel>();
        Wml110Dao dao = new Wml110Dao(con);

        WmlBiz comonBiz = new WmlBiz();

        //アカウントリストを取得
        adMdlList = dao.getAccountList(userSid);
        paramMdl.setAcntList(comonBiz.getAcntCombo(reqMdl, adMdlList));

        //ラベルリストを取得
        int dspCnt = paramMdl.getDspCount();
        if (dspCnt == 0 && adMdlList.size() != 0) {
            paramMdl.setWml110accountSid(adMdlList.get(0).getAccountSid());
            paramMdl.setDspCount(1);
        }

        int selectAccountNum = paramMdl.getWml110accountSid();
        WmlLabelDao wlbDao = new WmlLabelDao(con);
        ldMdlList = wlbDao.getLabelList(selectAccountNum);

        //ラベルを画面表示用に加工する
        WmlLabelBiz labelBiz = new WmlLabelBiz();
        List<LabelDataModel> lbList = labelBiz.getLabelDataList(ldMdlList);
        paramMdl.setLbnList(lbList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getWml110SortRadio())
            && ldMdlList.size() > 0) {

            WmlLabelModel wldMdl = ldMdlList.get(0);
            paramMdl.setWml110SortRadio(String.valueOf(wldMdl.getWlbSid()));
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
    public boolean updateSort(Connection con, Wml110ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getWml110sortLabel();
        if (keyList == null || keyList.length < 1) {
            return false;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getWml110SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return false;
        }
        int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return false;
        }

        SortResult<WmlLabelModel> result = null;

        WmlLabelDao labelDao = new WmlLabelDao(con);
        SortChangeBiz<WmlLabelModel> sortBiz =
                SortChangeBiz.<WmlLabelModel> builder()
                .setFuncTargetList(() -> {
                    return labelDao.getLabelList(paramMdl.getWml110accountSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getWlbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getWlbOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getWlbSid() == m2.getWlbSid()) {
                        return 0;
                    } else {
                        return (m1.getWlbSid() - m2.getWlbSid())
                                / Math.abs(m1.getWlbSid() - m2.getWlbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    labelDao.updateOrder(m.getWlbSid(), newSort);
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
     * <br>[機  能] ラベル名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getLabelName(Connection con,
                             int targetSid) throws SQLException {

        WmlLabelDao labelDao = new WmlLabelDao(con);
        WmlLabelModel labelMdl = labelDao.select(targetSid);
        return labelMdl.getWlbName();
    }
}
