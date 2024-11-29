package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_FILTER_FWADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterFwaddressModel implements Serializable {

    /** WFT_SID mapping */
    private int wftSid__;
    /** WFA_NO mapping */
    private int wfaNo__;
    /** WFA_ADDRESS mapping */
    private String wfaAddress__;

    /**
     * <p>Default Constructor
     */
    public WmlFilterFwaddressModel() {
    }

    /**
     * <p>get WFT_SID value
     * @return WFT_SID value
     */
    public int getWftSid() {
        return wftSid__;
    }

    /**
     * <p>set WFT_SID value
     * @param wftSid WFT_SID value
     */
    public void setWftSid(int wftSid) {
        wftSid__ = wftSid;
    }

    /**
     * <p>get WFA_NO value
     * @return WFA_NO value
     */
    public int getWfaNo() {
        return wfaNo__;
    }

    /**
     * <p>set WFA_NO value
     * @param wfaNo WFA_NO value
     */
    public void setWfaNo(int wfaNo) {
        wfaNo__ = wfaNo;
    }

    /**
     * <p>get WFA_ADDRESS value
     * @return WFA_ADDRESS value
     */
    public String getWfaAddress() {
        return wfaAddress__;
    }

    /**
     * <p>set WFA_ADDRESS value
     * @param wfaAddress WFA_ADDRESS value
     */
    public void setWfaAddress(String wfaAddress) {
        wfaAddress__ = wfaAddress;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wftSid__);
        buf.append(",");
        buf.append(wfaNo__);
        buf.append(",");
        buf.append(NullDefault.getString(wfaAddress__, ""));
        return buf.toString();
    }

}
