package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CLI_PLUGIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CliPluginModel implements Serializable {

    /** CSI_SID mapping */
    private int csiSid__;
    /** CPL_PID mapping */
    private String cplPid__;
    /** CPL_PNAME mapping */
    private String cplPname__;
    /** CPL_USEKBN mapping */
    private int cplUsekbn__ = -1;

    /**
     * <p>Default Constructor
     */
    public CliPluginModel() {
    }

    /**
     * <p>get CSI_SID value
     * @return CSI_SID value
     */
    public int getCsiSid() {
        return csiSid__;
    }

    /**
     * <p>set CSI_SID value
     * @param csiSid CSI_SID value
     */
    public void setCsiSid(int csiSid) {
        csiSid__ = csiSid;
    }

    /**
     * <p>get CPL_PID value
     * @return CPL_PID value
     */
    public String getCplPid() {
        return cplPid__;
    }

    /**
     * <p>set CPL_PID value
     * @param cplPid CPL_PID value
     */
    public void setCplPid(String cplPid) {
        cplPid__ = cplPid;
    }

    /**
     * <p>get CPL_PNAME value
     * @return CPL_PNAME value
     */
    public String getCplPname() {
        return cplPname__;
    }

    /**
     * <p>set CPL_PNAME value
     * @param cplPname CPL_PNAME value
     */
    public void setCplPname(String cplPname) {
        cplPname__ = cplPname;
    }

    /**
     * <p>get CPL_USEKBN value
     * @return CPL_USEKBN value
     */
    public int getCplUsekbn() {
        return cplUsekbn__;
    }

    /**
     * <p>set CPL_USEKBN value
     * @param cplUsekbn CPL_USEKBN value
     */
    public void setCplUsekbn(int cplUsekbn) {
        cplUsekbn__ = cplUsekbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(csiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cplPid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cplPname__, ""));
        buf.append(",");
        buf.append(cplUsekbn__);
        return buf.toString();
    }

}
