package jp.groupsession.v2.wml.smtp.model;

import java.io.Serializable;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;

/**
 * <br>[機  能] メール送信時に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmtpModel implements Serializable {

    /** アカウントSID */
    private int wacSid__ = 0;
    /** 送信サーバ */
    private String sendServer__ = null;
    /** 送信サーバ　ポート */
    private int sendPort__ = 25;
    /** SMTP認証 */
    private boolean smtpAuth__ = false;
    /** 送信サーバ ユーザ */
    private String sendUser__ = null;
    /** 送信サーバ パスワード */
    private String sendPassword__ = null;
    /** 文字コード */
    private String encode__ = Encoding.ISO_2022_JP;
    /** 認証形式(基本認証 or OAuth) */
    private int authType__ = GSConstWebmail.WAC_AUTH_TYPE_NORMAL;

    /** SSL */
    private int sendEncrypt__ = 0;
    /** POP before SMTP */
    private boolean popBeforeSmtp__ = false;
    /** POPサーバ */
    private String popServer__ = null;
    /** POPサーバ ポート */
    private int popServerPort__ = 110;
    /** POPサーバ ユーザ */
    private String popServerUser__ = null;
    /** POPサーバ パスワード */
    private String popServerPassword__ = null;
    /** POPサーバ SSL */
    private int popServerEncrypt__ = 0;

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

    /** 添付ファイル情報 */
    private List<WmlMailFileModel> tempFileList__ = null;

    /**
     * <p>wacSid を取得します。
     * @return wacSid
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#wacSid__
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#wacSid__
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>popServer を取得します。
     * @return popServer
     */
    public String getPopServer() {
        return popServer__;
    }
    /**
     * <p>popServer をセットします。
     * @param popServer popServer
     */
    public void setPopServer(String popServer) {
        popServer__ = popServer;
    }
    /**
     * <p>popServerPassword を取得します。
     * @return popServerPassword
     */
    public String getPopServerPassword() {
        return popServerPassword__;
    }
    /**
     * <p>popServerPassword をセットします。
     * @param popServerPassword popServerPassword
     */
    public void setPopServerPassword(String popServerPassword) {
        popServerPassword__ = popServerPassword;
    }
    /**
     * <p>popServerPort を取得します。
     * @return popServerPort
     */
    public int getPopServerPort() {
        return popServerPort__;
    }
    /**
     * <p>popServerPort をセットします。
     * @param popServerPort popServerPort
     */
    public void setPopServerPort(int popServerPort) {
        popServerPort__ = popServerPort;
    }
    /**
     * <p>popServerUser を取得します。
     * @return popServerUser
     */
    public String getPopServerUser() {
        return popServerUser__;
    }
    /**
     * <p>popServerUser をセットします。
     * @param popServerUser popServerUser
     */
    public void setPopServerUser(String popServerUser) {
        popServerUser__ = popServerUser;
    }
    /**
     * <p>popBeforeSmtp を取得します。
     * @return popBeforeSmtp
     */
    public boolean isPopBeforeSmtp() {
        return popBeforeSmtp__;
    }
    /**
     * <p>popBeforeSmtp をセットします。
     * @param popBeforeSmtp popBeforeSmtp
     */
    public void setPopBeforeSmtp(boolean popBeforeSmtp) {
        popBeforeSmtp__ = popBeforeSmtp;
    }
    /**
     * <p>sendPassword を取得します。
     * @return sendPassword
     */
    public String getSendPassword() {
        return sendPassword__;
    }
    /**
     * <p>sendPassword をセットします。
     * @param sendPassword sendPassword
     */
    public void setSendPassword(String sendPassword) {
        sendPassword__ = sendPassword;
    }
    /**
     * <p>sendPort を取得します。
     * @return sendPort
     */
    public int getSendPort() {
        return sendPort__;
    }
    /**
     * <p>sendPort をセットします。
     * @param sendPort sendPort
     */
    public void setSendPort(int sendPort) {
        sendPort__ = sendPort;
    }
    /**
     * <p>sendServer を取得します。
     * @return sendServer
     */
    public String getSendServer() {
        return sendServer__;
    }
    /**
     * <p>sendServer をセットします。
     * @param sendServer sendServer
     */
    public void setSendServer(String sendServer) {
        sendServer__ = sendServer;
    }
    /**
     * <p>sendUser を取得します。
     * @return sendUser
     */
    public String getSendUser() {
        return sendUser__;
    }
    /**
     * <p>sendUser をセットします。
     * @param sendUser sendUser
     */
    public void setSendUser(String sendUser) {
        sendUser__ = sendUser;
    }
    /**
     * <p>smtpAuth を取得します。
     * @return smtpAuth
     */
    public boolean isSmtpAuth() {
        return smtpAuth__;
    }
    /**
     * <p>smtpAuth をセットします。
     * @param smtpAuth smtpAuth
     */
    public void setSmtpAuth(boolean smtpAuth) {
        smtpAuth__ = smtpAuth;
    }
    /**
     * <p>encode を取得します。
     * @return encode
     */
    public String getEncode() {
        return encode__;
    }
    /**
     * <p>encode をセットします。
     * @param encode encode
     */
    public void setEncode(String encode) {
        encode__ = encode;
    }

    /**
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<WmlMailFileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<WmlMailFileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>sendEncrypt を取得します。
     * @return sendEncrypt
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#sendEncrypt__
     */
    public int getSendEncrypt() {
        return sendEncrypt__;
    }
    /**
     * <p>sendEncrypt をセットします。
     * @param sendEncrypt sendEncrypt
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#sendEncrypt__
     */
    public void setSendEncrypt(int sendEncrypt) {
        sendEncrypt__ = sendEncrypt;
    }
    /**
     * <p>popServerEncrypt を取得します。
     * @return popServerEncrypt
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#popServerEncrypt__
     */
    public int getPopServerEncrypt() {
        return popServerEncrypt__;
    }
    /**
     * <p>popServerEncrypt をセットします。
     * @param popServerEncrypt popServerEncrypt
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#popServerEncrypt__
     */
    public void setPopServerEncrypt(int popServerEncrypt) {
        popServerEncrypt__ = popServerEncrypt;
    }
    /**
     * <p>provider を取得します。
     * @return provider
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#provider__
     */
    public int getProvider() {
        return provider__;
    }
    /**
     * <p>provider をセットします。
     * @param provider provider
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#provider__
     */
    public void setProvider(int provider) {
        provider__ = provider;
    }
    /**
     * <p>clientId を取得します。
     * @return clientId
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#clientId__
     */
    public String getClientId() {
        return clientId__;
    }
    /**
     * <p>clientId をセットします。
     * @param clientId clientId
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#clientId__
     */
    public void setClientId(String clientId) {
        clientId__ = clientId;
    }
    /**
     * <p>secret を取得します。
     * @return secret
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#secret__
     */
    public String getSecret() {
        return secret__;
    }
    /**
     * <p>secret をセットします。
     * @param secret secret
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#secret__
     */
    public void setSecret(String secret) {
        secret__ = secret;
    }
    /**
     * <p>accessToken を取得します。
     * @return accessToken
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#accessToken__
     */
    public String getAccessToken() {
        return accessToken__;
    }
    /**
     * <p>accessToken をセットします。
     * @param accessToken accessToken
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#accessToken__
     */
    public void setAccessToken(String accessToken) {
        accessToken__ = accessToken;
    }
    /**
     * <p>refreshToken を取得します。
     * @return refreshToken
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#refreshToken__
     */
    public String getRefreshToken() {
        return refreshToken__;
    }
    /**
     * <p>refreshToken をセットします。
     * @param refreshToken refreshToken
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#refreshToken__
     */
    public void setRefreshToken(String refreshToken) {
        refreshToken__ = refreshToken;
    }
    /**
     * <p>authType を取得します。
     * @return authType
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#authType__
     */
    public int getAuthType() {
        return authType__;
    }
    /**
     * <p>authType をセットします。
     * @param authType authType
     * @see jp.groupsession.v2.wml.smtp.model.SmtpModel#authType__
     */
    public void setAuthType(int authType) {
        authType__ = authType;
    }

}
