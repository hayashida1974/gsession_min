package jp.groupsession.v2.man.man150kn;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.lic.LicenseOperation;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.FrePluginModel;
import jp.groupsession.v2.man.model.FreSysinfoModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man150knBiz.class);


    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl リクエスト情報
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリ
     * @throws IOToolsException 入出力時例外
     * @throws IOException  ファイル操作時例外
     * @throws EncryptionException 文字列複合化時例外
     */
    public void setInitData(Man150knParamModel paramMdl, RequestModel reqMdl, String tempDir)
        throws IOToolsException, EncryptionException, IOException {
        log__.debug("setInitData Start");

        String saveFileName = "";

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
            }

            String fullPath = tempDir + saveFileName;
            File impFile = new File(fullPath);

            String licenseId = "";
            String licenseCom = "";
            int licenseKbn = 0;
            ArrayList<LicenseModel> pluginList = new ArrayList<LicenseModel>();

            LicenseOperation lop = new LicenseOperation();
            LicenseModel lmdl = lop.getLicenseFileData(impFile);
            GsMessage gsMsg = new GsMessage(reqMdl);

            if (lmdl != null) {

                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseId())) {
                    licenseId = String.valueOf(lmdl.getLicenseId());
                }

                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseCom())) {
                    licenseCom = String.valueOf(lmdl.getLicenseCom());
                }

                if (!StringUtil.isNullZeroString(lmdl.getLicenseFreeSid())) {
                    //無料ライセンス
                    licenseKbn = GSConstLicese.LICENSE_KBN_FREE;
                } else {
                    //有償ライセンス
                    licenseKbn = GSConstLicese.LICENSE_KBN_NOT_FREE;
                }

                LicenseModel pmdl = null;
                //プラグイン情報 サポート
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitSupport())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(gsMsg.getMessage("cmn.support"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitSupport());
                    pluginList.add(pmdl);
                }

                //プラグイン情報 モバイル
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitMobile())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(gsMsg.getMessage("mobile.4"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitMobile());
                    pluginList.add(pmdl);
                }

                //プラグイン情報 CrossRide
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitCrossRide())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(GSConstLicese.PLUGIN_NAME_CROSSRIDE);
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitCrossRide());
                    pluginList.add(pmdl);
                }
            }

            paramMdl.setMan150LicenseId(licenseId);
            paramMdl.setMan150LicenseCom(licenseCom);
            paramMdl.setMan150LicenseKbn(licenseKbn);
            paramMdl.setMan150PluginList(pluginList);
        }
    }

    /**
     * <br>[機  能] ライセンスファイルを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param gs GSコンテキスト
     * @param domain ドメイン
     * @param con コネクション
     * @throws IOToolsException 入出力時例外
     * @throws IOException  入出力時例外
     * @throws EncryptionException 文字列複合化時例外
     * @throws SQLException
     */
    public void importLicenseFile(String appRoot,
                                  String tempDir,
                                  GSContext gs,
                                  String domain,
                                  Connection con)
        throws IOToolsException, IOException, EncryptionException, SQLException {

        String saveFileName = "";

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
            }

            String fullPath = tempDir + saveFileName;
            File impFile = new File(fullPath);

            LicenseOperation lop = new LicenseOperation();
            lop.updateLicenseFile(
                    appRoot, impFile, domain);
            lop.updateGSContext(impFile, domain, con);
        }
    }


    /**
     * <br>[機  能] ライセンスファイルを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param context コンテキスト
     * @param dbDirPath DBディレクトリパス
     * @param appRootPath アプリケーションルートパス
     * @param tempPath テンポラリディレクトリ
     * @param pconfig プラグイン設定情報
     * @param lmdl ライセンスモデル
     * @return JSON側データ
     * @throws Exception 実行例外
     */
    public String getSystemInfo(Connection con,
            RequestModel reqMdl,
            ServletContext context,
            String dbDirPath,
            String appRootPath,
            String tempPath,
            PluginConfig pconfig,
            LicenseModel lmdl)
                    throws Exception {

        //システム情報モデル
        FreSysinfoModel freSysinfoModel =
                __getSystemInfo(con, reqMdl, context, dbDirPath, appRootPath, tempPath, lmdl);
        //プラグイン利用状況リスト
        List <FrePluginModel> pluginList = __getPluginInfo(con, reqMdl, pconfig);

        String ret = __getSystemInfoReqParam(freSysinfoModel);
        ret += __getPluginInfoReqParam(pluginList);

        return ret;
    }


    /**
     * <br>[機  能] システム情報モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param context コンテキスト
     * @param dbDirPath DBディレクトリパス
     * @param appRootPath アプリケーションルートパス
     * @param tempPath テンポラリディレクトリ
     * @param lmdl ライセンスモデル
     * @return システム情報
     * @throws Exception 実行例外
     */
    private FreSysinfoModel __getSystemInfo(
            Connection con,
            RequestModel reqMdl,
            ServletContext context,
            String dbDirPath,
            String appRootPath,
            String tempPath,
            LicenseModel lmdl)
            throws Exception {

        GsMessage gsmsg = new GsMessage(reqMdl);
        FreSysinfoModel smdl = new FreSysinfoModel();
        CommonBiz cmnBiz = new CommonBiz();

        //GSシリアル番号
        CmnContmDao cntDao = new CmnContmDao(con);
        String gsUid = cntDao.getGsUid();
        if (!StringUtil.isNullZeroString(gsUid)) {
            smdl.setFliGssn(LicenseOperation.getDecryString(gsUid));
        }

        //ライセンスID
        smdl.setFliLiid(lmdl.getLicenseId());

        //ライセンスSID
        smdl.setFliSid(NullDefault.getInt(lmdl.getLicenseFreeSid(), 0));

        //ライセンス区分
        int licenseKbn = 0;
        if (!StringUtil.isNullZeroString(lmdl.getLicenseFreeSid())) {
            //無料ライセンス
            licenseKbn = GSConstLicese.LICENSE_KBN_FREE;
        } else {
            //有償ライセンス
            licenseKbn = GSConstLicese.LICENSE_KBN_NOT_FREE;
        }
        smdl.setFsiKbn(licenseKbn);

        //登録日時
        smdl.setFsiAdate(new UDate());

        //会社名
        smdl.setFsiCampany(lmdl.getLicenseCom());

        //サポート期限
        //期限をセパレータで分解
        if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitSupport())) {
            String[] splitSpLimitStr = lmdl.getLicenseLimitSupport().split("/");
            UDate spDate  = UDateUtil.getUDate2(
                    splitSpLimitStr[0], splitSpLimitStr[1], splitSpLimitStr[2]);
            spDate.setMaxHhMmSs();
            smdl.setFsiSplimit(spDate);
        }

        //GSモバイル期限
        if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitMobile())) {
            String[] splitMbLimitStr = lmdl.getLicenseLimitMobile().split("/");
            UDate mbDate  = UDateUtil.getUDate2(
                    splitMbLimitStr[0], splitMbLimitStr[1], splitMbLimitStr[2]);
            mbDate.setMaxHhMmSs();
            smdl.setFsiMblimit(mbDate);
        }

        //CrossRide期限
        if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitCrossRide())) {
            String[] splitCrLimitStr = lmdl.getLicenseLimitCrossRide().split("/");
            UDate crDate  = UDateUtil.getUDate2(
                    splitCrLimitStr[0], splitCrLimitStr[1], splitCrLimitStr[2]);
            crDate.setMaxHhMmSs();
            smdl.setFsiCrlimit(crDate);
        }

        //登録済みユーザ数
        UserBiz userBiz = new UserBiz();
        smdl.setFsiUsers(userBiz.getActiveUserCount(con));

        //GS利用開始日時
        UDate initDate = cmnBiz.getInitialDate(con);
        smdl.setFsiSdate(initDate);

        //利用開始当時のGSバージョン

        //使用中GSバージョン
        smdl.setFsiGsver(GSConst.VERSION);;

        //OS名
        MainCommonBiz manBiz = new MainCommonBiz();
        smdl.setFsiOsname(manBiz.getServerOS());

        //CPUのコア数
        smdl.setFsiCpucore(manBiz.getServerCpuCore());

        //JVMビットモード
        smdl.setFsiJvmbit(manBiz.getServerJVM());

        //サーブレットコンテナバージョン

        //Javaバージョン
        smdl.setFsiJavaver(manBiz.getServerJava());

        //メモリ使用
        DecimalFormat format1 = new DecimalFormat("#,###MB");
        DecimalFormat format2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long used = max - free;
        double ratio = (used * 100 / (double) max);
        smdl.setFsiUsedmem(format1.format(used) + " (" + format2.format(ratio) + ")");

        //メモリ最大
        smdl.setFsiMaxmem(format1.format(max));

        //空きディスク容量
        smdl.setFsiAdisc(manBiz.getServerFreeSpace(dbDirPath));

        //コネクション使用状況
        smdl.setFsiCon(manBiz.getServerConnection(reqMdl));

        ResourceBundle conOp = ResourceBundle.getBundle("connectOption");
        //H2コネクション設定
        smdl.setFsiH2LockMode(NullDefault.getInt(conOp.getString("LOCK_MODE"), 0));
        //H2コネクション設定
        smdl.setFsiH2LockTimeout(NullDefault.getInt(conOp.getString("LOCK_TIMEOUT"), 0));
        //H2コネクション設定
        smdl.setFsiH2DefaultLockTimeout(
                NullDefault.getInt(conOp.getString("DEFAULT_LOCK_TIMEOUT"), 0));
        //H2コネクション設定
        smdl.setFsiH2MultiThreaded(NullDefault.getInt(conOp.getString("MULTI_THREADED"), 0));
        //H2コネクション設定
        smdl.setFsiH2Ifexists(NullDefault.getString(conOp.getString("IFEXISTS"), ""));
        //H2コネクション設定
        smdl.setFsiH2Autocommit(NullDefault.getString(conOp.getString("AUTOCOMMIT"), ""));
        //H2コネクション設定
        smdl.setFsiH2DbCloseOnExit(NullDefault.getString(conOp.getString("DB_CLOSE_ON_EXIT"), ""));
        //H2コネクション設定
        smdl.setFsiH2CacheSize(NullDefault.getInt(conOp.getString("CACHE_SIZE"), 0));
        //H2コネクション設定
        smdl.setFsiH2PageSize(NullDefault.getInt(conOp.getString("PAGE_SIZE"), 0));
        //H2コネクション設定
        smdl.setFsiH2MaxLengthInplaceLob(
                NullDefault.getInt(conOp.getString("MAX_LENGTH_INPLACE_LOB"), 0));
        //H2コネクション設定
        smdl.setFsiH2CacheType(NullDefault.getString(conOp.getString("CACHE_TYPE"), ""));
        //H2コネクション設定
        smdl.setFsiH2Mvcc(NullDefault.getString(conOp.getString("MVCC"), ""));
        //H2コネクション設定
        smdl.setFsiH2QueryTimeout(NullDefault.getInt(conOp.getString("QUERY_TIMEOUT"), 0));
        //H2コネクション設定
        smdl.setFsiH2QueryTimeoutBatch(
                NullDefault.getInt(conOp.getString("QUERY_TIMEOUT_BATCH"), 0));
        //H2コネクション設定
        smdl.setFsiH2MaxLogSize(NullDefault.getInt(conOp.getString("MAX_LOG_SIZE"), 0));

        //ディレクトリ情報(GSDATA_DIR)
        smdl.setFsiGsdataDir(__pathNormalization(cmnBiz.getFileRootPath(appRootPath)));
        //ディレクトリ情報(BACKUP_DIR)
        smdl.setFsiBackupDir(__pathNormalization(CommonBiz.getBackupDirPath(appRootPath)));
        //ディレクトリ情報(FILEKANRI_DIR)
        smdl.setFsiFilekanriDir(
                __pathNormalization(cmnBiz.getFileRootPathForFileKanri(appRootPath)));
        //ディレクトリ情報(WEBMAIL_DIR)
        smdl.setFsiWebmailDir(__pathNormalization(cmnBiz.getFileRootPathForWebmail(appRootPath)));

        CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
        CmnBackupConfModel backConfMdl = backConfDao.select();

        String kankaku = null;
        String sedai = null;
        String syuturyoku = null;

        if (backConfMdl == null || backConfMdl.getBucInterval() == 0) {
            kankaku = gsmsg.getMessage("cmn.notset");
        } else {
            kankaku = __getKankaku(gsmsg, backConfMdl);
            sedai = backConfMdl.getBucGeneration() + gsmsg.getMessage("fil.6");

            if (backConfMdl.getBucZipout() == 0) {
                syuturyoku = gsmsg.getMessage("cmn.not.compress");
            } else {
                syuturyoku = gsmsg.getMessage("main.zip.format.output");
            }
        }

        //バックアップ間隔
        smdl.setFsiBackupInterval(kankaku);
        //バックアップ世代数
        smdl.setFsiBackupGeneration(sedai);

        //バックアップ圧縮有無
        smdl.setFsiBackupZip(syuturyoku);

        //バッチ処理情報の取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        if (batMdl != null) {
            smdl.setFsiBatchjob(String.valueOf(batMdl.getBatFrDate()));
        }

        //テンポラリディレクトリパス
        smdl.setFsiTempdir(tempPath);

        //COMPLIANT_VERSION
        smdl.setFsiCompliantVersion(
                NullDefault.getString(ConfigBundle.getValue("COMPLIANT_VERSION"), ""));
        //GS_MBA_IOS_VERSION
        smdl.setFsiGsMbaIosVersion(
                NullDefault.getString(ConfigBundle.getValue("GS_MBA_IOS_VERSION"), ""));
        //GS_CAL_IOS_VERSION
        smdl.setFsiGsCalIosVersion(
                NullDefault.getString(ConfigBundle.getValue("GS_CAL_IOS_VERSION"), ""));
        //GS_ADR_IOS_VERSION
        smdl.setFsiGsAdrIosVersion(
                NullDefault.getString(ConfigBundle.getValue("GS_ADR_IOS_VERSION"), ""));
        //GS_SML_IOS_VERSION
        smdl.setFsiGsSmlIosVersion(
                NullDefault.getString(ConfigBundle.getValue("GS_SML_IOS_VERSION"), ""));
        //GS_WML_IOS_VERSION
        smdl.setFsiGsWmlIosVersion(
                NullDefault.getString(ConfigBundle.getValue("GS_WML_IOS_VERSION"), ""));
        //FIL_ALL_SEARCH_USE
        smdl.setFsiFilAllSearchUse(
                NullDefault.getInt(ConfigBundle.getValue("FIL_ALL_SEARCH_USE"), 0));
        //MAIL_PORT_NUMBER
        smdl.setFsiMailPortNumber(
                NullDefault.getInt(ConfigBundle.getValue("MAIL_PORT_NUMBER"), 0));
        //PORTLET_MAXLENGTH
        smdl.setFsiPortletMaxlength(
                NullDefault.getInt(ConfigBundle.getValue("PORTLET_MAXLENGTH"), 0));
        //RSV_PRINT_USE
        smdl.setFsiRsvPrintUse(NullDefault.getInt(ConfigBundle.getValue("RSV_PRINT_USE"), 0));
        //MAILRECEIVE_THREAD_MAXCOUNT
        smdl.setFsiMailreceiveThreadMaxcount(
                NullDefault.getInt(ConfigBundle.getValue("MAILRECEIVE_THREAD_MAXCOUNT"), 0));
        //MAILRECEIVE_MAXCOUNT
        smdl.setFsiMailreceiveMaxcount(
                NullDefault.getInt(ConfigBundle.getValue("MAILRECEIVE_MAXCOUNT"), 0));
        //MAILRECEIVE_CONNECTTIMEOUT
        smdl.setFsiMailreceiveConnecttimeout(
                NullDefault.getInt(ConfigBundle.getValue("MAILRECEIVE_CONNECTTIMEOUT"), 0));
        //MAILRECEIVE_TIMEOUT
        smdl.setFsiMailreceiveTimeout(
                NullDefault.getInt(ConfigBundle.getValue("MAILRECEIVE_TIMEOUT"), 0));
        //MAILRECEIVE_RCVSVR_CHECKTIME
        smdl.setFsiMailreceiveRcvsvrChecktime(
                NullDefault.getInt(ConfigBundle.getValue("MAILRECEIVE_RCVSVR_CHECKTIME"), 0));
        //MAILBODY_LIMIT
        smdl.setFsiMailbodyLimit(
                NullDefault.getInt(ConfigBundle.getValue("MAILBODY_LIMIT"), 0));

        return smdl;
    }

    /**
     * <br>[機  能] リクエスト用システム情報モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smdl システム情報モデル
     * @return リクエスト用システム情報
     * @throws Exception 実行例外
     */
    private String __getSystemInfoReqParam(FreSysinfoModel smdl)
            throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("freSysinfoModel.fliSid=" + smdl.getFliSid());
        sb.append("&freSysinfoModel.fliGssn=" + smdl.getFliGssn());
        sb.append("&freSysinfoModel.fliLiid=" + smdl.getFliLiid());
        sb.append("&freSysinfoModel.fsiKbn=" + smdl.getFsiKbn());
        sb.append("&freSysinfoModel.fsiAdateStr=" + smdl.getFsiAdate());
        sb.append("&freSysinfoModel.fsiCampany="
                + URLEncoder.encode(smdl.getFsiCampany(), Encoding.UTF_8));
        if (smdl.getFsiSplimit() != null) {
            sb.append("&freSysinfoModel.fsiSplimitStr=" + smdl.getFsiSplimit());
        }
        if (smdl.getFsiMblimit() != null) {
            sb.append("&freSysinfoModel.fsiMblimitStr=" + smdl.getFsiMblimit());
        }
        if (smdl.getFsiCrlimit() != null) {
            sb.append("&freSysinfoModel.fsiCrlimitStr=" + smdl.getFsiCrlimit());
        }
        sb.append("&freSysinfoModel.fsiUsers=" + smdl.getFsiUsers());
        sb.append("&freSysinfoModel.fsiSdateStr=" + smdl.getFsiSdate());
        sb.append("&freSysinfoModel.fsiGsver=" + smdl.getFsiGsver());
        sb.append("&freSysinfoModel.fsiOsname=" + smdl.getFsiOsname());
        sb.append("&freSysinfoModel.fsiCpucore=" + smdl.getFsiCpucore());
        sb.append("&freSysinfoModel.fsiJvmbit=" + smdl.getFsiJvmbit());
        sb.append("&freSysinfoModel.fsiJavaver=" + smdl.getFsiJavaver());
        sb.append("&freSysinfoModel.fsiUsedmem=" + smdl.getFsiUsedmem());
        sb.append("&freSysinfoModel.fsiMaxmem=" + smdl.getFsiMaxmem());
        sb.append("&freSysinfoModel.fsiAdisc=" + smdl.getFsiAdisc());
        sb.append("&freSysinfoModel.fsiCon=" + smdl.getFsiCon());
        sb.append("&freSysinfoModel.fsiH2LockMode=" + smdl.getFsiH2LockMode());
        sb.append("&freSysinfoModel.fsiH2LockTimeout=" + smdl.getFsiH2LockTimeout());
        sb.append("&freSysinfoModel.fsiH2DefaultLockTimeout=" + smdl.getFsiH2DefaultLockTimeout());
        sb.append("&freSysinfoModel.fsiH2MultiThreaded=" + smdl.getFsiH2MultiThreaded());
        sb.append("&freSysinfoModel.fsiH2Ifexists=" + smdl.getFsiH2Ifexists());
        sb.append("&freSysinfoModel.fsiH2Autocommit=" + smdl.getFsiH2Autocommit());
        sb.append("&freSysinfoModel.fsiH2DbCloseOnExit=" + smdl.getFsiH2DbCloseOnExit());
        sb.append("&freSysinfoModel.fsiH2CacheSize=" + smdl.getFsiH2CacheSize());
        sb.append("&freSysinfoModel.fsiH2PageSize=" + smdl.getFsiH2PageSize());
        sb.append("&freSysinfoModel.fsiH2MaxLengthInplaceLob="
                + smdl.getFsiH2MaxLengthInplaceLob());
        sb.append("&freSysinfoModel.fsiH2CacheType=" + smdl.getFsiH2CacheType());
        sb.append("&freSysinfoModel.fsiH2Mvcc=" + smdl.getFsiH2Mvcc());
        sb.append("&freSysinfoModel.fsiH2QueryTimeout=" + smdl.getFsiH2QueryTimeout());
        sb.append("&freSysinfoModel.fsiH2QueryTimeoutBatch=" + smdl.getFsiH2QueryTimeoutBatch());
        sb.append("&freSysinfoModel.fsiH2MaxLogSize=" + smdl.getFsiH2MaxLogSize());
        sb.append("&freSysinfoModel.fsiGsdataDir="
                + URLEncoder.encode(smdl.getFsiGsdataDir(), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiBackupDir="
                + URLEncoder.encode(smdl.getFsiBackupDir(), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiFilekanriDir="
                + URLEncoder.encode(smdl.getFsiFilekanriDir(), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiWebmailDir="
                + URLEncoder.encode(smdl.getFsiWebmailDir(), Encoding.UTF_8));

        sb.append("&freSysinfoModel.fsiBackupInterval="
                + URLEncoder.encode(smdl.getFsiBackupInterval(), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiBackupGeneration="
                + URLEncoder.encode(
                        NullDefault.getString(smdl.getFsiBackupGeneration(), ""), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiBackupZip="
                + URLEncoder.encode(
                        NullDefault.getString(smdl.getFsiBackupZip(), ""), Encoding.UTF_8));

        sb.append("&freSysinfoModel.fsiBatchjob=" + smdl.getFsiBatchjob());
        sb.append("&freSysinfoModel.fsiTempdir="
                + URLEncoder.encode(smdl.getFsiTempdir(), Encoding.UTF_8));
        sb.append("&freSysinfoModel.fsiCompliantVersion=" + smdl.getFsiCompliantVersion());
        sb.append("&freSysinfoModel.fsiGsMbaIosVersion=" + smdl.getFsiGsMbaIosVersion());
        sb.append("&freSysinfoModel.fsiGsCalIosVersion=" + smdl.getFsiGsCalIosVersion());
        sb.append("&freSysinfoModel.fsiGsAdrIosVersion=" + smdl.getFsiGsAdrIosVersion());
        sb.append("&freSysinfoModel.fsiGsSmlIosVersion=" + smdl.getFsiGsSmlIosVersion());
        sb.append("&freSysinfoModel.fsiGsWmlIosVersion=" + smdl.getFsiGsWmlIosVersion());
        sb.append("&freSysinfoModel.fsiFilAllSearchUse=" + smdl.getFsiFilAllSearchUse());
        sb.append("&freSysinfoModel.fsiMailPortNumber=" + smdl.getFsiMailPortNumber());
        sb.append("&freSysinfoModel.fsiPortletMaxlength=" + smdl.getFsiPortletMaxlength());
        sb.append("&freSysinfoModel.fsiRsvPrintUse=" + smdl.getFsiRsvPrintUse());
        sb.append("&freSysinfoModel.fsiMailreceiveThreadMaxcount="
                + smdl.getFsiMailreceiveThreadMaxcount());
        sb.append("&freSysinfoModel.fsiMailreceiveMaxcount=" + smdl.getFsiMailreceiveMaxcount());
        sb.append("&freSysinfoModel.fsiMailreceiveConnecttimeout="
                + smdl.getFsiMailreceiveConnecttimeout());
        sb.append("&freSysinfoModel.fsiMailreceiveTimeout=" + smdl.getFsiMailreceiveTimeout());
        sb.append("&freSysinfoModel.fsiMailreceiveRcvsvrChecktime="
                + smdl.getFsiMailreceiveRcvsvrChecktime());
        sb.append("&freSysinfoModel.fsiMailbodyLimit=" + smdl.getFsiMailbodyLimit());

        return sb.toString();
    }

    /**
     * <br>[機  能] リクエスト用プラグイン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginList プラグイン情報リスト
     * @return リクエスト用システム情報
     * @throws Exception 実行例外
     */
    private String __getPluginInfoReqParam(List <FrePluginModel> pluginList)
            throws Exception {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pluginList.size(); i++) {
            FrePluginModel mdl = pluginList.get(i);
            sb.append("&pluginList[" + i + "].fplPid=" + mdl.getFplPid());
            sb.append("&pluginList[" + i + "].fplPname="
                    + URLEncoder.encode(
                            NullDefault.getString(mdl.getFplPname(), ""), Encoding.UTF_8));
            sb.append("&pluginList[" + i + "].fplUsekbn=" + mdl.getFplUsekbn());
        }

        return sb.toString();
    }

    /**
    *
    * <br>[機  能]パスの正規化を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param path 各ディレクトリのパス
    * @return 正規化を行ったパス
    */
   private String __pathNormalization(String path) {

       //ファイルセパレートを全てスラッシュに変換
       path = IOTools.replaceSlashFileSep(path);

       //スラッシュが余分についていた場合は取り除く
       if (path.endsWith("//")) {
           path = path.substring(0, path.length() - 1);
       }
       return path;
   }



   /**
    * <br>[機  能] バックアップ情報より間隔（文字）を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param gsmsg GSメッセージ
    * @param backConfMdl バックアップモデル
    * @return 間隔情報
    */
   private String __getKankaku(GsMessage gsmsg, CmnBackupConfModel backConfMdl) {

       //曜日
       String kankaku = null;
       String dow = null;
       if (backConfMdl.getBucInterval() != 1) {
           switch (backConfMdl.getBucDow()) {
           case (1):
               dow = gsmsg.getMessage("cmn.sunday3");
           break;
           case (2):
               dow = gsmsg.getMessage("cmn.monday3");
           break;
           case (3):
               dow = gsmsg.getMessage("cmn.tuesday3");
           break;
           case (4):
               dow = gsmsg.getMessage("cmn.wednesday3");
           break;
           case (5):
               dow = gsmsg.getMessage("cmn.thursday3");
           break;
           case (6):
               dow = gsmsg.getMessage("main.src.man080.7");
           break;
           case (7):
               dow = gsmsg.getMessage("cmn.saturday3");
           break;
           default:
               break;
           }
       }

       switch (backConfMdl.getBucInterval()) {
       case (1):
           kankaku = gsmsg.getMessage("cmn.everyday");
       break;
       case (2):
           kankaku = gsmsg.getMessage("cmn.weekly2") + dow;
       break;
       case (3):
           kankaku = gsmsg.getMessage("cmn.monthly.2")
           + gsmsg.getMessage("main.src.man025.3")
           + backConfMdl.getBucWeekMonth() + dow;
       break;
       default:
           kankaku = gsmsg.getMessage("cmn.notset");
           break;
       }

       return kankaku;
   }

   /**
    * <br>[機  能] システム情報モデルを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param pconfig プラグイン設定情報
    * @return システム情報
    * @throws SQLException SQL実行例外
    */
   private List <FrePluginModel> __getPluginInfo(
           Connection con, RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

       List <FrePluginModel> ret = new ArrayList<>();
       FrePluginModel mdl = null;

       //トップ表示設定を取得
       CmnTdispDao dispDao = new CmnTdispDao(con);
       //管理者ユーザの登録値を取得(使用に設定されているプラグインの取得)
       List<CmnTdispModel> dispList = dispDao.getAdminTdispList();

       //存在チェック用プラグインリスト
       ArrayList<String> checkList = new ArrayList<>();

       if (dispList != null || !dispList.isEmpty()) {
           for (CmnTdispModel dbDispMdl : dispList) {
               if (dbDispMdl.getTdpPid().equals("license")
                       || dbDispMdl.getTdpPid().equals(GSConst.PLUGINID_COMMON)
                       || dbDispMdl.getTdpPid().equals("help")) {
                   continue;
               }
               Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
               if (plugin != null) {

                   mdl = new FrePluginModel();
                   mdl.setFplPid(plugin.getId());
                   mdl.setFplPname(plugin.getName(reqMdl));
                   mdl.setFplUsekbn(GSConstMain.PLUGIN_USE);
                   ret.add(mdl);
                   checkList.add(plugin.getName(reqMdl));
               }
           }
       }
       //未使用に設定されているプラグインの取得
       for (Plugin plugin : pconfig.getPluginDataList()) {
           if (plugin != null
           && !plugin.getId().equals(GSConst.PLUGINID_COMMON)
           && !plugin.getId().equals("help")
           && !plugin.getId().equals("license")) {

               if (checkList.indexOf(plugin.getName(reqMdl)) == -1) {
                   mdl = new FrePluginModel();
                   mdl.setFplPid(plugin.getId());
                   mdl.setFplPname(plugin.getName(reqMdl));
                   mdl.setFplUsekbn(GSConstMain.PLUGIN_NOT_USE);
                   ret.add(mdl);
                   checkList.add(plugin.getName(reqMdl));
               }

           }
       }

       return ret;
   }

}