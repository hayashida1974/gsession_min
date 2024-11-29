package jp.groupsession.v2.adr.restapi.entities;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] アドレス帳 アドレス情報取得API アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin(GSConst.PLUGINID_ADDRESS)
public class AdrEntitiesAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AbstractRestApiAction.class);

    /**
     *
     * <br>[機  能] GETメソッド
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Get
    public void doGet(
        HttpServletResponse res,
        AdrEntitiesGetParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("アドレス帳 アドレス情報取得RESTAPIの開始");
                
        AdrEntitiesGetBiz biz = new AdrEntitiesGetBiz(ctx);
        biz.execute(param);
        log__.info("アドレス帳 アドレス情報取得取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResult(biz.getResult())
        .build().execute();
        log__.info("アドレス帳 アドレス情報取得RESTAPIのレスポンス完了");
    }
}
