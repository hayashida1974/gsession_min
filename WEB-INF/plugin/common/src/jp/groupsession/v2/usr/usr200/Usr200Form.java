package jp.groupsession.v2.usr.usr200;

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
public class Usr200Form extends AbstractGsForm {

    /** 初期表示フラグ */
    private int usr200initFlg__ = 0;
    /** 呼び出し元画面のラベルパラメータ名 */
    private String usr200parentLabelName__ = null;
    /** 選択ラベル */
    private String[] usr200selectLabel__ = null;

    /** カテゴリ情報一覧 */
    public List<LabelValueBean> categoryList__ = null;
    /** ラベル情報一覧 */
    public List<CmnLabelUsrModel> labelList__ = null;
    /** 選択カテゴリSID */
    public int categorySid__ = -1;


    /**
     * <p>usr200initFlg を取得します。
     * @return usr200initFlg
     */
    public int getUsr200initFlg() {
        return usr200initFlg__;
    }

    /**
     * <p>usr200initFlg をセットします。
     * @param usr200initFlg usr200initFlg
     */
    public void setUsr200initFlg(int usr200initFlg) {
        usr200initFlg__ = usr200initFlg;
    }

    /**
     * <p>usr200parentLabelName を取得します。
     * @return usr200parentLabelName
     */
    public String getUsr200parentLabelName() {
        return usr200parentLabelName__;
    }

    /**
     * <p>usr200parentLabelName をセットします。
     * @param usr200parentLabelName usr200parentLabelName
     */
    public void setUsr200parentLabelName(String usr200parentLabelName) {
        usr200parentLabelName__ = usr200parentLabelName;
    }

    /**
     * <p>usr200selectLabel を取得します。
     * @return usr200selectLabel
     */
    public String[] getUsr200selectLabel() {
        return usr200selectLabel__;
    }

    /**
     * <p>usr200selectLabel をセットします。
     * @param usr200selectLabel usr200selectLabel
     */
    public void setUsr200selectLabel(String[] usr200selectLabel) {
        usr200selectLabel__ = usr200selectLabel;
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