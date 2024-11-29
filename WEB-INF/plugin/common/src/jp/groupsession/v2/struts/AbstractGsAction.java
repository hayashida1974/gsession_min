package jp.groupsession.v2.struts;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.DBConnectionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.AbstractAction;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 本システムで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public abstract class AbstractGsAction extends AbstractAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractGsAction.class);
    /**
     * <br>[機  能] 各Actionクラス共通で使用する処理を行う
     * <br>[解  説] セッション確立の判定、レスポンスヘッダのセット等を行い、各Actionクラスの実行を行う
     * <br>[備  考] ログモードがデバック以上の場合のみ、キャッシュを有効にしている
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public final ActionForward execute(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res)
        throws Exception {

        UDate startTime = new UDate();
        req.setAttribute(GSConst.REQ_ATTRKEY_ACTIONPATH, map.getPath());

        writeStartLog(req);

        //ドメインチェック
        String domain = null;
        try {
            domain = GroupSession.getResourceManager().getDomainCheck(req);
            if (StringUtil.isNullZeroStringSpace(domain)) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        //スレッド名を設定
        Thread.currentThread().setName(
                                    map.getPath()
                                    + "-" + System.currentTimeMillis()
                                    + "-" + domain
                                    + "-" + Thread.currentThread().getId());

        //クライアントのIPアドレスをログに出力
        String cip = __getIpAddress(req);
        log__.info("クライアントIPアドレス " + cip);

        ActionForward forward = null;
        Connection con = null;
        try {

            //Refererチェック
            if (!checkReferer(map, req)) {
                res.sendRedirect(".." + map.findForward("gf_auth").getPath());
                return forward;
            }

            if (!isCacheOk(req, form)) {
                setNocache(res);
            }

            if (!canNoConnection(req, form)) {
                try {
                    con = __getConnection(req);
                } catch (DBConnectionException e) {
                    if (e instanceof DBBackupGuardException) {
                        //バックアップ中にアクセスした場合
                        res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                        return forwardBackupProgressPage(map, form, req, res);
                    }
                    //コネクションが取得できない場合は共通エラーメッセージ画面へ
                    forward = getConErrorPage2(map, req);
                    return forward;
                } catch (Exception e) {
                    //コネクションが取得できない場合は共通エラーメッセージ画面へ
                    log__.error("コネクションの取得に失敗", e);
                    forward = getConErrorPage(map, req);
                    return forward;
                }
            }


            if (!canNoConnection(req, form)) {
                if (con == null) {
                    //コネクションが取得できない場合は共通エラーメッセージ画面へ
                    log__.error("コネクションの取得に失敗");
                    forward = getConErrorPage(map, req);
                }

                CommonBiz cmnBiz = new CommonBiz();

                //DBのオペレーションログ設定をGSContextに反映する。
                cmnBiz.setOperationLogGsContext(con,
                        GroupSession.getResourceManager().getDomain(req));

                //セッションチェック
                if (isSession(req)) {

                    //セッションユーザが削除済みの場合不正なエラー
                    CmnUsrmDao cumDao = new CmnUsrmDao(con);
                    if (cumDao.isDeleteUserHnt(getSessionUserSid(req))) {
                        removeSession(req);
                        return getDomainErrorPage(map, req);
                    }

                    //正常なリクエスト
                    _updateSession(req, cmnBiz.getSessionTime(con), con);

                    //ドメインチェック
                    if (!canAccessDomain(req)) {

                        //セッション破棄
                        removeSession(req);
                        log__.warn("不正なドメインへのアクセスです");
                        forward = getDomainErrorPage(map, req);
                        return forward;
                    }
                } else {
                    //セッションが確立していない状態でのアクセスを許可するか
                    if (!canNoSessionAccess(req, form)) {
                        log__.warn("無効なリクエストです" + "IPアドレス:" + cip + " URL:" + __getUrl(req));
                        forward = getDomainErrorPage(map, req);
                        return forward;
                    }
                }

                BaseUserModel smodel = getSessionUserModel(req);
                //管理者以外のアクセスを許可するのか
                if (!canNotAdminAccess(req, form)) {
                    //許可しない場合、管理者か判定
                    boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, getPluginId());
                    if (!adminUser) {
                        //権限エラー
                        forward = getNotAdminSeniPage(map, req);
                        return forward;
                    }
                }
                if (smodel != null && smodel.getUsrsid() == 0) {
                    //adminユーザは管理者設定画面のみ遷移可能
                    if (!canNotAdminUserAccess()) {
                        forward = getSubmitErrorPage(map, req);
                        return forward;
                    }
                }

                //使用不可のプラグインへアクセスした場合
                if (!_isAccessPlugin(req, form, con)) {
                    //アクセス
                    forward = getSubmitErrorPage(map, req);
                    return forward;
                }
            }

            //MessageResoucesを設定
            setMessageResource(req);

            if (con != null) {
                //Connectionの自動コミットモードを設定
                con.setAutoCommit(_getAutoCommit());

                //GSファイアウォールチェック
                FirewallBiz firewall = FirewallBiz.getInstance();
                if (!firewall.isArrowAccess(getRequestModel(req), con, new UserAgent(req))) {
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
            }

            forward = executeAction(map, form, req, res, con);

            //HELP URL
            String helpUrl = __getHelpUrl(forward);
            log__.debug("Program ID is " + helpUrl);
            if (helpUrl != null) {
                //URLをセット
                __setHelpUrl(req, helpUrl);
            }
            writeEndLog(req);

        } catch (GSException e) {
            //
            if (e instanceof GSAuthenticateException) {
                //共通エラーメッセージ画面へ
                forward = getAuthErrorPage(map, req);
                return forward;
            }
        } catch (SocketException e) {
            //特に何もしない
            forward = null;
        } catch (Exception e) {

            //ClientAbortExceptionの場合、何もしない
            if (!e.getClass().getName()
                    .equals("org.apache.catalina.connector.ClientAbortException")) {

                log__.error("AbstractActionで例外がスローされました。", e);
                log__.error("リクエストパラメータ\r\n" + __getErrorParam(req));

                if (con != null) {
                    closeConnection(con);
                    con = null;
                    con = getConnection(req);
                }

                JDBCUtil.autoCommitOff(con);


                forward = __exceptionForward(e, req, map);
                if (forward == null) {
                    if (_doExcecuteException(map, form, req, res, con, e)) {
                        req.setAttribute(GSConst.ERROR_KEY, e);
                        req.setAttribute(GSConst.ERROR_PARA_KEY, __getErrorParam(req));
                        throw e;
                    }
                }
            } else {
                forward = null;
            }


        } catch (Throwable e) {
            log__.error("AbstractActionで例外がスローされました。(Throwable)", e);
            log__.error("リクエストパラメータ\r\n" + __getErrorParam(req));

            if (con != null) {
                closeConnection(con);
                con = null;
                con = getConnection(req);
            }

            JDBCUtil.autoCommitOff(con);
            if (_doExcecuteThrowable(map, form, req, res, con, e)) {
                req.setAttribute(GSConst.ERROR_KEY, e);
                req.setAttribute(GSConst.ERROR_PARA_KEY, __getErrorParam(req));
                throw new Exception(e);
            } else {
                forward = null;
            }
        } finally {
            JDBCUtil.closeConnection(con);
            _saveCheckedDomain(req);
        }

        //処理時間
        UDate endTime = new UDate();
        long msecond = UDateUtil.diffMillis(startTime, endTime);
        log__.debug("処理時間(ミリ秒) " + msecond);
        if (msecond > 3000) {
            //ログに詳細を出力
            log__.warn("---- [START] Warning!!!"
                  + " slow process 3 second over (" + msecond + ") ---------");
            _debugAction(map, form, req, res, forward);
            log__.warn("---- [END] Warning!!! slow process ---------");
        } else {
            //遅延がなければログレベルDEBUGでURLを出力
            //リクエストURL
            StringBuffer reqUrl = req.getRequestURL();
            log__.debug("リクエストURL = " + reqUrl);
            log__.debug("CMD = " + req.getParameter("CMD"));
        }

        //遷移先
        if (forward != null && forward.getName() != null && forward.getName().equals("gf_msg")) {
            Cmn999Form cmn999Form = (Cmn999Form) req.getAttribute("cmn999Form");
            if (cmn999Form != null && cmn999Form.isInfoToast()) {
                PluginConfig plugin = getPluginConfig(req);
                CommonBiz cmnBiz = new CommonBiz();
                String okUrl = cmn999Form.getUrlOK();
                if (!okUrl.contains(GSConst.P_CMD)) {
                    URI uri = new URI(okUrl);
                    String queryConnect = null;
                    if (uri.getQuery() == null) {
                        queryConnect = "?";
                    } else {
                        queryConnect = "&";
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append(okUrl);
                    builder.append(queryConnect);
                    builder.append(GSConst.P_CMD);
                    builder.append("=");
                    builder.append("");
                    okUrl = builder.toString();
                }
                String cmd = req.getParameter(GSConst.P_CMD);
                forward = cmnBiz.saveForwardUrl(map, plugin, okUrl, cmd);
            }
        }
        return forward;
    }

    /**
     * <br>[機  能] 特定例外エラー画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map リクエストマップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return 遷移先エラー画面
     * @throws Exception 実行時例外
     */
    public ActionForward forwardBackupProgressPage(ActionMapping map,
            ActionForm form, HttpServletRequest req, HttpServletResponse res)
    throws Exception {
        ActionForward forward = null;
        forward = map.findForward("gf_msg_backupprogress");
        return forward;
    }

    /**
     *
     * <br>[機  能] 特定例外エラー画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     * @param e 発生例外
     * @param req リクエスト
     * @param map リクエストマップ
     * @return 遷移先エラー画面
     */
    private ActionForward __exceptionForward(Exception e,
            HttpServletRequest req, ActionMapping map) {
        Throwable cause = GSAttachFileNotExistException.searchCause(e);
        if (cause != null) {
            log__.error("", cause);
            ActionForward forward = null;
            ActionForward okForward = map.findForward("gf_menu");
            Cmn999Form cmn999Form = new Cmn999Form();

            MessageResources msgRes = getResources(req);
            //メッセージセット
            cmn999Form.setIcon(Cmn999Form.ICON_WARN);
            cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
            cmn999Form.setType(Cmn999Form.TYPE_OK);
            cmn999Form.setUrlOK(okForward.getPath());
            cmn999Form.setMessage(msgRes.getMessage("error.save.attach.filenotexist"));
            cmn999Form.addHiddenParam("errCause", "GSAttachFileNotExistException");
            req.setAttribute("cmn999Form", cmn999Form);
            forward = map.findForward("gf_msg");
            return forward;

        }


        return null;
    }

    /**
     *
     * <br>[機  能] 次回ドメインチェック用のドメイン情報を保管する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    protected void _saveCheckedDomain(HttpServletRequest req) {
        GroupSession.getResourceManager().saveAccessDomain(req);
    }

    /**
     * <br>[機  能] debug用メソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param forward ActionForward フォワード
     */
    @SuppressWarnings("all")
    protected void _debugAction(
            ActionMapping map,
            ActionForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            ActionForward forward) {
        //5秒以上かかった処理をログに記録

        try {
            //IP
            log__.warn("クライアントIPアドレス " + CommonBiz.getRemoteAddr(req));

            //forward
            if (forward != null) {
                String path = forward.getPath();
                log__.warn("forward = " + path);
            }

            //クラス名
            Class cls = this.getClass();
            String clsName = cls.getName();
            log__.warn("クラス名 = " + clsName);

            //リクエストURL
            StringBuffer reqUrl = req.getRequestURL();
            log__.warn("リクエストURL = " + reqUrl);

            //リクエストパラメータ
            Enumeration enm = req.getParameterNames();
            while (enm.hasMoreElements()) {
                String key = (String) enm.nextElement();
                String val = req.getParameter(key);
                log__.warn(key + " = " + val);
            }

            //リファラー
            String referer = req.getHeader("referer");
            log__.warn("リファラー = " + NullDefault.getString(referer, ""));

            //DS
            DataSource ds = GroupSession.getResourceManager().getDataSource(getRequestModel(req));
            if (ds instanceof org.apache.commons.dbcp2.BasicDataSource) {
                //
                org.apache.commons.dbcp2.BasicDataSource bds
                    = (org.apache.commons.dbcp2.BasicDataSource) ds;
                int aconcnt = bds.getNumActive();
                int iconcnt = bds.getNumIdle();
                log__.warn("現在のコネクション状況: ACTIVE=" + aconcnt + " IDLE=" + iconcnt);
            }

            //メモリ
            DecimalFormat format1 = new DecimalFormat("#,###MB");
            DecimalFormat format2 = new DecimalFormat("##.#%");
            long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
            long used = max - free;
            double ratio = (used / (double) max);
            //使用
            log__.warn("メモリ使用" + format1.format(used));
            //使用(割合)
            log__.warn("メモリ使用割合" + format2.format(ratio));
            //最大値
            log__.warn("メモリ最大値" + format1.format(max));

            //リクエストパラメータ
            log__.warn("リクエストパラメータ\r\n" + __getErrorParam(req));

        } catch (Throwable e) {
        }

    }

    /**
     * <br>[機  能] リクエストURL文字列を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return パラメータ文字列
     */
    private String __getUrl(HttpServletRequest req) {
        StringBuffer buf = null;
        String url = null;
        if (req == null) {
            return "";
        }
        try {
            //リクエストパラメータ
            buf = req.getRequestURL();
            if (buf != null) {
                url = buf.toString();
            }
        } catch (Throwable e) {
        }
        return url;
    }

    /**
     * <br>[機  能] エラー発生時のリクエストパラメータ文字列を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return パラメータ文字列
     */
    @SuppressWarnings("all")
    private String __getErrorParam(HttpServletRequest req) {
        StringBuilder buf = new StringBuilder();
        if (req == null) {
            return "";
        }
        try {
            //リクエストパラメータ
            Enumeration enm = req.getParameterNames();
            while (enm.hasMoreElements()) {
                String key = (String) enm.nextElement();
                String val = req.getParameter(key);
                buf.append(key + " = " + val + "\r\n");
            }
        } catch (Throwable e) {
        }
        return buf.toString();
    }

    /**
     *
     * <br>[機  能] クライアントのIPアドレスを取得する。
     * <br>[解  説]
     * <br>[備  考] 取得できない場合はnullを返す
     * @param req リクエスト
     * @return ipアドレス
     */
    private String __getIpAddress(HttpServletRequest req) {
        String ip = null;
        //クライアントのIPアドレスをログに出力
        try {
            ip = CommonBiz.getRemoteAddr(req);
        } catch (RuntimeException e) {
        }
        return ip;
    }
    /**
     * <p>ヘルプHTMLのURLを返す。
     * @param forward フォーワード
     * @return ヘルプHTMLのURL
     */
    private String __getHelpUrl(ActionForward forward) {
        if (forward == null) {
            return null;
        }
        String path = forward.getPath();
        //NULL
        if (path == null) {
            return null;
        }
        //LENGTH
        path = path.trim().toLowerCase();
        if (path.length() <= 0) {
            return null;
        }

        //外部URL
        if (path.startsWith("http")) {
            return null;
        }

        //プラグインディレクトリ
        int pIndex = path.indexOf("plugin");
        if (pIndex == -1) {
            return null;
        }
        int pluginSlashStIndex = path.indexOf("/", pIndex) + 1;
        int pluginSlashEdIndex = path.indexOf("/", pluginSlashStIndex);

        if (pluginSlashStIndex >= pluginSlashEdIndex) {
            return null;
        }
        //plugin ディレクトリ
        String pluginid = path.substring(pluginSlashStIndex, pluginSlashEdIndex);

        //スラッシュ
        int slLastIndex = path.lastIndexOf("/");
        if (slLastIndex == -1) {
            return null;
        }
        //JSPファイル指定
        int jspLastIndex = path.lastIndexOf("jsp");
        if (jspLastIndex == -1) {
            return null;
        }
        //
        log__.debug("JSP PATH = " + path);
        int ssindex = slLastIndex + 1;
        int slindex = jspLastIndex - 1;
        if (ssindex >= slindex) {
            return null;
        }
        String pgid = path.substring(ssindex, slindex);


        //----------------------
        StringBuilder buf = new StringBuilder();
        //../help/hlp001.do?pid=XXXXXX&pgid=YYYYYY
        buf.append("../help/hlp001.do");
        buf.append("?pluginid=");
        buf.append(pluginid);
        buf.append("&pgid=");
        buf.append(pgid);


        //----------------------


        return buf.toString();
    }

    /**
     * <p>HELP URLをリクエストオブジェクトにセットする。
     *
     * @param req リクエスト
     * @param url プログラムID
     */
    private void __setHelpUrl(HttpServletRequest req, String url) {
        req.setAttribute(GSConst.HELPURL, url);
    }

    /**
     * <p>セッションが確立しているか判定を行う
     * @param req リクエスト
     * @return true:セッションが確立している,false:セッションが確立していない
     */
    public boolean isSession(HttpServletRequest req) {
        BaseUserModel smodel = getSessionUserModel(req);
        if (smodel == null) {
            return false;
        }
        return true;
    }

    /**
     * <p>セッションからユーザモデルを取得する。
     * @param req リクエスト
     * @return SessionUserModel
     */
    public BaseUserModel getSessionUserModel(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Object tmp = session.getAttribute(GSConst.SESSION_KEY);
        if (tmp == null) {
            return null;
        }
        BaseUserModel smodel = (BaseUserModel) tmp;
        return smodel;
    }

    /**
     * <p>リクエストの情報を返す
     * @param req リクエスト
     * @return RequestModel
     */
    public RequestModel getRequestModel(HttpServletRequest req) {

        RequestModel reqMdl = new RequestModel();
        if (req != null) {
            HttpSession session = req.getSession(false);

            //ユーザモデルを取得
            if (session != null) {
                reqMdl.setSession(session);
                Object tmp = session.getAttribute(GSConst.SESSION_KEY);
                if (tmp != null) {
                    BaseUserModel smodel = (BaseUserModel) tmp;
                    reqMdl.setSmodel(smodel);
                }
            }

            //言語を取得
            reqMdl.setLocale(req.getLocale());

            //リファラーを取得
            reqMdl.setReferer(req.getHeader("REFERER"));

            //ドメインを取得
            reqMdl.setDomain(GroupSession.getResourceManager().getDomain(req));

            //エラーを取得
            if (req.getAttribute(GSConst.ERROR_KEY) != null) {
                reqMdl.setOerror(req.getAttribute(GSConst.ERROR_KEY));
            }

            //クライアントIP
            reqMdl.setRemoteAddr(CommonBiz.getRemoteAddr(req));

            //リクエストURI
            reqMdl.setRequestURI(req.getRequestURI());

            //リクエストURL
            reqMdl.setRequestURL(req.getRequestURL());

            //アクションパス
            String actionPath = (String) req.getAttribute(GSConst.REQ_ATTRKEY_ACTIONPATH);
            reqMdl.setActionPath(actionPath);

            //UserAgent
            reqMdl.setUserAgent(new UserAgent(req));
        }
        return reqMdl;
    }

    /**
     * <p>セッションに設定されているユーザモデルからユーザSIDを取得する。
     * @param req リクエスト
     * @return ユーザSID
     */
    public int getSessionUserSid(HttpServletRequest req) {
        BaseUserModel smodel = getSessionUserModel(req);
        if (smodel == null) {
            return -1;
        }

        return smodel.getUsrsid();
    }

    /**
     * <p>DB用のコネクションを取得します。
     * @param req リクエスト
     * @return DB用のコネクション
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    private Connection __getConnection(HttpServletRequest req)
    throws SQLException, DBConnectionException, Exception {
        GroupSession servlet = (GroupSession) getServlet();
        Connection con = null;
        boolean result = false;
        try {
            con = servlet.getConnection(req);
            con.setAutoCommit(false);
            result = true;
        } catch (SQLException e) {
            if (!(e instanceof DBBackupGuardException)) {
                log__.fatal("コネクションの取得に失敗", e);
            }
            throw e;
        } finally {
            if (!result) {
                closeConnection(con);
            }
        }
        return con;
    }

    /**
     * <p>DB用のコネクションを取得します。
     * @param req リクエスト
     * @return DB用のコネクション
     * @throws Exception 実行時例外
     */
    public Connection getConnection(HttpServletRequest req) throws Exception {
        GroupSession servlet = (GroupSession) getServlet();
        Connection con = null;
        boolean result = false;
        try {
            con = servlet.getConnection(req);
            con.setAutoCommit(false);
            result = true;
        } catch (SQLException e) {
            log__.fatal("コネクションの取得に失敗", e);
            throw e;
        } finally {
            if (!result) {
                closeConnection(con);
            }
        }
        return con;
    }

    /**
     * <p>DB用のコネクションを明示的に開放(close)します。
     * @param con コネクション
     */
    public void closeConnection(Connection con) {
        JDBCUtil.closeConnection(con);
    }

    /**
     * <p>セッションをアップデートします。
     * @param req リクエスト
     * @param interval インターバル
     * @param con DBコネクション
     */
    protected void _updateSession(HttpServletRequest req, int interval, Connection con) {
        HttpSession session = req.getSession(true);
        //セッションの有効時間設定
        session.setMaxInactiveInterval(interval);
    }

    /**
     * <p>Sessionを削除します。
     * <p>Sessionがない場合は何もしません。
     * @param req リクエスト
     */
    public void removeSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <p>データベースへのコネクションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoConnection(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <p>アクセス可能なドメインかを判定
     * @param req リクエスト
     * @return true:許可する,false:許可しない
     * @throws Exception ドメイン判定時に例外発生
     */
    public boolean canAccessDomain(HttpServletRequest req) throws Exception {
        return GroupSession.getResourceManager().canAccessDomain(req);
    }

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説] サブクラスでこのメソッドをオーバーライドして使用する
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminUserAccess() {
        return false;
    }

    /**
     * <p>メッセージリソースの取得
     * @param req リクエスト
     * @return MessageResources
     */
    public MessageResources getResources(HttpServletRequest req) {
        MessageResources msgRes = null;
        GsMessage gsMsg = new GsMessage();
        String resourceKey = gsMsg.getResourceKey("messages", req);
        msgRes = super.getResources(req, resourceKey);
        return msgRes;
    }

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     */
    public void setMessageResource(HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        GsMessage.setMessageResources(
                getResources(req, gsMsg.getResourceKey(req)));
    }

    /**
     * <p>管理者権限エラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getNotAdminSeniPage(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_power");
        return forward;
    }

    /**
     * <p>認証エラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getAuthErrorPage(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_auth");
        return forward;
    }

    /**
     * <br>[機  能] 認証エラーの場合に遷移する画面を取得する。
     * <br>[解  説] ポップアップで認証エラーが発生した場合の処理を行う
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getAuthErrorPageWithPopup(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_auth_popup");
        return forward;
    }

    /**
     * <p>ドメインエラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getDomainErrorPage(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_domain");
        return forward;
    }

    /**
     * <p>コネクションエラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getConErrorPage(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_con");
        return forward;
    }

    /**
     * <p>コネクションエラー(コネクション使用率)の場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getConErrorPage2(ActionMapping map, HttpServletRequest req) {
        ActionForward forward = map.findForward("gf_con");
        return forward;
    }

    /**
     * <p>２重投稿エラーなどの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @return ActionForward フォワード
     */
    public ActionForward getSubmitErrorPage(ActionMapping map, HttpServletRequest req) {
        //
        ActionForward forward = map.findForward("gf_submit");
        return forward;
    }

    /**
     * <p>ファイル容量エラーの場合に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     */
    public ActionForward getFileSizeErrorPage(ActionMapping map,
                                                                HttpServletRequest req,
                                                                HttpServletResponse res) {
        ActionForward forward = map.findForward("gf_file_size");
        return forward;
    }

    /**
     * <br>[機  能] GSファイアウォールの個体識別番号制限によるメッセージ画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param msg メッセージ文字列
     * @return ActionForward
     */
    public ActionForward getClientErrorPage(ActionMapping map,
                                    HttpServletRequest req) {

        //セッションがあるとエラー画面で個体識別番号制限のループするため
        //セッションを破棄する
        removeSession(req);

        GsMessage gsMsg = new GsMessage(req);
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);

        ActionForward forwardOk = map.findForward("gf_logout");

        cmn999Form.setUrlOK(forwardOk.getPath());
        //メッセージ
        cmn999Form.setMessage(gsMsg.getMessage("mobile.cmn001.error.not.exit.authuid"));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");

    }

    /**
     * <p>キャッシュを有効にして良いか判定を行う
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * キャッシュを無効にする
     * @param res HttpServletResponse
     */
    public static void setNocache(HttpServletResponse res) {
        //キャッシュを無効にする
        Calendar objCal1 = Calendar.getInstance();
        Calendar objCal2 = Calendar.getInstance();
        objCal2.set(1970, 0, 1, 0, 0, 0);
        res.setDateHeader("Last-Modified", objCal1.getTime().getTime());
        res.setDateHeader("Expires", objCal2.getTime().getTime());
        res.setHeader("Cache-Control", "no-cache");
    }

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {

        PluginConfig pconfig
        = getPluginConfigForMain(
            getPluginConfig(req),
            con,
            getSessionUserSid(req),
            getRequestModel(req));

        //使用不可のプラグインへアクセスした場合
        if (!canAccessPlugin(pconfig.getPluginIdList())) {
            return false;
        }
        return true;
    }

    /** プラグインが使用可能か判定します
     * @param plugins 使用可能なプラグインID
     * @return boolean true:使用可能 false:使用不可
     */
    @Override
    public boolean canAccessPlugin(List<String> plugins) {
        for (String plugin : plugins) {
            if (getPluginId() == null
            || plugin.equals(getPluginId())) {
                log__.debug("使用可能なプラグインです。" + getPluginId());
                return true;
            }
        }
        log__.debug("使用不可なプラグインです。" + getPluginId());
        return super.canAccessPlugin(plugins);
    }

    /**
     * プラグインIDを取得します。
     * 各プラグインの抽象アクションでオーバーライドしてください。
     * @return String プラグインID
     */
    public String getPluginId() {
        return null;
    }

    /**
     * <p>採番マスタ取得
     * @param req リクエスト
     * @return 取得した採番マスタ
     * @throws Exception 実行例外
     */
    public MlCountMtController getCountMtController(HttpServletRequest req) throws Exception {
        return getCountMtController(getRequestModel(req));
    }

    /**
     * <p>採番マスタ取得
     * @param reqMdl リクエスト情報
     * @return 取得した採番マスタ
     * @throws Exception 実行例外
     */
    public MlCountMtController getCountMtController(RequestModel reqMdl) throws Exception {
        MlCountMtController mlCnt
            = GroupSession.getResourceManager().getCountController(reqMdl);

        return mlCnt;
    }

    /**
     * [機  能] アプリケーションのルートパスを返す<br>
     * [解  説] <br>
     * [備  考] <br>
     * @return パス
     */
    public String getAppRootPath() {
        GSContext gscontext = getGsContext();
        Object tmp = gscontext.get(GSContext.APP_ROOT_PATH);
        if (tmp == null) {
            return null;
        }
        if (!(tmp instanceof String)) {
            return null;
        }
        String path = (String) tmp;
        return path;
    }

    /**
     * [機  能] アプリケーションのテンポラリディレクトリのパスを返す<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param req リクエスト
     * @return パス
     */
    public String getTempPath(HttpServletRequest req) {

        String tmp = GroupSession.getResourceManager().getTempPath(req);

        if (StringUtil.isNullZeroString(tmp)) {
            return null;
        }

        return tmp;
    }

    /**
     * [機  能] GroupSession共通情報を取得します。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @return GSContext
     */
    public GSContext getGsContext() {

        return GroupSession.getContext();
    }

    /**
     * [機  能] プラグイン情報を取得します。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param req リクエスト
     * @return PluginConfig
     */
    public PluginConfig getPluginConfig(HttpServletRequest req) {

        PluginConfig pconfig = null;
        pconfig = GroupSession.getResourceManager().getPluginConfig(getRequestModel(req));
        if (pconfig == null) {
            log__.fatal("プラグインコンフィグの取得に失敗");
        }
        return pconfig;
    }

    /**
     * [機  能] 管理者設定の使用するプラグイン設定を反映したPluginConfigを取得します。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public PluginConfig getPluginConfigForMain(PluginConfig pconfig, Connection con,
                                            RequestModel reqMdl)
    throws SQLException {
        return getPluginConfigForMain(pconfig, con, -1, reqMdl);
    }

    /**
     * [機  能] 管理者設定の使用するプラグイン設定を反映したPluginConfigを取得します。<br>
     * [解  説] 指定されたユーザが使用不可のプラグインは除外します<br>
     * [備  考] <br>
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public PluginConfig getPluginConfigForMain(PluginConfig pconfig, Connection con, int userSid,
                                            RequestModel reqMdl)
    throws SQLException {

        PluginConfig ret = new PluginConfig();

        CommonBiz cmnBiz = new CommonBiz();
        List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con, userSid);

        if (!menuPluginIdList.isEmpty()) {
            List<String> allowMblAppIdList = cmnBiz.getAllowPluginIdForMblApp(con, reqMdl);
            Plugin plugin = null;
            for (String pId : menuPluginIdList) {
                if (allowMblAppIdList == null || allowMblAppIdList.contains(pId)) {
                    plugin = pconfig.getPlugin(pId);
                    if (plugin != null) {
                        ret.addPlugin(plugin);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] Refererのチェックを行います。
     * <br>[解  説] AppResourceのサーバ名称と比較します。
     * <br>[備  考]
     *
     * @param map マップ
     * @param req リクエスト
     * @return 判定結果 true:正常 false:不正
     */
    public boolean checkReferer(ActionMapping map, HttpServletRequest req) {

        boolean result = false;

        //サーバ名称が取得できなかった場合は全て正常とする
        String serverName = ConfigBundle.getValue("SERVER_NAME");
        if (StringUtil.isNullZeroString(serverName)) {
            return true;
        }
        StringTokenizer serverNames = new StringTokenizer(serverName, ",");

        //権限エラーメッセージ画面の場合、チェックを行わない
        String reqUrl = "";
        if (req.getRequestURL() != null) {
            reqUrl = req.getRequestURL().toString();
            if (reqUrl.indexOf("?") < 0) {
                reqUrl = reqUrl.concat("?CMD=AUTH_ERROR");
            }
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        String referer = NullDefault.getString(req.getHeader("Referer"), "");

        if (reqUrl.endsWith(map.findForward("gf_auth").getPath())
        && cmd.equals("AUTH_ERROR")) {
            return true;
        }


        log__.debug("Referer = " + referer);
        if (!referer.endsWith("/common/cmn001.do") && referer.length() == 0) {
            return true;
        }

        //サーバ名称が含まれているかをチェックする
        String refServerName = referer.toString();
        int refererIndex = referer.indexOf("://");
        if (refererIndex >= 0) {
            refServerName = refServerName.substring(refererIndex + 3);
        }

        if (refServerName.indexOf(":") >= 0) {
            refServerName = refServerName.substring(0, refServerName.indexOf(":"));
        } else if (refServerName.indexOf("/") >= 0) {
            refServerName = refServerName.substring(0, refServerName.indexOf("/"));
        }

        while (serverNames.hasMoreTokens()) {
            result = refServerName.equals(serverNames.nextToken());
            if (result) {
                break;
            }
        }

        return result;
    }

    /**
     * <br>[機  能] パスワード変更の可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:変更可能 false:変更不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canChangePassword(Connection con, int userSid)
    throws SQLException {
        return _getLoginInstance().canChangePassword(con, userSid);
    }

    /**
     * <br>[機  能] ログインユーザがシステム管理者かを判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return true:管理者権限あり false:管理者権限なし
     * @throws SQLException SQL実行時例外
     */
    protected boolean _isSystemAdmin(HttpServletRequest req, Connection con)
    throws SQLException {

        BaseUserModel smodel = getSessionUserModel(req);
        if (smodel != null && smodel.isAdmin()) {
            return true;
        }
        return false;  //権限エラー
    }

    /**
     * <br>[機  能] ログイン処理の実装クラスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ログイン処理の実装クラス
     */
    protected ILogin _getLoginInstance() {

        CommonBiz cmnBiz = new CommonBiz();
        return cmnBiz.getLoginInstance();

    }

    /**
     * [機  能] executeメソッド実行時に例外(Exception)が発生した場合の処理<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param e executeメソッド実行時に発生した例外(Exception)
     * @return true:Exceptionをthrowする false:Exceptionをthrowしない
     */
    protected boolean _doExcecuteException(ActionMapping map,
                                            ActionForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            Exception e) {
        return true;
    }

    /**
     * [機  能] executeメソッド実行時に例外(Throwable)が発生した場合の処理<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param e executeメソッド実行時に発生した例外 or エラー(Throwable)
     * @return true:Exceptionをthrowする false:Exceptionをthrowしない
     */
    protected boolean _doExcecuteThrowable(ActionMapping map,
                                            ActionForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            Throwable e) {

        return true;
    }

    /**
     * <br>[機  能] Connectionに設定する自動コミットモードを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return AutoCommit設定値
     */
    protected boolean _getAutoCommit() {
        return false;
    }

    /**
     * <br>[機  能] 指定したメッセージキーに対応したメッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param msgKey メッセージキー
     * @return メッセージ
     */
    public String getInterMessage(HttpServletRequest req, String msgKey) {
        GsMessage gsMsg = new GsMessage();
        return gsMsg.getMessage(req, msgKey);
    }

    /**
     * <br>[機  能] 指定したメッセージキーに対応したメッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param msgKey メッセージキー
     * @return メッセージ
     */
    public String getInterMessage(RequestModel reqMdl, String msgKey) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        return gsMsg.getMessage(msgKey);
    }
    /**
     *
     * <br>[機  能] アクション開始部 ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void writeStartLog(HttpServletRequest req) {
        return;
    }
    /**
     *
     * <br>[機  能] アクション終了部 ログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void writeEndLog(HttpServletRequest req) {
        return;
    }
}