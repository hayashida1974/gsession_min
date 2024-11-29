package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.wml.model.base.WmlDatausedSumModel;

/**
 * <p>WML_DATAUSED_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDatausedSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDatausedSumDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDatausedSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDatausedSumDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table WML_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table WML_DATAUSED_SUM (");
            sql.addSql("   SUM_TYPE integer,");
            sql.addSql("   WAC_DISCSIZE_SUM bigint not null,");
            sql.addSql("   WTP_DISCSIZE_SUM bigint not null,");
            sql.addSql("   WLG_DISCSIZE_SUM bigint not null");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert WML_DATAUSED_SUM Data Bindding JavaBean
     * @param bean WML_DATAUSED_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DATAUSED_SUM(");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   WAC_DISCSIZE_SUM,");
            sql.addSql("   WTP_DISCSIZE_SUM,");
            sql.addSql("   WLG_DISCSIZE_SUM");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getWacDiscsizeSum());
            sql.addLongValue(bean.getWtpDiscsizeSum());
            sql.addLongValue(bean.getWlgDiscsizeSum());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_DATAUSED_SUM Data Bindding JavaBean
     * @param bean WML_DATAUSED_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDatausedSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DATAUSED_SUM");
            sql.addSql(" set ");
            sql.addSql("   SUM_TYPE=?,");
            sql.addSql("   WAC_DISCSIZE_SUM=?,");
            sql.addSql("   WTP_DISCSIZE_SUM=?,");
            sql.addSql("   WLG_DISCSIZE_SUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSumType());
            sql.addLongValue(bean.getWacDiscsizeSum());
            sql.addLongValue(bean.getWtpDiscsizeSum());
            sql.addLongValue(bean.getWlgDiscsizeSum());

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
     * <p>Delete WML_DATAUSED_SUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select WML_DATAUSED_SUM All Data
     * @return List in WML_DATAUSED_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDatausedSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDatausedSumModel> ret = new ArrayList<WmlDatausedSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   WAC_DISCSIZE_SUM,");
            sql.addSql("   WTP_DISCSIZE_SUM,");
            sql.addSql("   WLG_DISCSIZE_SUM");
            sql.addSql(" from ");
            sql.addSql("   WML_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDatausedSumFromRs(rs));
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
     * <br>[機  能] 使用データサイズの「集計」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの「集計」
     * @throws SQLException
     */
    public WmlDatausedSumModel getTotalData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SUM_TYPE,");
            sql.addSql("   WAC_DISCSIZE_SUM,");
            sql.addSql("   WTP_DISCSIZE_SUM,");
            sql.addSql("   WLG_DISCSIZE_SUM");
            sql.addSql(" from ");
            sql.addSql("   WML_DATAUSED_SUM");
            sql.addSql(" where ");
            sql.addSql("   SUM_TYPE = ?");
            sql.addIntValue(GSConst.USEDDATA_SUMTYPE_TOTAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDatausedSumFromRs(rs);
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
     * <br>[機  能] 使用データサイズの集計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 使用データサイズの集計
     * @throws SQLException
     */
    public WmlDatausedSumModel getSumData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDatausedSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(WAC_DISCSIZE_SUM) as SUM_WAC_DISCSIZE_SUM,");
            sql.addSql("   sum(WTP_DISCSIZE_SUM) as SUM_WTP_DISCSIZE_SUM,");
            sql.addSql("   sum(WLG_DISCSIZE_SUM) as SUM_WLG_DISCSIZE_SUM");
            sql.addSql(" from ");
            sql.addSql("   WML_DATAUSED_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new WmlDatausedSumModel();
                ret.setWacDiscsizeSum(rs.getLong("SUM_WAC_DISCSIZE_SUM"));
                ret.setWtpDiscsizeSum(rs.getLong("SUM_WTP_DISCSIZE_SUM"));
                ret.setWlgDiscsizeSum(rs.getLong("SUM_WLG_DISCSIZE_SUM"));
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
     * <p>Create WML_DATAUSED_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDatausedSumModel
     * @throws SQLException SQL実行例外
     */
    private WmlDatausedSumModel __getWmlDatausedSumFromRs(ResultSet rs) throws SQLException {
        WmlDatausedSumModel bean = new WmlDatausedSumModel();
        bean.setSumType(rs.getInt("SUM_TYPE"));
        bean.setWacDiscsizeSum(rs.getLong("WAC_DISCSIZE_SUM"));
        bean.setWtpDiscsizeSum(rs.getLong("WTP_DISCSIZE_SUM"));
        bean.setWlgDiscsizeSum(rs.getLong("WLG_DISCSIZE_SUM"));
        return bean;
    }
}
