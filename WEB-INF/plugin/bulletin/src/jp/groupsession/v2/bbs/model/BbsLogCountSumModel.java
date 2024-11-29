package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BBS_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsLogCountSumModel implements Serializable {

    /** BLS_KBN mapping */
    private int blsKbn__;
    /** BLS_CNT mapping */
    private long blsCnt__;
    /** BLS_DATE mapping */
    private UDate blsDate__;
    /** BLS_MONTH mapping */
    private int blsMonth__;
    /** BLS_EDATE mapping */
    private UDate blsEdate__;

    /**
     * <p>Default Constructor
     */
    public BbsLogCountSumModel() {
    }

    /**
     * <p>get BLS_KBN value
     * @return BLS_KBN value
     */
    public int getBlsKbn() {
        return blsKbn__;
    }

    /**
     * <p>set BLS_KBN value
     * @param blsKbn BLS_KBN value
     */
    public void setBlsKbn(int blsKbn) {
        blsKbn__ = blsKbn;
    }

    /**
     * <p>get BLS_CNT value
     * @return BLS_CNT value
     */
    public long getBlsCnt() {
        return blsCnt__;
    }

    /**
     * <p>set BLS_CNT value
     * @param blsCnt BLS_CNT value
     */
    public void setBlsCnt(long blsCnt) {
        blsCnt__ = blsCnt;
    }

    /**
     * <p>get BLS_DATE value
     * @return BLS_DATE value
     */
    public UDate getBlsDate() {
        return blsDate__;
    }

    /**
     * <p>set BLS_DATE value
     * @param blsDate BLS_DATE value
     */
    public void setBlsDate(UDate blsDate) {
        blsDate__ = blsDate;
    }

    /**
     * <p>get BLS_MONTH value
     * @return BLS_MONTH value
     */
    public int getBlsMonth() {
        return blsMonth__;
    }

    /**
     * <p>set BLS_MONTH value
     * @param blsMonth BLS_MONTH value
     */
    public void setBlsMonth(int blsMonth) {
        blsMonth__ = blsMonth;
    }

    /**
     * <p>get BLS_EDATE value
     * @return BLS_EDATE value
     */
    public UDate getBlsEdate() {
        return blsEdate__;
    }

    /**
     * <p>set BLS_EDATE value
     * @param blsEdate BLS_EDATE value
     */
    public void setBlsEdate(UDate blsEdate) {
        blsEdate__ = blsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(blsKbn__);
        buf.append(",");
        buf.append(blsCnt__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(blsDate__, ""));
        buf.append(",");
        buf.append(blsMonth__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(blsEdate__, ""));
        return buf.toString();
    }

}
