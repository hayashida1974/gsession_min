package jp.groupsession.v2.cmn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

/**
 * <br>[機  能] ログイン、ログアウト時に実行されるリスナー
 * <br>[解  説] DBのコミット、ロールバック処理は呼び出し元で行うので行わないこと。
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ILoginLogoutListener {

    /**
     * <br>[機  能] ログイン時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doLogin(Connection con, int usid) throws SQLException;

    /**
     * <br>[機  能] ログアウト時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param session セッション
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     */
    public void doLogout(HttpSession session, Connection con,
                             int usid, String domain) throws SQLException;

}
