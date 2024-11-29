package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnDatafolderModel;

/**
 * <p>CMN_DATAFOLDER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDatafolderDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnDatafolderDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnDatafolderDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnDatafolderDao(Connection con) {
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
            sql.addSql("drop table CMN_DATAFOLDER");

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
            sql.addSql(" create table CMN_DATAFOLDER (");
            sql.addSql("   CDF_NAME varchar(20) not null,");
            sql.addSql("   CDF_SIZE bigint not null,");
            sql.addSql("   CDF_SORT integer not null,");
            sql.addSql("   primary key (CDF_NAME)");
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
     * <p>Insert CMN_DATAFOLDER Data Bindding JavaBean
     * @param bean CMN_DATAFOLDER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnDatafolderModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_DATAFOLDER(");
            sql.addSql("   CDF_NAME,");
            sql.addSql("   CDF_SIZE,");
            sql.addSql("   CDF_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCdfName());
            sql.addLongValue(bean.getCdfSize());
            sql.addIntValue(bean.getCdfSort());
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
     * <p>Update CMN_DATAFOLDER Data Bindding JavaBean
     * @param bean CMN_DATAFOLDER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnDatafolderModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_DATAFOLDER");
            sql.addSql(" set ");
            sql.addSql("   CDF_SIZE=?,");
            sql.addSql("   CDF_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   CDF_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCdfSize());
            sql.addIntValue(bean.getCdfSort());
            //where
            sql.addStrValue(bean.getCdfName());

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
     * <p>Select CMN_DATAFOLDER All Data
     * @return List in CMN_DATAFOLDERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnDatafolderModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnDatafolderModel> ret = new ArrayList<CmnDatafolderModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CDF_NAME,");
            sql.addSql("   CDF_SIZE,");
            sql.addSql("   CDF_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_DATAFOLDER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnDatafolderFromRs(rs));
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
     * <p>Select CMN_DATAFOLDER
     * @param cdfName CDF_NAME
     * @return CMN_DATAFOLDERModel
     * @throws SQLException SQL実行例外
     */
    public CmnDatafolderModel select(String cdfName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnDatafolderModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CDF_NAME,");
            sql.addSql("   CDF_SIZE,");
            sql.addSql("   CDF_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_DATAFOLDER");
            sql.addSql(" where ");
            sql.addSql("   CDF_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cdfName);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnDatafolderFromRs(rs);
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
     * <p>Delete CMN_DATAFOLDER
     * @param cdfName CDF_NAME
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String cdfName) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_DATAFOLDER");
            sql.addSql(" where ");
            sql.addSql("   CDF_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cdfName);

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
     * <p>Create CMN_DATAFOLDER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnDatafolderModel
     * @throws SQLException SQL実行例外
     */
    private CmnDatafolderModel __getCmnDatafolderFromRs(ResultSet rs) throws SQLException {
        CmnDatafolderModel bean = new CmnDatafolderModel();
        bean.setCdfName(rs.getString("CDF_NAME"));
        bean.setCdfSize(rs.getLong("CDF_SIZE"));
        bean.setCdfSort(rs.getInt("CDF_SORT"));
        return bean;
    }
}
