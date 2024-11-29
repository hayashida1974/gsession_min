package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

/**
 * <p>BBS_SOUKOU_BODY_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsSoukouBodyBinModel implements Serializable {

    /** BSK_SID mapping */
    private int bskSid__;
    /** BSB_FILE_SID mapping */
    private int bsbFileSid__;
    /** BIN_SID mapping */
    private long binSid__;

    /**
     * <p>Default Constructor
     */
    public BbsSoukouBodyBinModel() {
    }

    /**
     * <p>get BSK_SID value
     * @return BSK_SID value
     */
    public int getBskSid() {
        return bskSid__;
    }

    /**
     * <p>set BSK_SID value
     * @param bskSid BSK_SID value
     */
    public void setBskSid(int bskSid) {
        bskSid__ = bskSid;
    }

    /**
     * <p>get BSB_FILE_SID value
     * @return BSB_FILE_SID value
     */
    public int getBsbFileSid() {
        return bsbFileSid__;
    }

    /**
     * <p>set BSB_FILE_SID value
     * @param bsbFileSid BSB_FILE_SID value
     */
    public void setBsbFileSid(int bsbFileSid) {
        bsbFileSid__ = bsbFileSid;
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
        buf.append(bskSid__);
        buf.append(",");
        buf.append(bsbFileSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
