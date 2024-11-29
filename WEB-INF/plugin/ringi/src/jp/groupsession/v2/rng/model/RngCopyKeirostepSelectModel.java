package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCopyKeirostepSelectModel implements Serializable {

    /** RKS_SID mapping */
    private int rksSid__;
    /** RCK_SORT mapping */
    private int rckSort__ = 0;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** GRP_SID mapping */
    private int grpSid__ = -1;
    /** POS_SID mapping */
    private int posSid__ = -1;

    /**
     * <p>Default Constructor
     */
    public RngCopyKeirostepSelectModel() {
    }

    /**
     * <p>get RKS_SID value
     * @return RKS_SID value
     */
    public int getRksSid() {
        return rksSid__;
    }

    /**
     * <p>set RKS_SID value
     * @param rksSid RKS_SID value
     */
    public void setRksSid(int rksSid) {
        rksSid__ = rksSid;
    }

    /**
     * <p>get RCK_SORT value
     * @return RCK_SORT value
     */
    public int getRckSort() {
        return rckSort__;
    }

    /**
     * <p>set RCK_SORT value
     * @param rckSort RCK_SORT value
     */
    public void setRckSort(int rckSort) {
        rckSort__ = rckSort;
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get POS_SID value
     * @return POS_SID value
     */
    public int getPosSid() {
        return posSid__;
    }

    /**
     * <p>set POS_SID value
     * @param posSid POS_SID value
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(rksSid__);
        buf.append(",");
        buf.append(rckSort__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(posSid__);
        return buf.toString();
    }

    /**
     * <p> 複写用データから経路ステップ選択情報を取得
     * @return 経路ステップ選択情報
     */
    public RngKeirostepSelectModel getRssMdl() {
        RngKeirostepSelectModel bean = new RngKeirostepSelectModel();
        bean.setRksSid(rksSid__);
        bean.setUsrSid(usrSid__);
        bean.setGrpSid(grpSid__);
        bean.setPosSid(posSid__);
        return bean;
    }

    /**
     * <p> 経路ステップ選択情報を複写用データへ設定
     * @param rssMdl 経路ステップ選択情報
     * @param rckSort 経路順番号
     */
    public void setRssMdl(RngKeirostepSelectModel rssMdl, int rckSort) {
        if (rssMdl != null) {
            this.setRksSid(rssMdl.getRksSid());
            this.setRckSort(rckSort);
            this.setUsrSid(rssMdl.getUsrSid());
            this.setGrpSid(rssMdl.getGrpSid());
            this.setPosSid(rssMdl.getPosSid());
        }
    }
}
