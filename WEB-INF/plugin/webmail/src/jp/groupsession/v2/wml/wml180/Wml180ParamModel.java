package jp.groupsession.v2.wml.wml180;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;

/**
 * <br>[機  能] WEBメール 送受信ログ手動削除画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180ParamModel extends Wml020ParamModel {
    /** 削除条件 日付 */
    public static final int DEL_KBN_DATE = 0;
    /** 削除条件 範囲 */
    public static final int DEL_KBN_DATEAREA = 1;
    /** 削除条件 全て */
    public static final int DEL_KBN_ALL = 2;

    /** 削除条件 */
    private int wml180delKbn__ = DEL_KBN_DATE;

    /** 手動削除 年 */
    private int wml180delYear__ = GSConstWebmail.YEAR_THREE;
    /** 手動削除 月 */
    private int wml180delMonth__ = GSConstWebmail.DEL_MONTH_START;
    /** 手動削除 日 */
    private int wml180delDay__ = GSConstWebmail.DEL_DAY_START;

    /** 手動削除 範囲 From 年 */
    private int wml180delYearFr__ = GSConstWebmail.YEAR_ZERO;
    /** 手動削除 範囲 From 月 */
    private int wml180delMonthFr__ = GSConstWebmail.DEL_MONTH_START;
    /** 手動削除 範囲 From 日 */
    private int wml180delDayFr__ = GSConstWebmail.DEL_DAY_START;
    /** 手動削除 範囲 To 年 */
    private int wml180delYearTo__ = GSConstWebmail.YEAR_ZERO;
    /** 手動削除 範囲 To 月 */
    private int wml180delMonthTo__ = GSConstWebmail.DEL_MONTH_START;
    /** 手動削除 範囲 To 日 */
    private int wml180delDayTo__ = GSConstWebmail.DEL_DAY_START;

    /** 年コンボ */
    private List<LabelValueBean> yearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabelList__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabelList__ = null;

    /** 年コンボ 範囲 */
    private List<LabelValueBean> dateAreaYearLabelList__ = null;
    /** 月コンボ 範囲 */
    private List<LabelValueBean> dateAreaMonthLabelList__ = null;
    /** 日コンボ 範囲 */
    private List<LabelValueBean> dateAreaDayLabelList__ = null;

    /**
     * <p>dayLabelList を取得します。
     * @return dayLabelList
     */
    public List<LabelValueBean> getDayLabelList() {
        return dayLabelList__;
    }
    /**
     * <p>dayLabelList をセットします。
     * @param dayLabelList dayLabelList
     */
    public void setDayLabelList(List<LabelValueBean> dayLabelList) {
        dayLabelList__ = dayLabelList;
    }
    /**
     * <p>monthLabelList を取得します。
     * @return monthLabelList
     */
    public List<LabelValueBean> getMonthLabelList() {
        return monthLabelList__;
    }
    /**
     * <p>monthLabelList をセットします。
     * @param monthLabelList monthLabelList
     */
    public void setMonthLabelList(List<LabelValueBean> monthLabelList) {
        monthLabelList__ = monthLabelList;
    }
    /**
     * <p>wml180delDay を取得します。
     * @return wml180delDay
     */
    public int getWml180delDay() {
        return wml180delDay__;
    }
    /**
     * <p>wml180delDay をセットします。
     * @param wml180delDay wml180delDay
     */
    public void setWml180delDay(int wml180delDay) {
        wml180delDay__ = wml180delDay;
    }
    /**
     * <p>yearLabelList を取得します。
     * @return yearLabelList
     */
    public List<LabelValueBean> getYearLabelList() {
        return yearLabelList__;
    }
    /**
     * <p>yearLabelList をセットします。
     * @param yearLabelList yearLabelList
     */
    public void setYearLabelList(List<LabelValueBean> yearLabelList) {
        yearLabelList__ = yearLabelList;
    }
    /**
     * <p>wml180delMonth を取得します。
     * @return wml180delMonth
     */
    public int getWml180delMonth() {
        return wml180delMonth__;
    }
    /**
     * <p>wml180delMonth をセットします。
     * @param wml180delMonth wml180delMonth
     */
    public void setWml180delMonth(int wml180delMonth) {
        wml180delMonth__ = wml180delMonth;
    }
    /**
     * <p>wml180delYear を取得します。
     * @return wml180delYear
     */
    public int getWml180delYear() {
        return wml180delYear__;
    }
    /**
     * <p>wml180delYear をセットします。
     * @param wml180delYear wml180delYear
     */
    public void setWml180delYear(int wml180delYear) {
        wml180delYear__ = wml180delYear;
    }
    /**
     * <p>dateAreaDayLabelList を取得します。
     * @return dateAreaDayLabelList
     */
    public List<LabelValueBean> getDateAreaDayLabelList() {
        return dateAreaDayLabelList__;
    }
    /**
     * <p>dateAreaDayLabelList をセットします。
     * @param dateAreaDayLabelList dateAreaDayLabelList
     */
    public void setDateAreaDayLabelList(List<LabelValueBean> dateAreaDayLabelList) {
        dateAreaDayLabelList__ = dateAreaDayLabelList;
    }
    /**
     * <p>dateAreaMonthLabelList を取得します。
     * @return dateAreaMonthLabelList
     */
    public List<LabelValueBean> getDateAreaMonthLabelList() {
        return dateAreaMonthLabelList__;
    }
    /**
     * <p>dateAreaMonthLabelList をセットします。
     * @param dateAreaMonthLabelList dateAreaMonthLabelList
     */
    public void setDateAreaMonthLabelList(
            List<LabelValueBean> dateAreaMonthLabelList) {
        dateAreaMonthLabelList__ = dateAreaMonthLabelList;
    }
    /**
     * <p>dateAreaYearLabelList を取得します。
     * @return dateAreaYearLabelList
     */
    public List<LabelValueBean> getDateAreaYearLabelList() {
        return dateAreaYearLabelList__;
    }
    /**
     * <p>dateAreaYearLabelList をセットします。
     * @param dateAreaYearLabelList dateAreaYearLabelList
     */
    public void setDateAreaYearLabelList(List<LabelValueBean> dateAreaYearLabelList) {
        dateAreaYearLabelList__ = dateAreaYearLabelList;
    }
    /**
     * <p>wml180delDayFr を取得します。
     * @return wml180delDayFr
     */
    public int getWml180delDayFr() {
        return wml180delDayFr__;
    }
    /**
     * <p>wml180delDayFr をセットします。
     * @param wml180delDayFr wml180delDayFr
     */
    public void setWml180delDayFr(int wml180delDayFr) {
        wml180delDayFr__ = wml180delDayFr;
    }
    /**
     * <p>wml180delDayTo を取得します。
     * @return wml180delDayTo
     */
    public int getWml180delDayTo() {
        return wml180delDayTo__;
    }
    /**
     * <p>wml180delDayTo をセットします。
     * @param wml180delDayTo wml180delDayTo
     */
    public void setWml180delDayTo(int wml180delDayTo) {
        wml180delDayTo__ = wml180delDayTo;
    }
    /**
     * <p>wml180delKbn を取得します。
     * @return wml180delKbn
     */
    public int getWml180delKbn() {
        return wml180delKbn__;
    }
    /**
     * <p>wml180delKbn をセットします。
     * @param wml180delKbn wml180delKbn
     */
    public void setWml180delKbn(int wml180delKbn) {
        wml180delKbn__ = wml180delKbn;
    }
    /**
     * <p>wml180delMonthFr を取得します。
     * @return wml180delMonthFr
     */
    public int getWml180delMonthFr() {
        return wml180delMonthFr__;
    }
    /**
     * <p>wml180delMonthFr をセットします。
     * @param wml180delMonthFr wml180delMonthFr
     */
    public void setWml180delMonthFr(int wml180delMonthFr) {
        wml180delMonthFr__ = wml180delMonthFr;
    }
    /**
     * <p>wml180delMonthTo を取得します。
     * @return wml180delMonthTo
     */
    public int getWml180delMonthTo() {
        return wml180delMonthTo__;
    }
    /**
     * <p>wml180delMonthTo をセットします。
     * @param wml180delMonthTo wml180delMonthTo
     */
    public void setWml180delMonthTo(int wml180delMonthTo) {
        wml180delMonthTo__ = wml180delMonthTo;
    }
    /**
     * <p>wml180delYearFr を取得します。
     * @return wml180delYearFr
     */
    public int getWml180delYearFr() {
        return wml180delYearFr__;
    }
    /**
     * <p>wml180delYearFr をセットします。
     * @param wml180delYearFr wml180delYearFr
     */
    public void setWml180delYearFr(int wml180delYearFr) {
        wml180delYearFr__ = wml180delYearFr;
    }
    /**
     * <p>wml180delYearTo を取得します。
     * @return wml180delYearTo
     */
    public int getWml180delYearTo() {
        return wml180delYearTo__;
    }
    /**
     * <p>wml180delYearTo をセットします。
     * @param wml180delYearTo wml180delYearTo
     */
    public void setWml180delYearTo(int wml180delYearTo) {
        wml180delYearTo__ = wml180delYearTo;
    }
}