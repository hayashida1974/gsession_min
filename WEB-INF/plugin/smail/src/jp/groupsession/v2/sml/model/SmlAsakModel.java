package jp.groupsession.v2.sml.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SML_ASAK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAsakModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SMS_SID mapping */
    private int smsSid__;
    /** SMJ_SENDKBN mapping */
    private int smjSendkbn__;
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
    public SmlAsakModel() {
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
        buf.append(sacSid__);
        buf.append(",");
        buf.append(smsSid__);
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

    /**
     * <p>smjSendkbn を取得します。
     * @return smjSendkbn
     */
    public int getSmjSendkbn() {
        return smjSendkbn__;
    }

    /**
     * <p>smjSendkbn をセットします。
     * @param smjSendkbn smjSendkbn
     */
    public void setSmjSendkbn(int smjSendkbn) {
        smjSendkbn__ = smjSendkbn;
    }

    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

}
