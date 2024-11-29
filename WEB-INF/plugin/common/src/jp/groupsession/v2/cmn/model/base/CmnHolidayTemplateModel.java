package jp.groupsession.v2.cmn.model.base;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnHolidayTemplateModel implements Serializable {

    /** HLT_EXFLG 通常 */
    public static final int HLT_EXFLG_NORMAL = 0;
    /** HLT_EXFLG 拡張 */
    public static final int HLT_EXFLG_KAKUCHO = 1;

    /** HLT_SID mapping */
    private int hltSid__;
    /** HLT_DATE_MONTH mapping */
    private int hltDateMonth__;
    /** HLT_DATE_DAY mapping */
    private int hltDateDay__;
    /** HLT_NAME mapping */
    private String hltName__;
    /** HLT_EX_MONTH mapping */
    private int hltExMonth__;
    /** HLT_EX_WEEK_MONTH mapping */
    private int hltExWeekMonth__;
    /** HLT_EX_DAY_WEEK mapping */
    private int hltExDayWeek__;
    /** HLT_EX_FURIKAE mapping */
    private int hltExFurikae__;
    /** HLT_EXFLG mapping */
    private int hltExflg__;
    /** HLT_ADUSER mapping */
    private int hltAduser__;
    /** HLT_ADDATE mapping */
    private UDate hltAddate__;
    /** HLT_UPUSER mapping */
    private int hltUpuser__;
    /** HLT_UPDATE mapping */
    private UDate hltUpdate__;

    /**
     * <p>Default Constructor
     */
    public CmnHolidayTemplateModel() {
    }

    /**
     * <p>get HLT_SID value
     * @return HLT_SID value
     */
    public int getHltSid() {
        return hltSid__;
    }

    /**
     * <p>set HLT_SID value
     * @param hltSid HLT_SID value
     */
    public void setHltSid(int hltSid) {
        hltSid__ = hltSid;
    }

    /**
     * <p>get HLT_DATE_MONTH value
     * @return HLT_DATE_MONTH value
     */
    public int getHltDateMonth() {
        return hltDateMonth__;
    }

    /**
     * <p>set HLT_DATE_MONTH value
     * @param hltDateMonth HLT_DATE_MONTH value
     */
    public void setHltDateMonth(int hltDateMonth) {
        hltDateMonth__ = hltDateMonth;
    }

    /**
     * <p>get HLT_DATE_DAY value
     * @return HLT_DATE_DAY value
     */
    public int getHltDateDay() {
        return hltDateDay__;
    }

    /**
     * <p>set HLT_DATE_DAY value
     * @param hltDateDay HLT_DATE_DAY value
     */
    public void setHltDateDay(int hltDateDay) {
        hltDateDay__ = hltDateDay;
    }

    /**
     * <p>get HLT_NAME value
     * @return HLT_NAME value
     */
    public String getHltName() {
        return hltName__;
    }

    /**
     * <p>set HLT_NAME value
     * @param hltName HLT_NAME value
     */
    public void setHltName(String hltName) {
        hltName__ = hltName;
    }

    /**
     * <p>get HLT_EX_MONTH value
     * @return HLT_EX_MONTH value
     */
    public int getHltExMonth() {
        return hltExMonth__;
    }

    /**
     * <p>set HLT_EX_MONTH value
     * @param hltExMonth HLT_EX_MONTH value
     */
    public void setHltExMonth(int hltExMonth) {
        hltExMonth__ = hltExMonth;
    }

    /**
     * <p>get HLT_EX_WEEK_MONTH value
     * @return HLT_EX_WEEK_MONTH value
     */
    public int getHltExWeekMonth() {
        return hltExWeekMonth__;
    }

    /**
     * <p>set HLT_EX_WEEK_MONTH value
     * @param hltExWeekMonth HLT_EX_WEEK_MONTH value
     */
    public void setHltExWeekMonth(int hltExWeekMonth) {
        hltExWeekMonth__ = hltExWeekMonth;
    }

    /**
     * <p>get HLT_EX_DAY_WEEK value
     * @return HLT_EX_DAY_WEEK value
     */
    public int getHltExDayWeek() {
        return hltExDayWeek__;
    }

    /**
     * <p>set HLT_EX_DAY_WEEK value
     * @param hltExDayWeek HLT_EX_DAY_WEEK value
     */
    public void setHltExDayWeek(int hltExDayWeek) {
        hltExDayWeek__ = hltExDayWeek;
    }

    /**
     * <p>get HLT_EX_FURIKAE value
     * @return HLT_EX_FURIKAE value
     */
    public int getHltExFurikae() {
        return hltExFurikae__;
    }

    /**
     * <p>set HLT_EX_FURIKAE value
     * @param hltExFurikae HLT_EX_FURIKAE value
     */
    public void setHltExFurikae(int hltExFurikae) {
        hltExFurikae__ = hltExFurikae;
    }

    /**
     * <p>get HLT_EXFLG value
     * @return HLT_EXFLG value
     */
    public int getHltExflg() {
        return hltExflg__;
    }

    /**
     * <p>set HLT_EXFLG value
     * @param hltExflg HLT_EXFLG value
     */
    public void setHltExflg(int hltExflg) {
        hltExflg__ = hltExflg;
    }

    /**
     * <p>get HLT_ADUSER value
     * @return HLT_ADUSER value
     */
    public int getHltAduser() {
        return hltAduser__;
    }

    /**
     * <p>set HLT_ADUSER value
     * @param hltAduser HLT_ADUSER value
     */
    public void setHltAduser(int hltAduser) {
        hltAduser__ = hltAduser;
    }

    /**
     * <p>get HLT_ADDATE value
     * @return HLT_ADDATE value
     */
    public UDate getHltAddate() {
        return hltAddate__;
    }

    /**
     * <p>set HLT_ADDATE value
     * @param hltAddate HLT_ADDATE value
     */
    public void setHltAddate(UDate hltAddate) {
        hltAddate__ = hltAddate;
    }

    /**
     * <p>get HLT_UPUSER value
     * @return HLT_UPUSER value
     */
    public int getHltUpuser() {
        return hltUpuser__;
    }

    /**
     * <p>set HLT_UPUSER value
     * @param hltUpuser HLT_UPUSER value
     */
    public void setHltUpuser(int hltUpuser) {
        hltUpuser__ = hltUpuser;
    }

    /**
     * <p>get HLT_UPDATE value
     * @return HLT_UPDATE value
     */
    public UDate getHltUpdate() {
        return hltUpdate__;
    }

    /**
     * <p>set HLT_UPDATE value
     * @param hltUpdate HLT_UPDATE value
     */
    public void setHltUpdate(UDate hltUpdate) {
        hltUpdate__ = hltUpdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(hltSid__);
        buf.append(",");
        buf.append(hltDateMonth__);
        buf.append(",");
        buf.append(hltDateDay__);
        buf.append(",");
        buf.append(NullDefault.getString(hltName__, ""));
        buf.append(",");
        buf.append(hltExMonth__);
        buf.append(",");
        buf.append(hltExWeekMonth__);
        buf.append(",");
        buf.append(hltExDayWeek__);
        buf.append(",");
        buf.append(hltExFurikae__);
        buf.append(",");
        buf.append(hltExflg__);
        buf.append(",");
        buf.append(hltAduser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(hltAddate__, ""));
        buf.append(",");
        buf.append(hltUpuser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(hltUpdate__, ""));
        return buf.toString();
    }

}
