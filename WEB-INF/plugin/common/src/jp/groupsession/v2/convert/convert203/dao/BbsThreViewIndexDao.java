package jp.groupsession.v2.convert.convert203.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_THRE_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreViewIndexDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsThreViewIndexDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsThreViewIndexDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsThreViewIndexDao(Connection con) {
        super(con);
    }

    /**
     * <p>Create Index
     * @throws SQLException SQL実行例外
     */
    public void createIndex() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create unique index ");
            sql.addSql("   BBS_THRE_VIEW_INDEX");
            sql.addSql("   on ");
            sql.addSql("   BBS_THRE_VIEW(");
            sql.addSql("   USR_SID, BTI_SID");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

}
