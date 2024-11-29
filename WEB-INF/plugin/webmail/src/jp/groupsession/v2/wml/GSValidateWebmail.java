package jp.groupsession.v2.wml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.WmlValidateMailAddressResultModel;

/**
 * <br>[機  能] WEBメールの入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateWebmail {

    /**
     * <br>[機  能] テキストボックスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        boolean ret = false;

        ActionErrors cmnErrors = new ActionErrors();
        GSValidateCommon.validateTextField(
                cmnErrors, target, targetName, targetJp, maxLength, checkNoInput);

        @SuppressWarnings("unchecked")
        Iterator<ActionMessage> msgList = cmnErrors.get();

        while (msgList.hasNext()) {
            ActionMessage msg = msgList.next();
            errors.add(targetJp, msg);
            ret = true;
        }
        return ret;
    }
    /**
     * <br>[機  能] メールアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param tldList TLD制限許可リスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateMailTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput,
            String[] tldList) {

        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //メールフォーマットチェック
        if (!GSValidateUtil.isMailFormat(target, tldList)) {
            msg = new ActionMessage("error.input.format.text", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix
                    + "error.input.format.text");
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] メールアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @param tldList TLD制限許可リスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateMailTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput,
            RequestModel reqMdl,
            String[] tldList) {
        ActionMessage msg = null;

        int errCnt = errors.size();
        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //メールフォーマットチェック
        if (errCnt == errors.size()) {
            WmlValidateMailAddressResultModel resultMdl
                = validateMailAddress(
                        target, targetJp,
                        reqMdl, Encoding.ISO_2022_JP, tldList);

            List<ActionMessage> errMsgList = resultMdl.getErrMessageList();
            if (!errMsgList.isEmpty()) {
                for (int idx = 0; idx < errMsgList.size(); idx++) {
                    StrutsUtil.addMessage(errors, errMsgList.get(idx),
                                                    targetName + "_" + idx);
                }
            }
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] メールアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateMailWithNameTextBoxInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //メールフォーマットチェック
        boolean addressError = true;
        try {
            InternetAddress[] address = WmlBiz.parseAddress(target);
            addressError = address.length != 1;
        } catch (AddressException e) {
        }

        if (addressError) {
            msg = new ActionMessage("error.input.format.text", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix
                    + "error.input.format.text");
        }

        //入力エラー無し
        return addressError;
    }

    /**
     * <br>[機  能] アドレス検索の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateMailSearchInput(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            String msgKey = "error.input.spase.only";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            String msgKey = "error.input.spase.start";
            msg = new ActionMessage(msgKey, targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return true;
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            msg = new ActionMessage("error.input.njapan.text",
                    targetJp, nstr);
            StrutsUtil.addMessage(errors, msg,
                                     fieldFix + "error.input.njapan.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] スペースのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpace(value)) {
            String msgKey = "error.input.spase.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 先頭スペースチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateInitialSpace(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceStart(value)) {
            String msgKey = "error.input.spase.start";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 数字入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNumber(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        ActionMessage msg = null;

        String fieldFix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        if (value.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (!GSValidateUtil.isNumber(value)) {
            //半角数字チェック
            String msgKey = "error.input.number.hankaku";
            msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        return true;
    }


    public static boolean validateNumber2(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        ActionMessage msg = null;

        String fieldFix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            return false;
        }

        if (value.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        if (!GSValidateUtil.isNumber(value)) {
            //半角数字チェック
            String msgKey = "error.input.number.hankaku";
            msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param target チェック対象
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateTextarea(
            ActionErrors errors,
            String target,
            String targetName,
            String targetJp,
            int maxLength,
            boolean checkNoInput) {
        ActionMessage msg = null;

        String fieldFix = targetName + ".";

        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return false;
            }

            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetJp);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.required.text");
            return true;
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            msg = new ActionMessage("error.input.spase.cl.only", targetJp);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.spase.cl.only");
            return true;
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            msg = new ActionMessage("error.input.njapan.text", targetJp, nstr);
            StrutsUtil.addMessage(errors, msg, fieldFix + "error.input.njapan.text");
            return true;
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", targetJp, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldFix + "error.input.length.text");
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] ラジオボタンの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param value チェック対象
     * @param target チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param radioValueList ラジオボックスの値一覧
     * @return ActionErrors
     */
    public static ActionErrors validateRadioInput(
            RequestModel reqMdl,
            ActionErrors errors,
            int value,
            String target,
            String targetJp,
            int[] radioValueList) {

        for (int radioValue : radioValueList) {
            if (radioValue == value) {
                return errors;
            }
        }

        String eprefix = target + ".";
        ActionMessage msg = new ActionMessage("error.select.required.text",
                    targetJp);
        StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");

        return errors;
    }

    /**
     * <br>[機  能] 日付の論理チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param errors ActionErrors
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @param year 年
     * @param month 月
     * @param day 日
     * @param neccesary 入力必須項目か
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static boolean validateDate(
            HttpServletRequest req,
            ActionErrors errors,
            String targetName,
            String targetJp,
            int year,
            int month,
            int day,
            boolean neccesary) {

        ActionMessage msg = null;
        String eprefix = "date";
        String fieldFix = targetName + ".";

        GsMessage gsMsg = new GsMessage();
        String inputedDate = gsMsg.getMessage(req, "cmn.year", String.valueOf(year))
                            + month + gsMsg.getMessage(req, "cmn.month")
                            + day + gsMsg.getMessage(req, "cmn.day");

        if (year == GSConstWebmail.SELECT_DATECOMBO
                && month == GSConstWebmail.SELECT_DATECOMBO
                && day == GSConstWebmail.SELECT_DATECOMBO) {

            if (neccesary) {
                msg = new ActionMessage("error.select.required.text", targetJp);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                        + "error.select.required.text");
            }
            return false;
        }

        if (year == GSConstWebmail.SELECT_DATECOMBO
                || month == GSConstWebmail.SELECT_DATECOMBO
                || day == GSConstWebmail.SELECT_DATECOMBO) {
            msg = new ActionMessage("error.select.required.text", targetJp);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.select.required.text");
            return true;
        }

        int iBYear = year;
        int iBMonth = month;
        int iBDay = day;

        UDate date = new UDate();
        date.setDate(iBYear, iBMonth, iBDay);

        if (date.getYear() != iBYear
        || date.getMonth() != iBMonth
        || date.getIntDay() != iBDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    targetJp + "（" + inputedDate + ")");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldFix
                    + "error.input.notfound.date");
            return true;
        }
        //エラー無し
        return false;
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param chkFlg1 チェックフラグ1
     * @param chkFlg2 チェックフラグ2
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountFlg(
            RequestModel reqMdl,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int chkFlg1,
            int chkFlg2) {
        return validateCsvAccountFlg(
                reqMdl,
                errors,
                target,
                chkFlgName,
                targetJp,
                chkFlg1,
                chkFlg2,
                false);
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param chkFlg1 チェックフラグ1
     * @param chkFlg2 チェックフラグ2
     * @param noInput true: 入力必須、false: 任意入力
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountFlg(
            RequestModel reqMdl,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int chkFlg1,
            int chkFlg2,
            boolean noInput) {
        return validateCsvAccountFlg(
                reqMdl, errors, target,
                chkFlgName, targetJp,
                new int[] {chkFlg1, chkFlg2},
                noInput);
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param chkFlgList チェックフラグ
     * @param noInput true: 入力必須、false: 任意入力
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountFlg(
            RequestModel reqMdl,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int[] chkFlgList,
            boolean noInput) {

        ActionMessage msg = null;
        String eprefix = chkFlgName + ".";

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(target) || ValidateUtil.isSpace(target)) {
            if (!noInput) {
                return errors;
            }
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    targetJp);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (!GSValidateUtil.isNumber(target)) {
            //数値チェック
            msg = new ActionMessage("error.input.comp.text",
                    targetJp,
                    gsMsg.getMessage("cmn.numbers"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        } else {

            //指定された値かをチェック
            boolean result = false;
            for (int chkFlg : chkFlgList) {
                if (target.equals(String.valueOf(chkFlg))) {
                    result = true;
                    break;
                }
            }

            if (!result) {
                String[] params = null;
                String messageKey = null;
                if (chkFlgList.length == 2) {
                    params = new String[] {String.valueOf(chkFlgList[0]),
                                                        String.valueOf(chkFlgList[1])};
                    messageKey = "wml.134";
                } else {
                    params = new String[] {String.valueOf(chkFlgList[0]),
                                            String.valueOf(chkFlgList[1]),
                                            String.valueOf(chkFlgList[2])};
                    messageKey = "wml.277";
                }
                msg = new ActionMessage("error.input.comp.text",
                        targetJp, gsMsg.getMessage(messageKey, params));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
            }
        }

        return errors;
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param kbnList 区分値(複数)
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountKbn(
            RequestModel reqMdl,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int[] kbnList) {
        return validateCsvAccountKbn(
                reqMdl, errors, target,
                chkFlgName, targetJp, kbnList,
                true);
    }

    /**
     * <p>アカウント登録のチェックを行う(CSV用)
     * @param reqMdl リクエスト情報
     * @param errors ActionErrors
     * @param target 対象
     * @param chkFlgName チェックするフラグの名称
     * @param targetJp チェック対象名(日本語)
     * @param kbnList 区分値(複数)
     * @param noInput true: 入力必須、false: 任意入力
     * @return ActionErrors
     */
    public static ActionErrors validateCsvAccountKbn(
            RequestModel reqMdl,
            ActionErrors errors,
            String target,
            String chkFlgName,
            String targetJp,
            int[] kbnList,
            boolean noInput) {

        ActionMessage msg = null;
        String eprefix = chkFlgName + ".";

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(target) || ValidateUtil.isSpace(target)) {
            if (!noInput) {
                return errors;
            }
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    targetJp);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        } else if (!GSValidateUtil.isNumber(target)) {
            //数値チェック
            msg = new ActionMessage("error.input.comp.text",
                    targetJp,
                    gsMsg.getMessage("cmn.numbers"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
        } else {
            boolean errFlg = true;
            String valueText = "";
            for (int kbn : kbnList) {
                //指定された値かをチェック
                if (target.equals(String.valueOf(kbn))) {
                    errFlg = false;
                }

                if (valueText.length() > 0) {
                    valueText += ", ";
                }
                valueText += kbn;
            }

            if (errFlg) {
                msg = new ActionMessage("error.input.comp.text.either",
                                                        targetJp, valueText);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text.either");
            }
        }

        return errors;
    }

    /**
     * <p>ユーザＩＤ/グループIDの入力チェックを行う(CSV取込み時)
     * @param errors ActionErrors
     * @param id ユーザID or グループID
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param index 番号
     * @param userKbn ユーザ/グループ区分(0:ユーザ, 1:グループ)
     * @return ActionErrors
     */
    public static ActionErrors validateCsvUserId(
            ActionErrors errors,
            String id,
            String targetName,
            String targetJp,
            int index,
            String userKbn) {

        ActionMessage msg = null;
        String eprefix = targetName + index + ".";

        if (NullDefault.getString(userKbn, "").equals("1")) {
            if (id.length() > GSConstUser.MAX_LENGTH_GROUPID) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length2.text",
                        targetJp + index,
                        GSConstUser.MAX_LENGTH_GROUPID);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
            }
        } else {
            if (!(StringUtil.isNullZeroString(id))) {
                if (id.length() < GSConstUser.MIN_LENGTH_USERID
                || id.length() > GSConstUser.MAX_LENGTH_USERID) {
                    //MIN,MAX桁チェック
                    msg = new ActionMessage("error.input.length2.text",
                            targetJp + index,
                            GSConstUser.MIN_LENGTH_USERID, GSConstUser.MAX_LENGTH_USERID);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length2.text");
                } else if (!GSValidateUtil.isUseridFormat(id)) {
                    //ユーザＩＤフォーマットチェック
                    msg = new ActionMessage("error.input.format.text",
                            targetJp + index);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] ユーザID/グループIDの存在チェックを行う(CSV取込み時)
     * <br>[解  説]
     * <br>[備  考] ユーザIDの場合、自分のユーザIDは除く
     * @param errors ActionErrors
     * @param id ユーザID or グループID
     * @param targetName 対象名
     * @param targetJp チェック対象名(日本語)
     * @param con DBコネクション
     * @param index 番号
     * @param userKbn ユーザ/グループ区分(0:ユーザ, 1:グループ)
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    public static ActionErrors validateCsvUserIdExist(
            ActionErrors errors,
            String id,
            String targetName,
            String targetJp,
            Connection con,
            int index,
            String userKbn) throws SQLException {

        String indexStr = "";

        if (index > 0) {
            indexStr = String.valueOf(index);
        }

        ActionMessage msg = null;
        String eprefix = targetName + indexStr + ".";
        if (!(StringUtil.isNullZeroString(id))) {
            if (NullDefault.getString(userKbn, "").equals("1")) {
                //グループIDの存在チェック
                CmnGroupmDao grpDao = new CmnGroupmDao(con);
                if (!grpDao.existGroupid(id)) {
                    //存在しない場合のエラー
                    msg = new ActionMessage("error.not.exist.userid",
                            targetJp + indexStr);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
                }
            } else {
                //ユーザIDの存在チェック
                CmnUsrmDao dao = new CmnUsrmDao(con);
                boolean ret = dao.existLoginidEdit(-1, id);
                if (!ret) {
                    //存在しない場合のエラー
                    msg = new ActionMessage("error.not.exist.userid",
                            targetJp + indexStr);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.not.exist.userid");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] メールアドレスのフォーマットチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param nameJp パラメータ名(日本語名称)
     * @param reqMdl リクエスト情報
     * @param encode 文字コード
     * @param tldList TLD制限許可リスト
     * @return チェック結果
     */
    public static WmlValidateMailAddressResultModel validateMailAddress(
                                                String address, String nameJp,
                                                RequestModel reqMdl, String encode,
                                                String[] tldList) {

        WmlValidateMailAddressResultModel resultMdl
            = new WmlValidateMailAddressResultModel();

        String[] message = null;
        InternetAddress[] addressList = null;
        List<String> errAddressList = new ArrayList<String>();
        List<ActionMessage> errMsgList = new ArrayList<ActionMessage>();

        if (!StringUtil.isNullZeroString(address)) {
            try {
                addressList = WmlBiz.parseAddressPlus(address, encode);
            } catch (Exception e) {
                String[] mailList = address.split(",");
                for (String mail : mailList) {
                    try {
                        addressList = WmlBiz.parseAddressPlus(mail, encode);
                        for (InternetAddress addressData : addressList) {
                            if (!GSValidateUtil.isMailFormat(addressData.getAddress(), tldList)) {
                                errAddressList.add(mail);
                                break;
                            }
                        }
                    } catch (Exception mailE) {
                        errAddressList.add(mail);
                    }
                }
                addressList = null;
            }
            GsMessage gsMsg = new GsMessage(reqMdl);
            if (addressList == null || addressList.length == 0) {
                if (errAddressList.isEmpty()) {
                    message = new String[] {gsMsg.getMessage("wml.221", new String[] {nameJp})};
                    errMsgList.add(
                                    new ActionMessage("error.incorrect.format.mailaddress",
                                                                nameJp));
                }
            } else {
                //メールアドレスフォーマットチェック
                for (InternetAddress addressData : addressList) {
                    if (!GSValidateUtil.isMailFormat(addressData.getAddress(), tldList)) {
                        errAddressList.add(addressData.getAddress());
                    }
                }
                if (checkAddress(address)) {
                    message = new String[] {gsMsg.getMessage("wml.221", new String[] {nameJp})};
                    errMsgList.add(
                            new ActionMessage("error.incorrect.format.mailaddress",
                                                        nameJp));
                }
            }
            if (!errAddressList.isEmpty()) {
                String errAddressMsg = "";
                String errAddressMsgErr = "";
                boolean brFlg = false;
                for (String errAddress : errAddressList) {
                    if (brFlg) {
                        errAddressMsg += "\r\n";
                        errAddressMsgErr += "<br>";
                    } else {
                        brFlg = true;
                    }
                    errAddressMsg += "・" + errAddress;
                    errAddressMsgErr += "・"
                                            + StringUtilHtml.transToHTmlPlusAmparsant(errAddress);
                }

                message = new String[] {
                        gsMsg.getMessage("wml.274",
                                                    new String[] {nameJp, errAddressMsg})};
                errMsgList.add(
                                new ActionMessage("error.incorrect.format.mailaddress.list",
                                                    new String[] {nameJp, errAddressMsgErr}));
            }
        }

        resultMdl.setErrMessage(message);
        resultMdl.setErrMessageList(errMsgList);

        return resultMdl;
    }

    /**
     * <br>[機  能] アドレス不正文字チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param address 文字列
     * @return 文字列
     */
    public static boolean checkAddress(String address) {

        boolean bCheck = false;
        if (address == null) {
            return bCheck;
        }

        if (address.indexOf("\u0001", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0002", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0003", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0004", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0005", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0006", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0007", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0008", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u000b", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u000e", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u000f", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0010", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0011", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0012", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0013", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0014", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0015", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0016", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0017", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0018", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u0019", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001a", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001b", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001c", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001d", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001e", 0) != -1) {
            bCheck = true;
        }
        if (address.indexOf("\u001f", 0) != -1) {
            bCheck = true;
        }

        return bCheck;
    }

    /**
     * <br>[機  能] メール送信，草稿保存時の本文の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static String[] validateHonbun(
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage();
        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return null;
            }

            //未入力チェック
            return new String[]{gsMsg.getMessage("cmn.plz.input", new String[] {targetName})};
        }

        //スペース・改行のみチェック
        if (ValidateUtil.isSpaceOrKaigyou(target)) {
            return new String[]{gsMsg.getMessage("cmn.notinput.space.nline.only",
                                                                new String[] {targetName})};
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(target);
            return new String[]{gsMsg.getMessage("wml.222", new String[] {targetName}),
                    gsMsg.getMessage("wml.119") + nstr};
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            String[] params = {targetName, String.valueOf(maxLength)};
            return new String[]{gsMsg.getMessage("wml.224", params)};
        }

        //入力エラー無し
        return null;
    }

    /**
     * <br>[機  能] テキストボックス/テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static String[] validateText(
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return null;
            }

            //未入力チェック
            return new String[]{gsMsg.getMessage("cmn.plz.input", new String[] {targetName})};
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            String[] params = {targetName, String.valueOf(maxLength)};
            return new String[]{gsMsg.getMessage("wml.224", params)};
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            return new String[]{gsMsg.getMessage("wml.220", new String[] {targetName})};
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            return new String[]{gsMsg.getMessage("wml.223", new String[] {targetName})};
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            return new String[]{gsMsg.getMessage("wml.222", new String[] {targetName}),
                    gsMsg.getMessage("wml.119") + nstr};
        }

        //入力エラー無し
        return null;
    }
}