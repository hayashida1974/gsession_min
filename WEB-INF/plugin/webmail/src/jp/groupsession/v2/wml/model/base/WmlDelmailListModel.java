package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_DELMAIL_LIST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDelmailListModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WDL_ADATE mapping */
    private UDate wdlAdate__;

    /**
     * <p>Default Constructor
     */
    public WmlDelmailListModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wdlAdate__, ""));
        return buf.toString();
    }

}
