package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_THRE_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreSumModel implements Serializable {

    /** BTI_SID mapping */
    private int btiSid__;
    /** BTS_WRT_CNT mapping */
    private int btsWrtCnt__;
    /** BTS_WRT_DATE mapping */
    private UDate btsWrtDate__;
    /** BTS_AUID mapping */
    private int btsAuid__;
    /** BTS_ADATE mapping */
    private UDate btsAdate__;
    /** BTS_EUID mapping */
    private int btsEuid__;
    /** BTS_EDATE mapping */
    private UDate btsEdate__;
    /** BTS_SIZE mapping */
    private long btsSize__;
    /** BTS_TEMPFLG mapping */
    private int btsTempflg__;

    /**
     * <p>Default Constructor
     */
    public BbsThreSumModel() {
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
     * <p>get BTS_WRT_CNT value
     * @return BTS_WRT_CNT value
     */
    public int getBtsWrtCnt() {
        return btsWrtCnt__;
    }

    /**
     * <p>set BTS_WRT_CNT value
     * @param btsWrtCnt BTS_WRT_CNT value
     */
    public void setBtsWrtCnt(int btsWrtCnt) {
        btsWrtCnt__ = btsWrtCnt;
    }

    /**
     * <p>get BTS_WRT_DATE value
     * @return BTS_WRT_DATE value
     */
    public UDate getBtsWrtDate() {
        return btsWrtDate__;
    }

    /**
     * <p>set BTS_WRT_DATE value
     * @param btsWrtDate BTS_WRT_DATE value
     */
    public void setBtsWrtDate(UDate btsWrtDate) {
        btsWrtDate__ = btsWrtDate;
    }

    /**
     * <p>get BTS_AUID value
     * @return BTS_AUID value
     */
    public int getBtsAuid() {
        return btsAuid__;
    }

    /**
     * <p>set BTS_AUID value
     * @param btsAuid BTS_AUID value
     */
    public void setBtsAuid(int btsAuid) {
        btsAuid__ = btsAuid;
    }

    /**
     * <p>get BTS_ADATE value
     * @return BTS_ADATE value
     */
    public UDate getBtsAdate() {
        return btsAdate__;
    }

    /**
     * <p>set BTS_ADATE value
     * @param btsAdate BTS_ADATE value
     */
    public void setBtsAdate(UDate btsAdate) {
        btsAdate__ = btsAdate;
    }

    /**
     * <p>get BTS_EUID value
     * @return BTS_EUID value
     */
    public int getBtsEuid() {
        return btsEuid__;
    }

    /**
     * <p>set BTS_EUID value
     * @param btsEuid BTS_EUID value
     */
    public void setBtsEuid(int btsEuid) {
        btsEuid__ = btsEuid;
    }

    /**
     * <p>get BTS_EDATE value
     * @return BTS_EDATE value
     */
    public UDate getBtsEdate() {
        return btsEdate__;
    }

    /**
     * <p>set BTS_EDATE value
     * @param btsEdate BTS_EDATE value
     */
    public void setBtsEdate(UDate btsEdate) {
        btsEdate__ = btsEdate;
    }

    /**
     * <p>get BTS_SIZE value
     * @return BTS_SIZE value
     */
    public long getBtsSize() {
        return btsSize__;
    }

    /**
     * <p>set BTS_SIZE value
     * @param btsSize BTS_SIZE value
     */
    public void setBtsSize(long btsSize) {
        btsSize__ = btsSize;
    }

    /**
     * <p>get BTS_TEMPFLG value
     * @return BTS_TEMPFLG value
     */
    public int getBtsTempflg() {
        return btsTempflg__;
    }

    /**
     * <p>set BTS_TEMPFLG value
     * @param btsTempflg BTS_TEMPFLG value
     */
    public void setBtsTempflg(int btsTempflg) {
        btsTempflg__ = btsTempflg;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(btiSid__);
        buf.append(",");
        buf.append(btsWrtCnt__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btsWrtDate__, ""));
        buf.append(",");
        buf.append(btsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btsAdate__, ""));
        buf.append(",");
        buf.append(btsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(btsEdate__, ""));
        buf.append(",");
        buf.append(btsSize__);
        buf.append(",");
        buf.append(btsTempflg__);
        return buf.toString();
    }

}
