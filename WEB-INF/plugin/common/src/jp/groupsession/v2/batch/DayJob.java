package jp.groupsession.v2.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.background.BatchMasterThread;
import jp.groupsession.v2.cmn.background.IGsBatch;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DataUsedSizeBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.quartz.AbstractJob;
import jp.groupsession.v2.cmn.quartz.JobException;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.man.ManBatchBackupListenerImpl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 一日おきに実行されるJOB
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DayJob extends AbstractJob implements IGsBatch {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(DayJob.class);

    /** バッチ プログラム名 */
    private static final String PG_NAME = "jp.groupsession.v2.batch.DayJob";

    /** 日次バッチ開始日時 */
    private final UDate startTime__ = new UDate();

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public DayJob() { }

    /**
     * <p>Jobの実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void execute(PluginConfig pluginConfig) throws JobException,
            SQLException {
        log__.debug("START");
        BatchMasterThread bachMaster = new BatchMasterThread(this, pluginConfig);
        bachMaster.run();
        log__.debug("END");
    }

    /**
     * <p>Jobの実行
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void executeBatch(String dsKey, PluginConfig pluginConfig)
                               throws JobException, SQLException {

        __executeBatch(dsKey, pluginConfig, GSConst.NOT_DO_BATCH);
    }

    /**
     * <br>[機  能] バッチ処理強制実行
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void executeBatchManual(String dsKey, PluginConfig pluginConfig)
                               throws JobException, SQLException {

        __executeBatch(dsKey, pluginConfig, GSConst.DO_BATCH);
    }
    /**
    *
    * <br>[機  能] バックアップの判定
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param now 現在時間
    * @return 判定結果
    * @throws SQLException SQL実行時例外
    */
    private boolean __checkExecBackup(Connection con,
            UDate now) throws SQLException {
        //バックアップ設定を取得
        CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
        CmnBackupConfModel backConfMdl = backConfDao.select();

        if (backConfMdl == null) {
            log__.info("バックアップ設定が未登録の為、自動バックアップを行わない");
            return false;
        }

        int backupInterval = backConfMdl.getBucInterval();
        if (backupInterval == GSConstMain.BUCCONF_INTERVAL_NOSET) {
            log__.info("バックアップ設定.間隔が[未設定]の為、自動バックアップを行わない");
            return false;

        } else if (backupInterval == GSConstMain.BUCCONF_INTERVAL_WEEKLY) {
            if (now.getWeek() != backConfMdl.getBucDow()) {
                return false;
            }

        } else if (backupInterval == GSConstMain.BUCCONF_INTERVAL_MONTHLY) {
            if (now.getWeek() != backConfMdl.getBucDow()) {
                int wkWeekOfMonth
                    = MaintenanceUtil.getAccurateWeekOfMonth(now,
                                                        backConfMdl.getBucWeekMonth());
                if (wkWeekOfMonth != backConfMdl.getBucWeekMonth()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     * <br>[機  能] 実行時間の判定(実行予定時間 = 現在時間)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param now 現在時間
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    private boolean __checkExecDayBatch(Connection con, UDate now)
            throws SQLException {
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();

        int hour = -1;
        if (batMdl != null) {
            hour = batMdl.getBatFrDate();
        }
        return hour == now.getIntHour();
    }
    /**
     * <br>[機  能] バッチ処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @param doBatchFlg 強制実行フラグ 0:強制実行しない 1:強制実行する
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     *
     */
    private void __executeBatch(String dsKey, PluginConfig pluginConfig, int doBatchFlg)
            throws JobException, SQLException {
        Connection con = null;

        UDate now = new UDate();
        log__.info("(" + dsKey + ")" + "バッチジョブ開始します : "
                + now.getStrHour() + ":" + now.getStrMinute() + ":" + now.getStrSecond());

        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);
        boolean execBatch = false;
        boolean finishBatch = false;
        String listenerName = null;

        long dayBatchTime = System.currentTimeMillis();
        Throwable logException = null;
        try {

            con = GroupSession.getConnection(dsKey);

            if (con == null) {
                return;
            }
            //実行時間の判定(実行予定時間 = 現在時間)
            log__.info("startTime = " + UDateUtil.getSlashYYMD(startTime__)
                        + UDateUtil.getSeparateHMS(startTime__));
            if (doBatchFlg != GSConst.DO_BATCH && !__checkExecDayBatch(con, startTime__)) {
                return;
            }
            GSContext gscontext = getGscontext();
            //開始ログ出力
            GsMessage gsMsg = new GsMessage();
            MessageResources msgRes = (MessageResources) gscontext.get(GSContext.MSG_RESOURCE);
            GsMessage.setMessageResources(msgRes);

            String logValue = "";
            if (CommonBiz.isMultiAP() && CommonBiz.getApNumber() > 0) {
                logValue = "AP" + CommonBiz.getApNumber();
            }
            outPutStartLog(con, dsKey, "", logValue);
            dayBatchTime = System.currentTimeMillis();

            execBatch = true;
            if (h2db) {
                __setBatchQueryTimeout(con);
            }

            CommonBiz cmnBiz = new CommonBiz();


            if (__checkExecBackup(con, now)) {
                //バックアップバッチを実行
                setBackup(true);
                IBatchBackupListener[] backupBatchListeners
                    = cmnBiz.getBackupBatchListeners(pluginConfig, con);
                if (backupBatchListeners.length > 0) {
                    //プラグインバッチ開始ログ
                    outPutStartLog(con, dsKey, gsMsg.getMessage("main.man002.45", null), logValue);
                    long backupBatchTime = System.currentTimeMillis();

                    boolean finishBackup = false;
                    try {
                        for (IBatchBackupListener listener : backupBatchListeners) {
                            listenerName = listener.getClass().getName();
                            listener.doBeforeBackup(con, gscontext, dsKey);
                            listenerName = null;
                        }
                        log__.debug("Backup日次バッチ:事前処理完了");

                        for (IBatchBackupListener listener : backupBatchListeners) {
                            try {
                                listenerName = listener.getClass().getName();
                                listener.doBackup(con, gscontext);
                                log__.debug("Backup日次バッチ:doBackup "
                                        + listenerName);
                                listenerName = null;
                            } catch (Exception e) {
                                log__.fatal("Backup日次バッチ処理の実行に失敗 : "
                                        + NullDefault.getString(listenerName, ""), e);
                                throw e;
                            } finally {
                                if (listener instanceof ManBatchBackupListenerImpl) {
                                    JDBCUtil.closeConnection(con);
                                    con = null;
                                    con = __resetDBConnection(dsKey);
                                    if (h2db) {
                                        __setBatchQueryTimeout(con);
                                    }
                                }
                            }
                        }
                        finishBackup = true;
                    } finally {
                        setBackup(false);
                        for (IBatchBackupListener listener : backupBatchListeners) {
                            try {
                                listener.doAfterBackup(con, gscontext, dsKey);
                            } catch (Exception e) {
                                log__.error("バックアップ処理終了後で例外発生", e);
                            }
                        }
                        log__.debug("Backup日次バッチ:事後処理完了");
                        //バックアップバッチ終了ログ
                        if (finishBackup) {
                            outPutFinishLog(con, dsKey,
                                    gsMsg.getMessage("main.man002.45", null),
                                    backupBatchTime);
                        } else {
                            outPutFailedLog(con, dsKey,
                                    gsMsg.getMessage("main.man002.45", null), logException);
                        }
                    }
                }
                listenerName = null;
                if (isBackup()) {
                    setBackup(false);
                }
            }
            //バッチを実行
            IBatchListener[] batchListeners
                = cmnBiz.getBatchListeners(pluginConfig, con);
            IBatchModel ibm = new IBatchModel();
            ibm.setGsContext(gscontext);
            ibm.setDomain(dsKey);
            boolean batcheAllFinish = true;
            for (IBatchListener listener : batchListeners) {
                listenerName = listener.getClass().getName();
                log__.debug("日次バッチ実行対象" + listener.getClass().getName());
                try {
                    listener.doDayBatch(con, ibm);
                } catch (Exception e) {
                    log__.error("日次バッチ処理の実行に失敗 : "
                            + NullDefault.getString(listenerName, ""), e);
                    batcheAllFinish = false;
                }
                listenerName = null;
            }

            if (batcheAllFinish) {
                //ファイル実体物理削除

                //アプリケーションのルートパスを取得
                String rootPath = "";
                Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);
                if (pathObj != null) {
                    rootPath = (String) pathObj;
                }
                con.setAutoCommit(false);

                CommonBiz biz = new CommonBiz();
                biz.deleteAllLogicalDeletedBinInf(con, rootPath);

                con.commit();

            }

            //データ使用量の更新
            if (!StringUtil.isNullZeroString(dsKey)
            && !dsKey.equals(GSConst.GS_DOMAIN)) {
                DataUsedSizeBiz usedSizeBiz = new DataUsedSizeBiz(con);
                long totalSize = usedSizeBiz.getTotalByteSize();
                int usedDataSize = Math.round(totalSize / 1048576);
                GroupSession.getResourceManager().updateDbUse(dsKey, usedDataSize);
            }

            finishBatch = true;
        } catch (ClassNotFoundException e) {
            log__.fatal("指定された日次バッチ実装クラスが存在しない : "
                    + NullDefault.getString(listenerName, ""), e);
            throw new JobException("指定された日次バッチ実装クラスが存在しない : "
                    + NullDefault.getString(listenerName, ""), e);
        } catch (IllegalAccessException e) {
            log__.fatal("指定された日次バッチ実装クラスの取得に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
            throw new JobException("指定された日次バッチ実装クラスの取得に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
        } catch (InstantiationException e) {
            log__.fatal("指定された日次バッチ実装クラスの取得に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
            throw new JobException("指定された日次バッチ実装クラスの取得に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
        } catch (Exception e) {
            log__.fatal("日次バッチ処理の実行に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
            //throw new JobException("日次バッチ処理の実行に失敗", e);
            throw new JobException("日次バッチ処理の実行に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
        } catch (Throwable e) {
            log__.fatal("日次バッチ処理の実行に失敗: "
                    + NullDefault.getString(listenerName, ""), e);
            throw new JobException("日次バッチ処理の実行に失敗 : "
                    + NullDefault.getString(listenerName, ""), e);
        } finally {
            try {
                if (execBatch && con != null && !con.isClosed()) {
                    if (h2db) {
                        __setNormalQueryTimeout(con);
                    }

                    //ログ出力
                    if (finishBatch) {
                        outPutFinishLog(con, dsKey, "", dayBatchTime);
                    } else {
                        outPutFailedLog(con, dsKey, "", logException);
                    }
                }
            } catch (Exception e) {
                throw new JobException("日次バッチの終了処理に失敗 ", e);
            } finally {
                JDBCUtil.closeConnection(con);
                setBackup(false);
            }
        }
    }

    /**
    /**
     * <br>[機  能] QUERY_TIMEOUT を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param queryTimeout QUERY_TIMEOUT
     * @throws SQLException SQL実行時例外
     */
    private void __setQueryTimeout(Connection con, int queryTimeout) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("set QUERY_TIMEOUT ?;");
            sql.addIntValue(queryTimeout);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] QUERY_TIMEOUTにQUERY_TIMEOUT_BATCHを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @throws SQLException SQL実行時例外
     */
    private void __setBatchQueryTimeout(Connection con) throws SQLException {
        int queryTimeout = 0;
        ResourceBundle optionResource = ResourceBundle.getBundle("connectOption");
        Enumeration<String> keys = optionResource.getKeys();
        boolean undefined = true;
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.toUpperCase().equals("QUERY_TIMEOUT_BATCH")) {
                undefined = false;
                queryTimeout = Integer.parseInt(optionResource.getString(key));
                break;
            }
        }
        if (undefined) {
            queryTimeout = 1800000;
        }
        __setQueryTimeout(con, queryTimeout);

    }

    /**
     * <br>[機  能] 標準のQUERY_TIMEOUTを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @throws SQLException SQL実行時例外
     */
    private void __setNormalQueryTimeout(Connection con) throws SQLException {
        int queryTimeout = 0;
        ResourceBundle optionResource = ResourceBundle.getBundle("connectOption");
        Enumeration<String> keys = optionResource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.toUpperCase().equals("QUERY_TIMEOUT")) {
                queryTimeout = Integer.parseInt(optionResource.getString(key));
                break;
            }
        }
        __setQueryTimeout(con, queryTimeout);
    }
    /**
     *
     * <br>[機  能] データソース、コンテキストの再設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 再生成後のコネクション
     * @throws Exception 例外発生
     */
    private Connection __resetDBConnection(String dsKey) throws Exception {
        Connection con = null;
        try {
            Thread.sleep(3000);
            //データソース、コンテキストの再設定を行う
            //DS
            GroupSession.getResourceManager().resetDataSource(dsKey);
            log__.debug("Backup日次バッチ:DataSource 再作成");
            //採番
            GroupSession.getResourceManager()
                .resetCountController(dsKey);
            log__.debug("Backup日次バッチ:採番コントローラ 再作成(SAIBAN)");

            con = GroupSession.getConnection(dsKey, false);
        } catch (Exception e) {
            log__.error("Backup日次バッチ:コネクション再生成失敗", e);
            throw e;
        }
        return con;
    }
    /**
     *
     * <br>[機  能] 日時バッチ開始ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dsKey ドメイン
     * @param header 日時バッチ種類名
     * @param logValue 出力内容
     */
    public static void outPutStartLog(Connection con,
            String dsKey, String header, String logValue) {
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();

        String logMessage = header + gsMsg.getMessage("cmn.start", null);
        cmnBiz.outPutBatchLog(PG_NAME, con, logMessage,
                            GSConstLog.LEVEL_INFO, logValue, dsKey);

    }
    /**
     *
     * <br>[機  能] 日時バッチ終了ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dsKey ドメイン
     * @param header 日時バッチ種類名
     * @param startTime 開始時間
     */
    public static void outPutFinishLog(Connection con,
            String dsKey, String header, long startTime) {
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String logMessage = header + gsMsg.getMessage("cmn.end", null);
        String receiveTime = cmnBiz.getExecBatchTimeString(startTime);
        String logValue = "実行時間: " + receiveTime;
        cmnBiz.outPutBatchLog(PG_NAME, con, logMessage,
                            GSConstLog.LEVEL_INFO, logValue, dsKey);
    }
    /**
     *
     * <br>[機  能] 日時バッチ失敗ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dsKey ドメイン
     * @param header 日時バッチ種類名
     * @param e 例外クラス
     */
    public static void outPutFailedLog(Connection con, String dsKey, String header, Throwable e) {
        CommonBiz cmnBiz = new CommonBiz();
        String logMessage = header + "失敗";
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            sb.append(e.getClass().getName());
            sb.append(":");
            sb.append(e.getMessage());
//            sb.append("\n");
//            for (StackTraceElement element : e.getStackTrace()) {
//                sb.append(element.toString());
//                sb.append("\n");
//            }
        }
        cmnBiz.outPutBatchLog(PG_NAME, con, logMessage,
                            GSConstLog.LEVEL_INFO, sb.toString(), dsKey);
    }
}
