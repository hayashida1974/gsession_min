package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlLogCountModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** WLC_KBN mapping */
    private int wlcKbn__;
    /** WLC_CNT_TO mapping */
    private int wlcCntTo__;
    /** WLC_CNT_CC mapping */
    private int wlcCntCc__;
    /** WLC_CNT_BCC mapping */
    private int wlcCntBcc__;
    /** WLC_DATE mapping */
    private UDate wlcDate__;

    /**
     * <p>Default Constructor
     */
    public WmlLogCountModel() {
    }

    /**
     * <p>get WAC_SID value
     * @return WAC_SID value
     */
    public int getWacSid() {
        return wacSid__;
    }

    /**
     * <p>set WAC_SID value
     * @param wacSid WAC_SID value
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }

    /**
     * <p>get WLC_KBN value
     * @return WLC_KBN value
     */
    public int getWlcKbn() {
        return wlcKbn__;
    }

    /**
     * <p>set WLC_KBN value
     * @param wlcKbn WLC_KBN value
     */
    public void setWlcKbn(int wlcKbn) {
        wlcKbn__ = wlcKbn;
    }

    /**
     * <p>get WLC_CNT_TO value
     * @return WLC_CNT_TO value
     */
    public int getWlcCntTo() {
        return wlcCntTo__;
    }

    /**
     * <p>set WLC_CNT_TO value
     * @param wlcCntTo WLC_CNT_TO value
     */
    public void setWlcCntTo(int wlcCntTo) {
        wlcCntTo__ = wlcCntTo;
    }

    /**
     * <p>get WLC_CNT_CC value
     * @return WLC_CNT_CC value
     */
    public int getWlcCntCc() {
        return wlcCntCc__;
    }

    /**
     * <p>set WLC_CNT_CC value
     * @param wlcCntCc WLC_CNT_CC value
     */
    public void setWlcCntCc(int wlcCntCc) {
        wlcCntCc__ = wlcCntCc;
    }

    /**
     * <p>get WLC_CNT_BCC value
     * @return WLC_CNT_BCC value
     */
    public int getWlcCntBcc() {
        return wlcCntBcc__;
    }

    /**
     * <p>set WLC_CNT_BCC value
     * @param wlcCntBcc WLC_CNT_BCC value
     */
    public void setWlcCntBcc(int wlcCntBcc) {
        wlcCntBcc__ = wlcCntBcc;
    }

    /**
     * <p>get WLC_DATE value
     * @return WLC_DATE value
     */
    public UDate getWlcDate() {
        return wlcDate__;
    }

    /**
     * <p>set WLC_DATE value
     * @param wlcDate WLC_DATE value
     */
    public void setWlcDate(UDate wlcDate) {
        wlcDate__ = wlcDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(wlcKbn__);
        buf.append(",");
        buf.append(wlcCntTo__);
        buf.append(",");
        buf.append(wlcCntCc__);
        buf.append(",");
        buf.append(wlcCntBcc__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wlcDate__, ""));
        return buf.toString();
    }

}
