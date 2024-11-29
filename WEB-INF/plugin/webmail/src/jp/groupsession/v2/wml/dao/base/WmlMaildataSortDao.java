package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlMaildataSortModel;

/**
 * <p>WML_MAILDATA_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMaildataSortDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMaildataSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMaildataSortDao(Connection con) {
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
            sql.addSql("drop table WML_MAILDATA_SORT");

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
     * <p>Insert WML_MAILDATA_SORT Data Bindding JavaBean
     * @param bean WML_MAILDATA_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMaildataSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAILDATA_SORT(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WMS_SORTKEY,");
            sql.addSql("   WMS_ORDER");
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
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getWdrSid());
            sql.addIntValue(bean.getWlbSid());
            sql.addIntValue(bean.getWmsSortkey());
            sql.addIntValue(bean.getWmsOrder());
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
     * <p>Update WML_MAILDATA_SORT Data Bindding JavaBean
     * @param bean WML_MAILDATA_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMaildataSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" set ");
            sql.addSql("   WMS_SORTKEY=?,");
            sql.addSql("   WMS_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWmsSortkey());
            sql.addIntValue(bean.getWmsOrder());
            //where
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getWdrSid());
            sql.addIntValue(bean.getWlbSid());

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
     * <p>Select WML_MAILDATA_SORT All Data
     * @return List in WML_MAILDATA_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataSortModel> ret = new ArrayList<WmlMaildataSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WMS_SORTKEY,");
            sql.addSql("   WMS_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataSortFromRs(rs));
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
     * <p>アカウント毎のメールソート情報を取得する
     * @param wacSid アカウントSID
     * @return List in WML_MAILDATA_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataSortModel> select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataSortModel> ret = new ArrayList<WmlMaildataSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WMS_SORTKEY,");
            sql.addSql("   WMS_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataSortFromRs(rs));
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
     * <p>Select WML_MAILDATA_SORT
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @param wdrSid WDR_SID
     * @param wlbSid WLB_SID
     * @return WML_MAILDATA_SORTModel
     * @throws SQLException SQL実行例外
     */
    public WmlMaildataSortModel select(int wacSid, int usrSid, long wdrSid, int wlbSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMaildataSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WMS_SORTKEY,");
            sql.addSql("   WMS_ORDER");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);
            sql.addLongValue(wdrSid);
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMaildataSortFromRs(rs);
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
     * <p>Delete WML_MAILDATA_SORT
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @param wdrSid WDR_SID
     * @param wlbSid WLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int usrSid, long wdrSid, int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);
            sql.addLongValue(wdrSid);
            sql.addIntValue(wlbSid);

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
     * <br>[機  能] 指定したアカウントに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfAccount(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT");
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
     * <br>[機  能] 指定したユーザに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したラベルに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wlbSid ラベルSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfLabel(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

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
     * <p>Create WML_MAILDATA_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMaildataSortModel
     * @throws SQLException SQL実行例外
     */
    private WmlMaildataSortModel __getWmlMaildataSortFromRs(ResultSet rs) throws SQLException {
        WmlMaildataSortModel bean = new WmlMaildataSortModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWdrSid(rs.getInt("WDR_SID"));
        bean.setWlbSid(rs.getInt("WLB_SID"));
        bean.setWmsSortkey(rs.getInt("WMS_SORTKEY"));
        bean.setWmsOrder(rs.getInt("WMS_ORDER"));
        return bean;
    }
}
