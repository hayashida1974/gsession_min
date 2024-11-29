package jp.co.sjts.util.date;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 日付管理クラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class UDate implements Cloneable, Serializable, Comparator<UDate> {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UDate.class);

    /** 日曜日 */
    public static final int SUNDAY = 1;
    /** 月曜日 */
    public static final int MONDAY = 2;
    /** 火曜日 */
    public static final int TUESDAY = 3;
    /** 水曜日 */
    public static final int WEDNESDAY = 4;
    /** 木曜日 */
    public static final int THURSDAY = 5;
    /** 金曜日 */
    public static final int FRIDAY = 6;
    /** 土曜日 */
    public static final int SATURDAY = 7;

    /** 1月 */
    public static final int JANUARY = 1;
    /** 2月 */
    public static final int FEBRUARY = 2;
    /** 3月 */
    public static final int MARCH = 3;
    /** 4月 */
    public static final int APRIL = 4;
    /** 5月 */
    public static final int MAY = 5;
    /** 6月 */
    public static final int JUNE = 6;
    /** 7月 */
    public static final int JULY = 7;
    /** 8月 */
    public static final int AUGUST = 8;
    /** 9月 */
    public static final int SEPTEMBER = 9;
    /** 10月 */
    public static final int OCTOBER = 10;
    /** 11月 */
    public static final int NOVEMBER = 11;
    /** 12月 */
    public static final int DECEMBER = 12;

    /** compare(UDate o1, UDate o2)メソッド実施時、o1がo2より大きい場合に返されます */
    public static final int LARGE = 1;
    /** compare(UDate o1, UDate o2)メソッド実施時、o1とo2が等しい場合に返されます */
    public static final int EQUAL = 0;
    /** compare(UDate o1, UDate o2)メソッド実施時、o1がo2より小さい場合に返されます */
    public static final int SMALL = -1;

    /** 日付を格納します。 */
    private Calendar calendar__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] <p>インスタンスを生成します。<br>
     *              その際、現在の日時が設定されます。</p>
     * <br>[備  考]
     */
    public UDate() {
        calendar__ = Calendar.getInstance(java.util.TimeZone.getDefault());
        resetTime();
    }

    /**
     * <br>[機  能] 引数で指定したミリ秒よりインスタンスを生成します。
     * <br>[解  説] <p>インスタンスを生成します。<br>
     * <br>[備  考]
     * @param millis ミリ秒
     * @return 作成したUDate
     */
    public static UDate getInstance(long millis) {
        UDate udate = new UDate();
        udate.setTime(millis);
        return udate;
    }

    /**
     * <br>[機  能] 引数で指定したyyyymmdd or yyyy/mm/dd 形式のStringよりインスタンスを生成します。
     * <br>[解  説] <p>インスタンスを生成します。<br>
     * <br>[備  考]
     * @param yyyymmdd yyyymmdd形式のString
     * @return 作成したUDate
     */
    public static UDate getInstanceStr(String yyyymmdd) {
        UDate udate = new UDate();
        if (yyyymmdd.indexOf('/') < 0) {
            udate.setDate(yyyymmdd);
        } else {
            StringBuilder ymd = new StringBuilder("");
            StringTokenizer ymdToken = new StringTokenizer(yyyymmdd, "/");
            ymd.append(ymdToken.nextToken());
            String month = ymdToken.nextToken();
            if (month.length() < 2) {
                ymd.append("0");
            }
            ymd.append(month);
            String day = ymdToken.nextToken();
            if (day.length() < 2) {
                ymd.append("0");
            }
            ymd.append(day);
            udate.setDate(ymd.toString());
        }

        return udate;
    }

    /**
     * <br>[機  能] 引数で指定したjava.util.Dateよりインスタンスを生成します。
     * <br>[解  説] <p>インスタンスを生成します。<br>
     * <br>[備  考]
     * @param sqlDate java.sql.Date
     * @return 作成したUDate
     */
    public static UDate getInstanceSqlDate(java.sql.Date sqlDate) {
        long time = sqlDate.getTime();
        java.util.Date date = new java.util.Date(time);
        return UDate.getInstanceDate(date);
    }

    /**
     * <br>[機  能] 引数で指定したyyyymmdd形式のStringよりインスタンスを生成します。
     * <br>[解  説] <p>インスタンスを生成します。<br>
     * <br>[備  考]
     * @param date java.util.Date
     * @return 作成したUDate
     */
    public static UDate getInstanceDate(java.util.Date date) {
        UDate udate = new UDate();
        udate.setTime(date);
        return udate;
    }

    /**
     * <br>[機  能] 引数で指定したjava.sql.Timestampよりインスタンスを生成します。
     * <br>[解  説] <p>インスタンスを生成します。<br>
     * <br>[備  考]
     * @param tmstmp java.sql.Timestamp
     * @return 作成したUDate
     */
    public static UDate getInstanceTimestamp(java.sql.Timestamp tmstmp) {
//        UDate udate = new UDate();

        if (tmstmp == null) {
            return null;
        }

        long time = tmstmp.getTime();
        java.util.Date date = new java.util.Date(time);
        return UDate.getInstanceDate(date);
    }

    /**
     * <br>[機  能] 時間の前進後進計算を行います
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param fld Calendarクラスの時間フィールド
     * @param amount 進める数
     * @see java.util.Calendar
     */
    public void add(int fld, int amount) {
        calendar__.add(fld, amount);
    }

    /**
     * <br>[機  能] 設定されている「年」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 年に加算する整数
     */
    public void addYear(int amount) {
        calendar__.add(Calendar.YEAR, amount);
    }

    /**
     * <br>[機  能] 設定されている「月」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 月に加算する整数
     */
    public void addMonth(int amount) {
        calendar__.add(Calendar.MONTH, amount);
    }

    /**
     * <br>[機  能] 設定されている「月の日」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 月の日に加算する整数
     */
    public void addDay(int amount) {
        calendar__.add(Calendar.DATE, amount);
    }

    /**
     * <br>[機  能] 設定されている「時刻（24時間制)」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 時刻（24時間制)に加算する整数
     */
    public void addHour(int amount) {
        calendar__.add(Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * <br>[機  能] 設定されている「分」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 分に加算する整数
     */
    public void addMinute(int amount) {
        calendar__.add(Calendar.MINUTE, amount);
    }

    /**
     * <br>[機  能] 設定されている「秒」を指定された整数分加算します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param amount 秒に加算する整数
     */
    public void addSecond(int amount) {
        calendar__.add(Calendar.SECOND, amount);
    }

    /**
     * <br>[機  能] クローンを作成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return UDateオブジェクト
     */
    public synchronized Object clone() {
        return cloneUDate();
    }

    /**
     * <br>[機  能] クローンを返します。
     * <br>[解  説]
     * <br>[備  考] cloneメソッドはObject型で返しますが、このメソッドではUDate型で返します。
     *
     * @return UDateオブジェクト
     * @see #clone
     */
    public synchronized UDate cloneUDate() {
        UDate newDate = new UDate();
        newDate.setTimeStamp(this.getTimeStamp());
        return newDate;
    }

    /**
     * <br>[機  能] 日付をリセット(現在の日付に)します。
     * <br>[解  説]
     * <br>[備  考]
     */
    public synchronized void resetTime() {
        calendar__.setTime(new java.util.Date());
    }

    /**
     * <br>[機  能] 指定された年、月、日をセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param y  年
     * @param m  月
     * @param d  日
     */
    public synchronized void setDate(int y, int m, int d) {
        calendar__.set(y, m - 1, d);
    }

    /**
     * <br>[機  能] <p>yyyymmddの形式でフォーマットされた日付文字列から年、月、日を取得し<br>
     *              それぞれに設定します。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yyyymmdd  年月日(yyyymmdd)
     */
    public synchronized void setDate(String yyyymmdd) {
        setDate(
            Integer.parseInt(yyyymmdd.substring(0, 4)),
            Integer.parseInt(yyyymmdd.substring(4, 6)),
            Integer.parseInt(yyyymmdd.substring(6, 8)));
    }

    /**
     * <br>[機  能] yyyymmdd形式にフォーマットされた年月日文字列を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  年月日(yyyymmdd)
     */
    public synchronized String getDateString() {
        return getStrYear() + getStrMonth() + getStrDay();
    }

    /**
     * <br>[機  能] yyyymmddを指定した文字列で区切った年月日文字列を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param str 区切り文字
     * @return  年月日(yyyymmdd)
     */
    public synchronized String getDateString(String str) {
        return getStrYear() + str + getStrMonth() + str + getStrDay();
    }

    /**
     * <br>[機  能] yyyy-mm-dd形式にフォーマットされた年月日文字列を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  年月日(yyyy-mm-dd)
     */
    public synchronized String getDateStringForSql() {
        return getStrYear() + "-" + getStrMonth() + "-" + getStrDay();
    }

    /**
     * <br>[機  能] 年、月、日、時、分、秒をセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param y  年
     * @param m  月
     * @param d  日
     * @param h  時
     * @param min  分
     * @param s  秒
     */
    public synchronized void setTimeStamp(
        int y,
        int m,
        int d,
        int h,
        int min,
        int s) {
        calendar__.clear();
        calendar__.set(y, m - 1, d, h, min, s);
    }

    /**
     * <br>[機  能] yyyymmddhhMMssフォーマット文字列から取得した年、月、日、時、分、秒をセットします。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ymdhms  yyyymmddhhMMssフォーマット形式文字列
     */
    public synchronized void setTimeStamp(String ymdhms) {
        setTimeStamp(
            Integer.parseInt(ymdhms.substring(0, 4)),
            Integer.parseInt(ymdhms.substring(4, 6)),
            Integer.parseInt(ymdhms.substring(6, 8)),
            Integer.parseInt(ymdhms.substring(8, 10)),
            Integer.parseInt(ymdhms.substring(10, 12)),
            Integer.parseInt(ymdhms.substring(12, 14)));
    }

    /**
     * <br>[機  能] 年を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param year 年(yyyy)
     */
    public synchronized void setYear(int year) {
        calendar__.set(Calendar.YEAR, year);
    }

    /**
     * <br>[機  能] 月を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param month 月
     */
    public synchronized void setMonth(int month) {
        calendar__.set(Calendar.MONTH, month - 1);
    }

    /**
     * <br>[機  能] 日を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param day 日
     */
    public synchronized void setDay(int day) {
        calendar__.set(Calendar.DATE, day);
    }

    /**
     * <br>[機  能] 時間を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param hour 時間
     */
    public synchronized void setHour(int hour) {
        calendar__.set(Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * <br>[機  能] 分を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param minute 分
     */
    public synchronized void setMinute(int minute) {
        calendar__.set(Calendar.MINUTE, minute);
    }

    /**
     * <br>[機  能] 秒を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param second 秒
     */
    public synchronized void setSecond(int second) {
        calendar__.set(Calendar.SECOND, second);
    }

    /**
     * <br>[機  能] ミリ秒を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     *
     * @param msecond ミリ秒
     */
    public synchronized void setMilliSecond(int msecond) {
        calendar__.set(Calendar.MILLISECOND, msecond);
    }

    /**
     * <br>[機  能] 曜日を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dayOfWeek 日
     */
    public synchronized void setDayOfWeek(int dayOfWeek) {
        calendar__.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    /**
     * <br>[機  能] 年月日時分秒をyyyyMMddhhmmss形式の文字列に変換して返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  yyyyMMddhhmmss形式の文字列に変換した年月日時分秒
     */
    public synchronized String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = calendar__.getTime();
        return format.format(date);
    }
    /**
     * <br>[機  能] 年月日時分秒をyyyyMMddhhmmss形式の文字列に変換して返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  yyyy-MM-dd hh:mm:ss形式の文字列に変換した年月日時分秒
     */
    public synchronized String getTimeStamp2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = calendar__.getTime();
        return format.format(date);
    }
    /**
     * <br>[機  能] 年を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  年
     */
    public synchronized int getYear() {
        return calendar__.get(Calendar.YEAR);
    }

    /**
     * <br>[機  能] 年を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  年
     */
    public synchronized String getStrYear() {
        return StringUtil.toDecFormat(getYear(), "0000");
    }

    /**
     * <br>[機  能] 月を取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  月
     * @see java.util.Calendar#MONTH
     */
    public synchronized int getMonth() {
        int ret = -1;
        switch (calendar__.get(Calendar.MONTH)) {
            case Calendar.JANUARY : //1
                ret = JANUARY;
                break;
            case Calendar.FEBRUARY : //2
                ret = FEBRUARY;
                break;
            case Calendar.MARCH : //3
                ret = MARCH;
                break;
            case Calendar.APRIL : //4
                ret = APRIL;
                break;
            case Calendar.MAY : //5
                ret = MAY;
                break;
            case Calendar.JUNE : //6
                ret = JUNE;
                break;
            case Calendar.JULY : //7
                ret = JULY;
                break;
            case Calendar.AUGUST : //8
                ret = AUGUST;
                break;
            case Calendar.SEPTEMBER : //9
                ret = SEPTEMBER;
                break;
            case Calendar.OCTOBER : //10
                ret = OCTOBER;
                break;
            case Calendar.NOVEMBER : //11
                ret = NOVEMBER;
                break;
            case Calendar.DECEMBER : //12
            default:
                ret = DECEMBER;
                break;
        }
        return ret;
    }

    /**
     * <br>[機  能] 月をString型(00フォーマット)で取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  月
     */
    public synchronized String getStrMonth() {
        return StringUtil.toDecFormat(getMonth(), "00");
    }

    /**
     * <br>[機  能] 日をint型で取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 日
     */
    public synchronized int getIntDay() {
        return calendar__.get(Calendar.DATE);
    }

    /**
     * <br>[機  能] 日をString型(00フォーマット)で取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return 日
     */
    public synchronized String getStrDay() {
        return StringUtil.toDecFormat(getIntDay(), "00");
    }

    /**
     * <br>[機  能] 時間をint型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 時間
     */
    public synchronized int getIntHour() {
        return calendar__.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * <br>[機  能] 時間をString型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 時間
     */
    public synchronized String getStrHour() {
        return StringUtil.toDecFormat(getIntHour(), "00");
    }

    /**
     * <br>[機  能] 分をint型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 分
     */
    public synchronized int getIntMinute() {
        return calendar__.get(Calendar.MINUTE);
    }

    /**
     * <br>[機  能] 分をString型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 分
     */
    public synchronized String getStrMinute() {
        return StringUtil.toDecFormat(getIntMinute(), "00");
    }

    /**
     * <br>[機  能] 秒数をint型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 秒
     */
    public synchronized int getIntSecond() {
        return calendar__.get(Calendar.SECOND);
    }

    /**
     * <br>[機  能] 秒数をString型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 秒
     */
    public synchronized String getStrSecond() {
        return StringUtil.toDecFormat(getIntSecond(), "00");
    }

    /**
     * <br>[機  能] ミリ秒をint型で返します。
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     *
     * @return ミリ秒
     */
    public synchronized int getIntMilliSecond() {
        return calendar__.get(Calendar.MILLISECOND);
    }

    /**
     * <br>[機  能] ミリ秒をString型で返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ミリ秒
     */
    public synchronized String getStrMilliSecond() {
        return StringUtil.toDecFormat(getIntMilliSecond(), "0000");
    }

    /**
     * <br>[機  能] 1970/01/01からの時間をミリ秒で取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ミリ秒
     */
    public synchronized long getTimeMillis() {
        java.util.Date date = calendar__.getTime();
        return date.getTime();
    }

    /**
     * <br>[機  能] 時間を長整数型で取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 時間
     */
    public synchronized long getTime() {
        return (calendar__.getTime()).getTime();
    }

    /**
     * <br>[機  能] 長整数型で時間を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param time 設定する時間
     */
    public synchronized void setTime(long time) {
        calendar__.clear();
        calendar__.setTimeInMillis(time);
        (calendar__.getTime()).setTime(time);
    }

    /**
     * <br>[機  能] java.util.Dateで時間を設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date 設定する時間
     */
    public synchronized void setTime(java.util.Date date) {
        calendar__.setTime(date);
    }

    /**
     * <br>[機  能] 曜日を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 曜日を示す整数値を返します。
     */
    public synchronized int getWeek() {
        int ret = calendar__.get(Calendar.DAY_OF_WEEK);
        switch (ret) {
            case Calendar.SUNDAY ://日
                return SUNDAY;
            case Calendar.MONDAY ://月
                return MONDAY;
            case Calendar.TUESDAY ://火
                return TUESDAY;
            case Calendar.WEDNESDAY ://水
                return WEDNESDAY;
            case Calendar.THURSDAY ://木
                return THURSDAY;
            case Calendar.FRIDAY ://金
                return FRIDAY;
            case Calendar.SATURDAY ://土
            default :
                return SATURDAY;
        }
    }

    /**
     * <br>UDateの曜日定数から曜日文字を取得する
     * @return String 曜日
     * @param req リクエスト
     */
    public String getStrWeekJ(HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        int week = calendar__.get(Calendar.DAY_OF_WEEK);
        String str = "";
        switch (week) {
        case UDate.SUNDAY:
            str = gsMsg.getMessage(req, "cmn.sunday");
            break;
        case UDate.MONDAY:
            str = gsMsg.getMessage(req, "cmn.Monday");
            break;
        case UDate.TUESDAY:
            str = gsMsg.getMessage(req, "cmn.tuesday");
            break;
        case UDate.WEDNESDAY:
            str = gsMsg.getMessage(req, "cmn.wednesday");
            break;
        case UDate.THURSDAY:
            str = gsMsg.getMessage(req, "cmn.thursday");
            break;
        case UDate.FRIDAY:
            str = gsMsg.getMessage(req, "cmn.friday");
            break;
        case UDate.SATURDAY:
            str = gsMsg.getMessage(req, "cmn.saturday");
            break;
        default:
            break;
        }
        return str;
    }

    /**
     * <br>[機  能] UDateの曜日定数から曜日文字を取得する
     * <br>[解  説]
     * <br>[備  考]
     * <br>
     * @param reqMdl リクエスト情報
     * @return String 曜日
     */
    public String getStrWeekJ(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int week = calendar__.get(Calendar.DAY_OF_WEEK);
        String str = "";
        switch (week) {
        case UDate.SUNDAY:
            str = gsMsg.getMessage("cmn.sunday");
            break;
        case UDate.MONDAY:
            str = gsMsg.getMessage("cmn.Monday");
            break;
        case UDate.TUESDAY:
            str = gsMsg.getMessage("cmn.tuesday");
            break;
        case UDate.WEDNESDAY:
            str = gsMsg.getMessage("cmn.wednesday");
            break;
        case UDate.THURSDAY:
            str = gsMsg.getMessage("cmn.thursday");
            break;
        case UDate.FRIDAY:
            str = gsMsg.getMessage("cmn.friday");
            break;
        case UDate.SATURDAY:
            str = gsMsg.getMessage("cmn.saturday");
            break;
        default:
            break;
        }
        return str;
    }

    /**
     * <br>[機  能] 時分秒を00:00:00に設定する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void setZeroHhMmSs() {
        setHour(0);
        setMinute(0);
        setSecond(0);
        setMilliSecond(0);
    }

    /**
     * <br>[機  能] 日時分秒を00:00:00に設定する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void setZeroDdHhMmSs() {
        setDay(0);
        setHour(0);
        setMinute(0);
        setSecond(0);
        setMilliSecond(0);
    }

    /**
     * <br>[機  能] 時分秒を23:59:59.999に設定する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void setMaxHhMmSs() {
        setHour(23);
        setMinute(59);
        setSecond(59);
        setMilliSecond(999);
    }
    /**
     * <br>[機  能] 現在の月の何週目かを取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  現在の月の何週目かを示す整数値を返します。
     */
    public synchronized int getWeekOfMonth() {
        return calendar__.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * <br>[機  能] 現在の月の最終日を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return  最終日
     */
    public synchronized int getMaxDayOfMonth() {
        return calendar__.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * <br>[機  能] 現在の月の何週目かを設定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param wk  週
     */
    public synchronized void setWeek(int wk) {
        calendar__.set(Calendar.WEEK_OF_MONTH, wk);
    }

    /**
     * <br>[機  能] このオブジェクトの文字列表現を返します。
     * <br>[解  説]
     * <br>[備  考] ここではyyyymmddhhMMss形式の文字列を返します。
     *
     * @return yyyymmddhhMMss形式の文字列
     */
    public String toString() {
        if (calendar__ == null) {
            return null;
        }
        return getTimeStamp();
    }

    /**
     * <br>[機  能] 年が等しいか判定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 比較対照のUDate
     * @return true:等しい,false:等しくない
     */
    public boolean equalsYear(UDate target) {
        String targetYear = target.getStrYear();
        String thisYear = this.getStrYear();
        return thisYear.equals(targetYear);
    }

    /**
     * <br>[機  能] 月が等しいか判定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 比較対照のUDate
     * @return true:等しい,false:等しくない
     */
    public boolean equalsMonth(UDate target) {
        String targetMonth = target.getStrMonth();
        String thisMonth = this.getStrMonth();
        return thisMonth.equals(targetMonth);
    }

    /**
     * <br>[機  能] 日が等しいか判定します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param target 比較対照のUDate
     * @return true:等しい,false:等しくない
     */
    public boolean equalsDay(UDate target) {
        String targetDay = target.getStrDay();
        String thisDay = this.getStrDay();
        return thisDay.equals(targetDay);
    }

    /**
     * <br>[機  能] java.sql.Dateを取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return このオブジェクトをjava.sql.Date型にした値
     */
    public java.sql.Date getSQLDate() {
        //JDK1.4以上で使用可能
        //long mili = calendar__.getTimeInMillis();

        java.util.Date date = calendar__.getTime();
        long mili = date.getTime();
        return new java.sql.Date(mili);
    }

    /**
     * <br>[機  能] 時間の比較を行い、大小関係を返します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param o1 比較対象の最初のオブジェクト(UDate)
     * @param o2 比較対象の 2 番目のオブジェクト(UDate)
     * @return このクラスを基準に、時間の大小関係を定数で返します。
     *              o1 > o2 : SMALL
     *              o1 = o2 : EQUAL
     *              o1 < o2 : LARGE
     * @see #EQUAL
     * @see #LARGE
     * @see #SMALL
     * @throws ClassCastException 指定されたオブジェクトがUDateではなかった場合
     * @throws NullPointerException o1もしくはo2にnullが指定された場合
     */
    public int compare(UDate o1, UDate o2) throws ClassCastException, NullPointerException {
        int res = EQUAL;
        long difference = o2.getTimeMillis() - o1.getTimeMillis();

        if (difference > EQUAL) {
            res = LARGE;
        } else if (difference < EQUAL) {
            res = SMALL;
        }
        return res;
    }

    /**
     * <p>日付(YMD)のみの比較を行い、大小関係を返します。
     *
     * @param date 比較対照の日付
     * @return このクラスを基準に、日付の大小関係を定数で返します。
     * @see #EQUAL
     * @see #LARGE
     * @see #SMALL
     * @throws NullPointerException dateにnullが指定された場合
     */
    public int compareDateYMD(UDate date) throws NullPointerException {
        int res = EQUAL;
        int differenceY = date.getYear() - this.getYear();
        int differenceM = date.getMonth() - this.getMonth();
        int differenceD = date.getIntDay() - this.getIntDay();

        if (differenceY == EQUAL) {

            if (differenceM == EQUAL) {

                if (differenceD != EQUAL) {
                    if (differenceD > EQUAL) {
                        res = LARGE;
                    } else if (differenceD < EQUAL) {
                        res = SMALL;
                    }
                }

            } else {

                if (differenceM > EQUAL) {
                    res = LARGE;
                } else if (differenceM < EQUAL) {
                    res = SMALL;
                }

            }

        } else {

            if (differenceY > EQUAL) {
                res = LARGE;
            } else if (differenceY < EQUAL) {
                res = SMALL;
            }

        }

        return res;
    }

    /**
     * <p>日付(YM)のみの比較を行い、大小関係を返します。
     *
     * @param date 比較対照の日付
     * @return このクラスを基準に、日付の大小関係を定数で返します。
     * @see #EQUAL
     * @see #LARGE
     * @see #SMALL
     * @throws NullPointerException dateにnullが指定された場合
     */
    public int compareDateYM(UDate date) throws NullPointerException {
        int res = EQUAL;
        int differenceY = date.getYear() - this.getYear();
        int differenceM = date.getMonth() - this.getMonth();

        if (differenceY == EQUAL) {
            if (differenceM == EQUAL) {
            } else {

                if (differenceM > EQUAL) {
                    res = LARGE;
                } else if (differenceM < EQUAL) {
                    res = SMALL;
                }
            }
        } else {

            if (differenceY > EQUAL) {
                res = LARGE;
            } else if (differenceY < EQUAL) {
                res = SMALL;
            }
        }
        return res;
    }

    /**
     * <p>日付(YMDHM)のみの比較を行い、大小関係を返します。
     *
     * @param date 比較対照の日付
     * @return このクラスを基準に、日付の大小関係を定数で返します。
     * @see #EQUAL
     * @see #LARGE
     * @see #SMALL
     */
    public int compareDateYMDHM(UDate date) {
        int res = EQUAL;
        int differenceY = date.getYear() - this.getYear();
        int differenceM = date.getMonth() - this.getMonth();
        int differenceD = date.getIntDay() - this.getIntDay();
        int differenceH = date.getIntHour() - this.getIntHour();
        int differenceMi = date.getIntMinute() - this.getIntMinute();

        if (differenceY == EQUAL) {

            if (differenceM == EQUAL) {

                if (differenceD == EQUAL) {

                    if (differenceH == EQUAL) {

                        if (differenceMi > EQUAL) {
                            res = LARGE;
                        } else if (differenceMi < EQUAL) {
                            res = SMALL;
                        }
                    } else {
                        if (differenceH > EQUAL) {
                            res = LARGE;
                        } else if (differenceH < EQUAL) {
                            res = SMALL;
                        }
                    }

                } else {

                    if (differenceD > EQUAL) {
                        res = LARGE;
                    } else if (differenceD < EQUAL) {
                        res = SMALL;
                    }
                }

            } else {

                if (differenceM > EQUAL) {
                    res = LARGE;
                } else if (differenceM < EQUAL) {
                    res = SMALL;
                }

            }

        } else {

            if (differenceY > EQUAL) {
                res = LARGE;
            } else if (differenceY < EQUAL) {
                res = SMALL;
            }

        }

        log__.debug("compareDateYMDHM==>" + res);
        return res;
    }

    /**
     * <p>日付(HM)のみの比較を行い、大小関係を返します。
     *
     * @param date 比較対照の日付
     * @return このクラスを基準に、日付の大小関係を定数で返します。
     * @see #EQUAL
     * @see #LARGE
     * @see #SMALL
     */
    public int compareDateHM(UDate date) {
        int res = EQUAL;
        int differenceH = date.getIntHour() - this.getIntHour();
        int differenceMi = date.getIntMinute() - this.getIntMinute();

        if (differenceH == EQUAL) {

            if (differenceMi > EQUAL) {
                res = LARGE;
            } else if (differenceMi < EQUAL) {
                res = SMALL;
            }
        } else {
            if (differenceH > EQUAL) {
                res = LARGE;
            } else if (differenceH < EQUAL) {
                res = SMALL;
            }
        }

        log__.debug("compareDateYMDHM==>" + res);
        return res;
    }

    /**
     * このクラスがパラメータ値の開始、終了の間の日付か判定する
     * 判定材料はYMDHMまでとする
     * @param frDate 開始
     * @param toDate 終了
     * @return true:間にある false:間に無い
     */
    public boolean betweenYMDHM(UDate frDate, UDate toDate) {

        log__.debug(this.getDateString() + " " + this.getIntHour() + ":" + this.getIntMinute());
        if (this.compareDateYMDHM(frDate) != LARGE
         && this.compareDateYMDHM(toDate) != SMALL) {
            return true;
        }
        return false;
    }
    /**
     * <br>[機  能] 年月日が指定されたUDateの年月日と一致しているかどうか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param target 比較対象となるのUDate
     * @return true:一致, false:不一致
     */
    public boolean equalsDate(UDate target) {
        String strTarget = target.getDateString();
        String strThis = this.getDateString();
        return strTarget.equals(strThis);
    }

    /**
     * <br>[機  能] 年月日時分秒が指定されたUDateの年月日時分秒と一致しているかどうか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param target 比較対象となるUDate
     * @return true:一致, false:不一致
     */
    public boolean equalsTimeStamp(UDate target) {
        String strTarget = target.getTimeStamp();
        String strThis = this.getTimeStamp();
        return strTarget.equals(strThis);
    }

    /**
     * <br>[機  能] java.sql.Timestampを取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return このオブジェクトをjava.sql.Timestamp型にした値
     */
    public java.sql.Timestamp toSQLTimestamp() {
        //JDK1.4以上で使用可能
        //long mili = calendar__.getTimeInMillis();

        java.util.Date date = calendar__.getTime();
        long mili = date.getTime();
        return new java.sql.Timestamp(mili);
    }
    /**
     * <br>[機  能] java.util.Dateを取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return このオブジェクトをjava.util.Date型にした値
     */
    public java.util.Date toJavaUtilDate() {
        //JDK1.4以上で使用可能
        //long mili = calendar__.getTimeInMillis();

        java.util.Date date = calendar__.getTime();
        long mili = date.getTime();
        return new java.util.Date(mili);
    }
}
