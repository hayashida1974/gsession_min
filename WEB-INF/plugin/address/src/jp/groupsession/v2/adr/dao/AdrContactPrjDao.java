package jp.groupsession.v2.adr.dao;

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
import jp.groupsession.v2.adr.model.AdrContactPrjModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_CONTACT_PRJ Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AdrContactPrjDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrContactPrjDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrContactPrjDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrContactPrjDao(Connection con) {
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
            sql.addSql("drop table ADR_CONTACT_PRJ");

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
            sql.addSql(" create table ADR_CONTACT_PRJ (");
            sql.addSql("   ADC_SID NUMBER(10,0) not null,");
            sql.addSql("   PRJ_SID NUMBER(10,0) not null,");
            sql.addSql("   ADC_AUID NUMBER(10,0) not null,");
            sql.addSql("   ADC_ADATE varchar(23) not null,");
            sql.addSql("   ADC_EUID NUMBER(10,0) not null,");
            sql.addSql("   ADC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADC_SID, ADC_SID)");
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
     * <p>Insert ADR_CONTACT_PRJ Data Bindding JavaBean
     * @param bean ADR_CONTACT_PRJ Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrContactPrjModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_CONTACT_PRJ(");
            sql.addSql("   ADC_SID,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdcSid());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getAdcAuid());
            sql.addDateValue(bean.getAdcAdate());
            sql.addIntValue(bean.getAdcEuid());
            sql.addDateValue(bean.getAdcEdate());
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
     * <p>Update ADR_CONTACT_PRJ Data Bindding JavaBean
     * @param bean ADR_CONTACT_PRJ Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrContactPrjModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_CONTACT_PRJ");
            sql.addSql(" set ");
            sql.addSql("   PRJ_SID=?,");
            sql.addSql("   ADC_AUID=?,");
            sql.addSql("   ADC_ADATE=?,");
            sql.addSql("   ADC_EUID=?,");
            sql.addSql("   ADC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getAdcAuid());
            sql.addDateValue(bean.getAdcAdate());
            sql.addIntValue(bean.getAdcEuid());
            sql.addDateValue(bean.getAdcEdate());
            //where
            sql.addIntValue(bean.getAdcSid());

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
     * <p>Select ADR_CONTACT_PRJ All Data
     * @return List in ADR_CONTACT_PRJModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrContactPrjModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrContactPrjModel> ret = new ArrayList<AdrContactPrjModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADC_SID,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_CONTACT_PRJ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrContactPrjFromRs(rs));
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
     * <p>Select ADR_CONTACT_PRJ
     * @param adcSid ADC_SID
     * @return ADR_CONTACT_PRJModelList
     * @throws SQLException SQL実行例外
     */
    public List<AdrContactPrjModel> select(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AdrContactPrjModel> ret = new ArrayList<AdrContactPrjModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADC_SID,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_PRJ");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrContactPrjFromRs(rs));
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
     * <p>Delete ADR_CONTACT_PRJ
     * @param adcSid ADC_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int adcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_PRJ");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);

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
     * <p>Create ADR_CONTACT_PRJ Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrContactPrjModel
     * @throws SQLException SQL実行例外
     */
    private AdrContactPrjModel __getAdrContactPrjFromRs(ResultSet rs) throws SQLException {
        AdrContactPrjModel bean = new AdrContactPrjModel();
        bean.setAdcSid(rs.getInt("ADC_SID"));
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setAdcAuid(rs.getInt("ADC_AUID"));
        bean.setAdcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_ADATE")));
        bean.setAdcEuid(rs.getInt("ADC_EUID"));
        bean.setAdcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ADC_EDATE")));
        return bean;
    }
}
