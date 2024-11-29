package jp.groupsession.v2.cmn.jdbc;

import java.io.IOException;

import javax.sql.DataSource;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.DataSourceUtil;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] データソースを取得する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GsDataSourceManager implements IGsDataSourceManager {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GsDataSourceFactory.class);

    /** データソース */
    private static DataSource ds__ = null;

    /** 採番コントローラー */
    private static MlCountMtController dsCountController__ = null;

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return javax.sql.DataSource
     * @throws Exception データソースの取得に失敗
     */
    public DataSource getDataSource(RequestModel reqMdl) throws Exception {

        if (ds__ == null) {
            __createDataSource();
        }
        return ds__;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメインキー
     * @return javax.sql.DataSource
     * @throws Exception データソースの設定に失敗
     */
    public DataSource getDataSource(String dsKey) throws Exception {

        if (ds__ == null) {
            __createDataSource();
        }
        return ds__;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメインキー
     * @param context GS共通情報
     * @return javax.sql.DataSource
     * @throws Exception データソースの設定に失敗
     */
    public DataSource getDataSource(String dsKey, GSContext context) throws Exception {

        if (ds__ == null) {
            __createDataSource(context);
        }
        return ds__;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを再生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメインキー
     * @return データソース
     * @throws Exception データソースの設定に失敗
     */
    public DataSource resetDataSource(String dsKey) throws Exception {

        //データソースをクローズする
        if (ds__ != null) {
            ((BasicDataSource) ds__).close();
            ds__ = null;
        }

        __createDataSource();

        return ds__;
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController getCountController(RequestModel reqMdl)
                                             throws IOException, SAXException, Exception {

        if (dsCountController__ == null) {
            __createDataSourceCountController(reqMdl);
        }
        return dsCountController__;
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメインキー
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController getCountController(String dsKey)
                                             throws IOException, SAXException, Exception {

        if (dsCountController__ == null) {
            __createDataSourceCountController(dsKey);
        }
        return dsCountController__;
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメインキー
     * @param context GS共通情報
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController getCountController(String dsKey, GSContext context)
                                             throws IOException, SAXException, Exception {

        if (dsCountController__ == null) {
            __createDataSourceCountController(dsKey, context);
        }
        return dsCountController__;
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを再生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 採番コントローラ
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController resetCountController(String dsKey)
                                             throws IOException, SAXException, Exception {

        if (dsCountController__ == null) {
            __createDataSourceCountController(dsKey);
        }
        return dsCountController__;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @throws Exception データソースの取得に失敗
     */
    private void __createDataSource() throws Exception {
        __createDataSource(GroupSession.getContext());
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param context GS共通情報
     * @throws Exception データソースの取得に失敗
     */
    private void __createDataSource(GSContext context) throws Exception {

        try {
            String appPath = (String) context.get(GSContext.APP_ROOT_PATH);
            String filePath = __getDataSourcePath(appPath);
            String rootPath = (String) context.get(GSContext.APP_ROOT_PATH);
            IDbUtil dbUtil = DBUtilFactory.getInstance();

            DataSourceModel model = __createDataSourceModel(
                    GSConst.DATASOURCE_KEY, filePath, dbUtil, rootPath);
            ds__ = DataSourceUtil.createDataSource(model);
        } catch (IOException e) {
            log__.error("データソースの取得に失敗", e);
            throw new IOException("データソースの取得に失敗", e);
        } catch (SAXException e) {
            log__.error("データソースの取得に失敗", e);
            throw new IOException("データソースの取得に失敗", e);
        }
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番用データソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの取得に失敗
     */
    private static void __createDataSourceCountController(RequestModel reqMdl)
                                throws IOException, SAXException, Exception {

        try {
            String appPath = (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
            String filePath = __getDataSourcePath(appPath);
            String rootPath = (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
            IDbUtil dbUtil = DBUtilFactory.getInstance();

            DataSourceModel model = __createDataSourceModel(
                    GSConst.DATASOURCE_NUM_KEY, filePath, dbUtil, rootPath);

            MlCountMtController mlCnt = null;
            try {
                mlCnt = new MlCountMtController();
            } catch (Exception e) {
                log__.error("採番コントローラ取得失敗", e);
                throw new Exception("採番コントローラ取得失敗", e);
            }

                mlCnt.setDs(DataSourceUtil.createDataSource(model));
            dsCountController__ = mlCnt;
        } catch (IOException e) {
            log__.error("データソースの取得に失敗(採番用)", e);
            throw new IOException("データソースの取得に失敗(採番用)", e);
        } catch (SAXException e) {
            log__.error("データソースの取得に失敗(採番用)", e);
            throw new SAXException("データソースの取得に失敗(採番用)", e);
        }
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番用データソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     *  @param dsKey ドメイン
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの取得に失敗
     */
    private static void __createDataSourceCountController(String dsKey)
                                            throws IOException, SAXException, Exception {
        __createDataSourceCountController(dsKey, GroupSession.getContext());
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番用データソースを作成する。
     * <br>[解  説]
     * <br>[備  考]
     *  @param dsKey ドメイン
     *  @param context GS共通情報
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの取得に失敗
     */
    private static void __createDataSourceCountController(String dsKey, GSContext context)
                                            throws IOException, SAXException, Exception {

        try {
            String appPath = (String) context.get(GSContext.APP_ROOT_PATH);
            String filePath = __getDataSourcePath(appPath);
            String rootPath = (String) context.get(GSContext.APP_ROOT_PATH);
            IDbUtil dbUtil = DBUtilFactory.getInstance();

            DataSourceModel model = __createDataSourceModel(
                    GSConst.DATASOURCE_NUM_KEY, filePath, dbUtil, rootPath);

            MlCountMtController mlCnt = null;
            try {
                mlCnt = new MlCountMtController();
            } catch (Exception e) {
                log__.error("採番コントローラ取得失敗", e);
                throw new Exception("採番コントローラ取得失敗", e);
            }

                mlCnt.setDs(DataSourceUtil.createDataSource(model));
            dsCountController__ = mlCnt;
        } catch (IOException e) {
            log__.error("データソースの取得に失敗(採番用)", e);
            throw new IOException("データソースの取得に失敗(採番用)", e);
        } catch (SAXException e) {
            log__.error("データソースの取得に失敗(採番用)", e);
            throw new SAXException("データソースの取得に失敗(採番用)", e);
        }
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
    private static DataSourceModel __createDataSourceModel(String id,
            String filePath, IDbUtil dbUtil, String rootPath) throws IOException, SAXException {
        DataSourceSetting setting = GsDataSourceFactory.createBaseDataSourceSetting(filePath);
        log__.debug("データソースID==>" + id);
        DataSourceModel model = setting.getDataSourceModel(id);
        model.setUrl(dbUtil.createUrl(rootPath, model));
        return model;
    }

    /**
     * <br>[機  能] データソース設定ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのパス
     * @return データソース設定ファイルパス
     */
    private static String __getDataSourcePath(String rootPath) {

        //データソースの読込み
        String dsPath = null;
        //WEBアプリケーションのパス
        String prefix = IOTools.setEndPathChar(rootPath);
        dsPath = IOTools.replaceSlashFileSep(prefix
                + "WEB-INF/conf/dataSource.xml");
        return dsPath;
    }
}