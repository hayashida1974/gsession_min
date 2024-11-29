package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_TEMPLATE_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RngTemplateUserModel implements Serializable {

    /** RTP_SID mapping */
    private int rtpSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RTU_SORT mapping */
    private int rtuSort__;
    /** RTU_TYPE mapping */
    private int rtuType__;
    /** RTU_AUID mapping */
    private int rtuAuid__;
    /** RTU_ADATE mapping */
    private UDate rtuAdate__;
    /** RTU_EUID mapping */
    private int rtuEuid__;
    /** RTU_EDATE mapping */
    private UDate rtuEdate__;

    /**
     * <p>Default Constructor
     */
    public RngTemplateUserModel() {
    }

    /**
     * <p>get RTP_SID value
     * @return RTP_SID value
     */
    public int getRtpSid() {
        return rtpSid__;
    }

    /**
     * <p>set RTP_SID value
     * @param rtpSid RTP_SID value
     */
    public void setRtpSid(int rtpSid) {
        rtpSid__ = rtpSid;
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
     * <p>get RTU_SORT value
     * @return RTU_SORT value
     */
    public int getRtuSort() {
        return rtuSort__;
    }

    /**
     * <p>set RTU_SORT value
     * @param rtuSort RTU_SORT value
     */
    public void setRtuSort(int rtuSort) {
        rtuSort__ = rtuSort;
    }

    /**
     * <p>get RTU_TYPE value
     * @return RTU_TYPE value
     */
    public int getRtuType() {
        return rtuType__;
    }

    /**
     * <p>set RTU_TYPE value
     * @param rtuType RTU_TYPE value
     */
    public void setRtuType(int rtuType) {
        rtuType__ = rtuType;
    }

    /**
     * <p>get RTU_AUID value
     * @return RTU_AUID value
     */
    public int getRtuAuid() {
        return rtuAuid__;
    }

    /**
     * <p>set RTU_AUID value
     * @param rtuAuid RTU_AUID value
     */
    public void setRtuAuid(int rtuAuid) {
        rtuAuid__ = rtuAuid;
    }

    /**
     * <p>get RTU_ADATE value
     * @return RTU_ADATE value
     */
    public UDate getRtuAdate() {
        return rtuAdate__;
    }

    /**
     * <p>set RTU_ADATE value
     * @param rtuAdate RTU_ADATE value
     */
    public void setRtuAdate(UDate rtuAdate) {
        rtuAdate__ = rtuAdate;
    }

    /**
     * <p>get RTU_EUID value
     * @return RTU_EUID value
     */
    public int getRtuEuid() {
        return rtuEuid__;
    }

    /**
     * <p>set RTU_EUID value
     * @param rtuEuid RTU_EUID value
     */
    public void setRtuEuid(int rtuEuid) {
        rtuEuid__ = rtuEuid;
    }

    /**
     * <p>get RTU_EDATE value
     * @return RTU_EDATE value
     */
    public UDate getRtuEdate() {
        return rtuEdate__;
    }

    /**
     * <p>set RTU_EDATE value
     * @param rtuEdate RTU_EDATE value
     */
    public void setRtuEdate(UDate rtuEdate) {
        rtuEdate__ = rtuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rtpSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rtuSort__);
        buf.append(",");
        buf.append(rtuType__);
        buf.append(",");
        buf.append(rtuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rtuAdate__, ""));
        buf.append(",");
        buf.append(rtuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rtuEdate__, ""));
        return buf.toString();
    }

}
