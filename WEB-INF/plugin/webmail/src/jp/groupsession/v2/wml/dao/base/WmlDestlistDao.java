package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;

/**
 * <p>WML_DESTLIST Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDestlistDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDestlistDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDestlistDao(Connection con) {
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
            sql.addSql("drop table WML_DESTLIST");

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
            sql.addSql(" create table WML_DESTLIST (");
            sql.addSql("   WDL_SID NUMBER(10,0) not null,");
            sql.addSql("   WDL_NAME varchar(100) not null,");
            sql.addSql("   WDL_BIKO varchar(1000),");
            sql.addSql("   WDL_ORDER NUMBER(10,0) not null,");
            sql.addSql("   WDL_AUID NUMBER(10,0) not null,");
            sql.addSql("   WDL_ADATE varchar(23) not null,");
            sql.addSql("   WDL_EUID NUMBER(10,0) not null,");
            sql.addSql("   WDL_EDATE varchar(23) not null,");
            sql.addSql("   primary key (WDL_SID)");
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
     * <p>Insert WML_DESTLIST Data Bindding JavaBean
     * @param bean WML_DESTLIST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDestlistModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DESTLIST(");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDL_NAME,");
            sql.addSql("   WDL_BIKO,");
            sql.addSql("   WDL_AUID,");
            sql.addSql("   WDL_ADATE,");
            sql.addSql("   WDL_EUID,");
            sql.addSql("   WDL_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWdlSid());
            sql.addStrValue(bean.getWdlName());
            sql.addStrValue(bean.getWdlBiko());
            sql.addIntValue(bean.getWdlAuid());
            sql.addDateValue(bean.getWdlAdate());
            sql.addIntValue(bean.getWdlEuid());
            sql.addDateValue(bean.getWdlEdate());
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
     * <p>Update WML_DESTLIST Data Bindding JavaBean
     * @param bean WML_DESTLIST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDestlistModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" set ");
            sql.addSql("   WDL_NAME=?,");
            sql.addSql("   WDL_BIKO=?,");
            sql.addSql("   WDL_EUID=?,");
            sql.addSql("   WDL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWdlName());
            sql.addStrValue(bean.getWdlBiko());
            sql.addIntValue(bean.getWdlEuid());
            sql.addDateValue(bean.getWdlEdate());
            //where
            sql.addIntValue(bean.getWdlSid());

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
     * <p>Select WML_DESTLIST All Data
     * @return List in WML_DESTLISTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDestlistModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDestlistModel> ret = new ArrayList<WmlDestlistModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDL_NAME,");
            sql.addSql("   WDL_BIKO,");
            sql.addSql("   WDL_AUID,");
            sql.addSql("   WDL_ADATE,");
            sql.addSql("   WDL_EUID,");
            sql.addSql("   WDL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDestlistFromRs(rs));
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
     * <p>Select WML_DESTLIST
     * @param wdlSid WDL_SID
     * @return WML_DESTLISTModel
     * @throws SQLException SQL実行例外
     */
    public WmlDestlistModel select(int wdlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDestlistModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDL_NAME,");
            sql.addSql("   WDL_BIKO,");
            sql.addSql("   WDL_AUID,");
            sql.addSql("   WDL_ADATE,");
            sql.addSql("   WDL_EUID,");
            sql.addSql("   WDL_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDestlistFromRs(rs);
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
     * <p>Select WML_DESTLIST
     * @param wdlSidList WDL_SIDリスト
     * @return WML_DESTLISTModel
     * @throws SQLException SQL実行例外
     */
    public String select(String[] wdlSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDL_NAME,");
            sql.addSql("   WDL_BIKO,");
            sql.addSql("   WDL_AUID,");
            sql.addSql("   WDL_ADATE,");
            sql.addSql("   WDL_EUID,");
            sql.addSql("   WDL_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID in (");
            boolean sepFlg = false;
            for (String wdlSid : wdlSidList) {
                if (sepFlg) {
                    sql.addSql("     ,");
                } else {
                    sepFlg = true;
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(wdlSid));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            sepFlg = false;
            while (rs.next()) {
                if (sepFlg) {
                    ret += ", ";
                } else {
                    sepFlg = true;
                }
                ret += rs.getString("WDL_NAME");
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
     * <p>Delete WML_DESTLIST
     * @param wdlSid WDL_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wdlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);

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
     * <p>Create WML_DESTLIST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDestlistModel
     * @throws SQLException SQL実行例外
     */
    private WmlDestlistModel __getWmlDestlistFromRs(ResultSet rs) throws SQLException {
        WmlDestlistModel bean = new WmlDestlistModel();
        bean.setWdlSid(rs.getInt("WDL_SID"));
        bean.setWdlName(rs.getString("WDL_NAME"));
        bean.setWdlBiko(rs.getString("WDL_BIKO"));
        bean.setWdlAuid(rs.getInt("WDL_AUID"));
        bean.setWdlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WDL_ADATE")));
        bean.setWdlEuid(rs.getInt("WDL_EUID"));
        bean.setWdlEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WDL_EDATE")));
        return bean;
    }
}
