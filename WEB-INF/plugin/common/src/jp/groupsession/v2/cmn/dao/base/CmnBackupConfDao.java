package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_BACKUP_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBackupConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBackupConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnBackupConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBackupConfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_BACKUP_CONF Data Bindding JavaBean
     * @param bean CMN_BACKUP_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnBackupConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BACKUP_CONF(");
            sql.addSql("   BUC_INTERVAL,");
            sql.addSql("   BUC_DOW,");
            sql.addSql("   BUC_WEEK_MONTH,");
            sql.addSql("   BUC_GENERATION,");
            sql.addSql("   BUC_ZIPOUT,");
            sql.addSql("   BUC_AUID,");
            sql.addSql("   BUC_ADATE,");
            sql.addSql("   BUC_EUID,");
            sql.addSql("   BUC_EDATE");
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
            sql.addIntValue(bean.getBucInterval());
            sql.addIntValue(bean.getBucDow());
            sql.addIntValue(bean.getBucWeekMonth());
            sql.addIntValue(bean.getBucGeneration());
            sql.addIntValue(bean.getBucZipout());
            sql.addIntValue(bean.getBucAuid());
            sql.addDateValue(bean.getBucAdate());
            sql.addIntValue(bean.getBucEuid());
            sql.addDateValue(bean.getBucEdate());
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
     * <p>Update CMN_BACKUP_CONF Data Bindding JavaBean
     * @param bean CMN_BACKUP_CONF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBackupConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BACKUP_CONF");
            sql.addSql(" set ");
            sql.addSql("   BUC_INTERVAL=?,");
            sql.addSql("   BUC_DOW=?,");
            sql.addSql("   BUC_WEEK_MONTH=?,");
            sql.addSql("   BUC_GENERATION=?,");
            sql.addSql("   BUC_ZIPOUT=?,");
            sql.addSql("   BUC_EUID=?,");
            sql.addSql("   BUC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBucInterval());
            sql.addIntValue(bean.getBucDow());
            sql.addIntValue(bean.getBucWeekMonth());
            sql.addIntValue(bean.getBucGeneration());
            sql.addIntValue(bean.getBucZipout());
            sql.addIntValue(bean.getBucEuid());
            sql.addDateValue(bean.getBucEdate());

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
     * <p>Select CMN_BACKUP_CONF All Data
     * @return List in CMN_BACKUP_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnBackupConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBackupConfModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BUC_INTERVAL,");
            sql.addSql("   BUC_DOW,");
            sql.addSql("   BUC_WEEK_MONTH,");
            sql.addSql("   BUC_GENERATION,");
            sql.addSql("   BUC_ZIPOUT,");
            sql.addSql("   BUC_AUID,");
            sql.addSql("   BUC_ADATE,");
            sql.addSql("   BUC_EUID,");
            sql.addSql("   BUC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_BACKUP_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getCmnBackupConfFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return model;
    }

    /**
     * <p>Delete CMN_BACKUP_CONF
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BACKUP_CONF");

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
     * <p>Create CMN_BACKUP_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBackupConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnBackupConfModel __getCmnBackupConfFromRs(ResultSet rs) throws SQLException {
        CmnBackupConfModel bean = new CmnBackupConfModel();
        bean.setBucInterval(rs.getInt("BUC_INTERVAL"));
        bean.setBucDow(rs.getInt("BUC_DOW"));
        bean.setBucWeekMonth(rs.getInt("BUC_WEEK_MONTH"));
        bean.setBucGeneration(rs.getInt("BUC_GENERATION"));
        bean.setBucZipout(rs.getInt("BUC_ZIPOUT"));
        bean.setBucAuid(rs.getInt("BUC_AUID"));
        bean.setBucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUC_ADATE")));
        bean.setBucEuid(rs.getInt("BUC_EUID"));
        bean.setBucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUC_EDATE")));
        return bean;
    }
}
