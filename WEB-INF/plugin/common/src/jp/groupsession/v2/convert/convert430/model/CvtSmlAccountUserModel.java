package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;

/**
 * <p>SML_ACCOUNT_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtSmlAccountUserModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public CvtSmlAccountUserModel() {
    }

    /**
     * <p>get SAC_SID value
     * @return SAC_SID value
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>set SAC_SID value
     * @param sacSid SAC_SID value
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
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
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
