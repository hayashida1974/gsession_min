package jp.groupsession.v2.cmn.listener;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ILoginLogoutListener;
import jp.groupsession.v2.cmn.dao.base.CmnBrowserInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBrowserInfModel;
import jp.groupsession.v2.cmn.websocket.WebSocketSessionManager;
/**
 *
 * <br>[機  能] 共通 ログインログアウトリスナ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnGsLoginLogoutListenerImpl implements ILoginLogoutListener {

    @Override
    public void doLogin(Connection con, int usid) throws SQLException {
    }

    @Override
    public void doLogout(HttpSession session, Connection con, int usid,
            String domain) throws SQLException {
        //一時保管ファイルを削除
        String tempDir = GroupSession.getResourceManager().getTempPath(domain)
                       + File.separator + session.getId();
        IOTools.deleteDir(tempDir);

        // wsセッションが残っている場合
        WebSocketSessionManager wsManager = new WebSocketSessionManager(domain);
        Session wsSession = wsManager.getWebSocketSession(usid);
        // DBのブラウザ情報と、ログアウトしたブラウザのセッションIDが一致する場合
        CmnBrowserInfDao cbiDao = new CmnBrowserInfDao(con);
        CmnBrowserInfModel cbiMdl = cbiDao.select(usid);
        String sessionId = session.getId();
        if (wsSession != null && cbiMdl != null && sessionId.equals(cbiMdl.getCbiSessionId())) {
            // 既存のwsセッションおよびブラウザ情報を削除
            try {
                if (wsSession.isOpen()) {
                    wsSession.close();
                }
            } catch (IOException e) {
            }
        }

    }
}
