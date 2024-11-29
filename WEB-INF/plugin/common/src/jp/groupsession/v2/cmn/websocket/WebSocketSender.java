package jp.groupsession.v2.cmn.websocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GsResourceBundle;
/**
 *
 * <br>[機  能] WebSocket Message送信クラス
 * <br>[解  説] sessionの排他制御を行いメッセージ送信する
 * <br>[備  考]
 *
 * @author JTS
 */
public class WebSocketSender implements IWebSocketSender {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WebSocketSender.class);
    /** シングルトンインスタンス*/
    private static IWebSocketSender instance__;

    /**
     *
     * デフォルトコンストラクタ
     */
    protected WebSocketSender() {

    }
    /**
     *
     * <br>[機  能] WebSocket 送信 管理クラス取得
     * <br>[解  説]
     * <br>[備  考]
     * @return シングルトンインスタンス
     * @throws Exception Exception
     */
    public static IWebSocketSender getInstance() throws Exception {
        if (instance__ != null) {
            return instance__;
        }

        String clsName = GsResourceBundle.getString("IWebSocketSender");
        if (!StringUtil.isNullZeroString(clsName)) {
            try {
                @SuppressWarnings("unchecked")
                Class<IWebSocketSender> cls = (Class<IWebSocketSender>) Class.forName(clsName);
                instance__ = (IWebSocketSender) cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                log__.error(e);
                throw e;
            } catch (IllegalAccessException e) {
                log__.error(e);
                throw e;
            } catch (ClassNotFoundException e) {
                log__.error(e);
                throw e;
            } catch (IllegalArgumentException e) {
                log__.error(e);
                throw e;
            } catch (InvocationTargetException e) {
                log__.error(e);
                throw e;
            } catch (NoSuchMethodException e) {
                log__.error(e);
                throw e;
            } catch (SecurityException e) {
                log__.error(e);
                throw e;
            }
            if (instance__ != null) {
                return instance__;
            }
        }
        instance__ = new WebSocketSender();
        return instance__;
    }

    @Override
    public void sendText(String domain, int[] uids, String message) {
        sendTextDirect(domain, uids, message);
    }

    @Override
    public int[] sendTextDirect(String domain, int[] uids, String message) {
        List<Integer> retList = new ArrayList<Integer>();

        WebSocketSessionManager wsMan = new WebSocketSessionManager(domain);

        for (Integer uid : uids) {
            Session ws = wsMan.getWebSocketSession(uid);
            if (ws == null) {
                retList.add(uid);
                continue;
            }
            boolean success;
            try {
                success = WebSocketSessionManager._sendText(ws, message);
            } catch (IOException e) {
                log__.info("WEBSocketメッセージ送信時に例外処理が発生しました。送信先:" + uid);
                log__.info(e.getCause(), e);
                retList.add(uid);
                continue;
            }
            if (!success) {
                retList.add(uid);
            }
        }
        int[] ret = new int[retList.size()];
        for (int i = 0; i < retList.size(); i++) {
            ret[i] = retList.get(i);
        }

        return ret;
    }

}
