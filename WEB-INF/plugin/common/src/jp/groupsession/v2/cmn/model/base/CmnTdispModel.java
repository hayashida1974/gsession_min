package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_TDISP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnTdispModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** TDP_PID mapping */
    private String tdpPid__;
    /** TDP_ORDER mapping */
    private int tdpOrder__;
    /** TDP_AUID mapping */
    private int tdpAuid__;
    /** TDP_ADATE mapping */
    private UDate tdpAdate__;
    /** TDP_EUID mapping */
    private int tdpEuid__;
    /** TDP_EDATE mapping */
    private UDate tdpEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnTdispModel() {
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
     * <p>get TDP_PID value
     * @return TDP_PID value
     */
    public String getTdpPid() {
        return tdpPid__;
    }

    /**
     * <p>set TDP_PID value
     * @param tdpPid TDP_PID value
     */
    public void setTdpPid(String tdpPid) {
        tdpPid__ = tdpPid;
    }

    /**
     * <p>get TDP_ORDER value
     * @return TDP_ORDER value
     */
    public int getTdpOrder() {
        return tdpOrder__;
    }

    /**
     * <p>set TDP_ORDER value
     * @param tdpOrder TDP_ORDER value
     */
    public void setTdpOrder(int tdpOrder) {
        tdpOrder__ = tdpOrder;
    }

    /**
     * <p>get TDP_AUID value
     * @return TDP_AUID value
     */
    public int getTdpAuid() {
        return tdpAuid__;
    }

    /**
     * <p>set TDP_AUID value
     * @param tdpAuid TDP_AUID value
     */
    public void setTdpAuid(int tdpAuid) {
        tdpAuid__ = tdpAuid;
    }

    /**
     * <p>get TDP_ADATE value
     * @return TDP_ADATE value
     */
    public UDate getTdpAdate() {
        return tdpAdate__;
    }

    /**
     * <p>set TDP_ADATE value
     * @param tdpAdate TDP_ADATE value
     */
    public void setTdpAdate(UDate tdpAdate) {
        tdpAdate__ = tdpAdate;
    }

    /**
     * <p>get TDP_EUID value
     * @return TDP_EUID value
     */
    public int getTdpEuid() {
        return tdpEuid__;
    }

    /**
     * <p>set TDP_EUID value
     * @param tdpEuid TDP_EUID value
     */
    public void setTdpEuid(int tdpEuid) {
        tdpEuid__ = tdpEuid;
    }

    /**
     * <p>get TDP_EDATE value
     * @return TDP_EDATE value
     */
    public UDate getTdpEdate() {
        return tdpEdate__;
    }

    /**
     * <p>set TDP_EDATE value
     * @param tdpEdate TDP_EDATE value
     */
    public void setTdpEdate(UDate tdpEdate) {
        tdpEdate__ = tdpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(tdpPid__, ""));
        buf.append(",");
        buf.append(tdpOrder__);
        buf.append(",");
        buf.append(tdpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tdpAdate__, ""));
        buf.append(",");
        buf.append(tdpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tdpEdate__, ""));
        return buf.toString();
    }

}
