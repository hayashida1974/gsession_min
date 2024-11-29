package jp.groupsession.v2.restapi.exception;
/**
 *
 * <br>[機  能] 意図しない例外
 * <br>[解  説] 実装の不備による意図しない挙動による例外
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiRuntimeException extends RestApiException {
    /**
     *
     * コンストラクタ
     * @param message エラーメッセージ
     * @param trace トレース例外
     */
    public RestApiRuntimeException(String message, Throwable trace) {
        super(message, trace);
    }

}
