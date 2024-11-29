package jp.groupsession.v2.rng.rng050;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.csv.RngCsvWriter;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiKeiroNameModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 申請中案件管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng050Biz.class);
    /** Connection */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng050Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void initDsp(Rng050ParamModel paramMdl, int userSid)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        log__.debug("start");

        //グループ、ユーザコンボの設定
        GroupBiz gBiz = new GroupBiz();
        UserBiz uBiz = new UserBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setRng050groupList(gBiz.getGroupCombLabelList(con__, true, gsMsg));
        paramMdl.setRng050userList(
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
        UDate now = new UDate();
        UDate twoThousand = new UDate();
        twoThousand.setYear(2000);
        int diffYear = UDateUtil.diffYear(now, twoThousand);
        paramMdl.setRng050MinYear(diffYear);

        //年月日の値をセット
        picker.setDateParam(paramMdl, "rng050ApplDateFr",
                "sltApplYearFr", "sltApplMonthFr",
                "sltApplDayFr", shinFrom);
        picker.setDateParam(paramMdl, "rng050ApplDateTo",
                "sltApplYearTo", "sltApplMonthTo",
                "sltApplDayTo", shinTo);
        picker.setDateParam(paramMdl, "rng050LastManageDateFr",
                "sltLastManageYearFr", "sltLastManageMonthFr",
                "sltLastManageDayFr", lastFrom);
        picker.setDateParam(paramMdl, "rng050LastManageDateTo",
                "sltLastManageYearTo", "sltLastManageMonthTo",
                "sltLastManageDayTo", lastTo);

        if (paramMdl.getRngAdminSearchFlg() == 1) {

            RingiDao ringiDao = new RingiDao(con__);
            //最大件数
            int ringiCnt = ringiDao.getSinseiRingiDataCount(__createSearchModel(paramMdl, 0));

            //１ページの最大表示件数
            RngBiz rngBiz = new RngBiz(con__);
            int viewCount = rngBiz.getViewCount(con__, userSid);

            //ページ調整
            int maxPage = ringiCnt / viewCount;
            if ((ringiCnt % viewCount) > 0) {
                maxPage++;
            }
            int page = paramMdl.getRngAdminPageTop();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setRngAdminPageTop(page);
            paramMdl.setRngAdminPageBottom(page);

            //ページコンボ設定
            if (ringiCnt > viewCount) {
                paramMdl.setPageList(PageUtil.createPageOptions(ringiCnt, viewCount));
            }

            //検索結果一覧設定
            List<RingiDataModel> rngDataList
                            = ringiDao.getSinseiRingiDataList(
                                    __createSearchModel(paramMdl, viewCount));
            List<Integer> rngSidList = new ArrayList<Integer>();
            for (RingiDataModel rngData: rngDataList) {
                rngSidList.add(rngData.getRngSid());
            }
            Map<Integer, List<RingiKeiroNameModel>> channelMap = ringiDao.getKeiroName(rngSidList);
            for (RingiDataModel rngData: rngDataList) {
                boolean sasimodosi = false;
                List<RingiKeiroNameModel> channelList = channelMap.get(rngData.getRngSid());
                Iterator<RingiKeiroNameModel> it = channelList.iterator();
                RingiKeiroNameModel prevMdl = null;
                ArrayList<RingiKeiroNameModel> sasiList = new ArrayList<RingiKeiroNameModel>();
                while (it.hasNext()) {
                    RingiKeiroNameModel nextMdl = it.next();
                    if (prevMdl != null) {
                        if (nextMdl.getRksSid() == prevMdl.getRksSid()) {
                            if (prevMdl.getRksCount() > 1 || prevMdl.getPosSid() >= 0) {
                                prevMdl.setUsrgrpName(gsMsg.getMessage("rng.rng050.01"));
                            }
                            it.remove();
                            continue;
                        }
                    }
                    int status = nextMdl.getRksStatus();
                    if (sasimodosi) {
                        sasiList.add(nextMdl); // 差し戻し用にリストへ追加
                    }
                    if (status == RngConst.RNG_RNCSTATUS_CONFIRM) {
                        sasimodosi = true;
                    } else if (status == RngConst.RNG_RNCSTATUS_DENIAL) {
                        // 差し戻し(却下＋以前に確認中あり)
                        if (sasimodosi) {
                            // 確認中～却下までの経路ステータスを更新
                            for (RingiKeiroNameModel sasiMdl : sasiList) {
                                sasiMdl.setRksStatus(RngConst.RNG_RNCSTATUS_DENIAL);
                            }
                            sasimodosi = false;
                        }
                    } else if (status == RngConst.RNG_RNCSTATUS_KOETU) {
                        nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
                    } else if (status == RngConst.RNG_RNCSTATUS_SKIP) {
                        nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
                    }
                    prevMdl = nextMdl;
                }
                List<RingiKeiroNameModel> apprUser = new ArrayList<RingiKeiroNameModel>();
                for (RingiKeiroNameModel channelMdl : channelList) {
                    if (channelMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR) {
                        apprUser.add(channelMdl);
                    }
                }
                rngData.setKeiroNameList(apprUser);
            }

            paramMdl.setRng050rngDataList(rngDataList);
        } else {
            //ページ調整
            paramMdl.setRngAdminPageTop(1);
            paramMdl.setRngAdminPageBottom(1);
        }

        log__.debug("end");
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
    public void exoprtCsv(RequestModel reqMdl, Rng050ParamModel paramMdl,
                          int userSid, String outDir)
    throws CSVException {

        RingiSearchModel searchModel = __createSearchModel(paramMdl, 0);

        //CSVファイルを作成
        RngCsvWriter write = new RngCsvWriter(reqMdl, searchModel);

        write.outputCsv(con__, outDir);
    }

    /**
     * <br>[機  能] 検索結果の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 検索結果の件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Rng050ParamModel paramMdl) throws SQLException {

        RingiDao ringiDao = new RingiDao(con__);
        return ringiDao.getSinseiRingiDataCount(__createSearchModel(paramMdl, 0));
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param viewCount 最大表示件数
     * @return 検索条件
     */
    private RingiSearchModel __createSearchModel(Rng050ParamModel paramMdl, int viewCount) {
        RingiSearchModel searchModel = new RingiSearchModel();
        searchModel.setTitleSearchFlg(true);
        searchModel.setContentSearchFlg(false);
        searchModel.setRngIdSearchFlg(false);

        if (!StringUtil.isNullZeroString(paramMdl.getRngAdminKeyword())) {
            List<String> title = new ArrayList<String>();
            title.add(paramMdl.getRngAdminKeyword());
            searchModel.setKeyword(title);
        }

        searchModel.setGroupSid(paramMdl.getRngAdminGroupSid());
        searchModel.setUserSid(paramMdl.getRngAdminUserSid());

        searchModel.setApplDateFr(createUDate(paramMdl.getRngAdminApplYearFr(),
                                            paramMdl.getRngAdminApplMonthFr(),
                                            paramMdl.getRngAdminApplDayFr()));
        searchModel.setApplDateTo(createUDate(paramMdl.getRngAdminApplYearTo(),
                                            paramMdl.getRngAdminApplMonthTo(),
                                            paramMdl.getRngAdminApplDayTo()));
        searchModel.setLastMagageDateFr(createUDate(paramMdl.getRngAdminLastManageYearFr(),
                                                    paramMdl.getRngAdminLastManageMonthFr(),
                                                    paramMdl.getRngAdminLastManageDayFr()));
        searchModel.setLastMagageDateTo(createUDate(paramMdl.getRngAdminLastManageYearTo(),
                                                    paramMdl.getRngAdminLastManageMonthTo(),
                                                    paramMdl.getRngAdminLastManageDayTo()));

        searchModel.setSortKey(paramMdl.getRngAdminSortKey());
        searchModel.setOrderKey(paramMdl.getRngAdminOrderKey());
        searchModel.setSortKey2(RngConst.RNG_SORT_DATE);
        searchModel.setOrderKey2(RngConst.RNG_ORDER_DESC);

        searchModel.setPage(paramMdl.getRngAdminPageTop());
        searchModel.setMaxCnt(viewCount);
        searchModel.setAdminFlg(true);
        searchModel.setRngType(RngConst.RNG_MODE_SINSEI);

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
     * <br>[機  能] PDF出力用の情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userMdl ユーザ情報
     * @param viewCount 検索件数
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<RingiDataModel> getSearchPdf(
            Rng050ParamModel paramMdl,
            BaseUserModel userMdl,
            int viewCount) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        RingiDao ringiDao = new RingiDao(con__);
        //検索結果一覧設定
        List<RingiDataModel> rngDataList
                        = ringiDao.getSinseiRingiDataList(
                                __createSearchModel(paramMdl, viewCount));
        List<Integer> rngSidList = new ArrayList<Integer>();
        for (RingiDataModel rngData: rngDataList) {
            rngSidList.add(rngData.getRngSid());
        }
        Map<Integer, List<RingiKeiroNameModel>> channelMap = ringiDao.getKeiroName(rngSidList);
        for (RingiDataModel rngData: rngDataList) {
            boolean sasimodosi = false;
            List<RingiKeiroNameModel> channelList = channelMap.get(rngData.getRngSid());
            Iterator<RingiKeiroNameModel> it = channelList.iterator();
            RingiKeiroNameModel prevMdl = null;
            ArrayList<RingiKeiroNameModel> sasiList = new ArrayList<RingiKeiroNameModel>();
            while (it.hasNext()) {
                RingiKeiroNameModel nextMdl = it.next();
                if (prevMdl != null) {
                    if (nextMdl.getRksSid() == prevMdl.getRksSid()) {
                        if (prevMdl.getRksCount() > 1 || prevMdl.getPosSid() >= 0) {
                            prevMdl.setUsrgrpName(gsMsg.getMessage("rng.rng050.01"));
                        }
                        it.remove();
                        continue;
                    }
                }
                int status = nextMdl.getRksStatus();
                if (sasimodosi) {
                    sasiList.add(nextMdl); // 差し戻し用にリストへ追加
                }
                if (status == RngConst.RNG_RNCSTATUS_CONFIRM) {
                    sasimodosi = true;
                } else if (status == RngConst.RNG_RNCSTATUS_DENIAL) {
                    // 差し戻し(却下＋以前に確認中あり)
                    if (sasimodosi) {
                        // 確認中～却下までの経路ステータスを更新
                        for (RingiKeiroNameModel sasiMdl : sasiList) {
                            sasiMdl.setRksStatus(RngConst.RNG_RNCSTATUS_DENIAL);
                        }
                        sasimodosi = false;
                    }
                } else if (status == RngConst.RNG_RNCSTATUS_KOETU) {
                    nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
                } else if (status == RngConst.RNG_RNCSTATUS_SKIP) {
                    nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
                }
                prevMdl = nextMdl;
            }
            List<RingiKeiroNameModel> apprUser = new ArrayList<RingiKeiroNameModel>();
            for (RingiKeiroNameModel channelMdl : channelList) {
                if (channelMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR) {
                    apprUser.add(channelMdl);
                }
            }
            rngData.setKeiroNameList(apprUser);
        }
        return rngDataList;
    }
}
