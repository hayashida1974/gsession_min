package jp.groupsession.v2.cmn.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] プロジェクトメンバー設定操作情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjMemberEditModel extends CmnUsrmInfModel {

    /** プロジェクトメンバーキー */
    private String memberKey__;
    /** プロジェクト管理区分 */
    private int prmAdminKbn__;
    /** ソート */
    private int sort__;

    /**
     * <p>prmAdminKbn を取得します。
     * @return prmAdminKbn
     */
    public int getPrmAdminKbn() {
        return prmAdminKbn__;
    }
    /**
     * <p>prmAdminKbn をセットします。
     * @param prmAdminKbn prmAdminKbn
     */
    public void setPrmAdminKbn(int prmAdminKbn) {
        prmAdminKbn__ = prmAdminKbn;
    }
    /**
     * <p>memberKey を取得します。
     * @return memberKey
     */
    public String getMemberKey() {
        return memberKey__;
    }
    /**
     * <p>memberKey をセットします。
     * @param memberKey memberKey
     */
    public void setMemberKey(String memberKey) {
        memberKey__ = memberKey;
    }
    /**
     * <p>sort を取得します。
     * @return sort
     */
    public int getSort() {
        return sort__;
    }
    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(int sort) {
        sort__ = sort;
    }
}