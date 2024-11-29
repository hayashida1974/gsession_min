package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
/**
 *
 * <br>[機  能] デフォルトグループ選択 リスナインタフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IDefaultGroupSelectListner {
    public String answerDefaultGroup(RequestModel reqMdl,
            Connection con, ParameterObject paramModel,
            List<String> groupSidList) throws SQLException;
}
