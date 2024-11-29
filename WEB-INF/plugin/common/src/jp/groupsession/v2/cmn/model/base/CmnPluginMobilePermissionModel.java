package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPluginMobilePermissionModel implements Serializable {

    /** PMP_PLUGIN mapping */
    private String pmpPlugin__;

    /**
     * <p>Default Constructor
     */
    public CmnPluginMobilePermissionModel() {
    }

    /**
     * <p>get PMP_PLUGIN value
     * @return PMP_PLUGIN value
     */
    public String getPmpPlugin() {
        return pmpPlugin__;
    }

    /**
     * <p>set PMP_PLUGIN value
     * @param pmpPlugin PMP_PLUGIN value
     */
    public void setPmpPlugin(String pmpPlugin) {
        pmpPlugin__ = pmpPlugin;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(pmpPlugin__, ""));
        return buf.toString();
    }

}
