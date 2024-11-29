package jp.co.sjts.util.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;

/**
 * <br>[機  能] CSVファイルの読み込みを簡易化するためのクラス
 * <br>[解  説] CRLFを改行とみなし、CSVファイルを読み込む。
 *              LFは改行とはみなさない。
 *              readFileを実行すると、行毎にprocessedLineがコールされる。
 *              サブクラスでprocessedLineを実装
 * <br>[備  考] 大きなサイズのファイルも処理できます。
 * @author JTS
 */
public abstract class AbstractCsvRecordReader {
    /** CSVの解析時に、行の終端とみなす文字コード */
    public static final String RCODE = "\r\n";

    /** CSV読み込み行数上限 */
    public static final int MAX_ROW_COUNT = 10000;

    /** CSV読み込み行数上限 */
    public static final int MAX_ROW_COUNT_X5 = 50000;

    /** CSV読み込み行数上限 */
    public static final int MAX_ROW_COUNT_X20 = 200000;

    /**
     * [機 能] 1行に対する処理を実行します。<br>
     * [解 説] <br>
     * [備 考] 1行を読み込んだタイミングで実行されます。<br>
     * @param num 行数
     * @param lineStr 1行のString
     * @throws Exception csv読込時例外
     */
    protected abstract void processedLine(long num, String lineStr) throws Exception;
    /**
     *
     * <br>[機  能] CSVファイルの行数が指定数を超えているか確認
     * <br>[解  説]
     * <br>[備  考]
     * @param file ファイル
     * @param encode 読み込み時の文字コード
     * @param threashold 確認閾値
     * @return true:行数オーバー
     * @throws IOException ファイル読み込みエラー
     */
    public boolean isOverRowCount(File file, String encode, int threashold) throws IOException {
        FileInputStream fStream = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedreader = null;
        //ヘッダー行分を追加
        int max = threashold + 1;
        String nText = null;
        //行数
        long num = 0;
        try {
            //CSVファイルの読み込み
            fStream = new FileInputStream(file);
            isReader = new InputStreamReader(fStream, encode);
            bufferedreader = new BufferedReader(isReader);
            char[] cbuf = new char[1024];
            while (true) {
                int rcnt = bufferedreader.read(cbuf, 0, 1024);
                if (rcnt == -1) {
                    break;
                }
                String tmp = NullDefault.getString(nText, "").concat(__getStringFmChar(cbuf, rcnt));

                int idxCrlf = 0;
                while (idxCrlf != -1) {
                    idxCrlf = tmp.indexOf(RCODE);
                    if (idxCrlf == -1) {
                        nText = tmp;
                        break;
                    }
//                    String line = tmp.substring(0, idxCrlf);
                    tmp = tmp.substring(idxCrlf + 2, tmp.length());
                    num++;
                    if (num > max) {
                        return true;
                    }
                    //余りの文字列処理
                    if (tmp.length() == idxCrlf + 2) {
                        nText = null;
                    } else {
                        nText = tmp;
                    }
                }
                cbuf = new char[1024];

            }
            //最終行で、改行コードがないものへの対応
            if (nText != null && nText.length() > 0) {
                num++;
            }
            if (num > max) {
                return true;
            }
            return false;
        } finally {
            if (fStream != null) {
                fStream.close();
            }
            if (isReader != null) {
                isReader.close();
            }
            if (bufferedreader != null) {
                bufferedreader.close();
            }
        }
    }

    /**
     * [機 能] ファイルの読み込みを実行します。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param file ファイル
     * @param encode 読み込み時の文字コード
     * @return num 読込み行数
     * @throws Exception 読込時例外
     */
    public long readFile(File file, String encode) throws Exception {
        FileInputStream fStream = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedreader = null;

        String nText = null;
        //行数
        long num = 0;
        try {
            //CSVファイルの読み込み
            fStream = new FileInputStream(file);
            isReader = new InputStreamReader(fStream, encode);
            bufferedreader = new BufferedReader(isReader);
            char[] cbuf = new char[1024];
            while (true) {
                int rcnt = bufferedreader.read(cbuf, 0, 1024);
                if (rcnt == -1) {
                    break;
                }
                String tmp = NullDefault.getString(nText, "").concat(__getStringFmChar(cbuf, rcnt));

                int idxCrlf = 0;
                while (idxCrlf != -1) {
                    idxCrlf = tmp.indexOf(RCODE);
                    if (idxCrlf == -1) {
                        nText = tmp;
                        break;
                    }
                    String line = tmp.substring(0, idxCrlf);
//                    tmp = tmp.substring(idxCrlf + 2, tmp.length()).trim();
                    tmp = tmp.substring(idxCrlf + 2, tmp.length());
                    //
                    num++;
                    processedLine(num, line);
                    //余りの文字列処理
                    if (tmp.length() == idxCrlf + 2) {
                        nText = null;
                    } else {
                        nText = tmp;
                    }
                }
                cbuf = new char[1024];

            }
            //最終行で、改行コードがないものへの対応
            if (nText != null && nText.length() > 0) {
                num++;
                processedLine(num, nText);
            }
        } finally {
            if (fStream != null) {
                fStream.close();
            }
            if (isReader != null) {
                isReader.close();
            }
            if (bufferedreader != null) {
                bufferedreader.close();
            }
        }
        return num;
    }

    /**
     * [機 能] バッファから取得したchar配列から必要な長さの文字列を生成する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param cbuf バッファから読み込んだchar[]
     * @param len 生成する長さ
     * @return 生成したString
     */
    private String __getStringFmChar(char[] cbuf, int len) {
        //
        StringBuilder sb = new StringBuilder();
        sb.append(cbuf, 0, len);
        return sb.toString();
    }
    /**
    *
    * <br>[機  能] CSVファイルの行数が指定数を超えているか確認
    * <br>[解  説]
    * <br>[備  考]
    * @param file ファイルパス
    * @return true:行数オーバー
    * @throws IOException ファイル読み込みエラー
    */
   public boolean isOverRowCount(String file)
           throws IOException {
       return isOverRowCount(new File(file),
               Encoding.WINDOWS_31J,
               AbstractCsvRecordReader.MAX_ROW_COUNT);
   }

}