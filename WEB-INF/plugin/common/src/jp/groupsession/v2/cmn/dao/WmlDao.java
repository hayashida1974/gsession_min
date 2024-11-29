package jp.groupsession.v2.cmn.dao;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.WmlMailDataModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <br>[機  能] WEBメールに関するDB操作を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザが指定したメールを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考] 削除ユーザは除く
     * @param wmdMailnum メッセージ番号
     * @param userSid ユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canReadMail(long wmdMailnum, int userSid)
    throws SQLException {
        if (wmdMailnum <= 0 || userSid <= 0) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        int wacSid = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addLongValue(wmdMailnum);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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

        return canUseAccount(wacSid, userSid);
    }

    /**
     * <br>[機  能] ユーザが指定されたアカウントを使用できるかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseAccount(int wacSid, int userSid) throws SQLException {
        if (wacSid <= 0 || userSid <= 0) {
            return false;
        }

        return canUseAccount(wacSid, userSid,  __isAccountProxyUserAllowed());
    }


    /**
     * <br>[機  能]使用できるアカウントがあるか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return
     * @throws SQLException
     */
    public boolean canUseAccount(int userSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean bRtn = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("WAC_SID") > 0) {
                    bRtn = true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bRtn;
    }

    /**
     * <br>[機  能] ユーザが指定されたアカウントを使用できるかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param userSid ユーザSID
     * @param proxyUserFlg true: アカウント代理人を含める
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseAccount(int wacSid, int userSid, boolean proxyUserFlg)
    throws SQLException {
        if (wacSid <= 0 || userSid <= 0) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(WAC_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WAC_JKBN = ?");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

            sql.addSql(" and");
            __setCanUseAccountSql(sql, userSid, proxyUserFlg);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] ユーザが含まれるアカウントが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean getCanUseAccount(int userSid)
    throws SQLException {
        if (userSid <= 0) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    COUNT(WAC_SID) as CNT");
            sql.addSql("  from");
            sql.addSql("    WML_ACCOUNT");
            sql.addSql("  where");
            sql.addSql("    WAC_SID IN");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        WAC_SID");
            sql.addSql("      from");
            sql.addSql("        (");
            sql.addSql("         select");
            sql.addSql("           WAC_SID");
            sql.addSql("         from");
            sql.addSql("           WML_ACCOUNT_USER");
            sql.addSql("         where");
            sql.addSql("           USR_SID = ?");
            sql.addSql("         or");
            sql.addSql("           GRP_SID IN");
            sql.addSql("           (");
            sql.addSql("            select ");
            sql.addSql("              GRP_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("            where");
            sql.addSql("              USR_SID = ?");
            sql.addSql("           ) ");
            sql.addSql("        )GRP_CHK");
            sql.addSql("    )");
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] ユーザが含まれるアカウントが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean getCanUseAccountDairi(int userSid)
    throws SQLException {
        if (userSid <= 0) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    COUNT(WAC_SID) as CNT");
            sql.addSql("  from");
            sql.addSql("    WML_ACCOUNT");
            sql.addSql("  where");
            sql.addSql("    WAC_SID IN");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        WAC_SID");
            sql.addSql("      from");
            sql.addSql("        WML_ACCOUNT_USER_PROXY");
            sql.addSql("      where");
            sql.addSql("        USR_SID = ?");
            sql.addSql("    )");
            sql.addIntValue(userSid);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return result;
    }




    /**
     * <br>[機  能] アカウント代理人が許可されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true: アカウント代理人が許可されている true: アカウント代理人が許可されていない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isAccountProxyUserAllowed() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAD_PROXY_USER");
            sql.addSql(" from ");
            sql.addSql("   WML_ADM_CONF");
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("WAD_PROXY_USER") == GSConstWebmail.WAD_PROXY_USER_YES;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
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
    public SqlBuffer setAccountSearchSql(SqlBuffer sql, int userSid, boolean proxyUserFlg) {
        sql.addSql(" where");
        sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
        sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);

        sql.addSql(" and");
        __setCanUseAccountSql(sql, userSid, proxyUserFlg);

        return sql;
    }

    /**
     * <br>[機  能] メール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wmdMailnum メッセージ番号
     * @param domain ドメイン
     * @return メール情報
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルの読み込みに失敗
     */
    public WmlMailDataModel getMailData(long wmdMailnum, String domain)
    throws SQLException, TempFileException {
        WmlMailDataModel mailData = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_MAILDATA.WMD_FROM as WMD_FROM,");
            sql.addSql("   WML_MAILDATA.WMD_SDATE as WMD_SDATE,");
            sql.addSql("   WML_MAILDATA.WMD_TITLE as SUBJECT,");
            sql.addSql("   WML_MAIL_BODY.WMB_BODY as BODY");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_MAIL_BODY");
            sql.addSql(" where");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
            sql.addLongValue(wmdMailnum);
            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mailData = new WmlMailDataModel();
                mailData.setFromAddress(rs.getString("WMD_FROM"));
                mailData.setSendDate(UDate.getInstanceTimestamp(rs.getTimestamp("WMD_SDATE")));
                mailData.setSubject(rs.getString("SUBJECT"));
                mailData.setBody(rs.getString("BODY"));
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        if (mailData != null) {

            //宛先、CCを取得
            List<String> toList = new ArrayList<String>();
            List<String> ccList = new ArrayList<String>();
            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   WSA_TYPE,");
                sql.addSql("   WSA_ADDRESS");
                sql.addSql(" from ");
                sql.addSql("   WML_SENDADDRESS");
                sql.addSql(" where");
                sql.addSql("   WMD_MAILNUM = ?");
                sql.addSql(" order by");
                sql.addSql("   WSA_NUM");
                sql.addLongValue(wmdMailnum);
                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int wsaType = rs.getInt("WSA_TYPE");
                    String address = rs.getString("WSA_ADDRESS");
                    if (wsaType == GSConstWebmail.WSA_TYPE_TO) {
                        toList.add(address);
                    } else if (wsaType == GSConstWebmail.WSA_TYPE_CC) {
                        ccList.add(address);
                    }
                }
                mailData.setToList(toList);
                mailData.setCcList(ccList);

            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
            }

            //添付ファイル情報を取得
            List<Long> wtfSidList = new ArrayList<Long>();
            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   WTF_SID");
                sql.addSql(" from ");
                sql.addSql("   WML_TEMPFILE");
                sql.addSql(" where");
                sql.addSql("   WMD_MAILNUM = ?");
                sql.addLongValue(wmdMailnum);
                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    wtfSidList.add(rs.getLong("WTF_SID"));
                }

            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
            }

            if (!wtfSidList.isEmpty()) {
                CommonBiz cmnBiz = new CommonBiz();
                List<WmlTempfileModel> tempFileList = new ArrayList<WmlTempfileModel>();
                for (long wtfSid : wtfSidList) {
                    tempFileList.add(
                            cmnBiz.getBinInfoForWebmail(getCon(), wmdMailnum, wtfSid, domain));
                }
                mailData.setTempFileList(tempFileList);
            }
        }

        return mailData;
    }

    /**
     * <br>[機  能] ユーザが指定されたアカウントを使用できるかを判定するSQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @param proxyUserFlg true: アカウント代理人を許可する false: アカウント代理人を許可しない
     * @return SqlBuffer
     */
    private SqlBuffer __setCanUseAccountSql(SqlBuffer sql, int userSid, boolean proxyUserFlg) {
        sql.addSql("   (");
        sql.addSql("      (");
        sql.addSql("         WML_ACCOUNT.WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         WML_ACCOUNT.USR_SID = ?");
        sql.addSql("      )");
        sql.addSql("     or");
        sql.addSql("      (");
        sql.addSql("         WML_ACCOUNT.WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         exists (");
        sql.addSql("           select WAC_SID from WML_ACCOUNT_USER");
        sql.addSql("           where");
        sql.addSql("             GRP_SID in (");
        sql.addSql("               select GRP_SID from CMN_BELONGM");
        sql.addSql("               where USR_SID = ?");
        sql.addSql("             )");
        sql.addSql("           and");
        sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("     or");
        sql.addSql("      (");
        sql.addSql("         WML_ACCOUNT.WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         exists (");
        sql.addSql("           select WAC_SID from WML_ACCOUNT_USER");
        sql.addSql("           where USR_SID = ?");
        sql.addSql("           and");
        sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
        sql.addSql("         )");
        sql.addSql("      )");
        if (proxyUserFlg) {
            sql.addSql("     or");
            sql.addSql("       exists ( ");
            sql.addSql("         select WAC_SID from WML_ACCOUNT_USER_PROXY ");
            sql.addSql("         where");
            sql.addSql("           USR_SID = ? ");
            sql.addSql("         and ");
            sql.addSql("           WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER_PROXY.WAC_SID ");
            sql.addSql("       )");
        }
        sql.addSql("   )");

        sql.addIntValue(GSConstWebmail.WAC_TYPE_NORMAL);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConstWebmail.WAC_TYPE_GROUP);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConstWebmail.WAC_TYPE_USER);
        sql.addIntValue(userSid);
        if (proxyUserFlg) {
            sql.addIntValue(userSid);
        }

        return sql;
    }
}
