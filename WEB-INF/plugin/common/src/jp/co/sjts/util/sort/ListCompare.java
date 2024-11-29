package jp.co.sjts.util.sort;

import java.util.Comparator;
import java.util.List;

/**
 * <br>[機  能] java.util.Listの比較を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@SuppressWarnings("all")
public class ListCompare implements Comparator<List> {

    /** */
    private boolean order__ = true;
    /** */
    private int keyIndex__ = 0;

    /**
     * <p>順序とキーをセットする
     * @param order 並べ替えの順序　昇順:true　降順:false
     * @param keyIndex 並べ替えのキー　0から始める
     */
    public ListCompare(boolean order, int keyIndex) {
        order__ = order;
        keyIndex__ = keyIndex;
    }

    /**
     * <p>ソートのための比較を行う
     * @param list1 比較対照1
     * @param list2 比較対照2
     * @return 比較結果
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings({"all", "unchecked" })
    public int compare(List list1, List list2) {

        Comparable key1 = (Comparable) list1.get(keyIndex__);
        Comparable key2 = (Comparable) list2.get(keyIndex__);

        if (order__) {
            return key1.compareTo(key2);
        } else {
            return key2.compareTo(key1);
        }
    }

}
