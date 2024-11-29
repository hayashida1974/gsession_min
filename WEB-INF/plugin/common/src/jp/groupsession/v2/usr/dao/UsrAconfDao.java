package jp.groupsession.v2.usr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.usr.model.UsrAconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>USR_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class UsrAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UsrAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public UsrAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public UsrAconfDao(Connection con) {
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
            sql.addSql("drop table USR_ACONF");

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
            sql.addSql(" create table USR_ACONF (");
            sql.addSql("   UAD_EXPORT NUMBER(10,0),");
            sql.addSql("   UAD_AUID NUMBER(10,0),");
            sql.addSql("   UAD_ADATE varchar(26),");
            sql.addSql("   UAD_EUID NUMBER(10,0),");
            sql.addSql("   UAD_EDATE varchar(26)");
            sql.addSql("   UAD_QR_KBN INTEGER");

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
     * <p>Insert USR_ACONF Data Bindding JavaBean
     * @param bean USR_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(UsrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" USR_ACONF(");
            sql.addSql("   UAD_EXPORT,");
            sql.addSql("   UAD_AUID,");
            sql.addSql("   UAD_ADATE,");
            sql.addSql("   UAD_EUID,");
            sql.addSql("   UAD_EDATE,");
            sql.addSql("   UAD_QR_KBN");
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
            sql.addIntValue(bean.getUadExport());
            sql.addIntValue(bean.getUadAuid());
            sql.addDateValue(bean.getUadAdate());
            sql.addIntValue(bean.getUadEuid());
            sql.addDateValue(bean.getUadEdate());
            sql.addIntValue(bean.getUadQrKbn());
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
     * <p>Update USR_ACONF Data Bindding JavaBean
     * @param bean USR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(UsrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   USR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   UAD_EXPORT=?,");
            sql.addSql("   UAD_AUID=?,");
            sql.addSql("   UAD_ADATE=?,");
            sql.addSql("   UAD_EUID=?,");
            sql.addSql("   UAD_EDATE=?,");
            sql.addSql("   UAD_QR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUadExport());
            sql.addIntValue(bean.getUadAuid());
            sql.addDateValue(bean.getUadAdate());
            sql.addIntValue(bean.getUadEuid());
            sql.addDateValue(bean.getUadEdate());
            sql.addIntValue(bean.getUadQrKbn());

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
     * <p>基本設定機能の使用制限情報を更新します。
     * @param bean USR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBasicConfig(UsrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   USR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   UAD_EXPORT=?,");
            sql.addSql("   UAD_EUID=?,");
            sql.addSql("   UAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUadExport());
            sql.addIntValue(bean.getUadEuid());
            sql.addDateValue(bean.getUadEdate());

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
     * <p>Select USR_ACONF All Data
     * @return List in USR_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public UsrAconfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UsrAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   UAD_EXPORT,");
            sql.addSql("   UAD_AUID,");
            sql.addSql("   UAD_ADATE,");
            sql.addSql("   UAD_EUID,");
            sql.addSql("   UAD_EDATE,");
            sql.addSql("   UAD_QR_KBN");
            sql.addSql(" from ");
            sql.addSql("   USR_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getUsrAconfFromRs(rs);
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
     * <p>QRコード表示設定の更新を行います。
     * @param  bean USR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateQrDispKbn(UsrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   USR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   UAD_EUID=?,");
            sql.addSql("   UAD_EDATE=?,");
            sql.addSql("   UAD_QR_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUadEuid());
            sql.addDateValue(bean.getUadEdate());
            sql.addIntValue(bean.getUadQrKbn());

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
     * <p>Create USR_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created UsrAconfModel
     * @throws SQLException SQL実行例外
     */
    private UsrAconfModel __getUsrAconfFromRs(ResultSet rs) throws SQLException {
        UsrAconfModel bean = new UsrAconfModel();
        bean.setUadExport(rs.getInt("UAD_EXPORT"));
        bean.setUadAuid(rs.getInt("UAD_AUID"));
        bean.setUadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UAD_ADATE")));
        bean.setUadEuid(rs.getInt("UAD_EUID"));
        bean.setUadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UAD_EDATE")));
        bean.setUadQrKbn(rs.getInt("UAD_QR_KBN"));
        return bean;
    }
}
