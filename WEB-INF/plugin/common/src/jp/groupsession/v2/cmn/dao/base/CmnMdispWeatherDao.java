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
import jp.groupsession.v2.cmn.model.base.CmnMdispWeatherModel;

/**
 * <p>CMN_MDISP_WEATHER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMdispWeatherDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMdispWeatherDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMdispWeatherDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMdispWeatherDao(Connection con) {
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
            sql.addSql("drop table CMN_MDISP_WEATHER");

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
            sql.addSql(" create table CMN_MDISP_WEATHER (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   MDW_AREA NUMBER(10,0) not null,");
            sql.addSql("   MDW_SORT NUMBER(10,0) not null,");
            sql.addSql("   MDW_AUID NUMBER(10,0) not null,");
            sql.addSql("   MDW_ADATE varchar(23) not null,");
            sql.addSql("   MDW_EUID NUMBER(10,0) not null,");
            sql.addSql("   MDW_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID,MDW_AREA)");
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
     * <p>Insert CMN_MDISP_WEATHER Data Bindding JavaBean
     * @param bean CMN_MDISP_WEATHER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMdispWeatherModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MDISP_WEATHER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDW_AREA,");
            sql.addSql("   MDW_SORT,");
            sql.addSql("   MDW_AUID,");
            sql.addSql("   MDW_ADATE,");
            sql.addSql("   MDW_EUID,");
            sql.addSql("   MDW_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMdwArea());
            sql.addIntValue(bean.getMdwSort());
            sql.addIntValue(bean.getMdwAuid());
            sql.addDateValue(bean.getMdwAdate());
            sql.addIntValue(bean.getMdwEuid());
            sql.addDateValue(bean.getMdwEdate());
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
     * <p>Update CMN_MDISP_WEATHER Data Bindding JavaBean
     * @param bean CMN_MDISP_WEATHER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMdispWeatherModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" set ");
            sql.addSql("   MDW_SORT=?,");
            sql.addSql("   MDW_AUID=?,");
            sql.addSql("   MDW_ADATE=?,");
            sql.addSql("   MDW_EUID=?,");
            sql.addSql("   MDW_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDW_AREA=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMdwSort());
            sql.addIntValue(bean.getMdwAuid());
            sql.addDateValue(bean.getMdwAdate());
            sql.addIntValue(bean.getMdwEuid());
            sql.addDateValue(bean.getMdwEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMdwArea());

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
     * <p>Select CMN_MDISP_WEATHER All Data
     * @return List in CMN_MDISP_WEATHERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMdispWeatherModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMdispWeatherModel> ret = new ArrayList<CmnMdispWeatherModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDW_AREA,");
            sql.addSql("   MDW_SORT,");
            sql.addSql("   MDW_AUID,");
            sql.addSql("   MDW_ADATE,");
            sql.addSql("   MDW_EUID,");
            sql.addSql("   MDW_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MDISP_WEATHER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMdispWeatherFromRs(rs));
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
     * <p>Select CMN_MDISP_WEATHER All Data
     * @param usrSid USR_SID
     * @return List in CMN_MDISP_WEATHERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMdispWeatherModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMdispWeatherModel> ret = new ArrayList<CmnMdispWeatherModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDW_AREA,");
            sql.addSql("   MDW_SORT,");
            sql.addSql("   MDW_AUID,");
            sql.addSql("   MDW_ADATE,");
            sql.addSql("   MDW_EUID,");
            sql.addSql("   MDW_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   MDW_AREA in (");
            sql.addSql("     select CWA_SID from CMN_WEATHER_AREA");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   MDW_SORT");
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnMdispWeatherFromRs(rs));
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
     * <p>Select CMN_MDISP_WEATHER
     * @param usrSid USR_SID
     * @param mdwArea MDW_AREA
     * @return CMN_MDISP_WEATHERModel
     * @throws SQLException SQL実行例外
     */
    public CmnMdispWeatherModel select(int usrSid, int mdwArea) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMdispWeatherModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MDW_AREA,");
            sql.addSql("   MDW_SORT,");
            sql.addSql("   MDW_AUID,");
            sql.addSql("   MDW_ADATE,");
            sql.addSql("   MDW_EUID,");
            sql.addSql("   MDW_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDW_AREA=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mdwArea);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMdispWeatherFromRs(rs);
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
     * <br>[機  能] 指定したユーザの天気予報表示地域数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 天気予報表示地域数
     * @throws SQLException SQL実行例外
     */
    public int getAreaCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(MDW_AREA) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <p>Delete CMN_MDISP_WEATHER
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Delete CMN_MDISP_WEATHER
     * @param usrSid USR_SID
     * @param mdwArea MDW_AREA
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int mdwArea) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MDISP_WEATHER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MDW_AREA=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mdwArea);

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
     * <p>Create CMN_MDISP_WEATHER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMdispWeatherModel
     * @throws SQLException SQL実行例外
     */
    private CmnMdispWeatherModel __getCmnMdispWeatherFromRs(ResultSet rs) throws SQLException {
        CmnMdispWeatherModel bean = new CmnMdispWeatherModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMdwArea(rs.getInt("MDW_AREA"));
        bean.setMdwSort(rs.getInt("MDW_SORT"));
        bean.setMdwAuid(rs.getInt("MDW_AUID"));
        bean.setMdwAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MDW_ADATE")));
        bean.setMdwEuid(rs.getInt("MDW_EUID"));
        bean.setMdwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MDW_EDATE")));
        return bean;
    }
}
