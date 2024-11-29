package jp.groupsession.v2.cmn.restapi.users.authentication_type;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.restapi.users.CmnUsersParamModel;
import jp.groupsession.v2.rap.mbh.IMbhUUIDBiz;
import jp.groupsession.v2.rap.mbh.MbhUUIDBiz;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.NoAuthrization;
import jp.groupsession.v2.restapi.controller.annotation.NoLoginSession;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
*
* <br>[機  能] ワンタイムパスワード認証の使用有無を取得
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class AuthenticationTypeAction extends AbstractRestApiAction {
    /** ワンタイムパスワード使用しない*/
    private static final int OTPCHECK_NOUSE__ = 0;
    /** ワンタイムパスワード使用する*/
    private static final int OTPCHECK_USE__ = 1;
    /**
     *
     * <br>[機  能] GET Action
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException SQL実行時例外
     * @throws IOException
     */
    @Get
    @NoLoginSession
    @NoAuthrization
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse res,
            CmnUsersParamModel param,
            RestApiContext ctx) throws SQLException, IOException {
        AuthDao adao = new AuthDao(ctx.getCon());
        BaseUserModel smodel = null;
        try {
            smodel = adao.selectLoginNoPwd(param.getUserId(), null);
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行エラー", e);
        }
        if (smodel == null) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(ctx.getRequestModel())
                        .getMessage("cmn.user"));
        }
        //認証後 個体識別番号制御によるファイアウォールチェック
        FirewallBiz firewall = FirewallBiz.getInstance();
        if (!firewall.additionalCheckForMbl(smodel, ctx.getCon(), true)) {
            firewall.handlingFirewallError(
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
            return;
        }

        //ワンタイムパスワード必要判定
        int result;
        OnetimePasswordBiz onetimePasswordBiz = new OnetimePasswordBiz();
        try {
            if (onetimePasswordBiz.isNeedOtpAuth(req, smodel, ctx.getCon())) {
                result = OTPCHECK_USE__;
            } else {
                result = OTPCHECK_NOUSE__;
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行エラー", e);
        }
        ILogin loginBiz = _getLoginInstance();

        RestApiResponseWriter.builder(res, ctx)
        .addResult(new ResultElement("result")
                    .addContent(
                           new ResultElement("authenticationType")
                               .addContent(loginBiz.getLoginType(smodel, ctx.getCon()))
                            )
                    .addContent(
                            new ResultElement("onetimePasswordUseFlg")
                                .addContent(Integer.toString(result))
                             )
                    )
        .build().execute();


    }

}
