package jp.groupsession.v2.bbs.bbs180;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 掲示板 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs180Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs180Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Bbs180Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Bbs180Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間の掲示板閲覧の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Bbs180DspModel> getViewLogCountData(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Bbs180DspModel> ret = new HashMap<String, Bbs180DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            sql.addSql("       cast(BVL_VIEW_DATE as date) as BVDATE, ");
            sql.addSql("       count(*) as BVCNT ");
            sql.addSql("     from ");
            sql.addSql("       BBS_VIEW_LOG_COUNT ");
            sql.addSql("     where ");
            sql.addSql("       BVL_VIEW_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       BVL_VIEW_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       BVDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bbs180DspModel model = new Bbs180DspModel();
                String strDate = rs.getString("BVDATE");
                model.setDate(strDate);
                model.setVbbsNum(rs.getInt("BVCNT"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間の掲示板投稿数の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Bbs180DspModel> getWriteLogCountData(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Bbs180DspModel> ret = new HashMap<String, Bbs180DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            sql.addSql("       cast(BWL_WRT_DATE as date) as BWDATE, ");
            sql.addSql("       count(*) as BWCNT ");
            sql.addSql("     from ");
            sql.addSql("       BBS_WRITE_LOG_COUNT ");
            sql.addSql("     where ");
            sql.addSql("       BWL_WRT_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       BWL_WRT_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       BWDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bbs180DspModel model = new Bbs180DspModel();
                String strDate = rs.getString("BWDATE");
                model.setDate(strDate);
                model.setWbbsNum(rs.getInt("BWCNT"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間の掲示板閲覧集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalViewLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   BBS_VIEW_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   BVL_VIEW_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   BVL_VIEW_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
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
     * <br>[機  能] 指定された期間の掲示板投稿集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalWriteLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   BBS_WRITE_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   BWL_WRT_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   BWL_WRT_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
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
     * <br>[機  能] 掲示板閲覧集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getViewLogMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BVL_VIEW_DATE) as MAX_DATE, ");
            sql.addSql("   min(BVL_VIEW_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   BBS_VIEW_LOG_COUNT ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] 掲示板投稿集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getWriteLogMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BWL_WRT_DATE) as MAX_DATE, ");
            sql.addSql("   min(BWL_WRT_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   BBS_WRITE_LOG_COUNT ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] 指定された期間の掲示板閲覧の集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Bbs180DspModel> getViewLogCountDataSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Bbs180DspModel> ret = new HashMap<String, Bbs180DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("       BLS_MONTH as BVDATE, ");
            } else {
                sql.addSql("       BLS_DATE as BVDATE, ");
            }
            sql.addSql("       sum(BLS_CNT) as BVSUM ");
            sql.addSql("     from ");
            sql.addSql("       BBS_LOG_COUNT_SUM ");
            sql.addSql("     where ");
            sql.addSql("       BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_VIEW);
            sql.addSql("     and ");
            sql.addSql("       BLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       BLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       BVDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bbs180DspModel model = new Bbs180DspModel();
                String strDate = rs.getString("BVDATE");
                model.setDate(strDate);
                model.setVbbsNum(rs.getInt("BVSUM"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間の掲示板投稿数の集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Bbs180DspModel> getWriteLogCountDataSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map <String, Bbs180DspModel> ret = new HashMap<String, Bbs180DspModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("     select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("       BLS_MONTH as BWDATE, ");
            } else {
                sql.addSql("       BLS_DATE as BWDATE, ");
            }
            sql.addSql("       sum(BLS_CNT) as BWSUM ");
            sql.addSql("     from ");
            sql.addSql("       BBS_LOG_COUNT_SUM ");
            sql.addSql("     where ");
            sql.addSql("       BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_WRITE);
            sql.addSql("     and ");
            sql.addSql("       BLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql("     and ");
            sql.addSql("       BLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql("     group by ");
            sql.addSql("       BWDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bbs180DspModel model = new Bbs180DspModel();
                String strDate = rs.getString("BWDATE");
                model.setDate(strDate);
                model.setWbbsNum(rs.getInt("BWSUM"));
                ret.put(strDate, model);
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
     * <br>[機  能] 指定された期間の掲示板閲覧集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalViewLogSum(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   sum(BLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   BBS_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_VIEW);
            sql.addSql(" and ");
            sql.addSql("   BLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   BLS_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("SUM");
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
     * <br>[機  能] 指定された期間の掲示板投稿集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalWriteLogSum(
            UDate frDate,
            UDate toDate)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   sum(BLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   BBS_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_WRITE);
            sql.addSql(" and ");
            sql.addSql("   BLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   BLS_DATE <= ? ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("SUM");
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
     * <br>[機  能] 掲示板閲覧集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getViewLogSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(BLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   BBS_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_VIEW);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
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
     * <br>[機  能] 掲示板投稿集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getWriteLogSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(BLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   BBS_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   BLS_KBN = ? ");
            sql.addIntValue(GSConstBulletin.BLS_KBN_WRITE);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new UDate[] {
                        UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE")),
                        UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE"))};
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

}
