package jp.groupsession.v2.convert.convert530.dao;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>TCD_TIMEZONE_INFO Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdTimezoneInfoModel implements Serializable {

    /** TTI_SID mapping */
    private int ttiSid__;
    /** TTI_NAME mapping */
    private String ttiName__;
    /** TTI_RYAKU mapping */
    private String ttiRyaku__;
    /** TTI_SORT mapping */
    private int ttiSort__;
    /** TTI_USE mapping */
    private int ttiUse__;
    /** TTI_HOLIDAY mapping */
    private int ttiHoliday__;
    /** TTI_AUID mapping */
    private int ttiAuid__;
    /** TTI_ADATE mapping */
    private UDate ttiAdate__;
    /** TTI_EUID mapping */
    private int ttiEuid__;
    /** TTI_EDATE mapping */
    private UDate ttiEdate__;

    /**
     * <p>Default Constructor
     */
    public TcdTimezoneInfoModel() {
    }

    /**
     * <p>get TTI_SID value
     * @return TTI_SID value
     */
    public int getTtiSid() {
        return ttiSid__;
    }

    /**
     * <p>set TTI_SID value
     * @param ttiSid TTI_SID value
     */
    public void setTtiSid(int ttiSid) {
        ttiSid__ = ttiSid;
    }

    /**
     * <p>get TTI_NAME value
     * @return TTI_NAME value
     */
    public String getTtiName() {
        return ttiName__;
    }

    /**
     * <p>set TTI_NAME value
     * @param ttiName TTI_NAME value
     */
    public void setTtiName(String ttiName) {
        ttiName__ = ttiName;
    }

    /**
     * <p>get TTI_RYAKU value
     * @return TTI_RYAKU value
     */
    public String getTtiRyaku() {
        return ttiRyaku__;
    }

    /**
     * <p>set TTI_RYAKU value
     * @param ttiRyaku TTI_RYAKU value
     */
    public void setTtiRyaku(String ttiRyaku) {
        ttiRyaku__ = ttiRyaku;
    }

    /**
     * <p>get TTI_SORT value
     * @return TTI_SORT value
     */
    public int getTtiSort() {
        return ttiSort__;
    }

    /**
     * <p>set TTI_SORT value
     * @param ttiSort TTI_SORT value
     */
    public void setTtiSort(int ttiSort) {
        ttiSort__ = ttiSort;
    }

    /**
     * <p>get TTI_USE value
     * @return TTI_USE value
     */
    public int getTtiUse() {
        return ttiUse__;
    }

    /**
     * <p>set TTI_USE value
     * @param ttiUse TTI_USE value
     */
    public void setTtiUse(int ttiUse) {
        ttiUse__ = ttiUse;
    }

    /**
     * <p>get TTI_HOLIDAY value
     * @return TTI_HOLIDAY value
     */
    public int getTtiHoliday() {
        return ttiHoliday__;
    }

    /**
     * <p>set TTI_HOLIDAY value
     * @param ttiHoliday TTI_HOLIDAY value
     */
    public void setTtiHoliday(int ttiHoliday) {
        ttiHoliday__ = ttiHoliday;
    }

    /**
     * <p>get TTI_AUID value
     * @return TTI_AUID value
     */
    public int getTtiAuid() {
        return ttiAuid__;
    }

    /**
     * <p>set TTI_AUID value
     * @param ttiAuid TTI_AUID value
     */
    public void setTtiAuid(int ttiAuid) {
        ttiAuid__ = ttiAuid;
    }

    /**
     * <p>get TTI_ADATE value
     * @return TTI_ADATE value
     */
    public UDate getTtiAdate() {
        return ttiAdate__;
    }

    /**
     * <p>set TTI_ADATE value
     * @param ttiAdate TTI_ADATE value
     */
    public void setTtiAdate(UDate ttiAdate) {
        ttiAdate__ = ttiAdate;
    }

    /**
     * <p>get TTI_EUID value
     * @return TTI_EUID value
     */
    public int getTtiEuid() {
        return ttiEuid__;
    }

    /**
     * <p>set TTI_EUID value
     * @param ttiEuid TTI_EUID value
     */
    public void setTtiEuid(int ttiEuid) {
        ttiEuid__ = ttiEuid;
    }

    /**
     * <p>get TTI_EDATE value
     * @return TTI_EDATE value
     */
    public UDate getTtiEdate() {
        return ttiEdate__;
    }

    /**
     * <p>set TTI_EDATE value
     * @param ttiEdate TTI_EDATE value
     */
    public void setTtiEdate(UDate ttiEdate) {
        ttiEdate__ = ttiEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ttiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ttiName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ttiRyaku__, ""));
        buf.append(",");
        buf.append(ttiSort__);
        buf.append(",");
        buf.append(ttiUse__);
        buf.append(",");
        buf.append(ttiHoliday__);
        buf.append(",");
        buf.append(ttiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ttiAdate__, ""));
        buf.append(",");
        buf.append(ttiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ttiEdate__, ""));
        return buf.toString();
    }

}
