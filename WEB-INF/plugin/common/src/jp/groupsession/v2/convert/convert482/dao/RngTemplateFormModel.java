package jp.groupsession.v2.convert.convert482.dao;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_TEMPLATE_FORM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateFormModel implements Serializable {

    /** RTP_SID mapping */
    private int rtpSid__;
    /** RTP_VER mapping */
    private int rtpVer__;
    /** RTF_SID mapping */
    private int rtfSid__;
    /** RTF_ID mapping */
    private String rtfId__;
    /** RTF_TITLE mapping */
    private String rtfTitle__;
    /** RTF_REQUIRE mapping */
    private int rtfRequire__;
    /** RTF_TYPE mapping */
    private int rtfType__;
    /** RTF_BODY mapping */
    private String rtfBody__;

    /**
     * <p>Default Constructor
     */
    public RngTemplateFormModel() {
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
     * <p>get RTP_VER value
     * @return RTP_VER value
     */
    public int getRtpVer() {
        return rtpVer__;
    }

    /**
     * <p>set RTP_VER value
     * @param rtpVer RTP_VER value
     */
    public void setRtpVer(int rtpVer) {
        rtpVer__ = rtpVer;
    }

    /**
     * <p>get RTF_SID value
     * @return RTF_SID value
     */
    public int getRtfSid() {
        return rtfSid__;
    }

    /**
     * <p>set RTF_SID value
     * @param rtfSid RTF_SID value
     */
    public void setRtfSid(int rtfSid) {
        rtfSid__ = rtfSid;
    }

    /**
     * <p>get RTF_ID value
     * @return RTF_ID value
     */
    public String getRtfId() {
        return rtfId__;
    }

    /**
     * <p>set RTF_ID value
     * @param rtfId RTF_ID value
     */
    public void setRtfId(String rtfId) {
        rtfId__ = rtfId;
    }

    /**
     * <p>get RTF_TITLE value
     * @return RTF_TITLE value
     */
    public String getRtfTitle() {
        return rtfTitle__;
    }

    /**
     * <p>set RTF_TITLE value
     * @param rtfTitle RTF_TITLE value
     */
    public void setRtfTitle(String rtfTitle) {
        rtfTitle__ = rtfTitle;
    }

    /**
     * <p>get RTF_REQUIRE value
     * @return RTF_REQUIRE value
     */
    public int getRtfRequire() {
        return rtfRequire__;
    }

    /**
     * <p>set RTF_REQUIRE value
     * @param rtfRequire RTF_REQUIRE value
     */
    public void setRtfRequire(int rtfRequire) {
        rtfRequire__ = rtfRequire;
    }

    /**
     * <p>get RTF_TYPE value
     * @return RTF_TYPE value
     */
    public int getRtfType() {
        return rtfType__;
    }

    /**
     * <p>set RTF_TYPE value
     * @param rtfType RTF_TYPE value
     */
    public void setRtfType(int rtfType) {
        rtfType__ = rtfType;
    }

    /**
     * <p>get RTF_BODY value
     * @return RTF_BODY value
     */
    public String getRtfBody() {
        return rtfBody__;
    }

    /**
     * <p>set RTF_BODY value
     * @param rtfBody RTF_BODY value
     */
    public void setRtfBody(String rtfBody) {
        rtfBody__ = rtfBody;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rtpSid__);
        buf.append(",");
        buf.append(rtpVer__);
        buf.append(",");
        buf.append(rtfSid__);
        buf.append(",");
        buf.append(NullDefault.getString(rtfId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(rtfTitle__, ""));
        buf.append(",");
        buf.append(rtfRequire__);
        buf.append(",");
        buf.append(rtfType__);
        buf.append(",");
        buf.append(NullDefault.getString(rtfBody__, ""));
        return buf.toString();
    }

}
