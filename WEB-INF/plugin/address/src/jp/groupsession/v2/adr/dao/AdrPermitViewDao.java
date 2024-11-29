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
import jp.groupsession.v2.adr.model.AdrPermitViewModel;

/**
 * <p>ADR_PERMIT_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPermitViewDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrPermitViewDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrPermitViewDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrPermitViewDao(Connection con) {
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
            sql.addSql("drop table ADR_PERMIT_VIEW");

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
            sql.addSql(" create table ADR_PERMIT_VIEW (");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_PERMIT_VIEW NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   APV_AUID NUMBER(10,0) not null,");
            sql.addSql("   APV_ADATE varchar(23) not null,");
            sql.addSql("   APV_EUID NUMBER(10,0) not null,");
            sql.addSql("   APV_EDATE varchar(23) not null,");
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
     * <p>Insert ADR_PERMIT_VIEW Data Bindding JavaBean
     * @param bean ADR_PERMIT_VIEW Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrPermitViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_PERMIT_VIEW(");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APV_AUID,");
            sql.addSql("   APV_ADATE,");
            sql.addSql("   APV_EUID,");
            sql.addSql("   APV_EDATE");
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
            sql.addIntValue(bean.getAdrPermitView());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getApvAuid());
            sql.addDateValue(bean.getApvAdate());
            sql.addIntValue(bean.getApvEuid());
            sql.addDateValue(bean.getApvEdate());
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
     * <p>Update ADR_PERMIT_VIEW Data Bindding JavaBean
     * @param bean ADR_PERMIT_VIEW Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrPermitViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_PERMIT_VIEW");
            sql.addSql(" set ");
            sql.addSql("   ADR_PERMIT_VIEW=?,");
            sql.addSql("   APV_AUID=?,");
            sql.addSql("   APV_ADATE=?,");
            sql.addSql("   APV_EUID=?,");
            sql.addSql("   APV_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrPermitView());
            sql.addIntValue(bean.getApvAuid());
            sql.addDateValue(bean.getApvAdate());
            sql.addIntValue(bean.getApvEuid());
            sql.addDateValue(bean.getApvEdate());
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
     * <p>Select ADR_PERMIT_VIEW All Data
     * @return List in ADR_PERMIT_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPermitViewModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPermitViewModel> ret = new ArrayList<AdrPermitViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APV_AUID,");
            sql.addSql("   APV_ADATE,");
            sql.addSql("   APV_EUID,");
            sql.addSql("   APV_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERMIT_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPermitViewFromRs(rs));
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
     * <br>[機  能] 指定したアドレス帳の閲覧権限情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return List in ADR_PERMIT_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPermitViewModel> getPermitListForAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPermitViewModel> ret = new ArrayList<AdrPermitViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APV_AUID,");
            sql.addSql("   APV_ADATE,");
            sql.addSql("   APV_EUID,");
            sql.addSql("   APV_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_PERMIT_VIEW");
            sql.addSql(" where");
            sql.addSql("   ADR_SID = ?");

            sql.addIntValue(adrSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPermitViewFromRs(rs));
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
     * <p>Select ADR_PERMIT_VIEW
     * @param adrSid ADR_SID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return ADR_PERMIT_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public AdrPermitViewModel select(int adrSid, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrPermitViewModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADR_PERMIT_VIEW,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APV_AUID,");
            sql.addSql("   APV_ADATE,");
            sql.addSql("   APV_EUID,");
            sql.addSql("   APV_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_PERMIT_VIEW");
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
                ret = __getAdrPermitViewFromRs(rs);
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
     * <br>[機  能] 閲覧権限種別=グループ指定の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @param usrSid ユーザSID
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int checkPowGrp(int adrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_PERMIT_VIEW ");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ADR_PERMIT_VIEW=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID");
            sql.addSql(" in (");
            sql.addSql("  select");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM ");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" )");


            sql.addIntValue(adrSid);
            sql.addIntValue(1);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] 閲覧権限種別=ユーザ指定の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @param usrSid ユーザSID
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int checkPowUsr(int adrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_PERMIT_VIEW ");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_PERMIT_VIEW=?");

            sql.addIntValue(adrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(2);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Delete ADR_PERMIT_VIEW
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
            sql.addSql("   ADR_PERMIT_VIEW");
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
            sql.addSql("   ADR_PERMIT_VIEW");
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
     * <p>Create ADR_PERMIT_VIEW Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrPermitViewModel
     * @throws SQLException SQL実行例外
     */
    private AdrPermitViewModel __getAdrPermitViewFromRs(ResultSet rs) throws SQLException {
        AdrPermitViewModel bean = new AdrPermitViewModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdrPermitView(rs.getInt("ADR_PERMIT_VIEW"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setApvAuid(rs.getInt("APV_AUID"));
        bean.setApvAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APV_ADATE")));
        bean.setApvEuid(rs.getInt("APV_EUID"));
        bean.setApvEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APV_EDATE")));
        return bean;
    }
}
