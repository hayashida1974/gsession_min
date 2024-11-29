package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.wml.model.base.WmlMailLogSendModel;

/**
 * <p>WML_MAIL_LOG_SEND Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailLogSendDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailLogSendDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailLogSendDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailLogSendDao(Connection con) {
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
            sql.addSql("drop table WML_MAIL_LOG_SEND");

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
            sql.addSql(" create table WML_MAIL_LOG_SEND (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WLS_NUM NUMBER(10,0) not null,");
            sql.addSql("   WLS_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WLS_ADDRESS varchar(768) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WMD_MAILNUM,WLS_NUM)");
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
     * <p>Insert WML_MAIL_LOG_SEND Data Bindding JavaBean
     * @param bean WML_MAIL_LOG_SEND Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailLogSendModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_LOG_SEND(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWlsNum());
            sql.addIntValue(bean.getWlsType());
            sql.addStrValue(bean.getWlsAddress());
            sql.addIntValue(bean.getWacSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <p>Insert WML_MAIL_LOG_SEND Data Bindding JavaBean
     * @param beanList WML_MAIL_LOG_SEND DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlMailLogSendModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_LOG_SEND(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = getCon().prepareStatement(sql.toSqlString());

            for (WmlMailLogSendModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addIntValue(bean.getWlsNum());
                sql.addIntValue(bean.getWlsType());
                sql.addStrValue(bean.getWlsAddress());
                sql.addIntValue(bean.getWacSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <p>Update WML_MAIL_LOG_SEND Data Bindding JavaBean
     * @param bean WML_MAIL_LOG_SEND Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailLogSendModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" set ");
            sql.addSql("   WLS_TYPE=?,");
            sql.addSql("   WLS_ADDRESS=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLS_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWlsType());
            sql.addStrValue(bean.getWlsAddress());
            //where
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWlsNum());

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
     * <p>Select WML_MAIL_LOG_SEND All Data
     * @return List in WML_MAIL_LOG_SENDModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailLogSendModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailLogSendModel> ret = new ArrayList<WmlMailLogSendModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailLogSendFromRs(rs));
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
     * <p>Select WML_MAIL_LOG_SEND All Data
     * @param wmdMailnum WMD_MAILNUM
     * @return List in WML_MAIL_LOG_SENDModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailLogSendModel> select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailLogSendModel> ret = new ArrayList<WmlMailLogSendModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addLongValue(wmdMailnum);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailLogSendFromRs(rs));
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
     * <p>Select WML_MAIL_LOG_SEND
     * @param wmdMailnum WMD_MAILNUM
     * @param wlsNum WLS_NUM
     * @return WML_MAIL_LOG_SENDModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailLogSendModel select(long wmdMailnum, int wlsNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailLogSendModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLS_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wlsNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailLogSendFromRs(rs);
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
     * <p>Delete WML_MAIL_LOG_SEND
     * @param wmdMailnum WMD_MAILNUM
     * @param wlsNum WLS_NUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, int wlsNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLS_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wlsNum);

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
     * <p>Select WML_MAIL_LOG_SEND All Data
     * @return List in WML_MAIL_LOG_SENDModel
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>Select WML_MAIL_LOG_SEND All Data
     * @param offset 取得するレコード位置
     * @param limit 取得する最大件数
     * @return List in WML_MAIL_LOG_SENDModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailLogSendModel> selectLimit(
            long offset, long limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailLogSendModel> ret = new ArrayList<WmlMailLogSendModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLS_NUM,");
            sql.addSql("   WLS_TYPE,");
            sql.addSql("   WLS_ADDRESS,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc,");
            sql.addSql("   WLS_NUM asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailLogSendFromRs(rs));
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
     * <br>[機  能] 指定したメール情報の送信先ログのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param messageNumList メッセージ番号
     * @return データサイズ合計
     * @throws SQLException
     */
    public long getTotalDataSize(List<Long> messageNumList) throws SQLException {

        if (messageNumList == null || messageNumList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long dataSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(WLS_ADDRESS)) as ADDRESS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM in (");

            for (int idx = 0; idx < messageNumList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(messageNumList.get(idx));
            }

            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("ADDRESS_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <br>[機  能] 全ての送信先ログのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データサイズ合計
     * @throws SQLException
     */
    public long getAllDataSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long dataSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(WLS_ADDRESS)) as ADDRESS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG_SEND");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("ADDRESS_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <p>Create WML_MAIL_LOG_SEND Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailLogSendModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailLogSendModel __getWmlMailLogSendFromRs(ResultSet rs) throws SQLException {
        WmlMailLogSendModel bean = new WmlMailLogSendModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWlsNum(rs.getInt("WLS_NUM"));
        bean.setWlsType(rs.getInt("WLS_TYPE"));
        bean.setWlsAddress(rs.getString("WLS_ADDRESS"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
