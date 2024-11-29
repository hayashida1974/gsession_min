package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_MY_GROUP_SHARE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMyGroupShareModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MGP_SID mapping */
    private int mgpSid__;
    /** MGS_USR_SID mapping */
    private int mgsUsrSid__;
    /** MGS_GRP_SID mapping */
    private int mgsGrpSid__;

    /**
     * <p>Default Constructor
     */
    public CmnMyGroupShareModel() {
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
     * <p>get MGS_USR_SID value
     * @return MGS_USR_SID value
     */
    public int getMgsUsrSid() {
        return mgsUsrSid__;
    }

    /**
     * <p>set MGS_USR_SID value
     * @param mgsUsrSid MGS_USR_SID value
     */
    public void setMgsUsrSid(int mgsUsrSid) {
        mgsUsrSid__ = mgsUsrSid;
    }

    /**
     * <p>get MGS_GRP_SID value
     * @return MGS_GRP_SID value
     */
    public int getMgsGrpSid() {
        return mgsGrpSid__;
    }

    /**
     * <p>set MGS_GRP_SID value
     * @param mgsGrpSid MGS_GRP_SID value
     */
    public void setMgsGrpSid(int mgsGrpSid) {
        mgsGrpSid__ = mgsGrpSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mgpSid__);
        buf.append(",");
        buf.append(mgsUsrSid__);
        buf.append(",");
        buf.append(mgsGrpSid__);
        return buf.toString();
    }

}
