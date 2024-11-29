package jp.groupsession.v2.cmn.cmn001;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.lang.ClassUtil;
import jp.co.sjts.util.struts.GSHttpServletRequestWrapper;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ILoginLogoutListener;
import jp.groupsession.v2.cmn.LoginLogoutListenerUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.cmn004.Cmn004Form;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLoginConfDao;
import jp.groupsession.v2.cmn.login.IHaveAddedLogin;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.login.biz.ITokenAuthBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginConfModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.usr060.IUsr060Param;
import jp.groupsession.v2.usr.usr060.Usr060Form;

/**
 * <br>[機  能] ログイン画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn001Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn001Action.class);
    /** リダイレクト済み*/
    protected boolean isNonRedirected_ = true;

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        Cmn001Form thisForm = (Cmn001Form) form;
        //ログイン失敗回数が規定回数を超えていた場合、ロックアウトを行う
        int loginFailCount = Integer.parseInt(__getLoginFailCount(req));
        if (loginFailCount > 0) {
            con.setAutoCommit(true);
            CmnLoginConfDao loginConfDao = new CmnLoginConfDao(con);
            CmnLoginConfModel loginConfData = loginConfDao.getData();
            con.setAutoCommit(false);

            if (loginConfData != null
            && loginConfData.getLlcLockoutKbn() == GSConstCommon.LOGIN_LOCKKBN_LOCKOUT
            && loginFailCount >= loginConfData.getLlcFailCnt()) {
                thisForm.setCmn001LoginCtrl(Cmn001Form.LOGIN_CTRL_NG);
                return map.getInputForward();
            }
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd.trim();

        //初回アクセス
        boolean initAccess = thisForm.getCmn001initAccess() != Cmn001Form.INITACCESS_ACCESSED;
        thisForm.setCmn001initAccess(Cmn001Form.INITACCESS_ACCESSED);

        log__.debug("CMD = " + cmd);
        ActionForward forward = null;
        if (cmd.equals("login")) {
            //ログイン
            forward = __doLogin(map, thisForm, req, res, con);
        } else if (cmd.equals("tokenLogin")) {
            forward = __doTokenLogin(map, thisForm, req, res, con);
        } else if (cmd.equals("logout")) {

            //ロゴ画像バイナリSIDを取得
            __getImgBinSid(map, thisForm, req, res, con);

            //ログアウト
            forward = __doLogout(map, thisForm, req, res, con);
            ILogin loginBiz = _getLoginInstance();

            if (loginBiz.isPopup()) {
                //別ウィンドウで表示している場合、共通メッセージ画面へ遷移する
                Cmn999Form cmn999Form = new Cmn999Form();
                cmn999Form.setType(Cmn999Form.TYPE_OK);
                cmn999Form.setIcon(Cmn999Form.ICON_INFO);
                cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
                cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
                GsMessage gsMsg = new GsMessage(getRequestModel(req));
                cmn999Form.setMessage(gsMsg.getMessage("cmn.logout.comp.popup"));
                req.setAttribute("cmn999Form", cmn999Form);
                forward = map.findForward("gf_msg");
            } else {
                saveToken(req);
            }
        } else if (cmd.equals("getImageFile")) {

            //ロゴ画像の読込
            __doGetImageFile(map, thisForm, req, res, con);

        } else {
            //初回アクセス時、セッションが確立していない、かつ自動ログインONの場合、ログイン処理を行う
            ILogin loginBiz = _getLoginInstance();
            LoginModel loginModel = __createLoginModel(map, thisForm, req, con);

            //リダイレクト失敗時
            if (cmd.equals("getError")) {
                ActionErrors errors = new ActionErrors();
                ActionMessage msg = new ActionMessage("error.auth.notfound.idpass");
                StrutsUtil.addMessage(errors, msg, "error.auth.notfound.idpass");
                addErrors(req, errors);
            }

            //urlパラメータがある場合に別のログインURLにリダイレクトが必要の場合
            if (initAccess && !isSession(req)
                    && !StringUtil.isNullZeroString(thisForm.getUrl())
                    && loginBiz instanceof IHaveAddedLogin
                    && ((IHaveAddedLogin) loginBiz).isRedirectAddedLogin()
                    && isNonRedirected_) {
                String path = ((IHaveAddedLogin) loginBiz).getAddedLoginPage();
                path += "?url=" + URLEncoder.encode(thisForm.getUrl(), Encoding.UTF_8);
                res.sendRedirect(path);
                return null;
            } else if (initAccess && !isSession(req) && loginBiz.isAutoLogin()) {
                LoginResultModel resultModel = loginBiz.autoLogin(loginModel);
                if (!resultModel.isResult()) {
                    forward = loginBiz.getAutoLoginFailPage(map);
                    if (forward == null) {
                        return getAuthErrorPage(map, req);
                    }
                }
            }

            //セッション確立済みの場合はメインページへ遷移する
            if (isSession(req)) {

                String forword = loginBiz.checkForwordPassowrd(
                        getSessionUserModel(req),
                        con);
                return map.findForward(forword);
            }

            if (!loginBiz.isAutoLogin() || initAccess) {
                //ロゴ画像バイナリSIDを取得
                __getImgBinSid(map, thisForm, req, res, con);

                //デフォルト ログアウトも含む
                forward = __doLogout(map, thisForm, req, res, con);

                saveToken(req);
            }
        }

        GSHttpServletRequestWrapper wrapper
        = new GSHttpServletRequestWrapper(req);
        String domain = GroupSession.getResourceManager().getDomain(req);
        if (!StringUtil.isNullZeroString(domain)) {
            if (wrapper.getSession(false) == null) {
                wrapper.getSession(true);
            }
            wrapper.getSession(false).setAttribute("domain", domain);
        }

        //ZION ログイン画面リンク表示設定
        boolean pgdb = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES);
        if (pgdb) {
            String appRootPath = getAppRootPath();
            Cmn001ParamModel paramMdl = new Cmn001ParamModel();
            Cmn001Biz biz = new Cmn001Biz();
            paramMdl.setParam(thisForm);
            biz.setConfData(paramMdl, appRootPath, getRequestModel(req), con, map);
            paramMdl.setFormData(thisForm);
        }

        log__.debug("END");
        return forward;
    }
    /**
     *
     * <br>[機  能] APIで発行したトークンでログインを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doTokenLogin(ActionMapping map, Cmn001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        ActionErrors errors = new ActionErrors();
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

        //API使用不可の場合ApiTokenによるログイン不可
        if (pconf == null || pconf.getPlugin(GSConst.PLUGINID_API) == null) {
            String pluginName
            = getPluginConfig(req).getPlugin(GSConst.PLUGINID_API).getName(reqMdl);
            ActionMessage msg = new ActionMessage("error.cant.use.plugin", pluginName);
            StrutsUtil.addMessage(errors, msg, "error.cant.use.plugin");

            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }
        /**ApiToken認証クラス*/
        ITokenAuthBiz tokenBiz = (ITokenAuthBiz) ClassUtil.getDefConstractorObject(
                "jp.groupsession.v2.api.biz.ApiTokenBiz");
        //API設定によるトークン使用制限チェック
        if (!tokenBiz.isAbleTokenAuth(req, con)) {
            ActionMessage msg =   new ActionMessage("error.cant.use.token");
            StrutsUtil.addMessage(errors, msg, "error.cant.use.token");
            addErrors(req, errors);
            saveToken(req);
            return map.getInputForward();
        }

        LoginResultModel result = tokenBiz.authToken(form.getCmn001Token(), con);
        //認証エラー時
        if (result.getErrCode() != LoginResultModel.ECODE_NONE) {
            ActionForward forward;
            ActionForward urlForward = map.findForward("gf_domain_logout");
            Cmn999Form cmn999Form = new Cmn999Form();
            MessageResources msgRes = getResources(req);
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());

            cmn999Form.addHiddenAll(form, Cmn001Form.class, "");
            errors = result.getErrors();
            if (errors != null) {
                @SuppressWarnings("rawtypes")
                Iterator it = errors.get();
                if (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    cmn999Form.setMessage(msgRes.getMessage(error.getKey(), error.getValues()));
                }
            }
            req.setAttribute("cmn999Form", cmn999Form);

            forward = map.findForward("gf_msg");

            return forward;
        }

        BaseUserModel umodel = result.getLoginUser();
        GSLoginBiz cmnLoginBiz = new GSLoginBiz();
        cmnLoginBiz.doLogin(req, umodel, con, pconf);

        ILogin loginBiz = _getLoginInstance();

        //ログイン後遷移先設定
        String forwardName = loginBiz.checkForwordPassowrd(umodel, con);
        return map.findForward(forwardName);
    }

    /**
     * <p>ログイン
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doLogin(ActionMapping map, Cmn001Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        //ロゴ画像バイナリSIDを取得
        __getImgBinSid(map, form, req, res, con);

        //入力チェック
        ActionErrors errors = form.validateLogin(map, getRequestModel(req));
        if (errors.size() > 0) {
            //ロックアウト処理を行う
            __lockout(req, res, form, con);

            addErrors(req, errors);

            saveToken(req);
            return map.getInputForward();
        }

        //２重投稿チェック
        if (form.getCmn001loginType() == Cmn001Form.LOGIN_TYPE_SCREEN
        && !isTokenValid(req, true)) {
            __doLogout(map, form, req, res, con);
            return getAuthErrorPage(map, req);
        }

        //ログイン処理に必要な情報を設定する
        __resetMenu(req, res);

        LoginModel loginModel = __createLoginModel(map, form, req, con);

        con.setAutoCommit(false);

        //ログイン処理を行う
        ILogin loginBiz = _getLoginInstance();
        LoginResultModel resultModel = loginBiz.idpassAuth(loginModel);
        //ログインユーザの所属グループが未設定だった場合、ログイン不可
        if (resultModel.getErrCode() == LoginResultModel.ECODE_BELONGGRP_NONE) {
            return __doForwardGroupErrorPage(map, req, res, "error.auth.notset.belonggroup");
        }
        //ログインユーザのデフォルトグループが未設定だった場合、ログイン不可
        if (resultModel.getErrCode() == LoginResultModel.ECODE_DEFGRP_NONE) {
            return __doForwardGroupErrorPage(map, req, res, "error.auth.notset.defgroup");
        }

        GsMessage gsMsg = new GsMessage(req);
        String login = gsMsg.getMessage(req, "mobile.17");

        FirewallBiz firewall = FirewallBiz.getInstance();
        if (!firewall.additionalCheckForMbl(resultModel.getLoginUser(), con, false)) {
            return firewall.handlingFirewallError(
                () -> {
                    try {
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                    } catch (IOException e) {
                    }
                    return null;
                },
                () -> {
                    return getClientErrorPage(map, req);
                });
        }
        //ワンタイムパスワード入力が求められた場合
        if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_OTP) {
            return __doForwardOtp(map, resultModel, req, res, con);
        }
        //ログイン処理に失敗 かつ ActionErrorsが設定されている場合
        boolean loginError = false;
        ActionForward forward;
        if (!resultModel.isResult() && resultModel.getErrors() != null) {
            //ロックアウト処理を行う
            con.setAutoCommit(true);
            __lockout(req, res, form, con);
            con.setAutoCommit(false);

            loginError = true;
            addErrors(req, resultModel.getErrors());
            saveToken(req);
            forward = resultModel.getForward();
        } else {
            //ログイン失敗回数をクリアする
            __setCookie(req, res, "0", 0);

            BaseUserModel smodel = resultModel.getLoginUser();
            PluginConfig pconfig = loginModel.getPluginConfig();
            GSLoginBiz cmnLoginBiz = new GSLoginBiz();
            //ログイン共通処理
            cmnLoginBiz.doLogin(req, smodel, con, pconfig);
            //ログイン後遷移先設定
            String forwardName = loginBiz.checkForwordPassowrd(smodel, con);
            forward =  map.findForward(forwardName);

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    login, GSConstLog.LEVEL_INFO,
                    NullDefault.getString(form.getCmn001Userid(), ""));

        }

        //GETでアクセスされた時、リダイレクト
        if (req.getMethod().equals("GET")) {
            String path = GroupSession.getResourceManager().getRedirectUrl(req);

            String cmnurl = null;
            String errCmd = "getError";

            if (form.getUrl() != null && !form.getUrl().isEmpty()) {
                cmnurl = URLEncoder.encode(form.getUrl(), Encoding.UTF_8);
                path += "?url=" + cmnurl;
            }
            if (loginError) {
                if (path.contains("?url=")) {
                    path += "&CMD=" + errCmd;
                } else {
                    path += "?CMD=" + errCmd;
                }
            }

            res.sendRedirect(path);
        }


        return forward;
    }

    /**
     * <p>ログアウト
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doLogout(
        ActionMapping map,
        Cmn001Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd.trim();

        //ログインユーザSIDを取得
        int userSid = -1;
        String loginId = "";
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
            loginId = buMdl.getLgid();
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        String logout = gsMsg.getMessage("mobile.11");

        if (cmd.equals("logout") && buMdl != null) {
            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    logout, GSConstLog.LEVEL_INFO,
                    loginId);
        }

        //未ログインの場合はリスナーを呼び出さない
        if (userSid != -1) {
            boolean commitFlg = false;
            try {

                //ログイン、ログアウトリスナー取得
                log__.debug("ログアウトリスナー doLogout()開始");
                //使用可能プラグイン情報を取得
                PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
                ILoginLogoutListener[] lis =
                    LoginLogoutListenerUtil.getLoginLogoutListeners(pconf);
                //各プラグインリスナーを呼び出し
                for (int i = 0; i < lis.length; i++) {
                    lis[i].doLogout(req.getSession(), con, userSid,
                            GroupSession.getResourceManager().getDomain(req));
                }
                commitFlg = true;
                log__.debug("ログアウトリスナー doLogout()終了");
            } catch (ClassNotFoundException e) {
                log__.error("リスナー起動に失敗しました。", e);
            } catch (IllegalAccessException e) {
                log__.error("リスナー起動に失敗しました。", e);
            } catch (InstantiationException e) {
                log__.error("リスナー起動に失敗しました。", e);
            } catch (SQLException e) {
                log__.error("リスナー起動に失敗しました。", e);
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }

                //セッション破棄
                removeSession(req);
            }
        }

        //ログアウト後の画面を表示
        return map.getInputForward();
    }

    /**
     * <p>
     * セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>
     * サブクラスでこのメソッドをオーバーライドして使用する
     *
     * @param req
     *            リクエスト
     * @param form
     *            アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }
    /**
     * <p>アクセス可能なドメインかを判定
     * @param req リクエスト
     * @return true:許可する,false:許可しない
     */
    public boolean canAccessDomain(HttpServletRequest req) {
        return true;
    }

    /**
     * <br>[機  能] ロックアウト処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param form フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException URLEncode時の文字コードが不正
     */
    private void __lockout(HttpServletRequest req, HttpServletResponse res,
                            Cmn001Form form, Connection con)
    throws SQLException, UnsupportedEncodingException {

        CmnLoginConfDao loginConfDao = new CmnLoginConfDao(con);
        CmnLoginConfModel loginConfData = loginConfDao.getData();

        if (loginConfData == null
        || loginConfData.getLlcLockoutKbn() == GSConstCommon.LOGIN_LOCKKBN_NOSET) {
            return;
        }

        String failCount = __getLoginFailCount(req);
        failCount = String.valueOf(Integer.parseInt(failCount) + 1);

        int time = loginConfData.getLlcFailAge();
        if (Integer.parseInt(failCount) >= loginConfData.getLlcFailCnt()) {
            form.setCmn001LoginCtrl(Cmn001Form.LOGIN_CTRL_NG);
            time = loginConfData.getLlcLockAge();
        }

        __setCookie(req, res, failCount, time * 60);
    }

    /**
     * <br>[機  能] ログイン失敗回数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ログイン失敗回数
     */
    private String __getLoginFailCount(HttpServletRequest req) {
        String failCount = "0";

        //全てのクッキー情報を取得
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            //必要なクッキー情報を取得
            for (Cookie cookie : cookies) {
                String cname = cookie.getName();
                cname = __decodeCookies(cname);
                if (!StringUtil.isNullZeroString(cname)
                && cname.equals(GSConstCommon.LOCKOUT_FAILCOUNT)) {
                    failCount = NullDefault.getString(__decodeCookies(cookie.getValue()), "0");
                    break;
                }
            }

            if (!ValidateUtil.isNumber(failCount)
            || (new BigInteger(failCount)).compareTo(
                    new BigInteger(String.valueOf(Integer.MAX_VALUE))) > 0) {
                failCount = "0";
            }
        }
        return failCount;
    }

    /**
     * <br>[機  能] Cookieを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param failCount ログイン失敗回数
     * @param maxAge Cookie の最長存続期間
     * @throws UnsupportedEncodingException URLEncode時の文字コードが不正
     */
    private void __setCookie(HttpServletRequest req, HttpServletResponse res,
                            String failCount, int maxAge)
    throws UnsupportedEncodingException {

        String value = null;
        try {
            value = URLEncoder.encode(failCount, GSConst.ENCODING);
            log__.info("__setLoginFailedCookie.value==>" + value);
        } catch (UnsupportedEncodingException e) {
            log__.error("URLEncodeに失敗", e);
            throw e;
        }

        Cookie cookie = new Cookie(GSConstCommon.LOCKOUT_FAILCOUNT, value);
        // Cookie の最長存続期間
        cookie.setMaxAge(maxAge);

        // Cookie のPATH
        String cookiePath = req.getRequestURI();
        if (cookiePath.startsWith("/")) {
            cookiePath = cookiePath.substring(1);
        }
        cookiePath = "/" + cookiePath.substring(0, cookiePath.indexOf("/"));
        cookie.setPath(cookiePath);

        res.addCookie(cookie);
    }
    /**メニュー画面の状態をリセットする*/
    private void __resetMenu(HttpServletRequest req, HttpServletResponse res)
                    throws UnsupportedEncodingException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            //必要なクッキー情報を取得
            for (Cookie cookie : cookies) {
                if (GSConstCommon.COOKIE_MENULOCK.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    res.addCookie(cookie);
                    continue;
                }
                if (GSConstCommon.COOKIE_MENUOPEN.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    res.addCookie(cookie);
                    continue;
                }
            }
        }

    }


    /**
     * <p>クッキーの値をURLEncode前の文字列にデコードする
     * @param cookie クッキーの値
     * @return デコードした文字列
     */
    private String __decodeCookies(String cookie) {
        //NULLチェック
        if (cookie == null) {
            return null;
        }

        String ret = null;
        try {
            ret = URLDecoder.decode(cookie, "Shift_JIS");
        } catch (UnsupportedEncodingException e) {
        } catch (IllegalArgumentException e) {
        }
        return ret;
    }

    /**
     * <br>[機  能] ロゴ画像バイナリSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @throws Exception 実行時例外
     */
    private void __getImgBinSid(ActionMapping map,
                                            Cmn001Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        Cmn001Biz biz = new Cmn001Biz();
        CmnEnterInfModel model = biz.getEnterData(con);
        Long logoBinSid = Long.valueOf(0L);
        //URL
        String entUrl = GSConst.GS_HOMEPAGE_URL;
        if (model != null) {
            if (model.getEniImgKbn() == 1) {
                logoBinSid = model.getBinSid();
            } else {
                //デフォルトロゴ
                logoBinSid = Long.valueOf(0L);
            }

            //DB内のURLが既定のフォーマットと一致する場合、ロゴ押下時のURLに設定する
            if (ValidateUtil.isHttpUrlFormat(model.getEniUrl())
                    && !StringUtil.isNullZeroString(model.getEniUrl())) {
                entUrl = model.getEniUrl();
            }
        }
        form.setCmn001BinSid(logoBinSid);

        //URL
        form.setCmn001Url(entUrl);
    }

    /**
     * <br>[機  能] ロゴ画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Cmn001Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel cbMdl = null;

        //画像バイナリSIDの照合チェック
        Cmn001Biz biz = new Cmn001Biz();

        Cmn001ParamModel paramModel = new Cmn001ParamModel();
        paramModel.setParam(form);
        boolean logoHnt = biz.checkLogoBinSid(con, paramModel);
        paramModel.setFormData(form);

        if (!logoHnt) {
            return null;

        } else {
            cbMdl = cmnBiz.getBinInfo(con, form.getCmn001BinSid(),
                    GroupSession.getResourceManager().getDomain(req));
        }

        if (cbMdl != null) {
            JDBCUtil.closeConnectionAndNull(con);

            //ファイルをダウンロードする
            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                        Encoding.UTF_8);
        }
        return null;
    }


    /**
     * <p>ログイン処理時に使用する情報を設定する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    private LoginModel __createLoginModel(ActionMapping map, Cmn001Form form,
                                                            HttpServletRequest req,
                                                            Connection con)
    throws SQLException {
        //ログイン処理に必要な情報を設定する
        LoginModel loginModel = new LoginModel();
        loginModel.setLoginId(form.getCmn001Userid());
        loginModel.setPassword(form.getCmn001Passwd());
        loginModel.setCon(con);
        loginModel.setReq(req);
        loginModel.setMap(map);
        //使用可能プラグイン情報を取得
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        loginModel.setPluginConfig(pconf);

        return loginModel;
    }
    /**
     * <br>[機  能] ワンタイムパスワード画面へ遷移
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param login ログイン結果
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doForwardOtp(ActionMapping map, LoginResultModel login,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
        OtpSendResult result = onetimePasswordBiz.sendOtp(login.getLoginUser(),
                getRequestModel(req), con);
        ActionForward forward;
        ActionForward urlForward = map.findForward("gf_domain_logout");
        Cmn999Form cmn999Form = new Cmn999Form();
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setUrlOK(urlForward.getPath());

        switch (result.getEcode()) {
            case OtpSendResult.ECODE_NONE:
                forward = map.findForward("cmn004");
                Cmn004Form cmn004Form = new Cmn004Form();
                cmn004Form.setCmn004Token(result.getOtpToken());
                req.setAttribute("cmn004Form", cmn004Form);
                return forward;

            case OtpSendResult.ECODE_SENDERROR:
                cmn999Form.setIcon(Cmn999Form.ICON_WARN);
                cmn999Form.setMessage(msgRes.getMessage("error.smtp.otppass.send"));
                forward = map.findForward("gf_msg");
                req.setAttribute("cmn999Form", cmn999Form);
                return forward;

            case OtpSendResult.ECODE_NOADDRES_CANTEDIT:
                cmn999Form.setIcon(Cmn999Form.ICON_WARN);
                //メッセージセット
                cmn999Form.setMessage(msgRes.getMessage("error.brank.address.otpass.send"));
                forward = map.findForward("gf_msg");
                req.setAttribute("cmn999Form", cmn999Form);
                return forward;

            case OtpSendResult.ECODE_NOADDRES_ABLEEDIT:
                forward = map.findForward("usr060");
                IUsr060Param usr060Form = new Usr060Form();
                usr060Form.setUsr060Token(result.getOtpToken());
                HttpSession session = req.getSession(true);
                session.setAttribute(GSConst.SESSION_KEY, null);
                req.setAttribute("usr060Form", usr060Form);
                return forward;

            default:
        }

        addErrors(req, login.getErrors());

        saveToken(req);
        return map.getInputForward();

    }
    /**
     * <br>[機  能] 所属グループ or デフォルトグループ未設定エラー画面へ遷移
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param req リクエスト
     * @param res レスポンス
     * @param msgKey エラーメッセージキー
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doForwardGroupErrorPage(ActionMapping map,
            HttpServletRequest req, HttpServletResponse res,
            String msgKey)
            throws Exception {
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(msgRes.getMessage(msgKey));

        ActionForward urlForward = map.findForward("gf_logout");
        cmn999Form.setUrlOK(urlForward.getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}