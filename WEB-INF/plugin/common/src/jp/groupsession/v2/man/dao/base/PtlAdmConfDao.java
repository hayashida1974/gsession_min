package jp.groupsession.v2.man.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PTL_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlAdmConfDao(Connection con) {
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
            sql.addSql("drop table PTL_ADM_CONF");

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
            sql.addSql(" create table PTL_ADM_CONF (");
            sql.addSql("   PAC_PTL_EDITKBN time not null,");
            sql.addSql("   PAC_DEF_KBN time not null,");
            sql.addSql("   PAC_DEF_TYPE time not null,");
            sql.addSql("   primary key (PAC_PTL_EDITKBN)");
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
     * <p>Insert PTL_ADM_CONF Data Bindding JavaBean
     * @param bean PTL_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_ADM_CONF(");
            sql.addSql("   PAC_PTL_EDITKBN,");
            sql.addSql("   PAC_DEF_KBN,");
            sql.addSql("   PAC_DEF_TYPE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacPtlEditkbn());
            sql.addIntValue(bean.getPacDefKbn());
            sql.addIntValue(bean.getPacDefType());
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
     * <p>Update PTL_ADM_CONF Data Bindding JavaBean
     * @param bean PTL_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   PAC_DEF_KBN=?,");
            sql.addSql("   PAC_DEF_TYPE=?");
            sql.addSql(" where ");
            sql.addSql("   PAC_PTL_EDITKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacDefKbn());
            sql.addIntValue(bean.getPacDefType());
            //where
            sql.addIntValue(bean.getPacPtlEditkbn());

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
     * <p>Update PTL_ADM_CONF Data Bindding JavaBean
     * @param bean PTL_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateInitConf(PtlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   PAC_DEF_KBN=?,");
            sql.addSql("   PAC_DEF_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacDefKbn());
            sql.addIntValue(bean.getPacDefType());

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
     * <p>Update PTL_ADM_CONF Data Bindding JavaBean
     * @param bean PTL_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateEditKbn(PtlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   PAC_PTL_EDITKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacPtlEditkbn());

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
     * <p>Select PTL_ADM_CONF All Data
     * @return List in PTL_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public PtlAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlAdmConfModel ret = new PtlAdmConfModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PAC_PTL_EDITKBN,");
            sql.addSql("   PAC_DEF_KBN,");
            sql.addSql("   PAC_DEF_TYPE");
            sql.addSql(" from ");
            sql.addSql("   PTL_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getPtlAdmConfFromRs(rs);
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
     * <p>Select PTL_ADM_CONF
     * @param pacPtlEditkbn PAC_PTL_EDITKBN
     * @return PTL_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public PtlAdmConfModel select(int pacPtlEditkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PAC_PTL_EDITKBN,");
            sql.addSql("   PAC_DEF_KBN,");
            sql.addSql("   PAC_DEF_TYPE");
            sql.addSql(" from");
            sql.addSql("   PTL_ADM_CONF");
            sql.addSql(" where ");
            sql.addSql("   PAC_PTL_EDITKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pacPtlEditkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlAdmConfFromRs(rs);
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
     * <p>Delete PTL_ADM_CONF
     * @param pacPtlEditkbn PAC_PTL_EDITKBN
     * @return count削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int pacPtlEditkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_ADM_CONF");
            sql.addSql(" where ");
            sql.addSql("   PAC_PTL_EDITKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pacPtlEditkbn);

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
     * <p>Create PTL_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private PtlAdmConfModel __getPtlAdmConfFromRs(ResultSet rs) throws SQLException {
        PtlAdmConfModel bean = new PtlAdmConfModel();
        bean.setPacPtlEditkbn(rs.getInt("PAC_PTL_EDITKBN"));
        bean.setPacDefKbn(rs.getInt("PAC_DEF_KBN"));
        bean.setPacDefType(rs.getInt("PAC_DEF_TYPE"));
        return bean;
    }
}
