package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_PLUGIN_CONTROL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPluginControlModel implements Serializable {

    /** PCT_PID mapping */
    private String pctPid__;
    /** PCT_KBN mapping */
    private int pctKbn__;
    /** PCT_TYPE mapping */
    private int pctType__;

    /**
     * <p>Default Constructor
     */
    public CmnPluginControlModel() {
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
     * <p>get PCT_KBN value
     * @return PCT_KBN value
     */
    public int getPctKbn() {
        return pctKbn__;
    }

    /**
     * <p>set PCT_KBN value
     * @param pctKbn PCT_KBN value
     */
    public void setPctKbn(int pctKbn) {
        pctKbn__ = pctKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(pctPid__, ""));
        buf.append(",");
        buf.append(pctKbn__);
        return buf.toString();
    }

    /**
     * <p>pctType を取得します。
     * @return pctType
     */
    public int getPctType() {
        return pctType__;
    }

    /**
     * <p>pctType をセットします。
     * @param pctType pctType
     */
    public void setPctType(int pctType) {
        pctType__ = pctType;
    }

}
