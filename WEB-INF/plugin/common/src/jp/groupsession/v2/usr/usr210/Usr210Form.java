package jp.groupsession.v2.usr.usr210;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ラベル選択のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr210Form extends AbstractGsForm {

    /** 初期表示フラグ */
    private int usr210initFlg__ = 0;
    /** 呼び出し元画面のラベルパラメータ名 */
    private String usr210parentLabelName__ = null;
    /** 選択ラベル */
    private String[] usr210selectLabel__ = null;

    /** カテゴリ情報一覧 */
    public List<LabelValueBean> categoryList__ = null;
    /** ラベル情報一覧 */
    public List<CmnLabelUsrModel> labelList__ = null;
    /** 選択カテゴリSID */
    public int categorySid__ = -1;


    /**
     * <p>usr210initFlg を取得します。
     * @return usr210initFlg
     */
    public int getUsr210initFlg() {
        return usr210initFlg__;
    }

    /**
     * <p>usr210initFlg をセットします。
     * @param usr210initFlg usr210initFlg
     */
    public void setUsr210initFlg(int usr210initFlg) {
        usr210initFlg__ = usr210initFlg;
    }

    /**
     * <p>usr210parentLabelName を取得します。
     * @return usr210parentLabelName
     */
    public String getUsr210parentLabelName() {
        return usr210parentLabelName__;
    }

    /**
     * <p>usr210parentLabelName をセットします。
     * @param usr210parentLabelName usr210parentLabelName
     */
    public void setUsr210parentLabelName(String usr210parentLabelName) {
        usr210parentLabelName__ = usr210parentLabelName;
    }

    /**
     * <p>usr210selectLabel を取得します。
     * @return usr210selectLabel
     */
    public String[] getUsr210selectLabel() {
        return usr210selectLabel__;
    }

    /**
     * <p>usr210selectLabel をセットします。
     * @param usr210selectLabel usr210selectLabel
     */
    public void setUsr210selectLabel(String[] usr210selectLabel) {
        usr210selectLabel__ = usr210selectLabel;
    }

    /**
     * <p>categoryList を取得します。
     * @return categoryList
     */
    public List<LabelValueBean> getCategoryList() {
        return categoryList__;
    }

    /**
     * <p>categoryList をセットします。
     * @param categoryList categoryList
     */
    public void setCategoryList(List<LabelValueBean> categoryList) {
        categoryList__ = categoryList;
    }

    /**
     * <p>labelList を取得します。
     * @return labelList
     */
    public List<CmnLabelUsrModel> getLabelList() {
        return labelList__;
    }

    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(List<CmnLabelUsrModel> labelList) {
        labelList__ = labelList;
    }

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

}