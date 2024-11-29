package jp.groupsession.v2.man.man180;

import java.util.ArrayList;

import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man180Form extends AbstractGsForm {

    /** 自動削除バッチ区分 */
    private int man180BatchKbn__;
    /** 年 */
    private int man180Year__;
    /** 月 */
    private int man180Month__;
    /** 年リスト */
    private ArrayList<LabelValueBean> man180YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> man180MonthLabelList__ = null;
    /** バッチ起動時間 */
    private String man180BatchTime__ = null;
    /** 初期表示フラグ */
    private boolean man180InitDsp__ = true;

    /**
     * <p>man180BatchKbn を取得します。
     * @return man180BatchKbn
     */
    public int getMan180BatchKbn() {
        return man180BatchKbn__;
    }
    /**
     * <p>man180BatchKbn をセットします。
     * @param man180BatchKbn man180BatchKbn
     */
    public void setMan180BatchKbn(int man180BatchKbn) {
        man180BatchKbn__ = man180BatchKbn;
    }
    /**
     * <p>man180Year を取得します。
     * @return man180Year
     */
    public int getMan180Year() {
        return man180Year__;
    }
    /**
     * <p>man180Year をセットします。
     * @param man180Year man180Year
     */
    public void setMan180Year(int man180Year) {
        man180Year__ = man180Year;
    }
    /**
     * <p>man180Month を取得します。
     * @return man180Month
     */
    public int getMan180Month() {
        return man180Month__;
    }
    /**
     * <p>man180Month をセットします。
     * @param man180Month man180Month
     */
    public void setMan180Month(int man180Month) {
        man180Month__ = man180Month;
    }
    /**
     * <p>man180YearLabelList を取得します。
     * @return man180YearLabelList
     */
    public ArrayList<LabelValueBean> getMan180YearLabelList() {
        return man180YearLabelList__;
    }
    /**
     * <p>man180YearLabelList をセットします。
     * @param man180YearLabelList man180YearLabelList
     */
    public void setMan180YearLabelList(ArrayList<LabelValueBean> man180YearLabelList) {
        man180YearLabelList__ = man180YearLabelList;
    }
    /**
     * <p>man180MonthLabelList を取得します。
     * @return man180MonthLabelList
     */
    public ArrayList<LabelValueBean> getMan180MonthLabelList() {
        return man180MonthLabelList__;
    }
    /**
     * <p>man180MonthLabelList をセットします。
     * @param man180MonthLabelList man180MonthLabelList
     */
    public void setMan180MonthLabelList(
            ArrayList<LabelValueBean> man180MonthLabelList) {
        man180MonthLabelList__ = man180MonthLabelList;
    }
    /**
     * <p>man180BatchTime を取得します。
     * @return man180BatchTime
     */
    public String getMan180BatchTime() {
        return man180BatchTime__;
    }
    /**
     * <p>man180BatchTime をセットします。
     * @param man180BatchTime man180BatchTime
     */
    public void setMan180BatchTime(String man180BatchTime) {
        man180BatchTime__ = man180BatchTime;
    }
    /**
     * <p>man180InitDsp を取得します。
     * @return man180InitDsp
     */
    public boolean isMan180InitDsp() {
        return man180InitDsp__;
    }
    /**
     * <p>man180InitDsp をセットします。
     * @param man180InitDsp man180InitDsp
     */
    public void setMan180InitDsp(boolean man180InitDsp) {
        man180InitDsp__ = man180InitDsp;
    }
}