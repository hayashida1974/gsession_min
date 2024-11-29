package jp.groupsession.v2.cmn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.DataSourceModel;


/**
 * <br>[機  能] DataBaseの起動、終了を行う抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IDbUtil {

    /**
     * <br>[機  能] DBサーバを起動する前に行う事前処理
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @throws Exception 不明な例外
     */
    public void init(String rootPath) throws Exception;

    /**
     * <br>[機  能] DBサーバを起動する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @throws Exception 不明な例外
     */
    public void startDbServer(String rootPath) throws Exception;

    /**
     * <br>[機  能] DBサーバを終了する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @param con DB Connection
     * @throws Exception 不明な例外
     */
    public void shutdownDbServer(String rootPath, Connection con) throws Exception;

    /**
     * <br>[機  能] データベース設定の再読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws Exception 再読み込みに失敗
     */
    public void restartDbServer(String rootPath) throws SQLException, Exception;

    /**
     * <br>[機  能] DB接続文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param rootPath アプリケーションのルートパス
     * @param dsModel DataSourceModel
     * @return db url
     */
    public String createUrl(String rootPath, DataSourceModel dsModel);

    /**
     * <br>[機  能] デフォルトエスケープ文字列を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return db url
     */
    public String defaultEscape();
    
    
    /**
     * <br>[機  能] like検索時のデフォルトエスケープ文字列(置換前)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return デフォルトエスケープ文字列
     */
    public String defaultLikeEscape();
    
    /**
     * <br>[機  能] like検索時のデフォルトエスケープ文字列(置換後)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return デフォルトエスケープ文字列
     */
    public String defaultLikeEscapeAfter();

    /**
     * <br>[機  能] DBの種類を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return DBの種類(0:H2 Database, 1:PostgreSQL など)
     */
    public abstract int getDbType();

}
