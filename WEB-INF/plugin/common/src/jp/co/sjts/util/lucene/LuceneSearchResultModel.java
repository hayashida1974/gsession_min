package jp.co.sjts.util.lucene;

import java.io.Serializable;
import java.util.List;

/**
 * <br>[機  能] Luceneの検索結果を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class LuceneSearchResultModel implements Serializable {

    /** Hit件数 */
    private int hitCount__ = 0;
    /** 結果リスト */
    private List<LuceneSearchResultRowModel> resultList__ = null;

    /**
     * <p>hitCount を取得します。
     * @return hitCount
     */
    public int getHitCount() {
        return hitCount__;
    }
    /**
     * <p>hitCount をセットします。
     * @param hitCount hitCount
     */
    public void setHitCount(int hitCount) {
        hitCount__ = hitCount;
    }
    /**
     * <p>resultList を取得します。
     * @return resultList
     */
    public List<LuceneSearchResultRowModel> getResultList() {
        return resultList__;
    }
    /**
     * <p>resultList をセットします。
     * @param resultList resultList
     */
    public void setResultList(List<LuceneSearchResultRowModel> resultList) {
        resultList__ = resultList;
    }

}
