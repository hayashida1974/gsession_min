package jp.groupsession.v2.man.man021;

import java.util.List;

import jp.groupsession.v2.man.man020.Man020ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021ParamModel extends Man020ParamModel {
    /** 日付 月 */
    private int man021HolMonth__ = -1;
    /** 日付 日 */
    private int man021HolDay__ = -1;
    /** 日付 月 変更前*/
    private int man021HolMonthOld__ = -1;
    /** 日付 日 変更前 */
    private int man021HolDayOld__ = -1;
    /** 休日名 */
    private String man021HolName__ = null;
    /** 処理モード */
    private int man021CmdMode__ = 0;
    /** 月リスト */
    private List < LabelValueBean > man021MonthLabel__ = null;
    /** 日リスト */
    private List < LabelValueBean > man021DayLabel__ = null;

    /**
     * @return man021HolDay を戻します。
     */
    public int getMan021HolDay() {
        return man021HolDay__;
    }
    /**
     * @param man021HolDay 設定する man021HolDay。
     */
    public void setMan021HolDay(int man021HolDay) {
        man021HolDay__ = man021HolDay;
    }
    /**
     * @return man021HolMonth を戻します。
     */
    public int getMan021HolMonth() {
        return man021HolMonth__;
    }
    /**
     * @param man021HolMonth 設定する man021HolMonth。
     */
    public void setMan021HolMonth(int man021HolMonth) {
        man021HolMonth__ = man021HolMonth;
    }
    /**
     * @return man021HolDayOld を戻します。
     */
    public int getMan021HolDayOld() {
        return man021HolDayOld__;
    }
    /**
     * @param man021HolDayOld 設定する man021HolDayOld。
     */
    public void setMan021HolDayOld(int man021HolDayOld) {
        man021HolDayOld__ = man021HolDayOld;
    }
    /**
     * @return man021HolMonthOld を戻します。
     */
    public int getMan021HolMonthOld() {
        return man021HolMonthOld__;
    }
    /**
     * @param man021HolMonthOld 設定する man021HolMonthOld。
     */
    public void setMan021HolMonthOld(int man021HolMonthOld) {
        man021HolMonthOld__ = man021HolMonthOld;
    }
    /**
     * @return man021HolName を戻します。
     */
    public String getMan021HolName() {
        return man021HolName__;
    }
    /**
     * @param man021HolName 設定する man021HolName。
     */
    public void setMan021HolName(String man021HolName) {
        man021HolName__ = man021HolName;
    }
    /**
     * @return man021CmdMode を戻します。
     */
    public int getMan021CmdMode() {
        return man021CmdMode__;
    }
    /**
     * @param man021CmdMode 設定する man021CmdMode。
     */
    public void setMan021CmdMode(int man021CmdMode) {
        man021CmdMode__ = man021CmdMode;
    }
    /**
     * @return man021DayLabel を戻します。
     */
    public List < LabelValueBean > getMan021DayLabel() {
        return man021DayLabel__;
    }
    /**
     * @param man021DayLabel 設定する man021DayLabel。
     */
    public void setMan021DayLabel(List < LabelValueBean > man021DayLabel) {
        man021DayLabel__ = man021DayLabel;
    }
    /**
     * @return man021MonthLabel を戻します。
     */
    public List < LabelValueBean > getMan021MonthLabel() {
        return man021MonthLabel__;
    }
    /**
     * @param man021MonthLabel 設定する man021MonthLabel。
     */
    public void setMan021MonthLabel(List < LabelValueBean > man021MonthLabel) {
        man021MonthLabel__ = man021MonthLabel;
    }
}