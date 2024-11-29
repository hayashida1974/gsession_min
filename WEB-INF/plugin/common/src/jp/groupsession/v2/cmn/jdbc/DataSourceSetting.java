package jp.groupsession.v2.cmn.jdbc;

import java.util.HashMap;
import java.util.Map;

import jp.co.sjts.util.jdbc.DataSourceModel;

/**
 * <br>[機  能] データソースの管理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DataSourceSetting {
    /** データソース情報保持用Map */
    private Map<String, DataSourceModel> dataSourceMap__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public DataSourceSetting() {
        dataSourceMap__ = new HashMap<String, DataSourceModel>();
    }
    /**
     * <p>dataSourceMap を取得します。
     * @return dataSourceMap
     */
    public Map<String, DataSourceModel> getDataSourceMap() {
        return dataSourceMap__;
    }

    /**
     * <p>dataSourceMap をセットします。
     * @param dataSourceMap dataSourceMap
     */
    public void setDataSourceMap(Map<String, DataSourceModel> dataSourceMap) {
        dataSourceMap__ = dataSourceMap;
    }

    /**
     * <br>[機  能] データソース情報を追加する。
     * <br>[解  説]
     * <br>[備  考]
     * @param model データソース情報
     */
    public void addDatasource(DataSourceModel model) {
        dataSourceMap__.put(model.getId(), model);
    }

    /**
     * <br>[機  能] データソースIDを指定し、DataSourceModelを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param id データソースID
     * @return 引数idに対応したDataSourceModel。存在しない場合はnullを返す
     */
    public DataSourceModel getDataSourceModel(String id) {
        //
        return dataSourceMap__.get(id);
    }
}
