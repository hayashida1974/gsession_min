package jp.groupsession.v2.cmn;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] CmnBinfModelをファイル名でソートする
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileNameComparator implements Comparator<CmnBinfModel> {
    /**
     * @param  cmnBinfModelA 比較対象
     * @param  cmnBinfModelB 比較対象
     * @return ソート結果
     */
    public int compare(CmnBinfModel cmnBinfModelA, CmnBinfModel cmnBinfModelB) {
        String binFileNameA = cmnBinfModelA.getBinFileName();
        String binFileNameB = cmnBinfModelB.getBinFileName();
        return binFileNameA.compareTo(binFileNameB);
    }
}
