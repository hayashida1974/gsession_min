package jp.co.sjts.util.encryption;

import javax.servlet.ServletException;

/**
 * <br>[機  能] 暗号化に関するする例外
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class EncryptionException extends ServletException {

    /**
     * <br>[機  能] 空のExceptionを生成します
     * <br>[解  説]
     * <br>[備  考]
     */
    public EncryptionException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param s エラーメッセージ
     */
    public EncryptionException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] エラーメッセージ、Throwableをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param s エラーメッセージ
     * @param e throwable
     */
    public EncryptionException(String s, Throwable e) {
        super(s, e);
    }
}
