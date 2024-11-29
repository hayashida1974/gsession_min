package jp.groupsession.v2.cmn.restapi.holidays;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 休日情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnHolidaysAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] 休日情報API
    * <br>[解  説] GET
    * <br>[備  考] 休日情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param paramModel パラメータモデル
    * @param ctx RestApiコンテキスト
     * @throws SQLException
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           CmnHolidaysGetParamModel paramModel,
           RestApiContext ctx) throws SQLException {

       //初期値 現在
       UDate frDate = new UDate();
       if (paramModel.getFromDate() != null) {
           UDate from = paramModel.getFromDate();
           frDate.setDate(from.getYear(),
                   from.getMonth(),
                   from.getIntDay());
       }
       frDate.setHour(0);
       frDate.setMinute(0);
       frDate.setSecond(0);

       UDate toDate = frDate.cloneUDate();
       if (paramModel.getToDate() != null) {
           UDate to = paramModel.getToDate();
           toDate.setDate(to.getYear(),
                   to.getMonth(),
                   to.getIntDay());
       } else {
           //初期値 現在+1年
           toDate.addYear(1);
       }
       toDate.setHour(0);
       toDate.setMinute(0);
       toDate.setSecond(0);

       // 開始>終了の時、エラーとする
       if (frDate.compareDateYMD(toDate) == -1) {
           throw new RestApiValidateException(
                   EnumError.PARAM_COLLABORATION,
                   "error.input.comp.text",
                   new GsMessage(ctx.getRequestModel())
                       .getMessage("cmn.period"),
                   new GsMessage(ctx.getRequestModel())
                       .getMessage("restapi.common.holidays.1")
                   );
       }

       CmnHolidayDao holDao = new CmnHolidayDao(ctx.getCon());
       HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);

       if (holMap == null) {
           holMap = new HashMap<String, CmnHolidayModel>();
       }

       Set<Entry<String, CmnHolidayModel>> set = holMap.entrySet();
       Iterator<Entry<String, CmnHolidayModel>> it = set.iterator();
       TreeMap<String, CmnHolidayModel> holTreeMap = new TreeMap<String, CmnHolidayModel>();
       while (it.hasNext()) {
           Entry<String, CmnHolidayModel> entry = (Entry<String, CmnHolidayModel>) it.next();
           holTreeMap.put(entry.getKey(), entry.getValue());
       }
       Collection<CmnHolidayModel> colections = holTreeMap.values();

       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       for (CmnHolidayModel data : colections) {
           ResultElement result = new ResultElement("result");
           String holdate = data.getHolDate().getStrYear()
                                 + "/" + data.getHolDate().getStrMonth()
                                 + "/" + data.getHolDate().getStrDay();
           result.addContent(new ResultElement("date").addContent(holdate));
           result.addContent(new ResultElement("name").addContent(data.getHolName()));
           result.addContent(new ResultElement("timecardCalcFlg").addContent(
                   String.valueOf(data.getHolTcd())));
           builder.addResult(result);
       }
       builder.build().execute();
   }
}