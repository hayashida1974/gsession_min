package jp.co.sjts.util.sort;

import java.util.Arrays;

/**
 * <br>[機  能] プリミティブ型のソートを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrimitiveSorter {

    /**
     * <br>[機  能] ソートを行いその結果を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param target ソート対象
     * @param order ソート順
     * @return ソートした配列
     */
    public static int[] sortInt(final int[] target, final boolean order) {
        if (target == null) {
            return null;
        }
        //Integerに変換
        Integer[] t2 = new Integer[target.length];
        for (int i = 0; i < t2.length; i++) {
            t2[i] = new Integer(target[i]);
        }
        //ソート
        SimpleCompare comp = new SimpleCompare();
        comp.setOrder(order);
        Arrays.sort(t2, comp);

        //int[]に戻す
        int[] ret = new int[target.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = t2[i].intValue();
        }
        return null;
    }
}
