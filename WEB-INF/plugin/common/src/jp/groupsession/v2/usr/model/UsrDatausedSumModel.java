package jp.groupsession.v2.usr.model;

import java.io.Serializable;

/**
 * <p>USR_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class UsrDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** CMN_USR_SIZE mapping */
    private long cmnUsrSize__;

    /**
     * <p>Default Constructor
     */
    public UsrDatausedSumModel() {
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
     * <p>get CMN_USR_SIZE value
     * @return CMN_USR_SIZE value
     */
    public long getCmnUsrSize() {
        return cmnUsrSize__;
    }

    /**
     * <p>set CMN_USR_SIZE value
     * @param cmnUsrSize CMN_USR_SIZE value
     */
    public void setCmnUsrSize(long cmnUsrSize) {
        cmnUsrSize__ = cmnUsrSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(cmnUsrSize__);
        return buf.toString();
    }

}
