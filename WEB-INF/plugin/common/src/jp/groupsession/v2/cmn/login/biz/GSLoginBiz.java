package jp.groupsession.v2.cmn.login.biz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ILoginLogoutListener;
import jp.groupsession.v2.cmn.LoginLogoutListenerUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnMblUidDao;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrLangDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.base.CmnLoginHistoryModel;
import jp.groupsession.v2.cmn.model.base.CmnMblUidModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrLangModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.biz.MainUsedDataBiz;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;

/**
 * <br>[機  能] ログイン処理インターフェースの実装クラス
 * <br>[解  説] 通常のログイン処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSLoginBiz implements ILogin {



    /**
     * <br>[機  能] ユーザが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param loginId ログインID
     * @param password パスワード
     * @return true:存在する、false:存在しない
     * @throws Exception 実行時例外
     */
    public boolean isExistsUser(Connection con, String loginId, String password)
    throws Exception {
        //ユーザ情報取得
        AuthDao adao = new AuthDao(con);
        BaseUserModel smodel = null;
        try {
            String epassword =
                GSPassword.getEncryPassword(password);
            smodel = adao.selectLogin(loginId, epassword);
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        }
        if (smodel != null) {
            return true;
        }
        return false;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GSLoginBiz.class);

    @Override
    public LoginResultModel login(LoginModel model) throws Exception {
        LoginResultModel ret = idpassAuth(model);
        if (!ret.isResult()) {
            return ret;
        }
        BaseUserModel smodel = ret.getLoginUser();
        HttpServletRequest req = model.getReq();
        Connection con = model.getCon();
        PluginConfig pconfig = model.getPluginConfig();
        ActionMapping map = model.getMap();

        //ログイン共通処理
        doLogin(req, smodel, con, pconfig);
        //ログイン後遷移先設定
        String forwardName = checkForwordPassowrd(smodel, con);
        ActionForward forward = map.findForward(forwardName);
        ret.setResult(true);
        ret.setForward(forward);

        return ret;
    }
    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説] APIアクセス時に実行
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return forward
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel login2(LoginModel model) throws Exception {
        LoginResultModel ret = idpassAuth(model);
        if (!ret.isResult()) {
            return ret;
        }
        BaseUserModel smodel = ret.getLoginUser();
        HttpServletRequest req = model.getReq();
        Connection con = model.getCon();
        PluginConfig pconfig = model.getPluginConfig();
        doLoginApi(req, smodel, con, pconfig);
        return ret;
    }
    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説] 自動ログイン時に使用
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel autoLogin(LoginModel model) throws Exception {
        LoginResultModel resultModel = new LoginResultModel();
        return resultModel;
    }
    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説] 自動ログイン時に使用
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel autoLoginMbl(LoginModel model) throws Exception {
        LoginResultModel resultModel = new LoginResultModel();
        return resultModel;
    }
    /**
     * <br>[機  能] モバイル用ログイン処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return forward
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel loginMbl(LoginModel model) throws Exception {
        LoginResultModel ret = idpassAuth(model);
        if (!ret.isResult()) {
            return ret;
        }
        BaseUserModel smodel = ret.getLoginUser();
        HttpServletRequest req = model.getReq();
        Connection con = model.getCon();
        ActionMapping map = model.getMap();
        PluginConfig pconfig = model.getPluginConfig();


        //システム予約ユーザはモバイルでのログインは不可
        if (__checkSystemUser(smodel)) {
            ret.setErrCode(LoginResultModel.ECODE_MBLLOGIN_SYSUSR);
            ret.setErrors();
            ret.setForward(map.getInputForward());
            ret.setResult(false);
            return ret;
        }
        //ログイン共通処理
        doLoginMbl(req, smodel, con, pconfig);

        //ログイン後遷移先設定
        String forwardName = checkForwordPassowrd(smodel, con);
        ActionForward forward = map.findForward(forwardName);
        ret.setResult(true);
        ret.setForward(forward);

        return ret;
    }

    /**
     * <br>[機  能] モバイル用ログイン処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return forward
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel loginMblApi(LoginModel model) throws Exception {
        ActionForward forward = null;

        LoginResultModel ret = idpassAuth(model);
        if (!ret.isResult()) {
            return ret;
        }
        BaseUserModel smodel = ret.getLoginUser();
        HttpServletRequest req = model.getReq();
        Connection con = model.getCon();
        ActionMapping map = model.getMap();
        PluginConfig pconfig = model.getPluginConfig();


        //システム予約ユーザはモバイルでのログインは不可
        if (__checkSystemUser(smodel)) {
            ret.setErrCode(LoginResultModel.ECODE_MBLLOGIN_SYSUSR);
            ret.setErrors();
            ret.setForward(map.getInputForward());
            ret.setResult(false);
            return ret;
        }
        //ログイン共通処理
        doLoginMbl(req, smodel, con, pconfig);



        ret.setResult(true);
        ret.setForward(forward);

        return ret;
    }

    /**
     *
     * <br>[機  能] 自動登録設定時 個体識別番号、個体識別番号履歴を登録
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param uid 個体識別番号
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException 実行時例外
     */
    public void saveUuidIfAutoSave(int usrSid, String uid, Connection con, String domain)
            throws SQLException {
        // 個体識別番号設定取得
        CmnUsrmInfDao infDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel infMdl = infDao.selectUidStatus(usrSid);
        CmnMblUidDao uidDao = new CmnMblUidDao(con);
        CmnMblUidModel uidMdl = uidDao.select(usrSid);

        if (infMdl != null) {

            // 個体識別番号が正常に送信されてきている
            if (!StringUtil.isNullZeroStringSpace(uid)) {
                // 個体識別番号でログイン制御 = する & 個体識別番号自動登録 = する
                if (infMdl.getUsiNumCont()   == GSConstUser.UID_CONTROL
                 && infMdl.getUsiNumAutadd() == GSConstUser.UID_AUTO_REG_OK) {
                    if (uidMdl != null) {
                        // 個体識別番号1を更新
                        uidDao.updateUid(usrSid, uid);
                    } else {
                        // 個体識別番号を登録
                        uidDao.insertUid(usrSid, uid);
                    }
                    // 個体識別番号自動登録区分をリセット
                    infDao.updateAutAdd(usrSid);
                }
            }
        }
    }
    /**
     * <br>[機  能] パスワード変更の可否を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:変更可能 false:変更不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canChangePassword(Connection con, int userSid)
    throws SQLException {
        return true;
    }

    /**
     * <br>[機  能] パスワードのフォーマットを行う
     * <br>[解  説] データベースに登録時はこのメソッドでフォーマットした
     * <br>         パスワードが使用される
     * <br>[備  考]
     * @param password パスワード
     * @return パスワード
     */
    public String formatPassword(String password) {
        return password;
    }

    /**
     * <br>[機  能] セッションを確立する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel ユーザ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setSession(HttpServletRequest req,
            BaseUserModel smodel, Connection con)
    throws SQLException {

        //セッション情報を削除
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = req.getSession(true);
        session.setAttribute(GSConst.SESSION_KEY, smodel);
        //セッションの有効時間設定
        CommonBiz cmnBiz = new CommonBiz();
        session.setMaxInactiveInterval(cmnBiz.getSessionTime(con));

    }

    /**
     * <br>[機  能] セッションを確立する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel ユーザ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setSessionApi(HttpServletRequest req,
            BaseUserModel smodel, Connection con)
    throws SQLException {

        //セッション情報を削除
        HttpSession session = req.getSession(true);
        session.setAttribute(GSConst.SESSION_KEY, smodel);
        //セッションの有効時間設定
        CommonBiz cmnBiz = new CommonBiz();
        session.setMaxInactiveInterval(cmnBiz.getSessionTime(con));

    }


    /**
     * <br>[機  能] ユーザがシステム予約ユーザかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param smodel ユーザ情報
     * @return 判定結果 true:システム予約ユーザ false:一般ユーザ
     */
    private boolean __checkSystemUser(BaseUserModel smodel) {
        if (smodel != null) {
            int userSid = smodel.getUsrsid();
            return userSid == GSConst.SYSTEM_USER_ADMIN
                    || userSid == GSConst.SYSTEM_USER_MAIL;
        }

        return false;
    }

    /**
     * <br>[機  能] 別ウィンドウで表示するかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true: 別ウィンドウ false: 通常
     */
    public boolean isPopup() {
        return false;
    }

    /**
     * <br>[機  能] 自動ログイン処理を行うかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true: 自動ログイン処理を行う false: 自動ログイン処理を行わない
     */
    public boolean isAutoLogin() {
        return false;
    }

    /**
     * <br>[機  能] 画面遷移先を返す(frame:メイン画面、pswdLimit:初回ログイン時変更、pswdUpdat:期限切れ時変更)
     * <br>[解  説]
     * <br>[備  考]
     * @param smodel セッションユーザモデル
     * @param con コネクション
     * @return forword 画面遷移先
     * @throws Exception ログイン処理時に例外発生
     */
    public String checkForwordPassowrd(BaseUserModel smodel, Connection con) throws Exception {

        CmnUsrmDao cudao = new CmnUsrmDao(con);
        String forword = "frame";
        //ユーザ情報を取得する
        try {

            CmnUsrmModel usrModel = cudao.select(smodel.getUsrsid());
            //画面遷移先を決める
            if (usrModel != null) {
                // 初回ログイン時パスワード変更区分チェック
                log__.debug("パスワード変更区分 = " + usrModel.getUsrPswdKbn());

                if (usrModel.getUsrPswdKbn() == GSConstUser.PSWD_UPDATE_ON) {
                    forword = "pswdUpdate";
                } else {
                    log__.debug("パスワード変更区分 = " + usrModel.getUsrPswdKbn());
                    // パスワード更新時間の取得
                    UDate pwEdate = usrModel.getUsrPswdEdate();
                    //現在の時間の取得
                    UDate now = new UDate();

                    // パスワード有効日数の取得
                    CmnPswdConfDao pwdao = new CmnPswdConfDao(con);
                    CmnPswdConfModel pwModel = pwdao.select();

                    int limit = -1;
                    if (pwModel != null) {
                        limit = pwModel.getPwcLimitDay();
                    }
                    if (limit != -1) {
                        pwEdate.addDay(limit);
                        log__.debug("パスワード有効期限日 = " + pwEdate);
                        log__.debug("現在日 = " + now);
                        if (now.compare(now, pwEdate) != UDate.LARGE) {
                            forword = "pswdLimit";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return forword;
    }

    /**
     * <br>[機  能] 自動ログイン失敗時に遷移する画面を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @return ActionForward
     */
    public ActionForward getAutoLoginFailPage(ActionMapping map) {
        return null;
    }
    @Override
    public LoginResultModel idpassAuth(LoginModel model) throws Exception {
        LoginResultModel resultModel = new LoginResultModel();

        ActionForward forward = null;
        Connection con = model.getCon();
        String loginId = model.getLoginId();
        String password = model.getPassword();
        ActionMapping map = model.getMap();
        HttpServletRequest req = model.getReq();

        //ユーザ情報取得
        AuthDao adao = new AuthDao(con);
        BaseUserModel smodel = null;
        try {
            String epassword =
                GSPassword.getEncryPassword(password);
            smodel = adao.selectLogin(loginId, epassword);
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        }
        //該当ユーザなし
        if (smodel == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        } else if (smodel.getUsrsid() == 1) {
            //システムメールユーザはログイン不可
            resultModel.setErrCode(LoginResultModel.ECODE_SYSMAIL_LOGIN);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        }
        if (model.getLoginType() == LoginModel.LOGINTYPE_API
                && smodel.getUsrsid() == 0) {
            //APIかつGS管理者の場合はログイン不可
            resultModel.setErrCode(LoginResultModel.ECODE_SYSMAIL_LOGIN);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        }

        if (smodel.getUsrsid() > GSConstUser.USER_RESERV_SID
                && smodel.getUsrUkoFlg() == GSConst.YUKOMUKO_MUKO) {
            //ログイン停止ユーザはログイン不可
            resultModel.setErrCode(LoginResultModel.ECODE_LOGINTEISI_USER);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        }

        resultModel.setLoginUser(smodel);

        //所属グループ未設定の場合、ログイン不可
        CmnBelongmDao belongDao = new CmnBelongmDao(con);
        if (belongDao.selectUserBelongGroupSid(smodel.getUsrsid()).isEmpty()) {
            resultModel.setErrCode(LoginResultModel.ECODE_BELONGGRP_NONE);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        }

        //デフォルトグループ未設定の場合、ログイン不可
        GroupBiz grpBiz = new GroupBiz();
        if (grpBiz.getDefaultGroupId(smodel.getUsrsid(), con) == null) {
            resultModel.setErrCode(LoginResultModel.ECODE_DEFGRP_NONE);
            resultModel.setErrors();
            resultModel.setForward(map.getInputForward());
            return resultModel;
        }

        //ワンタイムパスワード必要判定
        OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
        if (onetimePasswordBiz.isNeedOtpAuth(req, smodel, con)) {
            forward = map.findForward("cmn004");
            resultModel.setErrCode(LoginResultModel.ECODE_NEED_OTP);
            resultModel.setErrors();
            resultModel.setResult(false);
            resultModel.setForward(forward);
            return resultModel;
        }

        //遷移先
        forward = map.findForward("frame");
        resultModel.setResult(true);
        resultModel.setForward(forward);
        return resultModel;
    }
    @Override
    public LoginResultModel idpassAuthApi(LoginModel model) throws Exception {
        String password = model.getPassword();
        ActionMapping map = model.getMap();
        if (password == null || password.length() == 0) {
            LoginResultModel resultModel = new LoginResultModel();
            resultModel.setErrCode(LoginResultModel.ECODE_USERNONE);
            resultModel.setErrors();
            if (map != null) {
                resultModel.setForward(map.getInputForward());
            }
            return resultModel;
        }
        model.setLoginType(LoginModel.LOGINTYPE_API);

        return idpassAuth(model);
    }
    /**
     *
     * <br>[機  能] セッション内のログイン情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel セッションに保管するモデル
     * @param con コネクション
     * @throws Exception 保管情報の取得に失敗時
     */
    private void __initLoginSession(HttpServletRequest req,
            BaseUserModel smodel, Connection con) throws Exception {
        //管理者判定
        GroupDao gdao = new GroupDao(con);
        if (gdao.isBelongAdmin(smodel.getUsrsid())) {
            //管理者
            smodel.setAdminFlg(true);
        } else {
            //一般
            smodel.setAdminFlg(false);
        }

        //前回ログイン時間を取得する。
        CmnUsrmInfDao cidao = new CmnUsrmInfDao(con);
        UDate lsttime = cidao.getLastLoginTime(smodel.getUsrsid());
        if (lsttime != null) {
            String strSdate =
                UDateUtil.getSlashYYMD(lsttime)
                + "  "
                + UDateUtil.getSeparateHMS(lsttime);
            smodel.setLstLogintime(strSdate);
        }
        //テーマ画像のパス設定
        CmnUsrThemeDao utdao = new CmnUsrThemeDao(con);
        smodel.setCtmPath(utdao.select(smodel.getUsrsid()));

        //言語の設定
        CmnUsrLangDao ldao = new CmnUsrLangDao(con);
        CmnUsrLangModel lmdl = null;
        lmdl = ldao.select(smodel.getUsrsid());
        if (lmdl != null) {
            smodel.setCountry(lmdl.getCulCountry());
        }

        //会社ドメインの設定
        String domain = GroupSession.getResourceManager().getDomain(req);
        smodel.setDomain(domain);

        //セッション情報を削除
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //セッション確立
        session = req.getSession(true);
        session.setAttribute(GSConst.SESSION_KEY, smodel);
        //セッションの有効時間設定
        CommonBiz cmnBiz = new CommonBiz();
        session.setMaxInactiveInterval(cmnBiz.getSessionTime(con));

        session.setAttribute("domain", domain);

    }
    /**
     *
     * <br>[機  能] セッション内のログイン情報を設定する
     * <br>[解  説] モバイル版用
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel セッションに保管するモデル
     * @param con コネクション
     * @throws Exception 保管情報の取得に失敗時
     */
    private void __initLoginSessionMbl(HttpServletRequest req,
            BaseUserModel smodel, Connection con) throws Exception {
        //管理者判定
        GroupDao gdao = new GroupDao(con);
        if (gdao.isBelongAdmin(smodel.getUsrsid())) {
            //管理者
            smodel.setAdminFlg(true);
        } else {
            //一般
            smodel.setAdminFlg(false);
        }

        //前回ログイン時間を取得する。
        CmnUsrmInfDao cidao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uinfModel = cidao.select(smodel.getUsrsid());
        UDate lsttime = null;
        if (uinfModel != null) {
            lsttime = uinfModel.getUsiLtlgin();
        }
        if (lsttime != null) {
            String strSdate =
                UDateUtil.getSlashYYMD(lsttime)
                + "  "
                + UDateUtil.getSeparateHMS(lsttime);
            smodel.setLstLogintime(strSdate);
        }

        //テーマ画像のパス設定
        CmnUsrThemeDao utdao = new CmnUsrThemeDao(con);
        smodel.setCtmPath(utdao.select(smodel.getUsrsid()));

        //言語の設定
        CmnUsrLangDao ldao = new CmnUsrLangDao(con);
        CmnUsrLangModel lmdl = null;
        lmdl = ldao.select(smodel.getUsrsid());
        if (lmdl != null) {
            smodel.setCountry(lmdl.getCulCountry());
        }

        //会社ドメインの設定
        String domain = GroupSession.getResourceManager().getDomain(req);
        smodel.setDomain(domain);

        //セッション確立
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = req.getSession(true);
        session.setAttribute(GSConst.SESSION_KEY, smodel);
        session.setAttribute("domain", domain);

        //セッションの有効時間設定
        CommonBiz cmnBiz = new CommonBiz();
        session.setMaxInactiveInterval(cmnBiz.getSessionTime(con));

    }
    /**
     * <br>[機  能] ログイン時のリスナーを実行
     * <br>[解  説]
     * <br>[備  考]
     * @param smodel セッションユーザ
     * @param pconfig プラグインコンフィグ
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __runLoginListner(BaseUserModel smodel,
            PluginConfig pconfig, Connection con) throws Exception {
        boolean commitFlg = false;
        try {
            //ログイン、ログアウトリスナー取得
            log__.debug("ログインリスナー doLogin()開始");
            ILoginLogoutListener[] lis =
                LoginLogoutListenerUtil.getLoginLogoutListeners(pconfig);
            //各プラグインリスナーを呼び出し
            for (int i = 0; i < lis.length; i++) {
                lis[i].doLogin(con, smodel.getUsrsid());
            }
            commitFlg = true;
            log__.debug("ログインリスナー doLogin()終了");
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
        }
    }
    /**
     *
     * <br>[機  能] ログイン履歴の保管
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel セッションユーザ
     * @param con コネクション
     * @throws Exception SQL実行時例外
     */
    private void __writeLoginHistry(HttpServletRequest req,
            BaseUserModel smodel, Connection con) throws Exception {
        boolean commitFlg = false;
        try {

            int usrSid = smodel.getUsrsid();
            UDate now = new UDate();

            //ログイン履歴に登録
            CmnLoginHistoryModel hismodel = new CmnLoginHistoryModel();
            hismodel.setUsrSid(smodel.getUsrsid());
            hismodel.setClhTerminal(GSConstCommon.TERMINAL_KBN_PC);
            hismodel.setClhIp(NullDefault.getString(CommonBiz.getRemoteAddr(req), ""));
            hismodel.setClhCar(GSConstCommon.CAR_KBN_PC);
            hismodel.setClhUid(null);
            hismodel.setClhAuid(usrSid);
            hismodel.setClhAdate(now);
            hismodel.setClhEuid(usrSid);
            hismodel.setClhEdate(now);

            CmnLoginHistoryDao hisDao = new CmnLoginHistoryDao(con);
            hisDao.insert(hismodel);

            //ログイン履歴のデータ使用量を登録
            MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
            usedDataBiz.insertLoginHistoryDataSize(now, usrSid, hismodel.getClhIp(), true);

            CmnUsrmInfModel ltmodel = new CmnUsrmInfModel();
            ltmodel.setUsrSid(usrSid);
            ltmodel.setUsiLtlgin(now);
            ltmodel.setUsiEdate(now);
            ltmodel.setUsiEuid(usrSid);

            //最終ログイン時間を更新
            AuthDao adao = new AuthDao(con);
            adao.updateLastLoginTime(ltmodel);
            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("ログイン時間の更新に失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

    }
    /**
     *
     * <br>[機  能] ログイン時のリスナーを実行
     * <br>[解  説] モバイル用
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel セッションユーザ
     * @param con コネクション
     * @param userAgent ユーザエージェントモデル
     * @throws Exception SQL実行時例外
     */
    private void __writeLoginHistryMbl(HttpServletRequest req,
            BaseUserModel smodel, Connection con, UserAgent userAgent) throws Exception {
        boolean commitFlg = false;
        try {

            //ログイン履歴に登録
            int usrSid = smodel.getUsrsid();
            UDate now = new UDate();

            CmnLoginHistoryModel hismodel = new CmnLoginHistoryModel();
            hismodel.setUsrSid(smodel.getUsrsid());
            hismodel.setClhTerminal(GSConstCommon.TERMINAL_KBN_MOBILE);
            hismodel.setClhIp(NullDefault.getString(CommonBiz.getRemoteAddr(req), ""));
            hismodel.setClhCar(userAgent.getClhKbn());
            hismodel.setClhUid(userAgent.getCuid());
            hismodel.setClhAuid(usrSid);
            hismodel.setClhAdate(now);
            hismodel.setClhEuid(usrSid);
            hismodel.setClhEdate(now);

            CmnLoginHistoryDao hisDao = new CmnLoginHistoryDao(con);
            hisDao.insert(hismodel);

            //ログイン履歴のデータ使用量を登録
            MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
            usedDataBiz.insertLoginHistoryDataSize(now, usrSid, hismodel.getClhIp(), true);

            CmnUsrmInfModel ltmodel = new CmnUsrmInfModel();
            ltmodel.setUsrSid(usrSid);
            ltmodel.setUsiLtlgin(now);
            ltmodel.setUsiEdate(now);
            ltmodel.setUsiEuid(usrSid);

            //最終ログイン時間を更新
            AuthDao adao = new AuthDao(con);
            adao.updateLastLoginTime(ltmodel);
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ログイン時間の更新に失敗", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }

    }
    /**
     *
     * <br>[機  能] ログイン時共通処理
     * <br>[解  説] セッション更新、ログイン履歴保管 、リスナ実行
     * <br>[備  考]
     * @param req リクエスト
     * @param smodel セッションユーザ情報
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws Exception 実行時例外
     */
    public void doLogin(HttpServletRequest req,
            BaseUserModel smodel, Connection con,
            PluginConfig pconfig) throws Exception {
        __initLoginSession(req, smodel, con);

        __writeLoginHistry(req, smodel, con);

        __runLoginListner(smodel, pconfig, con);
    }
    /**
     *
     * <br>[機  能] ログイン時共通処理
     * <br>[解  説] セッション更新、ログイン履歴保管 、リスナ実行
     * <br>[備  考] モバイル用
     * @param req リクエスト
     * @param smodel セッションユーザ情報
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws Exception 実行時例外
     */
    public void doLoginMbl(HttpServletRequest req,
            BaseUserModel smodel, Connection con,
            PluginConfig pconfig) throws Exception {

        __initLoginSessionMbl(req, smodel, con);

        // エージェント取得
        UserAgent agent = new UserAgent(req);

        __writeLoginHistryMbl(req, smodel, con, agent);
        // 個体識別番号設定
        saveUuidIfAutoSave(smodel.getUsrsid(), agent.getCuid(), con,
                GroupSession.getResourceManager().getDomain(req));

        __runLoginListner(smodel, pconfig, con);

    }
    /**
     *
     * <br>[機  能] ログイン時共通処理
     * <br>[解  説] セッション更新、ログイン履歴保管 、リスナ実行
     * <br>[備  考] API用
     * @param req リクエスト
     * @param smodel セッションユーザ情報
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws Exception 実行時例外
     */
    public void doLoginApi(HttpServletRequest req,
            BaseUserModel smodel, Connection con,
            PluginConfig pconfig) throws Exception {

        __initLoginSession(req, smodel, con);

        __runLoginListner(smodel, pconfig, con);

    }
    @Override
    public boolean canUseOnetimePassword() {
        return true;
    }
    @Override
    public String getLoginType(BaseUserModel model, Connection con) {
        return "normal";
    }

}