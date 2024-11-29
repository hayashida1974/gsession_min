package jp.groupsession.v2.wml.wml230kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ登録確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml230knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml230knDao.class);
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml230knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能]フィルター条件を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wftSid フィルターSID
     * @return WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    public int deleteFilter(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);

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
     * <br>[機  能]フィルター適用順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wftSid フィルターSID
     * @return WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    public int deleteFilterOrder(int wacSid, int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID!=?");
            sql.addSql(" and");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wftSid);

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
