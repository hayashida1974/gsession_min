package jp.groupsession.v2.convert.convert430.model;

import java.io.Serializable;

/**
 * <p>BBS_FOR_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CvtBbsForInfModel implements Serializable {

    /** BFI_SID mapping */
    private int bfiSid__;
    /** BFI_NAME mapping */
    private String bfiName__;

    /**
     * <p>Default Constructor
     */
    public CvtBbsForInfModel() {
    }

    /**
     * <p>get BFI_SID value
     * @return BFI_SID value
     */
    public int getBfiSid() {
        return bfiSid__;
    }

    /**
     * <p>set BFI_SID value
     * @param bfiSid BFI_SID value
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }

    /**
     * <p>get BFI_NAME value
     * @return BFI_NAME value
     */
    public String getBfiName() {
        return bfiName__;
    }

    /**
     * <p>set BFI_NAME value
     * @param bfiName BFI_NAME value
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
    }
}
