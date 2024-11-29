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
import jp.groupsession.v2.wml.model.base.WmlAccountUserProxyModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_USER_PROXY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountUserProxyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountUserProxyDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountUserProxyDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountUserProxyDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_USER_PROXY");

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
            sql.addSql(" create table WML_ACCOUNT_USER_PROXY (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0)");
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
     * <p>Insert WML_ACCOUNT_USER_PROXY Data Bindding JavaBean
     * @param bean WML_ACCOUNT_USER_PROXY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountUserProxyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_USER_PROXY(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <br>[機  能] アカウント代理人の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param userSidList ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void insertProxyUser(int wacSid, String[] userSidList) throws SQLException {

        if (wacSid <= 0 || userSidList == null || userSidList.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_USER_PROXY(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (String userSid : userSidList) {
                sql.clearValue();
                pstmt.clearParameters();

                sql.addIntValue(wacSid);
                sql.addIntValue(Integer.parseInt(userSid));
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_ACCOUNT_USER_PROXY Data Bindding JavaBean
     * @param bean WML_ACCOUNT_USER_PROXY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountUserProxyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_USER_PROXY");
            sql.addSql(" set ");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたアカウントの代理人ユーザを全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteProxyUser(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   WML_ACCOUNT_USER_PROXY");
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
     * <p>Select WML_ACCOUNT_USER_PROXY All Data
     * @return List in WML_ACCOUNT_USER_PROXYModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountUserProxyModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountUserProxyModel> ret = new ArrayList<WmlAccountUserProxyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_USER_PROXY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountUserProxyFromRs(rs));
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
     * <br>[機  能] 指定したアカウントの代理人ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 代理人ユーザSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getProxyUserList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> userList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_USER_PROXY");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userList;
    }

    /**
     * <p>Create WML_ACCOUNT_USER_PROXY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountUserProxyModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountUserProxyModel __getWmlAccountUserProxyFromRs(ResultSet rs)
    throws SQLException {
        WmlAccountUserProxyModel bean = new WmlAccountUserProxyModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
