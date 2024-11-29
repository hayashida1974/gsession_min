package jp.groupsession.v2.sml.restapi.accounts.labels;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.restapi.accounts.templates.SmlAccountsTemplatesGetParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsLabelsAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] ラベル情報API
    * <br>[解  説] GET
    * <br>[備  考] ラベル情報を取得する
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Get
   public void doGet(
           HttpServletResponse res,
           SmlAccountsTemplatesGetParamModel param,
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
       //アカウント 使用可能判定
       SmlAccountsLabelsBiz biz = new SmlAccountsLabelsBiz();
       biz.validateAccount(ctx.getCon(), ctx.getRequestModel(),
               param.getAccountSid(), ctx.getRequestUserSid());

       // ラベル情報取得
       SmlLabelDao  smllblDao = new SmlLabelDao(ctx.getCon());
       List<SmlLabelModel> labelLlist = smllblDao.getLabelList(param.getAccountSid());

       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       for (SmlLabelModel labelModel : labelLlist) {
           ResultElement result = new ResultElement("result");
           result.addContent(new ResultElement("sid").addContent(
                   String.valueOf(labelModel.getSlbSid())));
           result.addContent(new ResultElement("name").addContent(labelModel.getSlbName()));
           builder.addResult(result);
       }
       builder.build().execute();
   }
   /**
   *
   * <br>[機  能] ラベル情報API
   * <br>[解  説] POST
   * <br>[備  考] ラベル情報を登録する
   * @param req サーブレットリクエスト
   * @param res サーブレットレスポンス
   * @param param パラメータモデル
   * @param ctx APIコンテキスト
   * @throws Exception
   * @throws SQLException
   * @throws IOToolsException
   */
   @Post
   public void doPost(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsLabelsPostParamModel param,
           RestApiContext ctx) throws Exception, SQLException, IOToolsException {

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
       //アカウント 使用可能判定
       SmlAccountsLabelsBiz biz = new SmlAccountsLabelsBiz();
       biz.validateAccount(ctx.getCon(), ctx.getRequestModel(),
               param.getAccountSid(), ctx.getRequestUserSid());

       ResultElement result = new ResultElement("result");

       //登録処理
       int labelSid = biz.addLabel(ctx.getCon(), ctx.getRequestModel(),
               param.getAccountSid(), ctx.getRequestUserSid(),
               param.getName());
       result.addContent(String.valueOf(labelSid));

       if (labelSid != -1) {
           // ログ出力
           SmlAccountDao sacDao = new SmlAccountDao(ctx.getCon());
           SmlAccountModel sacMdl = sacDao.select(param.getAccountSid());
           String sacName   = "";
           if (sacMdl != null) {
               sacName = sacMdl.getSacName();
           }
           SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), ctx.getRequestModel());
           smlBiz.outPutApiLog(req, ctx.getCon(), ctx.getRequestUserSid(),
                   this.getClass().getCanonicalName(),
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.entry"),
                   GSConstLog.LEVEL_INFO, "アカウント:"
                           + sacName + "\n[name]" + String.valueOf(param.getAccountSid()));
       }

       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
   /**
   *
   * <br>[機  能] ラベル情報API
   * <br>[解  説] DELETE
   * <br>[備  考] ラベル情報を削除する
   * @param req サーブレットリクエスト
   * @param res サーブレットレスポンス
   * @param param パラメータモデル
   * @param ctx APIコンテキスト
   * @throws SQLException
   */
   @Delete
   public void doDelete(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsLabelsDelParamModel param,
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
       //アカウント 使用可能判定
       SmlAccountsLabelsBiz biz = new SmlAccountsLabelsBiz();
       biz.validateAccount(ctx.getCon(), ctx.getRequestModel(),
               param.getAccountSid(), ctx.getRequestUserSid());

       ResultElement result = new ResultElement("result");

       //削除処理
       biz.delLabel(ctx.getCon(), ctx.getRequestModel(),
               param.getAccountSid(), ctx.getRequestUserSid(),
               param.getLabelSid());

       //ログ出力処理
       SmlAccountDao sacDao = new SmlAccountDao(ctx.getCon());
       SmlAccountModel sacMdl = sacDao.select(param.getAccountSid());
       String sacName   = "";
       if (sacMdl != null) {
           sacName = sacMdl.getSacName();
       }
       SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), getRequestModel(req));
       smlBiz.outPutApiLog(req, ctx.getCon(),
               ctx.getRequestUserSid(), this.getClass().getCanonicalName(),
               getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO, "アカウント:"
                                        + sacName + "\n");

       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
}
