package jp.groupsession.v2.sml.sml370;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100ParamModel;

/**
 * <br>[機  能] ショートメール統計情報画面のパラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sml370ParamModel extends Sml100ParamModel {

    /** メイン管理者権限フラグ */
    private boolean sml370GsAdminFlg__ = false;
    /** 使用可能フラグ WEBメール */
    private boolean sml370CtrlFlgWml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean sml370CtrlFlgCir__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean sml370CtrlFlgFil__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean sml370CtrlFlgBbs__ = false;
    /** 使用可能フラグ チャット */
    private boolean sml370CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int sml370DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> sml370DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> sml370DspMonthLabel__;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> sml370DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> sml370DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String sml370DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String sml370DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String sml370DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String sml370DateMonthlyToMonth__;

    /** 初期フラグ */
    private int sml370InitFlg__;

    /** 週別 日付範囲 開始 */
    private String sml370DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String sml370DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String sml370DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String sml370DateDailyToStr__;
    /** 表示件数 */
    private int sml370DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> sml370DspNumLabel__;
    /** システムメール区分 (0:含める  1:除く） */
    private int sml370SysMailKbn__ = 0;

    /** 現在ページ */
    private int sml370NowPage__ = 1;
    /** 表示ページ（上） */
    private int sml370DspPage1__;
    /** 表示ページ（下） */
    private int sml370DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> sml370PageLabel__;
    /** 現在のグラフの項目 */
    private String sml370GraphItem__ = GSConstSmail.SML_LOG_GRAPH_JMAIL;

    /** 集計データ一覧 */
    private List<Sml370DspModel> sml370LogCountList__;
    /** 平均件数 受信メール */
    private String sml370AveJmailNum__;
    /** 平均件数 送信メール */
    private String sml370AveSmailNum__;
    /** 合計件数 受信メール */
    private String sml370SumJmailNum__;
    /** 合計件数 送信メール */
    private String sml370SumSmailNum__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（受信メール数） */
    private String jsonJmailData__;
    /** グラフデータ（送信メール数） */
    private String jsonSmailData__;

    /**
     * <p>sml370GsAdminFlg を取得します。
     * @return sml370GsAdminFlg
     */
    public boolean isSml370GsAdminFlg() {
        return sml370GsAdminFlg__;
    }
    /**
     * <p>sml370GsAdminFlg をセットします。
     * @param sml370GsAdminFlg sml370GsAdminFlg
     */
    public void setSml370GsAdminFlg(boolean sml370GsAdminFlg) {
        sml370GsAdminFlg__ = sml370GsAdminFlg;
    }
    /**
     * <p>sml370CtrlFlgWml を取得します。
     * @return sml370CtrlFlgWml
     */
    public boolean isSml370CtrlFlgWml() {
        return sml370CtrlFlgWml__;
    }
    /**
     * <p>sml370CtrlFlgWml をセットします。
     * @param sml370CtrlFlgWml sml370CtrlFlgWml
     */
    public void setSml370CtrlFlgWml(boolean sml370CtrlFlgWml) {
        sml370CtrlFlgWml__ = sml370CtrlFlgWml;
    }
    /**
     * <p>sml370CtrlFlgCir を取得します。
     * @return sml370CtrlFlgCir
     */
    public boolean isSml370CtrlFlgCir() {
        return sml370CtrlFlgCir__;
    }
    /**
     * <p>sml370CtrlFlgCir をセットします。
     * @param sml370CtrlFlgCir sml370CtrlFlgCir
     */
    public void setSml370CtrlFlgCir(boolean sml370CtrlFlgCir) {
        sml370CtrlFlgCir__ = sml370CtrlFlgCir;
    }
    /**
     * <p>sml370CtrlFlgFil を取得します。
     * @return sml370CtrlFlgFil
     */
    public boolean isSml370CtrlFlgFil() {
        return sml370CtrlFlgFil__;
    }
    /**
     * <p>sml370CtrlFlgFil をセットします。
     * @param sml370CtrlFlgFil sml370CtrlFlgFil
     */
    public void setSml370CtrlFlgFil(boolean sml370CtrlFlgFil) {
        sml370CtrlFlgFil__ = sml370CtrlFlgFil;
    }
    /**
     * <p>sml370CtrlFlgBbs を取得します。
     * @return sml370CtrlFlgBbs
     */
    public boolean isSml370CtrlFlgBbs() {
        return sml370CtrlFlgBbs__;
    }
    /**
     * <p>sml370CtrlFlgBbs をセットします。
     * @param sml370CtrlFlgBbs sml370CtrlFlgBbs
     */
    public void setSml370CtrlFlgBbs(boolean sml370CtrlFlgBbs) {
        sml370CtrlFlgBbs__ = sml370CtrlFlgBbs;
    }
    /**
     * <p>sml370DateUnit を取得します。
     * @return sml370DateUnit
     */
    public int getSml370DateUnit() {
        return sml370DateUnit__;
    }
    /**
     * <p>sml370DateUnit をセットします。
     * @param sml370DateUnit sml370DateUnit
     */
    public void setSml370DateUnit(int sml370DateUnit) {
        sml370DateUnit__ = sml370DateUnit;
    }
    /**
     * <p>sml370DspYearLabel を取得します。
     * @return sml370DspYearLabel
     */
    public List<LabelValueBean> getSml370DspYearLabel() {
        return sml370DspYearLabel__;
    }
    /**
     * <p>sml370DspYearLabel をセットします。
     * @param sml370DspYearLabel sml370DspYearLabel
     */
    public void setSml370DspYearLabel(List<LabelValueBean> sml370DspYearLabel) {
        sml370DspYearLabel__ = sml370DspYearLabel;
    }
    /**
     * <p>sml370DspMonthLabel を取得します。
     * @return sml370DspMonthLabel
     */
    public List<LabelValueBean> getSml370DspMonthLabel() {
        return sml370DspMonthLabel__;
    }
    /**
     * <p>sml370DspMonthLabel をセットします。
     * @param sml370DspMonthLabel sml370DspMonthLabel
     */
    public void setSml370DspMonthLabel(List<LabelValueBean> sml370DspMonthLabel) {
        sml370DspMonthLabel__ = sml370DspMonthLabel;
    }
    /**
     * <p>sml370DspWeekFrLabel を取得します。
     * @return sml370DspWeekFrLabel
     */
    public List<LabelValueBean> getSml370DspWeekFrLabel() {
        return sml370DspWeekFrLabel__;
    }
    /**
     * <p>sml370DspWeekFrLabel をセットします。
     * @param sml370DspWeekFrLabel sml370DspWeekFrLabel
     */
    public void setSml370DspWeekFrLabel(List<LabelValueBean> sml370DspWeekFrLabel) {
        sml370DspWeekFrLabel__ = sml370DspWeekFrLabel;
    }
    /**
     * <p>sml370DspWeekToLabel を取得します。
     * @return sml370DspWeekToLabel
     */
    public List<LabelValueBean> getSml370DspWeekToLabel() {
        return sml370DspWeekToLabel__;
    }
    /**
     * <p>sml370DspWeekToLabel をセットします。
     * @param sml370DspWeekToLabel sml370DspWeekToLabel
     */
    public void setSml370DspWeekToLabel(List<LabelValueBean> sml370DspWeekToLabel) {
        sml370DspWeekToLabel__ = sml370DspWeekToLabel;
    }
    /**
     * <p>sml370DateMonthlyFrYear を取得します。
     * @return sml370DateMonthlyFrYear
     */
    public String getSml370DateMonthlyFrYear() {
        return sml370DateMonthlyFrYear__;
    }
    /**
     * <p>sml370DateMonthlyFrYear をセットします。
     * @param sml370DateMonthlyFrYear sml370DateMonthlyFrYear
     */
    public void setSml370DateMonthlyFrYear(String sml370DateMonthlyFrYear) {
        sml370DateMonthlyFrYear__ = sml370DateMonthlyFrYear;
    }
    /**
     * <p>sml370DateMonthlyFrMonth を取得します。
     * @return sml370DateMonthlyFrMonth
     */
    public String getSml370DateMonthlyFrMonth() {
        return sml370DateMonthlyFrMonth__;
    }
    /**
     * <p>sml370DateMonthlyFrMonth をセットします。
     * @param sml370DateMonthlyFrMonth sml370DateMonthlyFrMonth
     */
    public void setSml370DateMonthlyFrMonth(String sml370DateMonthlyFrMonth) {
        sml370DateMonthlyFrMonth__ = sml370DateMonthlyFrMonth;
    }
    /**
     * <p>sml370DateMonthlyToYear を取得します。
     * @return sml370DateMonthlyToYear
     */
    public String getSml370DateMonthlyToYear() {
        return sml370DateMonthlyToYear__;
    }
    /**
     * <p>sml370DateMonthlyToYear をセットします。
     * @param sml370DateMonthlyToYear sml370DateMonthlyToYear
     */
    public void setSml370DateMonthlyToYear(String sml370DateMonthlyToYear) {
        sml370DateMonthlyToYear__ = sml370DateMonthlyToYear;
    }
    /**
     * <p>sml370DateMonthlyToMonth を取得します。
     * @return sml370DateMonthlyToMonth
     */
    public String getSml370DateMonthlyToMonth() {
        return sml370DateMonthlyToMonth__;
    }
    /**
     * <p>sml370DateMonthlyToMonth をセットします。
     * @param sml370DateMonthlyToMonth sml370DateMonthlyToMonth
     */
    public void setSml370DateMonthlyToMonth(String sml370DateMonthlyToMonth) {
        sml370DateMonthlyToMonth__ = sml370DateMonthlyToMonth;
    }

    /**
     * <p>sml370DspNum を取得します。
     * @return sml370DspNum
     */
    public int getSml370DspNum() {
        return sml370DspNum__;
    }
    /**
     * <p>sml370DspNum をセットします。
     * @param sml370DspNum sml370DspNum
     */
    public void setSml370DspNum(int sml370DspNum) {
        sml370DspNum__ = sml370DspNum;
    }
    /**
     * <p>sml370DspNumLabel を取得します。
     * @return sml370DspNumLabel
     */
    public List<LabelValueBean> getSml370DspNumLabel() {
        return sml370DspNumLabel__;
    }
    /**
     * <p>sml370DspNumLabel をセットします。
     * @param sml370DspNumLabel sml370DspNumLabel
     */
    public void setSml370DspNumLabel(List<LabelValueBean> sml370DspNumLabel) {
        sml370DspNumLabel__ = sml370DspNumLabel;
    }
    /**
     * <p>sml370SysMailKbn を取得します。
     * @return sml370SysMailKbn
     */
    public int getSml370SysMailKbn() {
        return sml370SysMailKbn__;
    }
    /**
     * <p>sml370SysMailKbn をセットします。
     * @param sml370SysMailKbn sml370SysMailKbn
     */
    public void setSml370SysMailKbn(int sml370SysMailKbn) {
        sml370SysMailKbn__ = sml370SysMailKbn;
    }
    /**
     * <p>sml370NowPage を取得します。
     * @return sml370NowPage
     */
    public int getSml370NowPage() {
        return sml370NowPage__;
    }
    /**
     * <p>sml370NowPage をセットします。
     * @param sml370NowPage sml370NowPage
     */
    public void setSml370NowPage(int sml370NowPage) {
        sml370NowPage__ = sml370NowPage;
    }
    /**
     * <p>sml370DspPage1 を取得します。
     * @return sml370DspPage1
     */
    public int getSml370DspPage1() {
        return sml370DspPage1__;
    }
    /**
     * <p>sml370DspPage1 をセットします。
     * @param sml370DspPage1 sml370DspPage1
     */
    public void setSml370DspPage1(int sml370DspPage1) {
        sml370DspPage1__ = sml370DspPage1;
    }
    /**
     * <p>sml370DspPage2 を取得します。
     * @return sml370DspPage2
     */
    public int getSml370DspPage2() {
        return sml370DspPage2__;
    }
    /**
     * <p>sml370DspPage2 をセットします。
     * @param sml370DspPage2 sml370DspPage2
     */
    public void setSml370DspPage2(int sml370DspPage2) {
        sml370DspPage2__ = sml370DspPage2;
    }
    /**
     * <p>sml370PageLabel を取得します。
     * @return sml370PageLabel
     */
    public List<LabelValueBean> getSml370PageLabel() {
        return sml370PageLabel__;
    }
    /**
     * <p>sml370PageLabel をセットします。
     * @param sml370PageLabel sml370PageLabel
     */
    public void setSml370PageLabel(List<LabelValueBean> sml370PageLabel) {
        sml370PageLabel__ = sml370PageLabel;
    }
    /**
     * <p>sml370GraphItem を取得します。
     * @return sml370GraphItem
     */
    public String getSml370GraphItem() {
        return sml370GraphItem__;
    }
    /**
     * <p>sml370GraphItem をセットします。
     * @param sml370GraphItem sml370GraphItem
     */
    public void setSml370GraphItem(String sml370GraphItem) {
        sml370GraphItem__ = sml370GraphItem;
    }
    /**
     * <p>sml370InitFlg を取得します。
     * @return sml370InitFlg
     */
    public int getSml370InitFlg() {
        return sml370InitFlg__;
    }
    /**
     * <p>sml370InitFlg をセットします。
     * @param sml370InitFlg sml370InitFlg
     */
    public void setSml370InitFlg(int sml370InitFlg) {
        sml370InitFlg__ = sml370InitFlg;
    }

    /**
     * <p>sml370DateWeeklyFrStr を取得します。
     * @return sml370DateWeeklyFrStr
     */
    public String getSml370DateWeeklyFrStr() {
        return sml370DateWeeklyFrStr__;
    }
    /**
     * <p>sml370DateWeeklyFrStr をセットします。
     * @param sml370DateWeeklyFrStr sml370DateWeeklyFrStr
     */
    public void setSml370DateWeeklyFrStr(String sml370DateWeeklyFrStr) {
        sml370DateWeeklyFrStr__ = sml370DateWeeklyFrStr;
    }
    /**
     * <p>sml370DateWeeklyToStr を取得します。
     * @return sml370DateWeeklyToStr
     */
    public String getSml370DateWeeklyToStr() {
        return sml370DateWeeklyToStr__;
    }
    /**
     * <p>sml370DateWeeklyToStr をセットします。
     * @param sml370DateWeeklyToStr sml370DateWeeklyToStr
     */
    public void setSml370DateWeeklyToStr(String sml370DateWeeklyToStr) {
        sml370DateWeeklyToStr__ = sml370DateWeeklyToStr;
    }
    /**
     * <p>sml370DateDailyFrStr を取得します。
     * @return sml370DateDailyFrStr
     */
    public String getSml370DateDailyFrStr() {
        return sml370DateDailyFrStr__;
    }
    /**
     * <p>sml370DateDailyFrStr をセットします。
     * @param sml370DateDailyFrStr sml370DateDailyFrStr
     */
    public void setSml370DateDailyFrStr(String sml370DateDailyFrStr) {
        sml370DateDailyFrStr__ = sml370DateDailyFrStr;
    }
    /**
     * <p>sml370DateDailyToStr を取得します。
     * @return sml370DateDailyToStr
     */
    public String getSml370DateDailyToStr() {
        return sml370DateDailyToStr__;
    }
    /**
     * <p>sml370DateDailyToStr をセットします。
     * @param sml370DateDailyToStr sml370DateDailyToStr
     */
    public void setSml370DateDailyToStr(String sml370DateDailyToStr) {
        sml370DateDailyToStr__ = sml370DateDailyToStr;
    }
    /**
     * <p>sml370LogCountList を取得します。
     * @return sml370LogCountList
     */
    public List<Sml370DspModel> getSml370LogCountList() {
        return sml370LogCountList__;
    }
    /**
     * <p>sml370LogCountList をセットします。
     * @param sml370LogCountList sml370LogCountList
     */
    public void setSml370LogCountList(List<Sml370DspModel> sml370LogCountList) {
        sml370LogCountList__ = sml370LogCountList;
    }
    /**
     * @return sml370AveJmailNum
     */
    public String getSml370AveJmailNum() {
        return sml370AveJmailNum__;
    }
    /**
     * @param sml370AveJmailNum セットする sml370AveJmailNum
     */
    public void setSml370AveJmailNum(String sml370AveJmailNum) {
        this.sml370AveJmailNum__ = sml370AveJmailNum;
    }
    /**
     * @return sml370AveSmailNum
     */
    public String getSml370AveSmailNum() {
        return sml370AveSmailNum__;
    }
    /**
     * @param sml370AveSmailNum セットする sml370AveSmailNum
     */
    public void setSml370AveSmailNum(String sml370AveSmailNum) {
        this.sml370AveSmailNum__ = sml370AveSmailNum;
    }
    /**
     * @return sml370SumJmailNum
     */
    public String getSml370SumJmailNum() {
        return sml370SumJmailNum__;
    }
    /**
     * @param sml370SumJmailNum セットする sml370SumJmailNum
     */
    public void setSml370SumJmailNum(String sml370SumJmailNum) {
        this.sml370SumJmailNum__ = sml370SumJmailNum;
    }
    /**
     * @return sml370SumSmailNum
     */
    public String getSml370SumSmailNum() {
        return sml370SumSmailNum__;
    }
    /**
     * @param sml370SumSmailNum セットする sml370SumSmailNum
     */
    public void setSml370SumSmailNum(String sml370SumSmailNum) {
        this.sml370SumSmailNum__ = sml370SumSmailNum;
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
     * <p>jsonJmailData を取得します。
     * @return jsonJmailData
     */
    public String getJsonJmailData() {
        return jsonJmailData__;
    }
    /**
     * <p>jsonJmailData をセットします。
     * @param jsonJmailData jsonJmailData
     */
    public void setJsonJmailData(String jsonJmailData) {
        jsonJmailData__ = jsonJmailData;
    }
    /**
     * <p>jsonSmailData を取得します。
     * @return jsonSmailData
     */
    public String getJsonSmailData() {
        return jsonSmailData__;
    }
    /**
     * <p>jsonSmailData をセットします。
     * @param jsonSmailData jsonSmailData
     */
    public void setJsonSmailData(String jsonSmailData) {
        jsonSmailData__ = jsonSmailData;
    }
    /**
     * <p>sml370CtrlFlgCht を取得します。
     * @return sml370CtrlFlgCht
     * @see jp.groupsession.v2.sml.sml370.Sml370ParamModel#sml370CtrlFlgCht__
     */
    public boolean isSml370CtrlFlgCht() {
        return sml370CtrlFlgCht__;
    }
    /**
     * <p>sml370CtrlFlgCht をセットします。
     * @param sml370CtrlFlgCht sml370CtrlFlgCht
     * @see jp.groupsession.v2.sml.sml370.Sml370ParamModel#sml370CtrlFlgCht__
     */
    public void setSml370CtrlFlgCht(boolean sml370CtrlFlgCht) {
        sml370CtrlFlgCht__ = sml370CtrlFlgCht;
    }
}