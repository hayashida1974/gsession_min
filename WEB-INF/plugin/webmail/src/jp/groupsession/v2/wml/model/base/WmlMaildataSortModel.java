package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_MAILDATA_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataSortModel implements Serializable {

    /** WAC_SID mapping */
    private int wacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WDR_SID mapping */
    private long wdrSid__;
    /** WLB_SID mapping */
    private int wlbSid__;
    /** WMS_SORTKEY mapping */
    private int wmsSortkey__;
    /** WMS_ORDER mapping */
    private int wmsOrder__;

    /**
     * <p>Default Constructor
     */
    public WmlMaildataSortModel() {
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
     * <p>get WDR_SID value
     * @return WDR_SID value
     */
    public long getWdrSid() {
        return wdrSid__;
    }

    /**
     * <p>set WDR_SID value
     * @param wdrSid WDR_SID value
     */
    public void setWdrSid(long wdrSid) {
        wdrSid__ = wdrSid;
    }

    /**
     * <p>get WLB_SID value
     * @return WLB_SID value
     */
    public int getWlbSid() {
        return wlbSid__;
    }

    /**
     * <p>set WLB_SID value
     * @param wlbSid WLB_SID value
     */
    public void setWlbSid(int wlbSid) {
        wlbSid__ = wlbSid;
    }

    /**
     * <p>get WMS_SORTKEY value
     * @return WMS_SORTKEY value
     */
    public int getWmsSortkey() {
        return wmsSortkey__;
    }

    /**
     * <p>set WMS_SORTKEY value
     * @param wmsSortkey WMS_SORTKEY value
     */
    public void setWmsSortkey(int wmsSortkey) {
        wmsSortkey__ = wmsSortkey;
    }

    /**
     * <p>get WMS_ORDER value
     * @return WMS_ORDER value
     */
    public int getWmsOrder() {
        return wmsOrder__;
    }

    /**
     * <p>set WMS_ORDER value
     * @param wmsOrder WMS_ORDER value
     */
    public void setWmsOrder(int wmsOrder) {
        wmsOrder__ = wmsOrder;
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
        buf.append(wdrSid__);
        buf.append(",");
        buf.append(wlbSid__);
        buf.append(",");
        buf.append(wmsSortkey__);
        buf.append(",");
        buf.append(wmsOrder__);
        return buf.toString();
    }

}
