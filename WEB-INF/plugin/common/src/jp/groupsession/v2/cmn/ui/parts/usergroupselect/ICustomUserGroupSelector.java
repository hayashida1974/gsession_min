package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

public interface ICustomUserGroupSelector {
    /**
     *
     * <br>[機  能] グループ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException;
    /**
     *
     * <br>[機  能] ユーザ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException;
}
