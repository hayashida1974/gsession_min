package jp.groupsession.v2.rng.model;



import java.io.Serializable;

/**
 * <p>RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplatecategoryUseModel implements Serializable {

    /** RTC_SID mapping */
    private int rtcSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;

    /**
     * <p>Default Constructor
     */
    public RngTemplatecategoryUseModel() {
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
        return buf.toString();
    }

}
