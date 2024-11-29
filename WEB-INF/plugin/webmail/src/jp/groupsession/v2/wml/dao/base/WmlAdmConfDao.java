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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;

/**
 * <p>WML_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAdmConfDao(Connection con) {
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
            sql.addSql("drop table WML_ADM_CONF");

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
            sql.addSql(" create table WML_ADM_CONF (");
            sql.addSql("   WAD_ACNT_MAKE NUMBER(10,0) not null,");
            sql.addSql("   WAD_ACCT_SENDFORMAT NUMBER(10,0) not null,");
            sql.addSql("   WAD_ACCT_LOG_REGIST NUMBER(10,0) not null,");
            sql.addSql("   WAD_PERMIT_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_AUTO_RECEIVE_TIME NUMBER(10,0) not null,");
            sql.addSql("   WAD_DISK NUMBER(10,0) not null,");
            sql.addSql("   WAD_DISK_SIZE NUMBER(10,0),");
            sql.addSql("   WAD_DISK_COMP NUMBER(10,0),");
            sql.addSql("   WAD_DELRECEIVE NUMBER(10,0) not null,");
            sql.addSql("   WAD_AUTORECEIVE NUMBER(10,0) not null,");
            sql.addSql("   WAD_SEND_HOST varchar(100),");
            sql.addSql("   WAD_SEND_PORT NUMBER(10,0),");
            sql.addSql("   WAD_SEND_SSL NUMBER(10,0),");
            sql.addSql("   WAD_RECEIVE_HOST varchar(100),");
            sql.addSql("   WAD_RECEIVE_PORT NUMBER(10,0),");
            sql.addSql("   WAD_RECEIVE_SSL NUMBER(10,0),");
            sql.addSql("   WAD_CHECK_ADDRESS NUMBER(10,0) not null,");
            sql.addSql("   WAD_CHECK_FILE NUMBER(10,0) not null,");
            sql.addSql("   WAD_COMPRESS_FILE NUMBER(10,0) not null,");
            sql.addSql("   WAD_TIMESENT NUMBER(10,0) not null,");
            sql.addSql("   WAD_SEND_LIMIT NUMBER(10,0) not null,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE NUMBER(10,0),");
            sql.addSql("   WAD_FWLIMIT NUMBER(10,0) not null,");
            sql.addSql("   WAD_BCC NUMBER(10,0) not null,");
            sql.addSql("   WAD_BCC_TH NUMBER(10,0),");
            sql.addSql("   WAD_WARN_DISK NUMBER(10,0) not null,");
            sql.addSql("   WAD_WARN_DISK_TH NUMBER(10,0),");
            sql.addSql("   WAD_SETTING_SERVER NUMBER(10,0) not null,");
            sql.addSql("   WAD_PROXY_USER NUMBER(10,0) not null,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF NUMBER(10,0),");
            sql.addSql("   WAD_TIMESENT_DEF NUMBER(10,0),");
            sql.addSql("   WAD_LINK_LIMIT NUMBER(10,0),");
            sql.addSql("   WAD_TLD_LIMIT NUMBER(10,0),");
            sql.addSql("   WAD_TLD_LIMIT_TEXT text");
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
     * <p>Insert WML_ADM_CONF Data Bindding JavaBean
     * @param bean WML_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ADM_CONF(");
            sql.addSql("   WAD_ACNT_MAKE,");
            sql.addSql("   WAD_ACCT_SENDFORMAT,");
            sql.addSql("   WAD_ACCT_LOG_REGIST,");
            sql.addSql("   WAD_PERMIT_KBN,");
            sql.addSql("   WAD_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAD_DISK,");
            sql.addSql("   WAD_DISK_SIZE,");
            sql.addSql("   WAD_DISK_COMP,");
            sql.addSql("   WAD_DELRECEIVE,");
            sql.addSql("   WAD_AUTORECEIVE,");
            sql.addSql("   WAD_SEND_HOST,");
            sql.addSql("   WAD_SEND_PORT,");
            sql.addSql("   WAD_SEND_SSL,");
            sql.addSql("   WAD_RECEIVE_HOST,");
            sql.addSql("   WAD_RECEIVE_PORT,");
            sql.addSql("   WAD_RECEIVE_SSL,");
            sql.addSql("   WAD_CHECK_ADDRESS,");
            sql.addSql("   WAD_CHECK_FILE,");
            sql.addSql("   WAD_COMPRESS_FILE,");
            sql.addSql("   WAD_TIMESENT,");
            sql.addSql("   WAD_SEND_LIMIT,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE,");
            sql.addSql("   WAD_FWLIMIT,");
            sql.addSql("   WAD_BCC,");
            sql.addSql("   WAD_BCC_TH,");
            sql.addSql("   WAD_WARN_DISK,");
            sql.addSql("   WAD_WARN_DISK_TH,");
            sql.addSql("   WAD_SETTING_SERVER,");
            sql.addSql("   WAD_PROXY_USER,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF,");
            sql.addSql("   WAD_TIMESENT_DEF,");
            sql.addSql("   WAD_LINK_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT_TEXT");
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
            sql.addIntValue(bean.getWadAcntMake());
            sql.addIntValue(bean.getWadAcctSendformat());
            sql.addIntValue(bean.getWadAcctLogRegist());
            sql.addIntValue(bean.getWadPermitKbn());
            sql.addIntValue(bean.getWadAutoReceiveTime());
            sql.addIntValue(bean.getWadDisk());
            sql.addIntValue(bean.getWadDiskSize());
            sql.addIntValue(bean.getWadDiskComp());
            sql.addIntValue(bean.getWadDelreceive());
            sql.addIntValue(bean.getWadAutoreceive());
            sql.addStrValue(bean.getWadSendHost());
            sql.addIntValue(bean.getWadSendPort());
            sql.addIntValue(bean.getWadSendSsl());
            sql.addStrValue(bean.getWadReceiveHost());
            sql.addIntValue(bean.getWadReceivePort());
            sql.addIntValue(bean.getWadReceiveSsl());
            sql.addIntValue(bean.getWadCheckAddress());
            sql.addIntValue(bean.getWadCheckFile());
            sql.addIntValue(bean.getWadCompressFile());
            sql.addIntValue(bean.getWadTimesent());
            sql.addIntValue(bean.getWadSendLimit());
            sql.addIntValue(bean.getWadSendLimitSize());
            sql.addIntValue(bean.getWadFwlimit());
            sql.addIntValue(bean.getWadBcc());
            sql.addIntValue(bean.getWadBccTh());
            sql.addIntValue(bean.getWadWarnDisk());
            sql.addIntValue(bean.getWadWarnDiskTh());
            sql.addIntValue(bean.getWadSettingServer());
            sql.addIntValue(bean.getWadProxyUser());
            sql.addIntValue(bean.getWadCompressFileDef());
            sql.addIntValue(bean.getWadTimesentDef());
            sql.addIntValue(bean.getWadlinkLimit());
            sql.addIntValue(bean.getWadTldLimit());
            sql.addStrValue(bean.getWadTldLimitText());

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
     * <p>Update WML_ADM_CONF Data Bindding JavaBean
     * @param bean WML_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   WAD_ACNT_MAKE=?,");
            sql.addSql("   WAD_ACCT_SENDFORMAT=?,");
            sql.addSql("   WAD_ACCT_LOG_REGIST=?,");
            sql.addSql("   WAD_PERMIT_KBN=?,");
            sql.addSql("   WAD_AUTO_RECEIVE_TIME=?,");
            sql.addSql("   WAD_DISK=?,");
            sql.addSql("   WAD_DISK_SIZE=?,");
            sql.addSql("   WAD_DISK_COMP=?,");
            sql.addSql("   WAD_DELRECEIVE=?,");
            sql.addSql("   WAD_AUTORECEIVE=?,");
            sql.addSql("   WAD_SEND_HOST=?,");
            sql.addSql("   WAD_SEND_PORT=?,");
            sql.addSql("   WAD_SEND_SSL=?,");
            sql.addSql("   WAD_RECEIVE_HOST=?,");
            sql.addSql("   WAD_RECEIVE_PORT=?,");
            sql.addSql("   WAD_RECEIVE_SSL=?,");
            sql.addSql("   WAD_CHECK_ADDRESS=?,");
            sql.addSql("   WAD_CHECK_FILE=?,");
            sql.addSql("   WAD_COMPRESS_FILE=?,");
            sql.addSql("   WAD_TIMESENT=?,");
            sql.addSql("   WAD_SEND_LIMIT=?,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE=?,");
            sql.addSql("   WAD_FWLIMIT=?,");
            sql.addSql("   WAD_BCC=?,");
            sql.addSql("   WAD_BCC_TH=?,");
            sql.addSql("   WAD_WARN_DISK=?,");
            sql.addSql("   WAD_WARN_DISK_TH=?,");
            sql.addSql("   WAD_SETTING_SERVER=?,");
            sql.addSql("   WAD_PROXY_USER=?,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF=?,");
            sql.addSql("   WAD_TIMESENT_DEF=?,");
            sql.addSql("   WAD_LINK_LIMIT=?,");
            sql.addSql("   WAD_TLD_LIMIT=?,");
            sql.addSql("   WAD_TLD_LIMIT_TEXT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWadAcntMake());
            sql.addIntValue(bean.getWadAcctSendformat());
            sql.addIntValue(bean.getWadAcctLogRegist());
            sql.addIntValue(bean.getWadPermitKbn());
            sql.addIntValue(bean.getWadAutoReceiveTime());
            sql.addIntValue(bean.getWadDisk());
            sql.addIntValue(bean.getWadDiskSize());
            sql.addIntValue(bean.getWadDiskComp());
            sql.addIntValue(bean.getWadDelreceive());
            sql.addIntValue(bean.getWadAutoreceive());
            sql.addStrValue(bean.getWadSendHost());
            sql.addIntValue(bean.getWadSendPort());
            sql.addIntValue(bean.getWadSendSsl());
            sql.addStrValue(bean.getWadReceiveHost());
            sql.addIntValue(bean.getWadReceivePort());
            sql.addIntValue(bean.getWadReceiveSsl());
            sql.addIntValue(bean.getWadCheckAddress());
            sql.addIntValue(bean.getWadCheckFile());
            sql.addIntValue(bean.getWadCompressFile());
            sql.addIntValue(bean.getWadTimesent());
            sql.addIntValue(bean.getWadSendLimit());
            sql.addIntValue(bean.getWadSendLimitSize());
            sql.addIntValue(bean.getWadFwlimit());
            sql.addIntValue(bean.getWadBcc());
            sql.addIntValue(bean.getWadBccTh());
            sql.addIntValue(bean.getWadWarnDisk());
            sql.addIntValue(bean.getWadWarnDiskTh());
            sql.addIntValue(bean.getWadSettingServer());
            sql.addIntValue(bean.getWadProxyUser());
            sql.addIntValue(bean.getWadCompressFileDef());
            sql.addIntValue(bean.getWadTimesentDef());
            sql.addIntValue(bean.getWadlinkLimit());
            sql.addIntValue(bean.getWadTldLimit());
            sql.addStrValue(bean.getWadTldLimitText());

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
     * <p>アカウント設定を更新する
     * @param bean WML_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAdmConf(WmlAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        boolean receiveUpFlg = bean.getWadAutoreceive() == GSConstWebmail.MAIL_AUTO_RSV_ON;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   WAD_ACNT_MAKE=?,");
            sql.addSql("   WAD_ACCT_SENDFORMAT=?,");
            sql.addSql("   WAD_ACCT_LOG_REGIST=?,");
            sql.addSql("   WAD_PERMIT_KBN=?,");
            if (receiveUpFlg) {
                sql.addSql("   WAD_AUTO_RECEIVE_TIME=?,");
            }
            sql.addSql("   WAD_DISK=?,");
            sql.addSql("   WAD_DISK_SIZE=?,");
            sql.addSql("   WAD_DISK_COMP=?,");
            sql.addSql("   WAD_DELRECEIVE=?,");
            sql.addSql("   WAD_AUTORECEIVE=?,");
            sql.addSql("   WAD_SEND_HOST=?,");
            sql.addSql("   WAD_SEND_PORT=?,");
            sql.addSql("   WAD_SEND_SSL=?,");
            sql.addSql("   WAD_RECEIVE_HOST=?,");
            sql.addSql("   WAD_RECEIVE_PORT=?,");
            sql.addSql("   WAD_RECEIVE_SSL=?,");
            sql.addSql("   WAD_CHECK_ADDRESS=?,");
            sql.addSql("   WAD_CHECK_FILE=?,");
            sql.addSql("   WAD_COMPRESS_FILE=?,");
            sql.addSql("   WAD_TIMESENT=?,");
            sql.addSql("   WAD_SEND_LIMIT=?,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE=?,");
            sql.addSql("   WAD_FWLIMIT=?,");
            sql.addSql("   WAD_BCC=?,");
            sql.addSql("   WAD_BCC_TH=?,");
            sql.addSql("   WAD_WARN_DISK=?,");
            sql.addSql("   WAD_WARN_DISK_TH=?,");
            sql.addSql("   WAD_SETTING_SERVER=?,");
            sql.addSql("   WAD_PROXY_USER=?,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF=?,");
            sql.addSql("   WAD_TIMESENT_DEF=?,");
            sql.addSql("   WAD_LINK_LIMIT=?,");

            if (bean.getWadTldLimit() == GSConstWebmail.WAD_TLD_UNLIMITED) {
                // TLD制限なしの場合 → 許可リスト更新しない
                sql.addSql("   WAD_TLD_LIMIT=?");
            } else {
                // TLD制限ありの場合 → 許可リスト更新する
                sql.addSql("   WAD_TLD_LIMIT=?,");
                sql.addSql("   WAD_TLD_LIMIT_TEXT=?");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWadAcntMake());
            sql.addIntValue(bean.getWadAcctSendformat());
            sql.addIntValue(bean.getWadAcctLogRegist());
            sql.addIntValue(bean.getWadPermitKbn());
            if (receiveUpFlg) {
                sql.addIntValue(bean.getWadAutoReceiveTime());
            }
            sql.addIntValue(bean.getWadDisk());
            sql.addIntValue(bean.getWadDiskSize());
            sql.addIntValue(bean.getWadDiskComp());
            sql.addIntValue(bean.getWadDelreceive());
            sql.addIntValue(bean.getWadAutoreceive());
            sql.addStrValue(bean.getWadSendHost());
            sql.addIntValue(bean.getWadSendPort());
            sql.addIntValue(bean.getWadSendSsl());
            sql.addStrValue(bean.getWadReceiveHost());
            sql.addIntValue(bean.getWadReceivePort());
            sql.addIntValue(bean.getWadReceiveSsl());
            sql.addIntValue(bean.getWadCheckAddress());
            sql.addIntValue(bean.getWadCheckFile());
            sql.addIntValue(bean.getWadCompressFile());
            sql.addIntValue(bean.getWadTimesent());
            sql.addIntValue(bean.getWadSendLimit());
            sql.addIntValue(bean.getWadSendLimitSize());
            sql.addIntValue(bean.getWadFwlimit());
            sql.addIntValue(bean.getWadBcc());
            sql.addIntValue(bean.getWadBccTh());
            sql.addIntValue(bean.getWadWarnDisk());
            sql.addIntValue(bean.getWadWarnDiskTh());
            sql.addIntValue(bean.getWadSettingServer());
            sql.addIntValue(bean.getWadProxyUser());
            sql.addIntValue(bean.getWadCompressFileDef());
            sql.addIntValue(bean.getWadTimesentDef());
            sql.addIntValue(bean.getWadlinkLimit());
            sql.addIntValue(bean.getWadTldLimit());

            if (bean.getWadTldLimit() != GSConstWebmail.WAD_TLD_UNLIMITED) {
                sql.addStrValue(bean.getWadTldLimitText());
            }

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
     * <p>Select WML_ADM_CONF All Data
     * @return List in WML_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAdmConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlAdmConfModel> ret = new ArrayList<WmlAdmConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAD_ACNT_MAKE,");
            sql.addSql("   WAD_ACCT_SENDFORMAT,");
            sql.addSql("   WAD_ACCT_LOG_REGIST,");
            sql.addSql("   WAD_PERMIT_KBN,");
            sql.addSql("   WAD_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAD_DISK,");
            sql.addSql("   WAD_DISK_SIZE,");
            sql.addSql("   WAD_DISK_COMP,");
            sql.addSql("   WAD_DELRECEIVE,");
            sql.addSql("   WAD_AUTORECEIVE,");
            sql.addSql("   WAD_AUTORECEIVE,");
            sql.addSql("   WAD_SEND_HOST,");
            sql.addSql("   WAD_SEND_PORT,");
            sql.addSql("   WAD_SEND_SSL,");
            sql.addSql("   WAD_RECEIVE_HOST,");
            sql.addSql("   WAD_RECEIVE_PORT,");
            sql.addSql("   WAD_RECEIVE_SSL,");
            sql.addSql("   WAD_CHECK_ADDRESS,");
            sql.addSql("   WAD_CHECK_FILE,");
            sql.addSql("   WAD_COMPRESS_FILE,");
            sql.addSql("   WAD_TIMESENT,");
            sql.addSql("   WAD_SEND_LIMIT,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE,");
            sql.addSql("   WAD_FWLIMIT,");
            sql.addSql("   WAD_BCC,");
            sql.addSql("   WAD_BCC_TH,");
            sql.addSql("   WAD_WARN_DISK,");
            sql.addSql("   WAD_WARN_DISK_TH,");
            sql.addSql("   WAD_SETTING_SERVER,");
            sql.addSql("   WAD_PROXY_USER,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF,");
            sql.addSql("   WAD_TIMESENT_DEF,");
            sql.addSql("   WAD_LINK_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAdmConfFromRs(rs));
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
     * <p>Select WML_ADM_CONF
     * @return  WML_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public WmlAdmConfModel selectAdmData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAdmConfModel ret = new WmlAdmConfModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAD_ACNT_MAKE,");
            sql.addSql("   WAD_ACCT_SENDFORMAT,");
            sql.addSql("   WAD_ACCT_LOG_REGIST,");
            sql.addSql("   WAD_PERMIT_KBN,");
            sql.addSql("   WAD_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAD_DISK,");
            sql.addSql("   WAD_DISK_SIZE,");
            sql.addSql("   WAD_DISK_COMP,");
            sql.addSql("   WAD_DELRECEIVE,");
            sql.addSql("   WAD_AUTORECEIVE,");
            sql.addSql("   WAD_SEND_HOST,");
            sql.addSql("   WAD_SEND_PORT,");
            sql.addSql("   WAD_SEND_SSL,");
            sql.addSql("   WAD_RECEIVE_HOST,");
            sql.addSql("   WAD_RECEIVE_PORT,");
            sql.addSql("   WAD_RECEIVE_SSL,");
            sql.addSql("   WAD_CHECK_ADDRESS,");
            sql.addSql("   WAD_CHECK_FILE,");
            sql.addSql("   WAD_COMPRESS_FILE,");
            sql.addSql("   WAD_TIMESENT,");
            sql.addSql("   WAD_SEND_LIMIT,");
            sql.addSql("   WAD_SEND_LIMIT_SIZE,");
            sql.addSql("   WAD_FWLIMIT,");
            sql.addSql("   WAD_BCC,");
            sql.addSql("   WAD_BCC_TH,");
            sql.addSql("   WAD_WARN_DISK,");
            sql.addSql("   WAD_WARN_DISK_TH,");
            sql.addSql("   WAD_SETTING_SERVER,");
            sql.addSql("   WAD_PROXY_USER,");
            sql.addSql("   WAD_COMPRESS_FILE_DEF,");
            sql.addSql("   WAD_TIMESENT_DEF,");
            sql.addSql("   WAD_LINK_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT,");
            sql.addSql("   WAD_TLD_LIMIT_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAdmConfFromRs(rs);
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
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int selectCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create WML_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private WmlAdmConfModel __getWmlAdmConfFromRs(ResultSet rs) throws SQLException {
        WmlAdmConfModel bean = new WmlAdmConfModel();
        bean.setWadAcntMake(rs.getInt("WAD_ACNT_MAKE"));
        bean.setWadAcctSendformat(rs.getInt("WAD_ACCT_SENDFORMAT"));
        bean.setWadAcctLogRegist(rs.getInt("WAD_ACCT_LOG_REGIST"));
        bean.setWadPermitKbn(rs.getInt("WAD_PERMIT_KBN"));
        bean.setWadAutoReceiveTime(rs.getInt("WAD_AUTO_RECEIVE_TIME"));
        bean.setWadDisk(rs.getInt("WAD_DISK"));
        bean.setWadDiskSize(rs.getInt("WAD_DISK_SIZE"));
        bean.setWadDiskComp(rs.getInt("WAD_DISK_COMP"));
        bean.setWadDelreceive(rs.getInt("WAD_DELRECEIVE"));
        bean.setWadAutoreceive(rs.getInt("WAD_AUTORECEIVE"));
        bean.setWadSendHost(rs.getString("WAD_SEND_HOST"));
        bean.setWadSendPort(rs.getInt("WAD_SEND_PORT"));
        bean.setWadSendSsl(rs.getInt("WAD_SEND_SSL"));
        bean.setWadReceiveHost(rs.getString("WAD_RECEIVE_HOST"));
        bean.setWadReceivePort(rs.getInt("WAD_RECEIVE_PORT"));
        bean.setWadReceiveSsl(rs.getInt("WAD_RECEIVE_SSL"));
        bean.setWadCheckAddress(rs.getInt("WAD_CHECK_ADDRESS"));
        bean.setWadCheckFile(rs.getInt("WAD_CHECK_FILE"));
        bean.setWadCompressFile(rs.getInt("WAD_COMPRESS_FILE"));
        bean.setWadTimesent(rs.getInt("WAD_TIMESENT"));
        bean.setWadSendLimit(rs.getInt("WAD_SEND_LIMIT"));
        bean.setWadSendLimitSize(rs.getInt("WAD_SEND_LIMIT_SIZE"));
        bean.setWadFwlimit(rs.getInt("WAD_FWLIMIT"));
        bean.setWadBcc(rs.getInt("WAD_BCC"));
        bean.setWadBccTh(rs.getInt("WAD_BCC_TH"));
        bean.setWadWarnDisk(rs.getInt("WAD_WARN_DISK"));
        bean.setWadWarnDiskTh(rs.getInt("WAD_WARN_DISK_TH"));
        bean.setWadSettingServer(rs.getInt("WAD_SETTING_SERVER"));
        bean.setWadProxyUser(rs.getInt("WAD_PROXY_USER"));
        bean.setWadCompressFileDef(rs.getInt("WAD_COMPRESS_FILE_DEF"));
        bean.setWadTimesentDef(rs.getInt("WAD_TIMESENT_DEF"));
        bean.setWadlinkLimit(rs.getInt("WAD_LINK_LIMIT"));
        bean.setWadTldLimit(rs.getInt("WAD_TLD_LIMIT"));
        bean.setWadTldLimitText(rs.getString("WAD_TLD_LIMIT_TEXT"));
        return bean;
    }
}
