package jp.groupsession.v2.man;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.cache.FileCache;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnInfoSendDateDao;
import jp.groupsession.v2.cmn.dao.base.CmnLhisBatchConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLogDao;
import jp.groupsession.v2.cmn.dao.base.CmnLogDelConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnOauthTokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpAtokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpTokenDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmCountDao;
import jp.groupsession.v2.cmn.model.base.CliPluginModel;
import jp.groupsession.v2.cmn.model.base.CmnLhisBatchConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogDelConfModel;
import jp.groupsession.v2.cmn.usedsize.UsedSizeBiz;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.man.biz.MainUsedDataBiz;
import jp.groupsession.v2.man.dao.base.ManDatausedSumDao;
import jp.groupsession.v2.man.model.base.ManDatausedSumModel;

/**
 * <br>[機  能] バッチ処理起動時に実行される処理を実装
 * <br>[解  説] メイン機能に関連した処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManBatchListenerImpl implements IBatchListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ManBatchListenerImpl.class);

    /**
     * <p>日次バッチ起動時に実行される
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws Exception バッチ処理実行時例外
     */
    public void doDayBatch(Connection con, IBatchModel param) throws Exception {
        String pluginName = "メイン";
        DayJob.outPutStartLog(con, param.getDomain(),
                pluginName, "");
        long startTime = System.currentTimeMillis();
        Throwable logException = null;
        boolean success = false;
        log__.debug("論理削除済みバイナリー情報のファイル削除、DBの物理削除処理を行う");

        //アプリケーションのルートパスを取得
        String rootPath = "";
        GSContext gscontext = param.getGsContext();
        Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);
        if (pathObj != null) {
            rootPath = (String) pathObj;
        }
        //テンポラリディレクトリをクリーン
        Object tmp
             = GroupSession.getResourceManager().getTempPath(param.getDomain());

        if (tmp != null) {
            if (tmp instanceof String) {
                String path = (String) tmp;
                //ファイルを削除
                File tempDir = new File(path);
                if (tempDir.exists()) {
                    IOTools.deleteDir(path);
                    IOTools.isDirCheck(path, true);
                }
            }
        }

        try {
            if (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES) {
                log__.info("ファイルキャッシュのリセット開始");
                FileCache.reset();
                log__.info("ファイルキャッシュのリセット終了");
            }
        } catch (Throwable e) {
            log__.error("ファイルキャッシュのリセットに失敗", e);
        }

        try {

            con.setAutoCommit(false);

            CommonBiz biz = new CommonBiz();
            biz.deleteAllLogicalDeletedBinInf(con, rootPath);

            con.commit();

            log__.debug("ログイン履歴削除処理開始");
            //統計情報の集計を行う(過去3日分)
            CmnUsrmCountDao usrCountDao = new CmnUsrmCountDao(con);
            UDate date = new UDate();
            date.addDay(-2);
            for (int i = 0; i < 3; i++) {
                usrCountDao.updateUserCount(date, true);
                date.addDay(1);
            }

            //ログイン履歴自動削除設定を取得
            CmnLhisBatchConfDao batchDao = new CmnLhisBatchConfDao(con);
            CmnLhisBatchConfModel batchMdl = batchDao.select();

            //「自動削除設定が設定済」かつ「削除区分 = 自動で削除する」の場合
            if (batchMdl != null && batchMdl.getCbcAdlKbn() == GSConstMain.LHIS_DELKBN_ON) {

                int adlYear = batchMdl.getCbcAdlYear();
                int adlMonth = batchMdl.getCbcAdlMonth();

                //削除する境界の日付を設定する
                UDate delUd = new UDate();
                log__.debug("現在日 = " + delUd.getDateString());
                log__.debug("削除条件 経過年 = " + adlYear);
                log__.debug("削除条件 経過年 = " + adlMonth);

                delUd.addYear((adlYear * -1));
                delUd.addMonth((adlMonth * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                log__.debug("削除境界線(この日以前のデータを削除) = " + delUd.getTimeStamp());

                //ログイン履歴のデータ使用量を登録(削除対象のデータサイズを減算)
                MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
                usedDataBiz.insertLoginHistoryDataSize(delUd, false);

                //ログイン履歴情報を削除
                CmnLoginHistoryDao historyDao = new CmnLoginHistoryDao(con);
                int count = historyDao.delete(delUd);

                con.commit();

                log__.debug("ログイン履歴" + count + "件を削除");
            }

            log__.debug("オペレーションログ自動削除処理開始");

            CmnLogDelConfDao logDelDao = new CmnLogDelConfDao(con);
            CmnLogDelConfModel logdelMdl = logDelDao.getOpeLogDelConf();

            if (logdelMdl.getLdcAdlKbn() == GSConstMain.OPE_LOG_CONF) {
                //オペレーションログ自動削除設定を実行
                int autoDelYear = logdelMdl.getLdcAdlYear();
                int autoDelMonth = logdelMdl.getLdcAdlMonth();
                CmnLogDao logDao = new CmnLogDao(con);
                UDate now = new UDate();

                //削除する境界の日付を設定する
                UDate delUdate = now.cloneUDate();
                log__.debug("現在日 = " + delUdate.getDateString());
                log__.debug("削除条件 経過年 = " + autoDelYear);
                log__.debug("削除条件 経過年 = " + autoDelMonth);

                delUdate.addYear((autoDelYear * -1));
                delUdate.addMonth((autoDelMonth * -1));
                delUdate.setHour(GSConstMain.DAY_END_HOUR);
                delUdate.setMinute(GSConstMain.DAY_END_MINUTES);
                delUdate.setSecond(GSConstMain.DAY_END_SECOND);
                delUdate.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                log__.debug("削除境界線(この日以前のログデータを削除) = " + delUdate.getTimeStamp());
                int logDelCount = logDao.delete(delUdate);

                //オペレーションログのデータ使用量を登録(削除対象データのデータサイズを減算)
                MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
                usedDataBiz.insertOpLogDataSize(logDelCount, false);

                log__.debug("オペレーションログ削除件数は" + logDelCount + "件です。");
                con.commit();
            }
            //有効期限切れの一次トークン（ワンタイムパスワード）を削除する
            UDate now = new UDate();
            CmnOtpTokenDao otokenDao = new CmnOtpTokenDao(con);
            otokenDao.deleteLimitOver(now);

            //有効期限切れの一次トークン（ワンタイムパスワード通知先アドレス設定画面）を削除する
            CmnOtpAtokenDao atokenDao = new CmnOtpAtokenDao(con);
            atokenDao.deleteLimitOver(now);

            //「使用されていない」OAuth認証トークンを削除する
            String[] oauthTableNameList = new String[] {
                    "CMN_OTP_CONF",
                    "SML_ADMIN",
                    "WML_ACCOUNT",
                    "ANP_CMN_CONF"
            };
            CmnOauthTokenDao cotDao = new CmnOauthTokenDao(con);
            List<Integer> cotSidList = cotDao.getNotUsedSidList(oauthTableNameList);

            cotDao.deleteToken(cotSidList);
            con.commit();

            log__.info("各フォルダー容量の確認と登録 開始");


            //各フォルダー容量の確認と登録
            GSContext gsContext = param.getGsContext();
            String appRootPath = (String) gsContext.get(GSContext.APP_ROOT_PATH);
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.updateFolderSize(con, appRootPath);
            con.commit();

            log__.info("使用データサイズの再集計 開始");
            //使用データサイズの再集計
            ManDatausedSumDao dataUsedDao = new ManDatausedSumDao(con);
            ManDatausedSumModel sumMdl = dataUsedDao.getSumData();
            dataUsedDao.delete();
            sumMdl.setSumType(GSConst.USEDDATA_SUMTYPE_TOTAL);
            dataUsedDao.insert(sumMdl);


            long usedDisk = 0;
            usedDisk += sumMdl.getCmnLogSize();
            usedDisk += sumMdl.getCmnLoginHistorySize();
            UsedSizeBiz usedSizeBiz = new UsedSizeBiz();
            usedSizeBiz.entryPluginUsedDisk(con, GSConstMain.PLUGIN_ID_MAIN, usedDisk);

            con.commit();
            log__.info("使用データサイズの再集計 終了");

            success = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsException", e);
            JDBCUtil.rollback(con);
            logException = e;
            throw e;
        } catch (Exception e) {
            logException = e;
            throw e;
        } finally {
            if (success) {
                DayJob.outPutFinishLog(con, param.getDomain(), pluginName, startTime);
            } else {
                DayJob.outPutFailedLog(con, param.getDomain(), pluginName, logException);
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
        //期限切れファイルキャッシュを削除
        try {
            FileCache.expiredCache();
        } catch (Exception e) {
            log__.error("期限切れファイルキャッシュの削除に失敗", e);
        }

        boolean commit = false;
        try {
            //ユーザ件数の再集計
            CmnUsrmCountDao usrCountDao = new CmnUsrmCountDao(con);
            UDate date = new UDate();
            usrCountDao.updateUserCount(date.cloneUDate());
            if (date.getIntHour() == 0) {
                UDate prev = date.cloneUDate();
                prev.addDay(-1);
                usrCountDao.updateUserCount(prev);
            }


            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("メイン 1時間毎バッチの実行に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <p>5分間隔で実行されるJob
     * @param con DBコネクション
     * @param param バッチ処理時に使用する情報
     * @throws SQLException SQL実行時例外
     */
    public void do5mBatch(Connection con, IBatchModel param) throws SQLException {
        UDate now = new UDate();
        CmnInfoSendDateDao csdDao = new CmnInfoSendDateDao(con);
        UDate sendDate = csdDao.getSendDate();
        //現在日時 > 利用状況次回送信日時の時に、利用状況を送信する
        if (sendDate == null || sendDate.compareDateYMDHM(now) > UDate.EQUAL) {
            log__.info("現在日時 > 次回送信日時のため、利用状況送信処理開始");
            HttpURLConnection hpCon = null;
            String repoUrl = NullDefault.getString(ConfigBundle.getValue("SYSTEM_REPORT_URL"), "");
            log__.info("利用状況送信先：" + repoUrl);
            boolean commit = false;
            try {

                URL url = new URL(repoUrl);
                hpCon = (HttpURLConnection) url.openConnection();
                hpCon.setDoOutput(true);
                hpCon.setRequestMethod("POST");
                hpCon.setUseCaches(false);
                hpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
                hpCon.setConnectTimeout(100000);
                OutputStreamWriter out = new OutputStreamWriter(hpCon.getOutputStream());

                GSContext context = param.getGsContext();
                String domain = param.getDomain();

                String confDbDir = ConfigBundle.getValue("GSDATA_DIR");
                if (StringUtil.isNullZeroString(confDbDir)) {
                    confDbDir = context.get(GSContext.APP_ROOT_PATH).toString();
                }

                LicenseModel lmdl = null;
                IGsResourceManager iGsManager = GroupSession.getResourceManager();
                if (context != null) {
                    lmdl = (LicenseModel) iGsManager.getLicenseMdl(domain);
                }
                log__.info("各種設定の読み取り完了");

                CommonBiz cmnBiz = new CommonBiz();
                //システム情報パラメータ
                String sysInfoParam =
                        cmnBiz.getSystemInfoReqParam(con, domain, confDbDir,
                                __getAppRootPath(context), __getTempPath(domain), lmdl);
                //プラグイン利用状況リストパラメータ
                PluginConfig pconfig = __getPluginConfig(domain);
                List <CliPluginModel> pluginList = cmnBiz.getPluginInfo(con, pconfig);
                String pluginInfoParam = cmnBiz.getPluginInfoReqParam(pluginList);

                String dataUsedParam = cmnBiz.getDatausedParam(con, pconfig);

                String reqParam = sysInfoParam + pluginInfoParam + dataUsedParam;
                log__.info("利用状況パラメータの取得完了");

                out.write(reqParam);
                out.close();
                hpCon.connect();
                // HTTPレスポンスコード
                final int status = hpCon.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    log__.info("利用状況送信処理に成功");
                    // 通信に成功した場合6ヶ月後に再度送信がされるように変更
                    now.addMonth(6);
                    if (sendDate == null) {
                        csdDao.insert(now);
                    } else {
                        csdDao.update(now);
                    }
                    con.commit();
                    commit = true;
                    log__.info("次回送信日の更新：" + now);
                }
            } catch (SQLException e) {
                log__.warn("利用状況取得に失敗：" + e);
            } catch (IOException e) {
                log__.warn("利用状況送信に失敗：" + e);
            } catch  (Exception e)  {
                log__.warn("利用状況送信処理で例外発生：" + e);
            } finally {
                if (!commit) {
                    con.rollback();
                }
                if (hpCon != null) {
                    // コネクションを切断
                    hpCon.disconnect();
                }
            }
        }
    }

    /**
     * [機  能] アプリケーションのルートパスを返す
     * [解  説]
     * [備  考]
     * @param gscontext GSContext
     * @return パス
     */
    private String __getAppRootPath(GSContext gscontext) {
        Object tmp = gscontext.get(GSContext.APP_ROOT_PATH);
        if (tmp == null) {
            return null;
        }
        if (!(tmp instanceof String)) {
            return null;
        }
        String path = (String) tmp;
        return path;
    }

    /**
     * [機  能] アプリケーションのテンポラリディレクトリのパスを返す
     * [解  説]
     * [備  考]
     * @param domain ドメイン
     * @return パス
     */
    private String __getTempPath(String domain) {

        String tmp = GroupSession.getResourceManager().getTempPath(domain);

        if (StringUtil.isNullZeroString(tmp)) {
            return null;
        }

        return tmp;
    }

    /**
     * [機  能] プラグイン情報を取得します。
     * [解  説]
     * [備  考]
     * @param domain ドメイン
     * @return PluginConfig
     */
    private PluginConfig __getPluginConfig(String domain) {

        PluginConfig pconfig = null;
        pconfig = GroupSession.getResourceManager().getPluginConfig(domain);
        if (pconfig == null) {
            log__.fatal("プラグインコンフィグの取得に失敗");
        }
        return pconfig;
    }
}
