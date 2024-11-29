package jp.groupsession.v2.rng.rng170;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040ParamModel;

/**
 * <br>[機  能] 稟議 手動データ削除画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng170ParamModel extends Rng040ParamModel {
    /** 申請中 手動削除区分 */
    private int rng170pendingKbn__ = RngConst.MANU_DEL_NO;
    /** 申請中 手動削除 年 */
    private int rng170pendingYear__ = RngConst.YEAR_THREE;
    /** 申請中 手動削除 月 */
    private int rng170pendingMonth__ = RngConst.DEL_MONTH_START;
    /** 申請中 手動削除 日 */
    private int rng170pendingDay__ = RngConst.DEL_MONTH_START;
    /** 完了 手動削除区分 */
    private int rng170completeKbn__ = RngConst.RAD_KBN_NO;
    /** 完了 手動削除 年 */
    private int rng170completeYear__ = RngConst.YEAR_THREE;
    /** 完了 手動削除 月 */
    private int rng170completeMonth__ = RngConst.DEL_MONTH_START;
    /** 完了 手動削除 日 */
    private int rng170completeDay__ = RngConst.DEL_MONTH_START;
    /** 草稿 手動削除区分 */
    private int rng170draftKbn__ = RngConst.RAD_KBN_NO;
    /** 草稿 手動削除 年 */
    private int rng170draftYear__ = RngConst.YEAR_THREE;
    /** 草稿 手動削除 月 */
    private int rng170draftMonth__ = RngConst.DEL_MONTH_START;
    /** 草稿 手動削除 日 */
    private int rng170draftDay__ = RngConst.DEL_MONTH_START;
    /** 年コンボ */
    private List<LabelValueBean> yearLabelList__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabelList__ = null;
    /** 日コンボ */
    private List<LabelValueBean> dayLabelList__ = null;
    /**
     * <p>rng170pendingKbn を取得します。
     * @return rng170pendingKbn
     */
    public int getRng170pendingKbn() {
        return rng170pendingKbn__;
    }
    /**
     * <p>rng170pendingKbn をセットします。
     * @param rng170pendingKbn rng170pendingKbn
     */
    public void setRng170pendingKbn(int rng170pendingKbn) {
        rng170pendingKbn__ = rng170pendingKbn;
    }
    /**
     * <p>rng170pendingYear を取得します。
     * @return rng170pendingYear
     */
    public int getRng170pendingYear() {
        return rng170pendingYear__;
    }
    /**
     * <p>rng170pendingYear をセットします。
     * @param rng170pendingYear rng170pendingYear
     */
    public void setRng170pendingYear(int rng170pendingYear) {
        rng170pendingYear__ = rng170pendingYear;
    }
    /**
     * <p>rng170pendingMonth を取得します。
     * @return rng170pendingMonth
     */
    public int getRng170pendingMonth() {
        return rng170pendingMonth__;
    }
    /**
     * <p>rng170pendingMonth をセットします。
     * @param rng170pendingMonth rng170pendingMonth
     */
    public void setRng170pendingMonth(int rng170pendingMonth) {
        rng170pendingMonth__ = rng170pendingMonth;
    }
    /**
     * <p>rng170pendingDay を取得します。
     * @return rng170pendingDay
     */
    public int getRng170pendingDay() {
        return rng170pendingDay__;
    }
    /**
     * <p>rng170pendingDay をセットします。
     * @param rng170pendingDay rng170pendingDay
     */
    public void setRng170pendingDay(int rng170pendingDay) {
        rng170pendingDay__ = rng170pendingDay;
    }
    /**
     * <p>rng170completeKbn を取得します。
     * @return rng170completeKbn
     */
    public int getRng170completeKbn() {
        return rng170completeKbn__;
    }
    /**
     * <p>rng170completeKbn をセットします。
     * @param rng170completeKbn rng170completeKbn
     */
    public void setRng170completeKbn(int rng170completeKbn) {
        rng170completeKbn__ = rng170completeKbn;
    }
    /**
     * <p>rng170completeYear を取得します。
     * @return rng170completeYear
     */
    public int getRng170completeYear() {
        return rng170completeYear__;
    }
    /**
     * <p>rng170completeYear をセットします。
     * @param rng170completeYear rng170completeYear
     */
    public void setRng170completeYear(int rng170completeYear) {
        rng170completeYear__ = rng170completeYear;
    }
    /**
     * <p>rng170completeMonth を取得します。
     * @return rng170completeMonth
     */
    public int getRng170completeMonth() {
        return rng170completeMonth__;
    }
    /**
     * <p>rng170completeMonth をセットします。
     * @param rng170completeMonth rng170completeMonth
     */
    public void setRng170completeMonth(int rng170completeMonth) {
        rng170completeMonth__ = rng170completeMonth;
    }
    /**
     * <p>rng170completeDay を取得します。
     * @return rng170completeDay
     */
    public int getRng170completeDay() {
        return rng170completeDay__;
    }
    /**
     * <p>rng170completeDay をセットします。
     * @param rng170completeDay rng170completeDay
     */
    public void setRng170completeDay(int rng170completeDay) {
        rng170completeDay__ = rng170completeDay;
    }
    /**
     * <p>rng170draftKbn を取得します。
     * @return rng170draftKbn
     */
    public int getRng170draftKbn() {
        return rng170draftKbn__;
    }
    /**
     * <p>rng170draftKbn をセットします。
     * @param rng170draftKbn rng170draftKbn
     */
    public void setRng170draftKbn(int rng170draftKbn) {
        rng170draftKbn__ = rng170draftKbn;
    }
    /**
     * <p>rng170draftYear を取得します。
     * @return rng170draftYear
     */
    public int getRng170draftYear() {
        return rng170draftYear__;
    }
    /**
     * <p>rng170draftYear をセットします。
     * @param rng170draftYear rng170draftYear
     */
    public void setRng170draftYear(int rng170draftYear) {
        rng170draftYear__ = rng170draftYear;
    }
    /**
     * <p>rng170draftMonth を取得します。
     * @return rng170draftMonth
     */
    public int getRng170draftMonth() {
        return rng170draftMonth__;
    }
    /**
     * <p>rng170draftMonth をセットします。
     * @param rng170draftMonth rng170draftMonth
     */
    public void setRng170draftMonth(int rng170draftMonth) {
        rng170draftMonth__ = rng170draftMonth;
    }
    /**
     * <p>rng170draftDay を取得します。
     * @return rng170draftDay
     */
    public int getRng170draftDay() {
        return rng170draftDay__;
    }
    /**
     * <p>rng170draftDay をセットします。
     * @param rng170draftDay rng170draftDay
     */
    public void setRng170draftDay(int rng170draftDay) {
        rng170draftDay__ = rng170draftDay;
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