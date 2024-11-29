package jp.groupsession.v2.wml.restapi.accounts.mails.label;

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
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メールにラベルを設定または解除するAPI アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsLabelAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsLabelAction.class);

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
        WmlAccountsMailsLabelPutParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール メールにラベルを設定または解除するRESTAPIの開始");
                
        WmlAccountsMailsLabelPutBiz biz = new WmlAccountsMailsLabelPutBiz(ctx);
        biz.execute(param);
        log__.info("WEBメール ラベルを設定または解除する 完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .build().execute();
        log__.info("WEBメール メールにラベルを設定または解除するRESTAPIのレスポンス完了");

        //完了後、オペレーションログを出力
        WmlRestMailDataBiz wrmBiz = new WmlRestMailDataBiz();
        try {
            int opLogType = WmlRestMailDataBiz.OPLOG_SETLABEL;
            if (param.getProcType().equals(WmlAccountsMailsLabelPutParamModel.PROCTYPE_REMOVE)) {
                opLogType = WmlRestMailDataBiz.OPLOG_REMOVELABEL;
            }
            wrmBiz.outputOpLog(ctx.getCon(), ctx.getRequestModel(),
                            param.getAccountId(),
                            param.getSidArray(),
                            new String[] {String.valueOf(opLogType)},
                            WmlRestMailDataBiz.OPLOG_MOVEMAIL);
        } catch (GSException e) {
            log__.error("オペレーションログの出力に失敗", e);
        }
    }
}
