package jp.groupsession.v2.sml.sml310;

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
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlJmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.dao.SmlLabelSortDao;
import jp.groupsession.v2.sml.dao.SmlSmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlWmeisLabelDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.LabelDataModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;


/**
 * <br>[機  能] ショートメール ラベルの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml310Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml310Biz.class);

    /**
     * <br>[機  能] ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteLabel(Connection con, Sml310ParamModel paramMdl) throws SQLException {

        boolean commitFlg = false;

        try {
            //ラベルを削除する
            SmlLabelDao slDao = new SmlLabelDao(con);
            slDao.delete(paramMdl.getSmlEditLabelId());

            //メール - ラベルを削除する
            SmlJmeisLabelDao jDao = new SmlJmeisLabelDao(con);
            jDao.delete(paramMdl.getSmlEditLabelId());
            SmlSmeisLabelDao sDao = new SmlSmeisLabelDao(con);
            sDao.delete(paramMdl.getSmlEditLabelId());
            SmlWmeisLabelDao wDao = new SmlWmeisLabelDao(con);
            wDao.delete(paramMdl.getSmlEditLabelId());

            //メール情報表示順を削除する
            SmlLabelSortDao mailSortDao = new SmlLabelSortDao(con);
            mailSortDao.deleteMailSortOfLabel(paramMdl.getSmlEditLabelId());

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
                             Sml310ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        List<AccountDataModel> adMdlList = new ArrayList<AccountDataModel>();
        List<LabelDataModel> ldMdlList = new ArrayList<LabelDataModel>();
        Sml310Dao dao = new Sml310Dao(con);

//        SmlCommonBiz comonBiz = new SmlCommonBiz(con, reqMdl);
//        //アカウントリストを取得
//        adMdlList = dao.getAccountList(userSid);
//        paramMdl.setAcntList(comonBiz.getAcntCombo(reqMdl, adMdlList));
        //アカウント名取得
        int selectSacSid = paramMdl.getSmlAccountSid();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(selectSacSid);
        paramMdl.setSml310accountName(sacMdl.getSacName());

        //ラベルリストを取得
        int dspCnt = paramMdl.getDspCount();
        if (dspCnt == 0 && adMdlList.size() != 0) {
            paramMdl.setSmlAccountSid(adMdlList.get(0).getAccountSid());
            paramMdl.setDspCount(1);
        }

        int selectAccountNum = paramMdl.getSmlAccountSid();
        ldMdlList = dao.getLabelList(selectAccountNum);
        LabelDataModel sldDspMdl = null;

        //ラベルを画面表示用に加工する
        List<LabelDataModel> lbList = new ArrayList<LabelDataModel>();

        for (LabelDataModel sldSetMdl : ldMdlList) {
            sldDspMdl = new LabelDataModel();
            sldDspMdl.setLabelSid(sldSetMdl.getLabelSid());
            sldDspMdl.setLabelName(sldSetMdl.getLabelName());
            sldDspMdl.setLabelOrder(sldSetMdl.getLabelOrder());
            sldDspMdl.setLbValue(String.valueOf(sldSetMdl.getLabelSid()));
            lbList.add(sldDspMdl);
        }
        paramMdl.setLbnList(lbList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getSml310SortRadio())
            && ldMdlList.size() > 0) {

            LabelDataModel sldMdl = ldMdlList.get(0);
            paramMdl.setSml310SortRadio(String.valueOf(sldMdl.getLabelSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Sml310ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getSml310sortLabel();
        if (keyList == null || keyList.length < 1) {
            return;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getSml310SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return;
        }
        SmlLabelDao labelDao = new SmlLabelDao(con);
        SortResult<SmlLabelModel> result = null;
        SortChangeBiz<SmlLabelModel> sortBiz =
                SortChangeBiz.<SmlLabelModel> builder()
                .setFuncTargetList(() -> {
                    return labelDao.getLabelList(paramMdl.getSmlAccountSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getSlbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getSlbOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getSlbSid() == m2.getSlbSid()) {
                        return 0;
                    } else {
                        return (m1.getSlbSid() - m2.getSlbSid())
                                / Math.abs(m1.getSlbSid() - m2.getSlbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    labelDao.updateOrder(m.getSlbSid(), newSort);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
