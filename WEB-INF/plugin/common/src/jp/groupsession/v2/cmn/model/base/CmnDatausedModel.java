package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_DATAUSED Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDatausedModel implements Serializable {

    /** CDU_PLUGIN mapping */
    private String cduPlugin__;
    /** CDU_SIZE mapping */
    private long cduSize__;

    /**
     * <p>Default Constructor
     */
    public CmnDatausedModel() {
    }

    /**
     * <p>get CDU_PLUGIN value
     * @return CDU_PLUGIN value
     */
    public String getCduPlugin() {
        return cduPlugin__;
    }

    /**
     * <p>set CDU_PLUGIN value
     * @param cduPlugin CDU_PLUGIN value
     */
    public void setCduPlugin(String cduPlugin) {
        cduPlugin__ = cduPlugin;
    }

    /**
     * <p>get CDU_SIZE value
     * @return CDU_SIZE value
     */
    public long getCduSize() {
        return cduSize__;
    }

    /**
     * <p>set CDU_SIZE value
     * @param cduSize CDU_SIZE value
     */
    public void setCduSize(long cduSize) {
        cduSize__ = cduSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cduPlugin__, ""));
        buf.append(",");
        buf.append(cduSize__);
        return buf.toString();
    }

}
