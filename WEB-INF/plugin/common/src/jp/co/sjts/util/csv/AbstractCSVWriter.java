package jp.co.sjts.util.csv;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jp.co.sjts.util.Encoding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] CSV出力に関するベースクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public abstract class AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractCSVWriter.class);

    /** CSV出力ファイルのパス */
    private String csvPath__ = null;

    /** 改行コード */
    private static final String NEWLINE = "\r\n";

    /**
     * <br>[機  能]末尾にCRLF改行コードを付加し1行の文字列をWriteします。
     * <br>[解  説]CSV出力時に必ず使用すること
     * <br>[備  考]
     * @param s 文字列
     * @param pw PrintWriter
     */
    public void println(String s, PrintWriter pw) {
        pw.print(s);
        pw.print(NEWLINE);
    }

    /**
     * <br>[機  能] CSV出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws CSVException CSV出力時例外
     */
    public void write() throws CSVException {

        CsvPrintWriter pw = null;
        boolean flg = false;

        try {

            //文字コードShift_JISで書き出し
            FileOutputStream fos = new FileOutputStream(csvPath__);
            OutputStreamWriter osw = new OutputStreamWriter(fos, Encoding.WINDOWS_31J);
            pw = new CsvPrintWriter(osw, true);

            flg = true;
            //CSV生成
            create(pw);

        } catch (IOException e) {
            log__.error("CSV書き出しエラー", e);
            throw new CSVException("CSV書き出しエラー", e);
        } finally {
            if (flg) {
                pw.flush();
                pw.close();
            }
        }
    }
    /**
     * <br>[機  能] CSV出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param encoding 文字エンコード
     * @throws CSVException CSV出力時例外
     */
    public void write(String encoding) throws CSVException {

        CsvPrintWriter pw = null;
        boolean flg = false;

        try {

            //文字コードShift_JISで書き出し
            FileOutputStream fos = new FileOutputStream(csvPath__);
            OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
            pw = new CsvPrintWriter(osw, true);

            flg = true;
            //CSV生成
            create(pw);

        } catch (IOException e) {
            log__.error("CSV書き出しエラー", e);
            throw new CSVException("CSV書き出しエラー", e);
        } finally {
            if (flg) {
                pw.flush();
                pw.close();
            }
        }
    }
    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public abstract void create(PrintWriter pw) throws CSVException;

    /**
     * <p>csvPath を取得します。
     * @return csvPath
     */
    public String getCsvPath() {
        return csvPath__;
    }
    /**
     * <p>csvPath をセットします。
     * @param csvPath csvPath
     */
    public void setCsvPath(String csvPath) {
        csvPath__ = csvPath;
    }
}
