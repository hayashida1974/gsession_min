package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** SML_MAIL_SIZE mapping */
    private long smlMailSize__;
    /** SAC_DISCSIZE_SUM mapping */
    private long sacDiscsizeSum__;

    /**
     * <p>Default Constructor
     */
    public SmlDatausedSumModel() {
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
     * <p>get SML_MAIL_SIZE value
     * @return SML_MAIL_SIZE value
     */
    public long getSmlMailSize() {
        return smlMailSize__;
    }

    /**
     * <p>set SML_MAIL_SIZE value
     * @param smlMailSize SML_MAIL_SIZE value
     */
    public void setSmlMailSize(long smlMailSize) {
        smlMailSize__ = smlMailSize;
    }

    /**
     * <p>get SAC_DISCSIZE_SUM value
     * @return SAC_DISCSIZE_SUM value
     */
    public long getSacDiscsizeSum() {
        return sacDiscsizeSum__;
    }

    /**
     * <p>set SAC_DISCSIZE_SUM value
     * @param sacDiscsizeSum SAC_DISCSIZE_SUM value
     */
    public void setSacDiscsizeSum(long sacDiscsizeSum) {
        sacDiscsizeSum__ = sacDiscsizeSum;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(smlMailSize__);
        buf.append(",");
        buf.append(sacDiscsizeSum__);
        return buf.toString();
    }

}
