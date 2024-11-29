package jp.groupsession.v2.sml.sml370;

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
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml370Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml370Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Sml370Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Sml370Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間の受信メールの集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param sysMailFlg システムメール区分
     * @return 受信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Sml370DspModel> getJmailLog(
            UDate frDate,
            UDate toDate,
            int sysMailFlg)
                    throws SQLException {
        Map <String, Sml370DspModel> ret = new HashMap<String, Sml370DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(SLC_DATE as date) as RDATE, ");
            sql.addSql("   count(SLC_CNT_TO) as RMAIL ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   SLC_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_JMAIL);

            //システムメールを除く場合
            if (sysMailFlg == 1) {
                sql.addSql(" and ");
                sql.addSql("   SLC_SYS_KBN = ? ");
                sql.addIntValue(GSConstSmail.LOG_COUNT_SYSKBN_NORMAL);
            }

            sql.addSql(" and ");
            sql.addSql("   SLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE <= ? ");
            sql.addDateValue(toDate);

            sql.addSql(" group by ");
            sql.addSql("   RDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Sml370DspModel model = new Sml370DspModel();
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
     * @param sysMailFlg システムメール区分
     * @return 送信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Sml370DspModel> getSmailLog(
            UDate frDate,
            UDate toDate,
            int sysMailFlg)
                    throws SQLException {
        Map <String, Sml370DspModel> ret = new HashMap<String, Sml370DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   cast(SLC_DATE as date) as SDATE, ");
            sql.addSql("   count(SLC_CNT_TO) as SMAIL ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   SLC_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Sml370DspModel model = new Sml370DspModel();
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
     * @param sysMailFlg システムメール区分
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJmailLog(
            UDate frDate,
            UDate toDate,
            int sysMailFlg)
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
            sql.addSql("   SML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   SLC_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_JMAIL);
            //システムメールを除く場合
            if (sysMailFlg == 1) {
                sql.addSql("     and ");
                sql.addSql("       SLC_SYS_KBN = ? ");
                sql.addIntValue(GSConstSmail.LOG_COUNT_SYSKBN_NORMAL);
            }
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE <= ? ");

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
            sql.addSql("   SML_LOG_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   SLC_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   SLC_DATE <= ? ");

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
            sql.addSql("   max(SLC_DATE) as MAX_DATE, ");
            sql.addSql("   min(SLC_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT ");

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
     * @param sysMailFlg システムメール区分
     * @param dateUnit 日付単位
     * @return 受信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Sml370DspModel> getJmailLogSum(
            UDate frDate,
            UDate toDate,
            int sysMailFlg,
            int dateUnit)
                    throws SQLException {
        Map <String, Sml370DspModel> ret = new HashMap<String, Sml370DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   SLS_MONTH as RDATE, ");
            } else {
                sql.addSql("   SLS_DATE as RDATE, ");
            }
            sql.addSql("   sum(SLS_CNT) as RMAIL ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   SLS_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_JMAIL);

            //システムメールを除く場合
            if (sysMailFlg == 1) {
                sql.addSql(" and ");
                sql.addSql("   SLS_SYS_KBN = ? ");
                sql.addIntValue(GSConstSmail.LOG_COUNT_SYSKBN_NORMAL);
            }

            sql.addSql(" and ");
            sql.addSql("   SLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE <= ? ");
            sql.addDateValue(toDate);

            sql.addSql(" group by ");
            sql.addSql("   RDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Sml370DspModel model = new Sml370DspModel();
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
     * @param sysMailFlg システムメール区分
     * @param dateUnit 日付単位
     * @return 送信メール集計情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Sml370DspModel> getSmailLogSum(
            UDate frDate,
            UDate toDate,
            int sysMailFlg,
            int dateUnit)
                    throws SQLException {
        Map <String, Sml370DspModel> ret = new HashMap<String, Sml370DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (dateUnit == GSConstMain.STATS_DATE_UNIT_MONTH) {
                sql.addSql("   SLS_MONTH as SDATE, ");
            } else {
                sql.addSql("   SLS_DATE as SDATE, ");
            }
            sql.addSql("   sum(SLS_CNT) as SMAIL ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   SLS_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE >= ? ");
            sql.addDateValue(frDate);
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE <= ? ");
            sql.addDateValue(toDate);
            sql.addSql(" group by ");
            sql.addSql("   SDATE ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Sml370DspModel model = new Sml370DspModel();
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
     * <br>[機  能] 指定された期間の受信メールの全件数を集計結果から取得する
     * <br>[解  説]
     * <br>[備  考] 月別、週別用
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @param sysMailFlg システムメール区分
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getTotalJmailLogSum(
            UDate frDate,
            UDate toDate,
            int sysMailFlg)
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
            sql.addSql("   sum(SLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   SLS_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_JMAIL);
            //システムメールを除く場合
            if (sysMailFlg == 1) {
                sql.addSql("     and ");
                sql.addSql("       SLS_SYS_KBN = ? ");
                sql.addIntValue(GSConstSmail.LOG_COUNT_SYSKBN_NORMAL);
            }
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE <= ? ");

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
     * <br>[機  能] 指定された期間の送信メールの全件数を集計結果から取得する
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
            sql.addSql("   sum(SLS_CNT) as SUM ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM ");
            sql.addSql(" where ");
            sql.addSql("   SLS_KBN = ? ");
            sql.addIntValue(GSConstSmail.LOG_COUNT_KBN_SMAIL);
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE <= ? ");

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
            sql.addSql("   max(SLS_DATE) as MAX_DATE, ");
            sql.addSql("   min(SLS_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM ");

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