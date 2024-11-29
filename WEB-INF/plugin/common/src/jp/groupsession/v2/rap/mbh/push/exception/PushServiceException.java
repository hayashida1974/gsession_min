package jp.groupsession.v2.rap.mbh.push.exception;

/**
 *
 * <br>[機  能] プッシュ通知サービス実行時例外
 * <br>[解  説]
 *  jp.groupsession.v2.rap.mbh.push.PushServiceOperator.doOperation(IPushOperationConsumer)
 *  の関数内で発生する例外をラップする
 * <br>[備  考]
 *
 * @author JTS
 */
public class PushServiceException extends Exception {

    /**
     *
     * コンストラクタ
     */
    public PushServiceException() {
        super();
    }
    /**
     *
     * コンストラクタ
     * @param message エラーメッセージ
     */
    public PushServiceException(String message) {
        super(message);
    }
    /**
     *
     * コンストラクタ
     * @param message エラーメッセージ
     * @param e 例外
     */
    public PushServiceException(String message, Throwable e) {
        super(message, e);

    }

}
