package jp.groupsession.v2.man.man290kn;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 日付指定条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290knDateSearchModel extends AbstractModel {

    /** 抽出区分 1=毎日 2=毎週　3=毎月*/
    private String dateKbn__ = null;

    /** 毎週・月　月曜日 */
    private String weekOfWeekly1__;
    /** 毎週・月　火曜日 */
    private String weekOfWeekly2__;
    /** 毎週・月　水曜日 */
    private String weekOfWeekly3__;
    /** 毎週・月　木曜日 */
    private String weekOfWeekly4__;
    /** 毎週・月　金曜日 */
    private String weekOfWeekly5__;
    /** 毎週・月　土曜日 */
    private String weekOfWeekly6__;
    /** 毎週・月　日曜日 */
    private String weekOfWeekly7__;

    /** 毎月　週 */
    private String weekOfMonthly__;
    /** 毎月　日 */
    private String dayOfMonthly__;

    /** 振替区分 0=しない 1=前営業日へ振替 2=翌営業日へ振替 */
    private String tranKbn__;
    /** 抽出期間 from */
    private UDate fromDate__ = null;
    /** 抽出期間 to */
    private UDate toDate__ = null;


    /**
     * <p>dateKbn を取得します。
     * @return dateKbn
     */
    public String getDateKbn() {
        return dateKbn__;
    }
    /**
     * <p>dateKbn をセットします。
     * @param dateKbn dateKbn
     */
    public void setDateKbn(String dateKbn) {
        dateKbn__ = dateKbn;
    }
    /**
     * <p>fromDate を取得します。
     * @return fromDate
     */
    public UDate getFromDate() {
        return fromDate__;
    }
    /**
     * <p>fromDate をセットします。
     * @param fromDate fromDate
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }
    /**
     * <p>tranKbn を取得します。
     * @return tranKbn
     */
    public String getTranKbn() {
        return tranKbn__;
    }
    /**
     * <p>tranKbn をセットします。
     * @param tranKbn tranKbn
     */
    public void setTranKbn(String tranKbn) {
        tranKbn__ = tranKbn;
    }

    /**
     * <p>dayOfMonthly を取得します。
     * @return dayOfMonthly
     */
    public String getDayOfMonthly() {
        return dayOfMonthly__;
    }
    /**
     * <p>dayOfMonthly をセットします。
     * @param dayOfMonthly dayOfMonthly
     */
    public void setDayOfMonthly(String dayOfMonthly) {
        dayOfMonthly__ = dayOfMonthly;
    }
    /**
     * <p>weekOfMonthly を取得します。
     * @return weekOfMonthly
     */
    public String getWeekOfMonthly() {
        return weekOfMonthly__;
    }
    /**
     * <p>weekOfMonthly をセットします。
     * @param weekOfMonthly weekOfMonthly
     */
    public void setWeekOfMonthly(String weekOfMonthly) {
        weekOfMonthly__ = weekOfMonthly;
    }
    /**
     * <p>weekOfWeekly1 を取得します。
     * @return weekOfWeekly1
     */
    public String getWeekOfWeekly1() {
        return weekOfWeekly1__;
    }
    /**
     * <p>weekOfWeekly1 をセットします。
     * @param weekOfWeekly1 weekOfWeekly1
     */
    public void setWeekOfWeekly1(String weekOfWeekly1) {
        weekOfWeekly1__ = weekOfWeekly1;
    }
    /**
     * <p>weekOfWeekly2 を取得します。
     * @return weekOfWeekly2
     */
    public String getWeekOfWeekly2() {
        return weekOfWeekly2__;
    }
    /**
     * <p>weekOfWeekly2 をセットします。
     * @param weekOfWeekly2 weekOfWeekly2
     */
    public void setWeekOfWeekly2(String weekOfWeekly2) {
        weekOfWeekly2__ = weekOfWeekly2;
    }
    /**
     * <p>weekOfWeekly3 を取得します。
     * @return weekOfWeekly3
     */
    public String getWeekOfWeekly3() {
        return weekOfWeekly3__;
    }
    /**
     * <p>weekOfWeekly3 をセットします。
     * @param weekOfWeekly3 weekOfWeekly3
     */
    public void setWeekOfWeekly3(String weekOfWeekly3) {
        weekOfWeekly3__ = weekOfWeekly3;
    }
    /**
     * <p>weekOfWeekly4 を取得します。
     * @return weekOfWeekly4
     */
    public String getWeekOfWeekly4() {
        return weekOfWeekly4__;
    }
    /**
     * <p>weekOfWeekly4 をセットします。
     * @param weekOfWeekly4 weekOfWeekly4
     */
    public void setWeekOfWeekly4(String weekOfWeekly4) {
        weekOfWeekly4__ = weekOfWeekly4;
    }
    /**
     * <p>weekOfWeekly5 を取得します。
     * @return weekOfWeekly5
     */
    public String getWeekOfWeekly5() {
        return weekOfWeekly5__;
    }
    /**
     * <p>weekOfWeekly5 をセットします。
     * @param weekOfWeekly5 weekOfWeekly5
     */
    public void setWeekOfWeekly5(String weekOfWeekly5) {
        weekOfWeekly5__ = weekOfWeekly5;
    }
    /**
     * <p>weekOfWeekly6 を取得します。
     * @return weekOfWeekly6
     */
    public String getWeekOfWeekly6() {
        return weekOfWeekly6__;
    }
    /**
     * <p>weekOfWeekly6 をセットします。
     * @param weekOfWeekly6 weekOfWeekly6
     */
    public void setWeekOfWeekly6(String weekOfWeekly6) {
        weekOfWeekly6__ = weekOfWeekly6;
    }
    /**
     * <p>weekOfWeekly7 を取得します。
     * @return weekOfWeekly7
     */
    public String getWeekOfWeekly7() {
        return weekOfWeekly7__;
    }
    /**
     * <p>weekOfWeekly7 をセットします。
     * @param weekOfWeekly7 weekOfWeekly7
     */
    public void setWeekOfWeekly7(String weekOfWeekly7) {
        weekOfWeekly7__ = weekOfWeekly7;
    }

}
