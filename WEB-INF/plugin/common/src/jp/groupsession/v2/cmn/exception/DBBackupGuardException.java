package jp.groupsession.v2.cmn.exception;

import jp.co.sjts.util.jdbc.DBConnectionException;

/**
 * <br>[機  能] データがない場合にエラーとする場合の例外
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DBBackupGuardException extends DBConnectionException {
    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public DBBackupGuardException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public DBBackupGuardException(String s) {
        super(s);
    }

    /**
     * <br>[機  能] エラーメッセージ、Throwableをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     * @param e throwable
     */
    public DBBackupGuardException(String s, Throwable e) {
        super(s, e);
    }
}
