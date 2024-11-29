package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_DISKADMIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnDiskadminModel implements Serializable {

    /** DSK_TYPE mapping */
    private int dskType__;
    /** DSK_VALUE mapping */
    private int dskValue__;
    /** DSK_AUID mapping */
    private int dskAuid__;
    /** DSK_ADATE mapping */
    private UDate dskAdate__;
    /** DSK_EUID mapping */
    private int dskEuid__;
    /** DSK_EDATE mapping */
    private UDate dskEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnDiskadminModel() {
    }

    /**
     * <p>get DSK_TYPE value
     * @return DSK_TYPE value
     */
    public int getDskType() {
        return dskType__;
    }

    /**
     * <p>set DSK_TYPE value
     * @param dskType DSK_TYPE value
     */
    public void setDskType(int dskType) {
        dskType__ = dskType;
    }

    /**
     * <p>get DSK_VALUE value
     * @return DSK_VALUE value
     */
    public int getDskValue() {
        return dskValue__;
    }

    /**
     * <p>set DSK_VALUE value
     * @param dskValue DSK_VALUE value
     */
    public void setDskValue(int dskValue) {
        dskValue__ = dskValue;
    }

    /**
     * <p>get DSK_AUID value
     * @return DSK_AUID value
     */
    public int getDskAuid() {
        return dskAuid__;
    }

    /**
     * <p>set DSK_AUID value
     * @param dskAuid DSK_AUID value
     */
    public void setDskAuid(int dskAuid) {
        dskAuid__ = dskAuid;
    }

    /**
     * <p>get DSK_ADATE value
     * @return DSK_ADATE value
     */
    public UDate getDskAdate() {
        return dskAdate__;
    }

    /**
     * <p>set DSK_ADATE value
     * @param dskAdate DSK_ADATE value
     */
    public void setDskAdate(UDate dskAdate) {
        dskAdate__ = dskAdate;
    }

    /**
     * <p>get DSK_EUID value
     * @return DSK_EUID value
     */
    public int getDskEuid() {
        return dskEuid__;
    }

    /**
     * <p>set DSK_EUID value
     * @param dskEuid DSK_EUID value
     */
    public void setDskEuid(int dskEuid) {
        dskEuid__ = dskEuid;
    }

    /**
     * <p>get DSK_EDATE value
     * @return DSK_EDATE value
     */
    public UDate getDskEdate() {
        return dskEdate__;
    }

    /**
     * <p>set DSK_EDATE value
     * @param dskEdate DSK_EDATE value
     */
    public void setDskEdate(UDate dskEdate) {
        dskEdate__ = dskEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(dskType__);
        buf.append(",");
        buf.append(dskValue__);
        buf.append(",");
        buf.append(dskAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(dskAdate__, ""));
        buf.append(",");
        buf.append(dskEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(dskEdate__, ""));
        return buf.toString();
    }

}
