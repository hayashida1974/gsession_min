package jp.co.sjts.util.mail;

/**
 * <br>[機 能] メール送信の際、例外が発生した場合throwされる例外
 * <br>[解 説]
 * <br>[備 考]
 * @author JTS
 */
public class MailSendException extends Exception {

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     * <P>メール送信時の例外発生時に生成されます。
     */
    public MailSendException() {
        super();
    }
    /**
     * @param message エラーメッセージ
     */
    public MailSendException(String message) {
        super(message);
    }
    /**
     * @param message エラーメッセージ
     * @param cause Throwable
     */
    public MailSendException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * @param cause Throwable
     */
    public MailSendException(Throwable cause) {
        super(cause);
    }
}