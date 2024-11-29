package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_BROWSER_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnBrowserInfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** CBI_SESSION_ID mapping */
    private String cbiSessionId__;

    /**
     * <p>Default Constructor
     */
    public CmnBrowserInfModel() {
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
     * <p>get CBI_SESSION_ID value
     * @return CBI_SESSION_ID value
     */
    public String getCbiSessionId() {
        return cbiSessionId__;
    }

    /**
     * <p>set CBI_SESSION_ID value
     * @param cbiSessionId CBI_SESSION_ID value
     */
    public void setCbiSessionId(String cbiSessionId) {
        cbiSessionId__ = cbiSessionId;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cbiSessionId__, ""));
        return buf.toString();
    }

}
