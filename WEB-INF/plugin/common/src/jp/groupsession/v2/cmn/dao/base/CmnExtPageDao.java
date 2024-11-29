package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnExtPageModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_EXT_PAGE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnExtPageDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnExtPageDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnExtPageDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnExtPageDao(Connection con) {
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
            sql.addSql("drop table CMN_EXT_PAGE");

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
            sql.addSql(" create table CMN_EXT_PAGE (");
            sql.addSql("   CEP_LIMIT_DSP NUMBER(4,0) not null,");
            sql.addSql("   CEP_AUID NUMBER(4,0) not null,");
            sql.addSql("   CEP_ADATE varchar(8) not null,");
            sql.addSql("   CEP_EUID NUMBER(4,0) not null,");
            sql.addSql("   CEP_EDATE varchar(8) not null");
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
     * <p>Insert CMN_EXT_PAGE Data Bindding JavaBean
     * @param bean CMN_EXT_PAGE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int insert(CmnExtPageModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_EXT_PAGE(");
            sql.addSql("   CEP_LIMIT_DSP,");
            sql.addSql("   CEP_AUID,");
            sql.addSql("   CEP_ADATE,");
            sql.addSql("   CEP_EUID,");
            sql.addSql("   CEP_EDATE");
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
            sql.addIntValue(bean.getCepLimitDsp());
            sql.addIntValue(bean.getCepAuid());
            sql.addDateValue(bean.getCepAdate());
            sql.addIntValue(bean.getCepEuid());
            sql.addDateValue(bean.getCepEdate());

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
     * <p> CMN_EXT_PAGEテーブルのレコードを更新します。
     * @param bean CMN_EXT_PAGEモデル
     * @return 更新件数
     * @throws SQLException SQL実行例外
     * */
    public int update(CmnExtPageModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_EXT_PAGE");
            sql.addSql(" set ");
            sql.addSql("   CEP_LIMIT_DSP=?,");
            sql.addSql("   CEP_AUID=?,");
            sql.addSql("   CEP_ADATE=?,");
            sql.addSql("   CEP_EUID=?,");
            sql.addSql("   CEP_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCepLimitDsp());
            sql.addIntValue(bean.getCepAuid());
            sql.addDateValue(bean.getCepAdate());
            sql.addIntValue(bean.getCepEuid());
            sql.addDateValue(bean.getCepEdate());

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
     * <p> CMN_EXT_PAGEテーブルの全レコードを取得します。
     * @return CmnExtPageModel
     * @throws SQLException SQL実行例外
     */
    public CmnExtPageModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CmnExtPageModel ret = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CEP_LIMIT_DSP,");
            sql.addSql("   CEP_AUID,");
            sql.addSql("   CEP_ADATE,");
            sql.addSql("   CEP_EUID,");
            sql.addSql("   CEP_EDATE");
            sql.addSql("  from  ");
            sql.addSql("    CMN_EXT_PAGE");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCmnExtPageFromRs(rs);
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
     * <p>Create CMN_EXT_PAGE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnContmModel
     * @throws SQLException SQL実行例外
     */
    private CmnExtPageModel __getCmnExtPageFromRs(ResultSet rs) throws SQLException {
        CmnExtPageModel bean = new CmnExtPageModel();
        bean.setCepLimitDsp(rs.getInt("CEP_LIMIT_DSP"));
        bean.setCepAuid(rs.getInt("CEP_AUID"));
        bean.setCepAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CEP_ADATE")));
        bean.setCepEuid(rs.getInt("CEP_EUID"));
        bean.setCepEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CEP_EDATE")));

        return bean;
    }
}