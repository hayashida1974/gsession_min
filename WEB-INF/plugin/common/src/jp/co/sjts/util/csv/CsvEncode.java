package jp.co.sjts.util.csv;

/**
 * <br>[機  能] 1データをCSVデータとして出力できる形に加工します。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CsvEncode {

    /**
     * <br>[機  能] 引数で指定した単語をCsv形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param   strItem 処理したい文字列
     * @return  strItem 処理した文字列
     */
    public static String encString(String strItem) {

        //文字列が0の時はそのまま返す
        if (strItem == null || strItem.length() == 0) {
            return "";
        }
        //文字列中に「"」「,」「CRLF」がなければそのまま返す
        if (strItem.indexOf('"') < 0
            && strItem.indexOf(',') < 0
            && strItem.indexOf("\n") < 0) {
            return strItem;
        }

        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        strBuf.append('"');
        for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
            char ch = strItem.charAt(intCnt);
            if ('"' == ch) {
                strBuf.append("\"\"");
            } else if ('\r' == ch) {
                //何もしない
            } else if ('\n' == ch) {
                strBuf.append("\n");
            } else {
                strBuf.append(ch);
            }
        }
        strBuf.append('"');
        return new String(strBuf);
    }
}
