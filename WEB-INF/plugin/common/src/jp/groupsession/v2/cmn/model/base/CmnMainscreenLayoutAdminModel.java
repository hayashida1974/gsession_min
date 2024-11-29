package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutAdminModel implements Serializable {

    /** MLC_ADM_LAYOUT_KBN mapping */
    private int mlcAdmLayoutKbn__;
    /** MLC_DEFAULT_KBN mapping */
    private int mlcDefaultKbn__;
    /** MLC_AUID mapping */
    private int mlcAuid__;
    /** MLC_ADATE mapping */
    private UDate mlcAdate__;
    /** MLC_EUID mapping */
    private int mlcEuid__;
    /** MLC_EDATE mapping */
    private UDate mlcEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutAdminModel() {
    }

    /**
     * <p>get MLC_ADM_LAYOUT_KBN value
     * @return MLC_ADM_LAYOUT_KBN value
     */
    public int getMlcAdmLayoutKbn() {
        return mlcAdmLayoutKbn__;
    }

    /**
     * <p>set MLC_ADM_LAYOUT_KBN value
     * @param mlcAdmLayoutKbn MLC_ADM_LAYOUT_KBN value
     */
    public void setMlcAdmLayoutKbn(int mlcAdmLayoutKbn) {
        mlcAdmLayoutKbn__ = mlcAdmLayoutKbn;
    }

    /**
     * <p>get MLC_DEFAULT_KBN value
     * @return MLC_DEFAULT_KBN value
     */
    public int getMlcDefaultKbn() {
        return mlcDefaultKbn__;
    }

    /**
     * <p>set MLC_DEFAULT_KBN value
     * @param mlcDefaultKbn MLC_DEFAULT_KBN value
     */
    public void setMlcDefaultKbn(int mlcDefaultKbn) {
        mlcDefaultKbn__ = mlcDefaultKbn;
    }

    /**
     * <p>get MLC_AUID value
     * @return MLC_AUID value
     */
    public int getMlcAuid() {
        return mlcAuid__;
    }

    /**
     * <p>set MLC_AUID value
     * @param mlcAuid MLC_AUID value
     */
    public void setMlcAuid(int mlcAuid) {
        mlcAuid__ = mlcAuid;
    }

    /**
     * <p>get MLC_ADATE value
     * @return MLC_ADATE value
     */
    public UDate getMlcAdate() {
        return mlcAdate__;
    }

    /**
     * <p>set MLC_ADATE value
     * @param mlcAdate MLC_ADATE value
     */
    public void setMlcAdate(UDate mlcAdate) {
        mlcAdate__ = mlcAdate;
    }

    /**
     * <p>get MLC_EUID value
     * @return MLC_EUID value
     */
    public int getMlcEuid() {
        return mlcEuid__;
    }

    /**
     * <p>set MLC_EUID value
     * @param mlcEuid MLC_EUID value
     */
    public void setMlcEuid(int mlcEuid) {
        mlcEuid__ = mlcEuid;
    }

    /**
     * <p>get MLC_EDATE value
     * @return MLC_EDATE value
     */
    public UDate getMlcEdate() {
        return mlcEdate__;
    }

    /**
     * <p>set MLC_EDATE value
     * @param mlcEdate MLC_EDATE value
     */
    public void setMlcEdate(UDate mlcEdate) {
        mlcEdate__ = mlcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(mlcAdmLayoutKbn__);
        buf.append(",");
        buf.append(mlcDefaultKbn__);
        buf.append(",");
        buf.append(mlcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mlcAdate__, ""));
        buf.append(",");
        buf.append(mlcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mlcEdate__, ""));
        return buf.toString();
    }

}
