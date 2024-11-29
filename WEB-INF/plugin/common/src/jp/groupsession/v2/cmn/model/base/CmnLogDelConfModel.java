package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_LOG_DEL_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogDelConfModel implements Serializable {

    /** LDC_ADL_KBN mapping */
    private int ldcAdlKbn__;
    /** LDC_ADL_YEAR mapping */
    private int ldcAdlYear__;
    /** LDC_ADL_MONTH mapping */
    private int ldcAdlMonth__;
    /** LDC_AUID mapping */
    private int ldcAuid__;
    /** LDC_ADATE mapping */
    private UDate ldcAdate__;
    /** LDC_EUID mapping */
    private int ldcEuid__;
    /** LDC_EDATE mapping */
    private UDate ldcEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnLogDelConfModel() {
    }

    /**
     * <p>get LDC_ADL_KBN value
     * @return LDC_ADL_KBN value
     */
    public int getLdcAdlKbn() {
        return ldcAdlKbn__;
    }

    /**
     * <p>set LDC_ADL_KBN value
     * @param ldcAdlKbn LDC_ADL_KBN value
     */
    public void setLdcAdlKbn(int ldcAdlKbn) {
        ldcAdlKbn__ = ldcAdlKbn;
    }

    /**
     * <p>get LDC_ADL_YEAR value
     * @return LDC_ADL_YEAR value
     */
    public int getLdcAdlYear() {
        return ldcAdlYear__;
    }

    /**
     * <p>set LDC_ADL_YEAR value
     * @param ldcAdlYear LDC_ADL_YEAR value
     */
    public void setLdcAdlYear(int ldcAdlYear) {
        ldcAdlYear__ = ldcAdlYear;
    }

    /**
     * <p>get LDC_ADL_MONTH value
     * @return LDC_ADL_MONTH value
     */
    public int getLdcAdlMonth() {
        return ldcAdlMonth__;
    }

    /**
     * <p>set LDC_ADL_MONTH value
     * @param ldcAdlMonth LDC_ADL_MONTH value
     */
    public void setLdcAdlMonth(int ldcAdlMonth) {
        ldcAdlMonth__ = ldcAdlMonth;
    }

    /**
     * <p>get LDC_AUID value
     * @return LDC_AUID value
     */
    public int getLdcAuid() {
        return ldcAuid__;
    }

    /**
     * <p>set LDC_AUID value
     * @param ldcAuid LDC_AUID value
     */
    public void setLdcAuid(int ldcAuid) {
        ldcAuid__ = ldcAuid;
    }

    /**
     * <p>get LDC_ADATE value
     * @return LDC_ADATE value
     */
    public UDate getLdcAdate() {
        return ldcAdate__;
    }

    /**
     * <p>set LDC_ADATE value
     * @param ldcAdate LDC_ADATE value
     */
    public void setLdcAdate(UDate ldcAdate) {
        ldcAdate__ = ldcAdate;
    }

    /**
     * <p>get LDC_EUID value
     * @return LDC_EUID value
     */
    public int getLdcEuid() {
        return ldcEuid__;
    }

    /**
     * <p>set LDC_EUID value
     * @param ldcEuid LDC_EUID value
     */
    public void setLdcEuid(int ldcEuid) {
        ldcEuid__ = ldcEuid;
    }

    /**
     * <p>get LDC_EDATE value
     * @return LDC_EDATE value
     */
    public UDate getLdcEdate() {
        return ldcEdate__;
    }

    /**
     * <p>set LDC_EDATE value
     * @param ldcEdate LDC_EDATE value
     */
    public void setLdcEdate(UDate ldcEdate) {
        ldcEdate__ = ldcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ldcAdlKbn__);
        buf.append(",");
        buf.append(ldcAdlYear__);
        buf.append(",");
        buf.append(ldcAdlMonth__);
        buf.append(",");
        buf.append(ldcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ldcAdate__, ""));
        buf.append(",");
        buf.append(ldcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ldcEdate__, ""));
        return buf.toString();
    }

}
