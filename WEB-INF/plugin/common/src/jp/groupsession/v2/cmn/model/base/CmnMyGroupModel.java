package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MY_GROUP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMyGroupModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MGP_SID mapping */
    private int mgpSid__;
    /** MGP_NAME mapping */
    private String mgpName__;
    /** MGP_MEMO mapping */
    private String mgpMemo__;
    /** MGP_AUID mapping */
    private int mgpAuid__;
    /** MGP_ADATE mapping */
    private UDate mgpAdate__;
    /** MGP_EUID mapping */
    private int mgpEuid__;
    /** MGP_EDATE mapping */
    private UDate mgpEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMyGroupModel() {
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
     * <p>get MGP_SID value
     * @return MGP_SID value
     */
    public int getMgpSid() {
        return mgpSid__;
    }

    /**
     * <p>set MGP_SID value
     * @param mgpSid MGP_SID value
     */
    public void setMgpSid(int mgpSid) {
        mgpSid__ = mgpSid;
    }

    /**
     * <p>get MGP_NAME value
     * @return MGP_NAME value
     */
    public String getMgpName() {
        return mgpName__;
    }

    /**
     * <p>set MGP_NAME value
     * @param mgpName MGP_NAME value
     */
    public void setMgpName(String mgpName) {
        mgpName__ = mgpName;
    }

    /**
     * <p>get MGP_MEMO value
     * @return MGP_MEMO value
     */
    public String getMgpMemo() {
        return mgpMemo__;
    }

    /**
     * <p>set MGP_MEMO value
     * @param mgpMemo MGP_MEMO value
     */
    public void setMgpMemo(String mgpMemo) {
        mgpMemo__ = mgpMemo;
    }

    /**
     * <p>get MGP_AUID value
     * @return MGP_AUID value
     */
    public int getMgpAuid() {
        return mgpAuid__;
    }

    /**
     * <p>set MGP_AUID value
     * @param mgpAuid MGP_AUID value
     */
    public void setMgpAuid(int mgpAuid) {
        mgpAuid__ = mgpAuid;
    }

    /**
     * <p>get MGP_ADATE value
     * @return MGP_ADATE value
     */
    public UDate getMgpAdate() {
        return mgpAdate__;
    }

    /**
     * <p>set MGP_ADATE value
     * @param mgpAdate MGP_ADATE value
     */
    public void setMgpAdate(UDate mgpAdate) {
        mgpAdate__ = mgpAdate;
    }

    /**
     * <p>get MGP_EUID value
     * @return MGP_EUID value
     */
    public int getMgpEuid() {
        return mgpEuid__;
    }

    /**
     * <p>set MGP_EUID value
     * @param mgpEuid MGP_EUID value
     */
    public void setMgpEuid(int mgpEuid) {
        mgpEuid__ = mgpEuid;
    }

    /**
     * <p>get MGP_EDATE value
     * @return MGP_EDATE value
     */
    public UDate getMgpEdate() {
        return mgpEdate__;
    }

    /**
     * <p>set MGP_EDATE value
     * @param mgpEdate MGP_EDATE value
     */
    public void setMgpEdate(UDate mgpEdate) {
        mgpEdate__ = mgpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mgpSid__);
        buf.append(",");
        buf.append(NullDefault.getString(mgpName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(mgpMemo__, ""));
        buf.append(",");
        buf.append(mgpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mgpAdate__, ""));
        buf.append(",");
        buf.append(mgpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mgpEdate__, ""));
        return buf.toString();
    }

}
