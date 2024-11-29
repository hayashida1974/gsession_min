package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール情報検索API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsMailboxesMailsQueryAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] メール情報検索API
    * <br>[解  説] POST
    * <br>[備  考] メール情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Post
   public void doPost(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailboxesMailsQueryPostParamModel param,
           RestApiContext ctx) throws SQLException {

       //ショートメールプラグインアクセス権限確認
       List<String> pluginList = new ArrayList<String>();
       pluginList.add(GSConst.PLUGINID_SML);
       if (!canAccessPlugin(pluginList)) {
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "error.cant.use.plugin",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                   );
       }

       GsMessage gsMsg = new GsMessage(req);
       SmlAccountsMailboxesMailsQueryBiz biz = new SmlAccountsMailboxesMailsQueryBiz();

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // 後の処理で-1される為、加算する。
       param.setOffset(param.getOffset() + 1);

       List<SmlRestapiMailModel> resList
                   = biz.getMailList(ctx.getCon(), ctx.getRequestUserModel().getUsrsid(), param);

       RestApiResponseWriter.builder(res, ctx)
       .setMax(biz.getMaxCount())
       .addResultList(resList)
       .build().execute();
   }
}
