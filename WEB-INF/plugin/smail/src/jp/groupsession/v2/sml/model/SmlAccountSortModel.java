package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_ACCOUNT_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAccountSortModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SAS_SORT mapping */
    private long sasSort__;

    /**
     * <p>Default Constructor
     */
    public SmlAccountSortModel() {
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
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get SAS_SORT value
     * @return SAS_SORT value
     */
    public long getSasSort() {
        return sasSort__;
    }

    /**
     * <p>set SAS_SORT value
     * @param sasSort SAS_SORT value
     */
    public void setSasSort(long sasSort) {
        sasSort__ = sasSort;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(sacSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(sasSort__);
        return buf.toString();
    }

}
