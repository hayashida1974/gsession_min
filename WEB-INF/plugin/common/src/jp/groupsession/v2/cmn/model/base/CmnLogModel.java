package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CMN_LOG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogModel implements Serializable {

    /** LOG_DATE mapping */
    private UDate logDate__;
    /** USR_SID mapping */
    private int usrSid__;
    /** LOG_LEVEL mapping */
    private String logLevel__;
    /** LOG_PLUGIN mapping */
    private String logPlugin__;
    /** LOG_PLUGIN_NAME mapping */
    private String logPluginName__;
    /** LOG_PG_ID mapping */
    private String logPgId__;
    /** LOG_PG_NAME mapping */
    private String logPgName__;
    /** LOG_OP_CODE mapping */
    private String logOpCode__;
    /** LOG_OP_VALUE mapping */
    private String logOpValue__;
    /** LOG_IP mapping */
    private String logIp__;
    /** VER_VERSION mapping */
    private String verVersion__;
    /** LOG_CODE mapping */
    private String logCode__;

    /**
     * <p>Default Constructor
     */
    public CmnLogModel() {
    }

    /**
     * <p>get LOG_DATE value
     * @return LOG_DATE value
     */
    public UDate getLogDate() {
        return logDate__;
    }

    /**
     * <p>set LOG_DATE value
     * @param logDate LOG_DATE value
     */
    public void setLogDate(UDate logDate) {
        logDate__ = logDate;
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
     * <p>get LOG_LEVEL value
     * @return LOG_LEVEL value
     */
    public String getLogLevel() {
        return logLevel__;
    }

    /**
     * <p>set LOG_LEVEL value
     * @param logLevel LOG_LEVEL value
     */
    public void setLogLevel(String logLevel) {
        logLevel__ = logLevel;
    }

    /**
     * <p>get LOG_PLUGIN value
     * @return LOG_PLUGIN value
     */
    public String getLogPlugin() {
        return logPlugin__;
    }

    /**
     * <p>set LOG_PLUGIN value
     * @param logPlugin LOG_PLUGIN value
     */
    public void setLogPlugin(String logPlugin) {
        logPlugin__ = logPlugin;
    }

    /**
     * <p>get LOG_PLUGIN_NAME value
     * @return LOG_PLUGIN_NAME value
     */
    public String getLogPluginName() {
        return logPluginName__;
    }

    /**
     * <p>set LOG_PLUGIN_NAME value
     * @param logPluginName LOG_PLUGIN_NAME value
     */
    public void setLogPluginName(String logPluginName) {
        logPluginName__ = logPluginName;
    }

    /**
     * <p>get LOG_PG_ID value
     * @return LOG_PG_ID value
     */
    public String getLogPgId() {
        return logPgId__;
    }

    /**
     * <p>set LOG_PG_ID value
     * @param logPgId LOG_PG_ID value
     */
    public void setLogPgId(String logPgId) {
        logPgId__ = logPgId;
    }

    /**
     * <p>get LOG_PG_NAME value
     * @return LOG_PG_NAME value
     */
    public String getLogPgName() {
        return logPgName__;
    }

    /**
     * <p>set LOG_PG_NAME value
     * @param logPgName LOG_PG_NAME value
     */
    public void setLogPgName(String logPgName) {
        logPgName__ = logPgName;
    }

    /**
     * <p>get LOG_OP_CODE value
     * @return LOG_OP_CODE value
     */
    public String getLogOpCode() {
        return logOpCode__;
    }

    /**
     * <p>set LOG_OP_CODE value
     * @param logOpCode LOG_OP_CODE value
     */
    public void setLogOpCode(String logOpCode) {
        logOpCode__ = logOpCode;
    }

    /**
     * <p>get LOG_OP_VALUE value
     * @return LOG_OP_VALUE value
     */
    public String getLogOpValue() {
        return logOpValue__;
    }

    /**
     * <p>set LOG_OP_VALUE value
     * @param logOpValue LOG_OP_VALUE value
     */
    public void setLogOpValue(String logOpValue) {
        logOpValue__ = logOpValue;
    }

    /**
     * <p>get LOG_IP value
     * @return LOG_IP value
     */
    public String getLogIp() {
        return logIp__;
    }

    /**
     * <p>set LOG_IP value
     * @param logIp LOG_IP value
     */
    public void setLogIp(String logIp) {
        logIp__ = logIp;
    }

    /**
     * <p>get VER_VERSION value
     * @return VER_VERSION value
     */
    public String getVerVersion() {
        return verVersion__;
    }

    /**
     * <p>set VER_VERSION value
     * @param verVersion VER_VERSION value
     */
    public void setVerVersion(String verVersion) {
        verVersion__ = verVersion;
    }

    /**
     * <p>get LOG_CODE value
     * @return LOG_CODE value
     */
    public String getLogCode() {
        return logCode__;
    }

    /**
     * <p>set LOG_CODE value
     * @param logCode LOG_CODE value
     */
    public void setLogCode(String logCode) {
        logCode__ = logCode;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getStringFmObj(logDate__, ""));
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(logLevel__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logPlugin__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logPluginName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logPgId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logPgName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logOpCode__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logOpValue__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logIp__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(verVersion__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(logCode__, ""));
        return buf.toString();
    }

}
