package jp.groupsession.v2.cmn;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.websocket.server.HandshakeRequest;

import org.xml.sax.SAXException;

import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.config.LoggingConfig;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceManager;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] GroupSessionの各種リソースを取得する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSResourceManager implements IGsResourceManager {

    /** プラグイン情報 */
    private static PluginConfig pluginConfig__ = null;

    /** ライセンス情報 */
    private static Object licenseMdl__ = null;

    /** ログ設定 */
    private static LoggingConfig loggingConfig__ = null;

    /** テンポラリディレクトリ */
    static String tempDir__ = null;

    /**
     * <br>[機  能] テンポラリディレクトリを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     */
    public void setInitData(String tempDir) {
        tempDir__ = tempDir;
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return javax.sql.DataSource
     * @throws Exception データソースの取得に失敗
     */
    public DataSource getDataSource(RequestModel reqMdl) throws Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getDataSource(reqMdl);
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return javax.sql.DataSource
     * @throws Exception データソースの設定に失敗
     */
    public DataSource getDataSource(String dsKey) throws Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getDataSource(dsKey);
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param context GS共通情報
     * @return javax.sql.DataSource
     * @throws Exception データソースの設定に失敗
     */
    public DataSource getDataSource(String dsKey, GSContext context) throws Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getDataSource(dsKey, context);
    }

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを再生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return javax.sql.DataSource
     * @throws Exception データソースの設定に失敗
     */
    public DataSource resetDataSource(String dsKey) throws Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.resetDataSource(dsKey);
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
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getCountController(reqMdl);
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController getCountController(String dsKey)
            throws IOException, SAXException, Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getCountController(dsKey);
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param context GS共通情報
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController getCountController(String dsKey, GSContext context)
            throws IOException, SAXException, Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.getCountController(dsKey, context);
    }

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを再生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return MlCountMtController
     * @throws IOException IOエラー
     * @throws SAXException XML構文エラー
     * @throws Exception 採番コントローラの設定に失敗
     */
    public MlCountMtController resetCountController(String dsKey)
            throws IOException, SAXException, Exception {
        GsDataSourceManager gsDataSource = new GsDataSourceManager();
        return gsDataSource.resetCountController(dsKey);
    }

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     */
    public String getDomain(HttpServletRequest req) {
        String domain = GSConst.GS_DOMAIN;
        return domain;
    }

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     */
    public String getDomain(HandshakeRequest req) {
        String domain = GSConst.GS_DOMAIN;
        return domain;
    }

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     * @throws Exception ドメイン取得時に例外発生
     */
    public String getDomainCheck(HttpServletRequest req) throws Exception {
        String domain = GSConst.GS_DOMAIN;
        return domain;
    }

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @return ドメイン
     * @throws Exception ドメイン取得時に例外発生
     */
    public String[] getDomain() throws Exception {
        String[] domain = {GSConst.GS_DOMAIN};
        return domain;
    }

    /**
     * <br>[機  能] アクセス可能なドメインかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return true:許可する,false:許可しない
     * @throws Exception ドメイン判定時に例外発生
     */
    public boolean canAccessDomain(HttpServletRequest req) throws Exception {
        return true;
    }

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return String テンポラリディレクトリのパス
     */
    public String getTempPath(HttpServletRequest req) {
        return tempDir__;
    }

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return String テンポラリディレクトリのパス
     */
    public String getTempPath(String domain) {
        return tempDir__;
    }
    /**
     * <br>[機  能] プラグインコンフィグを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param pconfig プラグインコンフィグ
     */
    public void setPluginConfig(String domain, PluginConfig pconfig) {
        pluginConfig__ = pconfig;
    }

    /**
     * <br>[機  能] プラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return プラグインコンフィグ
     */
    public PluginConfig getPluginConfig(String domain) {
        return pluginConfig__;
    }

    /**
     * <br>[機  能] プラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return プラグインコンフィグ
     */
    public PluginConfig getPluginConfig(RequestModel reqMdl) {
        return pluginConfig__;
    }

    /**
     * <br>[機  能] ログ設定を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param loggingConfig ログ設定
     */
    public void setLoggingConfig(String domain, LoggingConfig loggingConfig) {
        loggingConfig__ = loggingConfig;
    }

    /**
     * <br>[機  能] ログ設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return ログ設定
     */
    public LoggingConfig getLoggingConfig(String domain) {
        return loggingConfig__;
    }

    /**
     * <br>[機  能] ライセンスファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file
     * <br>[備  考]
     *
     * @param savePath 保存先パス
     * @param domain ドメイン
     * @return 添付ファイル保存用のパス
     */
    public String getSaveLicensePath(String savePath, String domain) {
        return savePath;
    }

    /**
     * <br>[機  能] ライセンスデータを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param domain ドメイン
     * @param licenseMdl ライセンス情報
     */
    public void updateLicense(String domain, Object licenseMdl) {
        licenseMdl__ = licenseMdl;
    }

    /**
     * <br>[機  能] ライセンスデータを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param domain ドメイン
     * @return Object ライセンスデータ
     */
    public Object getLicenseMdl(String domain) {
        return licenseMdl__;
    }

    /**
     * <br>[機  能] ドメインの追加削除
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param changeKbn 区分
     * @throws Exception ドメイン操作失敗
     */
    public void doDomain(String dsKey, int changeKbn) throws Exception {
    }

    /**
     * <br>[機  能] ユーザの登録可能数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return int 登録可能数
     */
    public int getUserCountLimit(String domain) {
        int userLimit = 0;

        String strUserLimit = GsResourceBundle.getString(GSConst.USER_COUNT_LIMIT);
        if (strUserLimit != null && ValidateUtil.isNumber(strUserLimit)) {
            userLimit = Integer.parseInt(strUserLimit);
        }

        return userLimit;
    }

    /**
     * <br>[機  能] ユーザの登録可能数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return int 登録可能数
     */
    public int getUserCountLimit(RequestModel reqMdl) {
        return getUserCountLimit(reqMdl.getDomain());
    }

    /**
     * <br>[機  能] DB使用量を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return int
     */
    public String getDbCanUse(String domain) {
        return "";
    }

    /**
     * <br>[機  能] DB使用量を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return int
     */
    public String getDbUse(String domain) {
        return "";
    }

    /**
     * <br>[機  能] リダイレクトURLを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return リダイレクトURL
     */
    public String getRedirectUrl(HttpServletRequest req) {
        return new String(req.getRequestURL());
    }

    @Override
    public void saveAccessDomain(HttpServletRequest req) {

    }

    @Override
    public void updateDbUse(String domain, int dataSize) throws Exception {

    }

}