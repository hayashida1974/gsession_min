package jp.groupsession.v2.cmn.jdbc;

import javax.sql.DataSource;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] データソース、採番コントローラ処理インターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IGsDataSourceManager {

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
     * <br>[機  能] GroupSessionで使用する採番コントローラを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ取得時に例外発生
     */
    public MlCountMtController getCountController(String dsKey) throws Exception;

    /**
     * <br>[機  能] GroupSessionで使用する採番コントローラを再生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dsKey ドメイン
     * @return 採番コントローラ
     * @throws Exception 採番コントローラ再生成に例外発生
     */
    public MlCountMtController resetCountController(String dsKey) throws Exception;
}
