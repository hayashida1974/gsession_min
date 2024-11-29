package jp.groupsession.v2.cmn.biz;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 並び替え共通化ビジネスロジック
 * <br>[解  説] 以下の処理について共通化する
 * <br> ・並び替えは選択要素とDB上で隣接する要素のソート値を交換する
 * <br> ・同値の間での並び替えが発生する時は一覧に対しソート値を連番で置き換える
 * <br> ・更新対象の要素はイニシャライズキー順に更新が実行される
 * <br>[備  考] ビルダークラスを使用しインスタンス化する
 * <br> ビルダークラスの公開ゲッターに必要なラムダ式をすべてセットすること
 *
 * @author JTS
 * @param <K>
 */
public class SortChangeBiz<E> {

    /**
     * <br>[機  能] モデル一覧取得関数インタフェース
     * <br>[解  説] 引数：なし
     * <br> 返り値：List<ソート対象モデル>
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface Selector<E> {
        List<E> select() throws SQLException;
    }

    /**
     * <br>[機  能] 選択中要素判定 関数インタフェース
     * <br>[解  説] 引数：ソート対象モデル
     * <br> 返り値：boolean
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface SelectChecker<E> {
        boolean isSelected(E m);
    }

    /**
     * <br>[機  能] ソート順設定値取得 関数インタフェース
     * <br>[解  説] 引数：ソート対象モデル
     * <br> 返り値：int
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface SortGetter<E> {
        int getSort(E m);
    }

    /**
     * <br>[機  能] 更新実行順判定 関数インタフェース
     * <br>[解  説] 引数：ソート対象モデル, 入れ替え先対象モデル 
     * <br> 返り値：int
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface OrderChecker<E> extends Comparator<E> {
        @Override
        int compare(E m1, E m2);
    }

    /**
     * <br>[機  能] 並び替え更新実行 関数インタフェース
     * <br>[解  説] 引数：ソート対象モデル，新規ソート順
     * <br> 返り値：なし
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface SortChanger<E> {
        void sortChange(E m, int s) throws SQLException;
    }
    
    /**
     * <br>[機  能] 条件なしモデル一覧取得関数インタフェース
     * <br>[解  説] 引数：なし
     * <br> 返り値：List<ソート対象モデル>
     * <br>[備  考] 
     */
    @FunctionalInterface
    public interface AllSelector<E> {
        List<E> select() throws SQLException;
    }

    /** ソート対象モデル一覧取得 ラムダ関数 */
    private Selector<E> funcTargetList__;
    /** 選択中要素判定 ラムダ関数 */
    private SelectChecker<E> funcIsSelected__;
    /** ソート順設定値取得 ラムダ関数 */
    private SortGetter<E> funcGetOrderNo__;
    /** 更新実行順判定 ラムダ関数 */
    private OrderChecker<E> funcExeComparater__;
    /** 並び替え更新実行 ラムダ関数 */
    private SortChanger<E> funcUpdateSort__;
    /** 条件なしソート対象モデル一覧取得 ラムダ関数 */
    private AllSelector<E> funcTargetListAll__ = null;
    /** ソート対象モデル一覧 */
    private List<E> mdlList__ = null;
    /** 選択対象モデル */
    private E selectedMdl__ = null;
    /** 入れ替え先対象モデル */
    private E targetMdl__ = null;
    /** ソート順変更用モデル保持マップ */
    Map<E, Integer> sortMap__;

    /**
     * コンストラクタ
     */
    private SortChangeBiz() {
    }

    /**
     * <br>[機  能] ビルダークラス
     * <br>[解  説] ビルダークラスとプライベートコンストラクタによって不用意な継承による悪用を防ぐ
     * <br>[備  考]
     * @param <E> ソート対象モデル 
     */
    public static class Builder<E> {
        /** ソート対象モデル一覧取得 ラムダ関数 */
        private Selector<E> funcTargetList__;
        /** 並び替え更新実行 ラムダ関数 */
        private SortChanger<E> funcUpdateSort__;
        /** ソート順設定値取得 ラムダ関数 */
        private SortGetter<E> funcGetOrderNo__;
        /** 更新実行順判定 ラムダ関数 */
        private OrderChecker<E> funcExeComparater__;
        /** 選択中要素判定 ラムダ関数 */
        private SelectChecker<E> funcIsSelected__;

        /**
         * <br>[機  能] ソート対象モデル一覧取得 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■ソート対象モデル一覧取得 ラムダ関数
         * <br>  引数無し
         * <br>  返り値：List<ソート対象モデル>
         * <br>   一覧表示で使用しているリストを返す
         * @param funcTargetList ソート対象モデル一覧取得 ラムダ関数
         * @return builder 
         */
        public Builder<E> setFuncTargetList(Selector<E> funcTargetList) {
            funcTargetList__ = funcTargetList;
            return this;
        }

        /**
         * <br>[機  能] 選択中要素判定 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■選択中要素判定 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素
         * <br>  返り値：引数の要素がパラメータで選択された要素かを判定して返す
         * @param funcIsSelected 並び替え更新実行 ラムダ関数
         * @return builder
         */
        public Builder<E> setFuncIsSelected(SelectChecker<E> funcIsSelected) {
            funcIsSelected__ = funcIsSelected;
            return this;
        }

        /**
         * <br>[機  能] ソート順設定値取得 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■ソート順設定値取得 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素
         * <br>  返り値：引数の要素から現在のソート順を取得し返す
         * @param funcGetOrderNo ソート順設定値取得 ラムダ関数
         * @return builder
         */
        public Builder<E> setFuncGetOrderNo(SortGetter<E> funcGetOrderNo) {
            funcGetOrderNo__ = funcGetOrderNo;
            return this;
        }

        /**
         * <br>[機  能] 更新実行順判定 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■更新実行順判定 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素1,ソート対象モデル一覧内の要素2 
         * <br>  返り値：要素1が変更対象テーブルの主キーで判断した場合に小さければ-1
         * <br>          要素1が変更対象テーブルの主キーで判断した場合に大きければ1
         * <br>          要素1が変更対象テーブルの主キーで判断した場合に同じであれば0を返す
         * @param funcExeComparater 更新実行順判定 ラムダ関数
         * @return builder
         */
        public Builder<E> setFuncExeComparater(OrderChecker<E> funcExeComparater) {
            funcExeComparater__ = funcExeComparater;
            return this;
        }

        /**
         * <br>[機  能] 並び替え更新実行 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■並び替え更新実行 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素、更新後のソート順
         * <br>  返り値：なし
         * <br>   引数のモデルとソート順で並び順の更新を実行する
         * @param funcUpdateSort 並び替え更新実行 ラムダ関数
         * @return builder
         */
        public Builder<E> setFuncUpdateSort(SortChanger<E> funcUpdateSort) {
            funcUpdateSort__ = funcUpdateSort;
            return this;
        }

        /**
         *
         * <br>[機  能] 並び替え共通化ビジネスロジックインスタンスを生成
         * <br>[解  説]
         * <br>[備  考]
         * @return 並び替え共通化ビジネスロジックインスタンス
         */
        public SortChangeBiz<E> build() {
            SortChangeBiz<E> ret = new SortChangeBiz<E>();
            ret.funcTargetList__ = funcTargetList__;
            ret.funcIsSelected__ = funcIsSelected__;
            ret.funcGetOrderNo__ = funcGetOrderNo__;
            ret.funcExeComparater__ = funcExeComparater__;
            ret.funcUpdateSort__ = funcUpdateSort__;
            return ret;
        }
    }
    
    /**
     * <br>[機  能] 対象モデル全件取得用ビルダークラス
     * <br>[解  説] ビルダークラスとプライベートコンストラクタによって不用意な継承による悪用を防ぐ
     * <br>[備  考]
     * @param <E> ソート対象モデル 
     */
    public static class SearchedSortChangeBuilder<E> extends Builder<E> {
        /** 条件なしソート対象モデル一覧取得 ラムダ関数 */
        private AllSelector<E> funcTargetListAll__ = null;
        
        /**
         * <br>[機  能] ソート対象モデル一覧取得 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■ソート対象モデル一覧取得 ラムダ関数
         * <br>  引数無し
         * <br>  返り値：List<ソート対象モデル>
         * <br>   一覧表示で使用しているリストを返す
         * @param funcTargetList ソート対象モデル一覧取得 ラムダ関数
         * @return builder 
         */
        public SearchedSortChangeBuilder<E> setFuncTargetListAll(AllSelector<E> funcTargetListAll) {
            funcTargetListAll__ = funcTargetListAll;
            return this;
        }
        
        /**
         * <br>[機  能] ソート対象モデル一覧取得 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■ソート対象モデル一覧取得 ラムダ関数
         * <br>  引数無し
         * <br>  返り値：List<ソート対象モデル>
         * <br>   一覧表示で使用しているリストを返す
         * @param funcTargetList ソート対象モデル一覧取得 ラムダ関数
         * @return builder 
         */
        public SearchedSortChangeBuilder<E> setFuncTargetList(Selector<E> funcTargetList) {
            super.funcTargetList__ = funcTargetList;
            return this;
        }

        /**
         * <br>[機  能] 選択中要素判定 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■選択中要素判定 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素
         * <br>  返り値：引数の要素がパラメータで選択された要素かを判定して返す
         * @param funcIsSelected 並び替え更新実行 ラムダ関数
         * @return builder
         */
        public SearchedSortChangeBuilder<E> setFuncIsSelected(SelectChecker<E> funcIsSelected) {
            super.funcIsSelected__ = funcIsSelected;
            return this;
        }

        /**
         * <br>[機  能] ソート順設定値取得 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■ソート順設定値取得 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素
         * <br>  返り値：引数の要素から現在のソート順を取得し返す
         * @param funcGetOrderNo ソート順設定値取得 ラムダ関数
         * @return builder
         */
        public SearchedSortChangeBuilder<E> setFuncGetOrderNo(SortGetter<E> funcGetOrderNo) {
            super.funcGetOrderNo__ = funcGetOrderNo;
            return this;
        }

        /**
         * <br>[機  能] 更新実行順判定 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■更新実行順判定 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素1,ソート対象モデル一覧内の要素2 
         * <br>  返り値：要素1が変更対象テーブルの主キーで判断した場合に小さければ-1
         * <br>          要素1が変更対象テーブルの主キーで判断した場合に大きければ1
         * <br>          要素1が変更対象テーブルの主キーで判断した場合に同じであれば0を返す
         * @param funcExeComparater 更新実行順判定 ラムダ関数
         * @return builder
         */
        public SearchedSortChangeBuilder<E> setFuncExeComparater(OrderChecker<E> funcExeComparater) {
            super.funcExeComparater__ = funcExeComparater;
            return this;
        }

        /**
         * <br>[機  能] 並び替え更新実行 ラムダ関数をセットする
         * <br>[解  説]
         * <br>[備  考]
         * <br> ■並び替え更新実行 ラムダ関数
         * <br>  引数：ソート対象モデル一覧内の要素、更新後のソート順
         * <br>  返り値：なし
         * <br>   引数のモデルとソート順で並び順の更新を実行する
         * @param funcUpdateSort 並び替え更新実行 ラムダ関数
         * @return builder
         */
        public SearchedSortChangeBuilder<E> setFuncUpdateSort(SortChanger<E> funcUpdateSort) {
            super.funcUpdateSort__ = funcUpdateSort;
            return this;
        }
        
        /**
         *
         * <br>[機  能] 並び替え共通化ビジネスロジックインスタンスを生成
         * <br>[解  説]
         * <br>[備  考]
         * @return 並び替え共通化ビジネスロジックインスタンス
         */
        public SortChangeBiz<E> build() {
            SortChangeBiz<E> ret = new SortChangeBiz<E>();
            ret = super.build();
            ret.funcTargetListAll__ = funcTargetListAll__;
            return ret;
        }
    }
    
    /**
     * <br>[機  能] ソート順変更情報を保持するクラス
     * <br>[解  説] 
     * <br>[備  考]
     * @param <E> ソート対象モデル 
     */
    public static class SortResult<E> {
        /** ソート対象モデル */
        private E mdl__;
        /** 変更前ソート順 */
        private int beforeSort;
        /** 変更後ソート順 */
        private int afterSort;
        /** 変更前表示順 */
        private int beforeDispOrder;
        /** 変更後表示順 */
        private int afterDispOrder;
        
        /**
         * <p>mdl を取得します。
         * @return mdl
         * @see jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult#mdl__
         */
        public E getMdl() {
            return mdl__;
        }
        /**
         * <p>beforeSort を取得します。
         * @return beforeSort
         * @see jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult#beforeSort
         */
        public int getBeforeSort() {
            return beforeSort;
        }
        /**
         * <p>afterSort を取得します。
         * @return afterSort
         * @see jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult#afterSort
         */
        public int getAfterSort() {
            return afterSort;
        }
        /**
         * <p>beforeDispOrder を取得します。
         * @return beforeDispOrder
         * @see jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult#beforeDispOrder
         */
        public int getBeforeDispOrder() {
            return beforeDispOrder;
        }
        /**
         * <p>afterDispOrder を取得します。
         * @return afterDispOrder
         * @see jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult#afterDispOrder
         */
        public int getAfterDispOrder() {
            return afterDispOrder;
        }
        
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param <E> ソート対象モデル
     *
     * @return ビルダークラス
     */
    public static <E> Builder<E> builder() {
        return new Builder<E>();
    }
    
    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param <E> ソート対象モデル
     *
     * @return ビルダークラス
     */
    public static <E> SearchedSortChangeBuilder<E> searchedSortChangeBuilder() {
        return new SearchedSortChangeBuilder<E>();
    }
    
    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param <E> ソート対象モデル
     *
     * @return ビルダークラス
     */
    private static <E> SortResult<E> builder(
            E m, int beforeSort, int afterSort, int beforeDispOrder, int afterDispOrder) {
        SortResult<E> ret = new SortResult<E>();
        ret.mdl__ = m;
        ret.beforeSort = beforeSort;
        ret.afterSort = afterSort;
        ret.beforeDispOrder = beforeDispOrder;
        ret.afterDispOrder = afterDispOrder;
        return ret;
    }
    
    /**
     *
     * <br>[機  能] 上へ移動する
     * <br>[解  説]
     * <br>[備  考]
     * @return SortResult ソート順変更情報保持モデル
     * @throws SQLException
     */
    public SortResult<E> up() throws SQLException {
        
        return __doSortChange(GSConst.SORT_UP);
    }

    /**
     * <br>[機  能] 下へ移動を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @return SortResult ソート順変更情報保持モデル
     * @throws SQLException
     */
    public SortResult<E> down() throws SQLException {
        
        return __doSortChange(GSConst.SORT_DOWN);
    }

    /**
     *
     * <br>[機  能] 移動を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @return SortResult ソート順変更情報保持モデル
     * @throws SQLException
     */
    public SortResult<E> __doSortChange(int changeKbn) throws SQLException {
        SortResult<E> ret = new SortResult<E>();

        __setModels(changeKbn);
        
        if (selectedMdl__ == null || targetMdl__ == null) {
            return null;
        }
        int selectedSort = funcGetOrderNo__.getSort(selectedMdl__);
        int targetSort = funcGetOrderNo__.getSort(targetMdl__);
        
        sortMap__ = new TreeMap<>(funcExeComparater__);
        if (selectedSort == targetSort) {
            //条件なし全件取得メソッドがある場合は、ソート順1からの連番に更新する
            if (funcTargetListAll__ != null) {
                mdlList__ = funcTargetListAll__.select();
            }
            __updateCorrectSort();
            __setModels(changeKbn);

            if (selectedMdl__ == null || targetMdl__ == null) {
                return null;
            }
            selectedSort = sortMap__.get(selectedMdl__);
            targetSort = sortMap__.get(targetMdl__);
        }
        //マップを初期化し、変更するモデルを設定
        sortMap__ = new TreeMap<>(funcExeComparater__);
        sortMap__.put(selectedMdl__, targetSort);
        sortMap__.put(targetMdl__, selectedSort);
        
        int afterDispOrder;
        if (changeKbn == GSConst.SORT_UP) {
            afterDispOrder = mdlList__.indexOf(selectedMdl__) - 1;
        } else {
            afterDispOrder = mdlList__.indexOf(selectedMdl__) + 1;
        }
        ret = builder(selectedMdl__, selectedSort, targetSort,
                mdlList__.indexOf(selectedMdl__), afterDispOrder);
        
        for (Map.Entry<E, Integer> map : sortMap__.entrySet()) {
            funcUpdateSort__.sortChange(map.getKey(), map.getValue());
        }
        
        return ret;
    }
    
    /**
     *
     * <br>[機  能] 選択要素、ターゲット要素設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __setModels(int changeFlg) throws SQLException {
        mdlList__ = funcTargetList__.select();
        if (mdlList__ == null || mdlList__.isEmpty()) {
            return;
        }
        
        for (int idx = 0; idx < mdlList__.size(); idx++) {
            if (funcIsSelected__.isSelected(mdlList__.get(idx))) {
                selectedMdl__ = mdlList__.get(idx);
                if (idx != 0 && changeFlg == GSConst.SORT_UP) {
                    targetMdl__ = mdlList__.get(idx - 1);    
                } else if (idx != mdlList__.size() -1  && changeFlg == GSConst.SORT_DOWN) {
                    targetMdl__ = mdlList__.get(idx + 1);    
                }
            }
        }
    }
    
    /**
     *
     * <br>[機  能] ソート順再設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException
     */
    private void __updateCorrectSort() throws SQLException {
        sortMap__ = new TreeMap<>(funcExeComparater__);
        int saiban = 1;
        for (E mdl : mdlList__) {
            sortMap__.put(mdl, saiban);
            saiban++;
        }
        for (Map.Entry<E, Integer> map : sortMap__.entrySet()) {
            funcUpdateSort__.sortChange(map.getKey(), map.getValue());
        }
    }    
}
