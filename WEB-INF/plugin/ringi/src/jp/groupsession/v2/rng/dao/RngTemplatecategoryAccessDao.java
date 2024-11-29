package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.model.RngTemplatecategoryAccessModel;

/**
 * <p>RNG_TEMPLATECATEGORY_ACCESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplatecategoryAccessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplatecategoryAccessDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplatecategoryAccessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplatecategoryAccessDao(Connection con) {
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
            sql.addSql("drop table RNG_TEMPLATECATEGORY_ACCESS");

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
            sql.addSql(" create table RNG_TEMPLATECATEGORY_ACCESS (");
            sql.addSql("   RTC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   RTA_KBN NUMBER(10,0) not null,");
            sql.addSql("   primary key (RTC_SID,USR_SID,GRP_SID)");
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
     * <p>Insert RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean
     * @param bean RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplatecategoryAccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATECATEGORY_ACCESS(");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RTA_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtcSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getRtaKbn());
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
     * <p>Update RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean
     * @param bean RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplatecategoryAccessModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATECATEGORY_ACCESS");
            sql.addSql(" set ");
            sql.addSql("   RTA_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtaKbn());
            //where
            sql.addIntValue(bean.getRtcSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select RNG_TEMPLATECATEGORY_ACCESS All Data
     * @return List in RNG_TEMPLATECATEGORY_ACCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplatecategoryAccessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplatecategoryAccessModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RTA_KBN");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATECATEGORY_ACCESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplatecategoryAccessFromRs(rs));
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
     * <p>Select RNG_TEMPLATECATEGORY_ACCESS
     * @param rtcSid RTC_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @return RNG_TEMPLATECATEGORY_ACCESSModel
     * @throws SQLException SQL実行例外
     */
    public RngTemplatecategoryAccessModel select(int rtcSid,
            int usrSid,
            int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplatecategoryAccessModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RTA_KBN");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_ACCESS");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngTemplatecategoryAccessFromRs(rs);
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
     * <p>Delete RNG_TEMPLATECATEGORY_ACCESS
     * @param rtcSid RTC_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rtcSid, int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_ACCESS");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

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
     * <p>Create RNG_TEMPLATECATEGORY_ACCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplatecategoryAccessModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplatecategoryAccessModel
    __getRngTemplatecategoryAccessFromRs(ResultSet rs) throws SQLException {
        RngTemplatecategoryAccessModel bean = new RngTemplatecategoryAccessModel();
        bean.setRtcSid(rs.getInt("RTC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setRtaKbn(rs.getInt("RTA_KBN"));
        return bean;
    }
}
