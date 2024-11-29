package jp.groupsession.v2.sml.sml070;

import java.util.ArrayList;

import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;

/**
 * <br>[機  能] ショートメール一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml070Form extends Sml020Form {

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