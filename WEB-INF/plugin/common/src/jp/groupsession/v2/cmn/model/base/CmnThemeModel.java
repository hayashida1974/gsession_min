package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_THEME Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnThemeModel implements Serializable {

    /** CTM_SID mapping */
    private int ctmSid__;
    /** CTM_ID mapping */
    private String ctmId__;
    /** CTM_NAME mapping */
    private String ctmName__;
    /** CTM_PATH mapping */
    private String ctmPath__;
    /** CTM_PATH_IMG mapping */
    private String ctmPathImg__;
    /** CTM_AUID mapping */
    private int ctmAuid__;
    /** CTM_ADATE mapping */
    private UDate ctmAdate__;
    /** CTM_EUID mapping */
    private int ctmEuid__;
    /** CTM_EDATE mapping */
    private UDate ctmEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnThemeModel() {
    }

    /**
     * <p>get CTM_SID value
     * @return CTM_SID value
     */
    public int getCtmSid() {
        return ctmSid__;
    }

    /**
     * <p>set CTM_SID value
     * @param ctmSid CTM_SID value
     */
    public void setCtmSid(int ctmSid) {
        ctmSid__ = ctmSid;
    }

    /**
     * <p>get CTM_ID value
     * @return CTM_ID value
     */
    public String getCtmId() {
        return ctmId__;
    }

    /**
     * <p>set CTM_ID value
     * @param ctmId CTM_ID value
     */
    public void setCtmId(String ctmId) {
        ctmId__ = ctmId;
    }

    /**
     * <p>get CTM_NAME value
     * @return CTM_NAME value
     */
    public String getCtmName() {
        return ctmName__;
    }

    /**
     * <p>set CTM_NAME value
     * @param ctmName CTM_NAME value
     */
    public void setCtmName(String ctmName) {
        ctmName__ = ctmName;
    }

    /**
     * <p>get CTM_PATH value
     * @return CTM_PATH value
     */
    public String getCtmPath() {
        return ctmPath__;
    }

    /**
     * <p>set CTM_PATH value
     * @param ctmPath CTM_PATH value
     */
    public void setCtmPath(String ctmPath) {
        ctmPath__ = ctmPath;
    }

    /**
     * <p>get CTM_PATH_IMG value
     * @return CTM_PATH_IMG value
     */
    public String getCtmPathImg() {
        return ctmPathImg__;
    }

    /**
     * <p>set CTM_PATH_IMG value
     * @param ctmPathImg CTM_PATH_IMG value
     */
    public void setCtmPathImg(String ctmPathImg) {
        ctmPathImg__ = ctmPathImg;
    }

    /**
     * <p>get CTM_AUID value
     * @return CTM_AUID value
     */
    public int getCtmAuid() {
        return ctmAuid__;
    }

    /**
     * <p>set CTM_AUID value
     * @param ctmAuid CTM_AUID value
     */
    public void setCtmAuid(int ctmAuid) {
        ctmAuid__ = ctmAuid;
    }

    /**
     * <p>get CTM_ADATE value
     * @return CTM_ADATE value
     */
    public UDate getCtmAdate() {
        return ctmAdate__;
    }

    /**
     * <p>set CTM_ADATE value
     * @param ctmAdate CTM_ADATE value
     */
    public void setCtmAdate(UDate ctmAdate) {
        ctmAdate__ = ctmAdate;
    }

    /**
     * <p>get CTM_EUID value
     * @return CTM_EUID value
     */
    public int getCtmEuid() {
        return ctmEuid__;
    }

    /**
     * <p>set CTM_EUID value
     * @param ctmEuid CTM_EUID value
     */
    public void setCtmEuid(int ctmEuid) {
        ctmEuid__ = ctmEuid;
    }

    /**
     * <p>get CTM_EDATE value
     * @return CTM_EDATE value
     */
    public UDate getCtmEdate() {
        return ctmEdate__;
    }

    /**
     * <p>set CTM_EDATE value
     * @param ctmEdate CTM_EDATE value
     */
    public void setCtmEdate(UDate ctmEdate) {
        ctmEdate__ = ctmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ctmSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ctmId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ctmName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ctmPath__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ctmPathImg__, ""));
        buf.append(",");
        buf.append(ctmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ctmAdate__, ""));
        buf.append(",");
        buf.append(ctmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ctmEdate__, ""));
        return buf.toString();
    }

}
