package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlLabelModel implements Serializable {

    /** SLB_SID mapping */
    private int slbSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SLB_NAME mapping */
    private String slbName__;
    /** SLB_TYPE mapping */
    private int slbType__;
    /** SLB_ORDER mapping */
    private int slbOrder__;
    /** SAC_SID mapping */
    private int sacSid__;

    /** 件数  */
    private int slbCount__;

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
     * <p>slbName を取得します。
     * @return slbName
     */
    public String getSlbName() {
        return slbName__;
    }

    /**
     * <p>slbName をセットします。
     * @param slbName slbName
     */
    public void setSlbName(String slbName) {
        slbName__ = slbName;
    }

    /**
     * <p>slbType を取得します。
     * @return slbType
     */
    public int getSlbType() {
        return slbType__;
    }

    /**
     * <p>slbType をセットします。
     * @param slbType slbType
     */
    public void setSlbType(int slbType) {
        slbType__ = slbType;
    }

    /**
     * <p>slbOrder を取得します。
     * @return slbOrder
     */
    public int getSlbOrder() {
        return slbOrder__;
    }

    /**
     * <p>slbOrder をセットします。
     * @param slbOrder slbOrder
     */
    public void setSlbOrder(int slbOrder) {
        slbOrder__ = slbOrder;
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
     * <p>slbCount を取得します。
     * @return slbCount
     */
    public int getSlbCount() {
        return slbCount__;
    }

    /**
     * <p>slbCount をセットします。
     * @param slbCount slbCount
     */
    public void setSlbCount(int slbCount) {
        slbCount__ = slbCount;
    }


}
