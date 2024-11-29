package jp.groupsession.v3.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.io.IOToolsException;

/**
 * <br>[機  能] db_init 再作成クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DBInit {

    /**
     * <br>[機  能] mainメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     * @throws Exception 例外発生
     */
    public static void main(String[] args) {

        if (args == null || args.length <= 0) {
            System.out.println("引数にルートパスが指定されていません。");
            return;
        }

        Connection con = null;
        try {
            String rootPath = args[0];
            rootPath = rootPath.replace('\\', '/');
            if (!rootPath.endsWith("/")) {
                rootPath += "/";
            }

            //ルートパス内の"war/WEB-INF" ディレクトリパスを取得
            String webInfPath = rootPath + "war/WEB-INF/";

            //war/WEB-INF/db-init/ディレクトリパスを取得
            String dbinitPath = webInfPath + "db_init/";

            //db_initが存在する場合は削除する
            File dbinitDir = new File(dbinitPath);
            deleteDir(dbinitDir);

            //実行対象のSQLファイルパス一覧を取得する
            List<File> sqlFileList = __getSQLFilePath(webInfPath);

            //除外対象SQLファイルの一覧を取得する
            String excludeFilePath = rootPath + "conf/db/sqlExclude.txt";
            List<String> excludeList = __readExcludeSQLList(excludeFilePath);

            //db_initに接続し、DBコネクションを取得
            con = getConnection(dbinitPath);

            //SQLの実行を行う
            for (File sqlFile : sqlFileList) {
                //除外対象SQLファイルかをチェック
                boolean excludeFlg = false;
                for (String excludeFile : excludeList) {
                    if (sqlFile.getPath().replace('\\', '/').indexOf(excludeFile) >= 0) {
                        excludeFlg = true;
                        System.out.println("excludeFile = " + sqlFile.getPath().replace('\\', '/'));
                    }
                }
                if (excludeFlg) {
                    continue;
                }

                InputStreamReader reader = null;
                try {
                    FileInputStream fis = new FileInputStream(sqlFile.getPath());
                    reader = new InputStreamReader(fis, "UTF-8");
                    org.h2.tools.RunScript.execute(con, reader);
                } catch (Exception e) {
                    System.out.println("エラー対象ファイル: " + sqlFile.getPath());
                    throw e;
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }

            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }


    }


    /**
     * <br>[機  能] DBコネクションを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dbDirPath DBディレクトリパス
     * @return DBコネクション
     * @throws Exception 例外発生
     */
    public static Connection getConnection(String dbDirPath) throws Exception {

        //接続文字列を取得
        String url = "jdbc:h2:";
        url += dbDirPath;
        url += "gs2db/gs2db";

        //各オプションを設定
        url += ";LOCK_MODE=3";
        url += ";LOCK_TIMEOUT=5000"; //タイムアウト
        url += ";DEFAULT_LOCK_TIMEOUT=5000";
        url += ";IFEXISTS=FALSE"; //※「自動作成(IFEXISTS)」をFALSE(存在しない場合、作成する)に設定する
        url += ";AUTOCOMMIT=TRUE"; //オートコミット true
        url += ";DB_CLOSE_ON_EXIT=FALSE"; //VM終了時にDatabaseをcloseする
        url += ";CACHE_SIZE=235929"; //キャッシュサイズ(256MB)
        url += ";PAGE_SIZE=8192";
        url += ";MAX_LENGTH_INPLACE_LOB=10240";
        url += ";CACHE_TYPE=SOFT_LRU";
        url += ";MVCC=TRUE"; //Multi-Version Concurrency Control (MVCC) を有効とする
        url += ";QUERY_TIMEOUT=120000";

        Connection con = null;


        //DBコネクション取得
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, "gsession", "gsession");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return con;
    }


    /**
     * <br>[機  能] 実行対象となるSQLファイルのファイルパスを取得する
     * <br>[解  説] WEB-INF/plugin/プラグインディレクトリ/sql ディレクトリ以下に存在する
     * <br>         下記ディレクトリ内のファイルを対象とする
     * <br>　・createtable
     * <br>　・index
     * <br>　・initdata
     * <br>　・h2
     * <br>[備  考]
     * @param webInfDir "war/WEB-INF"ディレクトリのディレクトリパス
     * @return SQLファイルのファイルパス一覧
     */
    private static List<File> __getSQLFilePath(String webInfDir) {

        List<File> sqlFileList = new ArrayList<File>();

        List<File> indexFileList = new ArrayList<File>();
        List<File> h2FileList = new ArrayList<File>();
        List<File> initdataFileList = new ArrayList<File>();


        String pluginPath = webInfDir + "plugin/";

        File file = new File(pluginPath);
        File[] pluginPathList = file.listFiles();
        for (File pluginDirPath : pluginPathList) {
            File sqlDirPath = new File(pluginPath + pluginDirPath.getName() + "/sql/");
            File[] sqlDirList = sqlDirPath.listFiles();

            //"sql"ディレクトリが存在しない場合
            if (sqlDirList == null) {
                continue;
            }

            for (File sqlDir : sqlDirList) {
                String sqlDirName = sqlDir.getName();

                if (sqlDirName.equals("createtable")) {
                    sqlFileList.addAll(Arrays.asList(sqlDir.listFiles()));
                } else if (sqlDirName.equals("index")) {
                    indexFileList.addAll(Arrays.asList(sqlDir.listFiles()));
                } else if (sqlDirName.equals("h2")) {
                    h2FileList.addAll(Arrays.asList(sqlDir.listFiles()));
                } else if (sqlDirName.equals("initdata")) {
                    initdataFileList.addAll(Arrays.asList(sqlDir.listFiles()));
                }

            }
        }

        //下記の順番でSQLファイルパスを設定する
        //※ 実行順番が異なる場合、「テーブルが存在しない」等のエラーが発生する
        //1. createsql
        //2. h2
        //3. index
        //4. initdata
        sqlFileList.addAll(h2FileList);
        sqlFileList.addAll(indexFileList);
        sqlFileList.addAll(initdataFileList);

        return sqlFileList;
    }

    /**
     * <br>[機  能] 指定したディレクトリ以下を全て削除します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param dir ディレクトリ
     * @exception IOToolsException 削除に失敗した場合にスロー
     */
    public static void deleteDir(final File dir) throws IOToolsException {
        if (!dir.exists()) { //すでにディレクトリが存在しない場合
            return;
        }
        if (!dir.isDirectory()) {
            throw new IOToolsException("ディレクトリではありません。");
        }

        //子削除
        File[] files = dir.listFiles();
        for (int intCnt = 0; intCnt < files.length; intCnt++) {
            if (files[intCnt].isDirectory()) {
                deleteDir(files[intCnt]);

            } else if (!files[intCnt].delete()) {
                throw new IOToolsException(
                    files[intCnt].getAbsolutePath()
                    + "の削除に失敗しました。");
            }
        }

        //ディレクトリ削除
        if (!dir.delete()) {
            throw new IOToolsException(
                dir.getAbsolutePath()
                + "の削除に失敗しました。");
        }
    }

    /**
     * <br>[機  能] 除外対象SQL設定ファイルから除外対象となるSQLファイルの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath
     * @return 除外対象SQLファイルの一覧
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    private static List<String> __readExcludeSQLList(String filePath)
    throws IOException {
        List<String> excludeList = new ArrayList<String>();

        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(filePath));

            String line = in.readLine();
            while (line != null) {
                excludeList.add(line);
                line = in.readLine();
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return excludeList;
    }
}
