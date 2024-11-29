package jp.groupsession.v2.man;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン機能の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateMain {

    /**
     * <br>[機  能] 名称の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param nameStr 名称
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @return ActionErrors
     */
    public static ActionErrors validateNameStr(
        ActionErrors errors,
        String nameStr,
        String checkObject,
        int maxLength) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(nameStr)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (nameStr.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(nameStr)) {
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(nameStr)) {
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(nameStr)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(nameStr);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value テキストエリアの入力値
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @param hissuFlg 必須フラグ true=必須、false=任意入力
     * @return ActionErrors
     */
    public static ActionErrors validateTxtarea(
        ActionErrors errors,
        String value,
        String checkObject,
        int maxLength,
        boolean hissuFlg) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(value)) {
            if (hissuFlg) {
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(errors, msg, checkObject);
            }
            return errors;
        }

        //MAX桁チェック
        if (value.length() > maxLength) {
            msg = new ActionMessage("error.input.length.textarea", checkObject, maxLength);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペース(改行)のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            msg = new ActionMessage("error.input.spase.cl.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        return errors;
    }

    /**
     * <br>[機  能] ID(コード)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param idStr ID(コード)
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @return ActionErrors
     */
    public static ActionErrors validateIDStr(
        ActionErrors errors,
        String idStr,
        String checkObject,
        int maxLength) {

        ActionMessage msg = null;

        //未入力チェック
        if (StringUtil.isNullZeroString(idStr)) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }

        //MAX桁チェック
        if (idStr.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペースのみチェック
        if (ValidateUtil.isSpace(idStr)) {
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(idStr)) {
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(idStr)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(idStr);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //ID(コード)フォーマットチェック
        if (!GSValidateUtil.isOtheridFormat(idStr)) {
            msg = new ActionMessage("error.input.format.text", idStr);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        return errors;
    }
}
