package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.wml.model.base.WbmLabelRelationModel;

/**
 * <p>WBM_LABEL_RELATION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WbmLabelRelationDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WbmLabelRelationDao.class);

    /**
     * <p>Default Constructor
     */
    public WbmLabelRelationDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WbmLabelRelationDao(Connection con) {
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
            sql.addSql("drop table WBM_LABEL_RELATION");

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
            sql.addSql(" create table WBM_LABEL_RELATION (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WLB_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WMD_MAILNUM,WLB_SID)");
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
     * <p>Insert WBM_LABEL_RELATION Data Bindding JavaBean
     * @param bean WBM_LABEL_RELATION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WbmLabelRelationModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WBM_LABEL_RELATION(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWlbSid());
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
     * <p>Update WBM_LABEL_RELATION Data Bindding JavaBean
     * @param bean WBM_LABEL_RELATION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WbmLabelRelationModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WBM_LABEL_RELATION");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addLongValue(bean.getWmdMailnum());
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
     * <p>Select WBM_LABEL_RELATION All Data
     * @return List in WBM_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WbmLabelRelationModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WbmLabelRelationModel> ret = new ArrayList<WbmLabelRelationModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID");
            sql.addSql(" from ");
            sql.addSql("   WBM_LABEL_RELATION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWbmLabelRelationFromRs(rs));
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
     * <p>Select WBM_LABEL_RELATION
     * @param wmdMailnum WMD_MAILNUM
     * @param wlbSid WLB_SID
     * @return WBM_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public WbmLabelRelationModel select(long wmdMailnum, int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WbmLabelRelationModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID");
            sql.addSql(" from");
            sql.addSql("   WBM_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWbmLabelRelationFromRs(rs);
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
     * <p>Delete WBM_LABEL_RELATION
     * @param wmdMailnum WMD_MAILNUM
     * @param wlbSid WLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WBM_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
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
     * <p>Create WBM_LABEL_RELATION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WbmLabelRelationModel
     * @throws SQLException SQL実行例外
     */
    private WbmLabelRelationModel __getWbmLabelRelationFromRs(ResultSet rs) throws SQLException {
        WbmLabelRelationModel bean = new WbmLabelRelationModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWlbSid(rs.getInt("WLB_SID"));
        return bean;
    }
}
