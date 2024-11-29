package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_USR_THEME Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrThemeModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** CTM_SID mapping */
    private int ctmSid__;
    /** UTM_AUID mapping */
    private int utmAuid__;
    /** UTM_ADATE mapping */
    private UDate utmAdate__;
    /** UTM_EUID mapping */
    private int utmEuid__;
    /** UTM_EDATE mapping */
    private UDate utmEdate__;


    /**
     * <p>Default Constructor
     */
    public CmnUsrThemeModel() {
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
     * <p>get UTM_AUID value
     * @return UTM_AUID value
     */
    public int getUtmAuid() {
        return utmAuid__;
    }

    /**
     * <p>set UTM_AUID value
     * @param utmAuid UTM_AUID value
     */
    public void setUtmAuid(int utmAuid) {
        utmAuid__ = utmAuid;
    }

    /**
     * <p>get UTM_ADATE value
     * @return UTM_ADATE value
     */
    public UDate getUtmAdate() {
        return utmAdate__;
    }

    /**
     * <p>set UTM_ADATE value
     * @param utmAdate UTM_ADATE value
     */
    public void setUtmAdate(UDate utmAdate) {
        utmAdate__ = utmAdate;
    }

    /**
     * <p>get UTM_EUID value
     * @return UTM_EUID value
     */
    public int getUtmEuid() {
        return utmEuid__;
    }

    /**
     * <p>set UTM_EUID value
     * @param utmEuid UTM_EUID value
     */
    public void setUtmEuid(int utmEuid) {
        utmEuid__ = utmEuid;
    }

    /**
     * <p>get UTM_EDATE value
     * @return UTM_EDATE value
     */
    public UDate getUtmEdate() {
        return utmEdate__;
    }

    /**
     * <p>set UTM_EDATE value
     * @param utmEdate CTM_PATH_IMG value
     */
    public void setUtmEdate(UDate utmEdate) {
        utmEdate__ = utmEdate;
    }


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ctmSid__);
        buf.append(",");
        buf.append(utmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(utmAdate__, ""));
        buf.append(",");
        buf.append(utmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(utmEdate__, ""));
        return buf.toString();
    }

}
