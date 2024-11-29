package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;

/**
 * <p>CMN_MDISP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMdispDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMdispDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMdispDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMdispDao(Connection con) {
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
            sql.addSql("drop table CMN_MDISP");

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
            sql.addSql(" create table CMN_MDISP (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   MDP_PID varchar(10) not null,");
            sql.addSql("   MDP_DSP NUMBER(10,0) not null,");
            sql.addSql("   MDP_RELOAD NUMBER(10,0) not null Default 600000,");
            sql.addSql("   MDP_AUID NUMBER(10,0) not null,");
            sql.addSql("   MDP_ADATE varchar(23) not null,");
            sql.addSql("   MDP_EUID NUMBER(10,0) not null,");
            sql.addSql("   MDP_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID,MDP_PID)");
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
     * <p>Insert CMN_MDISP Data Bindding JavaBean
     * @param bean CMN_MDISP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MDISP(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDP_PID,");
            sql.addSql("   MDP_DSP,");
            sql.addSql("   MDP_RELOAD,");
            sql.addSql("   MDP_AUID,");
            sql.addSql("   MDP_ADATE,");
            sql.addSql("   MDP_EUID,");
            sql.addSql("   MDP_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMdpPid());
            sql.addIntValue(bean.getMdpDsp());
            sql.addIntValue(bean.getMdpReload());
            sql.addIntValue(bean.getMdpAuid());
            sql.addDateValue(bean.getMdpAdate());
            sql.addIntValue(bean.getMdpEuid());
            sql.addDateValue(bean.getMdpEdate());
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
     * <p>Update CMN_MDISP Data Bindding JavaBean
     * @param bean CMN_MDISP Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MDISP");
            sql.addSql(" set ");
            sql.addSql("   MDP_DSP=?,");
            sql.addSql("   MDP_RELOAD=?,");
            sql.addSql("   MDP_AUID=?,");
            sql.addSql("   MDP_ADATE=?,");
            sql.addSql("   MDP_EUID=?,");
            sql.addSql("   MDP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMdpDsp());
            sql.addIntValue(bean.getMdpReload());
            sql.addIntValue(bean.getMdpAuid());
            sql.addDateValue(bean.getMdpAdate());
            sql.addIntValue(bean.getMdpEuid());
            sql.addDateValue(bean.getMdpEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMdpPid());

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
     * <p>Select CMN_MDISP All Data
     * @return List in CMN_MDISPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMdispModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMdispModel> ret = new ArrayList<CmnMdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDP_PID,");
            sql.addSql("   MDP_DSP,");
            sql.addSql("   MDP_RELOAD,");
            sql.addSql("   MDP_AUID,");
            sql.addSql("   MDP_ADATE,");
            sql.addSql("   MDP_EUID,");
            sql.addSql("   MDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MDISP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMdispFromRs(rs));
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
     * <p>ユーザSIDを指定しメイン画面表示設定一覧を取得する
     * @param userSid ユーザSID
     * @return ArrayList in CMN_MDISPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnMdispModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMdispModel> ret = new ArrayList<CmnMdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDP_PID,");
            sql.addSql("   MDP_DSP,");
            sql.addSql("   MDP_RELOAD,");
            sql.addSql("   MDP_AUID,");
            sql.addSql("   MDP_ADATE,");
            sql.addSql("   MDP_EUID,");
            sql.addSql("   MDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(userSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMdispFromRs(rs));
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
     * <p>Select CMN_MDISP
     * @param bean CMN_MDISP Model
     * @return CMN_MDISPModel
     * @throws SQLException SQL実行例外
     */
    public CmnMdispModel select(CmnMdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMdispModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDP_PID,");
            sql.addSql("   MDP_DSP,");
            sql.addSql("   MDP_RELOAD,");
            sql.addSql("   MDP_AUID,");
            sql.addSql("   MDP_ADATE,");
            sql.addSql("   MDP_EUID,");
            sql.addSql("   MDP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMdpPid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMdispFromRs(rs);
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
     * <p>Delete CMN_MDISP
     * @param bean CMN_MDISP Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnMdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMdpPid());

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
     * <p>Delete CMN_MDISP
     * @param userSid ユーザSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <p>Create CMN_MDISP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMdispModel
     * @throws SQLException SQL実行例外
     */
    private CmnMdispModel __getCmnMdispFromRs(ResultSet rs) throws SQLException {
        CmnMdispModel bean = new CmnMdispModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMdpPid(rs.getString("MDP_PID"));
        bean.setMdpDsp(rs.getInt("MDP_DSP"));
        bean.setMdpReload(rs.getInt("MDP_RELOAD"));
        bean.setMdpAuid(rs.getInt("MDP_AUID"));
        bean.setMdpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MDP_ADATE")));
        bean.setMdpEuid(rs.getInt("MDP_EUID"));
        bean.setMdpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MDP_EDATE")));
        return bean;
    }
}
