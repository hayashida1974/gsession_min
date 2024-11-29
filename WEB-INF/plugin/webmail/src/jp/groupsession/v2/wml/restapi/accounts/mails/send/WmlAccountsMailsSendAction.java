package jp.groupsession.v2.wml.restapi.accounts.mails.send;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] WEBメール 新規メール送信, 新規草稿作成RESTAPI
 * <br>[解  説]
 * <br>[備  考]
 */
@Plugin(GSConst.PLUGINID_WML)
public class WmlAccountsMailsSendAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsMailsSendAction.class);

    /**
     * <br>[機  能] メールの新規送信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param res サーブレットリクエスト
     * @param param 入力情報
     * @param ctx APIコンテキスト
     * @throws Exception 
     */
    @Post
    public void doPost(
        HttpServletResponse res,
        WmlAccountsMailsSendPostParamModel param,
        RestApiContext ctx,
        GSTemporaryPathModel tempPathModel) throws Exception {


        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);
        GSContext context = getGsContext();

        //メール送信の実行
        String tempDir = tempPathModel.getTempPath();
        WmlAccountsMailsSendPostBiz biz =
            new WmlAccountsMailsSendPostBiz(param, ctx, context, tempDir);
        biz._execute();

        //メールの送信
        log__.info("RESTAPI メール新規送信処理開始");
        
        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        //結果の作成
        RestApiResponseWriter.builder(res, ctx)
            .addResult(biz.getResult())
            .build().execute();
    }

    /**
     * <br>[機  能] メールの新規送信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param res サーブレットリクエスト
     * @param param 入力情報
     * @param ctx APIコンテキスト
     * @throws Exception 
     */
    @Put
    public void doPut(
        HttpServletResponse res,
        WmlAccountsMailsSendPutParamModel param,
        RestApiContext ctx,
        GSTemporaryPathModel tempPathModel) throws Exception {

        //テンポラリディレクトリ作成
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPathModel);
        GSContext context = getGsContext();

        //メール送信の実行
        String tempDir = tempPathModel.getTempPath();        
        WmlAccountsMailsSendPutBiz biz =
            new WmlAccountsMailsSendPutBiz(param, ctx, context, tempDir);
        log__.info("RESTAPI 編集してメール送信処理開始");
        biz._execute();

        //メールの送信
        log__.info("RESTAPI 編集してメール送信処理終了");

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPathModel);

        //結果の作成
        RestApiResponseWriter.builder(res, ctx)
            .addResult(biz.getResult())
            .build().execute();
    }

}
