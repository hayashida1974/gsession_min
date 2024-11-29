package jp.groupsession.v2.man.dao.base;

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
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTAL_LAYOUT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalLayoutDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalLayoutDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalLayoutDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalLayoutDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL_LAYOUT");

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
            sql.addSql(" create table PTL_PORTAL_LAYOUT (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   PLY_POSITION time not null,");
            sql.addSql("   PTS_VIEW integer not null,");
            sql.addSql("   PLY_AUID integer not null,");
            sql.addSql("   PLY_ADATE timestamp not null,");
            sql.addSql("   PLY_EUID integer not null,");
            sql.addSql("   PLY_EDATE timestamp not null,");
            sql.addSql("   primary key (PTL_SID,PLY_POSITION)");
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
     * <p>Insert PTL_PORTAL_LAYOUT Data Bindding JavaBean
     * @param bean PTL_PORTAL_LAYOUT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalLayoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL_LAYOUT(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTS_VIEW,");
            sql.addSql("   PLY_AUID,");
            sql.addSql("   PLY_ADATE,");
            sql.addSql("   PLY_EUID,");
            sql.addSql("   PLY_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getPlyPosition());
            sql.addIntValue(bean.getPtsView());
            sql.addIntValue(bean.getPlyAuid());
            sql.addDateValue(bean.getPlyAdate());
            sql.addIntValue(bean.getPlyEuid());
            sql.addDateValue(bean.getPlyEdate());
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
     * <p>Update PTL_PORTAL_LAYOUT Data Bindding JavaBean
     * @param bean PTL_PORTAL_LAYOUT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalLayoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_LAYOUT");
            sql.addSql(" set ");
            sql.addSql("   PTS_VIEW=?,");
            sql.addSql("   PLY_EUID=?,");
            sql.addSql("   PLY_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLY_POSITION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtsView());
            sql.addIntValue(bean.getPlyEuid());
            sql.addDateValue(bean.getPlyEdate());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addIntValue(bean.getPlyPosition());

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
     * <p>Select PTL_PORTAL_LAYOUT All Data
     * @return List in PTL_PORTAL_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalLayoutModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalLayoutModel> ret = new ArrayList<PtlPortalLayoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTS_VIEW,");
            sql.addSql("   PLY_AUID,");
            sql.addSql("   PLY_ADATE,");
            sql.addSql("   PLY_EUID,");
            sql.addSql("   PLY_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_LAYOUT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalLayoutFromRs(rs));
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
     * <p>Select PTL_PORTAL_LAYOUT
     * @param ptlSid PTL_SID
     * @return PTL_PORTAL_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalLayoutModel> select(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalLayoutModel> ret = new ArrayList<PtlPortalLayoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTS_VIEW,");
            sql.addSql("   PLY_AUID,");
            sql.addSql("   PLY_ADATE,");
            sql.addSql("   PLY_EUID,");
            sql.addSql("   PLY_EDATE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_LAYOUT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalLayoutFromRs(rs));
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
     * <p>Select PTL_PORTAL_LAYOUT
     * @param ptlSid PTL_SID
     * @param plyPosition PLY_POSITION
     * @return PTL_PORTAL_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortalLayoutModel select(int ptlSid, int plyPosition) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortalLayoutModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTS_VIEW,");
            sql.addSql("   PLY_AUID,");
            sql.addSql("   PLY_ADATE,");
            sql.addSql("   PLY_EUID,");
            sql.addSql("   PLY_EDATE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_LAYOUT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLY_POSITION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(plyPosition);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortalLayoutFromRs(rs);
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
     * <p>ポータルのレイアウト一覧を取得する。
     * @param ptlSid PTL_SID
     * @param ptsView OTS_VIEW
     * @return PTL_PORTAL_LAYOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalLayoutModel> getLayoutList(int ptlSid, int ptsView) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalLayoutModel> ret = new ArrayList<PtlPortalLayoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTS_VIEW,");
            sql.addSql("   PLY_AUID,");
            sql.addSql("   PLY_ADATE,");
            sql.addSql("   PLY_EUID,");
            sql.addSql("   PLY_EDATE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_LAYOUT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTS_VIEW=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(ptsView);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalLayoutFromRs(rs));
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
     * <p>Delete PTL_PORTAL_LAYOUT
     * @param ptlSid PTL_SID
     * @param plyPosition PLY_POSITION
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid, int plyPosition) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_LAYOUT");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLY_POSITION=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(plyPosition);

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
     * <p>Delete PTL_PORTAL_LAYOUT
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
            sql.addSql("   PTL_PORTAL_LAYOUT");
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
     * <p>Create PTL_PORTAL_LAYOUT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalLayoutModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalLayoutModel __getPtlPortalLayoutFromRs(ResultSet rs) throws SQLException {
        PtlPortalLayoutModel bean = new PtlPortalLayoutModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setPlyPosition(rs.getInt("PLY_POSITION"));
        bean.setPtsView(rs.getInt("PTS_VIEW"));
        bean.setPlyAuid(rs.getInt("PLY_AUID"));
        bean.setPlyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PLY_ADATE")));
        bean.setPlyEuid(rs.getInt("PLY_EUID"));
        bean.setPlyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PLY_EDATE")));
        return bean;
    }
}
