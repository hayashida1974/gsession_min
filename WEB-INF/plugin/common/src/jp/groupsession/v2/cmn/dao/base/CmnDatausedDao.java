package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnDatausedModel;

/**
 * <p>CMN_DATAUSED Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnDatausedDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnDatausedDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnDatausedDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnDatausedDao(Connection con) {
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
            sql.addSql("drop table CMN_DATAUSED");

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
            sql.addSql(" create table CMN_DATAUSED (");
            sql.addSql("   CDU_PLUGIN varchar(20) not null,");
            sql.addSql("   CDU_SIZE bigint not null,");
            sql.addSql("   primary key (CDU_PLUGIN)");
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
     * <p>Insert CMN_DATAUSED Data Bindding JavaBean
     * @param bean CMN_DATAUSED Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnDatausedModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_DATAUSED(");
            sql.addSql("   CDU_PLUGIN,");
            sql.addSql("   CDU_SIZE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCduPlugin());
            sql.addLongValue(bean.getCduSize());
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
     * <p>Update CMN_DATAUSED Data Bindding JavaBean
     * @param bean CMN_DATAUSED Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnDatausedModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_DATAUSED");
            sql.addSql(" set ");
            sql.addSql("   CDU_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   CDU_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCduSize());
            //where
            sql.addStrValue(bean.getCduPlugin());

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
     * <p>Select CMN_DATAUSED All Data
     * @return List in CMN_DATAUSEDModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnDatausedModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnDatausedModel> ret = new ArrayList<CmnDatausedModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CDU_PLUGIN,");
            sql.addSql("   CDU_SIZE");
            sql.addSql(" from ");
            sql.addSql("   CMN_DATAUSED");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnDatausedFromRs(rs));
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
     * <br>[機  能] 使用データサイズ情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 使用データサイズの降順に並び替えを行う
     * @return List in CMN_DATAUSEDModel
     * @throws SQLException SQL実行例外
     * @throws SQLException
     */
    public List<CmnDatausedModel> getDatausedList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnDatausedModel> ret = new ArrayList<CmnDatausedModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CDU_PLUGIN,");
            sql.addSql("   CDU_SIZE");
            sql.addSql(" from ");
            sql.addSql("   CMN_DATAUSED");
            sql.addSql(" order by ");
            sql.addSql("   CDU_SIZE desc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnDatausedFromRs(rs));
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
     * <p>Select CMN_DATAUSED
     * @param cduPlugin CDU_PLUGIN
     * @return CMN_DATAUSEDModel
     * @throws SQLException SQL実行例外
     */
    public CmnDatausedModel select(String cduPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnDatausedModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CDU_PLUGIN,");
            sql.addSql("   CDU_SIZE");
            sql.addSql(" from");
            sql.addSql("   CMN_DATAUSED");
            sql.addSql(" where ");
            sql.addSql("   CDU_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cduPlugin);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnDatausedFromRs(rs);
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
     * <p>Delete CMN_DATAUSED
     * @param cduPlugin CDU_PLUGIN
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String cduPlugin) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_DATAUSED");
            sql.addSql(" where ");
            sql.addSql("   CDU_PLUGIN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cduPlugin);

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
     * <p>Create CMN_DATAUSED Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnDatausedModel
     * @throws SQLException SQL実行例外
     */
    private CmnDatausedModel __getCmnDatausedFromRs(ResultSet rs) throws SQLException {
        CmnDatausedModel bean = new CmnDatausedModel();
        bean.setCduPlugin(rs.getString("CDU_PLUGIN"));
        bean.setCduSize(rs.getLong("CDU_SIZE"));
        return bean;
    }
}
