package jp.groupsession.v2.cmn.background;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;

/**
 * <br>[機  能] GroupSessionで実行するバッチ処理を実装したクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsBatch implements Runnable {

    /** 状態 停止中 */
    public static final int STATUS_STOP = 0;
    /** 状態 実行中 */
    public static final int STATUS_EXECUTE = 1;
    /** バッチ処理再試行件数 */
    private static final int RECEIVE_RETRY_COUNT = 5;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GsBatch.class);
    /** スレッド起動件数 */
    private static AtomicInteger threadCount__ = null;
    /** バッチ処理中ドメイン */
    private static Vector<String> executeDomain__ = null;

    /** 状態 */
    private int status__ = STATUS_STOP;
    /** JOBクラス */
    private IGsBatch iGsJob__ = null;
    /** ドメイン */
    private String dsKey__ = null;
    /** PluginConfig */
    private PluginConfig pluginConfig__ = null;
    /** 実行開始時間 */
    private long time__ = 0;

    static {
        threadCount__ = new AtomicInteger(0);
        executeDomain__ = new Vector<String>();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsJob 実行するJOBクラス
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @param time 実行開始時間
     */
    public GsBatch(Object gsJob, String dsKey, PluginConfig pluginConfig, long time) {
        iGsJob__ = (IGsBatch) gsJob;
        dsKey__ = dsKey;
        pluginConfig__ = pluginConfig;
        time__ = time;
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {
        ThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, dsKey__);

        log__.info("********** GsBatch start | " + time__);

        try {
            addThreadCount();
            setStatus(STATUS_EXECUTE);
            log__.info("実行中ドメイン : " + dsKey__ + " | " + time__);
            try {
                doBatch(dsKey__);
            } catch (Throwable e) {
                log__.error("Exception", e);
            }
            log__.info("実行ドメイン : " + dsKey__ + " end" + " | " + time__);

        } catch (Throwable e) {
            log__.error("Exception", e);
        } finally {
            log__.info("スレッド数1: " + threadCount__ + " | " + time__);

            __subtractThreadCount();
            setStatus(STATUS_STOP);
        }
        log__.info("********** GsBatch end | " + time__);
        ThreadContext.remove(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN);
    }

    /**
     * <br>[機  能] バッチ処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @throws Throwable バッチ処理に失敗
     */
    private void doBatch(String domain)
    throws Throwable {
        int tryCount = 0;
        while (isExecuteDomain(domain)) {
            if (tryCount > RECEIVE_RETRY_COUNT) {
                return;
            }

            synchronized (this) {
                this.wait(3000);
                tryCount++;
            }
        }

        try {
            addReceiveAccount(domain);
            __doBatch(domain);
        } catch (Throwable e) {
            log__.error("エラーアカウント = " + domain);
            throw e;
        } finally {
            removeExecuteDomain(domain);
        }
    }

    /**
     * <br>[機  能] バッチ処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @throws Throwable バッチ処理に失敗
     */
    private void __doBatch(String domain)
    throws Throwable {
        //バッチ処理
        iGsJob__.executeBatch(domain, pluginConfig__);
    }

    /**
     * <br>[機  能] 指定したドメインがバッチ処理中かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return true:バッチ処理中 false:バッチ処理未実施
     */
    public static boolean isExecuteDomain(String domain) {
        boolean execute = false;
        synchronized (executeDomain__) {
            execute = executeDomain__.indexOf(domain) >= 0;
        }
        return execute;
    }

    /**
     * <br>[機  能] 指定したドメインがバッチ処理中として設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     */
    public void addReceiveAccount(String domain) {
        synchronized (executeDomain__) {
            executeDomain__.add(domain);
        }
    }

    /**
     * <br>[機  能] 指定したドメインをバッチ処理中ドメインから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     */
    public synchronized void removeExecuteDomain(String domain) {
        synchronized (executeDomain__) {
            executeDomain__.remove(new String(domain));
        }
    }

    /**
     * <br>[機  能] 起動スレッド件数を+1する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void addThreadCount() {
        threadCount__.incrementAndGet();
    }

    /**
     * <br>[機  能] 起動スレッド件数を-1する
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __subtractThreadCount() {
        if (threadCount__.intValue() > 0) {
            threadCount__.decrementAndGet();
        }
    }

    /**
     * <br>[機  能] 起動スレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 起動スレッド件数
     */
    public static int getThreadCount() {
        return threadCount__.intValue();
    }

    /**
     * <p>status を取得します。
     * @return status
     */
    public int getStatus() {
        return status__;
    }

    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(int status) {
        status__ = status;
    }
}
