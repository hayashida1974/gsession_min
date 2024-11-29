package jp.co.sjts.util.csv;

import java.text.StringCharacterIterator;
import java.text.CharacterIterator;

/**
 * <br>[機  能] CSVファイルからレコード単位の分割処理を行います。
 * <br>[解  説]
 * <br>[備  考] データをメモリ内に格納するため、データサイズが大きくなる場合は使用しないこと。
 * @author JTS
 */
public class CsvRecord {
    /** CSVファイル全体 */
    private String strData__;
    /** 現在のポジション */
    private int intPos = 0;
    /** CSVファイル全体の文字数*/
    private int intMax; //データ長

    /**
     * <br>[機  能] CSVファイルの内容をString型でセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param    strData    CSVファイルの内容
     */
    public CsvRecord(String strData) {
        this.strData__ = strData;
        intMax = this.strData__.length();
    }

    /**
     * <br>[機  能] CSVファイルの内容(char[]型)をString型でセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param    chrData    CSVファイルの内容
     */
    public CsvRecord(char[] chrData) {
        this.strData__ = new String(chrData);
        intMax = this.strData__.length();
    }

    /**
     * <br>[機  能] 次のリターン位置を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     *  @param    intStart 開始位置
     *  @return   次のリターン位置
     */
    private int __nextRt(int intStart) {
        if (intStart >= intMax) {
            return intMax;
        }

        boolean blnFlg = false;
        String str = "";
        str = strData__.substring(intStart);

        StringCharacterIterator stit = new StringCharacterIterator(str);

        int intCnt = intStart;
        for (char ch = stit.first();
            ch != CharacterIterator.DONE;
            ch = stit.next()) {
            intCnt++;
            if (ch == '\n' && blnFlg) {
                break;
            } else if (ch == '\r') {
                blnFlg = true;
            } else {
                blnFlg = false;
            }
        }
        return intCnt;
    }

    /**
     * <br>[機  能] レコード数を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return   レコード数
     */
    public int getCount() {
        int i = 0;
        int intCnt = 1;
        while ((i = __nextRt(i)) < intMax) {
            i++;
            intCnt++;
        }
        return intCnt;
    }

    /**
     * <br>[機  能] 次のレコードをかえします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return    1レコード
     */
    public String getNextRecord() {
        if (intPos >= intMax) { //次のデータがなくなってもエラーなしにする
            return "";
        }
        int intNextp = __nextRt(intPos);
        String str = strData__.substring(intPos, intNextp);
        intPos = intNextp;
        return str;
    }

    /**
     * <br>[機  能] 次のレコードが有るか確認します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return    true:レコードは存在します,false:次のレコードは存在しません。
     */
    public boolean hasMoreRecord() {
        if ((__nextRt(intPos) <= intMax) && (intPos < intMax)) {
            return true;
        }
        return false;
    }
}
