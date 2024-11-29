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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlMailLogModel;

/**
 * <p>WML_MAIL_LOG Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailLogDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailLogDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailLogDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailLogDao(Connection con) {
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
            sql.addSql("drop table WML_MAIL_LOG");

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
            sql.addSql(" create table WML_MAIL_LOG (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WLG_TITLE varchar(1000) not null,");
            sql.addSql("   WLG_DATE varchar(23),");
            sql.addSql("   WLG_FROM varchar(256),");
            sql.addSql("   WLG_TEMPFLG NUMBER(10,0) not null,");
            sql.addSql("   WLG_MAILTYPE NUMBER(10,0) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WMD_MAILNUM)");
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
     * <p>Insert WML_MAIL_LOG Data Bindding JavaBean
     * @param bean WML_MAIL_LOG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_LOG(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLG_TITLE,");
            sql.addSql("   WLG_DATE,");
            sql.addSql("   WLG_FROM,");
            sql.addSql("   WLG_TEMPFLG,");
            sql.addSql("   WLG_MAILTYPE,");
            sql.addSql("   WAC_SID");
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

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addStrValue(bean.getWlgTitle());
            sql.addDateValue(bean.getWlgDate());
            sql.addStrValue(bean.getWlgFrom());
            sql.addIntValue(bean.getWlgTempflg());
            sql.addIntValue(bean.getWlgMailtype());
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
     * <p>Insert WML_MAIL_LOG Data Bindding JavaBean
     * @param beanList WML_MAIL_LOG DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlMailLogModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;

        SqlBuffer sql = new SqlBuffer();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_LOG(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLG_TITLE,");
            sql.addSql("   WLG_DATE,");
            sql.addSql("   WLG_FROM,");
            sql.addSql("   WLG_TEMPFLG,");
            sql.addSql("   WLG_MAILTYPE,");
            sql.addSql("   WAC_SID");
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

            pstmt = getCon().prepareStatement(sql.toSqlString());

            for (WmlMailLogModel bean : beanList) {

                sql.addLongValue(bean.getWmdMailnum());
                sql.addStrValue(bean.getWlgTitle());
                sql.addDateValue(bean.getWlgDate());
                sql.addStrValue(bean.getWlgFrom());
                sql.addIntValue(bean.getWlgTempflg());
                sql.addIntValue(bean.getWlgMailtype());
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
     * <p>Update WML_MAIL_LOG Data Bindding JavaBean
     * @param bean WML_MAIL_LOG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailLogModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_LOG");
            sql.addSql(" set ");
            sql.addSql("   WLG_TITLE=?,");
            sql.addSql("   WLG_DATE=?,");
            sql.addSql("   WLG_FROM=?,");
            sql.addSql("   WLG_TEMPFLG=?,");
            sql.addSql("   WLG_MAILTYPE=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWlgTitle());
            sql.addDateValue(bean.getWlgDate());
            sql.addStrValue(bean.getWlgFrom());
            sql.addIntValue(bean.getWlgTempflg());
            sql.addIntValue(bean.getWlgMailtype());
            //where
            sql.addLongValue(bean.getWmdMailnum());

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
     * <p>Select WML_MAIL_LOG All Data
     * @return List in WML_MAIL_LOGModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailLogModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailLogModel> ret = new ArrayList<WmlMailLogModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLG_TITLE,");
            sql.addSql("   WLG_DATE,");
            sql.addSql("   WLG_FROM,");
            sql.addSql("   WLG_TEMPFLG,");
            sql.addSql("   WLG_MAILTYPE,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailLogFromRs(rs));
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
     * <p>Select WML_MAIL_LOG
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_MAIL_LOGModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailLogModel select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailLogModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLG_TITLE,");
            sql.addSql("   WLG_DATE,");
            sql.addSql("   WLG_FROM,");
            sql.addSql("   WLG_TEMPFLG,");
            sql.addSql("   WLG_MAILTYPE,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_LOG");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailLogFromRs(rs);
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
     * <br>[機  能] 送受信ログの最小/最大日時
     * <br>[解  説]
     * <br>[備  考]
     * @return 送受信ログの最小/最大日時
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLimitDate() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UDate[] limitDate = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(WLG_DATE) as MINDATE,");
            sql.addSql("   max(WLG_DATE) as MAXDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");

            pstmt = getCon().prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                limitDate = new UDate[2];
                limitDate[0] = UDate.getInstanceTimestamp(rs.getTimestamp("MINDATE"));
                limitDate[1] = UDate.getInstanceTimestamp(rs.getTimestamp("MAXDATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return limitDate;
    }

    /**
     * <p>Delete WML_MAIL_LOG
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_LOG");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

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
     * <p>Select WML_MAIL_LOG All Data
     * @return List in WML_MAIL_LOGModel
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
            sql.addSql("   WML_MAIL_LOG");

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
     * <p>Select WML_MAIL_LOG All Data
     * @param offset 取得するレコード位置
     * @param limit 取得する最大件数
     * @return List in WML_MAIL_LOGModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailLogModel> selectLimit(
            long offset, long limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailLogModel> ret = new ArrayList<WmlMailLogModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLG_TITLE,");
            sql.addSql("   WLG_DATE,");
            sql.addSql("   WLG_FROM,");
            sql.addSql("   WLG_TEMPFLG,");
            sql.addSql("   WLG_MAILTYPE,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailLogFromRs(rs));
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
            sql.addSql("   sum(octet_length(WLG_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(WLG_FROM)) as FROM_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");
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
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("FROM_SIZE");
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
            sql.addSql("   sum(octet_length(WLG_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(WLG_FROM)) as FROM_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_LOG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("FROM_SIZE");
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
     * <p>Create WML_MAIL_LOG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailLogModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailLogModel __getWmlMailLogFromRs(ResultSet rs) throws SQLException {
        WmlMailLogModel bean = new WmlMailLogModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWlgTitle(rs.getString("WLG_TITLE"));
        bean.setWlgDate(UDate.getInstanceTimestamp(rs.getTimestamp("WLG_DATE")));
        bean.setWlgFrom(rs.getString("WLG_FROM"));
        bean.setWlgTempflg(rs.getInt("WLG_TEMPFLG"));
        bean.setWlgMailtype(rs.getInt("WLG_MAILTYPE"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
