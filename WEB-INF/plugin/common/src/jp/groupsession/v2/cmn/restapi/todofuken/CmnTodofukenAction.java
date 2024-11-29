package jp.groupsession.v2.cmn.restapi.todofuken;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
/**
 *
 * <br>[機  能] 都道府県情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnTodofukenAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] 都道府県情報API
    * <br>[解  説] GET
    * <br>[備  考] 都道府県情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param ctx RestApiコンテキスト
     * @throws SQLException
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           RestApiContext ctx) throws SQLException {

       CmnTdfkDao dao = new CmnTdfkDao(ctx.getCon());
       Map<Integer, CmnTdfkModel> tdfkMap = dao.getTdfkMap();
       Collection<CmnTdfkModel> colection = tdfkMap.values();
       Iterator<CmnTdfkModel> it = colection.iterator();
       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       while (it.hasNext()) {
           ResultElement result = new ResultElement("result");
           CmnTdfkModel data = it.next();
           result.addContent(new ResultElement("sid").addContent(String.valueOf(data.getTdfSid())));
           result.addContent(new ResultElement("name").addContent(data.getTdfName()));
           builder.addResult(result);
       }
       builder.build().execute();
   }
}