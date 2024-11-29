package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_MAIL_LOG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailLogModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WLG_TITLE mapping */
    private String wlgTitle__;
    /** WLG_DATE mapping */
    private UDate wlgDate__;
    /** WLG_FROM mapping */
    private String wlgFrom__;
    /** WLG_TEMPFLG mapping */
    private int wlgTempflg__;
    /** WLG_MAILTYPE mapping */
    private int wlgMailtype__;
    /** WAC_SID mapping */
    private int wacSid__;

    /**
     * <p>Default Constructor
     */
    public WmlMailLogModel() {
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
     * <p>get WLG_TITLE value
     * @return WLG_TITLE value
     */
    public String getWlgTitle() {
        return wlgTitle__;
    }

    /**
     * <p>set WLG_TITLE value
     * @param wlgTitle WLG_TITLE value
     */
    public void setWlgTitle(String wlgTitle) {
        wlgTitle__ = wlgTitle;
    }

    /**
     * <p>get WLG_DATE value
     * @return WLG_DATE value
     */
    public UDate getWlgDate() {
        return wlgDate__;
    }

    /**
     * <p>set WLG_DATE value
     * @param wlgDate WLG_DATE value
     */
    public void setWlgDate(UDate wlgDate) {
        wlgDate__ = wlgDate;
    }

    /**
     * <p>get WLG_FROM value
     * @return WLG_FROM value
     */
    public String getWlgFrom() {
        return wlgFrom__;
    }

    /**
     * <p>set WLG_FROM value
     * @param wlgFrom WLG_FROM value
     */
    public void setWlgFrom(String wlgFrom) {
        wlgFrom__ = wlgFrom;
    }

    /**
     * <p>get WLG_TEMPFLG value
     * @return WLG_TEMPFLG value
     */
    public int getWlgTempflg() {
        return wlgTempflg__;
    }

    /**
     * <p>set WLG_TEMPFLG value
     * @param wlgTempflg WLG_TEMPFLG value
     */
    public void setWlgTempflg(int wlgTempflg) {
        wlgTempflg__ = wlgTempflg;
    }

    /**
     * <p>get WLG_MAILTYPE value
     * @return WLG_MAILTYPE value
     */
    public int getWlgMailtype() {
        return wlgMailtype__;
    }

    /**
     * <p>set WLG_MAILTYPE value
     * @param wlgMailtype WLG_MAILTYPE value
     */
    public void setWlgMailtype(int wlgMailtype) {
        wlgMailtype__ = wlgMailtype;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(NullDefault.getString(wlgTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wlgDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wlgFrom__, ""));
        buf.append(",");
        buf.append(wlgTempflg__);
        buf.append(",");
        buf.append(wlgMailtype__);
        buf.append(",");
        buf.append(wacSid__);
        return buf.toString();
    }

}
