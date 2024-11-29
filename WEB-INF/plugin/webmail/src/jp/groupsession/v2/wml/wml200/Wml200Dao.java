package jp.groupsession.v2.wml.wml200;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.AccountDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml200Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml200Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml200Dao(Connection con) {
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

    /**
     * <br>[機  能] 表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param motoSid 入れ替え元ラベルSID
     * @param motoSort 入れ替え元ソート順
     * @param sakiSid 入れ替え先ラベルSID
     * @param sakiSort 入れ替え先ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(
        int motoSid,
        int motoSort,
        int sakiSid,
        int sakiSort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LABEL");
            sql.addSql("     set WLB_ORDER = case when WLB_SID = ? ");
            sql.addSql("     then ? ");
            sql.addSql("     when WLB_SID = ? ");
            sql.addSql("     then ? ");
            sql.addSql("     else WLB_ORDER end");

            sql.addIntValue(motoSid);
            sql.addIntValue(sakiSort);
            sql.addIntValue(sakiSid);
            sql.addIntValue(motoSort);

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
