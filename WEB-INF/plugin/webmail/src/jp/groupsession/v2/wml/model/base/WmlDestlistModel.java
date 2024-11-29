package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_DESTLIST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistModel implements Serializable {

    /** WDL_SID mapping */
    private int wdlSid__;
    /** WDL_NAME mapping */
    private String wdlName__;
    /** WDL_BIKO mapping */
    private String wdlBiko__;
    /** WDL_AUID mapping */
    private int wdlAuid__;
    /** WDL_ADATE mapping */
    private UDate wdlAdate__;
    /** WDL_EUID mapping */
    private int wdlEuid__;
    /** WDL_EDATE mapping */
    private UDate wdlEdate__;

    /**
     * <p>Default Constructor
     */
    public WmlDestlistModel() {
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
     * <p>get WDL_NAME value
     * @return WDL_NAME value
     */
    public String getWdlName() {
        return wdlName__;
    }

    /**
     * <p>set WDL_NAME value
     * @param wdlName WDL_NAME value
     */
    public void setWdlName(String wdlName) {
        wdlName__ = wdlName;
    }

    /**
     * <p>get WDL_BIKO value
     * @return WDL_BIKO value
     */
    public String getWdlBiko() {
        return wdlBiko__;
    }

    /**
     * <p>set WDL_BIKO value
     * @param wdlBiko WDL_BIKO value
     */
    public void setWdlBiko(String wdlBiko) {
        wdlBiko__ = wdlBiko;
    }

    /**
     * <p>get WDL_AUID value
     * @return WDL_AUID value
     */
    public int getWdlAuid() {
        return wdlAuid__;
    }

    /**
     * <p>set WDL_AUID value
     * @param wdlAuid WDL_AUID value
     */
    public void setWdlAuid(int wdlAuid) {
        wdlAuid__ = wdlAuid;
    }

    /**
     * <p>get WDL_ADATE value
     * @return WDL_ADATE value
     */
    public UDate getWdlAdate() {
        return wdlAdate__;
    }

    /**
     * <p>set WDL_ADATE value
     * @param wdlAdate WDL_ADATE value
     */
    public void setWdlAdate(UDate wdlAdate) {
        wdlAdate__ = wdlAdate;
    }

    /**
     * <p>get WDL_EUID value
     * @return WDL_EUID value
     */
    public int getWdlEuid() {
        return wdlEuid__;
    }

    /**
     * <p>set WDL_EUID value
     * @param wdlEuid WDL_EUID value
     */
    public void setWdlEuid(int wdlEuid) {
        wdlEuid__ = wdlEuid;
    }

    /**
     * <p>get WDL_EDATE value
     * @return WDL_EDATE value
     */
    public UDate getWdlEdate() {
        return wdlEdate__;
    }

    /**
     * <p>set WDL_EDATE value
     * @param wdlEdate WDL_EDATE value
     */
    public void setWdlEdate(UDate wdlEdate) {
        wdlEdate__ = wdlEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wdlSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wdlName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wdlBiko__, ""));
        buf.append(",");
        buf.append(wdlAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wdlAdate__, ""));
        buf.append(",");
        buf.append(wdlEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wdlEdate__, ""));
        return buf.toString();
    }

}
