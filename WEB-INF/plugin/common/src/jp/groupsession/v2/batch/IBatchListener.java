package jp.groupsession.v2.batch;

import java.sql.Connection;

/**
 * <br>[機  能] 日次バッチ起動時に実行されるリスナー
 * <br>[解  説]
 * <br>[備  考] Connectionのコミット、ロールバック処理は実装側で行うこと
 *
 * @author JTS
 */
public interface IBatchListener {

    /**
     * <p>日次バッチ起動時に実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception;

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception;

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception;
}
