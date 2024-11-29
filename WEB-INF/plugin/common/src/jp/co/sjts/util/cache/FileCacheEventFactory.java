package jp.co.sjts.util.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * <br>[機  能] ファイルキャッシュに関するイベントリスナーのファクトリクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCacheEventFactory extends CacheEventListenerFactory {
    /**
     * Create a <code>CacheEventListener</code>
     *
     * @param properties implementation specific properties. These are configured as comma
     *                   separated name value pairs in ehcache.xml
     * @return a constructed CacheEventListener
     */
    public CacheEventListener createCacheEventListener(Properties properties) {
        return new FileCacheEventListener();
    }
}