package jp.groupsession.v2.rng.model;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RNG_TEMPLATE_KEIRO_CONDITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateKeiroConditionModel implements Serializable {

    /** RTK_SID mapping */
    private int rtkSid__;
    /** RKC_IFTEMPLATE mapping */
    private int rkcIftemplate__;
    /** RKC_IFGROUP mapping */
    private int rkcIfgroup__ = -1;
    /** RKC_IFPOS mapping */
    private int rkcIfpos__ = -1;
    /** RKC_IFFORM mapping */
    private String rkcIfform__;
    /** RKC_IFFORM_OPR mapping */
    private String rkcIfformOpr__;
    /** RKC_IFFORM_VALUE mapping */
    private String rkcIfformValue__;
    /** RKC_ORID mapping */
    private int rkcOrid__;

    /**
     * <p>Default Constructor
     */
    public RngTemplateKeiroConditionModel() {
    }

    /**
     * <p>get RTK_SID value
     * @return RTK_SID value
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>set RTK_SID value
     * @param rtkSid RTK_SID value
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }

    /**
     * <p>get RKC_IFTEMPLATE value
     * @return RKC_IFTEMPLATE value
     */
    public int getRkcIftemplate() {
        return rkcIftemplate__;
    }

    /**
     * <p>set RKC_IFTEMPLATE value
     * @param rkcIftemplate RKC_IFTEMPLATE value
     */
    public void setRkcIftemplate(int rkcIftemplate) {
        rkcIftemplate__ = rkcIftemplate;
    }

    /**
     * <p>get RKC_IFGROUP value
     * @return RKC_IFGROUP value
     */
    public int getRkcIfgroup() {
        return rkcIfgroup__;
    }

    /**
     * <p>set RKC_IFGROUP value
     * @param rkcIfgroup RKC_IFGROUP value
     */
    public void setRkcIfgroup(int rkcIfgroup) {
        rkcIfgroup__ = rkcIfgroup;
    }

    /**
     * <p>get RKC_IFPOS value
     * @return RKC_IFPOS value
     */
    public int getRkcIfpos() {
        return rkcIfpos__;
    }

    /**
     * <p>set RKC_IFPOS value
     * @param rkcIfpos RKC_IFPOS value
     */
    public void setRkcIfpos(int rkcIfpos) {
        rkcIfpos__ = rkcIfpos;
    }

    /**
     * <p>get RKC_IFFORM value
     * @return RKC_IFFORM value
     */
    public String getRkcIfform() {
        return rkcIfform__;
    }

    /**
     * <p>set RKC_IFFORM value
     * @param rkcIfform RKC_IFFORM value
     */
    public void setRkcIfform(String rkcIfform) {
        rkcIfform__ = rkcIfform;
    }

    /**
     * <p>get RKC_IFFORM_OPR value
     * @return RKC_IFFORM_OPR value
     */
    public String getRkcIfformOpr() {
        return rkcIfformOpr__;
    }

    /**
     * <p>set RKC_IFFORM_OPR value
     * @param rkcIfformOpr RKC_IFFORM_OPR value
     */
    public void setRkcIfformOpr(String rkcIfformOpr) {
        rkcIfformOpr__ = rkcIfformOpr;
    }

    /**
     * <p>get RKC_IFFORM_VALUE value
     * @return RKC_IFFORM_VALUE value
     */
    public String getRkcIfformValue() {
        return rkcIfformValue__;
    }

    /**
     * <p>set RKC_IFFORM_VALUE value
     * @param rkcIfformValue RKC_IFFORM_VALUE value
     */
    public void setRkcIfformValue(String rkcIfformValue) {
        rkcIfformValue__ = rkcIfformValue;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rtkSid__);
        buf.append(",");
        buf.append(rkcIftemplate__);
        buf.append(",");
        buf.append(rkcIfgroup__);
        buf.append(",");
        buf.append(rkcIfpos__);
        buf.append(",");
        buf.append(NullDefault.getString(rkcIfform__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(rkcIfformOpr__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(rkcIfformValue__, ""));
        return buf.toString();
    }

    /**
     * <p>rkcOrid を取得します。
     * @return rkcOrid
     */
    public int getRkcOrid() {
        return rkcOrid__;
    }

    /**
     * <p>rkcOrid をセットします。
     * @param rkcOrid rkcOrid
     */
    public void setRkcOrid(int rkcOrid) {
        rkcOrid__ = rkcOrid;
    }

}
