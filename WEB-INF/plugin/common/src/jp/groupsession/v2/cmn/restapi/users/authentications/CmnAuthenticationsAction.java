package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.biz.ITokenAuthBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.NoAuthrization;
import jp.groupsession.v2.restapi.controller.annotation.NoLoginSession;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiAuthException;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.filter.annotation.AddFilter;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 認証情報取得API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnAuthenticationsAction extends AbstractRestApiAction {
    /** ユーザ情報レスポンス 用パラメータ名配列*/
    private static final String[] PARAMNAMES_TOKENINF =
            Stream.concat(
                    Stream.of("tokenText"),
                    Optional.ofNullable(
                            CmnUsersResultModel.class.getAnnotation(ResponceModel.class)
                            )
                        .map(ant -> ant.targetField())
                        .stream()
                        .flatMap(fields -> Arrays.stream(fields))
                        )
                .toArray(String[]::new);


    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param obj javaBean
     * @param fields 出力パラメータ名配列 （出力順に反映）
     * @param res サーブレットレスポンス
     * @param ctx RestApiコンテキスト
     */
    private void __writeElement(
            Object obj,
            String[] fields,
            HttpServletResponse res, RestApiContext ctx) {
        JSONObject json;
            json = JSONObject.fromObject(obj);
        ResultElement ret = new ResultElement("result");
        for (int i = 0; i < fields.length; i++) {
            String propName = fields[i];
            if (!json.containsKey(propName)) {
                continue;
            }
            String propObj = json.optString(propName);
            ret.addContent(new ResultElement(propName).addContent(propObj));
        }
        RestApiResponseWriter.builder(res, ctx)
        .addResult(
                ret
                    )
        .build().execute();


    }

    /**
     *
     * <br>[機  能] 認証トークンを作成
     * <br>[解  説] ベーシック認証
     * <br>[備  考]
     * @param req サーブレットリクエスト
     * @param res サーブレットレスポンス
     * @param ctx RestApiコンテキスト
     */
    @Post
    @Parameter(name = "action", value = "")
    @NoLoginSession
    @NoAuthrization
    @AddFilter(CmnAuthenticationsPostFilter.class)
    public void doPost(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx) {

        ISessionWriter sw = RequestLocal.get(
                GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                ISessionWriter.class);
        sw.doLogin(req, ctx);

        __postAuthrizationToken(req, res, ctx);

        //「ログイン成功」オペレーションログ登録
        __outputLoginLog(ctx);
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param ctx コンテキスト
     */
    private void __postAuthrizationToken(HttpServletRequest req,
            HttpServletResponse res, RestApiContext ctx) {


        //トークン生成
        IApiConfBiz apiConf;
        ITokenAuthBiz tokenBiz;
        try {
            apiConf  =
                    (IApiConfBiz) Class.forName("jp.groupsession.v2.api.biz.ApiConfBiz")
                    .getDeclaredConstructor().newInstance();
            tokenBiz =
                    (ITokenAuthBiz) Class.forName("jp.groupsession.v2.api.biz.ApiTokenBiz")
                    .getDeclaredConstructor(Connection.class, RequestModel.class)
                    .newInstance(ctx.getCon(), ctx.getRequestModel());

            String token = "";

            if (!apiConf.isAbleTokenAuth(req, ctx.getCon())) {
                throw new RestApiPermissionException(
                        EnumResourceReasonCode.RESOURCE_TOKEN_NOTUSE,
                        "error.cant.use.token");
            }
            token = tokenBiz.createToken(
                    req,
                    ctx.getRequestUserModel(),
                    apiConf.getConf(ctx.getCon()));
            CmnUsersResultModel resMdl = CmnUsersResultCreator
                    .builder(ctx.getCon())
                    .putUsrSid(Set.of(ctx.getRequestUserSid()))
                    .setAccsessUsrSid(ctx.getRequestUserSid())
                    .build()
                    .execute(ctx.getCon())
                    .get(0);
            JSONObject json  = JSONObject.fromObject(resMdl);
            json.put("tokenText", token);

            __writeElement(json, PARAMNAMES_TOKENINF,  res, ctx);

        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new RuntimeException("インスタンス生成例外", e);

        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }
    }

    /**
     *
     * <br>[機  能] 認証トークンを作成
     * <br>[解  説] ワンタイムパスワード認証時
     * <br>[備  考]
     * @param req
     * @param res
     * @param ctx
     */
    @NoLoginSession
    @NoAuthrization
    @Post
    @Parameter(name = "action", value = "onetimepassword-login")
    @AddFilter(CmnAuthenticationsOtpPostFilter.class)
    public void doPostOtp(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx) {
        //認証フィルタで設定されたISessionWriterでログインセッションの生成
        ISessionWriter sw = RequestLocal.get(
                GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                ISessionWriter.class);
        sw.doLogin(req, ctx);

        __postAuthrizationToken(req, res, ctx);

        //「ログイン成功」オペレーションログ登録
        __outputLoginLog(ctx);
    }

    /**
     *
     * <br>[機  能] トークン生存確認
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param param パラメータ
     * @param umodel ユーザ情報
     * @param ctx RestApiContext
     * @throws SQLException SQL実行時例外
     */
    @Get
    @NoLoginSession
    @AddFilter(CmnAuthenticationsGetFilter.class)
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            Connection con,
            CmnAuthenticationsGetParamModel param,
            BaseUserModel umodel,
            RestApiContext ctx) throws SQLException {

        //トークン生存確認はトークン認証のみ
        String auth = NullDefault.getString(req.getHeader("Authorization"), "");
        if (!auth.startsWith("Bearer")) {
            res.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
            throw new RestApiAuthException()
                .setReasonCode(EnumError.AUTH_TOKEN)
                .setMessageResource(
                        "error.notfound.token"
                        );

        }
        //トークンに紐づくユーザと指定ユーザが一致しない場合
        if (!Objects.equals(umodel.getLgid(), param.getUserId())) {
            throw new RestApiAuthException()
            .setReasonCode(EnumError.AUTH_TOKEN)
            .setMessageResource(
                    "error.notfound.token"
                    );

        }
        //ログインセッションユーザと指定ユーザが一致しない場合
        Optional.ofNullable(getSessionUserModel(req))
            .ifPresent(sessionUser -> {
                if (!Objects.equals(sessionUser.getLgid(), param.getUserId())) {
                    throw new RestApiAuthException()
                    .setReasonCode(EnumError.AUTH_TOKEN)
                    .setMessageResource(
                            "error.notfound.token"
                            );

                }

            });

        //セッション切れの場合はログインセッションを生成
        if (getSessionUserModel(req) == null) {
            Optional.ofNullable(
                    RequestLocal.get(
                    GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                    ISessionWriter.class))
                .ifPresent(sw -> {
                    sw.doLogin(req, ctx);
                    });
        }

        CmnUsersResultModel resMdl = CmnUsersResultCreator
                .builder(ctx.getCon())
                .putUsrSid(Set.of(umodel.getUsrsid()))
                .setAccsessUsrSid(umodel.getUsrsid())
                .build()
                .execute(ctx.getCon())
                .get(0);
        RestApiResponseWriter.builder(res, ctx)
        .addResult(
                resMdl
                    )
        .build().execute();

    }

    /**
     * <br>[機  能] 「ログイン成功」オペレーションログを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiContext
     */
    private void __outputLoginLog(RestApiContext ctx) {
        //ログ出力(ログイン)
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonApiLog(
            ctx.getRequestModel(),
            ctx.getCon(),
            ctx.getRequestUserSid(),
            getClass().getName(),
            gsMsg.getMessage("cmn.login"),
            GSConstLog.LEVEL_INFO,
            NullDefault.getString(ctx.getRequestUserModel().getLgid(), ""));
    }
}
