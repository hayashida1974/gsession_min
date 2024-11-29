package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_ACCOUNT_DISK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlAccountDiskModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WDS_SIZE mapping */
    private long wdsSize__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountDiskModel() {
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WDS_SIZE value
     * @return WDS_SIZE value
     */
    public long getWdsSize() {
        return wdsSize__;
    }

    /**
     * <p>set WDS_SIZE value
     * @param wdsSize WDS_SIZE value
     */
    public void setWdsSize(long wdsSize) {
        wdsSize__ = wdsSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wdsSize__);
        return buf.toString();
    }

}
