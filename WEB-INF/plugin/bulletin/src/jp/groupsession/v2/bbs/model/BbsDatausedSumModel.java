package jp.groupsession.v2.bbs.model;

import java.io.Serializable;

/**
 * <p>BBS_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** BBS_FOR_SUM_SIZE mapping */
    private long bbsForSumSize__;

    /**
     * <p>Default Constructor
     */
    public BbsDatausedSumModel() {
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
     * <p>get BBS_FOR_SUM_SIZE value
     * @return BBS_FOR_SUM_SIZE value
     */
    public long getBbsForSumSize() {
        return bbsForSumSize__;
    }

    /**
     * <p>set BBS_FOR_SUM_SIZE value
     * @param bbsForSumSize BBS_FOR_SUM_SIZE value
     */
    public void setBbsForSumSize(long bbsForSumSize) {
        bbsForSumSize__ = bbsForSumSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(bbsForSumSize__);
        return buf.toString();
    }

}
