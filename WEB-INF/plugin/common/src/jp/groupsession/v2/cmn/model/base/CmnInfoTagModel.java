package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_INFO_TAG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoTagModel implements Serializable {

    /** IMS_SID mapping */
    private int imsSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;

    /**
     * <p>Default Constructor
     */
    public CmnInfoTagModel() {
    }

    /**
     * <p>get IMS_SID value
     * @return IMS_SID value
     */
    public int getImsSid() {
        return imsSid__;
    }

    /**
     * <p>set IMS_SID value
     * @param imsSid IMS_SID value
     */
    public void setImsSid(int imsSid) {
        imsSid__ = imsSid;
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
        StringBuilder buf = new StringBuilder();
        buf.append(imsSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        return buf.toString();
    }

}
