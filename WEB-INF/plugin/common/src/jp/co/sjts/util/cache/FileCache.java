package jp.co.sjts.util.cache;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * <br>[機  能] ファイル情報のキャッシュを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileCache {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCache.class);

    /** CacheManager */
    private static CacheManager manager__ = null;

    /** Cache */
    private static Map<String, FileCache> cacheMap__ = null;

    /** リセットフラグ */
    private static AtomicBoolean resetFlg__ = new AtomicBoolean(false);

    /** 設定ファイル */
    private static String confFile__ = null;

    /** ファイルキャッシュ保存先ディレクトリパス */
    private String saveDir__ = null;

    /** キャッシュ名称 */
    private String cacheName__ = null;

    static {
        cacheMap__ = new HashMap<String, FileCache>();
    }

    /**
     * <br>[機  能] ファイルキャッシュインスタンスを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名称
     * @param saveDir ファイルキャッシュ保存先ディレクトリパス
     * @return ファイルキャッシュ

     */
    public static synchronized FileCache getInstance(String cacheName, String saveDir)  {
        FileCache fileCache = null;
        if (manager__ == null) {
            loadConfig(confFile__);
        }

        if (!manager__.cacheExists(cacheName)) {
            manager__.addCache(cacheName);
            fileCache = new FileCache();
            fileCache.setCacheName(cacheName);
            fileCache.setSaveDir(saveDir);
            cacheMap__.put(cacheName, fileCache);
            log__.info("ファイルキャッシュインスタンスを生成: キャッシュ名称 = " + cacheName);
        } else {
            fileCache = cacheMap__.get(cacheName);
        }
        return fileCache;
    }

    /**
     * <br>[機  能] ファイルキャッシュインスタンスを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名称
     * @param saveDir ファイルキャッシュ保存先ディレクトリパス
     * @param confFile 設定ファイルパス
     * @return ファイルキャッシュ
     */
    public static synchronized FileCache getInstance(String cacheName, String saveDir,
                                                                            String confFile) {
        FileCache.setConfFile(confFile);
        return getInstance(cacheName, saveDir);
    }

    /**
     * <br>[機  能] キャッシュ設定の読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath 設定ファイルパス
     */
    public static void loadConfig(String filePath) {
        manager__ = CacheManager.newInstance(filePath);
        confFile__ = filePath;
    }

    /**
     * <br>[機  能] 指定したキーに該当するデータがキャッシュされているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー値
     * @return 判定結果 true: キャッシュあり false: キャッシュなし
     */
    public boolean existsFileCache(String key) {
        //リセット中はキャッシュなしとして扱う
        if (resetFlg__.get()) {
            return false;
        }

        File cacheFile = getFile(key);
        boolean exists = (cacheFile != null && cacheFile.exists());
        cacheFile = null;
        return exists;
    }
    /**
     * <br>[機  能] キャッシュされたファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param key ファイルを取得するキー値
     * @return ファイルパス
     */
    public File getFile(String key) {
        //リセット中はキャッシュの取得を行わない
        if (resetFlg__.get()) {
            return null;
        }

        Element cacheEl = __getCacheElement(key);
        if (cacheEl == null || cacheEl.getObjectValue() == null) {
            return null;
        }

        return ((FileCacheModel) cacheEl.getObjectValue()).getPath();
    }

    /**
     * <br>[機  能] キャッシュ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー値
     * @return キャッシュ情報
     */
    private Element __getCacheElement(String key) {
        return _getCache(cacheName__).get(key);
    }

    /**
     * <br>[機  能] 指定されたファイルをキャッシュする
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー値
     * @param file ファイルパス
     * @throws IOException ファイルキャッシュ(ファイルコピー)に失敗
     * @throws IOToolsException ファイルキャッシュ保存先ディレクトリの作成に失敗
     */
    public void setFile(String key, String file) throws IOException, IOToolsException {
        setFile(key, file, false);
    }

    /**
     * <br>[機  能] 指定されたファイルをキャッシュする
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー値
     * @param file ファイルパス
     * @param update キャッシュの更新を行うか true: 更新する false: 更新しない
     * @throws IOException ファイルキャッシュ(ファイルコピー)に失敗
     * @throws IOToolsException ファイルキャッシュ保存先ディレクトリの作成に失敗
     */
    public void setFile(String key, String file, boolean update)
    throws IOException, IOToolsException {
        //リセット中はファイルのキャッシュを行わない
        if (resetFlg__.get()) {
            return;
        }

        //キー値、またはファイルパスが指定されていない場合は処理を終了する
        if (key == null || file == null) {
            return;
        }

        //指定したファイルが存在しない場合は処理を終了する
        File filePath = new File(file);
        if (!filePath.exists()) {
            return;
        }

        //指定したキーがキャッシュ済み、かつ更新を行わない場合は処理を終了する
        if (!update && existsFileCache(key)) {
            return;
        }

        //ファイルキャッシュ保存先パス
        File saveFilePath = __getSaveFilePath(cacheName__, key);
        File saveFileDir = new File(saveFilePath.getParent());

        //ファイル保存先ディレクトリの作成は5回まで試みる
        for (int tryCount = 1; tryCount <= 5; tryCount++) {
            try {
                IOTools.isDirCheck(saveFileDir.getPath(), true);
                tryCount = 5;
            } catch (IOToolsException ite) {
                log__.warn("Make FileCache Dir | TryCount: " + tryCount
                        + "\r\nFail FilePath = " + saveFileDir);
                if (tryCount >= 5) {
                    throw ite;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException inre) {
                        log__.error(inre);
                    }
                }
            }
        }

        //ファイルキャッシュ保存先のディスク使用率が規定値に達していた場合、キャッシュを行わない
        int diskLimit = NullDefault.getInt(
                                    (String) FileCacheConfig.getDiskLimit(), 0);
        if (diskLimit > 0) {
            BigDecimal diskRatio =
                    new BigDecimal(
                            saveFileDir.getTotalSpace()
                            - saveFileDir.getUsableSpace());
            diskRatio = diskRatio.divide(new BigDecimal(saveFileDir.getTotalSpace()),
                                                    2, BigDecimal.ROUND_HALF_UP);
            diskRatio = diskRatio.multiply(new BigDecimal(100));
            if (diskRatio.compareTo(new BigDecimal(diskLimit)) >= 0) {
                return;
            }
        }

        //ファイル情報のキャッシュを行う
        //また、キャッシュ対象のファイルをキャッシュファイル保存先ディレクトリへコピーする
        FileCacheModel cacheMdl = new FileCacheModel();
        cacheMdl.setKey(key);
        cacheMdl.setPath(saveFilePath);
        IOTools.copyBinFile(file, cacheMdl.getPath().getPath());
        _getCache(cacheName__).put(new Element(key, cacheMdl));
    }

    /**
     * <br>[機  能] 指定したkeyから取得したファイルを指定パスへコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー値
     * @param destPath コピー先ファイルパス
     * @throws IOException ファイルコピーに失敗
     */
    public void copyFile(String key, String destPath)
    throws IOException {
        IOTools.copyBinFile(getFile(key).getPath(), destPath);
    }

    /**
     * <br>[機  能] キャッシュのリセットを行う
     * <br>[解  説]
     * <br>[備  考]
     */
    public static void reset() {
        //リセット実施中の場合、処理を終了する
        if (resetFlg__.get()) {
            return;
        }

        resetFlg__.set(true);
        try {
            if (manager__ != null) {
                String[] cacheNameList = manager__.getCacheNames();
                if (cacheNameList != null) {
                    for (String cacheName : cacheNameList) {
                        FileCache.removeCache(cacheName);
                    }
                }

                cacheMap__.clear();
                manager__.clearAll();
            }
        } finally {
            resetFlg__.set(false);
        }
    }
    /**
     *
     * <br>[機  能] リセット中かどうかの判定を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true = リセット中
     */
    public static boolean isReseted() {
        return resetFlg__.get();
    }


    /**
     * <br>[機  能] ファイルキャッシュのcloseを行う
     * <br>[解  説]
     * <br>[備  考]
     */
    public static void close() {
        reset();

        if (manager__ != null) {
            manager__.shutdown();
        }
    }

    /**
     * <br>[機  能] キャッシュの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名称
     */
    public static void removeCache(String cacheName) {
        manager__.removeCache(cacheName);
        try {
            IOTools.deleteDir(cacheMap__.get(cacheName).getSaveDir());
            cacheMap__.remove(cacheName);
        } catch (Exception e) {
            log__.error("キャッシュ削除時に例外発生"
                    + " | cacheName = " + cacheName, e);
        }
    }

    /**
     * <br>[機  能] ファイルキャッシュの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名称
     * @param key ファイルキャッシュのキー
     */
    public void removeFileCache(String cacheName, String key) {
        _getCache(cacheName).remove(key);
    }

    /**
     * <br>[機  能] 指定した名称のキャッシュを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名
     * @return キャッシュ
     */
    protected Ehcache _getCache(String cacheName) {
        return manager__.getEhcache(cacheName);
    }

    /**
     * <br>[機  能] キャッシュファイル保存先ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacheName キャッシュ名称
     * @param key キー値
     * @return ファイルパス
     */
    private File __getSaveFilePath(String cacheName, String key) {
        return new File(
                    saveDir__
                + "cache/"
                + cacheName + "/"
                + key);
    }

    /**
     * <p>cacheName を取得します。
     * @return cacheName
     */
    public String getCacheName() {
        return cacheName__;
    }

    /**
     * <p>cacheName をセットします。
     * @param cacheName cacheName
     */
    public void setCacheName(String cacheName) {
        cacheName__ = cacheName;
    }

    /**
     * <p>confFile をセットします。
     * @param confFile confFile
     */
    public static void setConfFile(String confFile) {
        confFile__ = confFile;
    }

    /**
     * <p>saveDir を取得します。
     * @return saveDir
     */
    public String getSaveDir() {
        return saveDir__;
    }

    /**
     * <p>saveDir をセットします。
     * @param saveDir saveDir
     */
    public void setSaveDir(String saveDir) {
        saveDir__ = saveDir;
        saveDir__ = IOTools.replaceSlashFileSep(saveDir__);
        if (!saveDir__.endsWith("/")) {
            saveDir__ += "/";
        }
    }

    /**
     * <br>[機  能] 生存期間を過ぎたキャッシュをクリアする
     * <br>[解  説]
     * <br>[備  考] ファイルの削除を同時に行う
     */
    public static void expiredCache() {
        if (cacheMap__ != null && !cacheMap__.isEmpty()) {
            Iterator<String> cacheNameIter = cacheMap__.keySet().iterator();
            while (cacheNameIter.hasNext()) {
                String cacheName = cacheNameIter.next();
                Ehcache cache = cacheMap__.get(cacheName)._getCache(cacheName);
                List keyList = cache.getKeys();
                for (Object key : keyList) {
                    cache.get(key);
                }
                keyList = null;
            }
        }
    }
}