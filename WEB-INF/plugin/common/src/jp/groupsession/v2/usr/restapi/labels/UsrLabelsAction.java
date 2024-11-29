package jp.groupsession.v2.usr.restapi.labels;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
/**
 *
 * <br>[機  能] ラベル情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("user")
public class UsrLabelsAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] ラベル情報API
    * <br>[解  説] GET
    * <br>[備  考] ラベル情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param ctx RestApiコンテキスト
     * @throws SQLException
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           RestApiContext ctx) throws SQLException {

       CmnLabelUsrDao dao = new CmnLabelUsrDao(ctx.getCon());
       List<CmnLabelUsrModel> list = dao.select();
       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       for (CmnLabelUsrModel posModel : list) {
           ResultElement result = new ResultElement("result");
           result.addContent(new ResultElement(
                   "sid").addContent(String.valueOf(posModel.getLabSid())));
           result.addContent(new ResultElement("name").addContent(posModel.getLabName()));
           result.addContent(new ResultElement("bikoText").addContent(posModel.getLabBiko()));
           result.addContent(new ResultElement("sortNum").addContent(
                   String.valueOf(posModel.getLabSort())));
           result.addContent(new ResultElement("categorySid").addContent(
                   String.valueOf(posModel.getLucSid())));
           builder.addResult(result);
       }
       builder.build().execute();
   }
}