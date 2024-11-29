package jp.groupsession.v2.wml.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvlockDao;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvlockModel;

/**
 * <br>[機  能] メール受信バッチ処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class WmlReceiveBatch implements Runnable {

    /** 状態 停止中 */
    public static final int STATUS_STOP = 0;
    /** 状態 受信中 */
    public static final int STATUS_RECEIVE = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlReceiveBatch.class);

    /** メール受信処理再試行件数 */
    private static final int RECEIVE_RETRY_COUNT = 5;
    /** 受信処理中アカウントMapping */
    private static Map<String, Vector<Integer>> receiveAccountMap__ = null;
    /** スレッド起動件数Mapping */
    private static Map<String, AtomicInteger> threadCountMap__ = null;

    /** 状態 */
    private int status__ = STATUS_STOP;
    /** GroupSession共通情報 */
    private GSContext gsContext__ = null;
    /** アカウントSID */
    private int[] wacSid__ = null;
    /** ドメイン */
    private String domain__ = null;
    /** MessageResources */
    private MessageResources msgResource__ = null;

    /** 実行ユーザSID */
    private int userSid__ = 0;

    static {
        receiveAccountMap__ = new ConcurrentHashMap<String, Vector<Integer>>();
        threadCountMap__ = new ConcurrentHashMap<String, AtomicInteger>();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    @SuppressWarnings("all")
    private WmlReceiveBatch() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param msgResource MessageResources
     * @param domain ドメイン
     */
    public WmlReceiveBatch(GSContext gsContext,
                           int wacSid,
                           MessageResources msgResource,
                           String domain) {
        gsContext__ = gsContext;
        wacSid__ = new int[]{wacSid};
        msgResource__ = msgResource;
        domain__ = domain;

        if (!receiveAccountMap__.containsKey(domain__)) {
            receiveAccountMap__.put(domain__.toString(), new Vector<Integer>(1));
        }
        if (!threadCountMap__.containsKey(domain__)) {
            threadCountMap__.put(domain__.toString(), new AtomicInteger(0));
        }
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param msgResource MessageResources
     * @param domain ドメイン
     * @param usreSid
     */
    public WmlReceiveBatch(GSContext gsContext,
                           int wacSid,
                           MessageResources msgResource,
                           String domain,
                           int userSid) {
        this(gsContext, wacSid, msgResource, domain);
        this.userSid__ = userSid;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param msgResource MessageResources
     * @param domain ドメイン
     */
    public WmlReceiveBatch(GSContext gsContext,
                           int[] wacSid,
                           MessageResources msgResource,
                           String domain) {
        gsContext__ = gsContext;
        wacSid__ = wacSid;
        msgResource__ = msgResource;
        domain__ = domain;

        if (!receiveAccountMap__.containsKey(domain__)) {
        if (wacSid__ == null || wacSid__.length <= 1) {
            receiveAccountMap__.put(domain__.toString(), new Vector<Integer>(1));
        } else {
            receiveAccountMap__.put(domain__.toString(), new Vector<Integer>(wacSid__.length));
        }
        }
        if (!threadCountMap__.containsKey(domain__)) {
            threadCountMap__.put(domain__.toString(), new AtomicInteger(0));
        }
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {
        log__.info("********** WmlReceiveBatch start");
        try {
            addThreadCount();
            setStatus(STATUS_RECEIVE);

            for (int accountSid : wacSid__) {
                log__.info(domain__ + ":実行中アカウント : " + accountSid);
                try {
                    receiveMail(gsContext__, accountSid, domain__);
                } catch (Throwable e) {
                    log__.error("Exception", e);
                }
                log__.info(domain__ + ":実行中アカウント : " + accountSid + " end");
            }
        } catch (Throwable e) {
            log__.error("Exception", e);
        } finally {
            log__.info("スレッド数1: " + getThreadCount(domain__));
            __subtractThreadCount();
            log__.info("スレッド数2: " + getThreadCount(domain__));
            setStatus(STATUS_STOP);
        }
        log__.info("********** WmlReceiveBatch end");

    }

    /**
     * <br>[機  能] メール受信処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param domain ドメイン
     * @throws Throwable メール受信中に例外/エラー発生
     */
    public void receiveMail(GSContext gsContext, int wacSid, String domain)
    throws Throwable {

        Connection lockCon = null;
        try {
            lockCon = __getLockConnection(domain);

            int tryCount = 0;
            while (isReceiveAccount(lockCon, domain, wacSid)) {
                if (tryCount > RECEIVE_RETRY_COUNT) {
                    return;
                }

                synchronized (this) {
                    this.wait(3000);
                    tryCount++;
                }
            }

            try {
                addReceiveAccount(lockCon, wacSid);
                JDBCUtil.closeConnection(lockCon);

                __doReceive(gsContext, wacSid, domain);
            } catch (Throwable e) {
                log__.error(domain__ + ":エラーアカウント = " + wacSid);
                throw e;
            } finally {
                if (lockCon.isClosed()) {
                    lockCon = null;
                    try {
                        lockCon = __getLockConnection(domain);
                    } catch (Throwable e) {
                        log__.error("コネクションの取得に失敗", e);
                    }
                }
                removeReceiveAccount(lockCon, wacSid);
            }
        } finally {
            JDBCUtil.closeConnection(lockCon);
            lockCon = null;
        }
    }

    /**
     * <br>[機  能] メールの受信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param domain ドメイン
     * @throws Throwable メールの受信に失敗
     */
    private void __doReceive(GSContext gsContext, int wacSid, String domain)
    throws Throwable {
        //アカウント情報を取得する
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.readNewMail(wacSid,
                GroupSession.getResourceManager().getCountController(domain),
                    userSid__,
                    (String) gsContext.get(GSContext.APP_ROOT_PATH),
                    __getTempDirPath(gsContext, wacSid, domain),
                    GroupSession.getResourceManager().getDataSource(domain),
                    msgResource__, domain);
    }

    /**
     * <br>[機  能] 指定したアカウントがメール受信処理中かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @return true:受信処理中 false:受信処理未実施
     * @throws Exception 実行時例外
     */
    public static boolean isReceiveAccount(Connection con, String domain, int wacSid)
    throws Exception {
        boolean receive = false;
        synchronized (getReceiveAccount(domain)) {
            receive = getReceiveAccount(domain).indexOf(wacSid) >= 0;
            if (!receive) {
                WmlAccountRcvlockDao accountLockDao = new WmlAccountRcvlockDao(con);
                receive = accountLockDao.checkLockExists(wacSid);
                accountLockDao = null;
            }
        }
        return receive;
    }

    /**
     * <br>[機  能] 指定したアカウントをメール受信処理中として設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void addReceiveAccount(Connection con, int wacSid)
    throws SQLException {
        synchronized (getReceiveAccount(domain__)) {
            getReceiveAccount(domain__).add(wacSid);

            boolean commit = false;
            try {
                WmlAccountRcvlockDao accountLockDao = new WmlAccountRcvlockDao(con);
                WmlAccountRcvlockModel accountLockModel = new WmlAccountRcvlockModel();
                accountLockModel.setWacSid(wacSid);
                accountLockModel.setWrlRcvedate(new UDate());
                accountLockDao.insert(accountLockModel);
                accountLockModel = null;
                accountLockDao = null;
                con.commit();
                commit = true;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }
    }

    /**
     * <br>[機  能] 指定したアカウントをメール受信処理中アカウントから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public synchronized void removeReceiveAccount(Connection con, int wacSid)
    throws SQLException {
        synchronized (getReceiveAccount(domain__)) {
            getReceiveAccount(domain__).remove(new Integer(wacSid));

            if (con == null) {
                return;
            }
            boolean commit = false;
            try {
                WmlAccountRcvlockDao accountLockDao = new WmlAccountRcvlockDao(con);
                accountLockDao.delete(wacSid);
                accountLockDao = null;
                con.commit();
                commit = true;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }
    }

    /**
     * <br>[機  能] 受信処理中アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 受信処理中アカウント
     */
    public static Vector<Integer> getReceiveAccount(String domain) {
        if (!receiveAccountMap__.containsKey(domain)) {
            receiveAccountMap__.put(domain.toString(), new Vector<Integer>());
        }
        return receiveAccountMap__.get(domain);
    }

    /**
     * <br>[機  能] アカウント_受信ロック操作用Connectionを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return コネクション
     * @throws SQLException コネクションの取得に失敗
     * @throws Exception DataSourceの取得に失敗
     */
    private Connection __getLockConnection(String domain)
    throws SQLException, Exception {

        return GroupSession.getConnection(domain, 1000L);
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

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param wacSid アカウントSID
     * @param domain ドメイン
     * @return テンポラリディレクトリパス
     */
    private String __getTempDirPath(GSContext gsContext, int wacSid, String domain) {

        String tempDir = GroupSession.getResourceManager().getTempPath(domain);
        tempDir += "/";
        tempDir += GSConstWebmail.PLUGIN_ID_WEBMAIL;
        tempDir += "/batch/";
        tempDir += wacSid;
        tempDir += "/";

        return IOTools.replaceFileSep(tempDir.toString());
    }

    /**
     * <br>[機  能] 起動スレッド件数を+1する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void addThreadCount() {
        threadCountMap__.get(domain__).incrementAndGet();
    }

    /**
     * <br>[機  能] 起動スレッド件数を-1する
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __subtractThreadCount() {
        if (threadCountMap__.get(domain__).get() > 0) {
            threadCountMap__.get(domain__).decrementAndGet();
        }
    }

    /**
     * <br>[機  能] 起動スレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 起動スレッド件数
     */
    public static int getThreadCount(String domain) {
        AtomicInteger threadCnt = threadCountMap__.get(domain);
        if (threadCnt == null) {
            return 0;
        }
        return threadCnt.get();
    }

}
