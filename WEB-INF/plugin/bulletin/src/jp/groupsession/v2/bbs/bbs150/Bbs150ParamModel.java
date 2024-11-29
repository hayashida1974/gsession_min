package jp.groupsession.v2.bbs.bbs150;

import java.util.ArrayList;

import jp.groupsession.v2.bbs.bbs110.Bbs110ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー閲覧状況ポップアップの情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs150ParamModel extends Bbs110ParamModel {
    /** 年 */
    private int bbs150Year__;
    /** 月 */
    private int bbs150Month__;
    /** 年リスト */
    private ArrayList<LabelValueBean> bbs150YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> bbs150MonthLabelList__ = null;
    /** 初期表示フラグ */
    private boolean bbs150InitDsp__ = true;

    /**
     * <p>bbs150Year を取得します。
     * @return bbs150Year
     */
    public int getBbs150Year() {
        return bbs150Year__;
    }
    /**
     * <p>bbs150Year をセットします。
     * @param bbs150Year bbs150Year
     */
    public void setBbs150Year(int bbs150Year) {
        bbs150Year__ = bbs150Year;
    }
    /**
     * <p>bbs150Month を取得します。
     * @return bbs150Month
     */
    public int getBbs150Month() {
        return bbs150Month__;
    }
    /**
     * <p>bbs150Month をセットします。
     * @param bbs150Month bbs150Month
     */
    public void setBbs150Month(int bbs150Month) {
        bbs150Month__ = bbs150Month;
    }
    /**
     * <p>bbs150YearLabelList を取得します。
     * @return bbs150YearLabelList
     */
    public ArrayList<LabelValueBean> getBbs150YearLabelList() {
        return bbs150YearLabelList__;
    }
    /**
     * <p>bbs150YearLabelList をセットします。
     * @param bbs150YearLabelList bbs150YearLabelList
     */
    public void setBbs150YearLabelList(ArrayList<LabelValueBean> bbs150YearLabelList) {
        bbs150YearLabelList__ = bbs150YearLabelList;
    }
    /**
     * <p>bbs150MonthLabelList を取得します。
     * @return bbs150MonthLabelList
     */
    public ArrayList<LabelValueBean> getBbs150MonthLabelList() {
        return bbs150MonthLabelList__;
    }
    /**
     * <p>bbs150MonthLabelList をセットします。
     * @param bbs150MonthLabelList bbs150MonthLabelList
     */
    public void setBbs150MonthLabelList(
            ArrayList<LabelValueBean> bbs150MonthLabelList) {
        bbs150MonthLabelList__ = bbs150MonthLabelList;
    }
    /**
     * <p>bbs150InitDsp を取得します。
     * @return bbs150InitDsp
     */
    public boolean isBBs150InitDsp() {
        return bbs150InitDsp__;
    }
    /**
     * <p>bbs150InitDsp をセットします。
     * @param bbs150InitDsp bbs150InitDsp
     */
    public void setBbs150InitDsp(boolean bbs150InitDsp) {
        bbs150InitDsp__ = bbs150InitDsp;
    }
}