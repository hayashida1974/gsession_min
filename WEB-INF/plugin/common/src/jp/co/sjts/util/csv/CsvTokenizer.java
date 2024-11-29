package jp.co.sjts.util.csv;

import java.util.Enumeration;

/**
 * <br>[機  能] 1レコードのデータを指定された区切り文字で分割します。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CsvTokenizer implements Enumeration<String> {

    /** 1レコード */
    private String strCsv__ = null;
    /** 現在のポジション */
    private int intPos__ = 0;
    /** 1レコードの文字数 */
    private int intMax__;
    /** 区切り文字(デフォルトでカンマ) */
    private char chrDelim__ = ',';
    /** 区切り文字を置き換える文字列(デフォルトでカンマ) */
    private String strChgChr__ = ",";

    /**
     * <br>[機  能] CSVデータの一行をセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param strCsv CSVデータの一行
     */
    public CsvTokenizer(String strCsv) {
        this.strCsv__ = strCsv;
        intMax__ = strCsv.length();
    }

    /**
     * <br>[機  能] CSVデータの一行と区切り文字をセットします。
     * <br>[解  説]
     * <br>[備  考] <p>区切り文字が一文字以上の場合は区切り文字の初めの一文字が
     *              区切り文字として設定されます。</p>
     *
     *  @param strCsv CSVデータの一行
     *  @param strDelim 区切り文字
     */
    public CsvTokenizer(String strCsv, String strDelim) {
        this.strCsv__ = strCsv;
        this.chrDelim__ = strDelim.charAt(0);
        intMax__ = strCsv.length();
    }

    /**
     * <br>[機  能] CSVデータの一行と区切り文字1をセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     *  @param strCsv CSVデータの一行
     *  @param chrDelim 区切り文字
     */
    public CsvTokenizer(String strCsv, char chrDelim) {
        this.strCsv__ = strCsv;
        this.chrDelim__ = chrDelim;
        intMax__ = strCsv.length();
    }

    /**
     * <br>[機  能] CSVデータの一行と区切り文字をセットします。
     * <br>[解  説]
     * <br>[備  考] <p>区切り文字が一文字以上の場合は区切り文字の初めの一文字が
     *              区切り文字として設定されます。
     *              ここではカンマ以外の区切り文字を指定する場合に使用します。</p>
     *
     *  @param strCsv CSVデータの一行
     *  @param strDelim 区切り文字
     *  @param strChgChr カンマ以外の区切り文字
     */
    public CsvTokenizer(String strCsv, String strDelim, String strChgChr) {
        this.strCsv__ = strCsv;
        this.chrDelim__ = strDelim.charAt(0);
        this.strChgChr__ = strChgChr;
        intMax__ = strCsv.length();
    }

    /**
     * <br>[機  能] CSVデータの一行と区切り文字をセットします。
     * <br>[解  説]
     * <br>[備  考] <p>区切り文字が一文字以上の場合は区切り文字の初めの一文字が
     *              区切り文字として設定されます。
     *              ここではカンマ以外の区切り文字を指定する場合に使用します。</p>
     *
     *  @param strCsv CSVデータの一行
     *  @param chrDelim 区切り文字
     *  @param strChgChr カンマ以外の区切り文字
     */
    public CsvTokenizer(String strCsv, char chrDelim, String strChgChr) {
        this.strCsv__ = strCsv;
        this.chrDelim__ = chrDelim;
        this.strChgChr__ = strChgChr;
        intMax__ = strCsv.length();
    }

    /**
     * <br>[機  能] 次の要素があるかどうかを返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return true:要素あり,false:要素なし
     */
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    /**
     * <br>[機  能] 次の要素を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 次の要素
     */
    public String nextElement() {
        return nextToken();
    }

    /**
     * <br>[機  能] 次のカンマの位置を返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param intStart 開始位置
     * @return 次のカンマの位置を返す
     */
    private int __nextCm(int intStart) {
        boolean blnFlg = false;
        while (intStart < intMax__) {
            char ch = strCsv__.charAt(intStart);
            if (!blnFlg && ch == chrDelim__) {
                break;
            } else if ('"' == ch) {
                blnFlg = !blnFlg; // ""の処理もこれでOK
            }
            intStart++;
        }
        return intStart;
    }

    /**
     * <br>[機  能] 項目数を返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return    項目数を返す
     */
    public int length() {
        int i = 0;
        int ret = 1;
        while ((i = __nextCm(i)) < intMax__) {
            i++;
            ret++;
        }
        return ret;
    }

    /**
     * <br>[機  能] まだ項目が残っているかどうか調べる。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return まだ項目がのこっているならtrue
     */
    public boolean hasMoreTokens() {
        // "<=" でなく、"<" だと末尾の項目を正しく処理できない。
        return (__nextCm(intPos__) <= intMax__);
    }

    /**
     * <br>[機  能] 次の項目の文字列を返す。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 次の項目
     */
    public String nextToken() {

        if (intPos__ > intMax__) { //次のデータがなくなってもエラーなしにする
            return "";
        }

        int intCnt = intPos__; //現在のポジションを保存
        intPos__ = __nextCm(intCnt); //次のカンマ位置

        StringBuilder strb = new StringBuilder();
        while (intCnt < intPos__) {
            char chr = strCsv__.charAt(intCnt++);
            if (chr == chrDelim__) { //区切り文字変換
                strb.append(strChgChr__);
            } else {
                if (chr == '"') { //"は取り外す
                    if ((intCnt < intPos__)
                        && (strCsv__.charAt(intCnt) == '"')) { // "が単独で現れたときは何もしない
                        strb.append(chr);
                        intCnt++;
                    }
                } else {
                    strb.append(chr);
                }
            }
        }
        intPos__++; //現在位置をカンマから移動

        //分割後の文字が「"」のみの場合、空白文字として扱う
        String token = new String(strb);
        if (token != null && token.equals("\"")) {
            token = "";
        }

        return token;
    }
}
