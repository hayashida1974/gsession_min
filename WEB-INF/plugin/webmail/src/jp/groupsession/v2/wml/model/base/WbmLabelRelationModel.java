package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WBM_LABEL_RELATION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WbmLabelRelationModel implements Serializable {

    /** WMD_MAILNUM mapping */
    private long wmdMailnum__;
    /** WLB_SID mapping */
    private int wlbSid__;

    /**
     * <p>Default Constructor
     */
    public WbmLabelRelationModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wmdMailnum__);
        buf.append(",");
        buf.append(wlbSid__);
        return buf.toString();
    }

}
