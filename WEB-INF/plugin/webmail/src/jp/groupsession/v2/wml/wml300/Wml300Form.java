package jp.groupsession.v2.wml.wml300;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.wml020.Wml020Form;

/**
 * <br>[機  能] WEBメール統計情報画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml300Form extends Wml020Form {

    /** メイン管理者権限フラグ */
    private boolean wml300GsAdminFlg__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean wml300CtrlFlgSml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean wml300CtrlFlgCir__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean wml300CtrlFlgFil__ = false;
    /** 使用可能フラグ 掲示板 */
    private boolean wml300CtrlFlgBbs__ = false;
    /** 使用可能フラグ チャット */
    private boolean wml300CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int wml300DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> wml300DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> wml300DspMonthLabel__;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> wml300DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> wml300DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String wml300DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String wml300DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String wml300DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String wml300DateMonthlyToMonth__;
    /** 週別 日付範囲 開始 */
    private String wml300DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String wml300DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String wml300DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String wml300DateDailyToStr__;
    /** 表示件数 */
    private int wml300DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> wml300DspNumLabel__;

    /** 現在ページ */
    private int wml300NowPage__ = 1;
    /** 表示ページ（上） */
    private int wml300DspPage1__;
    /** 表示ページ（下） */
    private int wml300DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> wml300PageLabel__;
    /** 現在のグラフの項目 */
    private String wml300GraphItem__ = GSConstWebmail.WML_LOG_GRAPH_JMAIL;

    /** 集計データ一覧 */
    private List<Wml300DspModel> wml300LogCountList__;
    /** 平均件数 受信メール */
    private String wml300AveJmailNum__;
    /** 平均件数 送信メール */
    private String wml300AveSmailNum__;
    /** 合計件数 受信メール */
    private String wml300SumJmailNum__;
    /** 合計件数 送信メール */
    private String wml300SumSmailNum__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（受信メール数） */
    private String jsonJmailData__;
    /** グラフデータ（送信メール数） */
    private String jsonSmailData__;

    /**
     * <p>wml300GsAdminFlg を取得します。
     * @return wml300GsAdminFlg
     */
    public boolean isWml300GsAdminFlg() {
        return wml300GsAdminFlg__;
    }

    /**
     * <p>wml300GsAdminFlg をセットします。
     * @param wml300GsAdminFlg wml300GsAdminFlg
     */
    public void setWml300GsAdminFlg(boolean wml300GsAdminFlg) {
        wml300GsAdminFlg__ = wml300GsAdminFlg;
    }

    /**
     * <p>wml300CtrlFlgSml を取得します。
     * @return wml300CtrlFlgSml
     */
    public boolean isWml300CtrlFlgSml() {
        return wml300CtrlFlgSml__;
    }

    /**
     * <p>wml300CtrlFlgSml をセットします。
     * @param wml300CtrlFlgSml wml300CtrlFlgSml
     */
    public void setWml300CtrlFlgSml(boolean wml300CtrlFlgSml) {
        wml300CtrlFlgSml__ = wml300CtrlFlgSml;
    }

    /**
     * <p>wml300CtrlFlgCir を取得します。
     * @return wml300CtrlFlgCir
     */
    public boolean isWml300CtrlFlgCir() {
        return wml300CtrlFlgCir__;
    }

    /**
     * <p>wml300CtrlFlgCir をセットします。
     * @param wml300CtrlFlgCir wml300CtrlFlgCir
     */
    public void setWml300CtrlFlgCir(boolean wml300CtrlFlgCir) {
        wml300CtrlFlgCir__ = wml300CtrlFlgCir;
    }

    /**
     * <p>wml300CtrlFlgFil を取得します。
     * @return wml300CtrlFlgFil
     */
    public boolean isWml300CtrlFlgFil() {
        return wml300CtrlFlgFil__;
    }

    /**
     * <p>wml300CtrlFlgFil をセットします。
     * @param wml300CtrlFlgFil wml300CtrlFlgFil
     */
    public void setWml300CtrlFlgFil(boolean wml300CtrlFlgFil) {
        wml300CtrlFlgFil__ = wml300CtrlFlgFil;
    }

    /**
     * <p>wml300CtrlFlgBbs を取得します。
     * @return wml300CtrlFlgBbs
     */
    public boolean isWml300CtrlFlgBbs() {
        return wml300CtrlFlgBbs__;
    }

    /**
     * <p>wml300CtrlFlgBbs をセットします。
     * @param wml300CtrlFlgBbs wml300CtrlFlgBbs
     */
    public void setWml300CtrlFlgBbs(boolean wml300CtrlFlgBbs) {
        wml300CtrlFlgBbs__ = wml300CtrlFlgBbs;
    }

    /**
     * <p>wml300DateUnit を取得します。
     * @return wml300DateUnit
     */
    public int getWml300DateUnit() {
        return wml300DateUnit__;
    }

    /**
     * <p>wml300DateUnit をセットします。
     * @param wml300DateUnit wml300DateUnit
     */
    public void setWml300DateUnit(int wml300DateUnit) {
        wml300DateUnit__ = wml300DateUnit;
    }

    /**
     * <p>wml300DspYearLabel を取得します。
     * @return wml300DspYearLabel
     */
    public List<LabelValueBean> getWml300DspYearLabel() {
        return wml300DspYearLabel__;
    }

    /**
     * <p>wml300DspYearLabel をセットします。
     * @param wml300DspYearLabel wml300DspYearLabel
     */
    public void setWml300DspYearLabel(List<LabelValueBean> wml300DspYearLabel) {
        wml300DspYearLabel__ = wml300DspYearLabel;
    }

    /**
     * <p>wml300DspMonthLabel を取得します。
     * @return wml300DspMonthLabel
     */
    public List<LabelValueBean> getWml300DspMonthLabel() {
        return wml300DspMonthLabel__;
    }

    /**
     * <p>wml300DspMonthLabel をセットします。
     * @param wml300DspMonthLabel wml300DspMonthLabel
     */
    public void setWml300DspMonthLabel(List<LabelValueBean> wml300DspMonthLabel) {
        wml300DspMonthLabel__ = wml300DspMonthLabel;
    }

    /**
     * <p>wml300DspWeekFrLabel を取得します。
     * @return wml300DspWeekFrLabel
     */
    public List<LabelValueBean> getWml300DspWeekFrLabel() {
        return wml300DspWeekFrLabel__;
    }

    /**
     * <p>wml300DspWeekFrLabel をセットします。
     * @param wml300DspWeekFrLabel wml300DspWeekFrLabel
     */
    public void setWml300DspWeekFrLabel(List<LabelValueBean> wml300DspWeekFrLabel) {
        wml300DspWeekFrLabel__ = wml300DspWeekFrLabel;
    }

    /**
     * <p>wml300DspWeekToLabel を取得します。
     * @return wml300DspWeekToLabel
     */
    public List<LabelValueBean> getWml300DspWeekToLabel() {
        return wml300DspWeekToLabel__;
    }

    /**
     * <p>wml300DspWeekToLabel をセットします。
     * @param wml300DspWeekToLabel wml300DspWeekToLabel
     */
    public void setWml300DspWeekToLabel(List<LabelValueBean> wml300DspWeekToLabel) {
        wml300DspWeekToLabel__ = wml300DspWeekToLabel;
    }

    /**
     * <p>wml300DateMonthlyFrYear を取得します。
     * @return wml300DateMonthlyFrYear
     */
    public String getWml300DateMonthlyFrYear() {
        return wml300DateMonthlyFrYear__;
    }

    /**
     * <p>wml300DateMonthlyFrYear をセットします。
     * @param wml300DateMonthlyFrYear wml300DateMonthlyFrYear
     */
    public void setWml300DateMonthlyFrYear(String wml300DateMonthlyFrYear) {
        wml300DateMonthlyFrYear__ = wml300DateMonthlyFrYear;
    }

    /**
     * <p>wml300DateMonthlyFrMonth を取得します。
     * @return wml300DateMonthlyFrMonth
     */
    public String getWml300DateMonthlyFrMonth() {
        return wml300DateMonthlyFrMonth__;
    }

    /**
     * <p>wml300DateMonthlyFrMonth をセットします。
     * @param wml300DateMonthlyFrMonth wml300DateMonthlyFrMonth
     */
    public void setWml300DateMonthlyFrMonth(String wml300DateMonthlyFrMonth) {
        wml300DateMonthlyFrMonth__ = wml300DateMonthlyFrMonth;
    }

    /**
     * <p>wml300DateMonthlyToYear を取得します。
     * @return wml300DateMonthlyToYear
     */
    public String getWml300DateMonthlyToYear() {
        return wml300DateMonthlyToYear__;
    }

    /**
     * <p>wml300DateMonthlyToYear をセットします。
     * @param wml300DateMonthlyToYear wml300DateMonthlyToYear
     */
    public void setWml300DateMonthlyToYear(String wml300DateMonthlyToYear) {
        wml300DateMonthlyToYear__ = wml300DateMonthlyToYear;
    }

    /**
     * <p>wml300DateMonthlyToMonth を取得します。
     * @return wml300DateMonthlyToMonth
     */
    public String getWml300DateMonthlyToMonth() {
        return wml300DateMonthlyToMonth__;
    }

    /**
     * <p>wml300DateMonthlyToMonth をセットします。
     * @param wml300DateMonthlyToMonth wml300DateMonthlyToMonth
     */
    public void setWml300DateMonthlyToMonth(String wml300DateMonthlyToMonth) {
        wml300DateMonthlyToMonth__ = wml300DateMonthlyToMonth;
    }

    /**
     * <p>wml300DateWeeklyFrStr を取得します。
     * @return wml300DateWeeklyFrStr
     */
    public String getWml300DateWeeklyFrStr() {
        return wml300DateWeeklyFrStr__;
    }

    /**
     * <p>wml300DateWeeklyFrStr をセットします。
     * @param wml300DateWeeklyFrStr wml300DateWeeklyFrStr
     */
    public void setWml300DateWeeklyFrStr(String wml300DateWeeklyFrStr) {
        wml300DateWeeklyFrStr__ = wml300DateWeeklyFrStr;
    }

    /**
     * <p>wml300DateWeeklyToStr を取得します。
     * @return wml300DateWeeklyToStr
     */
    public String getWml300DateWeeklyToStr() {
        return wml300DateWeeklyToStr__;
    }

    /**
     * <p>wml300DateWeeklyToStr をセットします。
     * @param wml300DateWeeklyToStr wml300DateWeeklyToStr
     */
    public void setWml300DateWeeklyToStr(String wml300DateWeeklyToStr) {
        wml300DateWeeklyToStr__ = wml300DateWeeklyToStr;
    }

    /**
     * <p>wml300DateDailyFrStr を取得します。
     * @return wml300DateDailyFrStr
     */
    public String getWml300DateDailyFrStr() {
        return wml300DateDailyFrStr__;
    }

    /**
     * <p>wml300DateDailyFrStr をセットします。
     * @param wml300DateDailyFrStr wml300DateDailyFrStr
     */
    public void setWml300DateDailyFrStr(String wml300DateDailyFrStr) {
        wml300DateDailyFrStr__ = wml300DateDailyFrStr;
    }

    /**
     * <p>wml300DateDailyToStr を取得します。
     * @return wml300DateDailyToStr
     */
    public String getWml300DateDailyToStr() {
        return wml300DateDailyToStr__;
    }

    /**
     * <p>wml300DateDailyToStr をセットします。
     * @param wml300DateDailyToStr wml300DateDailyToStr
     */
    public void setWml300DateDailyToStr(String wml300DateDailyToStr) {
        wml300DateDailyToStr__ = wml300DateDailyToStr;
    }

    /**
     * <p>wml300DspNum を取得します。
     * @return wml300DspNum
     */
    public int getWml300DspNum() {
        return wml300DspNum__;
    }

    /**
     * <p>wml300DspNum をセットします。
     * @param wml300DspNum wml300DspNum
     */
    public void setWml300DspNum(int wml300DspNum) {
        wml300DspNum__ = wml300DspNum;
    }

    /**
     * <p>wml300DspNumLabel を取得します。
     * @return wml300DspNumLabel
     */
    public List<LabelValueBean> getWml300DspNumLabel() {
        return wml300DspNumLabel__;
    }

    /**
     * <p>wml300DspNumLabel をセットします。
     * @param wml300DspNumLabel wml300DspNumLabel
     */
    public void setWml300DspNumLabel(List<LabelValueBean> wml300DspNumLabel) {
        wml300DspNumLabel__ = wml300DspNumLabel;
    }

    /**
     * <p>wml300NowPage を取得します。
     * @return wml300NowPage
     */
    public int getWml300NowPage() {
        return wml300NowPage__;
    }

    /**
     * <p>wml300NowPage をセットします。
     * @param wml300NowPage wml300NowPage
     */
    public void setWml300NowPage(int wml300NowPage) {
        wml300NowPage__ = wml300NowPage;
    }

    /**
     * <p>wml300DspPage1 を取得します。
     * @return wml300DspPage1
     */
    public int getWml300DspPage1() {
        return wml300DspPage1__;
    }

    /**
     * <p>wml300DspPage1 をセットします。
     * @param wml300DspPage1 wml300DspPage1
     */
    public void setWml300DspPage1(int wml300DspPage1) {
        wml300DspPage1__ = wml300DspPage1;
    }

    /**
     * <p>wml300DspPage2 を取得します。
     * @return wml300DspPage2
     */
    public int getWml300DspPage2() {
        return wml300DspPage2__;
    }

    /**
     * <p>wml300DspPage2 をセットします。
     * @param wml300DspPage2 wml300DspPage2
     */
    public void setWml300DspPage2(int wml300DspPage2) {
        wml300DspPage2__ = wml300DspPage2;
    }

    /**
     * <p>wml300PageLabel を取得します。
     * @return wml300PageLabel
     */
    public List<LabelValueBean> getWml300PageLabel() {
        return wml300PageLabel__;
    }

    /**
     * <p>wml300PageLabel をセットします。
     * @param wml300PageLabel wml300PageLabel
     */
    public void setWml300PageLabel(List<LabelValueBean> wml300PageLabel) {
        wml300PageLabel__ = wml300PageLabel;
    }

    /**
     * <p>wml300GraphItem を取得します。
     * @return wml300GraphItem
     */
    public String getWml300GraphItem() {
        return wml300GraphItem__;
    }

    /**
     * <p>wml300GraphItem をセットします。
     * @param wml300GraphItem wml300GraphItem
     */
    public void setWml300GraphItem(String wml300GraphItem) {
        wml300GraphItem__ = wml300GraphItem;
    }

    /**
     * <p>wml300LogCountList を取得します。
     * @return wml300LogCountList
     */
    public List<Wml300DspModel> getWml300LogCountList() {
        return wml300LogCountList__;
    }

    /**
     * <p>wml300LogCountList をセットします。
     * @param wml300LogCountList wml300LogCountList
     */
    public void setWml300LogCountList(List<Wml300DspModel> wml300LogCountList) {
        wml300LogCountList__ = wml300LogCountList;
    }

    /**
     * <p>wml300AveJmailNum を取得します。
     * @return wml300AveJmailNum
     */
    public String getWml300AveJmailNum() {
        return wml300AveJmailNum__;
    }

    /**
     * <p>wml300AveJmailNum をセットします。
     * @param wml300AveJmailNum wml300AveJmailNum
     */
    public void setWml300AveJmailNum(String wml300AveJmailNum) {
        wml300AveJmailNum__ = wml300AveJmailNum;
    }

    /**
     * <p>wml300AveSmailNum を取得します。
     * @return wml300AveSmailNum
     */
    public String getWml300AveSmailNum() {
        return wml300AveSmailNum__;
    }

    /**
     * <p>wml300AveSmailNum をセットします。
     * @param wml300AveSmailNum wml300AveSmailNum
     */
    public void setWml300AveSmailNum(String wml300AveSmailNum) {
        wml300AveSmailNum__ = wml300AveSmailNum;
    }

    /**
     * <p>wml300SumJmailNum を取得します。
     * @return wml300SumJmailNum
     */
    public String getWml300SumJmailNum() {
        return wml300SumJmailNum__;
    }

    /**
     * <p>wml300SumJmailNum をセットします。
     * @param wml300SumJmailNum wml300SumJmailNum
     */
    public void setWml300SumJmailNum(String wml300SumJmailNum) {
        wml300SumJmailNum__ = wml300SumJmailNum;
    }

    /**
     * <p>wml300SumSmailNum を取得します。
     * @return wml300SumSmailNum
     */
    public String getWml300SumSmailNum() {
        return wml300SumSmailNum__;
    }

    /**
     * <p>wml300SumSmailNum をセットします。
     * @param wml300SumSmailNum wml300SumSmailNum
     */
    public void setWml300SumSmailNum(String wml300SumSmailNum) {
        wml300SumSmailNum__ = wml300SumSmailNum;
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
     * <p>wml300CtrlFlgCht を取得します。
     * @return wml300CtrlFlgCht
     * @see jp.groupsession.v2.wml.wml300.Wml300Form#wml300CtrlFlgCht__
     */
    public boolean isWml300CtrlFlgCht() {
        return wml300CtrlFlgCht__;
    }

    /**
     * <p>wml300CtrlFlgCht をセットします。
     * @param wml300CtrlFlgCht wml300CtrlFlgCht
     * @see jp.groupsession.v2.wml.wml300.Wml300Form#wml300CtrlFlgCht__
     */
    public void setWml300CtrlFlgCht(boolean wml300CtrlFlgCht) {
        wml300CtrlFlgCht__ = wml300CtrlFlgCht;
    }
}
