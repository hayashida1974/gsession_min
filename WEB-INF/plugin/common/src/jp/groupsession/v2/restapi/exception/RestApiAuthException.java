package jp.groupsession.v2.restapi.exception;
/**
 *
 * <br>[機  能] RESTAPI ユーザ認証失敗時例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiAuthException extends RestApiException {
    /** HTTPステータスコード*/
    private final int status__ = 401;
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
    public RestApiAuthException() {
        super(RestApiAuthException.class.getName(), null);
    }


}
