package jp.co.sjts.util;

import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;

/**
 * <br>[機  能] 各種パラメータチェック処理に用いるユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class ValidateUtil {
    /** */
    private static Log log__ = LogFactory.getLog(ValidateUtil.class);

    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_NOTKANJI_S__ = (char) 0x8140;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_NOTKANJI_E__ = (char) 0x84BE;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI1_S__ = (char) 0x889F;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI1_E__ = (char) 0x9872;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI2_S__ = (char) 0x989F;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI2_E__ = (char) 0x9FFC;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI3_S__ = (char) 0xE040;
    /** JISX0208チェック用Charactor Code */
    private static final char C_JIS_KANJI3_E__ = (char) 0xEAA4;
    /** JISX0208チェック用Charactor Code */
    private static final byte BYTE_0X0F__ = 0x0f;
    /** JISX0208チェック用Charactor Code */
    private static final int RADIX_HEX__ = 16;

    /** NEC特殊文字チェック用Charactor Code */
    private static final char C_NEC_S__ = (char) 0x8740;
    /** NEC特殊文字用Charactor Code */
    private static final char C_NEC_E__ = (char) 0x879E;
    /** NEC選定IBM拡張文字用Charactor Code */
    private static final char C_NEC_SIBM_S__ = (char) 0xED40;
    /** NEC選定IBM拡張文字用Charactor Code */
    private static final char C_NEC_SIBM_E__ = (char) 0xEFFC;
    /** IBM拡張文字用Charactor Code */
    private static final char C_IBM_S__ = (char) 0xFA40;
    /** IBM拡張文字用Charactor Code */
    private static final char C_IBM_E__ = (char) 0xFC4B;

    /**
     * <br>[機  能] 全てのメソッドがstaticアクセス可能なため、インスタンス化する必要がありません。
     * <br>[解  説]
     * <br>[備  考]
     */
    private ValidateUtil() {
    }

    /**
     * <br>[機  能] 指定された文字列がyyyymmdd形式かをチェックする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yyyymmdd チェック対象の文字列
     * @return true:yyyymmdd型である false:yyyymmdd型ではない
     */
    public static boolean isYYYYMMDD(String yyyymmdd) {
        if ((yyyymmdd == null) || (yyyymmdd.length() != 8)) {
            return false;
        }
        return isNumber(yyyymmdd);
    }

    /**
     * <br>[機  能] 指定された文字列がWyy/mm/dd形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] mm,ddの部分は一文字でもOK
     *
     * @param wareki チェック対象の文字列
     * @return true:Wyy/mm/dd型である false:Wyy/mm/dd型ではない
     */
    public static boolean isWarekiFormat(String wareki) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z]{1}[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2}$/", wareki)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定された文字列の形式がyy.mm.dd型かチェックする。
     * <br>[解  説]
     * <br>[備  考] mm,ddの部分はは一文字でもOK
     *
     * @param date チェック対象の文字列
     * @return true:yy.mm.dd型である false:yy.mm.dd型ではない
     */
    public static boolean isDateFormat(String date) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{1,2}$/", date)) {
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
     * <br>[機  能] 数字or「-」(ハイフン)かどうかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
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
     * <br>[機  能] 半角英数字or「-」(ハイフン)かどうかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isAlpOrNumberOrHaifun(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z0-9-]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * <br>[機  能] 半角英数字or「_」(アンダーバー)かどうかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isAlpOrNumberOrUnderscore(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z0-9_]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 数値とドットのみかチェックする
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isNumberDot(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9\\.]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 整数部14桁,小数部2桁以内の数値かどうか判定する
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isNumber14Dot2(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{1,14}\\.{0,1}[0-9]{0,2}$/", input)) {
            //16or15桁全て数値は除く,最後がドット(01.)等もチェック
            if (util.match("/^[0-9]{15,16}$/", input) || util.match("/\\.$/", input)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 整数部3桁,小数部2桁以内の数値かどうか判定する
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isNumber3Dot2(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{1,3}\\.{0,1}[0-9]{0,2}$/", input)) {
            //16or15桁全て数値は除く
            if (util.match("/^[0-9]{4,5}$/", input) || util.match("/\\.$/", input)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 整数部integer桁,小数部decimal桁以内の数値かどうか判定する
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     * <br>         また、小数部が存在しない場合は整数部のみのチェックを行う
     *
     * @param input 対象となる文字列
     * @param integer 整数部桁数
     * @param decimal 小数部桁数
     * @return true:数字-である false:数字-ではない
     */
    public static boolean isNumberDot(String input, int integer, int decimal) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{1," + integer + "}\\.{0,1}[0-9]{0," + decimal + "}$/", input)) {
            int rangeLen = integer + 1;
            //整数のみの構成、かつ整数部に割り当てられる桁数より長い
            if (util.match("/^[0-9]{" + rangeLen + ",}$/", input)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] ３桁の数字フォーマットチェック
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:郵便番号である false:郵便番号ではない
     */
    public static boolean isNumber3(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{3}$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] ４桁の数字フォーマットチェック
     * <br>[解  説]
     * <br>[備  考] 空文字が指定された場合はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:郵便番号である false:郵便番号ではない
     */
    public static boolean isNumber4(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{4}$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 半角カナチェック
     * <br>[解  説] <p>指定された文字が半角カナのみかどうかを判定します。
     *              空文字が指定された場合はfalseを返します。</p>
     * <br>[備  考] <p>unicode上のチェックであるため半角カナとコードがかぶる文字も引っかかるかも
     *              しれません。</p>
     *
     * @param input 対象となる文字列
     * @return true:半角カナである false:半角カナではない
     */
    public static boolean isHalfKana(String input) {
        if (input != null) {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char target = chars[i];
                if (target < 0xff61 || target > 0xff9f) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 一文字以上の半角カナチェック。
     * <br>[解  説] 半角カナが一文字でも存在する
     * <br>[備  考] 空文字はfalseを返します。
     * <br>[      ] <p>unicode上のチェックであるため半角カナとコードがかぶる文字も
     *              引っかかるかもしれません。</p>
     * @param input 対象となる文字列
     * @return true:半角カナを含む false:半角カナを含まない
     */
    public static boolean isHalfKanaOnlyOne(String input) {
        return (input.toCharArray()[0] >= 0xff61 || input.toCharArray()[0] <= 0xff9f);
    }

    /**
     * <br>[機  能] 入力された文字がスペースのみか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param value 判定対象の文字列
     * @return true:スペースのみ, false:スペースのみではない
     */
    public static boolean isSpace(String value) {
        char[] target = value.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('　' != c && ' ' != c) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 入力された文字の頭にスペースか入っているか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param kana 判定対象の文字列
     * @return true:文字の頭にスペースか入っている,
     *          false:文字の頭にスペースか入っていない
     */
    public static boolean isSpaceStart(String kana) {
        char[] target = kana.toCharArray();
        char c = target[0];
        if ('　' == c || ' ' == c) {
            //文字の頭にスペース
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 入力された文字がスペース、改行のみか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param kana 判定対象の文字列
     * @return true:スペース、改行のみ, false:スペース、改行のみではない
     */
    public static boolean isSpaceOrKaigyou(String kana) {
        char[] target = kana.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('　' != c && ' ' != c && '\r' != c && '\n' != c) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 入力された文字にタブ文字が含まれているか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param value 判定対象の文字列
     * @return true:タブ文字が含まれる, false:タブ文字が含まれない
     */
    public static boolean isTab(String value) {
        char[] target = value.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('\t' == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 全角カタカナか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param kana 判定対象の文字列
     * @return true:全角カタカナ, false:全角カタカナではない
     */
    public static boolean isWideKana(String kana) {
        char[] target = kana.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if (
                java.lang.Character.UnicodeBlock.KATAKANA
                ==
                java.lang.Character.UnicodeBlock.of(c)
                ) {
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 全角カタカナか判定を行う
     * <br>[解  説] <p>指定された文字列が全角カタカナのみで
     *              構成されているかを確認します。
     *              この時、全角、半角スペースは判定から除外します</p>
     * <br>[備  考]
     * @param kana 判定対象の文字列
     * @return true:全角カタカナ, false:全角カタカナではない
     */
    public static boolean isWideKanaSpOK(String kana) {
        char[] target = kana.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if (java.lang.Character.UnicodeBlock.KATAKANA == java.lang.Character.UnicodeBlock
                    .of(c)) {
            } else {
                //全角スペース,半角スペースは許可
                if (c == '　' || c == ' ') {
                    //ただし1文字目はのスペースはfalseを返す
                    if (i == 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
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
     * <br>[機  能] 全角数字とハイフンかどうかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param input 対象となる文字列
     * @return true:全角英数字ハイフンである false:全角英数字とハイフンではない
     */
    public static boolean isNumAndHf(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/[０-９‐]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 半角英数字かどうかチェックする(文字列全てが対象)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param input 対象となる文字列
     * @return true:全てが半角英数字 false:全てが半角英数字ではない
     */
    public static boolean isAllAlphaNum(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/[A-Za-z0-9]/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 英字かどうかをチェックします
     * <br>[解  説]
     * <br>[備  考] 空文字はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:英字である false:英字ではない
     */
    public static boolean isAlpha(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[A-Za-z]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 英字(小文字)かどうかをチェックします
     * <br>[解  説]
     * <br>[備  考] 空文字はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:英字である false:英字ではない
     */
    public static boolean isSmallAlpha(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[a-z]+$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 先頭に「kr.」があるかどうかをチェックします
     * <br>[解  説]
     * <br>[備  考] 空文字はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:先頭に「kr.」がある false:先頭に「kr.」がない
     */
    public static boolean isKrDot(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^kr\\./", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 郵便番号フォーマットチェック
     * <br>[解  説] 指定された文字列が郵便番号かどうかを判定します
     * <br>[備  考] 空文字はfalseを返します。
     *
     * @param input 対象となる文字列
     * @return true:郵便番号である false:郵便番号ではない
     */
    public static boolean isZip(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{3}-[0-9]{4}$/", input)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 電話・FAX番号チェック1   フォーマット: 00-0000-0000
     * <br>[解  説] 指定された文字列が電話番号もしくはFAX番号かどうかを判定します
     * <br>[備  考]
     *
     * @param input 対象となる文字列
     * @return true:電話・FAX番号である false:電話・FAX番号ではない
     */
    public static boolean isTel(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{2,4}-[0-9]{3,4}-[0-9]{3,4}$/", input)
            || util.match("/^[0-9]{4}-[0-9]{2}-[0-9]{4}$/", input)
            || util.match("/^[0-9]{5}-[0-9]{1}-[0-9]{4}$/", input)
            || util.match("/^[0-9]{6}-[0-9]{4}$/", input)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 電話・FAX番号チェック2   フォーマット: (00)-0000-0000
     * <br>[解  説] 指定された文字列が電話番号もしくはFAX番号かどうかを判定します
     * <br>[備  考]
     *
     * @param input 対象となる文字列
     * @return true:電話・FAX番号である false:電話・FAX番号ではない
     */
    public static boolean isTel2(String input) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{2,4}-[0-9]{3,4}-[0-9]{3,4}$/", input)
         || util.match("/^\\d{2,4}\\(?\\d{3,4}\\)?\\d{3,4}$/", input)
         || util.match("/^[0-9]{4}-[0-9]{2}-[0-9]{4}$/", input)
         || util.match("/^\\d{4}\\(?\\d{2}\\)?\\d{4}$/", input)
         || util.match("/^[0-9]{5}-[0-9]{1}-[0-9]{4}$/", input)
         || util.match("/^\\d{5}\\(?\\d{1}\\)?\\d{4}$/", input)
         || util.match("/^[0-9]{6}-[0-9]{4}$/", input)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] メールアドレスチェック
     * <br>[解  説]
     * <br>[備  考] 日本語ドメインには対応していません。
     * @param str 対象となる文字列
     * @param tldList TLD制限許可リスト
     * @return true:メールアドレスである false:メールアドレスではない
     */
    public static boolean isMailAddress(String str, String[] tldList) {
        EmailValidatorJp validator = EmailValidatorJp.getInstance();
        validator.setTldList(tldList);
        return validator.isValid(str);
    }

//    /**
//     * <br>[機  能] メールアドレスチェック
//     * <br>[解  説]
//     * <br>[備  考] 日本語ドメインには対応していません。
//     * @param str 対象となる文字列
//     * @return true:メールアドレスである false:メールアドレスではない
//     */
//    public static boolean isMailAddress(String str) {
//        Perl5Util util = new Perl5Util();
//        //if (util.match("/^[A-Za-z0-9.\\-_]+@([A-Za-z0-9.\\-]+)$/", str)) {
//        if (util.match("/^[A-Za-z0-9.\\-_]+@([A-Za-z0-9.\\-]+)$/", str)) {
//            String domainstr = util.group(1);
//            if (domainstr.indexOf(".") != -1
//                    && domainstr.startsWith(".") == false
//                    && domainstr.endsWith(".") == false
//                    && domainstr.startsWith("-") == false
//                    && domainstr.endsWith("-") == false
//                    && util.match("/\\.\\./", domainstr) == false
//                    && util.match("/-\\./", domainstr) == false
//                    && util.match("/\\.-/", domainstr) == false) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }

    /**
     * <br>[機  能] メールアドレスのドメインチェック
     * <br>[解  説]
     * <br>[備  考] 日本語ドメインには対応していません。
     *
     * @param target 対象となる文字列
     * @param domainAry ドメインリスト
     * @return true:指定ドメインである false:指定ドメインではない
     */
    public static boolean isDomainAddress(String target, String[] domainAry) {
        Perl5Util util = new Perl5Util();
        if (domainAry != null) {
            String lowTarget = target.toLowerCase();
            for (int i = 0; i < domainAry.length; i++) {
                String lowDomain = domainAry[i].toLowerCase();
                String pattern = "/^[A-Za-z0-9.\\-_]+@([A-Za-z0-9.\\-]*)(" + lowDomain + ")$/";
                if (util.match(pattern, lowTarget)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 文字列の日付(YYYYMMDD)が正しい日付(存在日)であるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param yyyyMMdd 対象年月日[YYYYMMDD]の文字列
     * @return true:実在する日付, false:実在しない日付
     */
    public static boolean isExistDateYyyyMMdd(String yyyyMMdd) {
        String yyyy = null;
        String mM = null;
        String dd = null;
        SimpleDateFormat format;
        Calendar cal;

        try {
            //桁チェック
            if (yyyyMMdd.length() != 8) {
                //桁数が異なる場合はNGを返す
                return false;
            }

            //数値チェック
            //数値以外の場合はExceptonで処理

            yyyy = (yyyyMMdd.substring(0, 4));
            mM = (yyyyMMdd.substring(4, 6));
            dd = (yyyyMMdd.substring(6, 8));

            //正しい年月日表記でない場合(ex.2000年13月32日)はNGを返す。
            //正常日付チェック
            format = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN);
            cal = Calendar.getInstance();
            cal.setTime(
                format.parse(yyyyMMdd, new ParsePosition(0)));
            if (cal.get(Calendar.YEAR) != Integer.parseInt(yyyy)) {
                //年が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.MONTH) + 1
                != Integer.parseInt(mM)) {
                //月が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.DATE) != Integer.parseInt(dd)) {
                //日が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }

            //すべてのチェックにかからなかった場合はOKを返す。
            return true;
        } catch (Exception e) {
            //数値チェックでExceptionの場合NGを返す
            //ここでのエラーは例外ではない
            return false;
        }
    }

    /**
     * <br>[機  能] 文字列の日付(YYYYMMDD)が正しい日付(存在日)であるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param yyyyMMddHHmmss 対象年月日[yyyyMMddHHmmss]の文字列
     * @return true:実在する日付, false:実在しない日付
     */
    public static boolean isExistDateYyyyMMddHHmmss(String yyyyMMddHHmmss) {
        String yyyy = null;
        String mM = null;
        String dd = null;
        String hh = null;
        String mm = null;
        String ss = null;
        SimpleDateFormat format;
        Calendar cal;

        try {
            //桁チェック
            if (yyyyMMddHHmmss.length() != 14) {
                //桁数が異なる場合はNGを返す
                return false;
            }

            //数値チェック
            //数値以外の場合はExceptonで処理

            yyyy = (yyyyMMddHHmmss.substring(0, 4));
            mM = (yyyyMMddHHmmss.substring(4, 6));
            dd = (yyyyMMddHHmmss.substring(6, 8));
            hh = (yyyyMMddHHmmss.substring(8, 10));
            mm = (yyyyMMddHHmmss.substring(10, 12));
            ss = (yyyyMMddHHmmss.substring(12, 14));

            //正しい年月日表記でない場合はNGを返す。
            //正常日付チェック
            format = new SimpleDateFormat("yyyyMMddHHmmss");
            cal = Calendar.getInstance();
            cal.setTime(
                format.parse(yyyyMMddHHmmss, new ParsePosition(0)));
            if (cal.get(Calendar.YEAR) != Integer.parseInt(yyyy)) {
                //年が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.MONTH) + 1
                != Integer.parseInt(mM)) {
                //月が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.DATE) != Integer.parseInt(dd)) {
                //日が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(hh)) {
                //時が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.MINUTE) != Integer.parseInt(mm)) {
                //分が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }
            if (cal.get(Calendar.SECOND) != Integer.parseInt(ss)) {
                //秒が異なる場合はNGを返す
                format = null;
                cal = null;
                return false;
            }

            //すべてのチェックにかからなかった場合はOKを返す。
            return true;
        } catch (Exception e) {
            //数値チェックでExceptionの場合NGを返す
            //ここでのエラーは例外ではない
            return false;
        }
    }

    /**
     * <br>[機  能] JISX0208チェック
     * <br>[解  説] 文字列がすべてJISX0208であるかチェックするメソッド
     * <br>         Cp943CエンコーディングでSJISコードにエンコーディングした場合に
     * <br>         文字コードが以下の範囲に入る文字。
     * <br>         ・0x8140から0x84BE (非漢字)
     * <br>         ・0x889Fから0x9872 (第１水準漢字)
     * <br>         ・0x989Fから0x9FFC (第２水準漢字)
     * <br>         ・0xE040から0xEAA4 (第２水準漢字)
     * <br>[備  考]
     * @param item 対象文字列
     * @return 判定結果
     */
    public static boolean isFullWidthJISX0208(String item) {
        if (isEmpty(item)) {
            return false;
        }
        char[] c = item.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (!isFullWidthJISX0208(c[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 引数で指定した文字列中にあるASCII、JIS第二水準の以外の文字で
     *              初めに出現した1文字取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param item チェック文字列
     * @return errorString エラー文字 (エラー無しの場合はnull)
     */
    public static String getNotJISXAscii(String item) {

        String errorString = null;
        if (isEmpty(item)) {
            return errorString;
        }
        char[] c = item.toCharArray();
        for (int i = 0; i < c.length; i++) {
            //ASCIIコード判定
            if (!StringUtil.isAscii(c[i])) {
                //JIS基本漢字判定
                if (!isFullWidthJISX0208(c[i])) {
                    errorString = String.valueOf(c[i]);
                    return errorString;
                }
            }
        }
        return errorString;
    }

    /**
     * <br>[機  能] 引数で指定した文字列中にあるASCII、JIS第二水準、改行の以外の文字で
     *              初めに出現した1文字取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param item チェック文字列
     * @return errorString エラー文字 (エラー無しの場合はnull)
     */
    public static String getNotJISXAsciiCrlf(String item) {

        String errorString = null;
        if (isEmpty(item)) {
            return errorString;
        }
        char[] c = item.toCharArray();
        for (int i = 0; i < c.length; i++) {
            //ASCIIコード判定
            if (!StringUtil.isAscii(c[i])) {
                //JIS基本漢字判定
                if (!isFullWidthJISX0208(c[i])) {
                    //改行コード判定
                    if (c[i] != '\r' && c[i] != '\n') {
                        errorString = String.valueOf(c[i]);
                        return errorString;
                    }
                }
            }
        }
        return errorString;
    }

    /**
     * <br>[機  能] 引数で指定した文字列中にあるASCII、UTF-8以外の文字で
     *              初めに出現した1文字取得する
     * <br>[解  説]
     * <br>[備  考] 改行はASCII、UTF-8以外の文字として扱う
     * @param item チェック文字列
     * @return errorString エラー文字 (エラー無しの場合はnull)
     */
    public static String getNotUniXAscii(String item) {
        String errorString = null;
        if (isEmpty(item)) {
            return errorString;
        }

        char[] c = item.toCharArray();
        for (int i = 0; i < c.length; i++) {
            errorString = __getNotUniXAscii(c, i);
            if (errorString != null) {
                break;
            } else if (c[i] == '\r' || c[i] == '\n') {
                errorString = String.valueOf(c[i]);
                break;
            }
        }
        return errorString;
    }

    /**
     * <br>[機  能] 引数で指定した文字列中にあるASCII、UTF-8、改行以外の文字で
     *              初めに出現した1文字取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param item チェック文字列
     * @return errorString エラー文字 (エラー無しの場合はnull)
     */
    public static String getNotUniXAsciiCrlf(String item) {

        String errorString = null;
        if (isEmpty(item)) {
            return errorString;
        }

        char[] c = item.toCharArray();
        for (int i = 0; i < c.length; i++) {
            errorString = __getNotUniXAscii(c, i);
            if (errorString != null) {
                break;
            }
        }
        return errorString;
    }

    /**
     * <br>[機  能] 指定した位置のcharacterがASCII、UTF-8以外かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param charArray Character
     * @param index 位置
     * @return ASCII、UTF-8の場合はnull、それ以外の場合は
     *          文字列を返す
     */
    private static String __getNotUniXAscii(char[] charArray, int index) {
        String errorString = null;

        //ASCIIコード判定
        if (!StringUtil.isAscii(charArray[index])) {
            //UTF-8判定
            if (Character.isHighSurrogate(charArray[index])) {
                if (charArray.length < index + 1) {
                    //下位サロゲートがない不正な文字はスペースを返す
                    return " ";
                }

                //サロゲートペアを返す
                char[] cs = new char[] {charArray[index], charArray[index + 1]};
                errorString = new String(cs);
                errorString += String.valueOf(charArray[index + 1]);
            } else if (Character.isLowSurrogate(charArray[index])) {
                //下位サロゲートは無視
            } else {
                if (!StringUtil.isUnicode(charArray[index])) {
                    errorString = String.valueOf(charArray[index]);
                }
            }
        }

        return errorString;
    }

    /**
     * <br>[機  能] 渡された文字列がnullか空文字の場合にtrueを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param value - 判定文字列
     * @return boolean
     */
    public static boolean isEmpty(String value) {
        return null == value || 0 == value.length();
    }

    /**
     * <br>[機  能] JISX0208チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param c 対象文字
     * @return 判定結果
     */
    public static boolean isFullWidthJISX0208(char c) {
        try {
            byte[] bytes = String.valueOf(c).getBytes(Encoding.WINDOWS_31J);
            if (2 != bytes.length) {
                return false;
            }
            String hexString = toHexString(bytes);
            char target = (char) Integer.parseInt(hexString, 16);
            /*
            ・0x8140から0x84BE (非漢字)
            ・0x889Fから0x9872 (第１水準漢字)
            ・0x989Fから0x9FFC (第２水準漢字)
            ・0xE040から0xEAA4 (第２水準漢字)
            */
            if (isMatch(target, C_JIS_NOTKANJI_S__, C_JIS_NOTKANJI_E__)
                || isMatch(target, C_JIS_KANJI1_S__, C_JIS_KANJI1_E__)
                || isMatch(target, C_JIS_KANJI2_S__, C_JIS_KANJI2_E__)
                || isMatch(target, C_JIS_KANJI3_S__, C_JIS_KANJI3_E__)) {
                return true;
            }
        } catch (NumberFormatException e) {
            log__.error("JISX0208チェックに失敗",  e);
        } catch (UnsupportedEncodingException e) {
            log__.error("JISX0208チェックに失敗",  e);
        }

        return false;
    }
    /**
     * <br>[機  能] JISX0208＋NEC特殊文字＋NEC選定IBM拡張文字＋IBM拡張文字の範囲内チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param c 対象文字
     * @return 判定結果
     */
    public static boolean isFullWidthJISX0208PlusNecIbm(char c) {
        try {
            byte[] bytes = String.valueOf(c).getBytes(Encoding.WINDOWS_31J);
            if (2 != bytes.length) {
                return false;
            }
            String hexString = toHexString(bytes);
            char target = (char) Integer.parseInt(hexString, 16);
            /*
            ・0x8140から0x84BE (非漢字)
            ・0x889Fから0x9872 (第１水準漢字)
            ・0x989Fから0x9FFC (第２水準漢字)
            ・0xE040から0xEAA4 (第２水準漢字)
            ・0x8740から0x879E (NEC特殊文字)
            ・0xED40から0xEFFC (NEC選定IBM拡張文字)
            ・0xED40から0xEFFC (IBM拡張文字)
            */
            if (isMatch(target, C_JIS_NOTKANJI_S__, C_JIS_NOTKANJI_E__)
                || isMatch(target, C_JIS_KANJI1_S__, C_JIS_KANJI1_E__)
                || isMatch(target, C_JIS_KANJI2_S__, C_JIS_KANJI2_E__)
                || isMatch(target, C_JIS_KANJI3_S__, C_JIS_KANJI3_E__)
                || isMatch(target, C_NEC_S__, C_NEC_E__)
                || isMatch(target, C_NEC_SIBM_S__, C_NEC_SIBM_E__)
                || isMatch(target, C_IBM_S__, C_IBM_E__)) {
                return true;
            }
        } catch (NumberFormatException e) {
            log__.error("JISX0208＋NEC特殊文字＋NEC選定IBM拡張文字＋IBM拡張文字の範囲内チェックに失敗", e);
        } catch (UnsupportedEncodingException e) {
            log__.error("JISX0208＋NEC特殊文字＋NEC選定IBM拡張文字＋IBM拡張文字の範囲内チェックに失敗", e);
        }

        return false;
    }

    /**
     * <br>[機  能] 16進符号なし整数としてバイト配列引数のストリング表現を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param b バイト配列
     * @return 16進ストリング表現
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(toHexString(b[i]));
        }
        return new String(sb);
    }
    /**
     * <br>[機  能] 16進符号なし整数としてバイト引数のストリング表現を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param b  byte
     * @return 16進ストリング表現
     */
    public static String toHexString(byte b) {
        //return Integer.toHexString(b);
        return toUnsignedString((int) b, 2);
    }

    /**
     * <br>[機  能] 16進符号なし整数としてint引数のストリング表現を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param i - 変換対象変数
     * @param j - 変換対象桁
     * @return ストリング表現
     */
    private static String toUnsignedString(int i, int j) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(Character.forDigit(i & BYTE_0X0F__, RADIX_HEX__));
            i >>>= 4;
        } while (0 < --j);
        return new String(sb.reverse());
    }
    /**
     * <br>[機  能] 渡された文字が渡された文字コードの範囲にすべて含まれているか判定して返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param c - 判定文字
     * @param start - 判定する範囲の始まりの文字コード(これを含む)
     * @param end - 判定する範囲の終端の文字コード(これを含む)
     * @return boolean
     */
    public static boolean isMatch(char c, char start, char end) {
        return (c >= start && c <= end);
    }

    /**
     * <br>[機  能] 指定された文字列がyyyy/mm/dd形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] mm,ddの部分は一文字でもOK
     *
     * @param date チェック対象の文字列
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
     * <br>[機  能] 指定された文字列がhh:mm形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] hh,mmの部分は一文字でもOK
     *
     * @param date チェック対象の文字列
     * @return true:hh:mm型である false:hh:mm型ではない
     */
    public static boolean isTimeFormat(String date) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{1,2}:[0-9]{1,2}$/", date)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定された文字列がyyyy/mm/dd hh:mm形式かチェックする。
     * <br>[解  説]
     * <br>[備  考] mm,ddの部分は一文字でもOK
     *
     * @param date チェック対象の文字列
     * @return true:yyyy/mm/dd hh:mm型である false:yyyy/mm/dd hh:mm型ではない
     */
    public static boolean isSlashDateTimeFormat(String date) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}. [0-9]{1,2}:[0-9]{1,2}$/", date)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定されたパスがルートパス以下にあるかチェックする。
     * <br>[解  説]
     * <br>[備  考] 不正な階層へのアクセスを制限するのに使用(ディレクトリトラバーサル対策)
     *
     * @param rootPath  ルートパス
     * @param checkPath チェック対象のパス
     * @return true:指定パスがルートパス以下に存在する false:指定パスがルートパス以下に存在しない
     */
    public static boolean isRootInPath(String rootPath, String checkPath) {
        // 相対パス → 絶対パスへ変換
        String rootAbsPath  = StringUtil.getAbsolutePath(rootPath);
        String checkAbsPath = StringUtil.getAbsolutePath(checkPath);

        if (!StringUtil.isNullZeroString(rootAbsPath)
         && !StringUtil.isNullZeroString(checkAbsPath)) {
            // チェックパスの先頭がルートパスと一致するか判定
            //  先頭一致する:   チェックパスがルートパス以下に存在する
            //  先頭一致しない: チェックパスがルートパス以下に存在しない
            return checkAbsPath.startsWith(rootAbsPath);
        }
        return false;
    }
    
    /**
     * <br>[機  能] 指定した文字列がHTTP URLフォーマットと一致するかをチェックする
     * <br>[解  説] 
     * <br>[備  考]
     *
     * @param url URL文字列
     * @return true:フォーマットと一致する false:フォーマットではない
     */
    public static boolean isHttpUrlFormat(String url) {
        
        boolean result = false;
        
        if (StringUtil.isNullZeroString(url)) {
            return true;
        }
        
        //スキーマチェック("http,"https"以外は許可しない)
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }
        
        // [://]以降の文字列を取得
        int pos = url.indexOf("://");
        String host = url.substring(pos + 3);
        
        // [://]以降の文字存在チェック
        result = (!StringUtil.isNullZeroString(host) && !ValidateUtil.isSpace(host));
        
        return result;
    }
}
