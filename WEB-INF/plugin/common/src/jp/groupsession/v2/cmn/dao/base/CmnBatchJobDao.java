package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_BATCHJOB Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBatchJobDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBatchJobDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnBatchJobDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBatchJobDao(Connection con) {
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
            sql.addSql("drop table CMN_BATCHJOB");

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
            sql.addSql(" create table CMN_BATCHJOB (");
            sql.addSql("   BAT_FR_DATE NUMBER(4,0) not null,");
            sql.addSql("   BAT_ADUSER NUMBER(4,0) not null,");
            sql.addSql("   BAT_ADDATE varchar(8) not null,");
            sql.addSql("   BAT_UPUSER NUMBER(4,0) not null,");
            sql.addSql("   BAT_UPDATE varchar(8) not null");
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
     * <p>Insert CMN_BATCHJOB Data Bindding JavaBean
     * @param bean CMN_BATCHJOB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnBatchJobModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BATCHJOB(");
            sql.addSql("   BAT_FR_DATE,");
            sql.addSql("   BAT_ADUSER,");
            sql.addSql("   BAT_ADDATE,");
            sql.addSql("   BAT_UPUSER,");
            sql.addSql("   BAT_UPDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBatFrDate());
            sql.addIntValue(bean.getBatAduser());
            sql.addDateValue(bean.getBatAddate());
            sql.addIntValue(bean.getBatUpuser());
            sql.addDateValue(bean.getBatUpdate());
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
     * <p>Update CMN_BATCHJOB Data Bindding JavaBean
     * @param bean CMN_BATCHJOB Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBatchJobModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BATCHJOB");
            sql.addSql(" set ");
            sql.addSql("   BAT_FR_DATE=?,");
            sql.addSql("   BAT_UPUSER=?,");
            sql.addSql("   BAT_UPDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBatFrDate());
            sql.addIntValue(bean.getBatUpuser());
            sql.addDateValue(bean.getBatUpdate());

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
     * <p>Select CMN_BATCHJOB
     * @return CMN_BATCHJOBModel
     * @throws SQLException SQL実行例外
     */
    public CmnBatchJobModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBatchJobModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BAT_FR_DATE,");
            sql.addSql("   BAT_ADUSER,");
            sql.addSql("   BAT_ADDATE,");
            sql.addSql("   BAT_UPUSER,");
            sql.addSql("   BAT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_BATCHJOB");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnBatchjobFromRs(rs);
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
     * <p>Delete CMN_BATCHJOB
     * @param bean CMN_BATCHJOB Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(CmnBatchJobModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BATCHJOB");

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
     * <p>Create CMN_BATCHJOB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBatchjobModel
     * @throws SQLException SQL実行例外
     */
    private CmnBatchJobModel __getCmnBatchjobFromRs(ResultSet rs) throws SQLException {
        CmnBatchJobModel bean = new CmnBatchJobModel();
        bean.setBatFrDate(rs.getInt("BAT_FR_DATE"));
        bean.setBatAduser(rs.getInt("BAT_ADUSER"));
        bean.setBatAddate(UDate.getInstanceTimestamp(rs.getTimestamp("BAT_ADDATE")));
        bean.setBatUpuser(rs.getInt("BAT_UPUSER"));
        bean.setBatUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("BAT_UPDATE")));
        return bean;
    }
}
