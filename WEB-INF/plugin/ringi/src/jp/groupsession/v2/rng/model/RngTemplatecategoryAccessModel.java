package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplatecategoryAccessModel implements Serializable {

    /** RTC_SID mapping */
    private int rtcSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** RTA_KBN mapping */
    private int rtaKbn__;

    /**
     * <p>Default Constructor
     */
    public RngTemplatecategoryAccessModel() {
    }

    /**
     * <p>get RTC_SID value
     * @return RTC_SID value
     */
    public int getRtcSid() {
        return rtcSid__;
    }

    /**
     * <p>set RTC_SID value
     * @param rtcSid RTC_SID value
     */
    public void setRtcSid(int rtcSid) {
        rtcSid__ = rtcSid;
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get RTA_KBN value
     * @return RTA_KBN value
     */
    public int getRtaKbn() {
        return rtaKbn__;
    }

    /**
     * <p>set RTA_KBN value
     * @param rtaKbn RTA_KBN value
     */
    public void setRtaKbn(int rtaKbn) {
        rtaKbn__ = rtaKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rtcSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(rtaKbn__);
        return buf.toString();
    }

}
