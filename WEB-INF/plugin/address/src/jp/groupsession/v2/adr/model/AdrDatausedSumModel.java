package jp.groupsession.v2.adr.model;

import java.io.Serializable;

/**
 * <p>ADR_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AdrDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** ADR_CONTACT_SIZE mapping */
    private long adrContactSize__;

    /**
     * <p>Default Constructor
     */
    public AdrDatausedSumModel() {
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
     * <p>get ADR_CONTACT_SIZE value
     * @return ADR_CONTACT_SIZE value
     */
    public long getAdrContactSize() {
        return adrContactSize__;
    }

    /**
     * <p>set ADR_CONTACT_SIZE value
     * @param adrContactSize ADR_CONTACT_SIZE value
     */
    public void setAdrContactSize(long adrContactSize) {
        adrContactSize__ = adrContactSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(adrContactSize__);
        return buf.toString();
    }

}
