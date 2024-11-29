package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_ACCOUNT_RCVDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvdataModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WRD_RECEIVE_DATE mapping */
    private UDate wrdReceiveDate__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvdataModel() {
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
     * <p>get WRD_RECEIVE_DATE value
     * @return WRD_RECEIVE_DATE value
     */
    public UDate getWrdReceiveDate() {
        return wrdReceiveDate__;
    }

    /**
     * <p>set WRD_RECEIVE_DATE value
     * @param wrdReceiveDate WRD_RECEIVE_DATE value
     */
    public void setWrdReceiveDate(UDate wrdReceiveDate) {
        wrdReceiveDate__ = wrdReceiveDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wrdReceiveDate__, ""));
        return buf.toString();
    }

}
