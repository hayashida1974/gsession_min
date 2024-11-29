package jp.groupsession.v2.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.websocket.server.HandshakeRequest;

import jp.groupsession.v2.cmn.config.LoggingConfig;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] リソースインターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IGsResourceManager {

    /**
     * <br>[機  能] テンポラリディレクトリを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     */
    public void setInitData(String tempDir);

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return データソース
     * @throws Exception データソース取得時に例外発生
     */
    public DataSource getDataSource(RequestModel reqMdl) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return データソース
     * @throws Exception データソース取得時に例外発生
     */
    public DataSource getDataSource(String dsKey) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param context GS共通情報
     * @return データソース
     * @throws Exception データソース取得時に例外発生
     */
    public DataSource getDataSource(String dsKey, GSContext context) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用するデータソースを再生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return データソース
     * @throws Exception データソースを再生成時に例外発生
     */
    public DataSource resetDataSource(String dsKey) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ取得時に例外発生
     */
    public MlCountMtController getCountController(RequestModel reqMdl) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ取得時に例外発生
     */
    public MlCountMtController getCountController(String dsKey) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する(ドメイン指定)。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param context GS共通情報
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ取得時に例外発生
     */
    public MlCountMtController getCountController(String dsKey, GSContext context) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを再生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ再生成に例外発生
     */
    public MlCountMtController resetCountController(String dsKey) throws Exception;

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     */
    public String getDomain(HttpServletRequest req);
    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     */
    public String getDomain(HandshakeRequest req);
    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @return ドメイン
     * @throws Exception ドメイン取得時に例外発生
     */
    public String[] getDomain() throws Exception;

    /**
     * <br>[機  能] GroupSessionを利用するドメインを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return ドメイン
     * @throws Exception ドメイン取得時に例外発生
     */
    public String getDomainCheck(HttpServletRequest req) throws Exception;

    /**
     * <br>[機  能] アクセス可能なドメインかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return true:許可する,false:許可しない
     * @throws Exception ドメイン判定時に例外発生
     */
    public boolean canAccessDomain(HttpServletRequest req) throws Exception;

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return String テンポラリディレクトリのパス
     */
    public String getTempPath(HttpServletRequest req);

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return String テンポラリディレクトリのパス
     */
    public String getTempPath(String domain);

    /**
     * <br>[機  能] プラグインコンフィグを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param pconfig プラグインコンフィグ
     */
    public void setPluginConfig(String domain, PluginConfig pconfig);

    /**
     * <br>[機  能] プラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return プラグインコンフィグ
     */
    public PluginConfig getPluginConfig(String domain);

    /**
     * <br>[機  能] プラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return プラグインコンフィグ
     */
    public PluginConfig getPluginConfig(RequestModel reqMdl);

    /**
     * <br>[機  能] ログ設定を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param loggingConfig ログ設定
     */
    public void setLoggingConfig(String domain, LoggingConfig loggingConfig);

    /**
     * <br>[機  能] ログ設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return ログ設定
     */
    public LoggingConfig getLoggingConfig(String domain);

    /**
     * <br>[機  能] ライセンスファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file
     * <br>[備  考]
     *
     * @param savePath 保存先パス
     * @param domain ドメイン
     * @return 添付ファイル保存用のパス
     */
    public String getSaveLicensePath(String savePath, String domain);

    /**
     * <br>[機  能] ライセンスデータを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param domain ドメイン
     * @param licenseMdl ライセンス情報
     */
    public void updateLicense(String domain, Object licenseMdl);

    /**
     * <br>[機  能] ライセンスデータを取り込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param domain ドメイン
     * @return Object ライセンスデータ
     */
    public Object getLicenseMdl(String domain);

    /**
     * <br>[機  能] ドメインの追加削除
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @param changeKbn 区分
     * @throws Exception ドメイン操作失敗
     */
    public void doDomain(String dsKey, int changeKbn) throws Exception;

    /**
     * <br>[機  能] ユーザの登録可能数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return int 登録可能数
     */
    public int getUserCountLimit(String domain);
    /**
     * <br>[機  能] ユーザの登録可能数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return int 登録可能数
     */
    public int getUserCountLimit(RequestModel reqMdl);
    /**
     * <br>[機  能] DB使用可能量を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 使用量
     * @throws Exception DB使用可能量取得時に例外発生
     */
    public String getDbCanUse(String domain) throws Exception;

    /**
     * <br>[機  能] データ使用量を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return データ使用量(MB)
     * @throws Exception データ使用量取得時に例外発生
     */
    public String getDbUse(String domain) throws Exception;
    /**
     *
     * <br>[機  能] 次回ドメインチェック用のドメイン情報を保管する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     */
    public void saveAccessDomain(HttpServletRequest req);
    /**
     *
     * <br>[機  能] データ使用量の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @param dataSize データ使用量(MB)
     * @throws Exception データ使用量の更新に失敗
     */
    public void updateDbUse(String domain, int dataSize) throws Exception;
    /**
     * <br>[機  能] リダイレクトURLを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return リダイレクトURL
     */
    public String getRedirectUrl(HttpServletRequest req);
}