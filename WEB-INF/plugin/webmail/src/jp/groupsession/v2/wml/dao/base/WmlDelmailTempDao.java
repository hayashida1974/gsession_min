package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlDelmailTempModel;

/**
 * <p>WML_DELMAIL_TEMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDelmailTempDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDelmailTempDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDelmailTempDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDelmailTempDao(Connection con) {
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
            sql.addSql("drop table WML_DELMAIL_TEMP");

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
            sql.addSql(" create table WML_DELMAIL_TEMP (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   WMD_MAILNUM bigint not null,");
            sql.addSql("   WAC_SID integer not null,");
            sql.addSql("   WDT_ADATE timestamp not null,");
            sql.addSql("   primary key (USR_SID,WMD_MAILNUM)");
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
     * <p>Insert WML_DELMAIL_TEMP Data Bindding JavaBean
     * @param bean WML_DELMAIL_TEMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDelmailTempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DELMAIL_TEMP(");
            sql.addSql("   USR_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDT_ADATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWacSid());
            sql.addDateValue(bean.getWdtAdate());
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
     * <p>Update WML_DELMAIL_TEMP Data Bindding JavaBean
     * @param bean WML_DELMAIL_TEMP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDelmailTempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" set ");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WDT_ADATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addDateValue(bean.getWdtAdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getWmdMailnum());

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
     * <p>Select WML_DELMAIL_TEMP All Data
     * @return List in WML_DELMAIL_TEMPModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDelmailTempModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDelmailTempModel> ret = new ArrayList<WmlDelmailTempModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDT_ADATE");
            sql.addSql(" from ");
            sql.addSql("   WML_DELMAIL_TEMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDelmailTempFromRs(rs));
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
     * <p>Select WML_DELMAIL_TEMP
     * @param usrSid USR_SID
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_DELMAIL_TEMPModel
     * @throws SQLException SQL実行例外
     */
    public WmlDelmailTempModel select(int usrSid, long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDelmailTempModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDT_ADATE");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDelmailTempFromRs(rs);
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
     * <p>Delete WML_DELMAIL_TEMP
     * @param usrSid USR_SID
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addLongValue(wmdMailnum);

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
     * <br>[機  能] 指定したユーザが登録した削除対象メール一覧を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <br>[機  能] 登録されている情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteAll() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");

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
     * <br>[機  能] 指定したユーザが登録した削除対象メール一覧が存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return count 件数
     * @throws SQLException SQL実行例外
     */
    public int getDataCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create WML_DELMAIL_TEMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDelmailTempModel
     * @throws SQLException SQL実行例外
     */
    private WmlDelmailTempModel __getWmlDelmailTempFromRs(ResultSet rs) throws SQLException {
        WmlDelmailTempModel bean = new WmlDelmailTempModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWdtAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WDT_ADATE")));
        return bean;
    }
}
