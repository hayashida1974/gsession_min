package jp.groupsession.v2.wml.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考] WML_MAILDATAから取得した情報を格納する
 *
 * @author JTS
 */
public class WmlMaildataModel implements Serializable {

    /** WMD_SID mapping */
    private long wmdSid__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WMD_MID mapping */
    private String wmdMid__;
    /** WMD_MSIZE mapping */
    private int wmdMsize__;
    /** WMD_SUBJECT mapping */
    private String wmdSubject__;
    /** WMD_MTEXT mapping */
    private String wmdMtext__;
    /** WMD_DATE mapping */
    private UDate wmdDate__;
    /** WMD_TEMPFLG mapping */
    private int wmdTempflg__;
    /** WMD_REPFLG mapping */
    private int wmdRepflg__;
    /** WMD_FWDFLG mapping */
    private int wmdFwdflg__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);

    /**
     * <p>Default Constructor
     */
    public WmlMaildataModel() {
    }

    /**
     * <p>get WMD_SID value
     * @return WMD_SID value
     */
    public long getWmdSid() {
        return wmdSid__;
    }

    /**
     * <p>set WMD_SID value
     * @param wmdSid WMD_SID value
     */
    public void setWmdSid(long wmdSid) {
        wmdSid__ = wmdSid;
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
     * <p>get WMD_MID value
     * @return WMD_MID value
     */
    public String getWmdMid() {
        return wmdMid__;
    }

    /**
     * <p>set WMD_MID value
     * @param wmdMid WMD_MID value
     */
    public void setWmdMid(String wmdMid) {
        wmdMid__ = wmdMid;
    }

    /**
     * <p>get WMD_MSIZE value
     * @return WMD_MSIZE value
     */
    public int getWmdMsize() {
        return wmdMsize__;
    }

    /**
     * <p>set WMD_MSIZE value
     * @param wmdMsize WMD_MSIZE value
     */
    public void setWmdMsize(int wmdMsize) {
        wmdMsize__ = wmdMsize;
    }

    /**
     * <p>get WMD_SUBJECT value
     * @return WMD_SUBJECT value
     */
    public String getWmdSubject() {
        return wmdSubject__;
    }

    /**
     * <p>set WMD_SUBJECT value
     * @param wmdSubject WMD_SUBJECT value
     */
    public void setWmdSubject(String wmdSubject) {
        wmdSubject__ = wmdSubject;
    }

    /**
     * <p>get WMD_MTEXT value
     * @return WMD_MTEXT value
     */
    public String getWmdMtext() {
        return wmdMtext__;
    }

    /**
     * <p>set WMD_MTEXT value
     * @param wmdMtext WMD_MTEXT value
     */
    public void setWmdMtext(String wmdMtext) {
        wmdMtext__ = wmdMtext;
    }

    /**
     * <p>get WMD_DATE value
     * @return WMD_DATE value
     */
    public UDate getWmdDate() {
        return wmdDate__;
    }

    /**
     * <p>set WMD_DATE value
     * @param wmdDate WMD_DATE value
     */
    public void setWmdDate(UDate wmdDate) {
        wmdDate__ = wmdDate;
    }

    /**
     * <p>get WMD_TEMPFLG value
     * @return WMD_TEMPFLG value
     */
    public int getWmdTempflg() {
        return wmdTempflg__;
    }

    /**
     * <p>set WMD_TEMPFLG value
     * @param wmdTempflg WMD_TEMPFLG value
     */
    public void setWmdTempflg(int wmdTempflg) {
        wmdTempflg__ = wmdTempflg;
    }

    /**
     * <p>get WMD_REPFLG value
     * @return WMD_REPFLG value
     */
    public int getWmdRepflg() {
        return wmdRepflg__;
    }

    /**
     * <p>set WMD_REPFLG value
     * @param wmdRepflg WMD_REPFLG value
     */
    public void setWmdRepflg(int wmdRepflg) {
        wmdRepflg__ = wmdRepflg;
    }

    /**
     * <p>get WMD_FWDFLG value
     * @return WMD_FWDFLG value
     */
    public int getWmdFwdflg() {
        return wmdFwdflg__;
    }

    /**
     * <p>set WMD_FWDFLG value
     * @param wmdFwdflg WMD_FWDFLG value
     */
    public void setWmdFwdflg(int wmdFwdflg) {
        wmdFwdflg__ = wmdFwdflg;
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
        buf.append(wmdSid__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wmdMid__, ""));
        buf.append(",");
        buf.append(wmdMsize__);
        buf.append(",");
        buf.append(NullDefault.getString(wmdSubject__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wmdMtext__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wmdDate__, ""));
        buf.append(",");
        buf.append(wmdTempflg__);
        buf.append(",");
        buf.append(wmdRepflg__);
        buf.append(",");
        buf.append(wmdFwdflg__);
        buf.append(",");
        buf.append(binSid__);
        return buf.toString();
    }

}
