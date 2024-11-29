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
import jp.groupsession.v2.cmn.model.base.CmnUsrPriSortConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_USR_PRI_SORT_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnUsrPriSortConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrPriSortConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrPriSortConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrPriSortConfDao(Connection con) {
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
            sql.addSql("drop table CMN_USR_PRI_SORT_CONF");

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
            sql.addSql(" create table CMN_USR_PRI_SORT_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   UPS_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   UPS_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   UPS_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   UPS_SORT_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   UPS_AUID NUMBER(10,0) not null,");
            sql.addSql("   UPS_ADATE varchar(23) not null,");
            sql.addSql("   UPS_EUID NUMBER(10,0) not null,");
            sql.addSql("   UPS_EDATE varchar(23) not null,");
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
     * <p>Insert CMN_USR_PRI_SORT_CONF Data Bindding JavaBean
     * @param bean CMN_USR_PRI_SORT_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrPriSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_PRI_SORT_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPS_SORT_KEY1,");
            sql.addSql("   UPS_SORT_ORDER1,");
            sql.addSql("   UPS_SORT_KEY2,");
            sql.addSql("   UPS_SORT_ORDER2,");
            sql.addSql("   UPS_AUID,");
            sql.addSql("   UPS_ADATE,");
            sql.addSql("   UPS_EUID,");
            sql.addSql("   UPS_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUpsSortKey1());
            sql.addIntValue(bean.getUpsSortOrder1());
            sql.addIntValue(bean.getUpsSortKey2());
            sql.addIntValue(bean.getUpsSortOrder2());
            sql.addIntValue(bean.getUpsAuid());
            sql.addDateValue(bean.getUpsAdate());
            sql.addIntValue(bean.getUpsEuid());
            sql.addDateValue(bean.getUpsEdate());
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
     * <p>Update CMN_USR_PRI_SORT_CONF Data Bindding JavaBean
     * @param bean CMN_USR_PRI_SORT_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrPriSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_PRI_SORT_CONF");
            sql.addSql(" set ");
            sql.addSql("   UPS_SORT_KEY1=?,");
            sql.addSql("   UPS_SORT_ORDER1=?,");
            sql.addSql("   UPS_SORT_KEY2=?,");
            sql.addSql("   UPS_SORT_ORDER2=?,");
            sql.addSql("   UPS_AUID=?,");
            sql.addSql("   UPS_ADATE=?,");
            sql.addSql("   UPS_EUID=?,");
            sql.addSql("   UPS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUpsSortKey1());
            sql.addIntValue(bean.getUpsSortOrder1());
            sql.addIntValue(bean.getUpsSortKey2());
            sql.addIntValue(bean.getUpsSortOrder2());
            sql.addIntValue(bean.getUpsAuid());
            sql.addDateValue(bean.getUpsAdate());
            sql.addIntValue(bean.getUpsEuid());
            sql.addDateValue(bean.getUpsEdate());
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
     * <p>Select CMN_USR_PRI_SORT_CONF All Data
     * @return List in CMN_USR_PRI_SORT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrPriSortConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnUsrPriSortConfModel> ret = new ArrayList<CmnUsrPriSortConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPS_SORT_KEY1,");
            sql.addSql("   UPS_SORT_ORDER1,");
            sql.addSql("   UPS_SORT_KEY2,");
            sql.addSql("   UPS_SORT_ORDER2,");
            sql.addSql("   UPS_AUID,");
            sql.addSql("   UPS_ADATE,");
            sql.addSql("   UPS_EUID,");
            sql.addSql("   UPS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_PRI_SORT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrPriSortConfFromRs(rs));
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
     * <p>Select CMN_USR_PRI_SORT_CONF
     * @param usrSid USR_SID
     * @return CMN_USR_PRI_SORT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrPriSortConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrPriSortConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPS_SORT_KEY1,");
            sql.addSql("   UPS_SORT_ORDER1,");
            sql.addSql("   UPS_SORT_KEY2,");
            sql.addSql("   UPS_SORT_ORDER2,");
            sql.addSql("   UPS_AUID,");
            sql.addSql("   UPS_ADATE,");
            sql.addSql("   UPS_EUID,");
            sql.addSql("   UPS_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_PRI_SORT_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrPriSortConfFromRs(rs);
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
     * <p>Delete CMN_USR_PRI_SORT_CONF
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 件数
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
            sql.addSql("   CMN_USR_PRI_SORT_CONF");
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
     * <p>各ユーザのソート設定を更新します。
     * @param bean CmnUsrPriSortConfModel
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSortConfig(CmnUsrPriSortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_PRI_SORT_CONF");
            sql.addSql(" set ");
            sql.addSql("   UPS_SORT_KEY1=?,");
            sql.addSql("   UPS_SORT_ORDER1=?,");
            sql.addSql("   UPS_SORT_KEY2=?,");
            sql.addSql("   UPS_SORT_ORDER2=?,");
            sql.addSql("   UPS_EUID=?,");
            sql.addSql("   UPS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUpsSortKey1());
            sql.addIntValue(bean.getUpsSortOrder1());
            sql.addIntValue(bean.getUpsSortKey2());
            sql.addIntValue(bean.getUpsSortOrder2());
            sql.addIntValue(bean.getUpsEuid());
            sql.addDateValue(bean.getUpsEdate());
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
     * <p>Create CMN_USR_PRI_SORT_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrPriSortConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrPriSortConfModel __getCmnUsrPriSortConfFromRs(ResultSet rs) throws SQLException {
        CmnUsrPriSortConfModel bean = new CmnUsrPriSortConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUpsSortKey1(rs.getInt("UPS_SORT_KEY1"));
        bean.setUpsSortOrder1(rs.getInt("UPS_SORT_ORDER1"));
        bean.setUpsSortKey2(rs.getInt("UPS_SORT_KEY2"));
        bean.setUpsSortOrder2(rs.getInt("UPS_SORT_ORDER2"));
        bean.setUpsAuid(rs.getInt("UPS_AUID"));
        bean.setUpsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UPS_ADATE")));
        bean.setUpsEuid(rs.getInt("UPS_EUID"));
        bean.setUpsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UPS_EDATE")));
        return bean;
    }
}
