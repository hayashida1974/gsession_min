package jp.groupsession.v2.man.man210kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.man210.Man210Form;

/**
 * <br>[機  能] メイン 管理者設定 モバイル使用一括設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man210knForm extends Man210Form {

    //表示項目
    /** メンバーリスト */
    private List<CmnUsrmInfModel> man210knMemberList__ = null;

    /**
     * <p>man210knMemberList を取得します。
     * @return man210knMemberList
     */
    public List<CmnUsrmInfModel> getMan210knMemberList() {
        return man210knMemberList__;
    }

    /**
     * <p>man210knMemberList をセットします。
     * @param man210knMemberList man210knMemberList
     */
    public void setMan210knMemberList(List<CmnUsrmInfModel> man210knMemberList) {
        man210knMemberList__ = man210knMemberList;
    }

}
