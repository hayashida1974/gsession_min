package jp.groupsession.v2.cmn.cmn320;

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
public class Cmn320Form extends AbstractGsForm {

    /** 六曜表示区分 */
    private int cmn320RokuyoDspKbn__ = -1;
    /** 六曜表示 */
    private int cmn320RokuyoDsp__ = -1;

    /**
     * <p>cmn320RokuyoDspKbn を取得します。
     * @return cmn320RokuyoDspKbn
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320ParamModel#cmn320RokuyoDspKbn__
     */
    public int getCmn320RokuyoDspKbn() {
        return cmn320RokuyoDspKbn__;
    }
    /**
     * <p>cmn320RokuyoDspKbn をセットします。
     * @param cmn320RokuyoDspKbn cmn320RokuyoDspKbn
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320ParamModel#cmn320RokuyoDspKbn__
     */
    public void setCmn320RokuyoDspKbn(int cmn320RokuyoDspKbn) {
        cmn320RokuyoDspKbn__ = cmn320RokuyoDspKbn;
    }
    /**
     * <p>cmn320RokuyoDsp を取得します。
     * @return cmn320RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320Form#cmn320RokuyoDsp__
     */
    public int getCmn320RokuyoDsp() {
        return cmn320RokuyoDsp__;
    }
    /**
     * <p>cmn320RokuyoDsp をセットします。
     * @param cmn320RokuyoDsp cmn320RokuyoDsp
     * @see jp.groupsession.v2.cmn.cmn320.Cmn320Form#cmn320RokuyoDsp__
     */
    public void setCmn320RokuyoDsp(int cmn320RokuyoDsp) {
        cmn320RokuyoDsp__ = cmn320RokuyoDsp;
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

       int rokuyouKbn = getCmn320RokuyoDspKbn();
       String dispConf = gsMsg.getMessage("cmn.cmn320.05");
       ActionMessage message = new ActionMessage(
               "error.select3.required.text", dispConf);
       if (rokuyouKbn != GSConst.SETTING_ADM && rokuyouKbn != GSConst.SETTING_USR) {
           errors.add("error.select3.required.text", message);
       }
       int rokuyou = getCmn320RokuyoDsp();
       dispConf = gsMsg.getMessage("cmn.cmn320.01");
       message = new ActionMessage(
               "error.select3.required.text", dispConf);
       if (rokuyou != GSConst.DSP_OK && rokuyou != GSConst.DSP_NOT) {
           errors.add("error.select3.required.text", message);
       }
       return errors;
   }
}