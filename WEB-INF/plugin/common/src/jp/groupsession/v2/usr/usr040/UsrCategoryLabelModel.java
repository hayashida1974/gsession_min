package jp.groupsession.v2.usr.usr040;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;

/**
 * <br>[機  能] カテゴリーラベルモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrCategoryLabelModel extends AbstractModel {
    /** カテゴリーSID */
    private int categorySid__ = 0;
    /** カテゴリー名 */
    private String categoryName__ = null;
    /** ラベルリスト */
    private ArrayList<CmnLabelUsrModel> labelList__ = null;
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
    public ArrayList<CmnLabelUsrModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(ArrayList<CmnLabelUsrModel> labelList) {
        labelList__ = labelList;
    }

}