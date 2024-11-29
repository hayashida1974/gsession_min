package jp.groupsession.v2.cmn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.tools.RunScript;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] H2Databaseの初期処理、起動、終了を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSH2Util implements IDbUtil {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GSH2Util.class);
    /** DB PORT */
    private static final int DB_PORT__ = 9092;
    /** DB名 */
    private static final String DB_NAME__ = "gs2db";

    /** 接続文字列オプション */
    private static String jdbcUrlOption__ = null;

    static {

        //H2Database 接続オプションを取得する
        try {
            ResourceBundle optionResource = ResourceBundle.getBundle("connectOption");

            Enumeration<String> keys = optionResource.getKeys();
            StringBuilder sb = new StringBuilder("");
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = optionResource.getString(key);
                if (key != null && "QUERY_TIMEOUT_BATCH".equals(key.toUpperCase())) {
                    continue;
                }
                if (!StringUtil.isNullZeroString(value)) {
                    sb.append(";").append(key).append("=").append(value);
                }
            }

            jdbcUrlOption__ = sb.toString();

        } catch (Exception e) {
            log__.info("H2 Database 接続オプションなし", e);
        }
    }

    /**
     * <br>[機  能] DBサーバを起動する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @throws Exception ServletException
     */
    public void startDbServer(String rootPath) throws Exception {
//        //ポート番号を取得する
//        String port = getDbPort();
//        //データベースディレクトリ
//        String dbDir = GsDataSourceFactory.getDbDir(rootPath);
//        dbDir = dbDir + File.separator + DB_NAME__;
//        log__.debug("DB DIR = " + dbDir);
//
//        try {
//            // H2サーバを起動（別スレッド）
//            Server server = Server.createTcpServer(new String[] { "-baseDir",
//                    dbDir, "-tcpPort", port });
//            server.start();
//            log__.info("H2 DataBaseを起動");
//        } catch (Exception e) {
//            log__.debug("H2 DataBaseの起動に失敗", e);
//            throw new ServletException("H2 DataBaseの起動に失敗", e);
//        }
    }

    /**
     * <br>[機 能] DBサーバを停止する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param rootPath アプリケーションのルートパス
     * @param con Connection
     * @throws Exception Exception
     */
    public void shutdownDbServer(String rootPath, Connection con) throws Exception {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
//            sql.addSql("SHUTDOWN IMMEDIATELY");
            sql.addSql("SHUTDOWN");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            log__.info("しばらくお待ちください。");
            pstmt.executeUpdate();
            con = null;
            System.gc();

            log__.info("H2 DataBaseを終了");

        } catch (Exception e) {
            log__.debug("H2 DataBaseの終了に失敗", e);
            throw new ServletException("H2 DataBaseの終了に失敗", e);
//        } finally {
//            JDBCUtil.closeConnection(con);
        }
    }

    /**
     * <br>[機  能] データベース設定の再読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws Exception 再読み込みに失敗
     */
    public void restartDbServer(String rootPath) throws SQLException, Exception {
        this.shutdownDbServer(rootPath, GroupSession.getConnection(GSConst.GS_DOMAIN));
        __sleep(2000);
        this.startDbServer(rootPath);
    }

    /**
     * <br>[機  能] DBサーバの初期設定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのパス
     * @throws ServletException derby設定時例外
     */
    public void init(String rootPath) throws ServletException {
        log__.info("H2 Databaseの設定 開始");
        //データベースディレクトリ
        String dbDir = GsDataSourceFactory.getDbDir(rootPath);

        //H2データベースファイル
        String dBFile = dbDir + File.separator + "gs2db";

        try {
            if (!IOTools.isDirCheck(dBFile, false)) {

                //initデータベースディレクトリ
                String h2InitDir =
                    rootPath
                    + "WEB-INF"
                    + File.separator
                    + "db_init";

                //initデータベースファイル
                String derbyInitDBFile = h2InitDir + File.separator + DB_NAME__;

                if (!IOTools.isDirCheck(derbyInitDBFile, false)) {
                    log__.error("db、db_initのどちらにもgs2dbが存在しません");
                    throw new ServletException("db、db_initのどちらにもgs2dbが存在しません");
                }

                //initデータベースをコピー
                IOTools.copyDir(new File(h2InitDir), new File(dbDir));

            }
            //H2 V1.1チェック
            if (isH2v11(dBFile)) {
                //V1.1の場合V1.2へ変換を行う
                log__.info("h2 database migrate v1.1 to v1.2 start");
                convertH2v11tov12(rootPath);
            }

            //ロックファイルを削除
            File writeLockFile = new File(dBFile + File.separator + "gs2db"
                                        + File.separator + "write.lock");
            if (writeLockFile.exists() && writeLockFile.canWrite()) {
                writeLockFile.delete();
            }

        } catch (SQLException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("SQLException");
        } catch (IOToolsException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("IOToolsException");
        } catch (IOException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("IOException");
        } catch (SAXException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("SAXException");
        } catch (InterruptedException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("InterruptedException");
        } catch (Exception e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("Exception");
        }

        log__.info("H2 Databaseの設定 終了");
    }

    /**
     * <br>[機  能] H2のファイルフォーマットがV1.1かどうか判定を行う。
     * <br>[解  説] 判定方法は拡張子が「.data.db」であるかどうかで判定を行う
     * <br>[備  考]
     * @param path dbディレクトリのパス
     * @return true:1.1, false:other
     */
    public boolean isH2v11(String path) {
        boolean ret = false;
        //
        File dbDir = new File(path);
        for (File f : dbDir.listFiles()) {
            log__.info("ファイル名 " + f.getName());
            if (f.getName().endsWith(".data.db")) {
                ret = true;
                return ret;
            }
        }
        return ret;
    }
    /**
     * <br>[機  能] H2のファイルフォーマットを1.1から1.2形式へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath dbディレクトリのパス
     * @throws IOException 書き込みエラー
     * @throws SQLException DB実行時例外
     * @throws SAXException XML読み込みエラー
     * @throws InterruptedException 実行時例外
     * @throws Exception 実行時例外
     */
    public void convertH2v11tov12(String rootPath)
    throws IOException, SQLException, SAXException, InterruptedException, Exception {
        //backup.sqlファイルを作成する
        String dbDir = GsDataSourceFactory.getDbDir(rootPath);
        String url = "jdbc:h2:" + dbDir + DB_NAME__ + File.separator + DB_NAME__;
        String scriptFileName = dbDir + DB_NAME__ + File.separator + "backup.sql";

        //DS設定情報を取得
        DataSourceModel dsModel = getDataSourceModel(rootPath);
        String user = dsModel.getUser();
        String password = dsModel.getPass();
        log__.debug("ds user ==>" + user);
        log__.debug("ds password ==>" + password);

        //javaのパス
        String javaPath = System.getProperty("java.home");
        log__.info("javaPath==>" + javaPath);
        DecimalFormat f1 = new DecimalFormat("#,###KB");
        DecimalFormat f2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / 1024;
        long total = Runtime.getRuntime().totalMemory() / 1024;
        long max = Runtime.getRuntime().maxMemory() / 1024;
        long used = total - free;
        double ratio = (used * 100 / (double) total);
        String info =
        "Java メモリ情報 : 合計=" + f1.format(total) + "、"
        + "使用量=" + f1.format(used) + " (" + f2.format(ratio) + "%)、"
        + "使用可能最大=" + f1.format(max);
        log__.info(info);

        //割り当てメモリ設定(初期値128MB)
        long maxXmxMb = 128;
        String xmxMbStr = "128m";
        if (max > 0) {
            maxXmxMb = max / 1024;
        }
        if (maxXmxMb > 128) {
            xmxMbStr = String.valueOf(maxXmxMb) + "m";
        }

        String[] cmdStr = new String[] {
                IOTools.setEndPathChar(javaPath) + "bin" + File.separator + "java",
                "-Xmx" + xmxMbStr,
                "-cp", rootPath + "/WEB-INF/convert/lib/h2-1.2.127.jar",
                "org.h2.tools.Script",
                "-script", scriptFileName,
                "-url", url,
                "-user", user,
                "-password", password
        };
        InputStream inErr = null;
        Vector<String> vecOsOutput = new Vector<String>();
        try {
            StringBuilder buf = new StringBuilder();
            for (String c : cmdStr) {
                buf.append(c + " ");
            }
            log__.info(buf.toString());
            Process p = Runtime.getRuntime().exec(cmdStr);
//            inErr = __getInErr(p); //入力、エラーストリームを取得
            inErr = p.getErrorStream();
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(inErr, Encoding.JISAUTODETECT));
            String line;
            while ((line = bf.readLine()) != null) {
                vecOsOutput.add(line);
            }
            p.waitFor();
        } catch (IOException e) {
            log__.error("v1.1からv1.2のmigrateに失敗", e);
            throw e;
        } catch (InterruptedException e) {
            log__.error("v1.1からv1.2のmigrateに失敗", e);
            throw e;
        }

        //コマンド実行時のエラー有無判定
        boolean errorFlg = false;
        if (vecOsOutput.size() > 0) {
            errorFlg = true;
            for (int i = 0; i < vecOsOutput.size(); i++) {
                log__.fatal((String) vecOsOutput.elementAt(i));
            }
        }

        if (errorFlg) {
            log__.fatal("v1.1からv1.2のmigrateに失敗しました。");
            throw new Exception();
        } else {
            log__.info(scriptFileName + "の作成完了");

            //v1.2でバックアップスクリプトを取込む
            String oldFilePath = dbDir + DB_NAME__ + File.separator + "gs2db.data.db";
            log__.info(oldFilePath);
            File oldFile = new File(oldFilePath);
            oldFile.renameTo(new File(oldFile.getAbsoluteFile() + ".backup"));
            log__.info("旧ファイルのリネーム完了");
            //
            RunScript.execute(url, user, password, scriptFileName, "UTF-8", true);
            new File(scriptFileName).delete();
            log__.info("scriptファイルの削除完了");
            new File(oldFile.getAbsoluteFile() + ".backup").delete();
            log__.info("旧ファイルの削除完了");
            //
            log__.info("migrate完了");
        }

    }

    /**
     * データソースの設定モデルを取得する
     * @param appRoot アプリケーションRootPath
     * @return DataSourceModel データソースの設定モデル
     * @throws IOException XML読み込み失敗
     * @throws SAXException XML読み込み失敗
     */
    private DataSourceModel getDataSourceModel(String appRoot) throws IOException, SAXException {
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        //変更前XMLを取得
        String prefix = IOTools.setEndPathChar(appRoot);
        String dsPath = IOTools.replaceSlashFileSep(prefix
                + "/WEB-INF/conf/dataSource.xml");

        DataSourceModel model = GsDataSourceFactory.createDataSourceModel(
                GSConst.DATASOURCE_KEY, dsPath, dbUtil, appRoot);
        return model;
    }
//    /**
//     * [機 能] 入力ストリーム、エラーストリームを取得する。<br>
//     * [解 説] <br>
//     * [備 考] <br>
//     * @param  pr Process
//     * @return InputStream
//     */
//    private InputStream __getInErr(Process pr) {
//        InputStream in = pr.getInputStream();
//        InputStream err = pr.getErrorStream(); // get process ERROR stream
//        return new SequenceInputStream(in, err); // in, err を繋ぐ
//    }

    /**
     * <br>[機  能] DB接続文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @param dsModel データソース情報のモデル
     * @return db url
     */
    public String createUrl(String rootPath, DataSourceModel dsModel) {
        //Server Mode
//        String url = "jdbc:h2:tcp://localhost:" + getDbPort() + "/" + DB_NAME__;
        //Embedded Mode
        String dbDir = GsDataSourceFactory.getDbDir(rootPath);
        String url = "jdbc:h2:" + dbDir + DB_NAME__ + File.separator + DB_NAME__;

//        url += ";LOCK_MODE=3";  //READ時はロックしない
//        url += ";LOCK_TIMEOUT=5000";  //タイムアウトを５秒に設定
//        url += ";DEFAULT_LOCK_TIMEOUT=5000";  //タイムアウトを５秒に設定
//        url += ";MULTI_THREADED=1";  //マルチスレッドで動作
//        url += ";IFEXISTS=TRUE";     //指定されたパスが存在しない場合は自動で作成しない
//        url += ";AUTOCOMMIT=OFF";    //オートコミットはしない
////        url += ";DB_CLOSE_DELAY=4"; //4秒後に閉じる
//        url += ";DB_CLOSE_ON_EXIT=FALSE";     //VMが閉じたときに

        //接続文字列にオプションを追加
        url += jdbcUrlOption__;

        log__.debug("db_url==>" + url);
        return url;
    }

    /**
     * DBが使用するPORT番号を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @return String
     */
    public static String getDbPort() {
        //設定ファイル読み込み
        String confDbPort = ConfigBundle.getValue("DB_PORT");
        log__.debug("confDbPort==>" + confDbPort);
        String port = "";
        if (StringUtil.isNullZeroStringSpace(confDbPort)) {
            port = Integer.toString(DB_PORT__);
        } else {
            port = confDbPort;
        }
        log__.debug("port==>" + port);
        return port;
    }

//    /**
//     * <br>[機  能] H2 DBのコンパクト化
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public static void compact(String rootPath) throws Exception {
//        String dbDir = GsDataSourceFactory.getDbDir(rootPath);
//        System.out.println("DBDIR = " + dbDir);
//
//
//        String url = "jdbc:h2:" + dbDir + DB_NAME__ + File.separator + DB_NAME__;
//        String file = "data/compact.sql";
//        //
//        //IDbUtil dbUtil = DBUtilFactory.getInstance();
//        String dbConfPath = GsDataSourceFactory.getDataSourcePath(rootPath);
//        DataSourceModel dsmodel = GsDataSourceFactory.createDataSourceModel(
//                            GSConst.DATASOURCE_NUM_KEY,dbConfPath, new GSH2Util(), rootPath);
//
//        //
//        String dbuser = dsmodel.getUser();
//        String dbpass = dsmodel.getPass();
////        Script.execute(url, dbuser, dbpass, file);
////        System.out.println("スクリプト生成完了");
////        DeleteDbFiles.execute(dbDir, DB_NAME__, true);
////        System.out.println("削除完了");
//        RunScript.execute(url, dbuser, dbpass, file, null, true);
//    }
//
//    /**
//     * <br>[機  能]
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public static void main(String args[]) throws Exception {
//        //
//        String path = "C:/eclipse3_2_2_mobile/workspace/GSession2_4/war/";
//        GSH2Util.compact(path);
//        System.out.println("コンパクション完了");
//    }

    /**
     * <br>[機  能] デフォルトエスケープ文字を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return デフォルトエスケープ文字
     */
    public String defaultEscape() {
        return "\\";
    }
    
    /**
     * <br>[機  能] like検索時のデフォルトエスケープ文字列(置換前)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return デフォルトエスケープ文字列
     */
    public String defaultLikeEscape() {
        return "\\";
    }
    
    /**
     * <br>[機  能] like検索時のデフォルトエスケープ文字列(置換後)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return デフォルトエスケープ文字列
     */
    public String defaultLikeEscapeAfter() {
        return "\\\\";
    }

    /**
     * <br>[機  能] DBの種類を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return DBの種類(0:H2 Database)
     */
    public int getDbType() {
        return GSConst.DBTYPE_H2DB;
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
}
