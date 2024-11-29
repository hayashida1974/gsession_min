package jp.groupsession.v2.rng.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>RNG_SINGI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngSingiModel implements Serializable {

    /** RKS_SID mapping */
    private int rksSid__;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** RNG_SID mapping */
    private int rngSid__;
    /** USR_SID_KOETU mapping */
    private int usrSidKoetu__ = -1;
    /** USR_SID_DAIRI mapping */
    private int usrSidDairi__ = -1;
    /** RSS_STATUS mapping */
    private int rssStatus__;
    /** RSS_COMMENT mapping */
    private String rssComment__;
    /** RSS_CHKDATE mapping */
    private UDate rssChkdate__;
    /** RSS_AUID mapping */
    private int rssAuid__ = -1;
    /** RSS_ADATE mapping */
    private UDate rssAdate__;
    /** RSS_EUID mapping */
    private int rssEuid__ = -1;
    /** RSS_EDATE mapping */
    private UDate rssEdate__;

    /**
     * <p>Default Constructor
     */
    public RngSingiModel() {
    }

    /**
     * <p>get RKS_SID value
     * @return RKS_SID value
     */
    public int getRksSid() {
        return rksSid__;
    }

    /**
     * <p>set RKS_SID value
     * @param rksSid RKS_SID value
     */
    public void setRksSid(int rksSid) {
        rksSid__ = rksSid;
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
     * <p>get RNG_SID value
     * @return RNG_SID value
     */
    public int getRngSid() {
        return rngSid__;
    }

    /**
     * <p>set RNG_SID value
     * @param rngSid RNG_SID value
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }

    /**
     * <p>get USR_SID_DAIRI value
     * @return USR_SID_DAIRI value
     */
    public int getUsrSidDairi() {
        return usrSidDairi__;
    }

    /**
     * <p>set USR_SID_DAIRI value
     * @param usrSidDairi USR_SID_DAIRI value
     */
    public void setUsrSidDairi(int usrSidDairi) {
        usrSidDairi__ = usrSidDairi;
    }

    /**
     * <p>get RSS_STATUS value
     * @return RSS_STATUS value
     */
    public int getRssStatus() {
        return rssStatus__;
    }

    /**
     * <p>set RSS_STATUS value
     * @param rssStatus RSS_STATUS value
     */
    public void setRssStatus(int rssStatus) {
        rssStatus__ = rssStatus;
    }

    /**
     * <p>get RSS_COMMENT value
     * @return RSS_COMMENT value
     */
    public String getRssComment() {
        return rssComment__;
    }

    /**
     * <p>set RSS_COMMENT value
     * @param rssComment RSS_COMMENT value
     */
    public void setRssComment(String rssComment) {
        rssComment__ = rssComment;
    }

    /**
     * <p>get RSS_CHKDATE value
     * @return RSS_CHKDATE value
     */
    public UDate getRssChkdate() {
        return rssChkdate__;
    }

    /**
     * <p>set RSS_CHKDATE value
     * @param rssChkdate RSS_CHKDATE value
     */
    public void setRssChkdate(UDate rssChkdate) {
        rssChkdate__ = rssChkdate;
    }

    /**
     * <p>get RSS_AUID value
     * @return RSS_AUID value
     */
    public int getRssAuid() {
        return rssAuid__;
    }

    /**
     * <p>set RSS_AUID value
     * @param rssAuid RSS_AUID value
     */
    public void setRssAuid(int rssAuid) {
        rssAuid__ = rssAuid;
    }

    /**
     * <p>get RSS_ADATE value
     * @return RSS_ADATE value
     */
    public UDate getRssAdate() {
        return rssAdate__;
    }

    /**
     * <p>set RSS_ADATE value
     * @param rssAdate RSS_ADATE value
     */
    public void setRssAdate(UDate rssAdate) {
        rssAdate__ = rssAdate;
    }

    /**
     * <p>get RSS_EUID value
     * @return RSS_EUID value
     */
    public int getRssEuid() {
        return rssEuid__;
    }

    /**
     * <p>set RSS_EUID value
     * @param rssEuid RSS_EUID value
     */
    public void setRssEuid(int rssEuid) {
        rssEuid__ = rssEuid;
    }

    /**
     * <p>get RSS_EDATE value
     * @return RSS_EDATE value
     */
    public UDate getRssEdate() {
        return rssEdate__;
    }

    /**
     * <p>set RSS_EDATE value
     * @param rssEdate RSS_EDATE value
     */
    public void setRssEdate(UDate rssEdate) {
        rssEdate__ = rssEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rksSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rngSid__);
        buf.append(",");
        buf.append(usrSidDairi__);
        buf.append(",");
        buf.append(rssStatus__);
        buf.append(",");
        buf.append(NullDefault.getString(rssComment__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rssChkdate__, ""));
        buf.append(",");
        buf.append(rssAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rssAdate__, ""));
        buf.append(",");
        buf.append(rssEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rssEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>usrSidKoetu を取得します。
     * @return usrSidKoetu
     */
    public int getUsrSidKoetu() {
        return usrSidKoetu__;
    }

    /**
     * <p>usrSidKoetu をセットします。
     * @param usrSidKoetu usrSidKoetu
     */
    public void setUsrSidKoetu(int usrSidKoetu) {
        usrSidKoetu__ = usrSidKoetu;
    }

}
