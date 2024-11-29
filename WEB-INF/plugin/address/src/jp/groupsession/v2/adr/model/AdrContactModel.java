package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>ADR_CONTACT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrContactModel extends AbstractModel {

    /** ADC_SID mapping */
    private int adcSid__;
    /** ADR_SID mapping */
    private int adrSid__;
    /** ADC_TITLE mapping */
    private String adcTitle__;
    /** ADC_TYPE mapping */
    private int adcType__;
    /** ADC_CTTIME mapping */
    private UDate adcCttime__;
    /** ADC_CTTIME_TO mapping */
    private UDate adcCttimeTo__;
    /** PRJ_SID mapping */
    private int prjSid__;
    /** ADC_BIKO mapping */
    private String adcBiko__;
    /** ADC_AUID mapping */
    private int adcAuid__;
    /** ADC_ADATE mapping */
    private UDate adcAdate__;
    /** ADC_EUID mapping */
    private int adcEuid__;
    /** ADC_EDATE mapping */
    private UDate adcEdate__;
    /** ADC_GRP_SID mapping */
    private int adcGrpSid__;

    /**
     * <p>Default Constructor
     */
    public AdrContactModel() {
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
     * <p>get ADC_TITLE value
     * @return ADC_TITLE value
     */
    public String getAdcTitle() {
        return adcTitle__;
    }

    /**
     * <p>set ADC_TITLE value
     * @param adcTitle ADC_TITLE value
     */
    public void setAdcTitle(String adcTitle) {
        adcTitle__ = adcTitle;
    }

    /**
     * <p>get ADC_TYPE value
     * @return ADC_TYPE value
     */
    public int getAdcType() {
        return adcType__;
    }

    /**
     * <p>set ADC_TYPE value
     * @param adcType ADC_TYPE value
     */
    public void setAdcType(int adcType) {
        adcType__ = adcType;
    }

    /**
     * <p>get ADC_CTTIME value
     * @return ADC_CTTIME value
     */
    public UDate getAdcCttime() {
        return adcCttime__;
    }

    /**
     * <p>set ADC_CTTIME value
     * @param adcCttime ADC_CTTIME value
     */
    public void setAdcCttime(UDate adcCttime) {
        adcCttime__ = adcCttime;
    }

    /**
     * <p>get ADC_CTTIME_TO value
     * @return ADC_CTTIME_TO value
     */
    public UDate getAdcCttimeTo() {
        return adcCttimeTo__;
    }

    /**
     * <p>set ADC_CTTIME_TO value
     * @param adcCttimeTo ADC_CTTIME_TO value
     */
    public void setAdcCttimeTo(UDate adcCttimeTo) {
        adcCttimeTo__ = adcCttimeTo;
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
     * <p>get ADC_BIKO value
     * @return ADC_BIKO value
     */
    public String getAdcBiko() {
        return adcBiko__;
    }

    /**
     * <p>set ADC_BIKO value
     * @param adcBiko ADC_BIKO value
     */
    public void setAdcBiko(String adcBiko) {
        adcBiko__ = adcBiko;
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
     * <p>adcGrpSid を取得します。
     * @return adcGrpSid
     */
    public int getAdcGrpSid() {
        return adcGrpSid__;
    }

    /**
     * <p>adcGrpSid をセットします。
     * @param adcGrpSid adcGrpSid
     */
    public void setAdcGrpSid(int adcGrpSid) {
        adcGrpSid__ = adcGrpSid;
    }
}