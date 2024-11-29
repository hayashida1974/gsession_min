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
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_PORTAL_POSITION_PARAM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortalPositionParamDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortalPositionParamDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortalPositionParamDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortalPositionParamDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTAL_POSITION_PARAM");

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
            sql.addSql(" create table PTL_PORTAL_POSITION_PARAM (");
            sql.addSql("   PTL_SID integer not null,");
            sql.addSql("   PTP_ITEMID varchar(17) not null,");
            sql.addSql("   PPM_PARAM_NO integer not null,");
            sql.addSql("   PPM_PARAM_NAME varchar(50) not null,");
            sql.addSql("   PPM_PARAM_VALUE varchar(1000) not null,");
            sql.addSql("   primary key (PTL_SID,PTP_ITEMID,PPM_PARAM_NO)");
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
     * <p>Insert PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean
     * @param bean PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortalPositionParamModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTAL_POSITION_PARAM(");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PPM_PARAM_NO,");
            sql.addSql("   PPM_PARAM_NAME,");
            sql.addSql("   PPM_PARAM_VALUE");
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
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtpItemid());
            sql.addIntValue(bean.getPpmParamNo());
            sql.addStrValue(bean.getPpmParamName());
            sql.addStrValue(bean.getPpmParamValue());
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
     * <p>Update PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean
     * @param bean PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortalPositionParamModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
            sql.addSql(" set ");
            sql.addSql("   PPM_PARAM_NAME=?,");
            sql.addSql("   PPM_PARAM_VALUE=?");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");
            sql.addSql(" and");
            sql.addSql("   PPM_PARAM_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPpmParamName());
            sql.addStrValue(bean.getPpmParamValue());
            //where
            sql.addIntValue(bean.getPtlSid());
            sql.addStrValue(bean.getPtpItemid());
            sql.addIntValue(bean.getPpmParamNo());

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
     * <p>Select PTL_PORTAL_POSITION_PARAM All Data
     * @return List in PTL_PORTAL_POSITION_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionParamModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortalPositionParamModel> ret = new ArrayList<PtlPortalPositionParamModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PPM_PARAM_NO,");
            sql.addSql("   PPM_PARAM_NAME,");
            sql.addSql("   PPM_PARAM_VALUE");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionParamFromRs(rs));
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
     * <p>Select PTL_PORTAL_POSITION_PARAM
     * @param ptlSid PTL_SID
     * @param ptpItemid PTP_ITEMID
     * @return PTL_PORTAL_POSITION_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionParamModel> select(int ptlSid, String ptpItemid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalPositionParamModel> ret = new ArrayList<PtlPortalPositionParamModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PPM_PARAM_NO,");
            sql.addSql("   PPM_PARAM_NAME,");
            sql.addSql("   PPM_PARAM_VALUE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");
            sql.addSql(" order by");
            sql.addSql("   PPM_PARAM_NO");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addStrValue(ptpItemid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionParamFromRs(rs));
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
     * <p>Select PTL_PORTAL_POSITION_PARAM
     * @param ptlSid PTL_SID
     * @param ptpItemid PTP_ITEMID
     * @param ppmParamNo PPM_PARAM_NO
     * @return PTL_PORTAL_POSITION_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortalPositionParamModel select(int ptlSid, String ptpItemid, int ppmParamNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortalPositionParamModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PPM_PARAM_NO,");
            sql.addSql("   PPM_PARAM_NAME,");
            sql.addSql("   PPM_PARAM_VALUE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTP_ITEMID=?");
            sql.addSql(" and");
            sql.addSql("   PPM_PARAM_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addStrValue(ptpItemid);
            sql.addIntValue(ppmParamNo);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortalPositionParamFromRs(rs);
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
     * <p>指定したパラメータ情報を取得する。
     * @param ptlSid PTL_SID
     * @param paramName PPM_PARAM_NAME
     * @return PTL_PORTAL_POSITION_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortalPositionParamModel> getParamList(int ptlSid, String paramName)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortalPositionParamModel> ret = new ArrayList<PtlPortalPositionParamModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_SID,");
            sql.addSql("   PTP_ITEMID,");
            sql.addSql("   PPM_PARAM_NO,");
            sql.addSql("   PPM_PARAM_NAME,");
            sql.addSql("   PPM_PARAM_VALUE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addSql(" and");
            sql.addSql("   PPM_PARAM_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ptlSid);
            sql.addStrValue(paramName);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortalPositionParamFromRs(rs));
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
     * <p>Delete PTL_PORTAL_POSITION_PARAM
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
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
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
     * <p>Delete PTL_PORTAL_POSITION_PARAM
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
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
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
     * @param ptpItemidList PTP_ITEMID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ptlSid, List<String> ptpItemidList) throws SQLException {

        int count = 0;
        if (ptpItemidList == null || ptpItemidList.size() < 1) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTAL_POSITION_PARAM");
            sql.addSql(" where ");
            sql.addSql("   PTL_SID=?");
            sql.addIntValue(ptlSid);

            int i = 0;
            for (String itemId : ptpItemidList) {
                if (i == 0) {
                    sql.addSql(" and");
                    sql.addSql(" (");
                } else {
                    sql.addSql(" or");
                }
                sql.addSql("   PTP_ITEMID=?");
                sql.addStrValue(itemId);
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
     * <p>Create PTL_PORTAL_POSITION_PARAM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortalPositionParamModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortalPositionParamModel __getPtlPortalPositionParamFromRs(ResultSet rs)
    throws SQLException {
        PtlPortalPositionParamModel bean = new PtlPortalPositionParamModel();
        bean.setPtlSid(rs.getInt("PTL_SID"));
        bean.setPtpItemid(rs.getString("PTP_ITEMID"));
        bean.setPpmParamNo(rs.getInt("PPM_PARAM_NO"));
        bean.setPpmParamName(rs.getString("PPM_PARAM_NAME"));
        bean.setPpmParamValue(rs.getString("PPM_PARAM_VALUE"));
        return bean;
    }
}
