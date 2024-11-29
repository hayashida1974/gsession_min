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
import jp.groupsession.v2.wml.model.base.WmlDelmailListModel;

/**
 * <p>WML_DELMAIL_LIST Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDelmailListDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDelmailListDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDelmailListDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDelmailListDao(Connection con) {
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
            sql.addSql("drop table WML_DELMAIL_LIST");

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
            sql.addSql(" create table WML_DELMAIL_LIST (");
            sql.addSql("   WAC_SID integer not null,");
            sql.addSql("   WMD_MAILNUM bigint not null,");
            sql.addSql("   WDL_ADATE timestamp not null,");
            sql.addSql("   primary key (WAC_SID,WMD_MAILNUM)");
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
     * <p>Insert WML_DELMAIL_LIST Data Bindding JavaBean
     * @param bean WML_DELMAIL_LIST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDelmailListModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DELMAIL_LIST(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WDL_ADATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addDateValue(bean.getWdlAdate());
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
     * <p>Update WML_DELMAIL_LIST Data Bindding JavaBean
     * @param bean WML_DELMAIL_LIST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDelmailListModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" set ");
            sql.addSql("   WDL_ADATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getWdlAdate());
            //where
            sql.addIntValue(bean.getWacSid());
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
     * <p>Select WML_DELMAIL_LIST All Data
     * @return List in WML_DELMAIL_LISTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDelmailListModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDelmailListModel> ret = new ArrayList<WmlDelmailListModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WDL_ADATE");
            sql.addSql(" from ");
            sql.addSql("   WML_DELMAIL_LIST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDelmailListFromRs(rs));
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
     * <p>Select WML_DELMAIL_LIST
     * @param wacSid WAC_SID
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_DELMAIL_LISTModel
     * @throws SQLException SQL実行例外
     */
    public WmlDelmailListModel select(int wacSid, long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDelmailListModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WDL_ADATE");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDelmailListFromRs(rs);
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
     *
     * <br>[機  能] 指定ユーザが登録した一括削除対象メールのメッセージ番号一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メッセージ番号一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getDelmailList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> mailNumList = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from ");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailNumList.add(rs.getLong("WMD_MAILNUM"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mailNumList;
    }

    /**
     * <br>[機  能] 一括削除対象メールの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return count 件数
     * @throws SQLException SQL実行例外
     */
    public int getDelmailCount(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete WML_DELMAIL_LIST
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <p>Delete WML_DELMAIL_LIST
     * @param wacSid WAC_SID
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
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
     * <br>[機  能] 登録されている情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteAll() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 一括削除対象メール情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNumList メッセージ番号一覧
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteDellist(int wacSid, List<Long> mailNumList)
    throws SQLException {

        if (mailNumList == null || mailNumList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM in (");

            sql.addSql("     ?");
            sql.addLongValue(mailNumList.get(0));

            for (int numIdx = 1; numIdx < mailNumList.size(); numIdx++) {
                sql.addSql("     ,?");
                sql.addLongValue(mailNumList.get(numIdx));
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 一時保管した削除対象メール情報をメール情報一括削除対象メール情報として登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSid
     * @throws SQLException SQL実行時例外
     */
    public void insertTempData(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DELMAIL_LIST(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WDL_ADATE");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_TEMP");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addDateValue(new UDate());
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 一括削除実行中に中断したアカウントの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メッセージ番号一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSuspendedAccount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> accountList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   min(WDL_ADATE) as ADATE");
            sql.addSql(" from");
            sql.addSql("   WML_DELMAIL_LIST");
            sql.addSql(" group by");
            sql.addSql("   WAC_SID");
            sql.addSql(" order by");
            sql.addSql("   ADATE asc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountList.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return accountList;
    }

    /**
     * <p>Create WML_DELMAIL_LIST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDelmailListModel
     * @throws SQLException SQL実行例外
     */
    private WmlDelmailListModel __getWmlDelmailListFromRs(ResultSet rs) throws SQLException {
        WmlDelmailListModel bean = new WmlDelmailListModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWdlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WDL_ADATE")));
        return bean;
    }
}
