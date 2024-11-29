package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_TEMPLATE_BIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateBinModel implements Serializable {

    /** RTP_SID mapping */
    private int rtpSid__;
    /** BIN_SID mapping */
    private Long binSid__ = Long.valueOf(0);
    /** RTP_VER mapping */
    private int rtpVer__;
    /**
     * <p>Default Constructor
     */
    public RngTemplateBinModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rtpSid__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
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


}
