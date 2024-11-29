package jp.groupsession.v2.cmn.model;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.UserAgent;

/**
 * <br>[機  能] リクエストから取得する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RequestModel {
    /** ユーザモデル */
    private BaseUserModel smodel__ = null;
    /** 言語 */
    private Locale locale__ = null;
    /** リファラー */
    private String referer__ = null;
    /** セッション */
    private HttpSession session__ = null;
    /** ドメイン */
    private String domain__ = null;
    /** エラー(ログ出力用) */
    private Object oerror__ = null;
    /** クライアントIP */
    private String remoteAddr__ = null;
    /** RequestURI */
    private String requestURI__ = null;
    /** RequestURL */
    private StringBuffer requestURL__ = null;
    /** アクションパス*/
    private String actionPath__ = null;
    /** UserAgent */
    private UserAgent userAgent__ = null;
    /**
     * <p>smodel を取得します。
     * @return smodel
     */
    public BaseUserModel getSmodel() {
        return smodel__;
    }
    /**
     * <p>smodel をセットします。
     * @param smodel smodel
     */
    public void setSmodel(BaseUserModel smodel) {
        smodel__ = smodel;
    }

    /**
     * <p>referer を取得します。
     * @return referer
     */
    public String getReferer() {
        return referer__;
    }
    /**
     * <p>referer をセットします。
     * @param referer referer
     */
    public void setReferer(String referer) {
        referer__ = referer;
    }
    /**
     * <p>locale を取得します。
     * @return locale
     */
    public Locale getLocale() {
        return locale__;
    }
    /**
     * <p>locale をセットします。
     * @param locale locale
     */
    public void setLocale(Locale locale) {
        locale__ = locale;
    }
    /**
     * <p>session を取得します。
     * @return session
     */
    public HttpSession getSession() {
        return session__;
    }
    /**
     * <p>session をセットします。
     * @param session session
     */
    public void setSession(HttpSession session) {
        session__ = session;
    }
    /**
     * <p>domain を取得します。
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }
    /**
     * <p>domain をセットします。
     * @param domain domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
    /**
     * <p>oerror を取得します。
     * @return oerror
     */
    public Object getOerror() {
        return oerror__;
    }
    /**
     * <p>oerror をセットします。
     * @param oerror oerror
     */
    public void setOerror(Object oerror) {
        oerror__ = oerror;
    }
    /**
     * <p>remoteAddr を取得します。
     * @return remoteAddr
     */
    public String getRemoteAddr() {
        return remoteAddr__;
    }
    /**
     * <p>remoteAddr をセットします。
     * @param remoteAddr remoteAddr
     */
    public void setRemoteAddr(String remoteAddr) {
        remoteAddr__ = remoteAddr;
    }
    /**
     * <p>requestURI を取得します。
     * @return requestURI
     */
    public String getRequestURI() {
        return requestURI__;
    }
    /**
     * <p>requestURI をセットします。
     * @param requestURI requestURI
     */
    public void setRequestURI(String requestURI) {
        requestURI__ = requestURI;
    }
    /**
     * <p>requestURL を取得します。
     * @return requestURL
     */
    public StringBuffer getRequestURL() {
        return requestURL__;
    }
    /**
     * <p>requestURL をセットします。
     * @param requestURL requestURL
     */
    public void setRequestURL(StringBuffer requestURL) {
        requestURL__ = requestURL;
    }
    /**
     * <p>actionPath を取得します。
     * @return actionPath
     * @see jp.groupsession.v2.cmn.model.RequestModel#actionPath__
     */
    public String getActionPath() {
        return actionPath__;
    }
    /**
     * <p>actionPath をセットします。
     * @param actionPath actionPath
     * @see jp.groupsession.v2.cmn.model.RequestModel#actionPath__
     */
    public void setActionPath(String actionPath) {
        actionPath__ = actionPath;
    }
    /**
     * <p>userAgent を取得します。
     * @return userAgent
     */
    public UserAgent getUserAgent() {
        return userAgent__;
    }
    /**
     * <p>userAgent をセットします。
     * @param userAgent userAgent
     */
    public void setUserAgent(UserAgent userAgent) {
        userAgent__ = userAgent;
    }
}
