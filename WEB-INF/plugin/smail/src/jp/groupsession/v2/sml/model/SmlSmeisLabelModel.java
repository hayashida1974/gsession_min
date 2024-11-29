package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_SMEIS_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlSmeisLabelModel implements Serializable {

    /** SMS_SID mapping */
    private int smsSid__;
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
     * <p>smsSid を取得します。
     * @return smsSid
     */
    public int getSmsSid() {
        return smsSid__;
    }
    /**
     * <p>smsSid をセットします。
     * @param smsSid smsSid
     */
    public void setSmsSid(int smsSid) {
        smsSid__ = smsSid;
    }

}
