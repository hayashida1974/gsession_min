package jp.groupsession.v2.convert.convert202.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.convert.convert202.model.ConvPositionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 役職マスタのDAOクラス(コンバート用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvPositionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvPositionDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvPositionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvPositionDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 役職名称から該当件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posName 役職名称
     * @return int 該当件数
     * @throws SQLException SQL実行例外
     */
    public int getPosCount(String posName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(POS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where");
            sql.addSql("   POS_NAME = ?");

            sql.addStrValue(posName);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
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
     * <br>[機  能] 役職情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_POSITION Data Bindding JavaBean
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insertPos(ConvPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_POSITION(");
            sql.addSql("   POS_SID,");
            sql.addSql("   POS_NAME,");
            sql.addSql("   POS_BIKO,");
            sql.addSql("   POS_SORT,");
            sql.addSql("   POS_AUID,");
            sql.addSql("   POS_ADATE,");
            sql.addSql("   POS_EUID,");
            sql.addSql("   POS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(bean.getPosSid());
            sql.addStrValue(bean.getPosName());
            sql.addStrValue(bean.getPosBiko());
            sql.addIntValue(bean.getPosSort());
            sql.addIntValue(bean.getPosAuid());
            sql.addDateValue(bean.getPosAdate());
            sql.addIntValue(bean.getPosEuid());
            sql.addDateValue(bean.getPosEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
