package jp.groupsession.v2.restapi.exception;
/**
 *
 * <br>[機  能] RESTAPI DB接続例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiDBConnectException extends RestApiException {
    /** HTTPステータスコード*/
    private final int status__ = 503;
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
    public RestApiDBConnectException() {
        super(RestApiDBConnectException.class.getName(), null);
        setReasonCode(EnumError.SYS_DB_BUSY);
        setMessageResource(
                "error.busy.line");
    }

}
