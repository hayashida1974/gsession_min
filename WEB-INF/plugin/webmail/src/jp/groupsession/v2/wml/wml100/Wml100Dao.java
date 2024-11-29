package jp.groupsession.v2.wml.wml100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.base.AccountDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール アカウントの管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml100Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml100Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml100Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<AccountDataModel> getAccountList(int userSid)
    throws SQLException {

        //管理者設定 代理人を取得する
        WmlBiz wmlBiz = new WmlBiz();
        boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(getCon());

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AccountDataModel> ret = new ArrayList<AccountDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID, ");
            sql.addSql("   WML_ACCOUNT.WAC_ACCOUNT_ID as WAC_ACCOUNT_ID, ");
            sql.addSql("   WML_ACCOUNT.WAC_TYPE as WAC_TYPE, ");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME, ");
            sql.addSql("   WML_ACCOUNT.WAC_ADDRESS as WAC_ADDRESS, ");
            sql.addSql("   WML_ACCOUNT.WAC_BIKO as WAC_BIKO, ");
            sql.addSql("   WML_ACCOUNT.WAC_RECEIVE_DATE as WAC_RECEIVE_DATE, ");
            sql.addSql("   WML_ACCOUNT_RCVDATA.WRD_RECEIVE_DATE as WRD_RECEIVE_DATE,");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT as WAS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_RCVDATA,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select WAC_SID, WAS_SORT");
            sql.addSql("      from WML_ACCOUNT_SORT ");
            sql.addSql("      where USR_SID = ? ");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID ");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         WML_ACCOUNT.USR_SID = ? ");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("           GRP_SID in ( ");
            sql.addSql("             select GRP_SID from CMN_BELONGM ");
            sql.addSql("             where USR_SID = ? ");
            sql.addSql("           )");
            sql.addSql("         and ");
            sql.addSql("           WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("             USR_SID = ? ");
            sql.addSql("           and ");
            sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            if (proxyUserFlg) {
                sql.addSql("      or ");
                sql.addSql("        exists ( ");
                sql.addSql("          select WAC_SID from WML_ACCOUNT_USER_PROXY ");
                sql.addSql("          where");
                sql.addSql("            USR_SID = ? ");
                sql.addSql("          and ");
                sql.addSql("            WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER_PROXY.WAC_SID ");
                sql.addSql("        )");
            }
            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_SID = WML_ACCOUNT_RCVDATA.WAC_SID");

            sql.addSql(" order by ");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_NORMAL);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_GROUP);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_USER);
            sql.addIntValue(userSid);
            if (proxyUserFlg) {
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AccountDataModel model = new AccountDataModel();
                model.setAccountSid(rs.getInt("WAC_SID"));
                model.setAccountUid(rs.getString("WAC_ACCOUNT_ID"));
                model.setAccountType(rs.getInt("WAC_TYPE"));
                model.setAccountName(rs.getString("WAC_NAME"));
                model.setAccountAddress(rs.getString("WAC_ADDRESS"));
                model.setAccountBiko(rs.getString("WAC_BIKO"));
                model.setAccountSort(rs.getLong("WAS_SORT"));
                model.setReceiveDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WRD_RECEIVE_DATE")));
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
     * <br>[機  能] アカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param proxyUserFlg true: アカウント代理人を許可する false: アカウント代理人を許可しない
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccountSidList(int userSid, boolean proxyUserFlg)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID ");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select WAC_SID, WAS_SORT");
            sql.addSql("      from WML_ACCOUNT_SORT ");
            sql.addSql("      where USR_SID = ? ");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID ");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         WML_ACCOUNT.USR_SID = ? ");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("           GRP_SID in ( ");
            sql.addSql("             select GRP_SID from CMN_BELONGM ");
            sql.addSql("             where USR_SID = ? ");
            sql.addSql("           )");
            sql.addSql("         and ");
            sql.addSql("           WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("             USR_SID = ? ");
            sql.addSql("           and ");
            sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            if (proxyUserFlg) {
                sql.addSql("      or ");
                sql.addSql("        exists ( ");
                sql.addSql("          select WAC_SID from WML_ACCOUNT_USER_PROXY ");
                sql.addSql("          where");
                sql.addSql("            USR_SID = ? ");
                sql.addSql("          and ");
                sql.addSql("            WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER_PROXY.WAC_SID ");
                sql.addSql("        )");
            }
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_NORMAL);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_GROUP);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_USER);
            sql.addIntValue(userSid);
            if (proxyUserFlg) {
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());
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
     * <br>[機  能] 表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param motoSid 入れ替え元フィルターSID
     * @param motoSort 入れ替え元ソート順
     * @param sakiSid 入れ替え先フィルターSID
     * @param sakiSort 入れ替え先ソート順
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateSort(
        int motoSid,
        int motoSort,
        int sakiSid,
        int sakiSort,
        int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_SORT");
            sql.addSql("     set WAS_SORT =");
            sql.addSql("       case when WAC_SID = ? ");
            sql.addSql("       then  ? ");
            sql.addSql("       when WAC_SID = ? ");
            sql.addSql("       then  ? ");
            sql.addSql("       else WAS_SORT end");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(motoSid);
            sql.addIntValue(sakiSort);
            sql.addIntValue(sakiSid);
            sql.addIntValue(motoSort);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
