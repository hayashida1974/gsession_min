package jp.groupsession.v2.wml.restapi.accounts.mails.open_condition;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] WEBメール メールを既読または未読にするAPI アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsOpenConditionAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsOpenConditionAction.class);

    /**
     *
     * <br>[機  能] PUTメソッド
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Put
    public void doPut(
        HttpServletResponse res,
        WmlAccountsMailsOpenConditionPutParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メールを既読または未読にするRESTAPIの開始");
                
        WmlAccountsMailsOpenConditionPutBiz biz = new WmlAccountsMailsOpenConditionPutBiz(ctx);
        biz.execute(param);
        log__.info("WEBメール メールを既読または未読にする 完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
        log__.info("WEBメール メールを既読または未読にするRESTAPIのレスポンス完了");
    }
}
