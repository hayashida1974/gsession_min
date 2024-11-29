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
import jp.groupsession.v2.wml.model.base.WbmLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WBM_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WbmLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WbmLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public WbmLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WbmLabelDao(Connection con) {
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
            sql.addSql("drop table WBM_LABEL");

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
            sql.addSql(" create table WBM_LABEL (");
            sql.addSql("   WLB_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   WLB_NAME varchar(300) not null,");
            sql.addSql("   primary key (WLB_SID)");
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
     * <p>Insert WBM_LABEL Data Bindding JavaBean
     * @param bean WBM_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WbmLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WBM_LABEL(");
            sql.addSql("   WLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WLB_NAME");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWlbSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getWlbName());
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
     * <p>Update WBM_LABEL Data Bindding JavaBean
     * @param bean WBM_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WbmLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WBM_LABEL");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   WLB_NAME=?");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getWlbName());
            //where
            sql.addIntValue(bean.getWlbSid());

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
     * <p>Select WBM_LABEL All Data
     * @return List in WBM_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<WbmLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WbmLabelModel> ret = new ArrayList<WbmLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WLB_NAME");
            sql.addSql(" from ");
            sql.addSql("   WBM_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWbmLabelFromRs(rs));
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
     * <p>Select WBM_LABEL
     * @param wlbSid WLB_SID
     * @return WBM_LABELModel
     * @throws SQLException SQL実行例外
     */
    public WbmLabelModel select(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WbmLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WLB_NAME");
            sql.addSql(" from");
            sql.addSql("   WBM_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWbmLabelFromRs(rs);
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
     * <p>Delete WBM_LABEL
     * @param wlbSid WLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WBM_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

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
     * <p>Create WBM_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WbmLabelModel
     * @throws SQLException SQL実行例外
     */
    private WbmLabelModel __getWbmLabelFromRs(ResultSet rs) throws SQLException {
        WbmLabelModel bean = new WbmLabelModel();
        bean.setWlbSid(rs.getInt("WLB_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWlbName(rs.getString("WLB_NAME"));
        return bean;
    }
}
