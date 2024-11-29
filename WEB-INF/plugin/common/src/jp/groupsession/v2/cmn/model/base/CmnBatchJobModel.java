package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_BATCHJOB Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBatchJobModel implements Serializable {

    /** BAT_FR_DATE mapping */
    private int batFrDate__;
    /** BAT_ADUSER mapping */
    private int batAduser__;
    /** BAT_ADDATE mapping */
    private UDate batAddate__;
    /** BAT_UPUSER mapping */
    private int batUpuser__;
    /** BAT_UPDATE mapping */
    private UDate batUpdate__;

    /**
     * <p>Default Constructor
     */
    public CmnBatchJobModel() {
    }

    /**
     * <p>get BAT_FR_DATE value
     * @return BAT_FR_DATE value
     */
    public int getBatFrDate() {
        return batFrDate__;
    }

    /**
     * <p>set BAT_FR_DATE value
     * @param batFrDate BAT_FR_DATE value
     */
    public void setBatFrDate(int batFrDate) {
        batFrDate__ = batFrDate;
    }

    /**
     * <p>get BAT_ADUSER value
     * @return BAT_ADUSER value
     */
    public int getBatAduser() {
        return batAduser__;
    }

    /**
     * <p>set BAT_ADUSER value
     * @param batAduser BAT_ADUSER value
     */
    public void setBatAduser(int batAduser) {
        batAduser__ = batAduser;
    }

    /**
     * <p>get BAT_ADDATE value
     * @return BAT_ADDATE value
     */
    public UDate getBatAddate() {
        return batAddate__;
    }

    /**
     * <p>set BAT_ADDATE value
     * @param batAddate BAT_ADDATE value
     */
    public void setBatAddate(UDate batAddate) {
        batAddate__ = batAddate;
    }

    /**
     * <p>get BAT_UPUSER value
     * @return BAT_UPUSER value
     */
    public int getBatUpuser() {
        return batUpuser__;
    }

    /**
     * <p>set BAT_UPUSER value
     * @param batUpuser BAT_UPUSER value
     */
    public void setBatUpuser(int batUpuser) {
        batUpuser__ = batUpuser;
    }

    /**
     * <p>get BAT_UPDATE value
     * @return BAT_UPDATE value
     */
    public UDate getBatUpdate() {
        return batUpdate__;
    }

    /**
     * <p>set BAT_UPDATE value
     * @param batUpdate BAT_UPDATE value
     */
    public void setBatUpdate(UDate batUpdate) {
        batUpdate__ = batUpdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(batFrDate__);
        buf.append(",");
        buf.append(batAduser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(batAddate__, ""));
        buf.append(",");
        buf.append(batUpuser__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(batUpdate__, ""));
        return buf.toString();
    }

}
