package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_CONTACT_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrContactBinModel implements Serializable {

    /** ADC_SID mapping */
    private int adcSid__;
    /** BIN_SID mapping */
    private Long binSid__;
    /** ACB_AUID mapping */
    private int acbAuid__;
    /** ACB_ADATE mapping */
    private UDate acbAdate__;
    /** ACB_EUID mapping */
    private int acbEuid__;
    /** ACB_EDATE mapping */
    private UDate acbEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrContactBinModel() {
    }

    /**
     * <p>get ADC_SID value
     * @return ADC_SID value
     */
    public int getAdcSid() {
        return adcSid__;
    }

    /**
     * <p>set ADC_SID value
     * @param adcSid ADC_SID value
     */
    public void setAdcSid(int adcSid) {
        adcSid__ = adcSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get ACB_AUID value
     * @return ACB_AUID value
     */
    public int getAcbAuid() {
        return acbAuid__;
    }

    /**
     * <p>set ACB_AUID value
     * @param acbAuid ACB_AUID value
     */
    public void setAcbAuid(int acbAuid) {
        acbAuid__ = acbAuid;
    }

    /**
     * <p>get ACB_ADATE value
     * @return ACB_ADATE value
     */
    public UDate getAcbAdate() {
        return acbAdate__;
    }

    /**
     * <p>set ACB_ADATE value
     * @param acbAdate ACB_ADATE value
     */
    public void setAcbAdate(UDate acbAdate) {
        acbAdate__ = acbAdate;
    }

    /**
     * <p>get ACB_EUID value
     * @return ACB_EUID value
     */
    public int getAcbEuid() {
        return acbEuid__;
    }

    /**
     * <p>set ACB_EUID value
     * @param acbEuid ACB_EUID value
     */
    public void setAcbEuid(int acbEuid) {
        acbEuid__ = acbEuid;
    }

    /**
     * <p>get ACB_EDATE value
     * @return ACB_EDATE value
     */
    public UDate getAcbEdate() {
        return acbEdate__;
    }

    /**
     * <p>set ACB_EDATE value
     * @param acbEdate ACB_EDATE value
     */
    public void setAcbEdate(UDate acbEdate) {
        acbEdate__ = acbEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adcSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(acbAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(acbAdate__, ""));
        buf.append(",");
        buf.append(acbEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(acbEdate__, ""));
        return buf.toString();
    }

}
