package jp.groupsession.v2.man.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_PCONF_EDIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPconfEditModel implements Serializable {

    /** CPE_PCONF_KBN mapping */
    private int cpePconfKbn__;
    /** CPE_PASSWORD_KBN mapping */
    private int cpePasswordKbn__;
    /** CPE_AUID mapping */
    private int cpeAuid__;
    /** CPE_ADATE mapping */
    private UDate cpeAdate__;
    /** CPE_EUID mapping */
    private int cpeEuid__;
    /** CPE_EDATE mapping */
    private UDate cpeEdate__;
    /** CPE_OTPSEND_KBN mapping */
    private int cpeOtpsendKbn__;

    /**
     * <p>Default Constructor
     */
    public CmnPconfEditModel() {
    }

    /**
     * <p>get CPE_PCONF_KBN value
     * @return CPE_PCONF_KBN value
     */
    public int getCpePconfKbn() {
        return cpePconfKbn__;
    }

    /**
     * <p>set CPE_PCONF_KBN value
     * @param cpePconfKbn CPE_PCONF_KBN value
     */
    public void setCpePconfKbn(int cpePconfKbn) {
        cpePconfKbn__ = cpePconfKbn;
    }

    /**
     * <p>get CPE_PASSWORD_KBN value
     * @return CPE_PASSWORD_KBN value
     */
    public int getCpePasswordKbn() {
        return cpePasswordKbn__;
    }

    /**
     * <p>set CPE_PASSWORD_KBN value
     * @param cpePasswordKbn CPE_PASSWORD_KBN value
     */
    public void setCpePasswordKbn(int cpePasswordKbn) {
        cpePasswordKbn__ = cpePasswordKbn;
    }

    /**
     * <p>get CPE_AUID value
     * @return CPE_AUID value
     */
    public int getCpeAuid() {
        return cpeAuid__;
    }

    /**
     * <p>set CPE_AUID value
     * @param cpeAuid CPE_AUID value
     */
    public void setCpeAuid(int cpeAuid) {
        cpeAuid__ = cpeAuid;
    }

    /**
     * <p>get CPE_ADATE value
     * @return CPE_ADATE value
     */
    public UDate getCpeAdate() {
        return cpeAdate__;
    }

    /**
     * <p>set CPE_ADATE value
     * @param cpeAdate CPE_ADATE value
     */
    public void setCpeAdate(UDate cpeAdate) {
        cpeAdate__ = cpeAdate;
    }

    /**
     * <p>get CPE_EUID value
     * @return CPE_EUID value
     */
    public int getCpeEuid() {
        return cpeEuid__;
    }

    /**
     * <p>set CPE_EUID value
     * @param cpeEuid CPE_EUID value
     */
    public void setCpeEuid(int cpeEuid) {
        cpeEuid__ = cpeEuid;
    }

    /**
     * <p>get CPE_EDATE value
     * @return CPE_EDATE value
     */
    public UDate getCpeEdate() {
        return cpeEdate__;
    }

    /**
     * <p>set CPE_EDATE value
     * @param cpeEdate CPE_EDATE value
     */
    public void setCpeEdate(UDate cpeEdate) {
        cpeEdate__ = cpeEdate;
    }

    /**
     * <p>get CPE_OTPSEND_KBN value
     * @return CPE_OTPSEND_KBN value
     */
    public int getCpeOtpsendKbn() {
        return cpeOtpsendKbn__;
    }

    /**
     * <p>set CPE_OTPSEND_KBN value
     * @param cpeOtpsendKbn CPE_OTPSEND_KBN value
     */
    public void setCpeOtpsendKbn(int cpeOtpsendKbn) {
        cpeOtpsendKbn__ = cpeOtpsendKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cpePconfKbn__);
        buf.append(",");
        buf.append(cpePasswordKbn__);
        buf.append(",");
        buf.append(cpeAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cpeAdate__, ""));
        buf.append(",");
        buf.append(cpeEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cpeEdate__, ""));
        buf.append(",");
        buf.append(cpeOtpsendKbn__);
        return buf.toString();
    }

}
