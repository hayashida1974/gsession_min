package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_FIREWALL_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnFirewallConfModel implements Serializable {

    /** CFC_ALLOW_IP mapping */
    private String cfcAllowIp__;
    /** CFC_ALLOW_MBL mapping */
    private int cfcAllowMbl__;
    /** CFC_ALLOW_MBL_PLUGIN mapping */
    private int cfcAllowMblPlugin__;
    /** CFC_ALLOW_ANP mapping */
    private int cfcAllowAnp__;

    /**
     * <p>Default Constructor
     */
    public CmnFirewallConfModel() {
    }

    /**
     * <p>get CFC_ALLOW_IP value
     * @return CFC_ALLOW_IP value
     */
    public String getCfcAllowIp() {
        return cfcAllowIp__;
    }

    /**
     * <p>set CFC_ALLOW_IP value
     * @param cfcAllowIp CFC_ALLOW_IP value
     */
    public void setCfcAllowIp(String cfcAllowIp) {
        cfcAllowIp__ = cfcAllowIp;
    }

    /**
     * <p>get CFC_ALLOW_MBL value
     * @return CFC_ALLOW_MBL value
     */
    public int getCfcAllowMbl() {
        return cfcAllowMbl__;
    }

    /**
     * <p>set CFC_ALLOW_MBL value
     * @param cfcAllowMbl CFC_ALLOW_MBL value
     */
    public void setCfcAllowMbl(int cfcAllowMbl) {
        cfcAllowMbl__ = cfcAllowMbl;
    }

    /**
     * <p>get CFC_ALLOW_MBL_PLUGIN value
     * @return CFC_ALLOW_MBL_PLUGIN value
     */
    public int getCfcAllowMblPlugin() {
        return cfcAllowMblPlugin__;
    }

    /**
     * <p>set CFC_ALLOW_MBL_PLUGIN value
     * @param cfcAllowMblPlugin CFC_ALLOW_MBL_PLUGIN value
     */
    public void setCfcAllowMblPlugin(int cfcAllowMblPlugin) {
        cfcAllowMblPlugin__ = cfcAllowMblPlugin;
    }

    /**
     * <p>get CFC_ALLOW_ANP value
     * @return CFC_ALLOW_ANP value
     */
    public int getCfcAllowAnp() {
        return cfcAllowAnp__;
    }

    /**
     * <p>set CFC_ALLOW_ANP value
     * @param cfcAllowAnp CFC_ALLOW_ANP value
     */
    public void setCfcAllowAnp(int cfcAllowAnp) {
        cfcAllowAnp__ = cfcAllowAnp;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cfcAllowIp__, ""));
        buf.append(",");
        buf.append(cfcAllowMbl__);
        buf.append(",");
        buf.append(cfcAllowAnp__);
        return buf.toString();
    }

}
