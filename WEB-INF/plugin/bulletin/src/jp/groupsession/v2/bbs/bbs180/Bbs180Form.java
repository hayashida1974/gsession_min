package jp.groupsession.v2.bbs.bbs180;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs160.Bbs160Form;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 掲示板統計情報画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs180Form extends Bbs160Form {

    /** メイン管理者権限フラグ */
    private boolean bbs180GsAdminFlg__ = false;
    /** 使用可能フラグ WEBメール */
    private boolean bbs180CtrlFlgWml__ = false;
    /** 使用可能フラグ ショートメール */
    private boolean bbs180CtrlFlgSml__ = false;
    /** 使用可能フラグ 回覧板 */
    private boolean bbs180CtrlFlgCir__ = false;
    /** 使用可能フラグ ファイル管理 */
    private boolean bbs180CtrlFlgFil__ = false;
    /** 使用可能フラグ チャット */
    private boolean bbs180CtrlFlgCht__ = false;

    /** 日付表示単位 */
    private int bbs180DateUnit__ = GSConstMain.STATS_DATE_UNIT_MONTH;
    /** 年指定コンボ */
    private List<LabelValueBean> bbs180DspYearLabel__;
    /** 月指定コンボ */
    private List<LabelValueBean> bbs180DspMonthLabel__;
    /** 週指定Fromコンボ */
    private List<LabelValueBean> bbs180DspWeekFrLabel__;
    /** 週指定Toコンボ */
    private List<LabelValueBean> bbs180DspWeekToLabel__;
    /** 月別 日付 開始年 */
    private String bbs180DateMonthlyFrYear__;
    /** 月別 日付 開始月 */
    private String bbs180DateMonthlyFrMonth__;
    /** 月別 日付 終了年 */
    private String bbs180DateMonthlyToYear__;
    /** 月別 日付 終了月 */
    private String bbs180DateMonthlyToMonth__;
    /** 週別 日付範囲 開始 */
    private String bbs180DateWeeklyFrStr__;
    /** 週別 日付範囲 終了 */
    private String bbs180DateWeeklyToStr__;
    /** 日別 日付範囲 開始 */
    private String bbs180DateDailyFrStr__;
    /** 日別 日付範囲 終了 */
    private String bbs180DateDailyToStr__;
    /** 表示件数 */
    private int bbs180DspNum__ = 40;
    /** 表示件数コンボ */
    private List<LabelValueBean> bbs180DspNumLabel__;

    /** 現在ページ */
    private int bbs180NowPage__ = 1;
    /** 表示ページ（上） */
    private int bbs180DspPage1__;
    /** 表示ページ（下） */
    private int bbs180DspPage2__;
    /** ページラベル */
    private List<LabelValueBean> bbs180PageLabel__;
    /** 現在のグラフの項目 */
    private String bbs180GraphItem__ = GSConstBulletin.BBS_LOG_GRAPH_VIEW;

    /** 集計データ一覧 */
    private List<Bbs180DspModel> bbs180LogCountList__;
    /** 平均件数 掲示板閲覧 */
    private String bbs180AveVbbsNum__;
    /** 平均件数 掲示板投稿 */
    private String bbs180AveWbbsNum__;
    /** 合計件数 掲示板閲覧 */
    private String bbs180SumVbbsNum__;
    /** 合計件数 掲示板投稿 */
    private String bbs180SumWbbsNum__;

    /** 統計情報の戻り先 */
    private String logCountBack__;

    /** 現在の全スレッド数 */
    private String bbsThreCnt__;
    /** 現在の全投稿数 */
    private String bbsUpCnt__;

    /** グラフデータ（日付） */
    private String jsonDateData__;
    /** グラフデータ（閲覧数） */
    private String jsonVbbsData__;
    /** グラフデータ（投稿数） */
    private String jsonWbbsData__;


    /**
     * <p>bbs180GsAdminFlg を取得します。
     * @return bbs180GsAdminFlg
     */
    public boolean isBbs180GsAdminFlg() {
        return bbs180GsAdminFlg__;
    }
    /**
     * <p>bbs180GsAdminFlg をセットします。
     * @param bbs180GsAdminFlg bbs180GsAdminFlg
     */
    public void setBbs180GsAdminFlg(boolean bbs180GsAdminFlg) {
        bbs180GsAdminFlg__ = bbs180GsAdminFlg;
    }
    /**
     * <p>bbs180CtrlFlgWml を取得します。
     * @return bbs180CtrlFlgWml
     */
    public boolean isBbs180CtrlFlgWml() {
        return bbs180CtrlFlgWml__;
    }
    /**
     * <p>bbs180CtrlFlgWml をセットします。
     * @param bbs180CtrlFlgWml bbs180CtrlFlgWml
     */
    public void setBbs180CtrlFlgWml(boolean bbs180CtrlFlgWml) {
        bbs180CtrlFlgWml__ = bbs180CtrlFlgWml;
    }
    /**
     * <p>bbs180CtrlFlgSml を取得します。
     * @return bbs180CtrlFlgSml
     */
    public boolean isBbs180CtrlFlgSml() {
        return bbs180CtrlFlgSml__;
    }
    /**
     * <p>bbs180CtrlFlgSml をセットします。
     * @param bbs180CtrlFlgSml bbs180CtrlFlgSml
     */
    public void setBbs180CtrlFlgSml(boolean bbs180CtrlFlgSml) {
        bbs180CtrlFlgSml__ = bbs180CtrlFlgSml;
    }
    /**
     * <p>bbs180CtrlFlgCir を取得します。
     * @return bbs180CtrlFlgCir
     */
    public boolean isBbs180CtrlFlgCir() {
        return bbs180CtrlFlgCir__;
    }
    /**
     * <p>bbs180CtrlFlgCir をセットします。
     * @param bbs180CtrlFlgCir bbs180CtrlFlgCir
     */
    public void setBbs180CtrlFlgCir(boolean bbs180CtrlFlgCir) {
        bbs180CtrlFlgCir__ = bbs180CtrlFlgCir;
    }
    /**
     * <p>bbs180CtrlFlgFil を取得します。
     * @return bbs180CtrlFlgFil
     */
    public boolean isBbs180CtrlFlgFil() {
        return bbs180CtrlFlgFil__;
    }
    /**
     * <p>bbs180CtrlFlgFil をセットします。
     * @param bbs180CtrlFlgFil bbs180CtrlFlgFil
     */
    public void setBbs180CtrlFlgFil(boolean bbs180CtrlFlgFil) {
        bbs180CtrlFlgFil__ = bbs180CtrlFlgFil;
    }
    /**
     * <p>bbs180DateUnit を取得します。
     * @return bbs180DateUnit
     */
    public int getBbs180DateUnit() {
        return bbs180DateUnit__;
    }
    /**
     * <p>bbs180DateUnit をセットします。
     * @param bbs180DateUnit bbs180DateUnit
     */
    public void setBbs180DateUnit(int bbs180DateUnit) {
        bbs180DateUnit__ = bbs180DateUnit;
    }
    /**
     * <p>bbs180DspYearLabel を取得します。
     * @return bbs180DspYearLabel
     */
    public List<LabelValueBean> getBbs180DspYearLabel() {
        return bbs180DspYearLabel__;
    }
    /**
     * <p>bbs180DspYearLabel をセットします。
     * @param bbs180DspYearLabel bbs180DspYearLabel
     */
    public void setBbs180DspYearLabel(List<LabelValueBean> bbs180DspYearLabel) {
        bbs180DspYearLabel__ = bbs180DspYearLabel;
    }
    /**
     * <p>bbs180DspMonthLabel を取得します。
     * @return bbs180DspMonthLabel
     */
    public List<LabelValueBean> getBbs180DspMonthLabel() {
        return bbs180DspMonthLabel__;
    }
    /**
     * <p>bbs180DspMonthLabel をセットします。
     * @param bbs180DspMonthLabel bbs180DspMonthLabel
     */
    public void setBbs180DspMonthLabel(List<LabelValueBean> bbs180DspMonthLabel) {
        bbs180DspMonthLabel__ = bbs180DspMonthLabel;
    }
    /**
     * <p>bbs180DspWeekFrLabel を取得します。
     * @return bbs180DspWeekFrLabel
     */
    public List<LabelValueBean> getBbs180DspWeekFrLabel() {
        return bbs180DspWeekFrLabel__;
    }
    /**
     * <p>bbs180DspWeekFrLabel をセットします。
     * @param bbs180DspWeekFrLabel bbs180DspWeekFrLabel
     */
    public void setBbs180DspWeekFrLabel(List<LabelValueBean> bbs180DspWeekFrLabel) {
        bbs180DspWeekFrLabel__ = bbs180DspWeekFrLabel;
    }
    /**
     * <p>bbs180DspWeekToLabel を取得します。
     * @return bbs180DspWeekToLabel
     */
    public List<LabelValueBean> getBbs180DspWeekToLabel() {
        return bbs180DspWeekToLabel__;
    }
    /**
     * <p>bbs180DspWeekToLabel をセットします。
     * @param bbs180DspWeekToLabel bbs180DspWeekToLabel
     */
    public void setBbs180DspWeekToLabel(List<LabelValueBean> bbs180DspWeekToLabel) {
        bbs180DspWeekToLabel__ = bbs180DspWeekToLabel;
    }
    /**
     * <p>bbs180DateMonthlyFrYear を取得します。
     * @return bbs180DateMonthlyFrYear
     */
    public String getBbs180DateMonthlyFrYear() {
        return bbs180DateMonthlyFrYear__;
    }
    /**
     * <p>bbs180DateMonthlyFrYear をセットします。
     * @param bbs180DateMonthlyFrYear bbs180DateMonthlyFrYear
     */
    public void setBbs180DateMonthlyFrYear(String bbs180DateMonthlyFrYear) {
        bbs180DateMonthlyFrYear__ = bbs180DateMonthlyFrYear;
    }
    /**
     * <p>bbs180DateMonthlyFrMonth を取得します。
     * @return bbs180DateMonthlyFrMonth
     */
    public String getBbs180DateMonthlyFrMonth() {
        return bbs180DateMonthlyFrMonth__;
    }
    /**
     * <p>bbs180DateMonthlyFrMonth をセットします。
     * @param bbs180DateMonthlyFrMonth bbs180DateMonthlyFrMonth
     */
    public void setBbs180DateMonthlyFrMonth(String bbs180DateMonthlyFrMonth) {
        bbs180DateMonthlyFrMonth__ = bbs180DateMonthlyFrMonth;
    }
    /**
     * <p>bbs180DateMonthlyToYear を取得します。
     * @return bbs180DateMonthlyToYear
     */
    public String getBbs180DateMonthlyToYear() {
        return bbs180DateMonthlyToYear__;
    }
    /**
     * <p>bbs180DateMonthlyToYear をセットします。
     * @param bbs180DateMonthlyToYear bbs180DateMonthlyToYear
     */
    public void setBbs180DateMonthlyToYear(String bbs180DateMonthlyToYear) {
        bbs180DateMonthlyToYear__ = bbs180DateMonthlyToYear;
    }
    /**
     * <p>bbs180DateMonthlyToMonth を取得します。
     * @return bbs180DateMonthlyToMonth
     */
    public String getBbs180DateMonthlyToMonth() {
        return bbs180DateMonthlyToMonth__;
    }
    /**
     * <p>bbs180DateMonthlyToMonth をセットします。
     * @param bbs180DateMonthlyToMonth bbs180DateMonthlyToMonth
     */
    public void setBbs180DateMonthlyToMonth(String bbs180DateMonthlyToMonth) {
        bbs180DateMonthlyToMonth__ = bbs180DateMonthlyToMonth;
    }
    /**
     * <p>bbs180DateWeeklyFrStr を取得します。
     * @return bbs180DateWeeklyFrStr
     */
    public String getBbs180DateWeeklyFrStr() {
        return bbs180DateWeeklyFrStr__;
    }
    /**
     * <p>bbs180DateWeeklyFrStr をセットします。
     * @param bbs180DateWeeklyFrStr bbs180DateWeeklyFrStr
     */
    public void setBbs180DateWeeklyFrStr(String bbs180DateWeeklyFrStr) {
        bbs180DateWeeklyFrStr__ = bbs180DateWeeklyFrStr;
    }
    /**
     * <p>bbs180DateWeeklyToStr を取得します。
     * @return bbs180DateWeeklyToStr
     */
    public String getBbs180DateWeeklyToStr() {
        return bbs180DateWeeklyToStr__;
    }
    /**
     * <p>bbs180DateWeeklyToStr をセットします。
     * @param bbs180DateWeeklyToStr bbs180DateWeeklyToStr
     */
    public void setBbs180DateWeeklyToStr(String bbs180DateWeeklyToStr) {
        bbs180DateWeeklyToStr__ = bbs180DateWeeklyToStr;
    }
    /**
     * <p>bbs180DateDailyFrStr を取得します。
     * @return bbs180DateDailyFrStr
     */
    public String getBbs180DateDailyFrStr() {
        return bbs180DateDailyFrStr__;
    }
    /**
     * <p>bbs180DateDailyFrStr をセットします。
     * @param bbs180DateDailyFrStr bbs180DateDailyFrStr
     */
    public void setBbs180DateDailyFrStr(String bbs180DateDailyFrStr) {
        bbs180DateDailyFrStr__ = bbs180DateDailyFrStr;
    }
    /**
     * <p>bbs180DateDailyToStr を取得します。
     * @return bbs180DateDailyToStr
     */
    public String getBbs180DateDailyToStr() {
        return bbs180DateDailyToStr__;
    }
    /**
     * <p>bbs180DateDailyToStr をセットします。
     * @param bbs180DateDailyToStr bbs180DateDailyToStr
     */
    public void setBbs180DateDailyToStr(String bbs180DateDailyToStr) {
        bbs180DateDailyToStr__ = bbs180DateDailyToStr;
    }
    /**
     * <p>bbs180DspNum を取得します。
     * @return bbs180DspNum
     */
    public int getBbs180DspNum() {
        return bbs180DspNum__;
    }
    /**
     * <p>bbs180DspNum をセットします。
     * @param bbs180DspNum bbs180DspNum
     */
    public void setBbs180DspNum(int bbs180DspNum) {
        bbs180DspNum__ = bbs180DspNum;
    }
    /**
     * <p>bbs180DspNumLabel を取得します。
     * @return bbs180DspNumLabel
     */
    public List<LabelValueBean> getBbs180DspNumLabel() {
        return bbs180DspNumLabel__;
    }
    /**
     * <p>bbs180DspNumLabel をセットします。
     * @param bbs180DspNumLabel bbs180DspNumLabel
     */
    public void setBbs180DspNumLabel(List<LabelValueBean> bbs180DspNumLabel) {
        bbs180DspNumLabel__ = bbs180DspNumLabel;
    }
    /**
     * <p>bbs180NowPage を取得します。
     * @return bbs180NowPage
     */
    public int getBbs180NowPage() {
        return bbs180NowPage__;
    }
    /**
     * <p>bbs180NowPage をセットします。
     * @param bbs180NowPage bbs180NowPage
     */
    public void setBbs180NowPage(int bbs180NowPage) {
        bbs180NowPage__ = bbs180NowPage;
    }
    /**
     * <p>bbs180DspPage1 を取得します。
     * @return bbs180DspPage1
     */
    public int getBbs180DspPage1() {
        return bbs180DspPage1__;
    }
    /**
     * <p>bbs180DspPage1 をセットします。
     * @param bbs180DspPage1 bbs180DspPage1
     */
    public void setBbs180DspPage1(int bbs180DspPage1) {
        bbs180DspPage1__ = bbs180DspPage1;
    }
    /**
     * <p>bbs180DspPage2 を取得します。
     * @return bbs180DspPage2
     */
    public int getBbs180DspPage2() {
        return bbs180DspPage2__;
    }
    /**
     * <p>bbs180DspPage2 をセットします。
     * @param bbs180DspPage2 bbs180DspPage2
     */
    public void setBbs180DspPage2(int bbs180DspPage2) {
        bbs180DspPage2__ = bbs180DspPage2;
    }
    /**
     * <p>bbs180PageLabel を取得します。
     * @return bbs180PageLabel
     */
    public List<LabelValueBean> getBbs180PageLabel() {
        return bbs180PageLabel__;
    }
    /**
     * <p>bbs180PageLabel をセットします。
     * @param bbs180PageLabel bbs180PageLabel
     */
    public void setBbs180PageLabel(List<LabelValueBean> bbs180PageLabel) {
        bbs180PageLabel__ = bbs180PageLabel;
    }
    /**
     * <p>bbs180GraphItem を取得します。
     * @return bbs180GraphItem
     */
    public String getBbs180GraphItem() {
        return bbs180GraphItem__;
    }
    /**
     * <p>bbs180GraphItem をセットします。
     * @param bbs180GraphItem bbs180GraphItem
     */
    public void setBbs180GraphItem(String bbs180GraphItem) {
        bbs180GraphItem__ = bbs180GraphItem;
    }
    /**
     * <p>bbs180LogCountList を取得します。
     * @return bbs180LogCountList
     */
    public List<Bbs180DspModel> getBbs180LogCountList() {
        return bbs180LogCountList__;
    }
    /**
     * <p>bbs180LogCountList をセットします。
     * @param bbs180LogCountList bbs180LogCountList
     */
    public void setBbs180LogCountList(List<Bbs180DspModel> bbs180LogCountList) {
        bbs180LogCountList__ = bbs180LogCountList;
    }
    /**
     * <p>bbs180AveVbbsNum を取得します。
     * @return bbs180AveVbbsNum
     */
    public String getBbs180AveVbbsNum() {
        return bbs180AveVbbsNum__;
    }
    /**
     * <p>bbs180AveVbbsNum をセットします。
     * @param bbs180AveVbbsNum bbs180AveVbbsNum
     */
    public void setBbs180AveVbbsNum(String bbs180AveVbbsNum) {
        bbs180AveVbbsNum__ = bbs180AveVbbsNum;
    }
    /**
     * <p>bbs180AveWbbsNum を取得します。
     * @return bbs180AveWbbsNum
     */
    public String getBbs180AveWbbsNum() {
        return bbs180AveWbbsNum__;
    }
    /**
     * <p>bbs180AveWbbsNum をセットします。
     * @param bbs180AveWbbsNum bbs180AveWbbsNum
     */
    public void setBbs180AveWbbsNum(String bbs180AveWbbsNum) {
        bbs180AveWbbsNum__ = bbs180AveWbbsNum;
    }
    /**
     * <p>bbs180SumVbbsNum を取得します。
     * @return bbs180SumVbbsNum
     */
    public String getBbs180SumVbbsNum() {
        return bbs180SumVbbsNum__;
    }
    /**
     * <p>bbs180SumVbbsNum をセットします。
     * @param bbs180SumVbbsNum bbs180SumVbbsNum
     */
    public void setBbs180SumVbbsNum(String bbs180SumVbbsNum) {
        bbs180SumVbbsNum__ = bbs180SumVbbsNum;
    }
    /**
     * <p>bbs180SumWbbsNum を取得します。
     * @return bbs180SumWbbsNum
     */
    public String getBbs180SumWbbsNum() {
        return bbs180SumWbbsNum__;
    }
    /**
     * <p>bbs180SumWbbsNum をセットします。
     * @param bbs180SumWbbsNum bbs180SumWbbsNum
     */
    public void setBbs180SumWbbsNum(String bbs180SumWbbsNum) {
        bbs180SumWbbsNum__ = bbs180SumWbbsNum;
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
     * <p>bbsThreCnt を取得します。
     * @return bbsThreCnt
     */
    public String getBbsThreCnt() {
        return bbsThreCnt__;
    }
    /**
     * <p>bbsThreCnt をセットします。
     * @param bbsThreCnt bbsThreCnt
     */
    public void setBbsThreCnt(String bbsThreCnt) {
        bbsThreCnt__ = bbsThreCnt;
    }
    /**
     * <p>bbsUpCnt を取得します。
     * @return bbsUpCnt
     */
    public String getBbsUpCnt() {
        return bbsUpCnt__;
    }
    /**
     * <p>bbsUpCnt をセットします。
     * @param bbsUpCnt bbsUpCnt
     */
    public void setBbsUpCnt(String bbsUpCnt) {
        bbsUpCnt__ = bbsUpCnt;
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
     * <p>jsonVbbsData を取得します。
     * @return jsonVbbsData
     */
    public String getJsonVbbsData() {
        return jsonVbbsData__;
    }
    /**
     * <p>jsonVbbsData をセットします。
     * @param jsonVbbsData jsonVbbsData
     */
    public void setJsonVbbsData(String jsonVbbsData) {
        jsonVbbsData__ = jsonVbbsData;
    }
    /**
     * <p>jsonWbbsData を取得します。
     * @return jsonWbbsData
     */
    public String getJsonWbbsData() {
        return jsonWbbsData__;
    }
    /**
     * <p>jsonWbbsData をセットします。
     * @param jsonWbbsData jsonWbbsData
     */
    public void setJsonWbbsData(String jsonWbbsData) {
        jsonWbbsData__ = jsonWbbsData;
    }
    /**
     * <p>bbs180CtrlFlgCht を取得します。
     * @return bbs180CtrlFlgCht
     * @see jp.groupsession.v2.bbs.bbs180.Bbs180Form#bbs180CtrlFlgCht__
     */
    public boolean isBbs180CtrlFlgCht() {
        return bbs180CtrlFlgCht__;
    }
    /**
     * <p>bbs180CtrlFlgCht をセットします。
     * @param bbs180CtrlFlgCht bbs180CtrlFlgCht
     * @see jp.groupsession.v2.bbs.bbs180.Bbs180Form#bbs180CtrlFlgCht__
     */
    public void setBbs180CtrlFlgCht(boolean bbs180CtrlFlgCht) {
        bbs180CtrlFlgCht__ = bbs180CtrlFlgCht;
    }

}