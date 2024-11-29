package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_DELMAIL_TEMP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDelmailTempModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WDT_ADATE mapping */
    private UDate wdtAdate__;

    /**
     * <p>Default Constructor
     */
    public WmlDelmailTempModel() {
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
     * <p>get WMD_MAILNUM value
     * @return WMD_MAILNUM value
     */
    public long getWmdMailnum() {
        return wmdMailnum__;
    }

    /**
     * <p>set WMD_MAILNUM value
     * @param wmdMailnum WMD_MAILNUM value
     */
    public void setWmdMailnum(long wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
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
     * <p>get WDT_ADATE value
     * @return WDT_ADATE value
     */
    public UDate getWdtAdate() {
        return wdtAdate__;
    }

    /**
     * <p>set WDT_ADATE value
     * @param wdtAdate WDT_ADATE value
     */
    public void setWdtAdate(UDate wdtAdate) {
        wdtAdate__ = wdtAdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wdtAdate__, ""));
        return buf.toString();
    }

}
