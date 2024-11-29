package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_ACCOUNT_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountSortModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WAS_SORT mapping */
    private long wasSort__;

    /**
     * <p>Default Constructor
     */
    public WmlAccountSortModel() {
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
     * <p>get WAS_SORT value
     * @return WAS_SORT value
     */
    public long getWasSort() {
        return wasSort__;
    }

    /**
     * <p>set WAS_SORT value
     * @param wasSort WAS_SORT value
     */
    public void setWasSort(long wasSort) {
        wasSort__ = wasSort;
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
        buf.append(wasSort__);
        return buf.toString();
    }

}
