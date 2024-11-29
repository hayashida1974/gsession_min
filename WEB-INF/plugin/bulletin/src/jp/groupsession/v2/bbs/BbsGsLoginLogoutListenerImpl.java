package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import jp.groupsession.v2.cmn.ILoginLogoutListener;
/**
 *
 * <br>[機  能] 掲示板 ログインログアウトリスナ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsGsLoginLogoutListenerImpl implements ILoginLogoutListener {

    @Override
    public void doLogin(Connection con, int usid) throws SQLException {
    }

    @Override
    public void doLogout(HttpSession session, Connection con, int usid,
            String domain) throws SQLException {
        //一時保管ファイルを削除
        /*String tempDir = GroupSession.getResourceManager().getTempPath(domain)
                       + File.separator + GSConstBulletin.PLUGIN_ID_BULLETIN
                       + File.separator + session.getId();
        IOTools.deleteDir(tempDir);*/

    }

}
