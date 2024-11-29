package jp.groupsession.v2.convert.convert430.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SML_SMEIS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtSmlSmeisModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** SMS_SID mapping */
    private int smsSid__;
    /** SMS_SDATE mapping */
    private UDate smsSdate__;
    /** SMS_TITLE mapping */
    private String smsTitle__;
    /** SMS_MARK mapping */
    private int smsMark__;
    /** SMS_BODY mapping */
    private String smsBody__;
    /** SMS_JKBN mapping */
    private int smsJkbn__;
    /** SMS_AUID mapping */
    private int smsAuid__;
    /** SMS_ADATE mapping */
    private UDate smsAdate__;
    /** SMS_EUID mapping */
    private int smsEuid__;
    /** SMS_EDATE mapping */
    private UDate smsEdate__;

    /**
     * <p>Default Constructor
     */
    public CvtSmlSmeisModel() {
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
     * <p>get SMS_SID value
     * @return SMS_SID value
     */
    public int getSmsSid() {
        return smsSid__;
    }

    /**
     * <p>set SMS_SID value
     * @param smsSid SMS_SID value
     */
    public void setSmsSid(int smsSid) {
        smsSid__ = smsSid;
    }

    /**
     * <p>get SMS_SDATE value
     * @return SMS_SDATE value
     */
    public UDate getSmsSdate() {
        return smsSdate__;
    }

    /**
     * <p>set SMS_SDATE value
     * @param smsSdate SMS_SDATE value
     */
    public void setSmsSdate(UDate smsSdate) {
        smsSdate__ = smsSdate;
    }

    /**
     * <p>get SMS_TITLE value
     * @return SMS_TITLE value
     */
    public String getSmsTitle() {
        return smsTitle__;
    }

    /**
     * <p>set SMS_TITLE value
     * @param smsTitle SMS_TITLE value
     */
    public void setSmsTitle(String smsTitle) {
        smsTitle__ = smsTitle;
    }

    /**
     * <p>get SMS_MARK value
     * @return SMS_MARK value
     */
    public int getSmsMark() {
        return smsMark__;
    }

    /**
     * <p>set SMS_MARK value
     * @param smsMark SMS_MARK value
     */
    public void setSmsMark(int smsMark) {
        smsMark__ = smsMark;
    }

    /**
     * <p>get SMS_BODY value
     * @return SMS_BODY value
     */
    public String getSmsBody() {
        return smsBody__;
    }

    /**
     * <p>set SMS_BODY value
     * @param smsBody SMS_BODY value
     */
    public void setSmsBody(String smsBody) {
        smsBody__ = smsBody;
    }

    /**
     * <p>get SMS_JKBN value
     * @return SMS_JKBN value
     */
    public int getSmsJkbn() {
        return smsJkbn__;
    }

    /**
     * <p>set SMS_JKBN value
     * @param smsJkbn SMS_JKBN value
     */
    public void setSmsJkbn(int smsJkbn) {
        smsJkbn__ = smsJkbn;
    }

    /**
     * <p>get SMS_AUID value
     * @return SMS_AUID value
     */
    public int getSmsAuid() {
        return smsAuid__;
    }

    /**
     * <p>set SMS_AUID value
     * @param smsAuid SMS_AUID value
     */
    public void setSmsAuid(int smsAuid) {
        smsAuid__ = smsAuid;
    }

    /**
     * <p>get SMS_ADATE value
     * @return SMS_ADATE value
     */
    public UDate getSmsAdate() {
        return smsAdate__;
    }

    /**
     * <p>set SMS_ADATE value
     * @param smsAdate SMS_ADATE value
     */
    public void setSmsAdate(UDate smsAdate) {
        smsAdate__ = smsAdate;
    }

    /**
     * <p>get SMS_EUID value
     * @return SMS_EUID value
     */
    public int getSmsEuid() {
        return smsEuid__;
    }

    /**
     * <p>set SMS_EUID value
     * @param smsEuid SMS_EUID value
     */
    public void setSmsEuid(int smsEuid) {
        smsEuid__ = smsEuid;
    }

    /**
     * <p>get SMS_EDATE value
     * @return SMS_EDATE value
     */
    public UDate getSmsEdate() {
        return smsEdate__;
    }

    /**
     * <p>set SMS_EDATE value
     * @param smsEdate SMS_EDATE value
     */
    public void setSmsEdate(UDate smsEdate) {
        smsEdate__ = smsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(smsSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smsSdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smsTitle__, ""));
        buf.append(",");
        buf.append(smsMark__);
        buf.append(",");
        buf.append(NullDefault.getString(smsBody__, ""));
        buf.append(",");
        buf.append(smsJkbn__);
        buf.append(",");
        buf.append(smsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smsAdate__, ""));
        buf.append(",");
        buf.append(smsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(smsEdate__, ""));
        return buf.toString();
    }

}
