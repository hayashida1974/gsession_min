package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLhisBatchConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ログイン履歴自動削除設定に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnLhisBatchConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLhisBatchConfDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLhisBatchConfDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ログイン履歴自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret ユーザ情報
     * @throws SQLException SQL実行例外
     */
    public CmnLhisBatchConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CmnLhisBatchConfModel ret = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CBC_ADL_KBN,");
            sql.addSql("   CBC_ADL_YEAR,");
            sql.addSql("   CBC_ADL_MONTH,");
            sql.addSql("   CBC_AUID,");
            sql.addSql("   CBC_ADATE,");
            sql.addSql("   CBC_EUID,");
            sql.addSql("   CBC_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_LHIS_BATCH_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSQLExceptionFromRs(rs);
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
     * <br>[機  能] ログイン履歴自動削除設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データ
     * @return count 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insert(CmnLhisBatchConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql("   CMN_LHIS_BATCH_CONF");
            sql.addSql("   (");
            sql.addSql("     CBC_ADL_KBN,");
            sql.addSql("     CBC_ADL_YEAR,");
            sql.addSql("     CBC_ADL_MONTH,");
            sql.addSql("     CBC_AUID,");
            sql.addSql("     CBC_ADATE,");
            sql.addSql("     CBC_EUID,");
            sql.addSql("     CBC_EDATE");
            sql.addSql("   )");
            sql.addSql("   values");
            sql.addSql("   (");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCbcAdlKbn());
            sql.addIntValue(bean.getCbcAdlYear());
            sql.addIntValue(bean.getCbcAdlMonth());
            sql.addIntValue(bean.getCbcAuid());
            sql.addDateValue(bean.getCbcAdate());
            sql.addIntValue(bean.getCbcEuid());
            sql.addDateValue(bean.getCbcEdate());

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
     * <br>[機  能] ログイン履歴自動削除設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新データ
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnLhisBatchConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LHIS_BATCH_CONF");
            sql.addSql(" set ");
            sql.addSql("   CBC_ADL_KBN = ?,");
            sql.addSql("   CBC_ADL_YEAR = ?,");
            sql.addSql("   CBC_ADL_MONTH = ?,");
            sql.addSql("   CBC_EUID = ?,");
            sql.addSql("   CBC_EDATE = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCbcAdlKbn());
            sql.addIntValue(bean.getCbcAdlYear());
            sql.addIntValue(bean.getCbcAdlMonth());
            sql.addIntValue(bean.getCbcEuid());
            sql.addDateValue(bean.getCbcEdate());

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
     * <p>Create CMN_LHIS_BATCH_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return bean Data Bindding Model
     * @throws SQLException SQL実行例外
     */
    private CmnLhisBatchConfModel __getSQLExceptionFromRs(ResultSet rs)
        throws SQLException {

        CmnLhisBatchConfModel bean = new CmnLhisBatchConfModel();
        bean.setCbcAdlKbn(rs.getInt("CBC_ADL_KBN"));
        bean.setCbcAdlYear(rs.getInt("CBC_ADL_YEAR"));
        bean.setCbcAdlMonth(rs.getInt("CBC_ADL_MONTH"));
        bean.setCbcAuid(rs.getInt("CBC_AUID"));
        bean.setCbcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CBC_ADATE")));
        bean.setCbcEuid(rs.getInt("CBC_EUID"));
        bean.setCbcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CBC_EDATE")));

        return bean;
    }
}