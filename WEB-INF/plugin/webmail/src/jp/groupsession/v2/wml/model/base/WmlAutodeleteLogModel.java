package jp.groupsession.v2.wml.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WML_AUTODELETE_LOG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAutodeleteLogModel implements Serializable {

    /** WAL_DEL_KBN mapping */
    private int walDelKbn__;
    /** WAL_DEL_YEAR mapping */
    private int walDelYear__;
    /** WAL_DEL_MONTH mapping */
    private int walDelMonth__;
    /** WAL_DEL_DAY mapping */
    private int walDelDay__;
    /** WAL_AUID mapping */
    private int walAuid__;
    /** WAL_ADATE mapping */
    private UDate walAdate__;
    /** WAL_EUID mapping */
    private int walEuid__;
    /** WAL_EDATE mapping */
    private UDate walEdate__;

    /**
     * <p>Default Constructor
     */
    public WmlAutodeleteLogModel() {
    }

    /**
     * <p>get WAL_DEL_KBN value
     * @return WAL_DEL_KBN value
     */
    public int getWalDelKbn() {
        return walDelKbn__;
    }

    /**
     * <p>set WAL_DEL_KBN value
     * @param walDelKbn WAL_DEL_KBN value
     */
    public void setWalDelKbn(int walDelKbn) {
        walDelKbn__ = walDelKbn;
    }

    /**
     * <p>get WAL_DEL_YEAR value
     * @return WAL_DEL_YEAR value
     */
    public int getWalDelYear() {
        return walDelYear__;
    }

    /**
     * <p>set WAL_DEL_YEAR value
     * @param walDelYear WAL_DEL_YEAR value
     */
    public void setWalDelYear(int walDelYear) {
        walDelYear__ = walDelYear;
    }

    /**
     * <p>get WAL_DEL_MONTH value
     * @return WAL_DEL_MONTH value
     */
    public int getWalDelMonth() {
        return walDelMonth__;
    }

    /**
     * <p>set WAL_DEL_MONTH value
     * @param walDelMonth WAL_DEL_MONTH value
     */
    public void setWalDelMonth(int walDelMonth) {
        walDelMonth__ = walDelMonth;
    }

    /**
     * <p>get WAL_DEL_DAY value
     * @return WAL_DEL_DAY value
     */
    public int getWalDelDay() {
        return walDelDay__;
    }

    /**
     * <p>set WAL_DEL_DAY value
     * @param walDelDay WAL_DEL_DAY value
     */
    public void setWalDelDay(int walDelDay) {
        walDelDay__ = walDelDay;
    }

    /**
     * <p>get WAL_AUID value
     * @return WAL_AUID value
     */
    public int getWalAuid() {
        return walAuid__;
    }

    /**
     * <p>set WAL_AUID value
     * @param walAuid WAL_AUID value
     */
    public void setWalAuid(int walAuid) {
        walAuid__ = walAuid;
    }

    /**
     * <p>get WAL_ADATE value
     * @return WAL_ADATE value
     */
    public UDate getWalAdate() {
        return walAdate__;
    }

    /**
     * <p>set WAL_ADATE value
     * @param walAdate WAL_ADATE value
     */
    public void setWalAdate(UDate walAdate) {
        walAdate__ = walAdate;
    }

    /**
     * <p>get WAL_EUID value
     * @return WAL_EUID value
     */
    public int getWalEuid() {
        return walEuid__;
    }

    /**
     * <p>set WAL_EUID value
     * @param walEuid WAL_EUID value
     */
    public void setWalEuid(int walEuid) {
        walEuid__ = walEuid;
    }

    /**
     * <p>get WAL_EDATE value
     * @return WAL_EDATE value
     */
    public UDate getWalEdate() {
        return walEdate__;
    }

    /**
     * <p>set WAL_EDATE value
     * @param walEdate WAL_EDATE value
     */
    public void setWalEdate(UDate walEdate) {
        walEdate__ = walEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(walDelKbn__);
        buf.append(",");
        buf.append(walDelYear__);
        buf.append(",");
        buf.append(walDelMonth__);
        buf.append(",");
        buf.append(walDelDay__);
        buf.append(",");
        buf.append(walAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(walAdate__, ""));
        buf.append(",");
        buf.append(walEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(walEdate__, ""));
        return buf.toString();
    }

}
