package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

/**
 * <p>PTL_MAIN_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlMainConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** PTL_SID mapping */
    private int ptlSid__;

    /**
     * <p>Default Constructor
     */
    public PtlMainConfModel() {
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
     * <p>get PTL_SID value
     * @return PTL_SID value
     */
    public int getPtlSid() {
        return ptlSid__;
    }

    /**
     * <p>set PTL_SID value
     * @param ptlSid PTL_SID value
     */
    public void setPtlSid(int ptlSid) {
        ptlSid__ = ptlSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ptlSid__);
        return buf.toString();
    }

}
