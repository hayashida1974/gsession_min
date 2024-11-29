package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_BODY_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlBodyBinModel implements Serializable {

    /** SML_SID mapping */
    private int smlSid__;
    /** SBB_SID mapping */
    private int sbbSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public SmlBodyBinModel() {
    }

    /**
     * <p>get SML_SID value
     * @return SML_SID value
     */
    public int getSmlSid() {
        return smlSid__;
    }

    /**
     * <p>set SML_SID value
     * @param smlSid SML_SID value
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }
    
    /**
     * <p>get SBB_SID value
     * @return SBB_SID value
     */
    public int getSbbSid() {
        return sbbSid__;
    }

    /**
     * <p>set SBB_SID value
     * @param sbbSid SBB_SID value
     */
    public void setSbbSid(int sbbSid) {
        sbbSid__ = sbbSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(smlSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
