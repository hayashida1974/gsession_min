package jp.groupsession.v2.man.man390;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴統計情報画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man390ParamModel extends AbstractParamModel {

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 使用可能フラグ WEBメール */
    private boolean man390CtrlFlgWml__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean man390CtrlFlgSml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean man390CtrlFlgCir__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean man390CtrlFlgFil__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean man390CtrlFlgBbs__ = false;
    /** 使用可能フラグ チャット */
    private boolean man390CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int man390DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> man390DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> man390DspMonthLabel__;
    /** 週指定Frコンボ */
    private List<LabelValueBean> man390DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> man390DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String man390DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String man390DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String man390DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String man390DateMonthlyToMonth__;
    /** 週別 日付範囲 開始 */
    private String man390DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String man390DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String man390DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String man390DateDailyToStr__;
    /** 表示件数 */
    private int man390DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> man390DspNumLabel__;

    /** 現在ページ */
    private int man390NowPage__ = 1;
    /** 表示ページ（上） */
    private int man390DspPage1__;
    /** 表示ページ（下） */
    private int man390DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> man390PageLabel__;
    /** 現在のグラフの項目 */
    private String man390GraphItem__;

    /** 集計データ一覧 */
    private List<Man390DspModel> man390LogCountList__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** ユーザ数 */
    private String allUserNum__;
    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（ログイン率） */
    private String jsonLoginRate__;
    /** ログイン回数の合計 */
    private String man390SumLoginNum__;
    /** ログイン回数の平均 */
    private String man390AveLoginNum__;
    /** ログインユーザ数の合計 */
    private String man390SumLoginData__;
    /** ログインユーザ数の平均 */
    private String man390AveLoginData__;
    /** ログイン率の平均 */
    private String man390AveLoginRate__;

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <p>man390CtrlFlgWml を取得します。
     * @return man390CtrlFlgWml
     */
    public boolean isMan390CtrlFlgWml() {
        return man390CtrlFlgWml__;
    }
    /**
     * <p>man390CtrlFlgWml をセットします。
     * @param man390CtrlFlgWml man390CtrlFlgWml
     */
    public void setMan390CtrlFlgWml(boolean man390CtrlFlgWml) {
        man390CtrlFlgWml__ = man390CtrlFlgWml;
    }
    /**
     * <p>man390CtrlFlgSml を取得します。
     * @return man390CtrlFlgSml
     */
    public boolean isMan390CtrlFlgSml() {
        return man390CtrlFlgSml__;
    }
    /**
     * <p>man390CtrlFlgSml をセットします。
     * @param man390CtrlFlgSml man390CtrlFlgSml
     */
    public void setMan390CtrlFlgSml(boolean man390CtrlFlgSml) {
        man390CtrlFlgSml__ = man390CtrlFlgSml;
    }
    /**
     * <p>man390CtrlFlgCir を取得します。
     * @return man390CtrlFlgCir
     */
    public boolean isMan390CtrlFlgCir() {
        return man390CtrlFlgCir__;
    }
    /**
     * <p>man390CtrlFlgCir をセットします。
     * @param man390CtrlFlgCir man390CtrlFlgCir
     */
    public void setMan390CtrlFlgCir(boolean man390CtrlFlgCir) {
        man390CtrlFlgCir__ = man390CtrlFlgCir;
    }
    /**
     * <p>man390CtrlFlgFil を取得します。
     * @return man390CtrlFlgFil
     */
    public boolean isMan390CtrlFlgFil() {
        return man390CtrlFlgFil__;
    }
    /**
     * <p>man390CtrlFlgFil をセットします。
     * @param man390CtrlFlgFil man390CtrlFlgFil
     */
    public void setMan390CtrlFlgFil(boolean man390CtrlFlgFil) {
        man390CtrlFlgFil__ = man390CtrlFlgFil;
    }
    /**
     * <p>man390CtrlFlgBbs を取得します。
     * @return man390CtrlFlgBbs
     */
    public boolean isMan390CtrlFlgBbs() {
        return man390CtrlFlgBbs__;
    }
    /**
     * <p>man390CtrlFlgBbs をセットします。
     * @param man390CtrlFlgBbs man390CtrlFlgBbs
     */
    public void setMan390CtrlFlgBbs(boolean man390CtrlFlgBbs) {
        man390CtrlFlgBbs__ = man390CtrlFlgBbs;
    }
    /**
     * <p>man390DateUnit を取得します。
     * @return man390DateUnit
     */
    public int getMan390DateUnit() {
        return man390DateUnit__;
    }
    /**
     * <p>man390DateUnit をセットします。
     * @param man390DateUnit man390DateUnit
     */
    public void setMan390DateUnit(int man390DateUnit) {
        man390DateUnit__ = man390DateUnit;
    }
    /**
     * <p>man390DspYearLabel を取得します。
     * @return man390DspYearLabel
     */
    public List<LabelValueBean> getMan390DspYearLabel() {
        return man390DspYearLabel__;
    }
    /**
     * <p>man390DspYearLabel をセットします。
     * @param man390DspYearLabel man390DspYearLabel
     */
    public void setMan390DspYearLabel(List<LabelValueBean> man390DspYearLabel) {
        man390DspYearLabel__ = man390DspYearLabel;
    }
    /**
     * <p>man390DspMonthLabel を取得します。
     * @return man390DspMonthLabel
     */
    public List<LabelValueBean> getMan390DspMonthLabel() {
        return man390DspMonthLabel__;
    }
    /**
     * <p>man390DspMonthLabel をセットします。
     * @param man390DspMonthLabel man390DspMonthLabel
     */
    public void setMan390DspMonthLabel(List<LabelValueBean> man390DspMonthLabel) {
        man390DspMonthLabel__ = man390DspMonthLabel;
    }
    /**
     * <p>man390DspWeekFrLabel を取得します。
     * @return man390DspWeekFrLabel
     */
    public List<LabelValueBean> getMan390DspWeekFrLabel() {
        return man390DspWeekFrLabel__;
    }
    /**
     * <p>man390DspWeekFrLabel をセットします。
     * @param man390DspWeekFrLabel man390DspWeekFrLabel
     */
    public void setMan390DspWeekFrLabel(List<LabelValueBean> man390DspWeekFrLabel) {
        man390DspWeekFrLabel__ = man390DspWeekFrLabel;
    }
    /**
     * <p>man390DspWeekToLabel を取得します。
     * @return man390DspWeekToLabel
     */
    public List<LabelValueBean> getMan390DspWeekToLabel() {
        return man390DspWeekToLabel__;
    }
    /**
     * <p>man390DspWeekToLabel をセットします。
     * @param man390DspWeekToLabel man390DspWeekToLabel
     */
    public void setMan390DspWeekToLabel(List<LabelValueBean> man390DspWeekToLabel) {
        man390DspWeekToLabel__ = man390DspWeekToLabel;
    }
    /**
     * <p>man390DateMonthlyFrYear を取得します。
     * @return man390DateMonthlyFrYear
     */
    public String getMan390DateMonthlyFrYear() {
        return man390DateMonthlyFrYear__;
    }
    /**
     * <p>man390DateMonthlyFrYear をセットします。
     * @param man390DateMonthlyFrYear man390DateMonthlyFrYear
     */
    public void setMan390DateMonthlyFrYear(String man390DateMonthlyFrYear) {
        man390DateMonthlyFrYear__ = man390DateMonthlyFrYear;
    }
    /**
     * <p>man390DateMonthlyFrMonth を取得します。
     * @return man390DateMonthlyFrMonth
     */
    public String getMan390DateMonthlyFrMonth() {
        return man390DateMonthlyFrMonth__;
    }
    /**
     * <p>man390DateMonthlyFrMonth をセットします。
     * @param man390DateMonthlyFrMonth man390DateMonthlyFrMonth
     */
    public void setMan390DateMonthlyFrMonth(String man390DateMonthlyFrMonth) {
        man390DateMonthlyFrMonth__ = man390DateMonthlyFrMonth;
    }
    /**
     * <p>man390DateMonthlyToYear を取得します。
     * @return man390DateMonthlyToYear
     */
    public String getMan390DateMonthlyToYear() {
        return man390DateMonthlyToYear__;
    }
    /**
     * <p>man390DateMonthlyToYear をセットします。
     * @param man390DateMonthlyToYear man390DateMonthlyToYear
     */
    public void setMan390DateMonthlyToYear(String man390DateMonthlyToYear) {
        man390DateMonthlyToYear__ = man390DateMonthlyToYear;
    }
    /**
     * <p>man390DateMonthlyToMonth を取得します。
     * @return man390DateMonthlyToMonth
     */
    public String getMan390DateMonthlyToMonth() {
        return man390DateMonthlyToMonth__;
    }
    /**
     * <p>man390DateMonthlyToMonth をセットします。
     * @param man390DateMonthlyToMonth man390DateMonthlyToMonth
     */
    public void setMan390DateMonthlyToMonth(String man390DateMonthlyToMonth) {
        man390DateMonthlyToMonth__ = man390DateMonthlyToMonth;
    }
    /**
     * <p>man390DateWeeklyFrStr を取得します。
     * @return man390DateWeeklyFrStr
     */
    public String getMan390DateWeeklyFrStr() {
        return man390DateWeeklyFrStr__;
    }
    /**
     * <p>man390DateWeeklyFrStr をセットします。
     * @param man390DateWeeklyFrStr man390DateWeeklyFrStr
     */
    public void setMan390DateWeeklyFrStr(String man390DateWeeklyFrStr) {
        man390DateWeeklyFrStr__ = man390DateWeeklyFrStr;
    }
    /**
     * <p>man390DateWeeklyToStr を取得します。
     * @return man390DateWeeklyToStr
     */
    public String getMan390DateWeeklyToStr() {
        return man390DateWeeklyToStr__;
    }
    /**
     * <p>man390DateWeeklyToStr をセットします。
     * @param man390DateWeeklyToStr man390DateWeeklyToStr
     */
    public void setMan390DateWeeklyToStr(String man390DateWeeklyToStr) {
        man390DateWeeklyToStr__ = man390DateWeeklyToStr;
    }
    /**
     * <p>man390DateDailyFrStr を取得します。
     * @return man390DateDailyFrStr
     */
    public String getMan390DateDailyFrStr() {
        return man390DateDailyFrStr__;
    }
    /**
     * <p>man390DateDailyFrStr をセットします。
     * @param man390DateDailyFrStr man390DateDailyFrStr
     */
    public void setMan390DateDailyFrStr(String man390DateDailyFrStr) {
        man390DateDailyFrStr__ = man390DateDailyFrStr;
    }
    /**
     * <p>man390DateDailyToStr を取得します。
     * @return man390DateDailyToStr
     */
    public String getMan390DateDailyToStr() {
        return man390DateDailyToStr__;
    }
    /**
     * <p>man390DateDailyToStr をセットします。
     * @param man390DateDailyToStr man390DateDailyToStr
     */
    public void setMan390DateDailyToStr(String man390DateDailyToStr) {
        man390DateDailyToStr__ = man390DateDailyToStr;
    }
    /**
     * <p>man390DspNum を取得します。
     * @return man390DspNum
     */
    public int getMan390DspNum() {
        return man390DspNum__;
    }
    /**
     * <p>man390DspNum をセットします。
     * @param man390DspNum man390DspNum
     */
    public void setMan390DspNum(int man390DspNum) {
        man390DspNum__ = man390DspNum;
    }
    /**
     * <p>man390DspNumLabel を取得します。
     * @return man390DspNumLabel
     */
    public List<LabelValueBean> getMan390DspNumLabel() {
        return man390DspNumLabel__;
    }
    /**
     * <p>man390DspNumLabel をセットします。
     * @param man390DspNumLabel man390DspNumLabel
     */
    public void setMan390DspNumLabel(List<LabelValueBean> man390DspNumLabel) {
        man390DspNumLabel__ = man390DspNumLabel;
    }
    /**
     * <p>man390NowPage を取得します。
     * @return man390NowPage
     */
    public int getMan390NowPage() {
        return man390NowPage__;
    }
    /**
     * <p>man390NowPage をセットします。
     * @param man390NowPage man390NowPage
     */
    public void setMan390NowPage(int man390NowPage) {
        man390NowPage__ = man390NowPage;
    }
    /**
     * <p>man390DspPage1 を取得します。
     * @return man390DspPage1
     */
    public int getMan390DspPage1() {
        return man390DspPage1__;
    }
    /**
     * <p>man390DspPage1 をセットします。
     * @param man390DspPage1 man390DspPage1
     */
    public void setMan390DspPage1(int man390DspPage1) {
        man390DspPage1__ = man390DspPage1;
    }
    /**
     * <p>man390DspPage2 を取得します。
     * @return man390DspPage2
     */
    public int getMan390DspPage2() {
        return man390DspPage2__;
    }
    /**
     * <p>man390DspPage2 をセットします。
     * @param man390DspPage2 man390DspPage2
     */
    public void setMan390DspPage2(int man390DspPage2) {
        man390DspPage2__ = man390DspPage2;
    }
    /**
     * <p>man390PageLabel を取得します。
     * @return man390PageLabel
     */
    public List<LabelValueBean> getMan390PageLabel() {
        return man390PageLabel__;
    }
    /**
     * <p>man390PageLabel をセットします。
     * @param man390PageLabel man390PageLabel
     */
    public void setMan390PageLabel(List<LabelValueBean> man390PageLabel) {
        man390PageLabel__ = man390PageLabel;
    }
    /**
     * <p>man390GraphItem を取得します。
     * @return man390GraphItem
     */
    public String getMan390GraphItem() {
        return man390GraphItem__;
    }
    /**
     * <p>man390GraphItem をセットします。
     * @param man390GraphItem man390GraphItem
     */
    public void setMan390GraphItem(String man390GraphItem) {
        man390GraphItem__ = man390GraphItem;
    }
    /**
     * <p>man390LogCountList を取得します。
     * @return man390LogCountList
     */
    public List<Man390DspModel> getMan390LogCountList() {
        return man390LogCountList__;
    }
    /**
     * <p>man390LogCountList をセットします。
     * @param man390LogCountList man390LogCountList
     */
    public void setMan390LogCountList(List<Man390DspModel> man390LogCountList) {
        man390LogCountList__ = man390LogCountList;
    }
    /**
     * <p>logCountBack を取得します。
     * @return logCountBack
     */
    public String getLogCountBack() {
        return logCountBack__;
    }
    /**
     * <p>logCountBack をセットします。
     * @param logCountBack logCountBack
     */
    public void setLogCountBack(String logCountBack) {
        logCountBack__ = logCountBack;
    }
    /**
     * <p>allUserNum を取得します。
     * @return allUserNum
     */
    public String getAllUserNum() {
        return allUserNum__;
    }
    /**
     * <p>allUserNum をセットします。
     * @param allUserNum allUserNum
     */
    public void setAllUserNum(String allUserNum) {
        allUserNum__ = allUserNum;
    }
    /**
     * <p>jsonDateData を取得します。
     * @return jsonDateData
     */
    public String getJsonDateData() {
        return jsonDateData__;
    }
    /**
     * <p>jsonDateData をセットします。
     * @param jsonDateData jsonDateData
     */
    public void setJsonDateData(String jsonDateData) {
        jsonDateData__ = jsonDateData;
    }
    /**
     * <p>jsonLoginRate を取得します。
     * @return jsonLoginRate
     */
    public String getJsonLoginRate() {
        return jsonLoginRate__;
    }
    /**
     * <p>jsonLoginRate をセットします。
     * @param jsonLoginRate jsonLoginRate
     */
    public void setJsonLoginRate(String jsonLoginRate) {
        jsonLoginRate__ = jsonLoginRate;
    }
    /**
     * <p>man390SumLoginNum を取得します。
     * @return man390SumLoginNum
     */
    public String getMan390SumLoginNum() {
        return man390SumLoginNum__;
    }
    /**
     * <p>man390SumLoginNum をセットします。
     * @param man390SumLoginNum man390SumLoginNum
     */
    public void setMan390SumLoginNum(String man390SumLoginNum) {
        man390SumLoginNum__ = man390SumLoginNum;
    }
    /**
     * <p>man390AveLoginNum を取得します。
     * @return man390AveLoginNum
     */
    public String getMan390AveLoginNum() {
        return man390AveLoginNum__;
    }
    /**
     * <p>man390AveLoginNum をセットします。
     * @param man390AveLoginNum man390AveLoginNum
     */
    public void setMan390AveLoginNum(String man390AveLoginNum) {
        man390AveLoginNum__ = man390AveLoginNum;
    }
    /**
     * <p>man390SumLoginData を取得します。
     * @return man390SumLoginData
     */
    public String getMan390SumLoginData() {
        return man390SumLoginData__;
    }
    /**
     * <p>man390SumLoginData をセットします。
     * @param man390SumLoginData man390SumLoginData
     */
    public void setMan390SumLoginData(String man390SumLoginData) {
        man390SumLoginData__ = man390SumLoginData;
    }
    /**
     * <p>man390AveLoginData を取得します。
     * @return man390AveLoginData
     */
    public String getMan390AveLoginData() {
        return man390AveLoginData__;
    }
    /**
     * <p>man390AveLoginData をセットします。
     * @param man390AveLoginData man390AveLoginData
     */
    public void setMan390AveLoginData(String man390AveLoginData) {
        man390AveLoginData__ = man390AveLoginData;
    }
    /**
     * <p>man390AveLoginRate を取得します。
     * @return man390AveLoginRate
     */
    public String getMan390AveLoginRate() {
        return man390AveLoginRate__;
    }
    /**
     * <p>man390AveLoginRate をセットします。
     * @param man390AveLoginRate man390AveLoginRate
     */
    public void setMan390AveLoginRate(String man390AveLoginRate) {
        man390AveLoginRate__ = man390AveLoginRate;
    }
    /**
     * <p>man390CtrlFlgCht を取得します。
     * @return man390CtrlFlgCht
     * @see jp.groupsession.v2.man.man390.Man390ParamModel#man390CtrlFlgCht__
     */
    public boolean isMan390CtrlFlgCht() {
        return man390CtrlFlgCht__;
    }
    /**
     * <p>man390CtrlFlgCht をセットします。
     * @param man390CtrlFlgCht man390CtrlFlgCht
     * @see jp.groupsession.v2.man.man390.Man390ParamModel#man390CtrlFlgCht__
     */
    public void setMan390CtrlFlgCht(boolean man390CtrlFlgCht) {
        man390CtrlFlgCht__ = man390CtrlFlgCht;
    }
}
