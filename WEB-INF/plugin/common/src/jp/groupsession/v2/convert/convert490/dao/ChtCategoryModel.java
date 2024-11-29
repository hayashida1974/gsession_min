package jp.groupsession.v2.convert.convert490.dao;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_CATEGORY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtCategoryModel implements Serializable {

    /** CHC_SID mapping */
    private int chcSid__;
    /** CHC_NAME mapping */
    private String chcName__;
    /** CHC_SORT mapping */
    private int chcSort__;
    /** CHC_AUID mapping */
    private int chcAuid__;
    /** CHC_ADATE mapping */
    private UDate chcAdate__;
    /** CHC_EUID mapping */
    private int chcEuid__;
    /** CHC_EDATE mapping */
    private UDate chcEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtCategoryModel() {
    }

    /**
     * <p>get CHC_SID value
     * @return CHC_SID value
     */
    public int getChcSid() {
        return chcSid__;
    }

    /**
     * <p>set CHC_SID value
     * @param chcSid CHC_SID value
     */
    public void setChcSid(int chcSid) {
        chcSid__ = chcSid;
    }

    /**
     * <p>get CHC_NAME value
     * @return CHC_NAME value
     */
    public String getChcName() {
        return chcName__;
    }

    /**
     * <p>set CHC_NAME value
     * @param chcName CHC_NAME value
     */
    public void setChcName(String chcName) {
        chcName__ = chcName;
    }

    /**
     * <p>get CHC_SORT value
     * @return CHC_SORT value
     */
    public int getChcSort() {
        return chcSort__;
    }

    /**
     * <p>set CHC_SORT value
     * @param chcSort CHC_SORT value
     */
    public void setChcSort(int chcSort) {
        chcSort__ = chcSort;
    }

    /**
     * <p>get CHC_AUID value
     * @return CHC_AUID value
     */
    public int getChcAuid() {
        return chcAuid__;
    }

    /**
     * <p>set CHC_AUID value
     * @param chcAuid CHC_AUID value
     */
    public void setChcAuid(int chcAuid) {
        chcAuid__ = chcAuid;
    }

    /**
     * <p>get CHC_ADATE value
     * @return CHC_ADATE value
     */
    public UDate getChcAdate() {
        return chcAdate__;
    }

    /**
     * <p>set CHC_ADATE value
     * @param chcAdate CHC_ADATE value
     */
    public void setChcAdate(UDate chcAdate) {
        chcAdate__ = chcAdate;
    }

    /**
     * <p>get CHC_EUID value
     * @return CHC_EUID value
     */
    public int getChcEuid() {
        return chcEuid__;
    }

    /**
     * <p>set CHC_EUID value
     * @param chcEuid CHC_EUID value
     */
    public void setChcEuid(int chcEuid) {
        chcEuid__ = chcEuid;
    }

    /**
     * <p>get CHC_EDATE value
     * @return CHC_EDATE value
     */
    public UDate getChcEdate() {
        return chcEdate__;
    }

    /**
     * <p>set CHC_EDATE value
     * @param chcEdate CHC_EDATE value
     */
    public void setChcEdate(UDate chcEdate) {
        chcEdate__ = chcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(chcSid__);
        buf.append(",");
        buf.append(NullDefault.getString(chcName__, ""));
        buf.append(",");
        buf.append(chcSort__);
        buf.append(",");
        buf.append(chcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(chcAdate__, ""));
        buf.append(",");
        buf.append(chcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(chcEdate__, ""));
        return buf.toString();
    }

}
