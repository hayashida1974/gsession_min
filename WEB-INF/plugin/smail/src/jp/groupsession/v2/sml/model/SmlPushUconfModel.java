package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_PUSH_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlPushUconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SAC_SID mapping */
    private int sacSid__;
    /** SPU_PUSHUSE mapping */
    private int spuPushuse__;

    /**
     * <p>Default Constructor
     */
    public SmlPushUconfModel() {
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
     * <p>get SPU_PUSHUSE value
     * @return SPU_PUSHUSE value
     */
    public int getSpuPushuse() {
        return spuPushuse__;
    }

    /**
     * <p>set SPU_PUSHUSE value
     * @param spuPushuse SPU_PUSHUSE value
     */
    public void setSpuPushuse(int spuPushuse) {
        spuPushuse__ = spuPushuse;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(sacSid__);
        buf.append(",");
        buf.append(spuPushuse__);
        return buf.toString();
    }

}
