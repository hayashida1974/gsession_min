package jp.groupsession.v2.man.man190;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴手動削除画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man190ParamModel extends AbstractParamModel {
    /** 年 */
    private int man190Year__;
    /** 月 */
    private int man190Month__;
    /** 年リスト */
    private ArrayList<LabelValueBean> man190YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> man190MonthLabelList__ = null;
    /** 初期表示フラグ */
    private boolean man190InitDsp__ = true;

    /**
     * <p>man190Year を取得します。
     * @return man190Year
     */
    public int getMan190Year() {
        return man190Year__;
    }
    /**
     * <p>man190Year をセットします。
     * @param man190Year man190Year
     */
    public void setMan190Year(int man190Year) {
        man190Year__ = man190Year;
    }
    /**
     * <p>man190Month を取得します。
     * @return man190Month
     */
    public int getMan190Month() {
        return man190Month__;
    }
    /**
     * <p>man190Month をセットします。
     * @param man190Month man190Month
     */
    public void setMan190Month(int man190Month) {
        man190Month__ = man190Month;
    }
    /**
     * <p>man190YearLabelList を取得します。
     * @return man190YearLabelList
     */
    public ArrayList<LabelValueBean> getMan190YearLabelList() {
        return man190YearLabelList__;
    }
    /**
     * <p>man190YearLabelList をセットします。
     * @param man190YearLabelList man190YearLabelList
     */
    public void setMan190YearLabelList(ArrayList<LabelValueBean> man190YearLabelList) {
        man190YearLabelList__ = man190YearLabelList;
    }
    /**
     * <p>man190MonthLabelList を取得します。
     * @return man190MonthLabelList
     */
    public ArrayList<LabelValueBean> getMan190MonthLabelList() {
        return man190MonthLabelList__;
    }
    /**
     * <p>man190MonthLabelList をセットします。
     * @param man190MonthLabelList man190MonthLabelList
     */
    public void setMan190MonthLabelList(
            ArrayList<LabelValueBean> man190MonthLabelList) {
        man190MonthLabelList__ = man190MonthLabelList;
    }
    /**
     * <p>man190InitDsp を取得します。
     * @return man190InitDsp
     */
    public boolean isMan190InitDsp() {
        return man190InitDsp__;
    }
    /**
     * <p>man190InitDsp をセットします。
     * @param man190InitDsp man190InitDsp
     */
    public void setMan190InitDsp(boolean man190InitDsp) {
        man190InitDsp__ = man190InitDsp;
    }
}