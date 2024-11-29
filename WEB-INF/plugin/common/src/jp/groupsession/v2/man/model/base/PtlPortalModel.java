package jp.groupsession.v2.man.model.base;
import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>PTL_PORTAL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalModel implements Serializable {

    /** PTL_SID mapping */
    private int ptlSid__;
    /** PTL_NAME mapping */
    private String ptlName__;
    /** PTL_DESCRIPTION mapping */
    private String ptlDescription__;
    /** PTL_OPEN mapping */
    private int ptlOpen__;
    /** PTL_ACCESS mapping */
    private int ptlAccess__;
    /** PTL_AUID mapping */
    private int ptlAuid__;
    /** PTL_ADATE mapping */
    private UDate ptlAdate__;
    /** PTL_EUID mapping */
    private int ptlEuid__;
    /** PTL_EDATE mapping */
    private UDate ptlEdate__;

    /** PTS_SORT mapping */
    private int ptsSort__;

    /** PTS_SORT 文字列(ポータルSID:並び順) */
    private String strPtsSort__;


    /**
     * <p>Default Constructor
     */
    public PtlPortalModel() {
    }

    /**
     * <p>get PTL_SID value
     * @return PTL_SID value
     */
    public int getPtlSid() {
        return ptlSid__;
    }

    /**
     * <p>set PTL_SID value
     * @param ptlSid PTL_SID value
     */
    public void setPtlSid(int ptlSid) {
        ptlSid__ = ptlSid;
    }

    /**
     * <p>get PTL_NAME value
     * @return PTL_NAME value
     */
    public String getPtlName() {
        return ptlName__;
    }

    /**
     * <p>set PTL_NAME value
     * @param ptlName PTL_NAME value
     */
    public void setPtlName(String ptlName) {
        ptlName__ = ptlName;
    }

    /**
     * <p>get PTL_DESCRIPTION value
     * @return PTL_DESCRIPTION value
     */
    public String getPtlDescription() {
        return ptlDescription__;
    }

    /**
     * <p>set PTL_DESCRIPTION value
     * @param ptlDescription PTL_DESCRIPTION value
     */
    public void setPtlDescription(String ptlDescription) {
        ptlDescription__ = ptlDescription;
    }

    /**
     * <p>get PTL_OPEN value
     * @return PTL_OPEN value
     */
    public int getPtlOpen() {
        return ptlOpen__;
    }

    /**
     * <p>set PTL_OPEN value
     * @param ptlOpen PTL_OPEN value
     */
    public void setPtlOpen(int ptlOpen) {
        ptlOpen__ = ptlOpen;
    }

    /**
     * <p>get PTL_AUID value
     * @return PTL_AUID value
     */
    public int getPtlAuid() {
        return ptlAuid__;
    }

    /**
     * <p>set PTL_AUID value
     * @param ptlAuid PTL_AUID value
     */
    public void setPtlAuid(int ptlAuid) {
        ptlAuid__ = ptlAuid;
    }

    /**
     * <p>get PTL_ADATE value
     * @return PTL_ADATE value
     */
    public UDate getPtlAdate() {
        return ptlAdate__;
    }

    /**
     * <p>set PTL_ADATE value
     * @param ptlAdate PTL_ADATE value
     */
    public void setPtlAdate(UDate ptlAdate) {
        ptlAdate__ = ptlAdate;
    }

    /**
     * <p>get PTL_EUID value
     * @return PTL_EUID value
     */
    public int getPtlEuid() {
        return ptlEuid__;
    }

    /**
     * <p>set PTL_EUID value
     * @param ptlEuid PTL_EUID value
     */
    public void setPtlEuid(int ptlEuid) {
        ptlEuid__ = ptlEuid;
    }

    /**
     * <p>get PTL_EDATE value
     * @return PTL_EDATE value
     */
    public UDate getPtlEdate() {
        return ptlEdate__;
    }

    /**
     * <p>set PTL_EDATE value
     * @param ptlEdate PTL_EDATE value
     */
    public void setPtlEdate(UDate ptlEdate) {
        ptlEdate__ = ptlEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(ptlSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ptlName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ptlDescription__, ""));
        buf.append(",");
        buf.append(ptlOpen__);
        buf.append(",");
        buf.append(ptlAccess__);
        buf.append(",");
        buf.append(ptlAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptlAdate__, ""));
        buf.append(",");
        buf.append(ptlEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptlEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>ptlAccess を取得します。
     * @return ptlAccess
     */
    public int getPtlAccess() {
        return ptlAccess__;
    }

    /**
     * <p>ptlAccess をセットします。
     * @param ptlAccess ptlAccess
     */
    public void setPtlAccess(int ptlAccess) {
        ptlAccess__ = ptlAccess;
    }

    /**
     * <p>ptsSort を取得します。
     * @return ptsSort
     */
    public int getPtsSort() {
        return ptsSort__;
    }

    /**
     * <p>ptsSort をセットします。
     * @param ptsSort ptsSort
     */
    public void setPtsSort(int ptsSort) {
        ptsSort__ = ptsSort;
    }

    /**
     * @return strPtsSort
     */
    public String getStrPtsSort() {
        return strPtsSort__;
    }

    /**
     * @param strPtsSort セットする strPtsSort
     */
    public void setStrPtsSort(String strPtsSort) {
        strPtsSort__ = strPtsSort;
    }

}
