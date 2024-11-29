package jp.co.sjts.util.sort;

import java.io.File;
import java.util.Arrays;

/**
 * <br>[機  能] ファイルオブジェクトのソートを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileSorter {

    /**
     * <br>[機  能] ソートを行いその結果を返します。
     * <br>[解  説] <p>ソート対象を指定されたソート順でソートします
     *              <br>ソートはファイル名を対象に行います。
     * <br>[備  考]
     * @param target ソート対象
     * @param order ソート順
     * @return ソートした配列
     */
    public static File[] sort(final File[] target, final boolean order) {
        return sort(target, order , FileCompare.SORTKEY_FILE_NAME);
    }

    /**
     * <br>[機  能] ソートを行いその結果を返します。
     * <br>[解  説] <p>ソート対象（ファイル名一覧）を指定されたソート順でソートします
     *              <br>その際、指定されたキーによりソートキーを決定します。
     * <br>[備  考]
     * @param target ソート対象
     * @param order ソート順
     * @param key 0:ファイル名でソート,1:更新時間でソート
     * @return ソートした配列
     */
    public static File[] sort(final File[] target, final boolean order, int key) {
        if (target == null) {
            return null;
        }
        //ソート
        FileCompare comp = new FileCompare();
        comp.setOrder(order);
        comp.setKey(key);
        Arrays.sort(target, comp);
        return target;
    }
}
