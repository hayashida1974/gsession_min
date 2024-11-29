package jp.groupsession.v2.cmn.cmn110;

import java.io.File;
import java.io.FilenameFilter;

import jp.co.sjts.util.StringUtil;

/**
 * <br>[機  能] 添付ファイル名のフィルタリング用クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn110FilenameFilter implements FilenameFilter {

    /** フィルタリングに使用する文字列 */
    private String startStr__ = null;
    /** フィルタリングに使用する文字列 */
    private String endStr__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    @SuppressWarnings("unused")
    private Cmn110FilenameFilter() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param startStr フィルタリングに使用する文字列
     * @param endStr フィルタリングに使用する文字列
     */
    public Cmn110FilenameFilter(String startStr, String endStr) {
        startStr__ = startStr;
        endStr__ = endStr;
    }

    /**
     * 指定されたファイルをファイルリストに含めるかどうかを判定する
     * @param dir ファイルが見つかったディレクトリ
     * @param name ファイル名
     * @return 名前をファイルリストに含める場合は true、そうでない場合は false
     */
    public boolean accept(File dir, String name) {

        if (StringUtil.isNullZeroString(name)) {
            return false;
        }

        if (StringUtil.isNullZeroString(startStr__)) {
            return false;
        }

        if (name.length() < startStr__.length()) {
            return false;
        }

        boolean result = false;
        if (name.startsWith(startStr__) && name.endsWith(endStr__)) {
            return true;
        }

        return result;
    }
}
