package jp.groupsession.v2.sml.sml360kn;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.sml360.Sml360Form;

/**
 * <br>[機  能] ショートメール 個人設定 フィルタ登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml360knForm extends Sml360Form {

    /** 条件１ 条件 */
    private String sml360conditionType1View__ = null;
    /** 条件２ 条件 */
    private String sml360conditionType2View__ = null;
    /** 条件３ 条件 */
    private String sml360conditionType3View__ = null;
    /** 条件４ 条件 */
    private String sml360conditionType4View__ = null;
    /** 条件５ 条件 */
    private String sml360conditionType5View__ = null;
    /** 条件１ 条件指定 */
    private String sml360conditionExs1View__ = null;
    /** 条件２ 条件指定 */
    private String sml360conditionExs2View__ = null;
    /** 条件３ 条件指定 */
    private String sml360conditionExs3View__ = null;
    /** 条件４ 条件指定 */
    private String sml360conditionExs4View__ = null;
    /** 条件５ 条件指定 */
    private String sml360conditionExs5View__ = null;
    /** ラベル名 */
    private String sml360LabelView__ = null;
    /** フィルター区分 */
    private String sml360filterTypeView__ = null;


    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("dspCount", getDspCount());
        msgForm.addHiddenParam("sml350sortFilter", getSml350SortRadio());
        msgForm.addHiddenParam("smlEditFilterId", getSmlEditFilterId());
        msgForm.addHiddenParam("sml350SortRadio", getSml350SortRadio());
    }


    /**
     * <p>sml360conditionType1View を取得します。
     * @return sml360conditionType1View
     */
    public String getSml360conditionType1View() {
        return sml360conditionType1View__;
    }


    /**
     * <p>sml360conditionType1View をセットします。
     * @param sml360conditionType1View sml360conditionType1View
     */
    public void setSml360conditionType1View(String sml360conditionType1View) {
        sml360conditionType1View__ = sml360conditionType1View;
    }


    /**
     * <p>sml360conditionType2View を取得します。
     * @return sml360conditionType2View
     */
    public String getSml360conditionType2View() {
        return sml360conditionType2View__;
    }


    /**
     * <p>sml360conditionType2View をセットします。
     * @param sml360conditionType2View sml360conditionType2View
     */
    public void setSml360conditionType2View(String sml360conditionType2View) {
        sml360conditionType2View__ = sml360conditionType2View;
    }


    /**
     * <p>sml360conditionType3View を取得します。
     * @return sml360conditionType3View
     */
    public String getSml360conditionType3View() {
        return sml360conditionType3View__;
    }


    /**
     * <p>sml360conditionType3View をセットします。
     * @param sml360conditionType3View sml360conditionType3View
     */
    public void setSml360conditionType3View(String sml360conditionType3View) {
        sml360conditionType3View__ = sml360conditionType3View;
    }


    /**
     * <p>sml360conditionType4View を取得します。
     * @return sml360conditionType4View
     */
    public String getSml360conditionType4View() {
        return sml360conditionType4View__;
    }


    /**
     * <p>sml360conditionType4View をセットします。
     * @param sml360conditionType4View sml360conditionType4View
     */
    public void setSml360conditionType4View(String sml360conditionType4View) {
        sml360conditionType4View__ = sml360conditionType4View;
    }


    /**
     * <p>sml360conditionType5View を取得します。
     * @return sml360conditionType5View
     */
    public String getSml360conditionType5View() {
        return sml360conditionType5View__;
    }


    /**
     * <p>sml360conditionType5View をセットします。
     * @param sml360conditionType5View sml360conditionType5View
     */
    public void setSml360conditionType5View(String sml360conditionType5View) {
        sml360conditionType5View__ = sml360conditionType5View;
    }


    /**
     * <p>sml360conditionExs1View を取得します。
     * @return sml360conditionExs1View
     */
    public String getSml360conditionExs1View() {
        return sml360conditionExs1View__;
    }


    /**
     * <p>sml360conditionExs1View をセットします。
     * @param sml360conditionExs1View sml360conditionExs1View
     */
    public void setSml360conditionExs1View(String sml360conditionExs1View) {
        sml360conditionExs1View__ = sml360conditionExs1View;
    }


    /**
     * <p>sml360conditionExs2View を取得します。
     * @return sml360conditionExs2View
     */
    public String getSml360conditionExs2View() {
        return sml360conditionExs2View__;
    }


    /**
     * <p>sml360conditionExs2View をセットします。
     * @param sml360conditionExs2View sml360conditionExs2View
     */
    public void setSml360conditionExs2View(String sml360conditionExs2View) {
        sml360conditionExs2View__ = sml360conditionExs2View;
    }


    /**
     * <p>sml360conditionExs3View を取得します。
     * @return sml360conditionExs3View
     */
    public String getSml360conditionExs3View() {
        return sml360conditionExs3View__;
    }


    /**
     * <p>sml360conditionExs3View をセットします。
     * @param sml360conditionExs3View sml360conditionExs3View
     */
    public void setSml360conditionExs3View(String sml360conditionExs3View) {
        sml360conditionExs3View__ = sml360conditionExs3View;
    }


    /**
     * <p>sml360conditionExs4View を取得します。
     * @return sml360conditionExs4View
     */
    public String getSml360conditionExs4View() {
        return sml360conditionExs4View__;
    }


    /**
     * <p>sml360conditionExs4View をセットします。
     * @param sml360conditionExs4View sml360conditionExs4View
     */
    public void setSml360conditionExs4View(String sml360conditionExs4View) {
        sml360conditionExs4View__ = sml360conditionExs4View;
    }


    /**
     * <p>sml360conditionExs5View を取得します。
     * @return sml360conditionExs5View
     */
    public String getSml360conditionExs5View() {
        return sml360conditionExs5View__;
    }


    /**
     * <p>sml360conditionExs5View をセットします。
     * @param sml360conditionExs5View sml360conditionExs5View
     */
    public void setSml360conditionExs5View(String sml360conditionExs5View) {
        sml360conditionExs5View__ = sml360conditionExs5View;
    }


    /**
     * <p>sml360LabelView を取得します。
     * @return sml360LabelView
     */
    public String getSml360LabelView() {
        return sml360LabelView__;
    }


    /**
     * <p>sml360LabelView をセットします。
     * @param sml360LabelView sml360LabelView
     */
    public void setSml360LabelView(String sml360LabelView) {
        sml360LabelView__ = sml360LabelView;
    }


    /**
     * <p>sml360filterTypeView を取得します。
     * @return sml360filterTypeView
     */
    public String getSml360filterTypeView() {
        return sml360filterTypeView__;
    }


    /**
     * <p>sml360filterTypeView をセットします。
     * @param sml360filterTypeView sml360filterTypeView
     */
    public void setSml360filterTypeView(String sml360filterTypeView) {
        sml360filterTypeView__ = sml360filterTypeView;
    }


}
