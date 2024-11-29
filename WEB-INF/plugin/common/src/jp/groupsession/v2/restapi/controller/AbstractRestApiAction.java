package jp.groupsession.v2.restapi.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.BasicAuthorizationUtil;
import jp.co.sjts.util.lang.ClassUtil;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.LoginModel;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.login.biz.ITokenAuthBiz;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.cmn.restapi.users.authentications.EnumResourceReasonCode;
import jp.groupsession.v2.rap.mbh.IMbhUUIDBiz;
import jp.groupsession.v2.rap.mbh.MbhUUIDBiz;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.NoAuthrization;
import jp.groupsession.v2.restapi.controller.annotation.NoLoginSession;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.exception.DynamicPluginReasonCode;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiAuthException;
import jp.groupsession.v2.restapi.exception.RestApiDBBackupGuardException;
import jp.groupsession.v2.restapi.exception.RestApiDBConnectException;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.exception.RestApiInvalidMethodException;
import jp.groupsession.v2.restapi.exception.RestApiNotFoundException;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiPrivateMethodException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.filter.RestApiRequestFilter;
import jp.groupsession.v2.restapi.filter.annotation.AddFilter;
import jp.groupsession.v2.restapi.parameter.ParamModelCreator;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

public abstract class AbstractRestApiAction extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractRestApiAction.class);
    /** テンポラリディレクトリサブパス*/
    private static final String TEMPORARY_SUBPATH = "temporary";


    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return "api";
    }
    /**
     * サブクラスでのオーバライド禁止
     */
    @Override
    public final ActionForward execute(ActionMapping arg0, ActionForm arg1,
            ServletRequest arg2, ServletResponse arg3) throws Exception {
        return super.execute(arg0, arg1, arg2, arg3);
    }
    /**
     * スーパクラスでのコネクションチェック回避
     * サブクラスでのオーバライド禁止
     */
    @Override
    public final boolean canNoConnection(HttpServletRequest req, ActionForm form) {
        return true;
    }
    /**
     * スーパクラスでのセッションチェック回避
     * サブクラスでのオーバライド禁止
     */
    @Override
    public final boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }
    /**
     * 一般ユーザでのアクセス許可
     * サブクラスでのオーバライド禁止
     */
    @Override
    public final boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }
    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected final boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        return true;
    }
    /** プラグインが使用可能か判定します
     * @param plugins 使用可能なプラグインID
     * @return boolean true:使用可能 false:使用不可
     */
    @Override
    public final boolean canAccessPlugin(List<String> plugins) {
        return true;
    }
    /** APIプラグインが使用可能か判定します
     * @param plugins 使用可能なプラグインID
     * @return boolean true:使用可能 false:使用不可
     */
    public boolean canAccessApiPlugin(List<String> plugins) {
        for (String plugin : plugins) {
            if (getPluginId() == null
             || plugin.equals("api")) {
                log__.debug("使用可能なプラグインです。api");
                return true;
            }
        }
        log__.debug("使用不可なプラグインです。api");
        return false;
    }

    /**
     * セッション使用時にセッションにドメイン情報を保管
     * サブクラスでのオーバライド禁止
     */
    @Override
    protected final void _saveCheckedDomain(HttpServletRequest req) {
        if (req.getSession(false) != null) {
            super._saveCheckedDomain(req);
        }
    }
    /**
     * RESTAPIはセッションの認証情報を必須としない
     * サブクラスでのオーバライド禁止
     */
    @Override
    public  final boolean isSession(HttpServletRequest req) {
        return false;
    }
    /**
     * <p>DB用のコネクションを取得します。
     * @param req リクエスト
     * @return DB用のコネクション
     */
    private Connection __getConnection(HttpServletRequest req) {


        GroupSession servlet = (GroupSession) getServlet();
        Connection con = null;
        con = RequestLocal.get(GSConstApi.LOCALKEY_CONNECTION, Connection.class);
        if (con != null) {
            return con;
        }
        boolean result = false;
        try {
            con = servlet.getConnection(req);
            con.setAutoCommit(false);
            result = true;
        } catch (DBBackupGuardException e) {
            throw new RestApiDBBackupGuardException();
        } catch (Exception e) {
            log__.error("コネクションの取得に失敗", e);
            throw new RestApiDBConnectException();
        } finally {
            if (!result) {
                closeConnection(con);
            }
        }
        return con;
    }
    /**
     * リクエストモデルを返す
     * 都度生成せず、キャッシュしたモデルが再利用される
     * @param req リクエスト
     * @return リクエストモデル
     */
    public final RequestModel getRequestModel(HttpServletRequest req) {
        try {
            return RestApiContext._getInstance().getRequestModel();
        } catch (Exception e) {
            RequestModel ret  = super.getRequestModel(req);

            //sessionの不用意な利用を防ぐためRequestModelからはsessionを削除
            ret.setSession(null);

            ret.setActionPath(String.format("/restapi%s", ret.getActionPath()));
            return ret;
        }
    }

    /**
     * <br>[機  能] RestApi共通処理
     * <br>[解  説] RESTAPIではセッションによる認証を行わず、コネクションはnull
     * <br>[備  考] サブクラスでのオーバーライド禁止
     * @param mapping マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション 必ずnull
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    @Override
    public final ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __executeAction(mapping, form, req, res);
    }
    /**
     * <br>[機  能] RestApi共通処理
     * <br>[解  説]
     * <br>[備  考]
     * @param mapping マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public final  ActionForward __executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        RestApiContext ctx = null;
        /** アクションメソッド*/
        Method method;
        try {
            method = __mapDoMethod(req, mapping);
        } catch (RestApiException e) {
            if (ctx == null) {
                ctx = RestApiContext._builder()
                        .setMap(mapping)
                        .setMessageResources(getResources(req))
                        .build(req);
            }
            e.write(req, res, ctx);
            ctx.close();
            return null;
        } catch (Throwable e) {
            if (ctx == null) {
                ctx = RestApiContext._builder()
                        .setMap(mapping)
                        .setMessageResources(getResources(req))
                        .build(req);
            }
            log__.error(e);
            if (e.getCause() != null) {
                log__.error(e.getCause());
            }
            new RestApiException(e).write(req, res, ctx);
            return null;
        }

        try (AutoCloseable logBlock = __restApiLogBlock(req);
             Connection con = __getConnection(req);
                ) {

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
                        IMbhUUIDBiz uidBiz = MbhUUIDBiz.getInstance();
                        GsMessage gsMsg = new GsMessage(req);
                        throw uidBiz.createRestApiUUIDException(gsMsg);
                    });
            }


            PluginConfig adminPconfig = null;
            adminPconfig = getPluginConfig(req);
            RequestModel reqMdl = getRequestModel(req);

            BaseUserModel umodel = null;

            if (!method.isAnnotationPresent(NoLoginSession.class)) {
                umodel = reqMdl.getSmodel();
            }
            //ログインセッション有効時、不正ドメインアクセスをチェックする
            if (umodel != null && !canAccessDomain(req)) {
                throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_TOKEN)
                .setMessageResource(
                        "error.notfound.token"
                        );
            }

            if (!method.isAnnotationPresent(NoAuthrization.class)) {
                if (umodel != null) {
                    if (umodel.getUsrsid() == 1) {
                        //システムメールユーザはログイン不可
                        throw new RestApiPermissionException(
                                ReasonCode.EnumError.IMPERMISSIBLE,
                                "error.inpermissive.resource.path");
                    }
                    if (umodel.getUsrsid() == 0) {
                        //APIかつGS管理者の場合はログイン不可
                        throw new RestApiPermissionException(
                                ReasonCode.EnumError.IMPERMISSIBLE,
                                "error.inpermissive.resource.path");
                    }

                }
                umodel = Optional.ofNullable(umodel)
                            .orElseGet(() -> {
                    //ログインセッションがない場合はヘッダー情報で認証を行う
                    LoginResultModel result = null;
                    String auth = NullDefault.getString(req.getHeader("Authorization"), "");
                    if (auth.startsWith("Bearer")) {
                        try {
                            writeApiInfoLog(req, "トークン認証 START");
                            result = __authToken(mapping, req, res, con);
                        } catch (SQLException e) {
                            log__.error("認証時 SQLエクセプション", e);
                            throw new RuntimeException("認証時 SQLエクセプション", e);
                        } finally {
                            writeApiInfoLog(req, "トークン認証 END");
                        }
                    } else if (auth.startsWith("Basic")) {
                        try {
                            writeApiInfoLog(req, "BASIC認証 START");
                            result = __authBasic(mapping, req, res, con);
                        } catch (SQLException e) {
                            log__.error("認証時 SQLエクセプション", e);
                            throw new RuntimeException("認証時 SQLエクセプション", e);
                        } finally {
                            writeApiInfoLog(req, "BASIC認証 END");
                        }

                    } else {
                        res.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
                        throw new RestApiAuthException()
                            .setReasonCode(EnumError.AUTH_TOKEN)
                            .setMessageResource(
                                    "error.notfound.token"
                                    );
                    }
                    return result.getLoginUser();
                });
                //管理者判定
                GroupDao gdao = new GroupDao(con);
                if (gdao.isBelongAdmin(umodel.getUsrsid())) {
                    //管理者
                    umodel.setAdminFlg(true);
                } else {
                    //一般
                    umodel.setAdminFlg(false);
                }


            }

            PluginConfig pconfig;
            if (umodel == null) {
                pconfig = getPluginConfigForMain(adminPconfig, con, reqMdl);
            } else {
                pconfig = getPluginConfigForMain(adminPconfig, con, umodel.getUsrsid(), reqMdl);
            }

            reqMdl.setSmodel(umodel);
            ctx = RestApiContext._builder()
                .setAppRootPath(getAppRootPath())
                .setCon(con)
                .setMap(mapping)
                .setPconfig(pconfig)
                .setReqMdl(reqMdl)
                .setMessageResources(getResources(req))
                .build(req);

            //認証後 個体識別番号制御によるファイアウォールチェック
            if (umodel != null) {

                if (!firewall.additionalCheckForMbl(ctx.getRequestUserModel(), con, false)) {
                    return firewall.handlingFirewallError(
                        () -> {
                            try {
                                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return null;
                        },
                        () -> {
                            IMbhUUIDBiz uidBiz = MbhUUIDBiz.getInstance();

                            GsMessage gsMsg = new GsMessage(req);
                            throw uidBiz.createRestApiUUIDException(gsMsg);

                        });
                }
            }
            //使用不可のプラグインへアクセス権限チェック
            __checkCanAccessPlugin(ctx, adminPconfig);

            __updateSession(req, ctx, false);

            boolean filterFinish =
                    RestApiRequestFilter.executeActionFilter(
                            req,
                            res,
                            method.getAnnotationsByType(AddFilter.class));

            //フィルタによる中断がなければActionメソッドを実行
            if (filterFinish) {
                __doApi(method, req, res);
            }

        } catch (RestApiException e) {
            if (ctx == null) {
                ctx = RestApiContext._builder()
                        .setMap(mapping)
                        .setMessageResources(getResources(req))
                        .build(req);
            }
            e.write(req, res, ctx);
        } catch (Throwable e) {
            if (ctx == null) {
                ctx = RestApiContext._builder()
                        .setMap(mapping)
                        .setMessageResources(getResources(req))
                        .build(req);
            }
            log__.error("予期せぬエラー", e);
            new RestApiException(e).write(req, res, ctx);
        }
        ctx.close();
        return null;
    }
    /**
     *
     * <br>[機  能] プラグイン権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPI コンテキスト
     * @param adminPconfig 共通プラグインコンフィグ
     */
    private void __checkCanAccessPlugin(RestApiContext ctx, PluginConfig adminPconfig) {
        PluginConfig pconfig = ctx.getPluginConfig();
        RequestModel reqMdl = ctx.getRequestModel();
        if (pconfig.getPlugin("api") == null) {
            throw new RestApiNotFoundException()
            .setReasonCode(EnumError.RESOURCE_USER_CANTUSE_API)
            .setMessageResource("error.cant.use.plugin",
                    adminPconfig.getPlugin("api").getName(reqMdl)
                    );
        }
        Plugin[] ants = getClass().getAnnotationsByType(Plugin.class);
        for (Plugin ant : ants) {
            if (pconfig.getPlugin(ant.value()) == null) {
                //各プラグインごとのエラーコードを取得
                DynamicPluginReasonCode reasonCode = new DynamicPluginReasonCode(
                    ant.value(), DynamicPluginReasonCode.PLUGIN_CANT_USE);
                throw new RestApiNotFoundException()
                .setReasonCode(reasonCode)
                .setMessageResource("error.cant.use.plugin",
                        adminPconfig.getPlugin(ant.value()).getName(reqMdl)
                        );
            }
        }
        String action_pluginId = getPluginId();
        if (pconfig.getPlugin(action_pluginId) == null) {
            //各プラグインごとのエラーコードを取得
            DynamicPluginReasonCode reasonCode = new DynamicPluginReasonCode(
                action_pluginId, DynamicPluginReasonCode.PLUGIN_CANT_USE);
            throw new RestApiNotFoundException()
            .setReasonCode(reasonCode)
            .setMessageResource("error.cant.use.plugin",
                    adminPconfig.getPlugin(action_pluginId).getName(reqMdl)
                    );
        }

    }
    /**
     *
     * <br>[機  能] APIの実行
     * <br>[解  説]
     * <br>[備  考]
     * @param method 実行メソッド
     * @param req リクエスト
     * @param res レスポンス
     */
    @SuppressWarnings("unchecked")
    private void __doApi(
            Method method,
            HttpServletRequest req,
            HttpServletResponse res) {
        final RestApiContext ctx = RestApiContext._getInstance();
        if (method == null) {
            throw new RestApiPrivateMethodException();
        }

        //バリデートエラーリスト
        List<RestApiValidateException> validErr = new ArrayList<>();

        //メソッド引数設定
        List<Object> params = new ArrayList<>();
        for (Parameter param : method.getParameters()) {
            if (param.isAnnotationPresent(ParamModel.class)
                    || param.getType().isAnnotationPresent(ParamModel.class)) {
                Object paramMdl = null;
                ParamModelCreator<Object> crt = new ParamModelCreator<Object>(
                                           (Class<Object>) param.getType(), ctx);
                try {
                    paramMdl = crt.execute(req);
                } catch (RestApiValidateException e) {
                    validErr.add(e);
                }
                params.add(paramMdl);
                continue;
            }
            if (param.getType().isAssignableFrom(ActionMapping.class)) {
                params.add(RestApiContext._getInstance().getMap());
                continue;
            }
            if (param.getType().isAssignableFrom(Connection.class)) {
                params.add(ctx.getCon());
                continue;
            }
            if (param.getType().isAssignableFrom(HttpServletRequest.class)) {
                params.add(req);
                continue;
            }
            if (param.getType().isAssignableFrom(HttpServletResponse.class)) {
                params.add(res);
                continue;
            }
            if (param.getType().isAssignableFrom(BaseUserModel.class)) {
                params.add(ctx.getRequestUserModel());
                continue;
            }

            if (param.getType().isAssignableFrom(RestApiContext.class)) {
                params.add(ctx);
                continue;
            }

            if (param.getType().isAssignableFrom(GSTemporaryPathModel.class)) {
                params.add(__getTempDirPath(req, ctx));
                continue;
            }

            params.add(null);

        }
        if (validErr.size() > 0) {
            throw new RestApiValidateExceptionNest(validErr);
        }
        try {
            Object[] paramArr = params.toArray(new Object[method.getParameterCount()]);
            method.invoke(this, paramArr);

        } catch (IllegalAccessException | IllegalArgumentException
                e) {
            throw new RuntimeException("リクエストメソッド実行失敗", e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RestApiException) {
                throw (RestApiException) e.getTargetException();
            }
            throw new RuntimeException("リクエストメソッド実行失敗", e);
        }

    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリモデルを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param ctx コンテキスト
     * @return テンポラリディレクトリモデル
     */
    private GSTemporaryPathModel __getTempDirPath(HttpServletRequest req,
            RestApiContext ctx) {

        __updateSession(req, ctx, true);
        ctx.getRequestModel().getSession().setAttribute(
                GSConst.SESSION_KEY_DOMAIN,
                ctx.getRequestModel().getDomain());

        return GSTemporaryPathModel.getInstance(ctx.getRequestModel(),
                GSConst.PLUGINID_API,
                TEMPORARY_SUBPATH);
    }
    /**
     * <br>[機  能] BASIC認証を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @throws Exception SQL例外等
     * @return ユーザ情報。認証に失敗した場合はnullを返す。
     */
    private LoginResultModel __authBasic(
            ActionMapping map, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws SQLException {
        IApiConfBiz apiConf;
        try {
            apiConf = (IApiConfBiz) Class.forName("jp.groupsession.v2.api.biz.ApiConfBiz")
            .getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new RuntimeException("API設定取得例外", e);
        }
        //ベーシック認証が使用不可の場合はエラーXML
        if (!apiConf.isAbleBasicAuth(req, con)) {
            res.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
            throw new RestApiAuthException()
            .setReasonCode(EnumError.AUTH_CANT_USE_BASIC)
            .setMessageResource("error.cant.use.basic");
        }

        String loginId = null;
        LoginResultModel resultModel = null;
        try {
            loginId = BasicAuthorizationUtil.getUserID(req);
            String password = BasicAuthorizationUtil.getPassword(req);

            //ログイン処理に必要な情報を設定する
            LoginModel loginModel = new LoginModel();
            loginModel.setLoginId(loginId);
            loginModel.setPassword(password);
            loginModel.setCon(con);
            loginModel.setReq(req);
            loginModel.setMap(map);
            //使用可能プラグイン情報を取得
            RequestModel reqMdl = getRequestModel(req);
            PluginConfig pconf = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
            loginModel.setPluginConfig(pconf);
            //ログイン処理を行う
            ILogin loginBiz = _getLoginInstance();
            resultModel = loginBiz.idpassAuthApi(loginModel);

            log__.debug("loginId = " + loginId);
            log__.debug("password = " + password);
        } catch (SQLException e) {
            log__.error("SQL実行エラー:ログイン処理の実行に失敗", e);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (resultModel.getErrCode() == LoginResultModel.ECODE_USERNONE) {
            res.setHeader(
                    "WWW-Authenticate",
                    "Basic realm=\"" + req.getContextPath() + "\"");
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_TOKEN)
                .setMessageResource(
                        "error.notfound.token"
                        );
        }

        if (resultModel.getErrCode() == LoginResultModel.ECODE_SYSMAIL_LOGIN) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.inpermissive.resource.path"
                    );
        }


        if (resultModel.getErrCode() == LoginResultModel.ECODE_LOGINTEISI_USER) {
            throw new RestApiPermissionException(
                    EnumResourceReasonCode.RESOURCE_USER_LOGIN_STOP,
                    "error.auth.stop.login.api");
        }


        if (resultModel.getErrCode() != LoginResultModel.ECODE_NONE) {
            res.setHeader(
                    "WWW-Authenticate",
                    "Basic realm=\"" + req.getContextPath() + "\"");
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_TOKEN)
                .setMessageResource(
                        ((ActionMessage) resultModel.getErrors().get().next())
                            .getKey()
                        );
        }

        return resultModel;
    }

    /**
     *
     * <br>[機  能] Token認証を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return 認証結果
     * @throws SQLException 実行時例外
     */
    private LoginResultModel __authToken(ActionMapping map,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        String auth = req.getHeader("Authorization");
        if (auth == null) {
            return null;
        }
        String token = auth.substring(auth.lastIndexOf(' ') + 1);
        /**ApiToken認証クラス*/
        ITokenAuthBiz tokenBiz;
        try {
            tokenBiz = (ITokenAuthBiz) ClassUtil.getDefConstractorObject(
                    "jp.groupsession.v2.api.biz.ApiTokenBiz");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException("インスタンス生成エラー", e);
        }
        //API設定によるトークン使用制限チェック
        if (!tokenBiz.isAbleTokenAuth(req, con)) {
            res.setHeader(
                    "WWW-Authenticate",
                    "Basic realm=\"" + req.getContextPath() + "\"");
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_CANT_USE_TOKEN)
                .setMessageResource("error.cant.use.token");
        }

        LoginResultModel ret = tokenBiz.authToken(token, con);
        if (ret.getErrCode() == LoginResultModel.ECODE_SYSMAIL_LOGIN) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.inpermissive.resource.path"
                    );
        }

        if (ret.getErrCode() == LoginResultModel.ECODE_LOGINTEISI_USER) {
            throw new RestApiPermissionException(
                    EnumResourceReasonCode.RESOURCE_USER_LOGIN_STOP,
                    "error.auth.stop.login.api");
        }


        if (ret.getErrCode() != LoginResultModel.ECODE_NONE) {
            res.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_TOKEN)
                .setMessageResource(
                        ((ActionMessage) ret.getErrors().get().next())
                            .getKey()
                        );
        }


        return ret;
    }

    /**
     *
     * <br>[機  能] 開始終了ログ
     * <br>[解  説] try with resource内で使用し処理の開始終了でのログ出力を行う
     * <br>[備  考]
     * @param req リクエスト
     * @return ログブロック
     */
    private AutoCloseable __restApiLogBlock(HttpServletRequest req) {
        String logHead = __getLogHead(req);
        if (isWriteApiLog()) {
            log__.info(logHead + "--------- START -------------");
            String ip = null;
            //IPアドレス
            try {
                ip = CommonBiz.getRemoteAddr(req);
            } catch (RuntimeException e) {
            }

            log__.info(logHead + "ipadress = " + ip);
            BaseUserModel smodel = null;

            String cookie = req.getHeader("Cookie");
            log__.info(logHead + "Cookie = " + cookie);

            HttpSession session = req.getSession(false);
            if (session != null) {
                Object tmp = session.getAttribute(GSConst.SESSION_KEY);
                if (tmp != null) {
                    smodel = (BaseUserModel) tmp;
                }
            }
            if (smodel != null) {
                log__.info(logHead + "start sessionUserSid = " + smodel.getUsrsid());
                log__.info(logHead + "start sessionLoginId = " + smodel.getLgid());
                log__.info(logHead + "start sessionDomain = " + smodel.getDomain());

            } else {
                log__.info(logHead + "start session = null");

            }

            String auth = req.getHeader("Authorization");

            log__.info(logHead + "auth = " + auth);
        }
        return new AutoCloseable() {
            @Override
            public void close() throws Exception {
                if (!isWriteApiLog()) {
                    return;
                }
                String logHead = __getLogHead(req);

                BaseUserModel smodel = null;
                HttpSession session = req.getSession(false);
                if (session != null) {
                    Object tmp = session.getAttribute(GSConst.SESSION_KEY);
                    if (tmp != null) {
                        smodel = (BaseUserModel) tmp;
                    }
                }
                if (smodel != null) {
                    log__.info(logHead + "finish sessionUserSid = " + smodel.getUsrsid());
                    log__.info(logHead + "finish sessionLoginId = " + smodel.getLgid());
                    log__.info(logHead + "finish sessionDomain = " + smodel.getDomain());

                } else {
                    log__.info(logHead + "finish session = null");

                }

                log__.info(logHead + "--------- END -------------");
            }
        };
    }

    @Override
    public final void writeStartLog(HttpServletRequest req) {
        super.writeStartLog(req);
    }
    @Override
    public final void writeEndLog(HttpServletRequest req) {
        super.writeEndLog(req);
    }

    /**
     *
     * <br>[機  能] APIログ固定 冒頭部作成
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return PIログ固定 冒頭部
     */
    private String __getLogHead(HttpServletRequest req) {
        String domain = null;
        try {
            domain = GroupSession.getResourceManager().getDomainCheck(req);
        } catch (Exception e1) {
        }
        long threadId = Thread.currentThread().getId();
        String actionPath = (String) req.getAttribute(GSConst.REQ_ATTRKEY_ACTIONPATH);
        String logHead = domain  + actionPath + "(" + threadId + ") : ";
        return logHead;
    }

    /**
    *
    * <br>[機  能] APIログ出力フラグ
    * <br>[解  説]
    * <br>[備  考]
    * @return 出力可否
    */
    public boolean isWriteApiLog() {
        return false;
    }
    /**
     *
     * <br>[機  能] API用InfoLogの出力
     * <br>[解  説] API毎の出力対象フラグで制御できる
     * <br>[備  考]
     * @param req リクエスト
     * @param mes 出力メッセージ
     */
    public void writeApiInfoLog(HttpServletRequest req, String mes) {
        if (!isWriteApiLog()) {
            return;
        }
        String logHead = __getLogHead(req);
        log__.info(logHead + mes);
    }
    /**
     *
     * <br>[機  能] アクションメソッド振り分け
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param map アクションマップ
     * @return アクションメソッド
     */
    Method __mapDoMethod(HttpServletRequest req, ActionMapping map) {
        List<Method> methods;
        Method method = null;

        //メソッド判定
        switch (req.getMethod()) {
        case "GET":
            methods = Stream.of(getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Get.class))
                .collect(Collectors.toList());
            break;
        case "POST":
            methods = Stream.of(getClass().getMethods())
            .filter(m -> m.isAnnotationPresent(Post.class))
            .collect(Collectors.toList());
            break;
        case "PUT":
            methods = Stream.of(getClass().getMethods())
            .filter(m -> m.isAnnotationPresent(Put.class))
            .collect(Collectors.toList());
            break;
        case "DELETE":
            methods = Stream.of(getClass().getMethods())
            .filter(m -> m.isAnnotationPresent(Delete.class))
            .collect(Collectors.toList());
            break;
        default:
            throw new RestApiInvalidMethodException();
        }

        //パラメータ指定のメソッドを探索
        method = methods.stream()
                .filter(m -> {
                    jp.groupsession.v2.restapi.controller.annotation.Parameter[] ants
                        = m.getAnnotationsByType(
                            jp.groupsession.v2.restapi.controller.annotation.Parameter.class
                            );
                    if (ants == null || ants.length == 0) {
                        return false;
                    }
                    return Stream.of(ants)
                        .allMatch(p -> {
                            String param;
                            param = req.getParameter(p.name());
                            if (!StringUtil.isNullZeroString(param)) {
                                if (Objects.equals(
                                        NullDefault.getString(param, ""),
                                        p.value())) {
                                    return true;
                                }
                                return false;
                            }
                            param = map.getProperty(p.name());
                            if (!StringUtil.isNullZeroString(param)) {
                                if (Objects.equals(
                                        NullDefault.getString(param, ""),
                                        p.value())) {
                                    return true;
                                }
                                return false;
                            }
                            param = map.getParameter();
                            if (!StringUtil.isNullZeroString(param)) {
                                if (Objects.equals(p.name(), "parameter")
                                        && Objects.equals(NullDefault.getString(param, ""),
                                                p.value())) {
                                    return true;
                                }
                                if (Objects.equals(NullDefault.getString(param, ""),
                                        p.value())) {
                                    return true;
                                }
                                return false;
                            }
                            if (Objects.equals(NullDefault.getString(param, ""),
                                    p.value())) {
                                return true;
                            }
                            return false;
                        });
                })
                .findAny()
                .orElse(null);
        if (method == null) {
            //パラメータ指定をいずれも満たさない場合
            //パラメータなし
            method = methods.stream()
                    .filter(m -> {
                        jp.groupsession.v2.restapi.controller.annotation.Parameter[] ants
                            = m.getAnnotationsByType(
                                jp.groupsession.v2.restapi.controller.annotation.Parameter.class
                                );
                        if (ants == null || ants.length == 0) {
                            return true;
                        }
                        return false;
                    })
                    .findAny()
                    .orElse(null);
        }

        if (method == null) {
            throw new RestApiInvalidMethodException();
        }
        return method;


    }
    /**
     *
     * <br>[機  能] セッション時間の更新
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param ctx RESTAPIコンテキスト
     * @param create true なければセッションを生成する
     */
    private void __updateSession(HttpServletRequest req,
            RestApiContext ctx, boolean create) {
        CommonBiz cmnBiz = new CommonBiz();
        try {
            HttpSession session = req.getSession(create);
            if (session == null) {
                return;
            }
            session.setMaxInactiveInterval(cmnBiz.getSessionTime(ctx.getCon()));
            ctx.getRequestModel().setSession(session);
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }

    }

}
