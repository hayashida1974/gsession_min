package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;

/**
 * <p>SML_ACCOUNT_DISK Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAccountDiskDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountDiskDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlAccountDiskDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountDiskDao(Connection con) {
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
            sql.addSql("drop table SML_ACCOUNT_DISK");

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
            sql.addSql(" create table SML_ACCOUNT_DISK (");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   SDS_SIZE Date not null,");
            sql.addSql("   primary key (SAC_SID)");
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
     * <p>Insert SML_ACCOUNT_DISK Data Bindding JavaBean
     * @param bean SML_ACCOUNT_DISK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAccountDiskModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_DISK(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SDS_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addLongValue(bean.getSdsSize());
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
     * <p>Update SML_ACCOUNT_DISK Data Bindding JavaBean
     * @param bean SML_ACCOUNT_DISK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAccountDiskModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" set ");
            sql.addSql("   SDS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getSdsSize());
            //where
            sql.addIntValue(bean.getSacSid());

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
     * <br>[機  能] 指定したアカウントの容量を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param sabun 差分容量
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateSabun(int sacSid, long sabun) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" set ");
            sql.addSql("   SDS_SIZE = SDS_SIZE + ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(sabun);
            sql.addIntValue(sacSid);

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
     * <p>Select SML_ACCOUNT_DISK All Data
     * @return List in SML_ACCOUNT_DISKModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountDiskModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountDiskModel> ret = new ArrayList<SmlAccountDiskModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SDS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_DISK");

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
     * <p>Select SML_ACCOUNT_DISK
     * @param wacSid SAC_SID
     * @return SML_ACCOUNT_DISKModel
     * @throws SQLException SQL実行例外
     */
    public SmlAccountDiskModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAccountDiskModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SDS_SIZE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

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
            sql.addSql("   SDS_SIZE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                useDiskSize = rs.getLong("SDS_SIZE");
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
            sql.addSql("   sum(SML_ACCOUNT_DISK.SDS_SIZE) as TOTAL_SIZE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT,");
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" where");
            sql.addSql("   SML_ACCOUNT.SAC_SID = SML_ACCOUNT_DISK.SAC_SID");
            sql.addSql(" and");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_ACCOUNT.SAC_SID not in (");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            sql.addIntValue(GSConst.SYSTEM_USER_MAIL);

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
     * <p>Delete SML_ACCOUNT_DISK
     * @param wacSid SAC_SID
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
            sql.addSql("   SML_ACCOUNT_DISK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

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
     * <p>Create SML_ACCOUNT_DISK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAccountDiskModel
     * @throws SQLException SQL実行例外
     */
    private SmlAccountDiskModel __getWmlAccountDiskFromRs(ResultSet rs) throws SQLException {
        SmlAccountDiskModel bean = new SmlAccountDiskModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSdsSize(rs.getLong("SDS_SIZE"));
        return bean;
    }
}
