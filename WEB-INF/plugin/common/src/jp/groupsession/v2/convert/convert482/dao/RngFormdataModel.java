package jp.groupsession.v2.convert.convert482.dao;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_FORMDATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngFormdataModel implements Serializable {

    /** RNG_SID mapping */
    private int rngSid__;
    /** RFD_SID mapping */
    private int rfdSid__;
    /** RFD_ROWNO mapping */
    private int rfdRowno__;
    /** RFD_COLNO mapping */
    private int rfdColno__;
    /** RFD_VALUE mapping */
    private String rfdValue__;
    /** RFD_AUID mapping */
    private int rfdAuid__;
    /** RFD_ADATE mapping */
    private UDate rfdAdate__;
    /** RFD_EUID mapping */
    private int rfdEuid__;
    /** RFD_EDATE mapping */
    private UDate rfdEdate__;
    /** RFD_ID mapping */
    private String rfdId__;

    /**
     * <p>Default Constructor
     */
    public RngFormdataModel() {
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
     * <p>get RFD_SID value
     * @return RFD_SID value
     */
    public int getRfdSid() {
        return rfdSid__;
    }

    /**
     * <p>set RFD_SID value
     * @param rfdSid RFD_SID value
     */
    public void setRfdSid(int rfdSid) {
        rfdSid__ = rfdSid;
    }

    /**
     * <p>get RFD_ROWNO value
     * @return RFD_ROWNO value
     */
    public int getRfdRowno() {
        return rfdRowno__;
    }

    /**
     * <p>set RFD_ROWNO value
     * @param rfdRowno RFD_ROWNO value
     */
    public void setRfdRowno(int rfdRowno) {
        rfdRowno__ = rfdRowno;
    }

    /**
     * <p>get RFD_COLNO value
     * @return RFD_COLNO value
     */
    public int getRfdColno() {
        return rfdColno__;
    }

    /**
     * <p>set RFD_COLNO value
     * @param rfdColno RFD_COLNO value
     */
    public void setRfdColno(int rfdColno) {
        rfdColno__ = rfdColno;
    }

    /**
     * <p>get RFD_VALUE value
     * @return RFD_VALUE value
     */
    public String getRfdValue() {
        return rfdValue__;
    }

    /**
     * <p>set RFD_VALUE value
     * @param rfdValue RFD_VALUE value
     */
    public void setRfdValue(String rfdValue) {
        rfdValue__ = rfdValue;
    }

    /**
     * <p>get RFD_AUID value
     * @return RFD_AUID value
     */
    public int getRfdAuid() {
        return rfdAuid__;
    }

    /**
     * <p>set RFD_AUID value
     * @param rfdAuid RFD_AUID value
     */
    public void setRfdAuid(int rfdAuid) {
        rfdAuid__ = rfdAuid;
    }

    /**
     * <p>get RFD_ADATE value
     * @return RFD_ADATE value
     */
    public UDate getRfdAdate() {
        return rfdAdate__;
    }

    /**
     * <p>set RFD_ADATE value
     * @param rfdAdate RFD_ADATE value
     */
    public void setRfdAdate(UDate rfdAdate) {
        rfdAdate__ = rfdAdate;
    }

    /**
     * <p>get RFD_EUID value
     * @return RFD_EUID value
     */
    public int getRfdEuid() {
        return rfdEuid__;
    }

    /**
     * <p>set RFD_EUID value
     * @param rfdEuid RFD_EUID value
     */
    public void setRfdEuid(int rfdEuid) {
        rfdEuid__ = rfdEuid;
    }

    /**
     * <p>get RFD_EDATE value
     * @return RFD_EDATE value
     */
    public UDate getRfdEdate() {
        return rfdEdate__;
    }

    /**
     * <p>set RFD_EDATE value
     * @param rfdEdate RFD_EDATE value
     */
    public void setRfdEdate(UDate rfdEdate) {
        rfdEdate__ = rfdEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rngSid__);
        buf.append(",");
        buf.append(rfdSid__);
        buf.append(",");
        buf.append(rfdRowno__);
        buf.append(",");
        buf.append(rfdColno__);
        buf.append(",");
        buf.append(NullDefault.getString(rfdValue__, ""));
        buf.append(",");
        buf.append(rfdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rfdAdate__, ""));
        buf.append(",");
        buf.append(rfdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rfdEdate__, ""));
        return buf.toString();
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

}
