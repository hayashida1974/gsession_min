package jp.groupsession.v2.cmn.websocket;

/**
 *
 * <br>[機  能] WebSocket メッセージ送信オブジェクト インタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IWebSocketSender {
    /**
     *
     * <br>[機  能] uid指定でWebSocketでメッセージ送信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン文字列
     * @param uid 送信先
     * @param message 送信本文
     */
    public abstract void sendText(String domain, int[] uid, String message);

    /**
     *
     * <br>[機  能] uid指定でWebSocketでメッセージ送信を行う
     * <br>[解  説]
     * <br>[備  考] 直接サーバに接続しているユーザに限る
     * @param domain ドメイン文字列
     * @param uid 送信先
     * @param message 送信本文
     * @return 直接サーバに接続していないユーザSID
     */
    public abstract int[] sendTextDirect(String domain, int[] uid, String message);

}
