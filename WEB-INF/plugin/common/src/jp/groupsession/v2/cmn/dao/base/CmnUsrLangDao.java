package jp.groupsession.v2.cmn.dao.base;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrLangModel;

/**
 * <p>CMN_USR_THEME Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrLangDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrLangDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrLangDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrLangDao(Connection con) {
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
            sql.addSql("drop table CMN_USR_LANG");

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
            sql.addSql(" create table CMN_USR_LANG (");
            sql.addSql("   USR_SID      integer       not null,");
            sql.addSql("   CUL_COUNTRY  varchar (20)  not null,");
            sql.addSql("   CUL_AUID     integer       not null,");
            sql.addSql("   CUL_ADATE    timestamp     not null,");
            sql.addSql("   CUL_EUID     integer       not null,");
            sql.addSql("   CUL_EDATE    timestamp     not null,");
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
     * <p>Insert CMN_USR_LANG Data Bindding JavaBean
     * @param bean CMN_USR_LANG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrLangModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_LANG(");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUL_COUNTRY,");
            sql.addSql("   CUL_AUID,");
            sql.addSql("   CUL_ADATE,");
            sql.addSql("   CUL_EUID,");
            sql.addSql("   CUL_EDATE");
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
            sql.addStrValue(bean.getCulCountry());
            sql.addIntValue(bean.getCulAuid());
            sql.addDateValue(bean.getCulAdate());
            sql.addIntValue(bean.getCulEuid());
            sql.addDateValue(bean.getCulEdate());
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
     * <p>Update CMN_USR_LANG Data Bindding JavaBean
     * @param bean CMN_USR_LANG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrLangModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_LANG");
            sql.addSql(" set ");
            sql.addSql("   CUL_COUNTRY=?,");
            sql.addSql("   CUL_AUID=?,");
            sql.addSql("   CUL_ADATE=?,");
            sql.addSql("   CUL_EUID=?,");
            sql.addSql("   CUL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCulCountry());
            sql.addIntValue(bean.getCulAuid());
            sql.addDateValue(bean.getCulAdate());
            sql.addIntValue(bean.getCulEuid());
            sql.addDateValue(bean.getCulEdate());
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
     * <p>Select CMN_USR_LANG All Data
     * @return List in CMN_USR_LANGModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrLangModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrLangModel> ret = new ArrayList<CmnUsrLangModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUL_COUNTRY,");
            sql.addSql("   CUL_AUID,");
            sql.addSql("   CUL_ADATE,");
            sql.addSql("   CUL_EUID,");
            sql.addSql("   CUL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_LANG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrLangFromRs(rs));
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
     * <p>Select CMN_USR_LANG
     * @param usrSid USR_SID
     * @return CMN_USR_LANGModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrLangModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrLangModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUL_COUNTRY,");
            sql.addSql("   CUL_AUID,");
            sql.addSql("   CUL_ADATE,");
            sql.addSql("   CUL_EUID,");
            sql.addSql("   CUL_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_LANG");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrLangFromRs(rs);
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
     * <p>Delete CMN_USR_THEME
     * @param usrSid USR_SID
     * @return count
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
            sql.addSql("   CMN_USR_LANG");
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
     * <p>Create CMN_USR_LANG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrThemeModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrLangModel __getCmnUsrLangFromRs(ResultSet rs) throws SQLException {
        CmnUsrLangModel bean = new CmnUsrLangModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCulCountry(rs.getString("CUL_COUNTRY"));
        bean.setCulAuid(rs.getInt("CUL_AUID"));
        bean.setCulAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUL_ADATE")));
        bean.setCulEuid(rs.getInt("CUL_EUID"));
        bean.setCulEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUL_EDATE")));

        return bean;
    }
}