package jp.co.sjts.util.io;

/**
 * <br>[機  能] ファイル操作ユーティリティークラスの例外クラスです。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IOToolsException extends Exception {

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public IOToolsException() {
        super();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] エラーメッセージを指定してインスタンスを生成します。
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public IOToolsException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] エラーメッセージ,Throwableを指定してインスタンスを生成します。
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     * @param e Throwable
     */
    public IOToolsException(String s, Throwable e) {
        super(s, e);
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] Throwableを指定してインスタンスを生成します。
     * <br>[備  考]
     *
     * @param e Throwable
     */
    public IOToolsException(Throwable e) {
        super(e);
    }
}
