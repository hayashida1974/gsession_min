package jp.groupsession.v2.rng.rng150;

import java.util.ArrayList;

import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.rng060.Rng060ParamModel;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ選択画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng150ParamModel extends Rng060ParamModel {
    /** テンプレートカテゴリ一覧 */
    private ArrayList<RngTemplateCategoryModel> rng150CatList__;
    /** 選択されているカテゴリ */
    private String rng150SortRadio__;
    /**
     * @return rng150CatList
     */
    public ArrayList<RngTemplateCategoryModel> getRng150CatList() {
        return rng150CatList__;
    }
    /**
     * @param rng150CatList 設定する rng150CatList
     */
    public void setRng150CatList(ArrayList<RngTemplateCategoryModel> rng150CatList) {
        rng150CatList__ = rng150CatList;
    }
    /**
     * @return rng150SortRadio
     */
    public String getRng150SortRadio() {
        return rng150SortRadio__;
    }
    /**
     * @param rng150SortRadio 設定する rng150SortRadio
     */
    public void setRng150SortRadio(String rng150SortRadio) {
        rng150SortRadio__ = rng150SortRadio;
    }
}