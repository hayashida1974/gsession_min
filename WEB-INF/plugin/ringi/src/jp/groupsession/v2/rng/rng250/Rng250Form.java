package jp.groupsession.v2.rng.rng250;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng080.Rng080Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議個人設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng250Form extends Rng080Form {

    /** ショートメール通知設定（管理者設定） */
    private int rng250AdmSmlNtf__ = RngConst.RAR_SML_NTF_USER;
    /** ショートメール通知(結果通知) */
    private int rng250smlNtf__ = RngConst.RNG_SMAIL_TSUUCHI;
    /** ショートメール通知(受信通知) */
    private int rng250smlNtfJyusin__ = RngConst.RNG_SMAIL_TSUUCHI;
    /** ショートメール通知(代理人通知) */
    private int rng250smlNtfDairi__ = RngConst.RNG_SMAIL_TSUUCHI;
    /** ショートメール利用設定*/
    private int canUseSml__ = 0;


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
       String eprefix = "rng250.";

       // 数値範囲チェック
       if (rng250smlNtfJyusin__ < RngConst.RNG_SMAIL_TSUUCHI
               || rng250smlNtfJyusin__ > RngConst.RNG_SMAIL_NOT_TSUUCHI) {
           // 受信通知区分
           String checkObj = gsMsg.getMessage("rng.rng190.03");
           msg = new ActionMessage(msgKey, ringi, checkObj);
           StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
       }
       if (rng250smlNtf__ < RngConst.RNG_SMAIL_TSUUCHI
               || rng250smlNtf__ > RngConst.RNG_SMAIL_NOT_TSUUCHI) {
           // 結果通知区分
           String checkObj = gsMsg.getMessage("rng.rng190.01");
           msg = new ActionMessage(msgKey, ringi, checkObj);
           StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
       }
       if (rng250smlNtfDairi__ < RngConst.RNG_SMAIL_TSUUCHI
               || rng250smlNtfDairi__ > RngConst.RNG_SMAIL_NOT_TSUUCHI) {
           // 代理人通知区分
           String checkObj = gsMsg.getMessage("rng.rng190.05");
           msg = new ActionMessage(msgKey, ringi, checkObj);
           StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
       }
       return errors;
   }

    /**
     * <p>rng250smlNtf を取得します。
     * @return rng250smlNtf
     */
    public int getRng250smlNtf() {
        return rng250smlNtf__;
    }

    /**
     * <p>rng250smlNtf をセットします。
     * @param rng250smlNtf rng250smlNtf
     */
    public void setRng250smlNtf(int rng250smlNtf) {
        rng250smlNtf__ = rng250smlNtf;
    }

    /**
     * <p>rng250AdmSmlNtf を取得します。
     * @return rng250AdmSmlNtf
     */
    public int getRng250AdmSmlNtf() {
        return rng250AdmSmlNtf__;
    }

    /**
     * <p>rng250AdmSmlNtf をセットします。
     * @param rng250AdmSmlNtf rng250AdmSmlNtf
     */
    public void setRng250AdmSmlNtf(int rng250AdmSmlNtf) {
        rng250AdmSmlNtf__ = rng250AdmSmlNtf;
    }

    /**
     * <p>rng250smlNtfJyusin を取得します。
     * @return rng250smlNtfJyusin
     */
    public int getRng250smlNtfJyusin() {
        return rng250smlNtfJyusin__;
    }

    /**
     * <p>rng250smlNtfJyusin をセットします。
     * @param rng250smlNtfJyusin rng250smlNtfJyusin
     */
    public void setRng250smlNtfJyusin(int rng250smlNtfJyusin) {
        rng250smlNtfJyusin__ = rng250smlNtfJyusin;
    }

    /**
     * <p>rng250smlNtfDairi を取得します。
     * @return rng250smlNtfDairi
     */
    public int getRng250smlNtfDairi() {
        return rng250smlNtfDairi__;
    }

    /**
     * <p>rng250smlNtfDairi をセットします。
     * @param rng250smlNtfDairi rng250smlNtfDairi
     */
    public void setRng250smlNtfDairi(int rng250smlNtfDairi) {
        rng250smlNtfDairi__ = rng250smlNtfDairi;
    }

    /**
     * <p>canUseSml を取得します。
     * @return canUseSml
     */
    public int getCanUseSml() {
        return canUseSml__;
    }

    /**
     * <p>canUseSml をセットします。
     * @param canUseSml canUseSml
     */
    public void setCanUseSml(int canUseSml) {
        canUseSml__ = canUseSml;
    }

}
