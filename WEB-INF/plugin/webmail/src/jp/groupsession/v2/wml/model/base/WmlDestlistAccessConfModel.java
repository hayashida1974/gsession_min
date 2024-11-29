package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistAccessConfModel implements Serializable {

    /** WDL_SID mapping */
    private int wdlSid__;
    /** WLA_KBN mapping */
    private int wlaKbn__;
    /** WLA_USR_SID mapping */
    private int wlaUsrSid__;
    /** WLA_AUTH mapping */
    private int wlaAuth__;

    /**
     * <p>Default Constructor
     */
    public WmlDestlistAccessConfModel() {
    }

    /**
     * <p>get WDL_SID value
     * @return WDL_SID value
     */
    public int getWdlSid() {
        return wdlSid__;
    }

    /**
     * <p>set WDL_SID value
     * @param wdlSid WDL_SID value
     */
    public void setWdlSid(int wdlSid) {
        wdlSid__ = wdlSid;
    }

    /**
     * <p>get WLA_KBN value
     * @return WLA_KBN value
     */
    public int getWlaKbn() {
        return wlaKbn__;
    }

    /**
     * <p>set WLA_KBN value
     * @param wlaKbn WLA_KBN value
     */
    public void setWlaKbn(int wlaKbn) {
        wlaKbn__ = wlaKbn;
    }

    /**
     * <p>get WLA_USR_SID value
     * @return WLA_USR_SID value
     */
    public int getWlaUsrSid() {
        return wlaUsrSid__;
    }

    /**
     * <p>set WLA_USR_SID value
     * @param wlaUsrSid WLA_USR_SID value
     */
    public void setWlaUsrSid(int wlaUsrSid) {
        wlaUsrSid__ = wlaUsrSid;
    }

    /**
     * <p>get WLA_AUTH value
     * @return WLA_AUTH value
     */
    public int getWlaAuth() {
        return wlaAuth__;
    }

    /**
     * <p>set WLA_AUTH value
     * @param wlaAuth WLA_AUTH value
     */
    public void setWlaAuth(int wlaAuth) {
        wlaAuth__ = wlaAuth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wdlSid__);
        buf.append(",");
        buf.append(wlaKbn__);
        buf.append(",");
        buf.append(wlaUsrSid__);
        buf.append(",");
        buf.append(wlaAuth__);
        return buf.toString();
    }

}
