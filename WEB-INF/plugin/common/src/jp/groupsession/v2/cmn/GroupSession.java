package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;
import org.apache.struts.Globals;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.apache.struts.util.MessageResources;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

import jp.co.sjts.util.cache.FileCache;
import jp.co.sjts.util.cache.FileCacheConfig;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.DBConnectionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.lang.ClassUtil;
import jp.co.sjts.util.struts.BaseServlet;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.DayTrigerListener;
import jp.groupsession.v2.batch.HourJob;
import jp.groupsession.v2.batch.M5Job;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn001.Cmn001Biz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PluginDigester;
import jp.groupsession.v2.cmn.exception.DBBackupGuardException;
import jp.groupsession.v2.cmn.quartz.JobException;
import jp.groupsession.v2.cmn.server.IServer;
import jp.groupsession.v2.restapi.filter.RestApiRequestFilter;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機 能] GroupSessionのコントローラー。ActionServletの拡張クラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GroupSession extends BaseServlet {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GroupSession.class);

    /** バッチ処理スケジュール */
    private Scheduler sched__ = null;

    /** GS共通情報 */
    private static GSContext gscontext__ = null;

    /** プラグイン情報 */
    private static PluginConfig pluginConfig__  = null;

    /** プラグイン用StrutsConfigファイル名 */
    private static final String PLUGIN_STRUTS_CONFIG_FILENAME = "struts_config.xml";

    /** プラグイン設定ファイル名 */
    private static final String PLUGIN_CONFIG_FILENAME = "plugin.xml";
    /** サーバサービスのリスト */
    private List<IServer> servers__ = null;

    /**
     * <p>Servlet INFO
     * @return サーブレット情報
     */
    public String getServletInfo() {
        return GSConst.PROJECT_NAME;
    }

    /**
     * <p>初期化処理
     * @exception ServletException
     *                初期設定の取得に失敗した場合にスロー
     */
    public void init() throws ServletException {


        super.init();

        log__ = LogFactory.getLog(GroupSession.class);
        log__.info("---- init start ---");

        //GS共通情報の作成
        gscontext__ = new GSContext();

        //初期化中ステータス
        Status status = new Status();
        status.setInit(true);
        gscontext__.put(GSContext.STATUS, status);

        //ルートディレクトリ
        String rootPath = __getRealPath();
        log__.info(GSContext.APP_ROOT_PATH + "= " + rootPath);
        gscontext__.put(GSContext.APP_ROOT_PATH, rootPath);

        //テンポラリディレクトリ
        String tmpdir = __getTempPath();
        log__.info("TEMP_PATH = " + tmpdir);
        getResourceManager().setInitData(tmpdir);

        try {
            //TEMPディレクトリ内削除
            IOTools.deleteInDir(tmpdir);
        } catch (IOToolsException e) {
            log__.error("TEMPディレクトリ削除に失敗", e);
        }

        //添付ファイルユーティリティの作成
        ITempFileUtil tempFileUtil = TempFileUtilFactory.getInstance(tmpdir);
        gscontext__.put(GSContext.TEMP_FILE_UTIL, tempFileUtil);

        //ファイルキャッシュ保存先ディレクトリの削除
        if (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES) {
            try {
                FileCacheConfig.readConfig(rootPath);
                String cacheSaveDir = FileCacheConfig.getSaveDir();
                if (cacheSaveDir != null) {
                    IOTools.deleteDir(cacheSaveDir);
                }
            } catch (IOException e) {
                log__.error("ファイルキャッシュ設定ファイルの読み込みに失敗", e);
            }
        }
        //リンク設定の読み込み
        try {
            Cmn001Biz.readConfig(rootPath);
        } catch (IOException e) {
            log__.error("ログイン画面リンク設定の読み込みに失敗", e);
        }

        //プラグイン設定の読み込み
        log__.info("プラグインをロード");
        pluginConfig__ = __initPluginConfig();

        //設定ファイルの読み込み
        try {
            ConfigBundle.readConfig(getServletContext());
        } catch (IOException e) {
            log__.error("設定ファイルの読み込みに失敗", e);
        }

        //SERVER_URL設定エラーチェック
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        if (urlBiz.haveConfError()) {
            log__.error("設定「SERVER_URL」のフォーマットが不正");
        }

        //MessageResourceを設定
        MessageResources resources =
            (MessageResources) getServletContext().getAttribute(
                    GsMessage.RESOURECE_KEY);
        gscontext__.put(GSContext.MSG_RESOURCE, resources);

        IDbUtil dbUtil = DBUtilFactory.getInstance();
        log__.info("DBの接続設定を開始");
        try {
            //DBの設定
            dbUtil.init(__getRealPath());
        } catch (Exception e) {
            log__.error("DBの初期化処理に失敗", e);
            throw new ServletException(e);
        }

        //DBの起動
        log__.info("DB起動処理を実行");
        try {
            dbUtil.startDbServer(rootPath);
        } catch (Exception e) {
            log__.error("DBサーバの起動に失敗", e);
            throw new ServletException(e);
        }

        //デフォルトエスケープ文字を設定する。
        JDBCUtil.def_esc = dbUtil.defaultEscape();
        JDBCUtil.def_like_esc = dbUtil.defaultLikeEscape();
        JDBCUtil.def_like_esc_after = dbUtil.defaultLikeEscapeAfter();

        //BeanUtilsにResolverを指定(Struts1 脆弱性対応)
        BeanUtilsBean.getInstance().getPropertyUtils().setResolver(new SafetyResolver());

        //起動処理
        GSInit gsInit = new GSInit();
        try {
            gsInit.execute(pluginConfig__);
        } catch (JobException e) {
            log__.error("リスナー実行に失敗しました。", e);
            e.printStackTrace();
        } catch (SQLException e) {
            log__.error("リスナー実行に失敗しました。", e);
        }

        //サーバスレッド起動
        try {
            servers__ = new ArrayList<IServer>();
            String[] serverClassNames = pluginConfig__.getServerClassNames();
            for (String className : serverClassNames) {
                log__.debug("serverClassNames = " + className);
                //
                try {
                    Object obj = ClassUtil.getObject(className);
                    IServer server = (IServer) obj;
                    server.start();
                    servers__.add(server);
                } catch (ClassNotFoundException e) {
                    log__.error("サーバスレッドの起動に失敗", e);
                } catch (InstantiationException e) {
                    log__.error("サーバスレッドの起動に失敗", e);
                } catch (IllegalAccessException e) {
                    log__.error("サーバスレッドの起動に失敗", e);
                } catch (Exception e) {
                    log__.error("サーバスレッドの起動に失敗", e);
                }
            }
        } catch (RuntimeException e) {
            log__.error("サーバスレッドの起動に失敗", e);
        }

        //バッチ起動
        if (__isBatchStatus()) {
            __startJob();
        }

        //初期化中ステータス
        status.setInit(false);
        gscontext__.put(GSContext.STATUS, status);

        log__.info("---- init end ---");
    }

    /**
     * <p>夜間バッチ処理の起動
     * @throws ServletException Job起動実行例外
     */
    private void __startJob() throws ServletException {
        //夜間バッチジョブの設定・開始
        log__.info("夜間バッチJOBスケジュール 起動");
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            if (sched__ != null && !sched__.isShutdown()) {
                //既に起動していた場合は終了する
                sched__.shutdown(true);
            }
            sched__ = sf.getScheduler();

            //DAY 起動時間設定 ------------------------------------------------------------
            String sconf = "1 0 * * * ?"; //毎時0分1秒
            DayJob bjob = new DayJob();
            __setScheduleJob(bjob, "job1", "group1", "trigger1", sconf);

            //1Hour 起動時間設定 ------------------------------------------------------------
            String sconf1h = "5 0 */1 * * ?"; //1時間毎
            HourJob h1job = new HourJob();
            __setScheduleJob(h1job, "job1h", "group1h", "trigger1h", sconf1h);

            //5Minute 起動時間設定 ------------------------------------------------------------
            String sconf5m = "10 */5 * * * ?"; //5分毎
            M5Job m5job = new M5Job();
            __setScheduleJob(m5job, "job5m", "group5m", "trigger5m", sconf5m);

            //
            log__.debug("GSCONTEXT = " + gscontext__);
            DayTrigerListener dtlis = new DayTrigerListener();
            dtlis.setGscontext(gscontext__);
            dtlis.setPluginConfig(pluginConfig__);
            sched__.getListenerManager().addTriggerListener(dtlis, EverythingMatcher.allTriggers());

            //ジョブ開始
            sched__.start();
        } catch (SchedulerException e) {
            log__.fatal("夜間バッチのJob設定に失敗", e);
            throw new ServletException("夜間バッチのJob設定に失敗", e);
//        } catch (ParseException e) {
//            log__.fatal("夜間バッチのJob設定に失敗(設定ファイル記述ミス)", e);
//            throw new ServletException("夜間バッチのJob設定に失敗(設定ファイル記述ミス)", e);
        }
        log__.info("夜間バッチJOBスケジュール 起動完了");
    }

    /**
     * <p>終了処理
     */
    public void destroy() {
        super.destroy();
        log__.info("GroupSession 終了処理 開始");

        //初期化中ステータス
        Object objSt = gscontext__.get(GSContext.STATUS);
        Status status = null;
        if (objSt == null) {
            status = new Status();
        } else {
            status = (Status) objSt;
        }
        status.setShutdown(true);
        gscontext__.put(GSContext.STATUS, status);

        //一時ファイル用ディレクトリを初期化
        try {
            log__.info("GSリスナー destroy()開始");
            String[] domainList = getResourceManager().getDomain();
            for (String domain : domainList) {
                //GSリスナー取得
                IGsListener[] lis
                       = GsListenerUtil.getGsListeners(getResourceManager().getPluginConfig(domain));

                //各プラグインリスナーを呼び出し
                Connection con = null;
                for (int i = 0; i < lis.length; i++) {
                    try {
                        con = getConnection(domain, true);
                        lis[i].gsDestroy(gscontext__, con, domain);
                    } catch (Throwable e) {
                        log__.error(e);
                    } finally {
                        JDBCUtil.closeConnection(con);
                    }
                }
            }
            log__.info("GSリスナー destroy()終了");

            log__.info("一時ファイル用ディレクトリを初期化 destroy");
            try {
                IOTools.deleteDir(__getTempPath());
            } catch (Throwable e) {
                log__.info("一時ファイル用ディレクトリの初期化に失敗", e);
            }

            //ファイルキャッシュ保存先ディレクトリの初期化
            try {
                if (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_POSTGRES) {
                    FileCache.close();
                    log__.info("ファイルキャッシュ保存先ディレクトリの初期化 destroy");
                    String cacheSaveDir = FileCacheConfig.getSaveDir();
                    if (cacheSaveDir != null) {
                        IOTools.deleteDir(cacheSaveDir);
                    }
                }
            } catch (Throwable e) {
                log__.info("ファイルキャッシュ保存先ディレクトリの初期化に失敗", e);
            }

            //Server Shutdown
            log__.info("各サーバサービスシャットダウン 開始");
            int shutdownTryCount = 0;
            for (IServer server : servers__) {
                try {
                    server.shutdown();
                    while (!server.isShutdownEnd() && shutdownTryCount < 10) {
                        __sleep(1000);
                        shutdownTryCount++;
                    }
                    shutdownTryCount = 0;
                    server = null;
                } catch (Throwable e) {
                    log__.error(e);
                }
            }
            log__.info("サーバサービスシャットダウン 終了");

            //Server終了
            __sleep(2000);

            //Jobスケジューラー
            if (__isBatchStatus()) {
                log__.info("Jobスケジューラーシャットダウン 開始");
                try {
                    sched__.shutdown(true);
                } catch (Throwable e) {
                    log__.error(e);
                }
                log__.info("Jobスケジューラーシャットダウン 終了");
            }

            //
            __sleep(2000);

            //DB Shutdown
            log__.info("DBシャットダウン 開始");
            IDbUtil dbUtil = DBUtilFactory.getInstance();
            //ルートディレクトリ
            String rootPath = __getRealPath();
            for (String domain : domainList) {
                dbUtil.shutdownDbServer(rootPath, getConnection(domain, true));
            }
            log__.info("DBシャットダウン 終了");
        } catch (ServletException e) {
            log__.error("終了処理に失敗", e);
        } catch (ClassNotFoundException e) {
            log__.error("終了処理に失敗", e);
        } catch (IllegalAccessException e) {
            log__.error("終了処理に失敗", e);
        } catch (InstantiationException e) {
            log__.error("終了処理に失敗", e);
        } catch (SQLException e) {
            log__.error("終了処理に失敗", e);
        } catch (IOToolsException e) {
            log__.error("終了処理に失敗", e);
        } catch (Exception e) {
            log__.error("終了処理に失敗", e);
        } catch (Throwable e) {
            log__.error("終了処理に失敗", e);
        }
        log__.info("GroupSession 終了処理 完了");
        LogFactory.releaseAll();
    }

    /**
     * <p>処理を指定ミリ秒またせるメソッド。
     * milisecに3000を指定した場合3秒待ち
     * @param milisec ミリ秒
     */
    private static void __sleep(long milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            log__.error(milisec + "ミリ秒の待ち処理に失敗", e);
        }
    }

    /**
     * <p>
     * プールからコネクションを取得する
     * <p>
     * 3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>
     * またリトライ時は3秒の待ち時間の後にリトライする
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * また、バックアップ実行中はDBBackupGuardExceptionへスローする。
     * @param dsKey ドメインキー
     * @return Connection
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    public static Connection getConnection(String dsKey)
    throws SQLException, DBConnectionException, Exception {
        return getConnection(dsKey, true);
    }

    /**
     * <p>
     * プールからコネクションを取得する
     * <p>
     * 3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>
     * またリトライ時は3秒の待ち時間の後にリトライする
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * また、バックアップ実行中はDBBackupGuardExceptionへスローする。
     * @param dsKey ドメインキー
     * @param checkBackup バックアップ中かを確認する
     * @return Connection
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    public static Connection getConnection(String dsKey, boolean checkBackup)
    throws SQLException, DBConnectionException, Exception {

        CommonBiz cmnBiz = new CommonBiz();
        if (checkBackup && !cmnBiz.canDBAccess()) {
            throw new DBBackupGuardException();
        }
        IGsResourceManager ids = null;
        ids = (IGsResourceManager) getResourceManager();
        DataSource ds = ids.getDataSource(dsKey);

        if (ds == null) {
            return null;
        }
        return JDBCUtil.getConnection(ds);
    }

    /**
     * <p>
     * プールからコネクションを取得する
     * <p>
     * 3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>
     * またリトライ時は3秒の待ち時間の後にリトライする
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * また、バックアップ実行中はDBBackupGuardExceptionへスローする。
     * @param req リクエスト
     * @return Connection
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    public Connection getConnection(HttpServletRequest req)
    throws SQLException, DBConnectionException, Exception {
        CommonBiz cmnBiz = new CommonBiz();
        if (!cmnBiz.canDBAccess()) {
            throw new DBBackupGuardException();
        }
        IGsResourceManager ids = null;
        ids = (IGsResourceManager) getResourceManager();
        DataSource ds = ids.getDataSource(ids.getDomain(req));
        return JDBCUtil.getConnection(ds);
    }

    /**
     * <p>
     * プールからコネクションを取得する
     * <p>
     * 3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>
     * またリトライ時は3秒の待ち時間の後にリトライする
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * また、バックアップ実行中はDBBackupGuardExceptionへスローする。
     * @param dsKey ドメインキー
     * @param milisec リトライまでの待ち時間(ミリ秒)
     * @return Connection
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    public static Connection getConnection(String dsKey, long milisec)
    throws SQLException, DBConnectionException, Exception {
        CommonBiz cmnBiz = new CommonBiz();
        if (!cmnBiz.canDBAccess()) {
            throw new DBBackupGuardException();
        }
        IGsResourceManager ids = null;
        ids = (IGsResourceManager) getResourceManager();
        DataSource ds = ids.getDataSource(dsKey);
        return JDBCUtil.getConnection(ds, milisec);
    }

    /**
     * <p>
     * プールからコネクションを取得する
     * <p>
     * 3回リトライし、失敗した場合はSQLExceptionをスローする
     * <p>
     * またリトライ時は3秒の待ち時間の後にリトライする
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * また、バックアップ実行中はDBBackupGuardExceptionへスローする。
     * @param ds DataSource
     * @return Connection
     * @throws SQLException コネクションの取得に失敗
     * @throws DBConnectionException コネクションの取得に失敗
     * @throws Exception 実行時例外
     */
    public static Connection getConnection(DataSource ds)
    throws SQLException, DBConnectionException, Exception {
        CommonBiz cmnBiz = new CommonBiz();
        if (!cmnBiz.canDBAccess()) {
            throw new DBBackupGuardException();
        }
        return JDBCUtil.getConnection(ds);
    }

    /**
     * [機 能] アプリケーションのパスを返す<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return パス
     */
    private String __getRealPath() {
        String path = getServletContext().getRealPath("/");
        return path;
    }

    /**
     * [機 能] テンポラリディレクトリのパスを返す<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return テンポラリディレクトリのパス
     * @throws ServletException パスが取得できない場合、アクセスできない場合にスロー
     */
    private String __getTempPath() throws ServletException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        if (tmpdir == null) {
            // WEB-INF/tmp をテンポラリディレクトリとして使用する
            tmpdir = IOTools.setEndPathChar(__getRealPath()) + GSContext.TEMP_PATH_PREFIX;
            try {
                IOTools.isDirCheck(tmpdir, true);
            } catch (IOToolsException e) {
                log__.error("テンポラリディレクトリにアクセスできません", e);
                throw new ServletException("テンポラリディレクトリにアクセスできません");
            }
        } else {
            boolean yflg = false;
            try {
                yflg = IOTools.isDirCheck(tmpdir, true);
                //OSテンポラリディレクトリ + "GSTEMPDIR" をテンポラリディレクトリとして使用
                tmpdir = IOTools.setEndPathChar(tmpdir) + GSContext.TEMP_PATH_PREFIX;
            } catch (IOToolsException e) {
                log__.error("テンポラリディレクトリにアクセスできません", e);
            }
            if (yflg == false) {
                throw new ServletException("テンポラリディレクトリにアクセスできません");
            }
        }
        return tmpdir;
    }

    /**
     * <p>
     * Struts設定ファイルの読込
     * </p>
     * @param prefix
     *            Module prefix for this module
     * @param paths
     *            ここでは使用しない
     * @exception ServletException
     *                if initialization cannot be performed
     * @return ModuleConfig
     */
    @SuppressWarnings("deprecation")
    protected ModuleConfig initModuleConfig(String prefix, String paths)
            throws ServletException {

        if (log.isDebugEnabled()) {
            log.debug("Initializing module path '" + prefix
                    + "' configuration from '" + paths + "'");
        }

        // Parse the configuration for this module
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();
        ModuleConfig config = factoryObject.createModuleConfig(prefix);

        // Configure the Digester instance we will use
        Digester digester = initConfigDigester();

        // pluginのstrutsconfigを取得する
        List < String > pconfigs = getPluginStrutsConfigPath();
        Iterator < String > pcit = pconfigs.iterator();
        while (pcit.hasNext()) {
            digester.push(config);
            String path = IOTools.replaceSlashFileSep(pcit.next());
            String rpath = __getRealPath();
            // 相対パスでないとエラー？
            String soutai = IOTools.replaceSlashFileSep(path.substring(rpath
                    .length() - 1));
            log__.info("struts_config.xml path = " + soutai);

            try {
                this.parseModuleConfigFile(digester, soutai);
            } catch (UnavailableException e) {
                log__.error(PLUGIN_STRUTS_CONFIG_FILENAME + "の読込に失敗: ファイル名"
                        + path, e);
                throw new ServletException(e);
            } catch (Exception e) {
                log__.error(PLUGIN_STRUTS_CONFIG_FILENAME + "の読込に失敗: ファイル名"
                        + path, e);
                throw new ServletException(e);
            }
        }

        getServletContext().setAttribute(
                Globals.MODULE_KEY + config.getPrefix(), config);

        FormBeanConfig[] fbs = config.findFormBeanConfigs();
        for (int i = 0; i < fbs.length; i++) {
            if (fbs[i].getDynamic()) {
                fbs[i].getDynaActionFormClass();
            }
        }

        return config;
    }

    /**
     * <p>プラグインディレクトリに存在するStruts-Configを返す
     * @return パス格納リスト
     */
    private List < String > getPluginStrutsConfigPath() {
        ArrayList < String > ret = new ArrayList<String>();
        String rpath = __getRealPath();
        rpath = IOTools.setEndPathChar(rpath);
        // プラグインディレクトリのパス
        String pluginPath = rpath + "WEB-INF" + File.separator + "plugin";
        List < File > dirFiles = IOTools.getDirs(pluginPath);
        Iterator < File > it = dirFiles.iterator();
        while (it.hasNext()) {
            File dir = it.next();
            String dirPath = dir.getAbsolutePath();
            String configPath = dirPath + File.separator
                    + PLUGIN_STRUTS_CONFIG_FILENAME;
            try {
                if (IOTools.isFileCheck(dirPath, PLUGIN_STRUTS_CONFIG_FILENAME,
                        false)) {
                    ret.add(configPath);
                }
            } catch (IOToolsException e) {
                log__.error(PLUGIN_STRUTS_CONFIG_FILENAME + "の読込に失敗: ファイル名"
                        + configPath, e);
            }
        }
        return ret;
    }

    /**
     * <p>GS共通情報を取得する。
     * @return context を戻します。
     */
    public static GSContext getContext() {
        return gscontext__;
    }

    /**
     * <p>
     * Plugin設定ファイルの読込
     * </p>
     * @exception ServletException
     *                if initialization cannot be performed
     * @return プラグイン情報
     */
    private PluginConfig __initPluginConfig() throws ServletException {

        PluginConfig pluginConfig = new PluginConfig();
        //pluginの設定ファイルを取得する
        List < String > pconfigs = getPluginConfigPath();

        String path = "";
        try {
            String msgDir = IOTools.setEndPathChar(__getRealPath())
                            + "WEB-INF"
                            + File.separator
                            + "msgconf"
                            + File.separator;
            File msgFile = new File(msgDir + "Messages.properties");

            String commonMsgDir = IOTools.setEndPathChar(__getRealPath())
                                + "WEB-INF"
                                + File.separator
                                + "plugin"
                                + File.separator
                                + "common"
                                + File.separator;
            String commonMsgFile = commonMsgDir + "Messages.properties";

            IOTools.isDirCheck(msgDir, true);
            if (msgFile.exists()) {
                IOTools.deleteFile(msgFile.getPath());
            }
            msgFile.createNewFile();
            IOTools.copyBinFile(commonMsgFile, msgFile.getPath());

            //各pluginのクラスをロード
            for (String pconfig : pconfigs) {
                path = IOTools.replaceSlashFileSep(pconfig);

                Plugin plugin = PluginDigester.createPluginConfig(path);
                log__.info("---- plugin ID = " + plugin.getId());
                if (plugin.getLogInfo() != null) {
                    log__.info("plugin..getLogInfo().getOut() = " + plugin.getLogInfo().isOut());
                }
                pluginConfig.addPlugin(plugin);

            }
            
            //RestApiのロード
            RestApiRequestFilter.loadRestApi(getClass().getClassLoader());
        } catch (Exception e) {
            log__.error(PLUGIN_CONFIG_FILENAME + "の読込に失敗: ファイル名"
                    + path, e);
            throw new ServletException(e);
        }

        return pluginConfig;
    }


    /**
     * <p>プラグインディレクトリに存在するPlugin設定ファイルを返す
     * @return パス格納リスト
     */
    private List < String > getPluginConfigPath() {
        ArrayList < String > ret = new ArrayList<String>();
        String rpath = __getRealPath();
        rpath = IOTools.setEndPathChar(rpath);
        // プラグインディレクトリのパス
        String pluginPath = rpath + "WEB-INF" + File.separator + "plugin";
        List < File > dirFiles = IOTools.getDirs(pluginPath);
        Iterator < File > it = dirFiles.iterator();
        while (it.hasNext()) {
            File dir = it.next();
            String dirPath = dir.getAbsolutePath();
            String configPath = dirPath + File.separator
                    + PLUGIN_CONFIG_FILENAME;
            try {
                if (IOTools.isFileCheck(dirPath, PLUGIN_CONFIG_FILENAME,
                        false)) {
                    ret.add(configPath);
                }
            } catch (IOToolsException e) {
                log__.error(PLUGIN_STRUTS_CONFIG_FILENAME + "の読込に失敗: ファイル名"
                        + configPath, e);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] バッチスケジューラの再起動を行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SchedulerException スケジューラの停止に失敗
     * @throws ServletException スケジューラの起動に失敗
     */
    public void restartJob() throws SchedulerException, ServletException {
        if (__isBatchStatus()) {
            sched__.shutdown(true);
            __sleep(2000);
            __startJob();
        }
    }

    /**
     * <br>[機  能] データベース設定の再読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     * @throws Exception 再読み込みに失敗
     */
    public void restartDbServer() throws SQLException, Exception {
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        //ルートディレクトリ
        String rootPath = __getRealPath();
        dbUtil.restartDbServer(rootPath);
    }

    /**
     * <br>[機  能] データソース取得処理の実装クラス取得
     * <br>[解  説]
     * <br>[備  考]
     * @return データソース取得処理の実装クラス
     */
    public static IGsResourceManager getResourceManager() {
        String strResourceManager = GsResourceBundle.getString("IResourceManager");
        log__.info("IResourceManager" + strResourceManager);
        IGsResourceManager gsResourceManager = null;

        try {
            gsResourceManager =
                (IGsResourceManager) Class.forName(strResourceManager).newInstance();
        } catch (InstantiationException e) {
            log__.error(e);
        } catch (IllegalAccessException e) {
            log__.error(e);
        } catch (ClassNotFoundException e) {
            log__.error(e);
        }

        return gsResourceManager;
    }

    /**
     * <br>[機  能] 初期設定のプラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return PluginConfig
     */
    public static PluginConfig getPconfig() {
        return pluginConfig__;
    }

    /**
     * <br>[機  能] バッチの実行の設定を取得する
     * <br>[解  説] true:実行する false:実行しない
     * <br>[備  考]
     * @return バッチの実行の設定
     */
   private static boolean __isBatchStatus() {
       String batchStatus = CommonBiz.getBatchStatus();

       return batchStatus.equals(GSConst.BATCH_STATUS_TRUE)
               || batchStatus.equals(GSConst.BATCH_STATUS_LIMITATION);
    }

    /**
     * <br>[機  能] バッチ処理の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param job Job
     * @param jobName jobName
     * @param groupName GroupName
     * @param triggerName TrigerName
     * @param sconf バッチ起動時間
     * @throws SchedulerException バッチ処理の設定に失敗
     */
    private void __setScheduleJob(Job job, String jobName,
                               String groupName, String triggerName, String sconf)
    throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(job.getClass())
                .withIdentity(jobName, groupName)
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(sconf))
                .forJob(jobDetail)
                .build();

        sched__.scheduleJob(jobDetail, trigger);
    }

    /**
     * @param gscontext 設定する gscontext。
     */
    public static void setGscontext(GSContext gscontext) {
        gscontext__ = gscontext;
    }

    /**
     * <br>[機  能] プロセスメソッドをオーバーライドし、セッションに会社ドメインを保存
     * <br>         ログのスレッドコンテキストにドメインを保存
     * <br>[解  説]
     * <br>[備  考]
     * @param request リクエスト
     * @param response レスポンス
     */
    @Override
    protected void process(HttpServletRequest request,
            HttpServletResponse response) {
        String domain = null;
        IGsResourceManager rsm = GroupSession.getResourceManager();
        domain = rsm.getDomain(request);
        try {
            ThreadContext.put(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN, domain);
            super.process(request, response);
        } finally {
            ThreadContext.remove(GSConst.KEY_LOGTHREADSTRAGE_DOMAIN);

            //スレッド名に"END-"を追加
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());
        }

    }

    /**
     * <br>[機  能] ログ設定の再読込処理を行う。
     * <br>[解  説] オーバーライドしDBコネクション使用率の閾値設定の再読み込みを追加
     * <br>[備  考]
     */
    @Override
    public void initLogConfiguration() throws ServletException {
        //WEBアプリケーションのパス
        String prefix =  getServletContext().getRealPath("/");
        prefix = IOTools.setEndPathChar(prefix);
        //DBコネクション使用率の閾値設定を再読み込み
        GSDBConCntLogger.reloadProperty(prefix);
        super.initLogConfiguration();
    }
    /**
     *
     * <br>[機  能] リクエストメソッドをサポートしているか
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @return true サポートしている
     */
    private boolean __checkSupportMethod(HttpServletRequest req) {
        String[] spMethod = Optional.ofNullable(
                req.getAttribute(GSConst.ATTRKEY_SUPORTMETHOD))
                .filter(attr -> attr instanceof String[])
                .map(attr -> (String[]) attr)
                .orElse(GSConst.SUPORTMETHOD_DEF);
        if (spMethod.length == 0) {
            return false;
        }
        return Arrays.stream(spMethod)
            .anyMatch(method ->
                Objects.equals(
                        method,
                        req.getMethod().toUpperCase()));

    }
    /**
     * HttpServlet の PUTメソッドの有効化
     */
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!__checkSupportMethod(req)) {
            res.sendError(
                    405,
                    "method PUT not supported");
            return;
        }
        process(req, res);
//        super.doPost(req, resp);
    }

    /**
     * HttpServlet の DELETEメソッドの有効化
     */
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!__checkSupportMethod(req)) {
            res.sendError(
                    405,
                    "method DELETE not supported");
            return;

        }
        process(req, res);
//        super.doGet(req, resp);
    }

}