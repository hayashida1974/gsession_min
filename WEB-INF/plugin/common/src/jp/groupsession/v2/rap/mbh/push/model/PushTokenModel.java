package jp.groupsession.v2.rap.mbh.push.model;

import java.io.Serializable;

/**
 * PUSH通信時に使用するトークン情報を保持する
 * 
 */
public class PushTokenModel implements Serializable {

    /** トークン */
    private String token__;
    /** アプリケーションID */
    private String appId__;

    /**
     * <p>token を取得します。
     * @return token
     */
    public String getToken() {
        return token__;
    }
    /**
     * <p>token をセットします。
     * @param token token
     */
    public void setToken(String token) {
        token__ = token;
    }
    /**
     * <p>appId を取得します。
     * @return appId
     */
    public String getAppId() {
        return appId__;
    }
    /**
     * <p>appId をセットします。
     * @param appId appId
     */
    public void setAppId(String appId) {
        appId__ = appId;
    }

}
