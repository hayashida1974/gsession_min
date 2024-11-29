package jp.groupsession.v2.sml.restapi.accounts.count;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.restapi.dao.SmlRestapiMailDao;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountLabelModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailCountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール件数情報API
 * <br>[解  説] 対象アカウントの全フォルダを対象
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsCountAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] メール件数情報API
    * <br>[解  説] GET
    * <br>[備  考] メール件数を取得する
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
           SmlAccountsCountGetParamModel param,
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

       // 選択されているアカウントが使用可能かを判定する
       SmailDao smlDao = new SmailDao(ctx.getCon());
       if (ctx.getRequestUserModel().getUsrsid() <= 0
               || param.getAccountSid() <= 0
               || !smlDao.canUseCheckAccount(
                       ctx.getRequestUserModel().getUsrsid(), param.getAccountSid())) {
           // アカウントがない場合
           throw new RestApiPermissionException(
                   ReasonCode.EnumError.IMPERMISSIBLE,
                   "search.data.notfound",
                   new GsMessage(ctx.getRequestModel()).getMessage("cmn.account")
                   );
       }

       // 未確認の件数を取得する。
       SmlRestapiMailDao dao = new SmlRestapiMailDao(ctx.getCon());
       List<SmlRestapiMailCountModel> countList = new ArrayList<SmlRestapiMailCountModel>();
       List<SmlRestapiMailCountLabelModel> labelCountList
                               = new ArrayList<SmlRestapiMailCountLabelModel>();
       int countNum = 0;
       int nonReadCountNum = 0;

       // 受信メール件数取得
       countNum = dao.getInboxCount(
               param.getAccountSid(),
               GSConst.JTKBN_TOROKU, false);
       nonReadCountNum = dao.getInboxCount(param.getAccountSid(),
               GSConst.JTKBN_TOROKU, true);
       SmlRestapiMailCountModel countMdl = new SmlRestapiMailCountModel();
       countMdl.setBoxName(GSConstSmail.RESTAPI_MAILBOX_INBOX);
       countMdl.setCountNum(countNum);
       countMdl.setNonReadCountNum(nonReadCountNum);
       countList.add(countMdl);

       // 送信メール件数取得
       countNum = dao.getSentCount(param.getAccountSid(), GSConst.JTKBN_TOROKU);
       countMdl = new SmlRestapiMailCountModel();
       countMdl.setBoxName(GSConstSmail.RESTAPI_MAILBOX_SENT);
       countMdl.setCountNum(countNum);
       countList.add(countMdl);

       // 草稿メール件数取得
       countNum = dao.getDraftCount(param.getAccountSid(), GSConst.JTKBN_TOROKU);
       countMdl = new SmlRestapiMailCountModel();
       countMdl.setBoxName(GSConstSmail.RESTAPI_MAILBOX_DRAFT);
       countMdl.setCountNum(countNum);
       countList.add(countMdl);

       // ゴミ箱メール件数取得
       countNum = dao.getInboxCount(
               param.getAccountSid(),
               GSConstSmail.SML_JTKBN_GOMIBAKO, false);
       countNum += dao.getSentCount(param.getAccountSid(), GSConstSmail.SML_JTKBN_GOMIBAKO);
       countNum += dao.getDraftCount(param.getAccountSid(), GSConstSmail.SML_JTKBN_GOMIBAKO);
       nonReadCountNum = dao.getInboxCount(param.getAccountSid(),
               GSConstSmail.SML_JTKBN_GOMIBAKO, true);
       countMdl = new SmlRestapiMailCountModel();
       countMdl.setBoxName(GSConstSmail.RESTAPI_MAILBOX_TRASH);
       countMdl.setCountNum(countNum);
       countMdl.setNonReadCountNum(nonReadCountNum);
       countList.add(countMdl);

       // 使用可能なラベル一覧取得
       SmlLabelDao labelDao = new SmlLabelDao(ctx.getCon());
       List<SmlLabelModel> labelList = labelDao.getLabelList(param.getAccountSid());

       // ラベルメール件数取得
       for (SmlLabelModel labelMdl : labelList) {
           // 受信 未読件数
           nonReadCountNum = dao.getLabelInboxCount(
                   param.getAccountSid(), labelMdl.getSlbSid(), true);

           // 受信 件数
           countNum = dao.getLabelInboxCount(
                   param.getAccountSid(), labelMdl.getSlbSid(), false);
           // 送信 件数
           countNum += dao.getLabelSentCount(
                   param.getAccountSid(), labelMdl.getSlbSid());
           // 草稿 件数
           countNum += dao.getLabelDraftCount(
                   param.getAccountSid(), labelMdl.getSlbSid());
           SmlRestapiMailCountLabelModel labelCountMdl = new SmlRestapiMailCountLabelModel();
           labelCountMdl.setBoxName(GSConstSmail.RESTAPI_MAILBOX_LABEL);
           labelCountMdl.setLabelName(labelMdl.getSlbName());
           labelCountMdl.setLabelSid(labelMdl.getSlbSid());
           labelCountMdl.setCountNum(countNum);
           labelCountMdl.setNonReadCountNum(nonReadCountNum);
           labelCountList.add(labelCountMdl);
       }

       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       builder.addResultList(countList);
       if (labelCountList.size() > 0) {
           builder.addResultList(labelCountList);
       }
       builder.build().execute();
   }
}