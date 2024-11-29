package jp.groupsession.v2.man.man230.model;


/**
 * 
 * <br>[機  能]ConnectionOption.propertiesの値を保持するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230ConnectionOptionModel {

    /** ロックモード */
    private String lockMode__ = null;
    /** ロックタイムアウト */
    private String lockTimeOut__ = null;
    /** デフォルトロックタイムアウト */
    private String defLockTimeOut__ = null;
    /** マルチスレッド */
    private String multiThreaded__ = null;
    /** IF EXISTS */
    private String ifExists__ = null;
    /** オートコミット */
    private String autoCommit__ = null;
    /** DBクローズオンイグジット */
    private String dbCloseOnExit__ = null;
    /** キャッシュサイズ*/
    private String cacheSize__ = null;
    /** ページサイズ */
    private String pageSize__ = null;
    /** マックスレングスインプレイス */
    private String maxLengthImplace__ = null;
    /** キャッシュタイプ */
    private String cacheType__ = null;
    /** MVCC */
    private String mvcc__ = null;
    /** クエリタイムアウト */
    private String queryTimeOut__ = null;
    /** クエリタイムアウトバッチ */
    private String queryTimeOutBatch__ = null;
    /** マックスログサイズ */
    private String maxLogSize__ = null;
    /**
     * <p>man230LockMode を取得します。
     * @return man230LockMode
     */
    public String getLockMode() {
        return lockMode__;
    }
    /**
     * <p>man230LockMode をセットします。
     * @param man230LockMode man230LockMode
     */
    public void setLockMode(String man230LockMode) {
        lockMode__ = man230LockMode;
    }
    /**
     * <p>man230LockTimeOut を取得します。
     * @return man230LockTimeOut
     */
    public String getLockTimeOut() {
        return lockTimeOut__;
    }
    /**
     * <p>man230LockTimeOut をセットします。
     * @param man230LockTimeOut man230LockTimeOut
     */
    public void setLockTimeOut(String man230LockTimeOut) {
        lockTimeOut__ = man230LockTimeOut;
    }
    /**
     * <p>man230DefLockTimeOut を取得します。
     * @return man230DefLockTimeOut
     */
    public String getDefLockTimeOut() {
        return defLockTimeOut__;
    }
    /**
     * <p>man230DefLockTimeOut をセットします。
     * @param man230DefLockTimeOut man230DefLockTimeOut
     */
    public void setDefLockTimeOut(String man230DefLockTimeOut) {
        defLockTimeOut__ = man230DefLockTimeOut;
    }
    /**
     * <p>man230MultiThreaded を取得します。
     * @return man230MultiThreaded
     */
    public String getMultiThreaded() {
        return multiThreaded__;
    }
    /**
     * <p>man230MultiThreaded をセットします。
     * @param man230MultiThreaded man230MultiThreaded
     */
    public void setMultiThreaded(String man230MultiThreaded) {
        multiThreaded__ = man230MultiThreaded;
    }
    /**
     * <p>man230IfExists を取得します。
     * @return man230IfExists
     */
    public String getIfExists() {
        return ifExists__;
    }
    /**
     * <p>man230IfExists をセットします。
     * @param man230IfExists man230IfExists
     */
    public void setIfExists(String man230IfExists) {
        ifExists__ = man230IfExists;
    }
    /**
     * <p>man230AutoCommit を取得します。
     * @return man230AutoCommit
     */
    public String getAutoCommit() {
        return autoCommit__;
    }
    /**
     * <p>man230AutoCommit をセットします。
     * @param man230AutoCommit man230AutoCommit
     */
    public void setAutoCommit(String man230AutoCommit) {
        autoCommit__ = man230AutoCommit;
    }
    /**
     * <p>man230DbCloseOnExit を取得します。
     * @return man230DbCloseOnExit
     */
    public String getDbCloseOnExit() {
        return dbCloseOnExit__;
    }
    /**
     * <p>man230DbCloseOnExit をセットします。
     * @param man230DbCloseOnExit man230DbCloseOnExit
     */
    public void setDbCloseOnExit(String man230DbCloseOnExit) {
        dbCloseOnExit__ = man230DbCloseOnExit;
    }
    /**
     * <p>man230CacheSize を取得します。
     * @return man230CacheSize
     */
    public String getCacheSize() {
        return cacheSize__;
    }
    /**
     * <p>man230CacheSize をセットします。
     * @param man230CacheSize man230CacheSize
     */
    public void setCacheSize(String man230CacheSize) {
        cacheSize__ = man230CacheSize;
    }
    /**
     * <p>man230PageSize を取得します。
     * @return man230PageSize
     */
    public String getPageSize() {
        return pageSize__;
    }
    /**
     * <p>man230PageSize をセットします。
     * @param man230PageSize man230PageSize
     */
    public void setPageSize(String man230PageSize) {
        pageSize__ = man230PageSize;
    }
    /**
     * <p>man230MaxLengthImplace を取得します。
     * @return man230MaxLengthImplace
     */
    public String getMaxLengthImplace() {
        return maxLengthImplace__;
    }
    /**
     * <p>man230MaxLengthImplace をセットします。
     * @param man230MaxLengthImplace man230MaxLengthImplace
     */
    public void setMaxLengthImplace(String man230MaxLengthImplace) {
        maxLengthImplace__ = man230MaxLengthImplace;
    }
    /**
     * <p>man230CacheType を取得します。
     * @return man230CacheType
     */
    public String getCacheType() {
        return cacheType__;
    }
    /**
     * <p>man230CacheType をセットします。
     * @param man230CacheType man230CacheType
     */
    public void setCacheType(String man230CacheType) {
        cacheType__ = man230CacheType;
    }
    /**
     * <p>man230Mvcc を取得します。
     * @return man230Mvcc
     */
    public String getMvcc() {
        return mvcc__;
    }
    /**
     * <p>man230Mvcc をセットします。
     * @param man230Mvcc man230Mvcc
     */
    public void setMvcc(String man230Mvcc) {
        mvcc__ = man230Mvcc;
    }
    /**
     * <p>man230QueryTimeOut を取得します。
     * @return man230QueryTimeOut
     */
    public String getQueryTimeOut() {
        return queryTimeOut__;
    }
    /**
     * <p>man230QueryTimeOut をセットします。
     * @param man230QueryTimeOut man230QueryTimeOut
     */
    public void setQueryTimeOut(String man230QueryTimeOut) {
        queryTimeOut__ = man230QueryTimeOut;
    }
    /**
     * <p>man230QueryTimeOutBatch を取得します。
     * @return man230QueryTimeOutBatch
     */
    public String getQueryTimeOutBatch() {
        return queryTimeOutBatch__;
    }
    /**
     * <p>man230QueryTimeOutBatch をセットします。
     * @param man230QueryTimeOutBatch man230QueryTimeOutBatch
     */
    public void setQueryTimeOutBatch(String man230QueryTimeOutBatch) {
        queryTimeOutBatch__ = man230QueryTimeOutBatch;
    }
    /**
     * <p>man230MaxLogSize を取得します。
     * @return man230MaxLogSize
     */
    public String getMaxLogSize() {
        return maxLogSize__;
    }
    /**
     * <p>man230MaxLogSize をセットします。
     * @param man230MaxLogSize man230MaxLogSize
     */
    public void setMaxLogSize(String man230MaxLogSize) {
        maxLogSize__ = man230MaxLogSize;
    }
    
}
