package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_ACCOUNT_DISK Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAccountDiskModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** SDS_SIZE mapping */
    private long sdsSize__;

    /**
     * <p>Default Constructor
     */
    public SmlAccountDiskModel() {
    }

    /**
     * <p>get SAC_SID value
     * @return SAC_SID value
     */
    public int getSacSid() {
        return sacSid__;
    }

    /**
     * <p>set SAC_SID value
     * @param sacSid SAC_SID value
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }

    /**
     * <p>get SDS_SIZE value
     * @return SDS_SIZE value
     */
    public long getSdsSize() {
        return sdsSize__;
    }

    /**
     * <p>set SDS_SIZE value
     * @param sdsSize SDS_SIZE value
     */
    public void setSdsSize(long sdsSize) {
        sdsSize__ = sdsSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(sdsSize__);
        return buf.toString();
    }

}
