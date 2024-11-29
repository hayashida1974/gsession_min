package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;

/**
 * <p>CIR_ACCOUNT_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtCirAccountUserModel implements Serializable {

    /** CAC_SID mapping */
    private int cacSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public CvtCirAccountUserModel() {
    }

    /**
     * <p>get CAC_SID value
     * @return CAC_SID value
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>set CAC_SID value
     * @param cacSid CAC_SID value
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
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
        buf.append(cacSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
