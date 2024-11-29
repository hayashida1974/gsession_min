package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_UIDL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlUidlModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WUD_ACCOUNT mapping */
    private String wudAccount__;
    /** WUD_UID mapping */
    private String wudUid__;

    /**
     * <p>Default Constructor
     */
    public WmlUidlModel() {
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
     * <p>get WUD_ACCOUNT value
     * @return WUD_ACCOUNT value
     */
    public String getWudAccount() {
        return wudAccount__;
    }

    /**
     * <p>set WUD_ACCOUNT value
     * @param wudAccount WUD_ACCOUNT value
     */
    public void setWudAccount(String wudAccount) {
        wudAccount__ = wudAccount;
    }

    /**
     * <p>get WUD_UID value
     * @return WUD_UID value
     */
    public String getWudUid() {
        return wudUid__;
    }

    /**
     * <p>set WUD_UID value
     * @param wudUid WUD_UID value
     */
    public void setWudUid(String wudUid) {
        wudUid__ = wudUid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wudAccount__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wudUid__, ""));
        return buf.toString();
    }

}
