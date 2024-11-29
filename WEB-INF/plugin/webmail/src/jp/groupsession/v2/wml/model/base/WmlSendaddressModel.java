package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>WML_SENDADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlSendaddressModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WSA_NUM mapping */
    private int wsaNum__;
    /** WSA_TYPE mapping */
    private int wsaType__;
    /** WSA_ADDRESS mapping */
    private String wsaAddress__;
    /** WAC_SID mapping */
    private int wacSid__;
    /** WSA_ADDRESS_SRH mapping */
    private String wsaAddressSrh__;

    /**
     * <p>Default Constructor
     */
    public WmlSendaddressModel() {
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
     * <p>get WSA_NUM value
     * @return WSA_NUM value
     */
    public int getWsaNum() {
        return wsaNum__;
    }

    /**
     * <p>set WSA_NUM value
     * @param wsaNum WSA_NUM value
     */
    public void setWsaNum(int wsaNum) {
        wsaNum__ = wsaNum;
    }

    /**
     * <p>get WSA_TYPE value
     * @return WSA_TYPE value
     */
    public int getWsaType() {
        return wsaType__;
    }

    /**
     * <p>set WSA_TYPE value
     * @param wsaType WSA_TYPE value
     */
    public void setWsaType(int wsaType) {
        wsaType__ = wsaType;
    }

    /**
     * <p>get WSA_ADDRESS value
     * @return WSA_ADDRESS value
     */
    public String getWsaAddress() {
        return wsaAddress__;
    }

    /**
     * <p>set WSA_ADDRESS value
     * @param wsaAddress WSA_ADDRESS value
     */
    public void setWsaAddress(String wsaAddress) {
        wsaAddress__ = wsaAddress;
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
     * <p>get WSA_ADDRESS_SRH value
     * @return WSA_ADDRESS_SRH value
     */
    public String getWsaAddressSrh() {
        return wsaAddressSrh__;
    }

    /**
     * <p>set WSA_ADDRESS_SRH value
     * @param wsaAddressSrh WSA_ADDRESS_SRH value
     */
    public void setWsaAddressSrh(String wsaAddressSrh) {
        wsaAddressSrh__ = wsaAddressSrh;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(wsaNum__);
        buf.append(",");
        buf.append(wsaType__);
        buf.append(",");
        buf.append(NullDefault.getString(wsaAddress__, ""));
        buf.append(",");
        buf.append(wacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wsaAddressSrh__, ""));
        return buf.toString();
    }

}
