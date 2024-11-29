package jp.groupsession.v2.cmn.model;

/**
 * <br>[機  能] OAuth認証情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OauthMailServerModel extends AbstractModel {

    //メールサーバ情報
    /** ホスト */
    private String host__ = null;
    /** ポート */
    private int port__ = 25;

    //OAuth認証情報
    /** プロバイダ */
    private int provider__ = 0;
    /** クライアントID */
    private String clientId__ = null;
    /** シークレットキー */
    private String secret__ = null;
    /** アクセストークン */
    private String accessToken__ = null;
    /** リフレッシュトークン */
    private String refreshToken__ = null;
    /** アカウントID */
    private String accountId__ = null;
    /**
     * <p>host を取得します。
     * @return host
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#host__
     */
    public String getHost() {
        return host__;
    }
    /**
     * <p>host をセットします。
     * @param host host
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#host__
     */
    public void setHost(String host) {
        host__ = host;
    }
    /**
     * <p>port を取得します。
     * @return port
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#port__
     */
    public int getPort() {
        return port__;
    }
    /**
     * <p>port をセットします。
     * @param port port
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#port__
     */
    public void setPort(int port) {
        port__ = port;
    }
    /**
     * <p>provider を取得します。
     * @return provider
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#provider__
     */
    public int getProvider() {
        return provider__;
    }
    /**
     * <p>provider をセットします。
     * @param provider provider
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#provider__
     */
    public void setProvider(int provider) {
        provider__ = provider;
    }
    /**
     * <p>clientId を取得します。
     * @return clientId
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#clientId__
     */
    public String getClientId() {
        return clientId__;
    }
    /**
     * <p>clientId をセットします。
     * @param clientId clientId
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#clientId__
     */
    public void setClientId(String clientId) {
        clientId__ = clientId;
    }
    /**
     * <p>secret を取得します。
     * @return secret
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#secret__
     */
    public String getSecret() {
        return secret__;
    }
    /**
     * <p>secret をセットします。
     * @param secret secret
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#secret__
     */
    public void setSecret(String secret) {
        secret__ = secret;
    }
    /**
     * <p>accessToken を取得します。
     * @return accessToken
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#accessToken__
     */
    public String getAccessToken() {
        return accessToken__;
    }
    /**
     * <p>accessToken をセットします。
     * @param accessToken accessToken
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#accessToken__
     */
    public void setAccessToken(String accessToken) {
        accessToken__ = accessToken;
    }
    /**
     * <p>refreshToken を取得します。
     * @return refreshToken
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#refreshToken__
     */
    public String getRefreshToken() {
        return refreshToken__;
    }
    /**
     * <p>refreshToken をセットします。
     * @param refreshToken refreshToken
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#refreshToken__
     */
    public void setRefreshToken(String refreshToken) {
        refreshToken__ = refreshToken;
    }
    /**
     * <p>accountId を取得します。
     * @return accountId
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#accountId__
     */
    public String getAccountId() {
        return accountId__;
    }
    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     * @see jp.groupsession.v2.cmn.model.OauthMailServerModel#accountId__
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
}
