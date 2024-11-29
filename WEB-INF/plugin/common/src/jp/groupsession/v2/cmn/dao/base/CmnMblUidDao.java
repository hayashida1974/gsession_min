package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnMblUidModel;

/**
 * <br>[機  能] 固体識別番号に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnMblUidDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMblUidDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMblUidDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定されたユーザの固体識別番号情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret ユーザ情報
     * @throws SQLException SQL実行例外
     */
    public CmnMblUidModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CmnMblUidModel ret = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CMU_UID_1,");
            sql.addSql("   CMU_UID_2,");
            sql.addSql("   CMU_UID_3,");
            sql.addSql("   CMU_AUID,");
            sql.addSql("   CMU_ADATE,");
            sql.addSql("   CMU_EUID,");
            sql.addSql("   CMU_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnMblUidFromRs(rs);
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
     * <p>Select CMN_MBL_UID All Data
     * @return List in CMN_MBL_UIDModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMblUidModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMblUidModel> ret = new ArrayList<CmnMblUidModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CMU_UID_1,");
            sql.addSql("   CMU_UID_2,");
            sql.addSql("   CMU_UID_3,");
            sql.addSql("   CMU_AUID,");
            sql.addSql("   CMU_ADATE,");
            sql.addSql("   CMU_EUID,");
            sql.addSql("   CMU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MBL_UID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMblUidFromRs(rs));
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
     * <br>[機  能] 固体識別番号を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 固体識別番号データ
     * @return cnt 更新件数
     * @throws SQLException SQL実行例外
     */
    public int insertUid(CmnMblUidModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql("   (");
            sql.addSql("     USR_SID,");
            sql.addSql("     CMU_UID_1,");
            sql.addSql("     CMU_UID_2,");
            sql.addSql("     CMU_UID_3,");
            sql.addSql("     CMU_AUID,");
            sql.addSql("     CMU_ADATE,");
            sql.addSql("     CMU_EUID,");
            sql.addSql("     CMU_EDATE");
            sql.addSql("   )");
            sql.addSql("   values");
            sql.addSql("   (");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCmuUid1());
            sql.addStrValue(bean.getCmuUid2());
            sql.addStrValue(bean.getCmuUid3());
            sql.addIntValue(bean.getCmuAuid());
            sql.addDateValue(bean.getCmuAdate());
            sql.addIntValue(bean.getCmuEuid());
            sql.addDateValue(bean.getCmuEdate());

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
     * <br>[機  能] 固体識別番号を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param uid 固体識別番号
     * @return cnt 更新件数
     * @throws SQLException SQL実行例外
     */
    public int insertUid(int usrSid, String uid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql("   (");
            sql.addSql("     USR_SID,");
            sql.addSql("     CMU_UID_1,");
            sql.addSql("     CMU_AUID,");
            sql.addSql("     CMU_ADATE,");
            sql.addSql("     CMU_EUID,");
            sql.addSql("     CMU_EDATE");
            sql.addSql("   )");
            sql.addSql("   values");
            sql.addSql("   (");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addStrValue(uid);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);

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
     * <br>[機  能] 固体識別番号を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 固体識別番号モデル
     * @return cnt 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUid(CmnMblUidModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql(" set ");
            sql.addSql("   CMU_UID_1 = ?,");
            sql.addSql("   CMU_UID_2 = ?,");
            sql.addSql("   CMU_UID_3 = ?,");
            sql.addSql("   CMU_EUID = ?,");
            sql.addSql("   CMU_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCmuUid1());
            sql.addStrValue(bean.getCmuUid2());
            sql.addStrValue(bean.getCmuUid3());
            sql.addIntValue(bean.getCmuEuid());
            sql.addDateValue(bean.getCmuEdate());
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
     * <br>[機  能] 固体識別番号を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param uid 固体識別番号
     * @return cnt 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUid(int usrSid, String uid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql(" set ");
            sql.addSql("   CMU_UID_1 = ?,");
            sql.addSql("   CMU_EUID = ?,");
            sql.addSql("   CMU_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(uid);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);
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
     * <br>[機  能] 指定されたユーザの固体識別番号を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return cnt 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteUid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MBL_UID");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

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
     * <p>Create CMN_MBL_UID Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMblUidModel
     * @throws SQLException SQL実行例外
     */
    private CmnMblUidModel __getCmnMblUidFromRs(ResultSet rs) throws SQLException {
        CmnMblUidModel bean = new CmnMblUidModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCmuUid1(rs.getString("CMU_UID_1"));
        bean.setCmuUid2(rs.getString("CMU_UID_2"));
        bean.setCmuUid3(rs.getString("CMU_UID_3"));
        bean.setCmuAuid(rs.getInt("CMU_AUID"));
        bean.setCmuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CMU_ADATE")));
        bean.setCmuEuid(rs.getInt("CMU_EUID"));
        bean.setCmuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CMU_EDATE")));
        return bean;
    }
}