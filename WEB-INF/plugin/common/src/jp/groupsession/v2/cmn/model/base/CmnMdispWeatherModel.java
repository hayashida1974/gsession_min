package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MDISP_WEATHER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMdispWeatherModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MDW_AREA mapping */
    private int mdwArea__;
    /** MDW_SORT mapping */
    private int mdwSort__;
    /** MDW_AUID mapping */
    private int mdwAuid__;
    /** MDW_ADATE mapping */
    private UDate mdwAdate__;
    /** MDW_EUID mapping */
    private int mdwEuid__;
    /** MDW_EDATE mapping */
    private UDate mdwEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMdispWeatherModel() {
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
     * <p>get MDW_AREA value
     * @return MDW_AREA value
     */
    public int getMdwArea() {
        return mdwArea__;
    }

    /**
     * <p>set MDW_AREA value
     * @param mdwArea MDW_AREA value
     */
    public void setMdwArea(int mdwArea) {
        mdwArea__ = mdwArea;
    }

    /**
     * <p>get MDW_SORT value
     * @return MDW_SORT value
     */
    public int getMdwSort() {
        return mdwSort__;
    }

    /**
     * <p>set MDW_SORT value
     * @param mdwSort MDW_SORT value
     */
    public void setMdwSort(int mdwSort) {
        mdwSort__ = mdwSort;
    }

    /**
     * <p>get MDW_AUID value
     * @return MDW_AUID value
     */
    public int getMdwAuid() {
        return mdwAuid__;
    }

    /**
     * <p>set MDW_AUID value
     * @param mdwAuid MDW_AUID value
     */
    public void setMdwAuid(int mdwAuid) {
        mdwAuid__ = mdwAuid;
    }

    /**
     * <p>get MDW_ADATE value
     * @return MDW_ADATE value
     */
    public UDate getMdwAdate() {
        return mdwAdate__;
    }

    /**
     * <p>set MDW_ADATE value
     * @param mdwAdate MDW_ADATE value
     */
    public void setMdwAdate(UDate mdwAdate) {
        mdwAdate__ = mdwAdate;
    }

    /**
     * <p>get MDW_EUID value
     * @return MDW_EUID value
     */
    public int getMdwEuid() {
        return mdwEuid__;
    }

    /**
     * <p>set MDW_EUID value
     * @param mdwEuid MDW_EUID value
     */
    public void setMdwEuid(int mdwEuid) {
        mdwEuid__ = mdwEuid;
    }

    /**
     * <p>get MDW_EDATE value
     * @return MDW_EDATE value
     */
    public UDate getMdwEdate() {
        return mdwEdate__;
    }

    /**
     * <p>set MDW_EDATE value
     * @param mdwEdate MDW_EDATE value
     */
    public void setMdwEdate(UDate mdwEdate) {
        mdwEdate__ = mdwEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mdwArea__);
        buf.append(",");
        buf.append(mdwSort__);
        buf.append(",");
        buf.append(mdwAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mdwAdate__, ""));
        buf.append(",");
        buf.append(mdwEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mdwEdate__, ""));
        return buf.toString();
    }

}
