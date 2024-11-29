package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_MAILDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WMD_TITLE mapping */
    private String wmdTitle__;
    /** WMD_SDATE mapping */
    private UDate wmdSdate__;
    /** WMD_FROM mapping */
    private String wmdFrom__;
    /** WMD_TEMPFLG mapping */
    private int wmdTempflg__;
    /** WMD_STATUS mapping */
    private int wmdStatus__;
    /** WMD_REPLY mapping */
    private int wmdReply__;
    /** WMD_FORWARD mapping */
    private int wmdForward__;
    /** WMD_READED mapping */
    private int wmdReaded__;
    /** WDR_SID mapping */
    private long wdrSid__;
    /** WMD_SIZE mapping */
    private long wmdSize__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WMD_ADATE mapping */
    private UDate wmdAdate__;
    /** WMD_ORIGIN mapping */
    private long wmdOrigin__;
    /** WMD_EDIT_TYPE mapping */
    private int wmdEditType__;

    /**
     * <p>Default Constructor
     */
    public WmlMaildataModel() {
    }

    /**
     * <p>get WMD_MAILNUM value
     * @return WMD_MAILNUM value
     */
    public long getWmdMailnum() {
        return wmdMailnum__;
    }

    /**
     * <p>set WMD_MAILNUM value
     * @param wmdMailnum WMD_MAILNUM value
     */
    public void setWmdMailnum(long wmdMailnum) {
        wmdMailnum__ = wmdMailnum;
    }

    /**
     * <p>get WMD_TITLE value
     * @return WMD_TITLE value
     */
    public String getWmdTitle() {
        return wmdTitle__;
    }

    /**
     * <p>set WMD_TITLE value
     * @param wmdTitle WMD_TITLE value
     */
    public void setWmdTitle(String wmdTitle) {
        wmdTitle__ = wmdTitle;
    }

    /**
     * <p>get WMD_SDATE value
     * @return WMD_SDATE value
     */
    public UDate getWmdSdate() {
        return wmdSdate__;
    }

    /**
     * <p>set WMD_SDATE value
     * @param wmdSdate WMD_SDATE value
     */
    public void setWmdSdate(UDate wmdSdate) {
        wmdSdate__ = wmdSdate;
    }

    /**
     * <p>get WMD_FROM value
     * @return WMD_FROM value
     */
    public String getWmdFrom() {
        return wmdFrom__;
    }

    /**
     * <p>set WMD_FROM value
     * @param wmdFrom WMD_FROM value
     */
    public void setWmdFrom(String wmdFrom) {
        wmdFrom__ = wmdFrom;
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
     * <p>get WMD_STATUS value
     * @return WMD_STATUS value
     */
    public int getWmdStatus() {
        return wmdStatus__;
    }

    /**
     * <p>set WMD_STATUS value
     * @param wmdStatus WMD_STATUS value
     */
    public void setWmdStatus(int wmdStatus) {
        wmdStatus__ = wmdStatus;
    }

    /**
     * <p>get WMD_REPLY value
     * @return WMD_REPLY value
     */
    public int getWmdReply() {
        return wmdReply__;
    }

    /**
     * <p>set WMD_REPLY value
     * @param wmdReply WMD_REPLY value
     */
    public void setWmdReply(int wmdReply) {
        wmdReply__ = wmdReply;
    }

    /**
     * <p>get WMD_FORWARD value
     * @return WMD_FORWARD value
     */
    public int getWmdForward() {
        return wmdForward__;
    }

    /**
     * <p>set WMD_FORWARD value
     * @param wmdForward WMD_FORWARD value
     */
    public void setWmdForward(int wmdForward) {
        wmdForward__ = wmdForward;
    }

    /**
     * <p>get WMD_READED value
     * @return WMD_READED value
     */
    public int getWmdReaded() {
        return wmdReaded__;
    }

    /**
     * <p>set WMD_READED value
     * @param wmdReaded WMD_READED value
     */
    public void setWmdReaded(int wmdReaded) {
        wmdReaded__ = wmdReaded;
    }

    /**
     * <p>get WDR_SID value
     * @return WDR_SID value
     */
    public long getWdrSid() {
        return wdrSid__;
    }

    /**
     * <p>set WDR_SID value
     * @param wdrSid WDR_SID value
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }

    /**
     * <p>get WMD_SIZE value
     * @return WMD_SIZE value
     */
    public long getWmdSize() {
        return wmdSize__;
    }

    /**
     * <p>set WMD_SIZE value
     * @param wmdSize WMD_SIZE value
     */
    public void setWmdSize(long wmdSize) {
        wmdSize__ = wmdSize;
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
     * <p>get WMD_ADATE value
     * @return WMD_ADATE value
     */
    public UDate getWmdAdate() {
        return wmdAdate__;
    }

    /**
     * <p>set WMD_ADATE value
     * @param wmdAdate WMD_ADATE value
     */
    public void setWmdAdate(UDate wmdAdate) {
        wmdAdate__ = wmdAdate;
    }

    /**
     * <p>get WMD_ORIGIN value
     * @return WMD_ORIGIN value
     */
    public long getWmdOrigin() {
        return wmdOrigin__;
    }

    /**
     * <p>set WMD_ORIGIN value
     * @param wmdOrigin WMD_ORIGIN value
     */
    public void setWmdOrigin(long wmdOrigin) {
        wmdOrigin__ = wmdOrigin;
    }

    /**
     * <p>get WMD_EDIT_TYPE value
     * @return WMD_EDIT_TYPE value
     */
    public int getWmdEditType() {
        return wmdEditType__;
    }

    /**
     * <p>set WMD_EDIT_TYPE value
     * @param wmdEditType WMD_EDIT_TYPE value
     */
    public void setWmdEditType(int wmdEditType) {
        wmdEditType__ = wmdEditType;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(NullDefault.getString(wmdTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wmdSdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wmdFrom__, ""));
        buf.append(",");
        buf.append(wmdTempflg__);
        buf.append(",");
        buf.append(wmdStatus__);
        buf.append(",");
        buf.append(wmdReply__);
        buf.append(",");
        buf.append(wmdForward__);
        buf.append(",");
        buf.append(wmdReaded__);
        buf.append(",");
        buf.append(wdrSid__);
        buf.append(",");
        buf.append(wmdSize__);
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wmdAdate__, ""));
        buf.append(",");
        buf.append(wmdOrigin__);
        buf.append(",");
        buf.append(wmdEditType__);
        return buf.toString();
    }

}
