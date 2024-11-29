package jp.groupsession.v2.cmn.websocket;


import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CloseableThreadName;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBrowserInfDao;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.cmn.listener.CmnGsListenerImpl;
import jp.groupsession.v2.cmn.model.base.CmnBrowserInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * WebSocket通信を行なうためのクラスです。
 * サーバとしての役割を担います。
 * ServerEndPoint引数内の文字列【common】は通信を行なうプラグイン名に置き換えることになると思います。
 * 【connection】は任意の文字列です。
 * */
@ServerEndpoint(value = "/common/connection",
        configurator = GetHttpSessionConfigurator.class)
public class WebSocketEndpointAction {
    /** 初回接続フラグ名 */
    public static final String WS_FIRST = "ws_first";
    private static final int WS_CONNECT_PARAM = 2;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WebSocketEndpointAction.class);

    /**
     * 通信開始時(ハンドシェイク時)の処理
     * @param wsSession 接続するクライアントのセッション情報
     * @param config ServerEndPoitnの設定
     * @throws IOException 入出力例外
     * @throws SQLException SQL実行例外
     */
    @OnOpen
    public void onOpen(Session wsSession, EndpointConfig config)
            throws IOException, SQLException {
        //ドメイン名取得
        String domain = (String) config.getUserProperties().get("domain");

        Thread.currentThread().setName("WebSocket"
                                        + "-" + System.currentTimeMillis()
                                        + "-" + domain
                                        + "-" + Thread.currentThread().getId());

        try {
            ThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);
            int usrSid;
            HttpSession httpSession = null;
            String pluginId = null;
            boolean isForceibly = false;

            // 開始時
            synchronized (wsSession) {
                //サービス終了後の場合実行しない
                if (!CmnGsListenerImpl.isStart()) {
                    return;
                }

                //Originが不正な場合、実行しない
                String origin = (String) config.getUserProperties().get("origin");
                List<String> wlList = (List<String>) config.getUserProperties().get("originWL");
                if (origin != null && wlList != null && origin.indexOf("://localhost") < 0) {
                    boolean result = false;
                    for (String wlStr : wlList) {
                        if (origin.indexOf(wlStr) > 0) {
                            result = true;
                            break;
                        }
                    }

                    if (!result) {
                        log__.warn("不正なOriginヘッダーが指定されたため、WebSocket接続を終了");
                        log__.warn("Origin:" + origin);
                        for (String wlStr : wlList) {
                            log__.warn("WL:" + wlStr);
                        }
                        return;
                    }
                }

                // セッション情報およびセッションユーザ情報を取得
                httpSession =
                        (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
                BaseUserModel umodel = null;

                try {
                    umodel = (BaseUserModel) httpSession.getAttribute(GSConst.SESSION_KEY);
                } catch (NullPointerException e) {
                    __denyNewConnectionNologin(wsSession);
                    return;
                }
                // ユーザ情報が存在しない場合は切断
                if (umodel == null) {
                    __denyNewConnectionNologin(wsSession);
                    return;
                }

                // ドメインチェック
                if (!domain.equals(GSConst.GS_DOMAIN) && !domain.equals(umodel.getDomain())) {
                    __denyNewConnectionNologin(wsSession);
                    return;
                }

                usrSid = umodel.getUsrsid();
                if (usrSid <= GSConstUser.USER_RESERV_SID) {
                    CloseReason reason =
                            new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "reserved");
                    wsSession.close(reason);
                    return;
                }
                //接続パラメータ取得
                String[] params = wsSession.getQueryString().split("&");
                int numParams = WS_CONNECT_PARAM;
                //接続パラメータが不正
                if (params.length < numParams) {
                    __denyNewConnectionIlegal(wsSession);
                    return;
                }
                pluginId = params[numParams - 2];
                isForceibly = params[numParams - 1].equals("forciblyClose");

            }
            Connection con = null;

            WebSocketSessionManager sessionManager = new WebSocketSessionManager(domain);
            //ログイン後の初回アクセス時は強制接続
            String ws_first = (String) httpSession.getAttribute(WS_FIRST);
            if (NullDefault.getInt(ws_first, 0) <= 0) {
                isForceibly = true;
                httpSession.setAttribute(WS_FIRST, "1");
            }

            // 指定したユーザがまだ接続していない場合
            try {
                con = GroupSession.getConnection(domain);
                CmnBrowserInfDao cbiDao = new CmnBrowserInfDao(con);
                CmnBrowserInfModel cbiMdl = cbiDao.select(usrSid);
                if (cbiMdl == null) {
                    // 接続情報登録
                    sessionManager.setWebSocketSession(usrSid, pluginId, wsSession);
                    __registAtOpen(con, usrSid, httpSession.getId(), pluginId, wsSession, domain);
                    return;
                }
                Session oldSession = sessionManager.getWebSocketSession(usrSid);
                String oldPluginId = sessionManager.getWebSocketPluginId(usrSid);
                // 強制切断
                if (isForceibly) {
                    if (oldSession != null) {
                        __errorMultipleScreens(oldSession);
                        __denyNewConnection(oldSession);
                    }
                    // 新規接続
                    sessionManager.setWebSocketSession(usrSid, pluginId, wsSession);
                    __registAtOpen(con, usrSid, httpSession.getId(), pluginId, wsSession, domain);
                    return;
                }
                String httpId = httpSession.getId();

                // 同じブラウザの別タブで開かれている
                // かつ 新しい画面がチャット
                // かつ 接続済みの画面がチャット以外
                // の場合新しく接続し、既存のセッションを切断
                if (oldPluginId != null
                        && oldSession != null
                        && httpId.equals(cbiMdl.getCbiSessionId())
                        && !oldPluginId.equals(GSConst.PLUGIN_ID_CHAT)
                        && pluginId.equals(GSConst.PLUGIN_ID_CHAT)) {
                    __denyNewConnection(oldSession);
                    // 接続ユーザのセッション情報を保管
                    sessionManager.setWebSocketSession(usrSid, pluginId, wsSession);
                    __registAtOpen(con, usrSid,
                            httpSession.getId(),
                            pluginId, wsSession, domain);
                    return;
                }

                // 既に同じユーザが接続している場合
                // 原則、既存の接続を保持し、新しいセッションは切断
                __denyNewConnection(wsSession);

            } catch (DBBackupGuardException e) {
                //バックアップ実行中の場合、切断する
                __denyNewConnectionNologin(wsSession);
            } catch (IOException e) {
                //クライアントへの送信エラー
            } catch (Exception e) {
                log__.error("WebSocket接続判定時に例外が発生しました。");
                log__.error(e.getCause(), e);
            } finally {
                JDBCUtil.closeConnection(con);
                con = null;
            }
        } finally {
            ThreadContext.remove(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN);

            //スレッド名に"END-"を設定する
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());
        }
    }


    /**
     * クライアントからメッセージが送られてきたときの処理
     * @param message メッセージ
     * @param wsSession セッション
     * @throws IOException 入出力例外
     * @throws SQLException SQL実行例外
     * */
    @OnMessage
    public void onMessage(String message, Session wsSession)
            throws IOException, SQLException {
        //サービス終了後の場合実行しない
        if (!CmnGsListenerImpl.isStart()) {
            return;
        }
    }

    /**
     * WebSocket通信時にエラーが発生した場合の処理
     * @param wsSession エラーが発生したユーザのセッション情報
     * @param t エラー時にthrowされるオブジェクト
     * @throws IOException 入出力例外
     * @throws SQLException SQL実行例外
     */
    @OnError
    public void onError(Session wsSession, Throwable t)
            throws IOException, SQLException {
            if (t != null) {
                boolean bCheck = (t instanceof EOFException);
                String domain = WebSocketSessionManager.getSessionDomain(wsSession);
                try (CloseableThreadContext.Instance ctc
                        = CloseableThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);
                        CloseableThreadName tnc = CloseableThreadName.setName("WebSocket"
                                + "-" + System.currentTimeMillis()
                                + "-" + domain
                                + "-" + Thread.currentThread().getId());
                        ) {

                    if (!bCheck) {
                        log__.info("WebSocket上でエラーが発生しました。");
                        log__.info(t);
                        for (StackTraceElement error : t.getStackTrace()) {
                            log__.info(error.toString());
                        }
                    }
                }
            }
    }

    /**
     * WebSocket通信終了時の処理
     * @param wsSession 接続しているクライアントのセッション情報
     * @param reason 切断理由
     * @throws Exception 例外処理
     * */
    @OnClose
    public void onClose(Session wsSession, CloseReason reason) throws Exception {
        String domain;
        int usrSid;
        synchronized (wsSession) {
            //サービス終了後の場合実行しない
            if (!CmnGsListenerImpl.isStart()) {
                return;
            }
            // 同一ユーザの接続が既にある場合、既存の情報は消さない
            String reasonText = null;
            if (reason != null && reason.getReasonPhrase() != null) {
                reasonText = reason.getReasonPhrase();
            }
            if (reasonText != null && reasonText.equals("deny")) {
                return;
            }
            usrSid = WebSocketSessionManager.getSessionUsrSid(wsSession);

            if (usrSid < 0) {
                return;
            }
            domain = (String) wsSession.getUserProperties().get("domain");

        }
        try (CloseableThreadContext.Instance ctc
                = CloseableThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);
                CloseableThreadName tnc = CloseableThreadName.setName("WebSocket"
                        + "-" + System.currentTimeMillis()
                        + "-" + domain
                        + "-" + Thread.currentThread().getId());
                ) {

            // ユーザのWebSocketセッション情報を除去
            WebSocketSessionManager.removeWebSocketSession(wsSession);
            Connection con = null;
            // ブラウザのセッションID情報を除去
            try {
                con = GroupSession.getConnection(domain);
                con.setAutoCommit(false);
                CmnBrowserInfDao cbiDao = new CmnBrowserInfDao(con);
                cbiDao.delete(usrSid);
                con.commit();
            } catch (DBBackupGuardException e) {
                //バックアップ実行中エラー
            } catch (Exception e) {
                log__.error("WebSocket接続ブラウザ情報除去時に例外処理が発生しました。");
                log__.error(e.getCause(), e);
                throw e;
            } finally {
                JDBCUtil.closeConnection(con);
                con = null;
            }
        }


    }

    /**
     * <p> WebSocket接続時のデータ登録を行う
     * @param con コネクション
     * @param usrSid 接続ユーザSID
     * @param httpId ブラウザのセッションID
     * @param pluginId プラグインID
     * @param wsSession WebSocketセッション
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * */
    private void __registAtOpen(
            Connection con, int usrSid, String httpId, String pluginId, Session wsSession,
            String domain)
            throws SQLException {
        try {
            WebSocketSessionManager sessionManager = new WebSocketSessionManager(domain);
            Object[] sessionInfo = sessionManager.getWebsocketInfo(usrSid);
            for (Object obj : sessionInfo) {
                synchronized (obj) {
                    con.setAutoCommit(false);
                    // ブラウザのセッションIDをDBへ登録
                    CmnBrowserInfDao cbiDao = new CmnBrowserInfDao(con);
                    CmnBrowserInfModel cbiMdl = new CmnBrowserInfModel();
                    cbiMdl.setUsrSid(usrSid);
                    cbiMdl.setCbiSessionId(httpId);
                    CmnBrowserInfModel mdl = cbiDao.select(usrSid);
                    if (mdl != null) {
                        cbiDao.delete(usrSid);
                    }
                    cbiDao.insert(cbiMdl);
                    con.commit();
                }
            }

        } catch (Exception e) {
            //log__.error("データベースへの情報登録時に例外処理が発生しました。");
            //log__.error(e.getCause(), e);
        } finally {
            JDBCUtil.closeConnection(con);
            con = null;
        }
    }


    /**
     * 新しいWebSocket通信の接続を拒否する
     * @param wsSession セッション情報
     * @throws IOException 入出力実行例外
     * @throws SQLException SQL実行例外
     * */
    private void __denyNewConnection(Session wsSession)
                    throws IOException, SQLException {
        __errorMultipleScreens(wsSession);
        CloseReason reason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "deny");
        WebSocketSessionManager.removeWebSocketSession(wsSession);
        wsSession.close(reason);
    }
    /**
     * 未ログイン状態での新しいWebSocket通信の接続を拒否する
     * @param wsSession セッション情報
     * @throws IOException 入出力実行例外
     * @throws SQLException SQL実行例外
     * */
    private void __denyNewConnectionNologin(Session wsSession)
                    throws IOException, SQLException {
        CloseReason reason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "nologin");
        WebSocketSessionManager.removeWebSocketSession(wsSession);
        wsSession.close(reason);
    }
    /**
     * 不正なリクエストでの新しいWebSocket通信の接続を拒否する
     * @param wsSession セッション情報
     * @throws IOException 入出力実行例外
     * @throws SQLException SQL実行例外
     * */
    private void __denyNewConnectionIlegal(Session wsSession)
                    throws IOException, SQLException {
        CloseReason reason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "ilegal");
        WebSocketSessionManager.removeWebSocketSession(wsSession);
        wsSession.close(reason);
    }


    /**
     *
     * <br>[機  能]複数画面運用時のエラー
     * <br>[解  説]
     * <br>[備  考]
     * @param wsSession セッション情報
     * @throws IOException 入出力実行例外
     * @throws SQLException SQL実行例外
     */
    private void __errorMultipleScreens(Session wsSession)
            throws IOException, SQLException {
        // チャット画面を開いた場合、接続が拒否された旨のメッセージを表示
        GsMessage gsMsg = new GsMessage();
        StringBuffer sentMessage = new StringBuffer();
        sentMessage.append("{");
        sentMessage.append("\"type\":\"openedOtherWindow\"");
        sentMessage.append(",");
        sentMessage.append("\"msgContent\":\"" + gsMsg.getMessage("cht.cht010.40") + "\"");
        sentMessage.append("}");

        WebSocketSessionManager._sendText(wsSession, sentMessage.toString());
    }

}
