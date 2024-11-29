package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlUidlModel;

/**
 * <p>WML_UIDL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlUidlDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlUidlDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlUidlDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlUidlDao(Connection con) {
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
            sql.addSql("drop table WML_UIDL");

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
            sql.addSql(" create table WML_UIDL (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WUD_ACCOUNT varchar(500) not null,");
            sql.addSql("   WUD_UID varchar(1000) not null,");
            sql.addSql("   primary key (WAC_SID,WUD_ACCOUNT)");
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
     * <p>Insert WML_UIDL Data Bindding JavaBean
     * @param bean WML_UIDL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlUidlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_UIDL(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_ACCOUNT,");
            sql.addSql("   WUD_UID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWudAccount());
            sql.addStrValue(bean.getWudUid());
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
        }
    }

    /**
     * <p>Update WML_UIDL Data Bindding JavaBean
     * @param bean WML_UIDL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlUidlModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_UIDL");
            sql.addSql(" set ");
            sql.addSql("   WUD_UID=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_ACCOUNT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWudUid());
            //where
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWudAccount());

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
     * <br>[機  能] 指定したアカウントの受信済みメールUID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @return 受信済みメールUIDL一覧
     * @throws SQLException SQL実行例外
     */
    public List<WmlUidlModel> select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlUidlModel> ret = new ArrayList<WmlUidlModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_ACCOUNT,");
            sql.addSql("   WUD_UID");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" group by ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_ACCOUNT,");
            sql.addSql("   WUD_UID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            WmlUidlModel bean = null;
            while (rs.next()) {
                bean = new WmlUidlModel();
                bean.setWacSid(rs.getInt("WAC_SID"));
                bean.setWudAccount(rs.getString("WUD_ACCOUNT"));
                bean.setWudUid(rs.getString("WUD_UID"));
                ret.add(bean);
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
     * <br>[機  能] 指定したアカウントの受信済みメールUID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @param wudAccount WUD_ACCOUNT
     * @return 受信済みメールUIDL一覧
     * @throws SQLException SQL実行例外
     */
    public List<String> getUIDList(int wacSid, String wudAccount) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WUD_UID");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_ACCOUNT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addStrValue(wudAccount);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("WUD_UID"));
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
     * <br>[機  能] 指定したUIDが登録済みかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @param wudAccount WUD_ACCOUNT
     * @param uid UID
     * @return true:登録済み false:未登録
     * @throws SQLException SQL実行例外
     */
    public boolean existUID(int wacSid, String wudAccount, String uid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" select 1 from WML_UIDL");
            sql.addSql(" where WUD_UID=?");
            sql.addSql(" and WUD_ACCOUNT=?");
            sql.addSql(" and WAC_SID=?");
            sql.setPagingValue(0, 1);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.addStrValue(uid);
            sql.addStrValue(wudAccount);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定したUIDから未登録のものを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @param wudAccount WUD_ACCOUNT
     * @param uid UID
     * @return UID
     * @throws SQLException SQL実行例外
     */
    public List<String> getNotExistUIDList(int wacSid, String wudAccount, List<String> uid)
    throws SQLException {
        List<String> uidList = new ArrayList<String>();
        if (uid == null || uid.isEmpty()) {
            return uidList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" select");
            sql.addSql("   WUD_UID");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_ACCOUNT=?");
            sql.addIntValue(wacSid);
            sql.addStrValue(wudAccount);

            sql.addSql(" and");
            sql.addSql("   WUD_UID in (");

            for (int idx = 0; idx < uid.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addStrValue(uid.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                uid.remove(rs.getString("WUD_UID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }
        return uid;
    }

    /**
     * <br>[機  能] 指定したアカウントのUIDL件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @return UIDL件数
     * @throws SQLException SQL実行例外
     */
    public long getUidlCount(int wacSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long count = 0;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getLong("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したUIDから未登録のものを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WAC_SID
     * @param wudAccount WUD_ACCOUNT
     * @param list ArrayList in UID
     * @return list in UID
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, String> getNotExistUIDMap(
            int wacSid, String wudAccount, ArrayList<String> list)
    throws SQLException {
        HashMap<String, String> uidMap = new HashMap<String, String>();
        if (list == null || list.isEmpty()) {
            return uidMap;
        }

        for (int i = 0; i < list.size(); i++) {
            uidMap.put(list.get(i), list.get(i));
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" select");
            sql.addSql("   WUD_UID");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_ACCOUNT=?");
            sql.addIntValue(wacSid);
            sql.addStrValue(wudAccount);

            sql.addSql(" and");
            sql.addSql("   WUD_UID in (");

            for (int idx = 0; idx < list.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addStrValue(list.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                uidMap.remove(rs.getString("WUD_UID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }
        return uidMap;
    }

    /**
     * <p>Delete WML_UIDL
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
            sql.addSql(" delete from WML_UIDL");
            sql.addSql(" where");
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
     * <p>Delete WML_UIDL
     * @param wacSid WAC_SID
     * @param wudAccount WUD_ACCOUNT
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, String wudAccount) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_ACCOUNT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addStrValue(wudAccount);

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
     * <p>Delete WML_UIDL
     * @param wacSid WAC_SID
     * @param wudUid WUD_UID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteUid(int wacSid, String wudUid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_UIDL");
            sql.addSql(" where WAC_SID=?");
            sql.addSql(" and WUD_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addStrValue(wudUid);

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
     *
     * <br>[機  能] WML_UIDLテーブルから「WML_UIDL_TMPテーブルに存在しないUID」を削除する
     * <br>[解  説]
     * <br>[備  考] 負荷が高くなることを想定し、一度に削除する件数はアカウントごと5000件づつとする
     * @param wacSid アカウントSID
     * @param limitCnt 削除最大件数
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteIkkatu(int wacSid, int limitCnt) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql(" WML_UIDL ");
            sql.addSql("   where ");
            sql.addSql("     WAC_SID = ?");
            sql.addSql("   and ");
            sql.addSql("     WUD_UID in( ");
            sql.addSql("       select ");
            sql.addSql("         WUD_UID ");
            sql.addSql("       from ");
            sql.addSql("         WML_UIDL ");
            sql.addSql("       where ");
            sql.addSql("         WAC_SID = ?");
            sql.addSql("       and ");
            sql.addSql("         WUD_UID not in ( ");
            sql.addSql("           select ");
            sql.addSql("             WUD_UID ");
            sql.addSql("           from ");
            sql.addSql("             WML_UIDL_TEMP ");
            sql.addSql("           where ");
            sql.addSql("             WAC_SID=? ");
            sql.addSql("       ) ");
            sql.addSql("      limit ? ");
            sql.addSql("     ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);
            sql.addIntValue(limitCnt);

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
}
