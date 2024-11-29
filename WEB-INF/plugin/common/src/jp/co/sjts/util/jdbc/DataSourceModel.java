package jp.co.sjts.util.jdbc;

/**
 * <br>[機  能] データソース情報を取得するためのモデル。
 * <br>[解  説] 使用方法はDataSourceUtilを参照すること。
 * <br>[備  考]
 *
 * @author JTS
 */
public class DataSourceModel {

    /** データソースID */
    private String id__ = null;
    /** 接続URL */
    private String url__ = null;
    /** ポート */
    private int port__ = 0;
    /** ユーザ */
    private String user__ = null;
    /** パスワード */
    private String pass__ = null;
    /** テストクエリー */
    private String validationQuery__ = null;
    /** ドライバクラス名 */
    private String driverClassName__ = null;
    /** 取得したコネクションをリードオンリーにするかどうかのフラグ true:リードオンリー, false:ライト、リード可能 */
    private boolean defaultReadOnly__ = false;
    /** 取得したコネクションのオートコミットをどうするかのフラグ true:オートコミット, false:オートコミットしない */
    private boolean defaultAutoCommit__ = false;
    /** MAXアイドルコネクション数 */
    private int maxIdle__ = 1;
    /** MAXコネクション数 */
    private int maxTotal__ = 1;
    /** MAX待ち時間(ミリ秒で指定する) */
    private long maxWait__ = 3000;
    /** Server URL */
    private String serverUrl__ = null;
    /** sid */
    private String sid__ = null;
    /** 基のConnectionを取得可能とするか？ */
    private boolean accessToUnderlyingConnectionAllowed__ = true;

    //リーク対策
    /** 接続の自動回収機能(true:ON, false:OFF) */
    private boolean removeAbandonedOnBorrow__ = false;
    /** 接続の自動回収基準時間(秒) */
    private int removeAbandonedTimeout__ = 600;
    /** 接続回収時にログ出力を行うか */
    private boolean logAbandoned__ = false;

    /**
     * @return serverUrl
     */
    public String getServerUrl() {
        return serverUrl__;
    }
    /**
     * @param serverUrl セットする serverUrl
     */
    public void setServerUrl(String serverUrl) {
        serverUrl__ = serverUrl;
    }
    /**
     * @return sid
     */
    public String getSid() {
        return sid__;
    }
    /**
     * @param sid セットする sid
     */
    public void setSid(String sid) {
        sid__ = sid;
    }
    /**
     * <p>defaultAutoCommit を取得します。
     * @return defaultAutoCommit
     */
    public boolean isDefaultAutoCommit() {
        return defaultAutoCommit__;
    }
    /**
     * <p>defaultAutoCommit をセットします。
     * @param defaultAutoCommit defaultAutoCommit
     */
    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        defaultAutoCommit__ = defaultAutoCommit;
    }
    /**
     * <p>defaultReadOnly を取得します。
     * @return defaultReadOnly
     */
    public boolean isDefaultReadOnly() {
        return defaultReadOnly__;
    }
    /**
     * <p>defaultReadOnly をセットします。
     * @param defaultReadOnly defaultReadOnly
     */
    public void setDefaultReadOnly(boolean defaultReadOnly) {
        defaultReadOnly__ = defaultReadOnly;
    }
    /**
     * <p>driverClassName を取得します。
     * @return driverClassName
     */
    public String getDriverClassName() {
        return driverClassName__;
    }
    /**
     * <p>driverClassName をセットします。
     * @param driverClassName driverClassName
     */
    public void setDriverClassName(String driverClassName) {
        driverClassName__ = driverClassName;
    }
    /**
     * <p>maxIdle を取得します。
     * @return maxIdle
     */
    public int getMaxIdle() {
        return maxIdle__;
    }
    /**
     * <p>maxIdle をセットします。
     * @param maxIdle maxIdle
     */
    public void setMaxIdle(int maxIdle) {
        maxIdle__ = maxIdle;
    }
    /**
     * <p>maxWait を取得します。
     * @return maxWait
     */
    public long getMaxWait() {
        return maxWait__;
    }
    /**
     * <p>maxWait をセットします。
     * @param maxWait maxWait
     */
    public void setMaxWait(long maxWait) {
        maxWait__ = maxWait;
    }
    /**
     * <p>pass を取得します。
     * @return pass
     */
    public String getPass() {
        return pass__;
    }
    /**
     * <p>pass をセットします。
     * @param pass pass
     */
    public void setPass(String pass) {
        pass__ = pass;
    }
    /**
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * @return port
     */
    public int getPort() {
    return port__;
    }
    /**
     * @param port 設定する port
     */
    public void setPort(int port) {
    port__ = port;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public String getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(String user) {
        user__ = user;
    }
    /**
     * <p>validationQuery を取得します。
     * @return validationQuery
     */
    public String getValidationQuery() {
        return validationQuery__;
    }
    /**
     * <p>validationQuery をセットします。
     * @param validationQuery validationQuery
     */
    public void setValidationQuery(String validationQuery) {
        validationQuery__ = validationQuery;
    }
    /**
     * <p>id を取得します。
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>removeAbandonedTimeout を取得します。
     * @return removeAbandonedTimeout
     */
    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout__;
    }
    /**
     * <p>removeAbandonedTimeout をセットします。
     * @param removeAbandonedTimeout removeAbandonedTimeout
     */
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        removeAbandonedTimeout__ = removeAbandonedTimeout;
    }
    /**
     * <p>logAbandoned を取得します。
     * @return logAbandoned
     */
    public boolean isLogAbandoned() {
        return logAbandoned__;
    }
    /**
     * <p>logAbandoned をセットします。
     * @param logAbandoned logAbandoned
     */
    public void setLogAbandoned(boolean logAbandoned) {
        logAbandoned__ = logAbandoned;
    }
    /**
     * <p>accessToUnderlyingConnectionAllowed を取得します。
     * @return accessToUnderlyingConnectionAllowed
     */
    public boolean isAccessToUnderlyingConnectionAllowed() {
        return accessToUnderlyingConnectionAllowed__;
    }
    /**
     * <p>accessToUnderlyingConnectionAllowed をセットします。
     * @param accessToUnderlyingConnectionAllowed accessToUnderlyingConnectionAllowed
     */
    public void setAccessToUnderlyingConnectionAllowed(
            boolean accessToUnderlyingConnectionAllowed) {
        accessToUnderlyingConnectionAllowed__ = accessToUnderlyingConnectionAllowed;
    }
    public int getMaxTotal() {
        return maxTotal__;
    }
    public void setMaxTotal(int maxTotal) {
        maxTotal__ = maxTotal;
    }
    public boolean isRemoveAbandonedOnBorrow() {
        return removeAbandonedOnBorrow__;
    }
    public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow) {
        removeAbandonedOnBorrow__ = removeAbandonedOnBorrow;
    }
}
