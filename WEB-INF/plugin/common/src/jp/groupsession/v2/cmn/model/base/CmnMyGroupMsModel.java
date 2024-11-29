package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_MY_GROUP_MS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMyGroupMsModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MGP_SID mapping */
    private int mgpSid__;
    /** MGM_SID mapping */
    private int mgmSid__;

    /**
     * <p>Default Constructor
     */
    public CmnMyGroupMsModel() {
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
     * <p>get MGP_SID value
     * @return MGP_SID value
     */
    public int getMgpSid() {
        return mgpSid__;
    }

    /**
     * <p>set MGP_SID value
     * @param mgpSid MGP_SID value
     */
    public void setMgpSid(int mgpSid) {
        mgpSid__ = mgpSid;
    }

    /**
     * <p>get MGM_SID value
     * @return MGM_SID value
     */
    public int getMgmSid() {
        return mgmSid__;
    }

    /**
     * <p>set MGM_SID value
     * @param mgmSid MGM_SID value
     */
    public void setMgmSid(int mgmSid) {
        mgmSid__ = mgmSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mgpSid__);
        buf.append(",");
        buf.append(mgmSid__);
        return buf.toString();
    }

}
