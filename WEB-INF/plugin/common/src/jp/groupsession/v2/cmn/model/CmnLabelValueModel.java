package jp.groupsession.v2.cmn.model;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能]  org.apache.struts.util.LabelValueBeanにスケジュール情報を付加したクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CmnLabelValueModel extends LabelValueBean {

    /** コンボボックスのスタイル指定用区分 */
    private String styleClass__;
    /** 選択不可 */
    private boolean disable__ = false;

    /**
     * コンストラクタ（通常はこちらを使う）
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル指定
     */
    public CmnLabelValueModel(String label, String value, String style) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
        setDisable(false);
    }

    /**
     * コンストラクタ(選択不可ラベル作成時に使用）
     * @param label ラベル
     * @param value バリュー
     * @param style スタイル
     * @param disable 選択不可
     */
    public CmnLabelValueModel(String label, String value, String style, boolean disable) {
        setLabel(label);
        setValue(value);
        setStyleClass(style);
        setDisable(disable);
    }

    /**
     * <p>styleClass を取得します。
     * @return styleClass
     */
    public String getStyleClass() {
        return styleClass__;
    }

    /**
     * <p>styleClass をセットします。
     * @param styleClass styleClass
     */
    public void setStyleClass(String styleClass) {
        styleClass__ = styleClass;
    }

    /**
     * <p>disable を取得します。
     * @return disable
     */
    public boolean isDisable() {
        return disable__;
    }

    /**
     * <p>disable をセットします。
     * @param disable disable
     */
    public void setDisable(boolean disable) {
        disable__ = disable;
    }

}