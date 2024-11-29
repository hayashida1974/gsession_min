package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_ACCOUNT_RCVLOCK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvlockModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WRL_RCVEDATE mapping */
    private UDate wrlRcvedate__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvlockModel() {
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
     * <p>get WRL_RCVEDATE value
     * @return WRL_RCVEDATE value
     */
    public UDate getWrlRcvedate() {
        return wrlRcvedate__;
    }

    /**
     * <p>set WRL_RCVEDATE value
     * @param wrlRcvedate WRL_RCVEDATE value
     */
    public void setWrlRcvedate(UDate wrlRcvedate) {
        wrlRcvedate__ = wrlRcvedate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wrlRcvedate__, ""));
        return buf.toString();
    }

}
