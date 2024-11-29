package jp.groupsession.v2.sml.sml270;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.AccountDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウントの管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml270Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml270Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml270Dao(Connection con) {
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

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AccountDataModel> ret = new ArrayList<AccountDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID, ");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE, ");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME, ");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO, ");
            sql.addSql("   ACCOUNT_SORT.SAS_SORT as SAS_SORT, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
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
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID ");

            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");

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

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addIntValue(GSConstSmail.SAC_TYPE_NORMAL);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AccountDataModel model = new AccountDataModel();
                model.setAccountSid(rs.getInt("SAC_SID"));
                model.setAccountType(rs.getInt("SAC_TYPE"));


                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    model.setAccountName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    model.setAccountName(rs.getString("SAC_NAME"));
                }

                model.setAccountBiko(rs.getString("SAC_BIKO"));
                model.setAccountSort(rs.getLong("SAS_SORT"));
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
            sql.addSql("   SML_ACCOUNT_SORT");
            sql.addSql("     set SAS_SORT =");
            sql.addSql("       case when SAC_SID = ? ");
            sql.addSql("       then  ? ");
            sql.addSql("       when SAC_SID = ? ");
            sql.addSql("       then  ? ");
            sql.addSql("       else SAS_SORT end");
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
