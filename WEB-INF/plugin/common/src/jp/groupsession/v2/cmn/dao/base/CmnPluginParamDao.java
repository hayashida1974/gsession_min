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
import jp.groupsession.v2.cmn.model.base.CmnPluginParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_PLUGIN_PARAM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPluginParamDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPluginParamDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPluginParamDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPluginParamDao(Connection con) {
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
            sql.addSql("drop table CMN_PLUGIN_PARAM");

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
            sql.addSql(" create table CMN_PLUGIN_PARAM (");
            sql.addSql("   CUP_PID varchar(10) not null,");
            sql.addSql("   CPP_NUM NUMBER(10,0) not null,");
            sql.addSql("   CPP_NAME varchar(100) not null,");
            sql.addSql("   CPP_VALUE varchar(1000),");
            sql.addSql("   primary key (CUP_PID,CPP_NUM)");
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
     * <p>Insert CMN_PLUGIN_PARAM Data Bindding JavaBean
     * @param bean CMN_PLUGIN_PARAM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPluginParamModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_PARAM(");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CPP_NUM,");
            sql.addSql("   CPP_NAME,");
            sql.addSql("   CPP_VALUE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCupPid());
            sql.addIntValue(bean.getCppNum());
            sql.addStrValue(bean.getCppName());
            sql.addStrValue(bean.getCppValue());
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
     * <p>Insert CMN_PLUGIN_PARAM Data Bindding JavaBean
     * @param beanList CMN_PLUGIN_PARAM Data List
     * @throws SQLException SQL実行例外
     */
    public void insert(List<CmnPluginParamModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PLUGIN_PARAM(");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CPP_NUM,");
            sql.addSql("   CPP_NAME,");
            sql.addSql("   CPP_VALUE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (CmnPluginParamModel mdl : beanList) {
                sql.addStrValue(mdl.getCupPid());
                sql.addIntValue(mdl.getCppNum());
                sql.addStrValue(mdl.getCppName());
                sql.addStrValue(mdl.getCppValue());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CMN_PLUGIN_PARAM Data Bindding JavaBean
     * @param bean CMN_PLUGIN_PARAM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPluginParamModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PLUGIN_PARAM");
            sql.addSql(" set ");
            sql.addSql("   CPP_NAME=?,");
            sql.addSql("   CPP_VALUE=?");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");
            sql.addSql(" and");
            sql.addSql("   CPP_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCppName());
            sql.addStrValue(bean.getCppValue());
            //where
            sql.addStrValue(bean.getCupPid());
            sql.addIntValue(bean.getCppNum());

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
     * <p>Select CMN_PLUGIN_PARAM All Data
     * @return List in CMN_PLUGIN_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnPluginParamModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginParamModel> ret = new ArrayList<CmnPluginParamModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CPP_NUM,");
            sql.addSql("   CPP_NAME,");
            sql.addSql("   CPP_VALUE");
            sql.addSql(" from ");
            sql.addSql("   CMN_PLUGIN_PARAM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginParamFromRs(rs));
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
     * <p>Select CMN_PLUGIN_PARAM
     * @param cupPid CUP_PID
     * @param cppNum CPP_NUM
     * @return CMN_PLUGIN_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public CmnPluginParamModel select(String cupPid, int cppNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPluginParamModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CPP_NUM,");
            sql.addSql("   CPP_NAME,");
            sql.addSql("   CPP_VALUE");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_PARAM");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");
            sql.addSql(" and");
            sql.addSql("   CPP_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);
            sql.addIntValue(cppNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnPluginParamFromRs(rs);
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
     * <br>[機  能] 指定したプラグインIDのパラメータデータ一覧を取得する（表示順）
     * <br>[解  説]
     * <br>[備  考]
     * @param cupPid CUP_PID
     * @return CMN_PLUGIN_PARAMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnPluginParamModel> select(String cupPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnPluginParamModel> ret = new ArrayList<CmnPluginParamModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CPP_NUM,");
            sql.addSql("   CPP_NAME,");
            sql.addSql("   CPP_VALUE");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_PARAM");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");
            sql.addSql(" order by");
            sql.addSql("   CPP_NUM asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnPluginParamFromRs(rs));
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
     * <p>Delete CMN_PLUGIN_PARAM
     * @param cupPid CUP_PID
     * @param cppNum CPP_NUM
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(String cupPid, int cppNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_PARAM");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");
            sql.addSql(" and");
            sql.addSql("   CPP_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);
            sql.addIntValue(cppNum);

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
     * <p>指定したプラグインIDのURLパラメータ情報を全て削除する
     * @param cupPid CUP_PID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(String cupPid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PLUGIN_PARAM");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);

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
     * <p>Create CMN_PLUGIN_PARAM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPluginParamModel
     * @throws SQLException SQL実行例外
     */
    private CmnPluginParamModel __getCmnPluginParamFromRs(ResultSet rs) throws SQLException {
        CmnPluginParamModel bean = new CmnPluginParamModel();
        bean.setCupPid(rs.getString("CUP_PID"));
        bean.setCppNum(rs.getInt("CPP_NUM"));
        bean.setCppName(rs.getString("CPP_NAME"));
        bean.setCppValue(rs.getString("CPP_VALUE"));
        return bean;
    }
}
