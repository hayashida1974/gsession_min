package jp.groupsession.v2.convert.convert430.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CIR_VIEW Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtCirViewModel implements Serializable {

    /** CIF_SID mapping */
    private int cifSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** CVW_MEMO mapping */
    private String cvwMemo__;
    /** CVW_CONF mapping */
    private int cvwConf__;
    /** CVW_CONF_DATE mapping */
    private UDate cvwConfDate__;
    /** CVW_DSP mapping */
    private int cvwDsp__;
    /** CVW_AUID mapping */
    private int cvwAuid__;
    /** CVW_ADATE mapping */
    private UDate cvwAdate__;
    /** CVW_EUID mapping */
    private int cvwEuid__;
    /** CVW_EDATE mapping */
    private UDate cvwEdate__;

    /**
     * <p>Default Constructor
     */
    public CvtCirViewModel() {
    }

    /**
     * <p>get CIF_SID value
     * @return CIF_SID value
     */
    public int getCifSid() {
        return cifSid__;
    }

    /**
     * <p>set CIF_SID value
     * @param cifSid CIF_SID value
     */
    public void setCifSid(int cifSid) {
        cifSid__ = cifSid;
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
     * <p>get CVW_MEMO value
     * @return CVW_MEMO value
     */
    public String getCvwMemo() {
        return cvwMemo__;
    }

    /**
     * <p>set CVW_MEMO value
     * @param cvwMemo CVW_MEMO value
     */
    public void setCvwMemo(String cvwMemo) {
        cvwMemo__ = cvwMemo;
    }

    /**
     * <p>get CVW_CONF value
     * @return CVW_CONF value
     */
    public int getCvwConf() {
        return cvwConf__;
    }

    /**
     * <p>set CVW_CONF value
     * @param cvwConf CVW_CONF value
     */
    public void setCvwConf(int cvwConf) {
        cvwConf__ = cvwConf;
    }

    /**
     * <p>get CVW_DSP value
     * @return CVW_DSP value
     */
    public int getCvwDsp() {
        return cvwDsp__;
    }

    /**
     * <p>set CVW_DSP value
     * @param cvwDsp CVW_DSP value
     */
    public void setCvwDsp(int cvwDsp) {
        cvwDsp__ = cvwDsp;
    }

    /**
     * <p>get CVW_AUID value
     * @return CVW_AUID value
     */
    public int getCvwAuid() {
        return cvwAuid__;
    }

    /**
     * <p>set CVW_AUID value
     * @param cvwAuid CVW_AUID value
     */
    public void setCvwAuid(int cvwAuid) {
        cvwAuid__ = cvwAuid;
    }

    /**
     * <p>get CVW_ADATE value
     * @return CVW_ADATE value
     */
    public UDate getCvwAdate() {
        return cvwAdate__;
    }

    /**
     * <p>set CVW_ADATE value
     * @param cvwAdate CVW_ADATE value
     */
    public void setCvwAdate(UDate cvwAdate) {
        cvwAdate__ = cvwAdate;
    }

    /**
     * <p>get CVW_EUID value
     * @return CVW_EUID value
     */
    public int getCvwEuid() {
        return cvwEuid__;
    }

    /**
     * <p>set CVW_EUID value
     * @param cvwEuid CVW_EUID value
     */
    public void setCvwEuid(int cvwEuid) {
        cvwEuid__ = cvwEuid;
    }

    /**
     * <p>get CVW_EDATE value
     * @return CVW_EDATE value
     */
    public UDate getCvwEdate() {
        return cvwEdate__;
    }

    /**
     * <p>set CVW_EDATE value
     * @param cvwEdate CVW_EDATE value
     */
    public void setCvwEdate(UDate cvwEdate) {
        cvwEdate__ = cvwEdate;
    }

    /**
     * <p>cvwConfDate を取得します。
     * @return cvwConfDate
     */
    public UDate getCvwConfDate() {
        return cvwConfDate__;
    }

    /**
     * <p>cvwConfDate をセットします。
     * @param cvwConfDate cvwConfDate
     */
    public void setCvwConfDate(UDate cvwConfDate) {
        cvwConfDate__ = cvwConfDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(cifSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cvwMemo__, ""));
        buf.append(",");
        buf.append(cvwConf__);
        buf.append(",");
        buf.append(cvwDsp__);
        buf.append(",");
        buf.append(cvwAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cvwAdate__, ""));
        buf.append(",");
        buf.append(cvwEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cvwEdate__, ""));
        return buf.toString();
    }

}
