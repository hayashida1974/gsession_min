package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnFirewallConfModel;

/**
 * <p>CMN_FIREWALL_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnFirewallConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnFirewallConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnFirewallConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnFirewallConfDao(Connection con) {
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
            sql.addSql("drop table CMN_FIREWALL_CONF");

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
            sql.addSql(" create table CMN_FIREWALL_CONF (");
            sql.addSql("   CFC_ALLOW_IP text not null,");
            sql.addSql("   CFC_ALLOW_MBL integer not null,");
            sql.addSql("   CFC_ALLOW_MBL_PLUGIN integer not null,");
            sql.addSql("   CFC_ALLOW_ANP integer not null");
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
     * <p>Insert CMN_FIREWALL_CONF Data Bindding JavaBean
     * @param bean CMN_FIREWALL_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnFirewallConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_FIREWALL_CONF(");
            sql.addSql("   CFC_ALLOW_IP,");
            sql.addSql("   CFC_ALLOW_MBL,");
            sql.addSql("   CFC_ALLOW_MBL_PLUGIN,");
            sql.addSql("   CFC_ALLOW_ANP");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCfcAllowIp());
            sql.addIntValue(bean.getCfcAllowMbl());
            sql.addIntValue(bean.getCfcAllowMblPlugin());
            sql.addIntValue(bean.getCfcAllowAnp());
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
     * <p>Update CMN_FIREWALL_CONF Data Bindding JavaBean
     * @param bean CMN_FIREWALL_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnFirewallConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_FIREWALL_CONF");
            sql.addSql(" set ");
            sql.addSql("   CFC_ALLOW_IP=?,");
            sql.addSql("   CFC_ALLOW_MBL=?,");
            sql.addSql("   CFC_ALLOW_MBL_PLUGIN=?,");
            sql.addSql("   CFC_ALLOW_ANP=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCfcAllowIp());
            sql.addIntValue(bean.getCfcAllowMbl());
            sql.addIntValue(bean.getCfcAllowMblPlugin());
            sql.addIntValue(bean.getCfcAllowAnp());

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
     * <p>Select CMN_FIREWALL_CONF All Data
     * @return List in CMN_FIREWALL_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnFirewallConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnFirewallConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CFC_ALLOW_IP,");
            sql.addSql("   CFC_ALLOW_MBL,");
            sql.addSql("   CFC_ALLOW_MBL_PLUGIN,");
            sql.addSql("   CFC_ALLOW_ANP");
            sql.addSql(" from ");
            sql.addSql("   CMN_FIREWALL_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnFirewallConfFromRs(rs);
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
     * <p>Create CMN_FIREWALL_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnFirewallConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnFirewallConfModel __getCmnFirewallConfFromRs(ResultSet rs) throws SQLException {
        CmnFirewallConfModel bean = new CmnFirewallConfModel();
        bean.setCfcAllowIp(rs.getString("CFC_ALLOW_IP"));
        bean.setCfcAllowMbl(rs.getInt("CFC_ALLOW_MBL"));
        bean.setCfcAllowMblPlugin(rs.getInt("CFC_ALLOW_MBL_PLUGIN"));
        bean.setCfcAllowAnp(rs.getInt("CFC_ALLOW_ANP"));
        return bean;
    }
}
