package jp.groupsession.v2.rng;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngUsedDataBiz;
import jp.groupsession.v2.rng.dao.RngAutodeleteDao;
import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;
import jp.groupsession.v2.rng.dao.RngDatausedSumDao;
import jp.groupsession.v2.rng.dao.RngTemplateBinDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateFormDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroConditionDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.SearchParamForRCT;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.SearchParamForRTP;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroUserDao;
import jp.groupsession.v2.rng.model.RngAutodeleteModel;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.model.RngDatausedSumModel;
import jp.groupsession.v2.rng.model.RngDeleteModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 施設予約についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(RingiBatchListenerImpl.class);

    /**
     * <br>[機  能] 日次バッチ起動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.info("稟議 日次バッチ処理開始");
        String pluginName = "稟議";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {
            //稟議 自動削除条件を取得
            RngAutodeleteDao autoDelDao = new RngAutodeleteDao(con);
            RngAutodeleteModel autoDelMdl = autoDelDao.getData();

            if (autoDelMdl != null) {
                List<RngDeleteModel> delList = new ArrayList<RngDeleteModel>();
                //申請中
                __addDeleteModel(delList,
                        autoDelMdl.getRadPendingKbn(),
                        RngDeleteModel.DELTYPE_PENDING,
                        autoDelMdl.getRadPendingYear(),
                        autoDelMdl.getRadPendingMonth(),
                        autoDelMdl.getRadPendingDay());

                //完了
                __addDeleteModel(delList,
                        autoDelMdl.getRadCompleteKbn(),
                        RngDeleteModel.DELTYPE_COMPLETE,
                        autoDelMdl.getRadCompleteYear(),
                        autoDelMdl.getRadCompleteMonth(),
                        autoDelMdl.getRadCompleteDay());

                //草稿
                __addDeleteModel(delList,
                        autoDelMdl.getRadDraftKbn(),
                        RngDeleteModel.DELTYPE_DRAFT,
                        autoDelMdl.getRadDraftYear(),
                        autoDelMdl.getRadDraftMonth(),
                        autoDelMdl.getRadDraftDay());

                RngBiz rngBiz = new RngBiz(null);
                rngBiz.deleteRngData(con, delList, 0);
                con.commit();
            }
            RngTemplateKeiroDao rtkDao = new RngTemplateKeiroDao(con);

            // 論理削除されたテンプレート（稟議で使用されていない）を削除
            RngTemplateDao rtpDao = new RngTemplateDao(con);
            List<RngTemplateModel> rtpList = rtpDao.selectSakujoTemp();
            RngTemplateFormDao rtfDao = new RngTemplateFormDao(con);
            RngTemplateBinDao rtbDao = new RngTemplateBinDao(con);
            CmnBinfDao cDao = new CmnBinfDao(con);

            if (!rtpList.isEmpty() && rtpList.size() > 0) {

                //稟議テンプレート情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
                RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con);
                usedDataBiz.insertTemplateDataSize(rtpList, false);

                rtpDao.deleteComp(rtpList);
                rtfDao.deleteComp(rtpList);
                for (RngTemplateModel rtpMdl : rtpList) {
                    //添付情報削除
                    List<Long> binSidList = rtbDao.getBinSid(rtpMdl.getRtpSid());
                    if (binSidList.size() > 0) {
                        CmnBinfModel cbMdl = new CmnBinfModel();
                        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
                        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
                        cbMdl.setBinUpdate(new UDate());
                        cDao.updateJKbn(cbMdl, binSidList);
                        rtbDao.deleteTpl(rtpMdl.getRtpSid());
                    }

                    rtkDao.deleteRonri(
                      new SearchParamForRTP(rtpMdl.getRtpSid(),
                              rtpMdl.getRtpVer()));
                }
            }
            // どの論理削除済み稟議テンプレートでも使用されていない経路テンプレートを取得
            RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con);
            List<RngChannelTemplateModel> rctList = rctDao.selectSakujoChannel();

            if (!rctList.isEmpty() && rctList.size() > 0) {
                rctDao.deleteNoUse(rctList);
                for (RngChannelTemplateModel rctMdl : rctList) {
                    rtkDao.deleteRonri(
                      new SearchParamForRCT(rctMdl.getRctSid(),
                              rctMdl.getUsrSid()));
                }
            }
            con.commit();

            // どの稟議、稟議テンプレート、経路テンプレート、複写用経路でも使用されていない経路テンプレートステップを取得
            RngTemplateKeiroUserDao rtuDao = new RngTemplateKeiroUserDao(con);
            RngTemplateKeiroConditionDao rtcDao = new RngTemplateKeiroConditionDao(con);
            List<RngTemplateKeiroModel> rtkList = rtkDao.selectTargetDel();
            // 経路テンプレートの物理削除
            if (!rtkList.isEmpty() && rtkList.size() > 0) {
                rtkDao.deleteNoUseStep(rtkList);
                rtuDao.deleteNoUseStep(rtkList);
                rtcDao.deleteNoUseStep(rtkList);
            }
            con.commit();
            log__.info("稟議ディスク容量計算を開始");
            //使用データサイズの再集計
            RngDatausedSumDao dataUsedDao = new RngDatausedSumDao(con);
            RngDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);


            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getRngRndataDiskSize();
            usedDisk += sumMdl.getRngTemplateDiskSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, RngConst.PLUGIN_ID_RINGI, usedDisk);

            con.commit();

            commitFlg = true;
            log__.info("稟議ディスク容量計算を終了");
        } catch (Exception e) {
            log__.error("稟議 日次バッチ処理失敗", e);
            logException = e;
            throw e;
        } finally {
            log__.info("稟議 日次バッチ処理終了");

            if (!commitFlg) {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            } else {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            }
        }
    }

    /**
     * <br>[機  能] 稟議削除条件Modelの追加を行う
     * <br>[解  説] 削除区分 = 削除する の場合のみ追加する
     * <br>[備  考]
     * @param delList 稟議削除条件List
     * @param delKbn 削除区分
     * @param delType 削除種別
     * @param year 年
     * @param month 月
     * @param day 日
     */
    private void __addDeleteModel(List<RngDeleteModel> delList,
                                int delKbn, int delType,
                                int year, int month, int day) {

        if (delKbn == RngConst.RAD_KBN_DELETE) {
            RngDeleteModel delMdl = new RngDeleteModel();
            delMdl.setDelType(delType);
            delMdl.setDelYear(year);
            delMdl.setDelMonth(month);
            delMdl.setDelDay(day);

            delList.add(delMdl);
        }
    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws Exception {
    }
}