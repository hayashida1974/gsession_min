package jp.groupsession.v2.man.man026;

import java.util.ArrayList;

import jp.groupsession.v2.man.man025.Man025ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/拡張画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man026ParamModel extends Man025ParamModel {
    /** 日付　月-拡張 */
    private int man026HltMonth__ = -1;
    /** 日付　週-拡張 */
    private int man026HltWeek__ = -1;
    /** 日付曜日-拡張 */
    private int man026HltDayOfWeek__ = -1;

    /** 休日名-拡張 */
    private String man026HltName__ = null;
    /** 自動振替フラグ */
    private int man026FuriFlg__ = 1;

    /** 月リスト-拡張 */
    private ArrayList < LabelValueBean > man026MonthLabel__ = null;
    /** 週リスト-拡張 */
    private ArrayList < LabelValueBean > man026WeekLabel__ = null;
    /** 曜日リスト-拡張 */
    private ArrayList < LabelValueBean > man026DayOfWeekLabel__ = null;


    /**
     * @return man026HltDayOfWeek__ を戻します。
     */
    public int getMan026HltDayOfWeek() {
        return man026HltDayOfWeek__;
    }

    /**
     * @param man026HltDayOfWeek 設定する man026HltDayOfWeek__。
     */
    public void setMan026HltDayOfWeek(int man026HltDayOfWeek) {
        this.man026HltDayOfWeek__ = man026HltDayOfWeek;
    }

    /**
     * @return man026HltMonth__ を戻します。
     */
    public int getMan026HltMonth() {
        return man026HltMonth__;
    }

    /**
     * @param man026HltMonth 設定する man026HltMonth__。
     */
    public void setMan026HltMonth(int man026HltMonth) {
        this.man026HltMonth__ = man026HltMonth;
    }

    /**
     * @return man026HltWeek__ を戻します。
     */
    public int getMan026HltWeek() {
        return man026HltWeek__;
    }

    /**
     * @param man026HltWeek 設定する man026HltWeek__。
     */
    public void setMan026HltWeek(int man026HltWeek) {
        this.man026HltWeek__ = man026HltWeek;
    }

    /**
     * @return man026MonthLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026MonthLabel() {
        return man026MonthLabel__;
    }

    /**
     * @param man026MonthLabel 設定する man026MonthLabel__。
     */
    public void setMan026MonthLabel(ArrayList < LabelValueBean > man026MonthLabel) {
        this.man026MonthLabel__ = man026MonthLabel;
    }

    /**
     * @return man026WeekLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026WeekLabel() {
        return man026WeekLabel__;
    }

    /**
     * @param man026WeekLabel 設定する man026WeekLabel__。
     */
    public void setMan026WeekLabel(ArrayList < LabelValueBean > man026WeekLabel) {
        this.man026WeekLabel__ = man026WeekLabel;
    }

    /**
     * @return man026DayOfWeekLabel__ を戻します。
     */
    public ArrayList < LabelValueBean > getMan026DayOfWeekLabel() {
        return man026DayOfWeekLabel__;
    }

    /**
     * @param man026DayOfWeekLabel 設定する man026DayOfWeekLabel__。
     */
    public void setMan026DayOfWeekLabel(
            ArrayList < LabelValueBean > man026DayOfWeekLabel) {
        this.man026DayOfWeekLabel__ = man026DayOfWeekLabel;
    }

    /**
     * @return man026HltName__ を戻します。
     */
    public String getMan026HltName() {
        return man026HltName__;
    }

    /**
     * @param man026HltName 設定する man026HltName__。
     */
    public void setMan026HltName(String man026HltName) {
        this.man026HltName__ = man026HltName;
    }


    /**
     * @return man026FuriFlg__ を戻します。
     */
    public int getMan026FuriFlg() {
        return man026FuriFlg__;
    }

    /**
     * @param man026FuriFlg 設定する man026FuriFlg__。
     */
    public void setMan026FuriFlg(int man026FuriFlg) {
        this.man026FuriFlg__ = man026FuriFlg;
    }
}