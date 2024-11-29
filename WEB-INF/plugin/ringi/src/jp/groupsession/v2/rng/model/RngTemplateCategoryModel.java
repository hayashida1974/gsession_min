package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <p>RTC_TEMPLATE_CATEGORY Data Bindding JavaBean
 *
 * @author JTS
 */
public class RngTemplateCategoryModel extends AbstractModel {

    /** RTC_SID mapping */
    private int rtcSid__;
    /** RTC_TYPE mapping */
    private int rtcType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RTC_SORT mapping */
    private int rtcSort__;
    /** RTC_NAME mapping */
    private String rtcName__;
    /** RTC_USE_LIMIT */
    private int rtcUseLimit__;
    /** RTC_LIMIT_TYPE */
    private int rtcLimitType__;
    /** RTC_AUID mapping */
    private int rtcAuid__;
    /** RTC_ADATE mapping */
    private UDate rtcAdate__;
    /** RTC_EUID mapping */
    private int rtcEuid__;
    /** RTC_EDATE mapping */
    private UDate rtcEdate__;
    /**
     * @return the rtcSid
     */
    public int getRtcSid() {
        return rtcSid__;
    }
    /**
     * @param rtcSid the rtcSid to set
     */
    public void setRtcSid(int rtcSid) {
        rtcSid__ = rtcSid;
    }
    /**
     * @return the rtcType
     */
    public int getRtcType() {
        return rtcType__;
    }
    /**
     * @param rtcType the rtcType to set
     */
    public void setRtcType(int rtcType) {
        rtcType__ = rtcType;
    }
    /**
     * @return the usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid the usrSid to set
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * @return rtcSort
     */
    public int getRtcSort() {
        return rtcSort__;
    }
    /**
     * @param rtcSort 設定する rtcSort
     */
    public void setRtcSort(int rtcSort) {
        rtcSort__ = rtcSort;
    }
    /**
     * @return the rtcName
     */
    public String getRtcName() {
        return rtcName__;
    }
    /**
     * @param rtcName the rtcName to set
     */
    public void setRtcName(String rtcName) {
        rtcName__ = rtcName;
    }
    /**
     * <p>rtcUseLimit を取得します。
     * @return rtcUseLimit
     */
    public int getRtcUseLimit() {
        return rtcUseLimit__;
    }
    /**
     * <p>rtcUseLimit をセットします。
     * @param rtcUseLimit rtcUseLimit
     */
    public void setRtcUseLimit(int rtcUseLimit) {
        rtcUseLimit__ = rtcUseLimit;
    }
    /**
     * <p>rtcLimitType を取得します。
     * @return rtcLimitType
     */
    public int getRtcLimitType() {
        return rtcLimitType__;
    }
    /**
     * <p>rtcLimitType をセットします。
     * @param rtcLimitType rtcLimitType
     */
    public void setRtcLimitType(int rtcLimitType) {
        rtcLimitType__ = rtcLimitType;
    }
    /**
     * @return the rtcAuid
     */
    public int getRtcAuid() {
        return rtcAuid__;
    }
    /**
     * @param rtcAuid the rtcAuid to set
     */
    public void setRtcAuid(int rtcAuid) {
        rtcAuid__ = rtcAuid;
    }
    /**
     * @return the rtcAdate
     */
    public UDate getRtcAdate() {
        return rtcAdate__;
    }
    /**
     * @param rtcAdate the rtcAdate to set
     */
    public void setRtcAdate(UDate rtcAdate) {
        rtcAdate__ = rtcAdate;
    }
    /**
     * @return the rtcEuid
     */
    public int getRtcEuid() {
        return rtcEuid__;
    }
    /**
     * @param rtcEuid the rtcEuid to set
     */
    public void setRtcEuid(int rtcEuid) {
        rtcEuid__ = rtcEuid;
    }
    /**
     * @return the rtcEdate
     */
    public UDate getRtcEdate() {
        return rtcEdate__;
    }
    /**
     * @param rtcEdate the rtcEdate to set
     */
    public void setRtcEdate(UDate rtcEdate) {
        rtcEdate__ = rtcEdate;
    }

}