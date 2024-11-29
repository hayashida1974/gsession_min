package jp.co.sjts.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 文字に関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class StringUtil {
    /** Unicode TAB文字 */
    public static final char LATIN_TAB = (char) 0x0009;

    /** 半角ラテンのUnicodeの最小値 スペースからみなす場合*/
    public static final char LATIN_HALF_SP_MIN = (char) 0x0020;
    /** 半角ラテンのUnicodeの最小値 ! */
    public static final char LATIN_HALF_MIN = (char) 0x0021;

    /** 半角ラテンのUnicodeの最大値 */
    public static final char LATIN_HALF_MAX = (char) 0x007e;

    /** 全角ラテンのUnicodeの最小値 */
    public static final char LATIN_FULL_MIN = (char) 0xff01;

    /** 全角ラテンのUnicodeの最大値 */
    public static final char LATIN_FULL_MAX = (char) 0xff5e;

    /** 全角ひらがなのUnicodeの最小値 */
    public static final char HIRAGANA_FULL_MIN = (char) 0x3041;

    /** 全角ひらがなのUnicodeの最大値 */
    public static final char HIRAGANA_FULL_MAX = (char) 0x3094;

    /** URLパターン文字列 */
    public static final String URL_PATTERN
        = "((\\w+)://){1}[\\w\\.\\-/:\\#\\!\\?\\=\\&\\;\\%\\~\\+\\$\\,\\*\\'\\(\\)\\@]+";

    /** ハイパーリンク 同ウィンドウ表示 */
    public static final int SAME_WIN = 0;
    /** ハイパーリンク 別ウィンドウ表示 */
    public static final int OTHER_WIN = 1;

    /** ://の文字数  */
    public static final int CORON_LEN = 3;

    /**
     * [機 能] 半角ラテン文字を全角ラテン文字に変換する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param pSource 変換する半角ラテン基本文字列
     * @return 変換後の全角ラテン基本文字列
     */
    public static String latinHalf2Full(String pSource) {
        //戻り値
        StringBuilder myReturn = new StringBuilder();

        // パラメーターの文字列を先頭から1文字ずつ調べる
        for (int i = 0; i < pSource.length(); i++) {
            //文字列から1文字取り出す
            Character myChar = new Character(pSource.substring(i, i + 1)
                    .charAt(0));
            // Unicode半角ラテン文字のコード範囲か調べる
            if (myChar.compareTo(new Character(LATIN_HALF_MIN)) >= 0
                    && myChar.compareTo(new Character(LATIN_HALF_MAX)) <= 0) {
                // 変換文字に 0xfee0 を加算して全角文字に変換する
                Character myNewChar = new Character(
                        (char) (myChar.charValue() + (new Character(
                                (char) 0xfee0)).charValue()));

                // 文字列としてセットする
                myReturn.append(myNewChar.toString());
                // Unicode半角ラテン以外なら変換しない
            } else {
                myReturn.append(pSource.substring(i, i + 1));
            }
        }

        return myReturn.toString();
     }

    /**
     * [機 能] ASCIIコードの範囲内か判定する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param c 判定対象の文字列
     *           変換する半角ラテン基本文字列
     * @return true:Asciiの範囲内,false:Asciiの範囲外
     */
    public static boolean isAscii(char c) {
        boolean ret = false;
        //文字列から1文字取り出す
        Character myChar = new Character(c);

        // Unicode半角ラテン文字のコード範囲か調べる(SPから~までの範囲内チェック)
        if (myChar.compareTo(new Character(LATIN_HALF_SP_MIN)) >= 0
            && myChar.compareTo(new Character(LATIN_HALF_MAX)) <= 0) {
            ret = true;
        }

        //タブ文字の場合
        if (myChar.compareTo(new Character(LATIN_TAB)) == 0) {
            return true;
        }

        return ret;
    }

    /**
     * [機 能] ユニコード(UTF-8)の範囲内か判定する。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param c 判定対象の文字列
     *           変換する半角ラテン基本文字列
     * @return true:Asciiの範囲内,false:Asciiの範囲外
     */
    public static boolean isUnicode(char c) {
        boolean ret = false;
        //文字列から1文字取り出す
        Character myChar = new Character(c);

        //Basic Latin
        //NKo
        if (myChar.compareTo(new Character((char) 0x0000)) >= 0
                && myChar.compareTo(new Character((char) 0x07ff)) <= 0) {
                ret = true;
        }

        //Devanagari
        //Mongolian
        if (myChar.compareTo(new Character((char) 0x0900)) >= 0
                && myChar.compareTo(new Character((char) 0x18af)) <= 0) {
                ret = true;
        }

        //Limbu
        //Buginese
        if (myChar.compareTo(new Character((char) 0x1900)) >= 0
                && myChar.compareTo(new Character((char) 0x1a1f)) <= 0) {
                ret = true;
        }

        //Balinese
        if (myChar.compareTo(new Character((char) 0x1b00)) >= 0
                && myChar.compareTo(new Character((char) 0x1b7f)) <= 0) {
                ret = true;
        }

        //Phonetic Extensions
        //Ethiopic Extended
        if (myChar.compareTo(new Character((char) 0x1d00)) >= 0
                && myChar.compareTo(new Character((char) 0x2ddf)) <= 0) {
                ret = true;
        }

        //Supplemental Punctuation
        //Kangxi Radicals
        if (myChar.compareTo(new Character((char) 0x2e00)) >= 0
                && myChar.compareTo(new Character((char) 0x2fdf)) <= 0) {
                ret = true;
        }

        //Ideographic Description Characters
        //Yi Radicals
        if (myChar.compareTo(new Character((char) 0x2ff0)) >= 0
                && myChar.compareTo(new Character((char) 0xa4cf)) <= 0) {
                ret = true;
        }

        //Modifier Tone Letters
        //Syloti Nagri
        if (myChar.compareTo(new Character((char) 0xa700)) >= 0
                && myChar.compareTo(new Character((char) 0xa82f)) <= 0) {
                ret = true;
        }

        //Phags-pa
        if (myChar.compareTo(new Character((char) 0xa840)) >= 0
                && myChar.compareTo(new Character((char) 0xa87f)) <= 0) {
                ret = true;
        }

        //Hangul Syllables
        if (myChar.compareTo(new Character((char) 0xac00)) >= 0
                && myChar.compareTo(new Character((char) 0xd7af)) <= 0) {
                ret = true;
        }

        //High Surrogates
        //Specials
        if (myChar.compareTo(new Character((char) 0xd800)) >= 0
                && myChar.compareTo(new Character((char) 0xffff)) <= 0) {
                ret = true;
        }

        return ret;
    }

    /**
     * [機 能] Latinエンコードから指定エンコードへの変換 (主にWEBで使用します。)<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param pSource 変換する文字列
     * @return 変換後の文字列
     * @throws UnsupportedEncodingException
     *             サポートされないエンコード指定時にスロー
     */
    public static String cnvLtinJapanese(String pSource)
            throws UnsupportedEncodingException {
        return cnvLatin2Japanese(pSource, "JISAutoDetect");
    }

    /**
     * [機 能] Latinエンコードから指定エンコードへの変換 (主にWEBで使用します。)<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param pSource 変換する文字列
     * @param pEncode 変換するエンコード
     * @return 変換後の文字列
     * @throws UnsupportedEncodingException サポートされないエンコード指定時にスロー
     */
    public static String cnvLatin2Japanese(String pSource, String pEncode)
            throws UnsupportedEncodingException {
        if (pSource == null) {
            return null;
        }

        return new String(pSource.getBytes("8859_1"), pEncode);
    }

    /**
     * [機 能] 全角ラテン文字を半角ラテン文字に変換する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param pSource 変換する全角ラテン基本文字列
     * @return 変換後の半角ラテン基本文字列
     */
    public static String latinFull2Half(String pSource) {
        //戻り値
        StringBuilder myReturn = new StringBuilder();

        // パラメーターの文字列を先頭から1文字ずつ調べる
        for (int i = 0; i < pSource.length(); i++) {
            //文字列から1文字取り出す
            Character myChar = new Character(pSource.substring(i, i + 1)
                    .charAt(0));
            // Unicode全角ラテン文字のコード範囲か調べる
            if (myChar.compareTo(new Character(LATIN_FULL_MIN)) >= 0
                    && myChar.compareTo(new Character(LATIN_FULL_MAX)) <= 0) {
                // 変換文字に 0xfee0 を減算して半角文字に変換する
                Character myNewChar = new Character(
                        (char) (myChar.charValue() - (new Character(
                                (char) 0xfee0)).charValue()));

                // 文字列としてセットする
                myReturn.append(myNewChar.toString());
                // Unicode全角ラテン以外なら変換しない
            } else {
                myReturn.append(pSource.substring(i, i + 1));
            }
       }
       return myReturn.toString();
    }

    /**
     * [機 能] 半角カタカナを全角カタカナに変換する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param pSource 変換する半角カタカナ文字列
     * @return 変換後の全角カタカナ文字列
     */
    public static String half2Full(String pSource) {
       String ret = null;
       String str = latinHalf2Full(pSource);
       ret = StringUtilKana.katakanaHalf2Full(str);
       return ret;
    }

    /**
     * <br>[機  能] 数値の文字列をカンマフォーマットして返します
     * <br>[解  説]
     * <br>[備  考]
     * @param numberString  数値文字列
     * @return  カンマフォーマットされた数値文字列.BigDecimal変換できない場合はnull
     */
    public static String toCommaFormat(String numberString) {
        try {
            new BigDecimal(numberString);
        } catch (Exception ex) {
            return null;

        }

        boolean isMinus = false;
        String last = numberString;
        String integer = "";
        String fraction = "";

        if (numberString.indexOf("-") > -1) {
            isMinus = true;
            last = numberString.substring(1);
        }

        int dotPos = last.indexOf(".");

        if (dotPos > 0) {
            fraction = last.substring(dotPos + 1);
            integer = last.substring(0, dotPos);
        } else {
            integer = last;
        }

        String commaInteger = "";

        while (integer.length() > 3) {
            String subStr = integer.substring(integer.length() - 3);
            commaInteger = "," + subStr + commaInteger;
            integer = integer.substring(0, integer.length() - 3);
        }

        String commaStr = integer + commaInteger;

        if (isMinus) {
            commaStr = "-" + commaStr;
        }

        if (dotPos > 0) {
            commaStr = commaStr + "." + fraction;
        }

        return commaStr;
    }

    /**
     * <br>[機  能] 数値の文字列を小数点以下の不要な０をトリムして返します
     * <br>[解  説] 1980.010は1,980.01
     * <br>[備  考]
     * @param numberString  数値文字列
     * @return  カンマフォーマットされた数値文字列.BigDecimal変換できない場合はnull
     */
    public static String toCommaUnderZeroTrim(String numberString) {
        try {
            BigDecimal bd = new BigDecimal(numberString);
            numberString = bd.toString();
        } catch (Exception ex) {
            return null;

        }

        boolean isMinus = false;
        String last = numberString;
        String integer = "";
        String fraction = "";

        if (numberString.indexOf("-") > -1) {
            isMinus = true;
            last = numberString.substring(1);
        }

        int dotPos = last.indexOf(".");
        if (dotPos > 0) {
            fraction = toUnderZeroTrim(last.substring(dotPos + 1));
            integer = last.substring(0, dotPos);
        } else {
            integer = last;
        }

        String commaInteger = "";

        while (integer.length() > 3) {
            String subStr = integer.substring(integer.length() - 3);
            commaInteger = "," + subStr + commaInteger;
            integer = integer.substring(0, integer.length() - 3);
        }

        String commaStr = integer + commaInteger;

        if (isMinus) {
            commaStr = "-" + commaStr;
        }

        if (dotPos > 0 && fraction.length() > 0) {
            commaStr = commaStr + "." + fraction;
        }

        return commaStr;
    }
    /**
     * <br>[機  能] 数値の文字列を小数点以下の不要な０をトリムして返します
     * <br>[解  説] 1980.010は1,980.01
     * <br>[備  考]
     * @param numberString  数値文字列
     * @return  カンマフォーマットされた数値文字列.BigDecimal変換できない場合はnull
     */
    public static String toUnderZeroTrim(String numberString) {
        String trimString = "";
        if (numberString == null) {
            return trimString;
        }
        trimString = numberString;
        while (trimString.length() > 0) {
            if (trimString.lastIndexOf("0") == trimString.length() - 1) {
                trimString = trimString.substring(0, trimString.length() - 1);
            } else {
                return trimString;
            }
        }
        return trimString;
    }
    /**
     * <br>[機  能] 引数をカンマフォーマットする
     * <br>[解  説]
     * <br>[備  考]
     * @param bigDecimal 対象の数値
     * @return フォーマット後の文字列
     */
    public static String toCommaFormat(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return toCommaFormat(bigDecimal.toString());
    }

    /**
     * <br>[機  能] nullか長さ0の文字の場合にtrueを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param str 対象の文字列
     * @return true:nullか0文字,false:1文字以上の文字列
     */
    public static boolean isNullZeroString(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() <= 0) {
            return true;
        }
        return false;
    }
    /**
     * <br>[機  能] nullか長さ0の文字の場合にtrueを返す(半角スペースも0文字とみなす)
     * <br>[解  説]
     * <br>[備  考]
     * @param str 対象の文字列
     * @return true:nullか0文字,false:1文字以上の文字列
     */
    public static boolean isNullZeroStringSpace(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() <= 0) {
            return true;
        } else if ((str.trim()).length() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 数字0埋めフォーマット
     * <br>[解  説]
     * <br>[備  考]
     * @param num 数字
     * @param pattern パターン
     * @return フォーマットされた数字文字列
     */
    public static String toDecFormat(int num, String pattern) {
        String snum = Integer.toString(num);
        int len = snum.length();
        if (len > pattern.length()) {
            len = snum.length();
        } else {
            len = pattern.length();
        }
        StringBuffer sb = new StringBuffer(len);
        FieldPosition fp = new FieldPosition(NumberFormat.INTEGER_FIELD);
        DecimalFormat df = new DecimalFormat(pattern);
        df.format(num, sb, fp);
        return sb.toString();
    }

    /**
     * <br>[機  能] 数字0埋めフォーマット
     * <br>[解  説]
     * <br>[備  考]
     * @param snum 数字
     * @param pattern パターン
     * @return フォーマットされた数字文字列
     */
    public static String toDecFormat(String snum, String pattern) {
        return toDecFormat(Integer.parseInt(snum), pattern);
    }

    /**
     * <br>[機  能] 数字をカンマ付きの文字列に変換します。
     * <br>[解  説]
     * <br>[備  考] 桁が足りない場合はゼロ埋めを行いyyyy/MM/ddの形式に合わせます
     * @param   number 処理したい数値
     * @return  カンマつきの数字文字列
     */
    public static String toCommaFromLong(long number) {
        return toCommaFromString(Long.toString(number));
    }

    /**
     * <br>[機  能] 数値の文字列をカンマフォーマットして返します
     * <br>[解  説]
     * <br>[備  考]
     * @param numberString  数値文字列
     * @return  カンマフォーマットされた数値文字列.BigDecimal変換できない場合はnull
     */
    public static String toCommaFromString(String numberString) {
        try {
            new BigDecimal(numberString);
        } catch (Exception ex) {
            return null;

        }

        boolean isMinus = false;
        String last = numberString;
        String integer = "";
        String fraction = "";

        if (numberString.indexOf("-") > -1) {
            isMinus = true;
            last = numberString.substring(1);
        }

        int dotPos = last.indexOf(".");

        if (dotPos > 0) {
            fraction = last.substring(dotPos + 1);
            integer = last.substring(0, dotPos);
        } else {
            integer = last;
        }

        String commaInteger = "";

        while (integer.length() > 3) {
            String subStr = integer.substring(integer.length() - 3);
            commaInteger = "," + subStr + commaInteger;
            integer = integer.substring(0, integer.length() - 3);
        }

        String commaStr = integer + commaInteger;

        if (isMinus) {
            commaStr = "-" + commaStr;
        }

        if (dotPos > 0) {
            commaStr = commaStr + "." + fraction;
        }

        return commaStr;
    }

    /**
     * <br>[機  能] 引数をカンマフォーマットする
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     * @param bigDecimal 対象の数値
     * @return フォーマット後の文字列
     */
    public static String toCommaFromBigDecimal(BigDecimal bigDecimal) {
        return toCommaFromString(bigDecimal.toString());
    }

    /**
     * <br>[機  能] YY.MM.DD形式の文字列をYYYYMMDD形式に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param yymmdd YY.MM.DD形式の文字列
     * @return YYYYMMDD形式の文字列
     */
    public static String getDateString(String yymmdd) {
        String date = null;
        int a = yymmdd.indexOf(".", 0);
        String year = StringUtil.toDecFormat(yymmdd.substring(0, a), "00");
        int b = yymmdd.indexOf(".", a + 1);
        String month = StringUtil.toDecFormat(yymmdd.substring(a + 1, b), "00");
        String day = StringUtil.toDecFormat(yymmdd.substring(b + 1), "00");
        date = "20" + year + month + day;
        return date;
    }

    /**
     * <br>[機  能] 時間文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param hour 時
     * @param minute 分
     * @param req リクエスト
     * @return 時間文字列
     */
    public String getTimeString(String hour, String minute,
                                HttpServletRequest req) {
        String[] params = {hour.toString(),
                        StringUtil.toDecFormat(minute.toString(), "00")};

        GsMessage gsMsg = new GsMessage();
        return gsMsg.getMessage(req, "cmn.time.input", params);
    }

    /**
     * <br>[機  能] 時間文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param hour 時
     * @param minute 分
     * @param reqMdl リクエスト情報
     * @return 時間文字列
     */
    public String getTimeString(String hour, String minute,
                                RequestModel reqMdl) {
        String[] params = {hour.toString(),
                        StringUtil.toDecFormat(minute.toString(), "00")};

        GsMessage gsMsg = new GsMessage(reqMdl);
        return gsMsg.getMessage("cmn.time.input", params);
    }

    /**
     * <br>[機  能] 半角数字を全角数字に変換する(半角数字、カンマ、ドットのみ対応)
     * <br>[解  説]
     * <br>[備  考]
     * @param numberString 半角数字
     * @return 全角数字
     */
    public static String getZenkaku(String numberString) {
        String zenkaku = "";
        char[] target = numberString.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('1' == c) {
                zenkaku += "１";
            } else if ('2' == c) {
                zenkaku += "２";
            } else if ('3' == c) {
                zenkaku += "３";
            } else if ('4' == c) {
                zenkaku += "４";
            } else if ('5' == c) {
                zenkaku += "５";
            } else if ('6' == c) {
                zenkaku += "６";
            } else if ('7' == c) {
                zenkaku += "７";
            } else if ('8' == c) {
                zenkaku += "８";
            } else if ('9' == c) {
                zenkaku += "９";
            } else if ('0' == c) {
                zenkaku += "０";
            } else if (',' == c) {
                zenkaku += "，";
            } else if ('.' == c) {
                zenkaku += "．";
            }
        }
        return zenkaku;
    }

    /**
     * <br>[機  能] 文字列からハイフンを削除
     * <br>[解  説]
     * <br>[備  考]
     * @param str 変換対象の文字列
     * @return 全角数字
     */
    public static String toDeleteHaifunStr(String str) {
        String ret = "";
        char[] target = str.toCharArray();
        for (int i = 0; i < target.length; i++) {
            char c = target[i];
            if ('-' == c) {
            } else {
                ret += c;
            }
        }
        return ret;
    }
    /**
     * <br>[機  能] 引数でわたされた文字列中にリターンコードがある時
     *              リターンコードを削除した文字列を返す。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param str 変換元の文字列
     * @return 変換済みの文字列
     */
    public static String toDeleteReturnCode(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(str);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()) {
            switch(c) {

            case '\n':
                break;
            case '\r':
                if (stit.next() != '\n') {
                        stit.previous();
                }
                break;
            default :
                retSB.append(c);
                break;
            }
        }
        return retSB.toString();
    }

    /**
     * <br>[機  能] 半角数字を全角数字に変換する(半角数字、ハイフンのみ対応)
     * <br>[解  説]
     * <br>[備  考]
     * @param numberString 半角数字
     * @return 全角数字
     */
    public static String getZenkakuNumAndHf(String numberString) {
        String zenkaku = "";
        if (!numberString.equals("")) {
            char[] target = numberString.toCharArray();
            for (int i = 0; i < target.length; i++) {
                char c = target[i];
                if ('1' == c) {
                    zenkaku += "１";
                } else if ('2' == c) {
                    zenkaku += "２";
                } else if ('3' == c) {
                    zenkaku += "３";
                } else if ('4' == c) {
                    zenkaku += "４";
                } else if ('5' == c) {
                    zenkaku += "５";
                } else if ('6' == c) {
                    zenkaku += "６";
                } else if ('7' == c) {
                    zenkaku += "７";
                } else if ('8' == c) {
                    zenkaku += "８";
                } else if ('9' == c) {
                    zenkaku += "９";
                } else if ('0' == c) {
                    zenkaku += "０";
                } else if ('-' == c) {
                    zenkaku += "‐";
                }
            }
        }
        return zenkaku;
    }

    /**
     * <br>[機  能] 全角数字を半角数字に変換する(全角数字、ハイフンのみ対応)
     * <br>[解  説]
     * <br>[備  考]
     * @param numberString 全角数字
     * @return 半角数字
     */
    public static String getHankakuNumAndHf(String numberString) {
        String hankaku = "";
        if (!numberString.equals("")) {
            char[] target = numberString.toCharArray();
            for (int i = 0; i < target.length; i++) {
                char c = target[i];
                if ('１' == c) {
                    hankaku += "1";
                } else if ('２' == c) {
                    hankaku += "2";
                } else if ('３' == c) {
                    hankaku += "3";
                } else if ('４' == c) {
                    hankaku += "4";
                } else if ('５' == c) {
                    hankaku += "5";
                } else if ('６' == c) {
                    hankaku += "6";
                } else if ('７' == c) {
                    hankaku += "7";
                } else if ('８' == c) {
                    hankaku += "8";
                } else if ('９' == c) {
                    hankaku += "9";
                } else if ('０' == c) {
                    hankaku += "0";
                } else if ('‐' == c) {
                    hankaku += "-";
                }
            }
        }
        return hankaku;
    }

    /**
     * <br>[機  能] ファイル名から拡張子を取得する。見つからない場合はnullを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @return 拡張子
     */
    public static String getExtension(String fileName) {
        String extStr = null;
        int num = fileName.lastIndexOf('.');
        if (num != -1) {
            extStr = fileName.substring(num, fileName.length());
        }
        return extStr;
    }

    /**
     * <br>[機  能] 郵便番号を"-"で分離し、上段の文字列を返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象文字列
     * @return 郵便番号上段
     */
    public static String getPost1(String target) {
        String ret = "";
        if (target == null) {
            return ret;
        }
        int idx = target.indexOf("-");
        if (idx != -1) {
            ret = target.substring(0, idx);
        } else {
            ret = target;
        }
        return ret;
    }

    /**
     * <br>[機  能] 郵便番号を"-"で分離し、下段の文字列を返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象文字列
     * @return 郵便番号下段
     */
    public static String getPost2(String target) {
        String ret = "";
        if (target == null) {
            return ret;
        }
        int idx = target.indexOf("-");
        if (idx != -1) {
            ret = target.substring(idx + 1, target.length());
        }
        return ret;
    }

    /**
     * <br>[機  能] 最大４桁の数字を分離し、HH:MMの文字列を返します。
     * <br>[解  説]
     * <br>[備  考] 上２桁が時間、下２桁が分の形式専用
     * @param target 対象数字
     * @return HH時間MM分の文字列
     */
    public static String toHourMinute(int target) {

        String ret = "";
        int min = target % 100;
        int hour = target / 100;

        String strHour = StringUtil.toDecFormat(hour, "00");
        String strMin = StringUtil.toDecFormat(min, "00");
        ret = strHour + ":" + strMin;
        return ret;
    }

    /**
     * <br>[機  能] 最大４桁の数字を分離し、HH時間MM分の文字列を返します。
     * <br>[解  説]
     * <br>[備  考] 上２桁が時間、下２桁が分の形式専用
     *
     *
     * @param target 対象数字
     * @param req HttpServletRequest
     * @return HH時間MM分の文字列
     */
    public static String toStrHourMinute(int target, HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String ret = "";
        int min = target % 100;
        int hour = target / 100;

        String strHour = StringUtil.toDecFormat(hour, "00");
        String strMin = StringUtil.toDecFormat(min, "00");
        ret = strHour
            + gsMsg.getMessage(req, "cmn.time")
            + strMin
            + gsMsg.getMessage(req, "cmn.minute");
        return ret;
    }


    /**
     * <br>[機  能] MM分からをHH時間MM分の文字列を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 対象数字
     * @param req HttpServletRequest
     * @return HH時間MM分の文字列
     */
    public static String toHourMinuteForMinute(int target, HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String ret = "";
        int min = target % 60;
        int hour = target / 60;

        String strHour = StringUtil.toDecFormat(hour, "00");
        String strMin = StringUtil.toDecFormat(min, "00");
        ret = strHour
            + gsMsg.getMessage(req, "cmn.time")
            + strMin
            + gsMsg.getMessage(req, "cmn.minute");
        return ret;
    }

    /**
     * <br>[機  能] 指定文字数後に改行(CRLF)を追加する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 対象の文字列
     * @param index 改行位置
     * @return 改行追加後の文字列
     */
    public static String addReturnCode(String target, int index) {
        StringBuilder ret = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(target);

        int cnt = 0;
        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()
            ) {
            if (cnt >= index) {
                ret.append("\r\n");
                cnt = 0;
            }
            cnt++;
            ret.append(c);
        }
        return ret.toString();
    }

    /**
     * <br>[機  能] Velocityフォーマット文字列に指定されたパターンを設定して返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param templeteString Velocity文字列
     * @param pattern templeteStringへ設定するパターン
     * @return templeteStringへpatternを設定した文字列
     * @throws ResourceNotFoundException メール本文のテンプレートファイルが存在しない場合
     * @throws ParseErrorException メール本文のテンプレートファイルが不正な場合
     * @throws Exception メール本文の作成に失敗した場合
     */
    @SuppressWarnings("all")
    public static String merge(String templeteString, Map pattern)
        throws ResourceNotFoundException,
        ParseErrorException,
        Exception {

        StringWriter sw = null;
        String result = null;

        try {
            Velocity.init();
            //Velocityコンテキストに値を設定s
            VelocityContext vc = new VelocityContext();

            Iterator it = pattern.keySet().iterator();
            String key = null;
            while (it.hasNext()) {
                key = (String) it.next();
                vc.put(key, pattern.get(key));
            }

            sw = new StringWriter();

            Velocity.evaluate(vc, sw, "StringUtil.merge", templeteString);
            sw.flush();

            result = sw.toString();

        } catch (ResourceNotFoundException rne) {
            throw rne;
        } catch (ParseErrorException pe) {
            throw pe;
        } finally {
            if (sw != null) {
                sw.close();
            }
        }

        return result;
    }

    /**
     * <br>[機  能] 文字列中の「\」、「'」、「"」を「\\」、「\'」、「\"」に変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return エスケープ後の文字列
     */
    public static String toCortationEscape(String str) {
        String escStr = "";
        if (str == null) {
            return escStr;
        }
        String escape = "\\";

        if (!str.equals("")) {
            char[] target = str.toCharArray();
            for (int i = 0; i < target.length; i++) {
                char c = target[i];
                if ('\'' == c) {
                    escStr += escape + c;
                } else if ('\"' == c) {
                    escStr += escape + c;
                } else if ('\\' == c) {
                    escStr += escape + c;
                } else {
                    escStr += c;
                }
            }
        }
        return escStr;
    }
    /**
     * <br>[機  能] 文字列中の「\」、「'」を「\\」、「\'」に変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @return エスケープ後の文字列
     */
    public static String toSingleCortationEscape(String str) {
        String escStr = "";
        if (str == null) {
            return escStr;
        }
        String escape = "\\";

        if (!str.equals("")) {
            char[] target = str.toCharArray();
            for (int i = 0; i < target.length; i++) {
                char c = target[i];
                if ('\'' == c) {
                    escStr += escape + c;
                } else if ('\\' == c) {
                    escStr += escape + c;
                } else {
                    escStr += c;
                }
            }
        }
        return escStr;
    }
    /**
     * <br>[機  能] 渡されたStringからURLを抽出し、ランダムなパラメータを追加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param req  置換前のString
     * @return  URL
     */
    public static String getLinkAddPrm(String req) {

        try {
            String ran = String.valueOf(Math.round(Math.random() * 100));

            Pattern pattern = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(req);
            while (matcher.find()) {
                String url = matcher.group();
                String afUrl = null;
                if (url.indexOf("?") > -1) {
                    afUrl = url.replace("?", "?" + ran + "&");
                } else {
                    afUrl = url + "?" + ran;
                }
                req = req.replace(url, afUrl);
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return req;
    }
    /**
     * <br>[機  能] 渡されたStringからURLを抽出し、ハイパーリンクにします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req  置換前のString
     * @param type ウィンドウの表示タイプ
     * @return  置換後のString
     */
    public static String transToLink(String req, int type) {
        return transToLink(req, type, false);
    }

    /**
     * <br>[機  能] 渡されたStringからURLを抽出し、ハイパーリンクにします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param req  置換前のString
     * @param type ウィンドウの表示タイプ
     * @param wbr URLに改行位置を設定
     * @return  置換後のString
     */
    public static String transToLink(String req, int type, boolean wbr) {

        try {

            Pattern pattern = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(req);

            if (!wbr) {
                String str = "<A HREF=\"$0\">$0</A>";
                if (type == OTHER_WIN) {
                    str = "<A HREF=\"$0\" target=\"_blank\">$0</A>";
                }
                req = matcher.replaceAll(str);

            } else {
                String replaceStr = "";
                String bufReq = req.toString();
                while (matcher.find()) {
                    String url = matcher.group();

                    int urlIndex = bufReq.indexOf(url) + url.length();
                    String str = bufReq.substring(0, urlIndex);
                    String linkStr = "<A HREF=\"" + url + "\"";
                    if (type == OTHER_WIN) {
                        linkStr += " target=\"_blank\"";
                    }

                    int cornIndex = url.indexOf("://") + CORON_LEN;
                    String urlBf = url.substring(0, cornIndex);
                    String urlAf = url.substring(cornIndex);
                    String viewUrl = urlAf.replace("/", "/<wbr>");
                    viewUrl = viewUrl.replace("%", "%<wbr>");
                    linkStr += ">" + urlBf + viewUrl + "</A>";

                    replaceStr += str.replace(url, linkStr);
                    bufReq = bufReq.substring(urlIndex);
                }

                if (replaceStr.length() > 0) {
                    replaceStr += bufReq;
                    req = replaceStr;
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return req;
    }

    /**
     * <br>[機  能] 文字列を指定した文字で置換します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param input 対象文字列
     * @param pattern 置換前の文字列
     * @param replacement 置換後の文字列
     * @return 置換後の文字列
     */
    public static String substitute(
        String input,
        String pattern,
        String replacement
        ) {

        return input.replace(pattern, replacement);
    }

    /**
     * <br>[機  能] 文字列を指定した文字で分割し、結果をVectorで返します
     * <br>[解  説]
     * <br>[備  考]
     * @param split 分割する文字
     * @param target 分割される文字
     * @return 分割された文字
     */
    public static ArrayList<String> split(String split, String target) {
        Perl5Util perl = new Perl5Util();
        ArrayList<String> e = new ArrayList<String>();
        perl.split(e, "m/[" + split + "]+/", target);
        return e;
    }
    /**
     * <br>[機  能] y,m,dを0埋めしてString yyyymmddを返します
     * <br>[解  説]
     * <br>[備  考] 引数をintで指定
     * @param y int年
     * @param m int月
     * @param d int日
     * @return yyyymmdd String年月日
     */
    public static String getStrYyyyMmDd(int y, int m, int d) {
        String yyyy = StringUtil.toDecFormat(y, "0000");
        String mm = StringUtil.toDecFormat(m, "00");
        String dd = StringUtil.toDecFormat(d, "00");
        String yyyymmdd = yyyy + mm + dd;

        return yyyymmdd;
    }
    /**
     * <br>[機  能] y,m,dを0埋めしてString yyyymmddを返します
     * <br>[解  説]
     * <br>[備  考] 引数をStringで指定
     * @param y String年
     * @param m String月
     * @param d String日
     * @return yyyymmdd String年月日
     */
    public static String getStrYyyyMmDd(String y, String m, String d) {
        String yyyy = StringUtil.toDecFormat(y, "0000");
        String mm = StringUtil.toDecFormat(m, "00");
        String dd = StringUtil.toDecFormat(d, "00");
        String yyyymmdd = yyyy + mm + dd;

        return yyyymmdd;
    }

    /**
     * <p>文字列の改行コードを指定した文字列に置換します。
     * @param value 文字列
     * @param repStr 改行コードと置換する文字列
     * @return 変換後のString
     */
    public static String replaceReturnCode(String value, String repStr) {
        if (value == null) {
            return null;
        }

        StringBuilder ret = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(value);

        for (
            char c = stit.first();
            c != CharacterIterator.DONE;
            c = stit.next()) {
            switch(c) {

            case '\n':
                ret.append(repStr);
                break;
            case '\r':
                if (stit.next() != '\n') {
                        stit.previous();
                }
                ret.append(repStr);
                break;
            default :
                ret.append(c);
                break;
            }
        }

        return ret.toString();
    }

    /**
     * <br>[機  能] 指定した文字列以内の文字を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param str 対象の文字列
     * @param length 指定の長さ
     * @return 指定の長さ以内の文字列
     */
    public static String trimRengeString(String str, int length) {
        String ret = null;
        if (str.length() > length) {
            ret = str.substring(0, length);
        } else {
            ret = str;
        }
        return ret;
    }

    /**
     * <br>[機  能] XMLで利用不可能な文字を削除する
     * <br>[解  説] JDOM でXMLを扱う場合のエラー回避のために使用する。
     * <br>         JDOM2ではUTF-8の範囲外文字をエスケープされるため使用する必要がない
     * <br>[備  考]
     * @param str 対象文字列
     * @return 変換後の対象文字列
     */
    public static String removeInvalidCharacterForXml(String str) {

        if (isNullZeroString(str)) {
            return str;
        }

        StringBuilder buf = new StringBuilder();
        char[] charList = str.toCharArray();

        for (char charValue : charList) {
            if (charValue <= 0x08
            || (charValue >= 0x0b && charValue <= 0x0c)
            || (charValue >= 0x0e && charValue <= 0x0f)
            || (charValue > 0xd7ff && charValue < 0xe000)
            || (charValue > 0xfffd && charValue < 0x10000)
            || (charValue > 0x10ffff)
            ) {
                continue;
            } else {
                buf.append(charValue);
            }
        }

        return buf.toString();
    }

    /**
     * <p>Listの内容をStringに変換します。
     * <p>1オブジェクトごとに改行します。(改行コードはOSのデフォルトになります)
     * @param list リスト
     * @return 変換後のString
     */
    @SuppressWarnings({"all", "unchecked" })
    public static String toStringByList(List list) {
        StringBuilder buf = new StringBuilder();
        String lineSep = System.getProperty("line.separator");

        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object obj =  it.next();
            buf.append(obj.toString() + lineSep);
        }
        return buf.toString();
    }

    /**
     * <br>[機  能] 相対パスから絶対パスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param str 対象のパス文字列
     * @return 変換後の絶対パス文字列
     */
    public static String getAbsolutePath(String str) {
        if (isNullZeroString(str)) {
            return str;
        }

        Path path = Paths.get(str);
        path = path.normalize(); // normalizeメソッドは冗長なパスを簡潔にするメソッド
        path = path.toAbsolutePath(); // 絶対パスに変換

        return path.toString();
    }
    /**
     *
     * <br>[機  能] 全角空白もふくめてトリミングを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param src トリミング対象文字列
     * @return トリミング後文字列
     */
    public static String trimJPSpace(String src) {
        return Optional.ofNullable(src)
            .map(str -> str.trim())
            .map(str -> str.replaceAll("^[　\t]*", ""))
            .map(str -> str.replaceAll("[　\t]*$", ""))
            .orElse(null);
    }
}