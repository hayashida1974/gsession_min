package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <br>[機  能] ショートメールが未読、既読になった時に実行されるリスナー
 * <br>[解  説] DBのコミット、ロールバック処理は呼び出し元で行うので行わないこと。
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ISMailListener {

    /**
     * <br>[機  能] ショートメールが未読になった(送信された)時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param smlSid メールSID
     * @param sacSid アカウントSID
     * @param usid 受信ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doSmailUnOpen(Connection con, int smlSid, int usid, int sacSid) throws SQLException;

    /**
     * <br>[機  能] ショートメールが既読になった時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param smlSid メールSID
     * @param sacSid アカウントSID
     * @param usid 受信ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doSmailOpen(Connection con, int smlSid, int usid, int sacSid) throws SQLException;

}