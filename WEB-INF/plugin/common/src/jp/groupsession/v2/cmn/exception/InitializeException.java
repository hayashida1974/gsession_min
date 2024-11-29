package jp.groupsession.v2.cmn.exception;

import javax.servlet.ServletException;

/**
 * <br>[機  能] Webアプリケーションの初期化時に発生する例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class InitializeException extends ServletException {
    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     */
    public InitializeException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public InitializeException(String s) {
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
    public InitializeException(String s, Throwable e) {
        super(s, e);
    }
}
