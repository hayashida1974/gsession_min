package jp.groupsession.v2.rng.rng160;

import java.util.List;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 稟議 自動削除設定画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng160ParamModel extends Rng040ParamModel {
    /** 初期表示区分 */
    private String rng160initFlg__ = String.valueOf(RngConst.DSP_FIRST);
    /** 申請中 自動削除区分 */
    private int rng160pendingKbn__ = RngConst.RAD_KBN_NO;
    /** 申請中 自動削除 年 */
    private int rng160pendingYear__ = RngConst.YEAR_ZERO;
    /** 申請中 自動削除 月 */
    private int rng160pendingMonth__ = RngConst.DEL_MONTH_START;
    /** 申請中 自動削除 日 */
    private int rng160pendingDay__ = RngConst.DEL_MONTH_START;
    /** 完了 自動削除区分 */
    private int rng160completeKbn__ = RngConst.RAD_KBN_NO;
    /** 完了 自動削除 年 */
    private int rng160completeYear__ = RngConst.YEAR_ZERO;
    /** 完了 自動削除 月 */
    private int rng160completeMonth__ = RngConst.DEL_MONTH_START;
    /** 完了 自動削除 日 */
    private int rng160completeDay__ = RngConst.DEL_MONTH_START;
    /** 草稿 自動削除区分 */
    private int rng160draftKbn__ = RngConst.RAD_KBN_NO;
    /** 草稿 自動削除 年 */
    private int rng160draftYear__ = RngConst.YEAR_ZERO;
    /** 草稿 自動削除 月 */
    private int rng160draftMonth__ = RngConst.DEL_MONTH_START;
    /** 草稿 自動削除 日 */
    private int rng160draftDay__ = RngConst.DEL_MONTH_START;
    /** 年コンボ */
    private List<LabelValueBean> yearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabelList__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabelList__ = null;
    /**
     * <p>rng160initFlg を取得します。
     * @return rng160initFlg
     */
    public String getRng160initFlg() {
        return rng160initFlg__;
    }
    /**
     * <p>rng160initFlg をセットします。
     * @param rng160initFlg rng160initFlg
     */
    public void setRng160initFlg(String rng160initFlg) {
        rng160initFlg__ = rng160initFlg;
    }
    /**
     * <p>rng160pendingKbn を取得します。
     * @return rng160pendingKbn
     */
    public int getRng160pendingKbn() {
        return rng160pendingKbn__;
    }
    /**
     * <p>rng160pendingKbn をセットします。
     * @param rng160pendingKbn rng160pendingKbn
     */
    public void setRng160pendingKbn(int rng160pendingKbn) {
        rng160pendingKbn__ = rng160pendingKbn;
    }
    /**
     * <p>rng160pendingYear を取得します。
     * @return rng160pendingYear
     */
    public int getRng160pendingYear() {
        return rng160pendingYear__;
    }
    /**
     * <p>rng160pendingYear をセットします。
     * @param rng160pendingYear rng160pendingYear
     */
    public void setRng160pendingYear(int rng160pendingYear) {
        rng160pendingYear__ = rng160pendingYear;
    }
    /**
     * <p>rng160pendingMonth を取得します。
     * @return rng160pendingMonth
     */
    public int getRng160pendingMonth() {
        return rng160pendingMonth__;
    }
    /**
     * <p>rng160pendingMonth をセットします。
     * @param rng160pendingMonth rng160pendingMonth
     */
    public void setRng160pendingMonth(int rng160pendingMonth) {
        rng160pendingMonth__ = rng160pendingMonth;
    }
    /**
     * <p>rng160pendingDay を取得します。
     * @return rng160pendingDay
     */
    public int getRng160pendingDay() {
        return rng160pendingDay__;
    }
    /**
     * <p>rng160pendingDay をセットします。
     * @param rng160pendingDay rng160pendingDay
     */
    public void setRng160pendingDay(int rng160pendingDay) {
        rng160pendingDay__ = rng160pendingDay;
    }
    /**
     * <p>rng160completeKbn を取得します。
     * @return rng160completeKbn
     */
    public int getRng160completeKbn() {
        return rng160completeKbn__;
    }
    /**
     * <p>rng160completeKbn をセットします。
     * @param rng160completeKbn rng160completeKbn
     */
    public void setRng160completeKbn(int rng160completeKbn) {
        rng160completeKbn__ = rng160completeKbn;
    }
    /**
     * <p>rng160completeYear を取得します。
     * @return rng160completeYear
     */
    public int getRng160completeYear() {
        return rng160completeYear__;
    }
    /**
     * <p>rng160completeYear をセットします。
     * @param rng160completeYear rng160completeYear
     */
    public void setRng160completeYear(int rng160completeYear) {
        rng160completeYear__ = rng160completeYear;
    }
    /**
     * <p>rng160completeMonth を取得します。
     * @return rng160completeMonth
     */
    public int getRng160completeMonth() {
        return rng160completeMonth__;
    }
    /**
     * <p>rng160completeMonth をセットします。
     * @param rng160completeMonth rng160completeMonth
     */
    public void setRng160completeMonth(int rng160completeMonth) {
        rng160completeMonth__ = rng160completeMonth;
    }
    /**
     * <p>rng160completeDay を取得します。
     * @return rng160completeDay
     */
    public int getRng160completeDay() {
        return rng160completeDay__;
    }
    /**
     * <p>rng160completeDay をセットします。
     * @param rng160completeDay rng160completeDay
     */
    public void setRng160completeDay(int rng160completeDay) {
        rng160completeDay__ = rng160completeDay;
    }
    /**
     * <p>rng160draftKbn を取得します。
     * @return rng160draftKbn
     */
    public int getRng160draftKbn() {
        return rng160draftKbn__;
    }
    /**
     * <p>rng160draftKbn をセットします。
     * @param rng160draftKbn rng160draftKbn
     */
    public void setRng160draftKbn(int rng160draftKbn) {
        rng160draftKbn__ = rng160draftKbn;
    }
    /**
     * <p>rng160draftYear を取得します。
     * @return rng160draftYear
     */
    public int getRng160draftYear() {
        return rng160draftYear__;
    }
    /**
     * <p>rng160draftYear をセットします。
     * @param rng160draftYear rng160draftYear
     */
    public void setRng160draftYear(int rng160draftYear) {
        rng160draftYear__ = rng160draftYear;
    }
    /**
     * <p>rng160draftMonth を取得します。
     * @return rng160draftMonth
     */
    public int getRng160draftMonth() {
        return rng160draftMonth__;
    }
    /**
     * <p>rng160draftMonth をセットします。
     * @param rng160draftMonth rng160draftMonth
     */
    public void setRng160draftMonth(int rng160draftMonth) {
        rng160draftMonth__ = rng160draftMonth;
    }
    /**
     * <p>rng160draftDay を取得します。
     * @return rng160draftDay
     */
    public int getRng160draftDay() {
        return rng160draftDay__;
    }
    /**
     * <p>rng160draftDay をセットします。
     * @param rng160draftDay rng160draftDay
     */
    public void setRng160draftDay(int rng160draftDay) {
        rng160draftDay__ = rng160draftDay;
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
}