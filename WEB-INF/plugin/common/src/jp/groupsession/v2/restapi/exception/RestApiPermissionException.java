package jp.groupsession.v2.restapi.exception;

/**
 * <br>[機  能] RESTAPI アクセス権限例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiPermissionException extends RestApiException {
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
     * @param reasonCode 理由コード
     * @param messageKey メッセージキー
     * @param values メッセージ挿入オブジェクト
     */
    public RestApiPermissionException(
            IReasonCode reasonCode,
            String messageKey,
            Object... values) {
        super(RestApiPermissionException.class.getName(), null);
        setReasonCode(reasonCode);
        setMessageResource(
                messageKey, values);
    }


}
