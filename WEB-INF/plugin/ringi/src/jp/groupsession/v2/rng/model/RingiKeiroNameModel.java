package jp.groupsession.v2.rng.model;


/**
 * <br>[機  能] 稟議経路情報を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiKeiroNameModel extends RingiChannelDataModel {

    /** 経路SID*/
    private int rksSid__;
    /** 経路状態*/
    private int rksStatus__;
    /** ユーザ/グループ名*/
    private String usrgrpName__;
    /** 同一経路上登録数*/
    private int rksCount__;
    /** 役職SID*/
    private int posSid__;
    /** グループ削除フラグ*/
    private int groupDelFlg__;

    /**
     * <p>rksSid_ を取得します。
     * @return rksSid_
     */
    public int getRksSid() {
        return rksSid__;
    }
    /**
     * <p>rksSid_ をセットします。
     * @param rksSid rksSid
     */
    public void setRksSid(int rksSid) {
        this.rksSid__ = rksSid;
    }
    /**
     * <p>rksStatus を取得します。
     * @return rksStatus
     */
    public int getRksStatus() {
        return rksStatus__;
    }
    /**
     * <p>rksStatus をセットします。
     * @param rksStatus rksStatus
     */
    public void setRksStatus(int rksStatus) {
        rksStatus__ = rksStatus;
    }
    /**
     * <p>usrgrpName を取得します。
     * @return usrgrpName
     */
    public String getUsrgrpName() {
        return usrgrpName__;
    }
    /**
     * <p>usrgrpName をセットします。
     * @param usrgrpName usrgrpName
     */
    public void setUsrgrpName(String usrgrpName) {
        usrgrpName__ = usrgrpName;
    }
    /**
     * <p>rksCount を取得します。
     * @return rksCount
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#rksCount__
     */
    public int getRksCount() {
        return rksCount__;
    }
    /**
     * <p>rksCount をセットします。
     * @param rksCount rksCount
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#rksCount__
     */
    public void setRksCount(int rksCount) {
        rksCount__ = rksCount;
    }
    /**
     * <p>posSid を取得します。
     * @return posSid
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#posSid__
     */
    public int getPosSid() {
        return posSid__;
    }
    /**
     * <p>posSid をセットします。
     * @param posSid posSid
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#posSid__
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }
    /**
     * <p>groupDelFlg を取得します。
     * @return groupDelFlg
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#groupDelFlg__
     */
    public int getGroupDelFlg() {
        return groupDelFlg__;
    }
    /**
     * <p>groupDelFlg をセットします。
     * @param groupDelFlg groupDelFlg
     * @see jp.groupsession.v2.rng.model.RingiKeiroNameModel#groupDelFlg__
     */
    public void setGroupDelFlg(int groupDelFlg) {
        groupDelFlg__ = groupDelFlg;
    }

}