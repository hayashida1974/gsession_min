package jp.groupsession.v2.adr.adr010.model;

import java.util.List;

import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] カテゴリーラベルモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrCategoryLabelModel extends AbstractModel {
    /** カテゴリーSID */
    private int categorySid__ = 0;
    /** カテゴリー名 */
    private String categoryName__ = null;
    /** ラベルリスト */
    private List<AdrLabelModel> labelList__ = null;
    /**
     * <p>categorySid を取得します。
     * @return categorySid
     */
    public int getCategorySid() {
        return categorySid__;
    }
    /**
     * <p>categorySid をセットします。
     * @param categorySid categorySid
     */
    public void setCategorySid(int categorySid) {
        categorySid__ = categorySid;
    }
    /**
     * <p>categoryName を取得します。
     * @return categoryName
     */
    public String getCategoryName() {
        return categoryName__;
    }
    /**
     * <p>categoryName をセットします。
     * @param categoryName categoryName
     */
    public void setCategoryName(String categoryName) {
        categoryName__ = categoryName;
    }
    /**
     * <p>labelList を取得します。
     * @return labelList
     */
    public List<AdrLabelModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(List<AdrLabelModel> labelList) {
        labelList__ = labelList;
    }

}