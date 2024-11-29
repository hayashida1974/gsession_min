package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBinModel implements Serializable {

    /** SML_SID mapping */
    private int smlSid__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);

    /**
     * <p>Default Constructor
     */
    public SmlBinModel() {
    }

    /**
     * <p>get CIF_SID value
     * @return CIF_SID value
     */
    public int getSmlSid() {
        return smlSid__;
    }

    /**
     * <p>set CIF_SID value
     * @param smlSid CIF_SID value
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(smlSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }
}