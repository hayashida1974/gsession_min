package jp.groupsession.v2.convert.convert202.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.convert.convert202.model.ConvContmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] コントロールマスタのDAOクラス(コンバート用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvContmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvContmDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvContmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvContmDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select CMN_CONTM All Data
     * @return ret CmnContmModel
     * @throws SQLException SQL実行例外
     */
    public ConvContmModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ConvContmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_MENU_STATIC");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnContmFromRs(rs);
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
     * <p>Insert CMN_CONTM Data Bindding JavaBean
     * @param bean CMN_CONTM Data Bindding JavaBean
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insert(ConvContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_CONTM(");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_MENU_STATIC");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCntPxyUse());
            sql.addStrValue(bean.getCntPxyUrl());
            sql.addIntValue(bean.getCntPxyPort());
            sql.addIntValue(bean.getCntMenuStatic());
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
     * <p>Create CMN_CONTM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnContmModel
     * @throws SQLException SQL実行例外
     */
    private ConvContmModel __getCmnContmFromRs(ResultSet rs) throws SQLException {
        ConvContmModel bean = new ConvContmModel();
        bean.setCntPxyUse(rs.getInt("CNT_PXY_USE"));
        bean.setCntPxyUrl(rs.getString("CNT_PXY_URL"));
        bean.setCntPxyPort(rs.getInt("CNT_PXY_PORT"));
        bean.setCntMenuStatic(rs.getInt("CNT_MENU_STATIC"));
        return bean;
    }

}
