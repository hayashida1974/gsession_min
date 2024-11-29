package jp.groupsession.v2.rng;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;

/**
 * <br>[機  能] 稟議の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngValidate {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngValidate.class);

    /** epefix 稟議*/
    private static final String RNG_E_RINGI__ = "ringi";
    /** 稟議テンプレートタイトル文字数 */
    protected static final int RNG_TPL_TITLE = 100;
    /** 稟議テンプレートフォーマット文字数 */
    protected static final int RNG_TPL_FORMAT = 1000;

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateCmnFieldText(
        ActionErrors errors,
        String checkObject,
        String field,
        String strField,
        int maxLength,
        boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = RNG_E_RINGI__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isTab(field)) {
            //タブスペースチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return ActionErrors
     */
    public static ActionErrors validateFieldTextArea(
            ActionErrors errors,
            String checkObject,
            String field,
            String strField,
            int maxLength,
            boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = RNG_E_RINGI__;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + fieldfix + strField);
            }
            return errors;
        }

        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.textarea", checkObject,
                    maxLength);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);

        //スペース、改行のみチェック
        } else if (ValidateUtil.isSpaceOrKaigyou(field)) {
            msg = new ActionMessage("error.input.spase.cl.only", checkObject);
            StrutsUtil.addMessage(errors, msg,
                    eprefix + fieldfix + strField);

        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + strField);
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除する稟議の選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param delRng 削除する稟議
     * @param strDelRng 削除する稟議の文字列
     * @return errors
     */
    public static ActionErrors validateDeleteRng(
            ActionErrors errors,
            String checkObject,
            String[] delRng,
            String strDelRng) {
        ActionMessage msg = null;
        String eprefix = RNG_E_RINGI__;
        String fieldfix = checkObject + ".";
        if (delRng == null || delRng.length <= 0) {
            //未選択チェック
            msg = new ActionMessage("error.select.required.text", checkObject);
            log__.debug("msg  = " + msg);
            log__.debug("eprefix  = " + eprefix);
            log__.debug("fieldfix  = " + fieldfix);
            log__.debug("strDelRng  = " + strDelRng);

            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strDelRng);
        }

        return errors;
    }

    /**
     * <br>[機能] 数量の入力チェックを行う
     * <br>[解説]
     * <br>[備考]
     * @param errors ActionErrors
     * @param nameStr テキストの入力値
     * @param checkObject チェック対象
     * @param maxLength 最大文字数
     * @return ActionErrors
     */
    public static ActionErrors validateNumberInt(
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

        //数値チェック
        if (!ValidateUtil.isNumber(nameStr)) {
            msg = new ActionMessage("error.input.number.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
        }

        //MAX桁チェック
        if (nameStr.length() > maxLength) {
            msg = new ActionMessage(
                    "error.input.length.text", checkObject, String.valueOf(maxLength));
            StrutsUtil.addMessage(errors, msg, checkObject);
        }
        //スペース（改行）のみチェック
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
     * <br>[機能] ラジオの入力チェックを行う
     * <br>[解説]
     * <br>[備考]
     * @param errors ActionErrors
     * @param nameStr テキストの入力値
     * @param checkObject チェック対象
     * @return ActionErrors
     */
    public static ActionErrors validateRadio(
        ActionErrors errors,
        String nameStr,
        String checkObject) {

        ActionMessage msg = null;

        //未入力チェック
        if (nameStr.equals("0")) {
            msg = new ActionMessage("error.input.required.text", checkObject);
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }
        return errors;
    }

}
