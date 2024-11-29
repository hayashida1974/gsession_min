package jp.groupsession.v2.batch;

import java.sql.Connection;

/**
 * <br>[機  能] バックアップ処理時に実行されるリスナー
 * <br>[解  説]
 * <br>[備  考] Connectionのコミット、ロールバック処理は呼び出し元で行うので行わないこと。
 *
 * @author JTS
 */
public interface IBatchBackupListener {

    /**
     * <p>バックアップ処理の前に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ前処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ前処理実行時例外
     */
    public void doBeforeBackup(Connection con, Object param, String domain) throws Exception;

    /**
     * <p>バックアップ処理
     * @param con DBコネクション
     * @param param バックアップ処理時に使用する情報
     * @throws Exception バックアップ処理実行時例外
     */
    public void doBackup(Connection con, Object param) throws Exception;

    /**
     * <p>バックアップ処理終了後に実行されるJob
     * @param con DBコネクション
     * @param param バックアップ終了後処理で使用する情報
     * @param domain ドメイン
     * @throws Exception バックアップ終了後処理実行時例外
     */
    public void doAfterBackup(Connection con, Object param, String domain) throws Exception;
}
