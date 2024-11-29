package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.sml.model.AtesakiModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメールで使用するユーザ情報を検索するためのDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlUserSearchDao extends UserSearchDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlUserSearchDao.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SmlUserSearchDao() {
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     */
    public SmlUserSearchDao(Connection con) {
        super(con);
    }
    /**
     * <br>[機  能] ユーザSID配列からユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID配列
     * @return ret ユーザ情報リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getUserDataFromSidList(String[] userSid) throws SQLException {
        Connection con = null;
        con = getCon();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        return getUserDataFromSidList(userSid, sortMdl);
    }

    /**
     * <br>[機  能] ユーザSID配列からユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID配列
     * @param sortMdl 共通ソート条件
     * @return ret ユーザ情報リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getUserDataFromSidList(String[] userSid,
            CmnCmbsortConfModel sortMdl)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as usrSid,");
            sql.addSql("   CMN_USRM.USR_JKBN as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as usiMei,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as binSid,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as usiPictKf,");
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
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID in (");

            for (int i = 0; i < userSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(userSid[i]);
            }
            sql.addSql(")");

            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            __setOrderSQL(sql, sortMdl);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AtesakiModel mdl = new AtesakiModel();
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));
                ret.add(mdl);
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
     * <br>[機  能] アカウントSID配列からアカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID配列
     * @return ret ユーザ情報リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AtesakiModel> getAccountDataFromSidList(String[] userSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN as sacJkbn");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_SID in (");

            for (int i = 0; i < userSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(userSid[i]);
            }
            sql.addSql(")");
            sql.addSql(" order by SML_ACCOUNT.SAC_NAME,SML_ACCOUNT.SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AtesakiModel mdl = new AtesakiModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                ret.add(mdl);
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
     * <br>[機  能] SqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート情報
     * @return SqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");
        sql.addSql(" (case when  CMN_USRM_INF.USR_SID is null then 1 else 0 end) ,");
        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }
}
