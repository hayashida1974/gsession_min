package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_USR_ADM_SORT_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnUsrAdmSortConfModel implements Serializable {

    /** UAS_SORT_KBN mapping */
    private int uasSortKbn__;
    /** UAS_SORT_KEY1 mapping */
    private int uasSortKey1__;
    /** UAS_SORT_ORDER1 mapping */
    private int uasSortOrder1__;
    /** UAS_SORT_KEY2 mapping */
    private int uasSortKey2__;
    /** UAS_SORT_ORDER2 mapping */
    private int uasSortOrder2__;
    /** UAS_AUID mapping */
    private int uasAuid__;
    /** UAS_ADATE mapping */
    private UDate uasAdate__;
    /** UAS_EUID mapping */
    private int uasEuid__;
    /** UAS_EDATE mapping */
    private UDate uasEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnUsrAdmSortConfModel() {
    }

    /**
     * <p>get UAS_SORT_KBN value
     * @return UAS_SORT_KBN value
     */
    public int getUasSortKbn() {
        return uasSortKbn__;
    }

    /**
     * <p>set UAS_SORT_KBN value
     * @param uasSortKbn UAS_SORT_KBN value
     */
    public void setUasSortKbn(int uasSortKbn) {
        uasSortKbn__ = uasSortKbn;
    }

    /**
     * <p>get UAS_SORT_KEY1 value
     * @return UAS_SORT_KEY1 value
     */
    public int getUasSortKey1() {
        return uasSortKey1__;
    }

    /**
     * <p>set UAS_SORT_KEY1 value
     * @param uasSortKey1 UAS_SORT_KEY1 value
     */
    public void setUasSortKey1(int uasSortKey1) {
        uasSortKey1__ = uasSortKey1;
    }

    /**
     * <p>get UAS_SORT_ORDER1 value
     * @return UAS_SORT_ORDER1 value
     */
    public int getUasSortOrder1() {
        return uasSortOrder1__;
    }

    /**
     * <p>set UAS_SORT_ORDER1 value
     * @param uasSortOrder1 UAS_SORT_ORDER1 value
     */
    public void setUasSortOrder1(int uasSortOrder1) {
        uasSortOrder1__ = uasSortOrder1;
    }

    /**
     * <p>get UAS_SORT_KEY2 value
     * @return UAS_SORT_KEY2 value
     */
    public int getUasSortKey2() {
        return uasSortKey2__;
    }

    /**
     * <p>set UAS_SORT_KEY2 value
     * @param uasSortKey2 UAS_SORT_KEY2 value
     */
    public void setUasSortKey2(int uasSortKey2) {
        uasSortKey2__ = uasSortKey2;
    }

    /**
     * <p>get UAS_SORT_ORDER2 value
     * @return UAS_SORT_ORDER2 value
     */
    public int getUasSortOrder2() {
        return uasSortOrder2__;
    }

    /**
     * <p>set UAS_SORT_ORDER2 value
     * @param uasSortOrder2 UAS_SORT_ORDER2 value
     */
    public void setUasSortOrder2(int uasSortOrder2) {
        uasSortOrder2__ = uasSortOrder2;
    }

    /**
     * <p>get UAS_AUID value
     * @return UAS_AUID value
     */
    public int getUasAuid() {
        return uasAuid__;
    }

    /**
     * <p>set UAS_AUID value
     * @param uasAuid UAS_AUID value
     */
    public void setUasAuid(int uasAuid) {
        uasAuid__ = uasAuid;
    }

    /**
     * <p>get UAS_ADATE value
     * @return UAS_ADATE value
     */
    public UDate getUasAdate() {
        return uasAdate__;
    }

    /**
     * <p>set UAS_ADATE value
     * @param uasAdate UAS_ADATE value
     */
    public void setUasAdate(UDate uasAdate) {
        uasAdate__ = uasAdate;
    }

    /**
     * <p>get UAS_EUID value
     * @return UAS_EUID value
     */
    public int getUasEuid() {
        return uasEuid__;
    }

    /**
     * <p>set UAS_EUID value
     * @param uasEuid UAS_EUID value
     */
    public void setUasEuid(int uasEuid) {
        uasEuid__ = uasEuid;
    }

    /**
     * <p>get UAS_EDATE value
     * @return UAS_EDATE value
     */
    public UDate getUasEdate() {
        return uasEdate__;
    }

    /**
     * <p>set UAS_EDATE value
     * @param uasEdate UAS_EDATE value
     */
    public void setUasEdate(UDate uasEdate) {
        uasEdate__ = uasEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(uasSortKbn__);
        buf.append(",");
        buf.append(uasSortKey1__);
        buf.append(",");
        buf.append(uasSortOrder1__);
        buf.append(",");
        buf.append(uasSortKey2__);
        buf.append(",");
        buf.append(uasSortOrder2__);
        buf.append(",");
        buf.append(uasAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uasAdate__, ""));
        buf.append(",");
        buf.append(uasEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(uasEdate__, ""));
        return buf.toString();
    }

}
