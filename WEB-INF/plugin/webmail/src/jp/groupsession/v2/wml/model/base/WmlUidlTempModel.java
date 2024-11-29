package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_UIDL_TEMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlUidlTempModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WUD_UID mapping */
    private String wudUid__;

    /**
     * <p>Default Constructor
     */
    public WmlUidlTempModel() {
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
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wudUid__, ""));
        return buf.toString();
    }

}
