package jp.groupsession.v2.cmn.dao.base;

import jp.co.sjts.util.date.UDate;
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
import jp.groupsession.v2.cmn.model.base.CmnInfoSendDateModel;

/**
 * <p>CMN_INFO_SEND_DATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoSendDateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnInfoSendDateDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnInfoSendDateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnInfoSendDateDao(Connection con) {
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
            sql.addSql("drop table CMN_INFO_SEND_DATE");

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
            sql.addSql(" create table CMN_INFO_SEND_DATE (");
            sql.addSql("   CSD_DATE timestamp not null");
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
     * <p>Insert CMN_INFO_SEND_DATE Data Bindding JavaBean
     * @param bean CMN_INFO_SEND_DATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnInfoSendDateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_INFO_SEND_DATE(");
            sql.addSql("   CSD_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getCsdDate());
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
     * <p>Insert CMN_INFO_SEND_DATE Data
     * @param date CMN_INFO_SEND_DATE
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int insert(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_INFO_SEND_DATE(");
            sql.addSql("   CSD_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?");
            sql.addSql(" )");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(date);

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
     * <p>Update CMN_INFO_SEND_DATE Data
     * @param date CMN_INFO_SEND_DATE
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_INFO_SEND_DATE");
            sql.addSql(" set ");
            sql.addSql("   CSD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(date);

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
     * <p>Update CMN_INFO_SEND_DATE Data Bindding JavaBean
     * @param bean CMN_INFO_SEND_DATE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnInfoSendDateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_INFO_SEND_DATE");
            sql.addSql(" set ");
            sql.addSql("   CSD_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getCsdDate());

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
     * <p>Select CMN_INFO_SEND_DATE Data
     * @return List in CMN_INFO_SEND_DATEModel
     * @throws SQLException SQL実行例外
     */
    public UDate getSendDate() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UDate ret = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CSD_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_SEND_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = UDate.getInstanceTimestamp(rs.getTimestamp("CSD_DATE"));
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
     * <p>Select CMN_INFO_SEND_DATE All Data
     * @return List in CMN_INFO_SEND_DATEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnInfoSendDateModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoSendDateModel> ret = new ArrayList<CmnInfoSendDateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CSD_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_SEND_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoSendDateFromRs(rs));
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
     * <p>Create CMN_INFO_SEND_DATE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnInfoSendDateModel
     * @throws SQLException SQL実行例外
     */
    private CmnInfoSendDateModel __getCmnInfoSendDateFromRs(ResultSet rs) throws SQLException {
        CmnInfoSendDateModel bean = new CmnInfoSendDateModel();
        bean.setCsdDate(UDate.getInstanceTimestamp(rs.getTimestamp("CSD_DATE")));
        return bean;
    }
}
