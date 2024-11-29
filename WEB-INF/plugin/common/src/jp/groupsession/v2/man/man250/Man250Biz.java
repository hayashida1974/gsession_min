package jp.groupsession.v2.man.man250;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man250.dao.Man250Dao;
import jp.groupsession.v2.man.man250.model.Man250DspModel;
import jp.groupsession.v2.man.man250.model.Man250SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man250Biz.class);

    /** ソート 日時 */
    public static final int SORT_KEY_DATE = 0;
    /** ソート プラグイン */
    public static final int SORT_KEY_PLUGIN = 1;
    /** ソート ログレベル */
    public static final int SORT_KEY_LOG_LEVEL = 2;
    /** ソート 実行ユーザ */
    public static final int SORT_KEY_USR_NAME = 3;
    /** ソート 画面・機能名 */
    public static final int SORT_KEY_PG_NAME = 4;
    /** ソート 内容 */
    public static final int SORT_KEY_VALUE = 5;
    /** ソート 操作 */
    public static final int SORT_KEY_OP_CODE = 6;
    /** IPアドレス */
    public static final int SORT_KEY_LOG_IP = 7;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man250Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @throws SQLException SQL実行時エラー
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public void setInitData(Man250ParamModel paramMdl, Connection con, PluginConfig pconfig)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //年月日コンボの値をセット
        UDate now = new UDate();

        //表示用プラグインリスト
        GsMessage gsMsg = new GsMessage(reqMdl__.getLocale());
        ArrayList<LabelValueBean> pluginConfigLabel = new ArrayList<LabelValueBean>();
        pluginConfigLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.not.specified"), "-1"));

        //ログ出力trueのプラグインをリストに追加
        PluginConfig logPluginConfig = __getPluginConfigForLog(pconfig, con);
        List < Plugin > plist = logPluginConfig.getPluginDataList();

        for (Plugin plugin : plist) {
            pluginConfigLabel.add(new LabelValueBean(plugin.getName(reqMdl__), plugin.getId()));
        }

        paramMdl.setPlgLabel(pluginConfigLabel);
        //グループコンボを取得
        GroupBiz cmnBiz = new GroupBiz();
        paramMdl.setGrpLabel(cmnBiz.getGroupCombLabelList(con, true, gsMsg));

        //グループに所属するユーザコンボを取得
        UserBiz uBiz = new UserBiz();
        paramMdl.setUsrLabel(uBiz.getUserLabelList(
                con, gsMsg, Integer.parseInt(paramMdl.getMan250SltGroup())));

        //ソートコンボを取得する
        List<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.loglevel"),
                        String.valueOf(SORT_KEY_LOG_LEVEL)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("man.run.time"),
                        String.valueOf(SORT_KEY_DATE)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.plugin"),
                        String.valueOf(SORT_KEY_PLUGIN)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("man.run.user"),
                        String.valueOf(SORT_KEY_USR_NAME)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("main.scr.feature.name"),
                        String.valueOf(SORT_KEY_PG_NAME)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.operations"),
                        String.valueOf(SORT_KEY_OP_CODE)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.content"),
                        String.valueOf(SORT_KEY_VALUE)));
        sortLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.ipaddress"),
                        String.valueOf(SORT_KEY_LOG_IP)));
        paramMdl.setSortLabel(sortLabel);

        if (paramMdl.getMan250SltDateFr() == null) {
            //初期値設定
            paramMdl.setMan250SltYearFr(now.getStrYear());
            paramMdl.setMan250SltMonthFr(String.valueOf(now.getMonth()));
            paramMdl.setMan250SltDayFr(String.valueOf(now.getIntDay()));
            paramMdl.setMan250SltYearTo(now.getStrYear());
            paramMdl.setMan250SltMonthTo(String.valueOf(now.getMonth()));
            paramMdl.setMan250SltDayTo(String.valueOf(now.getIntDay()));
            paramMdl.setMan250SltLogError("1");
            paramMdl.setMan250SltLogWarn("1");
            paramMdl.setMan250SltLogInfo("1");
            paramMdl.setMan250SltLogTrace("1");
        }

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getMan250SltDateFr() == null) {
            dateBiz.setDateParam(paramMdl, "man250SltDateFr",
                    "man250SltYearFr", "man250SltMonthFr",
                    "man250SltDayFr", null);
        }

        if (paramMdl.getMan250SltTimeFr() == null) {
            dateBiz.setTimeParam(paramMdl, "man250SltTimeFr",
                    "man250SltHourFr", "man250SltMinFr", null);
        }

        if (paramMdl.getMan250SltDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "man250SltDateTo",
                    "man250SltYearTo", "man250SltMonthTo",
                    "man250SltDayTo", null);
        }

        if (paramMdl.getMan250SltTimeTo() == null) {
            dateBiz.setTimeParam(paramMdl, "man250SltTimeTo",
                    "man250SltHourTo", "man250SltMinTo", null);
        }

    }

    /**
     * <br>[機  能] 検索結果情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return int 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchResult(Man250ParamModel paramMdl, Connection con)
    throws SQLException {

        Man250Dao dao = new Man250Dao(con);
        //１ページ表示件数
        int limit = GSConstLog.OPERATION_LOG_LIST_CNT;

        //検索モデルを取得
        Man250SearchModel searchMdl = getMan250SearchModel(paramMdl);

        //全データ件数
        int maxCount = dao.countLogList(searchMdl);
        log__.debug("オペレーションログ件数==>" + maxCount);

        //現在ページ、スタート行
        int nowPage = paramMdl.getMan250PageNum();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        if (offset > 0) {
            offset--;
        }
        searchMdl.setOffset(offset);
        searchMdl.setLimit(limit);

        //一覧を取得
        ArrayList<Man250DspModel> opList = dao.getLogList(searchMdl, reqMdl__);
        paramMdl.setMan250DspList(opList);

        //ページング
        paramMdl.setMan250PageNum(nowPage);
        paramMdl.setMan250SltPage1(nowPage);
        paramMdl.setMan250SltPage2(nowPage);
        paramMdl.setMan250PageLabel(PageUtil.createPageOptions(maxCount, limit));
        return maxCount;
    }

    /**
     * <br>[機  能] フォーム情報から検索モデルを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     * @return ScheduleListSearchModel 検索条件モデル
     */
    public Man250SearchModel getMan250SearchModel(Man250ParamModel paramMdl) throws SQLException {

        Man250SearchModel searchMdl = new Man250SearchModel();
        searchMdl.setUsrSid(Integer.parseInt(paramMdl.getMan250SvSltUser()));
        searchMdl.setOrder(paramMdl.getMan250SvOrderKey1());
        searchMdl.setSortKey(paramMdl.getMan250SvSortKey1());
        searchMdl.setOrder2(paramMdl.getMan250SvOrderKey2());
        searchMdl.setSortKey2(paramMdl.getMan250SvSortKey2());

        //検索モデルへ設定
        searchMdl.setSltUserSid(Integer.parseInt(paramMdl.getMan250SvSltUser()));
        searchMdl.setSltGroupSid(String.valueOf(paramMdl.getMan250SvSltGroup()));
        UDate date1 = new UDate();
        date1.setDate(
                Integer.parseInt(paramMdl.getMan250SvSltYearFr()),
                Integer.parseInt(paramMdl.getMan250SvSltMonthFr()),
                Integer.parseInt(paramMdl.getMan250SvSltDayFr()));
        if (paramMdl.getMan250SvSltHourFr().equals("-1")) {
            date1.setZeroHhMmSs();
        } else {
            date1.setHour(NullDefault.getInt(paramMdl.getMan250SvSltHourFr(), 0));
            date1.setMinute(NullDefault.getInt(paramMdl.getMan250SvSltMinFr(), 0));
            date1.setSecond(0);
        }

        UDate date2 = new UDate();
        date2.setDate(
                Integer.parseInt(paramMdl.getMan250SvSltYearTo()),
                Integer.parseInt(paramMdl.getMan250SvSltMonthTo()),
                Integer.parseInt(paramMdl.getMan250SvSltDayTo()));
        if (paramMdl.getMan250SvSltHourTo().equals("-1")) {
            date2.setMaxHhMmSs();
        } else {
            date2.setHour(NullDefault.getInt(paramMdl.getMan250SvSltHourTo(), 23));
            date2.setMinute(NullDefault.getInt(paramMdl.getMan250SvSltMinTo(), 59));
            date2.setSecond(0);
        }

        searchMdl.setDateFr(date1);
        searchMdl.setDateTo(date2);
        searchMdl.setLogLevelError(paramMdl.getMan250SvSltLogError());
        searchMdl.setLogLevelWarn(paramMdl.getMan250SvSltLogWarn());
        searchMdl.setLogLevelInfo(paramMdl.getMan250SvSltLogInfo());
        searchMdl.setLogLevelTrace(paramMdl.getMan250SvSltLogTrace());
        searchMdl.setPluginId(paramMdl.getMan250SvSltPlugin());
        searchMdl.setPluginId(paramMdl.getMan250SvSltPlugin());

        //検索キーワード
        CommonBiz cBiz = new CommonBiz();
        String keyWord = NullDefault.getString(paramMdl.getMan250SvKeyWord(), "");
        List<String> ketWordList = cBiz.setKeyword(keyWord);
        searchMdl.setKeyWord(ketWordList);

        //検索区分
        searchMdl.setKeyWordKbn(paramMdl.getMan250SvKeyWordKbn());
        String[] targets = paramMdl.getMan250SvSearchTarget();
        boolean targetFunction = false;
        boolean targetOperation = false;
        boolean targetContent = false;
        boolean targetIpaddress = false;
        if (targets != null && targets.length > 0) {
            for (String target: targets) {
                if (String.valueOf(GSConstMain.SEARCH_TARGET_FUNC).equals(target)) {
                    targetFunction = true;
                } else if (String.valueOf(GSConstMain.SEARCH_TARGET_OPERATION).equals(target)) {
                    targetOperation = true;
                } else if (String.valueOf(GSConstMain.SEARCH_TARGET_CONTENT).equals(target)) {
                    targetContent = true;
                } else if (String.valueOf(GSConstMain.SEARCH_TARGET_IPADDRESS).equals(target)) {
                    targetIpaddress = true;
                }
            }
        }

        //画面・機能名
        searchMdl.setTartgetFunc(targetFunction);
        //操作
        searchMdl.setTargetOperation(targetOperation);
        //内容
        searchMdl.setTargetContent(targetContent);
        //IPアドレス
        searchMdl.setTargetIpaddress(targetIpaddress);

        return searchMdl;
    }


    /**
     * <br>[機  能] ログレベルの日本語を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param logLevel ログレベル
     * @param reqMdl リクエスト情報
     * @return logLevelDsp 表示用ログレベル
     */
    public static String getLogLevelDsp(String logLevel, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage();
        String logLevelDsp = "";

        if (logLevel.equals(GSConstLog.LEVEL_ERROR)) {
            logLevelDsp = gsMsg.getMessage("man.error");

        } else if (logLevel.equals(GSConstLog.LEVEL_WARN)) {
            logLevelDsp = gsMsg.getMessage("cmn.warning");

        } else if (logLevel.equals(GSConstLog.LEVEL_INFO)) {
            logLevelDsp = gsMsg.getMessage("main.man240.2");

        } else if (logLevel.equals(GSConstLog.LEVEL_TRACE)) {
            logLevelDsp = gsMsg.getMessage("main.man240.3");

        }

        return logLevelDsp;
    }

    /**
     * <br>[機  能] 確認メッセージを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconf プラグインコンフィグ
     * @throws SQLException SQL実行時例外
     * @return kakuninMsg 確認メッセージ
     */
    public String getKakuninMessage(Man250ParamModel paramMdl, Connection con, PluginConfig pconf)
    throws SQLException {

        UDate date1 = new UDate();
        date1.setDate(
                Integer.parseInt(paramMdl.getMan250SvSltYearFr()),
                Integer.parseInt(paramMdl.getMan250SvSltMonthFr()),
                Integer.parseInt(paramMdl.getMan250SvSltDayFr()));
        if (paramMdl.getMan250SvSltHourFr().equals("-1")) {
            date1.setZeroHhMmSs();
        } else {
            date1.setHour(NullDefault.getInt(paramMdl.getMan250SvSltHourFr(), 0));
            date1.setMinute(NullDefault.getInt(paramMdl.getMan250SvSltMinFr(), 0));
            date1.setSecond(0);
        }

        UDate date2 = new UDate();
        date2.setDate(
                Integer.parseInt(paramMdl.getMan250SvSltYearTo()),
                Integer.parseInt(paramMdl.getMan250SvSltMonthTo()),
                Integer.parseInt(paramMdl.getMan250SvSltDayTo()));
        if (paramMdl.getMan250SvSltHourTo().equals("-1")) {
            date2.setMaxHhMmSs();
        } else {
            date2.setHour(NullDefault.getInt(paramMdl.getMan250SvSltHourTo(), 23));
            date2.setMinute(NullDefault.getInt(paramMdl.getMan250SvSltMinTo(), 59));
            date2.setSecond(0);
        }

        String kakuninMsg = "";
        GsMessage gsMsg = new GsMessage(reqMdl__.getLocale());
        kakuninMsg += gsMsg.getMessage("man.run.time")
                      + "&nbsp;"
                      + gsMsg.getMessage("cmn.start")
                      + "&nbsp;：&nbsp;";
        kakuninMsg += UDateUtil.getYymdJ(date1, reqMdl__)
                    + UDateUtil.getSeparateHMJ(date1, reqMdl__);
        kakuninMsg += "<br>";
        kakuninMsg += gsMsg.getMessage("man.run.time")
                      + "&nbsp;"
                      + gsMsg.getMessage("cmn.end")
                      + "&nbsp;：&nbsp;";
        kakuninMsg += UDateUtil.getYymdJ(date2, reqMdl__)
                    + UDateUtil.getSeparateHMJ(date2, reqMdl__);
        kakuninMsg += "<br>";
        if (!paramMdl.getMan250SvSltPlugin().equals("-1")) {
            Plugin plg = pconf.getPlugin(paramMdl.getMan250SvSltPlugin());
            if (plg != null) {
                kakuninMsg += gsMsg.getMessage("cmn.plugin")
                              + "&nbsp;：&nbsp;"
                              + plg.getName(reqMdl__);
                kakuninMsg += "<br>";

            }
        }
        kakuninMsg += gsMsg.getMessage("cmn.loglevel") + "&nbsp;：&nbsp;";
        if (paramMdl.getMan250SvSltLogError().equals("1")) {
            kakuninMsg += gsMsg.getMessage("man.error") + "&nbsp;&nbsp;";
        }
        if (paramMdl.getMan250SvSltLogWarn().equals("1")) {
            kakuninMsg += gsMsg.getMessage("cmn.warning") + "&nbsp;&nbsp;";
        }
        if (paramMdl.getMan250SvSltLogInfo().equals("1")) {
            kakuninMsg += gsMsg.getMessage("main.man240.2") + "&nbsp;&nbsp;";
        }
        if (paramMdl.getMan250SvSltLogTrace().equals("1")) {
            kakuninMsg += gsMsg.getMessage("main.man240.3");
        }
        kakuninMsg += "<br>";

        //実行ユーザ
        int usrSid = Integer.parseInt(paramMdl.getMan250SvSltUser());
        if (usrSid > 0) {
            CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usrmInfModel = usrmInfDao.select(usrSid);
            kakuninMsg += gsMsg.getMessage("man.run.user") + "&nbsp;：&nbsp;";
            kakuninMsg += StringUtilHtml.transToHTmlPlusAmparsant(usrmInfModel.getUsiSei()) + " "
                    + StringUtilHtml.transToHTmlPlusAmparsant(usrmInfModel.getUsiMei());
        }

        return kakuninMsg;
    }

    /**
     * <br>[機  能] ログ出力対象のPluginConfigを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    private PluginConfig __getPluginConfigForLog(PluginConfig pconfig, Connection con)
    throws SQLException {

        PluginConfig ret = new PluginConfig();
        //管理者設定を取得
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> menuPluginIdList = tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);

        Plugin plugin = null;
        plugin = pconfig.getPlugin(GSConst.PLUGINID_COMMON);
        if (plugin != null) {
            ret.addPlugin(plugin);
        }
        for (String pId : menuPluginIdList) {
            plugin = pconfig.getPlugin(pId);
            if (plugin != null && plugin.getLogInfo().isOut()) {
                ret.addPlugin(plugin);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 検索対象がNULLの場合、検索対象のデフォルト値を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    public void setDefultSearchTarget(Man250ParamModel paramMdl) {
        if (paramMdl.getMan250SearchTarget() == null
                || paramMdl.getMan250SearchTarget().length == 0) {
            paramMdl.setMan250SearchTarget(__getDefultSearchTarget());
        }
    }

    /**
     * <br>[機  能] 検索対象のデフォルト値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトターゲット配列
     */
    private static String[] __getDefultSearchTarget() {
        String[] targets = {
                String.valueOf(GSConstMain.SEARCH_TARGET_FUNC),
                String.valueOf(GSConstMain.SEARCH_TARGET_OPERATION),
                String.valueOf(GSConstMain.SEARCH_TARGET_CONTENT),
                String.valueOf(GSConstMain.SEARCH_TARGET_IPADDRESS)
              };
        return targets;
    }
}