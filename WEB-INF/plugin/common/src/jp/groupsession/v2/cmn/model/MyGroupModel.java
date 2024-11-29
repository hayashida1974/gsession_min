package jp.groupsession.v2.cmn.model;

import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MyGroupModel extends CmnMyGroupModel {
    /**マイグループ所有者*/
    private CmnUsrmInfModel owner__;

    /**マイグループ所属メンバー数*/
    private int memberCnt__;

    /**
     * <p>owner を取得します。
     * @return owner
     */
    public CmnUsrmInfModel getOwner() {
        return owner__;
    }

    /**
     * <p>owner をセットします。
     * @param owner owner
     */
    public void setOwner(CmnUsrmInfModel owner) {
        owner__ = owner;
    }

    /**
     * <p>memberCnt を取得します。
     * @return memberCnt
     */
    public int getMemberCnt() {
        return memberCnt__;
    }

    /**
     * <p>memberCnt をセットします。
     * @param memberCnt memberCnt
     */
    public void setMemberCnt(int memberCnt) {
        memberCnt__ = memberCnt;
    }



}
