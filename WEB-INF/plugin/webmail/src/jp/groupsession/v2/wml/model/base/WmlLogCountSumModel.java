package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>WML_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlLogCountSumModel implements Serializable {

    /** WLS_KBN mapping */
    private int wlsKbn__;
    /** WLS_CNT mapping */
    private long wlsCnt__;
    /** WLS_CNT_TO mapping */
    private long wlsCntTo__;
    /** WLS_CNT_CC mapping */
    private long wlsCntCc__;
    /** WLS_CNT_BCC mapping */
    private long wlsCntBcc__;
    /** WLS_DATE mapping */
    private UDate wlsDate__;
    /** WLS_MONTH mapping */
    private int wlsMonth__;
    /** WLS_EDATE mapping */
    private UDate wlsEdate__;

    /**
     * <p>Default Constructor
     */
    public WmlLogCountSumModel() {
    }

    /**
     * <p>get WLS_KBN value
     * @return WLS_KBN value
     */
    public int getWlsKbn() {
        return wlsKbn__;
    }

    /**
     * <p>set WLS_KBN value
     * @param wlsKbn WLS_KBN value
     */
    public void setWlsKbn(int wlsKbn) {
        wlsKbn__ = wlsKbn;
    }

    /**
     * <p>get WLS_CNT value
     * @return WLS_CNT value
     */
    public long getWlsCnt() {
        return wlsCnt__;
    }

    /**
     * <p>set WLS_CNT value
     * @param wlsCnt WLS_CNT value
     */
    public void setWlsCnt(long wlsCnt) {
        wlsCnt__ = wlsCnt;
    }

    /**
     * <p>get WLS_CNT_TO value
     * @return WLS_CNT_TO value
     */
    public long getWlsCntTo() {
        return wlsCntTo__;
    }

    /**
     * <p>set WLS_CNT_TO value
     * @param wlsCntTo WLS_CNT_TO value
     */
    public void setWlsCntTo(long wlsCntTo) {
        wlsCntTo__ = wlsCntTo;
    }

    /**
     * <p>get WLS_CNT_CC value
     * @return WLS_CNT_CC value
     */
    public long getWlsCntCc() {
        return wlsCntCc__;
    }

    /**
     * <p>set WLS_CNT_CC value
     * @param wlsCntCc WLS_CNT_CC value
     */
    public void setWlsCntCc(long wlsCntCc) {
        wlsCntCc__ = wlsCntCc;
    }

    /**
     * <p>get WLS_CNT_BCC value
     * @return WLS_CNT_BCC value
     */
    public long getWlsCntBcc() {
        return wlsCntBcc__;
    }

    /**
     * <p>set WLS_CNT_BCC value
     * @param wlsCntBcc WLS_CNT_BCC value
     */
    public void setWlsCntBcc(long wlsCntBcc) {
        wlsCntBcc__ = wlsCntBcc;
    }

    /**
     * <p>get WLS_DATE value
     * @return WLS_DATE value
     */
    public UDate getWlsDate() {
        return wlsDate__;
    }

    /**
     * <p>set WLS_DATE value
     * @param wlsDate WLS_DATE value
     */
    public void setWlsDate(UDate wlsDate) {
        wlsDate__ = wlsDate;
    }

    /**
     * <p>get WLS_MONTH value
     * @return WLS_MONTH value
     */
    public int getWlsMonth() {
        return wlsMonth__;
    }

    /**
     * <p>set WLS_MONTH value
     * @param wlsMonth WLS_MONTH value
     */
    public void setWlsMonth(int wlsMonth) {
        wlsMonth__ = wlsMonth;
    }

    /**
     * <p>get WLS_EDATE value
     * @return WLS_EDATE value
     */
    public UDate getWlsEdate() {
        return wlsEdate__;
    }

    /**
     * <p>set WLS_EDATE value
     * @param wlsEdate WLS_EDATE value
     */
    public void setWlsEdate(UDate wlsEdate) {
        wlsEdate__ = wlsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(wlsKbn__);
        buf.append(",");
        buf.append(wlsCnt__);
        buf.append(",");
        buf.append(wlsCntTo__);
        buf.append(",");
        buf.append(wlsCntCc__);
        buf.append(",");
        buf.append(wlsCntBcc__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wlsDate__, ""));
        buf.append(",");
        buf.append(wlsMonth__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(wlsEdate__, ""));
        return buf.toString();
    }

}
