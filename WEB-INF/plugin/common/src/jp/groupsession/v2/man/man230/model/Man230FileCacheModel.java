package jp.groupsession.v2.man.man230.model;

/**
 * 
 * <br>[機  能]Man230専用のfileCache.confのモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230FileCacheModel {

    /** CACHE_SAVEDIR */
    private String cacheSaveDir__ = null;
    /** CACHE_DISKLIMIT */
    private String cacheDiskLim__ = null;
    /**
     * <p>cacheSaveDir を取得します。
     * @return cacheSaveDir
     */
    public String getCacheSaveDir() {
        return cacheSaveDir__;
    }
    /**
     * <p>cacheSaveDir をセットします。
     * @param cacheSaveDir cacheSaveDir
     */
    public void setCacheSaveDir(String cacheSaveDir) {
        cacheSaveDir__ = cacheSaveDir;
    }
    /**
     * <p>cacheDiskLim を取得します。
     * @return cacheDiskLim
     */
    public String getCacheDiskLim() {
        return cacheDiskLim__;
    }
    /**
     * <p>cacheDiskLim をセットします。
     * @param cacheDiskLim cacheDiskLim
     */
    public void setCacheDiskLim(String cacheDiskLim) {
        cacheDiskLim__ = cacheDiskLim;
    }
}
