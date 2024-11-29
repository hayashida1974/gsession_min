package jp.groupsession.v2.man.model.base;

import java.io.Serializable;

/**
 * <p>MAN_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ManDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** CMN_LOG_SIZE mapping */
    private long cmnLogSize__;
    /** CMN_LOGIN_HISTORY_SIZE mapping */
    private long cmnLoginHistorySize__;

    /**
     * <p>Default Constructor
     */
    public ManDatausedSumModel() {
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
     * <p>get CMN_LOG_SIZE value
     * @return CMN_LOG_SIZE value
     */
    public long getCmnLogSize() {
        return cmnLogSize__;
    }

    /**
     * <p>set CMN_LOG_SIZE value
     * @param cmnLogSize CMN_LOG_SIZE value
     */
    public void setCmnLogSize(long cmnLogSize) {
        cmnLogSize__ = cmnLogSize;
    }

    /**
     * <p>get CMN_LOGIN_HISTORY_SIZE value
     * @return CMN_LOGIN_HISTORY_SIZE value
     */
    public long getCmnLoginHistorySize() {
        return cmnLoginHistorySize__;
    }

    /**
     * <p>set CMN_LOGIN_HISTORY_SIZE value
     * @param cmnLoginHistorySize CMN_LOGIN_HISTORY_SIZE value
     */
    public void setCmnLoginHistorySize(long cmnLoginHistorySize) {
        cmnLoginHistorySize__ = cmnLoginHistorySize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(cmnLogSize__);
        buf.append(",");
        buf.append(cmnLoginHistorySize__);
        return buf.toString();
    }

}
