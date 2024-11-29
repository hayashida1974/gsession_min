package jp.groupsession.v2.cmn.dao.base;

import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.model.base.IApiConfModel;
/**
 *
 * <br>[機  能] API基本設定DAO インタフェース定義
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IApiConfDao {

    /**
     * <p>Insert API_CONF Data Bindding JavaBean
     * @param bean API_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    void insert(IApiConfModel bean) throws SQLException;

    /**
     * <p>Update API_CONF Data Bindding JavaBean
     * @param bean API_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    int update(IApiConfModel bean) throws SQLException;

    /**
     * <p>Select API_CONF All Data
     * @return List in API_CONFModel
     * @throws SQLException SQL実行例外
     */
    List<IApiConfModel> select() throws SQLException;

}