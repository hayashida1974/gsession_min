package jp.groupsession.v2.wml.listener;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.batch.WmlReceiveMasterThread;
import jp.groupsession.v2.wml.batch.dao.WmlBatchDao;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.biz.WmlUidlCleaner;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz.Builder;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvlockDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteDao;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteLogDao;
import jp.groupsession.v2.wml.dao.base.WmlDatausedSumDao;
import jp.groupsession.v2.wml.dao.base.WmlDelmailTempDao;
import jp.groupsession.v2.wml.dao.base.WmlLogCountDao;
import jp.groupsession.v2.wml.dao.base.WmlLogCountSumDao;
import jp.groupsession.v2.wml.dao.base.WmlMailSendplanDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataSortDao;
import jp.groupsession.v2.wml.dao.base.WmlManageNoticeDao;
import jp.groupsession.v2.wml.dao.base.WmlUidlDao;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteLogModel;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteModel;
import jp.groupsession.v2.wml.model.base.WmlDatausedSumModel;
import jp.groupsession.v2.wml.model.base.WmlLogCountSumModel;
import jp.groupsession.v2.wml.model.base.WmlMailSendplanModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] 登録されているアカウントのメール受信処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlBatchListenerImpl implements IBatchListener {

    /** メール受信実行中フラグ 未設定 */
    private static final int RECEIVEMAIL_NORECEIVE__ = 0;
    /** メール受信実行中フラグ 受信中 */
    private static final int RECEIVEMAIL_RECEIVE__ = 1;

    /** メール受信実行中フラグ */
    private static Map<String, AtomicInteger> receiveMailMap__ = null;

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlBatchListenerImpl.class);

    static {
        receiveMailMap__ = new ConcurrentHashMap<String, AtomicInteger>();
    }

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {

        //Batch Status = 制限して実行 の場合、日次バッチを終了する
        String batchStatus = CommonBiz.getBatchStatus();
        if (batchStatus.equals(GSConst.BATCH_STATUS_LIMITATION)) {
            return;
        }
        String pluginName = "Webメール";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;

        log__.debug("WEBメール日次バッチ開始");

        con.setAutoCommit(false);

        //アカウントの削除を行う
        WmlBatchDao dao = new WmlBatchDao(con);
        List<Integer> wacSidList = dao.getDeleteAccount();
        for (int wacSid : wacSidList) {

            log__.info("WEBメール日次バッチ削除対象のアカウント : アカウントSID = " + wacSid);

            boolean delFlg = false;
            try {
                //フィルター条件の削除を行う
                dao.delFilterConditionType(wacSid);

                //フィルター適用順の削除を行う
                dao.delFilterSortType(wacSid);

                //フィルター_転送先アドレスの削除を行う
                dao.delFilterFwaddressType(wacSid);

                //フィルターの削除を行う
                dao.delFilterType(wacSid);

                //メール - ラベルの削除を行う。
                dao.delLabelRelationType(wacSid);

                //ラベルの削除を行う。
                dao.delLabelType(wacSid);

                log__.info("WEBメール日次バッチ フィルター、ラベル削除完了 : アカウントSID = " + wacSid);

                con.commit();

                //メール情報と関連するデータを削除
                WmlTempfileModel wtdMdl = new WmlTempfileModel();
                UDate now = new UDate();
                wtdMdl.setWtfEuid(0);
                wtdMdl.setWtfEdate(now);
                wtdMdl.setWtfJkbn(GSConstWebmail.WMD_STATUS_DUST);

                log__.info("WEBメール日次バッチ メール情報の削除開始 : アカウントSID = " + wacSid);

                List<Long> mailNumList = dao.getMailNumList(wacSid);

                while (!mailNumList.isEmpty()) {
                    //メール情報_送信予約の削除
                    dao.delMailData(mailNumList, "WML_MAIL_SENDPLAN");
                    //送信先の削除
                    dao.delMailData(mailNumList, "WML_SENDADDRESS");
                    //メール添付ファイルの論理削除
                    dao.updateTempFile(wtdMdl, mailNumList);
                    //メール - ラベル情報の削除
                    dao.delMailData(mailNumList, "WML_LABEL_RELATION");
                    //メールヘッダ情報の削除
                    dao.delMailData(mailNumList, "WML_HEADER_DATA");
                    //メール本文の削除
                    dao.delMailData(mailNumList, "WML_MAIL_BODY");
                    //メール情報の削除
                    dao.delMailData(mailNumList, "WML_MAILDATA");

                    con.commit();
                    mailNumList = null;
                    mailNumList = dao.getMailNumList(wacSid);
                }

                log__.info("WEBメール日次バッチ メール情報の削除完了 : アカウントSID = " + wacSid);

                //受信済みメールUIDLの削除を行う。
                List<String> uidlList = dao.getUidlList(wacSid);
                List<String> errorUidlList = new ArrayList<String>();
                WmlUidlDao uidlDao = new WmlUidlDao(con);
                long uidlCount = uidlDao.getUidlCount(wacSid);

                log__.info("WEBメール日次バッチ 受信済みメールUIDLの削除開始 : アカウントSID = " + wacSid);
                long delCount = 0;
                while (!uidlList.isEmpty()) {

                    for (String uid : uidlList) {
                        try {
                            uidlDao.deleteUid(wacSid, uid);
                        } catch (SQLException e) {
                            log__.error(e);
                            if (errorUidlList.indexOf(uid) < 0) {
                                errorUidlList.add(uid);
                            }
                        }
                    }
                    con.commit();
                    delCount += uidlList.size();
                    log__.info("WEBメール日次バッチ 削除した受信済みメールUIDLの件数 : " + delCount);

                    uidlList = null;
                    if (errorUidlList.size() >= 100 || delCount > uidlCount) {
                        break;
                    }

                    uidlList = dao.getUidlList(wacSid);
                    if (!errorUidlList.isEmpty()) {
                        for (String errorUid : errorUidlList) {
                            int removeIndex = uidlList.indexOf(errorUid);
                            if (removeIndex >= 0) {
                                uidlList.remove(removeIndex);
                            }
                        }
                    }
                }

                uidlDao.delete(wacSid);
                log__.info("WEBメール日次バッチ 受信済みメールUIDLの削除完了 : アカウントSID = " + wacSid);

                //ディレクトリの削除を行う。
                dao.delDirectory(wacSid);

                //アカウント並び順の削除を行う。
                dao.delAccountSort(wacSid);

                //アカウント使用者の削除を行う。
                dao.delAccountUser(wacSid);

                //アカウント署名の削除を行う
                dao.delAccountSign(wacSid);

                //アカウントディスク使用量の削除を行う。
                dao.delAccountDisk(wacSid);

                //アカウント_受信サーバ情報の削除を行う。
                dao.delAccountRcvSvr(wacSid);

                //アカウント_受信情報の削除を行う。
                dao.delAccountRcvData(wacSid);

                //メール情報表示順を削除する
                WmlMaildataSortDao mailSortDao = new WmlMaildataSortDao(con);
                mailSortDao.deleteMailSortOfAccount(wacSid);

                //アカウント情報の削除を行う。
                dao.delAccount(wacSid);

                log__.info("WEBメール日次バッチ アカウント情報の削除完了 : アカウントSID = " + wacSid);

                con.commit();

                delFlg = true;

            } catch (SQLException e) {
                log__.error("WEBメール日次バッチ アカウントの削除に失敗 : アカウントSID = " + wacSid, e);
            } catch (Exception e) {
                log__.error("WEBメール日次バッチ アカウントの削除にで例外発生 : アカウントSID = " + wacSid, e);
                logException = e;
                throw e;
            } catch (Error e) {
                log__.error("WEBメール日次バッチ アカウントの削除にでエラー発生 : アカウントSID = " + wacSid, e);
                logException = e;
                throw e;
            } finally {
                if (!delFlg) {
                    JDBCUtil.rollback(con);
                    DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
                }
            }
        }
        dao = null;
        wacSidList = null;

        WmlBiz wmlBiz = new WmlBiz();

        boolean commit = false;
        con.setAutoCommit(false);
        try {

            //メール情報の自動削除を行う
            WmlAutodeleteDao autoDelDao = new WmlAutodeleteDao(con);
            WmlAutodeleteModel autoDelMdl = autoDelDao.getAutoDelSetUp();
            if (autoDelMdl != null) {

                List<WmlMailDeleteModel> delList = new ArrayList<WmlMailDeleteModel>();
                delList = __setAutoDelData(delList, autoDelMdl.getWadDustKbn(),
                                        autoDelMdl.getWadDustYear(),
                                        autoDelMdl.getWadDustMonth(),
                                        autoDelMdl.getWadDustDay(),
                                        GSConstWebmail.DIR_TYPE_DUST);
                delList = __setAutoDelData(delList, autoDelMdl.getWadSendKbn(),
                                        autoDelMdl.getWadSendYear(),
                                        autoDelMdl.getWadSendMonth(),
                                        autoDelMdl.getWadSendDay(),
                                        GSConstWebmail.DIR_TYPE_SENDED);
                delList = __setAutoDelData(delList, autoDelMdl.getWadDraftKbn(),
                                        autoDelMdl.getWadDraftYear(),
                                        autoDelMdl.getWadDraftMonth(),
                                        autoDelMdl.getWadDraftDay(),
                                        GSConstWebmail.DIR_TYPE_DRAFT);
                delList = __setAutoDelData(delList, autoDelMdl.getWadResvKbn(),
                                        autoDelMdl.getWadResvYear(),
                                        autoDelMdl.getWadResvMonth(),
                                        autoDelMdl.getWadResvDay(),
                                        GSConstWebmail.DIR_TYPE_RECEIVE);
                delList = __setAutoDelData(delList, autoDelMdl.getWadKeepKbn(),
                                        autoDelMdl.getWadKeepYear(),
                                        autoDelMdl.getWadKeepMonth(),
                                        autoDelMdl.getWadKeepDay(),
                                        GSConstWebmail.DIR_TYPE_STORAGE);

                WmlAccountDao accountDao = new WmlAccountDao(con);
                List<Integer> autoDelWacSidList = accountDao.getExistAccountSidList();
                for (WmlMailDeleteModel delMdl : delList) {
                    for (int autoDelWacSid : autoDelWacSidList) {
                        delMdl.setWacSid(autoDelWacSid);
                        wmlBiz.deleteMailData(con, delMdl, 0);
                    }
                }
            }

            //送受信ログの自動削除を行う
            WmlAutodeleteLogDao autoDelLogDao = new WmlAutodeleteLogDao(con);
            WmlAutodeleteLogModel autoDelLogModel = autoDelLogDao.select();
            if (autoDelLogModel != null
            && autoDelLogModel.getWalDelKbn() == GSConstWebmail.WAL_KBN_AUTODELETE) {
                WebmailDao webmailDao = new WebmailDao(con);

                //送受信ログのデータ使用量を登録(削除対象のデータ使用量を減算)
                List<Long> delMessageNumList
                                    = webmailDao.getMailLogToDelete(
                                            autoDelLogModel.getWalDelYear(),
                                            autoDelLogModel.getWalDelMonth(),
                                            autoDelLogModel.getWalDelDay());
                WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(con);
                usedDataBiz.insertMaillogSize(delMessageNumList, false);

                //送受信ログの削除
                webmailDao.deleteMailLogSend(autoDelLogModel.getWalDelYear(),
                                            autoDelLogModel.getWalDelMonth(),
                                            autoDelLogModel.getWalDelDay());
                webmailDao.deleteMailLog(autoDelLogModel.getWalDelYear(),
                                        autoDelLogModel.getWalDelMonth(),
                                        autoDelLogModel.getWalDelDay());


            }
            //受信済みUIDLからサーバにないものを削除
            WmlUidlCleaner uidlCleaner = new WmlUidlCleaner();
            uidlCleaner.clean(con);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("WEBメール日次バッチ実行時に例外発生", e);
            logException = e;
            throw e;
        } catch (Error e) {
            log__.error("WEBメール日次バッチ実行時にエラー発生", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);

            }
        }

        //メール添付ファイルの物理削除を行う(100件ずつ)
        try {
            WmlTempfileDao tempFileDao = new WmlTempfileDao(con);
            List<Long> mailNum = tempFileDao.getMailNumOfDelTemp();
            if (mailNum != null && !mailNum.isEmpty()) {

                GSContext gsContext = param.getGsContext();

                //100件ずつ削除
                List<Long> mailDelNumList = null;
                long[] mailDelNumArray = null;
                for (int num = 0; num < mailNum.size(); num += 100) {

                    mailDelNumList = new ArrayList<Long>();

                    for (int index = num; index < num + 100 && index < mailNum.size(); index++) {
                        mailDelNumList.add(mailNum.get(index));
                    }

                    if (!mailDelNumList.isEmpty()) {
                        mailDelNumArray = new long[mailDelNumList.size()];
                        for (int index = 0; index < mailDelNumList.size(); index++) {
                            mailDelNumArray[index] = mailDelNumList.get(index);
                        }

                        commit = false;
                        con.setAutoCommit(false);
                        wmlBiz.deleteMailTempFile(con,
                                (String) gsContext.get(GSContext.APP_ROOT_PATH),
                                mailDelNumArray);
                        con.commit();
                        commit = true;
                    }
                }
            }


        } catch (Exception e) {
            log__.error("WEBメール日次バッチ 添付ファイル削除実行時に例外発生", e);
            logException = e;
            throw e;
        } catch (Error e) {
            log__.error("WEBメール日次バッチ 添付ファイル削除実行時にエラー発生", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }

        //未登録の統計情報_集計結果を登録
        commit = false;
        try {
            //統計情報の集計を行う
            WmlLogCountSumDao logSumDao = new WmlLogCountSumDao(con);
            List<WmlLogCountSumModel> logSumList = logSumDao.getNonRegisteredList(new UDate());
            if (logSumList != null && !logSumList.isEmpty()) {
                for (WmlLogCountSumModel logSumMdl : logSumList) {
                    if (logSumDao.update(logSumMdl) == 0) {
                        logSumDao.insert(logSumMdl);
                    }
                }
            }
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("WEBメール 統計情報の集計に失敗", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }

        //WEBメール 統計情報集計データ削除
        commit = false;
        try {
            log__.debug("WEBメール 統計情報集計データ削除処理開始");
                //集計データを削除
            WmlLogCountDao wlcDao = new WmlLogCountDao(con);
            int wmlLogDelCount = wlcDao.delete(new UDate());
                log__.debug("WEBメール 統計情報集計データ" + wmlLogDelCount + "件を削除");
                con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("WEBメール 統計情報集計データ削除実行時に例外発生", e);
            logException = e;
            throw e;
        } catch (Error e) {
            log__.error("WEBメール 統計情報集計データ削除実行時にエラー発生", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }

        //WEBメール 全アカウントを対象に、容量超過による受信失敗通知を未受信状態にする
        commit = false;
        try {
            WmlManageNoticeDao wmnDao = new WmlManageNoticeDao(con);
            wmnDao.updateForAccounts();
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("WEBメール 受信失敗通知を未受信へ設定時に例外発生", e);
            logException = e;
            throw e;
        } catch (Error e) {
            log__.error("WEBメール 受信失敗通知を未受信へ設定時にエラー発生", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }


        log__.info("使用データサイズの再集計 開始");
        //使用データサイズの再集計
        commit = false;
        try {
            WmlAccountDiskDao accountDiskDao = new WmlAccountDiskDao(con);

            WmlDatausedSumDao dataUsedDao = new WmlDatausedSumDao(con);
            WmlDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            sumMdl.setWacDiscsizeSum(accountDiskDao.getTotalUseDiskSize());
            dataUsedDao.insert(sumMdl);

            //プラグイン別使用データサイズ集計の登録
            long usedDisk = 0;
            usedDisk += sumMdl.getWacDiscsizeSum();
            usedDisk += sumMdl.getWtpDiscsizeSum();
            usedDisk += sumMdl.getWlgDiscsizeSum();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstWebmail.PLUGIN_ID_WEBMAIL, usedDisk);
            commit = true;
        } catch (Exception e) {
            log__.error("WEBメール 使用データサイズの再集計時に例外発生", e);
            logException = e;
            throw e;
        } catch (Error e) {
            log__.error("WEBメール 使用データサイズの再集計時にエラー発生", e);
            logException = e;
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
            }
        }
        log__.info("使用データサイズの再集計 終了");




        //メール情報一括削除_一時保管データを削除する
        WmlDelmailTempDao delTempDao = new WmlDelmailTempDao(con);
        delTempDao.deleteAll();




        DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);


        log__.debug("WEBメール日次バッチ終了");

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
            WmlLogCountSumDao logSumDao = new WmlLogCountSumDao(con);
            UDate date = new UDate();
            //日付変更直後のみ前日の集計も行う
            if (date.getIntHour() == 0) {
                UDate prev = date.cloneUDate();
                prev.addDay(-1);
                logSumDao.updateLogSum(prev);
            }

            //統計情報の集計を行う
            logSumDao.updateLogSum(date);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("WEBメール 1時間毎バッチの実行に失敗", e);
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

        log__.debug("WEBメールバッチ処理開始");

        con.setAutoCommit(false);
        boolean commit = false;

        WmlAccountDao accountDao = new WmlAccountDao(con);
        GSContext gsContext = param.getGsContext();
        String appRootPath = (String) gsContext.get(GSContext.APP_ROOT_PATH);

        //Batch Status = 制限して実行 の場合、自動受信のみを行う
        String batchStatus = CommonBiz.getBatchStatus();
        if (batchStatus.equals(GSConst.BATCH_STATUS_TRUE)) {

            try {

                WmlBiz wmlBiz = new WmlBiz();
                wmlBiz.outPutBatchLog(con,
                                                param.getDomain(),
                                                "予約送信開始",
                                                getClass().getName(),
                                                "WEBメール バッチ処理",
                                                GSConstLog.LEVEL_TRACE,
                                                null,
                                                "batch sendmail start");

                //送信対象メール(後で送信)のメール送信を行う
                WmlMailSendplanDao sendPlanDao = new WmlMailSendplanDao(con);
                List<WmlMailSendplanModel> sendPlanList = sendPlanDao.getSendMessagePlanList();
                Wml010Biz wml010Biz = new Wml010Biz();

                String domain = param.getDomain();

                int bodyLimitLen = WmlBiz.getBodyLimitLength(appRootPath);
                Map<Integer, WmlAccountModel> accountMap = new HashMap<Integer, WmlAccountModel>();

                WmlSendResultModel sendResultMdl = null;
                GsMessage gsMsg = new GsMessage();
                WmlMaildataDao maildataDao = new WmlMaildataDao(con);
                WmlMaildataModel mailData = null;

                String sendPlanStartDate = null;
                if (!sendPlanList.isEmpty()) {
                    UDate now = new UDate();
                    sendPlanStartDate = UDateUtil.getSlashYYMD(now)
                                                    + " " + UDateUtil.getSeparateHMS(now);
                    log__.debug("予約送信 送信開始 (開始日時: " + sendPlanStartDate + ")");
                    now = null;
                }

                for (WmlMailSendplanModel sendPlanData : sendPlanList) {
                    commit = false;

                    int sendWacSid = sendPlanData.getWacSid();
                    File batchTempDir = null;
                    try {
                        //送信対象メールのメール情報を取得する
                        WmlMailSearchModel searchMdl = new WmlMailSearchModel();
                        searchMdl.setAccountSid(sendWacSid);
                        searchMdl.setMailNum(sendPlanData.getWmdMailnum());
                        searchMdl.setStart(1);
                        searchMdl.setMaxCount(1);
                        List<WmlMailResultModel> mailList
                            = maildataDao.getMailList(searchMdl, bodyLimitLen);

                        WmlAccountModel accountData = accountMap.get(sendWacSid);
                        if (accountData == null) {
                            accountData = accountDao.select(sendWacSid);
                            accountMap.put(sendWacSid, accountData);
                        }

                        String wmlBatchId = __getSendPlanTempDirId(sendPlanData);
                        batchTempDir = new File(__getSendTempDir(domain, wmlBatchId));
                        while (batchTempDir.exists()) {
                            wmlBatchId = __getSendPlanTempDirId(sendPlanData);
                            batchTempDir = new File(__getSendTempDir(domain, wmlBatchId));
                        }
                        IOTools.isDirCheck(batchTempDir.getPath(), true);

                        String tempDir = __getSendTempDir(domain, wmlBatchId);


                        Builder sendMailBuilder
                            = wml010Biz.getSendMailBuilder(con, accountData,
                                GSConstWebmail.SEND_TYPE_EDIT, mailList,
                                appRootPath, tempDir,
                                domain);

                        boolean htmlMail
                            = sendPlanData.getWspMailtype() == GSConstWebmail.WSP_MAILTYPE_HTML;

                        sendMailBuilder.setWacSid(sendWacSid);
                        sendMailBuilder.setSendMessageNum(sendPlanData.getWmdMailnum());
                        sendMailBuilder.setSendMailType(GSConstWebmail.SEND_TYPE_EDIT);
                        sendMailBuilder.setHtmlMail(htmlMail);

                        WmlMailSendBiz sendBiz = sendMailBuilder.build(
                            con, gsContext, appRootPath, tempDir, 0);
                        
                        sendResultMdl = sendBiz.executeMail(con, gsContext,
                            0, appRootPath, tempDir, domain, Locale.JAPANESE);

                        if (sendResultMdl.getResult() == WmlSendResultModel.RESULT_SUCCESS) {
                            //送信対象メールの「送信予定」を削除する
                            sendPlanDao.delete(sendPlanData.getWmdMailnum());
                        } else {

                            //送信失敗通知を行う
                            try {
                                String sendMailTitle = gsMsg.getMessage("wml.244");
                                mailData = maildataDao.select(sendPlanData.getWmdMailnum());
                                String templateFile = "info_sizeover.txt";
                                if (sendResultMdl.getResult()
                                        == WmlSendResultModel.RESULT_SERVERERROR) {
                                    templateFile = "info_fail_sent_server.txt";
                                }

                                wmlBiz.sendSmail(con, sendMailTitle, mailData,
                                                templateFile,
                                                sendWacSid, appRootPath, domain);

                            } catch (Throwable th) {
                                log__.error("送信失敗時のショートメール通知に失敗 : "
                                                + sendPlanData.getWmdMailnum(), th);
                            }
                        }

                        //アカウントディスク容量の再計算
                        wmlBiz.updateAccountDiskSize(con, sendWacSid);

                        con.commit();
                        commit = true;
                    } catch (Exception e) {
                        log__.warn("メール送信に失敗 : " + sendPlanData.getWmdMailnum(), e);

                        //送信失敗通知を行う
                        try {
                            String sendMailTitle = gsMsg.getMessage("wml.244");
                            mailData = maildataDao.select(sendPlanData.getWmdMailnum());
                            wmlBiz.sendSmail(con, sendMailTitle, mailData, "info_fail_sent.txt",
                                                    sendWacSid, appRootPath, domain);
                        } catch (Throwable th) {
                            log__.error("送信失敗時のショートメール通知に失敗 : " + sendPlanData.getWmdMailnum(), th);
                        }

                    } finally {
                        if (!commit) {
                            con.rollback();
                        }

                        if (batchTempDir != null && batchTempDir.exists()) {
                            IOTools.deleteDir(batchTempDir.getParent());
                        }
                    }
                }

                if (sendPlanStartDate != null) {
                    log__.debug("予約送信 送信終了 (開始日時: " + sendPlanStartDate + ")");
                    sendPlanStartDate = null;
                }

                wmlBiz.outPutBatchLog(con,
                        param.getDomain(),
                        "予約送信終了",
                        getClass().getName(),
                        "WEBメール バッチ処理",
                        GSConstLog.LEVEL_TRACE,
                        null,
                        "batch sendmail end");
            } catch (Exception e) {
                log__.error("メール送信に失敗", e);
            }

            //アカウント_受信ロックの自動削除
            //現在日時より1時間前のロックレコードを削除対象とする
            commit = false;
            try {
                UDate delDate = new UDate();
                delDate.addHour(-1);
                WmlAccountRcvlockDao accountLockDao = new WmlAccountRcvlockDao(con);
                accountLockDao.deleteOldLock(delDate);
                con.commit();
                commit = true;
            } catch (Exception e) {
                log__.error("アカウント_受信ロックの自動削除に失敗", e);
            } finally {
                if (!commit) {
                    con.rollback();
                }
            }
        }

        if (!receiveMailMap__.containsKey(param.getDomain())) {
            receiveMailMap__.put(param.getDomain(), new AtomicInteger(RECEIVEMAIL_RECEIVE__));
        } else if (receiveMailMap__.get(param.getDomain()).get() == RECEIVEMAIL_RECEIVE__) {
            return;
        }

        receiveMailMap__.get(param.getDomain()).set(RECEIVEMAIL_RECEIVE__);

        commit = false;
        try {

            //アカウント管理者設定データを取得
            WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
            WmlAdmConfModel admConfMdl = new WmlAdmConfModel();
            admConfMdl = admConfDao.selectAdmData();

            //管理者一括設定区分
            int adminAllSettingKbn = admConfMdl.getWadPermitKbn();
            //自動受信間隔(管理者)
            int adminAutoReceiveTime = admConfMdl.getWadAutoReceiveTime();
            //自動受信区分(管理者)
            int adminAutoRsvKbn = admConfMdl.getWadAutoreceive();

            //管理者一括設定で自動受信を行わない場合、処理を終了する
            if (adminAllSettingKbn == GSConstWebmail.PERMIT_ADMIN
                    && adminAutoRsvKbn == GSConstWebmail.MAIL_AUTO_RSV_OFF) {

                log__.debug("管理者一括設定で自動受信を行わない");
                log__.debug("WEBメールバッチ処理終了");
                return;
            }

            //アカウント情報を取得する
            List<WmlAccountModel> accountDataList = new ArrayList<WmlAccountModel>();
            List<Integer> wacSidList = new ArrayList<Integer>();

            int apNum = CommonBiz.getApNumber();
            if (admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
                //管理者一括設定
                accountDataList = accountDao.getAllAccountDataForAutoReceive(apNum);
                wacSidList = __getAdmReseiveWacSids(accountDataList, adminAutoReceiveTime);

            } else {
                //アカウント単位
                accountDataList = accountDao.getAccountSidListForAutoReceive(apNum);
                wacSidList = __getReseiveWacSids(accountDataList, adminAutoReceiveTime);
            }

            //受信するアカウントSIDをセット
            int[] wacSid = new int [wacSidList.size()];
            for (int i = 0; i < wacSid.length; i++) {
                wacSid[i] = wacSidList.get(i);
            }

            //メール受信スレッドを起動する
            log__.debug("自動受信対象アカウント数: " + wacSid.length);
            WmlReceiveMasterThread receiveThread
                                = WmlReceiveMasterThread.getInstance(param.getDomain());
            receiveThread.receiveMail(gsContext, wacSid,
                                    (MessageResources) gsContext.get(GSContext.MSG_RESOURCE));

            log__.debug("WEBメールバッチ処理終了");

        } catch (Throwable e) {
            log__.error("WEBメールバッチ処理失敗", e);
            throw new Exception(e);
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
            if (receiveMailMap__.containsKey(param.getDomain())) {
                receiveMailMap__.get(param.getDomain()).set(RECEIVEMAIL_NORECEIVE__);
            }
        }
    }

    /**
     * <br>[機  能] 送信予定メールのテンポラリディレクトリIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sendPlanData 送信予定メールデータ
     * @return テンポラリディレクトリID
     */
    private String __getSendPlanTempDirId(WmlMailSendplanModel sendPlanData) {
        return String.valueOf(sendPlanData.getWmdMailnum() + System.nanoTime());
    }

    /**
     * <br>[機  能] メール削除情報を設定する
     * <br>[解  説] delKbn = 2:自動削除の場合、メール削除情報を追加する
     * <br>[備  考]
     * @param delList メール削除情報一覧
     * @param delKbn 自動削除区分
     * @param year 年
     * @param month 月
     * @param day 日
     * @param dirType ディレクトリ種別
     * @return メール削除情報一覧
     */
    private List<WmlMailDeleteModel> __setAutoDelData(List<WmlMailDeleteModel> delList, int delKbn,
                                                    int year, int month, int day, int dirType) {

        if (delKbn == GSConstWebmail.WAC_DELKBN_AUTO) {
            WmlMailDeleteModel delMdl = new WmlMailDeleteModel();
            delMdl.setManuDelYear(year);
            delMdl.setManuDelMonth(month);
            delMdl.setManuDelDay(day);
            delMdl.setManuDelDir(dirType);
            delMdl.setUseDate(true);
            delMdl.setDelAllAccount(true);
            delList.add(delMdl);
        }

        return delList;
    }

    /**
     * <br>[機  能] 自動受信を行うアカウントSIDを取得する
     * <br>[解  説] 管理者一括設定時に使用
     * <br>[備  考]
     * @param waMdlList アカウントデータ
     * @param adminAutoReceive 自動受信間隔
     * @return 受信アカウントSID
     */
    private List<Integer> __getAdmReseiveWacSids(List<WmlAccountModel> waMdlList,
                                                  int adminAutoReceive) {

        List<Integer> wacSid = new ArrayList<Integer>();
        for (WmlAccountModel accountMdl : waMdlList) {
            if (__getTimeHanteiAdmin(accountMdl.getWacReceiveDate(), adminAutoReceive)) {
                //受信可能なアカウントSID
                wacSid.add(accountMdl.getWacSid());
                log__.debug("自動受信アカウント_batchListener: " + accountMdl.getWacName());
            }
        }

        return wacSid;
    }

    /**
     * <br>[機  能] 自動受信を行うアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param waMdlList アカウントデータ
     * @param adminAutoReceive 管理者デフォルト自動受信間隔
     * @return 受信アカウントSID
     */
    private List<Integer> __getReseiveWacSids(List<WmlAccountModel> waMdlList,
                                               int adminAutoReceive) {

        List<Integer> wacSid = new ArrayList<Integer>();
        for (WmlAccountModel accountMdl : waMdlList) {
            if (__getTimeHantei(accountMdl.getWacReceiveDate(),
                                accountMdl.getWacAutoReceiveTime(),
                                adminAutoReceive)
                ) {
                //受信可能なアカウントSID
                wacSid.add(accountMdl.getWacSid());
                log__.debug("自動受信アカウント: " + accountMdl.getWacName());
            }
        }

        return wacSid;
    }
    /**
     * <br>[機  能] 自動受信を行うアカウントかどうか判定する
     * <br>[解  説] 管理者一括設定時に使用
     * <br>[備  考]
     * @param lastReceiveDate 最終受信時間
     * @param wacAutoRevTime 自動受信間隔
     * @return 受信可能なアカウントかどうか
     */
    private boolean __getTimeHanteiAdmin(UDate lastReceiveDate,
                                      int wacAutoRevTime) {
        return __checkReceiveDate(lastReceiveDate, wacAutoRevTime);
    }

    /**
     * <br>[機  能] 自動受信を行うアカウントかどうか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param lastReceiveDate 最終受信時間
     * @param wacAutoRevTime 自動受信間隔
     * @param adminAutoReceive 管理者デフォルト自動受信間隔
     * @return 受信可能なアカウントかどうか
     */
    private boolean __getTimeHantei(UDate lastReceiveDate,
                                      int wacAutoRevTime,
                                      int adminAutoReceive) {
        int time = wacAutoRevTime;

        //未設定アカウントは管理者設定のデフォルトをセット
        if (wacAutoRevTime == GSConstWebmail.AUTO_RECEIVE_FIRST) {
            time = adminAutoReceive;
        }
        return __checkReceiveDate(lastReceiveDate, time);
    }

    /**
     * <br>[機  能] 自動受信を行うアカウントかどうか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param lastReceiveDate 最終受信時間
     * @param revTime 自動受信間隔
     * @return 受信可能なアカウントかどうか
     */
    private boolean __checkReceiveDate(UDate lastReceiveDate, int revTime) {
        if (lastReceiveDate == null) {
            //未受信のアカウントは許可する
            return true;
        }

        switch (revTime) {
            case GSConstWebmail.AUTO_RECEIVE_5:
            case GSConstWebmail.AUTO_RECEIVE_10:
            case GSConstWebmail.AUTO_RECEIVE_30:
            case GSConstWebmail.AUTO_RECEIVE_60:
                break;
            default:
                revTime = GSConstWebmail.AUTO_RECEIVE_5;
                break;
        }

        UDate now = new UDate();
        log__.debug("***現在時間：" + UDateUtil.getSeparateHMS(now));
        log__.debug("***最終受信時間：" + UDateUtil.getSeparateHMS(lastReceiveDate));

        lastReceiveDate.addMinute(revTime);
        log__.debug("***最終受信時間を指定時間分進めた時間：" + UDateUtil.getSeparateHMS(lastReceiveDate));

        //指定した時刻で受信を行うため、分の切捨てを行う
        int remainder = lastReceiveDate.getIntMinute() % GSConstWebmail.AUTO_RECEIVE_5;
        lastReceiveDate.addMinute(remainder * -1);
        log__.debug("***受信基準時間：" + UDateUtil.getSeparateHMS(lastReceiveDate));

        return now.compareDateYMDHM(lastReceiveDate) != UDate.LARGE;
    }

    /**
     * <br>[機  能] メール予約送信で使用するテンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param subId サブディレクトリID
     * @return テンポラリディレクトリパス
     */
    private String __getSendTempDir(String domain, String subId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getBatchTempPath(domain,
                                            "batch5Min",
                                            GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                            "send",
                                            subId);
        tempDir = IOTools.replaceFileSep(tempDir);
        return tempDir;
    }
}