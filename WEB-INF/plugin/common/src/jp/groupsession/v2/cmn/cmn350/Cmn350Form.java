package jp.groupsession.v2.cmn.cmn350;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn350Form extends AbstractGsForm {

    /** 六曜表示 */
    private int cmn350RokuyoDsp__ = -1;

    /**
     * <p>cmn350RokuyoDsp を取得します。
     * @return cmn350RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn350.Cmn350Form#cmn350RokuyoDsp__
     */
    public int getCmn350RokuyoDsp() {
        return cmn350RokuyoDsp__;
    }
    /**
     * <p>cmn350RokuyoDsp をセットします。
     * @param cmn350RokuyoDsp cmn350RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn350.Cmn350Form#cmn350RokuyoDsp__
     */
    public void setCmn350RokuyoDsp(int cmn350RokuyoDsp) {
        cmn350RokuyoDsp__ = cmn350RokuyoDsp;
    }

   /**
    *
    * <br>[機  能] 入力チェックを行う
    * <br>[解  説]
    * <br>[備  考] 六曜表示のフォーマットチェック
    * @param req リクエストモデル
    * @return ActionErrors アクションエラー
    */
   public ActionErrors validateCheck(
           HttpServletRequest req) {

       ActionErrors errors = new ActionErrors();
       GsMessage gsMsg = new GsMessage(req);
       int rokuyou = getCmn350RokuyoDsp();
       String dispConf = gsMsg.getMessage("cmn.cmn320.01");
       ActionMessage message = new ActionMessage(
               "error.select3.required.text", dispConf);
       if (rokuyou != GSConst.DSP_OK && rokuyou != GSConst.DSP_NOT) {
           errors.add("error.select3.required.text", message);
       }
       return errors;
   }
}