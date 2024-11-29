package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_ACCOUNT_USER_PROXY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountUserProxyModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountUserProxyModel() {
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
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
