package jp.groupsession.v2.bbs.model;


import java.io.Serializable;

/**
 * <p>BBS_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsBinModel implements Serializable {

    /** BWI_SID mapping */
    private int bwiSid__;
    /** BIN_SID mapping */
    private Long binSid__;

    /**
     * <p>Default Constructor
     */
    public BbsBinModel() {
    }

    /**
     * <p>get BWI_SID value
     * @return BWI_SID value
     */
    public int getBwiSid() {
        return bwiSid__;
    }

    /**
     * <p>set BWI_SID value
     * @param bwiSid BWI_SID value
     */
    public void setBwiSid(int bwiSid) {
        bwiSid__ = bwiSid;
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
        buf.append(bwiSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
