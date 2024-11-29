package jp.co.sjts.util.date;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] UDateのユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class UDateUtil {

    /**
     * <br>[機  能] 空のコンストラクタ
     * <br>[解  説]
     * <br>[備  考] インスタンスの生成を行えないように設定
     */
    private UDateUtil() {
    }

    /**
     * <br>[機  能] 月の最終日かどうか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param date 判定の対象
     * @return true:最終日, false:最終日ではない
     */
    public static boolean isLastDayOfMonth(UDate date) {
        UDate cdate = date.cloneUDate();
        cdate.setDay(cdate.getMaxDayOfMonth());
        boolean ret = false;
        ret = cdate.equalsDate(date);
        return ret;
    }

    /**
     * <br>[機  能] 次の月の最終日をセットしたUDateを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param date 対象の日付
     * @return 月の最終日をセットしたUDate
     */
    public static UDate upMonthLastDay(UDate date) {
        return addMonthLastDay(date, 1);
    }

    /**
     * <br>[機  能] 引数で指定分の月数を進め、最終日をセットしたUDateを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param date 対象の日付
     * @param i 追加する月数
     * @return 最終日をセットしたUDate
     */
    public static UDate addMonthLastDay(UDate date, int i) {
        date.add(Calendar.MONTH, i);
        int max = date.getMaxDayOfMonth();
        date.setDay(max);
        return date;
    }

    /**
     * <br>[機  能] 二つの日付の差を求める
     * <br>[解  説]
     * <br>[備  考]
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差をミリ秒に変換した値
     */
    public static long diffMillis(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2;
            newDate = u1;
        } else {
            oldDate = u1;
            newDate = u2;
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();
        return ldiff;
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その秒数を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差を秒数に変換した値
     */
    public static long diffSecond(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2;
            newDate = u1;
        } else {
            oldDate = u1;
            newDate = u2;
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();
        return __divisionDown(ldiff, (long) 1000);
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その分を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差を分に変換した値
     */
    public static long diffMinute(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2;
            newDate = u1;
        } else {
            oldDate = u1;
            newDate = u2;
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();
        return __divisionDown(ldiff, ((long) 60 * (long) 1000));
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その時間を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差を時間に変換した値
     */
    public static long diffHour(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2;
            newDate = u1;
        } else {
            oldDate = u1;
            newDate = u2;
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();

        return __divisionDown(ldiff, ((long) 60 * (long) 60 * (long) 1000));
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その日数を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差をミリ秒に変換した値
     */
    public static int diffDay(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2;
            newDate = u1;
        } else {
            oldDate = u1;
            newDate = u2;
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();
        return toDaysOfMills(ldiff);
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その月数を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差をミリ秒に変換した値
     */
    public static int diffMonth(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2.cloneUDate();
            newDate = u1.cloneUDate();
        } else {
            oldDate = u1.cloneUDate();
            newDate = u2.cloneUDate();
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();

        //1ヶ月をミリ秒に変換(最大の日数で)
        long month =
            (long) 31 * (long) 24 * (long) 60 * (long) 60 * (long) 1000;
        //差の月を算出。念のため-1する
        int t = __divisionDown(ldiff, month) - 1;
        //月を進める
        oldDate.add(Calendar.MONTH, t);
        //閏年の計算のため、日付がold > new になるまで月を1月づつ進める
        while (oldDate.getTimeMillis() < newDate.getTimeMillis()) {
            t++;
            oldDate.add(Calendar.MONTH, 1);
        }
        //old > newになっているので-1する。
        //ミリ秒で同値になった場合は-1しない
        if (oldDate.getTimeMillis() != newDate.getTimeMillis()) {
            t--;
        }
        return t;
    }

    /**
     * <br>[機  能] 二つの日付の差を求め、その年数を返します
     * <br>[解  説]
     * <br>[備  考] 端数は切り捨てます
     * @param u1 対象1
     * @param u2 対象2
     * @return 時間の差を年に変換した値
     */
    public static int diffYear(final UDate u1, final UDate u2) {
        long ldiff = 0;
        UDate oldDate = null;
        UDate newDate = null;
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            oldDate = u2.cloneUDate();
            newDate = u1.cloneUDate();
        } else {
            oldDate = u1.cloneUDate();
            newDate = u2.cloneUDate();
        }
        //ミリ秒で差を算出
        ldiff = newDate.getTimeMillis() - oldDate.getTimeMillis();

        //1年をミリ秒に変換(閏年を含めた日数で)
        long year =
            (long) 366 * (long) 24 * (long) 60 * (long) 60 * (long) 1000;
        //差の年を算出。365日で計算する値よりも-1する
        int t = __divisionDown(ldiff, year) - 1;
        //年を進める(1年を365日として計算したものよりも1年古い年で)
        oldDate.add(Calendar.YEAR, t);
        //閏年の計算のため、日付がold > new になるまで年を1年づつ進める
        while (oldDate.getTimeMillis() < newDate.getTimeMillis()) {
            t++;
            oldDate.add(Calendar.YEAR, 1);
        }
        //old > newになっているので-1する。
        //ミリ秒で同値になった場合は-1しない
        if (oldDate.getTimeMillis() != newDate.getTimeMillis()) {
            t--;
        }
        return t;
    }

    /**
     * <br>[機  能] 現在日付に当てはまるかをチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param from 開始年月日
     * @param to 終了年月日
     * @return 現在の日付が範囲内ならtrue
     */
    public static boolean isRange(final UDate from, final UDate to) {
        UDate nowYMD = new UDate();
        if (from == null || to == null) {
            return false;
        }
        int fromNum = Integer.parseInt(from.getDateString());
        int toNum = Integer.parseInt(to.getDateString());
        int nowNum = Integer.parseInt(nowYMD.getDateString());
        return fromNum <= nowNum && nowNum <= toNum;
    }

    /**
     * <br>[機  能] l1/l2を行い端数を切り捨てた値を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param l1 計算対象1
     * @param l2 計算対象2
     * @return 計算結果
     */
    private static int __divisionDown(long l1, long l2) {
        BigDecimal b1 = BigDecimal.valueOf(l1);
        BigDecimal b2 = BigDecimal.valueOf(l2);
        BigDecimal result = b1.divide(b2, BigDecimal.ROUND_DOWN);
        return result.intValue();
    }

    /**
     * <br>[機  能] ミリ秒から日付を算出します
     * <br>[解  説]
     * <br>[備  考]
     * @param millis ミリ秒
     * @return 日付
     */
    public static int toDaysOfMills(long millis) {
        long h = 24;
        long mM = 60;
        long s = 60;
        long ms = 1000;
        return __divisionDown(millis, (h * mM * s * ms));
    }

    /**
     * <br>[機  能] 日付の差をUDateで返します
     * <br>[解  説]
     * <br>[備  考]
     * @param u1 対象1
     * @param u2 対象2
     * @return 日付の差
     */
    public static DiffDate diff(final UDate u1, final UDate u2) {

        UDate t1 = null; //新
        UDate t2 = null; //古
        if (u1.getTimeMillis() > u2.getTimeMillis()) {
            t1 = u1;
            t2 = u2;
        } else {
            t1 = u2;
            t2 = u1;
        }
        DiffDate date = new DiffDate();
        UDate tmp = t2.cloneUDate();
        //年
        int y = diffYear(t1, tmp);
        if (y > 0) {
            tmp.add(Calendar.YEAR, y);
            tmp.getTimeMillis();
        }
        date.setYear(y);

        //月
        int m = diffMonth(t1, tmp);
        if (m > 0) {
            tmp.add(Calendar.MONTH, m);
            tmp.getTimeMillis();
        }
        date.setMonth(m);

        //日
        int d = diffDay(t1, tmp);
        if (d > 0) {
            tmp.add(Calendar.DATE, d);
        }
        date.setDay(d);
        //時
        int h = (int) diffHour(t1, tmp); //intでキャストして問題ない桁になっている
        if (h > 0) {
            tmp.add(Calendar.HOUR, h);
        }
        date.setHour(h);
        //分
        int mM = (int) diffMinute(t1, tmp); //intでキャストして問題ない桁になっている
        if (mM > 0) {
            tmp.add(Calendar.MINUTE, mM);
        }
        date.setMinuts(mM);
        //秒   //intでキャストして問題ない桁になっている
        int s = (int) diffSecond(t1, tmp);
        if (s > 0) {
            tmp.add(Calendar.SECOND, s);
        }
        date.setSeconds(s);
        //ミリ秒
        int ms = (int) diffMillis(t1, tmp);
        date.setMillis(ms);
        return date;
    }

    /**
     * <br>[機  能] 文字列の年、月、日からUDateに変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param yyyy 年
     * @param mm 月
     * @param dd 日
     * @return UDate 作成に失敗した倍はnull
     */
    public static UDate getUDate(String yyyy, String mm, String dd) {
        if (StringUtil.isNullZeroString(yyyy)
            || StringUtil.isNullZeroString(mm)
            || StringUtil.isNullZeroString(dd)) {
            return null;
        }
        String year = StringUtil.toDecFormat(yyyy, "0000");
        String mon = StringUtil.toDecFormat(mm, "00");
        String day = StringUtil.toDecFormat(dd, "00");
        return UDate.getInstanceStr(year + mon + day);
    }

    /**
     * <br>[機  能] 文字列の年、月、日からUDateに変換する
     * <br>[解  説]
     * <br>[備  考] このメソッドでは、引数のnullチェックと数字チェックを行います
     * @param yyyy 年
     * @param mm 月
     * @param dd 日
     * @return UDate 作成に失敗した倍はnull
     */
    public static UDate getUDate2(String yyyy, String mm, String dd) {
        if (StringUtil.isNullZeroString(yyyy)
            || StringUtil.isNullZeroString(mm)
            || StringUtil.isNullZeroString(dd)) {
            return null;
        }
        if (!ValidateUtil.isNumber(yyyy)
            || !ValidateUtil.isNumber(mm)
            || !ValidateUtil.isNumber(dd)) {
            return null;
        }

        String year = StringUtil.toDecFormat(yyyy, "0000");
        String mon = StringUtil.toDecFormat(mm, "00");
        String day = StringUtil.toDecFormat(dd, "00");
        return UDate.getInstanceStr(year + mon + day);
    }

    /**
     * <br>[機  能] UDateをYY年MM月DD日形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY年MM月DD日形式の文字列
     * @param req リクエスト
     */
    public static String getYmdJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String yymmdd = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear() % 100), "00");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");

        yymmdd = gsMsg.getMessage(req, "cmn.year", year)
               + month
               + gsMsg.getMessage(req, "cmn.month")
               + day
               + gsMsg.getMessage(req, "cmn.day");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをYYYY年MM月DD日形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param req リクエスト
     * @return YYYY年MM月DD日形式の文字列
     */
    public static String getYymdJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String yymmdd = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear()), "0000");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");
        yymmdd = gsMsg.getMessage(req, "cmn.year", year)
        + month
        + gsMsg.getMessage(req, "cmn.month")
        + day
        + gsMsg.getMessage(req, "cmn.day");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをYYYY年MM月DD日形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param reqMdl リクエスト情報
     * @return YYYY年MM月DD日形式の文字列
     */
    public static String getYymdJ(UDate date, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        String yymmdd = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear()), "0000");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");
        yymmdd = gsMsg.getMessage("cmn.year", new String[]{year})
        + month
        + gsMsg.getMessage("cmn.month")
        + day
        + gsMsg.getMessage("cmn.day");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをYYYY年MM月DD日形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param reqMdl リクエスト情報
     * @return YYYY年MM月DD日(曜日)形式の文字列
     */
    public static String getYymdJwithStrWeek(UDate date, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        String yymmdd = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear()), "0000");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");
        yymmdd = gsMsg.getMessage("cmn.year", new String[]{year})
        + month
        + gsMsg.getMessage("cmn.month")
        + day
        + gsMsg.getMessage("cmn.day")
        + "("
        + date.getStrWeekJ(reqMdl)
        + ")";

        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをYYYY年MM月形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param req リクエスト
     * @return YYYY年MM月形式の文字列
     */
    public static String getYymJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String yymm = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear()), "0000");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        yymm = gsMsg.getMessage(req, "cmn.year", year)
        + month
        + gsMsg.getMessage(req, "cmn.month");
        return yymm;
    }

    /**
     * <br>[機  能] UDateをYYYY年MM月形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param reqMdl リクエスト情報
     * @return YYYY年MM月形式の文字列
     */
    public static String getYymJ(UDate date, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        String yymm = null;
        String year =
            StringUtil.toDecFormat(String.valueOf(date.getYear()), "0000");
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        yymm = gsMsg.getMessage("cmn.year", new String[] {year})
        + month
        + gsMsg.getMessage("cmn.month");
        return yymm;
    }

    /**
     * <br>[機  能] UDateをMM月DD日形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date UDate
     * param req リクエスト
     * @param req リクエスト
     * @return MM月DD日形式の文字列
     */
    public static String getMdJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String mmdd = null;
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");
        mmdd = month
        + gsMsg.getMessage(req, "cmn.month")
        + day
        + gsMsg.getMessage(req, "cmn.day");;
        return mmdd;
    }

    /**
     * <br>[機  能] UDateをMM月DD日(曜)形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param date UDate
     * @return MM月DD日形式の文字列
     * @param req リクエスト
     */
    public static String getMdJw(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String mmdd = null;
        String month =
            StringUtil.toDecFormat(String.valueOf(date.getMonth()), "00");
        String day =
            StringUtil.toDecFormat(String.valueOf(date.getIntDay()), "00");
        //曜日
        String wk = date.getStrWeekJ(req);

        mmdd = month + gsMsg.getMessage(req, "cmn.month")
             + day + gsMsg.getMessage(req, "cmn.day") + "(" + wk + ")";
        return mmdd;
    }


    /**
     * <br>[機  能] UDateをYYMMDD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY.MM.DD形式の文字列
     */
    public static String getYYMD(UDate date) {
        return getSeparateYYMD(date, "");
    }

    /**
     * <br>[機  能] UDateをYY.MM.DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY.MM.DD形式の文字列
     */
    public static String getDotYMD(UDate date) {
        return getSeparateYMD(date, ".");
    }

    /**
     * <br>[機  能] UDateをYY/MM/DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getSlashYMD(UDate date) {
        return getSeparateYMD(date, "/");
    }

    /**
     * <br>[機  能] UDateをYY/MM/DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getSeparateYMD(UDate date) {
        return getSeparateYMD(date, "");
    }

    /**
     * <br>[機  能] UDateをYYYY.MM.DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY.MM.DD形式の文字列
     */
    public static String getDotYYMD(UDate date) {
        return getSeparateYYMD(date, ".");
    }

    /**
     * <br>[機  能] UDateをYYYY/MM/DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getSlashYYMD(UDate date) {
        return getSeparateYYMD(date, "/");
    }

    /**
     * <br>[機  能] UDateをMM/DD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return YY/MM/DD形式の文字列
     */
    public static String getSlashMD(UDate date) {
        return getSeparateMD(date, "/");
    }

    /**
     * <br>[機  能] UDateを区切り文字の付いたYYMMDD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param sep 区切り文字
     * @return 区切り文字の付いたYYMMDD形式の文字列
     */
    private static String getSeparateYMD(UDate date, String sep) {
        if (date == null) {
            return "";
        }
        String pattern = "yy" + sep + "MM" + sep + "dd";
        SimpleDateFormat dtFmt = new SimpleDateFormat(pattern);
        return dtFmt.format(date.getSQLDate());
    }

    /**
     * <br>[機  能] UDateを区切り文字の付いたYYYYMMDD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param sep 区切り文字
     * @return 区切り文字の付いたYYMMDD形式の文字列
     */
    private static String getSeparateYYMD(UDate date, String sep) {
        if (date == null) {
            return "";
        }
        String pattern = "yyyy" + sep + "MM" + sep + "dd";
        SimpleDateFormat dtFmt = new SimpleDateFormat(pattern);
        return dtFmt.format(date.getSQLDate());
    }

    /**
     * <br>[機  能] UDateを区切り文字の付いたMMDD形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param sep 区切り文字
     * @return 区切り文字の付いたMMDD形式の文字列
     */
    private static String getSeparateMD(UDate date, String sep) {
        if (date == null) {
            return "";
        }
        String pattern = "MM" + sep + "dd";
        SimpleDateFormat dtFmt = new SimpleDateFormat(pattern);
        return dtFmt.format(date.getSQLDate());
    }

    /**
     * <br>[機  能] UDateをhh:mm:ss形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return hh:mm:ss形式の文字列
     */
    public static String getSeparateHMS(UDate date) {
        String sep = ":";
        if (date == null) {
            return "";
        }
        String pattern = "HH" + sep + "mm" + sep + "ss";
        SimpleDateFormat dtFmt = new SimpleDateFormat(pattern);
        return dtFmt.format(date.getSQLDate());
    }

    /**
     * <br>[機  能] UDateをhh時mm分ss秒形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param req リクエスト
     * @return hh:mm:ss形式の文字列
     */
    public static String getSeparateHMSJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String yymmdd = null;
        String hour =
            StringUtil.toDecFormat(String.valueOf(date.getIntHour()), "00");
        String min =
            StringUtil.toDecFormat(String.valueOf(date.getIntMinute()), "00");
        String sec =
            StringUtil.toDecFormat(String.valueOf(date.getIntSecond()), "00");
        yymmdd = hour
               + gsMsg.getMessage(req, "cmn.hour")
               + min
               + gsMsg.getMessage(req, "cmn.minute")
               + sec
               + gsMsg.getMessage(req, "cmn.second");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをhh時mm分形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param req リクエスト
     * @return hh時mm分形式の文字列
     */
    public static String getSeparateHMJ(UDate date, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        String yymmdd = null;
        String hour =
            StringUtil.toDecFormat(String.valueOf(date.getIntHour()), "00");
        String min =
            StringUtil.toDecFormat(String.valueOf(date.getIntMinute()), "00");
        yymmdd = hour
               + gsMsg.getMessage(req, "cmn.hour")
               + min + gsMsg.getMessage(req, "cmn.minute");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをhh時mm分形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param reqMdl リクエスト情報
     * @return hh時mm分形式の文字列
     */
    public static String getSeparateHMJ(UDate date, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        String yymmdd = null;
        String hour =
            StringUtil.toDecFormat(String.valueOf(date.getIntHour()), "00");
        String min =
            StringUtil.toDecFormat(String.valueOf(date.getIntMinute()), "00");
        yymmdd = hour
               + gsMsg.getMessage("cmn.hour")
               + min + gsMsg.getMessage("cmn.minute");
        return yymmdd;
    }

    /**
     * <br>[機  能] UDateをhh:mm形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return hh:mm:ss形式の文字列
     */
    public static String getSeparateHM(UDate date) {
        String sep = ":";
        if (date == null) {
            return "";
        }
        String pattern = "HH" + sep + "mm";
        SimpleDateFormat dtFmt = new SimpleDateFormat(pattern);
        return dtFmt.format(date.getSQLDate());
    }

    /**
     * <br>[機  能] UDateを画面表示形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @param reqMdl リクエスト情報
     * @return hh時mm分形式の文字列
     */
    public static String getSeparateHM(UDate date, RequestModel reqMdl) {

        String[] params = {StringUtil.toDecFormat(String.valueOf(date.getIntHour()), "00"),
                StringUtil.toDecFormat(String.valueOf(date.getIntMinute()), "00")};
        GsMessage gsMsg = new GsMessage(reqMdl);
        return gsMsg.getMessage("cmn.time.input", params);
    }

    /**
     * <br>[機  能] UDateを受取り、月の第１日の曜日を返却する
     * <br>[解  説]
     * <br>[備  考]
     * @param date UDate
     * @return int 曜日(Calender定数値)
     */
    public static int getFirstDayOfWeekOfMonth(UDate date) {

        int year = date.getYear();
        int month = date.getMonth();

        UDate dateAfter = new UDate();
        dateAfter.setYear(year);
        dateAfter.setMonth(month);
        dateAfter.setDay(1);

        return dateAfter.getWeek();
    }

    /**
     * <br>[機  能] UDateの曜日定数から曜日文字を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param week UDateの曜日定数
     * @param reqMdl リクエスト情報
     * @return String 曜日
     */
    public static String getStrWeek(int week, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
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
     * <br>[機  能] 年齢を取得する
     * <br>[解  説]
     * <br>[備  考] システム日付を基準日とする
     * @param birthday 生年月日
     * @return 年齢
     */
    public static int getAge(final UDate birthday) {
        return getAge(birthday, new UDate());
    }

    /**
     * <br>[機  能] 年齢を取得する
     * <br>[解  説]
     * <br>[備  考] 誕生日、基準日が不正な場合は-1を返す
     * @param birthday 生年月日
     * @param date 基準日
     * @return 年齢
     */
    public static int getAge(final UDate birthday, final UDate date) {

        if (birthday == null || date == null || birthday.compareDateYMD(date) == UDate.SMALL) {
            return -1;
        }

        int age = date.getYear() - birthday.getYear();

        if (age == 0 || date.getMonth() > birthday.getMonth()) {
            return age;
        }

        if (date.getMonth() < birthday.getMonth()) {
            age--;
        } else if (date.getIntDay() < birthday.getIntDay()) {
            age--;
        }

        return age;
    }

}