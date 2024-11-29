package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_DAIRI_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngDairiUserModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** USR_SID_DAIRI mapping */
    private int usrSidDairi__;
    /** RDU_START mapping */
    private UDate rduStart__;
    /** RDU_END mapping */
    private UDate rduEnd__;

    /**
     * <p>Default Constructor
     */
    public RngDairiUserModel() {
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
     * <p>get USR_SID_DAIRI value
     * @return USR_SID_DAIRI value
     */
    public int getUsrSidDairi() {
        return usrSidDairi__;
    }

    /**
     * <p>set USR_SID_DAIRI value
     * @param usrSidDairi USR_SID_DAIRI value
     */
    public void setUsrSidDairi(int usrSidDairi) {
        usrSidDairi__ = usrSidDairi;
    }

    /**
     * <p>get RDU_START value
     * @return RDU_START value
     */
    public UDate getRduStart() {
        return rduStart__;
    }

    /**
     * <p>set RDU_START value
     * @param rduStart RDU_START value
     */
    public void setRduStart(UDate rduStart) {
        rduStart__ = rduStart;
    }

    /**
     * <p>get RDU_END value
     * @return RDU_END value
     */
    public UDate getRduEnd() {
        return rduEnd__;
    }

    /**
     * <p>set RDU_END value
     * @param rduEnd RDU_END value
     */
    public void setRduEnd(UDate rduEnd) {
        rduEnd__ = rduEnd;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(usrSidDairi__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rduStart__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rduEnd__, ""));
        return buf.toString();
    }

}
