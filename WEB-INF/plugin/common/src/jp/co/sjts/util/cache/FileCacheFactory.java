package jp.co.sjts.util.cache;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.CacheDecoratorFactory;
import net.sf.ehcache.constructs.blocking.BlockingCache;

/**
 * <br>[機  能] ファイル情報のキャッシュを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCacheFactory extends CacheDecoratorFactory {

    @Override
    public Ehcache createDecoratedEhcache(Ehcache cache, Properties properties) {
        return new BlockingCache(cache);
    }

    @Override
    public Ehcache createDefaultDecoratedEhcache(Ehcache cache, Properties properties) {
        return new BlockingCache(cache);
    }
}