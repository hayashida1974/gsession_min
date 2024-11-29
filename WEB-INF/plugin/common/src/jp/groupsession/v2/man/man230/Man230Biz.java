package jp.groupsession.v2.man.man230;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.dao.base.CmnDatafolderDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.model.base.CmnDatafolderModel;
import jp.groupsession.v2.convert.dao.VersionDao;
import jp.groupsession.v2.convert.model.VersionModel;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.man230.model.Man230ConnectionOptionModel;
import jp.groupsession.v2.man.man230.model.Man230GsDataConfModel;
import jp.groupsession.v2.man.man230.model.Man230GsMobileSuiteVarsionModel;
import jp.groupsession.v2.man.man230.model.Man230ServerNameModel;
import jp.groupsession.v2.man.man230.model.Man230WebMailModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] メイン 管理者設定 システム情報画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230Biz {

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param context コンテキスト
     * @param dbDirPath DBディレクトリのパス
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @param pconfig プラグイン設定情報
     * @throws Exception 実行エラー
     */
    public void setInitData(Connection con, Man230ParamModel paramMdl,
            ServletContext context, String dbDirPath,
            RequestModel reqMdl, String appRootPath, PluginConfig pconfig)
                    throws Exception {

        //バージョン(DB)
        VersionDao verDao = new VersionDao(con);
        VersionModel verMdl = verDao.select();
        paramMdl.setMan230GSVersionDB(verMdl.getVerVersion());

        //バックアップ設定を取得
        CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
        CmnBackupConfModel backConfMdl = backConfDao.select();
        if (backConfMdl != null) {
            paramMdl.setMan230BackUpConf(backConfMdl);
        }

        //バッチ処理情報の取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        if (batMdl != null) {
            paramMdl.setMan230CmnBatchJob(batMdl);
        }

        //OS
        MainCommonBiz manBiz = new MainCommonBiz();
        paramMdl.setMan230Os(manBiz.getServerOS());

        //CPUコア数
        paramMdl.setMan230CpuCoreNum(manBiz.getServerCpuCore());

        //JVMビットモード
        paramMdl.setMan230JvmBitMode(manBiz.getServerJVM());

        //サーブレットコンテナバージョン
        paramMdl.setMan230J2ee(manBiz.getServerJ2ee(context));

        //java
        paramMdl.setMan230Java(manBiz.getServerJava());

        //メモリ情報
        __getMemoryInfo(paramMdl);

        //CPUプロセッサ
        paramMdl.setMan230CpuProcesser(manBiz.getCPUProcesser());

        //メモリサイズ
        paramMdl.setMan230MemSize(manBiz.getMemorySize());

        //ディスク情報サイズ
        paramMdl.setMan230DiskInfo(manBiz.getDiskInfo());

        //DB区分
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        paramMdl.setMan230DbKbn(String.valueOf(dbUtil.getDbType()));
        
        //データフォルダ容量
        __getDataFolderInfo(paramMdl, con);
        JDBCUtil.closeConnection(con);
        
        //ディスクの空き容量
        paramMdl.setMan230FreeDSpace(manBiz.getServerFreeSpace(dbDirPath));

        //DBのコネクション件数を取得する
        paramMdl.setMan230ConnectionCount(manBiz.getServerConnection(reqMdl));

        //H2コネクション設定の取得
        __getH2ConnectionConf(paramMdl);

        //gsdata.confの情報取得
        __getGsDataConf(paramMdl, appRootPath, reqMdl);

        //gsMobileSuitVersion情報の取得
        __getGsMobileSuiteVersion(paramMdl);

        //filesearch.conf情報の取得
        __getFileSearch(paramMdl);

        //mailserver.conf情報の取得
        __getMailServer(paramMdl);

        //portal.conf情報の取得
        __getPortal(paramMdl);

        //reserve.conf情報の取得
        __getReserve(paramMdl);

        //webmail.conf情報の取得
        __getWebMail(paramMdl);
    }

    /**
     *
     * <br>[機  能]gsdata.confの設定ファイルを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエストモデル
     */
    private void __getGsDataConf(Man230ParamModel paramMdl,
            String appRootPath, RequestModel reqMdl) {
        Man230GsDataConfModel gsDataConf = new Man230GsDataConfModel();
        CommonBiz cmb = new CommonBiz();

        //(デフォルト)表記の作成
        GsMessage gsmsg = new GsMessage(reqMdl);
        StringBuilder sb = new StringBuilder();
        sb.append("　(");
        sb.append(gsmsg.getMessage("cmn.default"));
        sb.append(")");
        String defStr = sb.toString();

        StringBuilder dspDirPath = new StringBuilder();
        String normalizPath = null;

        //正規化したパスを格納
        normalizPath = __pathNormalization(cmb.getGSDataDirPath(appRootPath));
        dspDirPath.append(normalizPath);
        if (ConfigBundle.getValue("GSDATA_DIR") == null) {
            dspDirPath.append(defStr);
        }
        gsDataConf.setGsDataDir((dspDirPath.toString()));

        dspDirPath = new StringBuilder();

        normalizPath = __pathNormalization(CommonBiz.getBackupDirPath(appRootPath));
        dspDirPath.append(normalizPath);
        if (ConfigBundle.getValue("BACKUP_DIR") == null) {
            dspDirPath.append(defStr);
        }
        gsDataConf.setBackUpDir((dspDirPath.toString()));

        dspDirPath = new StringBuilder();

        normalizPath = __pathNormalization(cmb.getFileRootPathForFileKanri(appRootPath));
        dspDirPath.append(normalizPath);
        if (ConfigBundle.getValue("FILEKANRI_DIR") == null) {
            dspDirPath.append(defStr);
        }
        gsDataConf.setFileKanriDir((dspDirPath.toString()));

        dspDirPath = new StringBuilder();
        normalizPath = __pathNormalization(cmb.getFileRootPathForWebmail(appRootPath));
        dspDirPath.append(normalizPath);
        if (ConfigBundle.getValue("WEBMAIL_DIR") == null) {
            dspDirPath.append(defStr);
        }
        gsDataConf.setWebMailDir((dspDirPath.toString()));

        paramMdl.setMan230GsdataConf(gsDataConf);
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
     *
     * <br>[機  能]H2コネクション設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getH2ConnectionConf(Man230ParamModel paramMdl) {

        ResourceBundle conOp = ResourceBundle.getBundle("connectOption");
        Man230ConnectionOptionModel optionMdl = new Man230ConnectionOptionModel();

        optionMdl.setLockMode(NullDefault.getString(conOp.getString("LOCK_MODE"), ""));
        optionMdl.setLockTimeOut(NullDefault.getString(conOp.getString("LOCK_TIMEOUT"), ""));
        optionMdl.setDefLockTimeOut(
                NullDefault.getString(conOp.getString("DEFAULT_LOCK_TIMEOUT"), ""));
        optionMdl.setMultiThreaded(NullDefault.getString(conOp.getString("MULTI_THREADED"), ""));
        optionMdl.setIfExists(NullDefault.getString(conOp.getString("IFEXISTS"), ""));
        optionMdl.setAutoCommit(NullDefault.getString(conOp.getString("AUTOCOMMIT"), ""));
        optionMdl.setDbCloseOnExit(NullDefault.getString(conOp.getString("DB_CLOSE_ON_EXIT"), ""));
        optionMdl.setCacheSize(NullDefault.getString(conOp.getString("CACHE_SIZE"), ""));
        optionMdl.setPageSize(NullDefault.getString(conOp.getString("PAGE_SIZE"), ""));
        optionMdl.setMaxLengthImplace(
                NullDefault.getString(conOp.getString("MAX_LENGTH_INPLACE_LOB"), ""));
        optionMdl.setCacheType(NullDefault.getString(conOp.getString("CACHE_TYPE"), ""));
        optionMdl.setMvcc(NullDefault.getString(conOp.getString("MVCC"), ""));
        optionMdl.setQueryTimeOut(NullDefault.getString(conOp.getString("QUERY_TIMEOUT"), ""));
        optionMdl.setQueryTimeOutBatch(
                NullDefault.getString(conOp.getString("QUERY_TIMEOUT_BATCH"), ""));
        optionMdl.setMaxLogSize(NullDefault.getString(conOp.getString("MAX_LOG_SIZE"), ""));
        paramMdl.setMan230ConnectionOp(optionMdl);
    }

    /**
     *
     * <br>[機  能]GsMobileSuiteVarsion.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getGsMobileSuiteVersion(Man230ParamModel paramMdl) {

        Man230GsMobileSuiteVarsionModel gmsvMdl = new Man230GsMobileSuiteVarsionModel();

        gmsvMdl.setCompliantVer(
                NullDefault.getString(ConfigBundle.getValue("COMPLIANT_VERSION"), ""));
        gmsvMdl.setGsMbaIosVer(
                NullDefault.getString(ConfigBundle.getValue("GS_MBA_IOS_VERSION"), ""));
        gmsvMdl.setGsCalIosVer(
                NullDefault.getString(ConfigBundle.getValue("GS_CAL_IOS_VERSION"), ""));
        gmsvMdl.setGsAdrIosVer(
                NullDefault.getString(ConfigBundle.getValue("GS_ADR_IOS_VERSION"), ""));
        gmsvMdl.setGsSmlIosVer(
                NullDefault.getString(ConfigBundle.getValue("GS_SML_IOS_VERSION"), ""));
        gmsvMdl.setGsWmlIosVer(
                NullDefault.getString(ConfigBundle.getValue("GS_WML_IOS_VERSION"), ""));

        paramMdl.setMan230GsMobSuiteVer(gmsvMdl);
    }

    /**
     *
     * <br>[機  能]filesearch.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getFileSearch(Man230ParamModel paramMdl) {

        paramMdl.setMan230FileSearch(
                NullDefault.getString(ConfigBundle.getValue("FIL_ALL_SEARCH_USE"), ""));

    }


    /**
     *
     * <br>[機  能]mailserver.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getMailServer(Man230ParamModel paramMdl) {

        paramMdl.setMan230MailServer(
                NullDefault.getString(ConfigBundle.getValue("MAIL_PORT_NUMBER"), ""));

    }

    /**
     *
     * <br>[機  能]portal.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getPortal(Man230ParamModel paramMdl) {

        paramMdl.setMan230Portal(
                NullDefault.getString(ConfigBundle.getValue("PORTLET_MAXLENGTH"), ""));

    }

    /**
     *
     * <br>[機  能]portal.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getReserve(Man230ParamModel paramMdl) {

        paramMdl.setMan230RsvPrintUse(
                NullDefault.getString(ConfigBundle.getValue("RSV_PRINT_USE"), ""));

    }

    /**
     *
     * <br>[機  能]GsMobileSuiteVarsion.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getServerName(Man230ParamModel paramMdl) {

        Man230ServerNameModel gmsvMdl = new Man230ServerNameModel();

        gmsvMdl.setServerName(
                NullDefault.getString(ConfigBundle.getValue("SERVER_NAME"), ""));
        gmsvMdl.setServerUrl(
                NullDefault.getString(ConfigBundle.getValue("SERVER_URL"), ""));
        gmsvMdl.setLicensePageUrl(
                NullDefault.getString(ConfigBundle.getValue("LICENSE_PAGE_URL"), ""));
        gmsvMdl.setLicensePageUrlCloud(
                NullDefault.getString(ConfigBundle.getValue("LICENSE_PAGE_URL_CLOUD"), ""));
        gmsvMdl.setErrorReportUrl(
                NullDefault.getString(ConfigBundle.getValue("ERROR_REPORT_URL"), ""));
        gmsvMdl.setGsSettingDocUrl(
                NullDefault.getString(ConfigBundle.getValue("GS_SETTING_DOC_URL"), ""));
        gmsvMdl.setGsBizUrl(
                NullDefault.getString(ConfigBundle.getValue("GS_BIZ_URL"), ""));

        paramMdl.setMan230ServerName(gmsvMdl);
    }

    /**
     *
     * <br>[機  能]sso.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getSso(Man230ParamModel paramMdl) {

        paramMdl.setMan230SsoKeyWord(
                NullDefault.getString(ConfigBundle.getValue("SSO_KEYWORD"), ""));

    }

    /**
     *
     * <br>[機  能]webmail.confを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __getWebMail(Man230ParamModel paramMdl) {

        Man230WebMailModel webMailMdl = new Man230WebMailModel();

        webMailMdl.setMailReceiveThreadMaxCount(
                NullDefault.getString(ConfigBundle.getValue("MAILRECEIVE_THREAD_MAXCOUNT"), ""));
        webMailMdl.setMailReceiveMaxCount(
                NullDefault.getString(ConfigBundle.getValue("MAILRECEIVE_MAXCOUNT"), ""));
        webMailMdl.setMailReceiveConnectTimeOut(
                NullDefault.getString(ConfigBundle.getValue("MAILRECEIVE_CONNECTTIMEOUT"), ""));
        webMailMdl.setMailReceiveTimeOut(
                NullDefault.getString(ConfigBundle.getValue("MAILRECEIVE_TIMEOUT"), ""));
        webMailMdl.setMailReceiveRcvSvrChecktime(
                NullDefault.getString(ConfigBundle.getValue("MAILRECEIVE_RCVSVR_CHECKTIME"), ""));
        webMailMdl.setMailBodyLimit(
                NullDefault.getString(ConfigBundle.getValue("MAILBODY_LIMIT"), ""));

        paramMdl.setMan230WebMailModel(webMailMdl);
    }

    /**
     * [機  能] 使用量、使用を試みる最大メモリ容量の情報を返します。<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param paramMdl パラメータ情報
     */
    private void __getMemoryInfo(Man230ParamModel paramMdl) {
        DecimalFormat format1 = new DecimalFormat("#,###MB");
        DecimalFormat format2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long used = max - free;
        double ratio = (used * 100 / (double) max);
        //使用
        paramMdl.setMan230MemoryUse(format1.format(used));
        //使用(割合)
        paramMdl.setMan230MemoryUsePer(format2.format(ratio));
        //最大値
        paramMdl.setMan230MemoryMax(format1.format(max));
    }
    
    /**
     * <br>[機  能] データフォルダ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return ディスク情報
     */
    private void __getDataFolderInfo(Man230ParamModel paramMdl, Connection con) throws Exception {
        
        if (Integer.parseInt(paramMdl.getMan230DbKbn()) != GSConst.DBTYPE_H2DB) {
            return;
        }
        
        CommonBiz cmnBiz = new CommonBiz();
        List<List<String>> dataFolderInfo = new ArrayList<List<String>>(); 
        CmnDatafolderDao cdfDao = new CmnDatafolderDao(con);
        List<CmnDatafolderModel> modelList = cdfDao.select();
        for (CmnDatafolderModel mdl : modelList) {
            List<String> dispRow = new ArrayList<String>();
            dispRow.add(mdl.getCdfName());
            String folderSize = cmnBiz.convertDiskSize(mdl.getCdfSize());
            dispRow.add(folderSize);
            dataFolderInfo.add(dispRow);
        }
        paramMdl.setMan230dataFolderInfo(dataFolderInfo);
        
    }

    /**
     * <br>[機  能] 「報告する」ボタンが押された時の本文作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getSystemInfo(Man230ParamModel paramMdl, RequestModel reqMdl) {

        JSONObject jsonData = new JSONObject();

        //servername.conf情報の取得
        __getServerName(paramMdl);

        //sso.conf情報の取得
        __getSso(paramMdl);

        //送信本文の作成
        GsMessage gsmsg = new GsMessage(reqMdl);
        StringBuilder sbSysInfo = new StringBuilder();

        //サポート・オプションライセンス
        sbSysInfo.append("\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.license.info"));
        //シリアル番号
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.serial.number")));
        sbSysInfo.append(paramMdl.getGsUid());
        //ライセンスID
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man150.3")));
        sbSysInfo.append(paramMdl.getLicenseId());
        //会社名
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.company.name")));
        sbSysInfo.append(paramMdl.getLicenseCom());

        //ライセンス情報
        for (LicenseModel pMdl : paramMdl.getPluginList()) {
            sbSysInfo.append("\n");
            sbSysInfo.append(__setSameLength(pMdl.getPluginName()));
            sbSysInfo.append(gsmsg.getMessage("cmn.period2"));
            sbSysInfo.append(pMdl.getLicenseLimit());
        }

        //登録者数
        sbSysInfo.append("\n");
        String entryUserName = gsmsg.getMessage("anp.anp110.02") + gsmsg.getMessage("rss.25");
        sbSysInfo.append(__setSameLength(entryUserName));
        sbSysInfo.append(paramMdl.getUserCount());
        sbSysInfo.append(gsmsg.getMessage("cmn.persons"));

        //サーバ情報
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.main.server.info"));
        //バージョン
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.version")));
        sbSysInfo.append(jp.groupsession.v2.cmn.GSConst.VERSION);
        sbSysInfo.append("(");
        sbSysInfo.append(paramMdl.getMan230GSVersionDB());
        sbSysInfo.append(")");
        //OS
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.1")));
        sbSysInfo.append(paramMdl.getMan230Os());
        //CPUプロセッサ
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.12")));
        sbSysInfo.append(paramMdl.getMan230CpuProcesser());
        //CPUのコア数
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.9")));
        sbSysInfo.append(paramMdl.getMan230CpuCoreNum() + "Core");
        //サーバメモリ
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.13")));
        sbSysInfo.append(paramMdl.getMan230MemSize());
        //サーバディスク容量
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.14")));
        List<List<String>> diskList = paramMdl.getMan230DiskInfo();
        sbSysInfo.append("\n");
        for (List<String> lineList : diskList) {
            String lineStr = "";
            for (String line : lineList) {
                if (lineStr.length() > 0) {
                    lineStr += " ";
                }
                lineStr += line;
            }
            sbSysInfo.append(__setSameLength(lineStr)).append("\n");
        }
        //JVMビットモード
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.10")));
        sbSysInfo.append(paramMdl.getMan230JvmBitMode());
        //J2EEコンテナ
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.2")));
        sbSysInfo.append(paramMdl.getMan230J2ee());
        //Java
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.3")));
        sbSysInfo.append(paramMdl.getMan230Java());
        //メモリ使用(割合)
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.4")));
        sbSysInfo.append(paramMdl.getMan230MemoryUse());
        sbSysInfo.append("(");
        sbSysInfo.append(paramMdl.getMan230MemoryUsePer());
        sbSysInfo.append("%)");
        //メモリ最大
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.5")));
        sbSysInfo.append(paramMdl.getMan230MemoryMax());
        //現在の空きディスク容量
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.6")));
        sbSysInfo.append(paramMdl.getMan230FreeDSpace());
        //コネクション使用状況
        sbSysInfo.append("\n\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.man230.7")));
        sbSysInfo.append(paramMdl.getMan230ConnectionCount());

        //H2コネクション設定
        Man230ConnectionOptionModel conectionOp = paramMdl.getMan230ConnectionOp();
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.main.h2connection"));
        //LOCK_MODE
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("LOCK_MODE"));
        sbSysInfo.append(conectionOp.getLockMode());
        //LOCK_TIMEOUT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("LOCK_TIMEOUT"));
        sbSysInfo.append(conectionOp.getLockTimeOut());
        //DEFAULT_LOCK_TIMEOUT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("DEFAULT_LOCK_TIMEOUT"));
        sbSysInfo.append(conectionOp.getDefLockTimeOut());
        //MULTI_THREADED
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MULTI_THREADED"));
        sbSysInfo.append(conectionOp.getMultiThreaded());
        //IFEXISTS
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("IFEXISTS"));
        sbSysInfo.append(conectionOp.getIfExists());
        //AUTOCOMMIT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("AUTOCOMMIT"));
        sbSysInfo.append(conectionOp.getAutoCommit());
        //DB_CLOSE_ON_EXIT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("DB_CLOSE_ON_EXIT"));
        sbSysInfo.append(conectionOp.getDbCloseOnExit());
        //CACHE_SIZE
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("CACHE_SIZE"));
        sbSysInfo.append(conectionOp.getCacheSize());
        //PAGE_SIZE
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("PAGE_SIZE"));
        sbSysInfo.append(conectionOp.getPageSize());
        //MAX_LENGTH_INPLACE_LOB
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAX_LENGTH_INPLACE_LOB"));
        sbSysInfo.append(conectionOp.getMaxLengthImplace());
        //CACHE_TYPE
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("CACHE_TYPE"));
        sbSysInfo.append(conectionOp.getCacheType());
        //MVCC
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MVCC"));
        sbSysInfo.append(conectionOp.getMvcc());
        //QUERY_TIMEOUT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("QUERY_TIMEOUT"));
        sbSysInfo.append(conectionOp.getQueryTimeOut());
        //QUERY_TIMEOUT_BATCH
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("QUERY_TIMEOUT_BATCH"));
        sbSysInfo.append(conectionOp.getQueryTimeOutBatch());
        //MAX_LOG_SIZE
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAX_LOG_SIZE"));
        sbSysInfo.append(conectionOp.getMaxLogSize());

        //ディレクトリ情報
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        Man230GsDataConfModel gsDataConf = paramMdl.getMan230GsdataConf();
        sbSysInfo.append(gsmsg.getMessage("project.prj170.2"));
        //GSDATA_DIR
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GSDATA_DIR"));
        sbSysInfo.append(gsDataConf.getGsDataDir());
        //BACKUP_DIR
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("BACKUP_DIR"));
        sbSysInfo.append(gsDataConf.getBackUpDir());
        //FILEKANRI_DIR
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("FILEKANRI_DIR"));
        sbSysInfo.append(gsDataConf.getFileKanriDir());
        //WEBMAIL_DIR
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("WEBMAIL_DIR"));
        sbSysInfo.append(gsDataConf.getWebMailDir());

        //自動バックアップ設定
        CmnBackupConfModel backUpMdl = paramMdl.getMan230BackUpConf();
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.autobackup.setting"));
        //バックアップ間隔
        sbSysInfo.append("\n");
        if (backUpMdl == null || backUpMdl.getBucInterval() == 0) {
            sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.main.backup.interval")));
            sbSysInfo.append(gsmsg.getMessage("cmn.notset"));
        } else {
            sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.main.backup.interval")));

            //曜日
            String dow = null;
            if (backUpMdl.getBucInterval() != 1) {
                switch (backUpMdl.getBucDow()) {
                case(1):
                    dow = gsmsg.getMessage("cmn.sunday3");
                    break;
                case(2):
                    dow = gsmsg.getMessage("cmn.monday3");
                    break;
                case(3):
                    dow = gsmsg.getMessage("cmn.tuesday3");
                    break;
                case(4):
                    dow = gsmsg.getMessage("cmn.wednesday3");
                    break;
                case(5):
                    dow = gsmsg.getMessage("cmn.thursday3");
                    break;
                case(6):
                    dow = gsmsg.getMessage("main.src.man080.7");
                    break;
                case(7):
                    dow = gsmsg.getMessage("cmn.saturday3");
                    break;
                default:
                    break;
                }
            }

            switch (backUpMdl.getBucInterval()) {
            case(1):
                sbSysInfo.append(gsmsg.getMessage("cmn.everyday"));
                break;
            case(2):
                sbSysInfo.append(gsmsg.getMessage("cmn.weekly2"));
                sbSysInfo.append(dow);
                break;
            case(3):
                sbSysInfo.append(gsmsg.getMessage("cmn.monthly.2"));
                sbSysInfo.append(gsmsg.getMessage("main.src.man025.3"));
                sbSysInfo.append(backUpMdl.getBucWeekMonth());
                sbSysInfo.append(dow);
                break;
            default:
                sbSysInfo.append(gsmsg.getMessage("cmn.notset"));
                break;
            }

            //バックアップ世代
            sbSysInfo.append("\n");
            String backUpGene = gsmsg.getMessage("man.number.generations");
            sbSysInfo.append(__setSameLength(backUpGene));
            sbSysInfo.append(backUpMdl.getBucGeneration());
            sbSysInfo.append(gsmsg.getMessage("fil.6"));

            //出力
            sbSysInfo.append("\n");
            sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.output")));
            if (backUpMdl.getBucZipout() == 0) {
                sbSysInfo.append(gsmsg.getMessage("cmn.not.compress"));
            } else {
                sbSysInfo.append(gsmsg.getMessage("main.zip.format.output"));
            }
        }

        //バッチジョブ起動時間
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        CmnBatchJobModel batchMdl = paramMdl.getMan230CmnBatchJob();
        String batchSetTime = gsmsg.getMessage("main.src.18") + gsmsg.getMessage("cmn.setting");
        sbSysInfo.append(batchSetTime);
        //バッチジョブ起動時間設定
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("main.src.18")));
        sbSysInfo.append(batchMdl.getBatFrDate());
        sbSysInfo.append(gsmsg.getMessage("tcd.running.time"));

        //プラグイン情報
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.plugin.info"));
        int pluginNum = 0;
        int maxPluginNum = paramMdl.getMan230PluginList().size();
        for (pluginNum = 0; pluginNum < maxPluginNum - 1; pluginNum++) {
            sbSysInfo.append("\n");
            sbSysInfo.append(__setSameLength(paramMdl.getMan230PluginList().get(pluginNum)));
            pluginNum++;
            if (paramMdl.getMan230PluginList().get(pluginNum).equals("0")) {
                sbSysInfo.append(gsmsg.getMessage("cmn.use"));
            } else {
                sbSysInfo.append(gsmsg.getMessage("cmn.unused"));
            }
        }

        //テンポラリディレクトリパス
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append(gsmsg.getMessage("cmn.main.temp.path"));
        //テンポラリディレクトリパス
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength(gsmsg.getMessage("cmn.main.temp.path")));
        sbSysInfo.append(paramMdl.getMan230TempPath());

        //gsMobileSuiteVersion
        Man230GsMobileSuiteVarsionModel gsMobSVMdl = paramMdl.getMan230GsMobSuiteVer();
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("gsMobileSuiteVersion.conf");
        //COMPLIANT_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("COMPLIANT_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getCompliantVer());
        //GS_MBA_IOS_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_MBA_IOS_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getGsMbaIosVer());
        //GS_CAL_IOS_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_CAL_IOS_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getGsCalIosVer());
        //GS_ADR_IOS_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_ADR_IOS_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getGsAdrIosVer());
        //GS_SML_IOS_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_SML_IOS_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getGsSmlIosVer());
        //GS_WML_IOS_VERSION
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_WML_IOS_VERSION"));
        sbSysInfo.append(gsMobSVMdl.getGsWmlIosVer());

        //filesearch.conf
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("filesearch.conf");
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("FIL_ALL_SEARCH_USE"));
        sbSysInfo.append(paramMdl.getMan230FileSearch());

        //mailserverl.conf
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("mailserver.conf");
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAIL_PORT_NUMBER"));
        sbSysInfo.append(paramMdl.getMan230MailServer());

        //portal.conf
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("portal.conf");
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("PORTLET_MAXLENGTH"));
        sbSysInfo.append(paramMdl.getMan230Portal());

        //reserve.conf
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("reserve.conf");
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("RSV_PRINT_USE"));
        sbSysInfo.append(paramMdl.getMan230RsvPrintUse());

        //servername.conf
        Man230ServerNameModel serverNameMdl = paramMdl.getMan230ServerName();
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("servername.conf");
        //SERVER_NAME
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("SERVER_NAME"));
        if (serverNameMdl.getServerName().equals("")) {
            sbSysInfo.append(gsmsg.getMessage("cmn.notset"));
        } else {
            sbSysInfo.append(serverNameMdl.getServerName());
        }
        //SERVER_URL
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("SERVER_URL"));
        if (serverNameMdl.getServerUrl().equals("")) {
            sbSysInfo.append(gsmsg.getMessage("cmn.notset"));
        } else {
            sbSysInfo.append(serverNameMdl.getServerUrl());
        }
        //LICENSE_PAGE_URL
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("LICENSE_PAGE_URL"));
        sbSysInfo.append(serverNameMdl.getLicensePageUrl());
        //LICENSE_PAGE_URL_CLOUD
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("LICENSE_PAGE_URL_CLOUD"));
        sbSysInfo.append(serverNameMdl.getLicensePageUrlCloud());
        //ERROR_REPORT_URL
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("ERROR_REPORT_URL"));
        sbSysInfo.append(serverNameMdl.getErrorReportUrl());
        //GS_SETTING_DOC_URL
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_SETTING_DOC_URL"));
        sbSysInfo.append(serverNameMdl.getGsSettingDocUrl());
        //GS_BIZ_URL
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("GS_BIZ_URL"));
        sbSysInfo.append(serverNameMdl.getGsBizUrl());

        //sso.conf
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("sso.conf");
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("SSO_KEYWORD"));
        sbSysInfo.append(paramMdl.getMan230SsoKeyWord());

        //webmail.conf
        Man230WebMailModel webMailMdl = paramMdl.getMan230WebMailModel();
        sbSysInfo.append("\n\n");
        sbSysInfo.append("・");
        sbSysInfo.append("webmail.conf");
        //MAILRECEIVE_THREAD_MAXCOUNT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILRECEIVE_THREAD_MAXCOUNT"));
        sbSysInfo.append(webMailMdl.getMailReceiveThreadMaxCount());
        //MAILRECEIVE_MAXCOUNT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILRECEIVE_MAXCOUNT"));
        sbSysInfo.append(webMailMdl.getMailReceiveMaxCount());
        //MAILRECEIVE_CONNECTTIMEOUT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILRECEIVE_CONNECTTIMEOUT"));
        sbSysInfo.append(webMailMdl.getMailReceiveConnectTimeOut());
        //MAILRECEIVE_TIMEOUT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILRECEIVE_TIMEOUT"));
        sbSysInfo.append(webMailMdl.getMailReceiveTimeOut());
        //MAILRECEIVE_RCVSVR_CHECKTIME
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILRECEIVE_RCVSVR_CHECKTIME"));
        sbSysInfo.append(webMailMdl.getMailReceiveRcvSvrChecktime());
        //MAILBODY_LIMIT
        sbSysInfo.append("\n");
        sbSysInfo.append(__setSameLength("MAILBODY_LIMIT"));
        sbSysInfo.append(webMailMdl.getMailBodyLimit());

        //エラーレポートを送るURLをセット
        jsonData.put("url", paramMdl.getMan230ServerName().getErrorReportUrl());
        //本文をセット
        jsonData.put("sysinfo", sbSysInfo.toString());
        StringBuilder companyId = new StringBuilder();
        companyId.append(paramMdl.getLicenseCom());
        companyId.append(" ");
        companyId.append(gsmsg.getMessage("main.man230.11"));
        companyId.append(" ");
        companyId.append(paramMdl.getLicenseId());
        //会社名とライセンスIDをセット
        jsonData.put("licenseid", companyId.toString());
        //GSのバージョンをセット
        jsonData.put("version", paramMdl.getMan230GSVersionDB());

        return jsonData;
    }

    /**
     *
     * <br>[機  能]メール出力の際に均等に空白を追加してインデントをとる
     * <br>[解  説]
     * <br>[備  考]
     * @param itemName 項目名
     * @return 空白で長さを統一した項目名
     */
    private String __setSameLength(String itemName) {

        StringBuilder sbLength = new StringBuilder();
        int maxWordLength = 30;

        //必要な空白の長さを計算
        int subLength = maxWordLength - itemName.getBytes().length;

        sbLength.append(itemName);
        //均等に空白を追加
        for (int plusBlank = 0; plusBlank < subLength; plusBlank++) {
            sbLength.append(" ");
        }

        return sbLength.toString();
    }
}
