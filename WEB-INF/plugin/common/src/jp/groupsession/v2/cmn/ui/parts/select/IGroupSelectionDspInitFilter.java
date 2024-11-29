package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

public interface IGroupSelectionDspInitFilter {

    /**
       *
       * <br>[機  能] 選択元 描画処理 フィルタ実行を行う
       * <br>[解  説]
       * <br>[備  考] 実装の最後でchain.doFilterを実行すること
       * @param chain
       * @param reqMdl
       * @param con
       * @param paramModel パラメータモデル
       * @param selector 選択UIモデル
       * @param initModel フィルタパラメータ
       * @throws SQL実行時例外
       *
       */
    void doFilter(GroupSelectionDspInitFilterChain chain,
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel,
            ISelector selector,
            GroupSelectionDspInitModel initModel
            ) throws SQLException;

}