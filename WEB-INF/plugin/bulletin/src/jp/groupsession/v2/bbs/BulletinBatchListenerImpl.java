package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.bbs.dao.BbsDatausedSumDao;
import jp.groupsession.v2.bbs.dao.BbsForSumDao;
import jp.groupsession.v2.bbs.dao.BbsLogCountSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreRsvDao;
import jp.groupsession.v2.bbs.dao.BbsViewLogCountDao;
import jp.groupsession.v2.bbs.dao.BbsWriteLogCountDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.bbs.model.BbsDatausedSumModel;
import jp.groupsession.v2.bbs.model.BbsLogCountSumModel;
import jp.groupsession.v2.bbs.model.BbsThreRsvModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 掲示板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinBatchListenerImpl implements IBatchListener {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(BulletinBatchListenerImpl.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BulletinBatchListenerImpl() {
        super();
    }

    /**
     * <br>[機  能] 日次バッチ起動時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        log__.debug("掲示板バッチ処理開始");
        String pluginName = "掲示板";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;
        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            BbsBiz biz = new BbsBiz();
            BbsAdmConfModel conf = biz.getBbsAdminData(con, -1);
            if (conf.getBacAtdelFlg() == GSConstBulletin.AUTO_DELETE_ON) {
                //自動削除実行

                //基準日を作成
                UDate bdate = new UDate();
                int year = conf.getBacAtdelY();
                int month = conf.getBacAtdelM();
                bdate.addYear(-year);
                bdate.addMonth(-month);
                //削除実行
                biz.deleteOldBulletin(con, bdate);
            }

            //掲示期限を過ぎたスレッドを削除する
            biz.deleteOverLimitBulletin(con);

            /* 統計情報の集計*/
            //1.集計データの取得
            BbsLogCountSumDao logSumDao = new BbsLogCountSumDao(con);
            int[] blsKbnList = {GSConstBulletin.BLS_KBN_WRITE,
                                    GSConstBulletin.BLS_KBN_VIEW
            };
            //2.集計データの登録
            UDate today = new UDate();
            for (int blsKbn : blsKbnList) {
                List<BbsLogCountSumModel> logSumList
                    = logSumDao.getNonRegisteredList(blsKbn, today);
                if (logSumList != null && !logSumList.isEmpty()) {
                    for (BbsLogCountSumModel logSumMdl : logSumList) {
                        if (logSumDao.update(logSumMdl) == 0) {
                            logSumDao.insert(logSumMdl);
                        }
                    }
                }
            }
            //3.集計データの削除 前日までのデータを削除
            BbsWriteLogCountDao bwlDao = new BbsWriteLogCountDao(con);
            bwlDao.delete(today);
            BbsViewLogCountDao bvlDao = new BbsViewLogCountDao(con);
            bvlDao.delete(today);
            log__.debug("掲示板 集計データを削除");

            con.commit();
            log__.info("使用データサイズの再集計 開始");
            //使用データサイズの再集計
            BbsForSumDao forSumDao = new BbsForSumDao(con);

            BbsDatausedSumDao dataUsedDao = new BbsDatausedSumDao(con);
            dataUsedDao.delete();

            BbsDatausedSumModel sumMdl = new BbsDatausedSumModel();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            sumMdl.setBbsForSumSize(forSumDao.getTotalUseDiskSize());
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(
                    con,
                    GSConstBulletin.PLUGIN_ID_BULLETIN,
                    sumMdl.getBbsForSumSize());


            con.commit();
            log__.info("使用データサイズの再集計 終了");

            commitFlg = true;
            log__.debug("掲示板バッチ処理終了");
        } catch (Exception e) {
            log__.error("掲示板バッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {
            if (commitFlg) {
                DayJob.outPutFinishLog(con, param.getDomain(),
                        pluginName, startTime);
            } else {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(),
                        pluginName, logException);
            }

        }
    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
        UDate now = new UDate();

            con.setAutoCommit(false);
            boolean commitFlg = false;
            //予約投稿リストDao
            BbsThreRsvDao btrDao = new BbsThreRsvDao(con);

            try {
                //フォーラム一覧を取得
                //予約投稿されたスレッドの内、表示開始するもののフォーラムSIDを取得
                List<Integer> bfiSidList = btrDao.selectBfiSid(now);
                BbsBiz bbsBiz = new BbsBiz();
                BulletinDao bbsDao = new BulletinDao(con);

                for (Integer bfiSid : bfiSidList) {

                    //投稿時間が過ぎたスレッドが存在するフォーラムの最終更新日時を更新する
                    //フォーラムの最終更新日時と表示されているスレッド内の最新日時から
                    //最新のものを取得して更新する
                    bbsDao.updateBfsWrtDateBatch(bfiSid, now);

                    //フォーラム集計情報の更新
                    bbsBiz.updateBbsForSum(con, bfiSid, 0, now);

                }


                //ショートメール通知
                List<BbsThreRsvModel> btrList = btrDao.select(now);
                if (bbsBiz.canSendSmail(con, 0)) {
                    //フォーラムSIDから、掲示開始日時を過ぎたスレッドを取得する
                    for (int i = 0; i < btrList.size(); i++) {
                        //スレッドSIDを取得
                        int btiSid = btrList.get(i).getBtiSid();
                        //ドメイン名
                        String domain = param.getDomain();
                        //GroupSession共通情報を格納
                        GSContext gsContext = param.getGsContext();
                        //ショートメール通知を実行
                        bbsBiz.sendSmailForBatch(con, gsContext, domain, btiSid);
                    }
                }

                //掲示開始日時および掲示終了日時が過ぎたスレッドのSIDをテーブルから削除
                btrDao.delete(now);


                commitFlg = true;
                log__.debug("掲示板バッチ処理終了");
            } catch (SQLException e) {
                log__.error("掲示板バッチ処理失敗", e);
                JDBCUtil.rollback(con);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }
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
