package jp.co.sjts.util.sort;

/**
 * <br>[機  能] ソート対象を定義するインターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IListSortable {
    /**
     * <br>[機  能] ソートのキーとなるオブジェクトを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートのキーとなるオブジェクト
     */
    public Object getSortKey();
}
