package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_MAIL_LOG_SEND Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailLogSendModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WLS_NUM mapping */
    private int wlsNum__;
    /** WLS_TYPE mapping */
    private int wlsType__;
    /** WLS_ADDRESS mapping */
    private String wlsAddress__;
    /** WAC_SID mapping */
    private int wacSid__;

    /**
     * <p>Default Constructor
     */
    public WmlMailLogSendModel() {
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
     * <p>get WLS_NUM value
     * @return WLS_NUM value
     */
    public int getWlsNum() {
        return wlsNum__;
    }

    /**
     * <p>set WLS_NUM value
     * @param wlsNum WLS_NUM value
     */
    public void setWlsNum(int wlsNum) {
        wlsNum__ = wlsNum;
    }

    /**
     * <p>get WLS_TYPE value
     * @return WLS_TYPE value
     */
    public int getWlsType() {
        return wlsType__;
    }

    /**
     * <p>set WLS_TYPE value
     * @param wlsType WLS_TYPE value
     */
    public void setWlsType(int wlsType) {
        wlsType__ = wlsType;
    }

    /**
     * <p>get WLS_ADDRESS value
     * @return WLS_ADDRESS value
     */
    public String getWlsAddress() {
        return wlsAddress__;
    }

    /**
     * <p>set WLS_ADDRESS value
     * @param wlsAddress WLS_ADDRESS value
     */
    public void setWlsAddress(String wlsAddress) {
        wlsAddress__ = wlsAddress;
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
        buf.append(wlsNum__);
        buf.append(",");
        buf.append(wlsType__);
        buf.append(",");
        buf.append(NullDefault.getString(wlsAddress__, ""));
        buf.append(",");
        buf.append(wacSid__);
        return buf.toString();
    }

}
