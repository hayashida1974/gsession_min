package jp.co.sjts.util.cache;

import java.io.File;
import java.io.Serializable;

/**
 * <br>[機  能] ファイルキャッシュ情報を保持するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCacheModel implements Serializable {
    /** キー値 */
    private String key__ = null;
    /** ファイルパス */
    private File path__ = null;
    /**
     * <p>key を取得します。
     * @return key
     */
    public String getKey() {
        return key__;
    }
    /**
     * <p>key をセットします。
     * @param key key
     */
    public void setKey(String key) {
        key__ = key;
    }
    /**
     * <p>path を取得します。
     * @return path
     */
    public File getPath() {
        return path__;
    }
    /**
     * <p>path をセットします。
     * @param path path
     */
    public void setPath(File path) {
        path__ = path;
    }
}