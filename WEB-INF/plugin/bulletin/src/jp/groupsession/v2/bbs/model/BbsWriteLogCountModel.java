package jp.groupsession.v2.bbs.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>BBS_WRITE_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsWriteLogCountModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BTI_SID mapping */
    private int btiSid__;
    /** BWL_WRT_DATE mapping */
    private UDate bwlWrtDate__;

    /**
     * <p>Default Constructor
     */
    public BbsWriteLogCountModel() {
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
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
     * <p>get BWL_WRT_DATE value
     * @return BWL_WRT_DATE value
     */
    public UDate getBwlWrtDate() {
        return bwlWrtDate__;
    }

    /**
     * <p>set BWL_WRT_DATE value
     * @param bwlWrtDate BWL_WRT_DATE value
     */
    public void setBwlWrtDate(UDate bwlWrtDate) {
        bwlWrtDate__ = bwlWrtDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(btiSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bwlWrtDate__, ""));
        return buf.toString();
    }

}
