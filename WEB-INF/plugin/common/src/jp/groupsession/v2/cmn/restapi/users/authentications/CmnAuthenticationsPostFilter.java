package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.login.otp.OtpSendResult;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiAuthException;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.filter.IActionFilter;
import jp.groupsession.v2.restapi.filter.RestApiActionFilterChain;
import jp.groupsession.v2.restapi.parameter.ParamModelCreator;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnAuthenticationsPostFilter implements IActionFilter {
    /** ワンタイムパスワード要求レスポンス 用パラメータ名配列*/
    private static final String[] PARAMNAMES_NEED_OTP
    = new String[] {
            /** メッセージ                                        */
            "messageText",
            /** 理由区分コード                                      */
            "reasonCodeText",
            /** ログイン方法                                      */
            "loginTypeText",
            /** 一時トークン                                       */
            "onteimePasswordTokenText",
            /** 差出人アドレス                                      */
            "sendMailFrom",
            /** 送信先アドレス                                      */
            "sendMailTo",
            /** メールタイトル                                      */
            "sendMailSubject",
            /** 送信日時                                         */
            "sendMailDateTime"
    };
    /** シングルサインオン要求レスポンス 用パラメータ名配列*/
    private static final String[] PARAMNAMES_NEED_SSO
    = new String[] {
            /** メッセージ                                        */
            "messageText",
            /** 理由区分コード                                      */
            "reasonCodeText",
            /** ログイン方法                                      */
            "loginTypeText"
    };

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res,
            RestApiContext ctx, RestApiActionFilterChain chain) {

        //セッションを破棄しログアウトさせる
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        CmnAuthenticationsParamModel param =
                new ParamModelCreator<CmnAuthenticationsParamModel>(
                        CmnAuthenticationsParamModel.class,
                        ctx)
                .execute(req);

        LoginResultModel resultModel = BasicAuthBiz.builder()
                .setUserId(param.getUserId())
                .setPassword(param.getPassword())
                .build(ctx)
                .execute(req);

        //トークン認証使用チェック
        IApiConfBiz apiConf;
        try {
            apiConf  =
                    (IApiConfBiz) Class.forName("jp.groupsession.v2.api.biz.ApiConfBiz")
                    .getDeclaredConstructor().newInstance();

            if (!apiConf.isAbleTokenAuth(req, ctx.getCon())) {
                throw new RestApiPermissionException(
                        EnumResourceReasonCode.RESOURCE_TOKEN_NOTUSE,
                        "error.cant.use.token");
            }
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new RuntimeException("インスタンス生成例外", e);

        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }

        CommonBiz cmnBiz = new CommonBiz();
        //APIプラグイン使用不可
        try {
            if (cmnBiz.getCanUsePluginUser(
                    ctx.getCon(),
                    GSConst.PLUGINID_API,
                    List.of(resultModel.getLoginUser().getUsrsid())
                    ).size() == 0) {

                throw new RestApiPermissionException(
                        EnumError.RESOURCE_USER_CANTUSE_API,
                        "error.cant.use.plugin",
                        ctx.getPluginConfig().getPlugin("api").getName(ctx.getRequestModel())
                                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL処理例外", e);
        }



        //別システムによるSSO連携が求められた場合
        if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_SSO) {

            __writeNeedSSOElement(
                    resultModel, PARAMNAMES_NEED_SSO, res, ctx);
            return;
        }

        //ワンタイムパスワード入力が求められた場合
        if (resultModel.getErrCode() == LoginResultModel.ECODE_NEED_OTP) {

            __writeElement(
                    __createOtpToken(resultModel, req, ctx), PARAMNAMES_NEED_OTP, res, ctx);

            return;
        }
        //認証ユーザをアクセスユーザとして設定
        ctx.getRequestModel().setSmodel(resultModel.getLoginUser());



        chain.doFilter(req, res, ctx);

        RequestLocal.put(
                GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                new CmnAuthenticationsSessionWriter());


    }
    /**
     *
     * <br>[機  能] SSOが必要な場合のレスポンス生成
     * <br>[解  説]
     * <br>[備  考]
     * @param resultModel
     * @param paramnamesNeedSso
     * @param res
     * @param ctx
     */
    private void __writeNeedSSOElement(LoginResultModel resultModel,
            String[] paramnamesNeedSso, HttpServletResponse res,
            RestApiContext ctx) {
        switch (resultModel.getLoginType()) {
            case "shibboreth":
                throw new RestApiAuthException()
                .setReasonCode(EnumResourceReasonCode.RESOURCE_AUTH_NEED_SHIBBOLETH)
                .setMessageResource("need.auth.sso");
            case "ntlm":
                throw new RestApiAuthException()
                .setReasonCode(EnumResourceReasonCode.RESOURCE_AUTH_NEED_NTLM)
                .setMessageResource("need.auth.sso");
            case "saml":
            default:
                throw new RestApiAuthException()
                .setReasonCode(EnumResourceReasonCode.RESOURCE_AUTH_NEED_SAML)
                .setMessageResource("need.auth.sso");

        }

    }
    /**
    *
    * <br>[機  能] ワンタイムパスワードログインが必要な場合のレスポンスモデル生成
    * <br>[解  説]
    * <br>[備  考]
    * @param resultModel ログイン結果
    * @param req リクエスト
    * @param ctx
    * @return レスポンスモデル
    */
   private Object __createOtpToken(LoginResultModel resultModel,
           HttpServletRequest req, RestApiContext ctx) {
       OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
       OtpSendResult otpResult;
       try {
           otpResult = onetimePasswordBiz.sendOtp(resultModel.getLoginUser(),
                   ctx.getRequestModel(), ctx.getCon());
       } catch (SQLException e) {
           throw new RuntimeException("ワンタイムパスワード送信処理 SQL実行例外", e);
       }
       //エラー発生
       if (otpResult.getEcode() == OtpSendResult.ECODE_NOADDRES_ABLEEDIT
               || otpResult.getEcode() == OtpSendResult.ECODE_NOADDRES_CANTEDIT) {
           throw new RestApiPermissionException(
               EnumResourceReasonCode.RESOURCE_NOTHING_OTPSENDADDRESS,
               "error.need.address.otpass.send");
       }
       if (otpResult.getEcode() == OtpSendResult.ECODE_SENDERROR) {
           throw new RestApiPermissionException(
               EnumResourceReasonCode.RESOURCE_SENDERROR_OTP,
               "error.smtp.otppass.send");
       }

       OtpNeedResultModel mdl = new OtpNeedResultModel(otpResult);
       mdl.setReasonCodeText(EnumResourceReasonCode.RESOURCE_AUTH_NEED_OTP.reasonCodeText());
       mdl.setMessageText(
               ctx.getMessageResources().getMessage("need.auth.onetimepassword")
                   );
       return mdl;
   }
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


}
