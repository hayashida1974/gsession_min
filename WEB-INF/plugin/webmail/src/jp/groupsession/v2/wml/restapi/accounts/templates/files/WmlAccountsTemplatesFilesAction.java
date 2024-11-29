package jp.groupsession.v2.wml.restapi.accounts.templates.files;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiAttachementResponseWriter;

/**
 * <br>[機  能] WEBメール テンプレート添付ファイルダウンロードAPI アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsTemplatesFilesAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsTemplatesFilesAction.class);

    /**
     *
     * <br>[機  能] GETメソッド
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param req リクエスト
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Get
    public void doGet(
        HttpServletRequest req,
        HttpServletResponse res,
        WmlAccountsTemplatesFilesGetParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("WEBメール テンプレート添付ファイルダウンロードRESTAPIの開始");

        RestApiAttachementResponseWriter.execute(res, req, ctx, param.getBinSid());
        log__.info("WEBメール テンプレート添付ファイルダウンロードRESTAPIのレスポンス完了");
    }
}
