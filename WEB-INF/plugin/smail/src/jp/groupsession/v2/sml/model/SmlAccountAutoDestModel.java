package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_AUTO_DEST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAccountAutoDestModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SAA_TYPE mapping */
    private int saaType__;
    /** SAA_SID mapping */
    private int saaSid__;

    /**
     * <p>Default Constructor
     */
    public SmlAccountAutoDestModel() {
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
     * <p>get SAA_TYPE value
     * @return SAA_TYPE value
     */
    public int getSaaType() {
        return saaType__;
    }

    /**
     * <p>set SAA_TYPE value
     * @param sadType SAA_TYPE value
     */
    public void setSaaType(int sadType) {
        saaType__ = sadType;
    }

    /**
     * <p>get SAA_SID value
     * @return SAA_SID value
     */
    public int getSaaSid() {
        return saaSid__;
    }

    /**
     * <p>set SAA_SID value
     * @param sadSid SAA_SID value
     */
    public void setSaaSid(int sadSid) {
        saaSid__ = sadSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(saaType__);
        buf.append(",");
        buf.append(saaSid__);
        return buf.toString();
    }

}
