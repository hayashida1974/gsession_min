package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_MAINSCREEN_LAYOUT_ADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnMainscreenLayoutAdminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMainscreenLayoutAdminDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenLayoutAdminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMainscreenLayoutAdminDao(Connection con) {
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
            sql.addSql("drop table CMN_MAINSCREEN_LAYOUT_ADMIN");

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
            sql.addSql(" create table CMN_MAINSCREEN_LAYOUT_ADMIN (");
            sql.addSql("   MLC_ADM_LAYOUT_KBN time not null,");
            sql.addSql("   MLC_DEFAULT_KBN time not null,");
            sql.addSql("   MLC_AUID integer not null,");
            sql.addSql("   MLC_ADATE timestamp not null,");
            sql.addSql("   MLC_EUID integer not null,");
            sql.addSql("   MLC_EDATE timestamp not null");
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
     * <p>Insert CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMainscreenLayoutAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MAINSCREEN_LAYOUT_ADMIN(");
            sql.addSql("   MLC_ADM_LAYOUT_KBN,");
            sql.addSql("   MLC_DEFAULT_KBN,");
            sql.addSql("   MLC_AUID,");
            sql.addSql("   MLC_ADATE,");
            sql.addSql("   MLC_EUID,");
            sql.addSql("   MLC_EDATE");
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
            sql.addIntValue(bean.getMlcAdmLayoutKbn());
            sql.addIntValue(bean.getMlcDefaultKbn());
            sql.addIntValue(bean.getMlcAuid());
            sql.addDateValue(bean.getMlcAdate());
            sql.addIntValue(bean.getMlcEuid());
            sql.addDateValue(bean.getMlcEdate());
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
     * <p>Update CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMainscreenLayoutAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_ADMIN");
            sql.addSql(" set ");
            sql.addSql("   MLC_ADM_LAYOUT_KBN=?,");
            sql.addSql("   MLC_DEFAULT_KBN=?,");
            sql.addSql("   MLC_EUID=?,");
            sql.addSql("   MLC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMlcAdmLayoutKbn());
            sql.addIntValue(bean.getMlcDefaultKbn());
            sql.addIntValue(bean.getMlcEuid());
            sql.addDateValue(bean.getMlcEdate());

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
     * <p>Select CMN_MAINSCREEN_LAYOUT_ADMIN All Data
     * @return List in CMN_MAINSCREEN_LAYOUT_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public CmnMainscreenLayoutAdminModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMainscreenLayoutAdminModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MLC_ADM_LAYOUT_KBN,");
            sql.addSql("   MLC_DEFAULT_KBN,");
            sql.addSql("   MLC_AUID,");
            sql.addSql("   MLC_ADATE,");
            sql.addSql("   MLC_EUID,");
            sql.addSql("   MLC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MAINSCREEN_LAYOUT_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMainscreenLayoutAdminFromRs(rs);
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
     * <p>Create CMN_MAINSCREEN_LAYOUT_ADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMainscreenLayoutAdminModel
     * @throws SQLException SQL実行例外
     */
    private CmnMainscreenLayoutAdminModel __getCmnMainscreenLayoutAdminFromRs(ResultSet rs)
    throws SQLException {
        CmnMainscreenLayoutAdminModel bean = new CmnMainscreenLayoutAdminModel();
        bean.setMlcAdmLayoutKbn(rs.getInt("MLC_ADM_LAYOUT_KBN"));
        bean.setMlcDefaultKbn(rs.getInt("MLC_DEFAULT_KBN"));
        bean.setMlcAuid(rs.getInt("MLC_AUID"));
        bean.setMlcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MLC_ADATE")));
        bean.setMlcEuid(rs.getInt("MLC_EUID"));
        bean.setMlcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MLC_EDATE")));
        return bean;
    }
}
