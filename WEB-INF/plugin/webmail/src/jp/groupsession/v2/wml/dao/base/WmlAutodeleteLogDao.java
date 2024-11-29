package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteLogModel;

/**
 * <p>WML_AUTODELETE_LOG Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAutodeleteLogDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAutodeleteLogDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAutodeleteLogDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAutodeleteLogDao(Connection con) {
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
            sql.addSql("drop table WML_AUTODELETE_LOG");

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
            sql.addSql(" create table WML_AUTODELETE_LOG (");
            sql.addSql("   WAL_DEL_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAL_DEL_YEAR NUMBER(10,0),");
            sql.addSql("   WAL_DEL_MONTH NUMBER(10,0),");
            sql.addSql("   WAL_DEL_DAY NUMBER(10,0),");
            sql.addSql("   WAL_AUID NUMBER(10,0) not null,");
            sql.addSql("   WAL_ADATE varchar(23) not null,");
            sql.addSql("   WAL_EUID NUMBER(10,0) not null,");
            sql.addSql("   WAL_EDATE varchar(23) not null");
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
     * <p>Insert WML_AUTODELETE_LOG Data Bindding JavaBean
     * @param bean WML_AUTODELETE_LOG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAutodeleteLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_AUTODELETE_LOG(");
            sql.addSql("   WAL_DEL_KBN,");
            sql.addSql("   WAL_DEL_YEAR,");
            sql.addSql("   WAL_DEL_MONTH,");
            sql.addSql("   WAL_DEL_DAY,");
            sql.addSql("   WAL_AUID,");
            sql.addSql("   WAL_ADATE,");
            sql.addSql("   WAL_EUID,");
            sql.addSql("   WAL_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWalDelKbn());
            sql.addIntValue(bean.getWalDelYear());
            sql.addIntValue(bean.getWalDelMonth());
            sql.addIntValue(bean.getWalDelDay());
            sql.addIntValue(bean.getWalAuid());
            sql.addDateValue(bean.getWalAdate());
            sql.addIntValue(bean.getWalEuid());
            sql.addDateValue(bean.getWalEdate());
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
     * <p>Update WML_AUTODELETE_LOG Data Bindding JavaBean
     * @param bean WML_AUTODELETE_LOG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAutodeleteLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_AUTODELETE_LOG");
            sql.addSql(" set ");
            sql.addSql("   WAL_DEL_KBN=?,");
            sql.addSql("   WAL_DEL_YEAR=?,");
            sql.addSql("   WAL_DEL_MONTH=?,");
            sql.addSql("   WAL_DEL_DAY=?,");
            sql.addSql("   WAL_EUID=?,");
            sql.addSql("   WAL_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWalDelKbn());
            sql.addIntValue(bean.getWalDelYear());
            sql.addIntValue(bean.getWalDelMonth());
            sql.addIntValue(bean.getWalDelDay());
            sql.addIntValue(bean.getWalEuid());
            sql.addDateValue(bean.getWalEdate());

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
     * <p>Select WML_AUTODELETE_LOG Data
     * @return  WML_AUTODELETE_LOGModel
     * @throws SQLException SQL実行例外
     */
    public WmlAutodeleteLogModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAutodeleteLogModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAL_DEL_KBN,");
            sql.addSql("   WAL_DEL_YEAR,");
            sql.addSql("   WAL_DEL_MONTH,");
            sql.addSql("   WAL_DEL_DAY,");
            sql.addSql("   WAL_AUID,");
            sql.addSql("   WAL_ADATE,");
            sql.addSql("   WAL_EUID,");
            sql.addSql("   WAL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_AUTODELETE_LOG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAutodeleteLogFromRs(rs);
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
     * <p>Create WML_AUTODELETE_LOG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAutodeleteLogModel
     * @throws SQLException SQL実行例外
     */
    private WmlAutodeleteLogModel __getWmlAutodeleteLogFromRs(ResultSet rs) throws SQLException {
        WmlAutodeleteLogModel bean = new WmlAutodeleteLogModel();
        bean.setWalDelKbn(rs.getInt("WAL_DEL_KBN"));
        bean.setWalDelYear(rs.getInt("WAL_DEL_YEAR"));
        bean.setWalDelMonth(rs.getInt("WAL_DEL_MONTH"));
        bean.setWalDelDay(rs.getInt("WAL_DEL_DAY"));
        bean.setWalAuid(rs.getInt("WAL_AUID"));
        bean.setWalAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WAL_ADATE")));
        bean.setWalEuid(rs.getInt("WAL_EUID"));
        bean.setWalEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WAL_EDATE")));
        return bean;
    }
}
