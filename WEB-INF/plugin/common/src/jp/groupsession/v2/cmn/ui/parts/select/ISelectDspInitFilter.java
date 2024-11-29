package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

public interface ISelectDspInitFilter {

    /**
        *
        * <br>[機  能] 選択済み 描画処理 フィルタ実行を行う
        * <br>[解  説]
        * <br>[備  考] 実装の最後でchain.doFilterを実行すること
        * @param chain
        * @param reqMdl
        * @param con
        * @param paramModel パラメータモデル
        * @param selector 選択UIモデル
        * @param select 選択UI 選択先モデル
        * @param selectList 現在の選択済みモデルリスト
        * @throws SQLException SQL実行時例外
        *
        */
    void doFilter(SelectDspInitFilterChain chain,
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel,
            ISelector selector,
            Select select,
            List<? extends IChild> selectList
            ) throws SQLException;

}