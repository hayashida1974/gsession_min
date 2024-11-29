package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

/**
 * <p>PTL_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlUconfModel implements Serializable {

    /** USR_SIED mapping */
    private int usrSid__;
    /** PUC_DEF_TYPE mapping */
    private int pucDefType__;

    /**
     * <p>Default Constructor
     */
    public PtlUconfModel() {
    }

    /**
     * <p>get USR_SIED value
     * @return USR_SIED value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SIED value
     * @param usrSid USR_SIED value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get PUC_DEF_TYPE value
     * @return PUC_DEF_TYPE value
     */
    public int getPucDefType() {
        return pucDefType__;
    }

    /**
     * <p>set PUC_DEF_TYPE value
     * @param pucDefType PUC_DEF_TYPE value
     */
    public void setPucDefType(int pucDefType) {
        pucDefType__ = pucDefType;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(pucDefType__);
        return buf.toString();
    }

}
