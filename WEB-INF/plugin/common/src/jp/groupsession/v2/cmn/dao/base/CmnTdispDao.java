package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_TDISP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnTdispDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnTdispDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnTdispDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnTdispDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_TDISP Data Bindding JavaBean
     * @param bean CMN_TDISP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_TDISP(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getTdpPid());
            sql.addIntValue(bean.getTdpOrder());
            sql.addIntValue(bean.getTdpAuid());
            sql.addDateValue(bean.getTdpAdate());
            sql.addIntValue(bean.getTdpEuid());
            sql.addDateValue(bean.getTdpEdate());
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
     * <br>[機  能] 新規プラグイン登録時の各ユーザのメニュー表示設定を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_TDISP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertNewPluginConfig(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   ?,");
            sql.addSql("   max(TDP_ORDER) + 1,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where");
            sql.addSql("   USR_SID <> ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select USR_SID from CMN_TDISP");
            sql.addSql("     where TDP_PID = ?");
            sql.addSql("   )");
            sql.addSql(" group by");
            sql.addSql("   USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getTdpPid());
            sql.addIntValue(bean.getTdpAuid());
            sql.addDateValue(bean.getTdpAdate());
            sql.addIntValue(bean.getTdpEuid());
            sql.addDateValue(bean.getTdpEdate());
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            sql.addStrValue(bean.getTdpPid());

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
     * <p>Update CMN_TDISP Data Bindding JavaBean
     * @param bean CMN_TDISP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int update(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" set ");
            sql.addSql("   TDP_ORDER=?,");
            sql.addSql("   TDP_AUID=?,");
            sql.addSql("   TDP_ADATE=?,");
            sql.addSql("   TDP_EUID=?,");
            sql.addSql("   TDP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTdpOrder());
            sql.addIntValue(bean.getTdpAuid());
            sql.addDateValue(bean.getTdpAdate());
            sql.addIntValue(bean.getTdpEuid());
            sql.addDateValue(bean.getTdpEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getTdpPid());

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
     * <p>表示順、変更者ID、変更日付の更新
     * @param bean CMN_TDISP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int updateSort(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" set ");
            sql.addSql("   TDP_ORDER=?,");
            sql.addSql("   TDP_EUID=?,");
            sql.addSql("   TDP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTdpOrder());
            sql.addIntValue(bean.getTdpEuid());
            sql.addDateValue(bean.getTdpEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getTdpPid());

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
     * <p>Select CMN_TDISP All Data
     * @return List in CMN_TDISPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnTdispModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnTdispModel> ret = new ArrayList<CmnTdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDISP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnTdispFromRs(rs));
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
     * <br>[機  能] 指定されたユーザのトップ表示設定を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CMN_TDISPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnTdispModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnTdispModel> ret = new ArrayList<CmnTdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   TDP_ORDER");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnTdispFromRs(rs));
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
     * <br>[機  能] 指定されたユーザのトップ表示設定(管理者設定)を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_TDISPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnTdispModel> getAdminTdispList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnTdispModel> ret = new ArrayList<CmnTdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TDP_ORDER > 0");
            sql.addSql(" order by");
            sql.addSql("   TDP_ORDER");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnTdispFromRs(rs));
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
     * <br>[機  能] メニューに表示するプラグインのID一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return プラグインID一覧
     * @throws SQLException SQL実行例外
     */
    public List<String> getMenuPluginIdList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TDP_PID");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TDP_ORDER > 0");
            sql.addSql(" order by");
            sql.addSql("   TDP_ORDER");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("TDP_PID"));
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
     * <p>Select CMN_TDISP USR_SID MUCH
     * @param bean CMN_TDISP Model
     * @return List in CMN_TDISPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnTdispModel> selectUsrSid(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnTdispModel> ret = new ArrayList<CmnTdispModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnTdispFromRs(rs));
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
     * <p>Select CMN_TDISP
     * @param bean CMN_TDISP Model
     * @return CMN_TDISPModel
     * @throws SQLException SQL実行例外
     */
    public CmnTdispModel select(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnTdispModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TDP_PID,");
            sql.addSql("   TDP_ORDER,");
            sql.addSql("   TDP_AUID,");
            sql.addSql("   TDP_ADATE,");
            sql.addSql("   TDP_EUID,");
            sql.addSql("   TDP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getTdpPid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnTdispFromRs(rs);
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
     * <p>Delete CMN_TDISP
     * @param bean CMN_TDISP Model
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(CmnTdispModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getTdpPid());

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
     * <br>[機  能] 指定されたユーザのトップ表示設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
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
     * <br>[機  能] 指定されたプラグインのトップ表示設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(String userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(userSid);

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
     * <br>[機  能] 全てのトップ表示設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたプラグインIDをユーザ設定から削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param pluginId プラグインID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteUserConf(String pluginId) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDISP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID<>?");
            sql.addSql(" and ");
            sql.addSql("   TDP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            sql.addStrValue(pluginId);

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
     * <p>Create CMN_TDISP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnTdispModel
     * @throws SQLException SQL実行例外
     */
    private CmnTdispModel __getCmnTdispFromRs(ResultSet rs) throws SQLException {
        CmnTdispModel bean = new CmnTdispModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTdpPid(rs.getString("TDP_PID"));
        bean.setTdpOrder(rs.getInt("TDP_ORDER"));
        bean.setTdpAuid(rs.getInt("TDP_AUID"));
        bean.setTdpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TDP_ADATE")));
        bean.setTdpEuid(rs.getInt("TDP_EUID"));
        bean.setTdpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TDP_EDATE")));
        return bean;
    }
}
