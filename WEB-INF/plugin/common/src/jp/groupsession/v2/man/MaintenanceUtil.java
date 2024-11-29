package jp.groupsession.v2.man;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 日付関連の機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class MaintenanceUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MaintenanceUtil.class);

    /**
     * <br>[機  能] 第X (週)の文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週(1～5)
     * @param reqMdl リクエスト情報
     * @return 日本語文字列の曜日。
     */
    public static String getWeek(String week, RequestModel reqMdl) {
        int intWeek = -1;
        if (ValidateUtil.isNumber(week)) {
            intWeek = Integer.parseInt(week);
        }
        return getWeek(intWeek, reqMdl);
    }

    /**
     * <br>[機  能] 第X (週)の文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週(1～5)
     * @param reqMdl リクエスト情報
     * @return 日本語文字列の曜日。
     */
    public static String getWeek(int week, RequestModel reqMdl) {
        String ret = "　　　";
        GsMessage gsMsg = new GsMessage(reqMdl);
        switch (week) {
        case 1:
            ret = gsMsg.getMessage("cmn.no.1");
            break;
        case 2:
            ret = gsMsg.getMessage("cmn.no.2");
            break;
        case 3:
            ret = gsMsg.getMessage("cmn.no.3");
            break;
        case 4:
            ret = gsMsg.getMessage("cmn.no.4");
            break;
        case 5:
            ret = gsMsg.getMessage("cmn.no.5");
            break;
        default:
        }
        return ret;
    }

    /**
     * <br>[機  能] 曜日の文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param youbiKbn int
     * @param reqMdl リクエスト情報
     * @return 日本語文字列の曜日。
     */
    public static String getYoubi(int youbiKbn, RequestModel reqMdl) {
        String ret = "　　　";
        GsMessage gsMsg = new GsMessage(reqMdl);
        switch (youbiKbn) {
        case 0:
            ret = gsMsg.getMessage("cmn.sunday3");
            break;
        case 1:
            ret = gsMsg.getMessage("cmn.monday3");
            break;
        case 2:
            ret = gsMsg.getMessage("cmn.tuesday3");
            break;
        case 3:
            ret = gsMsg.getMessage("cmn.wednesday3");
            break;
        case 4:
            ret = gsMsg.getMessage("cmn.thursday3");
            break;
        case 5:
            ret = gsMsg.getMessage("main.src.man080.7");
            break;
        case 6:
            ret = gsMsg.getMessage("cmn.saturday3");
            break;
        default:
        }
        return ret;
    }

    /**
     * <br>[機  能] カレンダークラス解釈の曜日文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param youbiKbn int
     * @param reqMdl リクエスト情報
     * @return 曜日
     */
    public static String getStrYoubiForCalendar(int youbiKbn, RequestModel reqMdl) {
        youbiKbn = 1 + youbiKbn;
        String ret = "　　　";
        GsMessage gsMsg = new GsMessage(reqMdl);
        switch (youbiKbn) {
        case 1:
            ret = gsMsg.getMessage("cmn.sunday3");
            break;
        case 2:
            ret = gsMsg.getMessage("cmn.monday3");
            break;
        case 3:
            ret = gsMsg.getMessage("cmn.tuesday3");
            break;
        case 4:
            ret = gsMsg.getMessage("cmn.wednesday3");
            break;
        case 5:
            ret = gsMsg.getMessage("cmn.thursday3");
            break;
        case 6:
            ret = gsMsg.getMessage("main.src.man080.7");
            break;
        case 7:
            ret = gsMsg.getMessage("cmn.saturday3");
            break;
        default:
        }
        return ret;
    }

    /**
     * <br>[機  能] カレンダークラス表現の曜日より、
     * <br>         ホリデーテンプレート表現の曜日文字列を返却します
     * <br>[解  説]
     * <br>[備  考]
     * @param youbiKbn int
     * @param reqMdl リクエスト情報
     * @return 曜日
     */
    public static String getStrYoubiFromCalendar(int youbiKbn, RequestModel reqMdl) {
        String ret = "　　　";
        GsMessage gsMsg = new GsMessage(reqMdl);
        switch (youbiKbn) {
        case 1:
            ret = gsMsg.getMessage("cmn.sunday3"); break;
        case 2:
            ret = gsMsg.getMessage("cmn.monday3"); break;
        case 3:
            ret = gsMsg.getMessage("cmn.tuesday3"); break;
        case 4:
            ret = gsMsg.getMessage("cmn.wednesday3"); break;
        case 5:
            ret = gsMsg.getMessage("cmn.thursday3"); break;
        case 6:
            ret = gsMsg.getMessage("main.src.man080.7"); break;
        case 7:
            ret = gsMsg.getMessage("cmn.saturday3"); break;
        default:
        }
        return ret;
    }

    /**
     * <br>[機  能] カレンダークラス表現の数値を返却します。
     * <br>[解  説]
     * <br>[備  考]
     * @param youbiKbn int
     * @return int カレンダークラス曜日表現
     */
    public static int getIntYoubiForCalendar(int youbiKbn) {
        int ret = -1;
        switch (youbiKbn) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            ret = youbiKbn + 1;
            break;
        default:
        }
        return ret;
    }

    /**
     * <br>[機  能] カレンダーが返却する週目と第何曜日に合致するか検査します。
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param compDayOfweek 比較対象週
     * @return boolean true :そのまま使用可 false : 週－１する必要有り
     */
    public static boolean isAccurateWeekOfMonth(UDate date, int compDayOfweek) {
        boolean result = true;
        log__.debug("START");

        log__.debug("getFirstDayOfWeekOfMonth(date)"
                + UDateUtil.getFirstDayOfWeekOfMonth(date));

        //年、月より月の第１日の曜日を求める。
        int firstDayOfWeek = (UDateUtil.getFirstDayOfWeekOfMonth(date));

        log__.debug("compDayOfweek :" + compDayOfweek
                + "  UDate/firstDayOfWeek :" + firstDayOfWeek);
        if ((compDayOfweek - firstDayOfWeek) < 0) {
            result = false;
        }

        log__.debug("END" + result);
        return result;
    }

    /**
     * <br>[機  能] カレンダーが返却する週目と第何曜日に合致するか検査し
     * <br>         実際に使用する週(第X週)を使用する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param compDayOfweek 比較対象週
     * @return 週(第X週)
     */
    public static int getAccurateWeekOfMonth(UDate date, int compDayOfweek) {

        int wkWeekOfMonth = 0;
        boolean result = MaintenanceUtil.isAccurateWeekOfMonth(
                            date, date.getWeek());
        if (result) {
            //そのままOK
            wkWeekOfMonth = date.getWeekOfMonth();
        } else {
            //-１週
            wkWeekOfMonth = date.getWeekOfMonth() - 1;
        }

        return wkWeekOfMonth;
    }

}
