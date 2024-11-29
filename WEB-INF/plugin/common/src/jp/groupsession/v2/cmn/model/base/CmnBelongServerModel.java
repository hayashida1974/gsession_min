package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_BELONG_SERVER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnBelongServerModel implements Serializable {

    /** USR_SID mapping */
    private String usrSid__;
    /** CBS_IP mapping */
    private String cbsIp__;

    /**
     * <p>Default Constructor
     */
    public CmnBelongServerModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public String getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get CBS_IP value
     * @return CBS_IP value
     */
    public String getCbsIp() {
        return cbsIp__;
    }

    /**
     * <p>set CBS_IP value
     * @param cbsIp CBS_IP value
     */
    public void setCbsIp(String cbsIp) {
        cbsIp__ = cbsIp;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(usrSid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cbsIp__, ""));
        return buf.toString();
    }

}
