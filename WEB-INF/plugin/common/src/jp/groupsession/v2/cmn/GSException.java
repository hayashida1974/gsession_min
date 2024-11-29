package jp.groupsession.v2.cmn;

import javax.servlet.ServletException;

/**
 * <br>[機  能] GroupSession汎用Exceptionクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSException extends ServletException {
    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public GSException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public GSException(String s) {
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
    public GSException(String s, Throwable e) {
        super(s, e);
    }

    /**
     * <br>[機  能] Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param e throwable
     */
    public GSException(Throwable e) {
        super(e);
    }
}
