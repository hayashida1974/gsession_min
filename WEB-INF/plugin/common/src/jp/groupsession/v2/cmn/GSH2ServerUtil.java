package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.tools.Server;

/**
 * <br>[機  能] H2Databaseの初期処理、サーバモードでの起動・終了を行う
 * <br>[解  説] 本クラスを使用することによって他のPCからDBへアクセス可能になります。
 * <br> JDBCのURLは「jdbc:h2:tcp://192.168.1.1:9092/gs2db」の様に設定してください。
 * <br>[備  考] ※本クラスは検証中であり、本稼動で使用することはお勧めしません。
 *
 * @author JTS
 */
public class GSH2ServerUtil implements IDbUtil {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GSH2ServerUtil.class);
    /** DB PORT */
    private static final int DB_PORT__ = 9092;
    /** DB名 */
    private static final String DB_NAME__ = "gs2db";

    /**
     * <br>[機  能] DBサーバを起動する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @throws Exception ServletException
     */
    public void startDbServer(String rootPath) throws Exception {
        //ポート番号を取得する
        String port = getDbPort();
        //データベースディレクトリ
        String dbDir = GsDataSourceFactory.getDbDir(rootPath);
        dbDir = dbDir + File.separator + DB_NAME__;
        log__.debug("DB DIR = " + dbDir);

        try {
            // H2サーバを起動（別スレッド）
            Server server = Server.createTcpServer(
                    new String[] {
                                "-baseDir", dbDir,
                                "-tcpPort", port,
                                "-tcpAllowOthers"
                                });
            server.start();
            log__.info("H2 DataBaseを起動");
        } catch (Exception e) {
            log__.debug("H2 DataBaseの起動に失敗", e);
            throw new ServletException("H2 DataBaseの起動に失敗", e);
        }
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
            sql.addSql("SHUTDOWN");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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

        //derbyデータベースファイル
        String dBFile = dbDir + File.separator + "gs2db";

        try {
            if (!IOTools.isDirCheck(dBFile, false)) {

                //initデータベースディレクトリ
                String derbyInitDir =
                    rootPath
                    + "WEB-INF"
                    + File.separator
                    + "db_init";

                //initデータベースファイル
                String derbyInitDBFile = derbyInitDir + File.separator + DB_NAME__;

                if (!IOTools.isDirCheck(derbyInitDBFile, false)) {
                    log__.error("db、db_initのどちらにもgs2dbが存在しません");
                    throw new ServletException("db、db_initのどちらにもgs2dbが存在しません");
                }

                //initデータベースをコピー
                IOTools.copyDir(new File(derbyInitDir), new File(dbDir));

            }
        } catch (IOToolsException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("IOToolsException");
        } catch (IOException e) {
            log__.error("H2 Databaseの設定に失敗", e);
            throw new ServletException("IOException");
        }

        log__.info("H2 Databaseの設定 終了");
    }

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
        String url = "jdbc:h2:tcp://localhost:" + getDbPort() + "/" + DB_NAME__;

        url += ";LOCK_MODE=3";  //READ時はロックしない
        url += ";LOCK_TIMEOUT=5000";  //タイムアウトを５秒に設定
        url += ";DEFAULT_LOCK_TIMEOUT=5000";  //タイムアウトを５秒に設定
        url += ";MULTI_THREADED=1";  //マルチスレッドで動作
        url += ";IFEXISTS=TRUE";     //指定されたパスが存在しない場合は自動で作成しない
        url += ";AUTOCOMMIT=OFF";    //オートコミットはしない
        url += ";DB_CLOSE_ON_EXIT=FALSE";     //VMが閉じたときに

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
