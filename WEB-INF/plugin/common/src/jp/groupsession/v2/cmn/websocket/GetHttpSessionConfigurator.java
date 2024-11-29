package jp.groupsession.v2.cmn.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GroupSession;

/**
 *
 * <br>[機  能] HTTPセッションを取得します
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    /**
     *WebSocketハンドシェイクからHTTPセッションを取得します
     *@param config サーバエンドポイントの構成
     *@param request ハンドシェイクリクエスト
     *@param response ハンドシェイクに対するレスポンス
     * */
    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if (httpSession != null) {
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
            
            //"Origin"
            List<String> origin = request.getHeaders().get("origin");
            if (origin != null && !origin.isEmpty()) {
                config.getUserProperties().put("origin", origin.get(0));
            }
            
            //設定ファイルからOriginチェックに使用する「サーバ名称」を取得
            List<String> wlList = new ArrayList<String>();
            String wlStr = ConfigBundle.getValue("SERVER_NAME");
            if (!StringUtil.isNullZeroString(wlStr)) {
                StringTokenizer st = new StringTokenizer(wlStr, ",");
                while (st.hasMoreElements()) {
                    wlList.add(st.nextToken().trim());
                }
            }
            
            if (wlList != null && !wlList.isEmpty()) {
                config.getUserProperties().put("originWL", wlList);
            }
        }
        //ドメイン取得
        String domain = GroupSession.getResourceManager().getDomain(request);
        config.getUserProperties().put("domain", domain);


    }
}
