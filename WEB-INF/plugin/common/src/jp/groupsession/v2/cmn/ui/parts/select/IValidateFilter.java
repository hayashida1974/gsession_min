package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

public interface IValidateFilter {
    /**
    *
    * <br>[機  能] 入力チェック動作 フィルタ実行を行う
    * <br>[解  説]
    * <br>[備  考] 入力チェック例外はchain.addException()で追記していく
    * <br>         実装の最後でchain.doFilterを実行すること
    * @param chain
    * @param reqMdl
    * @param con
    * @param paramModel パラメータモデル
    * @param selector 選択UIモデル
    * @param select 選択UI内入力チェック対象選択モデル
    * @throws SQL実行時例外
    */
   public void doFilter(
           ValidateFilterChain chain,
           RequestModel reqMdl,
           Connection con,
           ParameterObject paramModel,
           ISelector selector,
           Select select
           ) throws SQLException;
}