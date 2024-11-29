package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnPluginMobilePermissionModel;

/**
 * <p>CMN_PLUGIN_MOBILE_PERMISSION Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPluginMobilePermissionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPluginMobilePermissionDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPluginMobilePermissionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPluginMobilePermissionDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table CMN_PLUGIN_MOBILE_PERMISSION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table CMN_PLUGIN_MOBILE_PERMISSION (");
            sql.addSql("   PMP_PLUGIN varchar(10) not null,");
            sql.addSql("   primary key (PMP_PLUGIN)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean
     * @param bean CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPluginMobilePermissionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_MOBILE_PERMISSION(");
            sql.addSql("   PMP_PLUGIN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPmpPlugin());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean
     * @param bean CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPluginMobilePermissionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PLUGIN_MOBILE_PERMISSION");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   PMP_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addStrValue(bean.getPmpPlugin());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select CMN_PLUGIN_MOBILE_PERMISSION All Data
     * @return List in CMN_PLUGIN_MOBILE_PERMISSIONModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginMobilePermissionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginMobilePermissionModel> ret = new ArrayList<CmnPluginMobilePermissionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PMP_PLUGIN");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_MOBILE_PERMISSION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginMobilePermissionFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>モバイルアプリからの外部アクセスを許可するプラグインのID一覧を取得する</p>
     * @return プラグインID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAllowPluginIdList() throws SQLException {
        List<String> result = new ArrayList<String>();
        Statement stmt = null;
        ResultSet rs = null;
 
        try {
            stmt = getCon().createStatement();
            rs = stmt.executeQuery(
                "select PMP_PLUGIN from CMN_PLUGIN_MOBILE_PERMISSION");
            while (rs.next()) {
                result.add(rs.getString("PMP_PLUGIN"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return result;
    }

    /**
     * <p>Select CMN_PLUGIN_MOBILE_PERMISSION
     * @param pmpPlugin PMP_PLUGIN
     * @return CMN_PLUGIN_MOBILE_PERMISSIONModel
     * @throws SQLException SQL実行例外
     */
    public CmnPluginMobilePermissionModel select(String pmpPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPluginMobilePermissionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PMP_PLUGIN");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_MOBILE_PERMISSION");
            sql.addSql(" where ");
            sql.addSql("   PMP_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pmpPlugin);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnPluginMobilePermissionFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete CMN_PLUGIN_MOBILE_PERMISSION
     * @param pmpPlugin PMP_PLUGIN
     * @throws SQLException SQL実行例外
     */
    public int delete(String pmpPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_MOBILE_PERMISSION");
            sql.addSql(" where ");
            sql.addSql("   PMP_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pmpPlugin);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>登録データを全て削除する</p>
     * @param pmpPlugin PMP_PLUGIN
     * @throws SQLException SQL実行例外
     */
    public int deleteAll() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_MOBILE_PERMISSION");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create CMN_PLUGIN_MOBILE_PERMISSION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPluginMobilePermissionModel
     * @throws SQLException SQL実行例外
     */
    private CmnPluginMobilePermissionModel __getCmnPluginMobilePermissionFromRs(ResultSet rs) throws SQLException {
        CmnPluginMobilePermissionModel bean = new CmnPluginMobilePermissionModel();
        bean.setPmpPlugin(rs.getString("PMP_PLUGIN"));
        return bean;
    }
}
