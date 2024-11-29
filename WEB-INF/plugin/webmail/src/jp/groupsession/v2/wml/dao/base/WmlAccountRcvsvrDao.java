package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvsvrModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_RCVSVR Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvsvrDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountRcvsvrDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvsvrDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountRcvsvrDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_RCVSVR");

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
            sql.addSql(" create table WML_ACCOUNT_RCVSVR (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WRS_RECEIVE_COUNT Date not null,");
            sql.addSql("   WRS_RECEIVE_SIZE Date not null,");
            sql.addSql("   WRS_EDATE Date,");
            sql.addSql("   primary key (WAC_SID)");
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
     * <p>Insert WML_ACCOUNT_RCVSVR Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVSVR Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountRcvsvrModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_RCVSVR(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRS_RECEIVE_COUNT,");
            sql.addSql("   WRS_RECEIVE_SIZE,");
            sql.addSql("   WRS_EDATE");
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
            sql.addLongValue(bean.getWrsReceiveCount());
            sql.addLongValue(bean.getWrsReceiveSize());
            sql.addDateValue(bean.getWrsEdate());
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
     * <p>Update WML_ACCOUNT_RCVSVR Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVSVR Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountRcvsvrModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_RCVSVR");
            sql.addSql(" set ");
            sql.addSql("   WRS_RECEIVE_COUNT=?,");
            sql.addSql("   WRS_RECEIVE_SIZE=?,");
            sql.addSql("   WRS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWrsReceiveCount());
            sql.addLongValue(bean.getWrsReceiveSize());
            sql.addDateValue(bean.getWrsEdate());
            //where
            sql.addIntValue(bean.getWacSid());

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
     * <br>[機  能] 更新日を指定した日時に変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param edate 日時
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateEdate(int wacSid, UDate edate) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_RCVSVR");
            sql.addSql(" set ");
            sql.addSql("   WRS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addDateValue(edate);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select WML_ACCOUNT_RCVSVR All Data
     * @return List in WML_ACCOUNT_RCVSVRModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountRcvsvrModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountRcvsvrModel> ret = new ArrayList<WmlAccountRcvsvrModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRS_RECEIVE_COUNT,");
            sql.addSql("   WRS_RECEIVE_SIZE,");
            sql.addSql("   WRS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVSVR");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountRcvsvrFromRs(rs));
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
     * <p>Select WML_ACCOUNT_RCVSVR
     * @param wacSid WAC_SID
     * @return WML_ACCOUNT_RCVSVRModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountRcvsvrModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountRcvsvrModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRS_RECEIVE_COUNT,");
            sql.addSql("   WRS_RECEIVE_SIZE,");
            sql.addSql("   WRS_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVSVR");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountRcvsvrFromRs(rs);
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
     * <p>Delete WML_ACCOUNT_RCVSVR
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVSVR");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <p>Create WML_ACCOUNT_RCVSVR Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountRcvsvrModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountRcvsvrModel __getWmlAccountRcvsvrFromRs(ResultSet rs) throws SQLException {
        WmlAccountRcvsvrModel bean = new WmlAccountRcvsvrModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWrsReceiveCount(rs.getLong("WRS_RECEIVE_COUNT"));
        bean.setWrsReceiveSize(rs.getLong("WRS_RECEIVE_SIZE"));
        bean.setWrsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WRS_EDATE")));
        return bean;
    }
}
