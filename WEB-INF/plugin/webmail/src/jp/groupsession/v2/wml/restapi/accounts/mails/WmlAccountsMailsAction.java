package jp.groupsession.v2.wml.restapi.accounts.mails;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールを削除するAPI アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsAction.class);

    /**
     *
     * <br>[機  能] DELETEメソッド
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Delete
    public void doDelete(
        HttpServletResponse res,
        WmlAccountsMailsDeleteParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メールを削除するRESTAPIの開始");
                
        WmlAccountsMailsDeleteBiz biz = new WmlAccountsMailsDeleteBiz(ctx);

        //削除実行前に、削除対象メールの件名を取得する(オペレーションログ出力用)
        String[] delMailTitleArray = biz.getDeleteMailTitle(param);

        //削除実行
        biz.execute(param);
        log__.info("WEBメール メールを削除する 完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResult("OK")
        .build().execute();

        //完了後、オペレーションログを出力
        WmlRestMailDataBiz wrmBiz = new WmlRestMailDataBiz();
        try {
            wrmBiz.outputOpLog(ctx.getCon(), ctx.getRequestModel(),
                            param.getAccountId(),
                            param.getSidArray(),
                            delMailTitleArray,
                            WmlRestMailDataBiz.OPLOG_MAILDELETE);
        } catch (GSException e) {
            log__.error("オペレーションログの出力に失敗", e);
        }

        log__.info("WEBメール メールを削除するRESTAPIのレスポンス完了");
    }

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
        WmlAccountsMailsGetParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メールを単体で取得するRESTAPIの開始");
                
        WmlAccountsMailsGetBiz biz = new WmlAccountsMailsGetBiz(ctx);

        //メールの取得
        biz._execute(param);
        log__.info("WEBメール メールを単体で取得する 完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResult(biz.getResult())
        .build().execute();

        log__.info("WEBメール メールを単体で取得するRESTAPIのレスポンス完了");
    }
}
