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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlManageNoticeModel;

/**
 * <p>WML_MANAGE_NOTICE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlManageNoticeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlManageNoticeDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlManageNoticeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlManageNoticeDao(Connection con) {
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
            sql.addSql("drop table WML_MANAGE_NOTICE");

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
            sql.addSql(" create table WML_MANAGE_NOTICE (");
            sql.addSql("   WAC_SID integer not null,");
            sql.addSql("   WMN_RCVFAILED_FLG integer not null,");
            sql.addSql("   primary key (WAC_SID)");
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
     * <p>Insert WML_MANAGE_NOTICE Data Bindding JavaBean
     * @param bean WML_MANAGE_NOTICE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlManageNoticeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MANAGE_NOTICE(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMN_RCVFAILED_FLG");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWmnRcvfailedFlg());
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
     * <p>Update WML_MANAGE_NOTICE Data Bindding JavaBean
     * @param bean WML_MANAGE_NOTICE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlManageNoticeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MANAGE_NOTICE");
            sql.addSql(" set ");
            sql.addSql("   WMN_RCVFAILED_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWmnRcvfailedFlg());
            //where
            sql.addIntValue(bean.getWacSid());

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
     * <p>アカウントの受信失敗通知受信フラグを更新
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateForAccounts() throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MANAGE_NOTICE");
            sql.addSql(" set ");
            sql.addSql("   WMN_RCVFAILED_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID in (");
            sql.addSql("     select ");
            sql.addSql("       WAC_SID");
            sql.addSql("     from ");
            sql.addSql("       WML_ACCOUNT");
            sql.addSql("     where ");
            sql.addSql("       WAC_JKBN = ?");
            sql.addSql("   ) ");

            sql.addIntValue(GSConstWebmail.WML_FAILEDNOTICE_NOTRECEIVED);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);


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
     * <p>Select WML_MANAGE_NOTICE All Data
     * @return List in WML_MANAGE_NOTICEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlManageNoticeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlManageNoticeModel> ret =
                new ArrayList<WmlManageNoticeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMN_RCVFAILED_FLG");
            sql.addSql(" from ");
            sql.addSql("   WML_MANAGE_NOTICE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlManageNoticeFromRs(rs));
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
     * <p>Select WML_MANAGE_NOTICE
     * @param wacSid WAC_SID
     * @return WML_MANAGE_NOTICEModel
     * @throws SQLException SQL実行例外
     */
    public WmlManageNoticeModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlManageNoticeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMN_RCVFAILED_FLG");
            sql.addSql(" from");
            sql.addSql("   WML_MANAGE_NOTICE");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlManageNoticeFromRs(rs);
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
     * <p>Delete WML_MANAGE_NOTICE
     * @param wacSid WAC_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MANAGE_NOTICE");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <p>Create WML_MANAGE_NOTICE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlManageNoticeModel
     * @throws SQLException SQL実行例外
     */
    private WmlManageNoticeModel __getWmlManageNoticeFromRs(ResultSet rs) throws SQLException {
        WmlManageNoticeModel bean = new WmlManageNoticeModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWmnRcvfailedFlg(rs.getInt("WMN_RCVFAILED_FLG"));
        return bean;
    }
}
