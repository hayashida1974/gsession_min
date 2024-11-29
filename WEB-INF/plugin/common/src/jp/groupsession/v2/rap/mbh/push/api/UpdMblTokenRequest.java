package jp.groupsession.v2.rap.mbh.push.api;

/**
 * <br>[機  能] 管理端末更新APIリクエスト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UpdMblTokenRequest implements IPushApiRequest {
    /**
     *
     * デフォルトコンストラクタ
     */
    public UpdMblTokenRequest() {
    }

    /** 端末トークン */
    private String token__;
    /** 端末トークン */
    private String appId__;

    /**
     * <p>token を取得します。
     * @return token
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#token__
     */
    public String getToken() {
        return token__;
    }

    /**
     * <p>token をセットします。
     * @param token token
     * @see jp.groupsession.v2.rap.mbh.push.api.DelMblTokenRequest#token__
     */
    public void setToken(String token) {
        token__ = token;
    }

    /**
     * <p>appId を取得します。
     * @return appId
     * @see jp.groupsession.v2.rap.mbh.push.api.UpdMblTokenRequest#appId__
     */
    public String getAppId() {
        return appId__;
    }

    /**
     * <p>appId をセットします。
     * @param appId appId
     * @see jp.groupsession.v2.rap.mbh.push.api.UpdMblTokenRequest#appId__
     */
    public void setAppId(String appId) {
        appId__ = appId;
    }


}
