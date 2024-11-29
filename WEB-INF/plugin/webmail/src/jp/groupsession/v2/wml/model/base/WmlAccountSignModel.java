package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_ACCOUNT_SIGN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountSignModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WSI_NO mapping */
    private int wsiNo__;
    /** WSI_TITLE mapping */
    private String wsiTitle__;
    /** WSI_SIGN mapping */
    private String wsiSign__;
    /** WSI_DEF mapping */
    private int wsiDef__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountSignModel() {
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
     * <p>get WSI_NO value
     * @return WSI_NO value
     */
    public int getWsiNo() {
        return wsiNo__;
    }

    /**
     * <p>set WSI_NO value
     * @param wsiNo WSI_NO value
     */
    public void setWsiNo(int wsiNo) {
        wsiNo__ = wsiNo;
    }

    /**
     * <p>get WSI_TITLE value
     * @return WSI_TITLE value
     */
    public String getWsiTitle() {
        return wsiTitle__;
    }

    /**
     * <p>set WSI_TITLE value
     * @param wsiTitle WSI_TITLE value
     */
    public void setWsiTitle(String wsiTitle) {
        wsiTitle__ = wsiTitle;
    }

    /**
     * <p>get WSI_SIGN value
     * @return WSI_SIGN value
     */
    public String getWsiSign() {
        return wsiSign__;
    }

    /**
     * <p>set WSI_SIGN value
     * @param wsiSign WSI_SIGN value
     */
    public void setWsiSign(String wsiSign) {
        wsiSign__ = wsiSign;
    }

    /**
     * <p>get WSI_DEF value
     * @return WSI_DEF value
     */
    public int getWsiDef() {
        return wsiDef__;
    }

    /**
     * <p>set WSI_DEF value
     * @param wsiDef WSI_DEF value
     */
    public void setWsiDef(int wsiDef) {
        wsiDef__ = wsiDef;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wsiNo__);
        buf.append(",");
        buf.append(NullDefault.getString(wsiTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wsiSign__, ""));
        buf.append(",");
        buf.append(wsiDef__);
        return buf.toString();
    }

}
