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
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_MAINSCREEN_LAYOUT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMainscreenLayoutDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMainscreenLayoutDao(Connection con) {
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
            sql.addSql("drop table CMN_MAINSCREEN_LAYOUT");

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
            sql.addSql(" create table CMN_MAINSCREEN_LAYOUT (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   MSC_POSITION time not null,");
            sql.addSql("   MSL_AUID integer not null,");
            sql.addSql("   MSL_ADATE timestamp not null,");
            sql.addSql("   MSL_EUID integer not null,");
            sql.addSql("   MSL_EDATE timestamp not null,");
            sql.addSql("   primary key (USR_SID, MSC_POSITION)");
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
     * <p>Insert CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMainscreenLayoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MAINSCREEN_LAYOUT(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSL_AUID,");
            sql.addSql("   MSL_ADATE,");
            sql.addSql("   MSL_EUID,");
            sql.addSql("   MSL_EDATE");
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
            sql.addIntValue(bean.getMscPosition());
            sql.addIntValue(bean.getMslAuid());
            sql.addDateValue(bean.getMslAdate());
            sql.addIntValue(bean.getMslEuid());
            sql.addDateValue(bean.getMslEdate());
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
     * <p>Update CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMainscreenLayoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT");
            sql.addSql(" set ");
            sql.addSql("   MSC_POSITION=?,");
            sql.addSql("   MSL_EUID=?,");
            sql.addSql("   MSL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMscPosition());
            sql.addIntValue(bean.getMslEuid());
            sql.addDateValue(bean.getMslEdate());
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
     * <p>Select CMN_MAINSCREEN_LAYOUT All Data
     * @return List in CMN_MAINSCREEN_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMainscreenLayoutModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMainscreenLayoutModel> ret = new ArrayList<CmnMainscreenLayoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSL_AUID,");
            sql.addSql("   MSL_ADATE,");
            sql.addSql("   MSL_EUID,");
            sql.addSql("   MSL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMainscreenLayoutFromRs(rs));
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
     * <p>Select CMN_MAINSCREEN_LAYOUT
     * @param usrSid USR_SID
     * @return CMN_MAINSCREEN_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMainscreenLayoutModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnMainscreenLayoutModel> ret = new ArrayList<CmnMainscreenLayoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSL_AUID,");
            sql.addSql("   MSL_ADATE,");
            sql.addSql("   MSL_EUID,");
            sql.addSql("   MSL_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMainscreenLayoutFromRs(rs));
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
     * <p>Delete CMN_MAINSCREEN_LAYOUT
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
            sql.addSql("   CMN_MAINSCREEN_LAYOUT");
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
     * <p>Create CMN_MAINSCREEN_LAYOUT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMainscreenLayoutModel
     * @throws SQLException SQL実行例外
     */
    private CmnMainscreenLayoutModel __getCmnMainscreenLayoutFromRs(ResultSet rs)
    throws SQLException {
        CmnMainscreenLayoutModel bean = new CmnMainscreenLayoutModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMscPosition(rs.getInt("MSC_POSITION"));
        bean.setMslAuid(rs.getInt("MSL_AUID"));
        bean.setMslAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSL_ADATE")));
        bean.setMslEuid(rs.getInt("MSL_EUID"));
        bean.setMslEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSL_EDATE")));
        return bean;
    }
}
