package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlMaildataSortSearchModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_MAILDATA_SORT_SEARCH Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataSortSearchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMaildataSortSearchDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMaildataSortSearchDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMaildataSortSearchDao(Connection con) {
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
            sql.addSql("drop table WML_MAILDATA_SORT_SEARCH");

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
            sql.addSql(" create table WML_MAILDATA_SORT_SEARCH (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   WSS_SORTKEY NUMBER(10,0) not null,");
            sql.addSql("   WSS_ORDER NUMBER(10,0) not null,");
            sql.addSql("   primary key (WAC_SID,USR_SID)");
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
     * <p>Insert WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean
     * @param bean WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMaildataSortSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAILDATA_SORT_SEARCH(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WSS_SORTKEY,");
            sql.addSql("   WSS_ORDER");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getWssSortkey());
            sql.addIntValue(bean.getWssOrder());
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
     * <p>Update WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean
     * @param bean WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMaildataSortSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAILDATA_SORT_SEARCH");
            sql.addSql(" set ");
            sql.addSql("   WSS_SORTKEY=?,");
            sql.addSql("   WSS_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWssSortkey());
            sql.addIntValue(bean.getWssOrder());
            //where
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select WML_MAILDATA_SORT_SEARCH All Data
     * @return List in WML_MAILDATA_SORT_SEARCHModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataSortSearchModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMaildataSortSearchModel> ret = new ArrayList<WmlMaildataSortSearchModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WSS_SORTKEY,");
            sql.addSql("   WSS_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA_SORT_SEARCH");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataSortSearchFromRs(rs));
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
     * <p>Select WML_MAILDATA_SORT_SEARCH
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @return WML_MAILDATA_SORT_SEARCHModel
     * @throws SQLException SQL実行例外
     */
    public WmlMaildataSortSearchModel select(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMaildataSortSearchModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WSS_SORTKEY,");
            sql.addSql("   WSS_ORDER");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT_SEARCH");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMaildataSortSearchFromRs(rs);
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
     * <p>Delete WML_MAILDATA_SORT_SEARCH
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT_SEARCH");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

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
     * <p>Create WML_MAILDATA_SORT_SEARCH Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMaildataSortSearchModel
     * @throws SQLException SQL実行例外
     */
    private WmlMaildataSortSearchModel __getWmlMaildataSortSearchFromRs(ResultSet rs)
    throws SQLException {
        WmlMaildataSortSearchModel bean = new WmlMaildataSortSearchModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWssSortkey(rs.getInt("WSS_SORTKEY"));
        bean.setWssOrder(rs.getInt("WSS_ORDER"));
        return bean;
    }
}
