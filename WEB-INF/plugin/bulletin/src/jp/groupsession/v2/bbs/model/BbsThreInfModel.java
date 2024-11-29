package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_THRE_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreInfModel implements Serializable {

    /** BTI_SID mapping */
    private int btiSid__;
    /** BFI_SID mapping */
    private int bfiSid__;
    /** BTI_TITLE mapping */
    private String btiTitle__;
    /** BTI_AUID mapping */
    private int btiAuid__;
    /** BTI_ADATE mapping */
    private UDate btiAdate__;
    /** BTI_EUID mapping */
    private int btiEuid__;
    /** BTI_EDATE mapping */
    private UDate btiEdate__;
    /** BTI_LIMIT mapping */
    private int btiLimit__;
    /** BTI_LIMIT_FR_DATE mapping */
    private UDate btiLimitFrDate__;
    /** BTI_LIMIT_DATE mapping */
    private UDate btiLimitDate__;
    /** BTI_AGID mapping */
    private int btiAgid__;
    /** BTI_EGID mapping */
    private int btiEgid__;
    /** BTI_IMPORTANCE mapping */
    private int btiImportance__;

    /**
     * <p>Default Constructor
     */
    public BbsThreInfModel() {
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
     * <p>get BTI_TITLE value
     * @return BTI_TITLE value
     */
    public String getBtiTitle() {
        return btiTitle__;
    }

    /**
     * <p>set BTI_TITLE value
     * @param btiTitle BTI_TITLE value
     */
    public void setBtiTitle(String btiTitle) {
        btiTitle__ = btiTitle;
    }

    /**
     * <p>get BTI_AUID value
     * @return BTI_AUID value
     */
    public int getBtiAuid() {
        return btiAuid__;
    }

    /**
     * <p>set BTI_AUID value
     * @param btiAuid BTI_AUID value
     */
    public void setBtiAuid(int btiAuid) {
        btiAuid__ = btiAuid;
    }

    /**
     * <p>get BTI_ADATE value
     * @return BTI_ADATE value
     */
    public UDate getBtiAdate() {
        return btiAdate__;
    }

    /**
     * <p>set BTI_ADATE value
     * @param btiAdate BTI_ADATE value
     */
    public void setBtiAdate(UDate btiAdate) {
        btiAdate__ = btiAdate;
    }

    /**
     * <p>get BTI_EUID value
     * @return BTI_EUID value
     */
    public int getBtiEuid() {
        return btiEuid__;
    }

    /**
     * <p>set BTI_EUID value
     * @param btiEuid BTI_EUID value
     */
    public void setBtiEuid(int btiEuid) {
        btiEuid__ = btiEuid;
    }

    /**
     * <p>get BTI_EDATE value
     * @return BTI_EDATE value
     */
    public UDate getBtiEdate() {
        return btiEdate__;
    }

    /**
     * <p>set BTI_EDATE value
     * @param btiEdate BTI_EDATE value
     */
    public void setBtiEdate(UDate btiEdate) {
        btiEdate__ = btiEdate;
    }

    /**
     * <p>get BTI_LIMIT value
     * @return BTI_LIMIT value
     */
    public int getBtiLimit() {
        return btiLimit__;
    }

    /**
     * <p>set BTI_LIMIT value
     * @param btiLimit BTI_LIMIT value
     */
    public void setBtiLimit(int btiLimit) {
        btiLimit__ = btiLimit;
    }

    /**
     * <p>get BTI_LIMIT_DATE value
     * @return BTI_LIMIT_DATE value
     */
    public UDate getBtiLimitDate() {
        return btiLimitDate__;
    }

    /**
     * <p>set BTI_LIMIT_DATE value
     * @param btiLimitDate BTI_LIMIT_DATE value
     */
    public void setBtiLimitDate(UDate btiLimitDate) {
        btiLimitDate__ = btiLimitDate;
    }

    /**
     * <p>get BTI_AGID value
     * @return BTI_AGID value
     */
    public int getBtiAgid() {
        return btiAgid__;
    }

    /**
     * <p>set BTI_AGID value
     * @param btiAgid BTI_AGID value
     */
    public void setBtiAgid(int btiAgid) {
        btiAgid__ = btiAgid;
    }

    /**
     * <p>get BTI_EGID value
     * @return BTI_EGID value
     */
    public int getBtiEgid() {
        return btiEgid__;
    }

    /**
     * <p>set BTI_EGID value
     * @param btiEgid BTI_EGID value
     */
    public void setBtiEgid(int btiEgid) {
        btiEgid__ = btiEgid;
    }

    /**
     * <p>btiImportance を取得します。
     * @return btiImportance
     */
    public int getBtiImportance() {
        return btiImportance__;
    }

    /**
     * <p>btiImportance をセットします。
     * @param btiImportance btiImportance
     */
    public void setBtiImportance(int btiImportance) {
        btiImportance__ = btiImportance;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(btiSid__);
        buf.append(",");
        buf.append(bfiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(btiTitle__, ""));
        buf.append(",");
        buf.append(btiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btiAdate__, ""));
        buf.append(",");
        buf.append(btiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btiEdate__, ""));
        buf.append(",");
        buf.append(btiLimit__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btiLimitDate__, ""));
        buf.append(",");
        buf.append(btiAgid__);
        buf.append(",");
        buf.append(btiEgid__);
        return buf.toString();
    }

    /**
     * <p>btiLimitFrDate を取得します。
     * @return btiLimitFrDate
     */
    public UDate getBtiLimitFrDate() {
        return btiLimitFrDate__;
    }

    /**
     * <p>btiLimitFrDate をセットします。
     * @param btiLimitFrDate btiLimitFrDate
     */
    public void setBtiLimitFrDate(UDate btiLimitFrDate) {
        btiLimitFrDate__ = btiLimitFrDate;
    }

}
