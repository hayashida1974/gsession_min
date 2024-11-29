package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.sml.sml030.Sml030ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsMailboxesMailsAction extends AbstractRestApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountsMailboxesMailsAction.class);
    /**
    *
    * <br>[機  能] メール情報API
    * <br>[解  説] GET
    * <br>[備  考] メール情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Get
   public void doGet(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailboxesMailsGetParamModel param,
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

       SmlAccountsMailboxesMailsBiz biz = new SmlAccountsMailboxesMailsBiz();
       int maxCnt = biz.countMax(ctx, param);

       // 後の処理で-1される為、加算する。
       param.setOffset(param.getOffset() + 1);

       List<SmlRestapiMailModel> resList
                   = biz.getMailList(ctx, param);

       RestApiResponseWriter.builder(res, ctx)
       .setMax(maxCnt)
       .addResultList(resList)
       .build().execute();
   }
   /**
   *
   * <br>[機  能] メール情報API
   * <br>[解  説] DELETE
   * <br>[備  考] メール情報を送信先込みで削除する
    * @param req サーブレットリクエスト
   * @param res サーブレットレスポンス
   * @param param パラメータモデル
   * @param ctx APIコンテキスト
   * @throws SQLException
   */
   @Delete
   @Parameter(name = "boxName", value = "sent")
   @Parameter(name = "action", value = "with-destination")
   public void doDelete(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailboxesMailsDeleteParamModel param,
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
       RequestModel reqMdl = getRequestModel(req);
       Sml030Biz sml030biz = new Sml030Biz(reqMdl);

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メール使用可否チェック
       param.validateReadSmail(ctx.getCon(), gsMsg, param.getAccountSid(),
               param.getMailSid(), ctx.getRequestModel());

       // 指定されたメールSIDが送信メールかチェック
       boolean boxCheck = false;
       if (param.getMailSid() >= 0) {
           SmlSmeisModel mdl = new SmlSmeisModel();
           mdl.setSmsSid(param.getMailSid());
           SmlSmeisDao smsDao  = new SmlSmeisDao(ctx.getCon());
           mdl = smsDao.select(mdl);
           if (mdl != null && mdl.getSacSid() == param.getAccountSid()) {
               // 自身が送信したメールの場合のみＯＫ
               boxCheck = true;
           }
       }
       if (!boxCheck) {
           // メールSIDが不正
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "search.data.notfound",
                   new GsMessage(ctx.getRequestModel())
                       .getMessage("cmn.shortmail")
                   );
       }

       Sml030ParamModel paramMdl = new Sml030ParamModel();
       paramMdl.setSml010ProcMode(GSConstSmail.TAB_DSP_MODE_GOMIBAKO);
       paramMdl.setSmlViewAccount(param.getAccountSid());
       paramMdl.setSml010SelectedSid(param.getMailSid());
       paramMdl.setSml010SelectedMailKbn(GSConstSmail.TAB_DSP_MODE_SOSIN);
       //完全に削除可能かチェック
       if (sml030biz.isDeleteAll(paramMdl, ctx.getCon())) {
           // 完全削除処理実行
           sml030biz.allDeleteMessage(paramMdl, reqMdl, ctx.getCon());
       } else {
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "error.alldelete.mail.delete"
                   );
       }

       if (paramMdl.getErrorsList() != null
               && paramMdl.getErrorsList().size() > 0
               && paramMdl.getErrorsList().get(0).length() > 0) {
           // 実行処理によるエラーメッセージあり
           log__.info("LIST UPDATE ERROR END: " + paramMdl.getErrorsList().get(0));
           throw new RestApiValidateException(
                   EnumError.SYS_RUNTIME_ERROR,
                   "errors.free.msg",
                   paramMdl.getErrorsList().get(0)
                   );
       }
       log__.info("LIST UPDATE COMPLETE");

       //ログ出力処理
       SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), reqMdl);
       smlBiz.outPutApiLog(req, ctx.getCon(), ctx.getRequestUserModel().getUsrsid(),
               this.getClass().getCanonicalName(),
               getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE, "");

       //レスポンス
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
}
