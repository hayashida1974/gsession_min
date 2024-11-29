package jp.groupsession.v2.sml.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SML_BAN_DEST_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestConfModel implements Serializable {

    /** SBC_SID mapping */
    private int sbcSid__;
    /** SBC_NAME mapping */
    private String sbcName__;
    /** SBC_BIKO mapping */
    private String sbcBiko__;
    /** SBC_AUID mapping */
    private int sbcAuid__;
    /** SBC_ADATE mapping */
    private UDate sbcAdate__;
    /** SBC_EUID mapping */
    private int sbcEuid__;
    /** SBC_EDATE mapping */
    private UDate sbcEdate__;

    /**
     * <p>Default Constructor
     */
    public SmlBanDestConfModel() {
    }

    /**
     * <p>get SBC_SID value
     * @return SBC_SID value
     */
    public int getSbcSid() {
        return sbcSid__;
    }

    /**
     * <p>set SBC_SID value
     * @param sbcSid SBC_SID value
     */
    public void setSbcSid(int sbcSid) {
        sbcSid__ = sbcSid;
    }

    /**
     * <p>get SBC_NAME value
     * @return SBC_NAME value
     */
    public String getSbcName() {
        return sbcName__;
    }

    /**
     * <p>set SBC_NAME value
     * @param sbcName SBC_NAME value
     */
    public void setSbcName(String sbcName) {
        sbcName__ = sbcName;
    }

    /**
     * <p>get SBC_BIKO value
     * @return SBC_BIKO value
     */
    public String getSbcBiko() {
        return sbcBiko__;
    }

    /**
     * <p>set SBC_BIKO value
     * @param sbcBiko SBC_BIKO value
     */
    public void setSbcBiko(String sbcBiko) {
        sbcBiko__ = sbcBiko;
    }

    /**
     * <p>get SBC_AUID value
     * @return SBC_AUID value
     */
    public int getSbcAuid() {
        return sbcAuid__;
    }

    /**
     * <p>set SBC_AUID value
     * @param sbcAuid SBC_AUID value
     */
    public void setSbcAuid(int sbcAuid) {
        sbcAuid__ = sbcAuid;
    }

    /**
     * <p>get SBC_ADATE value
     * @return SBC_ADATE value
     */
    public UDate getSbcAdate() {
        return sbcAdate__;
    }

    /**
     * <p>set SBC_ADATE value
     * @param sbcAdate SBC_ADATE value
     */
    public void setSbcAdate(UDate sbcAdate) {
        sbcAdate__ = sbcAdate;
    }

    /**
     * <p>get SBC_EUID value
     * @return SBC_EUID value
     */
    public int getSbcEuid() {
        return sbcEuid__;
    }

    /**
     * <p>set SBC_EUID value
     * @param sbcEuid SBC_EUID value
     */
    public void setSbcEuid(int sbcEuid) {
        sbcEuid__ = sbcEuid;
    }

    /**
     * <p>get SBC_EDATE value
     * @return SBC_EDATE value
     */
    public UDate getSbcEdate() {
        return sbcEdate__;
    }

    /**
     * <p>set SBC_EDATE value
     * @param sbcEdate SBC_EDATE value
     */
    public void setSbcEdate(UDate sbcEdate) {
        sbcEdate__ = sbcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sbcSid__);
        buf.append(",");
        buf.append(NullDefault.getString(sbcName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(sbcBiko__, ""));
        buf.append(",");
        buf.append(sbcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sbcAdate__, ""));
        buf.append(",");
        buf.append(sbcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(sbcEdate__, ""));
        return buf.toString();
    }

}
