package jp.groupsession.v2.adr.adr260;

import java.util.List;

import jp.groupsession.v2.adr.model.AdrLabelModel;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 ラベル選択のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr260Form extends ActionForm {

    /** 初期表示フラグ */
    private int adr260initFlg__ = 0;
    /** 呼び出し元画面のラベルパラメータ名 */
    private String adr260parentLabelName__ = null;
    /** 選択ラベル */
    private String[] adr260selectLabel__ = null;
    /** 選択アドレス */
    private String[] adr260selectSid__ = null;

    /** カテゴリリスト */
    public List<LabelValueBean> categoryList__ = null;
    /** 選択カテゴリ */
    public int selectCategory__ = -1;

    /** ラベル情報一覧 */
    public List<AdrLabelModel> labelList__ = null;

    /**
     * <p>adr260selectLabel を取得します。
     * @return adr260selectLabel
     */
    public String[] getAdr260selectLabel() {
        return adr260selectLabel__;
    }
    /**
     * <p>adr260selectLabel をセットします。
     * @param adr260selectLabel adr260selectLabel
     */
    public void setAdr260selectLabel(String[] adr260selectLabel) {
        adr260selectLabel__ = adr260selectLabel;
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
    /**
     * <p>adr260parentLabelName を取得します。
     * @return adr260parentLabelName
     */
    public String getAdr260parentLabelName() {
        return adr260parentLabelName__;
    }
    /**
     * <p>adr260parentLabelName をセットします。
     * @param adr260parentLabelName adr260parentLabelName
     */
    public void setAdr260parentLabelName(String adr260parentLabelName) {
        adr260parentLabelName__ = adr260parentLabelName;
    }
    /**
     * <p>adr260initFlg を取得します。
     * @return adr260initFlg
     */
    public int getAdr260initFlg() {
        return adr260initFlg__;
    }
    /**
     * <p>adr260initFlg をセットします。
     * @param adr260initFlg adr260initFlg
     */
    public void setAdr260initFlg(int adr260initFlg) {
        adr260initFlg__ = adr260initFlg;
    }
    /**
     * <p>adr260selectSid を取得します。
     * @return adr260selectSid
     */
    public String[] getAdr260selectSid() {
        return adr260selectSid__;
    }
    /**
     * <p>adr260selectSid をセットします。
     * @param adr260selectSid adr260selectSid
     */
    public void setAdr260selectSid(String[] adr260selectSid) {
        adr260selectSid__ = adr260selectSid;
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
     * <p>selectCategory を取得します。
     * @return selectCategory
     */
    public int getSelectCategory() {
        return selectCategory__;
    }
    /**
     * <p>selectCategory をセットします。
     * @param selectCategory selectCategory
     */
    public void setSelectCategory(int selectCategory) {
        selectCategory__ = selectCategory;
    }
}