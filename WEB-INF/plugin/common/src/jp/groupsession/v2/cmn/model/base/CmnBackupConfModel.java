package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_BACKUP_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBackupConfModel implements Serializable {

    /** BUC_INTERVAL mapping */
    private int bucInterval__;
    /** BUC_DOW mapping */
    private int bucDow__;
    /** BUC_WEEK_MONTH mapping */
    private int bucWeekMonth__;
    /** BUC_GENERATION mapping */
    private int bucGeneration__;
    /** BUC_ZIPOUT mapping */
    private int bucZipout__;
    /** BUC_AUID mapping */
    private int bucAuid__;
    /** BUC_ADATE mapping */
    private UDate bucAdate__;
    /** BUC_EUID mapping */
    private int bucEuid__;
    /** BUC_EDATE mapping */
    private UDate bucEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnBackupConfModel() {
    }

    /**
     * <p>get BUC_INTERVAL value
     * @return BUC_INTERVAL value
     */
    public int getBucInterval() {
        return bucInterval__;
    }

    /**
     * <p>set BUC_INTERVAL value
     * @param bucInterval BUC_INTERVAL value
     */
    public void setBucInterval(int bucInterval) {
        bucInterval__ = bucInterval;
    }

    /**
     * <p>get BUC_DOW value
     * @return BUC_DOW value
     */
    public int getBucDow() {
        return bucDow__;
    }

    /**
     * <p>set BUC_DOW value
     * @param bucDow BUC_DOW value
     */
    public void setBucDow(int bucDow) {
        bucDow__ = bucDow;
    }

    /**
     * <p>get BUC_WEEK_MONTH value
     * @return BUC_WEEK_MONTH value
     */
    public int getBucWeekMonth() {
        return bucWeekMonth__;
    }

    /**
     * <p>set BUC_WEEK_MONTH value
     * @param bucWeekMonth BUC_WEEK_MONTH value
     */
    public void setBucWeekMonth(int bucWeekMonth) {
        bucWeekMonth__ = bucWeekMonth;
    }

    /**
     * <p>get BUC_GENERATION value
     * @return BUC_GENERATION value
     */
    public int getBucGeneration() {
        return bucGeneration__;
    }

    /**
     * <p>set BUC_GENERATION value
     * @param bucGeneration BUC_GENERATION value
     */
    public void setBucGeneration(int bucGeneration) {
        bucGeneration__ = bucGeneration;
    }

    /**
     * <p>get BUC_AUID value
     * @return BUC_AUID value
     */
    public int getBucAuid() {
        return bucAuid__;
    }

    /**
     * <p>set BUC_AUID value
     * @param bucAuid BUC_AUID value
     */
    public void setBucAuid(int bucAuid) {
        bucAuid__ = bucAuid;
    }

    /**
     * <p>get BUC_ADATE value
     * @return BUC_ADATE value
     */
    public UDate getBucAdate() {
        return bucAdate__;
    }

    /**
     * <p>set BUC_ADATE value
     * @param bucAdate BUC_ADATE value
     */
    public void setBucAdate(UDate bucAdate) {
        bucAdate__ = bucAdate;
    }

    /**
     * <p>get BUC_EUID value
     * @return BUC_EUID value
     */
    public int getBucEuid() {
        return bucEuid__;
    }

    /**
     * <p>set BUC_EUID value
     * @param bucEuid BUC_EUID value
     */
    public void setBucEuid(int bucEuid) {
        bucEuid__ = bucEuid;
    }

    /**
     * <p>get BUC_EDATE value
     * @return BUC_EDATE value
     */
    public UDate getBucEdate() {
        return bucEdate__;
    }

    /**
     * <p>set BUC_EDATE value
     * @param bucEdate BUC_EDATE value
     */
    public void setBucEdate(UDate bucEdate) {
        bucEdate__ = bucEdate;
    }

    /**
     * <p>get BUC_ZIPOUT value
     * @return BUC_ZIPOUT value
     */
    public int getBucZipout() {
        return bucZipout__;
    }
    /**
     * <p>set BUC_ZIPOUT value
     * @param bucZipout BUC_ZIPOUT value
     */
    public void setBucZipout(int bucZipout) {
        bucZipout__ = bucZipout;
    }
    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(bucInterval__);
        buf.append(",");
        buf.append(bucDow__);
        buf.append(",");
        buf.append(bucWeekMonth__);
        buf.append(",");
        buf.append(bucGeneration__);
        buf.append(",");
        buf.append(bucAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bucAdate__, ""));
        buf.append(",");
        buf.append(bucEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bucEdate__, ""));
        return buf.toString();
    }

}
