package jp.groupsession.v2.wml.restapi.destination_lists;

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

@Plugin(GSConst.PLUGINID_WML)
public class WmlDestinationListsAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlDestinationListsAction.class);

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

        log__.info("WEBメール 送信先リスト一覧取得RESTAPIの開始");
                
        WmlDestinationListsGetBiz biz = new WmlDestinationListsGetBiz(ctx);
        biz.execute();
        log__.info("WEBメール 送信先リスト一覧取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
        log__.info("WEBメール 送信先リスト一覧取得RESTAPIのレスポンス完了");
    }
}