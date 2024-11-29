package jp.groupsession.v2.convert.convert497.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.7へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //プロジェクト管理 ディレクトリテーブル削除
        __removePrjDirectory();

        //アンケート　回答情報削除
        __deleteEnqAns();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] プロジェクト管理 ディレクトリテーブル削除
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __removePrjDirectory() throws SQLException {
        SqlBuffer sql = new SqlBuffer();
        sql.addSql("drop table if exists PRJ_DIRECTORY;");
        __exeQuery(sql);

    }

    /**
     * <br>[機  能] 回答対象者ではないアンケートの回答情報を削除
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQLException
     */
    private void __deleteEnqAns() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ENQ_ANS_MAIN.EMN_SID,");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" left join");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         ENQ_SUBJECT.EMN_SID,");
            sql.addSql("         ENQ_SUBJECT.USR_SID");
            sql.addSql("       from");
            sql.addSql("         ENQ_SUBJECT");
            sql.addSql("       where");
            sql.addSql("         USR_SID > -1");
            sql.addSql("     )");
            sql.addSql("     UNION");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         ENQ_SUBJECT.EMN_SID,");
            sql.addSql("        CMN_BELONGM.USR_SID");
            sql.addSql("       from");
            sql.addSql("         ENQ_SUBJECT,");
            sql.addSql("         CMN_BELONGM");
            sql.addSql("       where");
            sql.addSql("         ENQ_SUBJECT.GRP_SID >= 0");
            sql.addSql("       and");
            sql.addSql("         ENQ_SUBJECT.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("     )");
            sql.addSql("   )SUBJECT");
            sql.addSql(" on");
            sql.addSql("   ENQ_ANS_MAIN.EMN_SID = SUBJECT.EMN_SID");
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID = SUBJECT.USR_SID");
            sql.addSql(" where");
            sql.addSql("   SUBJECT.USR_SID is null");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sql = new SqlBuffer();
                sql.addSql(" delete from ENQ_ANS_MAIN ");
                sql.addSql(" where EMN_SID=? ");
                sql.addSql(" and USR_SID =?");
                sql.addIntValue(rs.getInt("EMN_SID"));
                sql.addIntValue(rs.getInt("USR_SID"));
                __exeQuery(sql);

                sql = new SqlBuffer();
                sql.addSql(" delete from ENQ_ANS_SUB ");
                sql.addSql(" where EMN_SID=? ");
                sql.addSql(" and USR_SID =?");
                sql.addIntValue(rs.getInt("EMN_SID"));
                sql.addIntValue(rs.getInt("USR_SID"));
                __exeQuery(sql);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定されたSQLを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @throws SQLException SQL実行例外
     */
    private void __exeQuery(SqlBuffer sql) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
