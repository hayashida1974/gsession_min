package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutUserModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MSU_DEFAULT_KBN mapping */
    private int msuDefaultKbn__;
    /** MSU_AUID mapping */
    private int msuAuid__;
    /** MSU_ADATE mapping */
    private UDate msuAdate__;
    /** MSU_EUID mapping */
    private int msuEuid__;
    /** MSU_EDATE mapping */
    private UDate msuEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutUserModel() {
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get MSU_DEFAULT_KBN value
     * @return MSU_DEFAULT_KBN value
     */
    public int getMsuDefaultKbn() {
        return msuDefaultKbn__;
    }

    /**
     * <p>set MSU_DEFAULT_KBN value
     * @param msuDefaultKbn MSU_DEFAULT_KBN value
     */
    public void setMsuDefaultKbn(int msuDefaultKbn) {
        msuDefaultKbn__ = msuDefaultKbn;
    }

    /**
     * <p>get MSU_AUID value
     * @return MSU_AUID value
     */
    public int getMsuAuid() {
        return msuAuid__;
    }

    /**
     * <p>set MSU_AUID value
     * @param msuAuid MSU_AUID value
     */
    public void setMsuAuid(int msuAuid) {
        msuAuid__ = msuAuid;
    }

    /**
     * <p>get MSU_ADATE value
     * @return MSU_ADATE value
     */
    public UDate getMsuAdate() {
        return msuAdate__;
    }

    /**
     * <p>set MSU_ADATE value
     * @param msuAdate MSU_ADATE value
     */
    public void setMsuAdate(UDate msuAdate) {
        msuAdate__ = msuAdate;
    }

    /**
     * <p>get MSU_EUID value
     * @return MSU_EUID value
     */
    public int getMsuEuid() {
        return msuEuid__;
    }

    /**
     * <p>set MSU_EUID value
     * @param msuEuid MSU_EUID value
     */
    public void setMsuEuid(int msuEuid) {
        msuEuid__ = msuEuid;
    }

    /**
     * <p>get MSU_EDATE value
     * @return MSU_EDATE value
     */
    public UDate getMsuEdate() {
        return msuEdate__;
    }

    /**
     * <p>set MSU_EDATE value
     * @param msuEdate MSU_EDATE value
     */
    public void setMsuEdate(UDate msuEdate) {
        msuEdate__ = msuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(msuDefaultKbn__);
        buf.append(",");
        buf.append(msuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(msuAdate__, ""));
        buf.append(",");
        buf.append(msuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(msuEdate__, ""));
        return buf.toString();
    }

}
