package jp.groupsession.v2.cmn.quartz;

/**
 * <br>[機  能] Job実行時にエラーが発生した場合、throwする例外クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class JobException extends Exception {

    /**
     * コンストラクタ
     */
    public JobException() {
        super();
    }

    /**
     * コンストラクタ
     * @param message 例外メッセージ
     */
    public JobException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * @param message 例外メッセージ
     * @param cause Throwable
     */
    public JobException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * @param cause Throwable
     */
    public JobException(Throwable cause) {
        super(cause);
    }

}
