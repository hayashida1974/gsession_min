package jp.co.sjts.util.io;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * <br>[機 能] CRLFを改行とするWriter
 * <br>[解 説]
 * <br>[備 考] PrintWriterの拡張クラス
 * @author JTS
 */
public class CrlfTerminatedWriter extends PrintWriter {

    /** 改行コード */
    private static String lineSeparator = "\r\n";

    /** 自動でflush()を呼び出すか判定するフラグ */
    private final boolean autoFlush__;

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param out ライター
     */
    public CrlfTerminatedWriter(Writer out) {
        super(out);
        autoFlush__ = false;
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param out ライター
     * @param autoFlush 自動でflush()を呼び出すためのフラグ
     */
    public CrlfTerminatedWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
        autoFlush__ = autoFlush;
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param out OutputStreamオブジェクト
     */
    public CrlfTerminatedWriter(OutputStream out) {
        super(out);
        autoFlush__ = false;
    }

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param out OutputStreamオブジェクト
     * @param autoFlush 自動でflush()を呼び出すためのフラグ
     */
    public CrlfTerminatedWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
        this.autoFlush__ = autoFlush;
    }

    /**
     * [機 能] 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     */
    public void println() {
        synchronized (lock) {
            write(lineSeparator);
            if (autoFlush__) {
                flush();
            }
        }
    }

    /**
     * [機 能] 引数のboolean + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x booleanオブジェクト
     */
    public void println(boolean x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のchar + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x charオブジェクト
     */
    public void println(char x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のint + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x intオブジェクト
     */
    public void println(int x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のlong + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x longオブジェクト
     */
    public void println(long x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のfloat + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x floatオブジェクト
     */
    public void println(float x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のdouble + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x doubleオブジェクト
     */
    public void println(double x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のchar[] + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x char[]オブジェクト
     */
    public void println(char[] x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のString + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x Stringオブジェクト
     */
    public void println(String x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }

    /**
     * [機 能] 引数のObject + 改行を出力する<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param x Objectオブジェクト
     */
    public void println(Object x) {
        synchronized (lock) {
            print(x);
            println();
        }
    }
}
