package jp.groupsession.v2.cmn.restapi.plugins;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 *
 * <br>[機  能] プラグイン一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnPluginsAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] プラグイン一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param paramMdl パラメータモデル
     * @param ctx
     * @throws SQLException
     */
    @Get
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            CmnPluginsParamModel paramMdl,
            RestApiContext ctx) throws SQLException {

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(
                new CmnPluginsGetBiz(ctx)
                    .get(paramMdl)
                    )
        .build().execute();


    }

}
