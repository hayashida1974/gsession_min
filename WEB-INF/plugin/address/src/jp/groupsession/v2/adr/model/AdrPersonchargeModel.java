package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_PERSONCHARGE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPersonchargeModel implements Serializable {

    /** ADR_SID mapping */
    private int adrSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** APC_AUID mapping */
    private int apcAuid__;
    /** APC_ADATE mapping */
    private UDate apcAdate__;
    /** APC_EUID mapping */
    private int apcEuid__;
    /** APC_EDATE mapping */
    private UDate apcEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrPersonchargeModel() {
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
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
     * <p>get APC_AUID value
     * @return APC_AUID value
     */
    public int getApcAuid() {
        return apcAuid__;
    }

    /**
     * <p>set APC_AUID value
     * @param apcAuid APC_AUID value
     */
    public void setApcAuid(int apcAuid) {
        apcAuid__ = apcAuid;
    }

    /**
     * <p>get APC_ADATE value
     * @return APC_ADATE value
     */
    public UDate getApcAdate() {
        return apcAdate__;
    }

    /**
     * <p>set APC_ADATE value
     * @param apcAdate APC_ADATE value
     */
    public void setApcAdate(UDate apcAdate) {
        apcAdate__ = apcAdate;
    }

    /**
     * <p>get APC_EUID value
     * @return APC_EUID value
     */
    public int getApcEuid() {
        return apcEuid__;
    }

    /**
     * <p>set APC_EUID value
     * @param apcEuid APC_EUID value
     */
    public void setApcEuid(int apcEuid) {
        apcEuid__ = apcEuid;
    }

    /**
     * <p>get APC_EDATE value
     * @return APC_EDATE value
     */
    public UDate getApcEdate() {
        return apcEdate__;
    }

    /**
     * <p>set APC_EDATE value
     * @param apcEdate APC_EDATE value
     */
    public void setApcEdate(UDate apcEdate) {
        apcEdate__ = apcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adrSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(apcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apcAdate__, ""));
        buf.append(",");
        buf.append(apcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apcEdate__, ""));
        return buf.toString();
    }

}
