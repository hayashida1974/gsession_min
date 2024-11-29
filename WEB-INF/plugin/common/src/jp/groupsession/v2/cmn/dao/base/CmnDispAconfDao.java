package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnDispAconfModel;

/**
 * <p>CMN_DISP_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDispAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnDispAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnDispAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnDispAconfDao(Connection con) {
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
            sql.addSql("drop table CMN_DISP_ACONF");

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
            sql.addSql(" create table CMN_DISP_ACONF (");
            sql.addSql("   CDA_ROKUYOU_KBN integer not null,");
            sql.addSql("   CDA_ROKUYOU integer not null");
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
     * <p>Insert CMN_DISP_ACONF Data Bindding JavaBean
     * @param bean CMN_DISP_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnDispAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_DISP_ACONF(");
            sql.addSql("   CDA_ROKUYOU_KBN,");
            sql.addSql("   CDA_ROKUYOU");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCdaRokuyouKbn());
            sql.addIntValue(bean.getCdaRokuyou());
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
     * <p>Update CMN_DISP_ACONF Data Bindding JavaBean
     * @param bean CMN_DISP_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnDispAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_DISP_ACONF");
            sql.addSql(" set ");
            sql.addSql("   CDA_ROKUYOU_KBN=?,");
            sql.addSql("   CDA_ROKUYOU=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCdaRokuyouKbn());
            sql.addIntValue(bean.getCdaRokuyou());

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
     * <p>Select CMN_DISP_ACONF All Data
     * @return List in CMN_DISP_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnDispAconfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnDispAconfModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CDA_ROKUYOU_KBN,");
            sql.addSql("   CDA_ROKUYOU");
            sql.addSql(" from ");
            sql.addSql("   CMN_DISP_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getCmnDispAconfFromRs(rs);
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
     * <p>Create CMN_DISP_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnDispAconfModel
     * @throws SQLException SQL実行例外
     */
    private CmnDispAconfModel __getCmnDispAconfFromRs(ResultSet rs) throws SQLException {
        CmnDispAconfModel bean = new CmnDispAconfModel();
        bean.setCdaRokuyouKbn(rs.getInt("CDA_ROKUYOU_KBN"));
        bean.setCdaRokuyou(rs.getInt("CDA_ROKUYOU"));
        return bean;
    }
}
