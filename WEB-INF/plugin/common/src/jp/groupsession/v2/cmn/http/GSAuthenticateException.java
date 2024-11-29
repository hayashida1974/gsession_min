package jp.groupsession.v2.cmn.http;

import jp.groupsession.v2.cmn.GSException;

/**
 * <br>[機  能] 認証エラー発生時にスローされる
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSAuthenticateException extends GSException {

    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public GSAuthenticateException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public GSAuthenticateException(String s) {
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
    public GSAuthenticateException(String s, Throwable e) {
        super(s, e);
    }

    /**
     * <br>[機  能] Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param e throwable
     */
    public GSAuthenticateException(Throwable e) {
        super(e);
    }

}
