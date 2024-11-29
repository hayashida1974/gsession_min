package jp.groupsession.v2.cmn.restapi.users;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.AuthDao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.restapi.users.authentications.CmnUsersResultCreator;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnUsersAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] ユーザ情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param param
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(
            HttpServletResponse res,
            CmnUsersParamModel param,
            RestApiContext ctx) throws SQLException {

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
        CmnUsersResultCreator resultCreater =
                CmnUsersResultCreator
                .builder(ctx.getCon())
                .putUsrSid(Set.of(smodel.getUsrsid()))
                .setAccsessUsrSid(ctx.getRequestUserSid())
                .build();
            resultCreater.execute(ctx.getCon());

        CmnUsersResultModel resMdl = resultCreater
                .getMaskingResultList()
                .get(0);
        RestApiResponseWriter.builder(res, ctx)
        .addResult(
                resMdl
                    )
        .build().execute();

    }
    /**
    *
    * <br>[機  能] ユーザ情報の取得
    * <br>[解  説]
    * <br>[備  考]
    * @param res レスポンス
    * @param param
    * @param ctx
    * @throws SQLException
    */
    @Post
    @Parameter("query")
    public void doQuery(
            HttpServletResponse res,
            CmnUsersQueryParam param,
            RestApiContext ctx) throws SQLException {

        CmnUsersQueryBiz biz = new CmnUsersQueryBiz(param, ctx);
        biz.execute();

        RestApiResponseWriter.builder(res, ctx)
        .setMax(biz.getMaxCount())
        .addResultList(
            biz.resultUsr()
        )
        .build().execute();

    }
}
