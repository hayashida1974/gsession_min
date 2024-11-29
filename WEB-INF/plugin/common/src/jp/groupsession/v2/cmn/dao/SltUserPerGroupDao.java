package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.man.man050.LoginHistoryCsvRecordListenerImpl;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] グループセレクトボックスの情報を扱うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SltUserPerGroupDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SltUserPerGroupDao.class);

    /**
     * <p>Default Constructor
     */
    public SltUserPerGroupDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SltUserPerGroupDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] グループに所属するユーザ情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param gsid グループSID
     * @param sortMdl ソート情報
     * @return TarUserPerGroupModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SltUserPerGroupModel> selectAdminGroupUserList(int gsid,
                                                                CmnCmbsortConfModel sortMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SltUserPerGroupModel> ret =
            new ArrayList<SltUserPerGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID, ");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO, ");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YKSK, ");
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
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");

            sql = __setOrderSQL(sql, sortMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(GSConst.USER_ADMIN);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCbxUserPerGroupFromRs(rs));
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
     * <br>[機  能] グループに所属するユーザ(管理者)のSID一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param gsids グループSID
     * @param usrSids ユーザSID
     * @return TarUserPerGroupModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectAdminGroupUserSidList(ArrayList<Integer> gsids,
                    ArrayList<Integer> usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret =
            new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID in( ");
            for (int i = 0; i < gsids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(gsids.get(i).intValue());
            }
            sql.addSql("  ) ");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN = ?");
            sql.addIntValue(GSConst.USER_ADMIN);
            sql.addSql(" and");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");

            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM.USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" order by USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] グループに所属するユーザのうち指定されたユーザのみ、またはそれ以外の
     * <br>         ユーザ情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param gsid グループSID
     * @param userSid ユーザSID
     * @param iflg true:指定されたユーザのみ、false:指定されたユーザ以外
     * @param sortMdl ソート情報
     * @return TarUserPerGroupModel
     * @throws SQLException SQL実行例外
     */
    public List<SltUserPerGroupModel> selectGroupList(int gsid, String[] userSid,
            boolean iflg, CmnCmbsortConfModel sortMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SltUserPerGroupModel> ret = new ArrayList<SltUserPerGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID, ");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO, ");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YKSK, ");
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
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(gsid);
//            sql.addSql("   AND CMN_USRM.USR_SID > 100 ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = 0 ");
                        sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql("   AND CMN_USRM.USR_JKBN = " + GSConstUser.USER_JTKBN_ACTIVE);

            //ユーザSIDが指定されている場合
            if (userSid != null && userSid.length > 0) {

                sql.addSql(" and");
                if (userSid.length == 1) {
                    if (iflg) {
                        sql.addSql("   CMN_BELONGM.USR_SID = ?");
                    } else {
                        sql.addSql("   CMN_BELONGM.USR_SID <> ?");
                    }
                    sql.addIntValue(Integer.parseInt(userSid[0]));

                } else {
                    if (iflg) {
                        sql.addSql("   CMN_BELONGM.USR_SID in (");
                    } else {
                        sql.addSql("   CMN_BELONGM.USR_SID not in (");
                    }

                    int lastIdx = userSid.length - 1;
                    for (int idx = 0; idx < lastIdx; idx++) {
                        sql.addSql("     ?,");
                        sql.addIntValue(Integer.parseInt(userSid[idx]));
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(userSid[lastIdx]));

                    sql.addSql("   )");
                }
            }

            sql = __setOrderSQL(sql, sortMdl);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCbxUserPerGroupFromRs(rs));
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
     * <br>[機  能] グループSIDから所属ユーザ情報一覧を取得します
     * <br>[解  説] ソートキーでソート条件(0-3)を切り替えます
     * <br>         並びにオーダーキーで降順(0)昇順(1)を切り替えます
     * <br>[備  考]
     * @param gsid グループSID
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @return グループリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SltUserPerGroupModel> selectGroupListSort(int gsid,
                                                              int sortKey,
                                                              int orderKey)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SltUserPerGroupModel> ret = new ArrayList<SltUserPerGroupModel>();
        con = getCon();
        String order = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID,");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
//          sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YKSK, ");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YKSK,");
//          sql.addSql("   case");
//          sql.addSql("   when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
//          sql.addSql("   else 0");
//          sql.addSql("   end YAKUSYOKU_LEN");
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
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(gsid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" order by ");

            //キー値でascとdescを切り替えます
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                order = "asc";
            } else if (orderKey == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            //ソートカラム
            switch (sortKey) {
                case GSConstUser.USER_SORT_ID:
                    sql.addSql("   USR_LGID " + order);
                    break;
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("   YAKUSYOKU_EXIST " + order + ", ");
                    sql.addSql("   YAKUSYOKU_SORT " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_LALG:
                    sql.addSql("   USI_LTLGIN " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                default:
                    break;
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCbxUserPerGroupFromRs2(rs));
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
     * <br>[機  能] グループSIDから所属ユーザ情報一覧を取得します
     * <br>[解  説] ソートキーでソート条件(0-3)を切り替えます
     * <br>         並びにオーダーキーで降順(0)昇順(1)を切り替えます
     * <br>[備  考]
     * @param rl LoginHistoryCsvRecordListenerImpl
     * @param gsid グループSID
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行時例外
     * @throws CSVException Cvs出力例外
     */
    public void selectGroupListSortForCsv(LoginHistoryCsvRecordListenerImpl rl,
                                          int gsid,
                                          int sortKey,
                                          int orderKey,
                                          RequestModel reqMdl)
                                          throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        String order = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID as GRP_SID,");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as GRP_KBN,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SNO,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
//          sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YKSK, ");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YKSK,");
//          sql.addSql("   case");
//          sql.addSql("   when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
//          sql.addSql("   else 0");
//          sql.addSql("   end YAKUSYOKU_LEN");
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
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql("   AND CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(gsid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql("   AND CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("   AND CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" order by ");

            //キー値でascとdescを切り替えます
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                order = "asc";
            } else if (orderKey == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            //ソートカラム
            switch (sortKey) {
                case GSConstUser.USER_SORT_ID:
                    sql.addSql("   USR_LGID " + order);
                    break;
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("   YAKUSYOKU_EXIST " + order + ", ");
                    sql.addSql("   YAKUSYOKU_SORT " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                case GSConstUser.USER_SORT_LALG:
                    sql.addSql("   USI_LTLGIN " + order + ", ");
                    sql.addSql("   USI_SEI_KN " + order + ", ");
                    sql.addSql("   USI_MEI_KN " + order);
                    break;
                default:
                    break;
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setLoginHistoryCsvRecordFromRs(rs, rl, reqMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>CMN_BELONGM, CMN_GROUPM, CMN_USRM_INFの
     * 結果セットをBeanにセットします<br>
     * Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBelongmModel
     * @throws SQLException SQL実行例外
     */
    private SltUserPerGroupModel __getCbxUserPerGroupFromRs(ResultSet rs) throws SQLException {
        SltUserPerGroupModel bean = new SltUserPerGroupModel();
        bean.setGroupsid(rs.getInt("GRP_SID"));
        bean.setUsrsid(rs.getInt("USR_SID"));
        bean.setGrpAdmin(rs.getInt("GRP_KBN"));
        bean.setUsrlgid(rs.getString("USR_LGID"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setNamesei(rs.getString("USI_SEI"));
        bean.setNamemei(rs.getString("USI_MEI"));
        bean.setFullName(rs.getString("USI_SEI")
                + " " + rs.getString("USI_MEI"));
        bean.setSyainno(rs.getString("USI_SNO"));
        bean.setYakusyoku(rs.getString("USI_YKSK"));
        return bean;
    }

    /**
     * <p>結果セットからCmnLoginHistoryModelを取得する。
     * @param rs 結果セット
     * @param rl LoginHistoryCsvRecordListenerImpl
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setLoginHistoryCsvRecordFromRs(
            ResultSet rs, LoginHistoryCsvRecordListenerImpl rl,
            RequestModel reqMdl)
        throws SQLException, CSVException {

        SltUserPerGroupModel bean = new SltUserPerGroupModel();
        bean.setGroupsid(rs.getInt("GRP_SID"));
        bean.setUsrsid(rs.getInt("USR_SID"));
        bean.setGrpAdmin(rs.getInt("GRP_KBN"));
        bean.setUsrlgid(rs.getString("USR_LGID"));
        bean.setNamesei(rs.getString("USI_SEI"));
        bean.setNamemei(rs.getString("USI_MEI"));
        bean.setFullName(rs.getString("USI_SEI")
                + " " + rs.getString("USI_MEI"));
        bean.setSyainno(rs.getString("USI_SNO"));
        bean.setYakusyoku(rs.getString("USI_YKSK"));
        bean.setLgintimeStr(convertUDateGS2(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")), reqMdl));
        rl.setRecord(bean);
    }

    /**
     * <br>[機  能] UDate形式の時間を表示形式に変換します。
     * <br>[解  説] 例）20060101235959 → 2006/01/01 23:59:59
     * <br>[備  考]
     * @param udate UDate
     * @param reqMdl RequestModel
     * @return String
     */
    public static String convertUDateGS2(UDate udate, RequestModel reqMdl) {
        String strSdate = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (udate == null) {
            return gsMsg.getMessage("main.src.man050.4");
        }
        strSdate = UDateUtil.getSlashYYMD(udate)
                 + "  "
                 + UDateUtil.getSeparateHMS(udate);
        log__.debug("生成結果：" + strSdate);
        return strSdate;
    }

    /**
     * <p>CMN_BELONGM, CMN_GROUPM, CMN_USRM_INFの
     * 結果セットをBeanにセットします。（最終ログイン時間も取得します）<br>
     * Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBelongmModel
     * @throws SQLException SQL実行例外
     */
    private SltUserPerGroupModel __getCbxUserPerGroupFromRs2(ResultSet rs) throws SQLException {
        SltUserPerGroupModel bean = __getCbxUserPerGroupFromRs(rs);
        bean.setLgintime(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        return bean;
    }

    /**
     * <br>[機  能] order句の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート条件
     * @return SqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");

        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }

        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   USI_SEI_KN asc,");
                sql.addSql("   USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }
}
