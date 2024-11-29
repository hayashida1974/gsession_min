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
import jp.groupsession.v2.adr.model.AdrPermitEditModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_PERMIT_EDIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPermitEditDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrPermitEditDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrPermitEditDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrPermitEditDao(Connection con) {
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
            sql.addSql("drop table ADR_PERMIT_EDIT");

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
            sql.addSql(" create table ADR_PERMIT_EDIT (");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_PERMIT_EDIT NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   APE_AUID NUMBER(10,0) not null,");
            sql.addSql("   APE_ADATE varchar(23) not null,");
            sql.addSql("   APE_EUID NUMBER(10,0) not null,");
            sql.addSql("   APE_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADR_SID,GRP_SID,USR_SID)");
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
     * <p>Insert ADR_PERMIT_EDIT Data Bindding JavaBean
     * @param bean ADR_PERMIT_EDIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrPermitEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_PERMIT_EDIT(");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APE_AUID,");
            sql.addSql("   APE_ADATE,");
            sql.addSql("   APE_EUID,");
            sql.addSql("   APE_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getAdrPermitEdit());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getApeAuid());
            sql.addDateValue(bean.getApeAdate());
            sql.addIntValue(bean.getApeEuid());
            sql.addDateValue(bean.getApeEdate());
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
     * <p>Update ADR_PERMIT_EDIT Data Bindding JavaBean
     * @param bean ADR_PERMIT_EDIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrPermitEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_PERMIT_EDIT");
            sql.addSql(" set ");
            sql.addSql("   ADR_PERMIT_EDIT=?,");
            sql.addSql("   APE_AUID=?,");
            sql.addSql("   APE_ADATE=?,");
            sql.addSql("   APE_EUID=?,");
            sql.addSql("   APE_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrPermitEdit());
            sql.addIntValue(bean.getApeAuid());
            sql.addDateValue(bean.getApeAdate());
            sql.addIntValue(bean.getApeEuid());
            sql.addDateValue(bean.getApeEdate());
            //where
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Select ADR_PERMIT_EDIT All Data
     * @return List in ADR_PERMIT_EDITModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPermitEditModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPermitEditModel> ret = new ArrayList<AdrPermitEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APE_AUID,");
            sql.addSql("   APE_ADATE,");
            sql.addSql("   APE_EUID,");
            sql.addSql("   APE_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERMIT_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPermitEditFromRs(rs));
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
     * <br>[機  能] 指定したアドレス帳の編集権限情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return List in ADR_PERMIT_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPermitEditModel> getPermitListForAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPermitEditModel> ret = new ArrayList<AdrPermitEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APE_AUID,");
            sql.addSql("   APE_ADATE,");
            sql.addSql("   APE_EUID,");
            sql.addSql("   APE_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERMIT_EDIT");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID = ?");

            sql.addIntValue(adrSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrPermitEditFromRs(rs));
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
     * <p>Select ADR_PERMIT_EDIT
     * @param adrSid ADR_SID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return ADR_PERMIT_EDITModel
     * @throws SQLException SQL実行例外
     */
    public AdrPermitEditModel select(int adrSid, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrPermitEditModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_EDIT,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APE_AUID,");
            sql.addSql("   APE_ADATE,");
            sql.addSql("   APE_EUID,");
            sql.addSql("   APE_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_PERMIT_EDIT");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(grpSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrPermitEditFromRs(rs);
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
     * <p>Delete ADR_PERMIT_EDIT
     * @param adrSid ADR_SID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int adrSid, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_PERMIT_EDIT");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(grpSid);
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
     * <br>[機  能] 指定したアドレス帳の編集権限情報を削除する
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
            sql.addSql("   ADR_PERMIT_EDIT");
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
     * <p>Create ADR_PERMIT_EDIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrPermitEditModel
     * @throws SQLException SQL実行例外
     */
    private AdrPermitEditModel __getAdrPermitEditFromRs(ResultSet rs) throws SQLException {
        AdrPermitEditModel bean = new AdrPermitEditModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdrPermitEdit(rs.getInt("ADR_PERMIT_EDIT"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setApeAuid(rs.getInt("APE_AUID"));
        bean.setApeAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APE_ADATE")));
        bean.setApeEuid(rs.getInt("APE_EUID"));
        bean.setApeEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APE_EDATE")));
        return bean;
    }
}
