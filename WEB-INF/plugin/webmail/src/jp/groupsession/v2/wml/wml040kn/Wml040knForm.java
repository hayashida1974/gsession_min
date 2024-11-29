package jp.groupsession.v2.wml.wml040kn;

import java.sql.Connection;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.pop3.Pop3Server;
import jp.groupsession.v2.wml.smtp.WmlSmtpSender;
import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.wml040.Wml040Form;


/**
 * <br>[機  能] WEBメール アカウント登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040knForm extends Wml040Form {

    /** 備考 表示用 */
    private String wml040knBiko__ = null;
    /** 署名 表示用 */
    private String wml040knSign__ = null;
    /** テーマ 表示用 */
    private String wml040knTheme__ = null;
    /** 引用符 表示用 */
    private String wml040knQuotes__ = null;
    /** 受信暗号プロトコル 表示用 */
    private String wml040knReciveEncrypt__ = null;
    /** 送信暗号プロトコル 表示用 */
    private String wml040knSendEncrypt__ = null;
    /** プロバイダ 表示用 */
    private String wml040knProvider__ = null;

    /**
     * <p>wml040knBiko を取得します。
     * @return wml040knBiko
     */
    public String getWml040knBiko() {
        return wml040knBiko__;
    }
    /**
     * <p>wml040knBiko をセットします。
     * @param wml040knBiko wml040knBiko
     */
    public void setWml040knBiko(String wml040knBiko) {
        wml040knBiko__ = wml040knBiko;
    }
    /**
     * <p>wml040knSign を取得します。
     * @return wml040knSign
     */
    public String getWml040knSign() {
        return wml040knSign__;
    }
    /**
     * <p>wml040knSign をセットします。
     * @param wml040knSign wml040knSign
     */
    public void setWml040knSign(String wml040knSign) {
        wml040knSign__ = wml040knSign;
    }
    /**
     * <p>wml040knTheme を取得します。
     * @return wml040knTheme
     */
    public String getWml040knTheme() {
        return wml040knTheme__;
    }
    /**
     * <p>wml040knTheme をセットします。
     * @param wml040knTheme wml040knTheme
     */
    public void setWml040knTheme(String wml040knTheme) {
        wml040knTheme__ = wml040knTheme;
    }
    /**
     * <p>wml040knQuotes を取得します。
     * @return wml040knQuotes
     */
    public String getWml040knQuotes() {
        return wml040knQuotes__;
    }
    /**
     * <p>wml040knQuotes をセットします。
     * @param wml040knQuotes wml040knQuotes
     */
    public void setWml040knQuotes(String wml040knQuotes) {
        wml040knQuotes__ = wml040knQuotes;
    }

    /**
     * <br>[機  能] メール受信サーバの接続情報チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @return true: 正常 false:不正
     * @throws Exception 受信サーバまたは送信サーバの接続close時に例外発生
     */
    public boolean checkReceiveConnect(Connection con, String appRootPath, RequestModel reqMdl)
    throws Exception {

        //メール受信サーバの接続テスト
        WmlReceiveServerModel receiveMdl = new WmlReceiveServerModel();
        int authType = getWml040authMethod();
        receiveMdl.setAuthType(authType);
        if (authType == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {

            //トークン情報を取得
            OAuthBiz oatBiz = new OAuthBiz();
            int cotSid = oatBiz.checkOAuthToken(con, 
                    getWml040provider(), getWml040mailAddress(), true);
            OAuthBiz authBiz = new OAuthBiz();
            OauthDataModel authData = authBiz.getAuthData(con, cotSid);
            if (authData == null) {
                return false;
            }

            //アクセストークンの取得
            authBiz.getAccessToken(con, authData, authData.getCotSid(), authData.getCouSid());
            String accessToken = authData.getAccessToken();
            if (accessToken == null) {
                return false;
            }
            receiveMdl.setAccessToken(accessToken);

            receiveMdl.setReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_IMAP);
            receiveMdl.setUser(authData.getAccountId());

            //メール受信サーバ情報の取得
            receiveMdl.setProvider(authData.getProvider());

            OauthMailServerModel serverMdl = new OauthMailServerModel();
            authBiz.setReceiveServerData(serverMdl, authData.getProvider(), appRootPath);
            receiveMdl.setHost(serverMdl.getHost());
            receiveMdl.setPort(serverMdl.getPort());

        } else {
            receiveMdl.setReceiveType(GSConstWebmail.WAC_RECEIVE_TYPE_POP);
            receiveMdl.setHost(getWml040receiveServer());
            receiveMdl.setPort(Integer.parseInt(getWml040receiveServerPort()));
            receiveMdl.setUser(getWml040receiveServerUser());
            receiveMdl.setPassword(getWml040receiveServerPassword());
            receiveMdl.setReceiveEncrypt(getWml040receiveServerEncrypt());
        }
        receiveMdl.setReceiveConnectTimeout(
                WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_CONNECTTIMEOUT,
                        GSConstWebmail.RECEIVE_CONNECTTIMEOUT_DEFAULT));
        receiveMdl.setReceiveTimeout(
                WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_TIMEOUT,
                        GSConstWebmail.RECEIVE_TIMEOUT_DEFAULT));

        Pop3Server receiveServer = new Pop3Server();
        return receiveServer.checkPopServer(receiveMdl);
    }

    /**
     * <br>[機  能] メール送信サーバの接続情報チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @return true: 正常 false:不正
     * @throws Exception 受信サーバまたは送信サーバの接続close時に例外発生
     */
    public boolean checkSendConnect(Connection con, String appRootPath, RequestModel reqMdl)
    throws Exception {

        boolean result = false;
        WmlSmtpSender sender = null;
        try {
            SmtpModel smtpData = new SmtpModel();
            int authType = getWml040authMethod();
            smtpData.setAuthType(authType);

            if (authType == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                //OAuth認証
                OAuthBiz oatBiz = new OAuthBiz();
                int cotSid = oatBiz.checkOAuthToken(con, 
                        getWml040provider(), getWml040mailAddress(), true);
                //トークン情報を取得
                OAuthBiz authBiz = new OAuthBiz();
                OauthDataModel authData = authBiz.getAuthData(con, cotSid);
                if (authData == null) {
                    return false;
                }

                //アクセストークンの取得
                authBiz.getAccessToken(con, authData, authData.getCotSid(), authData.getCouSid());
                String accessToken = authData.getAccessToken();
                if (accessToken == null) {
                    return false;
                }
                smtpData.setAccessToken(accessToken);

                //メール送信サーバ情報の取得
                smtpData.setProvider(authData.getProvider());

                OauthMailServerModel serverMdl = new OauthMailServerModel();
                smtpData.setSendUser(authData.getAccountId());
                authBiz.setSendServerData(serverMdl, authData.getProvider(), appRootPath);
                smtpData.setSendServer(serverMdl.getHost());
                smtpData.setSendPort(serverMdl.getPort());

            } else {
                //通常認証

                smtpData.setSendServer(getWml040sendServer());
                smtpData.setSendPort(Integer.parseInt(getWml040sendServerPort()));
                if (!StringUtil.isNullZeroString(getWml040sendServerUser())) {
                    smtpData.setSendUser(getWml040sendServerUser());
                    smtpData.setSendPassword(getWml040sendServerPassword());
                    smtpData.setSmtpAuth(true);
                } else {
                    smtpData.setSmtpAuth(false);
                }

                smtpData.setPopBeforeSmtp(getWml040popBSmtp() == POPBSMTP_ON);
                if (smtpData.isPopBeforeSmtp()) {
                    smtpData.setPopServer(getWml040receiveServer());
                    smtpData.setPopServerPort(Integer.parseInt(getWml040receiveServerPort()));
                    smtpData.setPopServerUser(getWml040receiveServerUser());
                    smtpData.setPopServerPassword(getWml040receiveServerPassword());
                    smtpData.setPopServerEncrypt(getWml040receiveServerEncrypt());
                }
                smtpData.setSendEncrypt(getWml040sendServerEncrypt());
            }

            sender = new WmlSmtpSender();
            sender.connect(smtpData);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            if (sender != null) {
                sender.disConnect();
            }
        }

        return result;
    }
    /**
     * <p>wml040knReciveEncrypt を取得します。
     * @return wml040knReciveEncrypt
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knReciveEncrypt__
     */
    public String getWml040knReciveEncrypt() {
        return wml040knReciveEncrypt__;
    }
    /**
     * <p>wml040knReciveEncrypt をセットします。
     * @param wml040knReciveEncrypt wml040knReciveEncrypt
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knReciveEncrypt__
     */
    public void setWml040knReciveEncrypt(String wml040knReciveEncrypt) {
        wml040knReciveEncrypt__ = wml040knReciveEncrypt;
    }
    /**
     * <p>wml040knSendEncrypt を取得します。
     * @return wml040knSendEncrypt
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knSendEncrypt__
     */
    public String getWml040knSendEncrypt() {
        return wml040knSendEncrypt__;
    }
    /**
     * <p>wml040knSendEncrypt をセットします。
     * @param wml040knSendEncrypt wml040knSendEncrypt
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knSendEncrypt__
     */
    public void setWml040knSendEncrypt(String wml040knSendEncrypt) {
        wml040knSendEncrypt__ = wml040knSendEncrypt;
    }
    /**
     * <p>wml040knProvider を取得します。
     * @return wml040knProvider
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knProvider__
     */
    public String getWml040knProvider() {
        return wml040knProvider__;
    }
    /**
     * <p>wml040knProvider をセットします。
     * @param wml040knProvider wml040knProvider
     * @see jp.groupsession.v2.wml.wml040kn.Wml040knForm#wml040knProvider__
     */
    public void setWml040knProvider(String wml040knProvider) {
        wml040knProvider__ = wml040knProvider;
    }
}
