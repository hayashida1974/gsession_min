package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_MAINSCREEN_LAYOUT_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMainscreenLayoutUserDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMainscreenLayoutUserDao(Connection con) {
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
            sql.addSql("drop table CMN_MAINSCREEN_LAYOUT_USER");

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
            sql.addSql(" create table CMN_MAINSCREEN_LAYOUT_USER (");
            sql.addSql("   USR_SID time not null,");
            sql.addSql("   MSU_DEFAULT_KBN time not null,");
            sql.addSql("   MSU_AUID integer not null,");
            sql.addSql("   MSU_ADATE timestamp not null,");
            sql.addSql("   MSU_EUID integer not null,");
            sql.addSql("   MSU_EDATE timestamp not null,");
            sql.addSql("   primary key (USR_SID)");
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
     * <p>Insert CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMainscreenLayoutUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MAINSCREEN_LAYOUT_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSU_DEFAULT_KBN,");
            sql.addSql("   MSU_AUID,");
            sql.addSql("   MSU_ADATE,");
            sql.addSql("   MSU_EUID,");
            sql.addSql("   MSU_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMsuDefaultKbn());
            sql.addIntValue(bean.getMsuAuid());
            sql.addDateValue(bean.getMsuAdate());
            sql.addIntValue(bean.getMsuEuid());
            sql.addDateValue(bean.getMsuEdate());
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
     * <p>Update CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMainscreenLayoutUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_USER");
            sql.addSql(" set ");
            sql.addSql("   MSU_DEFAULT_KBN=?,");
            sql.addSql("   MSU_EUID=?,");
            sql.addSql("   MSU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMsuDefaultKbn());
            sql.addIntValue(bean.getMsuEuid());
            sql.addDateValue(bean.getMsuEdate());
            //where
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
     * <p>Select CMN_MAINSCREEN_LAYOUT_USER All Data
     * @return List in CMN_MAINSCREEN_LAYOUT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMainscreenLayoutUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMainscreenLayoutUserModel> ret = new ArrayList<CmnMainscreenLayoutUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSU_DEFAULT_KBN,");
            sql.addSql("   MSU_AUID,");
            sql.addSql("   MSU_ADATE,");
            sql.addSql("   MSU_EUID,");
            sql.addSql("   MSU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMainscreenLayoutUserFromRs(rs));
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
     * <p>Select CMN_MAINSCREEN_LAYOUT_USER
     * @param usrSid USR_SID
     * @return CMN_MAINSCREEN_LAYOUT_USERModel
     * @throws SQLException SQL実行例外
     */
    public CmnMainscreenLayoutUserModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMainscreenLayoutUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSU_DEFAULT_KBN,");
            sql.addSql("   MSU_AUID,");
            sql.addSql("   MSU_ADATE,");
            sql.addSql("   MSU_EUID,");
            sql.addSql("   MSU_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMainscreenLayoutUserFromRs(rs);
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
     * <p>Delete CMN_MAINSCREEN_LAYOUT_USER
     * @param usrSid USR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create CMN_MAINSCREEN_LAYOUT_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMainscreenLayoutUserModel
     * @throws SQLException SQL実行例外
     */
    private CmnMainscreenLayoutUserModel __getCmnMainscreenLayoutUserFromRs(ResultSet rs)
    throws SQLException {
        CmnMainscreenLayoutUserModel bean = new CmnMainscreenLayoutUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMsuDefaultKbn(rs.getInt("MSU_DEFAULT_KBN"));
        bean.setMsuAuid(rs.getInt("MSU_AUID"));
        bean.setMsuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSU_ADATE")));
        bean.setMsuEuid(rs.getInt("MSU_EUID"));
        bean.setMsuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSU_EDATE")));
        return bean;
    }
}
