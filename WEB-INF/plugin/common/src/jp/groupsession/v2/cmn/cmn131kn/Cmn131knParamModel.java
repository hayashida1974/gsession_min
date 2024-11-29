package jp.groupsession.v2.cmn.cmn131kn;

import java.util.List;

import jp.groupsession.v2.cmn.cmn131.Cmn131ParamModel;
import jp.groupsession.v2.usr.usr040.Usr040DspModel;

/**
 * <br>[機  能] メイン 個人設定 マイグループ登録確認画面のパラメータ、出力値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn131knParamModel extends Cmn131ParamModel {

    //表示項目
    /** メモ */
    private String cmn131knMemo__ = null;
    /** メンバーリスト */
    private List<Usr040DspModel> cmn131knMemberList__ = null;
    /** 共有メンバーリスト */
    private List<Usr040DspModel> cmn131knRefMemberList__ = null;

    /**
     * <p>cmn131knMemo を取得します。
     * @return cmn131knMemo
     */
    public String getCmn131knMemo() {
        return cmn131knMemo__;
    }

    /**
     * <p>cmn131knMemo をセットします。
     * @param cmn131knMemo cmn131knMemo
     */
    public void setCmn131knMemo(String cmn131knMemo) {
        cmn131knMemo__ = cmn131knMemo;
    }

    /**
     * <p>cmn131knMemberList を取得します。
     * @return cmn131knMemberList
     */
    public List<Usr040DspModel> getCmn131knMemberList() {
        return cmn131knMemberList__;
    }

    /**
     * <p>cmn131knMemberList をセットします。
     * @param cmn131knMemberList cmn131knMemberList
     */
    public void setCmn131knMemberList(List<Usr040DspModel> cmn131knMemberList) {
        cmn131knMemberList__ = cmn131knMemberList;
    }

    /**
     * <p>cmn131knRefMemberList を取得します。
     * @return cmn131knRefMemberList
     */
    public List<Usr040DspModel> getCmn131knRefMemberList() {
        return cmn131knRefMemberList__;
    }

    /**
     * <p>cmn131knRefMemberList をセットします。
     * @param cmn131knRefMemberList cmn131knRefMemberList
     */
    public void setCmn131knRefMemberList(List<Usr040DspModel> cmn131knRefMemberList) {
        cmn131knRefMemberList__ = cmn131knRefMemberList;
    }

}
