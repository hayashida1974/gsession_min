package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

/**
 *
 * <br>[機  能] 選択済み 描画処理フィルタチェーン
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SelectDspInitFilterChain {
    /** イテレータ*/
    private final Iterator<? extends ISelectDspInitFilter> it__;

    /** 現在の選択済みモデルリスト*/
    private List<IChild> selectList__;

    /**
     *
     * コンストラクタ
     * @param filterList フィルタリスト
     */
    public SelectDspInitFilterChain(Collection<? extends ISelectDspInitFilter> filterList) {
        it__ = filterList.iterator();
    }

    /**
    *
    * <br>[機  能] フィルタチェーン実行
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl
    * @param con
    * @param paramModel パラメータモデル
    * @param selector 選択UIモデル
    * @param select 選択UI内入力チェック対象選択モデル
    * @param selectList 現在の選択済みモデルリスト
     * @throws SQLException
    */
   public void doFilterChain(
           RequestModel reqMdl,
           Connection con,
           ParameterObject paramModel,
           ISelector selector,
           Select select,
           List<? extends IChild> selectList
           ) throws SQLException {
       selectList__ = new ArrayList<>();
       if (selectList != null) {
           selectList__.addAll(selectList);
       }
       if (it__.hasNext()) {
           ISelectDspInitFilter filter = it__.next();
           filter.doFilter(this,
                   reqMdl,
                   con,
                   paramModel,
                   selector,
                   select,
                   selectList__
                   );
           return;
       }
   }
   /**
    * <p>selectList を取得します。
    * @return selectList
    * @see jp.groupsession.v2.cmn.ui.parts.select.SelectDspInitFilterChain#selectList__
    */
   public List<? extends IChild> getSelectList() {
       return selectList__;
   }


}
