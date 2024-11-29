package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlLabelModel implements Serializable {

    /** WLB_SID mapping */
    private int wlbSid__;
    /** WLB_NAME mapping */
    private String wlbName__;
    /** WLB_ORDER mapping */
    private int wlbOrder__;
    /** WAC_SID mapping */
    private int wacSid__;

    /**
     * <p>Default Constructor
     */
    public WmlLabelModel() {
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
     * <p>get WLB_NAME value
     * @return WLB_NAME value
     */
    public String getWlbName() {
        return wlbName__;
    }

    /**
     * <p>set WLB_NAME value
     * @param wlbName WLB_NAME value
     */
    public void setWlbName(String wlbName) {
        wlbName__ = wlbName;
    }

    /**
     * <p>get WLB_ORDER value
     * @return WLB_ORDER value
     */
    public int getWlbOrder() {
        return wlbOrder__;
    }

    /**
     * <p>set WLB_ORDER value
     * @param wlbOrder WLB_ORDER value
     */
    public void setWlbOrder(int wlbOrder) {
        wlbOrder__ = wlbOrder;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wlbSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wlbName__, ""));
        buf.append(",");
        buf.append(wlbOrder__);
        buf.append(",");
        buf.append(wacSid__);
        return buf.toString();
    }

}
