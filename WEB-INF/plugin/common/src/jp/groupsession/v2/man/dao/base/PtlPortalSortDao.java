package jp.groupsession.v2.man.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.model.base.PtlPortalSortModel;

/**
 * <p>PTL_PORTAL_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalSortDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalSortDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL_SORT");

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
            sql.addSql(" create table PTL_PORTAL_SORT (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   PTS_KBN time not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   PTS_SORT integer not null,");
            sql.addSql("   primary key (PTL_SID,PTS_KBN,USR_SID)");
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
     * <p>Insert PTL_PORTAL_SORT Data Bindding JavaBean
     * @param bean PTL_PORTAL_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL_SORT(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTS_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getPtsKbn());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPtsSort());
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
     * <p>Update PTL_PORTAL_SORT Data Bindding JavaBean
     * @param bean PTL_PORTAL_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" set ");
            sql.addSql("   PTS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_KBN=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtsSort());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getPtsKbn());
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
     * <br>[機  能] 表示順序を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ptlSid ポータルSID
     * @param userSid ユーザSID
     * @param sort 変更ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int ptlSid, int userSid, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" set ");
            sql.addSql("   PTS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(ptlSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select PTL_PORTAL_SORT All Data
     * @return List in PTL_PORTAL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalSortModel> ret = new ArrayList<PtlPortalSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTS_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTS_SORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalSortFromRs(rs));
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
     * <p>Select PTL_PORTAL_SORT
     * @param ptlSid PTL_SID
     * @param ptsKbn PTS_KBN
     * @param usrSid USR_SID
     * @return PTL_PORTAL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortalSortModel select(int ptlSid, int ptsKbn, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortalSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTS_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_KBN=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(ptsKbn);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortalSortFromRs(rs);
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
     * <p>Select PTL_PORTAL_SORT
     * @param usrSid USR_SID
     * @return PTL_PORTAL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PtlPortalSortModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalSortModel> ret = new ArrayList<PtlPortalSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTS_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTS_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" where ");
            sql.addSql("   PTS_KBN=1");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalSortFromRs(rs));
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
     * <p>表示順の最大値を取得します。
     * @param ptsKbn ポータル
     * @return List in PTL_PORTAL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort(int ptsKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COALESCE(max(PTL_PORTAL_SORT.PTS_SORT), 0) MAXSORT");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" WHERE");
            sql.addSql("   PTS_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptsKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("MAXSORT");
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
     * <p>Delete PTL_PORTAL_SORT
     * @param ptlSid PTL_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

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
     * <p>Delete PTL_PORTAL_SORT
     * @param userSid userSid
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteSortForUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_SORT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTS_KBN=1");

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
     * <p>Create PTL_PORTAL_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalSortModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalSortModel __getPtlPortalSortFromRs(ResultSet rs) throws SQLException {
        PtlPortalSortModel bean = new PtlPortalSortModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setPtsKbn(rs.getInt("PTS_KBN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPtsSort(rs.getInt("PTS_SORT"));
        return bean;
    }
}
