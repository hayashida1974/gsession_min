package jp.groupsession.v2.wml.restapi.accounts.configs;

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
 * <br>[機  能] WEBメール アカウント一覧取得RESTAPI
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsConfigsAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsConfigsAction.class);

    /**
     * <br>[機  能] アカウント一覧取得API
     * <br>[解  説]
     * <br>[備  考]
     * @param res サーブレットリクエスト
     * @param param 入力情報
     * @param ctx APIコンテキスト
     * @throws SQLException
     */
    @Get
    public void doGet(
        HttpServletResponse res,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール アカウント一覧取得RESTAPIの開始");
        
        //ラベル一覧の取得
        WmlAccountsConfigsBiz biz = new WmlAccountsConfigsBiz(ctx);
        biz._execute();
        log__.info("WEBメール ラベル一覧の取得完了");

        //レスポンスの作成
        RestApiResponseWriter.builder(res, ctx)
            .addResultList(biz.getResult())
            .build().execute();
        log__.info("WEBメール アカウント一覧取得RESTAPIのレスポンス完了");
    }
}
