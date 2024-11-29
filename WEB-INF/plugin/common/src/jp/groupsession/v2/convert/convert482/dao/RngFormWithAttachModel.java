package jp.groupsession.v2.convert.convert482.dao;

import jp.co.sjts.util.date.UDate;

/**
 *
 * <br>[機  能] 稟議およびその稟議に対する添付情報を格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngFormWithAttachModel  {

    /** 稟議SID */
    private int rngSid__;
    /** 稟議フォームSID */
    private int rfdSid__;
    /** 稟議フォームID */
    private String rfdId__;
    /** 稟議フォーム 内容 */
    private String rfdValue__;
    /** 稟議申請者SID */
    private int rfdAuid__;
    /** 稟議申請日 */
    private UDate rfdAdate__;
    /** 稟議更新者SID */
    private int rfdEuid__;
    /** 稟議更新日 */
    private UDate rfdEdate__;
    /** テンプレートSID */
    private int rtpSid__;
    /** 添付ファイルバイナリSID */
    private Long binSid__;
    /** テンプレート種別 */
    private int rtpType__;



    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>rngSid をセットします。
     * @param rngSid rngSid
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }
    /**
     * <p>rfdSid を取得します。
     * @return rfdSid
     */
    public int getRfdSid() {
        return rfdSid__;
    }
    /**
     * <p>rfdSid をセットします。
     * @param rfdSid rfdSid
     */
    public void setRfdSid(int rfdSid) {
        rfdSid__ = rfdSid;
    }
    /**
     * <p>rfdId を取得します。
     * @return rfdId
     */
    public String getRfdId() {
        return rfdId__;
    }
    /**
     * <p>rfdId をセットします。
     * @param rfdId rfdId
     */
    public void setRfdId(String rfdId) {
        rfdId__ = rfdId;
    }
    /**
     * <p>rfdValue を取得します。
     * @return rfdValue
     */
    public String getRfdValue() {
        return rfdValue__;
    }
    /**
     * <p>rfdValue をセットします。
     * @param rfdValue rfdValue
     */
    public void setRfdValue(String rfdValue) {
        rfdValue__ = rfdValue;
    }
    /**
     * <p>rfdAuid を取得します。
     * @return rfdAuid
     */
    public int getRfdAuid() {
        return rfdAuid__;
    }
    /**
     * <p>rfdAuid をセットします。
     * @param rfdAuid rfdAuid
     */
    public void setRfdAuid(int rfdAuid) {
        rfdAuid__ = rfdAuid;
    }
    /**
     * <p>rfdAdate を取得します。
     * @return rfdAdate
     */
    public UDate getRfdAdate() {
        return rfdAdate__;
    }
    /**
     * <p>rfdAdate をセットします。
     * @param rfdAdate rfdAdate
     */
    public void setRfdAdate(UDate rfdAdate) {
        rfdAdate__ = rfdAdate;
    }
    /**
     * <p>rfdEuid を取得します。
     * @return rfdEuid
     */
    public int getRfdEuid() {
        return rfdEuid__;
    }
    /**
     * <p>rfdEuid をセットします。
     * @param rfdEuid rfdEuid
     */
    public void setRfdEuid(int rfdEuid) {
        rfdEuid__ = rfdEuid;
    }
    /**
     * <p>rfdEdate を取得します。
     * @return rfdEdate
     */
    public UDate getRfdEdate() {
        return rfdEdate__;
    }
    /**
     * <p>rfdEdate をセットします。
     * @param rfdEdate rfdEdate
     */
    public void setRfdEdate(UDate rfdEdate) {
        rfdEdate__ = rfdEdate;
    }
    /**
     * <p>rtpSid を取得します。
     * @return rtpSid
     */
    public int getRtpSid() {
        return rtpSid__;
    }
    /**
     * <p>rtpSid をセットします。
     * @param rtpSid rtpSid
     */
    public void setRtpSid(int rtpSid) {
        rtpSid__ = rtpSid;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>rtpType を取得します。
     * @return rtpType
     */
    public int getRtpType() {
        return rtpType__;
    }
    /**
     * <p>rtpType をセットします。
     * @param rtpType rtpType
     */
    public void setRtpType(int rtpType) {
        rtpType__ = rtpType;
    }


}
