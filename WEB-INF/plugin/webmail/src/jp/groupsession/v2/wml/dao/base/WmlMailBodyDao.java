package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlMailBodyModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_MAIL_BODY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailBodyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailBodyDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailBodyDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailBodyDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert WML_MAIL_BODY Data Bindding JavaBean
     * @param bean WML_MAIL_BODY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailBodyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_BODY(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMB_BODY,");
            sql.addSql("   WMB_CHARSET,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addLongValue(bean.getWmdMailnum());
            sql.addStrValue(bean.getWmbBody());
            sql.addStrValue(bean.getWmbCharset());
            sql.addIntValue(bean.getWacSid());

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
     * <p>Insert WML_MAIL_BODY Data Bindding JavaBean
     * @param beanList WML_MAIL_BODY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlMailBodyModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_BODY(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMB_BODY,");
            sql.addSql("   WMB_CHARSET,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (WmlMailBodyModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addStrValue(bean.getWmbBody());
                sql.addStrValue(bean.getWmbCharset());
                sql.addIntValue(bean.getWacSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_MAIL_BODY Data Bindding JavaBean
     * @param bean WML_MAIL_BODY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailBodyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" set ");
            sql.addSql("   WMB_BODY=?,");
            sql.addSql("   WMB_CHARSET=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWmbBody());
            sql.addStrValue(bean.getWmbCharset());
            //where
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
     * <p>Select WML_MAIL_BODY All Data
     * @return List in WML_MAIL_BODYModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailBodyModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailBodyModel> ret = new ArrayList<WmlMailBodyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMB_BODY,");
            sql.addSql("   WMB_CHARSET,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_BODY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailBodyFromRs(rs));
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
     * <p>Select WML_MAIL_BODY
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_MAIL_BODYModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailBodyModel select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailBodyModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMB_BODY,");
            sql.addSql("   WMB_CHARSET,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailBodyFromRs(rs);
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
     * <p>Delete WML_MAIL_BODY
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select WML_MAIL_BODY All Data
     * @return List in WML_MAIL_BODYModel
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_BODY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>Select WML_MAIL_BODY All Data
     * @return List in WML_MAIL_BODYModel
     * @throws SQLException SQL実行例外
     */
    public long maxMailNum() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WMD_MAILNUM) as MAX");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_BODY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("MAX");
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
     * <p>Select WML_MAIL_BODY All Data
     * @param from 開始
     * @param to 終了
     * @return List in WML_MAIL_BODYModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailBodyModel> selectPart(
            long from, long to) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailBodyModel> ret = new ArrayList<WmlMailBodyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMB_BODY,");
            sql.addSql("   WMB_CHARSET,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM >= ?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM <= ?");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc");

            sql.addLongValue(from);
            sql.addLongValue(to);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailBodyFromRs(rs));
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
     * <p>Create WML_MAIL_BODY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailBodyModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailBodyModel __getWmlMailBodyFromRs(ResultSet rs) throws SQLException {
        WmlMailBodyModel bean = new WmlMailBodyModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWmbBody(rs.getString("WMB_BODY"));
        bean.setWmbCharset(rs.getString("WMB_CHARSET"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
