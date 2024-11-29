package jp.groupsession.v3.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.sort.FileSorter;

/**
 * <br>[機  能] sql_init.sql作成クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CreateSqlFile {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CreateSqlFile.class);
    /** ルートディレクトリパス */
    private static File rootDir__ = null;

    /**
     * <br>[機  能]sql_init.sqlファイルを作成する(toolsの直下に出力)
     * <br>[解  説]
     * <br>[備  考]
     * @param args 引数
     * @throws Exception 実行例外
     */
    public static void main(String[] args) throws Exception {
        File crnt = new File(".");
        String cpath = crnt.getAbsolutePath();
        if (cpath.indexOf("ZION") > 0 || cpath.indexOf("byCloud") > 0) {
            //ZION or byCloudパッケージ作成の場合
            rootDir__ = new File("../../war/WEB-INF/tools/");
        } else {
            //その他
            rootDir__ = new File("../../../war/WEB-INF/tools/");
        }

        String appRoot = IOTools.setEndPathChar(rootDir__.getAbsolutePath());
        appRoot = appRoot.replace("tools", "plugin");
        rootDir__ = new File(appRoot);
        log__.info("rootDir__=" + rootDir__);

        String fldrName = null;

        //pluginフォルダ以下のディレクトリを取得
        File[] dirs = rootDir__.listFiles();
        dirs = FileSorter.sort(dirs, true, 0);

        //commonを先に実行
        File cmnSqlDir = new File(appRoot + "common" + File.separator + "sql");
        log__.info("common path " + cmnSqlDir);
        getSqlFile(cmnSqlDir);

        for (int i = 0; i < dirs.length; i++) {
            File pluginDir = dirs[i];
            if (pluginDir.isFile()) {
                continue;
            }
            String path = pluginDir.getAbsolutePath();
            if (path.toLowerCase().indexOf("common") > 0) {
                continue;
            }

            //sqlフォルダを探す
            File[] sqlDirs = pluginDir.listFiles();
            sqlDirs = FileSorter.sort(sqlDirs, true, 0);
            for (int n = 0; n < sqlDirs.length; n++) {
                File sqlDir = sqlDirs[n];

                fldrName = sqlDir.toString();
                //sqlフォルダがあった場合
                if (fldrName.indexOf("sql") != -1) {
                    getSqlFile(sqlDir);
                }
            }
        }
        log__.info("正常終了");
    }

    /**
     * <br>[機  能] sqlファイルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dir ディレクトリのパス
     * @throws IOException 実行例外
     */
    public static void getSqlFile(File dir) throws IOException {

        String fldrName = null;

        File[] sqlFldrDirs = dir.listFiles();
        sqlFldrDirs = FileSorter.sort(sqlFldrDirs, true, 0);

        for (int i = 0; i < sqlFldrDirs.length; i++) {
            File sqlFldr = sqlFldrDirs[i];
            fldrName = sqlFldr.toString();

            //sqlファイルを格納したフォルダがあった場合
            if (fldrName.indexOf("postgres") != -1) {
                //postgresフォルダを先に実行
                File[] sqlFileDirs = sqlFldr.listFiles();
                //ファイルを名前でソート
                sqlFileDirs = FileSorter.sort(sqlFileDirs, true, 0);
                for (int n = 0; n < sqlFileDirs.length; n++) {
                    File sqlFileDir = sqlFileDirs[n];
                    log__.info("ファイル名" + sqlFileDir.toString());
                }
                //sql_init.sqlの作成
                createSqlFile(sqlFileDirs);
            }
        }

        for (int i = 0; i < sqlFldrDirs.length; i++) {
            File sqlFldr = sqlFldrDirs[i];
            fldrName = sqlFldr.toString();

            if (fldrName.indexOf("postgres") == -1) {
                //sqlファイルを格納したフォルダがあった場合
                if (fldrName.indexOf("createtable") != -1
                       || fldrName.indexOf("initdata") != -1
                         || fldrName.indexOf("index") != -1) {

                    File[] sqlFileDirs = sqlFldr.listFiles();
                    //ファイルを名前でソート
                    sqlFileDirs = FileSorter.sort(sqlFileDirs, true, 0);
                    for (int n = 0; n < sqlFileDirs.length; n++) {
                        File sqlFileDir = sqlFileDirs[n];
                        log__.info("ファイル名" + sqlFileDir.toString());
                    }
                    //sql_init.sqlの作成
                    createSqlFile(sqlFileDirs);
                }
            }
        }
    }

    /**
     * <br>[機  能] sqlファイルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirs ディレクトリのパスの配列
     * @throws IOException 実行例外
     */
    public static void createSqlFile(File[] dirs) throws IOException {
        try {

            for (int i = 0; i < dirs.length; i++) {
                File sqlFileDir = dirs[i];
                //System.out.println("sqlFileDir=" + sqlFileDir);

                //拡張子がsqlのファイルを読み込む
                if (sqlFileDir.getPath().endsWith("sql")) {

                    FileInputStream is = new FileInputStream(
                                                       sqlFileDir.getPath());
                    //InputStreamReader in = new InputStreamReader(is, "Shift_JIS");
                    InputStreamReader in = new InputStreamReader(is, Encoding.UTF_8);

                    int ch;
                    StringBuilder strBuf = new StringBuilder();
                    while ((ch = in.read()) != -1) {
                        strBuf.append((char) ch);
                    }
                    in.close();
                    writeSqlFile(strBuf);
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] sqlの書き込みを行なう
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字
     * @throws IOException 実行例外
     */
    public static void writeSqlFile(StringBuilder str) throws IOException {
        String filePath = rootDir__.toString().replace("plugin", "tools") + "//sql_init.sql";
        BufferedWriter out =  null;
        try {
            FileOutputStream os = new FileOutputStream(filePath, true);
            out = new BufferedWriter(new OutputStreamWriter(os, Encoding.UTF_8));
            out.write(str.toString());
            out.newLine();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
            out = null;
        }
    }
}