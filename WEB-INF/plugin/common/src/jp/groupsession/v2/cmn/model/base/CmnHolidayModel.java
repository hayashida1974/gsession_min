package jp.groupsession.v2.cmn.model.base;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_HOLIDAY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnHolidayModel implements Serializable {

    /** HOL_DATE mapping */
    private UDate holDate__;
    /** HOL_YEAR mapping */
    private int holYear__;
    /** HOL_NAME mapping */
    private String holName__;
    /** HOL_EX_MONTH mapping */
    private int holExMonth__;
    /** HOL_EX_WEEK_MONTH mapping */
    private int holExWeekMonth__;
    /** HOL_EX_DAY_WEEK mapping */
    private int holExDayWeek__;
    /** HOL_EX_FURIKAE mapping */
    private int holExFurikae__;
    /** HOL_KBN mapping */
    private int holKbn__;
    /** HOL_TCD mapping */
    private int holTcd__;
    /** HOL_ADUSER mapping */
    private int holAduser__;
    /** HOL_ADDATE mapping */
    private UDate holAddate__;
    /** HOL_UPUSER mapping */
    private int holUpuser__;
    /** HOL_UPDATE mapping */
    private UDate holUpdate__;

    /**
     * <p>holTcd を取得します。
     * @return holTcd
     */
    public int getHolTcd() {
        return holTcd__;
    }

    /**
     * <p>holTcd をセットします。
     * @param holTcd holTcd
     */
    public void setHolTcd(int holTcd) {
        holTcd__ = holTcd;
    }

    /**
     * <p>Default Constructor
     */
    public CmnHolidayModel() {
    }

    /**
     * <p>get HOL_DATE value
     * @return HOL_DATE value
     */
    public UDate getHolDate() {
        return holDate__;
    }

    /**
     * <p>set HOL_DATE value
     * @param holDate HOL_DATE value
     */
    public void setHolDate(UDate holDate) {
        holDate__ = holDate;
    }

    /**
     * <p>get HOL_YEAR value
     * @return HOL_YEAR value
     */
    public int getHolYear() {
        return holYear__;
    }

    /**
     * <p>set HOL_YEAR value
     * @param holYear HOL_YEAR value
     */
    public void setHolYear(int holYear) {
        holYear__ = holYear;
    }

    /**
     * <p>get HOL_NAME value
     * @return HOL_NAME value
     */
    public String getHolName() {
        return holName__;
    }

    /**
     * <p>set HOL_NAME value
     * @param holName HOL_NAME value
     */
    public void setHolName(String holName) {
        holName__ = holName;
    }

    /**
     * <p>get HOL_EX_MONTH value
     * @return HOL_EX_MONTH value
     */
    public int getHolExMonth() {
        return holExMonth__;
    }

    /**
     * <p>set HOL_EX_MONTH value
     * @param holExMonth HOL_EX_MONTH value
     */
    public void setHolExMonth(int holExMonth) {
        holExMonth__ = holExMonth;
    }

    /**
     * <p>get HOL_EX_WEEK_MONTH value
     * @return HOL_EX_WEEK_MONTH value
     */
    public int getHolExWeekMonth() {
        return holExWeekMonth__;
    }

    /**
     * <p>set HOL_EX_WEEK_MONTH value
     * @param holExWeekMonth HOL_EX_WEEK_MONTH value
     */
    public void setHolExWeekMonth(int holExWeekMonth) {
        holExWeekMonth__ = holExWeekMonth;
    }

    /**
     * <p>get HOL_EX_DAY_WEEK value
     * @return HOL_EX_DAY_WEEK value
     */
    public int getHolExDayWeek() {
        return holExDayWeek__;
    }

    /**
     * <p>set HOL_EX_DAY_WEEK value
     * @param holExDayWeek HOL_EX_DAY_WEEK value
     */
    public void setHolExDayWeek(int holExDayWeek) {
        holExDayWeek__ = holExDayWeek;
    }

    /**
     * <p>get HOL_EX_FURIKAE value
     * @return HOL_EX_FURIKAE value
     */
    public int getHolExFurikae() {
        return holExFurikae__;
    }

    /**
     * <p>set HOL_EX_FURIKAE value
     * @param holExFurikae HOL_EX_FURIKAE value
     */
    public void setHolExFurikae(int holExFurikae) {
        holExFurikae__ = holExFurikae;
    }

    /**
     * <p>get HOL_KBN value
     * @return HOL_KBN value
     */
    public int getHolKbn() {
        return holKbn__;
    }

    /**
     * <p>set HOL_KBN value
     * @param holKbn HOL_KBN value
     */
    public void setHolKbn(int holKbn) {
        holKbn__ = holKbn;
    }

    /**
     * <p>get HOL_ADUSER value
     * @return HOL_ADUSER value
     */
    public int getHolAduser() {
        return holAduser__;
    }

    /**
     * <p>set HOL_ADUSER value
     * @param holAduser HOL_ADUSER value
     */
    public void setHolAduser(int holAduser) {
        holAduser__ = holAduser;
    }

    /**
     * <p>get HOL_ADDATE value
     * @return HOL_ADDATE value
     */
    public UDate getHolAddate() {
        return holAddate__;
    }

    /**
     * <p>set HOL_ADDATE value
     * @param holAddate HOL_ADDATE value
     */
    public void setHolAddate(UDate holAddate) {
        holAddate__ = holAddate;
    }

    /**
     * <p>get HOL_UPUSER value
     * @return HOL_UPUSER value
     */
    public int getHolUpuser() {
        return holUpuser__;
    }

    /**
     * <p>set HOL_UPUSER value
     * @param holUpuser HOL_UPUSER value
     */
    public void setHolUpuser(int holUpuser) {
        holUpuser__ = holUpuser;
    }

    /**
     * <p>get HOL_UPDATE value
     * @return HOL_UPDATE value
     */
    public UDate getHolUpdate() {
        return holUpdate__;
    }

    /**
     * <p>set HOL_UPDATE value
     * @param holUpdate HOL_UPDATE value
     */
    public void setHolUpdate(UDate holUpdate) {
        holUpdate__ = holUpdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getStringFmObj(holDate__, ""));
        buf.append(",");
        buf.append(holYear__);
        buf.append(",");
        buf.append(NullDefault.getString(holName__, ""));
        buf.append(",");
        buf.append(holExMonth__);
        buf.append(",");
        buf.append(holExWeekMonth__);
        buf.append(",");
        buf.append(holExDayWeek__);
        buf.append(",");
        buf.append(holExFurikae__);
        buf.append(",");
        buf.append(holKbn__);
        buf.append(",");
        buf.append(holAduser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(holAddate__, ""));
        buf.append(",");
        buf.append(holUpuser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(holUpdate__, ""));
        return buf.toString();
    }

}
