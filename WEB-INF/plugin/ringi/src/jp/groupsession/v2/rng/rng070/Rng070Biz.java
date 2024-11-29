package jp.groupsession.v2.rng.rng070;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.csv.RngCsvWriter;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngBinDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiKeiroNameModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.rng050.Rng050Biz;
import jp.groupsession.v2.rng.rng090.Rng090Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 完了案件管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng090Biz.class);
    /** Connection */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl リクエスト情報
     */
    public Rng070Biz(Connection con, RequestModel reqMdl) {
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
    public void initDsp(Rng070ParamModel paramMdl, int userSid)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //グループ、ユーザコンボの設定
        GroupBiz gBiz = new GroupBiz();
        UserBiz uBiz = new UserBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setRng070groupList(gBiz.getGroupCombLabelList(con__, true, gsMsg));
        paramMdl.setRng070userList(
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
        paramMdl.setRng070MinYear(diffYear);

        //年月日の値をセット
        picker.setDateParam(paramMdl, "rng070ApplDateFr",
                "sltApplYearFr", "sltApplMonthFr",
                "sltApplDayFr", shinFrom);
        picker.setDateParam(paramMdl, "rng070ApplDateTo",
                "sltApplYearTo", "sltApplMonthTo",
                "sltApplDayTo", shinTo);
        picker.setDateParam(paramMdl, "rng070LastManageDateFr",
                "sltLastManageYearFr", "sltLastManageMonthFr",
                "sltLastManageDayFr", lastFrom);
        picker.setDateParam(paramMdl, "rng070LastManageDateTo",
                "sltLastManageYearTo", "sltLastManageMonthTo",
                "sltLastManageDayTo", lastTo);
        log__.debug("paramMdl.getRngAdminSearchFlg() == " + paramMdl.getRngAdminSearchFlg());
        if (paramMdl.getRngAdminSearchFlg() == 1) {
            //検索結果件数
            int searchCnt = getSearchCount(paramMdl);

            //１ページの最大表示件数
            RngBiz rngBiz = new RngBiz(con__);
            int viewCount = rngBiz.getViewCount(con__, userSid);

            //ページ調整
            int maxPage = searchCnt / viewCount;
            if ((searchCnt % viewCount) > 0) {
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
            paramMdl.setRngAdminPageList(
                    PageUtil.createPageOptions(searchCnt, viewCount));

            //検索処理
            __search(paramMdl, viewCount);
        }

    }

    /**
     * <br>[機  能] 完了稟議件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 完了稟議件数
     * @throws SQLException 例外
     */
    public int getSearchCount(Rng070ParamModel paramMdl) throws SQLException {
        RingiDao dao = new RingiDao(con__);
        int searchCnt = dao.getKanryoRingiDataCount(__createSearchModel(paramMdl, 0));

        return searchCnt;
    }
    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param viewCount １ページの最大表示件数
     * @throws SQLException SQL実行時例外
     */
    private void __search(Rng070ParamModel paramMdl, int viewCount) throws SQLException {
        RingiDao dao = new RingiDao(con__);
        log__.debug("稟議情報を取得します");
        List<RingiDataModel> rngDataList = new ArrayList<RingiDataModel>();
        /* 検索結果を格納 */
        rngDataList = dao.getKanryoRingiDataList(__createSearchModel(paramMdl, viewCount));
        List<Integer> rngSidList = new ArrayList<Integer>();
        for (RingiDataModel rngData: rngDataList) {
            rngSidList.add(rngData.getRngSid());
        }
        Map<Integer, List<RingiKeiroNameModel>> channelMap = dao.getKeiroName(rngSidList);

        //稟議経路情報を取得
        for (RingiDataModel rngData : rngDataList) {
            List<RingiKeiroNameModel> channelList = channelMap.get(rngData.getRngSid());
            Iterator<RingiKeiroNameModel> it = channelList.iterator();
            RingiKeiroNameModel prevMdl = null;
            boolean sasimodosi = false;
            ArrayList<RingiKeiroNameModel> sasiList = new ArrayList<RingiKeiroNameModel>();
            while (it.hasNext()) {
                RingiKeiroNameModel nextMdl = it.next();
                if (prevMdl != null) {
                    if (nextMdl.getRksSid() == prevMdl.getRksSid()) {
                        if (prevMdl.getRksCount() > 1 || prevMdl.getPosSid() >= 0) {
                            GsMessage gsMsg = new GsMessage(reqMdl__);
                            prevMdl.setUsrgrpName(gsMsg.getMessage("rng.rng050.01"));
                        }
                        it.remove();
                    }
                }
                int status = nextMdl.getRksStatus();
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
                } else if (status == RngConst.RNG_RNCSTATUS_CONFIRMATION) {
                    nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
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

        //稟議情報をセット
        paramMdl.setRng070dataList(rngDataList);
    }

    /**
     * <br>[機  能] 稟議テンプレートの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid userSid
     * @param rngSid 稟議SID
     * @throws Exception 例外
     */
    public void deleteKanryoRng(Rng070ParamModel paramMdl, int userSid, int rngSid)
    throws Exception {

        RngBinDao rtbdao = new RngBinDao(con__);
        RngRndataDao rtdao = new RngRndataDao(con__);
        //指定した稟議テンプレートSIDと関連するバイナリSIDを取得
        ArrayList<String> binlist = rtbdao.selectBinList(rngSid);
        //現在日時
        UDate now = new UDate();

        //もし、バイナリSIDリストが空でなければ、
        if (binlist != null || !binlist.isEmpty()) {
            //添付ファイルの論理削除を行う。
            log__.debug("// 添付ファイルの論理削除を行います = " + binlist.size());
            for (String bin : binlist) {
                CmnBinfDao dao = new CmnBinfDao(con__);
                CmnBinfModel mdl = new CmnBinfModel();
                mdl.setBinUpuser(userSid);
                mdl.setBinUpdate(now);
                mdl.setBinJkbn(GSConst.JTKBN_DELETE);
                mdl.setBinSid(Long.parseLong(bin));
                //論理削除実行
                dao.removeBinData(mdl);
            }

        } else {
            //そうでなければ論理削除を行わない。
            log__.debug("// 添付ファイルの論理削除を行いませんでした。");
        }

        //指定した稟議テンプレートSIDのテンプレートを削除
        rtdao.delete(rngSid);
        //指定した稟議テンプレートSIDのバイナリ情報を全て削除
        rtbdao.delete(rngSid, userSid);
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
    private RingiSearchModel __createSearchModel(Rng070ParamModel paramMdl, int viewCount) {
        RingiSearchModel searchModel = new RingiSearchModel();
        searchModel.setTitleSearchFlg(true);
        searchModel.setContentSearchFlg(false);
        searchModel.setRngIdSearchFlg(false);
        Rng050Biz biz050 = new Rng050Biz(con__, reqMdl__);

        if (!StringUtil.isNullZeroString(paramMdl.getRngAdminKeyword())) {
            List<String> title = new ArrayList<String>();
            title.add(paramMdl.getRngAdminKeyword());
            searchModel.setKeyword(title);
        }
        searchModel.setGroupSid(paramMdl.getRngAdminGroupSid());
        searchModel.setUserSid(paramMdl.getRngAdminUserSid());

        searchModel.setApplDateFr(biz050.createUDate(paramMdl.getRngAdminApplYearFr(),
                                                    paramMdl.getRngAdminApplMonthFr(),
                                                    paramMdl.getRngAdminApplDayFr()));
        searchModel.setApplDateTo(biz050.createUDate(paramMdl.getRngAdminApplYearTo(),
                                                    paramMdl.getRngAdminApplMonthTo(),
                                                    paramMdl.getRngAdminApplDayTo()));
        searchModel.setLastMagageDateFr(biz050.createUDate(paramMdl.getRngAdminLastManageYearFr(),
                                                        paramMdl.getRngAdminLastManageMonthFr(),
                                                        paramMdl.getRngAdminLastManageDayFr()));
        searchModel.setLastMagageDateTo(biz050.createUDate(paramMdl.getRngAdminLastManageYearTo(),
                                                        paramMdl.getRngAdminLastManageMonthTo(),
                                                        paramMdl.getRngAdminLastManageDayTo()));

        searchModel.setSortKey(paramMdl.getRngAdminSortKey());
        searchModel.setOrderKey(paramMdl.getRngAdminOrderKey());
        searchModel.setPage(paramMdl.getRngAdminPageTop());
        searchModel.setMaxCnt(viewCount);
        searchModel.setAdminFlg(true);
        searchModel.setRngType(RngConst.RNG_MODE_KANRYO);
        searchModel.setStatusId(paramMdl.getRng070Kekka());

        return searchModel;
    }

    /**
     * <br>[機  能] 稟議名一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return stringリスト
     * @throws Exception 実行例外
     */
    public ArrayList<String> getRingiNameList(Rng070ParamModel paramMdl, Connection con)
    throws Exception {

        ArrayList<String> ret = new ArrayList<String>();
        if (paramMdl.getRng070dellist() != null) {
            ArrayList<Integer> rngSids = new ArrayList<Integer>();
            for (String strRngSid : paramMdl.getRng070dellist()) {
                rngSids.add(Integer.valueOf(strRngSid));
            }

            RngRndataDao dao = new RngRndataDao(con);
            ArrayList<RngRndataModel> rngList = dao.selectList(rngSids);
            for (RngRndataModel mdl : rngList) {
                ret.add(mdl.getRngTitle());
            }
        }
        return ret;
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
    public void exoprtCsv(RequestModel reqMdl, Rng070ParamModel paramMdl,
                          int userSid, String outDir)
    throws CSVException {

        RingiSearchModel searchModel = __createSearchModel(paramMdl, 0);

        //CSVファイルを作成
        RngCsvWriter write = new RngCsvWriter(reqMdl, searchModel);

        write.outputCsv(con__, outDir);
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
            Rng070ParamModel paramMdl,
            BaseUserModel userMdl,
            int viewCount) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        RingiDao dao = new RingiDao(con__);
        List<RingiDataModel> rngDataList = new ArrayList<RingiDataModel>();
        /* 検索結果を格納 */
        rngDataList = dao.getKanryoRingiDataList(__createSearchModel(paramMdl, viewCount));
        List<Integer> rngSidList = new ArrayList<Integer>();
        for (RingiDataModel rngData: rngDataList) {
            rngSidList.add(rngData.getRngSid());
        }
        Map<Integer, List<RingiKeiroNameModel>> channelMap = dao.getKeiroName(rngSidList);

        //稟議経路情報を取得
        for (RingiDataModel rngData : rngDataList) {
            List<RingiKeiroNameModel> channelList = channelMap.get(rngData.getRngSid());
            Iterator<RingiKeiroNameModel> it = channelList.iterator();
            RingiKeiroNameModel prevMdl = null;
            boolean sasimodosi = false;
            ArrayList<RingiKeiroNameModel> sasiList = new ArrayList<RingiKeiroNameModel>();
            while (it.hasNext()) {
                RingiKeiroNameModel nextMdl = it.next();
                if (prevMdl != null) {
                    if (nextMdl.getRksSid() == prevMdl.getRksSid()) {
                        if (prevMdl.getRksCount() > 1 || prevMdl.getPosSid() >= 0) {
                            prevMdl.setUsrgrpName(gsMsg.getMessage("rng.rng050.01"));
                        }
                        it.remove();
                    }
                }
                int status = nextMdl.getRksStatus();
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
                } else if (status == RngConst.RNG_RNCSTATUS_CONFIRMATION) {
                    nextMdl.setRksStatus(RngConst.RNG_RNCSTATUS_APPR);
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
