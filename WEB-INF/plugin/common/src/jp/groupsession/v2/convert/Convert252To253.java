package jp.groupsession.v2.convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.log.log4j.Log4jConfig;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.h2.tools.RunScript;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] Ver2.5.2のDBをVer2.5.3形式へコンバートするコンバータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Convert252To253 {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Convert252To253.class);
    /** デフォルトユーザ名 */
    private static final String DF_USER = "gsession";
    /** デフォルトパスワード */
    private static final String DF_PASS = "gsession";
    /** DB名 */
    private static final String DB_NAME__ = "gs2db";

    /** ユーザ名 */
    private static String userName__ = null;
    /** パスワード */
    private static String userPass__ = null;

    /**
     * Ver2.5.2のDBをVer2.5.3形式へコンバートする
     * @param args パラメータ
     * @throws Exception 実行時例外
     */
    public static void main(String[] args) throws Exception {
        __readLog4j2Xml();
        if (args.length <= 0) {
            //
            log__.warn("コンバートオプションを指定してください。");
            log__.warn("コンバートを中止します。");
            return;
        }

        //V2ルートディレクトリ
        String v2appRoot = IOTools.setEndPathChar(args[0]);
        String tmpPath = IOTools.setEndPathChar(v2appRoot + "tmp");
        if (IOTools.isDirCheck(tmpPath, true)) {
            File appRootDir = new File(v2appRoot);
            log__.info("ルートディレクトリ = " + appRootDir.getCanonicalPath());
        } else {
            //作成できない
            throw new IOToolsException("作業フォルダを作成できません。");
        }
        //変更先DBディレクトリ
        String dbDir = IOTools.setEndPathChar(GsDataSourceFactory.getDbDir(v2appRoot));
        //dataSource.xml設定内容を取得
        DataSourceModel model = getDataSourceModel(v2appRoot);
        userName__ = DF_USER;
        userPass__ = DF_PASS;
        if (model != null) {
            userName__ = NullDefault.getString(model.getUser(), DF_USER);
            userPass__ = NullDefault.getString(model.getPass(), DF_PASS);
        }

        log__.info(dbDir);
        log__.info(userName__);
        log__.info(userPass__);

        String url = "jdbc:h2:" + dbDir + DB_NAME__ + File.separator + DB_NAME__;
        String scriptFileName = dbDir + DB_NAME__ + File.separator + "backup.sql";
        String javaPath = System.getProperty("java.home");
        long max = Runtime.getRuntime().maxMemory() / 1024;
        String xmxMbStr = "128m";
        if (max > 0) {
            xmxMbStr = String.valueOf(max / 1024) + "m";
        }

        String[] cmdStr = new String[] {
                IOTools.setEndPathChar(javaPath) + "bin" + File.separator + "java",
                "-Xmx" + xmxMbStr,
                "-cp", v2appRoot + "/WEB-INF/convert/lib/h2-1.2.127.jar",
                "org.h2.tools.Script",
                "-script", scriptFileName,
                "-url", url,
                "-user", userName__,
                "-password", userPass__
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
            //入力、エラーストリームを取得
            inErr = p.getErrorStream();
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(inErr, Encoding.JISAUTODETECT));
            String line;
            while ((line = bf.readLine()) != null) {
                vecOsOutput.add(line);
            }
            p.waitFor();
        } catch (Exception e) {
            log__.error("v2.5.2からv2.5.3へのコンバートに失敗しました。");
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
            log__.info("CONVERT FAILED");
            log__.error("v2.5.2からv2.5.3へのコンバートに失敗しました。"
                    + "Javaの割り当てメモリ足りない可能性があります。"
                    + "環境に合せてメモリ設定を増やして実行してください。");
        } else {
            log__.info(scriptFileName + "の作成完了");

            //v1.2でバックアップスクリプトを取込む
            String oldFilePath = dbDir + DB_NAME__ + File.separator + "gs2db.data.db";
            log__.info(oldFilePath);
            File oldFile = new File(oldFilePath);
            oldFile.renameTo(new File(oldFile.getAbsoluteFile() + ".backup"));
            log__.info("コンバート元ファイルのリネーム完了");
            //
            RunScript.execute(url, userName__, userPass__, scriptFileName, "UTF-8", true);
            new File(scriptFileName).delete();
            log__.debug("scriptファイルの削除完了");
            new File(oldFile.getAbsoluteFile() + ".backup").delete();
            log__.debug("旧ファイルの削除完了");
            //SUCCESSFUL
            log__.info("CONVERT SUCCESSFUL");
            log__.info("v2.5.2からv2.5.3へのコンバートに成功しました");
        }
    }

    /**
     * <br>[機  能] 現データソース設定ファイルの内容をオブジェクトとして取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションRootPath
     * @return DataSourceModel
     * @throws IOException IO例外
     * @throws SAXException XML読み込み例外
     */
    public static DataSourceModel getDataSourceModel(String appRoot)
    throws IOException, SAXException {
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        //XMLを取得
        String prefix = IOTools.setEndPathChar(appRoot);
        String dsPath = IOTools.replaceSlashFileSep(prefix
                + "/WEB-INF/conf/dataSource.xml");
        DataSourceModel model = GsDataSourceFactory.createDataSourceModel(
                GSConst.DATASOURCE_KEY, dsPath, dbUtil, appRoot);
        return model;
    }

    /**
     * <br>[機  能] log4j2.xmlを読み込みます。
     * <br>[解  説]
     * <br>[備  考]
     */
    private static void __readLog4j2Xml() {
        //カレントディレクトリ取得
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir.substring(0, currentDir.length() - 3);

        //xmlファイルまでのパス取得
        String xmlPath = IOTools.replaceSlashFileSep(
                currentDir + "/conf/");

        //log4j2.xmlファイルにクラスパスを通す
        System.setProperty(
                ConfigurationFactory.CONFIGURATION_FILE_PROPERTY,
                "file:///" + xmlPath + "log4j2.xml");

        String logConfFile = IOTools.replaceSlashFileSep(
                xmlPath + "/log4j2.xml");
        Log4jConfig.reloadConfigFile(logConfFile);
    }
}
