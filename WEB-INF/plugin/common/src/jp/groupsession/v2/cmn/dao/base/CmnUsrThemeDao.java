package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrThemeModel;

/**
 * <p>CMN_USR_THEME Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrThemeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrThemeDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrThemeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrThemeDao(Connection con) {
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
            sql.addSql("drop table CMN_USR_THEME");

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
            sql.addSql(" create table CMN_USR_THEME (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   CTM_SID NUMBER(10,0) not null,");
            sql.addSql("   UTM_AUID NUMBER(10,0) not null,");
            sql.addSql("   UTM_ADATE varchar(23) not null,");
            sql.addSql("   UTM_EUID NUMBER(10,0) not null,");
            sql.addSql("   UTM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID,CTM_SID)");
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
     * <p>Insert CMN_USR_THEME Data Bindding JavaBean
     * @param bean CMN_USR_THEME Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrThemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_THEME(");
            sql.addSql("   USR_SID,");
            sql.addSql("   CTM_SID,");
            sql.addSql("   UTM_AUID,");
            sql.addSql("   UTM_ADATE,");
            sql.addSql("   UTM_EUID,");
            sql.addSql("   UTM_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getCtmSid());
            sql.addIntValue(bean.getUtmAuid());
            sql.addDateValue(bean.getUtmAdate());
            sql.addIntValue(bean.getUtmEuid());
            sql.addDateValue(bean.getUtmEdate());
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
     * <p>Update CMN_USR_THEME Data Bindding JavaBean
     * @param bean CMN_USR_THEME Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrThemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" set ");
            sql.addSql("   CTM_SID=?,");
            sql.addSql("   UTM_EUID=?,");
            sql.addSql("   UTM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCtmSid());
            sql.addIntValue(bean.getUtmEuid());
            sql.addDateValue(bean.getUtmEdate());
            //where
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
     * <p>Select CMN_USR_THEME All Data
     * @return List in CMN_USR_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrThemeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrThemeModel> ret = new ArrayList<CmnUsrThemeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CTM_SID,");
            sql.addSql("   UTM_AUID,");
            sql.addSql("   UTM_ADATE,");
            sql.addSql("   UTM_EUID,");
            sql.addSql("   UTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_THEME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrThemeFromRs(rs));
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
     * <p>Select CMN_USR_THEME
     * @param usrSid USR_SID
     * @param ctmSid CTM_SID
     * @return CMN_USR_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrThemeModel select(int usrSid, int ctmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrThemeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CTM_SID,");
            sql.addSql("   UTM_AUID,");
            sql.addSql("   UTM_ADATE,");
            sql.addSql("   UTM_EUID,");
            sql.addSql("   UTM_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ctmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrThemeFromRs(rs);
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
     * <p>Select CMN_USR_THEME
     * @param usrSid USR_SID
     * @return CMN_USR_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public String select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_THEME.CTM_PATH as CTM_PATH");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_THEME,");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CMN_USR_THEME.USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USR_THEME.CTM_SID = CMN_THEME.CTM_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("CTM_PATH");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        log__.debug("THEME = " + ret);
        return ret;
    }

    /**
     * <p>Select CMN_USR_THEME
     * @param usrSid USR_SID
     * @return CMN_USR_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Integer> select(List<Integer> usrSids) throws SQLException {
        
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        if (usrSids == null || usrSids.isEmpty()) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USR_THEME.CTM_SID as CTM_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USR_THEME.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID in (");
            for (int idx = 0; idx < usrSids.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(usrSids.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), rs.getInt("CTM_SID"));
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
     * <p>Select CMN_USR_THEME
     * @param usrSid USR_SID
     * @return CMN_USR_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public int getThemeSid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CTM_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CTM_SID");
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
     * <p>Delete CMN_USR_THEME
     * @param usrSid USR_SID
     * @param ctmSid CTM_SID
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int ctmSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_THEME");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(ctmSid);

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
     * <p>Delete CMN_USR_THEME
     * @param usrSid USR_SID
     * @return count
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
            sql.addSql("   CMN_USR_THEME");
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
     * <p>Create CMN_USR_THEME Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrThemeModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrThemeModel __getCmnUsrThemeFromRs(ResultSet rs) throws SQLException {
        CmnUsrThemeModel bean = new CmnUsrThemeModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCtmSid(rs.getInt("CTM_SID"));
        bean.setUtmAuid(rs.getInt("UTM_AUID"));
        bean.setUtmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UTM_ADATE")));
        bean.setUtmEuid(rs.getInt("UTM_EUID"));
        bean.setUtmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UTM_EDATE")));

        return bean;
    }
}