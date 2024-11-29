package jp.groupsession.v2.cmn.cmn200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.biz.ThemePathBiz;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
/**
 * <br>[機  能] ポップアップカレンダー休日取得のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn200Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn200Biz.class);
    /** ポップアップカレンダーでの表示日数 */
    private static final int CALENDAR_DAY_COUNT__ = 42;
    /** 開始　時 */
    private static final int DAY_START_HOUR__ = 0;
    /** 開始　分 */
    private static final int DAY_START_MINUTES__ = 0;
    /** 開始　秒 */
    private static final int DAY_START_SECOND__ = 0;
    /** 終了　時 */
    private static final int DAY_END_HOUR__ = 23;
    /** 終了　分 */
    private static final int DAY_END_MINUTES__ = 59;
    /** 終了　秒 */
    private static final int DAY_END_SECOND__ = 59;
    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * */
    public Cmn200Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] カレンダー表示情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @param dateRange 取得範囲(年指定)
     * @return 休日情報(JSON)
     * @throws SQLException SQL実行例外
     */
    public JSONObject setInitData(
                            String dateStr, int dateRange) throws SQLException {
        JSONObject ret = new JSONObject();
        ret.put("result", "1");
        ret.put("kyuujitu", __restDays(dateStr, dateRange));
        ret.put("themeCss", __getThemePathList());
        return ret;
    }

    /**
     * <br>[機  能] 休日情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @param dateRange 取得範囲(年指定)
     * @return 休日情報(JSON)
     * @throws SQLException SQL実行例外
     */
    private JSONArray __restDays(String dateStr, int dateRange) throws SQLException {
        boolean writeSuccess = false;
        int noDataFlg = 0;

        log__.debug(dateStr);

        UDate dspDate = null;

        if (dateStr == null) {
            noDataFlg = 1;
        } else {
            dspDate = UDate.getInstanceStr(dateStr);
        }
        //JOSON作成
        List<JSONObject> holdayList = new ArrayList<JSONObject>();
        if (noDataFlg != 1) {

            //休日情報を取得する
            UDate frDate = dspDate.cloneUDate();
            frDate.setHour(DAY_START_HOUR__);
            frDate.setMinute(DAY_START_MINUTES__);
            frDate.setSecond(DAY_START_SECOND__);
            UDate toDate = dspDate.cloneUDate();
            toDate.addDay(CALENDAR_DAY_COUNT__ - 1);
            toDate.setHour(DAY_END_HOUR__);
            toDate.setMinute(DAY_END_MINUTES__);
            toDate.setSecond(DAY_END_SECOND__);

            if (dateRange >= 1 && dateRange <= 5) {
                frDate.addYear(dateRange * -1);
                toDate.addYear(dateRange);
            }

            CmnHolidayDao holDao = new CmnHolidayDao(con__);
            HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);

            //休日情報を設定
            holMap.get(dspDate.getDateString());

            Set<Entry<String, CmnHolidayModel>> set = holMap.entrySet();
            Iterator<Entry<String, CmnHolidayModel>> it = set.iterator();
            while (it.hasNext()) {
                Cmn200HolidayMdl hlMdl = new Cmn200HolidayMdl();
                Entry<String, CmnHolidayModel> entry = (Entry<String, CmnHolidayModel>) it.next();
                CmnHolidayModel data = new CmnHolidayModel();
                data = (CmnHolidayModel) entry.getValue();
                hlMdl.setDate(data.getHolDate().getStrYear() + data.getHolDate().getStrMonth()
                                                      + data.getHolDate().getStrDay());
                hlMdl.setHolidayName(data.getHolName());
                log__.debug("休日名称＝" + data.getHolName());
                JSONObject jsonParam = JSONObject.fromObject(hlMdl);
                holdayList.add(jsonParam);
            }
            writeSuccess = true;
        }

        JSONArray jsonArray = null;
        if (writeSuccess && !holdayList.isEmpty()) {
            jsonArray = JSONArray.fromObject(holdayList);
        }

        return jsonArray;
    }

    /**
    *
    * <br>[機  能] theme.cssのリンクを書き出す
    * <br>[解  説] 指定されたファイルの経路上のtheme_base.cssのリンクも書き出す
    * <br>[備  考]
    * @return cssのパスリスト
    */
   private List<String> __getThemePathList()  {
       String themePath = reqMdl__.getSmodel().getCtmPath();
       if (themePath == null) {
           themePath = ThemePathBiz.DEFAULT_THEME_PATH;
       }
       ThemePathBiz tpBiz = new ThemePathBiz();
       return tpBiz.createPathList(themePath + "/theme.css");

   }


}