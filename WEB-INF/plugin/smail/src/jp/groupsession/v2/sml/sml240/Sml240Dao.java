package jp.groupsession.v2.sml.sml240;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウントマネージャー画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml240Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml240Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml240Dao(Connection con) {
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
    public List<Sml240AccountModel> getAccountList(Sml240SearchModel searchMdl,
                                                        RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Sml240AccountModel> ret = new ArrayList<Sml240AccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE as SAC_TYPE,");
            sql.addSql("   SML_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   SML_ACCOUNT.SAC_BIKO as SAC_BIKO,");
            sql.addSql("   ACCOUNT_USER_COUNT.DNT_COUNT as DNT_COUNT,");
            sql.addSql("   ACCOUNT_USER_COUNT.USR_COUNT as USR_COUNT,");
            sql.addSql("   SML_ACCOUNT_DISK.SDS_SIZE as SDS_SIZE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from ");
            sql.addSql("   ( ");
            sql.addSql("     SML_ACCOUNT");
            sql.addSql("     inner join ");
            sql.addSql("       SML_ACCOUNT_DISK");
            sql.addSql("     on ");
            sql.addSql("       SML_ACCOUNT.SAC_SID = SML_ACCOUNT_DISK.SAC_SID");
            sql.addSql("     left join");
            sql.addSql("       CMN_USRM_INF");
            sql.addSql("     on");
            sql.addSql("       SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("     left join");
            sql.addSql("       CMN_USRM");
            sql.addSql("     on");
            sql.addSql("       SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   ) ");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         SML_ACCOUNT_USER.SAC_SID,");
            sql.addSql("         count(CMN_GROUPM_TABLE.GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(CMN_USRM_TABLE.USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         SML_ACCOUNT_USER");

            sql.addSql("       left join");
            sql.addSql("         (select CMN_USRM.USR_SID from CMN_USRM"
                                    + " where CMN_USRM.USR_JKBN != 9) CMN_USRM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         SML_ACCOUNT_USER.USR_SID = CMN_USRM_TABLE.USR_SID");

            sql.addSql("       left join");
            sql.addSql("         (select GRP_SID from CMN_GROUPM"
                                    + " where CMN_GROUPM.GRP_JKBN != 9) CMN_GROUPM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         SML_ACCOUNT_USER.GRP_SID = CMN_GROUPM_TABLE.GRP_SID");

            sql.addSql("       group by");
            sql.addSql("         SML_ACCOUNT_USER.SAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.SAC_SID = ACCOUNT_USER_COUNT.SAC_SID");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            String order = "";
            if (searchMdl.getOrder() == GSConstSmail.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            if (searchMdl.getSortKey() == GSConstSmail.SKEY_USER) {
                sql.addSql("   (DNT_COUNT + USR_COUNT)" + order);
            } else if (searchMdl.getSortKey() == GSConstSmail.SKEY_DISKSIZE) {
                sql.addSql("   SML_ACCOUNT_DISK.SDS_SIZE" + order);
            } else {
                sql.addSql("   SAC_NAME" + order);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (searchMdl.getMaxCount() > 0) {
                if (searchMdl.getPage() > 1) {
                    rs.absolute(searchMdl.getPage() - 1);
                }

                for (int i = 0; rs.next() && i < searchMdl.getMaxCount(); i++) {

                    Sml240AccountModel model = new Sml240AccountModel(reqMdl);
                    model.setAccountSid(rs.getInt("SAC_SID"));

                    if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                            && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                        model.setAccountName(
                                rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                        model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    } else {
                        model.setAccountName(rs.getString("SAC_NAME"));
                    }

                    model.setAccountUserKbn(rs.getInt("SAC_TYPE"));

                    //BからMBへ変換
                    model.setDiskSizeUse(
                            rs.getBigDecimal("SDS_SIZE").divide(
                                    new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP));

                    model.setBiko(rs.getString("SAC_BIKO"));

                    model.setAccountGroupCount(rs.getInt("DNT_COUNT"));
                    model.setAccountUserCount(rs.getInt("USR_COUNT"));

                    ret.add(model);
                }
            } else {

                while (rs.next()) {
                    Sml240AccountModel model = new Sml240AccountModel(reqMdl);
                    model.setAccountSid(rs.getInt("SAC_SID"));

                    if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                            && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                        model.setAccountName(
                                rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    } else {
                        model.setAccountName(rs.getString("SAC_NAME"));
                    }

                    model.setAccountUserKbn(rs.getInt("SAC_TYPE"));

                    //BからMBへ変換
                    model.setDiskSizeUse(
                            rs.getBigDecimal("SDS_SIZE").divide(
                                    new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP));

                    model.setBiko(rs.getString("SAC_BIKO"));
                    model.setAccountGroupCount(rs.getInt("DNT_COUNT"));
                    model.setAccountUserCount(rs.getInt("USR_COUNT"));

                    ret.add(model);
                }
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
    public int recordCount(Sml240SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" count(*)");
            sql.addSql(" as CNT");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         SAC_SID,");
            sql.addSql("         count(GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         SML_ACCOUNT_USER");
            sql.addSql("       group by");
            sql.addSql("         SAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.SAC_SID = ACCOUNT_USER_COUNT.SAC_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID ");

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
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Sml240SearchModel searchMdl) {
        sql.addSql(" where");
        sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
        sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
        sql.addSql(" and");
        sql.addSql("   SML_ACCOUNT.SAC_SID > ?");
        sql.addIntValue(GSConstUser.SID_SYSTEM_MAIL);
        sql.addSql(" and");
        sql.addSql("   (CMN_USRM_INF.USR_SID > ?");
        sql.addIntValue(GSConstUser.SID_SYSTEM_MAIL);
        sql.addSql("   or");
        sql.addSql("   CMN_USRM_INF.USR_SID is null )");


        if (searchMdl.existSearchCondition()) {

            //キーワード
            if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
                sql.addSql(" and");
                sql.addSql(" ((  SML_ACCOUNT.SAC_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "' ) ");
                sql.addSql(" or");
                sql.addSql(" (  CMN_USRM_INF.USI_SEI like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "' ) ");
                sql.addSql(" or");
                sql.addSql(" (  CMN_USRM_INF.USI_MEI like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "' )) ");

            }

            //グループSID
            if (searchMdl.getGrpSid() >= 0 && searchMdl.getUserSid() <= 0) {
                sql.addSql(" and");
                sql.addSql("     SML_ACCOUNT.SAC_SID in (");
                sql.addSql("       select SAC_SID from SML_ACCOUNT_USER");
                sql.addSql("       where SML_ACCOUNT_USER.GRP_SID = ?");
                sql.addSql("       union all");
                sql.addSql("       select SAC_SID from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         SML_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.GRP_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.USR_SID = SML_ACCOUNT_USER.USR_SID");
                sql.addSql("     )");
                sql.addIntValue(searchMdl.getGrpSid());
                sql.addIntValue(searchMdl.getGrpSid());
            }

            //ユーザSID
            if (searchMdl.getUserSid() > 0) {
                sql.addSql(" and");
                sql.addSql("     SML_ACCOUNT.SAC_SID in (");
                sql.addSql("       select SAC_SID from SML_ACCOUNT_USER");
                sql.addSql("       where SML_ACCOUNT_USER.USR_SID = ?");
                sql.addSql("       union all");
                sql.addSql("       select SAC_SID from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         SML_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.USR_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.GRP_SID = SML_ACCOUNT_USER.GRP_SID");
                sql.addSql("     )");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addIntValue(searchMdl.getUserSid());
            }
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
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME");
            sql.addSql(" from ");
            sql.addSql("     SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(accountList[0]));
            for (String accountSid : accountList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");
            sql.addSql("   ORDER BY SML_ACCOUNT.SAC_NAME ASC");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("SAC_NAME"));
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
