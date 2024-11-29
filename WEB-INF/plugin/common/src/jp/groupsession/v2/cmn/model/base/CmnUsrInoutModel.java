package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_USR_INOUT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrInoutModel implements Serializable {

    /** UIO_SID mapping */
    private int uioSid__;
    /** UIO_STATUS mapping */
    private int uioStatus__;
    /** UIO_BIKO mapping */
    private String uioBiko__;
    /** UIO_AUID mapping */
    private int uioAuid__;
    /** UIO_ADATE mapping */
    private UDate uioAdate__;
    /** UIO_EUID mapping */
    private int uioEuid__;
    /** UIO_EDATE mapping */
    private UDate uioEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrInoutModel() {
    }

    /**
     * <p>get UIO_SID value
     * @return UIO_SID value
     */
    public int getUioSid() {
        return uioSid__;
    }

    /**
     * <p>set UIO_SID value
     * @param uioSid UIO_SID value
     */
    public void setUioSid(int uioSid) {
        uioSid__ = uioSid;
    }

    /**
     * <p>get UIO_STATUS value
     * @return UIO_STATUS value
     */
    public int getUioStatus() {
        return uioStatus__;
    }

    /**
     * <p>set UIO_STATUS value
     * @param uioStatus UIO_STATUS value
     */
    public void setUioStatus(int uioStatus) {
        uioStatus__ = uioStatus;
    }

    /**
     * <p>get UIO_BIKO value
     * @return UIO_BIKO value
     */
    public String getUioBiko() {
        return uioBiko__;
    }

    /**
     * <p>set UIO_BIKO value
     * @param uioBiko UIO_BIKO value
     */
    public void setUioBiko(String uioBiko) {
        uioBiko__ = uioBiko;
    }

    /**
     * <p>get UIO_AUID value
     * @return UIO_AUID value
     */
    public int getUioAuid() {
        return uioAuid__;
    }

    /**
     * <p>set UIO_AUID value
     * @param uioAuid UIO_AUID value
     */
    public void setUioAuid(int uioAuid) {
        uioAuid__ = uioAuid;
    }

    /**
     * <p>get UIO_ADATE value
     * @return UIO_ADATE value
     */
    public UDate getUioAdate() {
        return uioAdate__;
    }

    /**
     * <p>set UIO_ADATE value
     * @param uioAdate UIO_ADATE value
     */
    public void setUioAdate(UDate uioAdate) {
        uioAdate__ = uioAdate;
    }

    /**
     * <p>get UIO_EUID value
     * @return UIO_EUID value
     */
    public int getUioEuid() {
        return uioEuid__;
    }

    /**
     * <p>set UIO_EUID value
     * @param uioEuid UIO_EUID value
     */
    public void setUioEuid(int uioEuid) {
        uioEuid__ = uioEuid;
    }

    /**
     * <p>get UIO_EDATE value
     * @return UIO_EDATE value
     */
    public UDate getUioEdate() {
        return uioEdate__;
    }

    /**
     * <p>set UIO_EDATE value
     * @param uioEdate UIO_EDATE value
     */
    public void setUioEdate(UDate uioEdate) {
        uioEdate__ = uioEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(uioSid__);
        buf.append(",");
        buf.append(uioStatus__);
        buf.append(",");
        buf.append(uioBiko__);
        buf.append(",");
        buf.append(uioAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uioAdate__, ""));
        buf.append(",");
        buf.append(uioEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uioEdate__, ""));
        return buf.toString();
    }
}