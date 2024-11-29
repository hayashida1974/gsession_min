package jp.groupsession.v2.wml.restapi.accounts.mails.query;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] WEBメール メール一覧取得API アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsQueryAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsQueryAction.class);

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
        WmlAccountsMailsQueryPostParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メール一覧取得RESTAPIの開始");
                
        WmlAccountsMailsQueryPostBiz biz = new WmlAccountsMailsQueryPostBiz(ctx);
        biz.execute(param);
        log__.info("WEBメール メール一覧取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
        log__.info("WEBメール メール一覧取得RESTAPIのレスポンス完了");
    }
}
