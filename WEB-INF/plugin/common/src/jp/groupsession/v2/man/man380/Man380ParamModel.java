package jp.groupsession.v2.man.man380;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ手動削除画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man380ParamModel extends AbstractParamModel {
    /** 年 */
    private int man380Year__;
    /** 月 */
    private int man380Month__;
    /** 年リスト */
    private ArrayList<LabelValueBean> man380YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> man380MonthLabelList__ = null;
    /** 初期表示フラグ */
    private boolean man380InitDsp__ = true;

    /**
     * <p>man380Year を取得します。
     * @return man380Year
     */
    public int getMan380Year() {
        return man380Year__;
    }
    /**
     * <p>man380Year をセットします。
     * @param man380Year man380Year
     */
    public void setMan380Year(int man380Year) {
        man380Year__ = man380Year;
    }
    /**
     * <p>man380Month を取得します。
     * @return man380Month
     */
    public int getMan380Month() {
        return man380Month__;
    }
    /**
     * <p>man380Month をセットします。
     * @param man380Month man380Month
     */
    public void setMan380Month(int man380Month) {
        man380Month__ = man380Month;
    }
    /**
     * <p>man380YearLabelList を取得します。
     * @return man380YearLabelList
     */
    public ArrayList<LabelValueBean> getMan380YearLabelList() {
        return man380YearLabelList__;
    }
    /**
     * <p>man380YearLabelList をセットします。
     * @param man380YearLabelList man380YearLabelList
     */
    public void setMan380YearLabelList(ArrayList<LabelValueBean> man380YearLabelList) {
        man380YearLabelList__ = man380YearLabelList;
    }
    /**
     * <p>man380MonthLabelList を取得します。
     * @return man380MonthLabelList
     */
    public ArrayList<LabelValueBean> getMan380MonthLabelList() {
        return man380MonthLabelList__;
    }
    /**
     * <p>man380MonthLabelList をセットします。
     * @param man380MonthLabelList man380MonthLabelList
     */
    public void setMan380MonthLabelList(
            ArrayList<LabelValueBean> man380MonthLabelList) {
        man380MonthLabelList__ = man380MonthLabelList;
    }
    /**
     * <p>man380InitDsp を取得します。
     * @return man380InitDsp
     */
    public boolean isMan380InitDsp() {
        return man380InitDsp__;
    }
    /**
     * <p>man380InitDsp をセットします。
     * @param man380InitDsp man380InitDsp
     */
    public void setMan380InitDsp(boolean man380InitDsp) {
        man380InitDsp__ = man380InitDsp;
    }
}