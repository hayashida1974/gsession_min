package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_PROXY_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnProxyAddressModel implements Serializable {

    /** CXA_ADDRESS mapping */
    private String cxaAddress__;
    /** CXA_NO mapping */
    private int cxaNo__;

    /**
     * <p>Default Constructor
     */
    public CmnProxyAddressModel() {
    }

    /**
     * <p>get CXA_ADDRESS value
     * @return CXA_ADDRESS value
     */
    public String getCxaAddress() {
        return cxaAddress__;
    }

    /**
     * <p>set CXA_ADDRESS value
     * @param cxaAddress CXA_ADDRESS value
     */
    public void setCxaAddress(String cxaAddress) {
        cxaAddress__ = cxaAddress;
    }

    /**
     * <p>get CXA_NO value
     * @return CXA_NO value
     */
    public int getCxaNo() {
        return cxaNo__;
    }

    /**
     * <p>set CXA_NO value
     * @param cxaNo CXA_NO value
     */
    public void setCxaNo(int cxaNo) {
        cxaNo__ = cxaNo;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cxaAddress__, ""));
        buf.append(",");
        buf.append(cxaNo__);
        return buf.toString();
    }

}
