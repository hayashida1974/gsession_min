package jp.groupsession.v2.bbs.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>BBS_VIEW_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsViewLogCountModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BTI_SID mapping */
    private int btiSid__;
    /** BVL_VIEW_DATE mapping */
    private UDate bvlViewDate__;

    /**
     * <p>Default Constructor
     */
    public BbsViewLogCountModel() {
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
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * <p>get BTI_SID value
     * @return BTI_SID value
     */
    public int getBtiSid() {
        return btiSid__;
    }

    /**
     * <p>set BTI_SID value
     * @param btiSid BTI_SID value
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }

    /**
     * <p>get BVL_VIEW_DATE value
     * @return BVL_VIEW_DATE value
     */
    public UDate getBvlViewDate() {
        return bvlViewDate__;
    }

    /**
     * <p>set BVL_VIEW_DATE value
     * @param bvlViewDate BVL_VIEW_DATE value
     */
    public void setBvlViewDate(UDate bvlViewDate) {
        bvlViewDate__ = bvlViewDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(btiSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bvlViewDate__, ""));
        return buf.toString();
    }

}
