package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_TYPEINDUSTRY Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrTypeindustryModel implements Serializable {

    /** ATI_SID mapping */
    private int atiSid__;
    /** ATI_NAME mapping */
    private String atiName__;
    /** ATI_BIKO mapping */
    private String atiBiko__;
    /** ATI_SORT mapping */
    private int atiSort__;
    /** ATI_AUID mapping */
    private int atiAuid__;
    /** ATI_ADATE mapping */
    private UDate atiAdate__;
    /** ATI_EUID mapping */
    private int atiEuid__;
    /** ATI_EDATE mapping */
    private UDate atiEdate__;

    /**
     * <p>Default Constructor
     */
    public AdrTypeindustryModel() {
    }

    /**
     * <p>get ATI_SID value
     * @return ATI_SID value
     */
    public int getAtiSid() {
        return atiSid__;
    }

    /**
     * <p>set ATI_SID value
     * @param atiSid ATI_SID value
     */
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }

    /**
     * <p>get ATI_NAME value
     * @return ATI_NAME value
     */
    public String getAtiName() {
        return atiName__;
    }

    /**
     * <p>set ATI_NAME value
     * @param atiName ATI_NAME value
     */
    public void setAtiName(String atiName) {
        atiName__ = atiName;
    }

    /**
     * <p>get ATI_BIKO value
     * @return ATI_BIKO value
     */
    public String getAtiBiko() {
        return atiBiko__;
    }

    /**
     * <p>set ATI_BIKO value
     * @param atiBiko ATI_BIKO value
     */
    public void setAtiBiko(String atiBiko) {
        atiBiko__ = atiBiko;
    }

    /**
     * <p>get ATI_SORT value
     * @return ATI_SORT value
     */
    public int getAtiSort() {
        return atiSort__;
    }

    /**
     * <p>set ATI_SORT value
     * @param atiSort ATI_SORT value
     */
    public void setAtiSort(int atiSort) {
        atiSort__ = atiSort;
    }

    /**
     * <p>get ATI_AUID value
     * @return ATI_AUID value
     */
    public int getAtiAuid() {
        return atiAuid__;
    }

    /**
     * <p>set ATI_AUID value
     * @param atiAuid ATI_AUID value
     */
    public void setAtiAuid(int atiAuid) {
        atiAuid__ = atiAuid;
    }

    /**
     * <p>get ATI_ADATE value
     * @return ATI_ADATE value
     */
    public UDate getAtiAdate() {
        return atiAdate__;
    }

    /**
     * <p>set ATI_ADATE value
     * @param atiAdate ATI_ADATE value
     */
    public void setAtiAdate(UDate atiAdate) {
        atiAdate__ = atiAdate;
    }

    /**
     * <p>get ATI_EUID value
     * @return ATI_EUID value
     */
    public int getAtiEuid() {
        return atiEuid__;
    }

    /**
     * <p>set ATI_EUID value
     * @param atiEuid ATI_EUID value
     */
    public void setAtiEuid(int atiEuid) {
        atiEuid__ = atiEuid;
    }

    /**
     * <p>get ATI_EDATE value
     * @return ATI_EDATE value
     */
    public UDate getAtiEdate() {
        return atiEdate__;
    }

    /**
     * <p>set ATI_EDATE value
     * @param atiEdate ATI_EDATE value
     */
    public void setAtiEdate(UDate atiEdate) {
        atiEdate__ = atiEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(atiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(atiName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(atiBiko__, ""));
        buf.append(",");
        buf.append(atiSort__);
        buf.append(",");
        buf.append(atiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(atiAdate__, ""));
        buf.append(",");
        buf.append(atiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(atiEdate__, ""));
        return buf.toString();
    }

}
