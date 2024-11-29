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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュールに関するDB操作を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchDao extends AbstractDao {

    /** 役職 表示順 未設定 */
    public static final int POS_SORT_NONE = -1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchDao.class);

    /**
     * <p>Default Constructor
     */
    public SchDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したユーザが所属するグループ、およびスケジュールの閲覧が可能なグループのグループ情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @return ret List GroupModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> selectGroupNmListOrderbyClass(int usid) throws SQLException {

        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(usid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<GroupModel> ret = new ArrayList<GroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CL.SID as GROUPSID,");
            sql.addSql("  CL.NAME as GROUPNAME");
            sql.addSql(" from");
            sql.addSql(" (");
            sql.addSql(" select");
            sql.addSql("    GC.GCL_SID1 as GCL_SID1,");
            sql.addSql("    GC.GCL_SID2 as GCL_SID2,");
            sql.addSql("    GC.GCL_SID3 as GCL_SID3,");
            sql.addSql("    GC.GCL_SID4 as GCL_SID4,");
            sql.addSql("    GC.GCL_SID5 as GCL_SID5,");
            sql.addSql("    GC.GCL_SID6 as GCL_SID6,");
            sql.addSql("    GC.GCL_SID7 as GCL_SID7,");
            sql.addSql("    GC.GCL_SID8 as GCL_SID8,");
            sql.addSql("    GC.GCL_SID9 as GCL_SID9,");
            sql.addSql("    GC.GCL_SID10 as GCL_SID10,");
            sql.addSql("    GC.GCL_AUID,");
            sql.addSql("    GC.GCL_ADATE,");
            sql.addSql("    GC.GCL_EUID,");
            sql.addSql("    GC.GCL_EDATE,");
            sql.addSql("    GI1.GRP_NAME as GNAME1,");
            sql.addSql("    GI2.GRP_NAME as GNAME2,");
            sql.addSql("    GI3.GRP_NAME as GNAME3,");
            sql.addSql("    GI4.GRP_NAME as GNAME4,");
            sql.addSql("    GI5.GRP_NAME as GNAME5,");
            sql.addSql("    GI6.GRP_NAME as GNAME6,");
            sql.addSql("    GI7.GRP_NAME as GNAME7,");
            sql.addSql("    GI8.GRP_NAME as GNAME8,");
            sql.addSql("    GI9.GRP_NAME as GNAME9,");
            sql.addSql("    GI10.GRP_NAME as GNAME10,");
            sql.addSql("    case when GI1.GRP_NAME is null then '' else GI1.GRP_NAME end s1,");
            sql.addSql("    case when GI2.GRP_NAME is null then '' else GI2.GRP_NAME end s2,");
            sql.addSql("    case when GI3.GRP_NAME is null then '' else GI3.GRP_NAME end s3,");
            sql.addSql("    case when GI4.GRP_NAME is null then '' else GI4.GRP_NAME end s4,");
            sql.addSql("    case when GI5.GRP_NAME is null then '' else GI5.GRP_NAME end s5,");
            sql.addSql("    case when GI6.GRP_NAME is null then '' else GI6.GRP_NAME end s6,");
            sql.addSql("    case when GI7.GRP_NAME is null then '' else GI7.GRP_NAME end s7,");
            sql.addSql("    case when GI8.GRP_NAME is null then '' else GI8.GRP_NAME end s8,");
            sql.addSql("    case when GI9.GRP_NAME is null then '' else GI9.GRP_NAME end s9,");
            sql.addSql("    case when GI10.GRP_NAME is null then '' "
                    + "else GI10.GRP_NAME end s10,");
            sql.addSql("    (case when GC.GCL_SID10 > 0 then GC.GCL_SID10");
            sql.addSql("          when GC.GCL_SID9 > 0 then GC.GCL_SID9");
            sql.addSql("          when GC.GCL_SID8 > 0 then GC.GCL_SID8");
            sql.addSql("          when GC.GCL_SID7 > 0 then GC.GCL_SID7");
            sql.addSql("          when GC.GCL_SID6 > 0 then GC.GCL_SID6");
            sql.addSql("          when GC.GCL_SID5 > 0 then GC.GCL_SID5");
            sql.addSql("          when GC.GCL_SID4 > 0 then GC.GCL_SID4");
            sql.addSql("          when GC.GCL_SID3 > 0 then GC.GCL_SID3");
            sql.addSql("          when GC.GCL_SID2 > 0 then GC.GCL_SID2");
            sql.addSql("          when GC.GCL_SID1 > 0 then GC.GCL_SID1");
            sql.addSql("          else -1");
            sql.addSql("    end) SID,");
            sql.addSql("    (case when GI10.GRP_NAME is not null then GI10.GRP_NAME");
            sql.addSql("          when GI9.GRP_NAME is not null then GI9.GRP_NAME");
            sql.addSql("          when GI8.GRP_NAME is not null then GI8.GRP_NAME");
            sql.addSql("          when GI7.GRP_NAME is not null then GI7.GRP_NAME");
            sql.addSql("          when GI6.GRP_NAME is not null then GI6.GRP_NAME");
            sql.addSql("          when GI5.GRP_NAME is not null then GI5.GRP_NAME");
            sql.addSql("          when GI4.GRP_NAME is not null then GI4.GRP_NAME");
            sql.addSql("          when GI3.GRP_NAME is not null then GI3.GRP_NAME");
            sql.addSql("          when GI2.GRP_NAME is not null then GI2.GRP_NAME");
            sql.addSql("          when GI1.GRP_NAME is not null then GI1.GRP_NAME");
            sql.addSql("          else ''");
            sql.addSql("    end) as NAME");
            sql.addSql(" from");
            sql.addSql("    ((((((((((");
            sql.addSql("    CMN_GROUP_CLASS GC");
            sql.addSql("    left join CMN_GROUPM GI1 on GC.GCL_SID1 = GI1.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI2 on GC.GCL_SID2 = GI2.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI3 on GC.GCL_SID3 = GI3.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI4 on GC.GCL_SID4 = GI4.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI5 on GC.GCL_SID5 = GI5.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI6 on GC.GCL_SID6 = GI6.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI7 on GC.GCL_SID7 = GI7.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI8 on GC.GCL_SID8 = GI8.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI9 on GC.GCL_SID9 = GI9.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI10 on GC.GCL_SID10 = GI10.GRP_SID)");
            sql.addSql(" where");
            sql.addSql("    GI1.GRP_JKBN = 0");
            sql.addSql(" or    GI2.GRP_JKBN = 0");
            sql.addSql(" or    GI3.GRP_JKBN = 0");
            sql.addSql(" or    GI4.GRP_JKBN = 0");
            sql.addSql(" or    GI5.GRP_JKBN = 0");
            sql.addSql(" or    GI5.GRP_JKBN = 0");
            sql.addSql(" or    GI6.GRP_JKBN = 0");
            sql.addSql(" or    GI7.GRP_JKBN = 0");
            sql.addSql(" or    GI8.GRP_JKBN = 0");
            sql.addSql(" or    GI9.GRP_JKBN = 0");
            sql.addSql(" or    GI10.GRP_JKBN = 0");
            sql.addSql(" ) CL");

            //ユーザが所属するグループ
            sql.addSql(" where");
            sql.addSql("   CL.SID in (");
            sql.addSql("     select GRP_SID from CMN_BELONGM");
            sql.addSql("     where USR_SID=?");
            sql.addSql("   )");
            sql.addIntValue(usid);

            //スケジュールアクセス可能なグループ
            sql.addSql(" or");
            sql.addSql("   CL.SID in (");
            __setSpAccessGrpListSql(sql, usid, true, userPosSort, -1);
            sql.addSql("   )");

            //スケジュールアクセス可能なユーザが所属するグループ
            sql.addSql(" or");
            sql.addSql("   CL.SID in (");
            sql.addSql("     select");
            sql.addSql("       CMN_BELONGM.GRP_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       SCH_SPACCESS_TARGET");
            sql.addSql("     where");
            sql.addSql("        SCH_SPACCESS_TARGET.SST_TYPE = ?");
            sql.addIntValue(GSConstSchedule.SST_TYPE_USER);

            __setSpAccessUserListSql(sql, -1, usid, true, userPosSort, -1);
            sql.addSql("     and");
            sql.addSql("       SCH_SPACCESS_TARGET.SST_TSID = CMN_BELONGM.USR_SID");
            sql.addSql("   )");

            sql.addSql(" order by");
            sql.addSql("    CL.s1,");
            sql.addSql("    CL.s2,");
            sql.addSql("    CL.s3,");
            sql.addSql("    CL.s4,");
            sql.addSql("    CL.s5,");
            sql.addSql("    CL.s6,");
            sql.addSql("    CL.s7,");
            sql.addSql("    CL.s8,");
            sql.addSql("    CL.s9,");
            sql.addSql("    CL.s10,");
            sql.addSql("    CL.GCL_SID1,");
            sql.addSql("    CL.GCL_SID2,");
            sql.addSql("    CL.GCL_SID3,");
            sql.addSql("    CL.GCL_SID4,");
            sql.addSql("    CL.GCL_SID5,");
            sql.addSql("    CL.GCL_SID6,");
            sql.addSql("    CL.GCL_SID7,");
            sql.addSql("    CL.GCL_SID8,");
            sql.addSql("    CL.GCL_SID9,");
            sql.addSql("    CL.GCL_SID10");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            GroupModel gpMdl = null;
            while (rs.next()) {
                gpMdl = new GroupModel();
                gpMdl.setGroupSid(rs.getInt("GROUPSID"));
                gpMdl.setGroupName(rs.getString("GROUPNAME"));
                ret.add(gpMdl);
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
     * <br>[機  能] 指定したユーザに設定された役職の「並び順」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 役職の「並び順」
     * @throws SQLException SQL実行例外
     */
    public int getUserPosSort(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int posSort = POS_SORT_NONE;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_POSITION.POS_SORT as POS_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_POSITION.POS_SID > 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_POSITION.POS_SID = CMN_USRM_INF.POS_SID");
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                posSort = rs.getInt("POS_SORT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return posSort;
    }

    /**
     * <br>[機  能] ユーザがアクセス可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessGrpList(int userSid)
        throws SQLException {
        return getSpAccessGrpList(userSid, true, -1);
    }

    /**
     * <br>[機  能] ユーザがアクセス可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sspAuth 権限区分
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessGrpList(int userSid, int sspAuth)
        throws SQLException {
        return getSpAccessGrpList(userSid, true, sspAuth);
    }

    /**
     * <br>[機  能] ユーザがスケジュールを閲覧不可能グループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessGrpList(int userSid) throws SQLException {
        return getSpAccessGrpList(userSid, false, -1);
    }

    /**
     * <br>[機  能] ユーザがスケジュールの追加・変更・削除を不可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotRegistGrpList(int userSid) throws SQLException {
        return getSpAccessGrpList(userSid, false, GSConstSchedule.SSP_AUTH_EDIT);
    }

    /**
     * <br>[機  能] 指定したユーザの特例アクセスグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param sspAuth 権限区分
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSpAccessGrpList(int userSid, boolean type, int sspAuth)
    throws SQLException {

        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> grpList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSpAccessGrpListSql(sql, userSid, type, userPosSort, sspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                grpList.add(rs.getInt("SST_TSID"));
            }

            //アクセス不可グループ
            if (!type) {
                //所属グループ内のみ共有可の場合、所属グループ以外のグループは除外する
                int sadCrange = getSadCrange();
                if (sadCrange == GSConstSchedule.CRANGE_SHARE_GROUP) {
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closePreparedStatement(pstmt);

                    sql = new SqlBuffer();
                    sql.addSql(" select GRP_SID from CMN_GROUPM");
                    sql.addSql(" where GRP_JKBN = ?");
                    sql.addSql(" and");
                    sql.addSql("   GRP_SID not in (");
                    sql.addSql("     select GRP_SID from CMN_BELONGM");
                    sql.addSql("     where USR_SID = ?");
                    sql.addSql("   );");
                    sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                    sql.addIntValue(userSid);

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();

                    int notBelongGrpSid = 0;
                    while (rs.next()) {
                        notBelongGrpSid = rs.getInt("GRP_SID");
                        if (grpList.indexOf(notBelongGrpSid) < 0) {
                            grpList.add(notBelongGrpSid);
                        }
                    }
                }

                //アクセス不可グループを取得する場合、可能に設定されているグループを除外する
                List<Integer> accessGrpList = getAccessGrpList(userSid, sspAuth);
                List<Integer> notAccessGrpList = new ArrayList<Integer>();
                for (int grpSid : grpList) {
                    if (accessGrpList.indexOf(grpSid) < 0) {
                        notAccessGrpList.add(grpSid);
                    }
                }
                grpList.clear();
                grpList.addAll(notAccessGrpList);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpList;
    }

    /**
     * <br>[機  能] 指定したユーザの特例アクセスグループを取得するSQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param userPosSort ユーザの「役職 並び順」を取得
     * @param sspAuth 権限区分
     * @throws SQLException SQL実行時例外
     */
    private void __setSpAccessGrpListSql(SqlBuffer sql, int userSid, boolean type,
                                                            int userPosSort, int sspAuth)
    throws SQLException {

        sql.addSql("      select SST_TSID from SCH_SPACCESS_TARGET");
        sql.addSql("      where");
        sql.addSql("        SST_TYPE = ?");
        sql.addSql("      and");
        if (type) {
            sql.addSql("        SSA_SID in (");
        } else {
            sql.addSql("        SSA_SID not in (");
        }
        sql.addSql("          select SSA_SID from SCH_SPACCESS_PERMIT");
        sql.addSql("          where");
        sql.addSql("            (");
        sql.addSql("              (");
        sql.addSql("                SSP_TYPE = ?");
        sql.addSql("              and");
        sql.addSql("                SSP_PSID = ?");
        sql.addSql("              )");
        sql.addSql("            or");
        sql.addSql("              (");
        sql.addSql("                SSP_TYPE = ?");
        sql.addSql("              and");
        sql.addSql("                SSP_PSID in (");
        sql.addSql("                  select GRP_SID from CMN_BELONGM");
        sql.addSql("                  where USR_SID = ?");
        sql.addSql("                )");
        sql.addSql("              )");
        sql.addIntValue(GSConstSchedule.SST_TYPE_GROUP);
        sql.addIntValue(GSConstSchedule.SSP_TYPE_USER);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConstSchedule.SSP_TYPE_GROUP);
        sql.addIntValue(userSid);

        if (userPosSort != POS_SORT_NONE) {
            sql.addSql("            or");
            sql.addSql("              (");
            sql.addSql("                SSP_TYPE = ?");
            sql.addSql("              and");
            sql.addSql("                SSP_PSID in (");
            sql.addSql("                  select POS_SID from CMN_POSITION");
            sql.addSql("                  where POS_SID > 0");
            sql.addSql("                  and POS_SORT >= ?");
            sql.addSql("                )");
            sql.addSql("              )");
            sql.addIntValue(GSConstSchedule.SSP_TYPE_POSITION);
            sql.addIntValue(userPosSort);
        }
        sql.addSql("            )");

        if (sspAuth == GSConstSchedule.SSP_AUTH_EDIT
        || sspAuth == GSConstSchedule.SSP_AUTH_VIEWONLY) {
            sql.addSql("          and");
            sql.addSql("            SSP_AUTH = ?");
            sql.addIntValue(sspAuth);
        }

        sql.addSql("        )");
    }

    /**
     * <br>[機  能] スケジュールの閲覧が可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessUserList(int grpSid, int userSid) throws SQLException {
        return getSpAccessUserList(grpSid, userSid, true, -1);
    }

    /**
     * <br>[機  能] スケジュールの閲覧が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessUserList(int userSid) throws SQLException {
        return getSpAccessUserList(-1, userSid, false, -1);
    }

    /**
     * <br>[機  能] スケジュールの閲覧が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessUserList(int grpSid, int userSid) throws SQLException {
        return getSpAccessUserList(grpSid, userSid, false, -1);
    }

    /**
     * <br>[機  能] スケジュールの登録・編集・削除が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotRegistUserList(int userSid) throws SQLException {
        return getSpAccessUserList(-1, userSid, false, GSConstSchedule.SSP_AUTH_EDIT);
    }

    /**
     * <br>[機  能] 特例アクセスユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param sspAuth 権限区分
     * @return 特例アクセスユーザの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSpAccessUserList(int grpSid, int userSid, boolean type, int sspAuth)
    throws SQLException {
        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userList = new ArrayList<Integer>();
        con = getCon();

        try {
            //特例アクセスユーザを取得
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_SPACCESS_TARGET.SST_TSID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_SPACCESS_TARGET");
            sql.addSql(" where");
            sql.addSql("   SCH_SPACCESS_TARGET.SST_TYPE = ?");
            sql.addIntValue(GSConstSchedule.SST_TYPE_USER);
            __setSpAccessUserListSql(sql, grpSid, userSid, type, userPosSort, sspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getInt("USR_SID"));
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);

            //特例アクセス グループに所属するユーザを取得
            sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_SPACCESS_TARGET,");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("   SCH_SPACCESS_TARGET.SST_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = SCH_SPACCESS_TARGET.SST_TSID");
            sql.addIntValue(GSConstSchedule.SST_TYPE_GROUP);
            __setSpAccessUserListSql(sql, grpSid, userSid, type, userPosSort, sspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            int belongUsrSid = 0;
            while (rs.next()) {
                belongUsrSid = rs.getInt("USR_SID");
                if (userList.indexOf(belongUsrSid) < 0) {
                    userList.add(belongUsrSid);
                }
            }
            if (!type) {
                //所属グループ内のみ共有可の場合、所属グループ以外のユーザは除外する
                int sadCrange = getSadCrange();
                if (sadCrange == GSConstSchedule.CRANGE_SHARE_GROUP) {
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closePreparedStatement(pstmt);

                    sql = new SqlBuffer();
                    sql.addSql(" select USR_SID from CMN_USRM");
                    sql.addSql(" where USR_JKBN = ?");
                    sql.addSql(" and");
                    sql.addSql("   USR_SID not in (");
                    sql.addSql("   select");
                    sql.addSql("     CMN_BELONGM.USR_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_BELONGM,");
                    sql.addSql("     CMN_BELONGM BELONGM2");
                    sql.addSql("   where");
                    sql.addSql("     BELONGM2.USR_SID = ?");
                    sql.addSql("   and");
                    sql.addSql("     CMN_BELONGM.GRP_SID = BELONGM2.GRP_SID");
                    sql.addSql("   );");
                    sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                    sql.addIntValue(userSid);

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();

                    int notBelongUsrSid = 0;
                    while (rs.next()) {
                        notBelongUsrSid = rs.getInt("USR_SID");
                        if (userList.indexOf(notBelongUsrSid) < 0) {
                            userList.add(notBelongUsrSid);
                        }
                    }
                }
                //アクセス不可ユーザを取得する場合、アクセス可能に設定されているユーザを除外する
                List<Integer> accessUserList = getSpAccessUserList(grpSid, userSid, true, sspAuth);
                List<Integer> notAccessUserList = new ArrayList<Integer>();
                for (int notAccessUserSid : userList) {
                    if (accessUserList.indexOf(notAccessUserSid) < 0) {
                        notAccessUserList.add(notAccessUserSid);
                    }
                }
                userList.clear();
                userList.addAll(notAccessUserList);
                //判定対象ユーザを閲覧不可ユーザから除外する
                int userIndex = userList.indexOf(userSid);
                if (userIndex >= 0) {
                    userList.remove(userIndex);
                }

            } else {
                //判定対象ユーザを閲覧可能ユーザに含める
                if (userList.indexOf(userSid) < 0) {
                    userList.add(userSid);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userList;
    }


    /**
     * <br>[機  能] 特例アクセスユーザ取得時のSQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param userPosSort ユーザの「役職 並び順」
     * @param sspAuth 権限区分
     * @throws SQLException SQL実行時例外
     */
    public void __setSpAccessUserListSql(SqlBuffer sql, int grpSid, int userSid,
                                                            boolean type, int userPosSort,
                                                            int sspAuth)
    throws SQLException {
        sql.addSql(" and");
        if (type) {
            sql.addSql("   SCH_SPACCESS_TARGET.SSA_SID in (");
        } else {
            sql.addSql("   SCH_SPACCESS_TARGET.SSA_SID not in (");
        }
        sql.addSql("     select SSA_SID from SCH_SPACCESS_PERMIT");
        sql.addSql("     where");
        sql.addSql("       (");
        sql.addSql("         (");
        sql.addSql("           SSP_TYPE = ?");
        sql.addSql("         and");
        sql.addSql("           SSP_PSID = ?");
        sql.addSql("         )");
        sql.addSql("       or");
        sql.addSql("         (");
        sql.addSql("           SSP_TYPE = ?");
        sql.addSql("         and");
        sql.addSql("           SSP_PSID in (");
        sql.addSql("             select GRP_SID from CMN_BELONGM");
        sql.addSql("             where USR_SID = ?");
        sql.addSql("           )");
        sql.addSql("         )");
        sql.addIntValue(GSConstSchedule.SSP_TYPE_USER);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConstSchedule.SSP_TYPE_GROUP);
        sql.addIntValue(userSid);

        if (userPosSort != POS_SORT_NONE) {
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           SSP_TYPE = ?");
            sql.addSql("         and");
            sql.addSql("           SSP_PSID in (");
            sql.addSql("             select POS_SID from CMN_POSITION");
            sql.addSql("             where POS_SID > 0");
            sql.addSql("             and POS_SORT >= ?");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addIntValue(GSConstSchedule.SSP_TYPE_POSITION);
            sql.addIntValue(userPosSort);
        }

        sql.addSql("       )");

        if (sspAuth == GSConstSchedule.SSP_AUTH_EDIT
        || sspAuth == GSConstSchedule.SSP_AUTH_VIEWONLY) {
            sql.addSql("     and");
            sql.addSql("       SSP_AUTH = ?");
            sql.addIntValue(sspAuth);
        }

        sql.addSql("   )");
    }

    /**
     * <br>[機  能] 指定したグループのスケジュールを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessGroupSchedule(int grpSid, int sessionUserSid)
    throws SQLException {

        List<Integer> notAccessGrpList
            = getNotAccessGrpList(sessionUserSid);

        return notAccessGrpList.indexOf(grpSid) < 0;
    }

    /**
     * <br>[機  能] 指定したユーザのスケジュールを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessUserSchedule(int userSid, int sessionUserSid)
    throws SQLException {
        List<Integer> notAccessUserList
            = getNotAccessUserList(sessionUserSid);
        return notAccessUserList.indexOf(userSid) < 0;
    }

    /**
     * <br>[機  能] 指定したグループのスケジュールを登録可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistGroupSchedule(int grpSid, int sessionUserSid)
    throws SQLException {
        List<Integer> notRegistGrpList
            = getNotRegistGrpList(sessionUserSid);

        return notRegistGrpList.indexOf(grpSid) < 0;
    }

    /**
     * <br>[機  能] 指定したグループのスケジュールを登録可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param sessionUserSid セッションユーザSID
     * @param chkExist 存在しないユーザチェック
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistGroupSchedule(int grpSid, int sessionUserSid, boolean chkExist)
    throws SQLException {

        boolean ret = false;
        ret = canRegistGroupSchedule(grpSid, sessionUserSid);
        if (chkExist && ret) {
            CmnGroupmDao cgDao = new CmnGroupmDao(getCon());
            CmnGroupmModel cgMdl = cgDao.select(grpSid);
            if (cgMdl != null) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したユーザのスケジュールを登録可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistUserSchedule(int userSid, int sessionUserSid)
    throws SQLException {
        List<Integer> notRegistUserList
            = getNotRegistUserList(sessionUserSid);
        return notRegistUserList.indexOf(userSid) < 0;
    }

    /**
     * <br>[機  能] 指定したユーザのスケジュールを登録可能かを判定する(存在しないユーザチェック)
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sessionUserSid セッションユーザSID
     * @param chkExist 存在しないユーザチェック
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistUserSchedule(int userSid, int sessionUserSid, boolean chkExist)
    throws SQLException {

        boolean ret = false;
        ret = canRegistUserSchedule(userSid, sessionUserSid);
        if (chkExist && ret) {
            UserSearchDao usDao = new UserSearchDao(getCon());
            UserSearchModel usMdl = usDao.getUserInfoJtkb(userSid, GSConst.JTKBN_TOROKU);
            if (usMdl != null) {
                ret = true;
            } else {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したユーザが編集を許可されているユーザの所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getGroupBelongSpRegistUser(int userSid) throws SQLException {
        return getGroupBelongSpAccessUser(userSid, GSConstSchedule.SSP_AUTH_EDIT);
    }
    /**
     * <br>[機  能] 指定したユーザがアクセスを許可されているユーザの所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getGroupBelongSpAccessUser(int userSid) throws SQLException {
        return getGroupBelongSpAccessUser(userSid, -1);
    }
    /**
     * <br>[機  能] 指定したユーザがアクセスを許可されているユーザの所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sspAuth 権限区分
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getGroupBelongSpAccessUser(int userSid, int sspAuth) throws SQLException {

        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> grpList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    CMN_BELONGM.GRP_SID as GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   SCH_SPACCESS_TARGET");
            sql.addSql(" where");
            sql.addSql("   SCH_SPACCESS_TARGET.SST_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   SCH_SPACCESS_TARGET.SSA_SID in (");
            sql.addSql("     select SSA_SID from SCH_SPACCESS_PERMIT");
            sql.addSql("     where");
            sql.addSql("      (");
            sql.addSql("       (");
            sql.addSql("         SSP_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         SSP_PSID = ?");
            sql.addSql("       )");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("         SSP_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         SSP_PSID in (");
            sql.addSql("           select GRP_SID from CMN_BELONGM");
            sql.addSql("           where USR_SID = ?");
            sql.addSql("         )");
            sql.addSql("       )");

            sql.addIntValue(GSConstSchedule.SST_TYPE_USER);
            sql.addIntValue(GSConstSchedule.SSP_TYPE_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstSchedule.SSP_TYPE_GROUP);
            sql.addIntValue(userSid);

            if (userPosSort != POS_SORT_NONE) {
                sql.addSql("     or");
                sql.addSql("       (");
                sql.addSql("         SSP_TYPE = ?");
                sql.addSql("       and");
                sql.addSql("         SSP_PSID in (");
                sql.addSql("           select POS_SID from CMN_POSITION");
                sql.addSql("           where POS_SID > 0");
                sql.addSql("           and POS_SORT >= ?");
                sql.addSql("         )");
                sql.addSql("       )");
                sql.addIntValue(GSConstSchedule.SSP_TYPE_POSITION);
                sql.addIntValue(userPosSort);
            }
            sql.addSql("      )");

            if (sspAuth == GSConstSchedule.SSP_AUTH_EDIT
                    || sspAuth == GSConstSchedule.SSP_AUTH_VIEWONLY) {
                        sql.addSql("     and");
                        sql.addSql("       SSP_AUTH = ?");
                        sql.addIntValue(sspAuth);
                    }

            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = SCH_SPACCESS_TARGET.SST_TSID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                grpList.add(rs.getInt("GRP_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpList;
    }

    /**
     * <br>[機  能] スケジュール管理者設定 共有範囲を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return スケジュール管理者設定 共有範囲
     * @throws SQLException SQL実行時例外
     */
    public int getSadCrange() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sadCrange = GSConstSchedule.CRANGE_SHARE_ALL;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAD_CRANGE");
            sql.addSql(" from ");
            sql.addSql("   SCH_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sadCrange = rs.getInt("SAD_CRANGE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sadCrange;
    }

    /**
     * <br>[機  能] 指定したスケジュールのコンタクト履歴情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteScheduleContact(Connection con, int scdSid)
    throws SQLException {

        PreparedStatement pstmt = null;

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where");
            sql.addSql("   ADC_SID in (");
            sql.addSql("     select ADC_SID from SCH_ADDRESS");
            sql.addSql("     where");
            sql.addSql("       SCD_SID = ?");
            sql.addSql("     and");
            sql.addSql("       ADC_SID not in (");
            sql.addSql("         select ADC_SID from SCH_ADDRESS");
            sql.addSql("         where SCD_SID <> ?");
            sql.addSql("       )");
            sql.addSql("   )");

            sql.addIntValue(scdSid);
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
