package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_ACCOUNT_RCVSVR Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvsvrModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WRS_RECEIVE_COUNT mapping */
    private long wrsReceiveCount__;
    /** WRS_RECEIVE_SIZE mapping */
    private long wrsReceiveSize__;
    /** WRS_EDATE mapping */
    private UDate wrsEdate__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvsvrModel() {
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
     * <p>get WRS_RECEIVE_COUNT value
     * @return WRS_RECEIVE_COUNT value
     */
    public long getWrsReceiveCount() {
        return wrsReceiveCount__;
    }

    /**
     * <p>set WRS_RECEIVE_COUNT value
     * @param wrsReceiveCount WRS_RECEIVE_COUNT value
     */
    public void setWrsReceiveCount(long wrsReceiveCount) {
        wrsReceiveCount__ = wrsReceiveCount;
    }

    /**
     * <p>get WRS_RECEIVE_SIZE value
     * @return WRS_RECEIVE_SIZE value
     */
    public long getWrsReceiveSize() {
        return wrsReceiveSize__;
    }

    /**
     * <p>set WRS_RECEIVE_SIZE value
     * @param wrsReceiveSize WRS_RECEIVE_SIZE value
     */
    public void setWrsReceiveSize(long wrsReceiveSize) {
        wrsReceiveSize__ = wrsReceiveSize;
    }

    /**
     * <p>get WRS_EDATE value
     * @return WRS_EDATE value
     */
    public UDate getWrsEdate() {
        return wrsEdate__;
    }

    /**
     * <p>set WRS_EDATE value
     * @param wrsEdate WRS_EDATE value
     */
    public void setWrsEdate(UDate wrsEdate) {
        wrsEdate__ = wrsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wrsReceiveCount__);
        buf.append(",");
        buf.append(wrsReceiveSize__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wrsEdate__, ""));
        return buf.toString();
    }

}
