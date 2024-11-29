package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_HEADER_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlHeaderDataModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WMH_NUM mapping */
    private int wmhNum__;
    /** WMH_TYPE mapping */
    private String wmhType__;
    /** WMH_CONTENT mapping */
    private String wmhContent__;
    /** WAC_SID mapping */
    private int wacSid__;

    /**
     * <p>Default Constructor
     */
    public WmlHeaderDataModel() {
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
     * <p>get WMH_NUM value
     * @return WMH_NUM value
     */
    public int getWmhNum() {
        return wmhNum__;
    }

    /**
     * <p>set WMH_NUM value
     * @param wmhNum WMH_NUM value
     */
    public void setWmhNum(int wmhNum) {
        wmhNum__ = wmhNum;
    }

    /**
     * <p>get WMH_TYPE value
     * @return WMH_TYPE value
     */
    public String getWmhType() {
        return wmhType__;
    }

    /**
     * <p>set WMH_TYPE value
     * @param wmhType WMH_TYPE value
     */
    public void setWmhType(String wmhType) {
        wmhType__ = wmhType;
    }

    /**
     * <p>get WMH_CONTENT value
     * @return WMH_CONTENT value
     */
    public String getWmhContent() {
        return wmhContent__;
    }

    /**
     * <p>set WMH_CONTENT value
     * @param wmhContent WMH_CONTENT value
     */
    public void setWmhContent(String wmhContent) {
        wmhContent__ = wmhContent;
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
        buf.append(wmhNum__);
        buf.append(",");
        buf.append(NullDefault.getString(wmhType__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wmhContent__, ""));
        buf.append(",");
        buf.append(wacSid__);
        return buf.toString();
    }

}
