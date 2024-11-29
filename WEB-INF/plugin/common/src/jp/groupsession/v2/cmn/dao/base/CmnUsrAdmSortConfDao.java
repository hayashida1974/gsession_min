package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_USR_ADM_SORT_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnUsrAdmSortConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrAdmSortConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrAdmSortConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrAdmSortConfDao(Connection con) {
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
            sql.addSql("drop table CMN_USR_ADM_SORT_CONF");

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
            sql.addSql(" create table CMN_USR_ADM_SORT_CONF (");
            sql.addSql("   UAS_SORT_KBN NUMBER(10,0) not null,");
            sql.addSql("   UAS_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   UAS_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   UAS_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   UAS_SORT_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   UAS_AUID NUMBER(10,0) not null,");
            sql.addSql("   UAS_ADATE varchar(23) not null,");
            sql.addSql("   UAS_EUID NUMBER(10,0) not null,");
            sql.addSql("   UAS_EDATE varchar(23) not null");
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
     * <p>Insert CMN_USR_ADM_SORT_CONF Data Bindding JavaBean
     * @param bean CMN_USR_ADM_SORT_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrAdmSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_ADM_SORT_CONF(");
            sql.addSql("   UAS_SORT_KBN,");
            sql.addSql("   UAS_SORT_KEY1,");
            sql.addSql("   UAS_SORT_ORDER1,");
            sql.addSql("   UAS_SORT_KEY2,");
            sql.addSql("   UAS_SORT_ORDER2,");
            sql.addSql("   UAS_AUID,");
            sql.addSql("   UAS_ADATE,");
            sql.addSql("   UAS_EUID,");
            sql.addSql("   UAS_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUasSortKbn());
            sql.addIntValue(bean.getUasSortKey1());
            sql.addIntValue(bean.getUasSortOrder1());
            sql.addIntValue(bean.getUasSortKey2());
            sql.addIntValue(bean.getUasSortOrder2());
            sql.addIntValue(bean.getUasAuid());
            sql.addDateValue(bean.getUasAdate());
            sql.addIntValue(bean.getUasEuid());
            sql.addDateValue(bean.getUasEdate());
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
     * <p>管理者ソート設定を更新します。
     * @param bean USR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSortConfig(CmnUsrAdmSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_ADM_SORT_CONF");
            sql.addSql(" set ");
            sql.addSql("   UAS_SORT_KBN=?,");
            sql.addSql("   UAS_SORT_KEY1=?,");
            sql.addSql("   UAS_SORT_ORDER1=?,");
            sql.addSql("   UAS_SORT_KEY2=?,");
            sql.addSql("   UAS_SORT_ORDER2=?,");
            sql.addSql("   UAS_EUID=?,");
            sql.addSql("   UAS_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUasSortKbn());
            sql.addIntValue(bean.getUasSortKey1());
            sql.addIntValue(bean.getUasSortOrder1());
            sql.addIntValue(bean.getUasSortKey2());
            sql.addIntValue(bean.getUasSortOrder2());
            sql.addIntValue(bean.getUasEuid());
            sql.addDateValue(bean.getUasEdate());

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
     * <p>Update CMN_USR_ADM_SORT_CONF Data Bindding JavaBean
     * @param bean CMN_USR_ADM_SORT_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrAdmSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_ADM_SORT_CONF");
            sql.addSql(" set ");
            sql.addSql("   UAS_SORT_KBN=?,");
            sql.addSql("   UAS_SORT_KEY1=?,");
            sql.addSql("   UAS_SORT_ORDER1=?,");
            sql.addSql("   UAS_SORT_KEY2=?,");
            sql.addSql("   UAS_SORT_ORDER2=?,");
            sql.addSql("   UAS_AUID=?,");
            sql.addSql("   UAS_ADATE=?,");
            sql.addSql("   UAS_EUID=?,");
            sql.addSql("   UAS_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUasSortKbn());
            sql.addIntValue(bean.getUasSortKey1());
            sql.addIntValue(bean.getUasSortOrder1());
            sql.addIntValue(bean.getUasSortKey2());
            sql.addIntValue(bean.getUasSortOrder2());
            sql.addIntValue(bean.getUasAuid());
            sql.addDateValue(bean.getUasAdate());
            sql.addIntValue(bean.getUasEuid());
            sql.addDateValue(bean.getUasEdate());

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
     * <p>Select CMN_USR_ADM_SORT_CONF All Data
     * @return CmnUsrAdmSortConfModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrAdmSortConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrAdmSortConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   UAS_SORT_KBN,");
            sql.addSql("   UAS_SORT_KEY1,");
            sql.addSql("   UAS_SORT_ORDER1,");
            sql.addSql("   UAS_SORT_KEY2,");
            sql.addSql("   UAS_SORT_ORDER2,");
            sql.addSql("   UAS_AUID,");
            sql.addSql("   UAS_ADATE,");
            sql.addSql("   UAS_EUID,");
            sql.addSql("   UAS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_ADM_SORT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrAdmSortConfFromRs(rs);
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
     * <p>Create CMN_USR_ADM_SORT_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrAdmSortConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrAdmSortConfModel __getCmnUsrAdmSortConfFromRs(ResultSet rs) throws SQLException {
        CmnUsrAdmSortConfModel bean = new CmnUsrAdmSortConfModel();
        bean.setUasSortKbn(rs.getInt("UAS_SORT_KBN"));
        bean.setUasSortKey1(rs.getInt("UAS_SORT_KEY1"));
        bean.setUasSortOrder1(rs.getInt("UAS_SORT_ORDER1"));
        bean.setUasSortKey2(rs.getInt("UAS_SORT_KEY2"));
        bean.setUasSortOrder2(rs.getInt("UAS_SORT_ORDER2"));
        bean.setUasAuid(rs.getInt("UAS_AUID"));
        bean.setUasAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UAS_ADATE")));
        bean.setUasEuid(rs.getInt("UAS_EUID"));
        bean.setUasEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UAS_EDATE")));
        return bean;
    }
}
