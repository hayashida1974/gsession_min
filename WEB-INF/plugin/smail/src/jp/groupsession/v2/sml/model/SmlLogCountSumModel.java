package jp.groupsession.v2.sml.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>SML_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLogCountSumModel implements Serializable {

    /** SLS_KBN mapping */
    private int slsKbn__;
    /** SLS_SYS_KBN mapping */
    private int slsSysKbn__;
    /** SLS_CNT mapping */
    private long slsCnt__;
    /** SLS_CNT_TO mapping */
    private long slsCntTo__;
    /** SLS_CNT_CC mapping */
    private long slsCntCc__;
    /** SLS_CNT_BCC mapping */
    private long slsCntBcc__;
    /** SLS_DATE mapping */
    private UDate slsDate__;
    /** SLS_MONTH mapping */
    private int slsMonth__;
    /** SLS_EDATE mapping */
    private UDate slsEdate__;

    /**
     * <p>Default Constructor
     */
    public SmlLogCountSumModel() {
    }

    /**
     * <p>get SLS_KBN value
     * @return SLS_KBN value
     */
    public int getSlsKbn() {
        return slsKbn__;
    }

    /**
     * <p>set SLS_KBN value
     * @param slsKbn SLS_KBN value
     */
    public void setSlsKbn(int slsKbn) {
        slsKbn__ = slsKbn;
    }

    /**
     * <p>get SLS_SYS_KBN value
     * @return SLS_SYS_KBN value
     */
    public int getSlsSysKbn() {
        return slsSysKbn__;
    }

    /**
     * <p>set SLS_SYS_KBN value
     * @param slsSysKbn SLS_SYS_KBN value
     */
    public void setSlsSysKbn(int slsSysKbn) {
        slsSysKbn__ = slsSysKbn;
    }

    /**
     * <p>get SLS_CNT value
     * @return SLS_CNT value
     */
    public long getSlsCnt() {
        return slsCnt__;
    }

    /**
     * <p>set SLS_CNT value
     * @param slsCnt SLS_CNT value
     */
    public void setSlsCnt(long slsCnt) {
        slsCnt__ = slsCnt;
    }

    /**
     * <p>get SLS_CNT_TO value
     * @return SLS_CNT_TO value
     */
    public long getSlsCntTo() {
        return slsCntTo__;
    }

    /**
     * <p>set SLS_CNT_TO value
     * @param slsCntTo SLS_CNT_TO value
     */
    public void setSlsCntTo(long slsCntTo) {
        slsCntTo__ = slsCntTo;
    }

    /**
     * <p>get SLS_CNT_CC value
     * @return SLS_CNT_CC value
     */
    public long getSlsCntCc() {
        return slsCntCc__;
    }

    /**
     * <p>set SLS_CNT_CC value
     * @param slsCntCc SLS_CNT_CC value
     */
    public void setSlsCntCc(long slsCntCc) {
        slsCntCc__ = slsCntCc;
    }

    /**
     * <p>get SLS_CNT_BCC value
     * @return SLS_CNT_BCC value
     */
    public long getSlsCntBcc() {
        return slsCntBcc__;
    }

    /**
     * <p>set SLS_CNT_BCC value
     * @param slsCntBcc SLS_CNT_BCC value
     */
    public void setSlsCntBcc(long slsCntBcc) {
        slsCntBcc__ = slsCntBcc;
    }

    /**
     * <p>get SLS_DATE value
     * @return SLS_DATE value
     */
    public UDate getSlsDate() {
        return slsDate__;
    }

    /**
     * <p>set SLS_DATE value
     * @param slsDate SLS_DATE value
     */
    public void setSlsDate(UDate slsDate) {
        slsDate__ = slsDate;
    }

    /**
     * <p>get SLS_MONTH value
     * @return SLS_MONTH value
     */
    public int getSlsMonth() {
        return slsMonth__;
    }

    /**
     * <p>set SLS_MONTH value
     * @param slsMonth SLS_MONTH value
     */
    public void setSlsMonth(int slsMonth) {
        slsMonth__ = slsMonth;
    }

    /**
     * <p>get SLS_EDATE value
     * @return SLS_EDATE value
     */
    public UDate getSlsEdate() {
        return slsEdate__;
    }

    /**
     * <p>set SLS_EDATE value
     * @param slsEdate SLS_EDATE value
     */
    public void setSlsEdate(UDate slsEdate) {
        slsEdate__ = slsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(slsKbn__);
        buf.append(",");
        buf.append(slsSysKbn__);
        buf.append(",");
        buf.append(slsCnt__);
        buf.append(",");
        buf.append(slsCntTo__);
        buf.append(",");
        buf.append(slsCntCc__);
        buf.append(",");
        buf.append(slsCntBcc__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(slsDate__, ""));
        buf.append(",");
        buf.append(slsMonth__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(slsEdate__, ""));
        return buf.toString();
    }

}
