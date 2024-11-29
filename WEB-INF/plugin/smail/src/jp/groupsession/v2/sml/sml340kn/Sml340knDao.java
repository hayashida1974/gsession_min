package jp.groupsession.v2.sml.sml340kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml340knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml340knDao.class);
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml340knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能]フィルター条件を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sftSid フィルターSID
     * @return SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    public int deleteFilter(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);

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
     * @param sacSid アカウントSID
     * @param sftSid フィルターSID
     * @return SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    public int deleteFilterOrder(int sacSid, int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID!=?");
            sql.addSql(" and");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(sftSid);

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
