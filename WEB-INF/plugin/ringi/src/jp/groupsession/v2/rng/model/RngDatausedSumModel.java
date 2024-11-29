package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RngDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** RNG_RNDATA_DISK_SIZE mapping */
    private long rngRndataDiskSize__;
    /** RNG_TEMPLATE_DISK_SIZE mapping */
    private long rngTemplateDiskSize__;

    /**
     * <p>Default Constructor
     */
    public RngDatausedSumModel() {
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
     * <p>get RNG_RNDATA_DISK_SIZE value
     * @return RNG_RNDATA_DISK_SIZE value
     */
    public long getRngRndataDiskSize() {
        return rngRndataDiskSize__;
    }

    /**
     * <p>set RNG_RNDATA_DISK_SIZE value
     * @param rngRndataDiskSize RNG_RNDATA_DISK_SIZE value
     */
    public void setRngRndataDiskSize(long rngRndataDiskSize) {
        rngRndataDiskSize__ = rngRndataDiskSize;
    }

    /**
     * <p>get RNG_TEMPLATE_DISK_SIZE value
     * @return RNG_TEMPLATE_DISK_SIZE value
     */
    public long getRngTemplateDiskSize() {
        return rngTemplateDiskSize__;
    }

    /**
     * <p>set RNG_TENPLATE_DISK_SIZE value
     * @param rngTemplateDiskSize RNG_TEMPLATE_DISK_SIZE value
     */
    public void setRngTemplateDiskSize(long rngTemplateDiskSize) {
        rngTemplateDiskSize__ = rngTemplateDiskSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(rngRndataDiskSize__);
        buf.append(",");
        buf.append(rngTemplateDiskSize__);
        return buf.toString();
    }

}
