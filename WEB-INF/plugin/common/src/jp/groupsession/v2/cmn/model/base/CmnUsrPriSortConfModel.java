package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_USR_PRI_SORT_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnUsrPriSortConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** UPS_SORT_KEY1 mapping */
    private int upsSortKey1__;
    /** UPS_SORT_ORDER1 mapping */
    private int upsSortOrder1__;
    /** UPS_SORT_KEY2 mapping */
    private int upsSortKey2__;
    /** UPS_SORT_ORDER2 mapping */
    private int upsSortOrder2__;
    /** UPS_AUID mapping */
    private int upsAuid__;
    /** UPS_ADATE mapping */
    private UDate upsAdate__;
    /** UPS_EUID mapping */
    private int upsEuid__;
    /** UPS_EDATE mapping */
    private UDate upsEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrPriSortConfModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get UPS_SORT_KEY1 value
     * @return UPS_SORT_KEY1 value
     */
    public int getUpsSortKey1() {
        return upsSortKey1__;
    }

    /**
     * <p>set UPS_SORT_KEY1 value
     * @param upsSortKey1 UPS_SORT_KEY1 value
     */
    public void setUpsSortKey1(int upsSortKey1) {
        upsSortKey1__ = upsSortKey1;
    }

    /**
     * <p>get UPS_SORT_ORDER1 value
     * @return UPS_SORT_ORDER1 value
     */
    public int getUpsSortOrder1() {
        return upsSortOrder1__;
    }

    /**
     * <p>set UPS_SORT_ORDER1 value
     * @param upsSortOrder1 UPS_SORT_ORDER1 value
     */
    public void setUpsSortOrder1(int upsSortOrder1) {
        upsSortOrder1__ = upsSortOrder1;
    }

    /**
     * <p>get UPS_SORT_KEY2 value
     * @return UPS_SORT_KEY2 value
     */
    public int getUpsSortKey2() {
        return upsSortKey2__;
    }

    /**
     * <p>set UPS_SORT_KEY2 value
     * @param upsSortKey2 UPS_SORT_KEY2 value
     */
    public void setUpsSortKey2(int upsSortKey2) {
        upsSortKey2__ = upsSortKey2;
    }

    /**
     * <p>get UPS_SORT_ORDER2 value
     * @return UPS_SORT_ORDER2 value
     */
    public int getUpsSortOrder2() {
        return upsSortOrder2__;
    }

    /**
     * <p>set UPS_SORT_ORDER2 value
     * @param upsSortOrder2 UPS_SORT_ORDER2 value
     */
    public void setUpsSortOrder2(int upsSortOrder2) {
        upsSortOrder2__ = upsSortOrder2;
    }

    /**
     * <p>get UPS_AUID value
     * @return UPS_AUID value
     */
    public int getUpsAuid() {
        return upsAuid__;
    }

    /**
     * <p>set UPS_AUID value
     * @param upsAuid UPS_AUID value
     */
    public void setUpsAuid(int upsAuid) {
        upsAuid__ = upsAuid;
    }

    /**
     * <p>get UPS_ADATE value
     * @return UPS_ADATE value
     */
    public UDate getUpsAdate() {
        return upsAdate__;
    }

    /**
     * <p>set UPS_ADATE value
     * @param upsAdate UPS_ADATE value
     */
    public void setUpsAdate(UDate upsAdate) {
        upsAdate__ = upsAdate;
    }

    /**
     * <p>get UPS_EUID value
     * @return UPS_EUID value
     */
    public int getUpsEuid() {
        return upsEuid__;
    }

    /**
     * <p>set UPS_EUID value
     * @param upsEuid UPS_EUID value
     */
    public void setUpsEuid(int upsEuid) {
        upsEuid__ = upsEuid;
    }

    /**
     * <p>get UPS_EDATE value
     * @return UPS_EDATE value
     */
    public UDate getUpsEdate() {
        return upsEdate__;
    }

    /**
     * <p>set UPS_EDATE value
     * @param upsEdate UPS_EDATE value
     */
    public void setUpsEdate(UDate upsEdate) {
        upsEdate__ = upsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(upsSortKey1__);
        buf.append(",");
        buf.append(upsSortOrder1__);
        buf.append(",");
        buf.append(upsSortKey2__);
        buf.append(",");
        buf.append(upsSortOrder2__);
        buf.append(",");
        buf.append(upsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(upsAdate__, ""));
        buf.append(",");
        buf.append(upsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(upsEdate__, ""));
        return buf.toString();
    }

}
