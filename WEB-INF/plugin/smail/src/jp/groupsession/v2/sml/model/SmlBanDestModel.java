package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_BAN_DEST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestModel implements Serializable {

    /** SBC_SID mapping */
    private int sbcSid__;
    /** SBD_TARGET_SID mapping */
    private int sbdTargetSid__;
    /** SBD_TARGET_KBN mapping */
    private int sbdTargetKbn__;

    /**
     * <p>Default Constructor
     */
    public SmlBanDestModel() {
    }

    /**
     * <p>get SBC_SID value
     * @return SBC_SID value
     */
    public int getSbcSid() {
        return sbcSid__;
    }

    /**
     * <p>set SBC_SID value
     * @param sbcSid SBC_SID value
     */
    public void setSbcSid(int sbcSid) {
        sbcSid__ = sbcSid;
    }

    /**
     * <p>get SBD_TARGET_SID value
     * @return SBD_TARGET_SID value
     */
    public int getSbdTargetSid() {
        return sbdTargetSid__;
    }

    /**
     * <p>set SBD_TARGET_SID value
     * @param sbdTargetSid SBD_TARGET_SID value
     */
    public void setSbdTargetSid(int sbdTargetSid) {
        sbdTargetSid__ = sbdTargetSid;
    }

    /**
     * <p>get SBD_TARGET_KBN value
     * @return SBD_TARGET_KBN value
     */
    public int getSbdTargetKbn() {
        return sbdTargetKbn__;
    }

    /**
     * <p>set SBD_TARGET_KBN value
     * @param sbdTargetKbn SBD_TARGET_KBN value
     */
    public void setSbdTargetKbn(int sbdTargetKbn) {
        sbdTargetKbn__ = sbdTargetKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sbcSid__);
        buf.append(",");
        buf.append(sbdTargetSid__);
        buf.append(",");
        buf.append(sbdTargetKbn__);
        return buf.toString();
    }

}
