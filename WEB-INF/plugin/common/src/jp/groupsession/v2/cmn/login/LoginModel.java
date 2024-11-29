package jp.groupsession.v2.cmn.login;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ログイン処理時に使用する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginModel extends AbstractModel {

    /** ログイン種別 通常 */
    public static final int LOGINTYPE_NORMAL = 0;
    /** ログイン種別 API */
    public static final int LOGINTYPE_API = 1;

    /** ログインID */
    private String loginId__ = null;
    /** パスワード */
    private String password__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /** アクションマッピング */
    private ActionMapping map__ = null;
    /** プラグイン情報 */
    private PluginConfig pluginConfig__ = null;
    /** ログイン種別 (通常 or API) */
    private int loginType__ = 0;
    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>loginId を取得します。
     * @return loginId
     */
    public String getLoginId() {
        return loginId__;
    }
    /**
     * <p>loginId をセットします。
     * @param loginId loginId
     */
    public void setLoginId(String loginId) {
        loginId__ = loginId;
    }
    /**
     * <p>map を取得します。
     * @return map
     */
    public ActionMapping getMap() {
        return map__;
    }
    /**
     * <p>map をセットします。
     * @param map map
     */
    public void setMap(ActionMapping map) {
        map__ = map;
    }
    /**
     * <p>password を取得します。
     * @return password
     */
    public String getPassword() {
        return password__;
    }
    /**
     * <p>password をセットします。
     * @param password password
     */
    public void setPassword(String password) {
        password__ = password;
    }
    /**
     * <p>req を取得します。
     * @return req
     */
    public HttpServletRequest getReq() {
        return req__;
    }
    /**
     * <p>req をセットします。
     * @param req req
     */
    public void setReq(HttpServletRequest req) {
        req__ = req;
    }
    /**
     * <p>pluginConfig を取得します。
     * @return pluginConfig
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig__;
    }
    /**
     * <p>pluginConfig をセットします。
     * @param pluginConfig pluginConfig
     */
    public void setPluginConfig(PluginConfig pluginConfig) {
        pluginConfig__ = pluginConfig;
    }

    /**
     * <p>loginType を取得します。
     * @return loginType
     * @see jp.groupsession.v2.cmn.login.LoginModel#loginType__
     */
    public int getLoginType() {
        return loginType__;
    }
    /**
     * <p>loginType をセットします。
     * @param loginType loginType
     * @see jp.groupsession.v2.cmn.login.LoginModel#loginType__
     */
    public void setLoginType(int loginType) {
        loginType__ = loginType;
    }
}
