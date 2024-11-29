package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_TDFK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnTdfkModel implements Serializable {

    /** TDF_SID mapping */
    private int tdfSid__;
    /** TDF_NAME mapping */
    private String tdfName__;
    /** TDF_AUID mapping */
    private int tdfAuid__;
    /** TDF_ADATE mapping */
    private UDate tdfAdate__;
    /** TDF_EUID mapping */
    private int tdfEuid__;
    /** TDF_EDATE mapping */
    private UDate tdfEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnTdfkModel() {
    }

    /**
     * <p>get TDF_SID value
     * @return TDF_SID value
     */
    public int getTdfSid() {
        return tdfSid__;
    }

    /**
     * <p>set TDF_SID value
     * @param tdfSid TDF_SID value
     */
    public void setTdfSid(int tdfSid) {
        tdfSid__ = tdfSid;
    }

    /**
     * <p>get TDF_NAME value
     * @return TDF_NAME value
     */
    public String getTdfName() {
        return tdfName__;
    }

    /**
     * <p>set TDF_NAME value
     * @param tdfName TDF_NAME value
     */
    public void setTdfName(String tdfName) {
        tdfName__ = tdfName;
    }

    /**
     * <p>get TDF_AUID value
     * @return TDF_AUID value
     */
    public int getTdfAuid() {
        return tdfAuid__;
    }

    /**
     * <p>set TDF_AUID value
     * @param tdfAuid TDF_AUID value
     */
    public void setTdfAuid(int tdfAuid) {
        tdfAuid__ = tdfAuid;
    }

    /**
     * <p>get TDF_ADATE value
     * @return TDF_ADATE value
     */
    public UDate getTdfAdate() {
        return tdfAdate__;
    }

    /**
     * <p>set TDF_ADATE value
     * @param tdfAdate TDF_ADATE value
     */
    public void setTdfAdate(UDate tdfAdate) {
        tdfAdate__ = tdfAdate;
    }

    /**
     * <p>get TDF_EUID value
     * @return TDF_EUID value
     */
    public int getTdfEuid() {
        return tdfEuid__;
    }

    /**
     * <p>set TDF_EUID value
     * @param tdfEuid TDF_EUID value
     */
    public void setTdfEuid(int tdfEuid) {
        tdfEuid__ = tdfEuid;
    }

    /**
     * <p>get TDF_EDATE value
     * @return TDF_EDATE value
     */
    public UDate getTdfEdate() {
        return tdfEdate__;
    }

    /**
     * <p>set TDF_EDATE value
     * @param tdfEdate TDF_EDATE value
     */
    public void setTdfEdate(UDate tdfEdate) {
        tdfEdate__ = tdfEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(tdfSid__);
        buf.append(",");
        buf.append(NullDefault.getString(tdfName__, ""));
        buf.append(",");
        buf.append(tdfAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tdfAdate__, ""));
        buf.append(",");
        buf.append(tdfEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(tdfEdate__, ""));
        return buf.toString();
    }

}
