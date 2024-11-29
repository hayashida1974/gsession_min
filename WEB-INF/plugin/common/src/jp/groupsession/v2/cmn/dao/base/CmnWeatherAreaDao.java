package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnWeatherAreaModel;

/**
 * <p>CMN_WEATHER_AREA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnWeatherAreaDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnWeatherAreaDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnWeatherAreaDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnWeatherAreaDao(Connection con) {
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
            sql.addSql("drop table CMN_WEATHER_AREA");

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
            sql.addSql(" create table CMN_WEATHER_AREA (");
            sql.addSql("   CWA_SID NUMBER(10,0) not null,");
            sql.addSql("   CWA_NAME varchar(50) not null,");
            sql.addSql("   CWA_SORT NUMBER(10,0) not null,");
            sql.addSql("   primary key (CWA_SID)");
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
     * <p>Insert CMN_WEATHER_AREA Data Bindding JavaBean
     * @param bean CMN_WEATHER_AREA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnWeatherAreaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_WEATHER_AREA(");
            sql.addSql("   CWA_SID,");
            sql.addSql("   CWA_NAME,");
            sql.addSql("   CWA_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCwaSid());
            sql.addStrValue(bean.getCwaName());
            sql.addIntValue(bean.getCwaSort());
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
     * <p>Update CMN_WEATHER_AREA Data Bindding JavaBean
     * @param bean CMN_WEATHER_AREA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnWeatherAreaModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" set ");
            sql.addSql("   CWA_NAME=?,");
            sql.addSql("   CWA_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   CWA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCwaName());
            sql.addIntValue(bean.getCwaSort());
            //where
            sql.addIntValue(bean.getCwaSid());

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
     * <p>Select CMN_WEATHER_AREA All Data
     * @return List in CMN_WEATHER_AREAModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnWeatherAreaModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnWeatherAreaModel> ret = new ArrayList<CmnWeatherAreaModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CWA_SID,");
            sql.addSql("   CWA_NAME,");
            sql.addSql("   CWA_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" order by");
            sql.addSql("   CWA_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnWeatherAreaFromRs(rs));
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
     * <p>Select CMN_WEATHER_AREA
     * @param cwaSid CWA_SID
     * @return CMN_WEATHER_AREAModel
     * @throws SQLException SQL実行例外
     */
    public CmnWeatherAreaModel select(int cwaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnWeatherAreaModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CWA_SID,");
            sql.addSql("   CWA_NAME,");
            sql.addSql("   CWA_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" where ");
            sql.addSql("   CWA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cwaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnWeatherAreaFromRs(rs);
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
     * <p>Delete CMN_WEATHER_AREA
     * @param cwaSid CWA_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int cwaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" where ");
            sql.addSql("   CWA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cwaSid);

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
     * <p>Create CMN_WEATHER_AREA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnWeatherAreaModel
     * @throws SQLException SQL実行例外
     */
    private CmnWeatherAreaModel __getCmnWeatherAreaFromRs(ResultSet rs) throws SQLException {
        CmnWeatherAreaModel bean = new CmnWeatherAreaModel();
        bean.setCwaSid(rs.getInt("CWA_SID"));
        bean.setCwaName(rs.getString("CWA_NAME"));
        bean.setCwaSort(rs.getInt("CWA_SORT"));
        return bean;
    }

    /**
     * <br>[機  能]天気予報地域一覧取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param areaSids 表示地域一覧配列
     * @return List in CMN_WEATHER_AREAModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnWeatherAreaModel> getWeatherArea(
            String[] areaSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnWeatherAreaModel> ret = new ArrayList<CmnWeatherAreaModel>();
        con = getCon();

        if (null == areaSids || areaSids.length == 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CWA_SID,");
            sql.addSql("   CWA_NAME,");
            sql.addSql("   CWA_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_WEATHER_AREA");
            sql.addSql(" where ");
            sql.addSql("   CWA_SID in (");

            int idx = 0;
            for (String sid : areaSids) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(sid));
                idx++;
            }
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnWeatherAreaFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}
