package jp.groupsession.v2.cmn.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] OAuth認証情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OauthDataModel extends AbstractModel {
    /** アカウントID(メールアドレス) */
    private String accountId__ = null;
    /** アカウント ユーザID */
    private String userId__ = null;
    /** アクセストークン */
    private String accessToken__ = null;
    /** アクセストークン取得日時 */
    private UDate accessTokenDate__ = null;
    /** トークン種別 */
    private String tokenType__ = null;
    /** リフレッシュトークン */
    private String refreshToken__ = null;
    /** コード */
    private String code__ = null;

    /** クライアントID */
    private String clientId__ = null;
    /** シークレットキー */
    private String secretKey__ = null;
    /** 認証情報SID */
    private int couSid__ = 0;
    /** OAuth認証トークンSID */
    private int cotSid__ = 0;
    /** OAuth認証情報トークンSID 設定先パラメータ名 */
    private String cotSidParamName__ = null;
    /** プロバイダ(COU_PROVIDER) */
    private int provider__ = 0;

    /** プラグインID */
    private String pluginId__ = null;
    /** 画面ID */
    private String screenId__ = null;
    /** リダイレクトURL */
    private String redirectUrl__ = null;

    /** state */
    private String state__ = null;
    /** code_verifier */
    private String codeVerifier__ = null;
    /**
     * <p>accountId を取得します。
     * @return accountId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }
    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
    /**
     * <p>userId を取得します。
     * @return userId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#userId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>accessToken を取得します。
     * @return accessToken
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accessToken__
     */
    public String getAccessToken() {
        return accessToken__;
    }
    /**
     * <p>accessToken をセットします。
     * @param accessToken accessToken
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accessToken__
     */
    public void setAccessToken(String accessToken) {
        accessToken__ = accessToken;
    }
    /**
     * <p>accessTokenDate を取得します。
     * @return accessTokenDate
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accessTokenDate__
     */
    public UDate getAccessTokenDate() {
        return accessTokenDate__;
    }
    /**
     * <p>accessTokenDate をセットします。
     * @param accessTokenDate accessTokenDate
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#accessTokenDate__
     */
    public void setAccessTokenDate(UDate accessTokenDate) {
        accessTokenDate__ = accessTokenDate;
    }
    /**
     * <p>tokenType を取得します。
     * @return tokenType
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#tokenType__
     */
    public String getTokenType() {
        return tokenType__;
    }
    /**
     * <p>tokenType をセットします。
     * @param tokenType tokenType
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#tokenType__
     */
    public void setTokenType(String tokenType) {
        tokenType__ = tokenType;
    }
    /**
     * <p>refreshToken を取得します。
     * @return refreshToken
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#refreshToken__
     */
    public String getRefreshToken() {
        return refreshToken__;
    }
    /**
     * <p>refreshToken をセットします。
     * @param refreshToken refreshToken
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#refreshToken__
     */
    public void setRefreshToken(String refreshToken) {
        refreshToken__ = refreshToken;
    }
    /**
     * <p>code を取得します。
     * @return code
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#code__
     */
    public String getCode() {
        return code__;
    }
    /**
     * <p>code をセットします。
     * @param code code
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#code__
     */
    public void setCode(String code) {
        code__ = code;
    }
    /**
     * <p>clientId を取得します。
     * @return clientId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#clientId__
     */
    public String getClientId() {
        return clientId__;
    }
    /**
     * <p>clientId をセットします。
     * @param clientId clientId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#clientId__
     */
    public void setClientId(String clientId) {
        clientId__ = clientId;
    }
    /**
     * <p>secretKey を取得します。
     * @return secretKey
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#secretKey__
     */
    public String getSecretKey() {
        return secretKey__;
    }
    /**
     * <p>secretKey をセットします。
     * @param secretKey secretKey
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#secretKey__
     */
    public void setSecretKey(String secretKey) {
        secretKey__ = secretKey;
    }
    /**
     * <p>couSid を取得します。
     * @return couSid
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#couSid__
     */
    public int getCouSid() {
        return couSid__;
    }
    /**
     * <p>couSid をセットします。
     * @param couSid couSid
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#couSid__
     */
    public void setCouSid(int couSid) {
        couSid__ = couSid;
    }
    /**
     * <p>cotSid を取得します。
     * @return cotSid
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#cotSid__
     */
    public int getCotSid() {
        return cotSid__;
    }
    /**
     * <p>cotSid をセットします。
     * @param cotSid cotSid
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#cotSid__
     */
    public void setCotSid(int cotSid) {
        cotSid__ = cotSid;
    }
    /**
     * <p>cotSidParamName を取得します。
     * @return cotSidParamName
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#cotSidParamName__
     */
    public String getCotSidParamName() {
        return cotSidParamName__;
    }
    /**
     * <p>cotSidParamName をセットします。
     * @param cotSidParamName cotSidParamName
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#cotSidParamName__
     */
    public void setCotSidParamName(String cotSidParamName) {
        cotSidParamName__ = cotSidParamName;
    }
    /**
     * <p>provider を取得します。
     * @return provider
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#provider__
     */
    public int getProvider() {
        return provider__;
    }
    /**
     * <p>provider をセットします。
     * @param provider provider
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#provider__
     */
    public void setProvider(int provider) {
        provider__ = provider;
    }
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>screenId を取得します。
     * @return screenId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#screenId__
     */
    public String getScreenId() {
        return screenId__;
    }
    /**
     * <p>screenId をセットします。
     * @param screenId screenId
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#screenId__
     */
    public void setScreenId(String screenId) {
        screenId__ = screenId;
    }
    /**
     * <p>redirectUrl を取得します。
     * @return redirectUrl
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#redirectUrl__
     */
    public String getRedirectUrl() {
        return redirectUrl__;
    }
    /**
     * <p>redirectUrl をセットします。
     * @param redirectUrl redirectUrl
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#redirectUrl__
     */
    public void setRedirectUrl(String redirectUrl) {
        redirectUrl__ = redirectUrl;
    }
    /**
     * <p>state を取得します。
     * @return state
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#state__
     */
    public String getState() {
        return state__;
    }
    /**
     * <p>state をセットします。
     * @param state state
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#state__
     */
    public void setState(String state) {
        state__ = state;
    }
    /**
     * <p>codeVerifier を取得します。
     * @return codeVerifier
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#codeVerifier__
     */
    public String getCodeVerifier() {
        return codeVerifier__;
    }
    /**
     * <p>codeVerifier をセットします。
     * @param codeVerifier codeVerifier
     * @see jp.groupsession.v2.cmn.model.OauthDataModel#codeVerifier__
     */
    public void setCodeVerifier(String codeVerifier) {
        codeVerifier__ = codeVerifier;
    }

}
