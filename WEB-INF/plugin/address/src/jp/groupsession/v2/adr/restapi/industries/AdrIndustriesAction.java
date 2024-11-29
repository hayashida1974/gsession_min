package jp.groupsession.v2.adr.restapi.industries;

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
 * <br>[機  能] アドレス帳 業種一覧取得API アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin(GSConst.PLUGINID_ADDRESS)
public class AdrIndustriesAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AdrIndustriesAction.class);

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
        RestApiContext ctx) throws SQLException {

        log__.info("アドレス帳 業種一覧取得RESTAPIの開始");
                
        AdrIndustriesGetBiz biz = new AdrIndustriesGetBiz(ctx);
        biz.execute();
        log__.info("アドレス帳 業種一覧取得取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
        log__.info("アドレス帳 業種一覧取得RESTAPIのレスポンス完了");
    }
}
