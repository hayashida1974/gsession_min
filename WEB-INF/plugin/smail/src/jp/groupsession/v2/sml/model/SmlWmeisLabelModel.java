package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_WMEIS_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlWmeisLabelModel implements Serializable {

    /** SMS_SID mapping */
    private int smwSid__;
    /** SLB_SID mapping */
    private int slbSid__;
    /** SAC_SID mapping */
    private int sacSid__;

    /**
     * <p>slbSid を取得します。
     * @return slbSid
     */
    public int getSlbSid() {
        return slbSid__;
    }
    /**
     * <p>slbSid をセットします。
     * @param slbSid slbSid
     */
    public void setSlbSid(int slbSid) {
        slbSid__ = slbSid;
    }
    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public int getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(int sacSid) {
        sacSid__ = sacSid;
    }
    /**
     * <p>smwSid を取得します。
     * @return smwSid
     */
    public int getSmwSid() {
        return smwSid__;
    }
    /**
     * <p>smwSid をセットします。
     * @param smwSid smwSid
     */
    public void setSmwSid(int smwSid) {
        smwSid__ = smwSid;
    }
}
