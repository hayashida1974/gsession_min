package jp.groupsession.v2.wml.restapi.accounts.mails.move;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestDirectoryBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールを移動するAPI アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsMoveAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsMoveAction.class);

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
        WmlAccountsMailsMovePutParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メールを移動するRESTAPIの開始");
                
        WmlAccountsMailsMovePutBiz biz = new WmlAccountsMailsMovePutBiz(ctx);
        biz.execute(param);
        log__.info("WEBメール メールを移動する 完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
 
        //完了後、オペレーションログを出力
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        WmlRestDirectoryBiz wrdBiz = new WmlRestDirectoryBiz();
        int wacSid = wraBiz.getWmlAccountSid(ctx.getCon(), param.getAccountId());
        long moveDirSid = wrdBiz.getDirectorySid(ctx.getCon(), wacSid, param.getDirectoryType());
        WmlRestMailDataBiz wrmBiz = new WmlRestMailDataBiz();
        try {
            wrmBiz.outputOpLog(ctx.getCon(), ctx.getRequestModel(),
                            param.getAccountId(),
                            param.getSidArray(),
                            new String[] {String.valueOf(moveDirSid)},
                            WmlRestMailDataBiz.OPLOG_MOVEMAIL);
        } catch (GSException e) {
            log__.error("オペレーションログの出力に失敗", e);
        }
 
        log__.info("WEBメール メールを移動するRESTAPIのレスポンス完了");
    }
}
