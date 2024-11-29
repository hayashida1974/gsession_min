package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

/**
 * <p>CMN_INFO_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoBinModel implements Serializable {

    /** IMS_SID mapping */
    private int imsSid__;
    /** BIN_SID mapping */
    private Long binSid__;

    /**
     * <p>Default Constructor
     */
    public CmnInfoBinModel() {
    }

    /**
     * <p>get IMS_SID value
     * @return IMS_SID value
     */
    public int getImsSid() {
        return imsSid__;
    }

    /**
     * <p>set IMS_SID value
     * @param imsSid IMS_SID value
     */
    public void setImsSid(int imsSid) {
        imsSid__ = imsSid;
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
        buf.append(imsSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
