package jp.groupsession.v2.man.model;

import java.io.Serializable;

/**
 * <p>FRE_PLUGIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FrePluginModel implements Serializable {

    /** FLI_SID mapping */
    private int fliSid__;
    /** FPL_PID mapping */
    private String fplPid__;
    /** FPL_PNAME mapping */
    private String fplPname__;
    /** FPL_USEKBN mapping */
    private int fplUsekbn__;

    /**
     * <p>Default Constructor
     */
    public FrePluginModel() {
    }

    /**
     * <p>get FLI_SID value
     * @return FLI_SID value
     */
    public int getFliSid() {
        return fliSid__;
    }

    /**
     * <p>set FLI_SID value
     * @param fliSid FLI_SID value
     */
    public void setFliSid(int fliSid) {
        fliSid__ = fliSid;
    }

    /**
     * <p>get FPL_PID value
     * @return FPL_PID value
     */
    public String getFplPid() {
        return fplPid__;
    }

    /**
     * <p>set FPL_PID value
     * @param fplPid FPL_PID value
     */
    public void setFplPid(String fplPid) {
        fplPid__ = fplPid;
    }

    /**
     * <p>get FPL_PNAME value
     * @return FPL_PNAME value
     */
    public String getFplPname() {
        return fplPname__;
    }

    /**
     * <p>set FPL_PNAME value
     * @param fplPname FPL_PNAME value
     */
    public void setFplPname(String fplPname) {
        fplPname__ = fplPname;
    }

    /**
     * <p>get FPL_USEKBN value
     * @return FPL_USEKBN value
     */
    public int getFplUsekbn() {
        return fplUsekbn__;
    }

    /**
     * <p>set FPL_USEKBN value
     * @param fplUsekbn FPL_USEKBN value
     */
    public void setFplUsekbn(int fplUsekbn) {
        fplUsekbn__ = fplUsekbn;
    }
}
