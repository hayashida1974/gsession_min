package jp.co.sjts.util.csv;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * <br>[機  能] CSV出力時に改行コードをCRLFとして出力する
 * <br>[解  説] 環境により改行コードが変化するため改行コードを固定でCRLFとする
 * <br>[備  考]
 * @author JTS
 */
public class CsvPrintWriter extends PrintWriter {

    /** 改行コード(CRLF) */
    public static final String RETURNCODE = "\r\n";

    /**
     * コンストラクタ
     * @param out OutputStream
     */
    public CsvPrintWriter(OutputStream out) {
        super(out);
    }

    /**
     * コンストラクタ
     * @param out OutputStream
     * @param autoFlush 自動Flushの有無
     */
    public CsvPrintWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    /**
     * コンストラクタ
     * @param out OutputStream
     */
    public CsvPrintWriter(Writer out) {
        super(out);
    }

    /**
     * コンストラクタ
     * @param out OutputStream
     * @param autoFlush 自動Flushの有無
     */
    public CsvPrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    /**
     * <br>[機  能]引数+CRLF改行コードを出力します
     * <br>[解  説]java.io.PrintWriter.printlnをオーバーライトします
     * <br>[備　考]
     * @param s 出力対象の文字列
     */
    public void println(String s) {
        this.print(s + RETURNCODE);
        flush();
    }
}

