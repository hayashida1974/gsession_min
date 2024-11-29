package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_MAIL_BODY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailBodyModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WMB_BODY mapping */
    private String wmbBody__;
    /** WMB_CHARSET mapping */
    private String wmbCharset__;
    /** WAC_SID mapping */
    private int wacSid__;

    /**
     * <p>Default Constructor
     */
    public WmlMailBodyModel() {
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
     * <p>get WMB_BODY value
     * @return WMB_BODY value
     */
    public String getWmbBody() {
        return wmbBody__;
    }

    /**
     * <p>set WMB_BODY value
     * @param wmbBody WMB_BODY value
     */
    public void setWmbBody(String wmbBody) {
        wmbBody__ = wmbBody;
    }

    /**
     * <p>get WMB_CHARSET value
     * @return WMB_CHARSET value
     */
    public String getWmbCharset() {
        return wmbCharset__;
    }

    /**
     * <p>set WMB_CHARSET value
     * @param wmbCharset WMB_CHARSET value
     */
    public void setWmbCharset(String wmbCharset) {
        wmbCharset__ = wmbCharset;
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
        buf.append(NullDefault.getString(wmbBody__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wmbCharset__, ""));
        buf.append(",");
        buf.append(wacSid__);
        return buf.toString();
    }

}
