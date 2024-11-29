package jp.groupsession.v2.adr.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ADR_CONTACT_PRJ Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AdrContactPrjModel implements Serializable {

    /** ADC_SID mapping */
    private int adcSid__;
    /** PRJ_SID mapping */
    private int prjSid__;
    /** ADC_AUID mapping */
    private int adcAuid__;
    /** ADC_ADATE mapping */
    private UDate adcAdate__;
    /** ADC_EUID mapping */
    private int adcEuid__;
    /** ADC_EDATE mapping */
    private UDate adcEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrContactPrjModel() {
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
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }

    /**
     * <p>get ADC_AUID value
     * @return ADC_AUID value
     */
    public int getAdcAuid() {
        return adcAuid__;
    }

    /**
     * <p>set ADC_AUID value
     * @param adcAuid ADC_AUID value
     */
    public void setAdcAuid(int adcAuid) {
        adcAuid__ = adcAuid;
    }

    /**
     * <p>get ADC_ADATE value
     * @return ADC_ADATE value
     */
    public UDate getAdcAdate() {
        return adcAdate__;
    }

    /**
     * <p>set ADC_ADATE value
     * @param adcAdate ADC_ADATE value
     */
    public void setAdcAdate(UDate adcAdate) {
        adcAdate__ = adcAdate;
    }

    /**
     * <p>get ADC_EUID value
     * @return ADC_EUID value
     */
    public int getAdcEuid() {
        return adcEuid__;
    }

    /**
     * <p>set ADC_EUID value
     * @param adcEuid ADC_EUID value
     */
    public void setAdcEuid(int adcEuid) {
        adcEuid__ = adcEuid;
    }

    /**
     * <p>get ADC_EDATE value
     * @return ADC_EDATE value
     */
    public UDate getAdcEdate() {
        return adcEdate__;
    }

    /**
     * <p>set ADC_EDATE value
     * @param adcEdate ADC_EDATE value
     */
    public void setAdcEdate(UDate adcEdate) {
        adcEdate__ = adcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(adcSid__);
        buf.append(",");
        buf.append(prjSid__);
        buf.append(",");
        buf.append(adcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(adcAdate__, ""));
        buf.append(",");
        buf.append(adcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(adcEdate__, ""));
        return buf.toString();
    }

}
