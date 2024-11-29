package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_USRM_LABEL_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLabelUsrConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLabelUsrConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLabelUsrConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLabelUsrConfDao(Connection con) {
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
            sql.addSql("drop table CMN_LABEL_USR_CONF");

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
            sql.addSql(" create table CMN_LABEL_USR_CONF (");
            sql.addSql("   LUF_EDIT  integer not null,");
            sql.addSql("   LUF_SET   integer not null,");
            sql.addSql("   LUF_AUID  integer not null,");
            sql.addSql("   LUF_ADATE timestamp not null,");
            sql.addSql("   LUF_EUID  integer not null,");
            sql.addSql("   LUF_EDATE timestamp not null");
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
     * <p>Insert CMN_LABEL_USR_CONF Data Bindding JavaBean
     * @param bean CMN_LABEL_USR_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLabelUsrConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LABEL_USR_CONF(");
            sql.addSql("   LUF_EDIT,");
            sql.addSql("   LUF_SET,");
            sql.addSql("   LUF_AUID,");
            sql.addSql("   LUF_ADATE,");
            sql.addSql("   LUF_EUID,");
            sql.addSql("   LUF_EDATE");
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
            sql.addIntValue(bean.getLufEdit());
            sql.addIntValue(bean.getLufSet());
            sql.addIntValue(bean.getLufAuid());
            sql.addDateValue(bean.getLufAdate());
            sql.addIntValue(bean.getLufEuid());
            sql.addDateValue(bean.getLufEdate());
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
     * <p>Update CMN_LABEL_USR_CONF Data Bindding JavaBean
     * @param bean CMN_LABEL_USR_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLabelUsrConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR_CONF");
            sql.addSql(" set ");
            sql.addSql("   LUF_EDIT=?,");
            sql.addSql("   LUF_SET=?,");
            sql.addSql("   LUF_AUID=?,");
            sql.addSql("   LUF_ADATE=?,");
            sql.addSql("   LUF_EUID=?,");
            sql.addSql("   LUF_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLufEdit());
            sql.addIntValue(bean.getLufSet());
            sql.addIntValue(bean.getLufAuid());
            sql.addDateValue(bean.getLufAdate());
            sql.addIntValue(bean.getLufEuid());
            sql.addDateValue(bean.getLufEdate());

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
     * <p>Select CMN_LABEL_USR_CONF All Data
     * @return CMN_LABEL_USR_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnLabelUsrConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnLabelUsrConfModel ret = new CmnLabelUsrConfModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LUF_EDIT,");
            sql.addSql("   LUF_SET,");
            sql.addSql("   LUF_AUID,");
            sql.addSql("   LUF_ADATE,");
            sql.addSql("   LUF_EUID,");
            sql.addSql("   LUF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCmnLabelUsrConfFromRs(rs);
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
     * <p>Select CMN_LABEL_USR_CONF All Data
     * @return CMN_LABEL_USR_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLabelUsrConfModel> select_all() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLabelUsrConfModel> ret = new ArrayList<CmnLabelUsrConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LUF_EDIT,");
            sql.addSql("   LUF_SET,");
            sql.addSql("   LUF_AUID,");
            sql.addSql("   LUF_ADATE,");
            sql.addSql("   LUF_EUID,");
            sql.addSql("   LUF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLabelUsrConfFromRs(rs));
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
     * <p>Delete CMN_LABEL_USR_CONF
     * @throws SQLException SQL実行例外
     * @return 件数
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
            sql.addSql("   CMN_LABEL_USR_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create CMN_LABEL_USR_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLabelUsrConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnLabelUsrConfModel __getCmnLabelUsrConfFromRs(ResultSet rs) throws SQLException {
        CmnLabelUsrConfModel bean = new CmnLabelUsrConfModel();
        bean.setLufEdit(rs.getInt("LUF_EDIT"));
        bean.setLufSet(rs.getInt("LUF_SET"));
        bean.setLufAuid(rs.getInt("LUF_AUID"));
        bean.setLufAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LUF_ADATE")));
        bean.setLufEuid(rs.getInt("LUF_EUID"));
        bean.setLufEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LUF_EDATE")));
        return bean;
    }
}
