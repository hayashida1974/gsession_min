package jp.groupsession.v2.sml.sml340kn;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.sml340.Sml340Form;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml340knForm extends Sml340Form {

    /** 条件１ 条件 */
    private String sml340conditionType1View__ = null;
    /** 条件２ 条件 */
    private String sml340conditionType2View__ = null;
    /** 条件３ 条件 */
    private String sml340conditionType3View__ = null;
    /** 条件４ 条件 */
    private String sml340conditionType4View__ = null;
    /** 条件５ 条件 */
    private String sml340conditionType5View__ = null;
    /** 条件１ 条件指定 */
    private String sml340conditionExs1View__ = null;
    /** 条件２ 条件指定 */
    private String sml340conditionExs2View__ = null;
    /** 条件３ 条件指定 */
    private String sml340conditionExs3View__ = null;
    /** 条件４ 条件指定 */
    private String sml340conditionExs4View__ = null;
    /** 条件５ 条件指定 */
    private String sml340conditionExs5View__ = null;
    /** ラベル名 */
    private String sml340LabelView__ = null;
    /** フィルター区分 */
    private String sml340filterTypeView__ = null;


    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("dspCount", getDspCount());
        msgForm.addHiddenParam("sml330sortFilter", getSml330SortRadio());
        msgForm.addHiddenParam("smlEditFilterId", getSmlEditFilterId());
        msgForm.addHiddenParam("sml330SortRadio", getSml330SortRadio());
    }


    /**
     * <p>sml340conditionType1View を取得します。
     * @return sml340conditionType1View
     */
    public String getSml340conditionType1View() {
        return sml340conditionType1View__;
    }


    /**
     * <p>sml340conditionType1View をセットします。
     * @param sml340conditionType1View sml340conditionType1View
     */
    public void setSml340conditionType1View(String sml340conditionType1View) {
        sml340conditionType1View__ = sml340conditionType1View;
    }


    /**
     * <p>sml340conditionType2View を取得します。
     * @return sml340conditionType2View
     */
    public String getSml340conditionType2View() {
        return sml340conditionType2View__;
    }


    /**
     * <p>sml340conditionType2View をセットします。
     * @param sml340conditionType2View sml340conditionType2View
     */
    public void setSml340conditionType2View(String sml340conditionType2View) {
        sml340conditionType2View__ = sml340conditionType2View;
    }


    /**
     * <p>sml340conditionType3View を取得します。
     * @return sml340conditionType3View
     */
    public String getSml340conditionType3View() {
        return sml340conditionType3View__;
    }


    /**
     * <p>sml340conditionType3View をセットします。
     * @param sml340conditionType3View sml340conditionType3View
     */
    public void setSml340conditionType3View(String sml340conditionType3View) {
        sml340conditionType3View__ = sml340conditionType3View;
    }


    /**
     * <p>sml340conditionType4View を取得します。
     * @return sml340conditionType4View
     */
    public String getSml340conditionType4View() {
        return sml340conditionType4View__;
    }


    /**
     * <p>sml340conditionType4View をセットします。
     * @param sml340conditionType4View sml340conditionType4View
     */
    public void setSml340conditionType4View(String sml340conditionType4View) {
        sml340conditionType4View__ = sml340conditionType4View;
    }


    /**
     * <p>sml340conditionType5View を取得します。
     * @return sml340conditionType5View
     */
    public String getSml340conditionType5View() {
        return sml340conditionType5View__;
    }


    /**
     * <p>sml340conditionType5View をセットします。
     * @param sml340conditionType5View sml340conditionType5View
     */
    public void setSml340conditionType5View(String sml340conditionType5View) {
        sml340conditionType5View__ = sml340conditionType5View;
    }


    /**
     * <p>sml340conditionExs1View を取得します。
     * @return sml340conditionExs1View
     */
    public String getSml340conditionExs1View() {
        return sml340conditionExs1View__;
    }


    /**
     * <p>sml340conditionExs1View をセットします。
     * @param sml340conditionExs1View sml340conditionExs1View
     */
    public void setSml340conditionExs1View(String sml340conditionExs1View) {
        sml340conditionExs1View__ = sml340conditionExs1View;
    }


    /**
     * <p>sml340conditionExs2View を取得します。
     * @return sml340conditionExs2View
     */
    public String getSml340conditionExs2View() {
        return sml340conditionExs2View__;
    }


    /**
     * <p>sml340conditionExs2View をセットします。
     * @param sml340conditionExs2View sml340conditionExs2View
     */
    public void setSml340conditionExs2View(String sml340conditionExs2View) {
        sml340conditionExs2View__ = sml340conditionExs2View;
    }


    /**
     * <p>sml340conditionExs3View を取得します。
     * @return sml340conditionExs3View
     */
    public String getSml340conditionExs3View() {
        return sml340conditionExs3View__;
    }


    /**
     * <p>sml340conditionExs3View をセットします。
     * @param sml340conditionExs3View sml340conditionExs3View
     */
    public void setSml340conditionExs3View(String sml340conditionExs3View) {
        sml340conditionExs3View__ = sml340conditionExs3View;
    }


    /**
     * <p>sml340conditionExs4View を取得します。
     * @return sml340conditionExs4View
     */
    public String getSml340conditionExs4View() {
        return sml340conditionExs4View__;
    }


    /**
     * <p>sml340conditionExs4View をセットします。
     * @param sml340conditionExs4View sml340conditionExs4View
     */
    public void setSml340conditionExs4View(String sml340conditionExs4View) {
        sml340conditionExs4View__ = sml340conditionExs4View;
    }


    /**
     * <p>sml340conditionExs5View を取得します。
     * @return sml340conditionExs5View
     */
    public String getSml340conditionExs5View() {
        return sml340conditionExs5View__;
    }


    /**
     * <p>sml340conditionExs5View をセットします。
     * @param sml340conditionExs5View sml340conditionExs5View
     */
    public void setSml340conditionExs5View(String sml340conditionExs5View) {
        sml340conditionExs5View__ = sml340conditionExs5View;
    }


    /**
     * <p>sml340LabelView を取得します。
     * @return sml340LabelView
     */
    public String getSml340LabelView() {
        return sml340LabelView__;
    }


    /**
     * <p>sml340LabelView をセットします。
     * @param sml340LabelView sml340LabelView
     */
    public void setSml340LabelView(String sml340LabelView) {
        sml340LabelView__ = sml340LabelView;
    }


    /**
     * <p>sml340filterTypeView を取得します。
     * @return sml340filterTypeView
     */
    public String getSml340filterTypeView() {
        return sml340filterTypeView__;
    }


    /**
     * <p>sml340filterTypeView をセットします。
     * @param sml340filterTypeView sml340filterTypeView
     */
    public void setSml340filterTypeView(String sml340filterTypeView) {
        sml340filterTypeView__ = sml340filterTypeView;
    }
}
