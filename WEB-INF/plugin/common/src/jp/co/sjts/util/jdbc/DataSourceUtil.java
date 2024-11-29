package jp.co.sjts.util.jdbc;

import java.time.Duration;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] データソース関係のユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DataSourceUtil {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(DataSourceUtil.class);

    /**
     * <br>[機  能] データソース関係のユーティリティ
     * <br>[解  説] 下記の様に使用してください。
     * <br>
     * <br> //接続情報を作成
     * <br> DataSourceModel dsModel = new DataSourceModel();
     * <br> ds.setUrl("jdbc:h2:${APP_ROOT}WEB-INF/db/gs2db/gs2db");
     * <br> ds.setUrl("jdbc:postgresql://192.168.1.196:5432/gsession3");
     * <br> dsModel.setUser("admin");
     * <br> dsModel.setPass("maneger");
     * <br> dsModel.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
     * <br> dsModel.setDefaultAutoCommit(false);
     * <br> //データソースの取得
     * <br> javax.sql.DataSource ds = DataSourceUtil.createDataSource(dsModel);
     * <br> //コネクションの取得
     * <br> javax.sql.Connection con = ds.getConnection();
     * <br>
     * <br>[備  考]
     * @param dsModel データソース情報のモデル
     * @return 生成したjavax.sql.DataSource
     */
    public static DataSource createDataSource(DataSourceModel dsModel) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(dsModel.getDriverClassName());
        bds.setUrl(dsModel.getUrl());
        bds.setUsername(dsModel.getUser());
        bds.setPassword(dsModel.getPass());
        bds.setValidationQuery(dsModel.getValidationQuery());
        bds.setDefaultReadOnly(dsModel.isDefaultReadOnly());
        bds.setDefaultAutoCommit(dsModel.isDefaultAutoCommit());
        bds.setMaxIdle(dsModel.getMaxIdle());
        bds.setMaxTotal(dsModel.getMaxTotal());
        bds.setMaxWait(Duration.ofMillis(dsModel.getMaxWait()));
        //リーク対策
        bds.setRemoveAbandonedOnBorrow(dsModel.isRemoveAbandonedOnBorrow());
        bds.setRemoveAbandonedTimeout(Duration.ofSeconds(dsModel.getRemoveAbandonedTimeout()));
        bds.setLogAbandoned(dsModel.isLogAbandoned());

        bds.setAccessToUnderlyingConnectionAllowed(
                dsModel.isAccessToUnderlyingConnectionAllowed());

        if (log__.isDebugEnabled()) {
            //driverClassName
            log__.debug("DriverClassName = " + dsModel.getDriverClassName());
            //url
            log__.debug("Url = " + dsModel.getUrl());
            //port
            log__.debug("Port = " + dsModel.getPort());
            //user
            log__.debug("User = " + dsModel.getUser());
            //pass
            log__.debug("Pass = " + dsModel.getPass());
            //validationQuery
            log__.debug("ValidationQuery = " + dsModel.getValidationQuery());
            //defaultReadOnly
            log__.debug("DefaultReadOnly = " + dsModel.isDefaultReadOnly());
            //defaultAutoCommit
            log__.debug("DefaultAutoCommit = " + dsModel.isDefaultAutoCommit());
            //maxIdle
            log__.debug("MaxIdle = " + dsModel.getMaxIdle());
            //maxTotal
            log__.debug("maxTotal = " + dsModel.getMaxTotal());
            //maxWait
            log__.debug("MaxWait = " + dsModel.getMaxWait());
            //removeAbandonedOnBorrow
            log__.debug("RemoveAbandonedOnBorrow = " + dsModel.isRemoveAbandonedOnBorrow());
            //removeAbandonedTimeout
            log__.debug("RemoveAbandonedTimeout = " + dsModel.getRemoveAbandonedTimeout());
            //logAbandoned
            log__.debug("LogAbandoned = " + dsModel.isLogAbandoned());
            //accessToUnderlyingConnectionAllowed
            log__.debug("accessToUnderlyingConnectionAllowed = "
                    + dsModel.isAccessToUnderlyingConnectionAllowed());
        }

        return bds;
    }
}
