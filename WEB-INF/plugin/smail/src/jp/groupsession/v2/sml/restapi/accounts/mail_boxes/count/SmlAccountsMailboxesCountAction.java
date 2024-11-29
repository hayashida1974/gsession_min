package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.count;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.restapi.dao.SmlRestapiMailDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール件数情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsMailboxesCountAction extends AbstractRestApiAction {
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
           SmlAccountsMailboxesCountGetParamModel param,
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


       //未確認の件数を取得する。
       SmlRestapiMailDao dao = new SmlRestapiMailDao(ctx.getCon());
       int countNum = 0;
       int nonReadCountNum = 0;
       String labelName = "";
       Integer labelSid = null;
       String boxName = param.getBoxName();
       if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_INBOX)) {
           //受信メール件数取得
           countNum = dao.getInboxCount(
                   param.getAccountSid(),
                   GSConst.JTKBN_TOROKU, false);
           nonReadCountNum = dao.getInboxCount(param.getAccountSid(),
                   GSConst.JTKBN_TOROKU, true);
       } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_SENT)) {
           //送信メール件数取得
           countNum = dao.getSentCount(param.getAccountSid(), GSConst.JTKBN_TOROKU);
       } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_DRAFT)) {
           //草稿メール件数取得
           countNum = dao.getDraftCount(param.getAccountSid(), GSConst.JTKBN_TOROKU);
       } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_TRASH)) {
           //ゴミ箱メール件数取得
           countNum = dao.getInboxCount(
                   param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_GOMIBAKO, false);
           countNum += dao.getSentCount(param.getAccountSid(), GSConstSmail.SML_JTKBN_GOMIBAKO);
           countNum += dao.getDraftCount(param.getAccountSid(), GSConstSmail.SML_JTKBN_GOMIBAKO);
           nonReadCountNum = dao.getInboxCount(param.getAccountSid(),
                   GSConstSmail.SML_JTKBN_GOMIBAKO, true);
       } else {
           // ラベル情報
           boxName = GSConstSmail.RESTAPI_MAILBOX_LABEL;
           labelSid = NullDefault.getInt(param.getBoxName(), -1);
           SmlLabelDao labelDao = new SmlLabelDao(ctx.getCon());
           SmlLabelModel labelMdl = labelDao.select(param.getAccountSid(), labelSid);
           labelName = labelMdl.getSlbName();

           // 受信 未読件数
           nonReadCountNum = dao.getLabelInboxCount(
                   param.getAccountSid(), labelSid, true);

           // 受信 件数
           countNum = dao.getLabelInboxCount(
                   param.getAccountSid(), labelSid, false);
           // 送信 件数
           countNum += dao.getLabelSentCount(
                   param.getAccountSid(), labelSid);
           // 草稿 件数
           countNum += dao.getLabelDraftCount(
                   param.getAccountSid(), labelSid);

       }

       Map<String, String> result = new LinkedHashMap<String, String>();
       result.put("boxName", boxName);
       result.put("countNum", String.valueOf(countNum));
       if (labelSid != null) {
           result.put("labelName", labelName);
           result.put("labelSid", String.valueOf(labelSid));
       }
       result.put("nonReadCountNum", String.valueOf(nonReadCountNum));

       RestApiResponseWriter.builder(res, ctx)
       .addResult(new ResultElement("result")
               .addContent(
                   result.entrySet()
                       .stream()
                       .map(e -> new ResultElement(e.getKey()).addContent(e.getValue()))
                       .collect(Collectors.toList())))
       .build().execute();
   }
}