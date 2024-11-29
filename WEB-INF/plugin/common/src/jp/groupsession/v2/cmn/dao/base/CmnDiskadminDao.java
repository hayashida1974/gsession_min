package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnDiskadminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_DISKADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnDiskadminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnDiskadminDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnDiskadminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnDiskadminDao(Connection con) {
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
            sql.addSql("drop table CMN_DISKADMIN");

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
            sql.addSql(" create table CMN_DISKADMIN (");
            sql.addSql("   DSK_TYPE NUMBER(10,0) not null,");
            sql.addSql("   DSK_VALUE NUMBER(10,0) not null,");
            sql.addSql("   DSK_AUID NUMBER(10,0) not null,");
            sql.addSql("   DSK_ADATE varchar(26) not null,");
            sql.addSql("   DSK_EUID NUMBER(10,0) not null,");
            sql.addSql("   DSK_EDATE varchar(26) not null");
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
     * <p>Insert CMN_DISKADMIN Data Bindding JavaBean
     * @param bean CMN_DISKADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnDiskadminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_DISKADMIN(");
            sql.addSql("   DSK_TYPE,");
            sql.addSql("   DSK_VALUE,");
            sql.addSql("   DSK_AUID,");
            sql.addSql("   DSK_ADATE,");
            sql.addSql("   DSK_EUID,");
            sql.addSql("   DSK_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getDskType());
            sql.addIntValue(bean.getDskValue());
            sql.addIntValue(bean.getDskAuid());
            sql.addDateValue(bean.getDskAdate());
            sql.addIntValue(bean.getDskEuid());
            sql.addDateValue(bean.getDskEdate());
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
     * <p>Update CMN_DISKADMIN Data Bindding JavaBean
     * @param bean CMN_DISKADMIN Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnDiskadminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_DISKADMIN");
            sql.addSql(" set ");
            sql.addSql("   DSK_TYPE=?,");
            sql.addSql("   DSK_VALUE=?,");
            sql.addSql("   DSK_AUID=?,");
            sql.addSql("   DSK_ADATE=?,");
            sql.addSql("   DSK_EUID=?,");
            sql.addSql("   DSK_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getDskType());
            sql.addIntValue(bean.getDskValue());
            sql.addIntValue(bean.getDskAuid());
            sql.addDateValue(bean.getDskAdate());
            sql.addIntValue(bean.getDskEuid());
            sql.addDateValue(bean.getDskEdate());

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
     * <p>Select CMN_DISKADMIN
     * @return CMN_DISKADMINModel
     * @throws SQLException SQL実行例外
     */
    public CmnDiskadminModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnDiskadminModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DSK_TYPE,");
            sql.addSql("   DSK_VALUE,");
            sql.addSql("   DSK_AUID,");
            sql.addSql("   DSK_ADATE,");
            sql.addSql("   DSK_EUID,");
            sql.addSql("   DSK_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_DISKADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnDiskadminFromRs(rs);
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
     * <p>Delete CMN_DISKADMIN
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_DISKADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create CMN_DISKADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnDiskadminModel
     * @throws SQLException SQL実行例外
     */
    private CmnDiskadminModel __getCmnDiskadminFromRs(ResultSet rs) throws SQLException {
        CmnDiskadminModel bean = new CmnDiskadminModel();
        bean.setDskType(rs.getInt("DSK_TYPE"));
        bean.setDskValue(rs.getInt("DSK_VALUE"));
        bean.setDskAuid(rs.getInt("DSK_AUID"));
        bean.setDskAdate(UDate.getInstanceTimestamp(rs.getTimestamp("DSK_ADATE")));
        bean.setDskEuid(rs.getInt("DSK_EUID"));
        bean.setDskEdate(UDate.getInstanceTimestamp(rs.getTimestamp("DSK_EDATE")));
        return bean;
    }
}
