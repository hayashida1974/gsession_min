package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupShareDao;
import jp.groupsession.v2.cmn.model.MyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupShareModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] マイグループ関係の処理を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MyGroupDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMyGroupDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public MyGroupDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public MyGroupDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] マイグループSID(複数)を指定してマイグループを削除する
     * <br>[解  説] マイグループ情報(CMN_MY_GROUP)、マイグループ情報明細(CMN_MY_GROUP_MS)
     *              の該当レコードを削除する
     * <br>[備  考]
     * @param groupSid マイグループSID
     * @throws SQLException SQL実行例外
     */
    public void deleteMyGroup(String[] groupSid) throws SQLException {

        Connection con = getCon();

        //マイグループ情報(CMN_MY_GROUP)を削除
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        cmgDao.deleteGroup(groupSid);

        //マイグループ情報明細(CMN_MY_GROUP_MS)を削除
        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        cmgmDao.deleteGroupMs(groupSid);

        //マイグループ共有設定(CMN_MY_GROUP_SHARE)を削除
        CmnMyGroupShareDao cmgsDao = new CmnMyGroupShareDao(con);
        cmgsDao.deleteGroupShare(groupSid);
    }

    /**
     * <br>[機  能] マイグループ情報を登録する
     * <br>[解  説] マイグループ情報(CMN_MY_GROUP)、マイグループ情報明細(CMN_MY_GROUP_MS)
     *              を登録する
     * <br>[備  考]
     * @param cmgMdl CmnMyGroupModel
     * @param userSid ユーザSID(マイグループ情報明細登録用)
     * @param shareUsrSid 共有ユーザSID
     * @param shareGrpSid 共有グループSID
     * @throws SQLException SQL実行例外
     */
    public void insertMyGroup(CmnMyGroupModel cmgMdl,
            String[] userSid,
            String[] shareUsrSid,
            String[] shareGrpSid) throws SQLException {

        Connection con = getCon();

        //マイグループ情報(CMN_MY_GROUP)を登録
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        cmgDao.insertMyGroup(cmgMdl);

        //マイグループ情報明細(CMN_MY_GROUP_MS)を登録
        insertMyGroupMs(cmgMdl, userSid);

        insertMyGroupShare(cmgMdl, shareUsrSid, shareGrpSid);

    }
    /**
     *
     * <br>[機  能] マイグループ共有設定追加
     * <br>[解  説]
     * <br>[備  考]
     * @param cmgMdl マイグループモデル
     * @param shareUsrSid 共有ユーザSID
     * @param shareGrpSid 共有グループSID
     * @throws SQLException SQL実行例外
     */
    public void insertMyGroupShare(CmnMyGroupModel cmgMdl, String[] shareUsrSid,
            String[] shareGrpSid) throws SQLException {
        Connection con = getCon();
        CmnMyGroupShareDao mgsDao = new CmnMyGroupShareDao(con);
        if (shareGrpSid != null && shareGrpSid.length > 0) {
            for (String sid : shareGrpSid) {
                int grpSid = NullDefault.getInt(sid, 0);
                if (grpSid >= 0) {
                    CmnMyGroupShareModel mgsMdl = new CmnMyGroupShareModel();
                    mgsMdl.setMgpSid(cmgMdl.getMgpSid());
                    mgsMdl.setUsrSid(cmgMdl.getUsrSid());
                    mgsMdl.setMgsGrpSid(grpSid);
                    mgsMdl.setMgsUsrSid(-1);
                    mgsDao.insert(mgsMdl);
                }
            }
        }
        if (shareUsrSid != null && shareUsrSid.length > 0) {
            for (String sid : shareUsrSid) {
                int usrSid = NullDefault.getInt(sid, -1);
                if (usrSid > GSConstUser.USER_RESERV_SID) {
                    CmnMyGroupShareModel mgsMdl = new CmnMyGroupShareModel();
                    mgsMdl.setMgpSid(cmgMdl.getMgpSid());
                    mgsMdl.setUsrSid(cmgMdl.getUsrSid());
                    mgsMdl.setMgsGrpSid(-1);
                    mgsMdl.setMgsUsrSid(usrSid);
                    mgsDao.insert(mgsMdl);
                }
            }
        }
    }
    /**
     * <br>[機  能] マイグループ情報を更新する
     * <br>[解  説] マイグループ情報(CMN_MY_GROUP)を更新し、
     *              マイグループ情報明細(CMN_MY_GROUP_MS)をdelete insertする
     * <br>[備  考]
     * @param cmgMdl CmnMyGroupModel
     * @param userSid ユーザSID(マイグループ情報明細登録用)
     * @param shareUsrSid 共有ユーザSID
     * @param shareGrpSid 共有グループSID
     * @throws SQLException SQL実行例外
     */
    public void updateMyGroup(CmnMyGroupModel cmgMdl, String[] userSid, String[] shareUsrSid,
            String[] shareGrpSid) throws SQLException {

        Connection con = getCon();

        //マイグループ情報(CMN_MY_GROUP)を更新
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        cmgDao.updateMyGroup(cmgMdl);

        //マイグループ情報明細(CMN_MY_GROUP_MS)を削除
        CmnMyGroupMsDao cmgmDao = new CmnMyGroupMsDao(con);
        cmgmDao.deleteGroupMs(cmgMdl.getMgpSid());

        //マイグループ情報明細(CMN_MY_GROUP_MS)を登録
        insertMyGroupMs(cmgMdl, userSid);

        CmnMyGroupShareDao cmgsDao = new CmnMyGroupShareDao(con);
        cmgsDao.deleteGroupShare(cmgMdl.getUsrSid(), cmgMdl.getMgpSid());
        insertMyGroupShare(cmgMdl, shareUsrSid, shareGrpSid);
    }

    /**
     * <br>[機  能] マイグループ情報明細を登録する
     * <br>[解  説] 配列を渡し、複数登録する
     * <br>[備  考]
     * @param bean CmnMyGroupModel
     * @param userSid ユーザSID(マイグループ情報明細登録用)
     * @throws SQLException SQL実行例外
     */
    public void insertMyGroupMs(CmnMyGroupModel bean, String[] userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (userSid == null) {
            return;
        }
        if (userSid.length < 1) {
            return;
        }

        try {

            for (int i = 0; i < userSid.length; i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CMN_MY_GROUP_MS(");
                sql.addSql("   USR_SID,");
                sql.addSql("   MGP_SID,");
                sql.addSql("   MGM_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getMgpSid());
                sql.addIntValue(NullDefault.getInt(userSid[i], 0));

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
     *
     * <br>[機  能] ユーザSIDからマイグループ一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sortMdl ソート条件
     * @param own 自分がオーナーのマイグループを含める
     * @param kyouyu 共有されているマイグループを含める
     * @return マイグループ一覧
     * @throws SQLException SQL実行例外
     */
    public List<MyGroupModel> getMyGroupList(int userSid,
            CmnCmbsortConfModel sortMdl,
            boolean own,
            boolean kyouyu) throws SQLException {
        List<MyGroupModel> ret = new ArrayList<MyGroupModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        if (!own && !kyouyu) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_MY_GROUP.USR_SID as USR_SID,");
            sql.addSql("    CMN_MY_GROUP.MGP_SID as MGP_SID,");
            sql.addSql("    CMN_MY_GROUP.MGP_NAME as MGP_NAME,");
            sql.addSql("    CMN_MY_GROUP.MGP_MEMO as MGP_MEMO,");
            sql.addSql("    CMN_MY_GROUP.MGP_AUID as MGP_AUID,");
            sql.addSql("    CMN_MY_GROUP.MGP_ADATE as MGP_ADATE,");
            sql.addSql("    CMN_MY_GROUP.MGP_EUID as MGP_EUID,");
            sql.addSql("    CMN_MY_GROUP.MGP_EDATE as MGP_EDATE,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   (case      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_POSITION.POS_SID = CMN_USRM_INF.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("   (        select");
            sql.addSql("              count(CMN_MY_GROUP_MS.MGM_SID)");
            sql.addSql("            from");
            sql.addSql("              CMN_MY_GROUP_MS,");
            sql.addSql("              CMN_USRM              ");
            sql.addSql("            where");
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID = CMN_MY_GROUP.MGP_SID");
            sql.addSql("              and CMN_MY_GROUP_MS.USR_SID = CMN_MY_GROUP.USR_SID");
            sql.addSql("              and CMN_MY_GROUP_MS.MGM_SID=CMN_USRM.USR_SID");
            sql.addSql("              and CMN_USRM.USR_JKBN=0");
            sql.addSql("    ) as mem_cnt");
            sql.addSql(" from");
            sql.addSql("     CMN_MY_GROUP,");
            sql.addSql("     CMN_USRM,              ");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql(" where  ");
            sql.addSql(" (  ");
            if (own) {
                sql.addSql("   CMN_MY_GROUP.USR_SID=? ");
                sql.addIntValue(userSid);
            }
            if (own && kyouyu) {
                sql.addSql("   or ");
            }
            if (kyouyu) {
                sql.addSql("   CMN_MY_GROUP.MGP_SID in (");
                sql.addSql("     select MGP_SID");
                sql.addSql("     from CMN_MY_GROUP_SHARE");
                sql.addSql("     where ");
                sql.addSql("       MGS_USR_SID = ?");
                sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
                sql.addSql("         select GRP_SID from CMN_BELONGM");
                sql.addSql("         where USR_SID=?");
                sql.addSql("         )");
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addIntValue(userSid);
                sql.addIntValue(userSid);
            }
            sql.addSql(" )  ");
            if (!own && kyouyu) {
                sql.addSql("  and CMN_MY_GROUP.USR_SID <> ? ");
                sql.addIntValue(userSid);
            }
            sql.addSql("   and CMN_MY_GROUP.USR_SID=CMN_USRM.USR_SID");
            sql.addSql("   and CMN_USRM.USR_JKBN=0");
            sql.addSql("   and CMN_MY_GROUP.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql("   ");
            sql.addSql(" order by ");
            sql.addSql("   MGP_NAME,");
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
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            MyGroupModel mgMdl = null;
            while (rs.next()) {
                mgMdl = __getCmnMyGroupFromRs(rs);
                mgMdl.setMemberCnt(rs.getInt("mem_cnt"));
                CmnUsrmInfModel usrMdl = new CmnUsrmInfModel();
                usrMdl.setUsrSid(rs.getInt("USR_SID"));
                usrMdl.setUsiSei(rs.getString("USI_SEI"));
                usrMdl.setUsiMei(rs.getString("USI_MEI"));
                usrMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                mgMdl.setOwner(usrMdl);
                ret.add(mgMdl);
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
     * <p>Create CMN_MY_GROUP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMyGroupModel
     * @throws SQLException SQL実行例外
     */
    private MyGroupModel __getCmnMyGroupFromRs(ResultSet rs) throws SQLException {
        MyGroupModel bean = new MyGroupModel();

        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMgpSid(rs.getInt("MGP_SID"));
        bean.setMgpName(rs.getString("MGP_NAME"));
        bean.setMgpMemo(rs.getString("MGP_MEMO"));
        bean.setMgpAuid(rs.getInt("MGP_AUID"));
        bean.setMgpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MGP_ADATE")));
        bean.setMgpEuid(rs.getInt("MGP_EUID"));
        bean.setMgpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MGP_EDATE")));
        return bean;
    }

    /**
     *
     * <br>[機  能] マイグループの参照可不可を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param mgpSid マイグループSID
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     * @return true:マイグループの参照可
     */
    public boolean isAbleViewMyGroup(int mgpSid, int sessionUsrSid) throws SQLException {
        boolean ret = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("     select USR_SID as SID");
            sql.addSql("     from CMN_MY_GROUP");
            sql.addSql("     where USR_SID = ? and MGP_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addIntValue(mgpSid);

            sql.addSql("     union all ");
            sql.addSql("     select USR_SID as SID ");
            sql.addSql("     from CMN_MY_GROUP_SHARE");
            sql.addSql("     where MGP_SID = ? ");
            sql.addIntValue(mgpSid);
            sql.addSql("     and (MGS_USR_SID=? or");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("           MGS_GRP_SID in (");
            sql.addSql("             select GRP_SID");
            sql.addSql("             from CMN_BELONGM");
            sql.addSql("             where USR_SID=?)");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
}
