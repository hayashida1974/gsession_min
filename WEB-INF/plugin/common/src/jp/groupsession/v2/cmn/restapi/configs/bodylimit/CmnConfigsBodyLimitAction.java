package jp.groupsession.v2.cmn.restapi.configs.bodylimit;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
/** 本文文字数制限設定情報 Action*/
public class CmnConfigsBodyLimitAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 本文文字数制限設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx) throws SQLException {
        RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);

        builder.addResult(
                new CmnConfigsBodyLimitResultModel(
                        ctx.getAppRootPath()
                        )
                    );

        builder.build().execute();

    }

}
