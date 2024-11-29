package jp.groupsession.v2.man.man040;

import java.util.ArrayList;

import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 バッチ処理起動時間設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man040Form extends AbstractGsForm {
    /** 開始時 */
    private String man040FrHour__ = null;
    /** 時リスト */
    private ArrayList < LabelValueBean > man040HourLabel__ = null;
    /**
     * <p>man040FrHour を取得します。
     * @return man040FrHour
     */
    public String getMan040FrHour() {
        return man040FrHour__;
    }
    /**
     * <p>man040FrHour をセットします。
     * @param man040FrHour man040FrHour
     */
    public void setMan040FrHour(String man040FrHour) {
        man040FrHour__ = man040FrHour;
    }
    /**
     * <p>man040HourLabel を取得します。
     * @return man040HourLabel
     */
    public ArrayList<LabelValueBean> getMan040HourLabel() {
        return man040HourLabel__;
    }
    /**
     * <p>man040HourLabel をセットします。
     * @param man040HourLabel man040HourLabel
     */
    public void setMan040HourLabel(ArrayList<LabelValueBean> man040HourLabel) {
        man040HourLabel__ = man040HourLabel;
    }
}