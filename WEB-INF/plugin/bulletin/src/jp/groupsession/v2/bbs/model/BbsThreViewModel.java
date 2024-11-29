package jp.groupsession.v2.bbs.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>BBS_THRE_VIEW Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreViewModel implements Serializable {

    /** BTI_SID mapping */
    private int btiSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BIV_VIEW_KBN mapping */
    private int bivViewKbn__;
    /** BIV_AUID mapping */
    private int bivAuid__;
    /** BIV_ADATE mapping */
    private UDate bivAdate__;
    /** BIV_EUID mapping */
    private int bivEuid__;
    /** BIV_EDATE mapping */
    private UDate bivEdate__;

    /**
     * <p>Default Constructor
     */
    public BbsThreViewModel() {
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
     * <p>get BIV_VIEW_KBN value
     * @return BIV_VIEW_KBN value
     */
    public int getBivViewKbn() {
        return bivViewKbn__;
    }

    /**
     * <p>set BIV_VIEW_KBN value
     * @param bivViewKbn BIV_VIEW_KBN value
     */
    public void setBivViewKbn(int bivViewKbn) {
        bivViewKbn__ = bivViewKbn;
    }

    /**
     * <p>get BIV_AUID value
     * @return BIV_AUID value
     */
    public int getBivAuid() {
        return bivAuid__;
    }

    /**
     * <p>set BIV_AUID value
     * @param bivAuid BIV_AUID value
     */
    public void setBivAuid(int bivAuid) {
        bivAuid__ = bivAuid;
    }

    /**
     * <p>get BIV_ADATE value
     * @return BIV_ADATE value
     */
    public UDate getBivAdate() {
        return bivAdate__;
    }

    /**
     * <p>set BIV_ADATE value
     * @param bivAdate BIV_ADATE value
     */
    public void setBivAdate(UDate bivAdate) {
        bivAdate__ = bivAdate;
    }

    /**
     * <p>get BIV_EUID value
     * @return BIV_EUID value
     */
    public int getBivEuid() {
        return bivEuid__;
    }

    /**
     * <p>set BIV_EUID value
     * @param bivEuid BIV_EUID value
     */
    public void setBivEuid(int bivEuid) {
        bivEuid__ = bivEuid;
    }

    /**
     * <p>get BIV_EDATE value
     * @return BIV_EDATE value
     */
    public UDate getBivEdate() {
        return bivEdate__;
    }

    /**
     * <p>set BIV_EDATE value
     * @param bivEdate BIV_EDATE value
     */
    public void setBivEdate(UDate bivEdate) {
        bivEdate__ = bivEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(btiSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(bivViewKbn__);
        buf.append(",");
        buf.append(bivAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bivAdate__, ""));
        buf.append(",");
        buf.append(bivEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bivEdate__, ""));
        return buf.toString();
    }

}
