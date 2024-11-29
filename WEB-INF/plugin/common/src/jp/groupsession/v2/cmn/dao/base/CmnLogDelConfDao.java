package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLogDelConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_LOG_DEL_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogDelConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLogDelConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLogDelConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLogDelConfDao(Connection con) {
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
            sql.addSql("drop table CMN_LOG_DEL_CONF");

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
            sql.addSql(" create table CMN_LOG_DEL_CONF (");
            sql.addSql("   LDC_ADL_KBN NUMBER(10,0) not null,");
            sql.addSql("   LDC_ADL_YEAR NUMBER(10,0),");
            sql.addSql("   LDC_ADL_MONTH NUMBER(10,0),");
            sql.addSql("   LDC_AUID NUMBER(10,0) not null,");
            sql.addSql("   LDC_ADATE varchar(23) not null,");
            sql.addSql("   LDC_EUID NUMBER(10,0) not null,");
            sql.addSql("   LDC_EDATE varchar(23) not null");
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
     * <p>Insert CMN_LOG_DEL_CONF Data Bindding JavaBean
     * @param bean CMN_LOG_DEL_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLogDelConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOG_DEL_CONF(");
            sql.addSql("   LDC_ADL_KBN,");
            sql.addSql("   LDC_ADL_YEAR,");
            sql.addSql("   LDC_ADL_MONTH,");
            sql.addSql("   LDC_AUID,");
            sql.addSql("   LDC_ADATE,");
            sql.addSql("   LDC_EUID,");
            sql.addSql("   LDC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLdcAdlKbn());
            sql.addIntValue(bean.getLdcAdlYear());
            sql.addIntValue(bean.getLdcAdlMonth());
            sql.addIntValue(bean.getLdcAuid());
            sql.addDateValue(bean.getLdcAdate());
            sql.addIntValue(bean.getLdcEuid());
            sql.addDateValue(bean.getLdcEdate());
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
     * <p>Update CMN_LOG_DEL_CONF Data Bindding JavaBean
     * @param bean CMN_LOG_DEL_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLogDelConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOG_DEL_CONF");
            sql.addSql(" set ");
            sql.addSql("   LDC_ADL_KBN=?,");
            sql.addSql("   LDC_ADL_YEAR=?,");
            sql.addSql("   LDC_ADL_MONTH=?,");
            sql.addSql("   LDC_AUID=?,");
            sql.addSql("   LDC_ADATE=?,");
            sql.addSql("   LDC_EUID=?,");
            sql.addSql("   LDC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLdcAdlKbn());
            sql.addIntValue(bean.getLdcAdlYear());
            sql.addIntValue(bean.getLdcAdlMonth());
            sql.addIntValue(bean.getLdcAuid());
            sql.addDateValue(bean.getLdcAdate());
            sql.addIntValue(bean.getLdcEuid());
            sql.addDateValue(bean.getLdcEdate());

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
     * <p>オペレーションログ自動削除状態を更新する
     * @param bean CMN_LOG_DEL_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateOpLogDel(CmnLogDelConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOG_DEL_CONF");
            sql.addSql(" set ");
            sql.addSql("   LDC_ADL_KBN=?,");
            sql.addSql("   LDC_ADL_YEAR=?,");
            sql.addSql("   LDC_ADL_MONTH=?,");
            sql.addSql("   LDC_EUID=?,");
            sql.addSql("   LDC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLdcAdlKbn());
            sql.addIntValue(bean.getLdcAdlYear());
            sql.addIntValue(bean.getLdcAdlMonth());
            sql.addIntValue(bean.getLdcEuid());
            sql.addDateValue(bean.getLdcEdate());

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
     * <p>Select CMN_LOG_DEL_CONF All Data
     * @return List in CMN_LOG_DEL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnLogDelConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnLogDelConfModel> ret = new ArrayList<CmnLogDelConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LDC_ADL_KBN,");
            sql.addSql("   LDC_ADL_YEAR,");
            sql.addSql("   LDC_ADL_MONTH,");
            sql.addSql("   LDC_AUID,");
            sql.addSql("   LDC_ADATE,");
            sql.addSql("   LDC_EUID,");
            sql.addSql("   LDC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG_DEL_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogDelConfFromRs(rs));
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
     * <p>オペレーションログ自動削除状態を取得する
     * @return List in CMN_LOG_DEL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnLogDelConfModel getOpeLogDelConf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnLogDelConfModel ret = new CmnLogDelConfModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LDC_ADL_KBN,");
            sql.addSql("   LDC_ADL_YEAR,");
            sql.addSql("   LDC_ADL_MONTH,");
            sql.addSql("   LDC_AUID,");
            sql.addSql("   LDC_ADATE,");
            sql.addSql("   LDC_EUID,");
            sql.addSql("   LDC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG_DEL_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnLogDelConfFromRs(rs);
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
     * <p>Create CMN_LOG_DEL_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLogDelConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnLogDelConfModel __getCmnLogDelConfFromRs(ResultSet rs) throws SQLException {
        CmnLogDelConfModel bean = new CmnLogDelConfModel();
        bean.setLdcAdlKbn(rs.getInt("LDC_ADL_KBN"));
        bean.setLdcAdlYear(rs.getInt("LDC_ADL_YEAR"));
        bean.setLdcAdlMonth(rs.getInt("LDC_ADL_MONTH"));
        bean.setLdcAuid(rs.getInt("LDC_AUID"));
        bean.setLdcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LDC_ADATE")));
        bean.setLdcEuid(rs.getInt("LDC_EUID"));
        bean.setLdcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LDC_EDATE")));
        return bean;
    }
}
