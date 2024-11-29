package jp.groupsession.v2.restapi.exception;

/**
 * <br>[機  能] RESTAPI 不正なメソッド指定による例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiInvalidMethodException extends RestApiException {
    /** HTTPステータスコード*/
    private final int status__ = 405;
    /**
     * <p>status を取得します。
     * @return status
     *
     */
    @Override
    public int getStatus() {
        return status__;
    }

    /**
     * コンストラクタ
     */
    public RestApiInvalidMethodException() {
        super(RestApiInvalidMethodException.class.getName(), null);
        setReasonCode(EnumError.REQ_METHOD);
        setMessageResource(
                "error.method.not.supported");
    }

}
