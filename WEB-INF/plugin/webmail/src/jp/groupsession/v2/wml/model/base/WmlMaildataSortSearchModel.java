package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataSortSearchModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WSS_SORTKEY mapping */
    private int wssSortkey__;
    /** WSS_ORDER mapping */
    private int wssOrder__;

    /**
     * <p>Default Constructor
     */
    public WmlMaildataSortSearchModel() {
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
     * <p>get WSS_SORTKEY value
     * @return WSS_SORTKEY value
     */
    public int getWssSortkey() {
        return wssSortkey__;
    }

    /**
     * <p>set WSS_SORTKEY value
     * @param wssSortkey WSS_SORTKEY value
     */
    public void setWssSortkey(int wssSortkey) {
        wssSortkey__ = wssSortkey;
    }

    /**
     * <p>get WSS_ORDER value
     * @return WSS_ORDER value
     */
    public int getWssOrder() {
        return wssOrder__;
    }

    /**
     * <p>set WSS_ORDER value
     * @param wssOrder WSS_ORDER value
     */
    public void setWssOrder(int wssOrder) {
        wssOrder__ = wssOrder;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wacSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(wssSortkey__);
        buf.append(",");
        buf.append(wssOrder__);
        return buf.toString();
    }

}
