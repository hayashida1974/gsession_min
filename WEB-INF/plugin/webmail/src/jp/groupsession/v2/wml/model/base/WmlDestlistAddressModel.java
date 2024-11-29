package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_DESTLIST_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistAddressModel implements Serializable {

    /** WDL_SID mapping */
    private int wdlSid__;
    /** WDA_TYPE mapping */
    private int wdaType__;
    /** WDA_SID mapping */
    private int wdaSid__;
    /** WDA_ADRNO mapping */
    private int wdaAdrno__;

    /**
     * <p>Default Constructor
     */
    public WmlDestlistAddressModel() {
    }

    /**
     * <p>get WDL_SID value
     * @return WDL_SID value
     */
    public int getWdlSid() {
        return wdlSid__;
    }

    /**
     * <p>set WDL_SID value
     * @param wdlSid WDL_SID value
     */
    public void setWdlSid(int wdlSid) {
        wdlSid__ = wdlSid;
    }

    /**
     * <p>get WDA_TYPE value
     * @return WDA_TYPE value
     */
    public int getWdaType() {
        return wdaType__;
    }

    /**
     * <p>set WDA_TYPE value
     * @param wdaType WDA_TYPE value
     */
    public void setWdaType(int wdaType) {
        wdaType__ = wdaType;
    }

    /**
     * <p>get WDA_SID value
     * @return WDA_SID value
     */
    public int getWdaSid() {
        return wdaSid__;
    }

    /**
     * <p>set WDA_SID value
     * @param wdaSid WDA_SID value
     */
    public void setWdaSid(int wdaSid) {
        wdaSid__ = wdaSid;
    }

    /**
     * <p>get WDA_ADRNO value
     * @return WDA_ADRNO value
     */
    public int getWdaAdrno() {
        return wdaAdrno__;
    }

    /**
     * <p>set WDA_ADRNO value
     * @param wdaAdrno WDA_ADRNO value
     */
    public void setWdaAdrno(int wdaAdrno) {
        wdaAdrno__ = wdaAdrno;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wdlSid__);
        buf.append(",");
        buf.append(wdaType__);
        buf.append(",");
        buf.append(wdaSid__);
        buf.append(",");
        buf.append(wdaAdrno__);
        return buf.toString();
    }

}
