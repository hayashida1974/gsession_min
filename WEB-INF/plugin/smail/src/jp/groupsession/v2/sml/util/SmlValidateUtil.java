package jp.groupsession.v2.sml.util;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] WEBメールプラグインの入力チェック機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlValidateUtil {

    /**
     * <p>日本語フィールドに利用可能な日本語文字列か判定を行う
     * @param str 文字列
     * @return true: 利用可能, false:利用不可能
     */
    public static boolean isJapaneaseString(String str) {
        String nstr = ValidateUtil.getNotJISXAscii(str);
        if (nstr == null) {
            //正常
            return true;
        } else {
            //異常 利用不可能な文字が含まれる
            return false;
        }
    }

    /**
     * <p>日本語フィールドに利用可能な日本語文字列か判定を行う(テキストエリア用)
     * <br>改行を許可
     * @param str 文字列
     * @return true: 利用可能, false:利用不可能
     */
    public static boolean isJapaneaseStringTextArea(String str) {
        String nstr = ValidateUtil.getNotJISXAsciiCrlf(str);
        if (nstr == null) {
            //正常
            return true;
        } else {
            //異常 利用不可能な文字が含まれる
            return false;
        }
    }

    /**
     * <p>日本語フィールドに利用不可能な日本語文字列の先頭1文字を取得する。
     * @param str 文字列
     * @return 利用不可能文字列,全て利用可能であればnullを返す
     */
    public static String getNotJapaneaseString(String str) {
        return ValidateUtil.getNotJISXAscii(str);
    }

    /**
     * <p>日本語フィールドに利用不可能な日本語文字列の先頭1文字を取得する。
     * @param str 文字列
     * @return 利用不可能文字列,全て利用可能であればnullを返す
     */
    public static String getNotJapaneaseStringTextArea(String str) {
        return ValidateUtil.getNotJISXAsciiCrlf(str);
    }

    /**
     * <p>全角カタカナか判定を行う。
     * <br>全角、半角スペースは認める。
     * @param kana 判定対象の文字列
     * @return true:全角カタカナ, false:全角カタカナではない
     */
    public static boolean isWideKana(String kana) {
        return ValidateUtil.isWideKanaSpOK(kana);
    }

    /**
     * <p>ユーザIDのフォーマットチェックを行う。
     * <br>半角英数、「.」、「_」、「-」、「@」が使用可能
     * @param input 文字列
     * @return true: 正常なユーザＩＤフォーマット, false: ユーザＩＤのフォーマットではない
     */
    public static boolean isUseridFormat(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Z0-9a-z@._\\-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>Asciiコード範囲内 or 数値か判定
     * @param input 文字列
     * @return true: 正常 false:エラー
     */
    public static boolean isAsciiOrNumber(String input) {

        boolean ret = true;
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            //ASCIIコード判定
            if (!StringUtil.isAscii(c[i])) {
                //数値判定
                if (!SmlValidateUtil.isNumber(String.valueOf(c[i]))) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * <p>パスワードのフォーマットチェックを行う。
     * <br>半角英数、「.」、「_」、「-」が使用可能
     * @param input 文字列
     * @return true: 正常なパスワードフォーマット, false: パスワードのフォーマットではない
     */
    public static boolean isPasswordFormat(String input) {
        Perl5Util util = new Perl5Util();

        // 半角英数、「.」、「_」、「-」が使用可能
        if (util.match("/^[A-Z0-9a-z@._\\-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * メールフォーマットチェックを行う
     * @param input 文字列
     * @return true:正常 false:フォーマットエラー
     */
    public static boolean isMailFormat(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z0-9.\\-_]+@([A-Za-z0-9.\\-]+)$/", input)) {
            String domainstr = util.group(1);
            if (domainstr.indexOf(".") != -1
                    && domainstr.startsWith(".") == false
                    && domainstr.endsWith(".") == false
                    && domainstr.startsWith("-") == false
                    && domainstr.endsWith("-") == false
                    && util.match("/\\.\\./", domainstr) == false
                    && util.match("/-\\./", domainstr) == false
                    && util.match("/\\.-/", domainstr) == false) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] URLフォーマットチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param input 文字列
     * @return true:正常 false:フォーマットエラー
     */
    public static boolean isUrl(String input) {
        Perl5Util util = new Perl5Util();
        return util.match("/^(http|https)(://[a-zA-Z0-9-]+[.][a-zA-Z0-9-.].*)$/", input);
    }

    /**
     * <br>[機  能] 電話・FAX番号チェック
     * <br>[解  説] 指定された文字列が電話番号もしくはFAX番号かどうかを判定します
     * <br>[備  考]
     *
     * @param input 対象となる文字列
     * @return true:電話・FAX番号である false:電話・FAX番号ではない
     */
    public static boolean isTel(String input) {
        Perl5Util util = new Perl5Util();
//        if (util.match("/^[0-9]{2,4}-[0-9]{3,4}-[0-9]{3,4}$/", input)
//            || util.match("/^[0-9]{4}-[0-9]{2}-[0-9]{4}$/", input)
//            || util.match("/^[0-9]{5}-[0-9]{1}-[0-9]{4}$/", input)
//            || util.match("/^[0-9]{6}-[0-9]{4}$/", input)) {
//
//            return true;
//        } else {
//            return false;
//        }

        if (util.match("/^\\+?[0-9]+[0-9-]+[0-9]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 数字かどうかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字である false:数字ではない
     */
    public static boolean isNumber(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>数字or「-」(ハイフン)かどうかチェックする
     * <p>空文字はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isNumberHaifun(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 数字 or 「.」かどうかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字である false:数字ではない
     */
    public static boolean isNumberDot(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9.]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>
     * [機 能] 半角英数字かどうかチェックする <br>
     * [解 説]<br>
     * [備 考]
     *
     * @param input
     *            対象となる文字列
     * @return true:半角英数字である false:半角英数字ではない
     */
    public static boolean isAlphaNum(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z0-9]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定された文字列がyyyy/mm/dd形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] mm,ddの部分は一文字でもOK
     *
     * @param date チェック大正の文字列
     * @return true:yyyy/mm/dd型である false:yyyy/mm/dd型ではない
     */
    public static boolean isSlashDateFormat(String date) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}$/", date)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextField(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        if (!validateMaxLength(errors, value, paramName, paramNameJpn, maxLength)) {
            return false;
        }

        //JIS第2水準チェック
        validateJapaneseString(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストフィールド(全角カナ)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextFieldKana(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //全角カナチェック
        validateKana(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストフィールド(数値)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param haifun ハイフン(-)を許可するか true:許可する false:許可しない
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @param req リクエスト
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextFieldNumber(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean haifun,
        boolean necessary,
        HttpServletRequest req) {

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //数値チェック
        validateNumber(errors, value, paramName, paramNameJpn, haifun, req);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextAreaField(
                                                ActionErrors errors,
                                                String value,
                                                String paramName,
                                                String paramNameJpn,
                                                int maxLength,
                                                boolean necessary) {
        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペース、改行のみチェック
        if (!validateSpaceOrBROnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        if (!validateMaxLength(errors, value, paramName, paramNameJpn, maxLength)) {
            return false;
        }

        //JIS第2水準チェック
        validateJapaneseString(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }
    /**
     * <br>[機  能] 未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNecessary(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            String msgKey = "error.input.required.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
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
     * <br>[機  能] スペース、改行のみのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOrBROnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            String msgKey = "error.input.spase.cl.only";
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
     * <br>[機  能] 最大長を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大長
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateMaxLength(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        String fieldfix = paramName + ".";

        if (value.length() > maxLength) {
            String msgKey = "error.input.length.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] JIS第２水準チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateJapaneseString(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        //利用不可能な文字を入力した場合
        if (!GSValidateUtil.isGsJapaneaseString(value)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseString(value);
            String msgKey = "error.input.njapan.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, nstr);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 全角カナチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateKana(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!isWideKana(value)) {
            //全角カナチェック
            String msgKey = "error.input.kana.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 数値チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param haifun ハイフン(-)を許可するか true:許可する false:許可しない
     * @param req リクエスト
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNumber(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                boolean haifun,
                                HttpServletRequest req) {

        String fieldfix = paramName + ".";

        if ((haifun && !isNumberHaifun(value))
        || (!haifun && !isNumber(value))) {
            // 数字以外の文字を入力した場合
            String msgKey = "error.input.number.hankaku";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn,
                                (new GsMessage()).getMessage(req, "cmn.numbers"));
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }
}