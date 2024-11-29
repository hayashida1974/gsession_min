package jp.groupsession.v2.sml.sml140kn;

import java.util.List;

import jp.groupsession.v2.sml.sml140.Sml140ParamModel;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;

/**
 * <br>[機  能] ショートメール 個人設定 手動削除確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml140knParamModel extends Sml140ParamModel {

    /** アカウント名リスト */
    private List<Sml240AccountModel> sml140knAccountList__ = null;

    /**
     * <p>sml140knAccountList を取得します。
     * @return sml140knAccountList
     */
    public List<Sml240AccountModel> getSml140knAccountList() {
        return sml140knAccountList__;
    }

    /**
     * <p>sml140knAccountList をセットします。
     * @param sml140knAccountList sml140knAccountList
     */
    public void setSml140knAccountList(List<Sml240AccountModel> sml140knAccountList) {
        sml140knAccountList__ = sml140knAccountList;
    }

}