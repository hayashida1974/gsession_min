package jp.groupsession.v2.sml.sml390kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlBanDestConfDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBanDestPermitDao;
import jp.groupsession.v2.sml.model.SmlBanDestConfModel;
import jp.groupsession.v2.sml.model.SmlBanDestModel;
import jp.groupsession.v2.sml.model.SmlBanDestPermitModel;
import jp.groupsession.v2.sml.sml390.Sml390Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] 送信制限登録確認 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390knBiz extends Sml390Biz {
    /** ログ */
    private static Log log__ = LogFactory.getLog(Sml390knBiz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Sml390knParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {
        super.setInitData(con, paramMdl, reqMdl);

        String position = paramMdl.getPostCombo().get(0).getLabel();
        for (LabelValueBean lv : paramMdl.getPostCombo()) {
            if (lv.getValue().equals(String.valueOf(paramMdl.getSml390post()))) {
                position = lv.getLabel();
            }
        }
        paramMdl.setSml390knDspPosition(position);
        paramMdl.setSml390knDspBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml390biko()));

    }
    /**
     *
     * <br>[機  能] 送信制限設定を確定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void commitBanDestConf(Connection con, Sml390knParamModel paramMdl,
            MlCountMtController cntCon,
            RequestModel reqMdl) throws SQLException {
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        SmlBanDestPermitDao sbpDao = new SmlBanDestPermitDao(con);

        int sbcSid = paramMdl.getSml380EditBan();
        if (sbcSid <= 0) {
            log__.debug("新規登録");
            sbcSid = __insertBanDestConf(con, paramMdl, cntCon, reqMdl);
        } else {
            log__.debug("編集");
            __editBanDestConf(con, paramMdl, reqMdl);
            sbdDao.delete(sbcSid);
            sbpDao.delete(sbcSid);
        }
        //送信制限登録
        for (String id :paramMdl.getSml390sbdTarget()) {
            SmlBanDestModel sbdMdl = null;
            if (id.startsWith("G")) {
                //グループ
                sbdMdl =  new SmlBanDestModel();
                sbdMdl.setSbdTargetKbn(GSConstSmail.SBD_TARGET_KBN_GROUP);
                sbdMdl.setSbdTargetSid(NullDefault.getInt(id.substring(1), -1));
            } else if (NullDefault.getInt(id, -1) > 0) {
                //ユーザ
                sbdMdl =  new SmlBanDestModel();
                sbdMdl.setSbdTargetKbn(GSConstSmail.SBD_TARGET_KBN_USER);
                sbdMdl.setSbdTargetSid(NullDefault.getInt(id, -1));
            }
            if (sbdMdl != null) {
                sbdMdl.setSbcSid(sbcSid);
                sbdDao.insert(sbdMdl);
            }
        }
        //代表アカウント
        for (String id :paramMdl.getSml390sbdTargetAcc()) {
            if (NullDefault.getInt(id, -1) > 0) {
                SmlBanDestModel sbdMdl =  new SmlBanDestModel();
                sbdMdl.setSbdTargetKbn(GSConstSmail.SBD_TARGET_KBN_ACCOUNT);
                sbdMdl.setSbdTargetSid(NullDefault.getInt(
                        id, -1));

                sbdMdl.setSbcSid(sbcSid);
                sbdDao.insert(sbdMdl);
            }
        }

        //送信制限許可登録
        if (paramMdl.getSml390post() > 0) {
            SmlBanDestPermitModel sbpMdl =  new SmlBanDestPermitModel();
            sbpMdl.setSbpTargetKbn(GSConstSmail.SBP_TARGET_KBN_POST);
            sbpMdl.setSbpTargetSid(paramMdl.getSml390post());
            sbpMdl.setSbcSid(sbcSid);
            sbpDao.insert(sbpMdl);
        }
        for (String id :paramMdl.getSml390sbpTarget()) {
            SmlBanDestPermitModel sbpMdl = null;
            if (id.startsWith("G")) {
                //グループ
                sbpMdl =  new SmlBanDestPermitModel();
                sbpMdl.setSbpTargetKbn(GSConstSmail.SBP_TARGET_KBN_GROUP);
                sbpMdl.setSbpTargetSid(NullDefault.getInt(id.substring(1), -1));

            } else if (NullDefault.getInt(id, -1) > 0) {
                //ユーザ
                sbpMdl =  new SmlBanDestPermitModel();
                sbpMdl.setSbpTargetKbn(GSConstSmail.SBP_TARGET_KBN_USER);
                sbpMdl.setSbpTargetSid(NullDefault.getInt(id, -1));
            }
            if (sbpMdl != null) {
                sbpMdl.setSbcSid(sbcSid);
                sbpDao.insert(sbpMdl);
            }
        }
    }
    /**
    *
    * <br>[機  能] 送信制限設定を新規登録する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータ情報
    * @param cntCon 採番コネクション
    * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @return 採番されたSID
    */
    private int __insertBanDestConf(Connection con, Sml390knParamModel paramMdl,
            MlCountMtController cntCon,
            RequestModel reqMdl) throws SQLException {
        int usrSid = reqMdl.getSmodel().getUsrsid();
        //送信制限管理SID採番
        int sbcSid = (int) cntCon.getSaibanNumber(
                GSConstSmail.PLUGIN_ID_SMAIL,
                GSConstSmail.SBNSID_SUB_SBC,
                usrSid);
        SmlBanDestConfModel sbcMdl = new SmlBanDestConfModel();
        sbcMdl.setSbcSid(sbcSid);
        sbcMdl.setSbcName(paramMdl.getSml390sbcName());
        sbcMdl.setSbcBiko(paramMdl.getSml390biko());
        UDate now = new UDate();
        sbcMdl.setSbcAuid(usrSid);
        sbcMdl.setSbcEuid(usrSid);
        sbcMdl.setSbcAdate(now);
        sbcMdl.setSbcEdate(now);
        SmlBanDestConfDao sbcDao = new SmlBanDestConfDao(con);
        sbcDao.insert(sbcMdl);
        return sbcSid;

    }
    /**
    *
    * <br>[機  能] 送信制限設定を編集する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータ情報
    * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
    */
    private void __editBanDestConf(Connection con, Sml390knParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {
        int usrSid = reqMdl.getSmodel().getUsrsid();
        int sbcSid = paramMdl.getSml380EditBan();

        SmlBanDestConfModel sbcMdl = new SmlBanDestConfModel();
        sbcMdl.setSbcSid(sbcSid);
        sbcMdl.setSbcName(paramMdl.getSml390sbcName());
        sbcMdl.setSbcBiko(paramMdl.getSml390biko());
        UDate now = new UDate();
        sbcMdl.setSbcAuid(usrSid);
        sbcMdl.setSbcEuid(usrSid);
        sbcMdl.setSbcAdate(now);
        sbcMdl.setSbcEdate(now);
        SmlBanDestConfDao sbcDao = new SmlBanDestConfDao(con);
        if (sbcDao.update(sbcMdl) == 0) {
            sbcDao.insert(sbcMdl);
        }
    }
}
