package jp.groupsession.v2.dba.gen;

/**
 * <br>[機  能] 生成時の例外
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class GenerateException extends Exception {

    /**
     *
     */
    public GenerateException() {
        super();
    }

    /**
     * @param message エラーメッセージ
     */
    public GenerateException(String message) {
        super(message);
    }

    /**
     * @param cause 例外オブジェクト
     */
    public GenerateException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message メッセージ
     * @param cause 例外オブジェクト
     */
    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

}
