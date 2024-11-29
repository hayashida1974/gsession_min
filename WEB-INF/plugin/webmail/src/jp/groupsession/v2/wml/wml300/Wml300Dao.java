package jp.groupsession.v2.wml.wml300;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml300Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml300Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Wml300Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Wml300Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間の受信メールの集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 受信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Wml300DspModel> getJmailLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {
        Map <String, Wml300DspModel> ret = new HashMap<String, Wml300DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(WLC_DATE as date) as RDATE, ");
            sql.addSql("   count(WLC_CNT) as RMAIL ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   WLC_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_JMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   RDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml300DspModel model = new Wml300DspModel();
                String strDate = rs.getString("RDATE");
                model.setDate(strDate);
                model.setJmailNum(rs.getInt("RMAIL"));
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
     * <br>[機  能] 指定された期間の送信メールの集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 送信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Wml300DspModel> getSmailLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {
        Map <String, Wml300DspModel> ret = new HashMap<String, Wml300DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(WLC_DATE as date) as SDATE, ");
            sql.addSql("   count(WLC_CNT) as SMAIL ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   WLC_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml300DspModel model = new Wml300DspModel();
                String strDate = rs.getString("SDATE");
                model.setDate(strDate);
                model.setSmailNum(rs.getInt("SMAIL"));
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
     * <br>[機  能] 指定された期間の受信メールの集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJmailLog(
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
            sql.addSql("   WML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   WLC_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_JMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE <= ? ");

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
     * <br>[機  能] 指定された期間の受信メールの集計データの全件数を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalSmailLog(
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
            sql.addSql("   WML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   WLC_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   WLC_DATE <= ? ");

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
     * <br>[機  能] 集計データの最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。日別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLogCountMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WLC_DATE) as MAX_DATE, ");
            sql.addSql("   min(WLC_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT ");

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
     * <br>[機  能] 指定された期間の受信メールの集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 受信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Wml300DspModel> getJmailLogSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {
        Map <String, Wml300DspModel> ret = new HashMap<String, Wml300DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   WLS_MONTH as RDATE, ");
            } else {
                sql.addSql("   WLS_DATE as RDATE, ");
            }
            sql.addSql("   sum(WLS_CNT) as RMAIL ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   WLS_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_JMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   RDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml300DspModel model = new Wml300DspModel();
                String strDate = rs.getString("RDATE");
                model.setDate(strDate);
                model.setJmailNum(rs.getInt("RMAIL"));
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
     * <br>[機  能] 指定された期間の送信メールの集計情報を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param dateUnit 日付単位
     * @return 送信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Wml300DspModel> getSmailLogSum(
            UDate frDate,
            UDate toDate,
            int dateUnit)
                    throws SQLException {
        Map <String, Wml300DspModel> ret = new HashMap<String, Wml300DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   WLS_MONTH as SDATE, ");
            } else {
                sql.addSql("   WLS_DATE as SDATE, ");
            }
            sql.addSql("   sum(WLS_CNT) as SMAIL ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   WLS_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml300DspModel model = new Wml300DspModel();
                String strDate = rs.getString("SDATE");
                model.setDate(strDate);
                model.setSmailNum(rs.getInt("SMAIL"));
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
     * <br>[機  能] 指定された期間の受信メールの集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJmailLogSum(
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
            sql.addSql("   sum(WLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   WLS_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_JMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE <= ? ");

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
     * <br>[機  能] 指定された期間の受信メールの集計データの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalSmailLogSum(
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
            sql.addSql("   sum(WLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   WLS_KBN = ? ");
            sql.addIntValue(GSConstWebmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE <= ? ");

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
     * <br>[機  能] 集計データの最新日時と最古日時を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す。月別、週別用。
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLogCountSumMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(WLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM ");

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