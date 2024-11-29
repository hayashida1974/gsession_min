package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WBM_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WbmLabelModel implements Serializable {

    /** WLB_SID mapping */
    private int wlbSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** WLB_NAME mapping */
    private String wlbName__;

    /**
     * <p>Default Constructor
     */
    public WbmLabelModel() {
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(wlbSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(wlbName__, ""));
        return buf.toString();
    }

}
