package jp.groupsession.v2.man.man390;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 統計情報画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man390Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man390Dao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Man390Dao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public Man390Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された期間の集計情報を取得する
     * <br>[解  説]
     * <br>[備  考] 日単位で取得する。
     * @param frDate 集計開始日付
     * @param toDate 集計終了日付
     * @return ログインユーザ数情報
     * @throws SQLException SQL実行例外
     */
    public Map <String, Man390DspModel> getLoginLog(
            UDate frDate,
            UDate toDate)
                    throws SQLException {
        Map <String, Man390DspModel> ret = new HashMap<String, Man390DspModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUC_DATE as LOGIN_DATE,");
            sql.addSql("   CUC_USER as LOGIN_USER_COUNT,");
            sql.addSql("   CUC_LOGIN as LOGIN_COUNT,");
            sql.addSql("   CUC_CNT as ALL_USER_COUNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT ");
            sql.addSql(" where ");
            sql.addSql("   CUC_DATE >= ? ");
            sql.addSql(" and ");
            sql.addSql("   CUC_DATE <= ? ");
            sql.addSql(" order by ");
            sql.addSql("   CUC_DATE asc ");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Man390DspModel model = new Man390DspModel();
                String strDate = rs.getString("LOGIN_DATE");
                model.setDate(strDate);
                model.setLoginUserNum(rs.getLong("LOGIN_USER_COUNT"));
                model.setLoginNum(rs.getLong("LOGIN_COUNT"));
                model.setRegUserNum(rs.getLong("ALL_USER_COUNT"));
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
     * <br>[機  能] ログイン履歴の最新日時と最古日時を取得する
     * <br>[解  説]
     * <br>[備  考] 最古日時、最新日時の配列を返す
     * @return 最古日時、最新日時の配列
     * @throws SQLException SQL実行例外
     */
    public UDate[] getLoginMaxMinDate() throws SQLException {
        UDate[] ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(CUC_DATE) as MAX_DATE, ");
            sql.addSql("   min(CUC_DATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT ");

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
