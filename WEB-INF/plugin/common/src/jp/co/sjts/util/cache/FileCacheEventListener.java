package jp.co.sjts.util.cache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

/**
 * <br>[機  能] ファイルキャッシュに関するイベントクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCacheEventListener extends CacheEventListenerAdapter {

    /**
     * {@inheritDoc}
     */
    public void notifyElementRemoved(Ehcache cache, Element element)
    throws CacheException {
        //キャッシュの削除を行った場合、キャッシュされたファイルを削除する
        __deleteFile(element);
    }

    /**
     * {@inheritDoc}
     */
    public void notifyElementExpired(Ehcache cache, Element element)
    throws CacheException {
        //キャッシュの生存期限を過ぎた場合、キャッシュされたファイルを削除する
        __deleteFile(element);
    }

    /**
     * {@inheritDoc}
     */
    public void notifyElementEvicted(Ehcache cache, Element element)
    throws CacheException {
        //キャッシュ数の上限によりキャッシュが削除された場合、キャッシュされたファイルを削除する
        __deleteFile(element);
    }

    /**
     * <br>[機  能] キャッシュされたファイルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param element Element
     */
    private void __deleteFile(Element element) {
        Object value = element.getObjectValue();
        if (value != null) {
            ((FileCacheModel) value).getPath().delete();
        }
    }
}