package jp.co.sjts.util.sort;

import java.io.File;
import java.util.Comparator;

/**
 * <br>[機  能] File比較オブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCompare implements Comparator<File> {

    /** ファイル名でソート */
    public static final int SORTKEY_FILE_NAME = 0;
    /** 更新時間でソート */
    public static final int SORTKEY_UPDATE_TIME = 1;

    /** ソート順 */
    private boolean order__ = true;

    /** キーを設定 */
    private int key__ = SORTKEY_FILE_NAME;

    /**
     * <br>[機  能] ソート順を指定する
     * <br>[解  説]
     * <br>[備  考]
     * @param order true:昇順,false:降順
     */
    public void setOrder(boolean order) {
        order__ = order;
    }

    /**
     * <br>[機  能] キーを設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param key key を設定。
     */
    public void setKey(int key) {
        key__ = key;
    }

    /**
     * <br>[機  能] 比較ロジック
     * <br>[解  説]
     * <br>[備  考]
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 比較結果
     */
    public int compare(File o1, File o2) {

        String tmp1 = null;
        String tmp2 = null;
        if (key__ == SORTKEY_FILE_NAME) {
            //
            tmp1 = o1.getName();
            tmp2 = o2.getName();
        } else {
            tmp1 = String.valueOf(o1.lastModified());
            tmp2 = String.valueOf(o2.lastModified());
        }

        if (order__) {
            return  tmp1.compareTo(tmp2);
        } else {
            return  tmp2.compareTo(tmp1);
        }
    }
}
