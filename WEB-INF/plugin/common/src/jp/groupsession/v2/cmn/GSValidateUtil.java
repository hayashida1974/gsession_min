package jp.groupsession.v2.cmn;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.oro.text.perl.Perl5Util;

/**
 * <br>[機  能] GroupSessionの入力チェックに関する各種機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSValidateUtil {

    /**
     * <br>[機  能] 日本語フィールドに利用可能な日本語文字列か判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return true: 利用可能, false:利用不可能
     */
    public static boolean isGsJapaneaseString(String str) {
        String nstr = ValidateUtil.getNotUniXAscii(str);
        if (nstr == null) {
            //正常
            return true;
        } else {
            //異常 利用不可能な文字が含まれる
            return false;
        }
    }

    /**
     * <br>[機  能] 日本語フィールドに利用可能な日本語文字列か判定を行う(テキストエリア用)。
     * <br>[解  説]
     * <br>[備  考] 改行を許可
     * @param str 文字列
     * @return true: 利用可能, false:利用不可能
     */
    public static boolean isGsJapaneaseStringTextArea(String str) {
        String nstr = ValidateUtil.getNotUniXAsciiCrlf(str);
        if (nstr == null) {
            //正常
            return true;
        } else {
            //異常 利用不可能な文字が含まれる
            return false;
        }
    }

    /**
     * <br>[機  能] 日本語フィールドに利用不可能な日本語文字列の先頭1文字を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return 利用不可能文字列,全て利用可能であればnullを返す
     */
    public static String getNotGsJapaneaseString(String str) {
        return ValidateUtil.getNotUniXAscii(str);
    }

    /**
     * <br>[機  能] 日本語フィールドに利用不可能な日本語文字列の先頭1文字を取得する。
     * <br>[解  説]
     * <br>[備  考] 絵文字に対して使用した場合不正な部分で切り取ってしまい、エラーになる場合がある
     * @param str 文字列
     * @return 利用不可能文字列,全て利用可能であればnullを返す
     */
    public static String getNotGsJapaneaseStringTextArea(String str) {
        return ValidateUtil.getNotUniXAsciiCrlf(str);
    }

    /**
     * <br>[機  能] 全角カタカナか判定を行う。
     * <br>[解  説]
     * <br>[備  考] 全角、半角スペースは認める。
     * @param kana 判定対象の文字列
     * @return true:全角カタカナ, false:全角カタカナではない
     */
    public static boolean isGsWideKana(String kana) {
        return ValidateUtil.isWideKanaSpOK(kana);
    }

    /**
     * <br>[機  能] IDのフォーマットチェックを行う。
     * <br>[解  説] 半角英数、「.」、「_」、「-」、「@」が使用可能
     * <br>[備  考]
     * @param input 文字列
     * @return true: 正常なユーザＩＤフォーマット, false: ユーザＩＤのフォーマットではない
     */
    public static boolean isOtheridFormat(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Z0-9a-z@._\\-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * <br>[機  能] ユーザIDのフォーマットチェックを行う。
     * <br>[解  説] 半角英数、「@ . _ - ! # $ % & ' * + / = ? ^ ` { | } ~」が使用可能

     * <br>[備  考]
     * @param input 文字列
     * @return true: 正常なユーザＩＤフォーマット, false: ユーザＩＤのフォーマットではない
     */
    public static boolean isUseridFormat(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Z0-9a-z@._\\-!#$%&'*+/=?^`{|}~]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] Asciiコード範囲内 or 数値か判定を行う。
     * <br>[解  説]
     * <br>[備  考]
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
                if (!GSValidateUtil.isNumber(String.valueOf(c[i]))) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] パスワードのフォーマットチェックを行う。
     * <br>[解  説] 半角英数、「-」「!」「#」「$」「%」「&」「(」「)」「,」「.」「/」「:」「;」「<」「=」「>」「@」「\」「|」「_」「*」が使用可能
     * <br>[備  考]
     * @param input 文字列
     * @return true: 正常なパスワードフォーマット, false: パスワードのフォーマットではない
     */
    public static boolean isPasswordFormat(String input) {
        Perl5Util util = new Perl5Util();
        return util.match("/^[A-Z0-9a-z\\-!#\\$%&\\(\\),\\./:;<=>@\\\\|_\\*]+$/", input);
    }

    /**
     * <br>[機  能] パスワードに対して、英数字記号混在のフォーマットチェックを行う。
     * <br>[解  説] 半角英数、「-」「!」「#」「$」「%」「&」「(」「)」「,」「.」「/」「:」「;」「<」「=」「>」「@」「\」「|」「_」「*」が使用可能
     * <br>[備  考]
     * @param coeKbn 英数混在区分 0 = なし, 1 = 英数字必須, 2 = 英数字＋記号必須
     * @param input 文字列
     * @return true: 正常なパスワードフォーマット, false: パスワードのフォーマットではない
     */
    public static boolean isPasswordCombinationFormat(int coeKbn, String input) {
        Perl5Util util = new Perl5Util();
        boolean ret = false;

        if (coeKbn == GSConstMain.PWC_COEKBN_ON_EN) {
            //英数混在が必須
            ret = util.match("/[0-9]/", input)
                    && util.match("/[A-Za-z]/", input);

        } else if (coeKbn == GSConstMain.PWC_COEKBN_ON_ENS) {
            //英数記号混在が必須
            ret = util.match("/[0-9]/", input)
                    && util.match("/[A-Za-z]/", input)
                    && util.match("/[\\-!#\\$%&\\(\\),\\./:;<=>@\\\\|_\\*]/", input);

        } else if (coeKbn == GSConstMain.PWC_COEKBN_OFF) {
            //制限なし
            ret = true;
        }

        return ret;
    }

    /**
     * <br>[機  能] パスワードに対して、大文字小文字混在のフォーマットチェックを行う。
     * <br>[解  説] 半角英数、「-」「!」「#」「$」「%」「&」「(」「)」「,」「.」「/」「:」「;」「<」「=」「>」「@」「\」「|」「_」「*」が使用可能
     * <br>[備  考]
     * @param uppercaseKbn 大文字小文字混在区分 0 = なし, 1 = 必須
     * @param input 文字列
     * @return true: 正常なパスワードフォーマット, false: パスワードのフォーマットではない
     */
    public static boolean isPasswordUppercaseFormat(
        int uppercaseKbn,
        String input) {

        Perl5Util util = new Perl5Util();
        boolean ret = false;
        if (uppercaseKbn == GSConstMain.PWC_UPPERCASE_ON) {
            //必須
            ret = util.match("/[A-Z]/", input)
                    && util.match("/[a-z]/", input);
        } else if (uppercaseKbn == GSConstMain.PWC_UPPERCASE_OFF) {
            //制限なし
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] メールフォーマットチェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param input 文字列
     * @return true:正常 false:フォーマットエラー
     */
    public static boolean isMailFormat(String input) {
        return GSValidateUtil.isMailFormat(input, null);
    }

    /**
     * <br>[機  能] メールフォーマットチェックを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param input 文字列
     * @param tldList TLD制限許可リスト
     * @return true:正常 false:フォーマットエラー
     */
    public static boolean isMailFormat(String input, String[] tldList) {
        return ValidateUtil.isMailAddress(input, tldList);
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
     * <br>[機 能] 半角英数字かどうかチェックする。
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param input 対象となる文字列
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
}