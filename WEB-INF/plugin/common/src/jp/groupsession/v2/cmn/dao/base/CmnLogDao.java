package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;

/**
 * <p>CMN_LOG Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnLogDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLogDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLogDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLogDao(Connection con) {
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
            sql.addSql("drop table CMN_LOG");

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
            sql.addSql(" create table CMN_LOG (");
            sql.addSql("   LOG_DATE varchar(23) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   LOG_LEVEL varchar(5) not null,");
            sql.addSql("   LOG_PLUGIN varchar(20),");
            sql.addSql("   LOG_PLUGIN_NAME varchar(50),");
            sql.addSql("   LOG_PG_ID varchar(20),");
            sql.addSql("   LOG_PG_NAME varchar(300),");
            sql.addSql("   LOG_OP_CODE varchar(10),");
            sql.addSql("   LOG_OP_VALUE varchar(3000),");
            sql.addSql("   LOG_IP varchar(40),");
            sql.addSql("   VER_VERSION varchar(60),");
            sql.addSql("   LOG_CODE varchar(100)");
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
     * <p>Insert CMN_LOG Data Bindding JavaBean
     * @param bean CMN_LOG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LOG(");
            sql.addSql("   LOG_DATE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   LOG_LEVEL,");
            sql.addSql("   LOG_PLUGIN,");
            sql.addSql("   LOG_PLUGIN_NAME,");
            sql.addSql("   LOG_PG_ID,");
            sql.addSql("   LOG_PG_NAME,");
            sql.addSql("   LOG_OP_CODE,");
            sql.addSql("   LOG_OP_VALUE,");
            sql.addSql("   LOG_IP,");
            sql.addSql("   VER_VERSION,");
            sql.addSql("   LOG_CODE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getLogDate());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getLogLevel());
            sql.addStrValue(bean.getLogPlugin());
            sql.addStrValue(bean.getLogPluginName());
            sql.addStrValue(bean.getLogPgId());
            sql.addStrValue(bean.getLogPgName());
            sql.addStrValue(bean.getLogOpCode());
            sql.addStrValue(
                    StringUtil.trimRengeString(
                            NullDefault.getString(bean.getLogOpValue(), ""), 3000));
            sql.addStrValue(bean.getLogIp());
            sql.addStrValue(bean.getVerVersion());
            sql.addStrValue(bean.getLogCode());

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
     * <p>Insert CMN_LOG Data Bindding JavaBean
     * @param beanList CMN_LOG Data List
     * @throws SQLException SQL実行例外
     */
    public void insert(List<CmnLogModel> beanList) throws SQLException {

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
            sql.addSql(" CMN_LOG(");
            sql.addSql("   LOG_DATE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   LOG_LEVEL,");
            sql.addSql("   LOG_PLUGIN,");
            sql.addSql("   LOG_PLUGIN_NAME,");
            sql.addSql("   LOG_PG_ID,");
            sql.addSql("   LOG_PG_NAME,");
            sql.addSql("   LOG_OP_CODE,");
            sql.addSql("   LOG_OP_VALUE,");
            sql.addSql("   LOG_IP,");
            sql.addSql("   VER_VERSION,");
            sql.addSql("   LOG_CODE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (CmnLogModel mdl : beanList) {
                sql.addDateValue(mdl.getLogDate());
                sql.addIntValue(mdl.getUsrSid());
                sql.addStrValue(mdl.getLogLevel());
                sql.addStrValue(mdl.getLogPlugin());
                sql.addStrValue(mdl.getLogPluginName());
                sql.addStrValue(mdl.getLogPgId());
                sql.addStrValue(mdl.getLogPgName());
                sql.addStrValue(mdl.getLogOpCode());
                sql.addStrValue(
                        StringUtil.trimRengeString(
                                NullDefault.getString(mdl.getLogOpValue(), ""), 3000));
                sql.addStrValue(mdl.getLogIp());
                sql.addStrValue(mdl.getVerVersion());
                sql.addStrValue(mdl.getLogCode());
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
     * <p>Update CMN_LOG Data Bindding JavaBean
     * @param bean CMN_LOG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LOG");
            sql.addSql(" set ");
            sql.addSql("   LOG_DATE=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   LOG_LEVEL=?,");
            sql.addSql("   LOG_PLUGIN=?,");
            sql.addSql("   LOG_PLUGIN_NAME=?,");
            sql.addSql("   LOG_PG_ID=?,");
            sql.addSql("   LOG_PG_NAME=?,");
            sql.addSql("   LOG_OP_CODE=?,");
            sql.addSql("   LOG_OP_VALUE=?,");
            sql.addSql("   LOG_IP=?,");
            sql.addSql("   VER_VERSION=?,");
            sql.addSql("   LOG_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getLogDate());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getLogLevel());
            sql.addStrValue(bean.getLogPlugin());
            sql.addStrValue(bean.getLogPluginName());
            sql.addStrValue(bean.getLogPgId());
            sql.addStrValue(bean.getLogPgName());
            sql.addStrValue(bean.getLogOpCode());
            sql.addStrValue(bean.getLogOpValue());
            sql.addStrValue(bean.getLogIp());
            sql.addStrValue(bean.getVerVersion());
            sql.addStrValue(bean.getLogCode());

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
     * <p>Select CMN_LOG All Data
     * @return ArrayList in CMN_LOGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLogModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLogModel> ret = new ArrayList<CmnLogModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LOG_DATE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   LOG_LEVEL,");
            sql.addSql("   LOG_PLUGIN,");
            sql.addSql("   LOG_PLUGIN_NAME,");
            sql.addSql("   LOG_PG_ID,");
            sql.addSql("   LOG_PG_NAME,");
            sql.addSql("   LOG_OP_CODE,");
            sql.addSql("   LOG_OP_VALUE,");
            sql.addSql("   LOG_IP,");
            sql.addSql("   VER_VERSION,");
            sql.addSql("   LOG_CODE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogFromRs(rs));
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
     * <p>Select CMN_LOG All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return ArrayList in CMN_LOGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLogModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnLogModel> ret = new ArrayList<CmnLogModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LOG_DATE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   LOG_LEVEL,");
            sql.addSql("   LOG_PLUGIN,");
            sql.addSql("   LOG_PLUGIN_NAME,");
            sql.addSql("   LOG_PG_ID,");
            sql.addSql("   LOG_PG_NAME,");
            sql.addSql("   LOG_OP_CODE,");
            sql.addSql("   LOG_OP_VALUE,");
            sql.addSql("   LOG_IP,");
            sql.addSql("   VER_VERSION,");
            sql.addSql("   LOG_CODE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG");
            sql.addSql(" order by ");
            sql.addSql("   LOG_DATE asc,");
            sql.addSql("   LOG_PLUGIN asc,");
            sql.addSql("   LOG_LEVEL asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogFromRs(rs));
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
     * <p>count CMN_LOG All Data
     * @return ArrayList in CMN_LOGModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if  (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] 指定された日付以前のログデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delDate 対象日
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(UDate delDate) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LOG");
            sql.addSql(" where ");
            sql.addSql("   LOG_DATE <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(delDate);

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
     * <p>Create CMN_LOG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLogModel
     * @throws SQLException SQL実行例外
     */
    private CmnLogModel __getCmnLogFromRs(ResultSet rs) throws SQLException {
        CmnLogModel bean = new CmnLogModel();
        bean.setLogDate(UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE")));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setLogLevel(rs.getString("LOG_LEVEL"));
        bean.setLogPlugin(rs.getString("LOG_PLUGIN"));
        bean.setLogPluginName(rs.getString("LOG_PLUGIN_NAME"));
        bean.setLogPgId(rs.getString("LOG_PG_ID"));
        bean.setLogPgName(rs.getString("LOG_PG_NAME"));
        bean.setLogOpCode(rs.getString("LOG_OP_CODE"));
        bean.setLogOpValue(rs.getString("LOG_OP_VALUE"));
        bean.setLogIp(rs.getString("LOG_IP"));
        bean.setVerVersion(rs.getString("VER_VERSION"));
        bean.setLogCode(rs.getString("LOG_CODE"));
        return bean;
    }
}
