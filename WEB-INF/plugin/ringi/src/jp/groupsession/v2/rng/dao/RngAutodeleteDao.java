package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.model.RngAutodeleteModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RNG_AUTODELETE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngAutodeleteDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngAutodeleteDao.class);

    /**
     * <p>Default Constructor
     */
    public RngAutodeleteDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngAutodeleteDao(Connection con) {
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
            sql.addSql("drop table RNG_AUTODELETE");

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
            sql.addSql(" create table RNG_AUTODELETE (");
            sql.addSql("   RAD_PENDING_KBN NUMBER(10,0) not null,");
            sql.addSql("   RAD_PENDING_YEAR NUMBER(10,0),");
            sql.addSql("   RAD_PENDING_MONTH NUMBER(10,0),");
            sql.addSql("   RAD_PENDING_DAY NUMBER(10,0),");
            sql.addSql("   RAD_COMPLETE_KBN NUMBER(10,0) not null,");
            sql.addSql("   RAD_COMPLETE_YEAR NUMBER(10,0),");
            sql.addSql("   RAD_COMPLETE_MONTH NUMBER(10,0),");
            sql.addSql("   RAD_COMPLETE_DAY NUMBER(10,0),");
            sql.addSql("   RAD_DRAFT_KBN NUMBER(10,0) not null,");
            sql.addSql("   RAD_DRAFT_YEAR NUMBER(10,0),");
            sql.addSql("   RAD_DRAFT_MONTH NUMBER(10,0),");
            sql.addSql("   RAD_DRAFT_DAY NUMBER(10,0),");
            sql.addSql("   RAD_AUID NUMBER(10,0) not null,");
            sql.addSql("   RAD_ADATE varchar(23) not null,");
            sql.addSql("   RAD_EUID NUMBER(10,0) not null,");
            sql.addSql("   RAD_EDATE varchar(23) not null");
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
     * <p>Insert RNG_AUTODELETE Data Bindding JavaBean
     * @param bean RNG_AUTODELETE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_AUTODELETE(");
            sql.addSql("   RAD_PENDING_KBN,");
            sql.addSql("   RAD_PENDING_YEAR,");
            sql.addSql("   RAD_PENDING_MONTH,");
            sql.addSql("   RAD_PENDING_DAY,");
            sql.addSql("   RAD_COMPLETE_KBN,");
            sql.addSql("   RAD_COMPLETE_YEAR,");
            sql.addSql("   RAD_COMPLETE_MONTH,");
            sql.addSql("   RAD_COMPLETE_DAY,");
            sql.addSql("   RAD_DRAFT_KBN,");
            sql.addSql("   RAD_DRAFT_YEAR,");
            sql.addSql("   RAD_DRAFT_MONTH,");
            sql.addSql("   RAD_DRAFT_DAY,");
            sql.addSql("   RAD_AUID,");
            sql.addSql("   RAD_ADATE,");
            sql.addSql("   RAD_EUID,");
            sql.addSql("   RAD_EDATE");
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
            sql.addSql("   ?,");
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
            sql.addIntValue(bean.getRadPendingKbn());
            sql.addIntValue(bean.getRadPendingYear());
            sql.addIntValue(bean.getRadPendingMonth());
            sql.addIntValue(bean.getRadPendingDay());
            sql.addIntValue(bean.getRadCompleteKbn());
            sql.addIntValue(bean.getRadCompleteYear());
            sql.addIntValue(bean.getRadCompleteMonth());
            sql.addIntValue(bean.getRadCompleteDay());
            sql.addIntValue(bean.getRadDraftKbn());
            sql.addIntValue(bean.getRadDraftYear());
            sql.addIntValue(bean.getRadDraftMonth());
            sql.addIntValue(bean.getRadDraftDay());
            sql.addIntValue(bean.getRadAuid());
            sql.addDateValue(bean.getRadAdate());
            sql.addIntValue(bean.getRadEuid());
            sql.addDateValue(bean.getRadEdate());
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
     * <p>Update RNG_AUTODELETE Data Bindding JavaBean
     * @param bean RNG_AUTODELETE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_AUTODELETE");
            sql.addSql(" set ");
            sql.addSql("   RAD_PENDING_KBN=?,");
            sql.addSql("   RAD_PENDING_YEAR=?,");
            sql.addSql("   RAD_PENDING_MONTH=?,");
            sql.addSql("   RAD_PENDING_DAY=?,");
            sql.addSql("   RAD_COMPLETE_KBN=?,");
            sql.addSql("   RAD_COMPLETE_YEAR=?,");
            sql.addSql("   RAD_COMPLETE_MONTH=?,");
            sql.addSql("   RAD_COMPLETE_DAY=?,");
            sql.addSql("   RAD_DRAFT_KBN=?,");
            sql.addSql("   RAD_DRAFT_YEAR=?,");
            sql.addSql("   RAD_DRAFT_MONTH=?,");
            sql.addSql("   RAD_DRAFT_DAY=?,");
            sql.addSql("   RAD_EUID=?,");
            sql.addSql("   RAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRadPendingKbn());
            sql.addIntValue(bean.getRadPendingYear());
            sql.addIntValue(bean.getRadPendingMonth());
            sql.addIntValue(bean.getRadPendingDay());
            sql.addIntValue(bean.getRadCompleteKbn());
            sql.addIntValue(bean.getRadCompleteYear());
            sql.addIntValue(bean.getRadCompleteMonth());
            sql.addIntValue(bean.getRadCompleteDay());
            sql.addIntValue(bean.getRadDraftKbn());
            sql.addIntValue(bean.getRadDraftYear());
            sql.addIntValue(bean.getRadDraftMonth());
            sql.addIntValue(bean.getRadDraftDay());
            sql.addIntValue(bean.getRadEuid());
            sql.addDateValue(bean.getRadEdate());

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
     * <p>Select RNG_AUTODELETE All Data
     * @return List in RNG_AUTODELETEModel
     * @throws SQLException SQL実行例外
     */
    public List<RngAutodeleteModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngAutodeleteModel> ret = new ArrayList<RngAutodeleteModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RAD_PENDING_KBN,");
            sql.addSql("   RAD_PENDING_YEAR,");
            sql.addSql("   RAD_PENDING_MONTH,");
            sql.addSql("   RAD_PENDING_DAY,");
            sql.addSql("   RAD_COMPLETE_KBN,");
            sql.addSql("   RAD_COMPLETE_YEAR,");
            sql.addSql("   RAD_COMPLETE_MONTH,");
            sql.addSql("   RAD_COMPLETE_DAY,");
            sql.addSql("   RAD_DRAFT_KBN,");
            sql.addSql("   RAD_DRAFT_YEAR,");
            sql.addSql("   RAD_DRAFT_MONTH,");
            sql.addSql("   RAD_DRAFT_DAY,");
            sql.addSql("   RAD_AUID,");
            sql.addSql("   RAD_ADATE,");
            sql.addSql("   RAD_EUID,");
            sql.addSql("   RAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_AUTODELETE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngAutodeleteFromRs(rs));
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
     * <p>Select RNG_AUTODELETE Data
     * @return RNG_AUTODELETEModel
     * @throws SQLException SQL実行例外
     */
    public RngAutodeleteModel getData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngAutodeleteModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RAD_PENDING_KBN,");
            sql.addSql("   RAD_PENDING_YEAR,");
            sql.addSql("   RAD_PENDING_MONTH,");
            sql.addSql("   RAD_PENDING_DAY,");
            sql.addSql("   RAD_COMPLETE_KBN,");
            sql.addSql("   RAD_COMPLETE_YEAR,");
            sql.addSql("   RAD_COMPLETE_MONTH,");
            sql.addSql("   RAD_COMPLETE_DAY,");
            sql.addSql("   RAD_DRAFT_KBN,");
            sql.addSql("   RAD_DRAFT_YEAR,");
            sql.addSql("   RAD_DRAFT_MONTH,");
            sql.addSql("   RAD_DRAFT_DAY,");
            sql.addSql("   RAD_AUID,");
            sql.addSql("   RAD_ADATE,");
            sql.addSql("   RAD_EUID,");
            sql.addSql("   RAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_AUTODELETE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngAutodeleteFromRs(rs);
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
     * <p>Create RNG_AUTODELETE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngAutodeleteModel
     * @throws SQLException SQL実行例外
     */
    private RngAutodeleteModel __getRngAutodeleteFromRs(ResultSet rs) throws SQLException {
        RngAutodeleteModel bean = new RngAutodeleteModel();
        bean.setRadPendingKbn(rs.getInt("RAD_PENDING_KBN"));
        bean.setRadPendingYear(rs.getInt("RAD_PENDING_YEAR"));
        bean.setRadPendingMonth(rs.getInt("RAD_PENDING_MONTH"));
        bean.setRadPendingDay(rs.getInt("RAD_PENDING_DAY"));
        bean.setRadCompleteKbn(rs.getInt("RAD_COMPLETE_KBN"));
        bean.setRadCompleteYear(rs.getInt("RAD_COMPLETE_YEAR"));
        bean.setRadCompleteMonth(rs.getInt("RAD_COMPLETE_MONTH"));
        bean.setRadCompleteDay(rs.getInt("RAD_COMPLETE_DAY"));
        bean.setRadDraftKbn(rs.getInt("RAD_DRAFT_KBN"));
        bean.setRadDraftYear(rs.getInt("RAD_DRAFT_YEAR"));
        bean.setRadDraftMonth(rs.getInt("RAD_DRAFT_MONTH"));
        bean.setRadDraftDay(rs.getInt("RAD_DRAFT_DAY"));
        bean.setRadAuid(rs.getInt("RAD_AUID"));
        bean.setRadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RAD_ADATE")));
        bean.setRadEuid(rs.getInt("RAD_EUID"));
        bean.setRadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RAD_EDATE")));
        return bean;
    }
}
