package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

/**
 * <p>BBS_FOR_ADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsForAdminModel implements Serializable {

    /** BFI_SID mapping */
    private int bfiSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public BbsForAdminModel() {
    }

    /**
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
