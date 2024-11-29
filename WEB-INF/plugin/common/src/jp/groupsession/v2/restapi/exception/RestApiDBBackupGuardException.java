package jp.groupsession.v2.restapi.exception;
/**
 *
 * <br>[機  能] RESTAPI バックアップによるDB接続例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiDBBackupGuardException extends RestApiException {
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
    public RestApiDBBackupGuardException() {
        super(RestApiDBBackupGuardException.class.getName(), null);
        setReasonCode(EnumError.SYS_DB_BACKUP);
        setMessageResource(
                "error.backing.up");
    }

}
