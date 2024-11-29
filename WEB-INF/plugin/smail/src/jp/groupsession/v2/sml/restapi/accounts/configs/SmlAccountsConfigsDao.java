package jp.groupsession.v2.sml.restapi.accounts.configs;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * アカウント設定情報API
 *
 *
 */
public class SmlAccountsConfigsDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountsConfigsDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountsConfigsDao(Connection con) {
        super(con);
    }

    /**
     * <p>アクセス可能なアカウントの設定情報を取得する
     * @param reqMdl リクエスト情報
     * @param sessionUserSid セッションユーザSID
     * @return List in SmlAccountsConfigsGetResultModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountsConfigsGetResultModel> getAccountConfigList(
            RequestModel reqMdl,
            int sessionUserSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountsConfigsGetResultModel> ret
                                 = new ArrayList<SmlAccountsConfigsGetResultModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID,");
            sql.addSql("   SML_ACCOUNT.USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_SEND_MAILTYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_THEME,");
            sql.addSql("   SML_ACCOUNT.SAC_QUOTES,");
            sql.addSql("   SML_PUSH_UCONF.SPU_PUSHUSE,");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       count(*)");
            sql.addSql("     from");
            sql.addSql("       SML_JMEIS");
            sql.addSql("     where");
            sql.addSql("       SML_ACCOUNT.SAC_SID = SML_JMEIS.SAC_SID");
            sql.addSql("       and");
            sql.addSql("       SML_JMEIS.SMJ_OPKBN = ?");
            sql.addSql("       and");
            sql.addSql("       SML_JMEIS.SMJ_JKBN = ?");
            sql.addSql("     ) as MIDOKU_COUNT,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CASE");
            sql.addSql("     WHEN SML_ACCOUNT.USR_SID is null THEN 1");
            sql.addSql("     ELSE 0");
            sql.addSql("     END ACCOUNT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select SAC_SID, SAS_SORT");
            sql.addSql("      from SML_ACCOUNT_SORT ");
            sql.addSql("      where USR_SID = ? ");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.SAC_SID = ACCOUNT_SORT.SAC_SID ");
            sql.addSql("   left join");
            sql.addSql("     SML_PUSH_UCONF");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.SAC_SID = SML_PUSH_UCONF.SAC_SID");
            sql.addSql("     and SML_ACCOUNT.USR_SID = ?");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         SML_ACCOUNT.SAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         SML_ACCOUNT.USR_SID = ? ");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         SML_ACCOUNT.SAC_SID in ( ");
            sql.addSql("           select SAC_SID from SML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("           GRP_SID in ( ");
            sql.addSql("             select GRP_SID from CMN_BELONGM ");
            sql.addSql("             where USR_SID = ? ");
            sql.addSql("           )");
            sql.addSql("         union all ");
            sql.addSql("           select SAC_SID from SML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("             USR_SID = ? ");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   ACCOUNT_SORT.SAS_SORT asc ");
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(sessionUserSid);
            sql.addIntValue(sessionUserSid);
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addIntValue(GSConstSmail.SAC_TYPE_NORMAL);
            sql.addIntValue(sessionUserSid);
            sql.addIntValue(sessionUserSid);
            sql.addIntValue(sessionUserSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAccountsConfigsGetResultModel resMdl = new SmlAccountsConfigsGetResultModel();
                // アカウント情報
                resMdl.setSid(rs.getInt("SAC_SID"));
                if (rs.getInt("ACCOUNT_TYPE") == 0) {
                    //代表アカウントではない場合は、アカウント名にユーザ姓名を設定
                    resMdl.setName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    //代表アカウントの場合はアカウント名を使用
                    resMdl.setName(rs.getString("SAC_NAME"));
                }

                if (rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_ACTIVE) {
                    resMdl.setUserDeleteFlg(0);
                } else {
                    resMdl.setUserDeleteFlg(1);
                }
                resMdl.setLoginStopFlg(rs.getInt("USR_UKO_FLG"));
                if (rs.getInt("USR_SID") == sessionUserSid) {
                    resMdl.setDefaultFlg(1);
                }
                resMdl.setMidokuCount(rs.getInt("MIDOKU_COUNT"));
                // 設定情報
                SmlAccountsConfigsConfigInfoModel configsInfoMdl
                                          = new SmlAccountsConfigsConfigInfoModel();
                configsInfoMdl.setBodyFormatFlg(rs.getInt("SAC_SEND_MAILTYPE"));
                configsInfoMdl.setThemeType(rs.getInt("SAC_THEME"));
                configsInfoMdl.setQuoteText(SmlCommonBiz.getViewMailQuotes(
                        rs.getInt("SAC_QUOTES"), reqMdl));
                configsInfoMdl.setPushFlg(rs.getInt("SPU_PUSHUSE"));
                // 自動送信先設定情報
                List<SmlAccountsConfigsAutoDestModel> autoDestList
                                    = getAutoDestList__(rs.getInt("SAC_SID"));
                configsInfoMdl.setAutoDestArray(autoDestList);
                resMdl.setConfigInfo(configsInfoMdl);
                ret.add(resMdl);
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
     * <p>指定したアカウントの自動送信先設定情報を取得する
     * @param accountSid アカウントSID
     * @return List in SmlAccountsConfigsGetResultModel
     * @throws SQLException SQL実行例外
     */
    private List<SmlAccountsConfigsAutoDestModel> getAutoDestList__(
            int accountSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountsConfigsAutoDestModel> ret
                                 = new ArrayList<SmlAccountsConfigsAutoDestModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAC_SID,");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAA_TYPE,");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAA_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_AUTODEST");
            sql.addSql("   left join");
            sql.addSql("     SML_ACCOUNT");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT_AUTODEST.SAA_SID = SML_ACCOUNT.SAC_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID= CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAC_SID = ?");
            sql.addIntValue(accountSid);
            sql.addSql(" order by");
            sql.addSql("   SML_ACCOUNT_AUTODEST.SAA_TYPE,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlAccountsConfigsAutoDestModel resMdl = new SmlAccountsConfigsAutoDestModel();
                resMdl.setSid(rs.getInt("SAA_SID"));
                resMdl.setName(rs.getString("SAC_NAME"));
                resMdl.setType(rs.getInt("SAA_TYPE"));
                if (rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_ACTIVE) {
                    resMdl.setUserDeleteFlg(0);
                } else {
                    resMdl.setUserDeleteFlg(1);
                }
                resMdl.setLoginStopFlg(rs.getInt("USR_UKO_FLG"));
                ret.add(resMdl);
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
