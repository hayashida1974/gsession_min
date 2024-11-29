package jp.groupsession.v2.wml.model.base;

import java.io.Serializable;

/**
 * <p>WML_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** WAC_DISCSIZE_SUM mapping */
    private long wacDiscsizeSum__;
    /** WTP_DISCSIZE_SUM mapping */
    private long wtpDiscsizeSum__;
    /** WLG_DISCSIZE_SUM mapping */
    private long wlgDiscsizeSum__;

    /**
     * <p>Default Constructor
     */
    public WmlDatausedSumModel() {
    }

    /**
     * <p>get SUM_TYPE value
     * @return SUM_TYPE value
     */
    public int getSumType() {
        return sumType__;
    }

    /**
     * <p>set SUM_TYPE value
     * @param sumType SUM_TYPE value
     */
    public void setSumType(int sumType) {
        sumType__ = sumType;
    }

    /**
     * <p>get WAC_DISCSIZE_SUM value
     * @return WAC_DISCSIZE_SUM value
     */
    public long getWacDiscsizeSum() {
        return wacDiscsizeSum__;
    }

    /**
     * <p>set WAC_DISCSIZE_SUM value
     * @param wacDiscsizeSum WAC_DISCSIZE_SUM value
     */
    public void setWacDiscsizeSum(long wacDiscsizeSum) {
        wacDiscsizeSum__ = wacDiscsizeSum;
    }

    /**
     * <p>get WTP_DISCSIZE_SUM value
     * @return WTP_DISCSIZE_SUM value
     */
    public long getWtpDiscsizeSum() {
        return wtpDiscsizeSum__;
    }

    /**
     * <p>set WTP_DISCSIZE_SUM value
     * @param wtpDiscsizeSum WTP_DISCSIZE_SUM value
     */
    public void setWtpDiscsizeSum(long wtpDiscsizeSum) {
        wtpDiscsizeSum__ = wtpDiscsizeSum;
    }

    /**
     * <p>get WLG_DISCSIZE_SUM value
     * @return WLG_DISCSIZE_SUM value
     */
    public long getWlgDiscsizeSum() {
        return wlgDiscsizeSum__;
    }

    /**
     * <p>set WLG_DISCSIZE_SUM value
     * @param wlgDiscsizeSum WLG_DISCSIZE_SUM value
     */
    public void setWlgDiscsizeSum(long wlgDiscsizeSum) {
        wlgDiscsizeSum__ = wlgDiscsizeSum;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(wacDiscsizeSum__);
        buf.append(",");
        buf.append(wtpDiscsizeSum__);
        buf.append(",");
        buf.append(wlgDiscsizeSum__);
        return buf.toString();
    }

}
