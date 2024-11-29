package jp.groupsession.v2.adr.adr190;

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
public class Adr190Form extends ActionForm {

    /** 初期表示フラグ */
    private int adr190initFlg__ = 0;
    /** 呼び出し元画面のラベルパラメータ名 */
    private String adr190parentLabelName__ = null;
    /** 選択ラベル */
    private String[] adr190selectLabel__ = null;

    /** ラベル情報一覧 */
    public List<AdrLabelModel> labelList__ = null;

    /** カテゴリ一覧 */
    private List<LabelValueBean> adr190category__ = null;
    /** 選択カテゴリ */
    private int adr190selectCategory__ = -1;

    /**
     * <p>adr190selectLabel を取得します。
     * @return adr190selectLabel
     */
    public String[] getAdr190selectLabel() {
        return adr190selectLabel__;
    }
    /**
     * <p>adr190selectLabel をセットします。
     * @param adr190selectLabel adr190selectLabel
     */
    public void setAdr190selectLabel(String[] adr190selectLabel) {
        adr190selectLabel__ = adr190selectLabel;
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
     * <p>adr190parentLabelName を取得します。
     * @return adr190parentLabelName
     */
    public String getAdr190parentLabelName() {
        return adr190parentLabelName__;
    }
    /**
     * <p>adr190parentLabelName をセットします。
     * @param adr190parentLabelName adr190parentLabelName
     */
    public void setAdr190parentLabelName(String adr190parentLabelName) {
        adr190parentLabelName__ = adr190parentLabelName;
    }
    /**
     * <p>adr190initFlg を取得します。
     * @return adr190initFlg
     */
    public int getAdr190initFlg() {
        return adr190initFlg__;
    }
    /**
     * <p>adr190initFlg をセットします。
     * @param adr190initFlg adr190initFlg
     */
    public void setAdr190initFlg(int adr190initFlg) {
        adr190initFlg__ = adr190initFlg;
    }
    /**
     * <p>adr190category を取得します。
     * @return adr190category
     */
    public List<LabelValueBean> getAdr190category() {
        return adr190category__;
    }
    /**
     * <p>adr190category をセットします。
     * @param adr190category adr190category
     */
    public void setAdr190category(List<LabelValueBean> adr190category) {
        adr190category__ = adr190category;
    }
    /**
     * <p>adr190selectCategory を取得します。
     * @return adr190selectCategory
     */
    public int getAdr190selectCategory() {
        return adr190selectCategory__;
    }
    /**
     * <p>adr190selectCategory をセットします。
     * @param adr190selectCategory adr190selectCategory
     */
    public void setAdr190selectCategory(int adr190selectCategory) {
        adr190selectCategory__ = adr190selectCategory;
    }
}