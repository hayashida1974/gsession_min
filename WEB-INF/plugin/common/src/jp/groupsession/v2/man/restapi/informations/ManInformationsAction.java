package jp.groupsession.v2.man.restapi.informations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
@Plugin("main")
public class ManInformationsAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] GET インフォメーション取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req サーブレットリクエスト
     * @param res サーブレットレスポンス
     * @param ctx RestApiコンテキスト
     */
    @Get
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx
            ) {
        ManInformationGetBiz biz = new ManInformationGetBiz();

        RestApiResponseWriter.builder(res, ctx)
            .addResultList(biz.execute(ctx, getServlet().getServletContext()))
            .build()
            .execute();

    }

}
