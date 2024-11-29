package jp.groupsession.v2.rng.rng130;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.csv.RngCsvWriter;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.pdf.RngPdfWriter;
import jp.groupsession.v2.rng.pdf.RngTanPdfModel;
import jp.groupsession.v2.rng.pdf.RngTanPdfUtil;
import jp.groupsession.v2.rng.rng030.Rng030KeiroParam;
import jp.groupsession.v2.rng.rng030.Rng030SingiParam;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng130Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng130Biz.class);

    /** コネクション */
    private Connection con__ = null;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Rng130Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng130Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションのルートパス
     * @param userMdl セッションユーザ情報
     * @param isAdmin 管理者か否か true:管理者, false:一般ユーザ
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(RequestModel reqMdl, Rng130ParamModel paramMdl,
            String appRoot, BaseUserModel userMdl, boolean isAdmin)
                    throws IOException, IOToolsException, SQLException,
                    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        int userSid = userMdl.getUsrsid(); // ログインユーザSID


        //共有カテゴリ情報取得
        getCategory(paramMdl, con__, reqMdl, userSid, isAdmin);
        //グループ、ユーザコンボの設定
        GroupBiz gBiz = new GroupBiz();
        UserBiz uBiz = new UserBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setRng130groupList(gBiz.getGroupCombLabelList(con__, true, gsMsg));
        paramMdl.setRng130userList(
                uBiz.getNormalUserLabelList(con__, paramMdl.getSltGroupSid(), null, true, gsMsg));

        DateTimePickerBiz picker = new DateTimePickerBiz();
        //開始From
        String shinFrom = gsMsg.getMessage("rng.83");
        //開始To
        String shinTo = gsMsg.getMessage("rng.84");
        //終了From
        String lastFrom = gsMsg.getMessage("rng.78");
        //終了To
        String lastTo = gsMsg.getMessage("rng.79");

        //2000年からの年数を取得
        UDate todayYear = new UDate();
        UDate twoThousand = new UDate();
        twoThousand.setYear(2000);
        int diffYear = UDateUtil.diffYear(todayYear, twoThousand);
        paramMdl.setRng130MinYear(diffYear);

        //年月日の値をセット
        picker.setDateParam(paramMdl, "rng130ApplDateFr",
                "sltApplYearFr", "sltApplMonthFr",
                "sltApplDayFr", shinFrom);
        picker.setDateParam(paramMdl, "rng130ApplDateTo",
                "sltApplYearTo", "sltApplMonthTo",
                "sltApplDayTo", shinTo);
        picker.setDateParam(paramMdl, "rng130LastManageDateFr",
                "sltLastManageYearFr", "sltLastManageMonthFr",
                "sltLastManageDayFr", lastFrom);
        picker.setDateParam(paramMdl, "rng130LastManageDateTo",
                "sltLastManageYearTo", "sltLastManageMonthTo",
                "sltLastManageDayTo", lastTo);

        //ソートキー設定
        paramMdl.setSortKeyList(createSortKeyList(paramMdl.getRng130Type()));

        //アカウント情報取得
        RngDairiUserDao dairiDao = new RngDairiUserDao(con__);
        UDate now = new UDate();
        AccountDataModel adMdl = new AccountDataModel();
        adMdl.setAccountSid(userSid);
        adMdl.setAccountName(reqMdl.getSmodel().getUsisei() + " " + reqMdl.getSmodel().getUsimei());
        adMdl.setUsrUkoFlg(0);
        List<AccountDataModel> accountList = new ArrayList<AccountDataModel>();
        List<AccountDataModel> dairiList = dairiDao.select(userSid, now, now);
        accountList.add(adMdl);
        accountList.addAll(dairiList);
        paramMdl.setRng010AccountList(accountList);

        if (paramMdl.getRng130searchFlg() == 1) {

            RingiDao ringiDao = new RingiDao(con__);
            int type = paramMdl.getSvRng130Type();

            //最大件数
            int ringiCnt = ringiDao.getRingiDataCount(
                    __createSearchModel(paramMdl, userSid, 0, false), type);

            //１ページの最大表示件数
            RngBiz rngBiz = new RngBiz(con__);
            int viewCount = rngBiz.getViewCount(con__, userSid);

            //ページ調整
            int maxPage = ringiCnt / viewCount;
            if ((ringiCnt % viewCount) > 0) {
                maxPage++;
            }
            int page = paramMdl.getRng130pageTop();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setRng130pageTop(page);
            paramMdl.setRng130pageBottom(page);

            //ページコンボ設定
            if (ringiCnt > viewCount) {
                paramMdl.setPageList(PageUtil.createPageOptions(ringiCnt, viewCount));
            }

            //検索結果一覧設定
            List<RingiDataModel> rngDataList
                            = ringiDao.getRingiDataList(
                                        __createSearchModel(paramMdl, userSid, viewCount, false),
                                        type);

            paramMdl.setRng130rngDataList(rngDataList);
        } else {
            //ページ調整
            paramMdl.setRng130pageTop(1);
            paramMdl.setRng130pageBottom(1);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] 検索結果の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @return 検索結果の件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Rng130ParamModel paramMdl, int userSid) throws SQLException {

        RingiDao ringiDao = new RingiDao(con__);
        return ringiDao.getRingiDataCount(__createSearchModel(paramMdl, userSid, 0, true),
                                        paramMdl.getRng130Type());
    }
    /**
     * <br>[機  能] 検索結果の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @param prevSearch 前回検索時の条件で検索するか
     * @return 検索結果の件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Rng130ParamModel paramMdl,
            int userSid, boolean prevSearch) throws SQLException {
        RingiDao ringiDao = new RingiDao(con__);
        return ringiDao.getRingiDataCount(__createSearchModel(paramMdl, userSid, 0, !prevSearch),
                                        paramMdl.getRng130Type());

    }
    /**
     * <br>[機  能] ソートキーの一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param type 種別
     * @return ソートキーの一覧
     */
    public List<LabelValueBean> createSortKeyList(int type) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String title = gsMsg.getMessage("cmn.title");
        String user = gsMsg.getMessage("rng.47");
        String shiDate = gsMsg.getMessage("rng.application.date");
        String recDate = gsMsg.getMessage("cmn.received.date");
        String lastDate = gsMsg.getMessage("rng.105");
        String creDate = gsMsg.getMessage("rng.37");
        String status = gsMsg.getMessage("cmn.status");

        List<LabelValueBean> sortKeyList = new ArrayList<LabelValueBean>();
        sortKeyList.add(new LabelValueBean(title, String.valueOf(RngConst.RNG_SORT_TITLE)));
        if (type != RngConst.RNG_MODE_SOUKOU) {
            sortKeyList.add(new LabelValueBean(user, String.valueOf(RngConst.RNG_SORT_NAME)));
            sortKeyList.add(new LabelValueBean(shiDate, String.valueOf(RngConst.RNG_SORT_DATE)));
        }
        if (type == RngConst.RNG_MODE_JYUSIN) {
            sortKeyList.add(new LabelValueBean(recDate, String.valueOf(RngConst.RNG_SORT_JYUSIN)));
        }
        if (type == RngConst.RNG_MODE_SINSEI || type == RngConst.RNG_MODE_KANRYO) {
            sortKeyList.add(new LabelValueBean(lastDate,
                                            String.valueOf(RngConst.RNG_SORT_KAKUNIN)));
        }
        if (type == RngConst.RNG_MODE_KANRYO) {
            sortKeyList.add(new LabelValueBean(status, String.valueOf(RngConst.RNG_SORT_KEKKA)));
        }
        if (type == RngConst.RNG_MODE_SOUKOU) {
            sortKeyList.add(new LabelValueBean(creDate, String.valueOf(RngConst.RNG_SORT_TOUROKU)));
        }

        return sortKeyList;
    }

    /**
     * <br>[機  能] CSVエクスポート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @param outDir 出力ディレクトリパス
     * @throws CSVException CSV実行例外
     */
    public void exoprtCsv(RequestModel reqMdl, Rng130ParamModel paramMdl,
                          int userSid, String outDir)
    throws CSVException {

        RingiSearchModel searchModel = __createSearchModel(paramMdl, userSid, 0, false);
        searchModel.setRngType(paramMdl.getSvRng130Type());

        //CSVファイルを作成
        RngCsvWriter write = new RngCsvWriter(reqMdl, searchModel);

        write.outputCsv(con__, outDir);
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param viewCount 最大表示件数
     * @param countFlg 最大件数フラグ
     * @return 検索条件
     */
    private RingiSearchModel __createSearchModel(Rng130ParamModel paramMdl, int userSid,
                                                int viewCount, boolean countFlg) {
        RingiSearchModel searchModel = new RingiSearchModel();

        if (countFlg) {
            searchModel.setUserSid(paramMdl.getRng010ViewAccount());
            searchModel.setRngType(paramMdl.getRng130Type());
            searchModel.setCategorySid(paramMdl.getRng010SearchCategory());
            searchModel.setApplGroupSid(paramMdl.getSltGroupSid());
            searchModel.setApplUserSid(paramMdl.getSltUserSid());
            searchModel.setKeyword(RngBiz.createKeywordList(paramMdl.getRngKeyword()));
            searchModel.setKeywordType(paramMdl.getRng130keyKbn());
            searchModel.setTitleSearchFlg(paramMdl.getRng130searchSubject1() == 1);
            searchModel.setContentSearchFlg(paramMdl.getRng130searchSubject2() == 1);
            searchModel.setRngIdSearchFlg(paramMdl.getRng130searchSubject3() == 1
                    && paramMdl.getRng130Type() != RngConst.RNG_MODE_SOUKOU);

            searchModel.setApplDateFr(createUDate(paramMdl.getSltApplYearFr(),
                                        paramMdl.getSltApplMonthFr(),
                                        paramMdl.getSltApplDayFr()));
            searchModel.setApplDateTo(createUDate(paramMdl.getSltApplYearTo(),
                                        paramMdl.getSltApplMonthTo(),
                                        paramMdl.getSltApplDayTo()));
            searchModel.setLastMagageDateFr(createUDate(paramMdl.getSltLastManageYearFr(),
                                        paramMdl.getSltLastManageMonthFr(),
                                        paramMdl.getSltLastManageDayFr()));
            searchModel.setLastMagageDateTo(createUDate(paramMdl.getSltLastManageYearTo(),
                                        paramMdl.getSltLastManageMonthTo(),
                                        paramMdl.getSltLastManageDayTo()));
            searchModel.setStatusId(paramMdl.getRng130Status());

        } else {
            searchModel.setUserSid(paramMdl.getSvRngViewAccount());
            searchModel.setRngType(paramMdl.getSvRng130Type());
            searchModel.setCategorySid(paramMdl.getSvRng130Category());
            searchModel.setApplGroupSid(paramMdl.getSvGroupSid());
            searchModel.setApplUserSid(paramMdl.getSvUserSid());
            searchModel.setKeyword(RngBiz.createKeywordList(paramMdl.getSvRngKeyword()));
            searchModel.setKeywordType(paramMdl.getSvRng130keyKbn());
            searchModel.setTitleSearchFlg(paramMdl.getSvRng130searchSubject1() == 1);
            searchModel.setContentSearchFlg(paramMdl.getSvRng130searchSubject2() == 1);
            searchModel.setRngIdSearchFlg(
                    (paramMdl.getSvRng130searchSubject3() == 1
                    && paramMdl.getSvRng130Type() != RngConst.RNG_MODE_SOUKOU));

            searchModel.setApplDateFr(createUDate(paramMdl.getSvApplYearFr(),
                                        paramMdl.getSvApplMonthFr(),
                                        paramMdl.getSvApplDayFr()));
            searchModel.setApplDateTo(createUDate(paramMdl.getSvApplYearTo(),
                                        paramMdl.getSvApplMonthTo(),
                                        paramMdl.getSvApplDayTo()));
            searchModel.setLastMagageDateFr(createUDate(paramMdl.getSvLastManageYearFr(),
                                        paramMdl.getSvLastManageMonthFr(),
                                        paramMdl.getSvLastManageDayFr()));
            searchModel.setLastMagageDateTo(createUDate(paramMdl.getSvLastManageYearTo(),
                                        paramMdl.getSvLastManageMonthTo(),
                                        paramMdl.getSvLastManageDayTo()));
            searchModel.setStatusId(paramMdl.getSvRng130Status());
        }


        searchModel.setSortKey(paramMdl.getSvSortKey1());
        searchModel.setOrderKey(paramMdl.getSvRng130orderKey1());
        searchModel.setSortKey2(paramMdl.getSvSortKey2());
        searchModel.setOrderKey2(paramMdl.getSvRng130orderKey2());
        searchModel.setPage(paramMdl.getRng130pageTop());
        searchModel.setMaxCnt(viewCount);
        searchModel.setAdminFlg(false);

        return searchModel;
    }

    /**
     * <br>[機  能] UDateのインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    public UDate createUDate(int year, int month, int day) {
        if (year <= 0 || month <= 0 || day <= 0) {
            return null;
        }

        UDate date = UDate.getInstance(0);
        date.setDate(year, month, day);
        return date;
    }

    /**
     * <br>[機  能] 共有カテゴリ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid ユーザSID
     * @param isAdmin 管理者ユーザ
     * @throws SQLException SQL実行時例外
     */
    public void getCategory(Rng130ParamModel paramMdl,
            Connection con, RequestModel reqMdl, int userSid,
            boolean isAdmin)
            throws SQLException {

        ArrayList<RngTemplateCategoryModel> categoryList; // テンプレートカテゴリ一覧
        ArrayList<LabelValueBean> combs;

        RngBiz biz = new RngBiz(con);
        //共有のカテゴリを取得する(システムorプラグイン管理者も制限チェックあり)
        categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                userSid, isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);

        //共有テンプレートカテゴリのコンボリストを設定
        combs = biz.createCategoryComb(reqMdl, categoryList, true,
                isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);
        paramMdl.setRng010CategoryList(combs);
    }

    /**
     * <br>[機  能] PDF出力用の情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userMdl ユーザ情報
     * @param searchCount 検索件数
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<RingiDataModel> getSearchPdf(
            Rng130ParamModel paramMdl,
            BaseUserModel userMdl,
            int searchCount) throws SQLException {

        RingiDao ringiDao = new RingiDao(con__);
        List<RingiDataModel> rngDataList
                        = ringiDao.getRingiDataList(
                                __createSearchModel(paramMdl, userMdl.getUsrsid(),
                                            searchCount, false),
                                    paramMdl.getSvRng130Type());
        return rngDataList;
    }

    /**
     * <br>[機  能] スケジュール(一括)をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param userSid セッションユーザSID
     * @return pdfModel 施設予約単票PDF情報
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行例外
     */
    public RngTanPdfModel createRngPdf(
            Rng130ParamModel paramMdl,
            String appRootPath,
            String outTempDir,
            int userSid)
                    throws IOException, SQLException {
        OutputStream oStream = null;

        //スケジュール(単票)PDF出力用モデル
        RngPdfWriter pdfWriter = new RngPdfWriter(con__, reqMdl__);
        RngTanPdfModel pdfModel = pdfWriter.getRngPdfDataList(
                                paramMdl.getRngSid(),
                                paramMdl.getRngProcMode(),
                                paramMdl.getRngApprMode(),
                                userSid);

        String saveFileName = "rngtan" + reqMdl__.getSmodel().getUsrsid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RngTanPdfUtil pdfUtil = new RngTanPdfUtil(reqMdl__);
            pdfUtil.createRngTanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("稟議(単票)PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("稟議(単票)PDF出力を終了します。");

        return pdfModel;
    }

    /**
    *
    * <br>[機  能] どこの経路で確認しているのかを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param rngSidList 稟議SIDリスト
    * @param userSid ユーザSID
    * @param procMode 稟議モード
    * @return 経路SID
    * @throws SQLException SQLException
    */
   public ArrayList<Integer> viewKeiroSid(ArrayList<Integer> rngSidList, int userSid, int procMode)
           throws SQLException {

       ArrayList<Integer> ret = new ArrayList<Integer>();
       for (int rngSid : rngSidList) {
         //どこの経路なのか確認
           RngKeiroStepDao kDao = new RngKeiroStepDao(con__);
           List<Rng030KeiroParam> kList = kDao.getRksSid(rngSid, userSid);
           int rksSid = 0;
           int maxRksSid = 0;
           for (Rng030KeiroParam kMdl : kList) {
               if (procMode == RngConst.RNG_MODE_JYUSIN) {
                   if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                       rksSid = kMdl.getKeiroStepSid();
                   }
               } else if (procMode == RngConst.RNG_MODE_SINSEI) {
                   if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                       rksSid = kMdl.getKeiroStepSid();
                   }
               } else if (procMode == RngConst.RNG_MODE_KANRYO) {
                   rksSid = kMdl.getKeiroStepSid();
               } else if (procMode == RngConst.RNG_MODE_KOETU) {
                   if (kMdl.getKeiroStatus() != RngConst.RNG_RNCSTATUS_CONFIRM) {
                       if (kMdl.getKeiroSingi() == RngConst.RNG_RNCTYPE_APPR
                               && kMdl.getKeiroKoetuSizi() == RngConst.RNG_KOETU_YES) {
                           rksSid = kMdl.getKeiroStepSid();
                       }
                   }
               }
               maxRksSid = kMdl.getKeiroStepSid();
           }
           if (rksSid == 0) {
               rksSid = maxRksSid;
           }
           ret.add(rksSid);
       }
       return ret;
   }

   /**
    *
    * <br>[機  能]経路リスト取得
    * <br>[解  説]
    * <br>[備  考]
    * @param ringiDao RingiDao
    * @param rngSidList 稟議SIDリスト
    * @throws SQLException SQLException
    * @return keiroList List<Rng030KeiroParam>
    */
     public HashMap<Integer, List<Rng030KeiroParam>> getKerio(
             RingiDao ringiDao, ArrayList<Integer> rngSidList)
           throws SQLException {
       //稟議経路情報を設定
       HashMap<Integer, List<Rng030KeiroParam>> ret
               = new HashMap<Integer, List<Rng030KeiroParam>>();
       List<Rng030KeiroParam> keiroList = ringiDao.getKeiroList(rngSidList);
       List<Rng030SingiParam> singiList = ringiDao.getSingiList(rngSidList);

       //審議リストを経路SIDごとのMapを生成
       Map<Integer, List<Rng030SingiParam>> rksSidSingiListMap
         = new HashMap<Integer, List<Rng030SingiParam>>();
       for (Rng030SingiParam sMdl : singiList) {
           Integer rksSid = Integer.valueOf(sMdl.getKeiroSid());
           List<Rng030SingiParam> sList;
           if (rksSidSingiListMap.containsKey(rksSid)) {
               sList = rksSidSingiListMap.get(rksSid);
           } else {
               sList = new ArrayList<Rng030SingiParam>();
               rksSidSingiListMap.put(rksSid, sList);
           }
           sList.add(sMdl);
       }

       for (Rng030KeiroParam kMdl : keiroList) {
           Integer rksSid = Integer.valueOf(kMdl.getKeiroStepSid());
           List<Rng030SingiParam> sList;
           if (rksSidSingiListMap.containsKey(rksSid)) {
               sList = rksSidSingiListMap.get(rksSid);
           } else {
               sList = new ArrayList<Rng030SingiParam>();
           }
           int keiroCnt = 0;
           int delUserCnt = 0;
           String sKoetuUserMei = null;
           for (Rng030SingiParam sMdl : sList) {
               sKoetuUserMei = sMdl.getSingiKoetu();
               keiroCnt += 1;
               if (sMdl.getUserJkbn() != 0) {
                   delUserCnt += 1;
               }
           }
           if (keiroCnt == delUserCnt) {
               kMdl.setKeiroDelFlg(1);
           }

           kMdl.setSingiList(sList);
           kMdl.setKeiroCount(keiroCnt);

           if (keiroCnt > 0) {
               int nPro = kMdl.getKeiroProgress();
               int nMes = 0;
               if (nPro == 0) {
                   // 全員の審議
                   nMes = RngConst.RNG_OUT_CONDITION_DELIBERATION;
               } else if (nPro == 1) {
                   // 全員の承認
                   nMes = RngConst.RNG_OUT_CONDITION_APPROVAL;
               } else if (nPro == 2) {
                   // 人かの承認
                   nMes = RngConst.RNG_OUT_CONDITION_NUMBER;
               } else {
                   // 割かの承認
                   nMes = RngConst.RNG_OUT_CONDITION_RATE;
               }
               kMdl.setKeiroMessage(nMes);
           }
           if (kMdl.getKeiroStatus() == 6) {
               kMdl.setKeiroKoetuMei(sKoetuUserMei);
           }

           List<Rng030KeiroParam> retModel = new ArrayList<Rng030KeiroParam>();
           if (ret.containsKey(kMdl.getRngSid())) {
               retModel = ret.get(kMdl.getRngSid());
           }
           retModel.add(kMdl);
           ret.put(kMdl.getRngSid(), retModel);
       }
       return ret;
   }
}
