package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_OTP_TOKEN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpTokenModel implements Serializable {

    /** COT_TOKEN mapping */
    private String cotToken__;
    /** USR_SID mapping */
    private int usrSid__;
    /** COT_PASS mapping */
    private String cotPass__;
    /** COT_LIMIT_DATE mapping */
    private UDate cotLimitDate__;
    /** COT_DATE mapping */
    private UDate cotDate__;

    /**
     * <p>Default Constructor
     */
    public CmnOtpTokenModel() {
    }

    /**
     * <p>get COT_TOKEN value
     * @return COT_TOKEN value
     */
    public String getCotToken() {
        return cotToken__;
    }

    /**
     * <p>set COT_TOKEN value
     * @param cotToken COT_TOKEN value
     */
    public void setCotToken(String cotToken) {
        cotToken__ = cotToken;
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
     * <p>get COT_PASS value
     * @return COT_PASS value
     */
    public String getCotPass() {
        return cotPass__;
    }

    /**
     * <p>set COT_PASS value
     * @param cotPass COT_PASS value
     */
    public void setCotPass(String cotPass) {
        cotPass__ = cotPass;
    }

    /**
     * <p>get COT_LIMIT_DATE value
     * @return COT_LIMIT_DATE value
     */
    public UDate getCotLimitDate() {
        return cotLimitDate__;
    }

    /**
     * <p>set COT_LIMIT_DATE value
     * @param cotLimitDate COT_LIMIT_DATE value
     */
    public void setCotLimitDate(UDate cotLimitDate) {
        cotLimitDate__ = cotLimitDate;
    }

    /**
     * <p>get COT_DATE value
     * @return COT_DATE value
     */
    public UDate getCotDate() {
        return cotDate__;
    }

    /**
     * <p>set COT_DATE value
     * @param cotDate COT_DATE value
     */
    public void setCotDate(UDate cotDate) {
        cotDate__ = cotDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cotToken__, ""));
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cotPass__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cotLimitDate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cotDate__, ""));
        return buf.toString();
    }

}
