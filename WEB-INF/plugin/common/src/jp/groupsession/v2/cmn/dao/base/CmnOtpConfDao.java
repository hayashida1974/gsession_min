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
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;

/**
 * <p>CMN_OTP_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnOtpConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnOtpConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnOtpConfDao(Connection con) {
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
            sql.addSql("drop table CMN_OTP_CONF");

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
            sql.addSql(" create table CMN_OTP_CONF (");
            sql.addSql("   COC_USE_OTP integer not null,");
            sql.addSql("   COC_OTP_USER integer not null,");
            sql.addSql("   COC_OTP_USER_TYPE integer not null,");
            sql.addSql("   COC_OTP_IPCOND integer not null,");
            sql.addSql("   COC_OTP_IP text,");
            sql.addSql("   COC_SMTP_URL varchar(200),");
            sql.addSql("   COC_SMTP_PORT varchar(5),");
            sql.addSql("   COC_SMTP_SSLUSE integer not null,");
            sql.addSql("   COC_SMTP_FROM varchar(200),");
            sql.addSql("   COC_SMTP_USER varchar(100),");
            sql.addSql("   COC_SMTP_PASS varchar(140),");
            sql.addSql("   COC_SMTP_AUTH_TYPE integer not null,");
            sql.addSql("   COT_SID integer");
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
     * <p>Insert CMN_OTP_CONF Data Bindding JavaBean
     * @param bean CMN_OTP_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnOtpConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_OTP_CONF(");
            sql.addSql("   COC_USE_OTP,");
            sql.addSql("   COC_OTP_USER,");
            sql.addSql("   COC_OTP_USER_TYPE,");
            sql.addSql("   COC_OTP_IPCOND,");
            sql.addSql("   COC_OTP_IP,");
            sql.addSql("   COC_SMTP_URL,");
            sql.addSql("   COC_SMTP_PORT,");
            sql.addSql("   COC_SMTP_SSLUSE,");
            sql.addSql("   COC_SMTP_FROM,");
            sql.addSql("   COC_SMTP_USER,");
            sql.addSql("   COC_SMTP_PASS,");
            sql.addSql("   COC_SMTP_AUTH_TYPE,");
            sql.addSql("   COT_SID");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCocUseOtp());
            sql.addIntValue(bean.getCocOtpUser());
            sql.addIntValue(bean.getCocOtpUserType());
            sql.addIntValue(bean.getCocOtpIpcond());
            sql.addStrValue(bean.getCocOtpIp());
            sql.addStrValue(bean.getCocSmtpUrl());
            sql.addStrValue(bean.getCocSmtpPort());
            sql.addIntValue(bean.getCocSmtpSsluse());
            sql.addStrValue(bean.getCocSmtpFrom());
            sql.addStrValue(bean.getCocSmtpUser());
            sql.addStrValue(bean.getCocSmtpPass());
            sql.addIntValue(bean.getCocSmtpAuthType());
            sql.addIntValue(bean.getCotSid());
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
     * <p>Update CMN_OTP_CONF Data Bindding JavaBean
     * @param bean CMN_OTP_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnOtpConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OTP_CONF");
            sql.addSql(" set ");
            sql.addSql("   COC_USE_OTP=?,");
            sql.addSql("   COC_OTP_USER=?,");
            sql.addSql("   COC_OTP_USER_TYPE=?,");
            sql.addSql("   COC_OTP_IPCOND=?,");
            sql.addSql("   COC_OTP_IP=?,");
            sql.addSql("   COC_SMTP_URL=?,");
            sql.addSql("   COC_SMTP_PORT=?,");
            sql.addSql("   COC_SMTP_SSLUSE=?,");
            sql.addSql("   COC_SMTP_FROM=?,");
            sql.addSql("   COC_SMTP_USER=?,");
            sql.addSql("   COC_SMTP_PASS=?,");
            sql.addSql("   COC_SMTP_AUTH_TYPE=?,");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCocUseOtp());
            sql.addIntValue(bean.getCocOtpUser());
            sql.addIntValue(bean.getCocOtpUserType());
            sql.addIntValue(bean.getCocOtpIpcond());
            sql.addStrValue(bean.getCocOtpIp());
            sql.addStrValue(bean.getCocSmtpUrl());
            sql.addStrValue(bean.getCocSmtpPort());
            sql.addIntValue(bean.getCocSmtpSsluse());
            sql.addStrValue(bean.getCocSmtpFrom());
            sql.addStrValue(bean.getCocSmtpUser());
            sql.addStrValue(bean.getCocSmtpPass());
            sql.addIntValue(bean.getCocSmtpAuthType());
            sql.addIntValue(bean.getCotSid());

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
     * <p>Select CMN_OTP_CONF All Data
     * @return List in CMN_OTP_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnOtpConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnOtpConfModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COC_USE_OTP,");
            sql.addSql("   COC_OTP_USER,");
            sql.addSql("   COC_OTP_USER_TYPE,");
            sql.addSql("   COC_OTP_IPCOND,");
            sql.addSql("   COC_OTP_IP,");
            sql.addSql("   COC_SMTP_URL,");
            sql.addSql("   COC_SMTP_PORT,");
            sql.addSql("   COC_SMTP_SSLUSE,");
            sql.addSql("   COC_SMTP_FROM,");
            sql.addSql("   COC_SMTP_USER,");
            sql.addSql("   COC_SMTP_PASS,");
            sql.addSql("   COC_SMTP_AUTH_TYPE,");
            sql.addSql("   COT_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_OTP_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnOtpConfFromRs(rs));
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
     * <p>Create CMN_OTP_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnOtpConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnOtpConfModel __getCmnOtpConfFromRs(ResultSet rs) throws SQLException {
        CmnOtpConfModel bean = new CmnOtpConfModel();
        bean.setCocUseOtp(rs.getInt("COC_USE_OTP"));
        bean.setCocOtpUser(rs.getInt("COC_OTP_USER"));
        bean.setCocOtpUserType(rs.getInt("COC_OTP_USER_TYPE"));
        bean.setCocOtpIpcond(rs.getInt("COC_OTP_IPCOND"));
        bean.setCocOtpIp(rs.getString("COC_OTP_IP"));
        bean.setCocSmtpUrl(rs.getString("COC_SMTP_URL"));
        bean.setCocSmtpPort(rs.getString("COC_SMTP_PORT"));
        bean.setCocSmtpSsluse(rs.getInt("COC_SMTP_SSLUSE"));
        bean.setCocSmtpFrom(rs.getString("COC_SMTP_FROM"));
        bean.setCocSmtpUser(rs.getString("COC_SMTP_USER"));
        bean.setCocSmtpPass(rs.getString("COC_SMTP_PASS"));
        bean.setCocSmtpAuthType(rs.getInt("COC_SMTP_AUTH_TYPE"));
        bean.setCotSid(rs.getInt("COT_SID"));
        return bean;
    }

}
