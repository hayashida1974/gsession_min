package jp.groupsession.v2.cmn.cmn270;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionRedirect;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;

/**
 * <br>[機  能] OAuth認証画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn270Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn270Biz.class);

    /** 画面ID */
    public static final String SCR_ID = "cmn270";

    /**
     * <br>[機  能] 認証ページへのリダイレクト設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @return ActionForward
     * @throws Exception 設定に失敗
     */
    public ActionForward setRedirectAuth(Connection con,
            RequestModel reqMdl,
            Cmn270ParamModel paramMdl)
    throws Exception {

        //認証情報を取得する
        int couSid = paramMdl.getCmn270AuthSid();
        CmnOauthDao authDao = new CmnOauthDao(con);
        CmnOauthModel authMdl = authDao.select(couSid);

        //指定されたプロバイダが存在しない場合、エラーとする
        if (authMdl == null) {
            return null;
        }

        String redirectUrl = getRedirectUrl(reqMdl);
        String mailAddress = NullDefault.getString(paramMdl.getCmn270MailAddress(), "");
        MailBiz mailBiz = new MailBiz();
        mailAddress = mailBiz.extractAddress(mailAddress);

        Random generator = new Random();
        String state = String.valueOf(generator.nextInt(Integer.MAX_VALUE));
        String codeVerifier = UUID.randomUUID().toString();

        OAuthBiz authBiz = new OAuthBiz();
        String codeChallenge = authBiz.createCodeChallenge(codeVerifier);

        ActionRedirect forward = null;
        if (authMdl.getCouProvider() == GSConstCommon.COU_PROVIDER_GOOGLE) {
            forward = new ActionRedirect(
                    "https://accounts.google.com/o/oauth2/auth");

            forward.addParameter("client_id", authMdl.getCouAuthId());
            forward.addParameter("redirect_uri", redirectUrl);
            forward.addParameter("scope",
                            "https://mail.google.com/"
                            + " https://www.googleapis.com/auth/userinfo.email");
            forward.addParameter("response_type", "code");
            forward.addParameter("approval_prompt", "force");
            forward.addParameter("access_type", "offline");

            forward.addParameter("state", state);
            forward.addParameter("code_challenge", codeChallenge);
            forward.addParameter("code_challenge_method", "S256");
            forward.addParameter("login_hint", mailAddress);

        } else if (authMdl.getCouProvider() == GSConstCommon.COU_PROVIDER_MICROSOFT) {
            forward = new ActionRedirect(
                    "https://login.microsoftonline.com/common/oauth2/v2.0/authorize");

            forward.addParameter("client_id", authMdl.getCouAuthId());
            forward.addParameter("response_type", "code");
            forward.addParameter("redirect_uri", redirectUrl);
            forward.addParameter("response_mode", "query");
            forward.addParameter("scope", OAuthBiz.MICROSOFT_SCOPE);
            forward.addParameter("state", state);
            forward.addParameter("code_challenge", codeChallenge);
            forward.addParameter("code_challenge_method", "S256");
            forward.addParameter("prompt", "consent");
            forward.addParameter("login_hint", mailAddress);
        }

        //認証情報を保存する
        OauthDataModel authData = new OauthDataModel();
        authData.setClientId(authMdl.getCouAuthId());
        authData.setSecretKey(authMdl.getCouAuthSecret());
        authData.setCouSid(couSid);
        authData.setProvider(authMdl.getCouProvider());
        authData.setPluginId(paramMdl.getCmn270PluginId());
        authData.setScreenId(paramMdl.getCmn270ParentId());
        authData.setAccountId(mailAddress);
        authData.setRedirectUrl(redirectUrl);
        authData.setState(state);
        authData.setCodeVerifier(codeVerifier);
        authData.setCotSidParamName(paramMdl.getCmn270cotSidParamName());
        authBiz.saveAuthData(authData, reqMdl, GSConstCommon.PLUGIN_ID_COMMON, SCR_ID);

        forward.setRedirect(true);

        return forward;
    }

    /**
     * <br>[機  能] 認証トークン情報等の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws Exception 設定に失敗
     */
    public void getToken(Connection con,
                        MlCountMtController mtCon,
                        RequestModel reqMdl,
                        Cmn270ParamModel paramMdl)
    throws Exception {

        OAuthBiz authBiz = new OAuthBiz();
        try {
            //認証情報の読み込みを行う
            OauthDataModel authData
                = authBiz.loadAuthData(reqMdl, GSConstCommon.PLUGIN_ID_COMMON, SCR_ID);

            //認証情報が保存されているかを確認
            if (authData == null) {
                log__.warn("認証情報が存在しません。");
                return;
            }
            
            //認証トークン情報 事前取得(認可ページにてキャンセル時、既に認証済みのアカウントだった場合に初期化する為に必要)
            paramMdl.setCotSid(authBiz.getToken(con, authData));

            //認可ページからのパラメータに「認可コード」が含まれるかを確認
            String code = paramMdl.getCode();
            if (StringUtil.isNullZeroString(code)) {
                log__.warn("認可コードが設定されていません。");
                return;
            }

            //認可コードを設定
            authData.setCode(code);

            //トークンの取得を行う
            authData = authBiz.getAuthData(con, authData);

            //OAuth認証情報トークンを登録/更新する
            int cotSid = authBiz.entryOAuthToken(con, mtCon, authData,
                                                reqMdl.getSmodel().getUsrsid());
            paramMdl.setCotSid(cotSid);
            paramMdl.setCmn270cotSidParamName(authData.getCotSidParamName());

            //認証が正常に完了した場合、認証完了フラグを設定する
            paramMdl.setCmn270AuthComplete(1);
        } catch (Exception e) {
            log__.error("認証トークン情報の取得に失敗", e);
        } finally {
            //取得処理完了後、「自画面の」テンポラリディレクトリを削除する
            File authFilePath
                = new File(authBiz.getAuthDataPath(reqMdl, GSConstCommon.PLUGIN_ID_COMMON,
                                                    Cmn270Biz.SCR_ID));
            IOTools.deleteDir(authFilePath.getParent());
        }
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

        OAuthBiz authBiz = new OAuthBiz();
        try {
            authBiz.resetToken(con, cotSid);
        } catch (Exception e) {
            log__.error("認証トークン情報の初期化に失敗", e);
        }
    }

    /**
     * <br>[機  能] 認証時のリダイレクトURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return リダイレクトURL
    * @throws URISyntaxException リクエストURL取得例外
     */
    public String getRedirectUrl(RequestModel reqMdl) throws URISyntaxException {
        String redirectUrl = AccessUrlBiz.getInstance().getBaseUrl(reqMdl);
        redirectUrl += "common/cmn270.do";

        return redirectUrl;
    }
}
