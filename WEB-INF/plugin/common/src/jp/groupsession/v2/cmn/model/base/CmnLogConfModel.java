package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;

import java.io.Serializable;

/**
 * <p>CMN_LOG_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogConfModel implements Serializable {

    /** LGC_PLUGIN mapping */
    private String lgcPlugin__;
    /** LGC_LEVEL_ERROR mapping */
    private int lgcLevelError__;
    /** LGC_LEVEL_WARN mapping */
    private int lgcLevelWarn__;
    /** LGC_LEVEL_INFO mapping */
    private int lgcLevelInfo__;
    /** LGC_LEVEL_TRACE mapping */
    private int lgcLevelTrace__;
    /** LGC_AUID mapping */
    private int lgcAuid__;
    /** LGC_ADATE mapping */
    private UDate lgcAdate__;
    /** LGC_EUID mapping */
    private int lgcEuid__;
    /** LGC_EDATE mapping */
    private UDate lgcEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnLogConfModel() {
    }
    /**
     * <p>init Constructor
     * @param plugin プラグイン
     */
    public CmnLogConfModel(String plugin) {
        UDate now = new UDate();
        lgcPlugin__ = plugin;
        lgcLevelError__ = GSConstLog.LOG_ENABLE;
        lgcLevelWarn__ = GSConstLog.LOG_ENABLE;
        lgcLevelInfo__ = GSConstLog.LOG_ENABLE;
        lgcLevelTrace__ = GSConstLog.LOG_ENABLE;
        lgcAuid__ = -1;
        lgcAdate__ = now;
        lgcEuid__ = -1;
        lgcEdate__ = now;
    }
    /**
     * <p>get LGC_PLUGIN value
     * @return LGC_PLUGIN value
     */
    public String getLgcPlugin() {
        return lgcPlugin__;
    }

    /**
     * <p>set LGC_PLUGIN value
     * @param lgcPlugin LGC_PLUGIN value
     */
    public void setLgcPlugin(String lgcPlugin) {
        lgcPlugin__ = lgcPlugin;
    }

    /**
     * <p>get LGC_LEVEL_ERROR value
     * @return LGC_LEVEL_ERROR value
     */
    public int getLgcLevelError() {
        return lgcLevelError__;
    }

    /**
     * <p>set LGC_LEVEL_ERROR value
     * @param lgcLevelError LGC_LEVEL_ERROR value
     */
    public void setLgcLevelError(int lgcLevelError) {
        lgcLevelError__ = lgcLevelError;
    }

    /**
     * <p>get LGC_LEVEL_WARN value
     * @return LGC_LEVEL_WARN value
     */
    public int getLgcLevelWarn() {
        return lgcLevelWarn__;
    }

    /**
     * <p>set LGC_LEVEL_WARN value
     * @param lgcLevelWarn LGC_LEVEL_WARN value
     */
    public void setLgcLevelWarn(int lgcLevelWarn) {
        lgcLevelWarn__ = lgcLevelWarn;
    }

    /**
     * <p>get LGC_LEVEL_INFO value
     * @return LGC_LEVEL_INFO value
     */
    public int getLgcLevelInfo() {
        return lgcLevelInfo__;
    }

    /**
     * <p>set LGC_LEVEL_INFO value
     * @param lgcLevelInfo LGC_LEVEL_INFO value
     */
    public void setLgcLevelInfo(int lgcLevelInfo) {
        lgcLevelInfo__ = lgcLevelInfo;
    }

    /**
     * <p>get LGC_LEVEL_TRACE value
     * @return LGC_LEVEL_TRACE value
     */
    public int getLgcLevelTrace() {
        return lgcLevelTrace__;
    }

    /**
     * <p>set LGC_LEVEL_TRACE value
     * @param lgcLevelTrace LGC_LEVEL_TRACE value
     */
    public void setLgcLevelTrace(int lgcLevelTrace) {
        lgcLevelTrace__ = lgcLevelTrace;
    }

    /**
     * <p>get LGC_AUID value
     * @return LGC_AUID value
     */
    public int getLgcAuid() {
        return lgcAuid__;
    }

    /**
     * <p>set LGC_AUID value
     * @param lgcAuid LGC_AUID value
     */
    public void setLgcAuid(int lgcAuid) {
        lgcAuid__ = lgcAuid;
    }

    /**
     * <p>get LGC_ADATE value
     * @return LGC_ADATE value
     */
    public UDate getLgcAdate() {
        return lgcAdate__;
    }

    /**
     * <p>set LGC_ADATE value
     * @param lgcAdate LGC_ADATE value
     */
    public void setLgcAdate(UDate lgcAdate) {
        lgcAdate__ = lgcAdate;
    }

    /**
     * <p>get LGC_EUID value
     * @return LGC_EUID value
     */
    public int getLgcEuid() {
        return lgcEuid__;
    }

    /**
     * <p>set LGC_EUID value
     * @param lgcEuid LGC_EUID value
     */
    public void setLgcEuid(int lgcEuid) {
        lgcEuid__ = lgcEuid;
    }

    /**
     * <p>get LGC_EDATE value
     * @return LGC_EDATE value
     */
    public UDate getLgcEdate() {
        return lgcEdate__;
    }

    /**
     * <p>set LGC_EDATE value
     * @param lgcEdate LGC_EDATE value
     */
    public void setLgcEdate(UDate lgcEdate) {
        lgcEdate__ = lgcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(lgcPlugin__, ""));
        buf.append(",");
        buf.append(lgcLevelError__);
        buf.append(",");
        buf.append(lgcLevelWarn__);
        buf.append(",");
        buf.append(lgcLevelInfo__);
        buf.append(",");
        buf.append(lgcLevelTrace__);
        buf.append(",");
        buf.append(lgcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(lgcAdate__, ""));
        buf.append(",");
        buf.append(lgcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(lgcEdate__, ""));
        return buf.toString();
    }

}
