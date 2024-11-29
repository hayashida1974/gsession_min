package jp.groupsession.v2.wml.wml110;

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
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.base.AccountDataModel;

/**
 * <br>[機  能] WEBメール ラベルの管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml110Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml110Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml110Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウントリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<AccountDataModel> getAccountList(int userSid)
    throws SQLException {
        //アカウント代理人が許可されているかを判定する
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
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME");
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
            sql.addSql(" where");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         WAC_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         USR_SID = ?");
            sql.addSql("      )");
            sql.addSql("     or");
            sql.addSql("      (");
            sql.addSql("         WAC_TYPE = ?");
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
            sql.addSql("         WAC_TYPE = ?");
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
            sql.addSql(" order by WAS_SORT asc, WAC_NAME asc");

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
                model.setAccountName(rs.getString("WAC_NAME"));
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

}
