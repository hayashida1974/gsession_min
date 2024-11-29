package jp.groupsession.v2.man.man390;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.StatisticsBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴統計情報画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man390Biz {
//    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Man390Biz.class);

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Man390Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Man390ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(
            Man390ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        Man390Dao dao = new Man390Dao(con__);
        //表示用フォーマット
        DecimalFormat cmmZeroFormat = new DecimalFormat("#,##0.0");
        DecimalFormat cmmBlankFormat = new DecimalFormat("#,###.#");

        //プラグインが使用可能であり、かつログインユーザにプラグインの管理者権限があるか
        List <String> pluginList = new ArrayList<String>();
        for (Plugin plugin: pluginConfig.getPluginDataList()) {
            pluginList.add(plugin.getId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        if (pluginList.contains(GSConst.PLUGINID_WML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_WML)) {
            paramMdl.setMan390CtrlFlgWml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_SML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_SML)) {
            paramMdl.setMan390CtrlFlgSml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_CIR)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_CIR)) {
            paramMdl.setMan390CtrlFlgCir(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_FILE)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_FILE)) {
            paramMdl.setMan390CtrlFlgFil(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_BULLETIN)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_BULLETIN)) {
            paramMdl.setMan390CtrlFlgBbs(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_CHAT)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_CHAT)) {
            paramMdl.setMan390CtrlFlgCht(true);
        }

        //表示件数リスト取得
        paramMdl.setMan390DspNumLabel(StatisticsBiz.getDspNumList());


        //表示単位
        int dateUnit = paramMdl.getMan390DateUnit();

        //日付を設定する
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();
        ArrayList<Man390DspModel> dateList = new ArrayList<Man390DspModel>();
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
            //月別表示の場合
            UDate[] udateFrTo = __setDateByMonth(dao, paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByMonth(udateFr.cloneUDate(), udateTo.cloneUDate());

        } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別表示の場合
            UDate[] udateFrTo = __setDateByWeek(paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByWeek(udateFr.cloneUDate(), udateTo.cloneUDate());

        } else {
            //日別表示の場合
            UDate[] udateFrTo = __setDateByDay(paramMdl, dateUnit);
            udateFr = udateFrTo[0];
            udateTo = udateFrTo[1];

            //日付一覧リストを取得する
            dateList = __getKikanListByDay(udateFr.cloneUDate(), udateTo.cloneUDate());
        }

        //選択日付の日数よりページ情報を取得する
        //現在ページ、スタート行
        int dateListSize = dateList.size();
        int limit = paramMdl.getMan390DspNum();
        int nowPage = paramMdl.getMan390NowPage();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(dateListSize, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setMan390DspPage1(nowPage);
        paramMdl.setMan390DspPage2(nowPage);
        paramMdl.setMan390NowPage(nowPage);
        paramMdl.setMan390PageLabel(PageUtil.createPageOptions(dateListSize, limit));

        //集計一覧を取得する
        UDate searchFrDate = udateFr.cloneUDate();
        UDate searchToDate = udateTo.cloneUDate();
        Map<String, Man390DspModel> loginUserLogMap = new HashMap<String, Man390DspModel>();
        loginUserLogMap = dao.getLoginLog(searchFrDate, searchToDate);

        if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
            //月別のデータをまとめる
            loginUserLogMap = __getSumOfMonth(loginUserLogMap, dateList);

        } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別のデータをまとめる
            loginUserLogMap = __getSumOfWeek(loginUserLogMap, dateList);
        }

        //表示範囲１ページ分の日付リストを生成する
        List<Man390DspModel> pageList = __getPageDspDateList(dateList, nowPage, limit);

        //表示用リストへ格納する
        for (Man390DspModel mdl : pageList) {
            //1件分のデータ
            Man390DspModel loginUserLogMdl = loginUserLogMap.get(mdl.getDate());
            if (loginUserLogMdl == null) {
                continue;
            }

            //登録ユーザ数をセット
            long regUserNum = loginUserLogMdl.getRegUserNum();
            mdl.setRegUserNum(regUserNum);

            //ログイン回数をセット
            long loginNum = loginUserLogMdl.getLoginNum();
            mdl.setLoginNum(loginNum);
            mdl.setDspLoginNum(StatisticsBiz.formatNum(loginNum));

            //ログイン率
            String dspLoginRate = "0.0%";

            if (dateUnit == GSConstMain.STATS_DATE_UNIT_DAY) {
                //日別の場合
                //ログインユーザ数をセット
                long loginUserNum = loginUserLogMdl.getLoginUserNum();
                mdl.setLoginUserNum(loginUserNum);
                mdl.setDspLoginUserNum(StatisticsBiz.formatNum(loginUserNum));

                //ログイン率(ログインユーザ数/登録ユーザ数)をセット
                if (loginUserLogMdl.getRegUserNum() > 0) {
                    double dLoginRate = __getPercentage(
                            loginUserNum, regUserNum);
                    dspLoginRate = cmmZeroFormat.format(dLoginRate) + "%";
                }

            } else {
                //週別、月別の場合
                //ログイン率(ログイン回数の総計/登録ユーザ数の総計)をセット
                if (regUserNum > 0) {
                    double dLoginRate = __getPercentage(loginNum, regUserNum);
                    dspLoginRate = cmmZeroFormat.format(dLoginRate) + "%";
                }
            }
            mdl.setDspLoginRate(dspLoginRate);
        }

        //集計データ一覧を設定
        paramMdl.setMan390LogCountList(pageList);

        //合計ログイン回数を設定
        int sumLoginNum = 0;
        for (Man390DspModel mdl : dateList) {
            Man390DspModel loginUserLogMdl = loginUserLogMap.get(mdl.getDate());
            if (loginUserLogMdl == null) {
                continue;
            }
            sumLoginNum += loginUserLogMdl.getLoginNum();
        }
        paramMdl.setMan390SumLoginNum(cmmBlankFormat.format(sumLoginNum));

        //統計情報の最初の画面を設定する
        if (paramMdl.getLogCountBack() == null) {
            paramMdl.setLogCountBack(GSConst.PLUGINID_MAIN);
        }

        //グラフ用データを作成する
        __makeGraphData(dateList, loginUserLogMap, paramMdl, dateUnit);
    }

    /**
     * <br>[機  能] ログイン履歴集計を月単位にまとめて返します
     * <br>[解  説]
     * <br>[備  考]
     * @param loginUserLogMap ログイン履歴集計一覧
     * @param dateList 指定範囲の日付リスト
     * @return 月単位にまとめたログイン履歴集計一覧
     */
    private Map<String, Man390DspModel> __getSumOfMonth(
            Map<String, Man390DspModel> loginUserLogMap,
            List<Man390DspModel> dateList) {

        Map<String, Man390DspModel> monthlyLogMap = new HashMap<String, Man390DspModel>();
        UDate now = new UDate();

        for (Man390DspModel mdl : dateList) {
            UDate targetUdate = mdl.getuDate().cloneUDate();
//            long loginUserNum = 0;
            long loginNum = 0;
            long regUserNum = 0;

            while (targetUdate.compareDateYM(mdl.getuDate()) != UDate.SMALL) {
                if (targetUdate.compareDateYMD(now) == UDate.SMALL) {
                    //対象日が今日を超えている場合
                    break;
                }

                //曜日
                int youbi = targetUdate.getWeek();
                if (youbi == UDate.SATURDAY || youbi == UDate.SUNDAY) {
                    //土曜日か日曜日である場合
                    targetUdate.addDay(1);
                    continue;
                }

                //データを月データとしてまとめる
                String keyDate = targetUdate.getStrYear()
                        + "-" + targetUdate.getStrMonth() + "-" + targetUdate.getStrDay();
                Man390DspModel dailyMdl = loginUserLogMap.get(keyDate);
                if (dailyMdl != null) {
//                  //ログインユーザ
//                  loginUserNum += dailyMdl.getLoginUserNum();
                    //ログイン回数
                    loginNum += dailyMdl.getLoginNum();
                    //登録ユーザ
                    regUserNum += dailyMdl.getRegUserNum();
                }

                //次の日へ
                targetUdate.addDay(1);
            }

            //月データをセットする
            Man390DspModel monthlyMdl = new Man390DspModel();
            String keyYyMm = mdl.getuDate().getStrYear() + "-" + mdl.getuDate().getStrMonth();
            monthlyMdl.setDate(keyYyMm);
//            monthlyMdl.setLoginUserNum(loginUserNum);
            monthlyMdl.setLoginNum(loginNum);
            monthlyMdl.setRegUserNum(regUserNum);
            monthlyLogMap.put(keyYyMm, monthlyMdl);
        }

        return monthlyLogMap;
    }

    /**
     * <br>[機  能] ログイン履歴集計を週単位にまとめて返します
     * <br>[解  説]
     * <br>[備  考]
     * @param loginUserLogMap ログイン履歴集計一覧
     * @param dateList 指定範囲の日付リスト
     * @return 週単位にまとめたログイン履歴集計一覧
     */
    private Map<String, Man390DspModel> __getSumOfWeek(
            Map<String, Man390DspModel> loginUserLogMap,
            List<Man390DspModel> dateList) {

        Map<String, Man390DspModel> weeklyLogMap = new HashMap<String, Man390DspModel>();
        UDate now = new UDate();

        for (Man390DspModel mdl : dateList) {
            UDate targetUdate = mdl.getuDate().cloneUDate();
            UDate targetToUdate = mdl.getuDate().cloneUDate();
            targetToUdate.addDay(6);
//            long loginUserNum = 0;
            long loginNum = 0;
            long regUserNum = 0;

            while (targetUdate.compareDateYMD(targetToUdate) != UDate.SMALL) {
                if (targetUdate.compareDateYMD(now) == UDate.SMALL) {
                    //対象日が今日を超えている場合
                    break;
                }

                //曜日
                int youbi = targetUdate.getWeek();
                if (youbi == UDate.SATURDAY || youbi == UDate.SUNDAY) {
                    //土曜日または日曜日
                    targetUdate.addDay(1);
                    continue;
                }

                //データを週データとしてまとめる
                String keyDate = targetUdate.getStrYear()
                        + "-" + targetUdate.getStrMonth() + "-" + targetUdate.getStrDay();
                Man390DspModel dailyMdl = loginUserLogMap.get(keyDate);
                if (dailyMdl != null) {
//                  //ログインユーザ
//                  loginUserNum += dailyMdl.getLoginUserNum();
                    //ログイン回数
                    loginNum += dailyMdl.getLoginNum();
                    //登録ユーザ
                    regUserNum += dailyMdl.getRegUserNum();
                }

                //次の日へ
                targetUdate.addDay(1);
            }

            //週データをセットする
            Man390DspModel weeklyMdl = new Man390DspModel();
            String keyYyMmDd = mdl.getuDate().getStrYear()
                    + "-" + mdl.getuDate().getStrMonth() + "-" + mdl.getuDate().getStrDay();
            weeklyMdl.setDate(keyYyMmDd);
//            weeklyMdl.setLoginUserNum(loginUserNum);
            weeklyMdl.setLoginNum(loginNum);
            weeklyMdl.setRegUserNum(regUserNum);
            weeklyLogMap.put(keyYyMmDd, weeklyMdl);
        }

        return weeklyLogMap;
    }

    /**
     * <br>[機  能] 月別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param dao Man390Dao
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @exception SQLException SQL実行時例外
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByMonth(
            Man390Dao dao, Man390ParamModel paramMdl, int dateUnit)
                    throws SQLException {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //集計データのMAX日付(現在の日付)とmin日付を取得
        UDate[] logMaxMin = dao.getLoginMaxMinDate();
        //年の表示下限をmin日付の1年前に設定
        UDate minLogDate = NullDefault.getUDate(logMaxMin[0], new UDate());
        minLogDate.addYear(-1);
        //年の表示上限をMAX日付の1年後に設定
        UDate maxLogDate = new UDate();
        maxLogDate.addYear(1);

        //年月コンボ作成
        paramMdl.setMan390DspYearLabel(StatisticsBiz.getDspYearList(
                minLogDate.getYear(), maxLogDate.getYear(), reqMdl__));
        paramMdl.setMan390DspMonthLabel(StatisticsBiz.getMonthList(reqMdl__));

        //TO年月を設定
        UDate defaultToDate = new UDate();
        String dateToYear = paramMdl.getMan390DateMonthlyToYear();
        String dateToMonth = paramMdl.getMan390DateMonthlyToMonth();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToYear)
                || StringUtil.isNullZeroString(dateToMonth)
                || !ValidateUtil.isNumber(dateToYear)
                || !ValidateUtil.isNumber(dateToMonth)) {
            dateToYear = String.valueOf(defaultToDate.getYear());
            dateToMonth = String.valueOf(defaultToDate.getMonth());
        }
        //年コンボの範囲内かチェック
        if (Integer.parseInt(dateToYear) > maxLogDate.getYear()) {
            dateToYear = maxLogDate.getStrYear();
        } else if (Integer.parseInt(dateToYear) < minLogDate.getYear()) {
            dateToYear = minLogDate.getStrYear();
        }
        paramMdl.setMan390DateMonthlyToYear(dateToYear);
        paramMdl.setMan390DateMonthlyToMonth(dateToMonth);

        //FROM年月を設定
        //初期表示を12ヶ月分に設定
        UDate defaultFrDate = new UDate();
        defaultFrDate.setYear(Integer.parseInt(dateToYear));
        defaultFrDate.setMonth(Integer.parseInt(dateToMonth));
        defaultFrDate.addYear(-1);
        defaultFrDate.addMonth(1);
        String dateFrYear = paramMdl.getMan390DateMonthlyFrYear();
        String dateFrMonth = paramMdl.getMan390DateMonthlyFrMonth();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateFrYear)
                || StringUtil.isNullZeroString(dateFrMonth)
                || !ValidateUtil.isNumber(dateFrYear)
                || !ValidateUtil.isNumber(dateFrMonth)) {
            dateFrYear = String.valueOf(defaultFrDate.getYear());
            dateFrMonth = String.valueOf(defaultFrDate.getMonth());
        }
        //年コンボの範囲内かチェック
        if (Integer.parseInt(dateFrYear) > maxLogDate.getYear()) {
            dateFrYear = maxLogDate.getStrYear();
        } else if (Integer.parseInt(dateFrYear) < minLogDate.getYear()) {
            dateFrYear = minLogDate.getStrYear();
        }
        //FROM > TOの場合、TOをFROMと同じに設定
        if (Integer.parseInt(dateFrYear) > Integer.parseInt(dateToYear)
                || (Integer.parseInt(dateFrYear) == Integer.parseInt(dateToYear)
                        && Integer.parseInt(dateFrMonth) > Integer.parseInt(dateToMonth))) {
            dateToYear = dateFrYear;
            dateToMonth = dateFrMonth;
        }
        paramMdl.setMan390DateMonthlyFrYear(dateFrYear);
        paramMdl.setMan390DateMonthlyFrMonth(dateFrMonth);

        //選択日付の範囲より日数を取得
        udateFr.setYear(Integer.parseInt(dateFrYear));
        udateFr.setMonth(Integer.parseInt(dateFrMonth));
        udateFr.setDay(1);
        udateFr.setZeroHhMmSs();
        udateTo.setYear(Integer.parseInt(dateToYear));
        udateTo.setMonth(Integer.parseInt(dateToMonth));
        udateTo.addMonth(1);
        udateTo.setDay(1);
        udateTo.addDay(-1);
        udateTo.setMaxHhMmSs();

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] 週別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByWeek(Man390ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //TO年月週を設定
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getMan390DateWeeklyToStr();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToStr)
                || !ValidateUtil.isSlashDateFormat(dateToStr)) {
            dateToStr = defaultToDate.getDateString("/");
        }
        udateTo = StatisticsBiz.getDateFromString(dateToStr);

        //初期表示を12週分に設定
        UDate defaultFrDate = udateTo.cloneUDate();
        defaultFrDate.addDay(-83);
        //nullと空文字チェック、数字チェック
        String dateFrStr = paramMdl.getMan390DateWeeklyFrStr();
        if (StringUtil.isNullZeroString(dateFrStr)
                || !ValidateUtil.isSlashDateFormat(dateFrStr)) {
            dateFrStr = defaultFrDate.getDateString("/");
        }
        udateFr = StatisticsBiz.getDateFromString(dateFrStr);

        //FROM > TOの場合、TOをFROMと同じに設定
        if (udateFr.compareDateYMD(udateTo) == UDate.SMALL) {
            udateTo = udateFr;
            dateToStr = dateFrStr;
        }

        //選択日付の範囲より日数を取得
        udateFr.setZeroHhMmSs();
        udateTo.setMaxHhMmSs();
        paramMdl.setMan390DateWeeklyFrStr(dateFrStr);
        paramMdl.setMan390DateWeeklyToStr(dateToStr);

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] 日別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByDay(Man390ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //直近の一か月分をデフォルトの値として設定する
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getMan390DateDailyToStr();
        //nullと空文字チェック、数字チェック
        if (StringUtil.isNullZeroString(dateToStr)
                || !ValidateUtil.isSlashDateFormat(dateToStr)) {
            dateToStr = defaultToDate.getDateString("/");
        }
        udateTo = StatisticsBiz.getDateFromString(dateToStr);

        //初期表示を2週間分に設定
        UDate defaultFrDate = udateTo.cloneUDate();
        defaultFrDate.addDay(-13);
        //nullと空文字チェック、数字チェック
        String dateFrStr = paramMdl.getMan390DateDailyFrStr();
        if (StringUtil.isNullZeroString(dateFrStr)
                || !ValidateUtil.isSlashDateFormat(dateFrStr)) {
            dateFrStr = defaultFrDate.getDateString("/");
        }
        udateFr = StatisticsBiz.getDateFromString(dateFrStr);

        //FROM > TOの場合、FROMをTOと同じに設定
        if (udateFr.compareDateYMD(udateTo) == UDate.SMALL) {
            udateFr = udateTo;
            dateToStr = dateFrStr;
        }

        //選択日付の範囲より日数を取得
        udateFr.setZeroHhMmSs();
        udateTo.setMaxHhMmSs();
        paramMdl.setMan390DateDailyFrStr(dateFrStr);
        paramMdl.setMan390DateDailyToStr(dateToStr);

        return new UDate[] {udateFr, udateTo};
    }

    /**
     * <br>[機  能] １ページの表示範囲の日付リスト
     * <br>[解  説]
     * <br>[備  考]
     * @param dateList 選択日付リスト
     * @param nowPage 現在のページ数
     * @param limit 件数
     * @return 1ページ分の日付リスト
     */
    private static List<Man390DspModel> __getPageDspDateList(
            ArrayList<Man390DspModel> dateList, int nowPage , int limit) {

        List<Man390DspModel> ret = new ArrayList<Man390DspModel>();

        //存在しないページが指定されていた場合は１ページ目扱い
        if (limit > dateList.size()) {
            nowPage = 1;
        }

        //開始位置
        int fromIndex = (nowPage - 1) * limit;
        //終了位置
        int toIndex = 0;
        if (fromIndex + limit >= dateList.size()) {
            toIndex = dateList.size();
        } else {
            toIndex = fromIndex + limit;
        }

        ret = dateList.subList(fromIndex, toIndex);

        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の月ごと一覧リストを取得する
     * <br>[解  説] 2000年1月～2001年1月までなら13月分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Man390DspModel> __getKikanListByMonth(UDate dateFr , UDate dateTo) {

        ArrayList<Man390DspModel> ret = new ArrayList<Man390DspModel>();

        while (dateFr.compareDateYM(dateTo) > UDate.SMALL) {
            Man390DspModel mdl = new Man390DspModel();
            mdl.setDate(String.valueOf(dateFr.getYear()) + "-" + dateFr.getStrMonth());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymJ(dateFr, reqMdl__));
            mdl.setLoginNum(0);
            mdl.setDspLoginNum("0");
            mdl.setLoginUserNum(0);
            mdl.setDspLoginUserNum("0");
            mdl.setLoginRate(0);
            mdl.setDspLoginRate("0.0%");
            ret.add(mdl);
            dateFr.addMonth(1);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の週ごと一覧リストを取得する
     * <br>[解  説] 1月1日～2月1日までなら5週分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Man390DspModel> __getKikanListByWeek(UDate dateFr , UDate dateTo) {

        ArrayList<Man390DspModel> ret = new ArrayList<Man390DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            //該当週の終了日
            UDate dateFrWeekend = dateFr.cloneUDate();
            dateFrWeekend.addDay(6);

            Man390DspModel mdl = new Man390DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setuDate(dateFr.cloneUDate());

            if (dateFrWeekend.compareDateYMD(dateTo) > UDate.SMALL) {
                //該当週の終了日が指定期間内に収まる場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateFrWeekend, reqMdl__));
            } else {
                //該当週の終了日が指定期間を超えている場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateTo, reqMdl__));
            }
            mdl.setLoginNum(0);
            mdl.setDspLoginNum("0");
            mdl.setLoginUserNum(0);
            mdl.setDspLoginUserNum("0");
            mdl.setLoginRate(0);
            mdl.setDspLoginRate("0.0%");
            ret.add(mdl);
            //次の週にする
            dateFr.addDay(7);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した期間の日付一覧リストを取得する
     * <br>[解  説] 1月1日～2月1日までなら32日分のリストを作成する
     * <br>[備  考]
     * @param dateFr From日付
     * @param dateTo To日付
     * @return 日付一覧マップ
     */
    private ArrayList<Man390DspModel> __getKikanListByDay(UDate dateFr , UDate dateTo) {

        ArrayList<Man390DspModel> ret = new ArrayList<Man390DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            Man390DspModel mdl = new Man390DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setuDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymdJwithStrWeek(dateFr, reqMdl__));
            mdl.setLoginNum(0);
            mdl.setDspLoginNum("0");
            mdl.setLoginUserNum(0);
            mdl.setDspLoginUserNum("0");
            mdl.setLoginRate(0);
            mdl.setDspLoginRate("0.0%");
            ret.add(mdl);
            dateFr.addDay(1);
        }
        return ret;
    }

    /**
     * <br>[機  能] グラフ用データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateList 日付一覧リスト
     * @param loginUserLogMap ログイン履歴集計一覧
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     */
    private void __makeGraphData(
            ArrayList<Man390DspModel> dateList,
            Map<String,
            Man390DspModel> loginUserLogMap,
            Man390ParamModel paramMdl,
            int dateUnit) {
        DecimalFormat cmmZeroFormat2 = new DecimalFormat("#0.0");
        StringBuilder jsonDateData = new StringBuilder();
        StringBuilder jsonLineData = new StringBuilder();

        for (int i = 0; i < dateList.size(); i++) {
            Man390DspModel dateMdl = dateList.get(i);
            UDate date = dateMdl.getuDate();
            Man390DspModel logMdl = loginUserLogMap.get(dateMdl.getDate());

            if (i == 0) {
                jsonDateData.append("[");
                jsonLineData.append("[");
            }

            //日付データをセット
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                //月別日付
                jsonDateData.append("'" + date.getStrYear() + "/" + date.getStrMonth() + "'");
            } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
                //週別日付
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "-'");
            } else {
                //日別日付
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "'");
            }

            //グラフのデータをセット
            if (logMdl != null && logMdl.getRegUserNum() > 0) {
                double loginRate = 0;
                if (dateUnit == GSConstMain.STATS_DATE_UNIT_DAY) {
                    //日別のログイン率
                    loginRate = __getPercentage(logMdl.getLoginUserNum(), logMdl.getRegUserNum());
                } else {
                    //月、週別のログイン率
                    loginRate = __getPercentage(logMdl.getLoginNum() , logMdl.getRegUserNum());
                }
                jsonLineData.append(cmmZeroFormat2.format(loginRate));
            } else {
                jsonLineData.append("0");
            }

            if (i == dateList.size() - 1) {
                jsonDateData.append("]");
                jsonLineData.append("]");
            } else {
                jsonDateData.append(",");
                jsonLineData.append(",");
            }
        }
        paramMdl.setJsonDateData(jsonDateData.toString());
        paramMdl.setJsonLoginRate(jsonLineData.toString());
    }

    /**
     * <br>[機  能] 数値から百分率を出します。
     * <br>[解  説]
     * <br>[備  考] 100を超える場合は100にします。
     * @param numerator 分子
     * @param denominator 分母
     * @return 結果の百分率
     */
    private static double __getPercentage(long numerator, long denominator) {
        double ret = 0;
        BigDecimal bdNumerator = new BigDecimal(numerator);
        BigDecimal bdDenominator = new BigDecimal(denominator);
        BigDecimal rate = bdNumerator.divide(
                bdDenominator, 3, BigDecimal.ROUND_HALF_UP);
        rate = rate.multiply(new BigDecimal(100));

        ret = rate.doubleValue();
//        if (ret > 100) {
//            //100%を超える場合は100%にする
//            ret = 100;
//        }
        return ret;
    }
}
