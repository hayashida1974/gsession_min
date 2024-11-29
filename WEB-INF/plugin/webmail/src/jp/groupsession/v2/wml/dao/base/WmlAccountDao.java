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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;

/**
 * <p>WML_ACCOUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlAccountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT");

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
            sql.addSql(" create table WML_ACCOUNT (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WAC_TYPE NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0),");
            sql.addSql("   WAC_NAME varchar(200) not null,");
            sql.addSql("   WAC_ADDRESS varchar(256) not null,");
            sql.addSql("   WAC_SEND_HOST varchar(100) not null,");
            sql.addSql("   WAC_SEND_PORT NUMBER(10,0) not null,");
            sql.addSql("   WAC_SEND_USER varchar(100),");
            sql.addSql("   WAC_SEND_PASS varchar(100),");
            sql.addSql("   WAC_SEND_SSL NUMBER(10,0) not null,");
            sql.addSql("   WAC_RECEIVE_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WAC_RECEIVE_HOST varchar(100) not null,");
            sql.addSql("   WAC_RECEIVE_PORT NUMBER(10,0) not null,");
            sql.addSql("   WAC_RECEIVE_USER varchar(100) not null,");
            sql.addSql("   WAC_RECEIVE_PASS varchar(100) not null,");
            sql.addSql("   WAC_RECEIVE_SSL NUMBER(10,0) not null,");
            sql.addSql("   WAC_DISK NUMBER(10,0) not null,");
            sql.addSql("   WAC_DISK_SIZE NUMBER(10,0) not null,");
            sql.addSql("   WAC_BIKO varchar(1000),");
            sql.addSql("   WAC_ORGANIZATION varchar(100),");
            sql.addSql("   WAC_SIGN varchar(1000),");
            sql.addSql("   WAC_SIGN_POINT_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAC_SIGN_DSP_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAC_AUTOTO varchar(256),");
            sql.addSql("   WAC_AUTOCC varchar(256),");
            sql.addSql("   WAC_AUTOBCC varchar(256),");
            sql.addSql("   WAC_DELRECEIVE NUMBER(10,0) not null,");
            sql.addSql("   WAC_RERECEIVE NUMBER(10,0) not null,");
            sql.addSql("   WAC_APOP NUMBER(10,0) not null,");
            sql.addSql("   WAC_SMTP_AUTH NUMBER(10,0) not null,");
            sql.addSql("   WAC_POPBSMTP NUMBER(10,0) not null,");
            sql.addSql("   WAC_ENCODE_SEND NUMBER(10,0) not null,");
            sql.addSql("   WAC_AUTORECEIVE NUMBER(10,0) not null,");
            sql.addSql("   WAC_SEND_MAILTYPE NUMBER(10,0) not null,");
            sql.addSql("   WAC_RECEIVE_DATE timestamp,");
            sql.addSql("   WAC_JKBN NUMBER(10,0) not null,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME NUMBER(10,0) not null,");
            sql.addSql("   WAC_THEME NUMBER(10,0) not null,");
            sql.addSql("   WAC_CHECK_ADDRESS NUMBER(10,0) not null,");
            sql.addSql("   WAC_CHECK_FILE NUMBER(10,0) not null,");
            sql.addSql("   WAC_COMPRESS_FILE NUMBER(10,0) not null,");
            sql.addSql("   WAC_TIMESENT NUMBER(10,0) not null,");
            sql.addSql("   WAC_QUOTES NUMBER(10,0) not null,");
            sql.addSql("   WAC_DISK_SPS NUMBER(10,0) not null,");
            sql.addSql("   WAC_AUTORECEIVE_AP NUMBER(10,0),");
            sql.addSql("   WAC_TIMESENT_DEF NUMBER(10,0),");
            sql.addSql("   WAC_COMPRESS_FILE_DEF NUMBER(10,0),");
            sql.addSql("   WAC_SIGN_AUTO NUMBER(10,0) not null,");
            sql.addSql("   WAC_TOP_CMD NUMBER(10,0) not null,");
            sql.addSql("   WAC_ACCOUNT_ID varchar(100) not null,");
            sql.addSql("   WAC_AUTH_TYPE integer not null,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE integer not null,");
            sql.addSql("   COT_SID integer,");
            sql.addSql("   primary key (WAC_SID)");
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
     * <p>Insert WML_ACCOUNT Data Bindding JavaBean
     * @param bean WML_ACCOUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_ACCOUNT_ID,");
            sql.addSql("   WAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ADDRESS,");
            sql.addSql("   WAC_SEND_HOST,");
            sql.addSql("   WAC_SEND_PORT,");
            sql.addSql("   WAC_SEND_USER,");
            sql.addSql("   WAC_SEND_PASS,");
            sql.addSql("   WAC_SEND_SSL,");
            sql.addSql("   WAC_RECEIVE_TYPE,");
            sql.addSql("   WAC_RECEIVE_HOST,");
            sql.addSql("   WAC_RECEIVE_PORT,");
            sql.addSql("   WAC_RECEIVE_USER,");
            sql.addSql("   WAC_RECEIVE_PASS,");
            sql.addSql("   WAC_RECEIVE_SSL,");
            sql.addSql("   WAC_DISK,");
            sql.addSql("   WAC_DISK_SIZE,");
            sql.addSql("   WAC_BIKO,");
            sql.addSql("   WAC_ORGANIZATION,");
            sql.addSql("   WAC_SIGN,");
            sql.addSql("   WAC_AUTOTO,");
            sql.addSql("   WAC_AUTOCC,");
            sql.addSql("   WAC_AUTOBCC,");
            sql.addSql("   WAC_DELRECEIVE,");
            sql.addSql("   WAC_RERECEIVE,");
            sql.addSql("   WAC_APOP,");
            sql.addSql("   WAC_TOP_CMD,");
            sql.addSql("   WAC_SMTP_AUTH,");
            sql.addSql("   WAC_POPBSMTP,");
            sql.addSql("   WAC_ENCODE_SEND,");
            sql.addSql("   WAC_AUTORECEIVE,");
            sql.addSql("   WAC_SEND_MAILTYPE,");
            sql.addSql("   WAC_RECEIVE_DATE,");
            sql.addSql("   WAC_JKBN,");
            sql.addSql("   WAC_SIGN_POINT_KBN,");
            sql.addSql("   WAC_SIGN_DSP_KBN,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAC_THEME,");
            sql.addSql("   WAC_CHECK_ADDRESS,");
            sql.addSql("   WAC_CHECK_FILE,");
            sql.addSql("   WAC_COMPRESS_FILE,");
            sql.addSql("   WAC_TIMESENT,");
            sql.addSql("   WAC_QUOTES,");
            sql.addSql("   WAC_DISK_SPS,");
            sql.addSql("   WAC_AUTORECEIVE_AP,");
            sql.addSql("   WAC_TIMESENT_DEF,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WAC_SIGN_AUTO,");
            sql.addSql("   WAC_AUTH_TYPE,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE,");
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
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWacAccountId());
            sql.addIntValue(bean.getWacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getWacName());
            sql.addStrValue(bean.getWacAddress());
            sql.addStrValue(bean.getWacSendHost());
            sql.addIntValue(bean.getWacSendPort());
            sql.addStrValue(bean.getWacSendUser());
            sql.addStrValue(bean.getWacSendPass());
            sql.addIntValue(bean.getWacSendSsl());
            sql.addIntValue(bean.getWacReceiveType());
            sql.addStrValue(bean.getWacReceiveHost());
            sql.addIntValue(bean.getWacReceivePort());
            sql.addStrValue(bean.getWacReceiveUser());
            sql.addStrValue(bean.getWacReceivePass());
            sql.addIntValue(bean.getWacReceiveSsl());
            sql.addIntValue(bean.getWacDisk());
            sql.addIntValue(bean.getWacDiskSize());
            sql.addStrValue(bean.getWacBiko());
            sql.addStrValue(bean.getWacOrganization());
            sql.addStrValue(bean.getWacSign());
            sql.addStrValue(bean.getWacAutoto());
            sql.addStrValue(bean.getWacAutocc());
            sql.addStrValue(bean.getWacAutobcc());
            sql.addIntValue(bean.getWacDelreceive());
            sql.addIntValue(bean.getWacRereceive());
            sql.addIntValue(bean.getWacApop());
            sql.addIntValue(bean.getWacTopCmd());
            sql.addIntValue(bean.getWacSmtpAuth());
            sql.addIntValue(bean.getWacPopbsmtp());
            sql.addIntValue(bean.getWacEncodeSend());
            sql.addIntValue(bean.getWacAutoreceive());
            sql.addIntValue(bean.getWacSendMailtype());
            sql.addDateValue(bean.getWacReceiveDate());
            sql.addIntValue(bean.getWacJkbn());
            sql.addIntValue(bean.getWacSignPointKbn());
            sql.addIntValue(bean.getWacSignDspKbn());
            sql.addIntValue(bean.getWacAutoReceiveTime());
            sql.addIntValue(bean.getWacTheme());
            sql.addIntValue(bean.getWacCheckAddress());
            sql.addIntValue(bean.getWacCheckFile());
            sql.addIntValue(bean.getWacCompressFile());
            sql.addIntValue(bean.getWacTimesent());
            sql.addIntValue(bean.getWacQuotes());
            sql.addIntValue(bean.getWacDiskSps());
            sql.addIntValue(bean.getWacAutoreceiveAp());
            sql.addIntValue(bean.getWacTimesentDef());
            sql.addIntValue(bean.getWacCompressFileDef());
            sql.addIntValue(bean.getWacSignAuto());
            sql.addIntValue(bean.getWacAuthType());
            sql.addIntValue(bean.getWacAutoSaveMinute());
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
     * <p>Update WML_ACCOUNT Data Bindding JavaBean
     * @param bean WML_ACCOUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateReceivePass(WmlAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_RECEIVE_PASS=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addStrValue(bean.getWacReceivePass());

            //where
            sql.addIntValue(bean.getWacSid());

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
     * <p>Select WML_ACCOUNT All Data
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountModel> ret = new ArrayList<WmlAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_ACCOUNT_ID,");
            sql.addSql("   WAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ADDRESS,");
            sql.addSql("   WAC_SEND_HOST,");
            sql.addSql("   WAC_SEND_PORT,");
            sql.addSql("   WAC_SEND_USER,");
            sql.addSql("   WAC_SEND_PASS,");
            sql.addSql("   WAC_SEND_SSL,");
            sql.addSql("   WAC_RECEIVE_TYPE,");
            sql.addSql("   WAC_RECEIVE_HOST,");
            sql.addSql("   WAC_RECEIVE_PORT,");
            sql.addSql("   WAC_RECEIVE_USER,");
            sql.addSql("   WAC_RECEIVE_PASS,");
            sql.addSql("   WAC_RECEIVE_SSL,");
            sql.addSql("   WAC_DISK,");
            sql.addSql("   WAC_DISK_SIZE,");
            sql.addSql("   WAC_BIKO,");
            sql.addSql("   WAC_ORGANIZATION,");
            sql.addSql("   WAC_SIGN,");
            sql.addSql("   WAC_SIGN_POINT_KBN,");
            sql.addSql("   WAC_SIGN_DSP_KBN,");
            sql.addSql("   WAC_AUTOTO,");
            sql.addSql("   WAC_AUTOCC,");
            sql.addSql("   WAC_AUTOBCC,");
            sql.addSql("   WAC_DELRECEIVE,");
            sql.addSql("   WAC_RERECEIVE,");
            sql.addSql("   WAC_APOP,");
            sql.addSql("   WAC_TOP_CMD,");
            sql.addSql("   WAC_SMTP_AUTH,");
            sql.addSql("   WAC_POPBSMTP,");
            sql.addSql("   WAC_ENCODE_SEND,");
            sql.addSql("   WAC_AUTORECEIVE,");
            sql.addSql("   WAC_SEND_MAILTYPE,");
            sql.addSql("   WAC_RECEIVE_DATE,");
            sql.addSql("   WAC_JKBN,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAC_THEME,");
            sql.addSql("   WAC_CHECK_ADDRESS,");
            sql.addSql("   WAC_CHECK_FILE,");
            sql.addSql("   WAC_COMPRESS_FILE,");
            sql.addSql("   WAC_TIMESENT,");
            sql.addSql("   WAC_QUOTES,");
            sql.addSql("   WAC_DISK_SPS,");
            sql.addSql("   WAC_AUTORECEIVE_AP,");
            sql.addSql("   WAC_TIMESENT_DEF,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WAC_SIGN_AUTO,");
            sql.addSql("   WAC_AUTH_TYPE,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   COT_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountFromRs(rs));
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
     * <br>[機  能] 自動受信対象アカウントのアカウントデータを取得する
     * <br>[解  説]
     * <br>[備  考] 代理人または使用者でログイン可能なユーザがいない場合対象にしない
     * @param apNum APサーバ番号
     * @return 自動受信対象アカウントのアカウントSID一覧
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountModel> getAccountSidListForAutoReceive(int apNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WmlAccountModel> ret = new ArrayList<WmlAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_ACCOUNT_ID as WAC_ACCOUNT_ID,");
            sql.addSql("   WML_ACCOUNT.WAC_TYPE as WAC_TYPE,");
            sql.addSql("   WML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME,");
            sql.addSql("   WML_ACCOUNT.WAC_ADDRESS as WAC_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_HOST as WAC_SEND_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PORT as WAC_SEND_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_USER as WAC_SEND_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PASS as WAC_SEND_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_SSL as WAC_SEND_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_TYPE as WAC_RECEIVE_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_HOST as WAC_RECEIVE_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PORT as WAC_RECEIVE_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_USER as WAC_RECEIVE_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PASS as WAC_RECEIVE_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_SSL as WAC_RECEIVE_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK as WAC_DISK,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SIZE as WAC_DISK_SIZE,");
            sql.addSql("   WML_ACCOUNT.WAC_BIKO as WAC_BIKO,");
            sql.addSql("   WML_ACCOUNT.WAC_ORGANIZATION as WAC_ORGANIZATION,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN as WAC_SIGN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_POINT_KBN as WAC_SIGN_POINT_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_DSP_KBN as WAC_SIGN_DSP_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOTO as WAC_AUTOTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOCC as WAC_AUTOCC,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOBCC as WAC_AUTOBCC,");
            sql.addSql("   WML_ACCOUNT.WAC_DELRECEIVE as WAC_DELRECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_RERECEIVE as WAC_RERECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_APOP as WAC_APOP,");
            sql.addSql("   WML_ACCOUNT.WAC_TOP_CMD as WAC_TOP_CMD,");
            sql.addSql("   WML_ACCOUNT.WAC_SMTP_AUTH as WAC_SMTP_AUTH,");
            sql.addSql("   WML_ACCOUNT.WAC_POPBSMTP as WAC_POPBSMTP,");
            sql.addSql("   WML_ACCOUNT.WAC_ENCODE_SEND as WAC_ENCODE_SEND,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE as WAC_AUTORECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_MAILTYPE as WAC_SEND_MAILTYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_DATE as WAC_RECEIVE_DATE,");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN as WAC_JKBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_RECEIVE_TIME as WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WML_ACCOUNT.WAC_THEME as WAC_THEME,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_ADDRESS as WAC_CHECK_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_FILE as WAC_CHECK_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE as WAC_COMPRESS_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT as WAC_TIMESENT,");
            sql.addSql("   WML_ACCOUNT.WAC_QUOTES as WAC_QUOTES,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SPS as WAC_DISK_SPS,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE_AP as WAC_AUTORECEIVE_AP,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT_DEF as WAC_TIMESENT_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE_DEF as WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_AUTO as WAC_SIGN_AUTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE as WAC_AUTH_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_SAVE_MINUTE as WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   WML_ACCOUNT.COT_SID as COT_SID,");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE as WRD_RECEIVE_DATE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT,");
            sql.addSql("   WML_ACCOUNT_RCVDATA");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE = ?");
            sql.addSql(" and ");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addIntValue(GSConstWebmail.MAIL_AUTO_RSV_ON);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            sql.addSql("   and WML_ACCOUNT.WAC_SID in (");
            sql.addSql("     select distinct WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("       from WML_ACCOUNT_USER ");
            sql.addSql("       left join CMN_BELONGM ");
            sql.addSql("         on WML_ACCOUNT_USER.GRP_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql("       left join CMN_USRM ");
            sql.addSql("         on WML_ACCOUNT_USER.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("         or CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       where CMN_USRM.USR_JKBN = 0 and CMN_USRM.USR_UKO_FLG = 0 ");
            sql.addSql("     union all ");
            sql.addSql("     select distinct WML_ACCOUNT_USER_PROXY.WAC_SID ");
            sql.addSql("       from WML_ACCOUNT_USER_PROXY ");
            sql.addSql("       left join CMN_USRM ");
            sql.addSql("         on WML_ACCOUNT_USER_PROXY.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       where CMN_USRM.USR_JKBN = 0 and CMN_USRM.USR_UKO_FLG = 0 ");
            sql.addSql("    )");

            if (apNum > 0) {
                sql.addSql(" and");
                if (apNum == 1) {
                    sql.addSql("   coalesce(WML_ACCOUNT.WAC_AUTORECEIVE_AP, 1) in (0, 1)");
                } else {
                    sql.addSql("   coalesce(WML_ACCOUNT.WAC_AUTORECEIVE_AP, 1) = ?");
                    sql.addIntValue(apNum);
                }
            }

            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_SID = WML_ACCOUNT_RCVDATA.WAC_SID");

            sql.addSql(" order by");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE");

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WmlAccountModel accountMdl = __getWmlAccountFromRs(rs);
                accountMdl.setWacReceiveDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
                ret.add(accountMdl);
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
     * <br>[機  能] アカウント情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考] 削除されたアカウントは除外する
     * @param userSid ユーザSID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getAccountCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getAccountCount(int userSid) throws SQLException {

        //アカウント代理人が許可されているかを判定する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

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
            sql = __setAccountSearchSql(sql, userSid, proxyUserFlg);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountModel> getAccountList(int userSid) throws SQLException {

        //アカウント代理人が許可されているかを判定する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountModel> ret = new ArrayList<WmlAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_ACCOUNT_ID as WAC_ACCOUNT_ID,");
            sql.addSql("   WML_ACCOUNT.WAC_TYPE as WAC_TYPE,");
            sql.addSql("   WML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME,");
            sql.addSql("   WML_ACCOUNT.WAC_ADDRESS as WAC_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_HOST as WAC_SEND_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PORT as WAC_SEND_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_USER as WAC_SEND_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PASS as WAC_SEND_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_SSL as WAC_SEND_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_TYPE as WAC_RECEIVE_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_HOST as WAC_RECEIVE_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PORT as WAC_RECEIVE_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_USER as WAC_RECEIVE_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PASS as WAC_RECEIVE_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_SSL as WAC_RECEIVE_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK as WAC_DISK,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SIZE as WAC_DISK_SIZE,");
            sql.addSql("   WML_ACCOUNT.WAC_BIKO as WAC_BIKO,");
            sql.addSql("   WML_ACCOUNT.WAC_ORGANIZATION as WAC_ORGANIZATION,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN as WAC_SIGN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_POINT_KBN as WAC_SIGN_POINT_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_DSP_KBN as WAC_SIGN_DSP_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOTO as WAC_AUTOTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOCC as WAC_AUTOCC,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOBCC as WAC_AUTOBCC,");
            sql.addSql("   WML_ACCOUNT.WAC_DELRECEIVE as WAC_DELRECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_RERECEIVE as WAC_RERECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_APOP as WAC_APOP,");
            sql.addSql("   WML_ACCOUNT.WAC_TOP_CMD as WAC_TOP_CMD,");
            sql.addSql("   WML_ACCOUNT.WAC_SMTP_AUTH as WAC_SMTP_AUTH,");
            sql.addSql("   WML_ACCOUNT.WAC_POPBSMTP as WAC_POPBSMTP,");
            sql.addSql("   WML_ACCOUNT.WAC_ENCODE_SEND as WAC_ENCODE_SEND,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE as WAC_AUTORECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_MAILTYPE as WAC_SEND_MAILTYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_DATE as WAC_RECEIVE_DATE,");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN as WAC_JKBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_RECEIVE_TIME as WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WML_ACCOUNT.WAC_THEME as WAC_THEME,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_ADDRESS as WAC_CHECK_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_FILE as WAC_CHECK_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE as WAC_COMPRESS_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT as WAC_TIMESENT,");
            sql.addSql("   WML_ACCOUNT.WAC_QUOTES as WAC_QUOTES,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SPS as WAC_DISK_SPS,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE_AP as WAC_AUTORECEIVE_AP,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT_DEF as WAC_TIMESENT_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE_DEF as WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_AUTO as WAC_SIGN_AUTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE as WAC_AUTH_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_SAVE_MINUTE as WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   WML_ACCOUNT.COT_SID as COT_SID");

            sql = __setAccountSearchSql(sql, userSid, proxyUserFlg);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, WML_ACCOUNT.WAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountFromRs(rs));
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
     * <br>[機  能] 指定したユーザが利用可能なアカウントのアカウントSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントSID一覧
     * @throws SQLException SQL実行例外
     */
    public int[] getAccountSidList(int userSid) throws SQLException {

        //アカウント代理人が許可されているかを判定する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int[] wacSidArray = new int[0];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID");

            sql = __setAccountSearchSql(sql, userSid, proxyUserFlg);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Integer> accountSidList = new ArrayList<Integer>();
            while (rs.next()) {
                accountSidList.add(rs.getInt("WAC_SID"));
            }

            wacSidArray = new int[accountSidList.size()];
            for (int i = 0; i < accountSidList.size(); i++) {
                wacSidArray[i] = accountSidList.get(i).intValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wacSidArray;
    }

    /**
     * <br>[機  能] 指定したユーザのデフォルトアカウントSIDを取得する
     * <br>[解  説] 利用可能なアカウントのうち、並び順 = 1のものをデフォルトアカウントとする
     * <br>[備  考] 利用可能なアカウントが存在しない場合、0を返す
     * @param userSid ユーザSID
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getDefaultAccountSid(int userSid) throws SQLException {

        //アカウント代理人が許可されているかを判定する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int defAccountSid = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID");

            sql = __setAccountSearchSql(sql, userSid, proxyUserFlg);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, WML_ACCOUNT.WAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                defAccountSid = rs.getInt("WAC_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return defAccountSid;
    }

    /**
     * <p>Select WML_ACCOUNT
     * @param wacSid WAC_SID
     * @return WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountModel select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_ACCOUNT_ID,");
            sql.addSql("   WAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ADDRESS,");
            sql.addSql("   WAC_SEND_HOST,");
            sql.addSql("   WAC_SEND_PORT,");
            sql.addSql("   WAC_SEND_USER,");
            sql.addSql("   WAC_SEND_PASS,");
            sql.addSql("   WAC_SEND_SSL,");
            sql.addSql("   WAC_RECEIVE_TYPE,");
            sql.addSql("   WAC_RECEIVE_HOST,");
            sql.addSql("   WAC_RECEIVE_PORT,");
            sql.addSql("   WAC_RECEIVE_USER,");
            sql.addSql("   WAC_RECEIVE_PASS,");
            sql.addSql("   WAC_RECEIVE_SSL,");
            sql.addSql("   WAC_DISK,");
            sql.addSql("   WAC_DISK_SIZE,");
            sql.addSql("   WAC_BIKO,");
            sql.addSql("   WAC_ORGANIZATION,");
            sql.addSql("   WAC_SIGN,");
            sql.addSql("   WAC_SIGN_POINT_KBN,");
            sql.addSql("   WAC_SIGN_DSP_KBN,");
            sql.addSql("   WAC_AUTOTO,");
            sql.addSql("   WAC_AUTOCC,");
            sql.addSql("   WAC_AUTOBCC,");
            sql.addSql("   WAC_DELRECEIVE,");
            sql.addSql("   WAC_RERECEIVE,");
            sql.addSql("   WAC_APOP,");
            sql.addSql("   WAC_TOP_CMD,");
            sql.addSql("   WAC_SMTP_AUTH,");
            sql.addSql("   WAC_POPBSMTP,");
            sql.addSql("   WAC_ENCODE_SEND,");
            sql.addSql("   WAC_AUTORECEIVE,");
            sql.addSql("   WAC_SEND_MAILTYPE,");
            sql.addSql("   WAC_RECEIVE_DATE,");
            sql.addSql("   WAC_JKBN,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAC_THEME,");
            sql.addSql("   WAC_CHECK_ADDRESS,");
            sql.addSql("   WAC_CHECK_FILE,");
            sql.addSql("   WAC_COMPRESS_FILE,");
            sql.addSql("   WAC_TIMESENT,");
            sql.addSql("   WAC_QUOTES,");
            sql.addSql("   WAC_DISK_SPS,");
            sql.addSql("   WAC_AUTORECEIVE_AP,");
            sql.addSql("   WAC_TIMESENT_DEF,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WAC_SIGN_AUTO,");
            sql.addSql("   WAC_AUTH_TYPE,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   COT_SID");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] アカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行例外
     */
    public String getAccountName(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String wacName = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wacName = rs.getString("WAC_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return wacName;
    }

    /**
     * <br>[機  能] アカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSidList アカウントSIDリスト
     * @return アカウント名
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getAccountName(String[] wacSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> wacNameList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(wacSidList[0]));
            for (String accountSid : wacSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                wacNameList.add(rs.getString("WAC_NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return wacNameList;
    }

    /**
     * <br>[機  能] 利用可能なアカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考] 代理人または使用者でログイン可能なユーザがいない場合対象にしない
     * @param apNum APサーバ番号
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<WmlAccountModel> getAllAccountDataForAutoReceive(int apNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WmlAccountModel> ret = new ArrayList<WmlAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_ACCOUNT_ID as WAC_ACCOUNT_ID,");
            sql.addSql("   WML_ACCOUNT.WAC_TYPE as WAC_TYPE,");
            sql.addSql("   WML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME,");
            sql.addSql("   WML_ACCOUNT.WAC_ADDRESS as WAC_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_HOST as WAC_SEND_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PORT as WAC_SEND_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_USER as WAC_SEND_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PASS as WAC_SEND_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_SSL as WAC_SEND_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_TYPE as WAC_RECEIVE_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_HOST as WAC_RECEIVE_HOST,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PORT as WAC_RECEIVE_PORT,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_USER as WAC_RECEIVE_USER,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_PASS as WAC_RECEIVE_PASS,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_SSL as WAC_RECEIVE_SSL,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK as WAC_DISK,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SIZE as WAC_DISK_SIZE,");
            sql.addSql("   WML_ACCOUNT.WAC_BIKO as WAC_BIKO,");
            sql.addSql("   WML_ACCOUNT.WAC_ORGANIZATION as WAC_ORGANIZATION,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN as WAC_SIGN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_POINT_KBN as WAC_SIGN_POINT_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_DSP_KBN as WAC_SIGN_DSP_KBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOTO as WAC_AUTOTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOCC as WAC_AUTOCC,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTOBCC as WAC_AUTOBCC,");
            sql.addSql("   WML_ACCOUNT.WAC_DELRECEIVE as WAC_DELRECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_RERECEIVE as WAC_RERECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_APOP as WAC_APOP,");
            sql.addSql("   WML_ACCOUNT.WAC_TOP_CMD as WAC_TOP_CMD,");
            sql.addSql("   WML_ACCOUNT.WAC_SMTP_AUTH as WAC_SMTP_AUTH,");
            sql.addSql("   WML_ACCOUNT.WAC_POPBSMTP as WAC_POPBSMTP,");
            sql.addSql("   WML_ACCOUNT.WAC_ENCODE_SEND as WAC_ENCODE_SEND,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE as WAC_AUTORECEIVE,");
            sql.addSql("   WML_ACCOUNT.WAC_SEND_MAILTYPE as WAC_SEND_MAILTYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_DATE as WAC_RECEIVE_DATE,");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN as WAC_JKBN,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_RECEIVE_TIME as WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WML_ACCOUNT.WAC_THEME as WAC_THEME,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_ADDRESS as WAC_CHECK_ADDRESS,");
            sql.addSql("   WML_ACCOUNT.WAC_CHECK_FILE as WAC_CHECK_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE as WAC_COMPRESS_FILE,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT as WAC_TIMESENT,");
            sql.addSql("   WML_ACCOUNT.WAC_QUOTES as WAC_QUOTES,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SPS as WAC_DISK_SPS,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE_AP as WAC_AUTORECEIVE_AP,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT_DEF as WAC_TIMESENT_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE_DEF as WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_AUTO as WAC_SIGN_AUTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE as WAC_AUTH_TYPE,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_SAVE_MINUTE as WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   WML_ACCOUNT.COT_SID as COT_SID,");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE as WRD_RECEIVE_DATE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT,");
            sql.addSql("   WML_ACCOUNT_RCVDATA");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            sql.addSql("   and WML_ACCOUNT.WAC_SID in (");
            sql.addSql("     select distinct WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("       from WML_ACCOUNT_USER ");
            sql.addSql("       left join CMN_BELONGM ");
            sql.addSql("         on WML_ACCOUNT_USER.GRP_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql("       left join CMN_USRM ");
            sql.addSql("         on WML_ACCOUNT_USER.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("         or CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       where CMN_USRM.USR_JKBN = 0 and CMN_USRM.USR_UKO_FLG = 0 ");
            sql.addSql("     union all ");
            sql.addSql("     select distinct WML_ACCOUNT_USER_PROXY.WAC_SID ");
            sql.addSql("       from WML_ACCOUNT_USER_PROXY ");
            sql.addSql("       left join CMN_USRM ");
            sql.addSql("         on WML_ACCOUNT_USER_PROXY.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("       where CMN_USRM.USR_JKBN = 0 and CMN_USRM.USR_UKO_FLG = 0 ");
            sql.addSql("    )");
            if (apNum > 0) {
                sql.addSql(" and");
                if (apNum == 1) {
                    sql.addSql("   coalesce(WML_ACCOUNT.WAC_AUTORECEIVE_AP, 1) in (0, 1)");
                } else {
                    sql.addSql("   coalesce(WML_ACCOUNT.WAC_AUTORECEIVE_AP, 1) = ?");
                    sql.addIntValue(apNum);
                }
            }
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_SID = WML_ACCOUNT_RCVDATA.WAC_SID");

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WmlAccountModel accountMdl = __getWmlAccountFromRs(rs);
                accountMdl.setWacReceiveDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
                ret.add(accountMdl);
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
     * <br>[機  能] 利用可能なアカウントのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> getExistAccountSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_JKBN = ?");

            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("WAC_SID"));
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
     * <p>Delete WML_ACCOUNT
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean WmlAccountModel
     * @param accountMode アカウント種別
     * @throws SQLException SQL実行例外
     */
    public void insertAccount(WmlAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_ACCOUNT_ID,");
            sql.addSql("   WAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ADDRESS,");
            sql.addSql("   WAC_SEND_HOST,");
            sql.addSql("   WAC_SEND_PORT,");
            sql.addSql("   WAC_SEND_USER,");
            sql.addSql("   WAC_SEND_PASS,");
            sql.addSql("   WAC_SEND_SSL,");
            sql.addSql("   WAC_RECEIVE_TYPE,");
            sql.addSql("   WAC_RECEIVE_HOST,");
            sql.addSql("   WAC_RECEIVE_PORT,");
            sql.addSql("   WAC_RECEIVE_USER,");
            sql.addSql("   WAC_RECEIVE_PASS,");
            sql.addSql("   WAC_RECEIVE_SSL,");
            sql.addSql("   WAC_DISK,");
            sql.addSql("   WAC_DISK_SIZE,");
            sql.addSql("   WAC_BIKO,");
            sql.addSql("   WAC_ORGANIZATION,");
            sql.addSql("   WAC_SIGN,");
            sql.addSql("   WAC_SIGN_POINT_KBN,");
            sql.addSql("   WAC_SIGN_DSP_KBN,");
            sql.addSql("   WAC_AUTOTO,");
            sql.addSql("   WAC_AUTOCC,");
            sql.addSql("   WAC_AUTOBCC,");
            sql.addSql("   WAC_DELRECEIVE,");
            sql.addSql("   WAC_RERECEIVE,");
            sql.addSql("   WAC_APOP,");
            sql.addSql("   WAC_TOP_CMD,");
            sql.addSql("   WAC_SMTP_AUTH,");
            sql.addSql("   WAC_POPBSMTP,");
            sql.addSql("   WAC_ENCODE_SEND,");
            sql.addSql("   WAC_AUTORECEIVE,");
            sql.addSql("   WAC_SEND_MAILTYPE,");
            sql.addSql("   WAC_RECEIVE_DATE,");
            sql.addSql("   WAC_JKBN,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAC_THEME,");
            sql.addSql("   WAC_CHECK_ADDRESS,");
            sql.addSql("   WAC_CHECK_FILE,");
            sql.addSql("   WAC_COMPRESS_FILE,");
            sql.addSql("   WAC_TIMESENT,");
            sql.addSql("   WAC_QUOTES,");
            sql.addSql("   WAC_DISK_SPS,");
            sql.addSql("   WAC_AUTORECEIVE_AP,");
            sql.addSql("   WAC_TIMESENT_DEF,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WAC_SIGN_AUTO,");
            sql.addSql("   WAC_AUTH_TYPE,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   COT_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   null,");
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
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWacAccountId());
            sql.addIntValue(bean.getWacType());
            //            if (accountMode != GSConstWebmail.WAC_TYPE_USER) {
            //                sql.addValue(bean.getUsrSid());
            //            }
            sql.addStrValue(bean.getWacName());
            sql.addStrValue(bean.getWacAddress());
            sql.addStrValue(bean.getWacSendHost());
            sql.addIntValue(bean.getWacSendPort());
            sql.addStrValue(bean.getWacSendUser());
            sql.addStrValue(bean.getWacSendPass());

            sql.addIntValue(bean.getWacSendSsl());

            if (bean.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                sql.addIntValue(GSConstWebmail.WAC_RECEIVE_TYPE_IMAP);
            } else if (bean.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_NORMAL) {
                sql.addIntValue(GSConstWebmail.WAC_RECEIVE_TYPE_POP);
            }

            sql.addStrValue(bean.getWacReceiveHost());
            sql.addIntValue(bean.getWacReceivePort());
            sql.addStrValue(bean.getWacReceiveUser());
            sql.addStrValue(bean.getWacReceivePass());
            sql.addIntValue(bean.getWacReceiveSsl());
            sql.addIntValue(bean.getWacDisk());
            sql.addIntValue(bean.getWacDiskSize());
            sql.addStrValue(bean.getWacBiko());
            sql.addStrValue(bean.getWacOrganization());
            sql.addStrValue(bean.getWacSign());
            sql.addIntValue(bean.getWacSignPointKbn());
            sql.addIntValue(bean.getWacSignDspKbn());
            sql.addStrValue(bean.getWacAutoto());
            sql.addStrValue(bean.getWacAutocc());
            sql.addStrValue(bean.getWacAutobcc());
            sql.addIntValue(bean.getWacDelreceive());
            sql.addIntValue(bean.getWacRereceive());
            sql.addIntValue(bean.getWacApop());
            sql.addIntValue(bean.getWacTopCmd());
            sql.addIntValue(bean.getWacSmtpAuth());
            sql.addIntValue(bean.getWacPopbsmtp());
            sql.addIntValue(bean.getWacEncodeSend());
            sql.addIntValue(bean.getWacAutoreceive());
            sql.addIntValue(bean.getWacSendMailtype());
            sql.addDateValue(bean.getWacReceiveDate());
            sql.addIntValue(bean.getWacJkbn());
            sql.addIntValue(bean.getWacAutoReceiveTime());
            sql.addIntValue(bean.getWacTheme());
            sql.addIntValue(bean.getWacCheckAddress());
            sql.addIntValue(bean.getWacCheckFile());
            sql.addIntValue(bean.getWacCompressFile());
            sql.addIntValue(bean.getWacTimesent());
            sql.addIntValue(bean.getWacQuotes());
            sql.addIntValue(bean.getWacDiskSps());
            sql.addIntValue(bean.getWacAutoreceiveAp());
            sql.addIntValue(bean.getWacTimesentDef());
            sql.addIntValue(bean.getWacCompressFileDef());
            sql.addIntValue(bean.getWacSignAuto());
            sql.addIntValue(bean.getWacAuthType());

            if (String.valueOf(bean.getWacAutoSaveMinute()).equals(GSConstWebmail.CSV_MINUTE_VALUE[6])) {
                sql.addIntValue(Integer.parseInt(GSConstWebmail.MINUTE_VALUE[6]));
            } else {
                sql.addIntValue(bean.getWacAutoSaveMinute());
            }

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
     * <br>[機  能] アカウント情報の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean WmlAccountModel
     * @param accountMode アカウントモード
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccount(WmlAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_ACCOUNT_ID=?,");
            sql.addSql("   WAC_NAME=?,");
            //アカウント処理モード 共通
            if (accountMode == GSConstWebmail.ACCOUNTMODE_COMMON) {
                sql.addSql("   WAC_TYPE=?,");
            }
            sql.addSql("   WAC_ADDRESS=?,");
            sql.addSql("   WAC_SEND_HOST=?,");
            sql.addSql("   WAC_SEND_PORT=?,");
            sql.addSql("   WAC_SEND_USER=?,");
            sql.addSql("   WAC_SEND_PASS=?,");
            sql.addSql("   WAC_SEND_SSL=?,");
            sql.addSql("   WAC_RECEIVE_TYPE=?,");
            sql.addSql("   WAC_RECEIVE_HOST=?,");
            sql.addSql("   WAC_RECEIVE_PORT=?,");
            sql.addSql("   WAC_RECEIVE_USER=?,");
            sql.addSql("   WAC_RECEIVE_PASS=?,");
            sql.addSql("   WAC_RECEIVE_SSL=?,");
            sql.addSql("   WAC_DISK=?,");
            sql.addSql("   WAC_DISK_SIZE=?,");
            sql.addSql("   WAC_BIKO=?,");
            sql.addSql("   WAC_ORGANIZATION=?,");
            sql.addSql("   WAC_SIGN=?,");
            sql.addSql("   WAC_SIGN_POINT_KBN=?,");
            sql.addSql("   WAC_SIGN_DSP_KBN=?,");
            sql.addSql("   WAC_AUTOTO=?,");
            sql.addSql("   WAC_AUTOCC=?,");
            sql.addSql("   WAC_AUTOBCC=?,");
            sql.addSql("   WAC_DELRECEIVE=?,");
            sql.addSql("   WAC_RERECEIVE=?,");
            sql.addSql("   WAC_APOP=?,");
            sql.addSql("   WAC_TOP_CMD=?,");
            sql.addSql("   WAC_SMTP_AUTH=?,");
            sql.addSql("   WAC_POPBSMTP=?,");
            sql.addSql("   WAC_ENCODE_SEND=?,");
            sql.addSql("   WAC_AUTORECEIVE=?,");
            sql.addSql("   WAC_SEND_MAILTYPE=?,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME=?,");
            sql.addSql("   WAC_THEME=?,");
            sql.addSql("   WAC_CHECK_ADDRESS=?,");
            sql.addSql("   WAC_CHECK_FILE=?,");
            sql.addSql("   WAC_COMPRESS_FILE=?,");
            sql.addSql("   WAC_TIMESENT=?,");
            sql.addSql("   WAC_QUOTES=?,");
            sql.addSql("   WAC_AUTORECEIVE_AP=?,");
            sql.addSql("   WAC_TIMESENT_DEF=?,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF=?,");
            sql.addSql("   WAC_SIGN_AUTO=?,");
            sql.addSql("   WAC_DISK_SPS=?,");
            sql.addSql("   WAC_AUTH_TYPE=?,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE=?,");
            sql.addSql("   COT_SID=?");

            //"自動受信 受信実行サーバ"は更新対象から除外
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWacAccountId());
            sql.addStrValue(bean.getWacName());
            //アカウント処理モード 共通
            if (accountMode == GSConstWebmail.ACCOUNTMODE_COMMON) {
                sql.addIntValue(bean.getWacType());
            }
            sql.addStrValue(bean.getWacAddress());
            sql.addStrValue(bean.getWacSendHost());
            sql.addIntValue(bean.getWacSendPort());
            sql.addStrValue(bean.getWacSendUser());
            sql.addStrValue(bean.getWacSendPass());
            sql.addIntValue(bean.getWacSendSsl());

            if (bean.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                sql.addIntValue(GSConstWebmail.WAC_RECEIVE_TYPE_IMAP);
            } else if (bean.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_NORMAL) {
                sql.addIntValue(GSConstWebmail.WAC_RECEIVE_TYPE_POP);
            }

            sql.addStrValue(bean.getWacReceiveHost());
            sql.addIntValue(bean.getWacReceivePort());
            sql.addStrValue(bean.getWacReceiveUser());
            sql.addStrValue(bean.getWacReceivePass());

            sql.addIntValue(bean.getWacReceiveSsl());
            sql.addIntValue(bean.getWacDisk());
            sql.addIntValue(bean.getWacDiskSize());
            sql.addStrValue(bean.getWacBiko());
            sql.addStrValue(bean.getWacOrganization());
            sql.addStrValue(bean.getWacSign());
            sql.addIntValue(bean.getWacSignPointKbn());
            sql.addIntValue(bean.getWacSignDspKbn());
            sql.addStrValue(bean.getWacAutoto());
            sql.addStrValue(bean.getWacAutocc());
            sql.addStrValue(bean.getWacAutobcc());
            sql.addIntValue(bean.getWacDelreceive());
            sql.addIntValue(bean.getWacRereceive());
            sql.addIntValue(bean.getWacApop());
            sql.addIntValue(bean.getWacTopCmd());
            sql.addIntValue(bean.getWacSmtpAuth());
            sql.addIntValue(bean.getWacPopbsmtp());
            sql.addIntValue(bean.getWacEncodeSend());
            sql.addIntValue(bean.getWacAutoreceive());
            sql.addIntValue(bean.getWacSendMailtype());
            sql.addIntValue(bean.getWacAutoReceiveTime());
            sql.addIntValue(bean.getWacTheme());
            sql.addIntValue(bean.getWacCheckAddress());
            sql.addIntValue(bean.getWacCheckFile());
            sql.addIntValue(bean.getWacCompressFile());
            sql.addIntValue(bean.getWacTimesent());
            sql.addIntValue(bean.getWacQuotes());
            sql.addIntValue(bean.getWacDiskSps());
            sql.addIntValue(bean.getWacTimesentDef());
            sql.addIntValue(bean.getWacCompressFileDef());
            sql.addIntValue(bean.getWacSignAuto());
            sql.addIntValue(bean.getWacDiskSps());
            sql.addIntValue(bean.getWacAuthType());

            if (String.valueOf(bean.getWacAutoSaveMinute()).equals(GSConstWebmail.CSV_MINUTE_VALUE[6])) {
                sql.addIntValue(Integer.parseInt(GSConstWebmail.MINUTE_VALUE[6]));
            } else {
                sql.addIntValue(bean.getWacAutoSaveMinute());
            }

            sql.addIntValue(bean.getCotSid());

            //where
            sql.addIntValue(bean.getWacSid());

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
     * <br>[機  能] アカウント情報の変更を行う(アカウント編集画面)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean WmlAccountModel
     * @param accountMode アカウントモード
     * @param settingServer サーバ情報の設定
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccountEdit(WmlAccountModel bean, int accountMode, int settingServer)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_NAME=?,");
            sql.addStrValue(bean.getWacName());

            //サーバー情報の設定 = 許可する の場合、受信サーバ・送信サーバ情報を更新する
            if (settingServer == GSConstWebmail.WAD_SETTING_SERVER_YES) {
                sql.addSql("   WAC_SEND_HOST=?,");
                sql.addSql("   WAC_SEND_PORT=?,");
                sql.addSql("   WAC_SEND_USER=?,");
                sql.addSql("   WAC_SEND_PASS=?,");
                sql.addSql("   WAC_SEND_SSL=?,");
                sql.addSql("   WAC_RECEIVE_TYPE=?,");
                sql.addSql("   WAC_RECEIVE_HOST=?,");
                sql.addSql("   WAC_RECEIVE_PORT=?,");
                sql.addSql("   WAC_RECEIVE_USER=?,");
                sql.addSql("   WAC_RECEIVE_PASS=?,");
                sql.addSql("   WAC_RECEIVE_SSL=?,");
                sql.addSql("   WAC_SMTP_AUTH=?,");
                sql.addSql("   COT_SID=?,");
                sql.addStrValue(bean.getWacSendHost());
                sql.addIntValue(bean.getWacSendPort());
                sql.addStrValue(bean.getWacSendUser());
                sql.addStrValue(bean.getWacSendPass());
                sql.addIntValue(bean.getWacSendSsl());
                sql.addIntValue(bean.getWacReceiveType());
                sql.addStrValue(bean.getWacReceiveHost());
                sql.addIntValue(bean.getWacReceivePort());
                sql.addStrValue(bean.getWacReceiveUser());
                sql.addStrValue(bean.getWacReceivePass());
                sql.addIntValue(bean.getWacReceiveSsl());
                sql.addIntValue(bean.getWacSmtpAuth());
                sql.addIntValue(bean.getCotSid());
            }

            //アカウント処理モード 共通
            if (accountMode == GSConstWebmail.ACCOUNTMODE_COMMON) {
                sql.addSql("   WAC_TYPE=?,");
                sql.addIntValue(bean.getWacType());
            }

            sql.addSql("   WAC_SIGN=?,");
            sql.addSql("   WAC_AUTOTO=?,");
            sql.addSql("   WAC_AUTOCC=?,");
            sql.addSql("   WAC_AUTOBCC=?,");
            sql.addSql("   WAC_THEME=?,");
            sql.addSql("   WAC_QUOTES=?,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE=?,");
            sql.addSql("   WAC_SIGN_AUTO=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addStrValue(bean.getWacSign());
            sql.addStrValue(bean.getWacAutoto());
            sql.addStrValue(bean.getWacAutocc());
            sql.addStrValue(bean.getWacAutobcc());
            sql.addIntValue(bean.getWacTheme());
            sql.addIntValue(bean.getWacQuotes());
            sql.addIntValue(bean.getWacAutoSaveMinute());
            sql.addIntValue(bean.getWacSignAuto());
            sql.addIntValue(bean.getWacSid());

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

    /**
     * <br>[機  能] 受信日時の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param receiveDate 受信日時
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int updateReceiveDate(int wacSid, UDate receiveDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_RECEIVE_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            sql.addDateValue(receiveDate);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(int wacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            sql.addIntValue(jkbn);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbnByAddress(String address, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_ADDRESS=?");

            sql.addIntValue(jkbn);
            sql.addStrValue(address);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSidList アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(String[] wacSidList, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   WAC_JKBN=?");
            sql.addIntValue(jkbn);
            sql.addSql(" where ");
            sql.addSql("   WAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(wacSidList[0]));
            for (String accountSid : wacSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したアカウントIDと同じアカウントIDが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacUid アカウントID
     * @param ownWacSid アカウントSID(自アカウントを除外するために使用)
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行エラー
     */
    public boolean existAccount(String wacUid, int ownWacSid) throws SQLException {
        int wacSid = getAccountSid(wacUid);
        return wacSid > 0 && wacSid != ownWacSid;
    }

    /**
     * <br>[機  能] 指定した受信サーバユーザ名が登録されたアカウントが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsvUserName 受信サーバ ユーザ名
     * @param ownWacSid アカウントSID(自アカウントを除外するために使用)
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行エラー
     */
    public boolean existRcvUser(String rsvUserName, int ownWacSid) throws SQLException {
        List<Integer> wacSidList = getAccountSidByRsvUser(rsvUserName);
        return wacSidList != null && !wacSidList.isEmpty();
    }

    /**
     * <br>[機  能] 指定したメールアドレスが登録されたアカウントが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param ownWacSid アカウントSID(自アカウントを除外するために使用)
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行エラー
     */
    public boolean existAddress(String address, int ownWacSid) throws SQLException {
        List<Integer> wacSidList = getAccountSidByAddress(address);
        return wacSidList != null && !wacSidList.isEmpty();
    }

    /**
     * <br>[機  能] 指定したアカウントIDのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountId アカウントID
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public int getAccountSid(String accountId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int wacSid = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WAC_SID from WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_ACCOUNT_ID = ?");
            sql.addSql(" and ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   WAC_SID asc");
            sql.addStrValue(accountId);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wacSid = rs.getInt("WAC_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wacSid;
    }

    /**
     * <br>[機  能] 指定したアカウント名のアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsvUserName 受信サーバ ユーザ名
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> getAccountSidByRsvUser(String rsvUserName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> wacSidList = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WAC_SID from WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_RECEIVE_USER = ?");
            sql.addSql(" and ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   WAC_SID asc");
            sql.addStrValue(rsvUserName);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                wacSidList.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wacSidList;
    }

    /**
     * <br>[機  能] 指定したアカウント名のアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> getAccountSidByAddress(String address) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> wacSidList = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WAC_SID from WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_ADDRESS = ?");
            sql.addSql(" and ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   WAC_SID asc");
            sql.addStrValue(address);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                wacSidList.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wacSidList;
    }

    /**
     * <br>[機  能] アカウント情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @param proxyUserFlg true: アカウント代理人を許可する false: アカウント代理人を許可しない
     * @return SqlBuffer
     */
    private SqlBuffer __setAccountSearchSql(SqlBuffer sql, int userSid, boolean proxyUserFlg) {
        sql.addSql(" from ");
        sql.addSql("   WML_ACCOUNT");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("       select WAC_SID, WAS_SORT");
        sql.addSql("       from WML_ACCOUNT_SORT");
        sql.addSql("       where USR_SID = ?");
        sql.addSql("     ) ACCOUNT_SORT");
        sql.addSql("   on");
        sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID");
        sql.addIntValue(userSid);

        WmlDao wmlDao = new WmlDao();
        sql = wmlDao.setAccountSearchSql(sql, userSid, proxyUserFlg);

        return sql;
    }

    /**
     * <br>[機  能] UIDL削除対象アカウントの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param limit 取得件数
     * @return アカウント情報一覧
     * @throws SQLException SQL実行エラー
     */
    public List<WmlAccountModel> getAccountDataForDelUidl(int wacSid, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WmlAccountModel> ret = new ArrayList<WmlAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_ACCOUNT_ID,");
            sql.addSql("   WAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ADDRESS,");
            sql.addSql("   WAC_SEND_HOST,");
            sql.addSql("   WAC_SEND_PORT,");
            sql.addSql("   WAC_SEND_USER,");
            sql.addSql("   WAC_SEND_PASS,");
            sql.addSql("   WAC_SEND_SSL,");
            sql.addSql("   WAC_RECEIVE_TYPE,");
            sql.addSql("   WAC_RECEIVE_HOST,");
            sql.addSql("   WAC_RECEIVE_PORT,");
            sql.addSql("   WAC_RECEIVE_USER,");
            sql.addSql("   WAC_RECEIVE_PASS,");
            sql.addSql("   WAC_RECEIVE_SSL,");
            sql.addSql("   WAC_DISK,");
            sql.addSql("   WAC_DISK_SIZE,");
            sql.addSql("   WAC_BIKO,");
            sql.addSql("   WAC_ORGANIZATION,");
            sql.addSql("   WAC_SIGN,");
            sql.addSql("   WAC_SIGN_POINT_KBN,");
            sql.addSql("   WAC_SIGN_DSP_KBN,");
            sql.addSql("   WAC_AUTOTO,");
            sql.addSql("   WAC_AUTOCC,");
            sql.addSql("   WAC_AUTOBCC,");
            sql.addSql("   WAC_DELRECEIVE,");
            sql.addSql("   WAC_RERECEIVE,");
            sql.addSql("   WAC_APOP,");
            sql.addSql("   WAC_TOP_CMD,");
            sql.addSql("   WAC_SMTP_AUTH,");
            sql.addSql("   WAC_POPBSMTP,");
            sql.addSql("   WAC_ENCODE_SEND,");
            sql.addSql("   WAC_AUTORECEIVE,");
            sql.addSql("   WAC_SEND_MAILTYPE,");
            sql.addSql("   WAC_RECEIVE_DATE,");
            sql.addSql("   WAC_JKBN,");
            sql.addSql("   WAC_AUTO_RECEIVE_TIME,");
            sql.addSql("   WAC_THEME,");
            sql.addSql("   WAC_CHECK_ADDRESS,");
            sql.addSql("   WAC_CHECK_FILE,");
            sql.addSql("   WAC_COMPRESS_FILE,");
            sql.addSql("   WAC_TIMESENT,");
            sql.addSql("   WAC_QUOTES,");
            sql.addSql("   WAC_DISK_SPS,");
            sql.addSql("   WAC_AUTORECEIVE_AP,");
            sql.addSql("   WAC_TIMESENT_DEF,");
            sql.addSql("   WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WAC_SIGN_AUTO,");
            sql.addSql("   WAC_AUTH_TYPE,");
            sql.addSql("   WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   COT_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            if (wacSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   WAC_SID >  ?");
                sql.addIntValue(wacSid);
            }

            sql.addSql(" order by");
            sql.addSql("   WAC_SID asc");

            sql.setPagingValue(0, limit);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WmlAccountModel accountMdl = __getWmlAccountFromRs(rs);
                ret.add(accountMdl);
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
     * <p>Create WML_ACCOUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountModel __getWmlAccountFromRs(ResultSet rs) throws SQLException {
        WmlAccountModel bean = new WmlAccountModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWacAccountId(rs.getString("WAC_ACCOUNT_ID"));
        bean.setWacType(rs.getInt("WAC_TYPE"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWacName(rs.getString("WAC_NAME"));
        bean.setWacAddress(rs.getString("WAC_ADDRESS"));
        bean.setWacSendHost(rs.getString("WAC_SEND_HOST"));
        bean.setWacSendPort(rs.getInt("WAC_SEND_PORT"));
        bean.setWacSendUser(rs.getString("WAC_SEND_USER"));
        bean.setWacSendPass(rs.getString("WAC_SEND_PASS"));
        bean.setWacSendSsl(rs.getInt("WAC_SEND_SSL"));
        bean.setWacReceiveType(rs.getInt("WAC_RECEIVE_TYPE"));
        bean.setWacReceiveHost(rs.getString("WAC_RECEIVE_HOST"));
        bean.setWacReceivePort(rs.getInt("WAC_RECEIVE_PORT"));
        bean.setWacReceiveUser(rs.getString("WAC_RECEIVE_USER"));
        bean.setWacReceivePass(rs.getString("WAC_RECEIVE_PASS"));
        bean.setWacReceiveSsl(rs.getInt("WAC_RECEIVE_SSL"));
        bean.setWacDisk(rs.getInt("WAC_DISK"));
        bean.setWacDiskSize(rs.getInt("WAC_DISK_SIZE"));
        bean.setWacBiko(rs.getString("WAC_BIKO"));
        bean.setWacOrganization(rs.getString("WAC_ORGANIZATION"));
        bean.setWacSign(rs.getString("WAC_SIGN"));
        bean.setWacSignPointKbn(rs.getInt("WAC_SIGN_POINT_KBN"));
        bean.setWacSignDspKbn(rs.getInt("WAC_SIGN_DSP_KBN"));
        bean.setWacAutoto(rs.getString("WAC_AUTOTO"));
        bean.setWacAutocc(rs.getString("WAC_AUTOCC"));
        bean.setWacAutobcc(rs.getString("WAC_AUTOBCC"));
        bean.setWacDelreceive(rs.getInt("WAC_DELRECEIVE"));
        bean.setWacRereceive(rs.getInt("WAC_RERECEIVE"));
        bean.setWacApop(rs.getInt("WAC_APOP"));
        bean.setWacTopCmd(rs.getInt("WAC_TOP_CMD"));
        bean.setWacSmtpAuth(rs.getInt("WAC_SMTP_AUTH"));
        bean.setWacPopbsmtp(rs.getInt("WAC_POPBSMTP"));
        bean.setWacEncodeSend(rs.getInt("WAC_ENCODE_SEND"));
        bean.setWacAutoreceive(rs.getInt("WAC_AUTORECEIVE"));
        bean.setWacSendMailtype(rs.getInt("WAC_SEND_MAILTYPE"));
        bean.setWacReceiveDate(UDate.getInstanceTimestamp(rs.getTimestamp("WAC_RECEIVE_DATE")));
        bean.setWacJkbn(rs.getInt("WAC_JKBN"));
        bean.setWacAutoReceiveTime(rs.getInt("WAC_AUTO_RECEIVE_TIME"));
        bean.setWacTheme(rs.getInt("WAC_THEME"));
        bean.setWacCheckAddress(rs.getInt("WAC_CHECK_ADDRESS"));
        bean.setWacCheckFile(rs.getInt("WAC_CHECK_FILE"));
        bean.setWacCompressFile(rs.getInt("WAC_COMPRESS_FILE"));
        bean.setWacTimesent(rs.getInt("WAC_TIMESENT"));
        bean.setWacQuotes(rs.getInt("WAC_QUOTES"));
        bean.setWacDiskSps(rs.getInt("WAC_DISK_SPS"));
        bean.setWacAutoreceiveAp(rs.getInt("WAC_AUTORECEIVE_AP"));
        bean.setWacTimesentDef(rs.getInt("WAC_TIMESENT_DEF"));
        bean.setWacCompressFileDef(rs.getInt("WAC_COMPRESS_FILE_DEF"));
        bean.setWacSignAuto(rs.getInt("WAC_SIGN_AUTO"));
        bean.setWacAuthType(rs.getInt("WAC_AUTH_TYPE"));
        bean.setWacAutoSaveMinute(rs.getInt("WAC_AUTO_SAVE_MINUTE"));
        bean.setCotSid(rs.getInt("COT_SID"));
        return bean;
    }
}
