package jp.groupsession.v2.wml.batch;

import java.sql.Connection;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] メール受信スレッド管理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlReceiveMasterThread extends Thread {

    /** スレッド名称 */
    public static final String THREAD_NAME = "GroupSession-ReceiveThread";
    /** 受信スレッド名称 */
    public static final String THREAD_NAME_RECEIVE = "GroupSession-ReceiveThread-";
    /** スレッド実行数最大件数 */
    public static final int THREAD_MAXCOUNT = 10;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlReceiveMasterThread.class);

    /** スレッドを起動しているか */
    private boolean start__ = false;

    /** GroupSession基本情報 */
    private GSContext gscontext__ = null;
    /** 受信対象アカウントSID */
    private int[] wacSid__ = null;
    /** 受信処理中 */
    private boolean receive__ = false;
    /** MessageResources */
    private MessageResources msgResource__ = null;
    /** ドメイン */
    private String domain__ = null;

    /** インスタンス */
    private static Map<String, WmlReceiveMasterThread> masterThreadMap__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    private WmlReceiveMasterThread() {
    }

    /**
     * <br>[機  能] メール受信スレッド管理インスタンスを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return メール受信スレッド管理インスタンス
     */
    public static synchronized WmlReceiveMasterThread getInstance(String domain) {
        if (masterThreadMap__ == null) {
            masterThreadMap__ = new ConcurrentHashMap<String, WmlReceiveMasterThread>();
        }

        WmlReceiveMasterThread wmlThread = masterThreadMap__.get(domain);
        if (wmlThread == null) {
            wmlThread = new WmlReceiveMasterThread();
            wmlThread.setName(THREAD_NAME);
            wmlThread.setPriority(Thread.MIN_PRIORITY);
            wmlThread.setDomain(domain);
            masterThreadMap__.put(domain, wmlThread);
        }
        return wmlThread;
    }

    /**
     * <br>[機  能] スレッドの実行処理
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {
        UDate now = new UDate();
        long time = now.getTimeMillis();
        String startTime =  UDateUtil.getSlashYYMD(now)
                                        + " " + UDateUtil.getSeparateHMS(now);
        now = null;
        log__.info("********** WmlReceiveMasterThread start (Start: " + startTime + ")");
        WmlBiz wmlBiz = new WmlBiz();
        Connection lockCon = null;

        //スレッド名の設定
        Thread.currentThread().setName("WmlRecv-" + System.currentTimeMillis()
                                        + "-" + domain__
                                        + "-" + Thread.currentThread().getId());

        //メール自動受信開始ログを出力
        try {
            lockCon = GroupSession.getConnection(domain__, 1000);
            wmlBiz.outPutBatchLog(lockCon,
                    domain__,
                    "メール自動受信開始",
                    getClass().getName(),
                    "WEBメール バッチ処理",
                    GSConstLog.LEVEL_TRACE,
                    null,
                    "auto receive start");
        } catch (Throwable e) {
            log__.error("オペレーションログの出力に失敗", e);
        } finally {
            JDBCUtil.closeConnection(lockCon);
            lockCon = null;
        }

        start__ = true;
        while (start__) {
            try {
                if (__isReceive()) {
                    log__.info("前回のメール自動受信処理完了まで待機");
                    sleep(10000);
                    continue;
                }

                __startReceive();
                int maxThreadCount = WmlBiz.getMaxReceiveThreadCount(
                                        (String) gscontext__.get(GSContext.APP_ROOT_PATH));

                lockCon = GroupSession.getConnection(domain__, 1000);

                //メール受信スレッドを開始する
                Vector<Integer> finishAccount = new Vector<Integer>(wacSid__.length);
                synchronized (finishAccount) {
                    int accountCnt = 1;
                    while (finishAccount.size() < wacSid__.length) {
                        for (int accountSid : wacSid__) {
                            if (finishAccount.indexOf(accountSid) >= 0) {
                                continue;
                            }

                            if (maxThreadCount > 0) {
                                while (WmlReceiveBatch.getThreadCount(domain__) >= maxThreadCount) {
                                    sleep(5000);
                                    log__.info("maxThreadCount = " + maxThreadCount);
                                    log__.info("WmlReceiveBatch.getThreadCount() = "
                                                + WmlReceiveBatch.getThreadCount(domain__));
                                    continue;
                                }
                            }

                            if (!WmlReceiveBatch.isReceiveAccount(lockCon, domain__, accountSid)) {
                                log__.info(domain__ + ":自動受信アカウント = " + accountSid);
                                log__.info("スレッド数1 = " + WmlReceiveBatch.getThreadCount(domain__));
                                WmlReceiveBatch batch
                                    = new WmlReceiveBatch(
                                            gscontext__, accountSid, msgResource__, domain__);
                                log__.info("スレッド数2 = " + WmlReceiveBatch.getThreadCount(domain__));
                                Thread thread = new Thread(batch);
                                thread.setName(THREAD_NAME_RECEIVE + accountSid);
                                log__.info("スレッド数3 = " + WmlReceiveBatch.getThreadCount(domain__));
                                thread.start();
                                if (batch.getStatus() == WmlReceiveBatch.STATUS_STOP) {
                                    log__.debug("受信スレッド開始まで待機 : " + accountSid);
                                    sleep(100);
                                }
                                log__.info("スレッド数4 = " + WmlReceiveBatch.getThreadCount(domain__));
                                log__.debug(domain__ + ":自動受信アカウント 実行開始件数 = "
                                                + accountCnt++ + " / " + wacSid__.length);
                                finishAccount.add(accountSid);
                            }
                        }
                    }
                }

                JDBCUtil.closeConnection(lockCon);
                lockCon = null;

                //全てのスレッドが完了するまで待機する
                while (WmlReceiveBatch.getThreadCount(domain__) > 0) {
                    log__.info("全てのスレッドが完了するまで待機　スレッド数 = "
                            + WmlReceiveBatch.getThreadCount(domain__));
                    sleep(10000);
                    continue;
                }
            } catch (Throwable e) {
                log__.error("Exception", e);
            } finally {
                if (lockCon != null) {
                    JDBCUtil.closeConnection(lockCon);
                    lockCon = null;
                }
                __endReceive();
                start__ = false;
            }
        }

        masterThreadMap__.remove(getDomain());
        log__.info("********** WmlReceiveMasterThread end (Start: " + startTime + ")");

        //メール自動受信終了ログを出力
        try {
            time = System.currentTimeMillis() - time;
            time -= time % 1000;
            time = time / 1000;
            String receiveTime = time % 60 + "秒";
            if (time >= 60) {
                time -= time % 60;
                time = time / 60;
                receiveTime = time + "分" + receiveTime;
            }
            lockCon = GroupSession.getConnection(domain__, 1000);
            wmlBiz.outPutBatchLog(lockCon,
                    domain__,
                    "メール自動受信終了",
                    getClass().getName(),
                    "WEBメール バッチ処理",
                    GSConstLog.LEVEL_TRACE,
                    "実行時間: " + receiveTime,
                    "auto receive end");
        } catch (Throwable e) {
            log__.error("オペレーションログの出力に失敗", e);
        } finally {
            JDBCUtil.closeConnection(lockCon);
            lockCon = null;

            //スレッド名に"END-"を設定する
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());
        }

        startTime = null;
    }

    /**
     * <br>[機  能] メール受信処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param msgResource MessageResources
     * @throws Throwable メール受信中に例外/エラー発生
     */
    public synchronized void receiveMail(GSContext gsContext, int[] wacSid,
                                            MessageResources msgResource)
    throws Throwable {
        log__.debug("WEBメールマスタースレッド起動");
        if (!__isReceive()) {
            __setReceive(gsContext, wacSid, msgResource);
            log__.debug("WEBメールマスタースレッド 受信処理開始");
            start();
        } else {
            log__.debug("WEBメールマスタースレッド 受信処理中のため終了");
        }
        log__.debug("WEBメールマスタースレッド終了");
    }

    /**
     * <br>[機  能] スレッドを開始する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void start() {
        if (!start__) {
            super.start();
        } else {
            log__.debug("WEBメールマスタースレッド 既に起動中のため終了");
        }
    }

    /**
     * <br>[機  能] メール受信処理を開始する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param msgResource MessageResources
     */
    private synchronized void __setReceive(GSContext gsContext, int[] wacSid,
                                            MessageResources msgResource) {
        gscontext__ = gsContext;
        wacSid__ = wacSid;
        msgResource__ = msgResource;
    }

    /**
     * <br>[機  能] メール受信処理を開始する
     * <br>[解  説]
     * <br>[備  考]
     */
    private synchronized void __startReceive() {
        receive__ = true;
    }

    /**
     * <br>[機  能] メール受信処理を終了する
     * <br>[解  説]
     * <br>[備  考]
     */
    private synchronized void __endReceive() {
        wacSid__ = null;
        receive__ = false;
    }

    /**
     * <br>[機  能] メール受信処理を実行中かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true: メール受信処理実行中 false:メール受信処理なし
     */
    private synchronized boolean __isReceive() {
        return receive__;
    }

    /**
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }
    /**
     * @param domain 設定する domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
}
