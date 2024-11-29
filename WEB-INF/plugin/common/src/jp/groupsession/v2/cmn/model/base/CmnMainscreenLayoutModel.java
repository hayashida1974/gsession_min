package jp.groupsession.v2.cmn.model.base;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MSC_POSITION mapping */
    private int mscPosition__;
    /** MSL_AUID mapping */
    private int mslAuid__;
    /** MSL_ADATE mapping */
    private UDate mslAdate__;
    /** MSL_EUID mapping */
    private int mslEuid__;
    /** MSL_EDATE mapping */
    private UDate mslEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutModel() {
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
     * <p>get MSC_POSITION value
     * @return MSC_POSITION value
     */
    public int getMscPosition() {
        return mscPosition__;
    }

    /**
     * <p>set MSC_POSITION value
     * @param mscPosition MSC_POSITION value
     */
    public void setMscPosition(int mscPosition) {
        mscPosition__ = mscPosition;
    }

    /**
     * <p>get MSL_AUID value
     * @return MSL_AUID value
     */
    public int getMslAuid() {
        return mslAuid__;
    }

    /**
     * <p>set MSL_AUID value
     * @param mslAuid MSL_AUID value
     */
    public void setMslAuid(int mslAuid) {
        mslAuid__ = mslAuid;
    }

    /**
     * <p>get MSL_ADATE value
     * @return MSL_ADATE value
     */
    public UDate getMslAdate() {
        return mslAdate__;
    }

    /**
     * <p>set MSL_ADATE value
     * @param mslAdate MSL_ADATE value
     */
    public void setMslAdate(UDate mslAdate) {
        mslAdate__ = mslAdate;
    }

    /**
     * <p>get MSL_EUID value
     * @return MSL_EUID value
     */
    public int getMslEuid() {
        return mslEuid__;
    }

    /**
     * <p>set MSL_EUID value
     * @param mslEuid MSL_EUID value
     */
    public void setMslEuid(int mslEuid) {
        mslEuid__ = mslEuid;
    }

    /**
     * <p>get MSL_EDATE value
     * @return MSL_EDATE value
     */
    public UDate getMslEdate() {
        return mslEdate__;
    }

    /**
     * <p>set MSL_EDATE value
     * @param mslEdate MSL_EDATE value
     */
    public void setMslEdate(UDate mslEdate) {
        mslEdate__ = mslEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mscPosition__);
        buf.append(",");
        buf.append(mslAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mslAdate__, ""));
        buf.append(",");
        buf.append(mslEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mslEdate__, ""));
        return buf.toString();
    }

}
