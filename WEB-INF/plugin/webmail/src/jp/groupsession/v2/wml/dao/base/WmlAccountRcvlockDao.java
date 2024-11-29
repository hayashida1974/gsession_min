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
import jp.groupsession.v2.wml.model.base.WmlAccountRcvlockModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_RCVLOCK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountRcvlockDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountRcvlockDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountRcvlockDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountRcvlockDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_RCVLOCK");

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
            sql.addSql(" create table WML_ACCOUNT_RCVLOCK (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WRL_RCVEDATE varchar(23) not null,");
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
     * <p>Insert WML_ACCOUNT_RCVLOCK Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVLOCK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountRcvlockModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_RCVLOCK(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRL_RCVEDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addIntValue(bean.getWacSid());
            sql.addDateValue(bean.getWrlRcvedate());
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_ACCOUNT_RCVLOCK Data Bindding JavaBean
     * @param bean WML_ACCOUNT_RCVLOCK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountRcvlockModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_RCVLOCK");
            sql.addSql(" set ");
            sql.addSql("   WRL_RCVEDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addDateValue(bean.getWrlRcvedate());
            //where
            sql.addIntValue(bean.getWacSid());

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
     * <p>Select WML_ACCOUNT_RCVLOCK All Data
     * @return List in WML_ACCOUNT_RCVLOCKModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountRcvlockModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountRcvlockModel> ret = new ArrayList<WmlAccountRcvlockModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRL_RCVEDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVLOCK");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountRcvlockFromRs(rs));
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
     * <p>Select WML_ACCOUNT_RCVLOCK
     * @param wacSid WAC_SID
     * @return WML_ACCOUNT_RCVLOCKModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountRcvlockModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountRcvlockModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WRL_RCVEDATE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVLOCK");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountRcvlockFromRs(rs);
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
     * <br>[機  能] 指定したアカウントのロックレコードが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return true: ロックあり false: ロックなし
     * @throws SQLException SQL実行例外
     */
    public boolean checkLockExists(int wacSid) throws SQLException {

        boolean result = false;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(WAC_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVLOCK");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Delete WML_ACCOUNT_RCVLOCK
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
            sql.addSql("   WML_ACCOUNT_RCVLOCK");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete WML_ACCOUNT_RCVLOCK
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
            sql.addSql("   WML_ACCOUNT_RCVLOCK");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
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
     * <br>[機  能] 最終受信日時が指定した日時より前のデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 削除基準日時
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOldLock(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_RCVLOCK");
            sql.addSql(" where ");
            sql.addSql("   WRL_RCVEDATE<=?");
            sql.addDateValue(date);

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
     * <p>Create WML_ACCOUNT_RCVLOCK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountRcvlockModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountRcvlockModel __getWmlAccountRcvlockFromRs(ResultSet rs) throws SQLException {
        WmlAccountRcvlockModel bean = new WmlAccountRcvlockModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWrlRcvedate(UDate.getInstanceTimestamp(rs.getTimestamp("WRL_RCVEDATE")));
        return bean;
    }
}
