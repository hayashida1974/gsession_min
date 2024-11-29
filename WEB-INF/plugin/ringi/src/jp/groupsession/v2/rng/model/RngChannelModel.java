package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_CHANNEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngChannelModel implements Serializable {

    /** RNG_SID mapping */
    private int rngSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RNC_SORT mapping */
    private int rncSort__;
    /** RNC_STATUS mapping */
    private int rncStatus__;
    /** RNC_COMMENT mapping */
    private String rncComment__;
    /** RNC_RCVDATE mapping */
    private UDate rncRcvdate__;
    /** RNC_CHKDATE mapping */
    private UDate rncChkdate__;
    /** RNC_TYPE mapping */
    private int rncType__;
    /** RNC_AUID mapping */
    private int rncAuid__;
    /** RNC_ADATE mapping */
    private UDate rncAdate__;
    /** RNC_EUID mapping */
    private int rncEuid__;
    /** RNC_EDATE mapping */
    private UDate rncEdate__;

    /**
     * <p>Default Constructor
     */
    public RngChannelModel() {
    }

    /**
     * <p>get RNG_SID value
     * @return RNG_SID value
     */
    public int getRngSid() {
        return rngSid__;
    }

    /**
     * <p>set RNG_SID value
     * @param rngSid RNG_SID value
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
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
     * <p>get RNC_SORT value
     * @return RNC_SORT value
     */
    public int getRncSort() {
        return rncSort__;
    }

    /**
     * <p>set RNC_SORT value
     * @param rncSort RNC_SORT value
     */
    public void setRncSort(int rncSort) {
        rncSort__ = rncSort;
    }

    /**
     * <p>get RNC_STATUS value
     * @return RNC_STATUS value
     */
    public int getRncStatus() {
        return rncStatus__;
    }

    /**
     * <p>set RNC_STATUS value
     * @param rncStatus RNC_STATUS value
     */
    public void setRncStatus(int rncStatus) {
        rncStatus__ = rncStatus;
    }

    /**
     * <p>get RNC_COMMENT value
     * @return RNC_COMMENT value
     */
    public String getRncComment() {
        return rncComment__;
    }

    /**
     * <p>set RNC_COMMENT value
     * @param rncComment RNC_COMMENT value
     */
    public void setRncComment(String rncComment) {
        rncComment__ = rncComment;
    }

    /**
     * <p>get RNC_RCVDATE value
     * @return RNC_RCVDATE value
     */
    public UDate getRncRcvdate() {
        return rncRcvdate__;
    }

    /**
     * <p>set RNC_RCVDATE value
     * @param rncRcvdate RNC_RCVDATE value
     */
    public void setRncRcvdate(UDate rncRcvdate) {
        rncRcvdate__ = rncRcvdate;
    }

    /**
     * <p>get RNC_CHKDATE value
     * @return RNC_CHKDATE value
     */
    public UDate getRncChkdate() {
        return rncChkdate__;
    }

    /**
     * <p>set RNC_CHKDATE value
     * @param rncChkdate RNC_CHKDATE value
     */
    public void setRncChkdate(UDate rncChkdate) {
        rncChkdate__ = rncChkdate;
    }

    /**
     * <p>get RNC_TYPE value
     * @return RNC_TYPE value
     */
    public int getRncType() {
        return rncType__;
    }

    /**
     * <p>set RNC_TYPE value
     * @param rncType RNC_TYPE value
     */
    public void setRncType(int rncType) {
        rncType__ = rncType;
    }

    /**
     * <p>get RNC_AUID value
     * @return RNC_AUID value
     */
    public int getRncAuid() {
        return rncAuid__;
    }

    /**
     * <p>set RNC_AUID value
     * @param rncAuid RNC_AUID value
     */
    public void setRncAuid(int rncAuid) {
        rncAuid__ = rncAuid;
    }

    /**
     * <p>get RNC_ADATE value
     * @return RNC_ADATE value
     */
    public UDate getRncAdate() {
        return rncAdate__;
    }

    /**
     * <p>set RNC_ADATE value
     * @param rncAdate RNC_ADATE value
     */
    public void setRncAdate(UDate rncAdate) {
        rncAdate__ = rncAdate;
    }

    /**
     * <p>get RNC_EUID value
     * @return RNC_EUID value
     */
    public int getRncEuid() {
        return rncEuid__;
    }

    /**
     * <p>set RNC_EUID value
     * @param rncEuid RNC_EUID value
     */
    public void setRncEuid(int rncEuid) {
        rncEuid__ = rncEuid;
    }

    /**
     * <p>get RNC_EDATE value
     * @return RNC_EDATE value
     */
    public UDate getRncEdate() {
        return rncEdate__;
    }

    /**
     * <p>set RNC_EDATE value
     * @param rncEdate RNC_EDATE value
     */
    public void setRncEdate(UDate rncEdate) {
        rncEdate__ = rncEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rngSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rncSort__);
        buf.append(",");
        buf.append(rncStatus__);
        buf.append(",");
        buf.append(NullDefault.getString(rncComment__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rncRcvdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rncChkdate__, ""));
        buf.append(",");
        buf.append(rncType__);
        buf.append(",");
        buf.append(rncAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rncAdate__, ""));
        buf.append(",");
        buf.append(rncEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rncEdate__, ""));
        return buf.toString();
    }

}
