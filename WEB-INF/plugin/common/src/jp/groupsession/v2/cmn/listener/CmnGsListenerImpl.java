package jp.groupsession.v2.cmn.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.IGsListener;
import jp.groupsession.v2.cmn.websocket.WebSocketSessionManager;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時に実行されるリスナーを実装
 * <br>[解  説] WEBソケットサービス状態を管理し、サーバ停止後のWebSocketからのアクセスを制御できるようにする
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnGsListenerImpl implements IGsListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(CmnGsListenerImpl.class);
    /** 状態 停止 */
    public static final int STATUS_STOP = 0;
    /** 状態 開始処理中 */
    public static final int STATUS_READY = 1;
    /** 状態 開始完了 */
    public static final int STATUS_START = 2;
    /** 状態 停止処理開始 */
    public static final int STATUS_SHOUTDOWN = 99;
    /** 状態 */
    private static int status__;
    /** WebSocket 通信確認スケジューラ */
    public static ScheduledExecutorService sch__;
    /** ロック用オブジェクト*/
    private static final Object LOCK__ = new Object();

    /**
     *
     * <br>[機  能] 起動済みか確認
     * <br>[解  説]
     * <br>[備  考]
     * @return true 起動済み false 起動していない
     */
    public static boolean isStart() {
        return (status__ == STATUS_START);
    }

    /** Servlet destroy()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws Exception 実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsInit(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsInit(GSContext gscontext, Connection con, String domain)
                                              throws Exception {
        synchronized (LOCK__) {
            if (isStart()) {
                return;
            }
            status__ = STATUS_READY;
            sch__ = Executors.newSingleThreadScheduledExecutor();
            sch__.scheduleWithFixedDelay(
                    WebSocketSessionManager::pingBroadcast, 180, 180, TimeUnit.SECONDS);
            status__ = STATUS_START;
        }
    }

    /** Servlet destroy()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsDestroy(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsDestroy(GSContext gscontext, Connection con, String domain) throws SQLException {
        if (sch__ != null && sch__.isShutdown() == false) {
            status__ = STATUS_SHOUTDOWN;
            log__.info("スケジューラーシャットダウン 開始");
            try {
                sch__.shutdown();
            } catch (Throwable e) {
                log__.error(e);
            }
            sch__ = null;
            status__ = STATUS_STOP;
        }
    }

}