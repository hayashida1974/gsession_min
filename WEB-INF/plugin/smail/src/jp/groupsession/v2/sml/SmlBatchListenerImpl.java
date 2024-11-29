package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.dao.SmlDatausedSumDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlLogCountDao;
import jp.groupsession.v2.sml.dao.SmlLogCountSumDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlDatausedSumModel;
import jp.groupsession.v2.sml.model.SmlLogCountSumModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] ショートメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(SmlBatchListenerImpl.class);

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        log__.debug("ショートメールバッチ処理開始");
        String pluginName = "ショートメール";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        con.setAutoCommit(false);
        boolean commitFlg = false;

        try {

           /**********************************************************
            *
            * 自動削除設定に従いデータを削除する
            *
            **********************************************************/

            SmlWmeisDao wmsDao = new SmlWmeisDao(con);

            //ショートメール自動削除
            SmlAdelDao delDao = new SmlAdelDao(con);
            SmlAdelModel delMdl = delDao.select(0);
            SmlCommonBiz smlBiz = new SmlCommonBiz();
            SmlAdminModel admMdl = smlBiz.getSmailAdminConf(-1, con);

            //「管理者が設定する」で設定済み
            if (delMdl != null
                    && admMdl.getSmaAutoDelKbn() == GSConstSmail.SML_DEL_KBN_ADM_SETTING) {

                if (delMdl.getSadDdelKbn() == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                    smlBiz.deleteJmeis(con, delMdl, 2);
                    smlBiz.deleteSmeis(con, delMdl, 2);
                    smlBiz.deleteWmeis(con, delMdl, 2);
                }

                if (delMdl.getSadJdelKbn() == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                    smlBiz.deleteJmeis(con, delMdl, 1);
                }

                if (delMdl.getSadSdelKbn() == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                    smlBiz.deleteSmeis(con, delMdl, 1);
                }

                if (delMdl.getSadWdelKbn() == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                    smlBiz.deleteWmeis(con, delMdl, 1);
                }

            //「各ユーザが設定する」で設定済み
            } else if (delMdl != null
                    && admMdl.getSmaAutoDelKbn() == GSConstSmail.SML_DEL_KBN_USER_SETTING) {

                //[自動削除する]に設定されている個人設定データを区分毎に取得
                ArrayList<SmlAdelModel> jdelList = delDao.selectAutoDelUserData(1);
                ArrayList<SmlAdelModel> sdelList = delDao.selectAutoDelUserData(2);
                ArrayList<SmlAdelModel> wdelList = delDao.selectAutoDelUserData(3);
                ArrayList<SmlAdelModel> ddelList = delDao.selectAutoDelUserData(4);

                //ゴミ箱タブデータ削除
                if (!ddelList.isEmpty()) {
                    for (SmlAdelModel ddelMdl : ddelList) {
                        smlBiz.deleteJmeis(con, ddelMdl, 2);
                        smlBiz.deleteSmeis(con, ddelMdl, 2);
                        smlBiz.deleteWmeis(con, ddelMdl, 2);
                    }
                }

                //受信タブデータ削除
                if (!jdelList.isEmpty()) {
                    for (SmlAdelModel jdelMdl : jdelList) {
                        smlBiz.deleteJmeis(con, jdelMdl, 1);
                    }
                }

                //送信タブデータ削除
                if (!sdelList.isEmpty()) {
                    for (SmlAdelModel sdelMdl : sdelList) {
                        smlBiz.deleteSmeis(con, sdelMdl, 1);
                    }
                }

                //草稿タブデータ削除
                if (!wdelList.isEmpty()) {
                    for (SmlAdelModel wdelMdl : wdelList) {
                        smlBiz.deleteWmeis(con, wdelMdl, 1);
                    }
                }
            }

           /**********************************************************
            *
            * 各テーブルの削除状況を確認し、関連のあるデータが全て
            * 論理削除されている場合は物理削除する
            *
            **********************************************************/
            //削除対象のメールSIDを取得
            SmailDao smlDao = new SmailDao(con);
            List<String> mailSidList = smlDao.getAllDeleteMailSidList();

            //sid を1000件ずつ処理
            int sidx = 0;
            for (int i = 0; i < mailSidList.size(); i++) {
                if (i % 1000 == 999 || i + 1 == mailSidList.size()) {
                    __deleteJittai(con, mailSidList.subList(sidx, i + 1));
                    sidx = i + 1;
                }
            }

            //論理削除された草稿メールを物理削除する
            List<Integer> delSidList = wmsDao.getDeletedMailSidList();
            smlBiz.deleteSoukouMail(con, delSidList);

            /* 統計情報の集計*/
            //1.集計データの取得
            SmlLogCountSumDao logSumDao = new SmlLogCountSumDao(con);
            UDate today = new UDate();
            List<SmlLogCountSumModel> logSumList = logSumDao.getNonRegisteredList(today);
            //2.集計データの登録
            if (logSumList != null && !logSumList.isEmpty()) {
                for (SmlLogCountSumModel logSumMdl : logSumList) {
                    if (logSumDao.update(logSumMdl) == 0) {
                        logSumDao.insert(logSumMdl);
                    }
                }
            }
            //3.集計データの削除 前日までのデータを削除
            SmlLogCountDao slcDao = new SmlLogCountDao(con);
            int smlLogDelCount = slcDao.delete(today);
            log__.debug("ショートメール 統計情報集計データ" + smlLogDelCount + "件を削除");

            con.commit();
            log__.info("使用データサイズの再集計 開始");
            //使用データサイズの再集計
            SmlDatausedSumDao dataUsedDao = new SmlDatausedSumDao(con);
            SmlDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();

            SmlAccountDiskDao accountDiskDao = new SmlAccountDiskDao(con);
            sumMdl.setSacDiscsizeSum(accountDiskDao.getTotalUseDiskSize());
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);


            //プラグイン別使用データサイズ集計の登録
            //合計にはメールサイズを設定
            long usedDisk =  sumMdl.getSmlMailSize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstSmail.PLUGIN_ID_SMAIL, usedDisk);
            con.commit();
            log__.info("使用データサイズの再集計 終了");


            commitFlg = true;

            log__.debug("ショートメールバッチ処理終了");

        } catch (Exception e) {
            log__.error("ショートメールバッチ処理失敗", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } finally {
            if (commitFlg) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                JDBCUtil.rollback(con);
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
    }

    /**
     *
     * <br>[機  能] 受信メール、送信メールの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mailSidList メールSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private void __deleteJittai(Connection con, List<String> mailSidList) throws SQLException {
        //ショートメール情報(送信)のデータ使用量を登録(削除対象のデータ使用量を減算)
        List<Integer> smsSidList = mailSidList.stream()
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
        usedDataBiz.insertSendDataSize(smsSidList, false);

        SmailDao smlDao = new SmailDao(con);
        //ラベルを削除
        smlDao.deleteJushinLabel(mailSidList);
        smlDao.deleteSoshinLabel(mailSidList);

        //ショートメール明細(受信)を物理削除
        SmlJmeisDao jmeisDao = new SmlJmeisDao(con);
        jmeisDao.deleteJMail(mailSidList);

        //ショートメール明細(送信)を物理削除
        SmlSmeisDao smeisDao = new SmlSmeisDao(con);
        smeisDao.deleteSMail(mailSidList);

        //ショートメールに送付されているバイナリSID一覧取得
        SmlBinDao sbinDao = new SmlBinDao(con);
        List<Long> binSidList = sbinDao.selectBinSidList(mailSidList);

        //バイナリ情報を論理削除
        CmnBinfDao binDao = new CmnBinfDao(con);
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
        cbMdl.setBinUpdate(new UDate());
        binDao.updateJKbn(cbMdl, binSidList);
        
        //ショートメールバイナリファイル送付情報を物理削除
        sbinDao.deleteSmlBin(mailSidList);
        
        //ショートメール本文に添付されているバイナリSID一覧取得
        SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
        List<Long> bodyBinSidList = sbbDao.selectBinSidList(mailSidList);
        cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(GSConstUser.SID_ADMIN);
        cbMdl.setBinUpdate(new UDate());
        binDao.updateJKbn(cbMdl, bodyBinSidList);
        
        //ショートメールボディバイナリファイル送付情報を物理削除
        sbbDao.delete(mailSidList);

    }

    /**
     * <p>1時間間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doOneHourBatch(Connection con, IBatchModel param) throws Exception {
        boolean commit = false;
        try {
            //統計情報の集計を行う
            SmlLogCountSumDao logSumDao = new SmlLogCountSumDao(con);
            UDate date = new UDate();
            //日付変更直後のみ前日の集計も行う
            if (date.getIntHour() == 0) {
                UDate prev = date.cloneUDate();
                prev.addDay(-1);
                logSumDao.updateLogSum(prev);
            }
            logSumDao.updateLogSum(date);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("ショートメール 1時間毎バッチの実行に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
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