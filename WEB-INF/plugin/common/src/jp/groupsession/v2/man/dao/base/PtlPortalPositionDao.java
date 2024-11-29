package jp.groupsession.v2.man.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTAL_POSITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalPositionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalPositionDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalPositionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalPositionDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL_POSITION");

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
            sql.addSql(" create table PTL_PORTAL_POSITION (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   PTP_ITEMID varchar(17) not null,");
            sql.addSql("   PLY_POSITION time not null,");
            sql.addSql("   PTP_SORT integer not null,");
            sql.addSql("   PTP_VIEW time not null,");
            sql.addSql("   PTP_TYPE time not null,");
            sql.addSql("   PLT_SID integer,");
            sql.addSql("   PCT_PID varchar(10),");
            sql.addSql("   MSC_ID varchar(150),");
            sql.addSql("   PTP_PARAMKBN integer");
            sql.addSql("   primary key (PTL_SID,PTP_ITEMID)");
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
     * <p>Insert PTL_PORTAL_POSITION Data Bindding JavaBean
     * @param bean PTL_PORTAL_POSITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL_POSITION(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtpItemid());
            sql.addIntValue(bean.getPlyPosition());
            sql.addIntValue(bean.getPtpSort());
            sql.addIntValue(bean.getPtpView());
            sql.addIntValue(bean.getPtpType());
            sql.addIntValue(bean.getPltSid());
            sql.addStrValue(bean.getPctPid());
            sql.addStrValue(bean.getMscId());
            sql.addIntValue(bean.getPtpParamkbn());
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
     * <p>Update PTL_PORTAL_POSITION Data Bindding JavaBean
     * @param bean PTL_PORTAL_POSITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" set ");
            sql.addSql("   PLY_POSITION=?,");
            sql.addSql("   PTP_SORT=?,");
            sql.addSql("   PTP_VIEW=?,");
            sql.addSql("   PTP_TYPE=?,");
            sql.addSql("   PLT_SID=?,");
            sql.addSql("   PCT_PID=?,");
            sql.addSql("   MSC_ID=?,");
            sql.addSql("   PTP_PARAMKBN=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPlyPosition());
            sql.addIntValue(bean.getPtpSort());
            sql.addIntValue(bean.getPtpView());
            sql.addIntValue(bean.getPtpType());
            sql.addIntValue(bean.getPltSid());
            sql.addStrValue(bean.getPctPid());
            sql.addStrValue(bean.getMscId());
            sql.addIntValue(bean.getPtpParamkbn());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtpItemid());

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
     * <p>表示区分の更新を行う。
     * @param bean PTL_PORTAL_POSITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateView(PtlPortalPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" set ");
            sql.addSql("   PTP_VIEW=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPtpView());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtpItemid());

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
     * <p>表示区分の更新を行う。
     * @param pltSid PTL_SID
     * @param plyPosition PLY_POSITION
     * @param upPosiList ポジションリスト
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(int pltSid, int plyPosition, List<Integer> upPosiList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (upPosiList == null || upPosiList.size() < 1) {
            return count;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" set ");
            sql.addSql("   PLY_POSITION=?");
            sql.addIntValue(plyPosition);

            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addIntValue(pltSid);
            sql.addSql(" and (");
            int i = 0;
            for (Integer position : upPosiList) {
                if (i > 0) {
                    sql.addSql(" or ");
                }
                sql.addSql("   PLY_POSITION=?");
                sql.addIntValue(position);

                i++;
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Select PTL_PORTAL_POSITION All Data
     * @return List in PTL_PORTAL_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalPositionModel> ret = new ArrayList<PtlPortalPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_POSITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionFromRs(rs));
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
     * <p>Select PTL_PORTAL_POSITION
     * @param ptlSid PTL_SID
     * @return PTL_PORTAL_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionModel> select(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalPositionModel> ret = new ArrayList<PtlPortalPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" order by");
            sql.addSql("   PLY_POSITION asc,");
            sql.addSql("   PTP_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionFromRs(rs));
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
     * <p>Select PTL_PORTAL_POSITION
     * @param ptlSid PTL_SID
     * @param ptpItemid PTP_ITEMID
     * @return PTL_PORTAL_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortalPositionModel select(int ptlSid, String ptpItemid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortalPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addStrValue(ptpItemid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortalPositionFromRs(rs);
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
     * <p>ポータル位置情報を取得する。
     * @param ptlSid PTL_SID
     * @param ptpType PTP_TYPE
     * @return PTL_PORTAL_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionModel> getPtlPosition(int ptlSid, int ptpType)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalPositionModel> ret = new ArrayList<PtlPortalPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(ptpType);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionFromRs(rs));
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
     * <p>ポータル位置情報の最大値を取得する。
     * @param ptlSid PTL_SID
     * @param plyPosition PLY_POSITION
     * @return ret 表示順の最大値
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort(int ptlSid, int plyPosition) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(PTP_SORT) as MAX_SORT");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
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
                ret = rs.getInt("MAX_SORT");
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
     * <p>表示するポータル位置情報を取得する。
     * @param ptlSid PTL_SID
     * @return PTL_PORTAL_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionModel> getViewPtlPosition(int ptlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalPositionModel> ret = new ArrayList<PtlPortalPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PLY_POSITION,");
            sql.addSql("   PTP_SORT,");
            sql.addSql("   PTP_VIEW,");
            sql.addSql("   PTP_TYPE,");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PCT_PID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   PTP_PARAMKBN");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_VIEW=?");
            sql.addSql(" order by");
            sql.addSql("   PTP_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addIntValue(GSConstPortal.LAYOUT_VIEW_ON);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionFromRs(rs));
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
     * <p>Delete PTL_PORTAL_POSITION
     * @param ptlSid PTL_SID
     * @param ptpItemid PTP_ITEMID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid, String ptpItemid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addStrValue(ptpItemid);

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
     * <p>Delete PTL_PORTAL_POSITION
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
            sql.addSql("   PTL_PORTAL_POSITION");
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
     * <p>Delete PTL_PORTAL_POSITION
     * @param pltSid PLT_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deletePortlet(int pltSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where ");
            sql.addSql("   PTP_TYPE=0");
            sql.addSql(" and ");
            sql.addSql("   PLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);

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
     * <p>Create PTL_PORTAL_POSITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalPositionModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalPositionModel __getPtlPortalPositionFromRs(ResultSet rs) throws SQLException {
        PtlPortalPositionModel bean = new PtlPortalPositionModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setPtpItemid(rs.getString("PTP_ITEMID"));
        bean.setPlyPosition(rs.getInt("PLY_POSITION"));
        bean.setPtpSort(rs.getInt("PTP_SORT"));
        bean.setPtpView(rs.getInt("PTP_VIEW"));
        bean.setPtpType(rs.getInt("PTP_TYPE"));
        bean.setPltSid(rs.getInt("PLT_SID"));
        bean.setPctPid(rs.getString("PCT_PID"));
        bean.setMscId(rs.getString("MSC_ID"));
        bean.setPtpParamkbn(rs.getInt("PTP_PARAMKBN"));
        return bean;
    }
}
