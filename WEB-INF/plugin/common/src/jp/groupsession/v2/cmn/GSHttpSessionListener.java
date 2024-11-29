package jp.groupsession.v2.cmn;

import java.io.File;
import java.sql.Connection;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機 能] GroupSession内のSession生成/破棄時の動作を記述します。
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GSHttpSessionListener implements HttpSessionListener {

    /** ログ */
    Log log__ = LogFactory.getLog(GSHttpSessionListener.class);

    /**
     * [機 能] Session生成時の処理を記述する
     * [解 説]
     * [備 考]
     * @param ev HttpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent ev) {

        Connection con = null;
        try {
            HttpSession session = ev.getSession();

            Object tmp = session.getAttribute(GSConst.SESSION_KEY);
            if (tmp != null) {
                BaseUserModel umodel = (BaseUserModel) tmp;
                int userSid = umodel.getUsrsid();
                GSContext gsContext = __getGSContext();
                con = __getConnection(gsContext, umodel.getDomain());
                con.setAutoCommit(false);
                ILoginLogoutListener[] listeners
                            = __getLoginLogoutListener(__getPluginConfig(umodel.getDomain()));
                for (ILoginLogoutListener lis : listeners) {
                    lis.doLogin(con, userSid);
                }
            }
        } catch (Exception e) {
            log__.error("ログイン処理時例外", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }
    }

    /**
     * [機 能] Session破棄時の処理を記述する
     * [解 説]
     * [備 考]
     * @param ev HttpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent ev) {

        Connection con = null;
        try {
            HttpSession session = ev.getSession();

            Object tmp = session.getAttribute(GSConst.SESSION_KEY);
            if (tmp != null) {
                BaseUserModel umodel = (BaseUserModel) tmp;
                int userSid = umodel.getUsrsid();
                ILoginLogoutListener[] listeners =
                    __getLoginLogoutListener(__getPluginConfig(umodel.getDomain()));
                GSContext gsContext = __getGSContext();
                con = __getConnection(gsContext, umodel.getDomain());
                con.setAutoCommit(false);
                for (ILoginLogoutListener lis : listeners) {
                    lis.doLogout(session, con, userSid, umodel.getDomain());
                }
            }

            //RestApiテンポラリフォルダの削除
            String restapiTemporaryDomain =
                    (String) session.getAttribute(GSConst.SESSION_KEY_DOMAIN);

            if (restapiTemporaryDomain != null) {
                String tempDir =
                        GroupSession.getResourceManager()
                                    .getTempPath(restapiTemporaryDomain)
                        + File.separator + session.getId();
                if (IOTools.isDirCheck(tempDir, false)) {
                    IOTools.deleteDir(tempDir);
                }
            }
        } catch (Exception e) {
            log__.error("ログアウト処理時例外", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }
    }

    /**
     * <br>[機  能] 各プラグインのILoginLogoutListener実装を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginConfig PluginConfig
     * @return GsListener実装
     * @throws ClassNotFoundException 指定されたILoginLogoutListenerクラスが存在しない
     * @throws IllegalAccessException GsListener実装クラスのインスタンス生成に失敗
     * @throws InstantiationException GsListener実装クラスのインスタンス生成に失敗
     */
    private ILoginLogoutListener[] __getLoginLogoutListener(PluginConfig pluginConfig)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        return LoginLogoutListenerUtil.getLoginLogoutListeners(pluginConfig);
    }

    /**
     * <br>[機  能] GroupSession共通情報格納クラスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return GroupSession共通情報格納クラス
     */
    private GSContext __getGSContext() {
        return GroupSession.getContext();
    }

    /**
     * <br>[機  能] コネクションを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsContext GroupSession共通情報
     * @param domain ドメイン
     * @return コネクション
     * @throws Exception 実行例外
     */
    private Connection __getConnection(GSContext gsContext, String domain) throws Exception {
        return GroupSession.getConnection(domain, (long) 1000);
    }

    /**
     * <br>[機  能] プラグイン設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return プラグイン設定情報
     */
    private PluginConfig __getPluginConfig(String dsKey) {
        return GroupSession.getResourceManager().getPluginConfig(dsKey);
    }
}
