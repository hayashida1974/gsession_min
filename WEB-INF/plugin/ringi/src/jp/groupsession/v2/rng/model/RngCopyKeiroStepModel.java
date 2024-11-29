package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <p>RNG_COPY_KEIRO_STEP Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCopyKeiroStepModel implements Serializable {

    /** RKS_SID mapping */
    private int rksSid__;
    /** RCK_SORT mapping */
    private int rckSort__;
    /** RTK_SID mapping */
    private int rtkSid__;
    /** RKS_BELONG_SID mapping */
    private int rksBelongSid__ = 0;

    /**
     * <p>Default Constructor
     */
    public RngCopyKeiroStepModel() {
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
     * <p>get RTK_SID value
     * @return RTK_SID value
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>set RTK_SID value
     * @param rtkSid RTK_SID value
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }

    /**
     * <p>rksBelongSid を取得します。
     * @return rksBelongSid
     */
    public int getRksBelongSid() {
        return rksBelongSid__;
    }

    /**
     * <p>rksBelongSid をセットします。
     * @param rksBelongSid rksBelongSid
     */
    public void setRksBelongSid(int rksBelongSid) {
        rksBelongSid__ = rksBelongSid;
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
        buf.append(rtkSid__);
        buf.append(",");
        buf.append(rksBelongSid__);
        return buf.toString();
    }

    /**
     * <p> 複写用データとベースとなる経路ステップ情報を結合する
     * @param  rksMdl ベースとなる経路ステップ情報
     * @return 経路ステップ情報
     */
    public RngKeiroStepModel margeRksMdl(RngKeiroStepModel rksMdl) {
        if (rksMdl == null) {
            return null;
        }
        RngKeiroStepModel bean = new RngKeiroStepModel();
        bean.setRksSid(rksSid__);
        bean.setRngSid(rksMdl.getRngSid());
        bean.setRksSort(rksMdl.getRngSid() + rckSort__);
        bean.setRksStatus(rksMdl.getRksStatus());
        bean.setRtkSid(rtkSid__);
        bean.setRksRollType(rksMdl.getRksRollType());
        bean.setRksRcvdate(rksMdl.getRksRcvdate());
        bean.setRksChkdate(rksMdl.getRksChkdate());
        bean.setRksAuid(rksMdl.getRksAuid());
        bean.setRksAdate(rksMdl.getRksAdate());
        bean.setRksEuid(rksMdl.getRksEuid());
        bean.setRksEdate(rksMdl.getRksEdate());
        bean.setRksBelongSid(rksBelongSid__);
        bean.setRksKeiroKoetu(rksMdl.getRksKeiroKoetu());
        bean.setRksKoetuSizi(rksMdl.getRksKoetuSizi());
        bean.setRksAddstep(rksMdl.getRksAddstep());
        return bean;
    }

    /**
     * <p> 経路ステップ情報を複写用データへ設定
     * @param rksMdl 経路ステップ情報
     */
    public void setRksMdl(RngKeiroStepModel rksMdl) {
        if (rksMdl != null) {
            this.setRksSid(rksMdl.getRksSid());
            this.setRckSort(rksMdl.getRksSort());
            this.setRtkSid(rksMdl.getRtkSid());
            this.setRksBelongSid(rksMdl.getRksBelongSid());
        }
    }
}
