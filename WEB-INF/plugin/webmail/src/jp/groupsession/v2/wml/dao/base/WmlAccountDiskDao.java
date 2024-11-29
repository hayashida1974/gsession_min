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
import jp.groupsession.v2.wml.model.base.WmlAccountDiskModel;

/**
 * <p>WML_ACCOUNT_DISK Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlAccountDiskDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountDiskDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountDiskDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountDiskDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_DISK");

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
            sql.addSql(" create table WML_ACCOUNT_DISK (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WDS_SIZE Date not null,");
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
     * <p>Insert WML_ACCOUNT_DISK Data Bindding JavaBean
     * @param bean WML_ACCOUNT_DISK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountDiskModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_DISK(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDS_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addLongValue(bean.getWdsSize());
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
     * <p>Update WML_ACCOUNT_DISK Data Bindding JavaBean
     * @param bean WML_ACCOUNT_DISK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountDiskModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_DISK");
            sql.addSql(" set ");
            sql.addSql("   WDS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWdsSize());
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
     * <p>Select WML_ACCOUNT_DISK All Data
     * @return List in WML_ACCOUNT_DISKModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountDiskModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountDiskModel> ret = new ArrayList<WmlAccountDiskModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_DISK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountDiskFromRs(rs));
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
     * <p>Select WML_ACCOUNT_DISK
     * @param wacSid WAC_SID
     * @return WML_ACCOUNT_DISKModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountDiskModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountDiskModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDS_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_DISK");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountDiskFromRs(rs);
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
     * <br>[機  能] アカウントのディスク使用量を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return アカウントのディスク使用量
     * @throws SQLException SQL実行例外
     */
    public long getUseDiskSize(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long useDiskSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDS_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_DISK");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                useDiskSize = rs.getLong("WDS_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return useDiskSize;
    }

    /**
     * <br>[機  能] アカウントのディスク使用量合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントのディスク使用量合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalUseDiskSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long useDiskSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(WDS_SIZE) as TOTAL_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_DISK");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                useDiskSize = rs.getLong("TOTAL_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return useDiskSize;
    }

    /**
     * <p>Delete WML_ACCOUNT_DISK
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
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
            sql.addSql("   WML_ACCOUNT_DISK");
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
     * <p>Create WML_ACCOUNT_DISK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountDiskModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountDiskModel __getWmlAccountDiskFromRs(ResultSet rs) throws SQLException {
        WmlAccountDiskModel bean = new WmlAccountDiskModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWdsSize(rs.getLong("WDS_SIZE"));
        return bean;
    }
}
