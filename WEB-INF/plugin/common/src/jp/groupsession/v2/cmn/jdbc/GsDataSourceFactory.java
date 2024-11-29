package jp.groupsession.v2.cmn.jdbc;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.DataSourceUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] GroupSessionで使用するデータソースのモデルを作成する。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsDataSourceFactory {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GsDataSourceFactory.class);

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath 設定ファイルのフルパス
     * @param dbUtil DBユーティリティ
     * @param rootPath アプリケーションROOTパス
     * @return javax.sql.DataSource
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     */
    public static DataSource createDataSource(String filePath, IDbUtil dbUtil, String rootPath)
            throws IOException, SAXException {
        DataSourceModel model = createDataSourceModel(
                GSConst.DATASOURCE_KEY, filePath, dbUtil, rootPath);
        return DataSourceUtil.createDataSource(model);
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番用データソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath 設定ファイルのフルパス
     * @param dbUtil DBユーティリティ
     * @param rootPath アプリケーションROOTパス
     * @return javax.sql.DataSource
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     */
    public static DataSource createDataSourceCountController(String filePath,
            IDbUtil dbUtil, String rootPath) throws IOException, SAXException {
        DataSourceModel model = createDataSourceModel(
                GSConst.DATASOURCE_NUM_KEY, filePath, dbUtil, rootPath);
        return DataSourceUtil.createDataSource(model);
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param id データソースID
     * @param filePath 設定ファイルのフルパス
     * @param dbUtil DBユーティリティ
     * @param rootPath アプリケーションROOTパス
     * @return DataSourceModel
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     */
    public static DataSourceModel createDataSourceModel(String id,
            String filePath, IDbUtil dbUtil, String rootPath) throws IOException, SAXException {
        DataSourceSetting setting = createBaseDataSourceSetting(filePath);
        log__.debug("データソースID==>" + id);
        DataSourceModel model = setting.getDataSourceModel(id);
        model.setUrl(dbUtil.createUrl(rootPath, model));
        return model;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースの基本モデルを作成する。(複数)
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath ファイルパス
     * @return 生成したDataSourceSettingオブジェクト
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     */
    public static DataSourceSetting createBaseDataSourceSetting(String filePath)
            throws IOException, SAXException {

        log__.debug("filePath is " + filePath);
        Digester d = new Digester();
        d.setValidating(false);
        d.addObjectCreate("DataSourceSetting", DataSourceSetting.class);

        d.addObjectCreate("DataSourceSetting/datasource", DataSourceModel.class);
        d.addSetNext("DataSourceSetting/datasource", "addDatasource");
        d.addBeanPropertySetter("DataSourceSetting/datasource/id", "id");
        d.addBeanPropertySetter("DataSourceSetting/datasource/driverClassName", "driverClassName");

        d.addBeanPropertySetter("DataSourceSetting/datasource/serverUrl", "serverUrl");
        d.addBeanPropertySetter("DataSourceSetting/datasource/port", "port");
        d.addBeanPropertySetter("DataSourceSetting/datasource/sid", "sid");

        d.addBeanPropertySetter("DataSourceSetting/datasource/username", "user");
        d.addBeanPropertySetter("DataSourceSetting/datasource/password", "pass");
        d.addBeanPropertySetter(
                "DataSourceSetting/datasource/defaultAutoCommit",
                "defaultAutoCommit");
        d.addBeanPropertySetter("DataSourceSetting/datasource/defaultReadOnly", "defaultReadOnly");
        d.addBeanPropertySetter("DataSourceSetting/datasource/maxTotal", "maxTotal");
        d.addBeanPropertySetter("DataSourceSetting/datasource/maxIdle", "maxIdle");
        d.addBeanPropertySetter("DataSourceSetting/datasource/maxWait", "maxWait");
        d.addBeanPropertySetter("DataSourceSetting/datasource/validationQuery", "validationQuery");

        d.addBeanPropertySetter("DataSourceSetting/datasource/removeAbandonedOnBorrow", "removeAbandonedOnBorrow");
        d.addBeanPropertySetter("DataSourceSetting/datasource/removeAbandonedTimeout",
                                "removeAbandonedTimeout");
        d.addBeanPropertySetter("DataSourceSetting/datasource/logAbandoned", "logAbandoned");
        d.addBeanPropertySetter("DataSourceSetting/datasource/accessToUnderlyingConnectionAllowed",
                                "accessToUnderlyingConnectionAllowed");

        DataSourceSetting setting = (DataSourceSetting) d.parse(new File(filePath));
        return setting;
    }

    /**
     * <br>[機  能] データソース設定ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのパス
     * @return データソース設定ファイルパス
     */
    public static String getDataSourcePath(String rootPath) {

        //データソースの読込み
        String dsPath = null;
        //WEBアプリケーションのパス
        String prefix = IOTools.setEndPathChar(rootPath);
        dsPath = IOTools.replaceSlashFileSep(prefix
                + "/WEB-INF/conf/dataSource.xml");
        return dsPath;
    }

    /**
     * <br>[機  能] データソース設定ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのパス
     * @param domain ドメイン
     * @return データソース設定ファイルパス
     */
    public static String getDataSourcePath(String rootPath, String domain) {

        //データソースの読込み
        String dsPath = null;
        //WEBアプリケーションのパス
        String prefix = IOTools.setEndPathChar(rootPath);
        dsPath = IOTools.replaceSlashFileSep(prefix
                + "/WEB-INF/conf/db/dataSource_" + domain + ".xml");
        return dsPath;
    }

    /**
     * <br>[機  能] DBの保存先ディレクトリのパスを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのパス
     * @return derbyデータベースディレクトリ
     */
    public static String getDbDir(String rootPath) {

        //データベースディレクトリ
        String derbyDir = "";
        try {
            ConfigBundle.readConfig(rootPath);
        } catch (IOException e) {
            log__.error("設定ファイルの読み込みに失敗", e);
        }
        String confDerbyDir = ConfigBundle.getValue("GSDATA_DIR");
        log__.info("gsdata.conf==>" + confDerbyDir);

        if (StringUtil.isNullZeroString(confDerbyDir)) {
            derbyDir =
                rootPath
                + "WEB-INF"
                + File.separator
                + "db";
        } else {
            confDerbyDir = IOTools.setEndPathChar(confDerbyDir);
            derbyDir = confDerbyDir + "db";
        }
        log__.info(derbyDir);
        derbyDir = IOTools.setEndPathChar(derbyDir);
        return derbyDir;
    }
}