package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlAccountSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountSortDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountSortDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_SORT");

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
            sql.addSql(" create table WML_ACCOUNT_SORT (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   WAS_SORT Date not null,");
            sql.addSql("   primary key (WAC_SID,USR_SID)");
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
     * <p>Insert WML_ACCOUNT_SORT Data Bindding JavaBean
     * @param bean WML_ACCOUNT_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_SORT(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getWasSort());
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
     * <p>Update WML_ACCOUNT_SORT Data Bindding JavaBean
     * @param bean WML_ACCOUNT_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql(" set ");
            sql.addSql("   WAS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWasSort());
            //where
            sql.addIntValue(bean.getWacSid());
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
     * <p>Select WML_ACCOUNT_SORT All Data
     * @return List in WML_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountSortModel> ret = new ArrayList<WmlAccountSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAS_SORT");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountSortFromRs(rs));
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
     * <p>Select WML_ACCOUNT_SORT
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @return WML_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountSortModel select(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAS_SORT");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountSortFromRs(rs);
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
     * <p>Select WML_ACCOUNT_SORT
     * @param usrSid USR_SID
     * @param sidList WAC_SID
     * @return WML_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountSortModel> select(int usrSid, List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlAccountSortModel> sortList = new ArrayList<WmlAccountSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAS_SORT");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            if (sidList != null && !sidList.isEmpty()) {
                sql.addSql(" and ");
                sql.addSql("   WAC_SID in (");
                for (int i = 0; i < sidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(",");
                    }
                    sql.addSql(String.valueOf(sidList.get(i)));
                }
                sql.addSql(") ");
            }


            sql.addSql(" order by WAS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sortList.add(__getWmlAccountSortFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sortList;
    }

    /**
     * <p>レコード件数を取得する
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @return WML_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public int count(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

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
     * <p>Delete WML_ACCOUNT_SORT
     * @param wacSid WAC_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
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
     * <p>Delete WML_ACCOUNT_SORT
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SORT");
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
     * <br>[機  能] アカウントの並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sessionSid セッションSID
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSort(int accountSid, int sessionSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(WML_ACCOUNT_SORT.WAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_USRM");
            sql.addSql("    left join ");
            sql.addSql("      WML_ACCOUNT_SORT ");
            sql.addSql("    on ");
            sql.addSql("      CMN_USRM.USR_SID = WML_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = ? ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            sql.addIntValue(sessionSid);

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
     * <br>[機  能] アカウント(グループ)の並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param wml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSortGp(int accountSid,
                                   String[] wml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(WML_ACCOUNT_SORT.WAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_BELONGM,");
            sql.addSql("    CMN_USRM");
            sql.addSql("  left join ");
            sql.addSql("    WML_ACCOUNT_SORT ");
            sql.addSql("  on ");
            sql.addSql("    CMN_USRM.USR_SID = WML_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = CMN_BELONGM.USR_SID ");
            sql.addSql("  and ");
            sql.addSql("    CMN_BELONGM.GRP_SID in ( ");
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                if (wml040userKbnGroup.length == 1 || (i == wml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" ) ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                sql.addIntValue(Integer.parseInt(wml040userKbnGroup[i]));
            }

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
     * <br>[機  能] アカウント(ユーザ)の並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param wml040userKbnUser ユーザ
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSortUsr(int accountSid,
                                   String[] wml040userKbnUser) throws SQLException {

        if (wml040userKbnUser == null || wml040userKbnUser.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("   ?, ");
            sql.addSql("   CMN_USRM.USR_SID, ");
            sql.addSql("   max(COALESCE(WML_ACCOUNT_SORT.WAS_SORT, 0)) + 1 ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql("   left join ");
            sql.addSql("    WML_ACCOUNT_SORT ");
            sql.addSql("   on ");
            sql.addSql("     CMN_USRM.USR_SID = WML_ACCOUNT_SORT.USR_SID ");
            sql.addSql("   where ");
            sql.addSql("     CMN_USRM.USR_SID in ( ");
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                if (wml040userKbnUser.length == 1 || (i == wml040userKbnUser.length - 1)) {
                    sql.addSql("     ?  ");
                } else {
                    sql.addSql("     ?,  ");
                }

            }
            sql.addSql("     ) ");
            sql.addSql("   and ");
            sql.addSql("     CMN_USRM.USR_SID not in ( ");
            sql.addSql("       select USR_SID from WML_ACCOUNT_SORT ");
            sql.addSql("       where WAC_SID = ? ");
            sql.addSql("     ) ");

            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            sql.addIntValue(accountSid);
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                sql.addIntValue(Integer.parseInt(wml040userKbnUser[i]));
            }
            sql.addIntValue(accountSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] アカウント(グループ)の並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param wml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void delAccountSortGp(int accountSid,
                                   String[] wml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_ACCOUNT_SORT ");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            sql.addSql("   select  ");
            sql.addSql("     USR_SID  ");
            sql.addSql("   from ");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   where ");
            sql.addSql("     GRP_SID in ( ");
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                if (wml040userKbnGroup.length == 1 || (i == wml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" )) ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                sql.addIntValue(Integer.parseInt(wml040userKbnGroup[i]));
            }

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
     * <br>[機  能] アカウント(ユーザ)の並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param wml040userKbnUser ユーザ
     * @throws SQLException SQL実行時例外
     */
    public void delAccountSortUsr(int accountSid,
                                   String[] wml040userKbnUser) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_ACCOUNT_SORT ");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                if (wml040userKbnUser.length == 1 || (i == wml040userKbnUser.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }

            }
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                sql.addIntValue(NullDefault.getInt(wml040userKbnUser[i], -1));
            }

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
     * <br>[機  能] 削除された代理人のアカウント並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param proxyUser 代理人ユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void delAccountSortProxyUser(int accountSid, String[] proxyUser)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_ACCOUNT_SORT ");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");
            sql.addIntValue(accountSid);

            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            sql.addSql("     select CMN_BELONGM.USR_SID from CMN_BELONGM, WML_ACCOUNT_USER");
            sql.addSql("     where WML_ACCOUNT_USER.WAC_SID = ? ");
            sql.addSql("     and coalesce(WML_ACCOUNT_USER.GRP_SID, 0) > 0");
            sql.addSql("     and CMN_BELONGM.GRP_SID = WML_ACCOUNT_USER.GRP_SID");
            sql.addSql("   ) ");
            sql.addIntValue(accountSid);

            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            sql.addSql("     select USR_SID from WML_ACCOUNT_USER");
            sql.addSql("     where WML_ACCOUNT_USER.WAC_SID = ? ");
            sql.addSql("     and coalesce(WML_ACCOUNT_USER.USR_SID, 0) > 0");
            sql.addSql("   ) ");
            sql.addIntValue(accountSid);

            if (proxyUser != null && proxyUser.length > 0) {
                sql.addSql(" and ");
                sql.addSql("   USR_SID not in ( ");
                for (int idx = 0; idx < proxyUser.length; idx++) {
                    if (idx > 0) {
                        sql.addSql("     ,? ");
                    } else {
                        sql.addSql("     ?  ");
                    }
                    sql.addIntValue(Integer.parseInt(proxyUser[idx]));
                }
                sql.addSql("   ) ");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] アカウントの並び順の新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param wml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountSort(int accountSid,
                                    String[] wml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(WML_ACCOUNT_SORT.WAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_BELONGM,");
            sql.addSql("    CMN_USRM");
            sql.addSql("    left join ");
            sql.addSql("      WML_ACCOUNT_SORT ");
            sql.addSql("    on ");
            sql.addSql("      CMN_USRM.USR_SID = WML_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = CMN_BELONGM.USR_SID ");
            sql.addSql("  and ");
            sql.addSql("    CMN_BELONGM.GRP_SID in ( ");
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                if (wml040userKbnGroup.length == 1 || (i == wml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" ) ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in ( ");
            sql.addSql("     select ");
            sql.addSql("       USR_SID ");
            sql.addSql("     from ");
            sql.addSql("       WML_ACCOUNT_SORT ");
            sql.addSql("     where ");
            sql.addSql("       WAC_SID = ? ");
            sql.addSql("   ) ");
            sql.addSql(" group by ");
            sql.addSql("   CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < wml040userKbnGroup.length; i++) {
                sql.addIntValue(NullDefault.getInt(wml040userKbnGroup[i], -1));
            }
            sql.addIntValue(accountSid);

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
     * <br>[機  能] アカウント(ユーザ)の新規登録の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param acutSaiSid 採番アカウントSID
     * @param wml040userKbnUser ユーザ
     * @param accountSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountSortUsr(int acutSaiSid,
                                   String[] wml040userKbnUser,
                                   int accountSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(WML_ACCOUNT_SORT.WAS_SORT, 0)) + 1 ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql("   left join ");
            sql.addSql("     WML_ACCOUNT_SORT ");
            sql.addSql("   on ");
            sql.addSql("     CMN_USRM.USR_SID = WML_ACCOUNT_SORT.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID in ( ");
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                if (wml040userKbnUser.length == 1 || (i == wml040userKbnUser.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }

            }
            sql.addSql("   ) ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in ( ");
            sql.addSql("     select ");
            sql.addSql("       USR_SID ");
            sql.addSql("     from ");
            sql.addSql("       WML_ACCOUNT_SORT ");
            sql.addSql("     where ");
            sql.addSql("       WAC_SID = ? ");
            sql.addSql("   ) ");
            sql.addSql(" group by ");
            sql.addSql("   CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(acutSaiSid);
            for (int i = 0; i < wml040userKbnUser.length; i++) {
                sql.addIntValue(NullDefault.getInt(wml040userKbnUser[i], -1));
            }
            sql.addIntValue(accountSid);
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
     * <p>Create WML_ACCOUNT_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountSortModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountSortModel __getWmlAccountSortFromRs(ResultSet rs) throws SQLException {
        WmlAccountSortModel bean = new WmlAccountSortModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWasSort(rs.getInt("WAS_SORT"));
        return bean;
    }
}
