package jp.groupsession.v2.adr.dao;

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
import jp.groupsession.v2.adr.model.AdrPersonchargeModel;

/**
 * <p>ADR_PERSONCHARGE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPersonchargeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrPersonchargeDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrPersonchargeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrPersonchargeDao(Connection con) {
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
            sql.addSql("drop table ADR_PERSONCHARGE");

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
            sql.addSql(" create table ADR_PERSONCHARGE (");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   APC_AUID NUMBER(10,0) not null,");
            sql.addSql("   APC_ADATE varchar(23) not null,");
            sql.addSql("   APC_EUID NUMBER(10,0) not null,");
            sql.addSql("   APC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADR_SID,USR_SID)");
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
     * <p>Insert ADR_PERSONCHARGE Data Bindding JavaBean
     * @param bean ADR_PERSONCHARGE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrPersonchargeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_PERSONCHARGE(");
            sql.addSql("   ADR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE");
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
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getApcAuid());
            sql.addDateValue(bean.getApcAdate());
            sql.addIntValue(bean.getApcEuid());
            sql.addDateValue(bean.getApcEdate());
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
     * <p>Update ADR_PERSONCHARGE Data Bindding JavaBean
     * @param bean ADR_PERSONCHARGE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrPersonchargeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" set ");
            sql.addSql("   APC_AUID=?,");
            sql.addSql("   APC_ADATE=?,");
            sql.addSql("   APC_EUID=?,");
            sql.addSql("   APC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getApcAuid());
            sql.addDateValue(bean.getApcAdate());
            sql.addIntValue(bean.getApcEuid());
            sql.addDateValue(bean.getApcEdate());
            //where
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select ADR_PERSONCHARGE All Data
     * @return List in ADR_PERSONCHARGEModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPersonchargeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPersonchargeModel> ret = new ArrayList<AdrPersonchargeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERSONCHARGE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPersonchargeFromRs(rs));
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
     * <br>[機  能] 指定したアドレス帳の担当者情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return List in ADR_PERMIT_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPersonchargeModel> getTantoListForAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPersonchargeModel> ret = new ArrayList<AdrPersonchargeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPersonchargeFromRs(rs));
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
     * <p>Select ADR_PERSONCHARGE
     * @param adrSid ADR_SID
     * @param usrSid USR_SID
     * @return ADR_PERSONCHARGEModel
     * @throws SQLException SQL実行例外
     */
    public AdrPersonchargeModel select(int adrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrPersonchargeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APC_AUID,");
            sql.addSql("   APC_ADATE,");
            sql.addSql("   APC_EUID,");
            sql.addSql("   APC_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrPersonchargeFromRs(rs);
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
     * <p>Delete ADR_PERSONCHARGE
     * @param adrSid ADR_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int adrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(usrSid);

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
     * <br>[機  能] 指定したアドレス帳の閲覧権限情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteToAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

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
     * <p>Create ADR_PERSONCHARGE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrPersonchargeModel
     * @throws SQLException SQL実行例外
     */
    private AdrPersonchargeModel __getAdrPersonchargeFromRs(ResultSet rs) throws SQLException {
        AdrPersonchargeModel bean = new AdrPersonchargeModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setApcAuid(rs.getInt("APC_AUID"));
        bean.setApcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APC_ADATE")));
        bean.setApcEuid(rs.getInt("APC_EUID"));
        bean.setApcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APC_EDATE")));
        return bean;
    }

    /**
     * <p>指定したユーザがアドレス帳の担当者かチェックする
     * @param adrSid ADR_SID
     * @param usrSid USR_SID
     * @return ADR_PERSONCHARGEModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckTanto(int adrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_PERSONCHARGE");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }
}
