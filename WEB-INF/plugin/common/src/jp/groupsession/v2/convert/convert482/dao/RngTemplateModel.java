package jp.groupsession.v2.convert.convert482.dao;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>RNG_TEMPLATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateModel implements Serializable, Comparable<RngTemplateModel> {

    /** RTP_SID mapping */
    private int rtpSid__;
    /** RTP_TYPE mapping */
    private int rtpType__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RTP_TITLE mapping */
    private String rtpTitle__;
    /** RTP_RNG_TITLE mapping */
    private String rtpRngTitle__;
    /** RTP_SORT mapping */
    private int rtpSort__;
    /** RTP_AUID mapping */
    private int rtpAuid__;
    /** RTP_ADATE mapping */
    private UDate rtpAdate__;
    /** RTP_EUID mapping */
    private int rtpEuid__;
    /** RTP_EDATE mapping */
    private UDate rtpEdate__;
    /** RTC_SID mapping */
    private int rtcSid__;
    /** RTP_VER mapping */
    private int rtpVer__;
    /** RTP_MAXVER_KBN mapping */
    private int rtpMaxverKbn__;
    /** RTP_JKBN mapping */
    private int rtpJkbn__;
    /** RTP_IDFORMAT_SID mapping */
    private int rtpIdformatSid__ = -1;
    /** RTP_FORM mapping */
    private String rtpForm__;
    /** RCT_SID mapping */
    private int rctSid__;
    /** RCT_USR_SID mapping */
    private int rctUsrSid__ = -1;
    /** RTP_Biko mapping */
    private String rtpBiko__;
    /** RTP_IDMANUAL mapping */
    private int rtpIdmanual__ = 1;
    /** RTP_SPEC_VER mapping */
    private int rtpSpecVer__;
    /** RTB_TEMP*/
    private int rtbTemp__;

    /** 所属カテゴリ名 */
    private String rtcName__;

    /**
     * <p>Default Constructor
     */
    public RngTemplateModel() {
    }

    /**
     * <p>get RTP_SID value
     * @return RTP_SID value
     */
    public int getRtpSid() {
        return rtpSid__;
    }

    /**
     * <p>set RTP_SID value
     * @param rtpSid RTP_SID value
     */
    public void setRtpSid(int rtpSid) {
        rtpSid__ = rtpSid;
    }

    /**
     * <p>get RTP_TYPE value
     * @return RTP_TYPE value
     */
    public int getRtpType() {
        return rtpType__;
    }

    /**
     * <p>set RTP_TYPE value
     * @param rtpType RTP_TYPE value
     */
    public void setRtpType(int rtpType) {
        rtpType__ = rtpType;
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
     * <p>get RTP_TITLE value
     * @return RTP_TITLE value
     */
    public String getRtpTitle() {
        return rtpTitle__;
    }

    /**
     * <p>set RTP_TITLE value
     * @param rtpTitle RTP_TITLE value
     */
    public void setRtpTitle(String rtpTitle) {
        rtpTitle__ = rtpTitle;
    }


    /**
     * <p>get RTP_SORT value
     * @return RTP_SORT value
     */
    public int getRtpSort() {
        return rtpSort__;
    }

    /**
     * <p>set RTP_SORT value
     * @param rtpSort RTP_SORT value
     */
    public void setRtpSort(int rtpSort) {
        rtpSort__ = rtpSort;
    }

    /**
     * <p>get RTP_AUID value
     * @return RTP_AUID value
     */
    public int getRtpAuid() {
        return rtpAuid__;
    }

    /**
     * <p>set RTP_AUID value
     * @param rtpAuid RTP_AUID value
     */
    public void setRtpAuid(int rtpAuid) {
        rtpAuid__ = rtpAuid;
    }

    /**
     * <p>get RTP_ADATE value
     * @return RTP_ADATE value
     */
    public UDate getRtpAdate() {
        return rtpAdate__;
    }

    /**
     * <p>set RTP_ADATE value
     * @param rtpAdate RTP_ADATE value
     */
    public void setRtpAdate(UDate rtpAdate) {
        rtpAdate__ = rtpAdate;
    }

    /**
     * <p>get RTP_EUID value
     * @return RTP_EUID value
     */
    public int getRtpEuid() {
        return rtpEuid__;
    }

    /**
     * <p>set RTP_EUID value
     * @param rtpEuid RTP_EUID value
     */
    public void setRtpEuid(int rtpEuid) {
        rtpEuid__ = rtpEuid;
    }

    /**
     * <p>get RTP_EDATE value
     * @return RTP_EDATE value
     */
    public UDate getRtpEdate() {
        return rtpEdate__;
    }

    /**
     * <p>set RTP_EDATE value
     * @param rtpEdate RTP_EDATE value
     */
    public void setRtpEdate(UDate rtpEdate) {
        rtpEdate__ = rtpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rtpSid__);
        buf.append(",");
        buf.append(rtpType__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(rtpTitle__, ""));
        buf.append(",");
        buf.append(rtpSort__);
        buf.append(",");
        buf.append(rtpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rtpAdate__, ""));
        buf.append(",");
        buf.append(rtpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rtpEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>指定されたオブジェクトとの比較を行う
     * @param obj 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(RngTemplateModel obj) {
        return getRtpSort() - obj.getRtpSort();
    }

    /**
     * <p>rtpRngTitle を取得します。
     * @return rtpRngTitle
     */
    public String getRtpRngTitle() {
        return rtpRngTitle__;
    }

    /**
     * <p>rtpRngTitle をセットします。
     * @param rtpRngTitle rtpRngTitle
     */
    public void setRtpRngTitle(String rtpRngTitle) {
        rtpRngTitle__ = rtpRngTitle;
    }

    /**
     * @return rtcSid
     */
    public int getRtcSid() {
        return rtcSid__;
    }

    /**
     * @param rtcSid 設定する rtcSid
     */
    public void setRtcSid(int rtcSid) {
        rtcSid__ = rtcSid;
    }

    /**
     * @return rtcName
     */
    public String getRtcName() {
        return rtcName__;
    }

    /**
     * @param rtcName 設定する rtcName
     */
    public void setRtcName(String rtcName) {
        rtcName__ = rtcName;
    }

    /**
     * <p>rtpVer を取得します。
     * @return rtpVer
     */
    public int getRtpVer() {
        return rtpVer__;
    }

    /**
     * <p>rtpVer をセットします。
     * @param rtpVer rtpVer
     */
    public void setRtpVer(int rtpVer) {
        rtpVer__ = rtpVer;
    }

    /**
     * <p>rtpMaxverKbn を取得します。
     * @return rtpMaxverKbn
     */
    public int getRtpMaxverKbn() {
        return rtpMaxverKbn__;
    }

    /**
     * <p>rtpMaxverKbn をセットします。
     * @param rtpMaxverKbn rtpMaxverKbn
     */
    public void setRtpMaxverKbn(int rtpMaxverKbn) {
        rtpMaxverKbn__ = rtpMaxverKbn;
    }

    /**
     * <p>rtpJkbn を取得します。
     * @return rtpJkbn
     */
    public int getRtpJkbn() {
        return rtpJkbn__;
    }

    /**
     * <p>rtpJkbn をセットします。
     * @param rtpJkbn rtpJkbn
     */
    public void setRtpJkbn(int rtpJkbn) {
        rtpJkbn__ = rtpJkbn;
    }

    /**
     * <p>rtpIdformatSid を取得します。
     * @return rtpIdformatSid
     */
    public int getRtpIdformatSid() {
        return rtpIdformatSid__;
    }

    /**
     * <p>rtpIdformatSid をセットします。
     * @param rtpIdformatSid rtpIdformatSid
     */
    public void setRtpIdformatSid(int rtpIdformatSid) {
        rtpIdformatSid__ = rtpIdformatSid;
    }

    /**
     * <p>rtpForm を取得します。
     * @return rtpForm
     */
    public String getRtpForm() {
        return rtpForm__;
    }

    /**
     * <p>rtpForm をセットします。
     * @param rtpForm rtpForm
     */
    public void setRtpForm(String rtpForm) {
        rtpForm__ = rtpForm;
    }

    /**
     * <p>rctSid を取得します。
     * @return rctSid
     */
    public int getRctSid() {
        return rctSid__;
    }

    /**
     * <p>rctSid をセットします。
     * @param rctSid rctSid
     */
    public void setRctSid(int rctSid) {
        rctSid__ = rctSid;
    }

    /**
     * <p>rctUsrSid を取得します。
     * @return rctUsrSid
     */
    public int getRctUsrSid() {
        return rctUsrSid__;
    }

    /**
     * <p>rctUsrSid をセットします。
     * @param rctUsrSid rctUsrSid
     */
    public void setRctUsrSid(int rctUsrSid) {
        rctUsrSid__ = rctUsrSid;
    }

    /**
     * <p>rtpBiko を取得します。
     * @return rtpBiko
     */
    public String getRtpBiko() {
        return rtpBiko__;
    }

    /**
     * <p>rtpBiko をセットします。
     * @param rtpBiko rtpBiko
     */
    public void setRtpBiko(String rtpBiko) {
        rtpBiko__ = rtpBiko;
    }

    /**
     * <p>rtpIdmanual を取得します。
     * @return rtpIdmanual
     */
    public int getRtpIdmanual() {
        return rtpIdmanual__;
    }

    /**
     * <p>rtpIdmanual をセットします。
     * @param rtpIdmanual rtpIdmanual
     */
    public void setRtpIdmanual(int rtpIdmanual) {
        rtpIdmanual__ = rtpIdmanual;
    }

    /**
     * <p>rtpSpecVer を取得します。
     * @return rtpSpecVer
     */
    public int getRtpSpecVer() {
        return rtpSpecVer__;
    }

    /**
     * <p>rtpSpecVer をセットします。
     * @param rtpSpecVer rtpSpecVer
     */
    public void setRtpSpecVer(int rtpSpecVer) {
        rtpSpecVer__ = rtpSpecVer;
    }

    /**
     * <p>rtbTemp を取得します。
     * @return rtbTemp
     * @see jp.groupsession.v2.convert.convert482.dao.RngTemplateModel#rtbTemp__
     */
    public int getRtbTemp() {
        return rtbTemp__;
    }

    /**
     * <p>rtbTemp をセットします。
     * @param rtbTemp rtbTemp
     * @see jp.groupsession.v2.convert.convert482.dao.RngTemplateModel#rtbTemp__
     */
    public void setRtbTemp(int rtbTemp) {
        rtbTemp__ = rtbTemp;
    }

}
