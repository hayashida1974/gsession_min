package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_JMEIS_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlJmeisLabelModel implements Serializable {

    /** SMJ_SID mapping */
    private int smjSid__;
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
     * <p>smjSid を取得します。
     * @return smjSid
     */
    public int getSmjSid() {
        return smjSid__;
    }
    /**
     * <p>smjSid をセットします。
     * @param smjSid smjSid
     */
    public void setSmjSid(int smjSid) {
        smjSid__ = smjSid;
    }


}
