package jp.groupsession.v2.cmn.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.listener.CmnGsListenerImpl;


/**
 *
 * <br>[機  能] WEBSocket接続先セッション管理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WebSocketSessionManager {
    /** 接続WebSocketSession情報 key:gsドメイン、value:keyにユーザSID、valueにプラグインIDとセッションの配列を持つMap*/
    private static ConcurrentHashMap<String, ConcurrentHashMap<Integer, Object[]>> sessionMap__ =
            new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Object[]>>();

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WebSocketSessionManager.class);

    /** アクセスドメインID */
    private String domain__;
    /**
     *
     * コンストラクタ
     * @param domain gsドメイン
     */
    public WebSocketSessionManager(String domain) {
        domain__ = domain;
    }

    /**
    *
    * コンストラクタ
    */
    public WebSocketSessionManager() {
    }
    /**
     *
     * <br>[機  能] Websocket情報オブジェクト取得する
     * <br>[解  説] Object[]=[String, Session]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return Websocket情報オブジェクト
     */
    public Object[] getWebsocketInfo(
            int usrSid) {
        if (!sessionMap__.containsKey(domain__)) {
            return null;
        }
        ConcurrentHashMap<Integer, Object[]> domainMap = sessionMap__.get(domain__);

        if (!domainMap.containsKey(usrSid)) {
            return null;
        }
        return domainMap.get(usrSid);
    }

    /**
     *
     * <br>[機  能] ユーザSIDから接続先セッションを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 接続先ユーザSID
     * @return javax.websocket.Session
     */
    public Session getWebSocketSession(int usrSid) {
        Object[] sessionInfo = getWebsocketInfo(
                usrSid);
        if (sessionInfo == null) {
            return null;
        }
        return (Session) sessionInfo[1];
    }
    /**
     *
     * <br>[機  能] ユーザSIDからセッション生成時のプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 接続先ユーザSID
     * @return javax.websocket.Session
     */
    public String getWebSocketPluginId(int usrSid) {
        Object[] sessionInfo = getWebsocketInfo(
                usrSid);
        if (sessionInfo == null) {
            return null;
        }
        return (String) sessionInfo[0];
    }

    /**
     *
     * <br>[機  能] 接続先セッションを保管
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 接続先ユーザSID
     * @param pluginId プラグインID
     * @param wsSession 接続先セッション
     */
    public void setWebSocketSession(int usrSid, String pluginId, Session wsSession) {
        ConcurrentHashMap<Integer, Object[]> domainMap;
        if (sessionMap__.containsKey(domain__)) {
            domainMap = sessionMap__.get(domain__);
        } else {
            domainMap = new ConcurrentHashMap<Integer, Object[]>();
            sessionMap__.put(domain__, domainMap);
        }
        domainMap.put(usrSid, new Object[] {pluginId, wsSession});
        //逆引き用情報の保管
        wsSession.getUserProperties().put("sessionUsrSid", usrSid);
        wsSession.getUserProperties().put("domain", domain__);
    }
    /**
     *
     * <br>[機  能] Session情報からユーザSIDの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param wsSession WebSocket接続セッション
     * @return ユーザSID文字列
     */
    public static int getSessionUsrSid(Session wsSession) {
        if (wsSession == null || wsSession.getId() == null) {
            return -1;
        }
        if (wsSession.getUserProperties().containsKey("sessionUsrSid")) {
            return (Integer) wsSession.getUserProperties().get("sessionUsrSid");
        }
        return -1;
    }
    /**
     *
     * <br>[機  能] Session情報からユーザSIDの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param wsSession WebSocket接続セッション
     * @return ユーザSID文字列
     */
   public static String getSessionDomain(Session wsSession) {
       if (wsSession == null || wsSession.getId() == null) {
           return null;
       }
       if (wsSession.getUserProperties().containsKey("domain")) {
           return  (String) wsSession.getUserProperties().get("domain");
       }
       return null;
   }

    /**
     *
     * <br>[機  能] 管理している接続先セッションを除去する
     * <br>[解  説]
     * <br>[備  考]
     * @param wsSession WebSocket接続セッション
     */
    public static void removeWebSocketSession(Session wsSession) {
        if (wsSession == null || wsSession.getId() == null) {
            return;
        }
        int usrSid = (Integer) wsSession.getUserProperties().getOrDefault(
                "sessionUsrSid", -1);
        String domain = (String) wsSession.getUserProperties().getOrDefault(
                "domain", null);
        if (domain == null) {
            return;
        }
        if (!sessionMap__.containsKey(domain)) {
            return;
        }
        ConcurrentHashMap<Integer, Object[]> domainMap = sessionMap__.get(domain);

        if (!domainMap.containsKey(usrSid)) {
            return;
        }
        domainMap.remove(usrSid);

    }
    /**
     * <p> セッションのタイムアウトを防ぐpingの送信
     * */
    public static void pingBroadcast() {
        //サービス終了後の場合実行しない
        if (!CmnGsListenerImpl.isStart()) {
            return;
        }
        for (Map.Entry<String, ConcurrentHashMap<Integer, Object[]>> domainEntry
                : sessionMap__.entrySet()) {
            ConcurrentHashMap<Integer, Object[]> domainMap = domainEntry.getValue();
            String domain = domainEntry.getKey();
            ThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);

            for (Map.Entry<Integer, Object[]> entry : domainMap.entrySet()) {
                Session wsSession = (Session) entry.getValue()[1];
                synchronized (wsSession) {
                    RemoteEndpoint.Basic ep = wsSession.getBasicRemote();
                    try {
                        ep.sendPing(ByteBuffer.allocate(1));
                    } catch (IOException  e) {
                        log__.info(e.getCause(), e);
                    } catch (IllegalArgumentException e) {
                        log__.error(e.getCause(), e);
                    }
                }
            }
        }
        ThreadContext.remove(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN);

    }
    /**
     *
     * <br>[機  能] 既存接続セッションの存在確認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 接続先ユーザSID
     * @return true:存在 false：存在しない
     */
    public boolean containsSession(int usrSid) {
        return (getWebsocketInfo(usrSid) != null);
    }

    /**
     *
     * <br>[機  能] WebSocketSession指定でWebSocketでメッセージ送信を行う
     * <br>[解  説] session毎の排他制御を行いメッセージ送信を行う
     * <br>[備  考] メッセージ送信は基本的にWebSocketSenderを使用すること
     * @param ws Session
     * @param message 送信本文
     * @return true:メッセージ送信成功 false:メッセージ送信失敗
     * @throws IOException IO例外 書き込み失敗など
    */
    protected static boolean _sendText(Session ws, String message) throws IOException {
        synchronized (ws) {
            if (!ws.isOpen()) {
                return false;
            }
            RemoteEndpoint.Basic ep = ws.getBasicRemote();
            ep.sendText(message);
        }
        return true;
    }

}
