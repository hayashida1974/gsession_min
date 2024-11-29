package jp.co.sjts.util.sort;

import java.util.Comparator;

/**
 * <br>[機  能] 比較オブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@SuppressWarnings("all")
public class SimpleCompare implements Comparator<Comparable> {
    /** ソート順 */
    private boolean order__ = true;

    /**
     * <br>[機  能] ソート順を指定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param order true:昇順,false:降順
     */
    public void setOrder(boolean order) {
        order__ = order;
    }

    /**
     * <br>[機  能] 比較ロジックです。
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 比較結果
     */
    public int compare(Comparable o1, Comparable o2) {

        if (order__) {
            return  o1.compareTo(o2);
        } else {
            return  o2.compareTo(o1);
        }
    }
}
