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
import jp.groupsession.v2.cmn.model.base.CmnPluginControlMemberModel;

/**
 * <p>CMN_PLUGIN_CONTROL_MEMBER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPluginControlMemberDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPluginControlMemberDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPluginControlMemberDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPluginControlMemberDao(Connection con) {
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
            sql.addSql("drop table CMN_PLUGIN_CONTROL_MEMBER");

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
            sql.addSql(" create table CMN_PLUGIN_CONTROL_MEMBER (");
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
     * <p>Insert CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean
     * @param bean CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPluginControlMemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_CONTROL_MEMBER(");
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
     * <p>Update CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean
     * @param bean CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPluginControlMemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
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
     * <p>Select CMN_PLUGIN_CONTROL_MEMBER All Data
     * @return List in CMN_PLUGIN_CONTROL_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginControlMemberModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginControlMemberModel> ret = new ArrayList<CmnPluginControlMemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginControlMemberFromRs(rs));
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
     * <p>Select CMN_PLUGIN_CONTROL_MEMBER All Data
     * @param pctPid PCT_PID
     * @return List in CMN_PLUGIN_CONTROL_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginControlMemberModel> select(String pctPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginControlMemberModel> ret = new ArrayList<CmnPluginControlMemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginControlMemberFromRs(rs));
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
     * <p>Select CMN_PLUGIN_CONTROL_MEMBER
     * @param pctPid PCT_PID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return CMN_PLUGIN_CONTROL_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public CmnPluginControlMemberModel select(String pctPid, int grpSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPluginControlMemberModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PCT_PID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);
            sql.addIntValue(grpSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnPluginControlMemberFromRs(rs);
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
     * <br>[機  能] 指定したユーザがアクセス権設定されているプラグインのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginIdList プラグインID
     * @param userSid ユーザSID
     * @return プラグインSID
     * @throws SQLException SQL実行例外
     */
    public List<String> getCantUsePluginList(List<String> pluginIdList, int userSid)
    throws SQLException {

        List<String> ret = new ArrayList<String>();
        if (pluginIdList == null || pluginIdList.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PCT_PID");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");

            sql.addSql(" where ");
            if (pluginIdList.size() == 1) {
                sql.addSql("   PCT_PID = ?");
                sql.addStrValue(pluginIdList.get(0));
            } else {
                sql.addSql("   PCT_PID in (");
                sql.addSql("     ?");
                sql.addStrValue(pluginIdList.get(0));

                for (int idx = 1; idx < pluginIdList.size(); idx++) {
                    sql.addSql("     ,?");
                    sql.addStrValue(pluginIdList.get(idx));
                }
                sql.addSql("   )");
            }

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      USR_SID = ?");
            sql.addSql("    or");
            sql.addSql("      GRP_SID in (");
            sql.addSql("        select GRP_SID from CMN_BELONGM");
            sql.addSql("        where USR_SID = ?");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            sql.addSql(" group by");
            sql.addSql("   PCT_PID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したプラグインを使用可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param userSidList ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getCantUseUserList(String pluginId, List<Integer> userSidList)
    throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (userSidList == null || userSidList.isEmpty()) {
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
            sql.addSql("   CMN_BELONGM.USR_SID as BELONGM_USR_SID,");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER.USR_SID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql("   left join");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   on");
            sql.addSql("     CMN_PLUGIN_CONTROL_MEMBER.GRP_SID >= 0");
            sql.addSql("   and");
            sql.addSql("     CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql(" where");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
            sql.addStrValue(pluginId);

            String usrSidSql = "";
            if (userSidList.size() == 1) {
                usrSidSql += " = " + userSidList.get(0).toString();
            } else {
                usrSidSql += " in ( " + userSidList.get(0).toString();
                for (int idx = 1; idx < userSidList.size(); idx++) {
                    usrSidSql += ", " + userSidList.get(idx);
                }
                usrSidSql += ")";
            }

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("       CMN_PLUGIN_CONTROL_MEMBER.GRP_SID >= 0");
            sql.addSql("     and");
            sql.addSql("       CMN_BELONGM.USR_SID" + usrSidSql);
            sql.addSql("     )");
            sql.addSql("    or");
            sql.addSql("     (");
            sql.addSql("       CMN_PLUGIN_CONTROL_MEMBER.GRP_SID < 0");
            sql.addSql("     and");
            sql.addSql("       CMN_PLUGIN_CONTROL_MEMBER.USR_SID" + usrSidSql);
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" group by");
            sql.addSql("   CMN_BELONGM.USR_SID,");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer userSid = rs.getInt("BELONGM_USR_SID");
                if (userSid > 0 && ret.indexOf(userSid) < 0) {
                    ret.add(userSid);
                } else {
                    userSid = rs.getInt("USR_SID");
                    if (userSid > 0 && ret.indexOf(userSid) < 0) {
                        ret.add(userSid);
                    }
                }

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
     * <br>[機  能] 指定したプラグインを使用可能なグループを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param grpSidList グループSID
     * @return グループSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getCantUseGroupList(String pluginId, List<Integer> grpSidList)
    throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (grpSidList == null || grpSidList.isEmpty()) {
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
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where");
            sql.addSql("   PCT_PID = ?");
            sql.addStrValue(pluginId);

            sql.addSql(" and");
            if (grpSidList.size() == 1) {
                sql.addSql("   GRP_SID = ?");
                sql.addIntValue(grpSidList.get(0));
            } else {
                sql.addSql("   GRP_SID in (?");
                sql.addIntValue(grpSidList.get(0));
                for (int idx = 1; idx < grpSidList.size(); idx++) {
                    sql.addSql("      ,?");
                    sql.addIntValue(grpSidList.get(idx));
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("GRP_SID"));
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
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
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
     * <p>Delete CMN_PLUGIN_CONTROL_MEMBER
     * @param pctPid PCT_PID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String pctPid, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);
            sql.addIntValue(grpSid);
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
     * <p>Delete CMN_PLUGIN_CONTROL_MEMBER
     * @param grpSid GRP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Delete CMN_PLUGIN_CONTROL_MEMBER
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
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
     * <br>[機  能]指定されているユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインＩＤ
     * @return ユーザ一覧
     * @throws SQLException SQLException
     */
    public List<Integer> selectUsrList(String pluginId) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql(" where");
            sql.addSql("   PCT_PID = ?");
            sql.addStrValue(pluginId);
            sql.addSql(" and");
            sql.addSql("   CMN_PLUGIN_CONTROL_MEMBER.USR_SID <> -1");
            sql.addSql(" UNION");
            sql.addSql(" (");
            sql.addSql("  select");
            sql.addSql("    CMN_BELONGM.USR_SID");
            sql.addSql("  from");
            sql.addSql("    CMN_PLUGIN_CONTROL_MEMBER");
            sql.addSql("  left join");
            sql.addSql("    CMN_BELONGM");
            sql.addSql("  on");
            sql.addSql("    CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("  where");
            sql.addSql("    CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
            sql.addStrValue(pluginId);
            sql.addSql("  and ");
            sql.addSql("    CMN_PLUGIN_CONTROL_MEMBER.GRP_SID <> -1");
            sql.addSql(" )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <p>Create CMN_PLUGIN_CONTROL_MEMBER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPluginControlMemberModel
     * @throws SQLException SQL実行例外
     */
    private CmnPluginControlMemberModel __getCmnPluginControlMemberFromRs(ResultSet rs)
    throws SQLException {
        CmnPluginControlMemberModel bean = new CmnPluginControlMemberModel();
        bean.setPctPid(rs.getString("PCT_PID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
