package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_PLUGIN_CONTROL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPluginControlDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPluginControlDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPluginControlDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPluginControlDao(Connection con) {
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
            sql.addSql("drop table CMN_PLUGIN_CONTROL");

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
            sql.addSql(" create table CMN_PLUGIN_CONTROL (");
            sql.addSql("   PCT_PID varchar(10) not null,");
            sql.addSql("   PCT_KBN NUMBER(10,0) not null,");
            sql.addSql("   primary key (PCT_PID)");
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
     * <p>Insert CMN_PLUGIN_CONTROL Data Bindding JavaBean
     * @param bean CMN_PLUGIN_CONTROL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPluginControlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_CONTROL(");
            sql.addSql("   PCT_PID,");
            sql.addSql("   PCT_KBN,");
            sql.addSql("   PCT_TYPE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPctPid());
            sql.addIntValue(bean.getPctKbn());
            sql.addIntValue(bean.getPctType());
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
     * <p>Update CMN_PLUGIN_CONTROL Data Bindding JavaBean
     * @param bean CMN_PLUGIN_CONTROL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPluginControlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PLUGIN_CONTROL");
            sql.addSql(" set ");
            sql.addSql("   PCT_KBN=?,");
            sql.addSql("   PCT_TYPE=?");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPctKbn());
            sql.addIntValue(bean.getPctType());
            //where
            sql.addStrValue(bean.getPctPid());

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
     * <p>Select CMN_PLUGIN_CONTROL All Data
     * @return List in CMN_PLUGIN_CONTROLModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginControlModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginControlModel> ret = new ArrayList<CmnPluginControlModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PCT_PID,");
            sql.addSql("   PCT_KBN,");
            sql.addSql("   PCT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_CONTROL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginControlFromRs(rs));
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
     * <p>Select CMN_PLUGIN_CONTROL
     * @param pctPid PCT_PID
     * @return CMN_PLUGIN_CONTROLModel
     * @throws SQLException SQL実行例外
     */
    public CmnPluginControlModel select(String pctPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPluginControlModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PCT_PID,");
            sql.addSql("   PCT_KBN,");
            sql.addSql("   PCT_TYPE");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL");
            sql.addSql(" where ");
            sql.addSql("   PCT_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnPluginControlFromRs(rs);
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
     * <br>[機  能] グループ/ユーザで使用制限が設定されているプラグインを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginIdList プラグインID一覧
     * @return グループ/ユーザで使用制限が設定されているプラグインのプラグインID一覧
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginControlModel> getMemberControlPluginList(List<String> pluginIdList)
    throws SQLException {

        List<CmnPluginControlModel> ret = new ArrayList<CmnPluginControlModel>();
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
            sql.addSql(" select");
            sql.addSql("   PCT_PID,");
            sql.addSql("   PCT_KBN,");
            sql.addSql("   PCT_TYPE");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_CONTROL");
            sql.addSql(" where ");
            sql.addSql("   PCT_KBN = ?");
            sql.addIntValue(GSConstMain.PCT_KBN_MEMBER);

            sql.addSql(" and");
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

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginControlFromRs(rs));
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
     * <p>Delete CMN_PLUGIN_CONTROL
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
            sql.addSql("   CMN_PLUGIN_CONTROL");
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
     * <p>Create CMN_PLUGIN_CONTROL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPluginControlModel
     * @throws SQLException SQL実行例外
     */
    private CmnPluginControlModel __getCmnPluginControlFromRs(ResultSet rs) throws SQLException {
        CmnPluginControlModel bean = new CmnPluginControlModel();
        bean.setPctPid(rs.getString("PCT_PID"));
        bean.setPctKbn(rs.getInt("PCT_KBN"));
        bean.setPctType(rs.getInt("PCT_TYPE"));
        return bean;
    }
}
