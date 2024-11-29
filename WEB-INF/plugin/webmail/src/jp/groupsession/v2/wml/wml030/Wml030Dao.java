package jp.groupsession.v2.wml.wml030;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;

/**
 * <br>[機  能] WEBメール アカウントマネージャー画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml030Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml030Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml030Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return アカウント情報の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Wml030AccountModel> getAccountList(Wml030SearchModel searchMdl,
            RequestModel reqMdl)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Wml030AccountModel> ret = new ArrayList<Wml030AccountModel>();
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
            sql.addSql("   WML_ACCOUNT.WAC_DISK as WAC_DISK,");
            sql.addSql("   WML_ACCOUNT.WAC_BIKO as WAC_BIKO,");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE as WRD_RECEIVE_DATE,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE as WAC_AUTH_TYPE,");
            sql.addSql("   WML_ACCOUNT.COT_SID as COT_SID,");
            sql.addSql("   ACCOUNT_USER_COUNT.DNT_COUNT as DNT_COUNT,");
            sql.addSql("   ACCOUNT_USER_COUNT.USR_COUNT as USR_COUNT,");
            sql.addSql("   WML_ACCOUNT_DISK.WDS_SIZE as WDS_SIZE,");
            sql.addSql("   CMN_OAUTH_TOKEN.COU_SID as COU_SID,");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_AUTH_RTOKEN as COT_AUTH_RTOKEN,");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_ACCOUNTID as COT_ACCOUNTID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVDATA,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     WML_ACCOUNT_DISK");
            sql.addSql("   on ");
            sql.addSql("     WML_ACCOUNT.WAC_SID = WML_ACCOUNT_DISK.WAC_SID");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         WAC_SID,");
            sql.addSql("         count(GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         WML_ACCOUNT_USER");
            sql.addSql("       group by");
            sql.addSql("         WAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_USER_COUNT.WAC_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_OAUTH_TOKEN");
            sql.addSql("   on ");
            sql.addSql("     WML_ACCOUNT.COT_SID = CMN_OAUTH_TOKEN.COT_SID");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            sql = __setSqlOrder(sql, searchMdl);

            //limit, offset
            int start = 0;
            if (searchMdl.getPage() > 1) {
                start = searchMdl.getPage() - 1;
            }
            
            sql.setPagingValue(start, searchMdl.getMaxCount());


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Wml030AccountModel model = new Wml030AccountModel(reqMdl);
                model.setAccountSid(rs.getInt("WAC_SID"));
                model.setAccountUid(rs.getString("WAC_ACCOUNT_ID"));
                model.setAccountName(rs.getString("WAC_NAME"));
                model.setMailAddress(rs.getString("WAC_ADDRESS"));
                model.setAccountUserKbn(rs.getInt("WAC_TYPE"));
                model.setDiskType(rs.getInt("WAC_DISK"));
                model.setReceiveDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
                model.setAuthType(rs.getInt("WAC_AUTH_TYPE"));
                model.setCotSid(rs.getInt("COT_SID"));

                //BからMBへ変換
                model.setDiskSizeUse(
                        rs.getBigDecimal("WDS_SIZE").divide(
                                new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP));

                model.setBiko(rs.getString("WAC_BIKO"));
                if (model.getAccountUserKbn() == GSConstWebmail.WAC_TYPE_GROUP) {
                    model.setAccountUserCount(rs.getInt("DNT_COUNT"));
                } else if (model.getAccountUserKbn() == GSConstWebmail.WAC_TYPE_USER) {
                    model.setAccountUserCount(rs.getInt("USR_COUNT"));
                }

                model.setCouSid(rs.getInt("COU_SID"));
                model.setRefreshToken(rs.getString("COT_AUTH_RTOKEN"));
                model.setAccountId(rs.getString("COT_ACCOUNTID"));
                model.setOauthCertifiedFlg(
                        model.getRefreshToken() != null);

                ret.add(model);
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
     * <br>[機  能] エクスポート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @return エクスポート情報
     * @throws SQLException SQL実行時例外
     */
    public List<Wml030ExportModel> getExportData(Wml030SearchModel searchMdl)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Wml030ExportModel> ret = new ArrayList<Wml030ExportModel>();
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
            sql.addSql("   WML_ACCOUNT.WAC_SEND_PORT as WAC_SEND_PORT ,");
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
            sql.addSql("   WML_ACCOUNT.WAC_COMPRESS_FILE_DEF as WAC_COMPRESS_FILE_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT as WAC_TIMESENT,");
            sql.addSql("   WML_ACCOUNT.WAC_TIMESENT_DEF as WAC_TIMESENT_DEF,");
            sql.addSql("   WML_ACCOUNT.WAC_QUOTES as WAC_QUOTES,");
            sql.addSql("   WML_ACCOUNT.WAC_DISK_SPS as WAC_DISK_SPS,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTORECEIVE_AP as WAC_AUTORECEIVE_AP,");
            sql.addSql("   WML_ACCOUNT.WAC_SIGN_AUTO as WAC_SIGN_AUTO,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE as WAC_AUTH_TYPE,");
            sql.addSql("   CMN_OAUTH.COU_PROVIDER as COU_PROVIDER,");
            sql.addSql("   WML_ACCOUNT.WAC_AUTO_SAVE_MINUTE as WAC_AUTO_SAVE_MINUTE,");
            sql.addSql("   WML_ACCOUNT.COT_SID as COT_SID,");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE as WRD_RECEIVE_DATE,");
            sql.addSql("   ACCOUNT_USER_COUNT.DNT_COUNT as DNT_COUNT,");
            sql.addSql("   ACCOUNT_USER_COUNT.USR_COUNT as USR_COUNT,");
            sql.addSql("   WML_ACCOUNT_DISK.WDS_SIZE as WDS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVDATA,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     CMN_OAUTH_TOKEN");
            sql.addSql("   on ");
            sql.addSql("     WML_ACCOUNT.COT_SID = CMN_OAUTH_TOKEN.COT_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_OAUTH");
            sql.addSql("   on ");
            sql.addSql("     CMN_OAUTH_TOKEN.COU_SID = CMN_OAUTH.COU_SID");
            sql.addSql("   left join");
            sql.addSql("     WML_ACCOUNT_DISK");
            sql.addSql("   on ");
            sql.addSql("     WML_ACCOUNT.WAC_SID = WML_ACCOUNT_DISK.WAC_SID");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         WAC_SID,");
            sql.addSql("         count(GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         WML_ACCOUNT_USER");
            sql.addSql("       group by");
            sql.addSql("         WAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_USER_COUNT.WAC_SID");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            sql = __setSqlOrder(sql, searchMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Wml030ExportModel model = new Wml030ExportModel();
                WmlAccountModel accountData = new WmlAccountModel();
                CmnOauthModel oauthData = new CmnOauthModel();
                accountData.setWacSid(rs.getInt("WAC_SID"));
                accountData.setWacAccountId(rs.getString("WAC_ACCOUNT_ID"));
                accountData.setWacType(rs.getInt("WAC_TYPE"));
                accountData.setUsrSid(rs.getInt("USR_SID"));
                accountData.setWacName(rs.getString("WAC_NAME"));
                accountData.setWacAddress(rs.getString("WAC_ADDRESS"));
                accountData.setWacSendHost(rs.getString("WAC_SEND_HOST"));
                accountData.setWacSendPort(rs.getInt("WAC_SEND_PORT"));
                accountData.setWacSendUser(rs.getString("WAC_SEND_USER"));
                accountData.setWacSendPass(rs.getString("WAC_SEND_PASS"));
                accountData.setWacSendSsl(rs.getInt("WAC_SEND_SSL"));
                accountData.setWacReceiveType(rs.getInt("WAC_RECEIVE_TYPE"));
                accountData.setWacReceiveHost(rs.getString("WAC_RECEIVE_HOST"));
                accountData.setWacReceivePort(rs.getInt("WAC_RECEIVE_PORT"));
                accountData.setWacReceiveUser(rs.getString("WAC_RECEIVE_USER"));
                accountData.setWacReceivePass(rs.getString("WAC_RECEIVE_PASS"));
                accountData.setWacReceiveSsl(rs.getInt("WAC_RECEIVE_SSL"));
                accountData.setWacDisk(rs.getInt("WAC_DISK"));
                accountData.setWacDiskSize(rs.getInt("WAC_DISK_SIZE"));
                accountData.setWacBiko(rs.getString("WAC_BIKO"));
                accountData.setWacOrganization(rs.getString("WAC_ORGANIZATION"));
                accountData.setWacSign(rs.getString("WAC_SIGN"));
                accountData.setWacSignPointKbn(rs.getInt("WAC_SIGN_POINT_KBN"));
                accountData.setWacSignDspKbn(rs.getInt("WAC_SIGN_DSP_KBN"));
                accountData.setWacAutoto(rs.getString("WAC_AUTOTO"));
                accountData.setWacAutocc(rs.getString("WAC_AUTOCC"));
                accountData.setWacAutobcc(rs.getString("WAC_AUTOBCC"));
                accountData.setWacDelreceive(rs.getInt("WAC_DELRECEIVE"));
                accountData.setWacRereceive(rs.getInt("WAC_RERECEIVE"));
                accountData.setWacApop(rs.getInt("WAC_APOP"));
                accountData.setWacTopCmd(rs.getInt("WAC_TOP_CMD"));
                accountData.setWacSmtpAuth(rs.getInt("WAC_SMTP_AUTH"));
                accountData.setWacPopbsmtp(rs.getInt("WAC_POPBSMTP"));
                accountData.setWacEncodeSend(rs.getInt("WAC_ENCODE_SEND"));
                accountData.setWacAutoreceive(rs.getInt("WAC_AUTORECEIVE"));
                accountData.setWacSendMailtype(rs.getInt("WAC_SEND_MAILTYPE"));
                accountData.setWacReceiveDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
                accountData.setWacJkbn(rs.getInt("WAC_JKBN"));
                accountData.setWacAutoReceiveTime(rs.getInt("WAC_AUTO_RECEIVE_TIME"));
                accountData.setWacTheme(rs.getInt("WAC_THEME"));
                accountData.setWacCheckAddress(rs.getInt("WAC_CHECK_ADDRESS"));
                accountData.setWacCheckFile(rs.getInt("WAC_CHECK_FILE"));
                accountData.setWacCompressFile(rs.getInt("WAC_COMPRESS_FILE"));
                accountData.setWacCompressFileDef(rs.getInt("WAC_COMPRESS_FILE_DEF"));
                accountData.setWacTimesent(rs.getInt("WAC_TIMESENT"));
                accountData.setWacTimesentDef(rs.getInt("WAC_TIMESENT_DEF"));
                accountData.setWacQuotes(rs.getInt("WAC_QUOTES"));
                accountData.setWacDiskSps(rs.getInt("WAC_DISK_SPS"));
                accountData.setWacAutoreceiveAp(rs.getInt("WAC_AUTORECEIVE_AP"));
                accountData.setWacSignAuto(rs.getInt("WAC_SIGN_AUTO"));
                accountData.setWacAuthType(rs.getInt("WAC_AUTH_TYPE"));
                oauthData.setCouProvider(rs.getInt("COU_PROVIDER"));
                accountData.setWacAutoSaveMinute(rs.getInt("WAC_AUTO_SAVE_MINUTE"));
                accountData.setCotSid(rs.getInt("COT_SID"));
                model.setAccountData(accountData);

                ret.add(model);
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
     * <br>[機  能] アカウント 使用者(ユーザ)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return アカウント 使用者(ユーザ)
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccountUseUser(int wacSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where");
            sql.addSql("   WML_ACCOUNT_USER.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT_USER.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" order by");
            sql.addSql("   YAKUSYOKU_EXIST asc,");
            sql.addSql("   YAKUSYOKU_SORT asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("USR_LGID"));
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
     * <br>[機  能] アカウント 使用者(グループ)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return アカウント 使用者(ユーザ)
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccountUseGroup(int wacSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_GROUPM.GRP_ID as GRP_ID");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM,");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where");
            sql.addSql("   WML_ACCOUNT_USER.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT_USER.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_GROUPM.GRP_NAME asc");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("GRP_ID"));
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
     * <br>[機  能] アカウント 代理人を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return アカウント 使用者(ユーザ)
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccountProxyUser(int wacSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select");
            sql.addSql("  CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("  (case");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("     else 0");
            sql.addSql("   end) as YAKUSYOKU_EXIST,");
            sql.addSql("  (case");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("     else (select");
            sql.addSql("             POS_SORT");
            sql.addSql("           from");
            sql.addSql("             CMN_POSITION");
            sql.addSql("           where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("   end) as YAKUSYOKU_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   WML_ACCOUNT_USER_PROXY");
            sql.addSql(" where");
            sql.addSql("   WML_ACCOUNT_USER_PROXY.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT_USER_PROXY.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" order by");
            sql.addSql("   YAKUSYOKU_EXIST asc,");
            sql.addSql("   YAKUSYOKU_SORT asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("USR_LGID"));
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
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  searchMdl 検索パラメータモデル
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int recordCount(Wml030SearchModel searchMdl) throws SQLException {
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
            sql.addSql("   WML_ACCOUNT,");
            sql.addSql("   WML_ACCOUNT_RCVDATA");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

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
     * <br>[機  能] SqlBufferに検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Wml030SearchModel searchMdl) {
        sql.addSql(" where");
        sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
        sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

        if (searchMdl.existSearchCondition()) {

            //キーワード
            if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
                sql.addSql(" and");
                sql.addSql("   WML_ACCOUNT.WAC_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }

            //グループSID
            if (searchMdl.getGrpSid() > 0 && searchMdl.getUserSid() <= 0) {
                sql.addSql(" and");
                sql.addSql("   (");
                sql.addSql("     exists (");
                sql.addSql("       select * from WML_ACCOUNT_USER");
                sql.addSql("       where WML_ACCOUNT_USER.GRP_SID = ?");
                sql.addSql("       and");
                sql.addSql("         WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
                sql.addSql("     )");
                sql.addSql("     or");
                sql.addSql("     exists (");
                sql.addSql("       select * from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         WML_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.GRP_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.USR_SID = WML_ACCOUNT_USER.USR_SID");
                sql.addSql("       and");
                sql.addSql("         WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
                sql.addSql("     )");
                sql.addSql("   )");
                sql.addIntValue(searchMdl.getGrpSid());
                sql.addIntValue(searchMdl.getGrpSid());
            }

            //ユーザSID
            if (searchMdl.getUserSid() > 0) {
                sql.addSql(" and");
                sql.addSql("   (");
                sql.addSql("     exists (");
                sql.addSql("       select * from WML_ACCOUNT_USER");
                sql.addSql("       where WML_ACCOUNT_USER.USR_SID = ?");
                sql.addSql("       and");
                sql.addSql("         WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
                sql.addSql("     )");
                sql.addSql("     or");
                sql.addSql("     exists (");
                sql.addSql("       select * from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         WML_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.USR_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.GRP_SID = WML_ACCOUNT_USER.GRP_SID");
                sql.addSql("       and");
                sql.addSql("         WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
                sql.addSql("     )");
                sql.addSql("   )");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addIntValue(searchMdl.getUserSid());
            }

            // 認証区分
            if (searchMdl.getNinsyoKbn() == 0) {
                sql.addSql(" and");
                sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE = ?");
                sql.addIntValue(GSConstWebmail.WAC_AUTH_TYPE_NORMAL);
            } else if (searchMdl.getNinsyoKbn() == 1) {
                sql.addSql(" and");
                sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE = ?");
                sql.addIntValue(GSConstWebmail.WAC_AUTH_TYPE_OAUTH);
                if (searchMdl.getNinsyoStatusKbn() == 1) {
                    sql.addSql(" and");
                    sql.addSql("   WML_ACCOUNT.COT_SID in (");
                    sql.addSql("   select");
                    sql.addSql("     COT_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_OAUTH_TOKEN");
                    sql.addSql("   where");
                    sql.addSql("     COT_AUTH_RTOKEN is not null");
                    sql.addSql("   )");
                } else if (searchMdl.getNinsyoStatusKbn() == 2) {
                    sql.addSql(" and");
                    sql.addSql("   WML_ACCOUNT.COT_SID in (");
                    sql.addSql("   select");
                    sql.addSql("     COT_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_OAUTH_TOKEN");
                    sql.addSql("   where");
                    sql.addSql("     COT_AUTH_RTOKEN is null");
                    sql.addSql("   )");
                }
            }
        }

        sql.addSql(" and");
        sql.addSql("   WML_ACCOUNT.WAC_SID = WML_ACCOUNT_RCVDATA.WAC_SID");

        return sql;
    }

    /**
     * <br>[機  能] SqlBufferに並び順を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlOrder(SqlBuffer sql, Wml030SearchModel searchMdl) {
        String order = "";
        if (searchMdl.getOrder() == GSConstWebmail.ORDER_DESC) {
            order = " desc";
        }
        sql.addSql(" order by");
        if (searchMdl.getSortKey() == GSConstWebmail.SKEY_MAIL) {
            sql.addSql("   WAC_ADDRESS" + order);
        } else if (searchMdl.getSortKey() == GSConstWebmail.SKEY_USER) {
            sql.addSql("   (DNT_COUNT + USR_COUNT)" + order);
        } else if (searchMdl.getSortKey() == GSConstWebmail.SKEY_DISKSIZE) {
            sql.addSql("   WML_ACCOUNT_DISK.WDS_SIZE" + order);
        } else if (searchMdl.getSortKey() == GSConstWebmail.SKEY_RECEIVEDATE) {
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE" + order);
        } else if (searchMdl.getSortKey() == GSConstWebmail.SKEY_AUTHTYPE) {
            sql.addSql("   WML_ACCOUNT.WAC_AUTH_TYPE" + order);
        } else {
            sql.addSql("   WAC_NAME" + order);
        }

        return sql;
    }

    /**
     * <br>[機  能] 指定したアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountList アカウントSIDリスト
     * @return アカウント名の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccountNameList(String[] accountList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME");
            sql.addSql(" from ");
            sql.addSql("     WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(accountList[0]));
            for (String accountSid :accountList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");
            sql.addSql("   ORDER BY WML_ACCOUNT.WAC_NAME ASC");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("WAC_NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}
