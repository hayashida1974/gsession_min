package jp.groupsession.v2.cmn.background;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.HourJob;
import jp.groupsession.v2.batch.M5Job;
import jp.groupsession.v2.cmn.GSDBConCntLogger;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] バッチ処理に関するスレッドを管理するための機能を実装したクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BatchMasterThread extends Thread {

    /** スレッド名称 */
    public static final String THREAD_NAME = "GroupSession-BatchThread";
    /** バッチスレッド名称 */
    public static final String THREAD_NAME_RECEIVE = "GroupSession-BatchThread-";
    /** スレッド実行数最大件数 */
    public static final int THREAD_MAXCOUNT = 5;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BatchMasterThread.class);

    /** スレッドを起動しているか */
    private boolean start__ = false;
    /** バッチ処理実行中 */
    private boolean execute__ = false;

    /** JOBクラス */
    private IGsBatch iGsJob__ = null;
    /** PluginConfig */
    private PluginConfig pluginConfig__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsJob 実行するJOBクラス
     * @param pluginConfig プラグイン情報
     */
    public BatchMasterThread(Object gsJob, PluginConfig pluginConfig) {
        iGsJob__ = (IGsBatch) gsJob;
        pluginConfig__ = pluginConfig;
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {

        //スレッド名の設定
        Thread.currentThread().setName("MasterThread-" + System.currentTimeMillis()
                                        + "-" + Thread.currentThread().getId());

        String batchType = "Batch";
        try {
            if (iGsJob__ != null) {
                if (iGsJob__ instanceof M5Job) {
                    batchType = "M5";
                } else if (iGsJob__ instanceof HourJob) {
                    batchType = "Hour";
                } else if (iGsJob__ instanceof DayJob) {
                    batchType = "Day";
                }
            }
        } catch (NoClassDefFoundError e) {
            log__.error(e);
        }

        long time = System.currentTimeMillis();
        log__.info("********** BatchMastarThread start"
                + " | time = " + time
                + " | BatchType = " + batchType);

        String[] domain = null;
        try {
            domain = GroupSession.getResourceManager().getDomain();
        } catch (Exception e) {
          log__.error("ドメインの取得に失敗", e);
        }

        start__ = true;
        while (start__) {
            try {
                if (__isExecute()) {
                    log__.info("前回のバッチ処理完了まで待機");
                    sleep(10000);
                    continue;
                }

                __startExecute();

                //バッチ処理を開始する
                Vector<String> finishDomain = new Vector<String>(domain.length);
                synchronized (finishDomain) {
                    while (finishDomain.size() < domain.length) {
                        for (String dsKey : domain) {
                            log__.debug("バッチ登録ドメイン＝" + dsKey);

                            if (finishDomain.indexOf(dsKey) >= 0) {
                                continue;
                            }

                            if (THREAD_MAXCOUNT > 0) {
                                while (GsBatch.getThreadCount() >= THREAD_MAXCOUNT) {
                                    sleep(5000);
                                    log__.info("maxThreadCount = " + THREAD_MAXCOUNT
                                            + " | time = " + time
                                            + " | BatchType = " + batchType);

                                    log__.info("GsBatch.getThreadCount() = "
                                                + GsBatch.getThreadCount()
                                                + " | time = " + time
                                                + " | BatchType = " + batchType);

                                    continue;
                                }
                            }

                            if (!GsBatch.isExecuteDomain(dsKey)) {
                                GsBatch batch = new GsBatch(iGsJob__, dsKey, pluginConfig__, time);
                                Thread thread = new Thread(batch);
                                thread.setName(batchType
                                            + "-" + time
                                            + "-" + dsKey
                                            + "-" + thread.getId());
                                thread.start();
                                if (batch.getStatus() == GsBatch.STATUS_STOP) {
                                    sleep(100);
                                }
                                log__.info("スレッド数 = " + GsBatch.getThreadCount()
                                        + " | time = " + time
                                        + " | BatchType = " + batchType);

                                finishDomain.add(dsKey);
                            }
                        }
                    }
                }

                //全てのスレッドが完了するまで待機する
                while (GsBatch.getThreadCount() > 0) {
                    log__.info("全てのスレッドが完了するまで待機　スレッド数 = "
                                + GsBatch.getThreadCount()
                                + " | time = " + time
                                + " | BatchType = " + batchType);

                    for (String domainId : domain) {
                        if (GsBatch.isExecuteDomain(domainId)) {
                            log__.info("実行中ドメイン: " + domainId
                                    + " | time = " + time
                                    + " | BatchType = " + batchType);

                        }
                    }
                    sleep(10000);
                    continue;
                }
            } catch (Throwable e) {
                log__.error("Exception", e);
            } finally {
                __endExecute();
                start__ = false;

            }
        }
        //5分バッチの終了後、DBコネクション状態(使用率警告)をログに残す
        if (iGsJob__ instanceof M5Job) {
            GSDBConCntLogger.outputWarn();
        }

        log__.info("********** BatchMastarThread end"
                + " | time = " + time
                + " | BatchType = " + batchType);

        //スレッド名に"END-"を設定する
        Thread.currentThread().setName(
                "END-" + Thread.currentThread().getName());
    }

    /**
     * <br>[機  能] バッチ処理を開始する
     * <br>[解  説]
     * <br>[備  考]
     */
    private synchronized void __startExecute() {
        execute__ = true;
    }

    /**
     * <br>[機  能] バッチ処理をを終了する
     * <br>[解  説]
     * <br>[備  考]
     */
    private synchronized void __endExecute() {
        execute__ = false;
    }

    /**
     * <br>[機  能] メール受信処理を実行中かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true: メール受信処理実行中 false:メール受信処理なし
     */
    private synchronized boolean __isExecute() {
        return execute__;
    }
}
