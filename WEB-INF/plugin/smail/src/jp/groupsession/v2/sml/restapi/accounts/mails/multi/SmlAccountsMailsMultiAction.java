package jp.groupsession.v2.sml.restapi.accounts.mails.multi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlJmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisLabelDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール状態変更API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsMailsMultiAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] ラベルを追加する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "add-label")
   public void doPutAddLabel(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // ラベルチェック
       param.validateCheckLabel(ctx.getCon(), gsMsg, param.getLabelSidArray(),
               ctx.getRequestModel(), param.getAccountSid());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       // ラベル追加
       Sml010Biz sml010Biz = new Sml010Biz();
       for (int labelSid : param.getLabelSidArray()) {
           // 受信
           for (int mailSid : param.getInboxMailSidArray()) {
               sml010Biz.setLabelJushin(String.valueOf(mailSid), labelSid,
                       param.getAccountSid(), ctx.getCon());
           }
           // 送信
           for (int mailSid : param.getSentMailSidArray()) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               String sid = "0" + String.valueOf(mailSid);
               sml010Biz.setLabelSoshin(sid, labelSid,
                       param.getAccountSid(), ctx.getCon());
           }
           // 草稿
           for (int mailSid : param.getDraftMailSidArray()) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               String sid = "0" + String.valueOf(mailSid);
               sml010Biz.setLabelSoko(sid, labelSid,
                       param.getAccountSid(), ctx.getCon());
           }
       }
       ctx.getCon().commit();
       outputLog(req, param, ctx,
               gsMsg.getMessage("cmn.entry"),
               gsMsg.getMessage("cmn.label.settings"));

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
       /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] ラベルを削除する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "remove-label")
   public void doPutRemoveLabel(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // ラベルチェック
       param.validateCheckLabel(ctx.getCon(), gsMsg, param.getLabelSidArray(),
               ctx.getRequestModel(), param.getAccountSid());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       // ラベル削除
       for (int labelSid : param.getLabelSidArray()) {
           // 受信
           for (int mailSid : param.getInboxMailSidArray()) {
               SmlJmeisLabelDao dao = new SmlJmeisLabelDao(ctx.getCon());
               dao.delete(mailSid, labelSid, param.getAccountSid());
           }
           // 送信
           for (int mailSid : param.getSentMailSidArray()) {
               SmlSmeisLabelDao dao = new SmlSmeisLabelDao(ctx.getCon());
               dao.delete(mailSid, labelSid, param.getAccountSid());
           }
           // 草稿
           for (int mailSid : param.getDraftMailSidArray()) {
               SmlWmeisLabelDao dao = new SmlWmeisLabelDao(ctx.getCon());
               dao.delete(mailSid, labelSid, param.getAccountSid());
           }
       }
       ctx.getCon().commit();
       // ログ出力
       outputLog(req, param, ctx,
               gsMsg.getMessage("cmn.delete"),
               gsMsg.getMessage("cmn.label.settings"));

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
       /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] メールを既読にする
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "change-readed")
   public void doPutChangeReaded(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       // 受信メッセージの開封区分を既読に変更
       for (int mailSid : param.getInboxMailSidArray()) {
           UDate sysUd = new UDate();
           SmlJmeisModel mdl = new SmlJmeisModel();
           mdl.setSacSid(param.getAccountSid());
           mdl.setSmjSid(mailSid);
           mdl.setSmjOpdate(sysUd);
           mdl.setSmjEuid(ctx.getRequestUserModel().getUsrsid());
           mdl.setSmjEdate(sysUd);
           SmlJmeisDao jdao = new SmlJmeisDao(ctx.getCon());
           //すでに開封していた場合は開封日時は更新しない
           if (!jdao.selOpKbnDate(mdl)) {
               jdao.updateOpKbn(ctx.getRequestUserModel().getUsrsid(), mdl);
           } else {
               mdl.setSmjOpkbn(GSConstSmail.OPKBN_OPENED);
               jdao.updateOpKbnOnly(ctx.getRequestUserModel().getUsrsid(), mdl);
           }
       }
       ctx.getCon().commit();

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
   /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] メールを未読にする
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "change-noread")
   public void doPutChangeNoread(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       // 受信メッセージの開封区分を未読に変更
       SmlJmeisDao jdao = new SmlJmeisDao(ctx.getCon());
       String[] sidArray = new String[param.getInboxMailSidArray().length];
       for (int i = 0; i < param.getInboxMailSidArray().length; i++) {
           sidArray[i] = String.valueOf(param.getInboxMailSidArray()[i]);
       }
       jdao.updateOpkbn(sidArray, ctx.getRequestUserModel().getUsrsid(),
               param.getAccountSid(), GSConstSmail.OPKBN_UNOPENED, new UDate());
       ctx.getCon().commit();

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
   /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] メールをゴミ箱へ移動
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "move-trash")
   public void doPutMoveTrash(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       //受信メッセージ
       if (param.getInboxMailSidArray() != null && param.getInboxMailSidArray().length > 0) {
           SmlJmeisDao jdao = new SmlJmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getInboxMailSidArray().length];
           for (int i = 0; i < param.getInboxMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getInboxMailSidArray()[i]);
           }
           jdao.moveJmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_GOMIBAKO,
                   new UDate(),
                   sidArray);
       }
       //送信メッセージ
       if (param.getSentMailSidArray() != null && param.getSentMailSidArray().length > 0) {
           SmlSmeisDao sdao = new SmlSmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getSentMailSidArray().length];
           for (int i = 0; i < param.getSentMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getSentMailSidArray()[i]);
           }
           sdao.moveSmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_GOMIBAKO,
                   new UDate(),
                   sidArray);
       }
       //草稿メッセージ
       if (param.getDraftMailSidArray() != null && param.getDraftMailSidArray().length > 0) {
           SmlWmeisDao wdao = new SmlWmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getDraftMailSidArray().length];
           for (int i = 0; i < param.getDraftMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getDraftMailSidArray()[i]);
           }
           wdao.moveWmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_GOMIBAKO,
                   new UDate(),
                   sidArray);
       }
       ctx.getCon().commit();
       // ログ出力
       outputLog(req, param, ctx,
               gsMsg.getMessage("cmn.edit"),
               gsMsg.getMessage("cmn.delete"));

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
   /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] メールをゴミ箱から戻す
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "alive-trash")
   public void doPutAliveTrash(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       //受信メッセージ
       if (param.getInboxMailSidArray() != null && param.getInboxMailSidArray().length > 0) {
           SmlJmeisDao jdao = new SmlJmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getInboxMailSidArray().length];
           for (int i = 0; i < param.getInboxMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getInboxMailSidArray()[i]);
           }
           jdao.moveJmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_TOROKU,
                   new UDate(),
                   sidArray);
       }
       //送信メッセージ
       if (param.getSentMailSidArray() != null && param.getSentMailSidArray().length > 0) {
           SmlSmeisDao sdao = new SmlSmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getSentMailSidArray().length];
           for (int i = 0; i < param.getSentMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getSentMailSidArray()[i]);
           }
           sdao.moveSmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_TOROKU,
                   new UDate(),
                   sidArray);
       }
       //草稿メッセージ
       if (param.getDraftMailSidArray() != null && param.getDraftMailSidArray().length > 0) {
           SmlWmeisDao wdao = new SmlWmeisDao(ctx.getCon());
           String[] sidArray = new String[param.getDraftMailSidArray().length];
           for (int i = 0; i < param.getDraftMailSidArray().length; i++) {
               // 後のロジックで1文字目を除外する為、回避用に1文字追加
               sidArray[i] = "0" + String.valueOf(param.getDraftMailSidArray()[i]);
           }
           wdao.moveWmeis(
                   ctx.getRequestUserModel().getUsrsid(),
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_TOROKU,
                   new UDate(),
                   sidArray);
       }
       ctx.getCon().commit();
       // ログ出力
       outputLog(req, param, ctx,
               gsMsg.getMessage("cmn.edit"),
               gsMsg.getMessage("cmn.undo"));

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
   /**
    *
    * <br>[機  能] メール状態変更API
    * <br>[解  説] PUT
    * <br>[備  考] メールをゴミ箱から削除する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param param パラメータモデル
    * @param ctx APIコンテキスト
    * @throws SQLException
    */
   @Put
   @Parameter(name = "action", value = "delete-trash")
   public void doPutDeleteTrash(
           HttpServletRequest req,
           HttpServletResponse res,
           SmlAccountsMailsMultiPutParamModel param,
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
       SmlAccountsMailsMultiBiz biz = new SmlAccountsMailsMultiBiz();

       // アカウントチェック
       param.validateCheckSmlAccount(ctx.getCon(), gsMsg,
               ctx.getRequestUserModel().getUsrsid(), param.getAccountSid(),
               ctx.getRequestModel());

       // メールチェック
       param.validateCheckMail(ctx.getCon(), gsMsg, param.getInboxMailSidArray(),
               param.getSentMailSidArray(), param.getDraftMailSidArray(),
               ctx.getRequestModel());

       // メール削除
       biz.deleteTrashMail(param.getInboxMailSidArray(),
               param.getSentMailSidArray(),
               param.getDraftMailSidArray(),
               ctx.getCon(),
               param.getAccountSid(),
               ctx.getRequestUserModel().getUsrsid());

       ctx.getCon().commit();
       // ログ出力
       outputLog(req, param, ctx,
               gsMsg.getMessage("cmn.delete"),
               gsMsg.getMessage("restapi.smail.delete.trash.1"));

       // リザルト
       ResultElement result = new ResultElement("result");
       result.addContent("OK");
       RestApiResponseWriter.builder(res, ctx)
       .addResult(result)
       .build().execute();
   }
  /**
   *
   * <br>[機  能] メール状態変更API
   * <br>[解  説] ログ出力
   * <br>[備  考]
   * @param req サーブレットリクエスト
   * @param param パラメータモデル
   * @param ctx APIコンテキスト
   * @param opCode コード
   * @param logVal ログ内容
   * @throws SQLException
   */
   public void outputLog(
           HttpServletRequest req,
           SmlAccountsMailsMultiPutParamModel param,
           RestApiContext ctx,
           String opCode,
           String logVal)
                  throws SQLException {

       SmlAccountDao sacDao = new SmlAccountDao(ctx.getCon());
       SmlAccountModel sacMdl = sacDao.select(param.getAccountSid());
       String sacName = "";
       if (sacMdl != null) {
           sacName = sacMdl.getSacName();
       }

       SmlCommonBiz smlBiz = new SmlCommonBiz(ctx.getCon(), ctx.getRequestModel());
       smlBiz.outPutApiLog(req, ctx.getCon(), ctx.getRequestUserModel().getUsrsid(),
               this.getClass().getCanonicalName(),
               opCode, GSConstLog.LEVEL_TRACE, "アカウント:" + sacName + "\n" + logVal);
   }
}
