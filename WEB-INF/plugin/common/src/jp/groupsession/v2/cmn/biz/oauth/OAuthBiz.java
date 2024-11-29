package jp.groupsession.v2.cmn.biz.oauth;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.MailBiz;

import jp.groupsession.v2.cmn.http.HttpOperation;
import jp.groupsession.v2.cmn.http.HttpRequestModel;
import jp.groupsession.v2.cmn.http.HttpResponseModel;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.dao.base.CmnOauthTokenDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthTokenModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;


/**
 * <br>[機  能] Oauth認証に関する機能を提供する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OAuthBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(OAuthBiz.class);

    /** 接続タイムアウト(1分) */
    private static final int TIMEOUT__ = 6000;

    /** アクセストークン再取得間隔(55分) */
    private static final long ATOKEN_INTERVAL__ = 3300000;

    /** Exchange Online接続時に指定するscope */
    public static final String MICROSOFT_SCOPE =
            "offline_access"
//            + " " + "https://graph.microsoft.com/User.Read"
            + " " + "https://outlook.office.com/IMAP.AccessAsUser.All"
            + " " + "https://outlook.office.com/SMTP.Send";

    /**
     * <br>[機  能] 認証情報(リフレッシュトークン等)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param authData 認証情報
     * @return 認証情報
     * @throws Exception 実行時例外
     */
    public OauthDataModel getAuthData(Connection con, OauthDataModel authData)
    throws Exception {

        String authUrl = null;
        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            authUrl = "https://www.googleapis.com/oauth2/v4/token";
        } else if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            authUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
        }

        HttpOperation httpOperation = new HttpOperation(con, TIMEOUT__);
        HttpRequestModel httpReqMdl = new HttpRequestModel();
        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            //クライアントID
            httpReqMdl.addParam("client_id", authData.getClientId());
            //クライアントシークレット
            httpReqMdl.addParam("client_secret", authData.getSecretKey());
            //認証種別
            httpReqMdl.addParam("grant_type", "authorization_code");
            //code
            httpReqMdl.addParam("code", authData.getCode());
            //リダイレクトURL
            httpReqMdl.addParam("redirect_uri", authData.getRedirectUrl());
            //code_verifier
            httpReqMdl.addParam("code_verifier", authData.getCodeVerifier());
        } else if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            //クライアントID
            httpReqMdl.addParam("client_id", authData.getClientId());
            //クライアントシークレット
            httpReqMdl.addParam("client_secret", authData.getSecretKey());
            //認証種別
            httpReqMdl.addParam("grant_type", "authorization_code");
            //code
            httpReqMdl.addParam("code", authData.getCode());
            //リダイレクトURL
            httpReqMdl.addParam("redirect_uri", authData.getRedirectUrl());
            //code_verifier
            httpReqMdl.addParam("code_verifier", authData.getCodeVerifier());

            //scope
            httpReqMdl.addParam("scope", MICROSOFT_SCOPE);
        }

        HttpResponseModel httpResMdl = httpOperation.sendRequest(authUrl, httpReqMdl);
        int statusCode = httpResMdl.getStatusCode();

        if (statusCode != 200) {
            //取得失敗
            throw new Exception("認証情報取得に失敗 : statusCode = " + statusCode + "\r\n"
                                + httpResMdl.getBody());
        } else {

            String result = httpResMdl.getBody();
            JSONObject jsonData = JSONObject.fromObject(result);
            Iterator<String> keyList = jsonData.keys();
            while (keyList.hasNext()) {
                String key = keyList.next();
                String value = jsonData.getString(key);

                if (key.equals("access_token")) {
                    authData.setAccessToken(value);
                    authData.setAccessTokenDate(new UDate());
                } else if (key.equals("refresh_token")) {
                    authData.setRefreshToken(value);
                }
            }

        }

        //取得したアクセストークンを使用し、アカウント情報を取得する
        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            setAccountData(httpOperation, authData);
        }
        return authData;
    }

    /**
     * <br>[機  能] リフレッシュトークンを指定し、アクセストークンを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param authData 認証情報
     * @param cotSid OAuth認証トークンSID
     * @param couSid 認証情報SID
     * @throws Exception 実行時例外
     */
    public void getAccessToken(Connection con, OauthDataModel authData, int cotSid, int couSid)
    throws Exception {
        //リフレッシュトークンが未設定の場合、処理を終了する
        if (StringUtil.isNullZeroString(authData.getRefreshToken())) {
            return;
        }

        String authUrl = null;
        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            authUrl = "https://www.googleapis.com/oauth2/v4/token";
        } else if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            authUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
        }

        HttpOperation httpOperation = new HttpOperation(con, TIMEOUT__);
        HttpRequestModel httpReqMdl = new HttpRequestModel();
        try {

            if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
                //クライアントID
                httpReqMdl.addParam("client_id", authData.getClientId());
                //クライアントシークレット
                httpReqMdl.addParam("client_secret", authData.getSecretKey());
                //認証種別
                httpReqMdl.addParam("grant_type", "refresh_token");
                //リフレッシュトークン
                httpReqMdl.addParam("refresh_token", authData.getRefreshToken());
            } else if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
                //クライアントID
                httpReqMdl.addParam("client_id", authData.getClientId());
                //クライアントシークレット
                httpReqMdl.addParam("client_secret", authData.getSecretKey());
                //認証種別
                httpReqMdl.addParam("grant_type", "refresh_token");
                //リフレッシュトークン
                httpReqMdl.addParam("refresh_token", authData.getRefreshToken());

                //scope
                httpReqMdl.addParam("scope", MICROSOFT_SCOPE);
            }

            HttpResponseModel httpResMdl = httpOperation.sendRequest(authUrl, httpReqMdl);
            int statusCode = httpResMdl.getStatusCode();

            if (statusCode != 200) {
                log__.warn("認証情報取得に失敗 : statusCode = " + statusCode + "\r\n"
                        + httpResMdl.getBody());

                //ステータスコード = 400の場合、リフレッシュトークンを削除
                if (statusCode == 400) {
                    authData.setAccessToken(null);
                    authData.setRefreshToken(null);
                }
            } else {
                String result = httpResMdl.getBody();
                JSONObject jsonData = JSONObject.fromObject(result);
                Iterator<String> keyList = jsonData.keys();

                while (keyList.hasNext()) {
                    String key = keyList.next();
                    String value = jsonData.getString(key);

                    if (key.equals("access_token")) {
//                    } else if (key.equals("token_type")) {
                        authData.setAccessToken(value);
                    } else if (key.equals("refresh_token")) {
                        authData.setRefreshToken(value);
                    }
                }
            }
        } finally {

            //OAuth認証トークンを更新する
            if (cotSid > 0 && couSid > 0) {
                CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
                CmnOauthTokenModel cotMdl = new CmnOauthTokenModel();
                cotMdl.setCotSid(cotSid);
                cotMdl.setCouSid(couSid);
                cotMdl.setCotAuthRtoken(authData.getRefreshToken());
                cotMdl.setCotAuthAtoken(authData.getAccessToken());
                if (authData.getAccessToken() != null) {
                    cotMdl.setCotAuthAtokenDate(new UDate());
                } else {
                    cotMdl.setCotAuthAtokenDate(null);
                }
                cotMdl.setCotAccountid(authData.getAccountId());
                cotDao.updateToken(cotMdl);
            }
        }
    }

    /**
     * <br>[機  能] 認証を行ったユーザのアカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param httpOperation HttpOperation
     * @param authData 認証情報
     * @throws Exception 実行時例外
     */
    public void setAccountData(HttpOperation httpOperation, OauthDataModel authData)
    throws Exception {

        //認証URL
        String authUrl = null;
        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            authUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo";
        } else if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            authUrl = "https://graph.microsoft.com/v1.0/me";
        }

        HttpRequestModel httpReqMdl = new HttpRequestModel();

        //アクセストークン
        httpReqMdl.addParam("access_token", authData.getAccessToken());

        if (authData.getProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            httpReqMdl.addHeader("Authorization", "Bearer " + authData.getAccessToken());
        }

        HttpResponseModel httpResModel = httpOperation.sendRequest(authUrl, httpReqMdl);
        int statusCode = httpResModel.getStatusCode();
        if (statusCode != 200) {
            //取得失敗
            throw new Exception("ユーザプロファイル取得に失敗 | statusCode = " + statusCode
                                + "\r\n" + httpResModel.getBody());
        } else {

            String result = httpResModel.getBody();
            JSONObject jsonData = JSONObject.fromObject(result);
            Iterator<String> keyList = jsonData.keys();
            while (keyList.hasNext()) {
                String key = keyList.next();
                String value = jsonData.getString(key);
                if (key.equals("user_id")) {
                    authData.setUserId(value);
                } else if (key.equals("email")) {
                    authData.setAccountId(value);
                }
            }
        }
    }

    /**
     * <br>[機  能] メール受信サーバ接続情報ModelにOAuth接続情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cotSid OAuth認証トークンSID
     * @param appRootPath アプリケーションルートパス
     * @return メール受信サーバ接続情報
     * @throws SQLException SQL実行時例外
     * @throws IOException WEBメール設定ファイルの読み込みに失敗
     * @throws Exception アクセストークンの取得に失敗
     */
    public OauthMailServerModel getReceiveAuthData(Connection con,
                                                    int cotSid,
                                                    String appRootPath)
    throws SQLException, IOException, Exception {

        OauthMailServerModel serverMdl = new OauthMailServerModel();
        int provider = 0;
        if (con != null && cotSid > 0) {
            CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
            CmnOauthTokenModel cotMdl = cotDao.select(cotSid);
            serverMdl.setAccessToken(cotMdl.getCotAuthAtoken());
            serverMdl.setRefreshToken(cotMdl.getCotAuthRtoken());
            serverMdl.setAccountId(cotMdl.getCotAccountid());
            CmnOauthDao couDao = new CmnOauthDao(con);
            CmnOauthModel couMdl = couDao.select(cotMdl.getCouSid());

            serverMdl.setClientId(couMdl.getCouAuthId());
            serverMdl.setSecret(couMdl.getCouAuthSecret());
            provider = couMdl.getCouProvider();
            serverMdl.setProvider(provider);

            //アクセストークンの再取得(最終取得時間から55分以上経過)
            UDate now = new UDate();
            UDate atokenDate = cotMdl.getCotAuthAtokenDate();
            if (atokenDate == null
            || atokenDate.getTime() + ATOKEN_INTERVAL__ <= now.getTime()) {
                OauthDataModel authData = new OauthDataModel();
                authData.setProvider(provider);
                authData.setClientId(couMdl.getCouAuthId());
                authData.setSecretKey(couMdl.getCouAuthSecret());
                authData.setRefreshToken(cotMdl.getCotAuthRtoken());
                getAccessToken(con, authData, cotSid, cotMdl.getCouSid());
                serverMdl.setAccessToken(authData.getAccessToken());
            }
        }


        //メール受信サーバ ホスト・ポートを設定
        setReceiveServerData(serverMdl, provider, appRootPath);

        return serverMdl;
    }

    /**
     * <br>[機  能] メール受信サーバ ホスト・ポートを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param serverMdl メールサーバ接続情報Model
     * @param provider プロバイダ
     * @param appRootPath アプリケーションルートパス
     */
    public void setReceiveServerData(OauthMailServerModel serverMdl, int provider,
                                    String appRootPath) {
        if (provider == GSConstCommon.COU_PROVIDER_GOOGLE) {
            serverMdl.setHost(
                    ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_RECEIVE_HOST_GOOGLE));

            serverMdl.setPort(
                    NullDefault.getInt(
                            ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_RECEIVE_PORT_GOOGLE),
                            GSConstWebmail.RECEIVE_PORT_DEFAULT));
        } else if (provider == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            serverMdl.setHost(
                    ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_RECEIVE_HOST_MICROSOFT));

            serverMdl.setPort(
                    NullDefault.getInt(
                            ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_RECEIVE_PORT_MICROSOFT),
                            GSConstWebmail.RECEIVE_PORT_DEFAULT));
        }
    }

    /**
     * <br>[機  能] メール送信サーバ接続情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cotSid OAuth認証トークンSID
     * @param appRootPath アプリケーションルートパス
     * @return メール送信サーバ接続情報Model
     * @throws SQLException SQL実行時例外
     * @throws IOException WEBメール設定ファイルの読み込みに失敗
     * @throws Exception アクセストークンの取得に失敗
     */
    public OauthMailServerModel getSendServerData(Connection con, int cotSid, String appRootPath)
            throws SQLException, IOException, Exception {
        return getSendServerData(con, cotSid, appRootPath, true);
    }

    /**
     * <br>[機  能] メール送信サーバ接続情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cotSid OAuth認証トークンSID
     * @param appRootPath アプリケーションルートパス
     * @param getTokenFlg アクセストークン取得フラグ true:取得する　false:取得しない
     * @return メール送信サーバ接続情報Model
     * @throws SQLException SQL実行時例外
     * @throws IOException WEBメール設定ファイルの読み込みに失敗
     * @throws Exception アクセストークンの取得に失敗
     */
    public OauthMailServerModel getSendServerData(Connection con,
                                int cotSid,
                                String appRootPath, boolean getTokenFlg)
    throws SQLException, IOException, Exception {

        OauthMailServerModel serverMdl = new OauthMailServerModel();
        int provider = 0;
        if (con != null && cotSid > 0) {
            CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
            CmnOauthTokenModel cotMdl = cotDao.select(cotSid);
            serverMdl.setAccessToken(cotMdl.getCotAuthAtoken());
            serverMdl.setRefreshToken(cotMdl.getCotAuthRtoken());
            serverMdl.setAccountId(cotMdl.getCotAccountid());

            CmnOauthDao couDao = new CmnOauthDao(con);
            CmnOauthModel couMdl = couDao.select(cotMdl.getCouSid());
            serverMdl.setClientId(couMdl.getCouAuthId());
            serverMdl.setSecret(couMdl.getCouAuthSecret());
            provider = couMdl.getCouProvider();
            serverMdl.setProvider(provider);

            //アクセストークンの取得
            if (getTokenFlg) {

                //アクセストークンの再取得(最終取得時間から55分以上経過)
                UDate now = new UDate();
                UDate atokenDate = cotMdl.getCotAuthAtokenDate();
                if (atokenDate == null
                || atokenDate.getTime() + ATOKEN_INTERVAL__ <= now.getTime()) {
                    OauthDataModel authData = new OauthDataModel();
                    authData.setProvider(provider);
                    authData.setClientId(couMdl.getCouAuthId());
                    authData.setSecretKey(couMdl.getCouAuthSecret());
                    authData.setRefreshToken(cotMdl.getCotAuthRtoken());
                    getAccessToken(con, authData, cotSid, cotMdl.getCouSid());
                    serverMdl.setAccessToken(authData.getAccessToken());
                }
            }
        }

        //メール送信サーバ ホスト・ポートを設定
        setSendServerData(serverMdl, provider, appRootPath);

        return serverMdl;
    }

    /**
     * <br>[機  能] メール送信サーバ ホスト・ポートを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param serverMdl メールサーバ接続情報Model
     * @param provider プロバイダ
     * @param appRootPath アプリケーションルートパス
     */
    public void setSendServerData(OauthMailServerModel serverMdl, int provider,
                                    String appRootPath) {

        if (provider == GSConstCommon.COU_PROVIDER_GOOGLE) {
            serverMdl.setHost(
                    ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_SEND_HOST_GOOGLE));

            serverMdl.setPort(
                    NullDefault.getInt(
                            ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_SEND_PORT_GOOGLE),
                            GSConstWebmail.SEND_PORT_DEFAULT));
        } else if (provider == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            serverMdl.setHost(
                    ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_SEND_HOST_MICROSOFT));

            serverMdl.setPort(
                    NullDefault.getInt(
                            ConfigBundle.getValue(GSConstCommon.MAILCONF_OAUTH_SEND_PORT_MICROSOFT),
                            GSConstWebmail.SEND_PORT_DEFAULT));
        }

    }


    /**
     * <br>[機  能] 指定したcode_valifier から code_challengeを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param codeVerifier code_valifier
     * @return code_challenge
     */
    public String createCodeChallenge(String codeVerifier) {
        String codeChallenge = null;
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                                            .digest(codeVerifier.getBytes("iso-2022-jp"));
            codeChallenge = Base64.encodeBase64String(bytes);
            codeChallenge = codeChallenge.replaceAll("=", "")
                                        .replaceAll("\\+", "-")
                                        .replaceAll("/", "_");
        } catch (UnsupportedEncodingException e) {
        } catch (NoSuchAlgorithmException e) {

        }
        return codeChallenge;
    }


    /**
     * <br>[機  能] 指定されたプロバイダの名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param couProvider プロバイダ(COU_PROVIDER)
     * @param reqMdl リクエストモデル
     * @return プロバイダ名称
     */
    public static String getProviderName(int couProvider, RequestModel reqMdl) {
        String providerName = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (couProvider == GSConstCommon.COU_PROVIDER_GOOGLE) {
            providerName = gsMsg.getMessage("cmn.cmn260.02");
        } else if (couProvider == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            providerName = gsMsg.getMessage("cmn.cmn260.03");
        }

        return providerName;
    }

    /**
     * <br>[機  能] OAuth認証トークン(CMN_OAUTH_TOKEN)の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @param authData OAuth認証情報
     * @param userSid 登録ユーザSID
     * @return cotSid
     * @throws SQLException SQL実行時例外
     */
    public int entryOAuthToken(Connection con, MlCountMtController mtCon,
                                OauthDataModel authData, int userSid)
    throws SQLException {

        //OAuth認証情報SIDが未設定、または存在しないSIDの場合、登録を行わない
        int couSid = authData.getCouSid();
        CmnOauthDao couDao = new CmnOauthDao(con);
        if (couSid <= 0 || !couDao.existOauthData(couSid)) {
            log__.warn("OAuth認証情報SIDが未設定、または存在しないSIDのため、登録処理を終了");
            return 0;
        }

        CmnOauthTokenModel cotMdl = new CmnOauthTokenModel();
        cotMdl.setCouSid(couSid);
        cotMdl.setCotAuthRtoken(authData.getRefreshToken());
        cotMdl.setCotAuthAtoken(authData.getAccessToken());
        cotMdl.setCotAuthAtokenDate(authData.getAccessTokenDate());

        MailBiz mailBiz = new MailBiz();
        String accountId = mailBiz.extractAddress(authData.getAccountId());
        cotMdl.setCotAccountid(accountId);

        boolean existFlg = false;
        int cotSid = authData.getCotSid();

        CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
        if (cotSid > 0) {
            //トークンの存在チェック
            CmnOauthTokenModel tokenMdl = cotDao.select(cotSid);
            existFlg = tokenMdl != null;
        } else {
            //同じプロバイダ、アカウントIDのトークン情報が存在するか確認
            cotSid = checkOAuthToken(con, authData.getCouSid(), accountId, false);
            existFlg = cotSid > 0;
        }

        if (existFlg) {
            //トークン情報が登録済みの場合、更新を行う
            cotMdl.setCotSid(cotSid);
            cotDao.updateToken(cotMdl);
        } else {
            //トークン情報が未登録の場合、新規登録を行う

            if (StringUtil.isNullZeroString(accountId)) {
                //アカウントIDが未設定の場合、登録を行わない
                log__.warn("アカウントIDが未設定のため、登録処理を終了");
                return 0;
            }

            cotSid = (int) mtCon.getSaibanNumber(GSConstCommon.SBNSID_COMMON,
                                        GSConstCommon.SBNSID_SUB_OAUTHTOKEN,
                                        userSid);
            cotMdl.setCotSid(cotSid);
            cotDao.insert(cotMdl);
        }

        return cotSid;
    }


    /**
     * <br>[機  能] 同じプロバイダ、アカウントIDのOAuth認証トークン情報が登録済みかを確認
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param couSid OAuth認証情報SID
     * @param address アカウントID(メールアドレス)
     * @param checkToken リフレッシュトークンの有無確認フラグ
     * @return 登録済みの場合、登録データのOAuth認証トークンSID、未登録の場合は0を返す
     * @throws SQLException SQL実行時例外
     */
    public int checkOAuthToken(Connection con, int couSid, String address, boolean checkToken)
    throws SQLException {

        int cotSid = 0;
        if (couSid <= 0 || StringUtil.isNullZeroString(address)) {
            return cotSid;
        }

        MailBiz mailBiz = new MailBiz();
        String accountId = mailBiz.extractAddress(address);

        CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
        CmnOauthTokenModel cotMdl = cotDao.getOauthToken(couSid, accountId);
        if (cotMdl != null) {
            if (!checkToken || cotMdl.getCotAuthRtoken() != null) {
                cotSid = cotMdl.getCotSid();
            }
        }

        return cotSid;
    }

    /**
     * <br>[機  能] 認証情報データの保存を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param authData 認証情報データ
     * @param pluginId プラグインID
     * @param screenId 画面ID
     * @param reqMdl リクエスト情報
     * @throws IOToolsException 認証情報ファイル保存先ディレクトリの作成に失敗
     */
    public void saveAuthData(OauthDataModel authData, RequestModel reqMdl,
                            String pluginId, String screenId)
    throws IOToolsException {

        File filePath = new File(getAuthDataPath(reqMdl, pluginId, screenId));
        ObjectFile objFile = new ObjectFile(filePath.getParent(), filePath.getName());
        objFile.save(authData);
    }

    /**
     * <br>[機  能] OAuth認証トークン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cotSid OAuth認証トークンSID
     * @return OauthDataModel OAuth認証情報
     * @throws SQLException SQL実行時例外
     */
    public OauthDataModel getAuthData(Connection con, int cotSid)
    throws SQLException {
        CmnOauthTokenDao oauthDao = new CmnOauthTokenDao(con);
        CmnOauthTokenModel oauthMdl = oauthDao.select(cotSid);

        OauthDataModel authData = null;

        if (oauthMdl != null) {

            authData = new OauthDataModel();
            authData.setCotSid(cotSid);
            authData.setCouSid(oauthMdl.getCouSid());
            authData.setAccountId(oauthMdl.getCotAccountid());
            authData.setAccessToken(oauthMdl.getCotAuthAtoken());
            authData.setRefreshToken(oauthMdl.getCotAuthRtoken());
            authData.setAccountId(oauthMdl.getCotAccountid());

            CmnOauthDao couDao = new CmnOauthDao(con);
            CmnOauthModel couMdl = couDao.select(authData.getCouSid());
            authData.setClientId(couMdl.getCouAuthId());
            authData.setSecretKey(couMdl.getCouAuthSecret());
            authData.setProvider(couMdl.getCouProvider());

        }

        return authData;
    }

    /**
     * <br>[機  能] 認証情報データの読み込みを行う
     * <br>[解  説] テンポラリディレクトリに一時保管した情報の読み込みを行う
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param pluginId プラグインID
     * @param screenId 画面ID
     * @return 認証情報データ
     * @throws IOToolsException 認証情報データの読み込みに失敗
     */
    public OauthDataModel loadAuthData(RequestModel reqMdl, String pluginId, String screenId)
    throws IOToolsException {

        File filePath = new File(getAuthDataPath(reqMdl, pluginId, screenId));
        ObjectFile objFile = new ObjectFile(filePath.getParent(), filePath.getName());

        Object authData = null;
        try {
            authData = objFile.load();
        } catch (IOToolsException e) {
            return null;
        }

        return (OauthDataModel) authData;
    }

    /**
     * <br>[機  能] 認証情報ファイル保存先パスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param pluginId プラグインID
     * @param screenId 画面ID
     * @return 認証情報ファイル保存先パス
     * @throws IOToolsException 認証情報ファイル保存先ディレクトリの作成に失敗
     */
    public String getAuthDataPath(RequestModel reqMdl, String pluginId, String screenId)
    throws IOToolsException {

        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                pluginId,
                                                screenId);

        IOTools.isDirCheck(tempDir, true);

        return tempDir + GSConstCommon.AUTH_FILENAME;
    }

    /**
     * <br>[機  能] 認証トークン情報の初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cotSid 認証トークン情報SID
     * @throws Exception 設定に失敗
     */
    public void resetToken(Connection con,
                        int cotSid)
    throws Exception {
        boolean existFlg = false;
        CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
        //トークンの存在チェック
        CmnOauthTokenModel tokenMdl = cotDao.select(cotSid);
        existFlg = tokenMdl != null;
        if (existFlg) {
            CmnOauthTokenModel cotMdl = new CmnOauthTokenModel();
            cotMdl.setCotSid(cotSid);
            cotMdl.setCotAuthRtoken(null);
            cotMdl.setCotAuthAtoken(null);
            cotMdl.setCotAuthAtokenDate(null);
            cotDao.updateToken(cotMdl);
        }
    }

    /**
     * <br>[機  能] 認証トークン情報の初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param authData 認証情報
     * @return cotSid 認証トークン情報SID
     * @throws Exception 設定に失敗
     */
    public int getToken(Connection con, OauthDataModel authData)
    throws Exception {
        MailBiz mailBiz = new MailBiz();
        String accountId = mailBiz.extractAddress(authData.getAccountId());
        int cotSid = authData.getCotSid();
        if (cotSid <= 0) {
            //同じプロバイダ、アカウントIDのトークン情報が存在するか確認
            cotSid = checkOAuthToken(con, authData.getCouSid(), accountId, false);
        }
        return cotSid;
    }  
}
