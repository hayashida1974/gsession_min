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
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.base.CmnLoginConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_LOGIN_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLoginConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLoginConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLoginConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLoginConfDao(Connection con) {
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
            sql.addSql("drop table CMN_LOGIN_CONF");

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
            sql.addSql(" create table CMN_LOGIN_CONF (");
            sql.addSql("   LLC_LOCKOUT_KBN NUMBER(10,0) not null,");
            sql.addSql("   LLC_FAIL_CNT NUMBER(10,0),");
            sql.addSql("   LLC_FAIL_AGE NUMBER(10,0),");
            sql.addSql("   LLC_LOCK_AGE NUMBER(10,0),");
            sql.addSql("   LLC_AUID NUMBER(10,0),");
            sql.addSql("   LLC_ADATE varchar(23),");
            sql.addSql("   LLC_EUID NUMBER(10,0),");
            sql.addSql("   LLC_EDATE varchar(23)");
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
     * <p>Insert CMN_LOGIN_CONF Data Bindding JavaBean
     * @param bean CMN_LOGIN_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLoginConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOGIN_CONF(");
            sql.addSql("   LLC_LOCKOUT_KBN,");
            sql.addSql("   LLC_FAIL_CNT,");
            sql.addSql("   LLC_FAIL_AGE,");
            sql.addSql("   LLC_LOCK_AGE,");
            sql.addSql("   LLC_AUID,");
            sql.addSql("   LLC_ADATE,");
            sql.addSql("   LLC_EUID,");
            sql.addSql("   LLC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLlcLockoutKbn());
            sql.addIntValue(bean.getLlcFailCnt());
            sql.addIntValue(bean.getLlcFailAge());
            sql.addIntValue(bean.getLlcLockAge());
            sql.addIntValue(bean.getLlcAuid());
            sql.addDateValue(bean.getLlcAdate());
            sql.addIntValue(bean.getLlcEuid());
            sql.addDateValue(bean.getLlcEdate());
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
     * <p>Insert CMN_LOGIN_CONF Data Bindding JavaBean
     * @param bean CMN_LOGIN_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertLoginConf(CmnLoginConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOGIN_CONF(");
            sql.addSql("   LLC_LOCKOUT_KBN,");
            sql.addSql("   LLC_FAIL_CNT,");
            sql.addSql("   LLC_FAIL_AGE,");
            sql.addSql("   LLC_LOCK_AGE,");
            sql.addSql("   LLC_AUID,");
            sql.addSql("   LLC_ADATE,");
            sql.addSql("   LLC_EUID,");
            sql.addSql("   LLC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addIntValue(bean.getLlcLockoutKbn());

            if (bean.getLlcLockoutKbn() == GSConstCommon.LOGIN_LOCKKBN_LOCKOUT) {
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addIntValue(bean.getLlcFailCnt());
                sql.addIntValue(bean.getLlcFailAge());
                sql.addIntValue(bean.getLlcLockAge());
            } else {
                sql.addSql("   null, null, null,");
            }

            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addIntValue(bean.getLlcAuid());
            sql.addDateValue(bean.getLlcAdate());
            sql.addIntValue(bean.getLlcEuid());
            sql.addDateValue(bean.getLlcEdate());

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
     * <p>Update CMN_LOGIN_CONF Data Bindding JavaBean
     * @param bean CMN_LOGIN_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLoginConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOGIN_CONF");
            sql.addSql(" set ");
            sql.addSql("   LLC_LOCKOUT_KBN=?,");
            sql.addSql("   LLC_FAIL_CNT=?,");
            sql.addSql("   LLC_FAIL_AGE=?,");
            sql.addSql("   LLC_LOCK_AGE=?,");
            sql.addSql("   LLC_EUID=?,");
            sql.addSql("   LLC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLlcLockoutKbn());
            sql.addIntValue(bean.getLlcFailCnt());
            sql.addIntValue(bean.getLlcFailAge());
            sql.addIntValue(bean.getLlcLockAge());
            sql.addIntValue(bean.getLlcEuid());
            sql.addDateValue(bean.getLlcEdate());

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
     * <p>Update CMN_LOGIN_CONF Data Bindding JavaBean
     * @param bean CMN_LOGIN_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLoginConf(CmnLoginConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOGIN_CONF");
            sql.addSql(" set ");
            sql.addSql("   LLC_LOCKOUT_KBN=?,");

            sql.addIntValue(bean.getLlcLockoutKbn());
            if (bean.getLlcLockoutKbn() == GSConstCommon.LOGIN_LOCKKBN_LOCKOUT) {
                sql.addSql("   LLC_FAIL_CNT=?,");
                sql.addSql("   LLC_FAIL_AGE=?,");
                sql.addSql("   LLC_LOCK_AGE=?,");
                sql.addIntValue(bean.getLlcFailCnt());
                sql.addIntValue(bean.getLlcFailAge());
                sql.addIntValue(bean.getLlcLockAge());
            } else {
                sql.addSql("   LLC_FAIL_CNT=null,");
                sql.addSql("   LLC_FAIL_AGE=null,");
                sql.addSql("   LLC_LOCK_AGE=null,");
            }

            sql.addSql("   LLC_EUID=?,");
            sql.addSql("   LLC_EDATE=?");
            sql.addIntValue(bean.getLlcEuid());
            sql.addDateValue(bean.getLlcEdate());

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
     * <p>Select CMN_LOGIN_CONF All Data
     * @return CMN_LOGIN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnLoginConfModel getData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnLoginConfModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LLC_LOCKOUT_KBN,");
            sql.addSql("   LLC_FAIL_CNT,");
            sql.addSql("   LLC_FAIL_AGE,");
            sql.addSql("   LLC_LOCK_AGE,");
            sql.addSql("   LLC_AUID,");
            sql.addSql("   LLC_ADATE,");
            sql.addSql("   LLC_EUID,");
            sql.addSql("   LLC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                model = __getCmnLoginConfFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }

    /**
     * <p>Select CMN_LOGIN_CONF All Data
     * @return List in CMN_LOGIN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnLoginConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLoginConfModel> ret = new ArrayList<CmnLoginConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LLC_LOCKOUT_KBN,");
            sql.addSql("   LLC_FAIL_CNT,");
            sql.addSql("   LLC_FAIL_AGE,");
            sql.addSql("   LLC_LOCK_AGE,");
            sql.addSql("   LLC_AUID,");
            sql.addSql("   LLC_ADATE,");
            sql.addSql("   LLC_EUID,");
            sql.addSql("   LLC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLoginConfFromRs(rs));
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
     * <p>Create CMN_LOGIN_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLoginConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnLoginConfModel __getCmnLoginConfFromRs(ResultSet rs) throws SQLException {
        CmnLoginConfModel bean = new CmnLoginConfModel();
        bean.setLlcLockoutKbn(rs.getInt("LLC_LOCKOUT_KBN"));
        bean.setLlcFailCnt(rs.getInt("LLC_FAIL_CNT"));
        bean.setLlcFailAge(rs.getInt("LLC_FAIL_AGE"));
        bean.setLlcLockAge(rs.getInt("LLC_LOCK_AGE"));
        bean.setLlcAuid(rs.getInt("LLC_AUID"));
        bean.setLlcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LLC_ADATE")));
        bean.setLlcEuid(rs.getInt("LLC_EUID"));
        bean.setLlcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LLC_EDATE")));
        return bean;
    }
}
