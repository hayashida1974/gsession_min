package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnPluginAdminModel;

/**
 * <p>CMN_PLUGIN_ADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPluginAdminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPluginAdminDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPluginAdminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPluginAdminDao(Connection con) {
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
            sql.addSql("drop table CMN_PLUGIN_ADMIN");

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
            sql.addSql(" create table CMN_PLUGIN_ADMIN (");
            sql.addSql("   PCT_PID varchar(10) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (PCT_PID,GRP_SID,USR_SID)");
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
     * <p>Insert CMN_PLUGIN_ADMIN Data Bindding JavaBean
     * @param bean CMN_PLUGIN_ADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPluginAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_ADMIN(");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPctPid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Update CMN_PLUGIN_ADMIN Data Bindding JavaBean
     * @param bean CMN_PLUGIN_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPluginAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addStrValue(bean.getPctPid());
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
     * <p>Select CMN_PLUGIN_ADMIN All Data
     * @return List in CMN_PLUGIN_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginAdminModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginAdminModel> ret = new ArrayList<CmnPluginAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginAdminFromRs(rs));
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
     * <p>Select CMN_PLUGIN_ADMIN
     * @param pctPid PCT_PID
     * @return CMN_PLUGIN_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginAdminModel> select(String pctPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnPluginAdminModel> ret = new ArrayList<CmnPluginAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginAdminFromRs(rs));
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
     * <p>Select CMN_PLUGIN_ADMIN
     * @param pctPid PCT_PID
     * @return CMN_PLUGIN_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginAdminModel> selectValidity(String pctPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnPluginAdminModel> ret = new ArrayList<CmnPluginAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_PLUGIN_ADMIN.PCT_PID,");
            sql.addSql("   CMN_PLUGIN_ADMIN.GRP_SID,");
            sql.addSql("   CMN_PLUGIN_ADMIN.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql(" left join");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" on");
            sql.addSql("   CMN_PLUGIN_ADMIN.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CMN_PLUGIN_ADMIN.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where");
            sql.addSql("   PCT_PID=?");
            sql.addSql(" and");
            sql.addSql("   ( GRP_JKBN <> ? or GRP_JKBN is null )");
            sql.addSql(" and");
            sql.addSql("   ( USR_JKBN <> ? or USR_JKBN is null )");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginAdminFromRs(rs));
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
     * <p>プラグイン管理者に設定されている件数を取得する。
     * @param usrSid ユーザSID
     * @param pluginId プラグインID
     * @return List in CMN_PLUGIN_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public int getCountPluginAdmin(int usrSid, String pluginId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(CMN_PLUGIN_ADMIN.USR_SID) as CNT");
            sql.addSql(" from  ");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql("   left join");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   on");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID > 0");
            sql.addSql("   and");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql(" where  ");
            sql.addSql("   (");
            sql.addSql("     CMN_PLUGIN_ADMIN.USR_SID = ?");
            sql.addSql("   or");
            sql.addSql("     CMN_BELONGM.USR_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   CMN_PLUGIN_ADMIN.PCT_PID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addStrValue(pluginId);

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
     * <p>プラグイン管理者に設定されている件数を取得する。
     * @param usrSid ユーザSID
     * @return List in CMN_PLUGIN_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public int getCountPluginAdmin(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(CMN_PLUGIN_ADMIN.USR_SID) as CNT");
            sql.addSql(" from  ");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql("   left join");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   on");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID > 0");
            sql.addSql("   and");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql(" where  ");
            sql.addSql("   (");
            sql.addSql("     CMN_PLUGIN_ADMIN.USR_SID = ?");
            sql.addSql("   or");
            sql.addSql("     CMN_BELONGM.USR_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);

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
     * <p>プラグイン管理者に設定されているプラグイン一覧を取得する。
     * @param usrSid ユーザSID
     * @return List in String
     * @throws SQLException SQL実行例外
     */
    public List<String> getPluginAdmin(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_PLUGIN_ADMIN.PCT_PID");
            sql.addSql(" from  ");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql("   left join");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   on");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID > 0");
            sql.addSql("   and");
            sql.addSql("     CMN_PLUGIN_ADMIN.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql(" where  ");
            sql.addSql("   (");
            sql.addSql("     CMN_PLUGIN_ADMIN.USR_SID = ?");
            sql.addSql("   or");
            sql.addSql("     CMN_BELONGM.USR_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("PCT_PID"));
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
     * <p>Delete CMN_PLUGIN_CONTROL_MEMBER
     * @param pctPid PCT_PID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String pctPid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);

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
     * <p>Create CMN_PLUGIN_ADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPluginControlAdminModel
     * @throws SQLException SQL実行例外
     */
    private CmnPluginAdminModel __getCmnPluginAdminFromRs(ResultSet rs) throws SQLException {
        CmnPluginAdminModel bean = new CmnPluginAdminModel();
        bean.setPctPid(rs.getString("PCT_PID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
