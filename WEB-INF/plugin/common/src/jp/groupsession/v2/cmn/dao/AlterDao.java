package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機 能] DB設定の変更時に使用するDAOクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class AlterDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AlterDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public AlterDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public AlterDao(Connection con) {
        super(con);
    }

    /**
     * <p>ユーザ名変更
     * @param oldUser 変更前ユーザ
     * @param newUser 変更後ユーザ
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int changeUser(String oldUser, String newUser) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" ALTER USER ");
            sql.addSql(oldUser);
            sql.addSql(" RENAME TO ");
            sql.addSql(newUser);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>パスワード変更
     * @param user ユーザ
     * @param pswd パスワード
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int changePassword(String user, String pswd) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" ALTER USER ");
            sql.addSql(user);
            sql.addSql(" SET ");
            sql.addSql("   PASSWORD");
            sql.addSql("'" + pswd + "'");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

}
