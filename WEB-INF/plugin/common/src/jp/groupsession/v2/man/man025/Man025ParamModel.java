package jp.groupsession.v2.man.man025;

import java.util.ArrayList;

import jp.groupsession.v2.man.man023.Man023HolidayTemplateModel;
import jp.groupsession.v2.man.man023.Man023ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 テンプレート追加/通常画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man025ParamModel extends Man023ParamModel {
    /** 処理モード */
    private String processMode__ = null;

    /** 休日テンプレートSID */
    private int editHltSid__ = -1;
    /** 年度 */
    private String man020DspYear__ = null;

    /** 日付　月 */
    private int man025HltMonth__ = -1;
    /** 日付　日 */
    private int man025HltDay__ = -1;
    /** 入力休日名 */
    private String man025HltName__ = null;

    /** 休日テンプレート全選択 */
    private int man023CheckAll__ = 0;

    /** 自動振替フラグ */
    private int man025FuriFlg__ = 1;

    /** 月リスト */
    private ArrayList < LabelValueBean > man025MonthLabel__ = null;
    /** 日リスト */
    private ArrayList < LabelValueBean > man025DayLabel__ = null;

    /** 削除対象休日 */
    private String[] holDate__ = null;
    /** 修正対象休日 */
    private String editHolDate__ = null;

    /** 休日テンプレート一覧リストデータ */
    private Man023HolidayTemplateModel[] man023TemplateList__ = null;


    /**
     * @return processMode__ を戻します。
     */
    public String getProcessMode() {
        return processMode__;
    }

    /**
     * @param processMode 設定する processMode__。
     */
    public void setProcessMode(String processMode) {
        processMode__ = processMode;
    }

    /**
     * @return editHolDate を戻します。
     */
    public String getEditHolDate() {
        return editHolDate__;
    }

    /**
     * @param editHolDate 設定する editHolDate。
     */
    public void setEditHolDate(String editHolDate) {
        editHolDate__ = editHolDate;
    }

    /**
     * @return holDate を戻します。
     */
    public String[] getHolDate() {
        return holDate__;
    }

    /**
     * @param holDate 設定する holDate。
     */
    public void setHolDate(String[] holDate) {
        holDate__ = holDate;
    }

    /**
     * @return man020DspYear を戻します。
     */
    public String getMan020DspYear() {
        return man020DspYear__;
    }

    /**
     * @param man020DspYear 設定する man020DspYear。
     */
    public void setMan020DspYear(String man020DspYear) {
        man020DspYear__ = man020DspYear;
    }

    /**
     * @return man023TemplateList__ を戻します。
     */
    public Man023HolidayTemplateModel[] getMan023TemplateList() {
        return man023TemplateList__;
    }

    /**
     * @param man023TemplateList 設定する man023TemplateList__。
     */
    public void setMan023TemplateList(Man023HolidayTemplateModel[] man023TemplateList) {
        this.man023TemplateList__ = man023TemplateList;
    }

    /**
     * @return editHltSid__ を戻します。
     */
    public int getEditHltSid() {
        return editHltSid__;
    }

    /**
     * @param editHltSid 設定する editHltSid__。
     */
    public void setEditHltSid(int editHltSid) {
        this.editHltSid__ = editHltSid;
    }


    /**
     * @return man025HltName__ を戻します。
     */
    public String getMan025HltName() {
        return man025HltName__;
    }

    /**
     * @param man025HltName 設定する man025HltName__。
     */
    public void setMan025HltName(String man025HltName) {
        this.man025HltName__ = man025HltName;
    }

    /**
     * @return man025FuriFlg__ を戻します。
     */
    public int getMan025FuriFlg() {
        return man025FuriFlg__;
    }

    /**
     * @param man025FuriFlg 設定する man025FuriFlg__。
     */
    public void setMan025FuriFlg(int man025FuriFlg) {
        this.man025FuriFlg__ = man025FuriFlg;
    }

    /**
     * @return man025HltDay__ を戻します。
     */
    public int getMan025HltDay() {
        return man025HltDay__;
    }

    /**
     * @param man025HltDay 設定する man025HltDay__。
     */
    public void setMan025HltDay(int man025HltDay) {
        this.man025HltDay__ = man025HltDay;
    }

    /**
     * @return man025HltMonth__ を戻します。
     */
    public int getMan025HltMonth() {
        return man025HltMonth__;
    }

    /**
     * @param man025HltMonth 設定する man025HltMonth__。
     */
    public void setMan025HltMonth(int man025HltMonth) {
        this.man025HltMonth__ = man025HltMonth;
    }

    /**
     * @return man025DayLabel を戻します。
     */
    public ArrayList < LabelValueBean > getMan025DayLabel() {
        return man025DayLabel__;
    }
    /**
     * @param man025DayLabel 設定する man025DayLabel。
     */
    public void setMan025DayLabel(ArrayList < LabelValueBean > man025DayLabel) {
        man025DayLabel__ = man025DayLabel;
    }
    /**
     * @return man025MonthLabel を戻します。
     */
    public ArrayList < LabelValueBean > getMan025MonthLabel() {
        return man025MonthLabel__;
    }
    /**
     * @param man025MonthLabel 設定する man025MonthLabel。
     */
    public void setMan025MonthLabel(ArrayList < LabelValueBean > man025MonthLabel) {
        man025MonthLabel__ = man025MonthLabel;
    }

    /**
     * @return man023CheckAll__ を戻します。
     */
    public int getMan023CheckAll() {
        return man023CheckAll__;
    }

    /**
     * @param man023CheckAll 設定する man023CheckAll__。
     */
    public void setMan023CheckAll(int man023CheckAll) {
        this.man023CheckAll__ = man023CheckAll;
    }
}