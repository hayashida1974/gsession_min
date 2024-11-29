package jp.groupsession.v2.cmn.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>CMN_PLUGIN_PARAM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPluginParamModel implements Serializable {

    /** CUP_PID mapping */
    private String cupPid__;
    /** CPP_NUM mapping */
    private int cppNum__;
    /** CPP_NAME mapping */
    private String cppName__;
    /** CPP_VALUE mapping */
    private String cppValue__;

    /**
     * <p>Default Constructor
     */
    public CmnPluginParamModel() {
    }

    /**
     * <p>get CUP_PID value
     * @return CUP_PID value
     */
    public String getCupPid() {
        return cupPid__;
    }

    /**
     * <p>set CUP_PID value
     * @param cupPid CUP_PID value
     */
    public void setCupPid(String cupPid) {
        cupPid__ = cupPid;
    }

    /**
     * <p>get CPP_NUM value
     * @return CPP_NUM value
     */
    public int getCppNum() {
        return cppNum__;
    }

    /**
     * <p>set CPP_NUM value
     * @param cppNum CPP_NUM value
     */
    public void setCppNum(int cppNum) {
        cppNum__ = cppNum;
    }

    /**
     * <p>get CPP_NAME value
     * @return CPP_NAME value
     */
    public String getCppName() {
        return cppName__;
    }

    /**
     * <p>set CPP_NAME value
     * @param cppName CPP_NAME value
     */
    public void setCppName(String cppName) {
        cppName__ = cppName;
    }

    /**
     * <p>get CPP_VALUE value
     * @return CPP_VALUE value
     */
    public String getCppValue() {
        return cppValue__;
    }

    /**
     * <p>set CPP_VALUE value
     * @param cppValue CPP_VALUE value
     */
    public void setCppValue(String cppValue) {
        cppValue__ = cppValue;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(cupPid__, ""));
        buf.append(",");
        buf.append(cppNum__);
        buf.append(",");
        buf.append(NullDefault.getString(cppName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cppValue__, ""));
        return buf.toString();
    }

}
