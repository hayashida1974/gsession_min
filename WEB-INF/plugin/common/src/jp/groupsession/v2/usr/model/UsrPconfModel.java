package jp.groupsession.v2.usr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>USR_PCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class UsrPconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** UPC_MAX_DSP mapping */
    private int upcMaxDsp__;
    /** UPC_AUID mapping */
    private int upcAuid__;
    /** UPC_ADATE mapping */
    private UDate upcAdate__;
    /** UPC_EUID mapping */
    private int upcEuid__;
    /** UPC_EDATE mapping */
    private UDate upcEdate__;

    /**
     * <p>Default Constructor
     */
    public UsrPconfModel() {
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
     * <p>get UPC_MAX_DSP value
     * @return UPC_MAX_DSP value
     */
    public int getUpcMaxDsp() {
        return upcMaxDsp__;
    }

    /**
     * <p>set UPC_MAX_DSP value
     * @param upcMaxDsp UPC_MAX_DSP value
     */
    public void setUpcMaxDsp(int upcMaxDsp) {
        upcMaxDsp__ = upcMaxDsp;
    }

    /**
     * <p>get UPC_AUID value
     * @return UPC_AUID value
     */
    public int getUpcAuid() {
        return upcAuid__;
    }

    /**
     * <p>set UPC_AUID value
     * @param upcAuid UPC_AUID value
     */
    public void setUpcAuid(int upcAuid) {
        upcAuid__ = upcAuid;
    }

    /**
     * <p>get UPC_ADATE value
     * @return UPC_ADATE value
     */
    public UDate getUpcAdate() {
        return upcAdate__;
    }

    /**
     * <p>set UPC_ADATE value
     * @param upcAdate UPC_ADATE value
     */
    public void setUpcAdate(UDate upcAdate) {
        upcAdate__ = upcAdate;
    }

    /**
     * <p>get UPC_EUID value
     * @return UPC_EUID value
     */
    public int getUpcEuid() {
        return upcEuid__;
    }

    /**
     * <p>set UPC_EUID value
     * @param upcEuid UPC_EUID value
     */
    public void setUpcEuid(int upcEuid) {
        upcEuid__ = upcEuid;
    }

    /**
     * <p>get UPC_EDATE value
     * @return UPC_EDATE value
     */
    public UDate getUpcEdate() {
        return upcEdate__;
    }

    /**
     * <p>set UPC_EDATE value
     * @param upcEdate UPC_EDATE value
     */
    public void setUpcEdate(UDate upcEdate) {
        upcEdate__ = upcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(upcMaxDsp__);
        buf.append(",");
        buf.append(upcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(upcAdate__, ""));
        buf.append(",");
        buf.append(upcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(upcEdate__, ""));
        return buf.toString();
    }

}
