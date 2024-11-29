package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_LOG_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLogConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLogConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLogConfDao(Connection con) {
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
            sql.addSql("drop table CMN_LOG_CONF");

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
            sql.addSql(" create table CMN_LOG_CONF (");
            sql.addSql("   LGC_PLUGIN varchar(20) not null,");
            sql.addSql("   LGC_LEVEL_ERROR NUMBER(10,0) not null,");
            sql.addSql("   LGC_LEVEL_WARN NUMBER(10,0) not null,");
            sql.addSql("   LGC_LEVEL_INFO NUMBER(10,0) not null,");
            sql.addSql("   LGC_LEVEL_TRACE NUMBER(10,0) not null,");
            sql.addSql("   LGC_AUID NUMBER(10,0),");
            sql.addSql("   LGC_ADATE varchar(23),");
            sql.addSql("   LGC_EUID NUMBER(10,0),");
            sql.addSql("   LGC_EDATE varchar(23),");
            sql.addSql("   primary key (LGC_PLUGIN)");
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
     * <p>Insert CMN_LOG_CONF Data Bindding JavaBean
     * @param bean CMN_LOG_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLogConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOG_CONF(");
            sql.addSql("   LGC_PLUGIN,");
            sql.addSql("   LGC_LEVEL_ERROR,");
            sql.addSql("   LGC_LEVEL_WARN,");
            sql.addSql("   LGC_LEVEL_INFO,");
            sql.addSql("   LGC_LEVEL_TRACE,");
            sql.addSql("   LGC_AUID,");
            sql.addSql("   LGC_ADATE,");
            sql.addSql("   LGC_EUID,");
            sql.addSql("   LGC_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getLgcPlugin());
            sql.addIntValue(bean.getLgcLevelError());
            sql.addIntValue(bean.getLgcLevelWarn());
            sql.addIntValue(bean.getLgcLevelInfo());
            sql.addIntValue(bean.getLgcLevelTrace());
            sql.addIntValue(bean.getLgcAuid());
            sql.addDateValue(bean.getLgcAdate());
            sql.addIntValue(bean.getLgcEuid());
            sql.addDateValue(bean.getLgcEdate());
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
     * <p>Update CMN_LOG_CONF Data Bindding JavaBean
     * @param bean CMN_LOG_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLogConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOG_CONF");
            sql.addSql(" set ");
            sql.addSql("   LGC_LEVEL_ERROR=?,");
            sql.addSql("   LGC_LEVEL_WARN=?,");
            sql.addSql("   LGC_LEVEL_INFO=?,");
            sql.addSql("   LGC_LEVEL_TRACE=?,");
            sql.addSql("   LGC_EUID=?,");
            sql.addSql("   LGC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   LGC_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLgcLevelError());
            sql.addIntValue(bean.getLgcLevelWarn());
            sql.addIntValue(bean.getLgcLevelInfo());
            sql.addIntValue(bean.getLgcLevelTrace());
            sql.addIntValue(bean.getLgcEuid());
            sql.addDateValue(bean.getLgcEdate());
            //where
            sql.addStrValue(bean.getLgcPlugin());

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
     * <p>Select CMN_LOG_CONF All Data
     * @return ArrayList in CMN_LOG_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLogConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLogConfModel> ret = new ArrayList<CmnLogConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LGC_PLUGIN,");
            sql.addSql("   LGC_LEVEL_ERROR,");
            sql.addSql("   LGC_LEVEL_WARN,");
            sql.addSql("   LGC_LEVEL_INFO,");
            sql.addSql("   LGC_LEVEL_TRACE,");
            sql.addSql("   LGC_AUID,");
            sql.addSql("   LGC_ADATE,");
            sql.addSql("   LGC_EUID,");
            sql.addSql("   LGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogConfFromRs(rs));
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
     * <p>Select CMN_LOG_CONF All Data
     * @return ArrayList in CMN_LOG_CONFModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, CmnLogConfModel> selectMap() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, CmnLogConfModel> ret = new HashMap<String, CmnLogConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LGC_PLUGIN,");
            sql.addSql("   LGC_LEVEL_ERROR,");
            sql.addSql("   LGC_LEVEL_WARN,");
            sql.addSql("   LGC_LEVEL_INFO,");
            sql.addSql("   LGC_LEVEL_TRACE,");
            sql.addSql("   LGC_AUID,");
            sql.addSql("   LGC_ADATE,");
            sql.addSql("   LGC_EUID,");
            sql.addSql("   LGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("LGC_PLUGIN"), __getCmnLogConfFromRs(rs));
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
     * <p>Select CMN_LOG_CONF
     * @param lgcPlugin LGC_PLUGIN
     * @return CMN_LOG_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnLogConfModel select(String lgcPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnLogConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LGC_PLUGIN,");
            sql.addSql("   LGC_LEVEL_ERROR,");
            sql.addSql("   LGC_LEVEL_WARN,");
            sql.addSql("   LGC_LEVEL_INFO,");
            sql.addSql("   LGC_LEVEL_TRACE,");
            sql.addSql("   LGC_AUID,");
            sql.addSql("   LGC_ADATE,");
            sql.addSql("   LGC_EUID,");
            sql.addSql("   LGC_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_LOG_CONF");
            sql.addSql(" where ");
            sql.addSql("   LGC_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(lgcPlugin);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnLogConfFromRs(rs);
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
     * <p>プラグインIDリストで指定したプラグインのログ設定を取得する。
     * @param pluginIdList プラグインIDリスト
     * @return ArrayList in CMN_LOG_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLogConfModel> select(List<String> pluginIdList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLogConfModel> ret = new ArrayList<CmnLogConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LGC_PLUGIN,");
            sql.addSql("   LGC_LEVEL_ERROR,");
            sql.addSql("   LGC_LEVEL_WARN,");
            sql.addSql("   LGC_LEVEL_INFO,");
            sql.addSql("   LGC_LEVEL_TRACE,");
            sql.addSql("   LGC_AUID,");
            sql.addSql("   LGC_ADATE,");
            sql.addSql("   LGC_EUID,");
            sql.addSql("   LGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG_CONF");
            sql.addSql(" where ");
            sql.addSql("   LGC_PLUGIN in (");

            int i = 0;
            for (String pluginId : pluginIdList) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addStrValue(pluginId);
                i++;
            }

            sql.addSql(")");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogConfFromRs(rs));
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
     * <p>Delete CMN_LOG_CONF
     * @param lgcPlugin LGC_PLUGIN
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(String lgcPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LOG_CONF");
            sql.addSql(" where ");
            sql.addSql("   LGC_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(lgcPlugin);

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
     * <p>指定したプラグインID以外を削除
     * @param notDelList 削除しないID一覧
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<String> notDelList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LOG_CONF");
            sql.addSql(" where ");
            sql.addSql("   LGC_PLUGIN not in(");
            for (int i = 0; i < notDelList.size(); i++) {
                String id = notDelList.get(i);
                if (i == 0) {
                    sql.addSql(" ? ");
                } else {
                    sql.addSql(", ? ");
                }
                sql.addStrValue(id);
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>Create CMN_LOG_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLogConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnLogConfModel __getCmnLogConfFromRs(ResultSet rs) throws SQLException {
        CmnLogConfModel bean = new CmnLogConfModel();
        bean.setLgcPlugin(rs.getString("LGC_PLUGIN"));
        bean.setLgcLevelError(rs.getInt("LGC_LEVEL_ERROR"));
        bean.setLgcLevelWarn(rs.getInt("LGC_LEVEL_WARN"));
        bean.setLgcLevelInfo(rs.getInt("LGC_LEVEL_INFO"));
        bean.setLgcLevelTrace(rs.getInt("LGC_LEVEL_TRACE"));
        bean.setLgcAuid(rs.getInt("LGC_AUID"));
        bean.setLgcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LGC_ADATE")));
        bean.setLgcEuid(rs.getInt("LGC_EUID"));
        bean.setLgcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LGC_EDATE")));
        return bean;
    }
}
