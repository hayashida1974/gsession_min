package jp.groupsession.v2.rng.rng190;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng190Form extends Rng040Form {

    /** ショートメール通知設定 0:各ユーザが設定 1:管理者が設定*/
    private int rng190SmlNtf__ = RngConst.RAR_SML_NTF_USER;
    /** 結果通知区分 0:通知しない 0:通知する 1:通知しない */
    private int rng190SmlNtfKbn__ = RngConst.RAR_SML_NTF_KBN_YES;
    /** 受信通知区分 0:通知しない 0:通知する 1:通知しない*/
    private int rng190SmlJusin__ = RngConst.RAR_SML_NTF_KBN_YES;
    /** 代理人通知区分 0:通知しない 0:通知する 1:通知しない */
    private int rng190SmlDairi__ = RngConst.RAR_SML_NTF_KBN_YES;

    /**
     *
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        String ringi = gsMsg.getMessage("rng.62");
        String msgKey = "error.input.sml.setting";
        String eprefix = "rng190.";

        // 数値範囲チェック
        if (rng190SmlNtf__ < RngConst.RAR_SML_NTF_USER
                || rng190SmlNtf__ > RngConst.RAR_SML_NTF_ADMIN) {
            // ショートメール通知設定
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, ringi, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (rng190SmlJusin__ < RngConst.RAR_SML_NTF_KBN_YES
                || rng190SmlJusin__ > RngConst.RAR_SML_NTF_KBN_NO) {
            // 受信通知区分
            String checkObj = gsMsg.getMessage("rng.rng190.03");
            msg = new ActionMessage(msgKey, ringi, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (rng190SmlNtfKbn__ < RngConst.RAR_SML_NTF_KBN_YES
                || rng190SmlNtfKbn__ > RngConst.RAR_SML_NTF_KBN_NO) {
            // 結果通知区分
            String checkObj = gsMsg.getMessage("rng.rng190.01");
            msg = new ActionMessage(msgKey, ringi, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (rng190SmlDairi__ < RngConst.RAR_SML_NTF_KBN_YES
                || rng190SmlDairi__ > RngConst.RAR_SML_NTF_KBN_NO) {
            // 代理人通知区分
            String checkObj = gsMsg.getMessage("rng.rng190.05");
            msg = new ActionMessage(msgKey, ringi, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        return errors;
    }

    /**
     * <p>rng190SmlNtf を取得します。
     * @return rng190SmlNtf
     */
    public int getRng190SmlNtf() {
        return rng190SmlNtf__;
    }
    /**
     * <p>rng190SmlNtf をセットします。
     * @param rng190SmlNtf rng190SmlNtf
     */
    public void setRng190SmlNtf(int rng190SmlNtf) {
        rng190SmlNtf__ = rng190SmlNtf;
    }
    /**
     * <p>rng190SmlNtfKbn を取得します。
     * @return rng190SmlNtfKbn
     */
    public int getRng190SmlNtfKbn() {
        return rng190SmlNtfKbn__;
    }
    /**
     * <p>rng190SmlNtfKbn をセットします。
     * @param rng190SmlNtfKbn rng190SmlNtfKbn
     */
    public void setRng190SmlNtfKbn(int rng190SmlNtfKbn) {
        rng190SmlNtfKbn__ = rng190SmlNtfKbn;
    }
    /**
     * <p>rng190SmlJusin を取得します。
     * @return rng190SmlJusin
     */
    public int getRng190SmlJusin() {
        return rng190SmlJusin__;
    }
    /**
     * <p>rng190SmlJusin をセットします。
     * @param rng190SmlJusin rng190SmlJusin
     */
    public void setRng190SmlJusin(int rng190SmlJusin) {
        rng190SmlJusin__ = rng190SmlJusin;
    }
    /**
     * <p>rng190SmlDairi を取得します。
     * @return rng190SmlDairi
     */
    public int getRng190SmlDairi() {
        return rng190SmlDairi__;
    }
    /**
     * <p>rng190SmlDairi をセットします。
     * @param rng190SmlDairi rng190SmlDairi
     */
    public void setRng190SmlDairi(int rng190SmlDairi) {
        rng190SmlDairi__ = rng190SmlDairi;
    }
}
