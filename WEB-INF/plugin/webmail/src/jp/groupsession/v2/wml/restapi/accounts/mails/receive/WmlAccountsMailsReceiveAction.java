package jp.groupsession.v2.wml.restapi.accounts.mails.receive;

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
public class WmlAccountsMailsReceiveAction extends AbstractRestApiAction {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsReceiveAction.class);

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
        WmlAccountsMailsReceivePostParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メール受信RESTAPIの開始");
                
        WmlAccountsMailsReceivePostBiz biz = new WmlAccountsMailsReceivePostBiz(ctx);
        biz.execute(param, getGsContext());
        log__.info("WEBメール メール受信完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResult("OK")
        .build().execute();
        log__.info("WEBメール メール受信RESTAPIのレスポンス完了");
    }
}
