package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.model.SmlFilterConditionModel;

/**
 * <p>SML_FILTER_CONDITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlFilterConditionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlFilterConditionDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlFilterConditionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlFilterConditionDao(Connection con) {
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
            sql.addSql("drop table SML_FILTER_CONDITION");

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
            sql.addSql(" create table SML_FILTER_CONDITION (");
            sql.addSql("   SFT_SID NUMBER(10,0) not null,");
            sql.addSql("   SFC_NUM NUMBER(10,0) not null,");
            sql.addSql("   SFC_TYPE NUMBER(10,0) not null,");
            sql.addSql("   SFC_EXPRESSION NUMBER(10,0) not null,");
            sql.addSql("   SFC_TEXT varchar(768) not null,");
            sql.addSql("   primary key (SFT_SID,SFC_NUM)");
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
     * <p>Insert SML_FILTER_CONDITION Data Bindding JavaBean
     * @param bean SML_FILTER_CONDITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlFilterConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_FILTER_CONDITION(");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFC_NUM,");
            sql.addSql("   SFC_TYPE,");
            sql.addSql("   SFC_EXPRESSION,");
            sql.addSql("   SFC_TEXT");
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
            sql.addIntValue(bean.getSftSid());
            sql.addIntValue(bean.getSfcNum());
            sql.addIntValue(bean.getSfcType());
            sql.addIntValue(bean.getSfcExpression());
            sql.addStrValue(bean.getSfcText());
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
     * <p>Update SML_FILTER_CONDITION Data Bindding JavaBean
     * @param bean SML_FILTER_CONDITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlFilterConditionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" set ");
            sql.addSql("   SFC_TYPE=?,");
            sql.addSql("   SFC_EXPRESSION=?,");
            sql.addSql("   SFC_TEXT=?");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSfcType());
            sql.addIntValue(bean.getSfcExpression());
            sql.addStrValue(bean.getSfcText());
            //where
            sql.addIntValue(bean.getSftSid());
            sql.addIntValue(bean.getSfcNum());

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
     * <p>Select SML_FILTER_CONDITION All Data
     * @return List in SML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlFilterConditionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlFilterConditionModel> ret = new ArrayList<SmlFilterConditionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFC_NUM,");
            sql.addSql("   SFC_TYPE,");
            sql.addSql("   SFC_EXPRESSION,");
            sql.addSql("   SFC_TEXT");
            sql.addSql(" from ");
            sql.addSql("   SML_FILTER_CONDITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlFilterConditionFromRs(rs));
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
     * <p>Select SML_FILTER_CONDITION All Data
     * @param sftSid SFT_SID
     * @return List in SML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlFilterConditionModel> select(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlFilterConditionModel> ret = new ArrayList<SmlFilterConditionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFC_NUM,");
            sql.addSql("   SFC_TYPE,");
            sql.addSql("   SFC_EXPRESSION,");
            sql.addSql("   SFC_TEXT");
            sql.addSql(" from ");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID = ?");

            sql.addIntValue(sftSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlFilterConditionFromRs(rs));
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
     * <p>Select SML_FILTER_CONDITION
     * @param sftSid SFT_SID
     * @param sfcNum SFC_NUM
     * @return SML_FILTER_CONDITIONModel
     * @throws SQLException SQL実行例外
     */
    public SmlFilterConditionModel select(int sftSid, int sfcNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlFilterConditionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFC_NUM,");
            sql.addSql("   SFC_TYPE,");
            sql.addSql("   SFC_EXPRESSION,");
            sql.addSql("   SFC_TEXT");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);
            sql.addIntValue(sfcNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlFilterConditionFromRs(rs);
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
     * <p>Delete SML_FILTER_CONDITION
     * @param sftSid SFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);

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
     * <p>Delete SML_FILTER_CONDITION
     * @param sftSid SFT_SID
     * @param sfcNum SFC_NUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sftSid, int sfcNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFC_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);
            sql.addIntValue(sfcNum);

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
     * <p>Create SML_FILTER_CONDITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlFilterConditionModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterConditionModel __getSmlFilterConditionFromRs(ResultSet rs)
    throws SQLException {
        SmlFilterConditionModel bean = new SmlFilterConditionModel();
        bean.setSftSid(rs.getInt("SFT_SID"));
        bean.setSfcNum(rs.getInt("SFC_NUM"));
        bean.setSfcType(rs.getInt("SFC_TYPE"));
        bean.setSfcExpression(rs.getInt("SFC_EXPRESSION"));
        bean.setSfcText(rs.getString("SFC_TEXT"));
        return bean;
    }
}
