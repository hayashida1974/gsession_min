package jp.groupsession.v2.sml.sml160kn;

import java.util.List;

import jp.groupsession.v2.sml.sml160.Sml160Form;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;

/**
 * <br>[機  能] ショートメール 管理者設定 手動削除確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml160knForm extends Sml160Form {
    /** アカウント名リスト */
    private List<Sml240AccountModel> sml160knAccountList__ = null;

    /**
     * <p>sml160knAccountList を取得します。
     * @return sml160knAccountList
     */
    public List<Sml240AccountModel> getSml160knAccountList() {
        return sml160knAccountList__;
    }

    /**
     * <p>sml160knAccountList をセットします。
     * @param sml160knAccountList sml160knAccountList
     */
    public void setSml160knAccountList(List<Sml240AccountModel> sml160knAccountList) {
        sml160knAccountList__ = sml160knAccountList;
    }
}