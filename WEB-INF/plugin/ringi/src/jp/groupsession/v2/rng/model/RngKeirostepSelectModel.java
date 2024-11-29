package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_KEIROSTEP_SELECT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngKeirostepSelectModel implements Serializable {

    /** RKS_SID mapping */
    private int rksSid__;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** GRP_SID mapping */
    private int grpSid__ = -1;
    /** POS_SID mapping */
    private int posSid__ = -1;

    /**
     * <p>Default Constructor
     */
    public RngKeirostepSelectModel() {
    }

    /**
     * <p>get RKS_SID value
     * @return RKS_SID value
     */
    public int getRksSid() {
        return rksSid__;
    }

    /**
     * <p>set RKS_SID value
     * @param rksSid RKS_SID value
     */
    public void setRksSid(int rksSid) {
        rksSid__ = rksSid;
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
     * <p>get POS_SID value
     * @return POS_SID value
     */
    public int getPosSid() {
        return posSid__;
    }

    /**
     * <p>set POS_SID value
     * @param posSid POS_SID value
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rksSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(posSid__);
        return buf.toString();
    }

}
