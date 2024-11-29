package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLangModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_LANG Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLangDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLangDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLangDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLangDao(Connection con) {
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
            sql.addSql("drop table CMN_LANG");

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
            sql.addSql(" create table CMN_LANG(");
            sql.addSql("   LNG_SID       integer      not null,");
            sql.addSql("   LNG_NAME      varchar (20) not null,");
            sql.addSql("   LNG_CODE      varchar (20) not null,");
            sql.addSql("   LNG_COUNTRY   varchar (20) not null,");
            sql.addSql("   primary key (LNG_SID)");
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
     * <p>Insert CMN_LANG Data Bindding JavaBean
     * @param bean CMN_LANG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLangModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LANG(");
            sql.addSql("   LNG_SID,");
            sql.addSql("   LNG_NAME,");
            sql.addSql("   LNG_CODE,");
            sql.addSql("   LNG_COUNTRY");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLngSid());
            sql.addStrValue(bean.getLngName());
            sql.addStrValue(bean.getLngCode());
            sql.addStrValue(bean.getLngCountry());
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
     * <p>Update CMN_LANG Data Bindding JavaBean
     * @param bean CMN_LANG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLangModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LANG");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   LNG_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getLngSid());

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
     * <p>Select CMN_LANG All Data
     * @return List in CMN_LANGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLangModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLangModel> ret = new ArrayList<CmnLangModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LNG_SID,");
            sql.addSql("   LNG_CODE,");
            sql.addSql("   LNG_NAME,");
            sql.addSql("   LNG_COUNTRY");
            sql.addSql(" from ");
            sql.addSql("   CMN_LANG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLangFromRs(rs));
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
     * <p>Select CMN_LANG
     * @param lngSid LNG_SID
     * @return CMN_LANGModel
     * @throws SQLException SQL実行例外
     */
    public CmnLangModel select(int lngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnLangModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LNG_SID,");
            sql.addSql("   LNG_NAME,");
            sql.addSql("   LNG_CODE,");
            sql.addSql("   LNG_COUNTRY");
            sql.addSql(" from");
            sql.addSql("   CMN_LANG");
            sql.addSql(" where ");
            sql.addSql("   LNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(lngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnLangFromRs(rs);
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
     * <p>Delete CMN_LANG
     * @return int 削除件数
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
            sql.addSql("   CMN_LANG");


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
     * <p>Delete CMN_LANG
     * @param lngSid LNG_SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int lngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LANG");
            sql.addSql(" where ");
            sql.addSql("   LNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(lngSid);

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
     * <p>Create CMN_LANG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLangModel
     * @throws SQLException SQL実行例外
     */
    private CmnLangModel __getCmnLangFromRs(ResultSet rs) throws SQLException {
        CmnLangModel bean = new CmnLangModel();
        bean.setLngSid(rs.getInt("LNG_SID"));
        bean.setLngName(rs.getString("LNG_NAME"));
        bean.setLngCode(rs.getString("LNG_CODE"));
        bean.setLngCountry(rs.getString("LNG_COUNTRY"));
        return bean;
    }
}
