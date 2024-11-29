package jp.groupsession.v2.sml.sml070;

import java.util.ArrayList;

import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.sml020.Sml020ParamModel;

/**
 * <br>[機  能] ショートメール一覧画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml070ParamModel extends Sml020ParamModel {
    /** メッセージ一覧リスト */
    private ArrayList<SmailModel> sml070SmlList__;

    /**
     * @return sml070SmlList を戻します。
     */
    public ArrayList<SmailModel> getSml070SmlList() {
        return sml070SmlList__;
    }
    /**
     * @param sml070SmlList 設定する sml070SmlList。
     */
    public void setSml070SmlList(ArrayList<SmailModel> sml070SmlList) {
        sml070SmlList__ = sml070SmlList;
    }
}