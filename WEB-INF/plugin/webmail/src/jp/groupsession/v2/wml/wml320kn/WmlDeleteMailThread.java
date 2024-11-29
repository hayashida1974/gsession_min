package jp.groupsession.v2.wml.wml320kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlDelmailListDao;

/**
 * <br>[機  能] WEBメール メール情報一括削除用クラス
 * <br>[解  説] 別スレッドで実行する
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class WmlDeleteMailThread extends Thread {

    /** 状態 停止中 */
    public static final int STATUS_STOP = 0;
    /** 状態 削除中 */
    public static final int STATUS_DELETE = 1;

    /** 開始ステータス 開始 */
    public static final int STARTSTATUS_START = 0;
    /** 開始ステータス 再開 */
    public static final int STARTSTATUS_RESTART = 1;

    /** 一括削除実行結果 完了 */
    public static final int RESULT_COMPLETE = 0;
    /** 一括削除実行結果 中断 */
    public static final int RESULT_STOP = 1;
    /** 一括削除実行結果 エラー */
    public static final int RESULT_ERROR = 2;


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDeleteMailThread.class);

    /** 削除処理中アカウントMapping */
    private static Map<String, CopyOnWriteArrayList<Integer>> deleteAccountMap__ = null;
    /** 待機中アカウントMapping */
    private static Map<String, CopyOnWriteArrayList<Integer>> suspendedAccountMap__ = null;

    /** 状態 */
    private int status__ = STATUS_STOP;
    /** アカウントSID */
    private int wacSid__ = 0;
    /** 実行ユーザSID */
    private int userSid__ = 0;
    /** ドメイン */
    private String domain__ = null;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** 開始ステータス */
    private int startStatus__ = 0;

    static {
        deleteAccountMap__ = new ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>>();
        suspendedAccountMap__ = new ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>>();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    @SuppressWarnings("all")
    private WmlDeleteMailThread() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param startStatus 開始ステータス
     */
    public WmlDeleteMailThread(
                            String domain,
                            int wacSid,
                            String appRootPath,
                            int startStatus) {
        domain__ = domain;
        wacSid__ = wacSid;
        appRootPath__ = appRootPath;
        startStatus__ = startStatus;

    }

    /**
     * <br>[機  能] メール情報一括削除スレッドを開始する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param startStatus 開始ステータス(0:開始 or 1:再開)
     */
    public static void startThread(String domain, int wacSid,
                                    String appRootPath,
                                    int startStatus) {
        __initMap(domain);

        try {
            //メール情報一括削除スレッドの件数が上限に達している場合
            //待機中スレッドを追加し、処理を終了する
            int maxThreadCnt = WmlBiz.getMaxDeleteThreadCount(appRootPath);
            if (getThreadCount(domain) >= maxThreadCnt) {
                addSuspendedAccount(domain, wacSid);
                return;
            }

            //メール一括削除実行中アカウントに指定アカウントを追加し、
            //スレッドの起動を行う
            addDeleteAccount(domain, wacSid);

            WmlDeleteMailThread thread
                = new WmlDeleteMailThread(domain, wacSid, appRootPath, startStatus);
            thread.start();
        } catch (Throwable e) {
            log__.error("メール情報一括削除スレッド開始時に例外発生", e);
        }

    }

    /**
     * <br>[機  能] 待機中アカウントの一括削除処理を開始する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param appRootPath アプリケーションルートパス
     */
    public static void startWaitThread(String domain, String appRootPath) {
        __initMap(domain);

        try {

            //待機中アカウントが存在しない場合、処理を終了する
            if (getSuspendedCount(domain) <= 0) {
                return;
            }

            //「最初の」待機中アカウントのアカウントSIDを取得
            int wacSid = suspendedAccountMap__.get(domain).get(0);

            //待機中アカウントから一括削除実行アカウントを削除する
            removeSuspendedAccount(domain, wacSid);

            //メール情報一括削除処理を実行する
            startThread(domain, wacSid, appRootPath,
                        STARTSTATUS_START);

        } catch (Throwable e) {
            log__.error("待機中アカウントの一括削除処理開始時に例外発生", e);
        }

    }

    /**
     * <br>[機  能] 各種Mappingの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     */
    private static void __initMap(String domain) {
        if (!deleteAccountMap__.containsKey(domain)) {
            deleteAccountMap__.put(domain.toString(), new CopyOnWriteArrayList<Integer>());
        }
        if (!suspendedAccountMap__.containsKey(domain)) {
            suspendedAccountMap__.put(domain.toString(), new CopyOnWriteArrayList<Integer>());
        }
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void run() {

        //スレッド名を設定
        Thread.currentThread().setName("WMLDelMail"
                + "-" + System.currentTimeMillis()
                + "-" + domain__
                + "-" + Thread.currentThread().getId());

        log__.info("********** WmlDeleteMailThread start sid = " + wacSid__);

        int result = RESULT_ERROR;
        Connection con = null;
        try {
            con = __getConnection(domain__);

            //一括削除開始ログを出力
            WmlAccountDao accountDao = new WmlAccountDao(con);
            String accountName = accountDao.getAccountName(wacSid__);
            WmlDelmailListDao delListDao = new WmlDelmailListDao(con);
            int deleteCount = delListDao.getDelmailCount(wacSid__);

            String operation = "開始";
            if (startStatus__ == STARTSTATUS_RESTART) {
                operation = "再開";
            }
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutBatchLog(con,
                    domain__,
                    "メール一括削除 " + operation,
                    getClass().getName(),
                    "WEBメール バッチ処理",
                    GSConstLog.LEVEL_TRACE,
                    "[アカウント名] " + accountName
                    + "\r\n[削除対象件数] " + deleteCount,
                    null);

            //メール情報一括削除実行
            log__.info(domain__ + ":メール情報一括削除 実行中アカウント : " + wacSid__);
            result = __doDelete(wacSid__, domain__);
            log__.info(domain__ + ":メール情報一括削除 実行中アカウント : " + wacSid__ + " end");

            //一括削除実行結果 = 完了 or 中断の場合、一括削除完了ログを出力
            if (result == RESULT_COMPLETE || result == RESULT_STOP) {
                CommonBiz cmnBiz = new CommonBiz();
                if (cmnBiz.canDBAccess()) {
                    String resultStr = "完了";
                    if (result == RESULT_STOP) {
                        operation = "中断";
                        deleteCount -= delListDao.getDelmailCount(wacSid__);
                    }
                    wmlBiz.outPutBatchLog(con,
                            domain__,
                            "メール一括削除 " + resultStr,
                            getClass().getName(),
                            "WEBメール バッチ処理",
                            GSConstLog.LEVEL_TRACE,
                            "[アカウント名] " + accountName
                            + "\r\n[削除件数] " + deleteCount,
                            null);
                }
            }
        } catch (DBBackupGuardException dbe) {
            result = RESULT_STOP;
        } catch (Throwable e) {
            log__.error("Exception", e);
        } finally {
            JDBCUtil.closeConnection(con);

            //一括削除実行中アカウントから対象アカウントを削除する
            removeDeleteAccount(domain__, wacSid__);

            setStatus(STATUS_STOP);

            //一括削除実行結果 = 完了の場合、待機中アカウントの一括削除処理を実施
            if (result == RESULT_COMPLETE) {
                try {
                    WmlDeleteMailThread.startWaitThread(new String(domain__),
                                                        new String(appRootPath__));
                } catch (Exception e) {
                    log__.error("待機中アカウント起動時に例外発生", e);
                }
            }

            try {
                //スレッド名に"END-"を設定する
                Thread.currentThread().setName(
                        "END-" + Thread.currentThread().getName());
            } catch (Throwable e) {
            }

        }
        log__.info("********** WmlDeleteMailThread end sid = " + wacSid__);

    }

    /**
     * <br>[機  能] メールの一括削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param domain ドメイン
     * @return 実行結果
     * @throws Throwable メールの削除に失敗
     */
    private int __doDelete(int wacSid, String domain)
    throws Throwable {

        Connection con = null;

        try {
            con = __getConnection(domain);

            //1. 削除対象メール一覧を取得する
            WmlDelmailListDao delListDao = new WmlDelmailListDao(con);
            List<Long> delMailList = delListDao.getDelmailList(wacSid__);

            //削除対象メールに関連する情報の削除を行う
            //※ 100件ずつ行う
            int delCount = 0;
            WebmailDao wmlDao = new WebmailDao(con);
            CommonBiz cmnBiz = new CommonBiz();

            for (int idx = 0; idx < delMailList.size(); idx += 100) {
                //1. バックアップ実行中の場合、処理を停止する
               if (!cmnBiz.canDBAccess()) {
                   return RESULT_STOP;
               }

                boolean commit = false;
                con.setAutoCommit(false);

                int maxIdx = idx + 100;
                List<Long> delMailNumList = new ArrayList<Long>();
                for (int numIdx = idx; numIdx < delMailList.size() && numIdx < maxIdx; numIdx++) {
                    delMailNumList.add(delMailList.get(numIdx));
                }

                try {
                    //2. 削除対象メールに関連する情報の削除を行う
                    wmlDao.manuSendplanDel(delMailNumList);
                    wmlDao.manuSendDel(delMailNumList);
                    wmlDao.manuLabelRelationDel(delMailNumList);
                    wmlDao.manuDelHeader(delMailNumList);
                    wmlDao.manuDelBody(delMailNumList);
                    delCount += wmlDao.manuDelMailData(delMailNumList);

                    //3. 添付ファイル情報の論理削除
                    wmlDao.manuUpdateTempFile(userSid__, delMailNumList);

                    //4. 一括削除対象メールを削除
                    delListDao.deleteDellist(wacSid, delMailNumList);

                    con.commit();
                    commit = true;
                } catch (Exception e) {
                    log__.error("メール情報一括削除時に例外発生", e);
                    return RESULT_ERROR;
                } finally {
                    if (!commit) {
                        JDBCUtil.closeConnection(con);
                    }
                }
            }

            //アカウントディスク容量の再集計を行う
            if (delCount > 0) {
                boolean commit = false;
                try {
                    WmlBiz wmlBiz = new WmlBiz();
                    wmlBiz.updateAccountDiskSize(con, wacSid__);
                    con.commit();
                    commit = true;
                } catch (Exception e) {
                    log__.error("メール情報一括削除: アカウントディスク容量の集計に失敗", e);
                    return RESULT_ERROR;
                } finally {
                    if (!commit) {
                        con.rollback();
                    }
                }
            }

        } finally {
            JDBCUtil.closeConnection(con);
        }


        return RESULT_COMPLETE;
    }

    /**
     * <br>[機  能] 指定したアカウントがメール一括削除処理中かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @return true:削除処理中 false:削除処理未実施
     * @throws Exception 実行時例外
     */
    public static boolean checkDeleteAccount(String domain, int wacSid)
    throws Exception {
        boolean receive = false;
        synchronized (getDeleteAccount(domain)) {
            receive = getDeleteAccount(domain).indexOf(wacSid) >= 0;
        }
        return receive;
    }

    /**
     * <br>[機  能] 実行中スレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 実行中スレッド件数
     * @throws Exception 実行時例外
     */
    public static int getThreadCount(String domain)
    throws Exception {
        int count = 0;
        synchronized (getDeleteAccount(domain)) {
            count = getDeleteAccount(domain).size();
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したアカウントをメール一括削除処理中として設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public static void addDeleteAccount(String domain, int wacSid)
    throws SQLException {
        synchronized (getDeleteAccount(domain)) {
            getDeleteAccount(domain).add(wacSid);
        }
    }

    /**
     * <br>[機  能] 指定したアカウントをメール一括削除処理中アカウントから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     */
    public static synchronized void removeDeleteAccount(String domain, int wacSid) {
        synchronized (getDeleteAccount(domain)) {
            getDeleteAccount(domain).remove(
                    getDeleteAccount(domain).indexOf(wacSid));
        }
    }

    /**
     * <br>[機  能] 削除処理中アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 削除処理中アカウント
     */
    public static CopyOnWriteArrayList<Integer> getDeleteAccount(String domain) {
        if (!deleteAccountMap__.containsKey(domain)) {
            deleteAccountMap__.put(domain.toString(), new CopyOnWriteArrayList<Integer>());
        }
        return deleteAccountMap__.get(domain);
    }

    /**
     * <br>[機  能] 待機中スレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 待機中スレッド件数
     * @throws Exception 実行時例外
     */
    public static int getSuspendedCount(String domain)
    throws Exception {
        int count = 0;
        synchronized (getSuspendedAccount(domain)) {
            count = getSuspendedAccount(domain).size();
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したアカウントを待機中アカウントとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public static void addSuspendedAccount(String domain, int wacSid)
    throws SQLException {
        synchronized (getSuspendedAccount(domain)) {
            getSuspendedAccount(domain).add(wacSid);
        }
    }

    /**
     * <br>[機  能] 指定したアカウントを待機中アカウントから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param wacSid アカウントSID
     */
    public static synchronized void removeSuspendedAccount(String domain, int wacSid) {
        synchronized (getSuspendedAccount(domain)) {
            getSuspendedAccount(domain).remove(
                    getSuspendedAccount(domain).indexOf(wacSid));
        }
    }

    /**
     * <br>[機  能] 待機中アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 待機中アカウント
     */
    public static CopyOnWriteArrayList<Integer> getSuspendedAccount(String domain) {
        if (!suspendedAccountMap__.containsKey(domain)) {
            suspendedAccountMap__.put(domain.toString(), new CopyOnWriteArrayList<Integer>());
        }
        return suspendedAccountMap__.get(domain);
    }

    /**
     * <br>[機  能] Connectionを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return コネクション
     * @throws SQLException コネクションの取得に失敗
     * @throws Exception DataSourceの取得に失敗
     */
    private Connection __getConnection(String domain)
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

}
