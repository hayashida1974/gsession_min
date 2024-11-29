package jp.groupsession.v2.adr.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ADR_POSITION Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPositionModel implements Serializable {

    /** APS_SID mapping */
    private int apsSid__;
    /** APS_NAME mapping */
    private String apsName__;
    /** APS_AUID mapping */
    private int apsAuid__;
    /** APS_ADATE mapping */
    private UDate apsAdate__;
    /** APS_EUID mapping */
    private int apsEuid__;
    /** APS_EDATE mapping */
    private UDate apsEdate__;
    /** APS_BIKO mapping */
    private String apsBiko__;
    /** APS_SORT mapping */
    private int apsSort__;

    /**
     * <p>Default Constructor
     */
    public AdrPositionModel() {
    }

    /**
     * <p>apsBiko を取得します。
     * @return apsBiko
     */
    public String getApsBiko() {
        return apsBiko__;
    }

    /**
     * <p>apsBiko をセットします。
     * @param apsBiko apsBiko
     */
    public void setApsBiko(String apsBiko) {
        apsBiko__ = apsBiko;
    }

    /**
     * <p>apsSort を取得します。
     * @return apsSort
     */
    public int getApsSort() {
        return apsSort__;
    }

    /**
     * <p>apsSort をセットします。
     * @param apsSort apsSort
     */
    public void setApsSort(int apsSort) {
        apsSort__ = apsSort;
    }

    /**
     * <p>get APS_SID value
     * @return APS_SID value
     */
    public int getApsSid() {
        return apsSid__;
    }

    /**
     * <p>set APS_SID value
     * @param apsSid APS_SID value
     */
    public void setApsSid(int apsSid) {
        apsSid__ = apsSid;
    }

    /**
     * <p>get APS_NAME value
     * @return APS_NAME value
     */
    public String getApsName() {
        return apsName__;
    }

    /**
     * <p>set APS_NAME value
     * @param apsName APS_NAME value
     */
    public void setApsName(String apsName) {
        apsName__ = apsName;
    }

    /**
     * <p>get APS_AUID value
     * @return APS_AUID value
     */
    public int getApsAuid() {
        return apsAuid__;
    }

    /**
     * <p>set APS_AUID value
     * @param apsAuid APS_AUID value
     */
    public void setApsAuid(int apsAuid) {
        apsAuid__ = apsAuid;
    }

    /**
     * <p>get APS_ADATE value
     * @return APS_ADATE value
     */
    public UDate getApsAdate() {
        return apsAdate__;
    }

    /**
     * <p>set APS_ADATE value
     * @param apsAdate APS_ADATE value
     */
    public void setApsAdate(UDate apsAdate) {
        apsAdate__ = apsAdate;
    }

    /**
     * <p>get APS_EUID value
     * @return APS_EUID value
     */
    public int getApsEuid() {
        return apsEuid__;
    }

    /**
     * <p>set APS_EUID value
     * @param apsEuid APS_EUID value
     */
    public void setApsEuid(int apsEuid) {
        apsEuid__ = apsEuid;
    }

    /**
     * <p>get APS_EDATE value
     * @return APS_EDATE value
     */
    public UDate getApsEdate() {
        return apsEdate__;
    }

    /**
     * <p>set APS_EDATE value
     * @param apsEdate APS_EDATE value
     */
    public void setApsEdate(UDate apsEdate) {
        apsEdate__ = apsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(apsSid__);
        buf.append(",");
        buf.append(NullDefault.getString(apsName__, ""));
        buf.append(",");
        buf.append(apsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apsAdate__, ""));
        buf.append(",");
        buf.append(apsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apsEdate__, ""));
        return buf.toString();
    }

}
