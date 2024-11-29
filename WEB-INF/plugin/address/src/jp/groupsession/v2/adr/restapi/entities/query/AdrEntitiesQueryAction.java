package jp.groupsession.v2.adr.restapi.entities.query;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] アドレス帳 アドレス情報一覧取得API アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConstAddress.PLUGIN_ID_ADDRESS)
public class AdrEntitiesQueryAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AdrEntitiesQueryAction.class);

    /**
     *
     * <br>[機  能] POSTメソッド
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Post
    public void doPost(
        HttpServletResponse res,
        AdrEntitiesQueryPostParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("アドレス帳 アドレス情報一覧取得RESTAPIの開始");

        AdrEntitiesQueryPostBiz biz = new AdrEntitiesQueryPostBiz(ctx);
        biz.execute(param);
        log__.info("アドレス帳 アドレス情報一覧取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .setMax(biz.getMaxCount())
        .build().execute();
        log__.info("アドレス帳 アドレス情報一覧取得RESTAPIのレスポンス完了");
    }
}
