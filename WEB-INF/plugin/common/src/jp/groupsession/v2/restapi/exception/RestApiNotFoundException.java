package jp.groupsession.v2.restapi.exception;

/**
 * <br>[機  能] RESTAPI 存在しないAPIアクセス例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiNotFoundException extends RestApiException {
    /** HTTPステータスコード*/
    private final int status__ = 403;
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
    public RestApiNotFoundException() {
        super(RestApiNotFoundException.class.getName(), null);
        setReasonCode(EnumError.REQ_URL);
        setMessageResource(
                "error.notfound.resource.path");
    }


}
