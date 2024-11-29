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
import jp.groupsession.v2.wml.model.base.WmlAccountRcvdataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_RCVDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountRcvdataDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountRcvdataDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_RCVDATA");

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
            sql.addSql(" create table WML_ACCOUNT_RCVDATA (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WRD_RECEIVE_DATE varchar(23),");
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
     * <p>Insert WML_ACCOUNT_RCVDATA Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountRcvdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_RCVDATA(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRD_RECEIVE_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addDateValue(bean.getWrdReceiveDate());
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
     * <p>Update WML_ACCOUNT_RCVDATA Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountRcvdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_RCVDATA");
            sql.addSql(" set ");
            sql.addSql("   WRD_RECEIVE_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getWrdReceiveDate());
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
     * <p>Update WML_ACCOUNT_RCVDATA Data Bindding JavaBean
     * @param wacSid アカウントSID
     * @param receiveDate 受信日時
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateReceiveDate(int wacSid, UDate receiveDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_RCVDATA");
            sql.addSql(" set ");
            sql.addSql("   WRD_RECEIVE_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(receiveDate);
            //where
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
     * <p>Select WML_ACCOUNT_RCVDATA All Data
     * @return List in WML_ACCOUNT_RCVDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountRcvdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountRcvdataModel> ret = new ArrayList<WmlAccountRcvdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRD_RECEIVE_DATE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountRcvdataFromRs(rs));
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
     * <p>Select WML_ACCOUNT_RCVDATA
     * @param wacSid WAC_SID
     * @return WML_ACCOUNT_RCVDATAModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountRcvdataModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountRcvdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRD_RECEIVE_DATE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVDATA");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountRcvdataFromRs(rs);
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
     * <p>Delete WML_ACCOUNT_RCVDATA
     * @param wacSid WAC_SID
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
            sql.addSql("   WML_ACCOUNT_RCVDATA");
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
     * <p>Create WML_ACCOUNT_RCVDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountRcvdataModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountRcvdataModel __getWmlAccountRcvdataFromRs(ResultSet rs) throws SQLException {
        WmlAccountRcvdataModel bean = new WmlAccountRcvdataModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWrdReceiveDate(UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
        return bean;
    }
}
