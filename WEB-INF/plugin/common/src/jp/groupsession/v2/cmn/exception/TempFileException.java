package jp.groupsession.v2.cmn.exception;


/**
 * <br>[機  能] 添付ファイル操作時に発生する例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TempFileException extends Exception {
    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public TempFileException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public TempFileException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] エラーメッセージ、Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     * @param e throwable
     */
    public TempFileException(String s, Throwable e) {
        super(s, e);
    }

    /**
     * <br>[機  能] Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param e throwable
     */
    public TempFileException(Throwable e) {
        super(e);
    }
}
