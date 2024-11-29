package jp.groupsession.v2.cmn.cmn004;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnLoginConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginConfModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ワンタイムパスワード入力画面 アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn004Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn004Action.class);

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

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        Cmn004Form thisForm = (Cmn004Form) form;
        //アクセス権限チェック
        ActionForward forward = __immigration(map, thisForm, req, res, con);
        if (forward != null) {
            return forward;
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd.trim();
        if (cmd.equals("cmn004OK")) {
            //ログイン
            return __doLogin(map, thisForm, req, res, con);
        } else {
            return __doInit(map, thisForm, req, res, con);
        }
    }
    /**
     *
     * <br>[機  能] アクセス権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return 遷移先 エラーなしはnull
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __immigration(ActionMapping map, Cmn004Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        ActionForward ret = null;
        String token = form.getCmn004Token();
        //トークン未設定
        if (StringUtil.isNullZeroString(token)) {
            return getSubmitErrorPage(map, req);
        }
        
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        LoginResultModel result = otpBiz.checkTokenLive(token, con);
        //トークンが不正
        if (result.getErrCode() == LoginResultModel.ECODE_TOKENNONE) {
            return getSubmitErrorPage(map, req);
        }
        
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        if (result.getErrCode() == LoginResultModel.ECODE_TOKENTIMEOVER) {
            //一次トークンの有効期限切れ
            ActionForward forward;
            ActionForward urlForward = map.findForward("gf_domain_logout");
            Cmn999Form cmn999Form = new Cmn999Form();
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(urlForward.getPath());
            req.setAttribute("cmn999Form", cmn999Form);
            cmn999Form.setMessage(gsMsg.getMessage("cmn.cmn004.error.limitover"));
            forward = map.findForward("gf_msg");
            return forward;
        }
        
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
                return map.findForward("gf_domain_logout");
            }
        }

        return ret;
    }
    @Override
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }
    @Override
    public boolean canAccessDomain(HttpServletRequest req) {
        return true;
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
     * <p>ログイン
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doLogin(ActionMapping map, Cmn004Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        //入力チェック
        ActionErrors errors = new ActionErrors();
        RequestModel reqMdl = getRequestModel(req);
        errors = form.validateLogin(map, reqMdl);
        if (errors.size() > 0) {
            //ロックアウト処理を行う
            __lockout(req, res, form, con);

            addErrors(req, errors);

            saveToken(req);
            return __doInit(map, form, req, res, con);
        }

        con.setAutoCommit(false);

        //ログイン処理を行う
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        LoginResultModel resultModel = otpBiz.otpAuth(
                form.getCmn004Token(), form.getCmn004OtPass(), con);


        //ワンタイムパスワードが間違っている
        if (resultModel.getErrCode() == LoginResultModel.ECODE_MISS_OTPASS) {
            //ロックアウト処理を行う
            con.setAutoCommit(true);
            __lockout(req, res, form, con);
            con.setAutoCommit(false);

            addErrors(req, resultModel.getErrors());
            saveToken(req);
            return __doInit(map, form, req, res, con);
        }

        GsMessage gsMsg = new GsMessage(req);
        String login = gsMsg.getMessage(req, "mobile.17");

        //ログイン処理に失敗 かつ ActionErrorsが設定されている場合
        if (!resultModel.isResult() && resultModel.getErrors() != null) {
            //ロックアウト処理を行う
            con.setAutoCommit(true);
            __lockout(req, res, form, con);
            con.setAutoCommit(false);

            addErrors(req, resultModel.getErrors());
            saveToken(req);
            return map.findForward("back");

        } else {
            boolean commit = false;
            try {
                //ログイン失敗回数をクリアする
                __setCookie(req, res, "0", 0);

                //ワンタイムパスワードを破棄
                CmnOtpTokenDao tokenDao = new CmnOtpTokenDao(con);
                tokenDao.delete(form.getCmn004Token());

                //使用可能プラグイン情報を取得
                PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);

                //ログイン共通処理を実行
                GSLoginBiz loginBiz = new GSLoginBiz();
                loginBiz.doLogin(req, resultModel.getLoginUser(), con, pconf);

                //ログ出力
                CommonBiz cmnBiz = new CommonBiz();
                cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                        login, GSConstLog.LEVEL_INFO,
                        NullDefault.getString(resultModel.getLoginUser().getLgid(), ""));
                con.commit();
                commit = true;
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }
        ILogin loginBiz = _getLoginInstance();
        String forwardStr = loginBiz.checkForwordPassowrd(resultModel.getLoginUser(), con);
        return map.findForward(forwardStr);
    }
    /**
     * <p>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doInit(ActionMapping map, Cmn004Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        Cmn004Biz biz = new Cmn004Biz(con, getRequestModel(req));
        saveToken(req);
        biz.doInit(form);
        return map.getInputForward();
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
                            Cmn004Form form, Connection con)
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
            time = loginConfData.getLlcLockAge();
        }

        __setCookie(req, res, failCount, time * 60);
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
    @Override
    public ActionForward getSubmitErrorPage(ActionMapping map,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //２重投稿エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        urlForward = map.findForward("gf_domain_logout");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.access.double.submit"));
        req.setAttribute("cmn999Form", cmn999Form);

        urlForward = map.findForward("gf_msg");
        return urlForward;
    }

}
