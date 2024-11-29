package jp.groupsession.v2.bbs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.bbs.model.BulletinMainModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.bbs.model.BulletinWachModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 掲示板プラグインで共通利用されるDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BulletinDao.class);

    /** 取得区分(カウント) */
    public static final int GET_COUNT = 0;
    /** 取得区分(一覧表示) */
    public static final int GET_LIST = 1;
    /** 取得区分(未確認・受信一覧) */
    public static final int GET_UNOPEN_LIST = 2;

    /**
     * <p>デフォルトコンストラクタ
     */
    public BulletinDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public BulletinDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] フォーラムの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSidList フォーラムSIDリスト
     * @param admin 管理者か否か true:管理者 false:一般ユーザ
     * @param isHierarchy 階層表示か否か true:階層表示である false:階層表示でない
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    public int getForumCount(
            List<Integer> bfiSidList, boolean admin, boolean isHierarchy)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        if (!admin && (bfiSidList == null || bfiSidList.size() < 1)) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(BBS_FOR_INF.BFI_SID) as forumCnt");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF");

            if (!admin || isHierarchy) {
                sql.addSql("  where");
            }

            if (!admin) {
                sql.addSql("    BFI_SID in (");
                for (int i = 0; i < bfiSidList.size(); ++i) {
                    if (i > 0) {
                        sql.addSql("    ,?");
                    } else {
                        sql.addSql("    ?");
                    }
                    sql.addIntValue(bfiSidList.get(i));
                }
                sql.addSql("    )");
            }

            if (isHierarchy) {
                if (!admin) {
                    sql.addSql("      and");
                }
                sql.addSql("    BBS_FOR_INF.BFI_LEVEL = ?");
                sql.addIntValue(1);
            }

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("forumCnt");
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
     * <br>[機  能] フォーラムの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param bfiSidList フォーラムSIDリスト
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    public int getForumSearchCount(
            BulletinSearchModel searchMdl, List<Integer> bfiSidList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        if (bfiSidList == null || bfiSidList.size() < 1) {
            return count;
        }

        List < String > keywordList = searchMdl.getKeywordList();
        UDate limitDate = UDate.getInstanceStr(searchMdl.getNow().getDateString());

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BBS_FOR_INF.BFI_SID) as forumCnt");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID in (");
            sql.addSql("     select");
            sql.addSql("       BBS_THRE_INF.BFI_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_THRE_INF,");
            sql.addSql("       BBS_WRITE_INF");
            sql.addSql("     where");
            sql.addSql("       BBS_WRITE_INF.BTI_SID = BBS_THRE_INF.BTI_SID");


            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);

            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addSql("         )");

            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT_FR_DATE > ?");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            if (!searchMdl.isAdmin()) {
                sql.addSql("          and");
                sql.addSql("            (");
                sql.addSql("               BBS_THRE_INF.BTI_AUID = ?");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addSql("             or");
                sql.addSql("               BBS_THRE_INF.BFI_SID in (");
                sql.addSql("                 select");
                sql.addSql("                   BBS_FOR_ADMIN.BFI_SID");
                sql.addSql("                 from");
                sql.addSql("                   BBS_FOR_ADMIN");
                sql.addSql("                 where");
                sql.addSql("                   BBS_FOR_ADMIN.USR_SID = ?");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addSql("               )");
                sql.addSql("            )");
            }
            sql.addSql("         )");

            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT_DATE < ?");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            if (!searchMdl.isAdmin()) {
                sql.addSql("          and");
                sql.addSql("            (");
                sql.addSql("               BBS_THRE_INF.BTI_AUID = ?");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addSql("             or");
                sql.addSql("               BBS_THRE_INF.BFI_SID in (");
                sql.addSql("                 select");
                sql.addSql("                   BBS_FOR_ADMIN.BFI_SID");
                sql.addSql("                 from");
                sql.addSql("                   BBS_FOR_ADMIN");
                sql.addSql("                 where");
                sql.addSql("                   BBS_FOR_ADMIN.USR_SID = ?");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addSql("               )");
                sql.addSql("            )");
            }
            sql.addSql("         )");

            sql.addSql("       )");

            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");

            sql.addSql("           (");
            sql.addSql("             (");
            sql.addSql("             BBS_WRITE_INF.BWI_TYPE = 0");
            sql.addSql("             and");
            sql.addSql("             BBS_WRITE_INF.BWI_VALUE like '%"
                    + JDBCUtil.escapeForLikeSearch(keywordList.get(0))
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("             )");
            sql.addSql("           or");
            sql.addSql("             (");
            sql.addSql("             BBS_WRITE_INF.BWI_TYPE = 1");
            sql.addSql("             and");
            sql.addSql("             BBS_WRITE_INF.BWI_VALUE_PLAIN like '%"
                  + JDBCUtil.escapeForLikeSearch(keywordList.get(0))
                  + "%' ESCAPE '"
                  + JDBCUtil.def_esc
                  + "'");
            sql.addSql("             )");
            sql.addSql("           )");

            for (int i = 1; i < keywordList.size(); i++) {
                sql.addSql("           and");
                sql.addSql("           (");
                sql.addSql("             (");
                sql.addSql("             BBS_WRITE_INF.BWI_TYPE = 0");
                sql.addSql("             and");
                sql.addSql("             BBS_WRITE_INF.BWI_VALUE like '%"
                        + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
                sql.addSql("             )");
                sql.addSql("           or");
                sql.addSql("             (");
                sql.addSql("             BBS_WRITE_INF.BWI_TYPE = 1");
                sql.addSql("             and");
                sql.addSql("             BBS_WRITE_INF.BWI_VALUE_PLAIN like '%"
                      + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                      + "%' ESCAPE '"
                      + JDBCUtil.def_esc
                      + "'");
                sql.addSql("             )");
                sql.addSql("           )");
            }

            sql.addSql("          )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           BBS_THRE_INF.BTI_TITLE like '%"
                    + JDBCUtil.escapeForLikeSearch(keywordList.get(0))
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            for (int i = 1; i < keywordList.size(); i++) {
                sql.addSql("   and");
                sql.addSql("           BBS_THRE_INF.BTI_TITLE like '%"
                        + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("   )");
//            if (!searchMdl.isAdmin()) {
                sql.addSql("  and");
                sql.addSql("    BBS_FOR_INF.BFI_SID in (");
                for (int i = 0; i < bfiSidList.size(); ++i) {
                    if (i > 0) {
                        sql.addSql("    ,?");
                    } else {
                        sql.addSql("    ?");
                    }
                    sql.addIntValue(bfiSidList.get(i));
                }
                sql.addSql("    )");
//            }
            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("forumCnt");
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
     * <br>[機  能] フォーラムコンボに表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者 false:一般ユーザ
     * @return フォーラム情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> getForumList(int userSid,
                                                boolean admin)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BbsForInfModel > ret = new ArrayList < BbsForInfModel >();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_CMT as BFI_CMT,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT as BFI_SORT");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF");
            if (!admin) {
                sql.addSql("  where");
                sql.addSql("    BBS_FOR_INF.BFI_SID in (");
                sql.addSql("      select BFI_SID from BBS_FOR_MEM");
                sql.addSql("      where");
                sql.addSql("        BBS_FOR_MEM.USR_SID = ?");
                sql.addSql("      or");
                sql.addSql("        BBS_FOR_MEM.GRP_SID in (");
                sql.addSql("          select GRP_SID from CMN_BELONGM");
                sql.addSql("          where CMN_BELONGM.USR_SID = ?");
                sql.addSql("        )");
                sql.addSql("    )");

                sql.addIntValue(userSid);
                sql.addIntValue(userSid);
            }
            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_SORT, BBS_FOR_INF.BFI_NAME");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BbsForInfModel forumMdl = new BbsForInfModel();
                forumMdl.setBfiSid(rs.getInt("BFI_SID"));
                forumMdl.setBfiName(rs.getString("BFI_NAME"));
                forumMdl.setBfiCmt(rs.getString("BFI_CMT"));
                ret.add(forumMdl);
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
     * <br>[機  能] フォーラム一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @param now 現在日時
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param admUsr システム管理者フラグ
     * @param parentSid 親フォーラムSID -1:指定なし
     * @param usersForumWrite ユーザを編集権限のメンバーとするフォーラム
     * @param usersForumAll ユーザをメンバーとするフォーラム
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getForumList(
            int userSid, boolean adminDisp, UDate now,
            int newCnt, int start, int end, boolean admUsr, int parentSid,
            List<BbsForInfModel> usersForumWrite, List<BbsForInfModel> usersForumAll)
                    throws SQLException {
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel >();

        if (!adminDisp
                && (usersForumAll == null || usersForumAll.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_CMT as BFI_CMT,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT as BFI_SORT,");
            sql.addSql("    BBS_FOR_INF.BFI_REPLY as BFI_REPLY,");
            sql.addSql("    BBS_FOR_INF.BIN_SID as BIN_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_MREAD as BFI_MREAD,");
            sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID as BFI_PARENT_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_LEVEL as BFI_LEVEL,");
            sql.addSql("    BBS_FOR_SUM.BFS_THRE_CNT as THRE_CNT,");
            sql.addSql("    BBS_FOR_SUM.BFS_WRT_CNT as WRITE_CNT,");
            sql.addSql("    BBS_FOR_SUM.BFS_WRT_DATE as BFS_WRT_DATE,");
            sql.addSql("    BBS_FOR_SUM.BFS_SIZE as BFS_SIZE,");
            sql.addSql("    BBSVIEW.viewCnt as viewCnt,");
            sql.addSql("    RSVTHRE.rsvThreCnt as rsvThreCnt,");
            sql.addSql("    DEL_CNT.delCnt as delCnt");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_SUM,");
            sql.addSql("    BBS_FOR_INF");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID as BFI_SID,");
            sql.addSql("         count(BBS_THRE_VIEW.BTI_SID) as viewCnt");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW,");
            sql.addSql("         BBS_THRE_INF");
            sql.addSql("       where");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("          or");
            sql.addSql("            (");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("             and");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("            )");
            sql.addSql("         )");
            sql.addSql("       group by");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID");
            sql.addSql("      ) BBSVIEW");
            sql.addSql("     on");
            sql.addSql("       BBS_FOR_INF.BFI_SID = BBSVIEW.BFI_SID");
            sql.addSql("    left join ");
            sql.addSql("      (select ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID as BFI_SID, ");
            sql.addSql("         count(BBS_THRE_INF.BTI_SID) as delCnt ");
            sql.addSql("       from ");
            sql.addSql("         BBS_FOR_SUM, ");
            sql.addSql("         BBS_THRE_INF ");
            sql.addSql("       where ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID = BBS_THRE_INF.BFI_SID ");
            sql.addSql("       and ");
            sql.addSql("         BBS_FOR_SUM.BFS_EDATE < BBS_THRE_INF.BTI_LIMIT_DATE ");
            sql.addSql("       and ");
            sql.addSql("         BBS_THRE_INF.BTI_LIMIT_DATE < ? ");
            sql.addSql("       group by ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID ");
            sql.addSql("      ) DEL_CNT");
            sql.addSql("    on");
            sql.addSql("      BBS_FOR_INF.BFI_SID = DEL_CNT.BFI_SID ");
            sql.addSql("     left join");
            sql.addSql("       (");
            sql.addSql("        select");
            sql.addSql("          BTI2.BFI_SID as BFI_SID2,");
            sql.addSql("          count(BTI2.BFI_SID) as rsvThreCnt");
            sql.addSql("        from");
            sql.addSql("          BBS_THRE_INF BTI2");
            sql.addSql("        where");
            sql.addSql("          BTI2.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BTI2.BTI_LIMIT_FR_DATE > ?");

            if (!admUsr) {
                sql.addSql("        and");

                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addSql("        (");
                    sql.addSql("          (");
                    sql.addSql("           BTI2.BTI_AUID = ? ");
                    sql.addSql("          and");
                    sql.addSql("           BTI2.BFI_SID ");
                    sql.addSql("             in ( ");

                    for (int idx = 0; idx < usersForumWrite.size(); idx++) {
                        if (idx > 0) {
                            sql.addSql("   ,?");
                        } else {
                            sql.addSql("   ?");
                        }
                    }

                    sql.addSql("             ) ");
                    sql.addSql("          ) ");

                    sql.addSql("         or ");
                }

                sql.addSql("           BTI2.BFI_SID ");
                sql.addSql("             in ( ");
                sql.addSql("                 select ");
                sql.addSql("                   BBS_FOR_ADMIN.BFI_SID ");
                sql.addSql("                 from ");
                sql.addSql("                   BBS_FOR_ADMIN ");
                sql.addSql("                 where ");
                sql.addSql("                   BBS_FOR_ADMIN.USR_SID = ? ");
                sql.addSql("                 ) ");

                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addSql("        )");
                }
            }

            sql.addSql("        group by");
            sql.addSql("          BTI2.BFI_SID");
            sql.addSql("        ) RSVTHRE");
            sql.addSql("     on");
            sql.addSql("       BBS_FOR_INF.BFI_SID = RSVTHRE.BFI_SID2");

            sql.addSql("  where");
            if (!adminDisp) {
                sql.addSql("    BBS_FOR_INF.BFI_SID in (");

                for (int idx = 0; idx < usersForumAll.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("   ,?");
                    } else {
                        sql.addSql("   ?");
                    }
                }

                sql.addSql("    )");
                sql.addSql("  and");
            }
            if (parentSid > -1) {
                sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID = ?");
                sql.addSql("  and");
            }
            sql.addSql("    BBS_FOR_SUM.BFI_SID = BBS_FOR_INF.BFI_SID");

            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_LEVEL,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME");
            if (end != 0) {
                sql.setPagingValue(start, (end - start + 1));
            }

            sql.addIntValue(GSConstBulletin.NEWUSER_THRE_VIEW_YES);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(now.getDateString());
            /*limitFrDate.setMaxHhMmSs();*/
            sql.addDateValue(limitFrDate);
            if (!admUsr) {
                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addIntValue(userSid);
                    for (BbsForInfModel bfiMdlWrite : usersForumWrite) {
                        sql.addIntValue(bfiMdlWrite.getBfiSid());
                    }
                }
                sql.addIntValue(userSid);
            }

            if (!adminDisp) {
                for (BbsForInfModel bfiMdlAll : usersForumAll) {
                    sql.addIntValue(bfiMdlAll.getBfiSid());
                }
            }

            if (parentSid > -1) {
                sql.addIntValue(parentSid);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(rs.getInt("BFI_SID"));
                btMdl.setBfiName(rs.getString("BFI_NAME"));
                btMdl.setBfiSort(rs.getInt("BFI_SORT"));
                btMdl.setBfiCmt(rs.getString("BFI_CMT"));
                btMdl.setBfiCmtView(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBfiCmt()), ""));
                btMdl.setBfiReply(rs.getInt("BFI_REPLY"));
                btMdl.setImgBinSid(rs.getLong("BIN_SID"));
                btMdl.setBfiMread(rs.getInt("BFI_MREAD"));
                btMdl.setParentForumSid(rs.getInt("BFI_PARENT_SID"));
                btMdl.setForumLevel(rs.getInt("BFI_LEVEL"));
                btMdl.setBfsThreCnt(rs.getInt("THRE_CNT"));
                btMdl.setWriteCnt(rs.getInt("WRITE_CNT"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BFS_WRT_DATE"));
                btMdl.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));

                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setReadedCnt(rs.getInt("viewCnt"));
                //未読/既読を設定
                if ((btMdl.getBfsThreCnt() - rs.getInt("delCnt")) <= btMdl.getReadedCnt()) {
                    btMdl.setReadFlg(BulletinDspModel.READFLG_READ);
                }
                btMdl.setBfsSize(rs.getLong("BFS_SIZE"));

                //掲示予定件数
                btMdl.setRsvThreCnt(rs.getInt("rsvThreCnt"));

                ret.add(btMdl);
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
     * <br>[機  能] フォーラム情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getForumData(int bfiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BulletinDspModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_CMT as BFI_CMT,");
            sql.addSql("    BBS_FOR_INF.BFI_REPLY as BFI_REPLY,");
            sql.addSql("    BBS_FOR_INF.BFI_READ as BFI_READ,");
            sql.addSql("    BBS_FOR_INF.BIN_SID as BIN_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_MREAD as BFI_MREAD,");
            sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID as BFI_PARENT_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_LEVEL as BFI_LEVEL,");
            sql.addSql("    BBS_FOR_INF.BFI_TEMPLATE_KBN as BFI_TEMPLATE_KBN,");
            sql.addSql("    BBS_FOR_INF.BFI_TEMPLATE as BFI_TEMPLATE,");
            sql.addSql("    BBS_FOR_INF.BFI_TEMPLATE_TYPE as BFI_TEMPLATE_TYPE,");
            sql.addSql("    BBS_FOR_INF.BFI_TEMPLATE_WRITE as BFI_TEMPLATE_WRITE,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK as BFI_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK_SIZE as BFI_DISK_SIZE,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK as BFI_WARN_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK_TH as BFI_WARN_DISK_TH,");

            sql.addSql("    BBS_FOR_INF.BFI_LIMIT as BFI_LIMIT,");
            sql.addSql("    BBS_FOR_INF.BFI_UNLIMITED as BFI_UNLIMITED,");
            sql.addSql("    BBS_FOR_INF.BFI_LIMIT_DATE as BFI_LIMIT_DATE,");
            sql.addSql("    BBS_FOR_INF.BFI_KEEP as BFI_KEEP,");
            sql.addSql("    BBS_FOR_INF.BFI_KEEP_DATE_Y as BFI_KEEP_DATE_Y,");
            sql.addSql("    BBS_FOR_INF.BFI_KEEP_DATE_M as BFI_KEEP_DATE_M,");
            sql.addSql("    BBS_FOR_INF.BFI_FOLLOW_PARENT_MEM as BFI_FOLLOW_PARENT_MEM,");

            sql.addSql("    BBS_FOR_INF.BFI_LIMIT_ON as BFI_LIMIT_ON,");
            sql.addSql("    BBS_FOR_INF.BFI_MIN_DIV as BFI_MIN_DIV,");

            sql.addSql("    BBS_FOR_SUM.BFS_WRT_CNT as BFS_WRT_CNT");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF");
            sql.addSql("    left join");
            sql.addSql("      BBS_FOR_SUM");
            sql.addSql("    on");
            sql.addSql("      BBS_FOR_INF.BFI_SID = BBS_FOR_SUM.BFI_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID = ?");
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new BulletinDspModel();
                ret.setBfiSid(rs.getInt("BFI_SID"));
                ret.setBfiName(rs.getString("BFI_NAME"));
                ret.setBfiCmt(rs.getString("BFI_CMT"));
                ret.setBfiCmtView(
                        NullDefault.getString(StringUtilHtml.transToHTml(ret.getBfiCmt()), ""));
                ret.setBfiReply(rs.getInt("BFI_REPLY"));
                ret.setBfiRead(rs.getInt("BFI_READ"));
                ret.setBinSid(rs.getLong("BIN_SID"));
                ret.setBfiMread(rs.getInt("BFI_MREAD"));
                ret.setParentForumSid(rs.getInt("BFI_PARENT_SID"));
                ret.setForumLevel(rs.getInt("BFI_LEVEL"));
                ret.setBfiTemplateKbn(rs.getInt("BFI_TEMPLATE_KBN"));
                ret.setBfiTemplate(rs.getString("BFI_TEMPLATE"));
                ret.setBfiTemplateType(rs.getInt("BFI_TEMPLATE_TYPE"));
                ret.setBfiTemplateWrite(rs.getInt("BFI_TEMPLATE_WRITE"));
                ret.setBfiDisk(rs.getInt("BFI_DISK"));
                ret.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
                ret.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
                ret.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));

                ret.setBfiLimitOn(rs.getInt("BFI_LIMIT_ON"));
                ret.setBfiLimit(rs.getInt("BFI_LIMIT"));
                ret.setBfiUnlimited(rs.getInt("BFI_UNLIMITED"));
                ret.setBfiLimitDate(rs.getInt("BFI_LIMIT_DATE"));
                ret.setBfiKeep(rs.getInt("BFI_KEEP"));
                ret.setBfiKeepDateY(rs.getInt("BFI_KEEP_DATE_Y"));
                ret.setBfiKeepDateM(rs.getInt("BFI_KEEP_DATE_M"));
                ret.setFollowParentMemFlg(rs.getInt("BFI_FOLLOW_PARENT_MEM"));
                ret.setMinDiv(rs.getInt("BFI_MIN_DIV"));
                ret.setWriteCnt(rs.getInt("BFS_WRT_CNT"));
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
     * <br>[機  能] 既読スレッド数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @return 既読スレッド数
     * @throws SQLException SQL実行例外
     */
    public int getForumNum(int bfiSid, int btiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(BBS_THRE_VIEW.USR_SID) as VIEW_CNT");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("         (");
            sql.addSql("          select");
            sql.addSql("            BFI_SID,");
            sql.addSql("            USR_SID,");
            sql.addSql("            count(*)");
            sql.addSql("          from");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                BFI_SID,");
            sql.addSql("                USR_SID");
            sql.addSql("              from");
            sql.addSql("                BBS_FOR_MEM");
            sql.addSql("              where USR_SID > 0");
            sql.addSql("            union all");
            sql.addSql("              select");
            sql.addSql("                BBS_FOR_MEM.BFI_SID as BFI_SID,");
            sql.addSql("                CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("              from");
            sql.addSql("                BBS_FOR_MEM,");
            sql.addSql("                CMN_BELONGM");
            sql.addSql("              where");
            sql.addSql("                BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("            ) BBS_MEM");
            sql.addSql("            group by BFI_SID, USR_SID");
            sql.addSql("         ) FORUM_MEM,");
            sql.addSql("    BBS_THRE_VIEW");
            sql.addSql("  where");
            sql.addSql("    CMN_USRM.USR_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("        ( BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN = ? )");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM.USR_SID = BBS_THRE_VIEW.USR_SID");
            sql.addSql("  and");
            sql.addSql("    FORUM_MEM.USR_SID = BBS_THRE_VIEW.USR_SID");
            sql.addSql("  and");
            sql.addSql("    FORUM_MEM.BFI_SID=?");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_VIEW.BTI_SID=?");

            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(bfiSid);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("VIEW_CNT");
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
     * <br>[機  能] スレッド情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getThreadData(int btiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BulletinDspModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_CMT as BFI_CMT,");
            sql.addSql("    BBS_FOR_INF.BFI_REPLY as BFI_REPLY,");
            sql.addSql("    BBS_FOR_INF.BFI_MREAD as BFI_MREAD,");
            sql.addSql("    BBS_FOR_INF.BFI_LIMIT_ON as BFI_LIMIT_ON,");
            sql.addSql("    BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_AUID as BTI_AUID,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT as BTI_LIMIT,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_FR_DATE as BTI_LIMIT_FR_DATE,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_DATE as BTI_LIMIT_DATE,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      BBS_THRE_SUM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID = BBS_THRE_INF.BFI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = ?");

            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new BulletinDspModel();
                ret.setBfiSid(rs.getInt("BFI_SID"));
                ret.setBfiName(rs.getString("BFI_NAME"));
                ret.setBfiCmt(rs.getString("BFI_CMT"));
                ret.setBfiLimitOn(rs.getInt("BFI_LIMIT_ON"));
                ret.setBfiCmtView(
                        NullDefault.getString(StringUtilHtml.transToHTml(ret.getBfiCmt()), ""));
                ret.setBfiReply(rs.getInt("BFI_REPLY"));
                ret.setBfiMread(rs.getInt("BFI_MREAD"));
                ret.setBtiSid(rs.getInt("BTI_SID"));
                ret.setBtiTitle(rs.getString("BTI_TITLE"));
                ret.setBtiLimit(rs.getInt("BTI_LIMIT"));
                ret.setBtiLimitFrDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_FR_DATE")));
                ret.setBtiLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_DATE")));
                ret.setAddUserSid(rs.getInt("BTI_AUID"));
                ret.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                ret.setWriteCnt(rs.getInt("BTS_WRT_CNT"));
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
     * <br>[機  能] フォーラム内の掲示予定スレッドの件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param usrSid ユーザSID
     * @param allFlg 全件フラグ true:全件 false:編集可能のみ
     * @param now 現在日時
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int countRsvThreData(int bfiSid, int usrSid, boolean allFlg, UDate now)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(*) CNT");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("  where");
            sql.addSql("    BFI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BTI_LIMIT = ?");
            sql.addSql("  and");
            sql.addSql("    BTI_LIMIT_FR_DATE > ?");

            if (!allFlg) {
                sql.addSql("  and");
                sql.addSql("  (");
                sql.addSql("     BTI_AUID = ?");
                sql.addSql("   or");
                sql.addSql("     BFI_SID ");
                sql.addSql("       in ( ");
                sql.addSql("            select ");
                sql.addSql("              BBS_FOR_ADMIN.BFI_SID ");
                sql.addSql("            from ");
                sql.addSql("              BBS_FOR_ADMIN ");
                sql.addSql("            where ");
                sql.addSql("              BBS_FOR_ADMIN.USR_SID = ? ");
                sql.addSql("           ) ");
                sql.addSql("    ) ");
            }

            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(now.getTimeStamp());
            sql.addDateValue(limitFrDate);

            if (!allFlg) {
                sql.addIntValue(usrSid);
                sql.addIntValue(usrSid);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return cnt;
    }

    /**
     * <br>[機  能] フォーラム情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行例外
     */
    public void deleteForumData(int bfiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            SqlBuffer sql = null;

            //投稿情報の添付情報を削除する
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       BBS_BIN.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_BIN");
            sql.addSql("     where");
            sql.addSql("       BBS_BIN.BWI_SID in (");
            sql.addSql("         select");
            sql.addSql("           BBS_WRITE_INF.BWI_SID");
            sql.addSql("         from");
            sql.addSql("           BBS_WRITE_INF");
            sql.addSql("         where");
            sql.addSql("           BBS_WRITE_INF.BFI_SID = ?");
            sql.addSql("       )");
            sql.addSql("   )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            //投稿情報を削除する
            sql = new SqlBuffer();
            sql.addSql(" delete from BBS_BIN");
            sql.addSql(" where");
            sql.addSql("   BWI_SID in (");
            sql.addSql("     select");
            sql.addSql("       BWI_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_WRITE_INF");
            sql.addSql("     where");
            sql.addSql("       BFI_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            //スレッド集計情報を削除する
            sql = new SqlBuffer();
            sql.addSql(" delete from BBS_THRE_SUM");
            sql.addSql(" where");
            sql.addSql("   BTI_SID in (");
            sql.addSql("     select");
            sql.addSql("       BTI_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_THRE_INF");
            sql.addSql("     where");
            sql.addSql("       BFI_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            //アイコン情報を削除する
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       BBS_FOR_INF.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_FOR_INF");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_INF.BFI_SID = ?");
            sql.addSql("   )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            //フォーラム情報を削除する
            String[] forum_table = new String[] {
                                        "BBS_FOR_INF",   //フォーラム情報
                                        "BBS_FOR_SUM",   //フォーラム集計情報
                                        "BBS_FOR_MEM",   //フォーラムメンバー
                                        "BBS_THRE_INF",  //スレッド情報
                                        "BBS_THRE_VIEW", //スレッド閲覧情報
                                        "BBS_WRITE_INF"  //投稿情報
                                            };
            for (String tblName : forum_table) {
                sql = new SqlBuffer();
                sql.addSql(" delete from");
                sql.addSql("   " + tblName);
                sql.addSql(" where");
                sql.addSql("   BFI_SID = ?");
                sql.addIntValue(bfiSid);

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <br>[機  能] 書き込み日時の最小値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSidList フォーラムSIDリスト
     * @param admin 管理者か否か true:管理者 false:一般ユーザ
     * @return 書き込み日時の最小値
     * @throws SQLException SQL実行例外
     */
    public UDate getOldestWriteDate(
            List<Integer> bfiSidList, boolean admin) throws SQLException {

        UDate ret = null;
        if (!admin
                && (bfiSidList == null || bfiSidList.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    min(BBS_WRITE_INF.BWI_ADATE) as oldDate");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF");
            if (!admin) {
                sql.addSql("  where");
                sql.addSql("    BBS_WRITE_INF.BFI_SID in (");

                for (int idx = 0; idx < bfiSidList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("    ,?");
                    } else {
                        sql.addSql("    ?");
                    }
                    sql.addIntValue(bfiSidList.get(idx));
                }
                sql.addSql("    )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = UDate.getInstanceTimestamp(rs.getTimestamp("oldDate"));
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
     * <br>[機  能] 指定されたフォーラムのスレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param now 現在日時
     * @return スレッド件数
     * @throws SQLException SQL実行例外
     */
    public int getThreadCount(int bfiSid, UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(BTI_SID) as threCount");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("  where");
            sql.addSql("    BFI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("threCount");
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
     * <br>[機  能] スレッド一覧を取得する
     * <br>[解  説] モバイル用
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param now 現在日時
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param memberForumSid メンバー情報を保持するフォーラムのSID
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getThreadList(
            int bfiSid, int userSid, UDate now,
            int newCnt, int start, int end, int memberForumSid)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");
            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN,");
            sql.addSql("    THRE_VIEW_CNT.VIEW_CNT as VIEW_CNT");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BTI_SID,");
            sql.addSql("         BIV_VIEW_KBN");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         USR_SID = ?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addSql("    left join");

            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID as BTI_SID,");
            sql.addSql("         count(BBS_THRE_VIEW.USR_SID) as VIEW_CNT");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM,");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         CMN_USRM.USR_JKBN = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql("       and");
            sql.addSql("        ( BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN = ? )");
            sql.addSql("       and");
            sql.addSql("         CMN_USRM.USR_SID = BBS_THRE_VIEW.USR_SID");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               BBS_FOR_MEM.USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.USR_SID > 0");
            sql.addSql("           )");
            sql.addSql("         or");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM,");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           )");
            sql.addSql("         )");

            sql.addSql("       group by");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID");
            sql.addSql("      ) THRE_VIEW_CNT");


            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW_CNT.BTI_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("  and");

            sql.addSql("    (");
            sql.addSql("       BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");

            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("  order by");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE desc");

            //パラメータ
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(memberForumSid);
            sql.addIntValue(memberForumSid);
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitDate = new UDate();
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            for (int index = 1; rs.next(); index++) {
                if  (index > end) {
                    break;
                }

                if (index >= start) {
                    BulletinDspModel btMdl = new BulletinDspModel();
                    btMdl.setBtiSid(rs.getInt("BTI_SID"));
                    btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                    btMdl.setWriteCnt(rs.getInt("BTS_WRT_CNT"));
                    UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                    btMdl.setWriteDate(bfsWrtDate);
                    if (bfsWrtDate != null) {
                        btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));

                        if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                            btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                        }
                    }
                    btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                    btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));

                    btMdl.setGrpSid(rs.getInt("BTI_EGID"));
                    btMdl.setGrpName(rs.getString("GRP_NAME"));
                    btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));
                    if (btMdl.getGrpSid() > 0) {
                        btMdl.setUserName(btMdl.getGrpName());
                        btMdl.setUserJkbn(btMdl.getGrpJkbn());
                    } else {
                        btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                        btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                        btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                    }

                    btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));
                    btMdl.setReadedCnt(rs.getInt("VIEW_CNT"));

                    ret.add(btMdl);
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
     * <br>[機  能] ポータル用のスレッド一覧を取得する
     * <br>[解  説] PC用
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param now 現在日時
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getPtlThreadList(int bfiSid, int userSid,
                                                    UDate now,
                                                    int newCnt, int start, int end,
                                                    int sortKey, int orderKey)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("    CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("    BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");
            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BTI_SID,");
            sql.addSql("         BIV_VIEW_KBN");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         USR_SID = ?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("  order by");

            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (orderKey != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            switch (sortKey) {
            case GSConstBulletin.SORT_KEY_THRED:
                //タイトル
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_TOUKOU:
                //投稿数
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_CNT " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_ETSURAN:
                //閲覧数
                sql.addSql("     THRE_VIEW_CNT.VIEW_CNT " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_USER:
                //投稿者
                sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_SAISHIN:
                //最新書き込み
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            default:
                sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE desc");
                break;
            }

            sql.addIntValue(userSid);
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitDate = new UDate();
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            sql.setPagingValue(start - 1, end - start + 1);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                btMdl.setWriteCnt(rs.getInt("BTS_WRT_CNT"));
                btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                btMdl.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));

                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));

                btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));

                btMdl.setGrpSid(rs.getInt("BTI_EGID"));
                btMdl.setGrpName(rs.getString("GRP_NAME"));
                btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));

                ret.add(btMdl);
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
     * <br>[機  能] スレッド一覧を取得する
     * <br>[解  説] PC用
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param now 現在日時
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param memberForumSid メンバー情報を保持するフォーラムのSID
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getThreadList(
            int bfiSid, int userSid, UDate now,
            int newCnt, int start, int end,
            int sortKey, int orderKey, int memberForumSid)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN  as  USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG  as  USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI  as  USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI  as  USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SEI_KN  as  USI_SEI_KN,");
            sql.addSql("    CMN_USRM_INF.USI_MEI_KN  as  USI_MEI_KN,");
            sql.addSql("    BBS_THRE_INF.BTI_SID  as  BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE  as  BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_EGID  as  BTI_EGID,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE  as  BTI_IMPORTANCE,");
            sql.addSql("    CMN_GROUPM.GRP_NAME  as  GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN  as  GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT  as  BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE  as  BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_SIZE  as  BTS_SIZE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG  as  BTS_TEMPFLG,");
            sql.addSql("    BBS_THRE_VIEW.BIV_VIEW_KBN  as  BIV_VIEW_KBN,");
            sql.addSql("    COALESCE(THRE_VIEW_CNT.VIEW_CNT,0) as VIEW_CNT ");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_SUM");
            sql.addSql("    inner join");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("      on");
            sql.addSql("        BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("    inner join");
            sql.addSql("    CMN_USRM");
            sql.addSql("      on");
            sql.addSql("        BBS_THRE_INF.BTI_AUID = CMN_USRM.USR_SID");
            sql.addSql("    inner join");
            sql.addSql("    CMN_USRM_INF");
            sql.addSql("      on");
            sql.addSql("        BBS_THRE_INF.BTI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("    left outer join");
            sql.addSql("    CMN_GROUPM");
            sql.addSql("      on");
            sql.addSql("        BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    left outer join");
            sql.addSql("    BBS_THRE_VIEW");
            sql.addSql("      on");
            sql.addSql("        BBS_THRE_INF.BTI_SID = BBS_THRE_VIEW.BTI_SID");
            sql.addSql("        and");
            sql.addSql("        BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("    left outer join");

            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID as BTI_SID,");
            sql.addSql("         count(BBS_THRE_VIEW.USR_SID) as VIEW_CNT");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM,");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         CMN_USRM.USR_JKBN = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN in ( ?, ? )");
            sql.addSql("       and");
            sql.addSql("         CMN_USRM.USR_SID = BBS_THRE_VIEW.USR_SID");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               BBS_FOR_MEM.USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.USR_SID > 0");
            sql.addSql("           )");
            sql.addSql("         or");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM,");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addSql("       group by");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID");
            sql.addSql("      ) THRE_VIEW_CNT");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW_CNT.BTI_SID");

            sql.addSql("    where");
            sql.addSql("      BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("    and");
            sql.addSql("    (  ");
            sql.addSql("      BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("      or");
            sql.addSql("      ( ");
            sql.addSql("        BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("        ?  BETWEEN   BBS_THRE_INF.BTI_LIMIT_FR_DATE");
            sql.addSql("               and   BBS_THRE_INF.BTI_LIMIT_DATE");
            sql.addSql("      )");
            sql.addSql("    )");

            sql.addSql("  order by");

            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (orderKey != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            switch (sortKey) {
            case GSConstBulletin.SORT_KEY_THRED:
                //タイトル
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_TOUKOU:
                //投稿数
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_CNT " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);

                break;

            case GSConstBulletin.SORT_KEY_ETSURAN:
                //閲覧数
                sql.addSql("     VIEW_CNT " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            case GSConstBulletin.SORT_KEY_USER:
                //投稿者
                sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            case GSConstBulletin.SORT_KEY_SIZE:
                //サイズ
                sql.addSql("     BBS_THRE_SUM.BTS_SIZE " + strOrder + ",");
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            case GSConstBulletin.SORT_KEY_SAISHIN:
                //最新書き込み
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            default:
                sql.addSql("     BBS_THRE_SUM.BTS_WRT_DATE desc ,");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE asc");
                break;
            }

            sql.addIntValue(userSid);
            
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(memberForumSid);
            sql.addIntValue(memberForumSid);

            sql.addIntValue(bfiSid);
            UDate limitDate = new UDate();
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);

            sql.setPagingValue(start - 1, end - start + 1);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                btMdl.setGrpSid(rs.getInt("BTI_EGID"));
                btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                btMdl.setGrpName(rs.getString("GRP_NAME"));
                btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));
                btMdl.setWriteCnt(rs.getInt("BTS_WRT_CNT"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                btMdl.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));

                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setBtsSize(rs.getLong("BTS_SIZE"));
                btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));
                btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));
                btMdl.setReadedCnt(rs.getInt("VIEW_CNT"));

                ret.add(btMdl);
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
     * <br>[機  能] スレッド一覧を取得する
     * <br>[解  説] PC用
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param admin 管理者フラグ
     * @param memberForumSid メンバー情報を保持するフォーラムのSID
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getRsvThreadList(
            int bfiSid, int userSid, int newCnt, int start, int end,
            int sortKey, int orderKey, boolean admin, int memberForumSid)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("    CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("    BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");

            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_FR_DATE as BTI_LIMIT_FR_DATE,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_DATE as BTI_LIMIT_DATE,");

            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");

            sql.addSql("    BBS_THRE_SUM.BTS_SIZE as BTS_SIZE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");

            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN,");
            sql.addSql("    THRE_VIEW_CNT.VIEW_CNT as VIEW_CNT");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BTI_SID,");
            sql.addSql("         BIV_VIEW_KBN");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         USR_SID = ?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID as BTI_SID,");
            sql.addSql("         count(BBS_THRE_VIEW.USR_SID) as VIEW_CNT");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM,");
            sql.addSql("         BBS_THRE_VIEW");

            sql.addSql("       where");
            sql.addSql("         CMN_USRM.USR_JKBN = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql("       and");
            sql.addSql("        ( BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN = ? )");
            sql.addSql("       and");
            sql.addSql("         CMN_USRM.USR_SID = BBS_THRE_VIEW.USR_SID");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               BBS_FOR_MEM.USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.USR_SID > 0");
            sql.addSql("           )");
            sql.addSql("         or");
            sql.addSql("           BBS_THRE_VIEW.USR_SID in (");
            sql.addSql("             select");
            sql.addSql("               CMN_BELONGM.USR_SID");
            sql.addSql("             from");
            sql.addSql("               BBS_FOR_MEM,");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             where");
            sql.addSql("               BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addSql("       group by");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID");
            sql.addSql("      ) THRE_VIEW_CNT");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW_CNT.BTI_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("  and");

            sql.addSql("    (");
            sql.addSql("       BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("     and");
            sql.addSql("       BBS_THRE_INF.BTI_LIMIT_FR_DATE > ?");
            sql.addSql("    )");


            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_AUID = CMN_USRM_INF.USR_SID");

            if (!admin) {
                sql.addSql("  and");
                sql.addSql("  (");
                sql.addSql("     BBS_THRE_INF.BTI_AUID = ?");
                sql.addSql("   or");
                sql.addSql("     BBS_THRE_INF.BFI_SID ");
                sql.addSql("       in ( ");
                sql.addSql("            select ");
                sql.addSql("              BBS_FOR_ADMIN.BFI_SID ");
                sql.addSql("            from ");
                sql.addSql("              BBS_FOR_ADMIN ");
                sql.addSql("            where ");
                sql.addSql("              BBS_FOR_ADMIN.USR_SID = ? ");
                sql.addSql("          ) ");
                sql.addSql("  )");
            }
            sql.addSql("  order by");

            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (orderKey != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            switch (sortKey) {
            case GSConstBulletin.SORT_KEY_THRED:
                //タイトル
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_FR_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE " + timeOrder);
                break;

            case GSConstBulletin.SORT_KEY_USER:
                //投稿者
                sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_FR_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);

                break;

            case GSConstBulletin.SORT_KEY_SIZE:
                //サイズ
                sql.addSql("     BBS_THRE_SUM.BTS_SIZE " + strOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_FR_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            case GSConstBulletin.SORT_KEY_LIMIT_FR:
                //掲示開始日
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_FR_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);

                break;

            case GSConstBulletin.SORT_KEY_LIMIT_TO:
                //掲示終了日
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE " + timeOrder + ",");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE " + strOrder);
                break;

            default:
                sql.addSql("    BBS_THRE_INF.BTI_LIMIT_FR_DATE asc ,");
                sql.addSql("     BBS_THRE_INF.BTI_LIMIT_DATE asc ,");
                sql.addSql("     BBS_THRE_INF.BTI_TITLE desc");
                break;
            }

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(memberForumSid);
            sql.addIntValue(memberForumSid);

            sql.addIntValue(bfiSid);

            UDate limitDate = new UDate();
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);

            if (!admin) {
                sql.addIntValue(userSid);
                sql.addIntValue(userSid);
            }

            if (end != 0) {
                sql.setPagingValue(start - 1, end - start + 1);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(bfiSid);
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                btMdl.setGrpSid(rs.getInt("BTI_EGID"));
                btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                btMdl.setGrpName(rs.getString("GRP_NAME"));
                btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));
                btMdl.setWriteCnt(rs.getInt("BTS_WRT_CNT"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                btMdl.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));

                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setBtsSize(rs.getLong("BTS_SIZE"));
                btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));
                btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));
                btMdl.setReadedCnt(rs.getInt("VIEW_CNT"));

                UDate btiLimitFrDate =
                        UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_FR_DATE"));
                btMdl.setBtiLimitFrDate(btiLimitFrDate);
                if (btiLimitFrDate != null) {
                    btMdl.setStrBtiLimitFrDate(__createDateNoTimeStr(btiLimitFrDate));
                }

                UDate btiLimitDate =
                        UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_DATE"));
                btMdl.setBtiLimitDate(btiLimitDate);
                if (btiLimitDate != null) {
                    btMdl.setStrBtiLimitDate(__createDateNoTimeStr(btiLimitDate));
                }

                ret.add(btMdl);
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
     * <br>[機  能] 未読スレッド一覧件数(インフォメーションメッセージ用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupSidList メンバー情報を保持するフォーラムのリスト
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public int getThreadListCnt(int userSid, List<Integer> groupSidList) throws SQLException {
        int ret = 0;
        if (groupSidList == null || groupSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select");
            sql.addSql("    count(BBS_THRE_INF.BTI_SID) as CNT");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("  where");

            sql.addSql("    BBS_THRE_INF.BFI_SID in (");
            for (int idx = 0; idx < groupSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("   ,?");
                } else {
                    sql.addSql("   ?");
                }
            }
            sql.addSql("    )");

            sql.addSql("  and");
            sql.addSql("    not exists");
            sql.addSql("     (");
            sql.addSql("       select 1 from BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         BIV_VIEW_KBN = 1");
            sql.addSql("       and");
            sql.addSql("         USR_SID = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_INF.BTI_SID = BBS_THRE_VIEW.BTI_SID");
            sql.addSql("     )");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");
            for (int forumSid : groupSidList) {
                sql.addIntValue(forumSid);
            }
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
                       log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] スレッド一覧(未読&既読)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param adminFlg 管理者フラグ true:管理者 false:一般ユーザ
     * @param viewCnt 表示件数
     * @param newCnt new表示日数
     * @param chkedDsp 既読表示 1=非表示 0=表示
     * @param memberForumList メンバー情報を保持するフォーラムのリスト
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getThreadList(int userSid, boolean adminFlg,
            int viewCnt, int newCnt, int chkedDsp, List<Integer> memberForumList)
                    throws SQLException {

        return getThreadList(userSid, adminFlg, viewCnt, newCnt, null, chkedDsp, memberForumList);

    }

    /**
     * <br>[機  能] スレッド一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param adminFlg 管理者フラグ true:管理者 false:一般ユーザ
     * @param viewCnt 表示件数
     * @param newCnt new表示日数
     * @param bfiSid フォーラムSID nullの場合は全てのフォーラム
     * @param chkedDsp 確認済み投稿の表示有無 1=非表示 0=表示
     * @param memberForumList メンバー情報を保持するフォーラムのリスト
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BulletinDspModel> getThreadList(int userSid, boolean adminFlg,
            int viewCnt, int newCnt, Integer bfiSid,
            int chkedDsp, List<Integer> memberForumList)
                    throws SQLException {
        ArrayList < BulletinDspModel > ret = new ArrayList < BulletinDspModel >();
        if (!adminFlg
                && (memberForumList == null || memberForumList.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("    THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");
            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN");
            sql.addSql("  from");

            //FROM
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    (");
            sql.addSql("     select BFI_SID, BTI_SID, BTI_TITLE, BTI_EGID, BTI_IMPORTANCE");
            sql.addSql("     from BBS_THRE_INF");
            sql.addSql("     where");
            if (chkedDsp == GSConstBulletin.BBS_CHECKED_NOT_DSP) {
                sql.addSql("       not exists (");
                sql.addSql("         select 1 from BBS_THRE_VIEW");
                sql.addSql("         where");
                sql.addSql("           BBS_THRE_VIEW.BIV_VIEW_KBN = 1");
                sql.addSql("         and");
                sql.addSql("           BBS_THRE_VIEW.USR_SID = ?");
                sql.addSql("         and");
                sql.addSql("           BBS_THRE_INF.BTI_SID = BBS_THRE_VIEW.BTI_SID");
                sql.addSql("       )");
                sql.addSql("     and");
                sql.addIntValue(userSid);
            }

            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            sql.addSql("        or");
            sql.addSql("          (");
            sql.addSql("             BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_FR_DATE <= ? ");
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_DATE >= ? ");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    ) THRE_INF");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("        select");
            sql.addSql("          BTI_SID,");
            sql.addSql("          BIV_VIEW_KBN");
            sql.addSql("        from");
            sql.addSql("          BBS_THRE_VIEW");
            sql.addSql("        where");
            sql.addSql("          USR_SID=?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addIntValue(userSid);

            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");

            //WHERE
            sql.addSql("  where");

            if (bfiSid != null) {
                sql.addSql("   THRE_INF.BFI_SID = ?");
                sql.addIntValue(bfiSid.intValue());
                sql.addSql(" and");
            }

            if (!adminFlg) {
                sql.addSql("    THRE_INF.BFI_SID in (");
                for (int idx = 0; idx < memberForumList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("   ,?");
                    } else {
                        sql.addSql("   ?");
                    }
                    sql.addIntValue(memberForumList.get(idx));
                }
              sql.addSql("    )");
              sql.addSql("  and");
            }

            sql.addSql("    THRE_INF.BFI_SID = BBS_FOR_INF.BFI_SID");
            sql.addSql("  and");
            sql.addSql("    THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_SUM.BTS_EUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addSql("  order by");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE desc");

            sql.setPagingValue(0, viewCnt);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            for (int index = 1; index <= viewCnt && rs.next(); index++) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(rs.getInt("BFI_SID"));
                btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBfiName(rs.getString("BFI_NAME"));
                btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));
                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinMainModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));

                btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));

                btMdl.setGrpSid(rs.getInt("BTI_EGID"));
                btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                btMdl.setGrpName(rs.getString("GRP_NAME"));
                btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));

                ret.add(btMdl);
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
     * <br>[機  能] 未読スレッド一覧を取得する(サブコンテンツ)
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param viewCnt 表示件数
     * @param groupSidList メンバー情報を保持するフォーラムのリスト
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getThreadList2(
            int userSid, int viewCnt, List<Integer> groupSidList)
                    throws SQLException {
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel >();
        if (groupSidList == null || groupSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BIN_SID as BIN_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT as BFI_SORT,");
            sql.addSql("    THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("    THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");
            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    (");
            sql.addSql("     select BFI_SID, BTI_SID, BTI_TITLE, BTI_EGID, BTI_IMPORTANCE");
            sql.addSql("     from BBS_THRE_INF");
            sql.addSql("     where");
            sql.addSql("       not exists (");
            sql.addSql("         select 1 from BBS_THRE_VIEW");
            sql.addSql("         where");
            sql.addSql("           BBS_THRE_VIEW.BIV_VIEW_KBN = 1");
            sql.addSql("         and");
            sql.addSql("           BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("         and");
            sql.addSql("           BBS_THRE_INF.BTI_SID = BBS_THRE_VIEW.BTI_SID");
            sql.addSql("       )");
            sql.addSql("     and");
            sql.addIntValue(userSid);
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            sql.addSql("        or");
            sql.addSql("          (");
            sql.addSql("             BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_DATE >= ?");
            sql.addSql("          )");
            sql.addSql("       )");

            sql.addSql("    ) THRE_INF");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");

            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("        select");
            sql.addSql("          BTI_SID,");
            sql.addSql("          BIV_VIEW_KBN");
            sql.addSql("        from");
            sql.addSql("          BBS_THRE_VIEW");
            sql.addSql("        where");
            sql.addSql("          USR_SID=?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addIntValue(userSid);

            sql.addSql("  where");
            sql.addSql("    THRE_INF.BFI_SID in (");
            for (int idx = 0; idx < groupSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("    ,?");
                } else {
                    sql.addSql("    ?");
                }
                sql.addIntValue(groupSidList.get(idx));
            }
            sql.addSql("    )");

            sql.addSql("  and");
            sql.addSql("    THRE_INF.BFI_SID = BBS_FOR_INF.BFI_SID");
            sql.addSql("  and");
            sql.addSql("    THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_SUM.BTS_EUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_SORT asc, BBS_THRE_SUM.BTS_WRT_DATE desc");

            sql.setPagingValue(0, viewCnt);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            for (int index = 1; index <= viewCnt && rs.next(); index++) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(rs.getInt("BFI_SID"));
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBfiName(rs.getString("BFI_NAME"));
                btMdl.setBinSid(rs.getLong("BIN_SID"));
                String name = rs.getString("BFI_Name");
                name = StringUtilHtml.transToHTmlPlusAmparsant(name);
                btMdl.setBfiName(name);

                String title = rs.getString("BTI_TITLE");
                title = StringUtilHtml.transToHTmlPlusAmparsant(title);
                btMdl.setBtiTitle(title);

                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));
                }

                if (rs.getInt("BTI_EGID") > 0) {
                    btMdl.setUserJkbn(rs.getInt("GRP_JKBN"));
                } else {
                    btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                }
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));
                btMdl.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                btMdl.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));

                ret.add(btMdl);
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
     * <br>[機  能] スレッド一覧(未読のみ)のMapを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param viewCnt 表示件数
     * @param newCnt new表示日数
     * @param groupSidList メンバー情報を保持するフォーラムのリスト
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<BulletinDspModel>> getNotReadThreadMap(
            int userSid, int viewCnt, int newCnt, List<Integer> groupSidList)
                    throws SQLException {
        Map<Integer, List<BulletinDspModel>> ret = new HashMap<Integer, List<BulletinDspModel>>();
        if (groupSidList == null || groupSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    0 as BIV_VIEW_KBN");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    (");
            sql.addSql("     select");
            sql.addSql("       BBS_THRE_INF.BTI_SID,");
            sql.addSql("       BBS_THRE_INF.BTI_TITLE,");
            sql.addSql("       BBS_THRE_INF.BFI_SID");
            sql.addSql("     from BBS_THRE_INF");
            sql.addSql("     where");
            sql.addSql("       BTI_SID not in");
            sql.addSql("         (");
            sql.addSql("          select BTI_SID from BBS_THRE_VIEW");
            sql.addSql("          where");
            sql.addSql("            BIV_VIEW_KBN = 1");
            sql.addSql("          and");
            sql.addSql("            USR_SID = ?");
            sql.addSql("         )");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            sql.addSql("        or");
            sql.addSql("          (");
            sql.addSql("             BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_DATE >= ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    ) THRE_INF");
            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID = THRE_INF.BFI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_FOR_INF.BFI_SID in (");

            for (int idx = 0; idx < groupSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("   ,?");
                } else {
                    sql.addSql("    ?");
                }
            }

            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_SID asc, BBS_THRE_SUM.BTS_WRT_DATE desc");

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            for (int groupSid : groupSidList) {
                sql.addIntValue(groupSid);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Integer bfiSid = new Integer(0);
            while (rs.next()) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(rs.getInt("BFI_SID"));
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                btMdl.setBfiName(rs.getString("BFI_Name"));
                btMdl.setBtiTitle(rs.getString("BTI_TITLE"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));
                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinMainModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));

                if (bfiSid.intValue() != btMdl.getBfiSid()) {
                    bfiSid = new Integer(btMdl.getBfiSid());
                    ret.put(bfiSid, new ArrayList<BulletinDspModel>());
                }

                ret.get(bfiSid).add(btMdl);
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
     * <br>[機  能] スレッド一覧(未読のみ)のMapを取得する(html用)
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param viewCnt 表示件数
     * @param newCnt new表示日数
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<BulletinDspModel>> getNotReadThreadMapHtml(int userSid,
                                                                    int viewCnt, int newCnt)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, List<BulletinDspModel>> ret = new HashMap<Integer, List<BulletinDspModel>>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_CNT as BTS_WRT_CNT,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    0 as BIV_VIEW_KBN");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    (");
            sql.addSql("     select BFI_SID, BTI_SID, BTI_TITLE from BBS_THRE_INF");
            sql.addSql("     where");
            sql.addSql("       not exists (");
            sql.addSql("          select 1 from BBS_THRE_VIEW");
            sql.addSql("          where");
            sql.addSql("            BIV_VIEW_KBN = 1");
            sql.addSql("          and");
            sql.addSql("            USR_SID = ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF. BTI_SID = BBS_THRE_VIEW.BTI_SID");
            sql.addSql("         )");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            sql.addSql("        or");
            sql.addSql("          (");
            sql.addSql("             BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("           and");
            sql.addSql("             BTI_LIMIT_DATE >= ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    ) THRE_INF");
            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID = THRE_INF.BFI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_FOR_INF.BFI_SID in (");
            sql.addSql("      select BFI_SID from BBS_FOR_MEM");
            sql.addSql("      where");
            sql.addSql("        BBS_FOR_MEM.USR_SID = ?");
            sql.addSql("      or");
            sql.addSql("        BBS_FOR_MEM.GRP_SID in (");
            sql.addSql("          select GRP_SID from CMN_BELONGM");
            sql.addSql("          where CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_SID asc, BBS_THRE_SUM.BTS_WRT_DATE desc");

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.setPagingValue(0, viewCnt);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Integer bfiSid = new Integer(0);

            //2011-06-21 未読スレッドの表示件数を制限
            for (int index = 1; index <= viewCnt && rs.next(); index++) {

                BulletinDspModel btMdl = new BulletinDspModel();
                btMdl.setBfiSid(rs.getInt("BFI_SID"));
                btMdl.setBtiSid(rs.getInt("BTI_SID"));
                String name = rs.getString("BFI_Name");
                name = StringUtilHtml.transToHTmlWithWbr(
                        StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(name)), 15);
                btMdl.setBfiName(name);

                String title = rs.getString("BTI_TITLE");
                title = StringUtilHtml.transToHTmlWithWbr(
                        StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(title)), 25);
                btMdl.setBtiTitle(title);

                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                if (bfsWrtDate != null) {
                    btMdl.setStrWriteDate(__createDateStr(bfsWrtDate));
                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        btMdl.setNewFlg(BulletinMainModel.NEWFLG_VIEW);
                    }
                }
                btMdl.setReadFlg(rs.getInt("BIV_VIEW_KBN"));

                if (bfiSid.intValue() != btMdl.getBfiSid()) {
                    bfiSid = new Integer(btMdl.getBfiSid());
                    ret.put(bfiSid, new ArrayList<BulletinDspModel>());
                }

                ret.get(bfiSid).add(btMdl);
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
     * <br>[機  能] 指定されたスレッドの投稿件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 投稿件数
     * @throws SQLException SQL実行例外
     */
    public int getWriteCount(int btiSid) throws SQLException {
        Connection con = getCon();
        BbsWriteInfDao bwiDao = new BbsWriteInfDao(con);
        return bwiDao.getWriteCountInThread(btiSid);
    }

    /**
     * <br>[機  能] 投稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return 投稿情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getWriteData(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BulletinDspModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_WRITE_INF.BWI_SID as BWI_SID,");
            sql.addSql("    BBS_WRITE_INF.BWI_VALUE as BWI_VALUE,");
            sql.addSql("    BBS_WRITE_INF.BWI_TYPE as BWI_TYPE,");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID as BWI_AUID,");
            sql.addSql("    BBS_WRITE_INF.BWI_EGID as BWI_EGID,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_WRITE_INF.BWI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("  where");
            sql.addSql("    BWI_SID = ?");

            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new BulletinDspModel();
                ret.setBwiSid(bwiSid);
                ret.setBwiValue(rs.getString("BWI_VALUE"));
                ret.setBwiType(rs.getInt("BWI_TYPE"));
                ret.setBwiValueView(
                        NullDefault.getString(StringUtilHtml.transToHTml(
                                ret.getBwiValue()), ""));
                ret.setAddUserSid(rs.getInt("BWI_AUID"));
                ret.setGrpSid(rs.getInt("BWI_EGID"));
                ret.setGrpName(rs.getString("GRP_NAME"));
                ret.setGrpJkbn(rs.getInt("GRP_JKBN"));

                //添付ファイル情報を取得
                ret.setTmpFileList(getWriteTmpFileList(ret.getBwiSid()));
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
     * <br>[機  能] 指定されたスレッドの投稿一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @return 投稿一覧
     * @throws IOException ユーザ画像ファイルの取得に失敗
     * @throws IOToolsException ユーザ画像ファイルの取得に失敗
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getWriteList(BulletinSearchModel searchMdl)
    throws IOException, IOToolsException, SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel >();
        boolean isOrderAsc = true;
        if (searchMdl.getOrderWriteDate() == GSConstBulletin.BBS_WRTLIST_ORDER_DESC) {
            isOrderAsc = false;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("    BINF.BIN_SID as BIN_SID,");
            sql.addSql("    BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    BBS_WRITE_INF.BWI_SID as BWI_SID,");
            sql.addSql("    BBS_WRITE_INF.BWI_TYPE as BWI_TYPE,");
            sql.addSql("    BBS_WRITE_INF.BWI_VALUE as BWI_VALUE,");
            sql.addSql("    BBS_WRITE_INF.BWI_VALUE_PLAIN as BWI_VALUE_PLAIN,");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID as BWI_AUID,");
            sql.addSql("    BBS_WRITE_INF.BWI_ADATE as BWI_ADATE,");
            sql.addSql("    BBS_WRITE_INF.BWI_EDATE as BWI_EDATE,");
            sql.addSql("    BBS_WRITE_INF.BWI_EGID as BWI_EGID,");
            sql.addSql("    BBS_WRITE_INF.BWI_PARENT_FLG as BWI_PARENT_FLG,");
            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_WRITE_INF.BWI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    ,");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BIN_SID,");
            sql.addSql("         BIN_FILE_NAME,");
            sql.addSql("         BIN_FILE_PATH");
            sql.addSql("       from");
            sql.addSql("         CMN_BINF");
            sql.addSql("       where");
            sql.addSql("         BIN_JKBN = 0");
            sql.addSql("      ) BINF");
            sql.addSql("    on");
            sql.addSql("      CMN_USRM_INF.BIN_SID = BINF.BIN_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("  order by");
            if (isOrderAsc) {
                sql.addSql("    BBS_WRITE_INF.BWI_PARENT_FLG desc,");
                sql.addSql("    BBS_WRITE_INF.BWI_ADATE asc");
            } else {
                sql.addSql("    BBS_WRITE_INF.BWI_PARENT_FLG asc,");
                sql.addSql("    BBS_WRITE_INF.BWI_ADATE desc");
            }

            sql.addIntValue(searchMdl.getBtiSid());

            log__.info(sql.toLogString());


            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                                            ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            for (int index = 1; index <= searchMdl.getEnd() && rs.next(); index++) {

                if (index >= searchMdl.getStart()) {
                    BulletinDspModel btMdl = new BulletinDspModel();
                    btMdl.setBwiSid(rs.getInt("BWI_SID"));
                    btMdl.setBwiType(rs.getInt("BWI_TYPE"));
                    btMdl.setBwiValue(rs.getString("BWI_VALUE"));
                    btMdl.setBwiValuePlain(rs.getString("BWI_VALUE_PLAIN"));

                    //内容のURL文字列をハイパーリンク表示にする
                    if (btMdl.getBwiType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
                        btMdl.setBwiValueView(StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                                NullDefault.getString(btMdl.getBwiValue(), "")));
                    }
                    btMdl.setBwiValueView(
                            StringUtilHtml.returntoBR(btMdl.getBwiValueView()));

                    String bwiValuePlainView =
                            StringUtilHtml.transToHTmlPlusAmparsant(
                                    NullDefault.getString(btMdl.getBwiValuePlain(), ""));
                    bwiValuePlainView = StringUtil.transToLink(bwiValuePlainView,
                                                StringUtil.OTHER_WIN, true);
                    bwiValuePlainView = StringUtilHtml.returntoBR(bwiValuePlainView);
                    btMdl.setBwiValuePlainView(bwiValuePlainView);

                    btMdl.setGrpSid(rs.getInt("BWI_EGID"));
                    btMdl.setGrpName(rs.getString("GRP_NAME"));
                    btMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));
                    btMdl.setUserSid(rs.getInt("USR_SID"));
                    if (btMdl.getGrpSid() > 0) {
                        btMdl.setUserName(btMdl.getGrpName());
                        btMdl.setUserJkbn(btMdl.getGrpJkbn());
                    } else {
                        btMdl.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                        btMdl.setUserJkbn(rs.getInt("USR_JKBN"));
                        btMdl.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
                    }

                    //写真公開可否を取得
                    int pictKf = rs.getInt("USI_PICT_KF");
                    btMdl.setUserPictKf(pictKf);
                    Long binSid = rs.getLong("BIN_SID");
                    btMdl.setPhotoFileSid(binSid);

                    //ユーザ画像ファイル名を設定
                    if (btMdl.getGrpSid() <= 0) {
                        String binFilePath = rs.getString("BIN_FILE_PATH");
                        if (binFilePath != null && pictKf == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                            String binFileName = rs.getString("BIN_FILE_NAME");
                            btMdl.setPhotoFileName(binFileName);
                            btMdl.setPhotoFilePath(binFilePath);
                        }
                    }

                    btMdl.setAddUserSid(rs.getInt("BWI_AUID"));
                    btMdl.setBwiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BWI_ADATE")));
                    btMdl.setStrBwiAdate(__createDateStr(btMdl.getBwiAdate()));
                    btMdl.setBwiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BWI_EDATE")));
                    btMdl.setStrBwiEdate(__createDateStr(btMdl.getBwiEdate()));
                    btMdl.setBwiParentFlg(rs.getInt("BWI_PARENT_FLG"));

                    UDate wrtDate = btMdl.getBwiAdate();
                    //登録日時 < 更新日時の場合、投稿更新フラグ = 更新 に設定
                    if (wrtDate.getTime() < btMdl.getBwiEdate().getTime()) {
                        wrtDate = btMdl.getBwiEdate();
                        btMdl.setWriteUpdateFlg(BulletinDspModel.WRITEUPDATEFLG_UPD);
                    }
                    //Newアイコン表示フラグ設定
                    if (UDateUtil.diffDay(wrtDate, new UDate()) < searchMdl.getNewCnt()) {
                        btMdl.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                    //ボタン表示フラグ設定
                    if (searchMdl.isAdmin() || btMdl.getAddUserSid() == searchMdl.getUserSid()
                            || searchMdl.isForumAdmin()) {
                        btMdl.setShowBtnFlg(BulletinDspModel.SHOWBTNFLG_YES);
                    }

                    //添付ファイル情報を取得
                    btMdl.setTmpFileList(getWriteTmpFileList(btMdl.getBwiSid()));

                    ret.add(btMdl);
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
     * <br>[機  能] 指定された投稿の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getWriteTmpFileList(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_BIN.BWI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(bwiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long lngSize = rs.getInt("BIN_FILE_SIZE");
                binMdl.setBinFileSize(lngSize);
                String strSize = cmnBiz.getByteSizeString(lngSize);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <br>[機  能] 指定されたスレッド内の投稿添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBbsBinInThread(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  delete from BBS_BIN");
            sql.addSql("  where");
            sql.addSql("    BWI_SID in (");
            sql.addSql("      select");
            sql.addSql("        BWI_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_WRITE_INF");
            sql.addSql("      where");
            sql.addSql("        BTI_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定されたスレッド内の投稿のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfForThread(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_BIN,");
            sql.addSql("        BBS_WRITE_INF");
            sql.addSql("      where");
            sql.addSql("        BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_BIN.BWI_SID = BBS_WRITE_INF.BWI_SID");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定された投稿のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfForWrite(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select BIN_SID from BBS_BIN");
            sql.addSql("      where BWI_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定のフォーラムのバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param bfSid フォーラムSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfForum(int bfSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select BIN_SID from BBS_FOR_INF");
            sql.addSql("      where BFI_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bfSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定された検索条件の検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param forumList メンバー情報を保持するフォーラムのリスト
     * @return 検索結果件数
     * @throws SQLException SQL例外発生
     */
    public int getSearchDtlCnt(BulletinSearchModel searchMdl, List<Integer> forumList)
            throws SQLException {
        int count = 0;
        if (!searchMdl.isAdmin()
                && (forumList == null || forumList.size() < 1)) {
            return count;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(distinct(BBS_THRE_INF.BTI_SID)) as searchCnt");
            createSearchDtlSql(sql, searchMdl, forumList);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("searchCnt");
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
     * <br>[機  能] 指定された検索条件の検索結果一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエストモデル
     * @param forumList メンバー情報を保持するフォーラムのリスト
     * @return 検索結果一覧
     * @throws SQLException SQL例外発生
     */
    public List<BulletinDspModel> getSearchResultList(
            BulletinSearchModel searchMdl, RequestModel reqMdl, List<Integer> forumList)
                    throws SQLException {
        List <BulletinDspModel> ret = new ArrayList <BulletinDspModel>();
        if (!searchMdl.isAdmin()
                && (forumList == null || forumList.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    distinct(BBS_THRE_INF.BTI_SID) as BTI_SID,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE");
            createSearchDtlSql(sql, searchMdl, forumList);
            sql.addSql(" order by");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE desc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            for (int index = 1; rs.next(); index++) {
                if  (index > searchMdl.getEnd()) {
                    break;
                }

                if (index >= searchMdl.getStart()) {
                    int btiSid = rs.getInt("BTI_SID");
                    BulletinDspModel btMdl =
                            getSearchResultData(btiSid, searchMdl.getUserSid(), reqMdl);
                    ret.add(btMdl);
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
     * <br>[機  能] 検索結果情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @param userSid ユーザSID
     * @param reqMdl リクエストモデル
     * @return 検索結果情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getSearchResultData(int btiSid, int userSid, RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BulletinDspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_THRE_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("    BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("    BBS_THRE_INF.BTI_EGID as BTI_EGID,");

            sql.addSql("    BBS_THRE_INF.BTI_LIMIT as BTI_LIMIT,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_FR_DATE as BTI_LIMIT_FR_DATE,");
            sql.addSql("    BBS_THRE_INF.BTI_LIMIT_DATE as BTI_LIMIT_DATE,");
            sql.addSql("    BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE,");

            sql.addSql("    CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("    BBS_WRITE_INF.BWI_TYPE as BWI_TYPE,");
            sql.addSql("    BBS_WRITE_INF.BWI_VALUE as BWI_VALUE,");
            sql.addSql("    BBS_WRITE_INF.BWI_VALUE_PLAIN as BWI_VALUE_PLAIN,");
            sql.addSql("    BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE,");
            sql.addSql("    BBS_THRE_SUM.BTS_TEMPFLG as BTS_TEMPFLG,");
            sql.addSql("    CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("    THRE_VIEW.BIV_VIEW_KBN as BIV_VIEW_KBN");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    BBS_THRE_SUM,");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    left join");
            sql.addSql("      (select");
            sql.addSql("         BTI_SID,");
            sql.addSql("         BIV_VIEW_KBN");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addSql("  where");
            sql.addSql("    BBS_THRE_INF.BTI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_WRITE_INF.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_PARENT_FLG = 1");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_AUID = CMN_USRM_INF.USR_SID");

            sql.addIntValue(userSid);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new BulletinDspModel();
                ret.setBfiSid(rs.getInt("BFI_SID"));
                ret.setBtiSid(rs.getInt("BTI_SID"));
                ret.setBtiTitle(rs.getString("BTI_TITLE"));
                ret.setBfiThreImportance(rs.getInt("BTI_IMPORTANCE"));
                ret.setBtiLimit(rs.getInt("BTI_LIMIT"));
                if (rs.getInt("BTI_LIMIT") == GSConstBulletin.THREAD_LIMIT_YES) {
                    ret.setBtiLimitFrDate(
                            UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_FR_DATE")));

                    UDate limitDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_DATE"));
                    ret.setBtiLimitDate(limitDate);
                    ret.setStrBtiLimitDate(UDateUtil.getYymdJ(limitDate, reqMdl));
                    UDate now = new UDate();
                    if (now.compareDateYMD(limitDate) == UDate.SMALL) {
                        ret.setThreadFinFlg(1);
                    } else {
                        ret.setThreadFinFlg(0);
                    }
                }

                ret.setBwiType(rs.getInt("BWI_TYPE"));

                ret.setBwiValue(rs.getString("BWI_VALUE"));
                String bwiValue = ret.getBwiValue();
                if (bwiValue.length() > 200) {
                    bwiValue = bwiValue.substring(0, 200);
                    bwiValue = bwiValue.concat("...");
                }
                ret.setBwiValueView(bwiValue);

                ret.setBwiValuePlain(rs.getString("BWI_VALUE_PLAIN"));
                String bwiValuePlain = ret.getBwiValuePlain();
                if (bwiValuePlain != null && bwiValuePlain.length() > 200) {
                    bwiValuePlain = bwiValuePlain.substring(0, 200);
                    bwiValuePlain = bwiValuePlain.concat("...");
                }
                ret.setBwiValuePlainView(bwiValuePlain);

                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE"));
                ret.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    ret.setStrWriteDate(__createDateStr(bfsWrtDate));
                }
                ret.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));
                ret.setReadFlg(rs.getInt("BIV_VIEW_KBN"));

                ret.setGrpSid(rs.getInt("BTI_EGID"));
                ret.setGrpName(rs.getString("GRP_NAME"));
                ret.setGrpJkbn(rs.getInt("GRP_JKBN"));
                if (ret.getGrpSid() > 0) {
                    ret.setUserName(ret.getGrpName());
                } else {
                    ret.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    ret.setUserYukoKbn(rs.getInt("USR_UKO_FLG"));
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
     * <br>[機  能] 詳細検索時の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql ＳＱＬバッファ
     * @param searchMdl 検索条件
     * @param forumList メンバー情報を保持するフォーラムのリスト
     */
    public void createSearchDtlSql(
            SqlBuffer sql, BulletinSearchModel searchMdl, List<Integer> forumList) {

        //未読/既読を条件に含めるか
        boolean readFlg = false;
        int readKbn = searchMdl.getReadKbn();
        if (readKbn == GSConstBulletin.BBSREADKBN_NOREAD
        || readKbn == GSConstBulletin.BBSREADKBN_READ) {
            readFlg = true;
        }

        //SQL文
        sql.addSql("  from");
        sql.addSql("    BBS_FOR_INF,");
        sql.addSql("    CMN_USRM_INF,");
        sql.addSql("    BBS_WRITE_INF,");
        sql.addSql("    BBS_THRE_SUM,");
        sql.addSql("    BBS_THRE_INF");
        if (readFlg) {
            sql.addSql("    left join");
            sql.addSql("      (select");
            sql.addSql("         BTI_SID,");
            sql.addSql("         BIV_VIEW_KBN");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("      ) THRE_VIEW");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_SID = THRE_VIEW.BTI_SID");
            sql.addIntValue(searchMdl.getUserSid());
        }

        sql.addSql("  where");
        sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_WRITE_INF.BTI_SID");
        sql.addSql("  and");
        sql.addSql("    BBS_WRITE_INF.BWI_AUID = CMN_USRM_INF.USR_SID");
        sql.addSql("  and");
        sql.addSql("    BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");

        //条件「掲示状態」
        int publicOngoing = searchMdl.getPublicStatusOngoing();
        int publicScheduled = searchMdl.getPublicStatusScheduled();
        int publicOver = searchMdl.getPublicStatusOver();
        if (publicOngoing == 0
                && publicScheduled == 0
                && publicOver == 0) {
            publicOngoing = 1;
            publicScheduled = 1;
            publicOver = 1;
        }

        if (publicOngoing == 1
                || publicScheduled == 1
                || publicOver == 1) {
            sql.addSql("  and");

            if (publicOngoing == 1
                    || (publicScheduled == 1
                            && publicOver == 1)) {
                sql.addSql("   (");
            }

            UDate limitDate = UDate.getInstanceStr(searchMdl.getNow().getDateString());

            boolean statusFlg = false;

            //掲示中
            if (publicOngoing == 1) {
                sql.addSql("      BBS_THRE_INF.BTI_LIMIT = ?");
                sql.addSql("    or");
                sql.addSql("     (");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT = ?");
                sql.addSql("      and");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
                sql.addSql("      and");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
                sql.addSql("     )");
                sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
                sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
                sql.addDateValue(limitDate);
                sql.addDateValue(limitDate);
                statusFlg = true;
            }

            //掲示予定
            if (publicScheduled == 1) {
                if (statusFlg) {
                    sql.addSql("    or");
                }
                sql.addSql("     (");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT = ?");
                sql.addSql("      and");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT_FR_DATE > ?");
                sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
                sql.addDateValue(limitDate);
                if (!searchMdl.isAdmin()) {
                    sql.addSql("      and");
                    sql.addSql("       (");
                    sql.addSql("          BBS_THRE_INF.BTI_AUID = ?");
                    sql.addIntValue(searchMdl.getUserSid());
                    sql.addSql("        or");
                    sql.addSql("          BBS_THRE_INF.BFI_SID in (");
                    sql.addSql("            select");
                    sql.addSql("              BBS_FOR_ADMIN.BFI_SID");
                    sql.addSql("            from");
                    sql.addSql("              BBS_FOR_ADMIN");
                    sql.addSql("            where");
                    sql.addSql("              BBS_FOR_ADMIN.USR_SID = ?");
                    sql.addIntValue(searchMdl.getUserSid());
                    sql.addSql("          )");
                    sql.addSql("       )");
                }
                sql.addSql("     )");
                statusFlg = true;
            }

            //掲示終了
            if (publicOver == 1) {
                if (statusFlg) {
                    sql.addSql("    or");
                }
                sql.addSql("     (");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT = ?");
                sql.addSql("      and");
                sql.addSql("        BBS_THRE_INF.BTI_LIMIT_DATE < ?");
                sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
                sql.addDateValue(limitDate);
                if (!searchMdl.isAdmin()) {
                    sql.addSql("      and");
                    sql.addSql("       (");
                    sql.addSql("          BBS_THRE_INF.BTI_AUID = ?");
                    sql.addIntValue(searchMdl.getUserSid());
                    sql.addSql("        or");
                    sql.addSql("          BBS_THRE_INF.BFI_SID in (");
                    sql.addSql("            select");
                    sql.addSql("              BBS_FOR_ADMIN.BFI_SID");
                    sql.addSql("            from");
                    sql.addSql("              BBS_FOR_ADMIN");
                    sql.addSql("            where");
                    sql.addSql("              BBS_FOR_ADMIN.USR_SID = ?");
                    sql.addIntValue(searchMdl.getUserSid());
                    sql.addSql("          )");
                    sql.addSql("       )");
                }
                sql.addSql("     )");
            }

            if (publicOngoing == 1
                    || (publicScheduled == 1
                            && publicOver == 1)) {
                sql.addSql("   )");
            }
        }

        //フォーラム
        if (searchMdl.getBfiSid() > 0) {
            sql.addSql("  and");
            sql.addSql("    BBS_THRE_INF.BFI_SID = ?");
            sql.addIntValue(searchMdl.getBfiSid());
        }

        List < String > keywordList = searchMdl.getKeywordList();

        if (keywordList != null && !keywordList.isEmpty()) {
            String keywordJoin = "   and";
            if (searchMdl.getKeywordKbn() == GSConstBulletin.KEYWORDKBN_OR) {
                keywordJoin = "   or";
            }

            //スレッドタイトル
            if (searchMdl.isSearchThreTitleFlg()) {
                sql.addSql("  and");

                if (searchMdl.isSearchWriteValueFlg()) {
                    sql.addSql("   (");
                }

                sql.addSql("     (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       BBS_THRE_INF.BTI_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("     )");
            }

            //投稿内容
            if (searchMdl.isSearchWriteValueFlg()) {

                if (searchMdl.isSearchThreTitleFlg()) {
                    sql.addSql("    or");
                } else {
                    sql.addSql("  and");
                }

                sql.addSql("     (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }

                    sql.addSql("       (");
                    sql.addSql("         (");
                    sql.addSql("         BBS_WRITE_INF.BWI_TYPE = 0");
                    sql.addSql("         and");
                    sql.addSql("         BBS_WRITE_INF.BWI_VALUE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("         )");
                    sql.addSql("       or");
                    sql.addSql("         (");
                    sql.addSql("         BBS_WRITE_INF.BWI_TYPE = 1");
                    sql.addSql("         and");
                    sql.addSql("         BBS_WRITE_INF.BWI_VALUE_PLAIN like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("         )");
                    sql.addSql("       )");
                }
                sql.addSql("     )");

                if (searchMdl.isSearchThreTitleFlg()) {
                    sql.addSql("   )");
                }
            }
        }

        //投稿者名
        if (!StringUtil.isNullZeroString(searchMdl.getContributorName())) {
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("      (");
            sql.addSql("        coalesce(BBS_WRITE_INF.BWI_EGID, 0) <= 0");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          CMN_USRM_INF.USI_SEI like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getContributorName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("        or");
            sql.addSql("          CMN_USRM_INF.USI_MEI like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getContributorName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addSql("      or");
            sql.addSql("      (");
            sql.addSql("        coalesce(BBS_WRITE_INF.BWI_EGID, 0) > 0");
            sql.addSql("      and");
            sql.addSql("        BBS_WRITE_INF.BWI_EGID in (");
            sql.addSql("          select GRP_SID from CMN_GROUPM");
            sql.addSql("          where GRP_JKBN = " + GSConst.JTKBN_TOROKU);
            sql.addSql("          and GRP_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getContributorName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addSql("    )");
        }

        //未読/既読
        if (readFlg) {
            if (readKbn == GSConstBulletin.BBSREADKBN_NOREAD) {
                sql.addSql("  and");
                sql.addSql("    (");
                sql.addSql("       THRE_VIEW.BIV_VIEW_KBN is null");
                sql.addSql("     or");
                sql.addSql("       THRE_VIEW.BIV_VIEW_KBN = ?");
                sql.addSql("     or");
                sql.addSql("       THRE_VIEW.BIV_VIEW_KBN = ?");
                sql.addSql("    )");
                sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_NO);
                sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            } else if (readKbn == GSConstBulletin.BBSREADKBN_READ) {
                sql.addSql("  and");
                sql.addSql("    THRE_VIEW.BIV_VIEW_KBN = ?");
                sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            }
        }

        //投稿日時
        if (searchMdl.getWriteDateFrom() != null) {
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_ADATE >= ?");
            sql.addDateValue(searchMdl.getWriteDateFrom());
        }
        if (searchMdl.getWriteDateTo() != null) {
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_ADATE <= ?");
            sql.addDateValue(searchMdl.getWriteDateTo());
        }
        //ユーザ・グループ
        sql.addSql("  and");
        sql.addSql("    BBS_THRE_INF.BFI_SID in (");
        for (int idx = 0; idx < forumList.size(); idx++) {
            if (idx > 0) {
                sql.addSql("    ,?");
            } else {
                sql.addSql("    ?");
            }
            sql.addIntValue(forumList.get(idx));
        }
        sql.addSql("    )");
    }

    /**
     * <br>[機  能] フォーラムメンバーの件数を取得する
     * <br>[解  説] 削除ユーザは除く
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラムメンバーの件数
     * @throws SQLException SQL実行例外
     */
    public int getForumMemCount(int bfiSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FORUM_MEM.USR_SID) as USRCNT");
            sql.addSql(" from");
            sql.addSql("         (");
            sql.addSql("          select");
            sql.addSql("            BFI_SID,");
            sql.addSql("            USR_SID,");
            sql.addSql("            count(*)");
            sql.addSql("          from");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                BFI_SID,");
            sql.addSql("                USR_SID");
            sql.addSql("              from");
            sql.addSql("                BBS_FOR_MEM");
            sql.addSql("              where USR_SID > 0");
            sql.addSql("            union all");
            sql.addSql("              select");
            sql.addSql("                BBS_FOR_MEM.BFI_SID as BFI_SID,");
            sql.addSql("                CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("              from");
            sql.addSql("                BBS_FOR_MEM,");
            sql.addSql("                CMN_BELONGM");
            sql.addSql("              where");
            sql.addSql("                BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("            ) BBS_MEM");
            sql.addSql("            group by BFI_SID, USR_SID");
            sql.addSql("         ) FORUM_MEM,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   FORUM_MEM.BFI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FORUM_MEM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");

            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("USRCNT");
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
     * <br>[機  能] フォーラムメンバー一覧を取得する
     * <br>[解  説] 削除ユーザは除く
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param sortKey ソート対象
     * @param orderKey 並び順
     * @param page ページ
     * @param maxCnt ページ毎の最大表示件数
     * @return フォーラムメンバー一覧
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getForumMemList(int bfiSid, int sortKey, int orderKey,
                                                int page, int maxCnt) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   case when");
            sql.addSql("     CMN_USRM_INF.USI_MAIL1_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_MAIL1");
            sql.addSql("   end USI_MAIL1,");
            sql.addSql("   case when");
            sql.addSql("     CMN_USRM_INF.USI_MAIL2_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_MAIL2");
            sql.addSql("   end USI_MAIL2,");
            sql.addSql("   case when");
            sql.addSql("     CMN_USRM_INF.USI_MAIL3_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_MAIL3");
            sql.addSql("   end USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
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
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");

            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select BBS_FOR_MEM.USR_SID from BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("   )");
            sql.addSql(" or");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addSql(" )");

            //オーダー
            String orderStr = " asc";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = " desc";
            }

            sql.addSql(" order by");
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN" + orderStr + ",");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + orderStr);
                    break;
                //社員/職員番号
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO" + orderStr + ",");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");
                    break;
                //役職
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("  YAKUSYOKU_EXIST");
                    sql.addSql(orderStr);
                    sql.addSql("  ,");
                    sql.addSql("  YAKUSYOKU_SORT");
                    sql.addSql(orderStr);

                    break;
                default:
                    break;
            }
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(bfiSid);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                                            ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                CmnUsrmInfModel uinfMdl = new CmnUsrmInfModel();
                uinfMdl.setUsrSid(rs.getInt("USR_SID"));
                uinfMdl.setUsiSei(rs.getString("USI_SEI"));
                uinfMdl.setUsiMei(rs.getString("USI_MEI"));
                uinfMdl.setUsiSeiKn(rs.getString("USI_SEI_KN"));
                uinfMdl.setUsiMeiKn(rs.getString("USI_MEI_KN"));
                uinfMdl.setUsiMail1(rs.getString("USI_MAIL1"));
                uinfMdl.setUsiMail2(rs.getString("USI_MAIL2"));
                uinfMdl.setUsiMail3(rs.getString("USI_MAIL3"));
                uinfMdl.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
                uinfMdl.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
                uinfMdl.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
                uinfMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.add(uinfMdl);
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
     * <br>[機  能] 指定したフォーラムのメンバー数を取得する
     * <br>[解  説] 削除されたユーザ(CMN_USRM.USR_JKBN = 9:削除ユーザ)は除く
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラムのメンバー数
     * @throws SQLException SQL実行例外
     */
    public int getForumMemberCount(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int memberCount = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_USRM_INF.USR_SID) as MEMBER_CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select BBS_FOR_MEM.USR_SID from BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("   )");
            sql.addSql(" or");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addSql(" )");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(bfiSid);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberCount = rs.getInt("MEMBER_CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return memberCount;
    }

    /**
     * <br>[機  能] フォーラムメンバー閲覧状況を取得する
     * <br>[解  説] 削除ユーザは除く
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @param sortKey ソート対象
     * @param orderKey 並び順
     * @param page ページ
     * @param maxCnt ページ毎の最大表示件数
     * @param memberForumSid メンバー情報を保持するフォーラムのSID
     * @return フォーラムメンバー一覧
     * @throws SQLException SQL実行例外
     */
    public List<BulletinWachModel> getForumMemWatchList(
            int bfiSid, int btiSid, int sortKey,
            int orderKey, int page, int maxCnt, int memberForumSid)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BulletinWachModel> ret = new ArrayList<BulletinWachModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   COALESCE(USR_THRE_CNT.CNT, 0) as USR_THREAD_COUNT");

            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql("   left join");
            sql.addSql("   ( ");
            sql.addSql("      select USR_SID, count(*) as CNT");
            sql.addSql("      from BBS_THRE_VIEW");
            sql.addSql("      where ( BIV_VIEW_KBN = ?");
            sql.addSql("         or");
            sql.addSql("      BIV_VIEW_KBN = ? ) ");
            sql.addSql("      and BFI_SID = ?");
            sql.addSql("      and BTI_SID = ?");
            sql.addSql("      group by USR_SID");
            sql.addSql("   ) USR_THRE_CNT");
            sql.addSql("   on");
            sql.addSql("   CMN_USRM_INF.USR_SID = USR_THRE_CNT.USR_SID");
            sql.addSql("   where");
            sql.addSql("     CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("   and");
            sql.addSql("     CMN_USRM.USR_JKBN = ?");
            sql.addSql("   and");
            //ユーザSID < 100は除外
            sql.addSql("     CMN_USRM.USR_SID > ?");

            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select BBS_FOR_MEM.USR_SID from BBS_FOR_MEM");
            sql.addSql("     where BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("   )");
            sql.addSql(" or");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addSql(" )");

            //オーダー
            String orderStr = " asc";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = " desc";
            }

            sql.addSql(" order by");
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN" + orderStr + ",");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + orderStr);
                    break;

                //閲覧状況
                case GSConstBulletin.SORT_KEY_WATCH:
                    sql.addSql("   USR_THREAD_COUNT" + orderStr + ",");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");
                    break;
                default:
                    break;
            }

            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(bfiSid);
            sql.addIntValue(btiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(memberForumSid);
            sql.addIntValue(memberForumSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString(),
                                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                                            ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                BulletinWachModel bwMdl = new BulletinWachModel();

                bwMdl.setUsrSid(rs.getInt("USR_SID"));
                bwMdl.setUsiSei(rs.getString("USI_SEI"));
                bwMdl.setUsiMei(rs.getString("USI_MEI"));
                bwMdl.setUsiSeiKn(rs.getString("USI_SEI_KN"));
                bwMdl.setUsiMeiKn(rs.getString("USI_MEI_KN"));
                bwMdl.setUsrYukoKbn(rs.getInt("USR_UKO_FLG"));
                bwMdl.setUserJkbn(rs.getInt("USR_THREAD_COUNT"));

                ret.add(bwMdl);
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
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateStr(UDate date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() - 1, date.getIntDay());
        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                strDate.append("( 日 )");
                break;
            case Calendar.MONDAY:
                strDate.append("( 月 )");
                break;
            case Calendar.TUESDAY:
                strDate.append("( 火 )");
                break;
            case Calendar.WEDNESDAY:
                strDate.append("( 水 )");
                break;
            case Calendar.THURSDAY:
                strDate.append("( 木 )");
                break;
            case Calendar.FRIDAY:
                strDate.append("( 金 )");
                break;
            case Calendar.SATURDAY:
                strDate.append("( 土 )");
                break;
            default:
                break;
        }
        strDate.append(" ");
        strDate.append(UDateUtil.getSeparateHMS(date));
        return strDate.toString();
    }

    /**
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateNoTimeStr(UDate date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() - 1, date.getIntDay());
        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                strDate.append("( 日 )");
                break;
            case Calendar.MONDAY:
                strDate.append("( 月 )");
                break;
            case Calendar.TUESDAY:
                strDate.append("( 火 )");
                break;
            case Calendar.WEDNESDAY:
                strDate.append("( 水 )");
                break;
            case Calendar.THURSDAY:
                strDate.append("( 木 )");
                break;
            case Calendar.FRIDAY:
                strDate.append("( 金 )");
                break;
            case Calendar.SATURDAY:
                strDate.append("( 土 )");
                break;
            default:
                break;
        }
        strDate.append(date.getStrHour() + "時" + date.getStrMinute() + "分");
        return strDate.toString();
    }

    /**
     * <p>指定された投稿SIDと添付ファイルバイナリSIDの組み合わせが存在するかを確認する
     * @param bwiSid 投稿SID
     * @param binSid 添付ファイルバイナリSID
     * @param forumSid フォーラムSID
     * @param threadSid スレッドSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existBbsWriTmp(int bwiSid, Long binSid,
            int forumSid, int threadSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BBS_BIN.BWI_SID as BWI_SID,");
            sql.addSql("   BBS_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   BBS_WRITE_INF.BFI_SID as BFI_SID,");
            sql.addSql("   BBS_WRITE_INF.BTI_SID as BTI_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_BIN");
            sql.addSql(" left join ");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" on ");
            sql.addSql("   BBS_BIN.BWI_SID = BBS_WRITE_INF.BWI_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_BIN.BWI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_BIN.BIN_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_WRITE_INF.BFI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_WRITE_INF.BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);
            sql.addLongValue(binSid);
            sql.addIntValue(forumSid);
            sql.addIntValue(threadSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ret = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定したフォーラム、ユーザの未読スレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @return スレッド情報一覧
     * @throws SQLException SQL実行例外
     */
    public int getUnreadThreadCount(int bfiSid, int userSid) throws SQLException {
        int ret = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select");
            sql.addSql("    count(BBS_THRE_INF.BTI_SID) as CNT");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("  where");
            sql.addSql("    BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    not exists");
            sql.addSql("     (");
            sql.addSql("       select 1 from BBS_THRE_VIEW");
            sql.addSql("       where");
            sql.addSql("         BIV_VIEW_KBN = 1");
            sql.addSql("       and");
            sql.addSql("         USR_SID = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_INF.BTI_SID = BBS_THRE_VIEW.BTI_SID");
            sql.addSql("     )");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BTI_LIMIT = ?");
            UDate limitDate = new UDate();
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");

            sql.addIntValue(bfiSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] スレッド内投稿の添付ファイルサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 添付ファイルサイズの合計
     * @throws SQLException SQL実行例外
     */
    public long getSumThreTempFileSize(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    sum(CMN_BINF.BIN_FILE_SIZE) as SUM_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = BBS_BIN.BWI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(btiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("SUM_FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fileSize;
    }

    /**
     * <br>[機  能] スレッド内投稿の本文添付ファイルサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 本文添付ファイルサイズの合計
     * @throws SQLException SQL実行例外
     */
    public long getSumThreBodyFileSize(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    sum(CMN_BINF.BIN_FILE_SIZE) as SUM_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_BODY_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = BBS_BODY_BIN.BWI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_BODY_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(btiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("SUM_FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fileSize;
    }

    /**
     * <br>[機  能] 指定した投稿の添付ファイルサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return 添付ファイルサイズの合計
     * @throws SQLException SQL実行例外
     */
    public long getSumWriteTempFileSize(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    sum(CMN_BINF.BIN_FILE_SIZE) as SUM_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = BBS_BIN.BWI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(bwiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("SUM_FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fileSize;
    }

    /**
     * <br>[機  能] 指定した投稿の本文添付ファイルサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return 本文添付ファイルサイズの合計
     * @throws SQLException SQL実行例外
     */
    public long getSumWriteBodyFileSize(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    sum(CMN_BINF.BIN_FILE_SIZE) as SUM_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_BODY_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = BBS_BODY_BIN.BWI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_BODY_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(bwiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("SUM_FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fileSize;
    }

    /**
     * <br>[機  能] ディスク容量の警告を行うフォーラムの一覧を取得する
     * <br>[解  説] 指定したユーザが参照可能なフォーラムのみを対象とする
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者 false:一般ユーザ
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinForumDiskModel> getWarnForumList(int userSid, boolean admin)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List <BulletinForumDiskModel> ret = new ArrayList <BulletinForumDiskModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK as BFI_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK_SIZE as BFI_DISK_SIZE,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK as BFI_WARN_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK_TH as BFI_WARN_DISK_TH,");
            sql.addSql("    BBS_FOR_SUM.BFS_SIZE as BFS_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_FOR_SUM");

            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_DISK = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK = ?");
            sql.addIntValue(GSConstBulletin.BFI_DISK_LIMITED);
            sql.addIntValue(GSConstBulletin.BFI_WARN_DISK_YES);

            if (!admin) {
                sql.addSql("  and");
                sql.addSql("    BBS_FOR_INF.BFI_SID in (");
                sql.addSql("      select");
                sql.addSql("        BFI_SID");
                sql.addSql("      from");
                sql.addSql("        BBS_FOR_ADMIN");
                sql.addSql("      where");
                sql.addSql("        USR_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(userSid);
            }
            sql.addSql("  and");
            sql.addSql("    BBS_FOR_INF.BFI_SID = BBS_FOR_SUM.BFI_SID");
            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_SORT, BBS_FOR_INF.BFI_NAME");
            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BulletinForumDiskModel diskMdl = new BulletinForumDiskModel();
                diskMdl.setBfiSid(rs.getInt("BFI_SID"));
                diskMdl.setBfiName(rs.getString("BFI_NAME"));
                diskMdl.setBfiDisk(rs.getInt("BFI_DISK"));
                diskMdl.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
                diskMdl.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
                diskMdl.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));
                diskMdl.setBfsSize(rs.getLong("BFS_SIZE"));

                ret.add(diskMdl);
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
     * <br>[機  能] 指定したフォーラムのディスク容量情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラムのディスク容量情報
     * @throws SQLException SQL実行例外
     */
    public BulletinForumDiskModel getForumDiskData(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BulletinForumDiskModel diskData = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK as BFI_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_DISK_SIZE as BFI_DISK_SIZE,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK as BFI_WARN_DISK,");
            sql.addSql("    BBS_FOR_INF.BFI_WARN_DISK_TH as BFI_WARN_DISK_TH,");
            sql.addSql("    BBS_FOR_SUM.BFS_SIZE as BFS_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_INF,");
            sql.addSql("    BBS_FOR_SUM");

            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_FOR_INF.BFI_SID = BBS_FOR_SUM.BFI_SID");
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                diskData = new BulletinForumDiskModel();
                diskData.setBfiSid(rs.getInt("BFI_SID"));
                diskData.setBfiName(rs.getString("BFI_NAME"));
                diskData.setBfiDisk(rs.getInt("BFI_DISK"));
                diskData.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
                diskData.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
                diskData.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));
                diskData.setBfsSize(rs.getLong("BFS_SIZE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return diskData;
    }

    /**
     * <br>[機  能] 指定したフォーラムの最終更新日時を最新のものに更新する
     * <br>[解  説]フォーラムの最終更新日時と表示されているスレッド内の最新日時から
     *                    最新のものを取得して更新する
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param now 現在日時
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int updateBfsWrtDateBatch(int bfiSid, UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   update");
            sql.addSql("     BBS_FOR_SUM");
            sql.addSql("   set ");
            sql.addSql("     BFS_WRT_DATE=(");
            sql.addSql("       select");
            sql.addSql("         max(WRT_DATE)");
            sql.addSql("       from");
            sql.addSql("         (");
            sql.addSql("          select");
            sql.addSql("            max(BTS_WRT_DATE) as WRT_DATE");
            sql.addSql("          from");
            sql.addSql("            BBS_THRE_SUM,");
            sql.addSql("            BBS_THRE_INF");
            sql.addSql("          where");
            sql.addSql("            BBS_THRE_INF.BFI_SID = ?");
            sql.addSql("          and");
            sql.addSql("            BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");
            sql.addSql("          and");
            sql.addSql("            (");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("             or");
            sql.addSql("               (");
            sql.addSql("                  BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("                and");
            sql.addSql("                  BBS_THRE_INF.BTI_LIMIT_FR_DATE <=?");
            sql.addSql("                and");
            sql.addSql("                  BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("               )");
            sql.addSql("            )");
            sql.addSql("          union");
            sql.addSql("          select");
            sql.addSql("            BFS_WRT_DATE as WRT_DATE");
            sql.addSql("          from");
            sql.addSql("            BBS_FOR_SUM");
            sql.addSql("          where");
            sql.addSql("            BFI_SID = ?");
            sql.addSql("         )  as WRT_DATE");
            sql.addSql("     )");
            sql.addSql("   where ");
            sql.addSql("     BFI_SID = ?");


            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(now);
            sql.addDateValue(now);
            sql.addIntValue(bfiSid);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;

    }

    /**
     * <br>[機  能] 指定したフォーラムを親としているフォーラムのフォーラム情報を取得します
     * <br>[解  説]
     * <br>[備  考] 表示順でソートされています
     * @param parentSid 親フォーラムSID
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @param userSid ユーザSID
     * @param groupSidList メンバー情報を保持するフォーラムのリスト
     * @return BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> getSortedChildForum(
            int parentSid, boolean adminDisp, int userSid, List<Integer> groupSidList)
                    throws SQLException {
        List<BbsForInfModel> ret = new ArrayList<BbsForInfModel>();

        if (!adminDisp
                && (groupSidList == null || groupSidList.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_PARENT_SID=?");

            //管理者設定画面でない場合
            if (!adminDisp) {
                sql.addSql(" and");
                sql.addSql("   BFI_SID in (");
                for (int idx = 0; idx < groupSidList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("   ,?");
                    } else {
                        sql.addSql("   ?");
                    }
                }
                sql.addSql("   )");
            }

            sql.addSql(" order by ");
            sql.addSql("   BFI_SORT, BFI_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(parentSid);
            //管理者設定画面でない場合
            if (!adminDisp) {
                for (int groupSid : groupSidList) {
                    sql.addIntValue(groupSid);
                }
            }

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BbsForInfModel bean = new BbsForInfModel();
                bean.setBfiSid(rs.getInt("BFI_SID"));
                bean.setBfiName(rs.getString("BFI_NAME"));
                bean.setBfiCmt(rs.getString("BFI_CMT"));
                bean.setBfiSort(rs.getInt("BFI_SORT"));
                bean.setBfiReply(rs.getInt("BFI_REPLY"));
                bean.setBfiRead(rs.getInt("BFI_READ"));
                bean.setBinSid(rs.getLong("BIN_SID"));
                bean.setBfiMread(rs.getInt("BFI_MREAD"));
                bean.setBfiAuid(rs.getInt("BFI_AUID"));
                bean.setBfiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_ADATE")));
                bean.setBfiEuid(rs.getInt("BFI_EUID"));
                bean.setBfiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_EDATE")));
                bean.setBfiDisk(rs.getInt("BFI_DISK"));
                bean.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
                bean.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
                bean.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));
                bean.setBfiLimit(rs.getInt("BFI_LIMIT"));
                bean.setBfiLimitDate(rs.getInt("BFI_LIMIT_DATE"));
                bean.setBfiKeep(rs.getInt("BFI_KEEP"));
                bean.setBfiKeepDateY(rs.getInt("BFI_KEEP_DATE_Y"));
                bean.setBfiKeepDateM(rs.getInt("BFI_KEEP_DATE_M"));
                bean.setBfiLimitOn(rs.getInt("BFI_LIMIT_ON"));
                bean.setBfiParentSid(rs.getInt("BFI_PARENT_SID"));
                bean.setBfiLevel(rs.getInt("BFI_LEVEL"));
                bean.setBfiFollowParentMem(rs.getInt("BFI_FOLLOW_PARENT_MEM"));
                ret.add(bean);
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
     * <br>[機  能] 指定したフォーラムを親としているフォーラムの数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param parentSid 親フォーラムSID
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @param groupSidList メンバー情報を保持するフォーラムのリスト
     * @return BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public int getChildForumCount(
            int parentSid, boolean adminDisp, List<Integer> groupSidList)
                    throws SQLException {
        int ret = 0;
        if (!adminDisp
                && (groupSidList == null || groupSidList.size() < 1)) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(BBS_FOR_INF.BFI_SID) as forumCnt");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID = ?");
            sql.addIntValue(parentSid);

            if (!adminDisp) {
                sql.addSql("  and");
                sql.addSql("    BBS_FOR_INF.BFI_SID in (");
                for (int idx = 0; idx < groupSidList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("    ,?");
                    } else {
                        sql.addSql("    ?");
                    }
                    sql.addIntValue(groupSidList.get(idx));
                }
                sql.addSql("    )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("forumCnt");
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
     * <br>[機  能] 予約投稿数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param now 現在の時刻
     * @param forumSid フォーラムSID
     * @param userSid ユーザSID
     * @param adminFlg 管理者フラグ true:管理者 false:一般ユーザ
     * @throws SQLException SQL実行例外
     * @return 予約投稿数
     */
    public int getScheduledPost(
            UDate now, int forumSid, int userSid, boolean adminFlg)
                    throws SQLException {
        int ret = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(BTI.BTI_SID) as rsvThreCnt");
            sql.addSql("  from");
            sql.addSql("    BBS_THRE_INF BTI");
            sql.addSql("  where");
            sql.addSql("    BTI.BTI_LIMIT = ?");
            sql.addSql("  and");
            sql.addSql("    BTI.BTI_LIMIT_FR_DATE > ?");
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addDateValue(now);

            if (forumSid > 0) {
                sql.addSql("  and");
                sql.addSql("    BTI.BFI_SID = ?");
                sql.addIntValue(forumSid);
            }

            if (!adminFlg) {
                sql.addSql("  and");
                sql.addSql("    (");
                sql.addSql("       BTI.BTI_AUID = ?");
                sql.addIntValue(userSid);
                sql.addSql("     or");
                sql.addSql("       BTI.BFI_SID in (");
                sql.addSql("         select");
                sql.addSql("           BBS_FOR_ADMIN.BFI_SID");
                sql.addSql("         from");
                sql.addSql("           BBS_FOR_ADMIN");
                sql.addSql("         where");
                sql.addSql("           BBS_FOR_ADMIN.USR_SID = ?");
                sql.addIntValue(userSid);
                sql.addSql("       )");
                sql.addSql("    )");
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            rs.next();
            ret = rs.getInt("rsvThreCnt");

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] 階層構造用にソートしたフォーラム一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param now 現在日時
     * @param newCnt new表示日数
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @param admUsr システム管理者フラグ
     * @param usersForumWrite ユーザを編集権限のメンバーとするフォーラム
     * @param usersForumAll 表示可能なフォーラム
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public List<BulletinDspModel> getForumListWithHierarchy(
            int userSid, UDate now, int newCnt,
            int start, int end, boolean admUsr,
            List<BbsForInfModel> usersForumWrite, List<BbsForInfModel> usersForumAll)
                    throws SQLException {
        List < BulletinDspModel > ret = new ArrayList < BulletinDspModel >();

        if (usersForumAll == null || usersForumAll.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BBS_FOR_INF.BFI_SID as BFI_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_NAME as BFI_NAME,");
            sql.addSql("    BBS_FOR_INF.BFI_CMT as BFI_CMT,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT as BFI_SORT,");
            sql.addSql("    BBS_FOR_INF.BFI_REPLY as BFI_REPLY,");
            sql.addSql("    BBS_FOR_INF.BIN_SID as BIN_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_MREAD as BFI_MREAD,");
            sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID as BFI_PARENT_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_LEVEL as BFI_LEVEL,");
            sql.addSql("    BBS_FOR_SUM.BFS_THRE_CNT as THRE_CNT,");
            sql.addSql("    BBS_FOR_SUM.BFS_WRT_CNT as WRITE_CNT,");
            sql.addSql("    BBS_FOR_SUM.BFS_WRT_DATE as BFS_WRT_DATE,");
            sql.addSql("    BBS_FOR_SUM.BFS_SIZE as BFS_SIZE,");
            sql.addSql("    BBSVIEW.viewCnt as viewCnt,");
            sql.addSql("    RSVTHRE.rsvThreCnt as rsvThreCnt,");
            sql.addSql("    DEL_CNT.delCnt as delCnt");
            sql.addSql("  from");
            sql.addSql("    BBS_FOR_SUM,");
            sql.addSql("    BBS_FOR_INF");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID as BFI_SID,");
            sql.addSql("         count(BBS_THRE_VIEW.BTI_SID) as viewCnt");
            sql.addSql("       from");
            sql.addSql("         BBS_THRE_VIEW,");
            sql.addSql("         BBS_THRE_INF");
            sql.addSql("       where");
            sql.addSql("         BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("       and");
            sql.addSql("         BBS_THRE_VIEW.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("            BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("          or");
            sql.addSql("            (");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("             and");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("             and");
            sql.addSql("               BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("            )");
            sql.addSql("         )");
            sql.addSql("       group by");
            sql.addSql("         BBS_THRE_VIEW.BFI_SID");
            sql.addSql("      ) BBSVIEW");
            sql.addSql("     on");
            sql.addSql("       BBS_FOR_INF.BFI_SID = BBSVIEW.BFI_SID");
            sql.addSql("    left join ");
            sql.addSql("      (select ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID as BFI_SID, ");
            sql.addSql("         count(BBS_THRE_INF.BTI_SID) as delCnt ");
            sql.addSql("       from ");
            sql.addSql("         BBS_FOR_SUM, ");
            sql.addSql("         BBS_THRE_INF ");
            sql.addSql("       where ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID = BBS_THRE_INF.BFI_SID ");
            sql.addSql("       and ");
            sql.addSql("         BBS_FOR_SUM.BFS_EDATE < BBS_THRE_INF.BTI_LIMIT_DATE ");
            sql.addSql("       and ");
            sql.addSql("         BBS_THRE_INF.BTI_LIMIT_DATE < ? ");
            sql.addSql("       group by ");
            sql.addSql("         BBS_FOR_SUM.BFI_SID ");
            sql.addSql("      ) DEL_CNT");
            sql.addSql("    on");
            sql.addSql("      BBS_FOR_INF.BFI_SID = DEL_CNT.BFI_SID ");
            sql.addSql("    left join");
            sql.addSql("       (");
            sql.addSql("        select");
            sql.addSql("          BTI2.BFI_SID as BFI_SID2,");
            sql.addSql("          count(BTI2.BFI_SID) as rsvThreCnt");
            sql.addSql("        from");
            sql.addSql("          BBS_THRE_INF BTI2");
            sql.addSql("        where");
            sql.addSql("          BTI2.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BTI2.BTI_LIMIT_FR_DATE > ?");

            if (!admUsr) {
                sql.addSql("        and");

                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addSql("        (");
                    sql.addSql("          (");
                    sql.addSql("           BTI2.BTI_AUID = ? ");
                    sql.addSql("          and");
                    sql.addSql("           BTI2.BFI_SID ");
                    sql.addSql("             in ( ");
                    for (int idx = 0; idx < usersForumWrite.size(); idx++) {
                        if (idx > 0) {
                            sql.addSql("                  ,?");
                        } else {
                            sql.addSql("                  ?");
                        }
                    }
                    sql.addSql("             ) ");
                    sql.addSql("          ) ");
                    sql.addSql("         or ");
                }

                sql.addSql("           BTI2.BFI_SID ");
                sql.addSql("             in ( ");
                sql.addSql("                 select ");
                sql.addSql("                   BBS_FOR_ADMIN.BFI_SID ");
                sql.addSql("                 from ");
                sql.addSql("                   BBS_FOR_ADMIN ");
                sql.addSql("                 where ");
                sql.addSql("                   BBS_FOR_ADMIN.USR_SID = ? ");
                sql.addSql("                 ) ");

                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addSql("        )");
                }
            }

            sql.addSql("        group by");
            sql.addSql("          BTI2.BFI_SID");
            sql.addSql("        ) RSVTHRE");
            sql.addSql("     on");
            sql.addSql("       BBS_FOR_INF.BFI_SID = RSVTHRE.BFI_SID2");

            sql.addSql("  where");
            sql.addSql("    BBS_FOR_INF.BFI_SID in (");
            for (int idx = 0; idx < usersForumAll.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("      ,?");
                } else {
                    sql.addSql("      ?");
                }
            }
            sql.addSql("    )");
            sql.addSql("  and");

            sql.addSql("    BBS_FOR_SUM.BFI_SID = BBS_FOR_INF.BFI_SID");

            sql.addSql("  order by");
            sql.addSql("    BBS_FOR_INF.BFI_LEVEL desc,");
            sql.addSql("    BBS_FOR_INF.BFI_PARENT_SID,");
            sql.addSql("    BBS_FOR_INF.BFI_SORT");
            if (end != 0) {
                sql.setPagingValue(start, (end - start + 1));
            }

            sql.addIntValue(GSConstBulletin.NEWUSER_THRE_VIEW_YES);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitFrDate);
            if (!admUsr) {
                if (usersForumWrite != null && usersForumWrite.size() > 0) {
                    sql.addIntValue(userSid);
                    for (BbsForInfModel bfiMdlWrite : usersForumWrite) {
                        sql.addIntValue(bfiMdlWrite.getBfiSid());
                    }
                }
                sql.addIntValue(userSid);
            }

            for (BbsForInfModel bfiMdlAll : usersForumAll) {
                sql.addIntValue(bfiMdlAll.getBfiSid());
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<BulletinDspModel> bdmList = new ArrayList<>();
            HashMap<Integer, List<BulletinDspModel>> familyMap = new HashMap<>();
            BulletinDspModel resultModel;
            int savedParentSid = -1;
            List<Integer> savedParentSidList = new ArrayList<Integer>();
            while (rs.next()) {

                resultModel = new BulletinDspModel();
                resultModel.setBfiSid(rs.getInt("BFI_SID"));
                resultModel.setBfiName(rs.getString("BFI_NAME"));
                resultModel.setBfiSort(rs.getInt("BFI_SORT"));
                resultModel.setBfiCmt(rs.getString("BFI_CMT"));
                resultModel.setBfiCmtView(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(resultModel.getBfiCmt()), ""));
                resultModel.setBfiReply(rs.getInt("BFI_REPLY"));
                resultModel.setImgBinSid(rs.getLong("BIN_SID"));
                resultModel.setBfiMread(rs.getInt("BFI_MREAD"));
                resultModel.setParentForumSid(rs.getInt("BFI_PARENT_SID"));
                resultModel.setForumLevel(rs.getInt("BFI_LEVEL"));
                resultModel.setBfsThreCnt(rs.getInt("THRE_CNT"));
                resultModel.setWriteCnt(rs.getInt("WRITE_CNT"));
                UDate bfsWrtDate = UDate.getInstanceTimestamp(rs.getTimestamp("BFS_WRT_DATE"));
                resultModel.setWriteDate(bfsWrtDate);
                if (bfsWrtDate != null) {
                    resultModel.setStrWriteDate(__createDateStr(bfsWrtDate));

                    if (UDateUtil.diffDay(bfsWrtDate, new UDate()) < newCnt) {
                        resultModel.setNewFlg(BulletinDspModel.NEWFLG_VIEW);
                    }
                }
                resultModel.setReadedCnt(rs.getInt("viewCnt"));
                //未読/既読を設定
                if ((resultModel.getBfsThreCnt() - rs.getInt("delCnt"))
                        <= resultModel.getReadedCnt()) {
                    resultModel.setReadFlg(BulletinDspModel.READFLG_READ);
                }
                resultModel.setBfsSize(rs.getLong("BFS_SIZE"));

                //掲示予定件数
                resultModel.setRsvThreCnt(rs.getInt("rsvThreCnt"));


                if (savedParentSid != -1
                        && savedParentSid != resultModel.getParentForumSid()) {
                    //親が同じフォーラムのフォーラム情報リストをマップにセット
                    familyMap.put(savedParentSid, bdmList);
                    bdmList = new ArrayList<>();
                }

                //同じ親を持つフォーラムのリストを作成
                bdmList.add(resultModel);

                if (!savedParentSidList.contains(savedParentSid)) {
                    //親になるフォーラムのSIDをセット
                    savedParentSidList.add(savedParentSid);
                }

                savedParentSid = resultModel.getParentForumSid();
            }
            //親が同じフォーラムのフォーラム情報リストをマップにセット
            familyMap.put(savedParentSid, bdmList);

            //階層を考慮してリストを並べ替える
            if (familyMap.size() > 0) {
                __getHisrarchyList(
                        ret, GSConstBulletin.BBS_DEFAULT_PFORUM_SID, familyMap);
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
     * <br>[機  能] 階層構造を考慮したリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ret フォーラム情報リスト
     * @param parentSid 親フォーラムSID
     * @param familyMap Key:親フォーラムSID Value:フォーラム情報 のHashMap
     */
    private void __getHisrarchyList(
            List<BulletinDspModel> ret, int parentSid,
            HashMap<Integer, List<BulletinDspModel>> familyMap) {
        //共通の親を持つフォーラムのリストを取得
        List<BulletinDspModel> childList = familyMap.get(parentSid);

        if (childList == null || childList.size() < 1) {
            return;
        }

        for (BulletinDspModel mdl : childList) {
            //子フォーラムの数を取得
            int numberOfchild = 0;
            if (familyMap.containsKey(mdl.getBfiSid())) {
                numberOfchild = familyMap.get(mdl.getBfiSid()).size();
            }

            //子フォーラムの数をセット
            mdl.setNumberOfChild(numberOfchild);

            //リストからフォーラム1つを結果リストに追加
            ret.add(mdl);

            if (numberOfchild > 0) {
                //このフォーラムを親にしているフォーラムのリストを取得する(再帰処理)
                __getHisrarchyList(ret, mdl.getBfiSid(), familyMap);
            }
        }
    }

    /**
     * <br>[機  能] 対象のフォーラムを親フォーラムとし、メンバー設定を準拠しているフォーラム一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param parentSidList 対象のフォーラムSIDリスト
     * @return 対象のフォーラムを親フォーラムとしてメンバー設定を準拠しているフォーラム一覧
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> getFollowingForumList(List<Integer> parentSidList)
            throws SQLException {
        List <BbsForInfModel> ret = new ArrayList <BbsForInfModel>();

        if (parentSidList == null || parentSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        int level = GSConstBulletin.BBS_FORUM_MIN_LEVEL;

        try {
            //1階層目で親フォーラムのメンバー準拠はあり得ないため飛ばす
            ++level;

            while (level <= GSConstBulletin.BBS_FORUM_MAX_LEVEL) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   BFI_SID,");
                sql.addSql("   BFI_NAME,");
                sql.addSql("   BFI_CMT,");
                sql.addSql("   BFI_SORT,");
                sql.addSql("   BFI_REPLY,");
                sql.addSql("   BFI_READ,");
                sql.addSql("   BIN_SID,");
                sql.addSql("   BFI_MREAD,");
                sql.addSql("   BFI_TEMPLATE_KBN,");
                sql.addSql("   BFI_TEMPLATE,");
                sql.addSql("   BFI_TEMPLATE_TYPE,");
                sql.addSql("   BFI_TEMPLATE_WRITE,");
                sql.addSql("   BFI_AUID,");
                sql.addSql("   BFI_ADATE,");
                sql.addSql("   BFI_EUID,");
                sql.addSql("   BFI_EDATE,");
                sql.addSql("   BFI_DISK,");
                sql.addSql("   BFI_DISK_SIZE,");
                sql.addSql("   BFI_WARN_DISK,");
                sql.addSql("   BFI_WARN_DISK_TH,");
                sql.addSql("   BFI_LIMIT,");
                sql.addSql("   BFI_LIMIT_DATE,");
                sql.addSql("   BFI_KEEP,");
                sql.addSql("   BFI_KEEP_DATE_Y,");
                sql.addSql("   BFI_KEEP_DATE_M,");
                sql.addSql("   BFI_LIMIT_ON,");
                sql.addSql("   BFI_PARENT_SID,");
                sql.addSql("   BFI_LEVEL,");
                sql.addSql("   BFI_FOLLOW_PARENT_MEM");
                sql.addSql(" from ");
                sql.addSql("   BBS_FOR_INF");
                sql.addSql(" where ");

                sql.addSql("   BFI_PARENT_SID in (");
                for (int i = 0; i < parentSidList.size(); ++i) {
                    if (i > 0) {
                        sql.addSql("   ,?");
                    } else {
                        sql.addSql("   ?");
                    }
                    sql.addIntValue(parentSidList.get(i));
                }
                sql.addSql("   )");
                sql.addSql(" and");

                sql.addSql("   BFI_LEVEL = ?");
                sql.addIntValue(level);

                sql.addSql(" and ");
                sql.addSql("   BFI_FOLLOW_PARENT_MEM = ?");
                sql.addIntValue(GSConstBulletin.FOLLOW_PARENT_MEMBER_YES);

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    BbsForInfModel bfiModel = __getBbsForInfFromRs(rs);
                    ret.add(bfiModel);

                    parentSidList.add(rs.getInt("BFI_SID"));
                }

                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);

                ++level;
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
     *
     * <br>[機  能]添付ファイル名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiSid 投稿SID
     * @return tempList 添付ファイル名
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getTempName(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> tempList = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_FILE_NAME, ");
            sql.addSql("   BIN_UPDATE ");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF ");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID ");
            sql.addSql(" in ");
            sql.addSql(" ( ");
            sql.addSql(" select ");
            sql.addSql("   BIN_SID ");
            sql.addSql(" from ");
            sql.addSql("   BBS_BIN ");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=? ");
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tempList.add(new LabelValueBean(
                        rs.getString("BIN_FILE_NAME"), rs.getString("BIN_UPDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return tempList;
    }

    /**
    *
    * <br>[機  能]グループSIDを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param groupSid グループSID
    * @return cnt カウント
    * @throws SQLException SQL実行例外
    */
   public Integer getUnknownGroup(List<String> groupSid) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       int nRtn = 0;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   count(GRP_SID) as CNT_SID");
           sql.addSql(" from ");
           sql.addSql("   CMN_GROUPM ");
           sql.addSql(" where ");
           sql.addSql("   GRP_SID IN ( ");
           for (int idx = 0; idx < groupSid.size(); idx++) {
               if (idx != 0) {
                   sql.addSql("   , ");
               }
               sql.addSql("   ? ");
               sql.addIntValue(Integer.parseInt(groupSid.get(idx)));
           }
           sql.addSql("   ) ");
           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           if (rs.next()) {
               nRtn = rs.getInt("CNT_SID");
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }

       return nRtn;
   }

    /**
     * <p>Create BBS_FOR_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsForInfModel
     * @throws SQLException SQL実行例外
     */
    private BbsForInfModel __getBbsForInfFromRs(ResultSet rs) throws SQLException {
        BbsForInfModel bean = new BbsForInfModel();
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBfiName(rs.getString("BFI_NAME"));
        bean.setBfiCmt(rs.getString("BFI_CMT"));
        bean.setBfiSort(rs.getInt("BFI_SORT"));
        bean.setBfiReply(rs.getInt("BFI_REPLY"));
        bean.setBfiRead(rs.getInt("BFI_READ"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBfiMread(rs.getInt("BFI_MREAD"));
        bean.setBfiTemplateKbn(rs.getInt("BFI_TEMPLATE_KBN"));
        bean.setBfiTemplate(rs.getString("BFI_TEMPLATE"));
        bean.setBfiTemplateType(rs.getInt("BFI_TEMPLATE_TYPE"));
        bean.setBfiTemplateWrite(rs.getInt("BFI_TEMPLATE_WRITE"));
        bean.setBfiAuid(rs.getInt("BFI_AUID"));
        bean.setBfiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_ADATE")));
        bean.setBfiEuid(rs.getInt("BFI_EUID"));
        bean.setBfiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_EDATE")));
        bean.setBfiDisk(rs.getInt("BFI_DISK"));
        bean.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
        bean.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
        bean.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));
        bean.setBfiLimit(rs.getInt("BFI_LIMIT"));
        bean.setBfiLimitDate(rs.getInt("BFI_LIMIT_DATE"));
        bean.setBfiKeep(rs.getInt("BFI_KEEP"));
        bean.setBfiKeepDateY(rs.getInt("BFI_KEEP_DATE_Y"));
        bean.setBfiKeepDateM(rs.getInt("BFI_KEEP_DATE_M"));
        bean.setBfiLimitOn(rs.getInt("BFI_LIMIT_ON"));
        bean.setBfiParentSid(rs.getInt("BFI_PARENT_SID"));
        bean.setBfiLevel(rs.getInt("BFI_LEVEL"));
        bean.setBfiFollowParentMem(rs.getInt("BFI_FOLLOW_PARENT_MEM"));
        return bean;
    }

    /**
     * <br>[機  能] 指定されたスレッドのフォーラムを変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param forumSid 移動先フォーラムSID
     * @param threadSidList 移動スレッドSIDリスト
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int updateBinfForMove(int forumSid, String[] threadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    BBS_THRE_INF");
            sql.addSql("  set");
            sql.addSql("    BFI_SID = ?");
            sql.addIntValue(forumSid);
            sql.addSql("  where");
            sql.addSql("    BTI_SID in (");
            boolean sepFlg = false;
            for (String sid : threadSidList) {
                if (sepFlg) {
                    sql.addSql("    ,");
                } else {
                    sepFlg = true;
                }
                sql.addSql("    ?");
                sql.addIntValue(Integer.parseInt(sid));
            }
            sql.addSql("    )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }
}
