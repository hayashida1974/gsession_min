package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_MAIL_TEMPLATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailTemplateModel implements Serializable {

    /** WTP_SID mapping */
    private int wtpSid__;
    /** WTP_TYPE mapping */
    private int wtpType__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WTP_NAME mapping */
    private String wtpName__;
    /** WTP_TITLE mapping */
    private String wtpTitle__;
    /** WTP_BODY mapping */
    private String wtpBody__;
    /** WTP_ORDER mapping */
    private int wtpOrder__;
    /** WTP_AUID mapping */
    private int wtpAuid__;
    /** WTP_ADATE mapping */
    private UDate wtpAdate__;
    /** WTP_EUID mapping */
    private int wtpEuid__;
    /** WTP_EDATE mapping */
    private UDate wtpEdate__;

    /**
     * <p>Default Constructor
     */
    public WmlMailTemplateModel() {
    }

    /**
     * <p>get WTP_SID value
     * @return WTP_SID value
     */
    public int getWtpSid() {
        return wtpSid__;
    }

    /**
     * <p>set WTP_SID value
     * @param wtpSid WTP_SID value
     */
    public void setWtpSid(int wtpSid) {
        wtpSid__ = wtpSid;
    }

    /**
     * <p>get WTP_TYPE value
     * @return WTP_TYPE value
     */
    public int getWtpType() {
        return wtpType__;
    }

    /**
     * <p>set WTP_TYPE value
     * @param wtpType WTP_TYPE value
     */
    public void setWtpType(int wtpType) {
        wtpType__ = wtpType;
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
     * <p>get WTP_NAME value
     * @return WTP_NAME value
     */
    public String getWtpName() {
        return wtpName__;
    }

    /**
     * <p>set WTP_NAME value
     * @param wtpName WTP_NAME value
     */
    public void setWtpName(String wtpName) {
        wtpName__ = wtpName;
    }

    /**
     * <p>get WTP_TITLE value
     * @return WTP_TITLE value
     */
    public String getWtpTitle() {
        return wtpTitle__;
    }

    /**
     * <p>set WTP_TITLE value
     * @param wtpTitle WTP_TITLE value
     */
    public void setWtpTitle(String wtpTitle) {
        wtpTitle__ = wtpTitle;
    }

    /**
     * <p>get WTP_BODY value
     * @return WTP_BODY value
     */
    public String getWtpBody() {
        return wtpBody__;
    }

    /**
     * <p>set WTP_BODY value
     * @param wtpBody WTP_BODY value
     */
    public void setWtpBody(String wtpBody) {
        wtpBody__ = wtpBody;
    }

    /**
     * <p>get WTP_ORDER value
     * @return WTP_ORDER value
     */
    public int getWtpOrder() {
        return wtpOrder__;
    }

    /**
     * <p>set WTP_ORDER value
     * @param wtpOrder WTP_ORDER value
     */
    public void setWtpOrder(int wtpOrder) {
        wtpOrder__ = wtpOrder;
    }

    /**
     * <p>get WTP_AUID value
     * @return WTP_AUID value
     */
    public int getWtpAuid() {
        return wtpAuid__;
    }

    /**
     * <p>set WTP_AUID value
     * @param wtpAuid WTP_AUID value
     */
    public void setWtpAuid(int wtpAuid) {
        wtpAuid__ = wtpAuid;
    }

    /**
     * <p>get WTP_ADATE value
     * @return WTP_ADATE value
     */
    public UDate getWtpAdate() {
        return wtpAdate__;
    }

    /**
     * <p>set WTP_ADATE value
     * @param wtpAdate WTP_ADATE value
     */
    public void setWtpAdate(UDate wtpAdate) {
        wtpAdate__ = wtpAdate;
    }

    /**
     * <p>get WTP_EUID value
     * @return WTP_EUID value
     */
    public int getWtpEuid() {
        return wtpEuid__;
    }

    /**
     * <p>set WTP_EUID value
     * @param wtpEuid WTP_EUID value
     */
    public void setWtpEuid(int wtpEuid) {
        wtpEuid__ = wtpEuid;
    }

    /**
     * <p>get WTP_EDATE value
     * @return WTP_EDATE value
     */
    public UDate getWtpEdate() {
        return wtpEdate__;
    }

    /**
     * <p>set WTP_EDATE value
     * @param wtpEdate WTP_EDATE value
     */
    public void setWtpEdate(UDate wtpEdate) {
        wtpEdate__ = wtpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wtpSid__);
        buf.append(",");
        buf.append(wtpType__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wtpName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wtpTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wtpBody__, ""));
        buf.append(",");
        buf.append(wtpOrder__);
        buf.append(",");
        buf.append(wtpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wtpAdate__, ""));
        buf.append(",");
        buf.append(wtpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wtpEdate__, ""));
        return buf.toString();
    }

}
