package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_BAN_DEST_PERMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestPermitModel implements Serializable {

    /** SBC_SID mapping */
    private int sbcSid__;
    /** SBP_TARGET_SID mapping */
    private int sbpTargetSid__;
    /** SBP_TARGET_KBN mapping */
    private int sbpTargetKbn__;

    /**
     * <p>Default Constructor
     */
    public SmlBanDestPermitModel() {
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
     * <p>get SBP_TARGET_SID value
     * @return SBP_TARGET_SID value
     */
    public int getSbpTargetSid() {
        return sbpTargetSid__;
    }

    /**
     * <p>set SBP_TARGET_SID value
     * @param sbpTargetSid SBP_TARGET_SID value
     */
    public void setSbpTargetSid(int sbpTargetSid) {
        sbpTargetSid__ = sbpTargetSid;
    }

    /**
     * <p>get SBP_TARGET_KBN value
     * @return SBP_TARGET_KBN value
     */
    public int getSbpTargetKbn() {
        return sbpTargetKbn__;
    }

    /**
     * <p>set SBP_TARGET_KBN value
     * @param sbpTargetKbn SBP_TARGET_KBN value
     */
    public void setSbpTargetKbn(int sbpTargetKbn) {
        sbpTargetKbn__ = sbpTargetKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sbcSid__);
        buf.append(",");
        buf.append(sbpTargetSid__);
        buf.append(",");
        buf.append(sbpTargetKbn__);
        return buf.toString();
    }

}
