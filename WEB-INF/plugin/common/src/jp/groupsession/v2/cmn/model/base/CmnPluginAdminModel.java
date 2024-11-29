package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_PLUGIN_CONTROL_ADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPluginAdminModel implements Serializable {

    /** PCT_PID mapping */
    private String pctPid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** USR_SID mapping */
    private int usrSid__;

    /**
     * <p>Default Constructor
     */
    public CmnPluginAdminModel() {
    }

    /**
     * <p>get PCT_PID value
     * @return PCT_PID value
     */
    public String getPctPid() {
        return pctPid__;
    }

    /**
     * <p>set PCT_PID value
     * @param pctPid PCT_PID value
     */
    public void setPctPid(String pctPid) {
        pctPid__ = pctPid;
    }

    /**
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
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
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(pctPid__, ""));
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(usrSid__);
        return buf.toString();
    }

}
