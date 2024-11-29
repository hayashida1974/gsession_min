package jp.groupsession.v2.man.man290;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man320.Man320ParamModel;

/**
 * <br>[機  能] メイン インフォメーション登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290ParamModel extends Man320ParamModel {
    /** 週リスト */
    private ArrayList <LabelValueBean> man290WeekLabel__ = null;
    /** 日リスト(拡張部分) */
    private ArrayList <LabelValueBean> man290ExDayLabel__ = null;

    //入力項目
    /** 拡張区分 */
    private String man290ExtKbn__ = null;
    /** 表示判定 */
    private int man290elementKbn__ = 0;
    /** 曜日multibox */
    private String[] man290Dweek__ = null;
    /** 週 */
    private String man290Week__ = null;
    /** 日 */
    private String man290Day__ = null;

    /** 開始年 */
    private String man290FrYear__ = null;
    /** 開始月 */
    private String man290FrMonth__ = null;
    /** 開始日 */
    private String man290FrDay__ = null;
    /** 開始時 */
    private String man290FrHour__ = null;
    /** 開始分 */
    private String man290FrMin__ = null;

    /** 終了年 */
    private String man290ToYear__ = null;
    /** 終了月 */
    private String man290ToMonth__ = null;
    /** 終了日 */
    private String man290ToDay__ = null;
    /** 終了時 */
    private String man290ToHour__ = null;
    /** 終了分 */
    private String man290ToMin__ = null;
    
    /** 開始 年月日 */
    private String man290FrDate__ = null;
    /** 開始 時間 */
    private String man290FrTime__ = null;
    /** 終了 年月日 */
    private String man290ToDate__ = null;
    /** 終了 時間 */
    private String man290ToTime__ = null;
    /** 開始 年コンボ 現在から何年前まで設定可能か */
    private int man290YearRangeMin__ = 0;
    /** 終了 年コンボ 現在から何年後まで設定可能か */
    private int man290YearRangeMax__ = 0;

    /** メッセージ */
    private String man290Msg__ = null;
    /** 内容 */
    private String man290Value__ = null;
    /** 状態区分 */
    private String man290Jtkbn__ = null;

    /** 公開対象者SID */
    private String[] man290memberSid__ = new String[0];
    /** グループSID */
    private int man290groupSid__ = Integer.parseInt(GSConstMain.GROUP_COMBO_VALUE);
    /** 公開対象者SID UI */
    private UserGroupSelector man290memberSidUI__ = null;

    /** 添付ファイル */
    private String[] man290files__ = null;
    /** ファイルコンボ */
    private List <LabelValueBean> man290FileLabelList__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int webSearchUse__ = GSConst.PLUGIN_USE;

    /** 休日表示区分 0=そのまま表示 1=表示しない 2=前営業日 3=翌営業日 */
    private int man290HolKbn__ = 0;

    /** ヘルプモード */
    private String man290helpMode__ = null;

    /**
     * @return the man290FileLabelList
     */
    public List<LabelValueBean> getMan290FileLabelList() {
        return man290FileLabelList__;
    }

    /**
     * @param man290FileLabelList the man290FileLabelList to set
     */
    public void setMan290FileLabelList(List<LabelValueBean> man290FileLabelList) {
        man290FileLabelList__ = man290FileLabelList;
    }

    /**
     * @return the man290files
     */
    public String[] getMan290files() {
        return man290files__;
    }

    /**
     * @param man290files the man290files to set
     */
    public void setMan290files(String[] man290files) {
        man290files__ = man290files;
    }

    /**
     * @return the man290elementKbn
     */
    public int getMan290elementKbn() {
        return man290elementKbn__;
    }

    /**
     * @param man290elementKbn the man290elementKbn to set
     */
    public void setMan290elementKbn(int man290elementKbn) {
        man290elementKbn__ = man290elementKbn;
    }

    /**
     * @return the man290ExDayLabel
     */
    public ArrayList<LabelValueBean> getMan290ExDayLabel() {
        return man290ExDayLabel__;
    }

    /**
     * @param man290ExDayLabel the man290ExDayLabel to set
     */
    public void setMan290ExDayLabel(ArrayList<LabelValueBean> man290ExDayLabel) {
        man290ExDayLabel__ = man290ExDayLabel;
    }

    /**
     * @return the man290groupSid
     */
    public int getMan290groupSid() {
        return man290groupSid__;
    }

    /**
     * @param man290groupSid the man290groupSid to set
     */
    public void setMan290groupSid(int man290groupSid) {
        man290groupSid__ = man290groupSid;
    }

    /**
     * @return the man290WeekLabel
     */
    public ArrayList<LabelValueBean> getMan290WeekLabel() {
        return man290WeekLabel__;
    }

    /**
     * @param man290WeekLabel the man290WeekLabel to set
     */
    public void setMan290WeekLabel(ArrayList<LabelValueBean> man290WeekLabel) {
        man290WeekLabel__ = man290WeekLabel;
    }

    /**
     * @return the man290ExtKbn
     */
    public String getMan290ExtKbn() {
        return man290ExtKbn__;
    }

    /**
     * @param man290ExtKbn the man290ExtKbn to set
     */
    public void setMan290ExtKbn(String man290ExtKbn) {
        man290ExtKbn__ = man290ExtKbn;
    }

    /**
     * @return the man290Dweek
     */
    public String[] getMan290Dweek() {
        return man290Dweek__;
    }

    /**
     * @param man290Dweek the man290Dweek to set
     */
    public void setMan290Dweek(String[] man290Dweek) {
        man290Dweek__ = man290Dweek;
    }

    /**
     * @return the man290Week
     */
    public String getMan290Week() {
        return man290Week__;
    }

    /**
     * @param man290Week the man290Week to set
     */
    public void setMan290Week(String man290Week) {
        man290Week__ = man290Week;
    }

    /**
     * @return the man290Day
     */
    public String getMan290Day() {
        return man290Day__;
    }

    /**
     * @param man290Day the man290Day to set
     */
    public void setMan290Day(String man290Day) {
        man290Day__ = man290Day;
    }

    /**
     * @return the man290FrYear
     */
    public String getMan290FrYear() {
        return man290FrYear__;
    }

    /**
     * @param man290FrYear the man290FrYear to set
     */
    public void setMan290FrYear(String man290FrYear) {
        man290FrYear__ = man290FrYear;
    }

    /**
     * @return the man290FrMonth
     */
    public String getMan290FrMonth() {
        return man290FrMonth__;
    }

    /**
     * @param man290FrMonth the man290FrMonth to set
     */
    public void setMan290FrMonth(String man290FrMonth) {
        man290FrMonth__ = man290FrMonth;
    }

    /**
     * @return the man290FrDay
     */
    public String getMan290FrDay() {
        return man290FrDay__;
    }

    /**
     * @param man290FrDay the man290FrDay to set
     */
    public void setMan290FrDay(String man290FrDay) {
        man290FrDay__ = man290FrDay;
    }

    /**
     * @return the man290FrHour
     */
    public String getMan290FrHour() {
        return man290FrHour__;
    }

    /**
     * @param man290FrHour the man290FrHour to set
     */
    public void setMan290FrHour(String man290FrHour) {
        man290FrHour__ = man290FrHour;
    }

    /**
     * @return the man290FrMin
     */
    public String getMan290FrMin() {
        return man290FrMin__;
    }

    /**
     * @param man290FrMin the man290FrMin to set
     */
    public void setMan290FrMin(String man290FrMin) {
        man290FrMin__ = man290FrMin;
    }

    /**
     * @return the man290ToYear
     */
    public String getMan290ToYear() {
        return man290ToYear__;
    }

    /**
     * @param man290ToYear the man290ToYear to set
     */
    public void setMan290ToYear(String man290ToYear) {
        man290ToYear__ = man290ToYear;
    }

    /**
     * @return the man290ToMonth
     */
    public String getMan290ToMonth() {
        return man290ToMonth__;
    }

    /**
     * @param man290ToMonth the man290ToMonth to set
     */
    public void setMan290ToMonth(String man290ToMonth) {
        man290ToMonth__ = man290ToMonth;
    }

    /**
     * @return the man290ToDay
     */
    public String getMan290ToDay() {
        return man290ToDay__;
    }

    /**
     * @param man290ToDay the man290ToDay to set
     */
    public void setMan290ToDay(String man290ToDay) {
        man290ToDay__ = man290ToDay;
    }

    /**
     * @return the man290ToHour
     */
    public String getMan290ToHour() {
        return man290ToHour__;
    }

    /**
     * @param man290ToHour the man290ToHour to set
     */
    public void setMan290ToHour(String man290ToHour) {
        man290ToHour__ = man290ToHour;
    }

    /**
     * @return the man290ToMin
     */
    public String getMan290ToMin() {
        return man290ToMin__;
    }

    /**
     * @param man290ToMin the man290ToMin to set
     */
    public void setMan290ToMin(String man290ToMin) {
        man290ToMin__ = man290ToMin;
    }

    /**
     * <p>man290FrDate を取得します。
     * @return man290FrDate
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290FrDate__
     */
    public String getMan290FrDate() {
        return man290FrDate__;
    }

    /**
     * <p>man290FrDate をセットします。
     * @param man290FrDate man290FrDate
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290FrDate__
     */
    public void setMan290FrDate(String man290FrDate) {
        man290FrDate__ = man290FrDate;
    }

    /**
     * <p>man290FrTime を取得します。
     * @return man290FrTime
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290FrTime__
     */
    public String getMan290FrTime() {
        return man290FrTime__;
    }

    /**
     * <p>man290FrTime をセットします。
     * @param man290FrTime man290FrTime
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290FrTime__
     */
    public void setMan290FrTime(String man290FrTime) {
        man290FrTime__ = man290FrTime;
    }

    /**
     * <p>man290ToDate を取得します。
     * @return man290ToDate
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290ToDate__
     */
    public String getMan290ToDate() {
        return man290ToDate__;
    }

    /**
     * <p>man290ToDate をセットします。
     * @param man290ToDate man290ToDate
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290ToDate__
     */
    public void setMan290ToDate(String man290ToDate) {
        man290ToDate__ = man290ToDate;
    }

    /**
     * <p>man290ToTime を取得します。
     * @return man290ToTime
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290ToTime__
     */
    public String getMan290ToTime() {
        return man290ToTime__;
    }

    /**
     * <p>man290ToTime をセットします。
     * @param man290ToTime man290ToTime
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290ToTime__
     */
    public void setMan290ToTime(String man290ToTime) {
        man290ToTime__ = man290ToTime;
    }

    /**
     * <p>man290YearRangeMin を取得します。
     * @return man290YearRangeMin
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290YearRangeMin__
     */
    public int getMan290YearRangeMin() {
        return man290YearRangeMin__;
    }

    /**
     * <p>man290YearRangeMin をセットします。
     * @param man290YearRangeMin man290YearRangeMin
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290YearRangeMin__
     */
    public void setMan290YearRangeMin(int man290YearRangeMin) {
        man290YearRangeMin__ = man290YearRangeMin;
    }

    /**
     * <p>man290YearRangeMax を取得します。
     * @return man290YearRangeMax
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290YearRangeMax__
     */
    public int getMan290YearRangeMax() {
        return man290YearRangeMax__;
    }

    /**
     * <p>man290YearRangeMax をセットします。
     * @param man290YearRangeMax man290YearRangeMax
     * @see jp.groupsession.v2.man.man290.Man290ParamModel#man290YearRangeMax__
     */
    public void setMan290YearRangeMax(int man290YearRangeMax) {
        man290YearRangeMax__ = man290YearRangeMax;
    }

    /**
     * @return the man290Msg
     */
    public String getMan290Msg() {
        return man290Msg__;
    }

    /**
     * @param man290Msg the man290Msg to set
     */
    public void setMan290Msg(String man290Msg) {
        man290Msg__ = man290Msg;
    }

    /**
     * @return the man290Value
     */
    public String getMan290Value() {
        return man290Value__;
    }

    /**
     * @param man290Value the man290Value to set
     */
    public void setMan290Value(String man290Value) {
        man290Value__ = man290Value;
    }

    /**
     * @return the man290Jtkbn
     */
    public String getMan290Jtkbn() {
        return man290Jtkbn__;
    }

    /**
     * @param man290Jtkbn the man290Jtkbn to set
     */
    public void setMan290Jtkbn(String man290Jtkbn) {
        man290Jtkbn__ = man290Jtkbn;
    }

    /**
     * @return the man290memberSid
     */
    public String[] getMan290memberSid() {
        return man290memberSid__;
    }

    /**
     * @param man290memberSid the man290memberSid to set
     */
    public void setMan290memberSid(String[] man290memberSid) {
        man290memberSid__ = man290memberSid;
    }

    /**
     * <p>man290memberSidUI を取得します。
     * @return man290memberSidUI
     */
    public UserGroupSelector getMan290memberSidUI() {
        return man290memberSidUI__;
    }

    /**
     * <p>man290memberSidUI をセットします。
     * @param man290memberSidUI man290memberSidUI
     */
    public void setMan290memberSidUI(UserGroupSelector man290memberSidUI) {
        man290memberSidUI__ = man290memberSidUI;
    }

    /**
     * <p>webSearchUse を取得します。
     * @return webSearchUse
     */
    public int getWebSearchUse() {
        return webSearchUse__;
    }

    /**
     * <p>webSearchUse をセットします。
     * @param webSearchUse webSearchUse
     */
    public void setWebSearchUse(int webSearchUse) {
        webSearchUse__ = webSearchUse;
    }

    /**
     * <p>man290HolKbn を取得します。
     * @return man290HolKbn
     */
    public int getMan290HolKbn() {
        return man290HolKbn__;
    }

    /**
     * <p>man290HolKbn をセットします。
     * @param man290HolKbn man290HolKbn
     */
    public void setMan290HolKbn(int man290HolKbn) {
        man290HolKbn__ = man290HolKbn;
    }

    /**
     * <p>man290helpMode を取得します。
     * @return man290helpMode
     */
    public String getMan290helpMode() {
        return man290helpMode__;
    }

    /**
     * <p>man290helpMode をセットします。
     * @param man290helpMode man290helpMode
     */
    public void setMan290helpMode(String man290helpMode) {
        man290helpMode__ = man290helpMode;
    }
}