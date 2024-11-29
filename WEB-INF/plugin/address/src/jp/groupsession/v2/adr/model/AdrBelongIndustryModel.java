package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_BELONG_INDUSTRY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrBelongIndustryModel implements Serializable {

    /** ACO_SID mapping */
    private int acoSid__;
    /** ATI_SID mapping */
    private int atiSid__;
    /** ABI_AUID mapping */
    private int abiAuid__;
    /** ABI_ADATE mapping */
    private UDate abiAdate__;
    /** ABI_EUID mapping */
    private int abiEuid__;
    /** ABI_EDATE mapping */
    private UDate abiEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrBelongIndustryModel() {
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ATI_SID value
     * @return ATI_SID value
     */
    public int getAtiSid() {
        return atiSid__;
    }

    /**
     * <p>set ATI_SID value
     * @param atiSid ATI_SID value
     */
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }

    /**
     * <p>get ABI_AUID value
     * @return ABI_AUID value
     */
    public int getAbiAuid() {
        return abiAuid__;
    }

    /**
     * <p>set ABI_AUID value
     * @param abiAuid ABI_AUID value
     */
    public void setAbiAuid(int abiAuid) {
        abiAuid__ = abiAuid;
    }

    /**
     * <p>get ABI_ADATE value
     * @return ABI_ADATE value
     */
    public UDate getAbiAdate() {
        return abiAdate__;
    }

    /**
     * <p>set ABI_ADATE value
     * @param abiAdate ABI_ADATE value
     */
    public void setAbiAdate(UDate abiAdate) {
        abiAdate__ = abiAdate;
    }

    /**
     * <p>get ABI_EUID value
     * @return ABI_EUID value
     */
    public int getAbiEuid() {
        return abiEuid__;
    }

    /**
     * <p>set ABI_EUID value
     * @param abiEuid ABI_EUID value
     */
    public void setAbiEuid(int abiEuid) {
        abiEuid__ = abiEuid;
    }

    /**
     * <p>get ABI_EDATE value
     * @return ABI_EDATE value
     */
    public UDate getAbiEdate() {
        return abiEdate__;
    }

    /**
     * <p>set ABI_EDATE value
     * @param abiEdate ABI_EDATE value
     */
    public void setAbiEdate(UDate abiEdate) {
        abiEdate__ = abiEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(acoSid__);
        buf.append(",");
        buf.append(atiSid__);
        buf.append(",");
        buf.append(abiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(abiAdate__, ""));
        buf.append(",");
        buf.append(abiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(abiEdate__, ""));
        return buf.toString();
    }

}
