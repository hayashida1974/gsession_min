package jp.groupsession.v2.cmn.model.base;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CMN_MAINSCREEN_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMainscreenConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MSC_ID mapping */
    private String mscId__;
    /** MSC_POSITION mapping */
    private int mscPosition__;
    /** MSC_ORDER mapping */
    private int mscOrder__;
    /** MSC_AUID mapping */
    private int mscAuid__;
    /** MSC_ADATE mapping */
    private UDate mscAdate__;
    /** MSC_EUID mapping */
    private int mscEuid__;
    /** MSC_EDATE mapping */
    private UDate mscEdate__;

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenConfModel() {
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
     * <p>get MSC_ID value
     * @return MSC_ID value
     */
    public String getMscId() {
        return mscId__;
    }

    /**
     * <p>set MSC_ID value
     * @param mscId MSC_ID value
     */
    public void setMscId(String mscId) {
        mscId__ = mscId;
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
     * <p>get MSC_ORDER value
     * @return MSC_ORDER value
     */
    public int getMscOrder() {
        return mscOrder__;
    }

    /**
     * <p>set MSC_ORDER value
     * @param mscOrder MSC_ORDER value
     */
    public void setMscOrder(int mscOrder) {
        mscOrder__ = mscOrder;
    }

    /**
     * <p>get MSC_AUID value
     * @return MSC_AUID value
     */
    public int getMscAuid() {
        return mscAuid__;
    }

    /**
     * <p>set MSC_AUID value
     * @param mscAuid MSC_AUID value
     */
    public void setMscAuid(int mscAuid) {
        mscAuid__ = mscAuid;
    }

    /**
     * <p>get MSC_ADATE value
     * @return MSC_ADATE value
     */
    public UDate getMscAdate() {
        return mscAdate__;
    }

    /**
     * <p>set MSC_ADATE value
     * @param mscAdate MSC_ADATE value
     */
    public void setMscAdate(UDate mscAdate) {
        mscAdate__ = mscAdate;
    }

    /**
     * <p>get MSC_EUID value
     * @return MSC_EUID value
     */
    public int getMscEuid() {
        return mscEuid__;
    }

    /**
     * <p>set MSC_EUID value
     * @param mscEuid MSC_EUID value
     */
    public void setMscEuid(int mscEuid) {
        mscEuid__ = mscEuid;
    }

    /**
     * <p>get MSC_EDATE value
     * @return MSC_EDATE value
     */
    public UDate getMscEdate() {
        return mscEdate__;
    }

    /**
     * <p>set MSC_EDATE value
     * @param mscEdate MSC_EDATE value
     */
    public void setMscEdate(UDate mscEdate) {
        mscEdate__ = mscEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(mscId__, ""));
        buf.append(",");
        buf.append(mscPosition__);
        buf.append(",");
        buf.append(mscOrder__);
        buf.append(",");
        buf.append(mscAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mscAdate__, ""));
        buf.append(",");
        buf.append(mscEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mscEdate__, ""));
        return buf.toString();
    }

}
