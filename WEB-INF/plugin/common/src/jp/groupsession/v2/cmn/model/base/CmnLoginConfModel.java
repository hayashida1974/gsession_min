package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_LOGIN_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLoginConfModel implements Serializable {

    /** LLC_LOCKOUT_KBN mapping */
    private int llcLockoutKbn__;
    /** LLC_FAIL_CNT mapping */
    private int llcFailCnt__;
    /** LLC_FAIL_AGE mapping */
    private int llcFailAge__;
    /** LLC_LOCK_AGE mapping */
    private int llcLockAge__;
    /** LLC_AUID mapping */
    private int llcAuid__;
    /** LLC_ADATE mapping */
    private UDate llcAdate__;
    /** LLC_EUID mapping */
    private int llcEuid__;
    /** LLC_EDATE mapping */
    private UDate llcEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnLoginConfModel() {
    }

    /**
     * <p>get LLC_LOCKOUT_KBN value
     * @return LLC_LOCKOUT_KBN value
     */
    public int getLlcLockoutKbn() {
        return llcLockoutKbn__;
    }

    /**
     * <p>set LLC_LOCKOUT_KBN value
     * @param llcLockoutKbn LLC_LOCKOUT_KBN value
     */
    public void setLlcLockoutKbn(int llcLockoutKbn) {
        llcLockoutKbn__ = llcLockoutKbn;
    }

    /**
     * <p>get LLC_FAIL_CNT value
     * @return LLC_FAIL_CNT value
     */
    public int getLlcFailCnt() {
        return llcFailCnt__;
    }

    /**
     * <p>set LLC_FAIL_CNT value
     * @param llcFailCnt LLC_FAIL_CNT value
     */
    public void setLlcFailCnt(int llcFailCnt) {
        llcFailCnt__ = llcFailCnt;
    }

    /**
     * <p>get LLC_FAIL_AGE value
     * @return LLC_FAIL_AGE value
     */
    public int getLlcFailAge() {
        return llcFailAge__;
    }

    /**
     * <p>set LLC_FAIL_AGE value
     * @param llcFailAge LLC_FAIL_AGE value
     */
    public void setLlcFailAge(int llcFailAge) {
        llcFailAge__ = llcFailAge;
    }

    /**
     * <p>get LLC_LOCK_AGE value
     * @return LLC_LOCK_AGE value
     */
    public int getLlcLockAge() {
        return llcLockAge__;
    }

    /**
     * <p>set LLC_LOCK_AGE value
     * @param llcLockAge LLC_LOCK_AGE value
     */
    public void setLlcLockAge(int llcLockAge) {
        llcLockAge__ = llcLockAge;
    }

    /**
     * <p>get LLC_AUID value
     * @return LLC_AUID value
     */
    public int getLlcAuid() {
        return llcAuid__;
    }

    /**
     * <p>set LLC_AUID value
     * @param llcAuid LLC_AUID value
     */
    public void setLlcAuid(int llcAuid) {
        llcAuid__ = llcAuid;
    }

    /**
     * <p>get LLC_ADATE value
     * @return LLC_ADATE value
     */
    public UDate getLlcAdate() {
        return llcAdate__;
    }

    /**
     * <p>set LLC_ADATE value
     * @param llcAdate LLC_ADATE value
     */
    public void setLlcAdate(UDate llcAdate) {
        llcAdate__ = llcAdate;
    }

    /**
     * <p>get LLC_EUID value
     * @return LLC_EUID value
     */
    public int getLlcEuid() {
        return llcEuid__;
    }

    /**
     * <p>set LLC_EUID value
     * @param llcEuid LLC_EUID value
     */
    public void setLlcEuid(int llcEuid) {
        llcEuid__ = llcEuid;
    }

    /**
     * <p>get LLC_EDATE value
     * @return LLC_EDATE value
     */
    public UDate getLlcEdate() {
        return llcEdate__;
    }

    /**
     * <p>set LLC_EDATE value
     * @param llcEdate LLC_EDATE value
     */
    public void setLlcEdate(UDate llcEdate) {
        llcEdate__ = llcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(llcLockoutKbn__);
        buf.append(",");
        buf.append(llcFailCnt__);
        buf.append(",");
        buf.append(llcFailAge__);
        buf.append(",");
        buf.append(llcLockAge__);
        buf.append(",");
        buf.append(llcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(llcAdate__, ""));
        buf.append(",");
        buf.append(llcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(llcEdate__, ""));
        return buf.toString();
    }

}
