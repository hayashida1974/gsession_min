package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_OTP_ATOKEN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpAtokenModel implements Serializable {

    /** COA_TOKEN mapping */
    private String coaToken__;
    /** USR_SID mapping */
    private int usrSid__;
    /** COA_PASS mapping */
    private String coaPass__;
    /** COA_ADDRESS mapping */
    private String coaAddress__;
    /** COA_LIMIT_DATE mapping */
    private UDate coaLimitDate__;
    /** COA_DATE mapping */
    private UDate coaDate__;

    /**
     * <p>Default Constructor
     */
    public CmnOtpAtokenModel() {
    }

    /**
     * <p>get COA_TOKEN value
     * @return COA_TOKEN value
     */
    public String getCoaToken() {
        return coaToken__;
    }

    /**
     * <p>set COA_TOKEN value
     * @param coaToken COA_TOKEN value
     */
    public void setCoaToken(String coaToken) {
        coaToken__ = coaToken;
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
     * <p>get COA_PASS value
     * @return COA_PASS value
     */
    public String getCoaPass() {
        return coaPass__;
    }

    /**
     * <p>set COA_PASS value
     * @param coaPass COA_PASS value
     */
    public void setCoaPass(String coaPass) {
        coaPass__ = coaPass;
    }

    /**
     * <p>get COA_ADDRESS value
     * @return COA_ADDRESS value
     */
    public String getCoaAddress() {
        return coaAddress__;
    }

    /**
     * <p>set COA_ADDRESS value
     * @param coaAddress COA_ADDRESS value
     */
    public void setCoaAddress(String coaAddress) {
        coaAddress__ = coaAddress;
    }

    /**
     * <p>get COA_LIMIT_DATE value
     * @return COA_LIMIT_DATE value
     */
    public UDate getCoaLimitDate() {
        return coaLimitDate__;
    }

    /**
     * <p>set COA_LIMIT_DATE value
     * @param coaLimitDate COA_LIMIT_DATE value
     */
    public void setCoaLimitDate(UDate coaLimitDate) {
        coaLimitDate__ = coaLimitDate;
    }

    /**
     * <p>get COA_DATE value
     * @return COA_DATE value
     */
    public UDate getCoaDate() {
        return coaDate__;
    }

    /**
     * <p>set COA_DATE value
     * @param coaDate COA_DATE value
     */
    public void setCoaDate(UDate coaDate) {
        coaDate__ = coaDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(coaToken__, ""));
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(coaPass__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(coaAddress__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(coaLimitDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(coaDate__, ""));
        return buf.toString();
    }

}
