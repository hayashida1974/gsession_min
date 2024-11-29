package jp.groupsession.v2.man.man260;

import java.util.List;

import jp.co.sjts.util.struts.AbstractForm;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ自動削除設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man260Form extends AbstractForm {

    /** 設定チェック */
    private String man260BatchKbn__ = null;
    /** 自動削除 年 */
    private String man260Year__ = null;
    /** 自動削除 月 */
    private String man260Month__ = null;
    /** バッチ起動時間 */
    private String man260BatchFrHour__ = null;
    /** 年コンボ */
    private List<LabelValueBean> yearLabel__ = null;
    /** 月コンボ */
    private List<LabelValueBean> monthLabel__ = null;

    /**
     * @return monthLabel
     */
    public List<LabelValueBean> getMonthLabel() {
        return monthLabel__;
    }
    /**
     * @param monthLabel 設定する monthLabel
     */
    public void setMonthLabel(List<LabelValueBean> monthLabel) {
        monthLabel__ = monthLabel;
    }
    /**
     * @return man260BatchKbn
     */
    public String getMan260BatchKbn() {
    return man260BatchKbn__;
    }
    /**
     * @param man260BatchKbn 設定する man260BatchKbn
     */
    public void setMan260BatchKbn(String man260BatchKbn) {
        man260BatchKbn__ = man260BatchKbn;
    }
    /**
     * @return man260Month
     */
    public String getMan260Month() {
        return man260Month__;
    }
    /**
     * @param man260Month 設定する man260Month
     */
    public void setMan260Month(String man260Month) {
        man260Month__ = man260Month;
    }
    /**
     * @return man260Year
     */
    public String getMan260Year() {
        return man260Year__;
    }
    /**
     * @param man260Year 設定する man260Year
     */
    public void setMan260Year(String man260Year) {
        man260Year__ = man260Year;
    }
    /**
     * @return yearLabel
     */
    public List<LabelValueBean> getYearLabel() {
        return yearLabel__;
    }
    /**
     * @param yearLabel 設定する yearLabel
     */
    public void setYearLabel(List<LabelValueBean> yearLabel) {
        yearLabel__ = yearLabel;
    }
    /**
     * @return man260BatchFrHour
     */
    public String getMan260BatchFrHour() {
        return man260BatchFrHour__;
    }
    /**
     * @param man260BatchFrHour 設定する man260BatchFrHour
     */
    public void setMan260BatchFrHour(String man260BatchFrHour) {
        man260BatchFrHour__ = man260BatchFrHour;
    }
}
