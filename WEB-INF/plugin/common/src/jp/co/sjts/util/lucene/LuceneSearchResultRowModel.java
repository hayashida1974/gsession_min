package jp.co.sjts.util.lucene;

import java.io.Serializable;

/**
 * <br>[機  能] Luceneの検索結果を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class LuceneSearchResultRowModel implements Serializable {

    /** 結果コンテント */
    private String content__ = null;
    /** 結果フレーズ所属パス */
    private String path__ = null;
    /** タイトル */
    private String title__ = null;

    /**
     * <p>content を取得します。
     * @return content
     */
    public String getContent() {
        return content__;
    }
    /**
     * <p>content をセットします。
     * @param content content
     */
    public void setContent(String content) {
        content__ = content;
    }
    /**
     * <p>path を取得します。
     * @return path
     */
    public String getPath() {
        return path__;
    }
    /**
     * <p>path をセットします。
     * @param path path
     */
    public void setPath(String path) {
        path__ = path;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
}
