package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;

/**
 * <p>RNG_TEMPLATE_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplateCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateCategoryDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RNG_TEMPLATE_CATEGORY Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_CATEGORY(");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTC_SORT,");
            sql.addSql("   RTC_NAME,");
            sql.addSql("   RTC_USE_LIMIT,");
            sql.addSql("   RTC_LIMIT_TYPE,");
            sql.addSql("   RTC_AUID,");
            sql.addSql("   RTC_ADATE,");
            sql.addSql("   RTC_EUID,");
            sql.addSql("   RTC_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtcSid());
            sql.addIntValue(bean.getRtcType());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRtcSort());
            sql.addStrValue(bean.getRtcName());
            sql.addIntValue(bean.getRtcUseLimit());
            sql.addIntValue(bean.getRtcLimitType());
            sql.addIntValue(bean.getRtcAuid());
            sql.addDateValue(bean.getRtcAdate());
            sql.addIntValue(bean.getRtcEuid());
            sql.addDateValue(bean.getRtcEdate());
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
     * <p>Update RNG_TEMPLATE_CATEGORY Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_CATEGORY Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplateCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   RTC_NAME=?,");
            sql.addSql("   RTC_USE_LIMIT=?,");
            sql.addSql("   RTC_LIMIT_TYPE=?,");
            sql.addSql("   RTC_EUID=?,");
            sql.addSql("   RTC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRtcName());
            sql.addIntValue(bean.getRtcUseLimit());
            sql.addIntValue(bean.getRtcLimitType());
            sql.addIntValue(bean.getRtcEuid());
            sql.addDateValue(bean.getRtcEdate());
            //where
            sql.addIntValue(bean.getRtcSid());

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
     * <p>Select RNG_TEMPLATE_CATEGORY All Data
     * @return カテゴリ一覧
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngTemplateCategoryModel> ret = new ArrayList<RngTemplateCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTC_SORT,");
            sql.addSql("   RTC_NAME,");
            sql.addSql("   RTC_USE_LIMIT,");
            sql.addSql("   RTC_LIMIT_TYPE,");
            sql.addSql("   RTC_AUID,");
            sql.addSql("   RTC_ADATE,");
            sql.addSql("   RTC_EUID,");
            sql.addSql("   RTC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngChannelTemplateFromRs(rs));
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
     * <br>[機  能] 共有テンプレートカテゴリ1件を取得します
     * <br>[解  説]
     * <br>[備  考] RngTemplateCategoryModelが返ってきます
     * @param rtcSid カテゴリSID
     * @return RngTemplateCategoryModel カテゴリ
     * @throws SQLException SQL実行時例外
     */
    public RngTemplateCategoryModel select(int rtcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplateCategoryModel ret = new RngTemplateCategoryModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTC_SORT,");
            sql.addSql("   RTC_NAME,");
            sql.addSql("   RTC_USE_LIMIT,");
            sql.addSql("   RTC_LIMIT_TYPE,");
            sql.addSql("   RTC_AUID,");
            sql.addSql("   RTC_ADATE,");
            sql.addSql("   RTC_EUID,");
            sql.addSql("   RTC_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngChannelTemplateFromRs(rs);
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
     * <br>[機  能] 共有テンプレートカテゴリ一覧を取得します
     * <br>[解  説]
     * <br>[備  考] RngTemplateCategoryModelのArrayListで返ってきます
     * @return ArrayList カテゴリ一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RngTemplateCategoryModel> selectAdmin() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<RngTemplateCategoryModel> ret = new ArrayList<RngTemplateCategoryModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTC_SORT,");
            sql.addSql("   RTC_NAME,");
            sql.addSql("   RTC_USE_LIMIT,");
            sql.addSql("   RTC_LIMIT_TYPE,");
            sql.addSql("   RTC_AUID,");
            sql.addSql("   RTC_ADATE,");
            sql.addSql("   RTC_EUID,");
            sql.addSql("   RTC_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_TYPE=?");
            sql.addSql(" order by ");
            sql.addSql("   RTC_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.RNG_TEMPLATE_SHARE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngChannelTemplateFromRs(rs));
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
     * <br>[機  能] 個人テンプレートカテゴリ一覧を取得します
     * <br>[解  説]
     * <br>[備  考] StringのArrayListで返ってきます
     * @param sessionUserSid ユーザSID
     * @return ArrayList カテゴリ一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RngTemplateCategoryModel> selectUser(int sessionUserSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<RngTemplateCategoryModel> ret = new ArrayList<RngTemplateCategoryModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTC_SORT,");
            sql.addSql("   RTC_NAME,");
            sql.addSql("   RTC_USE_LIMIT,");
            sql.addSql("   RTC_LIMIT_TYPE,");
            sql.addSql("   RTC_AUID,");
            sql.addSql("   RTC_ADATE,");
            sql.addSql("   RTC_EUID,");
            sql.addSql("   RTC_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_TYPE=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   RTC_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.RNG_TEMPLATE_PRIVATE);
            sql.addIntValue(sessionUserSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngChannelTemplateFromRs(rs));
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
     * <br>[機  能] カテゴリ(共有)の並び順最大値を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return カテゴリ内ソート最大値
     * @throws SQLException SQL実行時例外
     */
    public int getMaxSortShare() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(max(RTC_SORT), 0) as MAX");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_TYPE=?");

            sql.addIntValue(RngConst.RNG_TEMPLATE_SHARE);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
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
     * <br>[機  能] 指定カテゴリの並び順を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param rtcSid カテゴリSID
     * @return カテゴリ内ソート最大値
     * @throws SQLException SQL実行時例外
     */
    public int getSort(int rtcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SORT");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            sql.addIntValue(rtcSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RTC_SORT");
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
     * <br>[機  能] 並び順を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param rtcSid カテゴリSID
     * @param rtcSort 並び順
     * @param userSid ユーザSID
     * @param now 現在日時
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(int rtcSid, int rtcSort, int userSid, UDate now)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   RTC_SORT=?,");
            sql.addSql("   RTC_EUID=?,");
            sql.addSql("   RTC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSort);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(rtcSid);

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
     * <br>[機  能] カテゴリ(個人)の並び順最大値を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUserSid ユーザSID
     * @return カテゴリ内ソート最大値
     * @throws SQLException SQL実行時例外
     */
    public int getMaxSortPrivate(int sessionUserSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(max(RTC_SORT), 0) as MAX");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_TYPE=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(RngConst.RNG_TEMPLATE_PRIVATE);
            sql.addIntValue(sessionUserSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
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
     * <p>Delete RNG_TEMPLATE_CATEGORY
     * @param rctSid テンプレートカテゴリSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rctSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rctSid);

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
     * <br>[機  能] 並び順とカテゴリSIDのMappingを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param tplMode テンプレートモード 1:共有 2:個人
     * @param userSid ユーザSID
     * @return 並び順と稟議テンプレートSIDのMapping
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, Integer> getTplSortMap(int tplMode, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> tplMap = new HashMap<Integer, Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_SORT");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where");
            //テンプレートモード：共有
            if (tplMode == RngConst.RNG_TEMPLATE_SHARE) {
                sql.addSql("   RTC_TYPE = ?");

                sql.addIntValue(RngConst.RNG_TEMPLATE_SHARE);
            //テンプレートモード：個人
            } else if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                sql.addSql("   RTC_TYPE = ?");
                sql.addSql(" and ");
                sql.addSql("   USR_SID = ?");

                sql.addIntValue(RngConst.RNG_TEMPLATE_PRIVATE);
                sql.addIntValue(userSid);
            }
            sql.addSql(" order by");
            sql.addSql("   RTC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tplMap.put(rs.getInt("RTC_SORT"),
                        rs.getInt("RTC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return tplMap;
    }

    /**
     * <br>[機  能] 並び順を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param tplMode テンプレートモード 1:共有 2:個人
     * @param userSid ユーザSID
     * @param now 現在日付
     * @param sort 並び順
     * @throws SQLException SQL実行時例外
     */
    public void updateSortAll(int tplMode, int userSid, UDate now, int sort)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTC_SORT");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_CATEGORY");
            sql.addSql(" where");
            //テンプレートモード：共有
            if (tplMode == RngConst.RNG_TEMPLATE_SHARE) {
                sql.addSql("   RTC_TYPE = ?");

                sql.addIntValue(RngConst.RNG_TEMPLATE_SHARE);
            //テンプレートモード：個人
            } else if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                sql.addSql("   RTC_TYPE = ?");
                sql.addSql(" and ");
                sql.addSql("   USR_SID = ?");

                sql.addIntValue(RngConst.RNG_TEMPLATE_PRIVATE);
                sql.addIntValue(userSid);
            }
            sql.addSql(" and ");
            sql.addSql("   RTC_SORT > ?");
            sql.addIntValue(sort);
            sql.addSql(" order by");
            sql.addSql("   RTC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int rtcSid = rs.getInt("RTC_SID");
                int rtcSort = rs.getInt("RTC_SORT") - 1;
                updateSort(rtcSid, rtcSort, userSid, now);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create RNG_TEMPLATE_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateCategoryModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateCategoryModel __getRngChannelTemplateFromRs(ResultSet rs)
    throws SQLException {
        RngTemplateCategoryModel bean = new RngTemplateCategoryModel();
        bean.setRtcSid(rs.getInt("RTC_SID"));
        bean.setRtcType(rs.getInt("RTC_TYPE"));
        bean.setRtcSort(rs.getInt("RTC_SORT"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRtcName(rs.getString("RTC_NAME"));
        bean.setRtcUseLimit(rs.getInt("RTC_USE_LIMIT"));
        bean.setRtcLimitType(rs.getInt("RTC_LIMIT_TYPE"));
        bean.setRtcAuid(rs.getInt("RTC_AUID"));
        bean.setRtcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTC_ADATE")));
        bean.setRtcEuid(rs.getInt("RTC_EUID"));
        bean.setRtcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTC_EDATE")));
        return bean;
    }
}
