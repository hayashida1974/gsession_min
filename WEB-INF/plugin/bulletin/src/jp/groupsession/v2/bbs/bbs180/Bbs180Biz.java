package jp.groupsession.v2.bbs.bbs180;

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
import jp.groupsession.v2.bbs.dao.BbsForSumDao;
import jp.groupsession.v2.bbs.model.BbsForSumModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.StatisticsBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 掲示板 管理者設定 統計情報画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs180Biz {

    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Bbs180Biz.class);

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
    public Bbs180Biz(Connection con,
                     RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bbs180ParamModel
     * @param buMdl セッションユーザモデル
     * @param pluginConfig プラグイン設定
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(
            Bbs180ParamModel paramMdl,
            BaseUserModel buMdl,
            PluginConfig pluginConfig)
                    throws SQLException {
        Bbs180Dao dao = new Bbs180Dao(con__);

        //GS管理者権限情報を設定
        if (buMdl.getAdminFlg()) {
            paramMdl.setBbs180GsAdminFlg(true);
        }

        //プラグインが使用可能であり、かつログインユーザにプラグインの管理者権限があるか
        List <String> pluginList = new ArrayList<String>();
        for (Plugin plugin: pluginConfig.getPluginDataList()) {
            pluginList.add(plugin.getId());
        }
        CommonBiz cmnBiz = new CommonBiz();
        if (pluginList.contains(GSConst.PLUGINID_WML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_WML)) {
            paramMdl.setBbs180CtrlFlgWml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_SML)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_SML)) {
            paramMdl.setBbs180CtrlFlgSml(true);
        }
        if (pluginList.contains(GSConst.PLUGINID_CIR)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGINID_CIR)) {
            paramMdl.setBbs180CtrlFlgCir(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_FILE)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_FILE)) {
            paramMdl.setBbs180CtrlFlgFil(true);
        }
        if (pluginList.contains(GSConst.PLUGIN_ID_CHAT)
                && cmnBiz.isPluginAdmin(con__, buMdl, GSConst.PLUGIN_ID_CHAT)) {
            paramMdl.setBbs180CtrlFlgCht(true);
        }

        //表示件数リスト取得
        paramMdl.setBbs180DspNumLabel(StatisticsBiz.getDspNumList());

        //表示件数
        int limit = paramMdl.getBbs180DspNum();

        //日付リスト
        ArrayList<Bbs180DspModel> dateList = new ArrayList<Bbs180DspModel>();

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //表示単位
        int dateUnit = paramMdl.getBbs180DateUnit();

        //日付を設定する
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

        //日付リストのサイズ
        int dateListSize = dateList.size();

        //グラフ用日付リスト
        ArrayList<Bbs180DspModel> jsonDateList = new ArrayList<Bbs180DspModel>(dateList);

        //選択日付の日数よりページ情報を取得する
        //現在ページ、スタート行
        int nowPage = paramMdl.getBbs180NowPage();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(dateListSize, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        paramMdl.setBbs180DspPage1(nowPage);
        paramMdl.setBbs180DspPage2(nowPage);
        paramMdl.setBbs180NowPage(nowPage);
        paramMdl.setBbs180PageLabel(PageUtil.createPageOptions(dateListSize, limit));


        //１ページの表示範囲の日付リストを生成する
        List<Bbs180DspModel> pageList = __getPageDspDateList(dateList, nowPage, limit);
        UDate searchFrDate = new UDate();
        UDate searchToDate = new UDate();
//        if (pageList.size() > 0) {
//          searchFrDate = getDateFromString(pageList.get(0).getDate());
//          searchFrDate.setZeroHhMmSs();
//          searchToDate = getDateFromString(pageList.get(pageList.size() - 1).getDate());
//          searchToDate.setMaxHhMmSs();
//        }

        //グラフ表示のため指定範囲を全件取得
        searchFrDate = udateFr.cloneUDate();
        searchToDate = udateTo.cloneUDate();

        //集計一覧を取得する
        Map<String, Bbs180DspModel> viewLogCountMap = null;
        Map<String, Bbs180DspModel> writeLogCountMap = null;
        if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
            //週別の場合、日毎の集計情報を週単位にまとめる
            Map<String, Bbs180DspModel> dayViewLogMap =
                    dao.getViewLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            Map<String, Bbs180DspModel> dayWriteLogMap =
                    dao.getWriteLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            List<Map<String, Bbs180DspModel>> weekLogMapList =
                    __getLogMapByWeek(dayViewLogMap, dayWriteLogMap, dateList);
            viewLogCountMap = weekLogMapList.get(0);
            writeLogCountMap = weekLogMapList.get(1);

        } else {
            //月別、日別の場合
            viewLogCountMap = dao.getViewLogCountDataSum(searchFrDate, searchToDate, dateUnit);
            writeLogCountMap = dao.getWriteLogCountDataSum(searchFrDate, searchToDate, dateUnit);
        }

        //表示用リストへ格納する
        for (Bbs180DspModel mdl : pageList) {
            Bbs180DspModel viewLogCntData = viewLogCountMap.get(mdl.getDate());
            Bbs180DspModel writeLogCntData = writeLogCountMap.get(mdl.getDate());
            if (viewLogCntData != null) {
                mdl.setVbbsNum(viewLogCntData.getVbbsNum());
                mdl.setStrVbbsNum(StatisticsBiz.formatNum(viewLogCntData.getVbbsNum()));
            }
            if (writeLogCntData != null) {
                mdl.setWbbsNum(writeLogCntData.getWbbsNum());
                mdl.setStrWbbsNum(StatisticsBiz.formatNum(writeLogCntData.getWbbsNum()));
            }
        }
        paramMdl.setBbs180LogCountList(pageList);

        //合計値を取得する
        UDate clUdateFr = udateFr.cloneUDate();
        UDate clUdateTo = udateTo.cloneUDate();
        int sumVbbsLog = dao.getTotalViewLogSum(clUdateFr, clUdateTo);
        int sumWbbsLog = dao.getTotalWriteLogSum(clUdateFr, clUdateTo);
        paramMdl.setBbs180SumVbbsNum(StatisticsBiz.formatNum(sumVbbsLog));
        paramMdl.setBbs180SumWbbsNum(StatisticsBiz.formatNum(sumWbbsLog));

        //平均値を設定する
        DecimalFormat aveFormat = new DecimalFormat("#,###.#");
        double aveJcir = (double) sumVbbsLog / dateListSize;
        double aveScir = (double) sumWbbsLog / dateListSize;
        paramMdl.setBbs180AveVbbsNum(aveFormat.format(aveJcir));
        paramMdl.setBbs180AveWbbsNum(aveFormat.format(aveScir));


        //現在の全スレッド件数を取得する
        BbsForSumDao bbsCntDao = new BbsForSumDao(con__);
        BbsForSumModel bbsSumMdl = bbsCntDao.get();
        paramMdl.setBbsThreCnt(String.valueOf(bbsSumMdl.getBfsThreCnt()));
        //現在の全投稿件数を取得する
        paramMdl.setBbsUpCnt(String.valueOf(bbsSumMdl.getBfsWrtCnt()));

        //統計情報の最初の画面を設定する
        if (paramMdl.getLogCountBack() == null) {
            paramMdl.setLogCountBack(GSConst.PLUGIN_ID_BULLETIN);
        }

        //グラフ用データを作成する
        __makeGraphData(jsonDateList, viewLogCountMap, writeLogCountMap, paramMdl, dateUnit);
    }

    /**
     * <br>[機  能] 月別表示時の日付を設定する
     * <br>[解  説]
     * <br>[備  考] 設定した日付FROM、TOの配列を返す
     * @param dao Bbs180Dao
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     * @exception SQLException SQL実行時例外
     * @return 設定した日付FROM、TOの配列
     */
    private UDate[] __setDateByMonth(
            Bbs180Dao dao, Bbs180ParamModel paramMdl, int dateUnit)
                    throws SQLException {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //集計データのMAX日付(現在の日付)とmin日付を取得
        UDate[] logMaxMinDate = __getLogMaxMinDate(con__, dateUnit);
        //年の表示下限をmin日付の1年前に設定
        UDate minLogDate = NullDefault.getUDate(logMaxMinDate[0], new UDate());
        minLogDate.addYear(-1);
        //年の表示上限をMAX日付の1年後に設定
        UDate maxLogDate = new UDate();
        maxLogDate.addYear(1);

        //年月コンボ作成
        paramMdl.setBbs180DspYearLabel(StatisticsBiz.getDspYearList(
                minLogDate.getYear(), maxLogDate.getYear(), reqMdl__));
        paramMdl.setBbs180DspMonthLabel(StatisticsBiz.getMonthList(reqMdl__));

        //TO年月を設定
        UDate defaultToDate = new UDate();
        String dateToYear = paramMdl.getBbs180DateMonthlyToYear();
        String dateToMonth = paramMdl.getBbs180DateMonthlyToMonth();
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
        paramMdl.setBbs180DateMonthlyToYear(dateToYear);
        paramMdl.setBbs180DateMonthlyToMonth(dateToMonth);

        //FROM年月を設定
        //初期表示を12ヶ月分に設定
        UDate defaultFrDate = new UDate();
        defaultFrDate.setYear(Integer.parseInt(dateToYear));
        defaultFrDate.setMonth(Integer.parseInt(dateToMonth));
        defaultFrDate.addYear(-1);
        defaultFrDate.addMonth(1);
        String dateFrYear = paramMdl.getBbs180DateMonthlyFrYear();
        String dateFrMonth = paramMdl.getBbs180DateMonthlyFrMonth();
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
        paramMdl.setBbs180DateMonthlyFrYear(dateFrYear);
        paramMdl.setBbs180DateMonthlyFrMonth(dateFrMonth);

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
    private UDate[] __setDateByWeek(Bbs180ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //TO年月週を設定
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getBbs180DateWeeklyToStr();
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
        String dateFrStr = paramMdl.getBbs180DateWeeklyFrStr();
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
        paramMdl.setBbs180DateWeeklyFrStr(dateFrStr);
        paramMdl.setBbs180DateWeeklyToStr(dateToStr);

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
    private UDate[] __setDateByDay(Bbs180ParamModel paramMdl, int dateUnit) {

        //選択日付
        UDate udateFr = new UDate();
        UDate udateTo = new UDate();

        //直近の一か月分をデフォルトの値として設定する
        UDate defaultToDate = new UDate();
        String dateToStr = paramMdl.getBbs180DateDailyToStr();
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
        String dateFrStr = paramMdl.getBbs180DateDailyFrStr();
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
        paramMdl.setBbs180DateDailyFrStr(dateFrStr);
        paramMdl.setBbs180DateDailyToStr(dateToStr);

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
    private static List<Bbs180DspModel> __getPageDspDateList(
            ArrayList<Bbs180DspModel> dateList, int nowPage , int limit) {

        List<Bbs180DspModel> ret = new ArrayList<Bbs180DspModel>();

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
    private ArrayList<Bbs180DspModel> __getKikanListByMonth(UDate dateFr , UDate dateTo) {

        ArrayList<Bbs180DspModel> ret = new ArrayList<Bbs180DspModel>();

        while (dateFr.compareDateYM(dateTo) > UDate.SMALL) {
            Bbs180DspModel mdl = new Bbs180DspModel();
            mdl.setDate(String.valueOf(dateFr.getYear()) + dateFr.getStrMonth());
            mdl.setUDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymJ(dateFr, reqMdl__));
            mdl.setVbbsNum(0);
            mdl.setStrVbbsNum("0");
            mdl.setWbbsNum(0);
            mdl.setStrWbbsNum("0");
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
    private ArrayList<Bbs180DspModel> __getKikanListByWeek(UDate dateFr , UDate dateTo) {

        ArrayList<Bbs180DspModel> ret = new ArrayList<Bbs180DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            //該当週の終了日
            UDate dateFrWeekend = dateFr.cloneUDate();
            dateFrWeekend.addDay(6);

            Bbs180DspModel mdl = new Bbs180DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setUDate(dateFr.cloneUDate());
            if (dateFrWeekend.compareDateYMD(dateTo) > UDate.SMALL) {
                //該当週の終了日が指定期間内に収まる場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateFrWeekend, reqMdl__));
            } else {
                //該当週の終了日が指定期間を超えている場合
                mdl.setDspDate(UDateUtil.getYymdJ(dateFr, reqMdl__)
                        + " - " + UDateUtil.getYymdJ(dateTo, reqMdl__));
            }
            mdl.setVbbsNum(0);
            mdl.setStrVbbsNum("0");
            mdl.setWbbsNum(0);
            mdl.setStrWbbsNum("0");
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
    private ArrayList<Bbs180DspModel> __getKikanListByDay(UDate dateFr , UDate dateTo) {

        ArrayList<Bbs180DspModel> ret = new ArrayList<Bbs180DspModel>();

        while (dateFr.compareDateYMD(dateTo) > UDate.SMALL) {
            Bbs180DspModel mdl = new Bbs180DspModel();
            mdl.setDate(dateFr.getDateStringForSql());
            mdl.setUDate(dateFr.cloneUDate());
            mdl.setDspDate(UDateUtil.getYymdJwithStrWeek(dateFr, reqMdl__));
            mdl.setVbbsNum(0);
            mdl.setStrVbbsNum("0");
            mdl.setWbbsNum(0);
            mdl.setStrWbbsNum("0");
            ret.add(mdl);
            dateFr.addDay(1);
        }
        return ret;
    }

    /**
     * <br>[機  能] 日毎の集計情報から週毎の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dayViewLogMap 閲覧件数集計情報
     * @param dayWriteLogMap 投稿件数集計情報
     * @param dateList 日付リスト
     * @return 週毎の集計情報のリスト
     */
    private List<Map<String, Bbs180DspModel>> __getLogMapByWeek(
            Map<String, Bbs180DspModel> dayViewLogMap,
            Map<String, Bbs180DspModel> dayWriteLogMap,
            List<Bbs180DspModel> dateList) {
        Map<String, Bbs180DspModel> viewLogMap = new HashMap<String, Bbs180DspModel>();
        Map<String, Bbs180DspModel> writeLogMap = new HashMap<String, Bbs180DspModel>();
        List<Map<String, Bbs180DspModel>> ret = new ArrayList<Map<String, Bbs180DspModel>>();

        //週別表示用に日毎のデータを週単位にまとめ、表示用リストへ格納する
        for (Bbs180DspModel mdl : dateList) {

            //対象の週の開始日と終了日
            String strBeginOfWeek = mdl.getDate();
            UDate beginOfWeek = mdl.getUDate();
            UDate endOfWeek = beginOfWeek.cloneUDate();
            endOfWeek.addDay(6);

            //1週間分をまとめる
            int viewNum = 0;
            int writeNum = 0;
            UDate targetDay = beginOfWeek.cloneUDate();
            while (targetDay.compareDateYMD(endOfWeek) != UDate.SMALL) {
                String strTargetDay = targetDay.getStrYear()
                                                + "-" + targetDay.getStrMonth()
                                                + "-" + targetDay.getStrDay();
                Bbs180DspModel viewLogMdl = dayViewLogMap.get(strTargetDay);
                Bbs180DspModel writeLogMdl = dayWriteLogMap.get(strTargetDay);
                if (viewLogMdl != null) {
                    viewNum = viewNum + viewLogMdl.getVbbsNum();
                }
                if (writeLogMdl != null) {
                    writeNum = writeNum + writeLogMdl.getWbbsNum();
                }
                targetDay.addDay(1);
            }

            //閲覧件数集計一覧に1週間分まとめたデータを格納する
            Bbs180DspModel graphViewMdl = new Bbs180DspModel();
            graphViewMdl.setDate(strBeginOfWeek);
            graphViewMdl.setVbbsNum(viewNum);
            viewLogMap.put(strBeginOfWeek, graphViewMdl);

            //投稿件数集計一覧に1週間分まとめたデータを格納する
            Bbs180DspModel graphWriteMdl = new Bbs180DspModel();
            graphWriteMdl.setDate(strBeginOfWeek);
            graphWriteMdl.setWbbsNum(writeNum);
            writeLogMap.put(strBeginOfWeek, graphWriteMdl);
        }

        ret.add(viewLogMap);
        ret.add(writeLogMap);
        return ret;
    }

    /**
     * <br>[機  能] グラフ用データを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonDateList グラフ用日付リスト
     * @param viewLogMap 閲覧件数集計一覧
     * @param writeLogMap 投稿件数集計一覧
     * @param paramMdl パラメータモデル
     * @param dateUnit 日付単位
     */
    private void __makeGraphData(
            ArrayList<Bbs180DspModel> jsonDateList,
            Map<String, Bbs180DspModel> viewLogMap,
            Map<String, Bbs180DspModel> writeLogMap,
            Bbs180ParamModel paramMdl,
            int dateUnit) {
        StringBuilder jsonDateData = new StringBuilder();
        StringBuilder jsonViewLogData = new StringBuilder();
        StringBuilder jsonWriteLogData = new StringBuilder();
        for (int i = 0; i < jsonDateList.size(); i++) {
            if (i == 0) {
                jsonDateData.append("[");
                jsonViewLogData.append("[");
                jsonWriteLogData.append("[");
            }

            Bbs180DspModel dataMdl = jsonDateList.get(i);
            UDate date = dataMdl.getUDate();
            Bbs180DspModel viewLogCntData = viewLogMap.get(dataMdl.getDate());
            Bbs180DspModel writeLogCntData = writeLogMap.get(dataMdl.getDate());

            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                jsonDateData.append("'" + date.getStrYear() + "/" + date.getStrMonth() + "'");
            } else if (dateUnit == GSConstMain.STATS_DATE_UNIT_WEEK) {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "-'");
            } else {
                jsonDateData.append("'" + UDateUtil.getSlashYMD(date) + "'");
            }

            if (viewLogCntData != null) {
                jsonViewLogData.append(viewLogCntData.getVbbsNum());
            } else {
                jsonViewLogData.append("0");
            }

            if (writeLogCntData != null) {
                jsonWriteLogData.append(writeLogCntData.getWbbsNum());
            } else {
                jsonWriteLogData.append("0");
            }

            if (i == jsonDateList.size() - 1) {
                jsonDateData.append("]");
                jsonViewLogData.append("]");
                jsonWriteLogData.append("]");
            } else {
                jsonDateData.append(",");
                jsonViewLogData.append(",");
                jsonWriteLogData.append(",");
            }
        }
        paramMdl.setJsonDateData(jsonDateData.toString());
        paramMdl.setJsonVbbsData(jsonViewLogData.toString());
        paramMdl.setJsonWbbsData(jsonWriteLogData.toString());
    }

    /**
     * <br>[機  能] 閲覧と投稿の集計データから最古日付と最新日付を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param dateUnit 日付単位
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行時例外
     */
    private UDate[] __getLogMaxMinDate(Connection con, int dateUnit) throws SQLException {
        Bbs180Dao dao = new Bbs180Dao(con);
        UDate[] ret = null;

        //閲覧と投稿の最古日時・最大日時を取得
        UDate[] viewMaxMin = dao.getViewLogSumMaxMinDate();
        UDate[] writeMaxMin = dao.getWriteLogSumMaxMinDate();

        //日付の最小値
        UDate minView = viewMaxMin[0];
        UDate minWrite = writeMaxMin[0];
        UDate minLogDate = new UDate();
        if (minView != null && minWrite == null) {
            //閲覧日のMINがnullではなく、投稿日のMINがnullである場合
            minLogDate = minView;
        } else if (minView == null && minWrite != null) {
            //閲覧日のMINがnullであり、投稿日のMINがnullではない場合
            minLogDate = minWrite;
        } else if (minView != null && minWrite != null) {
            //閲覧日のMINと投稿日のMINがnullではない場合
            if (minLogDate.compare(minView, minWrite) == UDate.LARGE) {
                minLogDate = minView;
            } else {
                minLogDate = minWrite;
            }
        }

        //日付の最大値
        UDate maxView = viewMaxMin[1];
        UDate maxWrite = writeMaxMin[1];
        UDate maxLogDate = new UDate();
        if (maxView != null && maxWrite == null) {
            //閲覧日のMAXがnullではなく、投稿日のMAXがnullである場合
            maxLogDate = maxView;
        } else if (maxView == null && maxWrite != null) {
            //閲覧日のMAXがnullであり、投稿日のMAXがnullではない場合
            maxLogDate = maxWrite;
        } else if (maxView != null && maxWrite != null) {
            //閲覧日のMAXと投稿日のMAXがnullではない場合
            if (maxLogDate.compare(maxView, maxWrite) == UDate.LARGE) {
                maxLogDate = maxWrite;
            } else {
                maxLogDate = maxView;
            }
        }

        ret = new UDate[] {minLogDate, maxLogDate};
        return ret;
    }
}