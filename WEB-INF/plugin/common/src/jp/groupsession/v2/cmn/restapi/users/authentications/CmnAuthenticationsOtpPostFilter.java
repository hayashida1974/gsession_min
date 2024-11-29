package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.login.LoginResultModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.filter.IActionFilter;
import jp.groupsession.v2.restapi.filter.RestApiActionFilterChain;
import jp.groupsession.v2.restapi.parameter.ParamModelCreator;

public class CmnAuthenticationsOtpPostFilter implements IActionFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res,
            RestApiContext ctx, RestApiActionFilterChain chain) {
        //セッションを破棄しログアウトさせる
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        CmnAuthenticationsOtpParamModel param =
                new ParamModelCreator<CmnAuthenticationsOtpParamModel>(
                        CmnAuthenticationsOtpParamModel.class,
                        ctx)
                .execute(req);

        LoginResultModel resultModel = new OnetimePasswordAuthBiz(param, ctx)
                .execute(req);
        //認証ユーザをアクセスユーザとして設定
        ctx.getRequestModel().setSmodel(resultModel.getLoginUser());

        CommonBiz cmnBiz = new CommonBiz();
        //APIプラグイン使用不可
        try {
            if (cmnBiz.getCanUsePluginUser(
                    ctx.getCon(),
                    GSConst.PLUGINID_API,
                    List.of(ctx.getRequestUserSid())
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


        chain.doFilter(req, res, ctx);

        RequestLocal.put(
                GSConstApi.LOCALKEY_LOGINSESSION_WRITER,
                new CmnAuthenticationsSessionWriter());
    }

}
