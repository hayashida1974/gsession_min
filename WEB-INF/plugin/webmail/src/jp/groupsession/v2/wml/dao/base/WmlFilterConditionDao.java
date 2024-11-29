package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.wml.model.base.WmlFilterConditionModel;

/**
 * <p>WML_FILTER_CONDITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterConditionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlFilterConditionDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlFilterConditionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlFilterConditionDao(Connection con) {
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
            sql.addSql("drop table WML_FILTER_CONDITION");

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
            sql.addSql(" create table WML_FILTER_CONDITION (");
            sql.addSql("   WFT_SID NUMBER(10,0) not null,");
            sql.addSql("   WFC_NUM NUMBER(10,0) not null,");
            sql.addSql("   WFC_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WFC_EXPRESSION NUMBER(10,0) not null,");
            sql.addSql("   WFC_TEXT varchar(768) not null,");
            sql.addSql("   primary key (WFT_SID,WFC_NUM)");
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
     * <p>Insert WML_FILTER_CONDITION Data Bindding JavaBean
     * @param bean WML_FILTER_CONDITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlFilterConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_FILTER_CONDITION(");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFC_NUM,");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
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
            sql.addIntValue(bean.getWftSid());
            sql.addIntValue(bean.getWfcNum());
            sql.addIntValue(bean.getWfcType());
            sql.addIntValue(bean.getWfcExpression());
            sql.addStrValue(bean.getWfcText());
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
     * <p>Update WML_FILTER_CONDITION Data Bindding JavaBean
     * @param bean WML_FILTER_CONDITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlFilterConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" set ");
            sql.addSql("   WFC_TYPE=?,");
            sql.addSql("   WFC_EXPRESSION=?,");
            sql.addSql("   WFC_TEXT=?");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWfcType());
            sql.addIntValue(bean.getWfcExpression());
            sql.addStrValue(bean.getWfcText());
            //where
            sql.addIntValue(bean.getWftSid());
            sql.addIntValue(bean.getWfcNum());

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
     * <p>Select WML_FILTER_CONDITION All Data
     * @return List in WML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterConditionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlFilterConditionModel> ret = new ArrayList<WmlFilterConditionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFC_NUM,");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_CONDITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterConditionFromRs(rs));
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
     * <p>Select WML_FILTER_CONDITION All Data
     * @param wftSid WFT_SID
     * @return List in WML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterConditionModel> select(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlFilterConditionModel> ret = new ArrayList<WmlFilterConditionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFC_NUM,");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID = ?");

            sql.addIntValue(wftSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterConditionFromRs(rs));
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
     * <p>Select WML_FILTER_CONDITION
     * @param wftSid WFT_SID
     * @param wfcNum WFC_NUM
     * @return WML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public WmlFilterConditionModel select(int wftSid, int wfcNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlFilterConditionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFC_NUM,");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);
            sql.addIntValue(wfcNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlFilterConditionFromRs(rs);
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
     * <p>Delete WML_FILTER_CONDITION
     * @param wftSid WFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);

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
     * <p>Delete WML_FILTER_CONDITION
     * @param wftSid WFT_SID
     * @param wfcNum WFC_NUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wftSid, int wfcNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);
            sql.addIntValue(wfcNum);

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
     * <p>Create WML_FILTER_CONDITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlFilterConditionModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterConditionModel __getWmlFilterConditionFromRs(ResultSet rs)
    throws SQLException {
        WmlFilterConditionModel bean = new WmlFilterConditionModel();
        bean.setWftSid(rs.getInt("WFT_SID"));
        bean.setWfcNum(rs.getInt("WFC_NUM"));
        bean.setWfcType(rs.getInt("WFC_TYPE"));
        bean.setWfcExpression(rs.getInt("WFC_EXPRESSION"));
        bean.setWfcText(rs.getString("WFC_TEXT"));
        return bean;
    }
}
