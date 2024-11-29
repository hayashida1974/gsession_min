package jp.groupsession.v2.sml.model;

import java.io.Serializable;

/**
 * <p>SML_LABEL_SORT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLabelSortModel implements Serializable {

    /** SAC_SID mapping */
    private int sacSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SLB_SID mapping */
    private int slbSid__;
    /** SLS_SORTKEY mapping */
    private int slsSortkey__;
    /** SlS_ORDER mapping */
    private int slsOrder__;

    /**
     * <p>Default Constructor
     */
    public SmlLabelSortModel() {
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
     * <p>slsSortkey を取得します。
     * @return slsSortkey
     */
    public int getSlsSortkey() {
        return slsSortkey__;
    }

    /**
     * <p>slsSortkey をセットします。
     * @param slsSortkey slsSortkey
     */
    public void setSlsSortkey(int slsSortkey) {
        slsSortkey__ = slsSortkey;
    }

    /**
     * <p>slsOrder を取得します。
     * @return slsOrder
     */
    public int getSlsOrder() {
        return slsOrder__;
    }

    /**
     * <p>slsOrder をセットします。
     * @param slsOrder slsOrder
     */
    public void setSlsOrder(int slsOrder) {
        slsOrder__ = slsOrder;
    }

}
