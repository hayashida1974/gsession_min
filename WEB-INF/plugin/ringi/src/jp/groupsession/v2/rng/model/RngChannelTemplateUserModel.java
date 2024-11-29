package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_CHANNEL_TEMPLATE_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngChannelTemplateUserModel implements Serializable {

    /** RCT_SID mapping */
    private int rctSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RCU_SORT mapping */
    private int rcuSort__;
    /** RCU_TYPE mapping */
    private int rcuType__;
    /** RCU_AUID mapping */
    private int rcuAuid__;
    /** RCU_ADATE mapping */
    private UDate rcuAdate__;
    /** RCU_EUID mapping */
    private int rcuEuid__;
    /** RCU_EDATE mapping */
    private UDate rcuEdate__;

    /**
     * <p>Default Constructor
     */
    public RngChannelTemplateUserModel() {
    }

    /**
     * <p>get RCT_SID value
     * @return RCT_SID value
     */
    public int getRctSid() {
        return rctSid__;
    }

    /**
     * <p>set RCT_SID value
     * @param rctSid RCT_SID value
     */
    public void setRctSid(int rctSid) {
        rctSid__ = rctSid;
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
     * <p>get RCU_SORT value
     * @return RCU_SORT value
     */
    public int getRcuSort() {
        return rcuSort__;
    }

    /**
     * <p>set RCU_SORT value
     * @param rcuSort RCU_SORT value
     */
    public void setRcuSort(int rcuSort) {
        rcuSort__ = rcuSort;
    }

    /**
     * <p>get RCU_TYPE value
     * @return RCU_TYPE value
     */
    public int getRcuType() {
        return rcuType__;
    }

    /**
     * <p>set RCU_TYPE value
     * @param rcuType RCU_TYPE value
     */
    public void setRcuType(int rcuType) {
        rcuType__ = rcuType;
    }

    /**
     * <p>get RCU_AUID value
     * @return RCU_AUID value
     */
    public int getRcuAuid() {
        return rcuAuid__;
    }

    /**
     * <p>set RCU_AUID value
     * @param rcuAuid RCU_AUID value
     */
    public void setRcuAuid(int rcuAuid) {
        rcuAuid__ = rcuAuid;
    }

    /**
     * <p>get RCU_ADATE value
     * @return RCU_ADATE value
     */
    public UDate getRcuAdate() {
        return rcuAdate__;
    }

    /**
     * <p>set RCU_ADATE value
     * @param rcuAdate RCU_ADATE value
     */
    public void setRcuAdate(UDate rcuAdate) {
        rcuAdate__ = rcuAdate;
    }

    /**
     * <p>get RCU_EUID value
     * @return RCU_EUID value
     */
    public int getRcuEuid() {
        return rcuEuid__;
    }

    /**
     * <p>set RCU_EUID value
     * @param rcuEuid RCU_EUID value
     */
    public void setRcuEuid(int rcuEuid) {
        rcuEuid__ = rcuEuid;
    }

    /**
     * <p>get RCU_EDATE value
     * @return RCU_EDATE value
     */
    public UDate getRcuEdate() {
        return rcuEdate__;
    }

    /**
     * <p>set RCU_EDATE value
     * @param rcuEdate RCU_EDATE value
     */
    public void setRcuEdate(UDate rcuEdate) {
        rcuEdate__ = rcuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rctSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rcuSort__);
        buf.append(",");
        buf.append(rcuType__);
        buf.append(",");
        buf.append(rcuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rcuAdate__, ""));
        buf.append(",");
        buf.append(rcuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rcuEdate__, ""));
        return buf.toString();
    }

}
