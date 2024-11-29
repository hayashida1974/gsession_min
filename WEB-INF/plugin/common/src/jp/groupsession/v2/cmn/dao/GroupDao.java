package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr010.GrpCsvRecordListenerImpl;
import jp.groupsession.v2.usr.usr010.GrpExportModel;

/**
 * <br>[機 能] グループに関する操作を行うDAOクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GroupDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GroupDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public GroupDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public GroupDao(Connection con) {
        super(con);
    }

    /**
     * <p>グループツリー取得
     * @return ツリー表示用グループリスト
     */
    public ArrayList<GroupModel> getGroupTree() {
        return getGroupTree(null);
    }

    /**
     * <p>グループツリー取得
     * @param sortMdl ソート情報
     * @return ツリー表示用グループリスト
     */
    public ArrayList<GroupModel> getGroupTree(CmnCmbsortConfModel sortMdl) {
        return getGroupTree(sortMdl, false);
    }

    /**
     * <p>グループツリー取得
     * @param sortMdl ソート情報
     * @param viewTreeFlg グループ階層表示用か
     * @return ツリー表示用グループリスト
     */
    public ArrayList<GroupModel> getGroupTree(CmnCmbsortConfModel sortMdl, boolean viewTreeFlg) {

        ArrayList<GroupModel> ret = null;
        try {

            ArrayList<CmnGroupClassModel> list
                = (ArrayList<CmnGroupClassModel>) getGroupBeanList(sortMdl, viewTreeFlg);
            if (list == null || list.isEmpty()) {
                return null;
            }
            ret = new ArrayList<GroupModel>();
            Iterator<CmnGroupClassModel> it = list.iterator();

            while (it.hasNext()) {
                CmnGroupClassModel gcmodel = it.next();
                GroupModel gtModel = new GroupModel();
                //グループSIDをセット
                gtModel.setGroupSid(gcmodel.getLowGrpSid());
                //グループIDをセット
                gtModel.setGroupId(gcmodel.getLowGrpId());
                //グループ階層レベル
                gtModel.setClassLevel(gcmodel.getLowGrpLevel());
                //グループ名
                gtModel.setGroupName(gcmodel.getLowGrpName());
                log__.debug("グループSID = " + gtModel.getGroupSid());
                log__.debug("グループID = " + gtModel.getGroupId());
                log__.debug("グループ名 = " + gtModel.getGroupName());
                log__.debug("階層Lv = " + gtModel.getClassLevel());
                ret.add(gtModel);
            }
        } catch (SQLException e) {
            log__.error("SQLエラー", e);
        }
        return ret;
    }

    /**
     * <p>引数で指定したグループを取得する(複数)
     * @param gsids グループSID格納配列
     * @throws SQLException SQL実行例外
     * @return グループリスト
     */
    public List < CmnGroupmModel > getGroups(int[] gsids) throws SQLException  {

        CmnGroupmDao dao = new CmnGroupmDao(getCon());
        return dao.selectFromSid(gsids, CmnGroupmDao.GRP_JKBN_LIVING);
    }
    /**
     * <p>引数で指定したグループを取得する
     * @param gsid グループSID
     * @throws SQLException SQL実行例外
     * @return グループ情報
     */
    public CmnGroupmModel getGroup(int gsid) throws SQLException  {

        CmnGroupmDao dao = new CmnGroupmDao(getCon());
        return dao.select(gsid);
    }

    /**
     * <p>引数で指定したグループを取得する(複数)
     * @param gsids グループSID格納配列
     * @throws SQLException SQL実行例外
     * @return グループリスト
     */
    public List < CmnGroupmModel > getGroupsAll(int[] gsids) throws SQLException  {

        CmnGroupmDao dao = new CmnGroupmDao(getCon());
        return dao.selectFromSid(gsids);
    }
    /**
     * <p>引数で指定したグループを取得する
     * 削除グループを含む
     * @param gsid グループSID
     * @throws SQLException SQL実行例外
     * @return グループ情報
     */
    public CmnGroupmModel getGroupAll(int gsid) throws SQLException  {

        CmnGroupmDao dao = new CmnGroupmDao(getCon());
        return dao.selectGroup(gsid);
    }

    /**
     * <p>グループ階層用Listを取得します。
     * @param sortMdl ソート情報
     * @return List
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupClassModel> getGroupBeanList(CmnCmbsortConfModel sortMdl)
    throws SQLException {
        return getGroupBeanList(sortMdl, false, false);
    }

    /**
     * <p>グループ階層用Listを取得します。
     * @param sortMdl ソート情報
     * @param viewTreeFlg グループ階層表示用か
     * @return List
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupClassModel> getGroupBeanList(
            CmnCmbsortConfModel sortMdl, boolean viewTreeFlg) throws SQLException {
        return getGroupBeanList(sortMdl, false, false);
    }

    /**
     * <p>グループ階層用Listを取得します。
     * @param sortMdl ソート情報
     * @param viewTreeFlg グループ階層表示用か
     * @param cvsFlg cvsエクスポート用か
     * @return List
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupClassModel> getGroupBeanList(CmnCmbsortConfModel sortMdl,
                                                    boolean viewTreeFlg, boolean cvsFlg)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupClassModel> ret = new ArrayList<CmnGroupClassModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    GC.GCL_SID1,");
            sql.addSql("    GC.GCL_SID2,");
            sql.addSql("    GC.GCL_SID3,");
            sql.addSql("    GC.GCL_SID4,");
            sql.addSql("    GC.GCL_SID5,");
            sql.addSql("    GC.GCL_SID6,");
            sql.addSql("    GC.GCL_SID7,");
            sql.addSql("    GC.GCL_SID8,");
            sql.addSql("    GC.GCL_SID9,");
            sql.addSql("    GC.GCL_SID10,");
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
            sql.addSql("    GI1.GRP_ID as GID1,");
            sql.addSql("    GI2.GRP_ID as GID2,");
            sql.addSql("    GI3.GRP_ID as GID3,");
            sql.addSql("    GI4.GRP_ID as GID4,");
            sql.addSql("    GI5.GRP_ID as GID5,");
            sql.addSql("    GI6.GRP_ID as GID6,");
            sql.addSql("    GI7.GRP_ID as GID7,");
            sql.addSql("    GI8.GRP_ID as GID8,");
            sql.addSql("    GI9.GRP_ID as GID9,");
            sql.addSql("    GI10.GRP_ID as GID10,");
            if (sortMdl != null) {
                sql.addSql("  coalesce(GI1.GRP_ID, '') as si1,");
                sql.addSql("  coalesce(GI2.GRP_ID, '') as si2,");
                sql.addSql("  coalesce(GI3.GRP_ID, '') as si3,");
                sql.addSql("  coalesce(GI4.GRP_ID, '') as si4,");
                sql.addSql("  coalesce(GI5.GRP_ID, '') as si5,");
                sql.addSql("  coalesce(GI6.GRP_ID, '') as si6,");
                sql.addSql("  coalesce(GI7.GRP_ID, '') as si7,");
                sql.addSql("  coalesce(GI8.GRP_ID, '') as si8,");
                sql.addSql("  coalesce(GI9.GRP_ID, '') as si9,");
                sql.addSql("  coalesce(GI10.GRP_ID, '') as si10,");
            }
            sql.addSql("    coalesce(GI1.GRP_NAME, '')as s1,");
            sql.addSql("    coalesce(GI2.GRP_NAME, '') as s2,");
            sql.addSql("    coalesce(GI3.GRP_NAME, '') as s3,");
            sql.addSql("    coalesce(GI4.GRP_NAME, '') as s4,");
            sql.addSql("    coalesce(GI5.GRP_NAME, '') as s5,");
            sql.addSql("    coalesce(GI6.GRP_NAME, '') as s6,");
            sql.addSql("    coalesce(GI7.GRP_NAME, '') as s7,");
            sql.addSql("    coalesce(GI8.GRP_NAME, '') as s8,");
            sql.addSql("    coalesce(GI9.GRP_NAME, '') as s9,");
            sql.addSql("    coalesce(GI10.GRP_NAME, '') as s10");

            if (cvsFlg) {
                sql.addSql(",");
                sql.addSql("    coalesce(GI1.GRP_NAME_KN, '') as sk1,");
                sql.addSql("    coalesce(GI2.GRP_NAME_KN, '') as sk2,");
                sql.addSql("    coalesce(GI3.GRP_NAME_KN, '') as sk3,");
                sql.addSql("    coalesce(GI4.GRP_NAME_KN, '') as sk4,");
                sql.addSql("    coalesce(GI5.GRP_NAME_KN, '') as sk5,");
                sql.addSql("    coalesce(GI6.GRP_NAME_KN, '') as sk6,");
                sql.addSql("    coalesce(GI7.GRP_NAME_KN, '') as sk7,");
                sql.addSql("    coalesce(GI8.GRP_NAME_KN, '') as sk8,");
                sql.addSql("    coalesce(GI9.GRP_NAME_KN, '') as sk9,");
                sql.addSql("    coalesce(GI10.GRP_NAME_KN, '') as sk10,");
                sql.addSql("    case when GI1.GRP_ID is null then '' else '' end as psi1,");
                sql.addSql("    case when GI2.GRP_ID is null then '' else GI1.GRP_ID end as psi2,");
                sql.addSql("    case when GI3.GRP_ID is null then '' else GI2.GRP_ID end as psi3,");
                sql.addSql("    case when GI4.GRP_ID is null then '' else GI3.GRP_ID end as psi4,");
                sql.addSql("    case when GI5.GRP_ID is null then '' else GI4.GRP_ID end as psi5,");
                sql.addSql("    case when GI6.GRP_ID is null then '' else GI5.GRP_ID end as psi6,");
                sql.addSql("    case when GI7.GRP_ID is null then '' else GI6.GRP_ID end as psi7,");
                sql.addSql("    case when GI8.GRP_ID is null then '' else GI7.GRP_ID end as psi8,");
                sql.addSql("    case when GI9.GRP_ID is null then '' else GI8.GRP_ID end as psi9,");
                sql.addSql("    case when GI10.GRP_ID is null then '' "
                        + "else GI9.GRP_ID end as psi10,");
                sql.addSql("    coalesce(GI1.GRP_COMMENT, '') as cmt1,");
                sql.addSql("    coalesce(GI2.GRP_COMMENT, '') as cmt2,");
                sql.addSql("    coalesce(GI3.GRP_COMMENT, '') as cmt3,");
                sql.addSql("    coalesce(GI4.GRP_COMMENT, '') as cmt4,");
                sql.addSql("    coalesce(GI5.GRP_COMMENT, '') as cmt5,");
                sql.addSql("    coalesce(GI6.GRP_COMMENT, '') as cmt6,");
                sql.addSql("    coalesce(GI7.GRP_COMMENT, '') as cmt7,");
                sql.addSql("    coalesce(GI8.GRP_COMMENT, '') as cmt8,");
                sql.addSql("    coalesce(GI9.GRP_COMMENT, '') as cmt9,");
                sql.addSql("    coalesce(GI10.GRP_COMMENT, '') as cmt10");
            }

            sql.addSql(" from");
            sql.addSql("    CMN_GROUP_CLASS GC");
            sql.addSql("    left join CMN_GROUPM GI1 on GC.GCL_SID1 = GI1.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI2 on GC.GCL_SID2 = GI2.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI3 on GC.GCL_SID3 = GI3.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI4 on GC.GCL_SID4 = GI4.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI5 on GC.GCL_SID5 = GI5.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI6 on GC.GCL_SID6 = GI6.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI7 on GC.GCL_SID7 = GI7.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI8 on GC.GCL_SID8 = GI8.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI9 on GC.GCL_SID9 = GI9.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI10 on GC.GCL_SID10 = GI10.GRP_SID");
            sql.addSql(" where");
            sql.addSql("    GI1.GRP_JKBN = 0");
            sql.addSql(" or    GI2.GRP_JKBN = 0");
            sql.addSql(" or    GI3.GRP_JKBN = 0");
            sql.addSql(" or    GI4.GRP_JKBN = 0");
            sql.addSql(" or    GI5.GRP_JKBN = 0");
            sql.addSql(" or    GI6.GRP_JKBN = 0");
            sql.addSql(" or    GI7.GRP_JKBN = 0");
            sql.addSql(" or    GI8.GRP_JKBN = 0");
            sql.addSql(" or    GI9.GRP_JKBN = 0");
            sql.addSql(" or    GI10.GRP_JKBN = 0");

            sql.addSql(" order by");

            if (sortMdl == null) {
                sql.addSql("    s1,");
                sql.addSql("    s2,");
                sql.addSql("    s3,");
                sql.addSql("    s4,");
                sql.addSql("    s5,");
                sql.addSql("    s6,");
                sql.addSql("    s7,");
                sql.addSql("    s8,");
                sql.addSql("    s9,");
                sql.addSql("    s10,");
                sql.addSql("    GC.GCL_SID1,");
                sql.addSql("    GC.GCL_SID2,");
                sql.addSql("    GC.GCL_SID3,");
                sql.addSql("    GC.GCL_SID4,");
                sql.addSql("    GC.GCL_SID5,");
                sql.addSql("    GC.GCL_SID6,");
                sql.addSql("    GC.GCL_SID7,");
                sql.addSql("    GC.GCL_SID8,");
                sql.addSql("    GC.GCL_SID9,");
                sql.addSql("    GC.GCL_SID10");
            } else {

                String order1 = "asc";
                if (sortMdl.getCscGroupOrder1() == GSConst.ORDER_KEY_DESC) {
                    order1 = "desc";
                }
                switch (sortMdl.getCscGroupSkey1()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        if (viewTreeFlg && sortMdl.getCscGroupOrder1() == GSConst.ORDER_KEY_DESC) {
                            sql.addSql("    si1 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID2 = -1 then 0 else 1 end,");
                            sql.addSql("    si2 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID3 = -1 then 0 else 1 end,");
                            sql.addSql("    si3 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID4 = -1 then 0 else 1 end,");
                            sql.addSql("    si4 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID5 = -1 then 0 else 1 end,");
                            sql.addSql("    si5 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID6 = -1 then 0 else 1 end,");
                            sql.addSql("    si6 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID7 = -1 then 0 else 1 end,");
                            sql.addSql("    si7 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID8 = -1 then 0 else 1 end,");
                            sql.addSql("    si8 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID9 = -1 then 0 else 1 end,");
                            sql.addSql("    si9 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID10 = -1 then 0 else 1 end,");
                            sql.addSql("    si10 " + order1 + ",");

                        } else {
                            sql.addSql("    si1 " + order1 + ",");
                            sql.addSql("    si2 " + order1 + ",");
                            sql.addSql("    si3 " + order1 + ",");
                            sql.addSql("    si4 " + order1 + ",");
                            sql.addSql("    si5 " + order1 + ",");
                            sql.addSql("    si6 " + order1 + ",");
                            sql.addSql("    si7 " + order1 + ",");
                            sql.addSql("    si8 " + order1 + ",");
                            sql.addSql("    si9 " + order1 + ",");
                            sql.addSql("    si10 " + order1 + ",");
                        }
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        if (viewTreeFlg && sortMdl.getCscGroupOrder1() == GSConst.ORDER_KEY_DESC) {
                            sql.addSql("    s1 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID2 = -1 then 0 else 1 end,");
                            sql.addSql("    s2 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID3 = -1 then 0 else 1 end,");
                            sql.addSql("    s3 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID4 = -1 then 0 else 1 end,");
                            sql.addSql("    s4 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID5 = -1 then 0 else 1 end,");
                            sql.addSql("    s5 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID6 = -1 then 0 else 1 end,");
                            sql.addSql("    s6 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID7 = -1 then 0 else 1 end,");
                            sql.addSql("    s7 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID8 = -1 then 0 else 1 end,");
                            sql.addSql("    s8 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID9 = -1 then 0 else 1 end,");
                            sql.addSql("    s9 " + order1 + ",");
                            sql.addSql("    case when GC.GCL_SID10 = -1 then 0 else 1 end,");
                            sql.addSql("    s10 " + order1 + ",");
                        } else {
                            sql.addSql("    s1 " + order1 + ",");
                            sql.addSql("    s2 " + order1 + ",");
                            sql.addSql("    s3 " + order1 + ",");
                            sql.addSql("    s4 " + order1 + ",");
                            sql.addSql("    s5 " + order1 + ",");
                            sql.addSql("    s6 " + order1 + ",");
                            sql.addSql("    s7 " + order1 + ",");
                            sql.addSql("    s8 " + order1 + ",");
                            sql.addSql("    s9 " + order1 + ",");
                            sql.addSql("    s10 " + order1 + ",");
                        }
                        break;
                    default:
                        break;
                }

                String order2 = "asc";
                if (sortMdl.getCscGroupOrder2() == GSConst.ORDER_KEY_DESC) {
                    order2 = "desc";
                }
                switch (sortMdl.getCscGroupSkey2()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        sql.addSql("    si1 " + order2 + ",");
                        sql.addSql("    si2 " + order2 + ",");
                        sql.addSql("    si3 " + order2 + ",");
                        sql.addSql("    si4 " + order2 + ",");
                        sql.addSql("    si5 " + order2 + ",");
                        sql.addSql("    si6 " + order2 + ",");
                        sql.addSql("    si7 " + order2 + ",");
                        sql.addSql("    si8 " + order2 + ",");
                        sql.addSql("    si9 " + order2 + ",");
                        sql.addSql("    si10 " + order2 + ",");
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        sql.addSql("    s1 " + order2 + ",");
                        sql.addSql("    s2 " + order2 + ",");
                        sql.addSql("    s3 " + order2 + ",");
                        sql.addSql("    s4 " + order2 + ",");
                        sql.addSql("    s5 " + order2 + ",");
                        sql.addSql("    s6 " + order2 + ",");
                        sql.addSql("    s7 " + order2 + ",");
                        sql.addSql("    s8 " + order2 + ",");
                        sql.addSql("    s9 " + order2 + ",");
                        sql.addSql("    s10 " + order2 + ",");
                        break;
                    default:
                        break;
                }

                sql.addSql("    GC.GCL_SID1,");
                sql.addSql("    GC.GCL_SID2,");
                sql.addSql("    GC.GCL_SID3,");
                sql.addSql("    GC.GCL_SID4,");
                sql.addSql("    GC.GCL_SID5,");
                sql.addSql("    GC.GCL_SID6,");
                sql.addSql("    GC.GCL_SID7,");
                sql.addSql("    GC.GCL_SID8,");
                sql.addSql("    GC.GCL_SID9,");
                sql.addSql("    GC.GCL_SID10");
            }

            log__.info("SQL ==" + sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnGroupClassModel model = new CmnGroupClassModel();
                model.setGclSid1(rs.getInt("GCL_SID1"));
                model.setGclSid2(rs.getInt("GCL_SID2"));
                model.setGclSid3(rs.getInt("GCL_SID3"));
                model.setGclSid4(rs.getInt("GCL_SID4"));
                model.setGclSid5(rs.getInt("GCL_SID5"));
                model.setGclSid6(rs.getInt("GCL_SID6"));
                model.setGclSid7(rs.getInt("GCL_SID7"));
                model.setGclSid8(rs.getInt("GCL_SID8"));
                model.setGclSid9(rs.getInt("GCL_SID9"));
                model.setGclSid10(rs.getInt("GCL_SID10"));
                model.setGclAuid(rs.getInt("GCL_AUID"));
                model.setGclAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_ADATE")));
                model.setGclEuid(rs.getInt("GCL_EUID"));
                model.setGclEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_EDATE")));
                model.setGclName1(rs.getString("GNAME1"));
                model.setGclName2(rs.getString("GNAME2"));
                model.setGclName3(rs.getString("GNAME3"));
                model.setGclName4(rs.getString("GNAME4"));
                model.setGclName5(rs.getString("GNAME5"));
                model.setGclName6(rs.getString("GNAME6"));
                model.setGclName7(rs.getString("GNAME7"));
                model.setGclName8(rs.getString("GNAME8"));
                model.setGclName9(rs.getString("GNAME9"));
                model.setGclName10(rs.getString("GNAME10"));
                model.setGclId1(rs.getString("GId1"));
                model.setGclId2(rs.getString("GId2"));
                model.setGclId3(rs.getString("GId3"));
                model.setGclId4(rs.getString("GId4"));
                model.setGclId5(rs.getString("GId5"));
                model.setGclId6(rs.getString("GId6"));
                model.setGclId7(rs.getString("GId7"));
                model.setGclId8(rs.getString("GId8"));
                model.setGclId9(rs.getString("GId9"));
                model.setGclId10(rs.getString("GId10"));

                if (cvsFlg) {
                    model.setGclKanaName1(rs.getString("sk1"));
                    model.setGclKanaName2(rs.getString("sk2"));
                    model.setGclKanaName3(rs.getString("sk3"));
                    model.setGclKanaName4(rs.getString("sk4"));
                    model.setGclKanaName5(rs.getString("sk5"));
                    model.setGclKanaName6(rs.getString("sk6"));
                    model.setGclKanaName7(rs.getString("sk7"));
                    model.setGclKanaName8(rs.getString("sk8"));
                    model.setGclKanaName9(rs.getString("sk9"));
                    model.setGclKanaName10(rs.getString("sk10"));
                    model.setGclPid1(rs.getString("psi1"));
                    model.setGclPid2(rs.getString("psi2"));
                    model.setGclPid3(rs.getString("psi3"));
                    model.setGclPid4(rs.getString("psi4"));
                    model.setGclPid5(rs.getString("psi5"));
                    model.setGclPid6(rs.getString("psi6"));
                    model.setGclPid7(rs.getString("psi7"));
                    model.setGclPid8(rs.getString("psi8"));
                    model.setGclPid9(rs.getString("psi9"));
                    model.setGclPid10(rs.getString("psi10"));
                    model.setGclCmt1(rs.getString("cmt1"));
                    model.setGclCmt2(rs.getString("cmt2"));
                    model.setGclCmt3(rs.getString("cmt3"));
                    model.setGclCmt4(rs.getString("cmt4"));
                    model.setGclCmt5(rs.getString("cmt5"));
                    model.setGclCmt6(rs.getString("cmt6"));
                    model.setGclCmt7(rs.getString("cmt7"));
                    model.setGclCmt8(rs.getString("cmt8"));
                    model.setGclCmt9(rs.getString("cmt9"));
                    model.setGclCmt10(rs.getString("cmt10"));
                }
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
     * <p>グループ階層モデルを取得します。
     * @param gSid グループSID
     * @throws SQLException SQLエクセプション
     * @return GroupClassModel
     * @throws SQLException SQLException
     */
    public CmnGroupClassModel getGroupClassModel(int gSid) throws SQLException {
        if (gSid == -1) {
            return null;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupClassModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("    GC.GCL_SID1,");
            sql.addSql("    GC.GCL_SID2,");
            sql.addSql("    GC.GCL_SID3,");
            sql.addSql("    GC.GCL_SID4,");
            sql.addSql("    GC.GCL_SID5,");
            sql.addSql("    GC.GCL_SID6,");
            sql.addSql("    GC.GCL_SID7,");
            sql.addSql("    GC.GCL_SID8,");
            sql.addSql("    GC.GCL_SID9,");
            sql.addSql("    GC.GCL_SID10,");
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
            sql.addSql("    GI10.GRP_NAME as GNAME10");
            sql.addSql(" from ");
            sql.addSql("    CMN_GROUP_CLASS GC");
            sql.addSql("    left join CMN_GROUPM GI1 on GC.GCL_SID1 = GI1.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI2 on GC.GCL_SID2 = GI2.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI3 on GC.GCL_SID3 = GI3.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI4 on GC.GCL_SID4 = GI4.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI5 on GC.GCL_SID5 = GI5.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI6 on GC.GCL_SID6 = GI6.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI7 on GC.GCL_SID7 = GI7.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI8 on GC.GCL_SID8 = GI8.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI9 on GC.GCL_SID9 = GI9.GRP_SID");
            sql.addSql("    left join CMN_GROUPM GI10 on GC.GCL_SID10 = GI10.GRP_SID");
            sql.addSql(" where");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID1 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID2 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID3 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID2 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID3 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID3 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID4 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID5 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID6 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID7 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID8 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID9 = ?");
            sql.addSql(" and");
            sql.addSql("    GC.GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GC.GCL_SID10 = ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.setParameter(pstmt);
            log__.debug("SQL ==" + sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = new CmnGroupClassModel();
                model.setGclSid1(rs.getInt("GCL_SID1"));
                model.setGclSid2(rs.getInt("GCL_SID2"));
                model.setGclSid3(rs.getInt("GCL_SID3"));
                model.setGclSid4(rs.getInt("GCL_SID4"));
                model.setGclSid5(rs.getInt("GCL_SID5"));
                model.setGclSid6(rs.getInt("GCL_SID6"));
                model.setGclSid7(rs.getInt("GCL_SID7"));
                model.setGclSid8(rs.getInt("GCL_SID8"));
                model.setGclSid9(rs.getInt("GCL_SID9"));
                model.setGclSid10(rs.getInt("GCL_SID10"));
                model.setGclAuid(rs.getInt("GCL_AUID"));
                model.setGclAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_ADATE")));
                model.setGclEuid(rs.getInt("GCL_EUID"));
                model.setGclEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_EDATE")));
                model.setGclName1(rs.getString("GNAME1"));
                model.setGclName2(rs.getString("GNAME2"));
                model.setGclName3(rs.getString("GNAME3"));
                model.setGclName4(rs.getString("GNAME4"));
                model.setGclName5(rs.getString("GNAME5"));
                model.setGclName6(rs.getString("GNAME6"));
                model.setGclName7(rs.getString("GNAME7"));
                model.setGclName8(rs.getString("GNAME8"));
                model.setGclName9(rs.getString("GNAME9"));
                model.setGclName10(rs.getString("GNAME10"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }

    /**
     * <p>指定グループ以下のグループ階層Listを取得します。
     * @param gSid グループSID
     * @throws SQLException SQLエクセプション
     * @return ArrayList
     */
    public ArrayList<CmnGroupClassModel> getUnderGroupClassList(int gSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupClassModel> ret = new ArrayList<CmnGroupClassModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("    GCL_SID1,");
            sql.addSql("    GCL_SID2,");
            sql.addSql("    GCL_SID3,");
            sql.addSql("    GCL_SID4,");
            sql.addSql("    GCL_SID5,");
            sql.addSql("    GCL_SID6,");
            sql.addSql("    GCL_SID7,");
            sql.addSql("    GCL_SID8,");
            sql.addSql("    GCL_SID9,");
            sql.addSql("    GCL_SID10,");
            sql.addSql("    GCL_AUID,");
            sql.addSql("    GCL_ADATE,");
            sql.addSql("    GCL_EUID,");
            sql.addSql("    GCL_EDATE");
            sql.addSql(" from ");
            sql.addSql("    CMN_GROUP_CLASS");
            sql.addSql(" where ");
            sql.addSql("    GCL_SID1 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID2 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID3 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID4 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID5 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID6 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID7 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID8 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID9 = ?");
            sql.addSql(" or ");
            sql.addSql("    GCL_SID10 = ?");
            sql.addSql(" order by");
            sql.addSql("    GCL_SID1,");
            sql.addSql("    GCL_SID2,");
            sql.addSql("    GCL_SID3,");
            sql.addSql("    GCL_SID4,");
            sql.addSql("    GCL_SID5,");
            sql.addSql("    GCL_SID6,");
            sql.addSql("    GCL_SID7,");
            sql.addSql("    GCL_SID8,");
            sql.addSql("    GCL_SID9,");
            sql.addSql("    GCL_SID10");

            log__.debug("SQL ==" + sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.addIntValue(gSid);
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            CmnGroupClassModel model = null;
            while (rs.next()) {
                model = new CmnGroupClassModel();
                model.setGclSid1(rs.getInt("GCL_SID1"));
                model.setGclSid2(rs.getInt("GCL_SID2"));
                model.setGclSid3(rs.getInt("GCL_SID3"));
                model.setGclSid4(rs.getInt("GCL_SID4"));
                model.setGclSid5(rs.getInt("GCL_SID5"));
                model.setGclSid6(rs.getInt("GCL_SID6"));
                model.setGclSid7(rs.getInt("GCL_SID7"));
                model.setGclSid8(rs.getInt("GCL_SID8"));
                model.setGclSid9(rs.getInt("GCL_SID9"));
                model.setGclSid10(rs.getInt("GCL_SID10"));
                model.setGclAuid(rs.getInt("GCL_AUID"));
                model.setGclAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_ADATE")));
                model.setGclEuid(rs.getInt("GCL_EUID"));
                model.setGclEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_EDATE")));
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
     * <p>引数で指定したユーザが、指定したグループに所属しているか判定を行う
     * @param usid ユーザSID
     * @param gsid グループSID
     * @return true:所属している, false:所属していない
     * @throws SQLException SQL実行例外
     */
    public boolean isBelong(int usid, int gsid) throws SQLException {
        boolean retFlg = false;
        //
        CmnBelongmDao dao = new CmnBelongmDao(getCon());
        try {
            CmnBelongmModel bmodel = dao.select(usid, gsid);
            if (bmodel != null) {
                retFlg = true;
            }
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        }
        return retFlg;
    }

    /**
     * <p>引数で指定したユーザが、管理者グループに所属しているか判定を行う
     * @param usid ユーザSID
     * @return true:所属している, false:所属していない
     * @throws SQLException SQL実行例外
     */
    public boolean isBelongAdmin(int usid) throws SQLException {
        //
        return isBelong(usid, GSConstUser.SID_ADMIN);
    }

    /**
     * <p>引数で指定したユーザのデフォルトグループを取得する
     * @param usid ユーザSID
     * @throws SQLException SQL実行例外
     * @return グループ情報
     */
    public CmnGroupmModel getDefaultGroup(int usid) throws SQLException  {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select ");
            sql.addSql("  CMN_GROUPM.GRP_SID, ");
            sql.addSql("  CMN_GROUPM.GRP_ID, ");
            sql.addSql("  CMN_GROUPM.GRP_NAME, ");
            sql.addSql("  CMN_GROUPM.GRP_NAME_KN, ");
            sql.addSql("  CMN_GROUPM.GRP_COMMENT, ");
            sql.addSql("  CMN_GROUPM.GRP_AUID, ");
            sql.addSql("  CMN_GROUPM.GRP_ADATE, ");
            sql.addSql("  CMN_GROUPM.GRP_EUID, ");
            sql.addSql("  CMN_GROUPM.GRP_EDATE, ");
            sql.addSql("  CMN_GROUPM.GRP_SORT, ");
            sql.addSql("  CMN_GROUPM.GRP_JKBN ");
            sql.addSql("from ");
            sql.addSql("  CMN_BELONGM, ");
            sql.addSql("  CMN_GROUPM ");
            sql.addSql("where");
            sql.addSql("  CMN_BELONGM.USR_SID = ? ");
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.BEG_DEFGRP = ? ");
            sql.addSql("and");
            sql.addSql("  CMN_GROUPM.GRP_JKBN = ? ");
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");

            sql.addIntValue(usid);
            sql.addIntValue(1);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info("SQL ==" + sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new CmnGroupmModel();
                ret.setGrpSid(rs.getInt("GRP_SID"));
                ret.setGrpId(rs.getString("GRP_ID"));
                ret.setGrpName(rs.getString("GRP_NAME"));
                ret.setGrpNameKn(rs.getString("GRP_NAME_KN"));
                ret.setGrpComment(rs.getString("GRP_COMMENT"));
                ret.setGrpAuid(rs.getInt("GRP_AUID"));
                ret.setGrpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GRP_ADATE")));
                ret.setGrpEuid(rs.getInt("GRP_EUID"));
                ret.setGrpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GRP_EDATE")));
                ret.setGrpSort(rs.getInt("GRP_SORT"));
                ret.setGrpJkbn(rs.getInt("GRP_JKBN"));
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
     * <p>引数で指定したユーザのデフォルトグループを取得する
     * @param usids ユーザSIDリスト
     * @throws SQLException SQL実行例外
     * @return グループ情報
     */
    public Map<Integer, String> getDefaultGroups(int[] usids) throws SQLException  {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, String> ret = new HashMap<Integer, String>();
        con = getCon();

        if (usids.length == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select ");
            sql.addSql("  CMN_BELONGM.USR_SID,");
            sql.addSql("  CMN_GROUPM.GRP_NAME ");
            sql.addSql("from ");
            sql.addSql("  CMN_BELONGM, ");
            sql.addSql("  CMN_GROUPM ");
            sql.addSql("where");
            sql.addSql("  CMN_BELONGM.USR_SID in (");
            boolean initFlg = true;
            for (int usid : usids) {
                if (!initFlg) {
                    sql.addSql("   ,");
                }
                initFlg = false;
                sql.addSql("   ?");
                sql.addIntValue(usid);
            }
            sql.addSql("   )");
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.BEG_DEFGRP = ? ");
            sql.addIntValue(1);
            sql.addSql("and");
            sql.addSql("  CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info("SQL ==" + sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.put(rs.getInt("USR_SID"), rs.getString("GRP_NAME"));
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
     * <br>[機  能] 指定されたユーザSID-Aのユーザが所属しているグループに
     * <br>         指定されたユーザSID-Bのユーザが所属しているか判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSidA ユーザSID
     * @param usrSidB ユーザSID
     * @return ret true:所属している false:所属していない
     * @throws SQLException SQL実行例外
     */
    public boolean isSameGroupUser(int usrSidA, int usrSidB) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_BELONGM.USR_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   (select ");
            sql.addSql("      GRP_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   ) target");
            sql.addSql(" where");
            sql.addSql("   CMN_BELONGM.GRP_SID = target.GRP_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSidA);
            sql.addIntValue(usrSidB);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <br>[機  能] 指定されたユーザSID-Aのユーザが所属しているグループに
     * <br>         指定されたユーザSID-Bのユーザが所属しているか判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSidA ユーザSID-A
     * @param usrSidBList ユーザSID-B
     * @return ret true:所属している false:所属していない
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getSameGroupUser(int usrSidA, List<Integer> usrSidBList)
            throws SQLException {
        List<Integer> ret = new ArrayList<Integer>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        if (usrSidBList == null || usrSidBList.size() == 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   (select ");
            sql.addSql("      GRP_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   ) target");
            sql.addSql(" where");
            sql.addSql("   CMN_BELONGM.GRP_SID = target.GRP_SID");
            sql.addSql(" and");
            sql.addSql("   (CMN_BELONGM.USR_SID in (");
            for (int i = 0; i < usrSidBList.size(); i++) {
                if (i > 0 && i % 1000 == 0) {
                    sql.addSql("  ) or USR_SID in (");
                }
                if (i % 1000 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(usrSidBList.get(i)));
            }
            sql.addSql("  ))");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSidA);
            log__.info(sql.toLogString());

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
     * <p>引数で指定したユーザの所属グループを取得する
     * @param sidList ユーザSIDリスト
     * @throws SQLException SQL実行例外
     * @return グループ情報
     */
    public List<Integer> getUserGroup(List<Integer> sidList) throws SQLException  {
        List<Integer> ret = new ArrayList<Integer>();
        if (sidList.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select ");
            sql.addSql("  CMN_GROUPM.GRP_SID ");
            sql.addSql("from ");
            sql.addSql("  CMN_BELONGM, ");
            sql.addSql("  CMN_GROUPM ");
            sql.addSql("where");
            sql.addSql("  CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql("and");
            sql.addSql("  CMN_BELONGM.USR_SID in ( ");
            boolean firstFlg = true;
            for (int sid : sidList) {
                if (!firstFlg) {
                    sql.addSql(", ");
                } else {
                    firstFlg = false;
                }
                sql.addSql("? ");
                sql.addIntValue(sid);
            }
            sql.addSql(") ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info("SQL ==" + sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("GRP_SID"));
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
     * <p>グループ情報Listを取得します。
     * @param sortMdl ソート情報
     * @return List
     * @throws SQLException SQL実行例外
     */
    public ArrayList<GroupModel> getGroupList(CmnCmbsortConfModel sortMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<GroupModel> ret = new ArrayList<GroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    GP.GRP_SID as GRP_SID,");
            sql.addSql("    GP.GRP_ID as GRP_ID,");
            sql.addSql("    GP.GRP_NAME as GRP_NAME,");
            sql.addSql("    max(GC1.GCL_SID1) as LEVEL1,");
            sql.addSql("    max(GC2.GCL_SID2) as LEVEL2,");
            sql.addSql("    max(GC3.GCL_SID3) as LEVEL3,");
            sql.addSql("    max(GC4.GCL_SID4) as LEVEL4,");
            sql.addSql("    max(GC5.GCL_SID5) as LEVEL5,");
            sql.addSql("    max(GC1.GCL_SID6) as LEVEL6,");
            sql.addSql("    max(GC2.GCL_SID7) as LEVEL7,");
            sql.addSql("    max(GC3.GCL_SID8) as LEVEL8,");
            sql.addSql("    max(GC4.GCL_SID9) as LEVEL9,");
            sql.addSql("    max(GC5.GCL_SID10) as LEVEL10");
            sql.addSql(" from");
            sql.addSql("    CMN_GROUPM GP");
            sql.addSql("    left join CMN_GROUP_CLASS GC1 on GP.GRP_SID = GC1.GCL_SID1");
            sql.addSql("    left join CMN_GROUP_CLASS GC2 on GP.GRP_SID = GC2.GCL_SID2");
            sql.addSql("    left join CMN_GROUP_CLASS GC3 on GP.GRP_SID = GC3.GCL_SID3");
            sql.addSql("    left join CMN_GROUP_CLASS GC4 on GP.GRP_SID = GC4.GCL_SID4");
            sql.addSql("    left join CMN_GROUP_CLASS GC5 on GP.GRP_SID = GC5.GCL_SID5");
            sql.addSql("    left join CMN_GROUP_CLASS GC6 on GP.GRP_SID = GC6.GCL_SID6");
            sql.addSql("    left join CMN_GROUP_CLASS GC7 on GP.GRP_SID = GC7.GCL_SID7");
            sql.addSql("    left join CMN_GROUP_CLASS GC8 on GP.GRP_SID = GC8.GCL_SID8");
            sql.addSql("    left join CMN_GROUP_CLASS GC9 on GP.GRP_SID = GC9.GCL_SID9");
            sql.addSql("    left join CMN_GROUP_CLASS GC10 on GP.GRP_SID = GC10.GCL_SID10");
            sql.addSql(" where");
            sql.addSql("    GP.GRP_JKBN = ?");
            sql.addSql(" group by");
            sql.addSql("    GP.GRP_SID,");
            sql.addSql("    GP.GRP_ID,");
            sql.addSql("    GP.GRP_NAME");
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            sql.addSql(" order by");

            if (sortMdl == null) {
                sql.addSql("    GRP_NAME");
            } else {

                String order1 = "asc";
                if (sortMdl.getCscGroupOrder1() == GSConst.ORDER_KEY_DESC) {
                    order1 = "desc";
                }
                switch (sortMdl.getCscGroupSkey1()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        sql.addSql("    GRP_ID " + order1);
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        sql.addSql("    GRP_NAME " + order1);
                        break;
                    default:
                        break;
                }

                String order2 = "asc";
                if (sortMdl.getCscGroupOrder2() == GSConst.ORDER_KEY_DESC) {
                    order2 = "desc";
                }
                switch (sortMdl.getCscGroupSkey2()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        sql.addSql("    ,GRP_ID " + order2);
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        sql.addSql("    ,GRP_NAME " + order2);
                        break;
                    default:
                        break;
                }

            }

            log__.info("SQL ==" + sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                GroupModel model = new GroupModel();

                //グループSIDをセット
                model.setGroupSid(rs.getInt("GRP_SID"));
                //グループIDをセット
                model.setGroupId(rs.getString("GRP_ID"));
                //グループ名
                model.setGroupName(rs.getString("GRP_NAME"));

                //グループ階層レベル
                if (rs.getString("LEVEL1") != null) {
                    model.setClassLevel(1);
                } else if (rs.getString("LEVEL2") != null) {
                    model.setClassLevel(2);
                } else if (rs.getString("LEVEL3") != null) {
                    model.setClassLevel(3);
                } else if (rs.getString("LEVEL4") != null) {
                    model.setClassLevel(4);
                } else if (rs.getString("LEVEL5") != null) {
                    model.setClassLevel(5);
                } else if (rs.getString("LEVEL6") != null) {
                    model.setClassLevel(6);
                } else if (rs.getString("LEVEL7") != null) {
                    model.setClassLevel(7);
                } else if (rs.getString("LEVEL8") != null) {
                    model.setClassLevel(8);
                } else if (rs.getString("LEVEL9") != null) {
                    model.setClassLevel(9);
                } else if (rs.getString("LEVEL10") != null) {
                    model.setClassLevel(10);
                }
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
     * <p>グループ情報CSVを出力する。
     * @param rl UsrCsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createAllGroupInfoForCsv(GrpCsvRecordListenerImpl rl)
        throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            List<CmnGroupClassModel> list = getGroupBeanList(sortMdl, false, true);

            for (CmnGroupClassModel grp: list) {
                setUsrCsvRecordFromRs(grp, rl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>GroupCsvModelを取得する。
     * @param cgcMdl GroupCsvModel
     * @param rl UsrCsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setUsrCsvRecordFromRs(CmnGroupClassModel cgcMdl, GrpCsvRecordListenerImpl rl)
        throws SQLException, CSVException {
        GrpExportModel bean = new GrpExportModel();
        bean.setGrpId(cgcMdl.getLowGrpId());
        bean.setGrpName(cgcMdl.getLowGrpName());
        bean.setGrpNameKn(cgcMdl.getLowGrpKanaName());
        bean.setParentGpId(cgcMdl.getLowParentGrpId());
        bean.setGrpComment(cgcMdl.getLowCmt());
        rl.setRecord(bean);
    }

    /**
     * <p>グループIDとグループ名で検索したListを取得します。(全て階層レベル１)
     * @param sortMdl ソート情報
     * @param grpId グループID
     * @param grpName グループ名
     * @return List
     * @throws SQLException SQL実行例外
     */
    public ArrayList<GroupModel> getPartGroupList(
            CmnCmbsortConfModel sortMdl,
            String grpId,
            String grpName
            )
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<GroupModel> ret = new ArrayList<GroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    GP.GRP_SID as GRP_SID,");
            sql.addSql("    GP.GRP_ID as GRP_ID,");
            sql.addSql("    GP.GRP_NAME as GRP_NAME,");
            sql.addSql("    max(GC1.GCL_SID1) as LEVEL1,");
            sql.addSql("    max(GC2.GCL_SID2) as LEVEL2,");
            sql.addSql("    max(GC3.GCL_SID3) as LEVEL3,");
            sql.addSql("    max(GC4.GCL_SID4) as LEVEL4,");
            sql.addSql("    max(GC5.GCL_SID5) as LEVEL5,");
            sql.addSql("    max(GC1.GCL_SID6) as LEVEL6,");
            sql.addSql("    max(GC2.GCL_SID7) as LEVEL7,");
            sql.addSql("    max(GC3.GCL_SID8) as LEVEL8,");
            sql.addSql("    max(GC4.GCL_SID9) as LEVEL9,");
            sql.addSql("    max(GC5.GCL_SID10) as LEVEL10");
            sql.addSql(" from");
            sql.addSql("    CMN_GROUPM GP");
            sql.addSql("    left join CMN_GROUP_CLASS GC1 on GP.GRP_SID = GC1.GCL_SID1");
            sql.addSql("    left join CMN_GROUP_CLASS GC2 on GP.GRP_SID = GC2.GCL_SID2");
            sql.addSql("    left join CMN_GROUP_CLASS GC3 on GP.GRP_SID = GC3.GCL_SID3");
            sql.addSql("    left join CMN_GROUP_CLASS GC4 on GP.GRP_SID = GC4.GCL_SID4");
            sql.addSql("    left join CMN_GROUP_CLASS GC5 on GP.GRP_SID = GC5.GCL_SID5");
            sql.addSql("    left join CMN_GROUP_CLASS GC6 on GP.GRP_SID = GC6.GCL_SID6");
            sql.addSql("    left join CMN_GROUP_CLASS GC7 on GP.GRP_SID = GC7.GCL_SID7");
            sql.addSql("    left join CMN_GROUP_CLASS GC8 on GP.GRP_SID = GC8.GCL_SID8");
            sql.addSql("    left join CMN_GROUP_CLASS GC9 on GP.GRP_SID = GC9.GCL_SID9");
            sql.addSql("    left join CMN_GROUP_CLASS GC10 on GP.GRP_SID = GC10.GCL_SID10");
            sql.addSql(" where");
            sql.addSql("    GP.GRP_JKBN = ?");

            if (!StringUtil.isNullZeroStringSpace(grpId)) {
                sql.addSql(" and");
                sql.addSql("       GP.GRP_ID like '%"
                        + JDBCUtil.escapeForLikeSearch(grpId)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            if (!StringUtil.isNullZeroStringSpace(grpName)) {
                sql.addSql(" and");
                sql.addSql("       GP.GRP_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(grpName)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            sql.addSql(" group by");
            sql.addSql("    GP.GRP_SID,");
            sql.addSql("    GP.GRP_ID,");
            sql.addSql("    GP.GRP_NAME");
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            sql.addSql(" order by");

            if (sortMdl == null) {
                sql.addSql("    GRP_NAME");
            } else {

                String order1 = "asc";
                if (sortMdl.getCscGroupOrder1() == GSConst.ORDER_KEY_DESC) {
                    order1 = "desc";
                }
                switch (sortMdl.getCscGroupSkey1()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        sql.addSql("    GRP_ID " + order1);
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        sql.addSql("    GRP_NAME " + order1);
                        break;
                    default:
                        break;
                }

                String order2 = "asc";
                if (sortMdl.getCscGroupOrder2() == GSConst.ORDER_KEY_DESC) {
                    order2 = "desc";
                }
                switch (sortMdl.getCscGroupSkey2()) {
                    case GSConst.GROUPCMB_SKEY_GRPID:
                        sql.addSql("    ,GRP_ID " + order2);
                        break;
                    case GSConst.GROUPCMB_SKEY_NAME:
                        sql.addSql("    ,GRP_NAME " + order2);
                        break;
                    default:
                        break;
                }

            }

            log__.info("SQL ==" + sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                GroupModel model = new GroupModel();

                //グループSIDをセット
                model.setGroupSid(rs.getInt("GRP_SID"));
                //グループIDをセット
                model.setGroupId(rs.getString("GRP_ID"));
                //グループ名
                model.setGroupName(rs.getString("GRP_NAME"));

                //グループ階層レベル
                model.setClassLevel(1);

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