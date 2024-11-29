package jp.groupsession.v2.cmn.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] vacuumを実行するDAOクラス
 * <br>[解  説]
 * <br>[備  考] PostgresはDBにゴミが残るので定期的にvacuumを実行しDBを最適化する
 *
 * @author JTS
 */
public class VacuumDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(VacuumDao.class);
    /**
     * <p>Default Constructor
     */
    public VacuumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public VacuumDao(Connection con) {
        super(con);
    }

    /**
     * <p>vacuumを実行する。
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public static synchronized void doVacuum(Connection con) throws SQLException {

        PreparedStatement pstmt = null;

        //AutoCommitはtrueでなければならない
        con.setAutoCommit(true);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" vacuum analyze");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        //AutoCommitのフラグを元に戻す
        con.setAutoCommit(false);
    }

}
