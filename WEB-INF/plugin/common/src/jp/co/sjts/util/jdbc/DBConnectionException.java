package jp.co.sjts.util.jdbc;

import java.sql.SQLException;

/**
 * <br>[機  能] データベースコネクションの取得失敗時にスローされる例外です。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DBConnectionException extends SQLException {
    /**
     * <br>[機  能] 空のException
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public DBConnectionException() {
        super();
    }

    /**
     * <br>[機  能] エラーメッセージをセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param s エラーメッセージ
     */
    public DBConnectionException(String s) {
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
    public DBConnectionException(String s, Throwable e) {
        super(s, e);
    }
}
