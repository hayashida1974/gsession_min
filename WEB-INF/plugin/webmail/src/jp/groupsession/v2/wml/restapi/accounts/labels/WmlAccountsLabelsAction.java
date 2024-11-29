package jp.groupsession.v2.wml.restapi.accounts.labels;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] WEBメール ラベル一覧, ラベル登録RESTAPI
 * <br>[解  説]
 * <br>[備  考]
 * @param res
 * @param param
 * @param ctx
 * @throws SQLException
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsLabelsAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsLabelsAction.class);

    /**
     * <br>[機  能] ラベル一覧取得API
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
        WmlAccoutsLabelsGetParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール ラベル一覧取得RESTAPIの開始");
        
        //ラベル一覧の取得
        WmlAccoutsLabelsGetBiz biz = new WmlAccoutsLabelsGetBiz(param, ctx);
        biz._execute();
        log__.info("WEBメール ラベル一覧の取得完了");

        //レスポンスの作成
        RestApiResponseWriter.builder(res, ctx)
            .addResultList(biz.getResult())
            .build().execute();
        log__.info("WEBメール ラベル一覧取得RESTAPIのレスポンス完了");
    }

    /**
     * <br>[機  能] ラベル登録API
     * <br>[解  説]
     * <br>[備  考]
     * @param res サーブレットリクエスト
     * @param param 入力情報
     * @param ctx APIコンテキスト
     * @throws SQLException
     */
    @Post
    public void doPost(
        HttpServletResponse res,
        WmlAccoutsLabelsPostParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール ラベル登録RESTAPIの開始");
        
        //ラベル一覧の取得
        WmlAccoutsLabelsPostBiz biz = new WmlAccoutsLabelsPostBiz(param, ctx);
        biz._execute();
        log__.info("WEBメール ラベルの登録完了");

        //レスポンスの作成
        RestApiResponseWriter.builder(res, ctx)
            .addResult(biz.getResult())
            .build().execute();
        log__.info("WEBメール ラベル登録RESTAPIのレスポンス完了");
    }
}
