package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.model.SmlBanDestModel;

/**
 * <p>SML_BAN_DEST Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBanDestDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlBanDestDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlBanDestDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table SML_BAN_DEST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table SML_BAN_DEST (");
            sql.addSql("   SBC_SID NUMBER(10,0) not null,");
            sql.addSql("   SBD_TARGET_SID NUMBER(10,0) not null,");
            sql.addSql("   SBD_TARGET_KBN NUMBER(10,0) not null");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert SML_BAN_DEST Data Bindding JavaBean
     * @param bean SML_BAN_DEST Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlBanDestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_BAN_DEST(");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBD_TARGET_SID,");
            sql.addSql("   SBD_TARGET_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSbcSid());
            sql.addIntValue(bean.getSbdTargetSid());
            sql.addIntValue(bean.getSbdTargetKbn());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SML_BAN_DEST Data Bindding JavaBean
     * @param bean SML_BAN_DEST Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlBanDestModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_BAN_DEST");
            sql.addSql(" set ");
            sql.addSql("   SBC_SID=?,");
            sql.addSql("   SBD_TARGET_SID=?,");
            sql.addSql("   SBD_TARGET_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSbcSid());
            sql.addIntValue(bean.getSbdTargetSid());
            sql.addIntValue(bean.getSbdTargetKbn());

            log__.info(sql.toLogString());
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
     * <p>Select SML_BAN_DEST All Data
     * @return List in SML_BAN_DESTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBanDestModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBanDestModel> ret = new ArrayList<SmlBanDestModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBD_TARGET_SID,");
            sql.addSql("   SBD_TARGET_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBanDestFromRs(rs));
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
     * <p>Create SML_BAN_DEST Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlBanDestModel
     * @throws SQLException SQL実行例外
     */
    private SmlBanDestModel __getSmlBanDestFromRs(ResultSet rs) throws SQLException {
        SmlBanDestModel bean = new SmlBanDestModel();
        bean.setSbcSid(rs.getInt("SBC_SID"));
        bean.setSbdTargetSid(rs.getInt("SBD_TARGET_SID"));
        bean.setSbdTargetKbn(rs.getInt("SBD_TARGET_KBN"));
        return bean;
    }
    /**
    *
    * <br>[機  能] 送信制限管理SIDで送信制限を削除
    * <br>[解  説]
    * <br>[備  考]
    * @param sbcSid 送信制限管理SID
    * @return 削除行数
    * @throws SQLException SQL実行時例外
    */
    public int delete(int sbcSid) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST");
            sql.addSql(" where SBC_SID=?");
            sql.addIntValue(sbcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     *
     * <br>[機  能] 送信制限管理SIDから送信制限一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sbcSid 送信制限管理SID
     * @return 送信制限一覧
     * @throws SQLException SQL実行時例外
     */
    public List<SmlBanDestModel> getBanDestList(int sbcSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBanDestModel> ret = new ArrayList<SmlBanDestModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBD_TARGET_SID,");
            sql.addSql("   SBD_TARGET_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST");
            sql.addSql(" where SBC_SID=?");
            sql.addIntValue(sbcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBanDestFromRs(rs));
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
    * <br>[機  能] 送信先ユーザSID一覧から送信制限が存在するSIDを取得
    * <br>[解  説]
    * <br>[備  考]
    * @param userSids 送信先ユーザSID
    * @return SID一覧
    * @throws SQLException SQL実行時例外
    */
    public List<Integer> getBanDestUsrSidList(List<Integer> userSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        if (userSids == null || userSids.size() == 0) {
            return ret;
        }
        try {
            //SQL文
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select distinct ");
            sql.addSql(" case SML_BAN_DEST.SBD_TARGET_KBN ");
            sql.addSql("     when 0 then SML_BAN_DEST.SBD_TARGET_SID ");
            sql.addSql("     when 1 then CMN_BELONGM.USR_SID ");
            sql.addSql("     else -1 end as BAN_USR_SID ");
            sql.addSql(" from ");
            sql.addSql(" SML_BAN_DEST ");
            sql.addSql("  left join ");
            sql.addSql("    CMN_BELONGM ");
            sql.addSql("  on ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN = 1 ");
            sql.addSql("    and ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql(" where  ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN in (0, 1) ");
            sql.addSql("    and ( ");

            sql.addSql(__whereInString("SML_BAN_DEST.SBD_TARGET_SID", userSids));
            sql.addSql("       or ");
            sql.addSql(__whereInString("CMN_BELONGM.USR_SID", userSids));
            sql.addSql("        ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BAN_USR_SID"));
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
     * <br>[機  能] sidによる絞り込み条件文生成共通処理
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象フィールド名
     * @param list sid一覧
     * @return 絞り込み条件文
     */
    private String __whereInString(String target, List<Integer> list) {
        StringBuilder usrSidSql = new StringBuilder();
        if (list.size() == 1) {
            usrSidSql.append(" ");
            usrSidSql.append(target);
            usrSidSql.append(" = ");
            usrSidSql.append(String.valueOf(list.get(0)));
        } else if (list.size() > 500) {
            int st = 0;
            int max = list.size();
            int step = 500;
            int ed = step;
            while (st < max) {
                if (st == 0) {
                    usrSidSql.append(" ( ");
                } else {
                    usrSidSql.append(" or ");
                }
                if (ed > max) {
                    ed = max;
                }
                usrSidSql.append(target);
                usrSidSql.append(" in ( ");
                usrSidSql.append(String.valueOf(list.get(st)));
                for (int idx = st + 1; idx < ed; idx++) {
                    usrSidSql.append(", ");
                    usrSidSql.append(String.valueOf(list.get(idx)));
                }
                usrSidSql.append(" ) ");

                st = ed;
                ed = ed + step;
            }
            usrSidSql.append(" ) ");

        } else {
            usrSidSql.append(" ");
            usrSidSql.append(target);
            usrSidSql.append(" in ( ");
            usrSidSql.append(String.valueOf(list.get(0)));
            for (int idx = 1; idx < list.size(); idx++) {
                usrSidSql.append(", ");
                usrSidSql.append(String.valueOf(list.get(idx)));
            }
            usrSidSql.append(" ) ");
        }
        return usrSidSql.toString();
    }
    /**
    *
    * <br>[機  能] 送信先ユーザSID一覧からユーザに対して送信制限されていない一覧を取得
    * <br>[解  説]
    * <br>[備  考] 送信制限
    * @param userSids 送信先ユーザSID
    * @param sessionUsrSid ログインユーザSID
    * @return SID一覧
    * @throws SQLException SQL実行時例外
    */
    public List<Integer> getValiableDestUsrSidList(List<Integer> userSids,
            int sessionUsrSid) throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (userSids == null || userSids.size() == 0) {
            return ret;
        }
        //制限されているSID一覧
        List<Integer> banDest = getBanDestUsrSidList(userSids);
        //自分自身は無条件で送信可
        int ownIndex = banDest.indexOf(sessionUsrSid);
        if (ownIndex >= 0) {
            banDest.remove(ownIndex);
        }

        for (int sid : userSids) {
            if (sid == sessionUsrSid || !banDest.contains(sid)) {
                ret.add(sid);
            }
        }
        if (banDest.size() == 0) {
            return ret;
        }
        //制限されているSIDのうち、ログインユーザに送信が許可されているSIDを返す
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select distinct ");
            sql.addSql(" case SML_BAN_DEST.SBD_TARGET_KBN ");
            sql.addSql("     when 0 then SML_BAN_DEST.SBD_TARGET_SID ");
            sql.addSql("     when 1 then CMN_BELONGM.USR_SID ");
            sql.addSql("     else -1 end as BAN_USR_SID ");
            sql.addSql(" from ");
            sql.addSql(" SML_BAN_DEST ");
            sql.addSql("  left join ");
            sql.addSql("    CMN_BELONGM ");
            sql.addSql("  on ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN = 1 ");
            sql.addSql("    and ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql(" where  ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN in (0, 1) ");
            sql.addSql("    and ( ");
            sql.addSql(__whereInString("SML_BAN_DEST.SBD_TARGET_SID", banDest));
            sql.addSql("       or  ");
            sql.addSql(__whereInString("CMN_BELONGM.USR_SID", banDest));
            sql.addSql("        ) ");
            sql.addSql("    and SBC_SID in ( ");
            sql.addSql("      select  ");
            sql.addSql("        SBC_SID ");
            sql.addSql("      from SML_BAN_DEST_PERMIT ");
            sql.addSql("      where SBP_TARGET_KBN = 0 ");
            sql.addSql("        and SBP_TARGET_SID=? ");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("      union all ");
            sql.addSql("      select  ");
            sql.addSql("        SBC_SID ");
            sql.addSql("      from SML_BAN_DEST_PERMIT ");
            sql.addSql("      where SBP_TARGET_KBN = 1 ");
            sql.addSql("        and exists  ( ");
            sql.addSql("          select ");
            sql.addSql("            GRP_SID  ");
            sql.addSql("          from  ");
            sql.addSql("            CMN_BELONGM ");
            sql.addSql("          where CMN_BELONGM.USR_SID = ? ");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("            and CMN_BELONGM.GRP_SID = SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
            sql.addSql("        ) ");
            //セッションユーザが役職設定済みの場合
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usr = cuiDao.select(sessionUsrSid);
            if (usr.getPosSid() != 0) {
                sql.addSql("       union all ");
                sql.addSql("       select  ");
                sql.addSql("         SBC_SID ");
                sql.addSql("       from SML_BAN_DEST_PERMIT ");
                sql.addSql("       where SBP_TARGET_KBN = 2 ");
                sql.addSql("         and exists  ( ");
                sql.addSql("           select ");
                sql.addSql("             POS_SID  ");
                sql.addSql("           from  ");
                sql.addSql("             CMN_POSITION ");
                sql.addSql("           where  ");
                sql.addSql("             CMN_POSITION.POS_SID=SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
                sql.addSql("             and POS_SORT >= ( ");
                sql.addSql("               select POS_SORT as border ");
                sql.addSql("               from CMN_POSITION  ");
                sql.addSql("               where POS_SID = ( ");
                sql.addSql("                 select POS_SID  ");
                sql.addSql("                 from CMN_USRM_INF ");
                sql.addSql("                 where USR_SID=? ");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("               ) ");
                sql.addSql("             ) ");
                sql.addSql("         ) ");
            }
            sql.addSql("    ) ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BAN_USR_SID"));
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
    * <br>[機  能] ユーザが禁止されている送信先ユーザSID一覧を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid ユーザSID
    * @return ユーザが禁止されている送信先ユーザSID
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getBanDestUsrSidList(int usrSid) throws SQLException {
       return getBanDestUsrSidList(usrSid, null);
   }
    /**
     *
     * <br>[機  能] ユーザが禁止されている送信先ユーザSID一覧を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param destSids 送信先SID nullの場合は全ユーザから検索
     * @return ユーザが禁止されている送信先ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getBanDestUsrSidList(int usrSid,
            List<Integer> destSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql("  select  ");
            sql.addSql("   case B.SBD_TARGET_KBN ");
            sql.addSql("           when 0 then B.SBD_TARGET_SID ");
            sql.addSql("           when 1 then CMN_BELONGM.USR_SID ");
            sql.addSql("            end as BAN_USR_SID ");
            sql.addSql("  from  ");
            sql.addSql("  (select  ");
            sql.addSql("   SBC_SID, ");
            sql.addSql("   case when exists( ");
            sql.addSql("         select  ");
            sql.addSql("         SBC_SID ");
            sql.addSql("         from SML_BAN_DEST_PERMIT ");
            sql.addSql("         where SBP_TARGET_KBN=0 ");
            sql.addSql("           and SML_BAN_DEST_PERMIT.SBC_SID=SML_BAN_DEST.SBC_SID ");
            sql.addSql("           and SBP_TARGET_SID=? ");
            sql.addIntValue(usrSid);
            sql.addSql("         union all ");
            sql.addSql("         select  ");
            sql.addSql("         SBC_SID ");
            sql.addSql("         from SML_BAN_DEST_PERMIT ");
            sql.addSql("         where SBP_TARGET_KBN=1 ");
            sql.addSql("           and SML_BAN_DEST_PERMIT.SBC_SID=SML_BAN_DEST.SBC_SID ");
            sql.addSql("           and exists  ( ");
            sql.addSql("             select ");
            sql.addSql("               GRP_SID  ");
            sql.addSql("             from  ");
            sql.addSql("               CMN_BELONGM ");
            sql.addSql("             where  ");
            sql.addSql("                CMN_BELONGM.USR_SID = ? ");
            sql.addIntValue(usrSid);
            sql.addSql("                and CMN_BELONGM.GRP_SID ="
                +   " SML_BAN_DEST_PERMIT.SBP_TARGET_SID) ");
            //セッションユーザが役職設定済みの場合
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usr = cuiDao.select(usrSid);
            if (usr.getPosSid() != 0) {
                sql.addSql("       union all ");
                sql.addSql("       select  ");
                sql.addSql("         SBC_SID ");
                sql.addSql("       from SML_BAN_DEST_PERMIT ");
                sql.addSql("       where SBP_TARGET_KBN = 2 ");
                sql.addSql("         and exists  ( ");
                sql.addSql("           select ");
                sql.addSql("             POS_SID  ");
                sql.addSql("           from  ");
                sql.addSql("             CMN_POSITION ");
                sql.addSql("           where  ");
                sql.addSql("             CMN_POSITION.POS_SID=SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
                sql.addSql("             and POS_SORT >= ( ");
                sql.addSql("               select POS_SORT as border ");
                sql.addSql("               from CMN_POSITION  ");
                sql.addSql("               where POS_SID = ( ");
                sql.addSql("                 select POS_SID  ");
                sql.addSql("                 from CMN_USRM_INF ");
                sql.addSql("                 where USR_SID=? ");
                sql.addIntValue(usrSid);
                sql.addSql("               ) ");
                sql.addSql("             ) ");
                sql.addSql("         ) ");
            }
            sql.addSql("        ) ");
            sql.addSql("    ");
            sql.addSql("   then 1 ");
            sql.addSql("   else 0 end as VAR, ");
            sql.addSql("   SBD_TARGET_KBN, ");
            sql.addSql("   SBD_TARGET_SID ");
            sql.addSql("   from SML_BAN_DEST ");
            sql.addSql("  ) as B ");
            sql.addSql("  left join ");
            sql.addSql("       CMN_BELONGM ");
            sql.addSql("  on ");
            sql.addSql("       B.SBD_TARGET_KBN = 1 ");
            sql.addSql("   and ");
            sql.addSql("       B.SBD_TARGET_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql("  where  ");
            if (destSids != null && destSids.size() > 0) {
                sql.addSql(" (B.SBD_TARGET_KBN = 0 ");
                sql.addSql(" and ");
                sql.addSql(__whereInString("B.SBD_TARGET_SID", destSids));
                sql.addSql(" ) or");
                sql.addSql(" (B.SBD_TARGET_KBN = 1 ");
                sql.addSql(" and ");
                sql.addSql(__whereInString("CMN_BELONGM.USR_SID", destSids));
                sql.addSql(" )");

            } else {
                sql.addSql(" B.SBD_TARGET_KBN in (0, 1) ");
            }
            sql.addSql("  group by BAN_USR_SID ");
            sql.addSql("  having sum(VAR) = 0 ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BAN_USR_SID"));
            }
            //自分自身は無条件で送信可
            int ownIndex = ret.indexOf(usrSid);
            if (ownIndex >= 0) {
                ret.remove(ownIndex);
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
    * <br>[機  能] ユーザが禁止されている代表アカウントSID一覧を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid ユーザSID
    * @return ユーザが禁止されている送信先代表アカウントSID
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getBanDestAccSidList(int usrSid) throws SQLException {
       return getBanDestAccSidList(usrSid, null);
   }
    /**
    *
    * <br>[機  能] ユーザが禁止されている代表アカウントSID一覧を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid ユーザSID
    * @param destSids 送信先SID nullの場合は全アカウントから検索
    * @return ユーザが禁止されている送信先代表アカウントSID
    * @throws SQLException SQL実行時例外
    */
   public List<Integer> getBanDestAccSidList(int usrSid,
           List<Integer> destSids) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       ArrayList<Integer> ret = new ArrayList<Integer>();
       con = getCon();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();

           sql.addSql("  select  ");
           sql.addSql("   B.SBD_TARGET_SID ");
           sql.addSql("  from  ");
           sql.addSql("  (select  ");
           sql.addSql("   SBC_SID, ");
           sql.addSql("   case when exists( ");
           sql.addSql("         select  ");
           sql.addSql("         SBC_SID ");
           sql.addSql("         from SML_BAN_DEST_PERMIT ");
           sql.addSql("         where SBP_TARGET_KBN=0 ");
           sql.addSql("           and SML_BAN_DEST_PERMIT.SBC_SID=SML_BAN_DEST.SBC_SID ");
           sql.addSql("           and SBP_TARGET_SID=? ");
           sql.addIntValue(usrSid);
           sql.addSql("         union all ");
           sql.addSql("         select  ");
           sql.addSql("         SBC_SID ");
           sql.addSql("         from SML_BAN_DEST_PERMIT ");
           sql.addSql("         where SBP_TARGET_KBN=1 ");
           sql.addSql("           and SML_BAN_DEST_PERMIT.SBC_SID=SML_BAN_DEST.SBC_SID ");
           sql.addSql("           and exists  ( ");
           sql.addSql("             select ");
           sql.addSql("               GRP_SID  ");
           sql.addSql("             from  ");
           sql.addSql("               CMN_BELONGM ");
           sql.addSql("             where  ");
           sql.addSql("                CMN_BELONGM.USR_SID = ? ");
           sql.addIntValue(usrSid);
           sql.addSql("                and CMN_BELONGM.GRP_SID = "
                   + "SML_BAN_DEST_PERMIT.SBP_TARGET_SID) ");
           //セッションユーザが役職設定済みの場合
           CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
           CmnUsrmInfModel usr = cuiDao.select(usrSid);
           if (usr.getPosSid() != 0) {
               sql.addSql("       union all ");
               sql.addSql("       select  ");
               sql.addSql("         SBC_SID ");
               sql.addSql("       from SML_BAN_DEST_PERMIT ");
               sql.addSql("       where SBP_TARGET_KBN = 2 ");
               sql.addSql("         and exists  ( ");
               sql.addSql("           select ");
               sql.addSql("             POS_SID  ");
               sql.addSql("           from  ");
               sql.addSql("             CMN_POSITION ");
               sql.addSql("           where  ");
               sql.addSql("             CMN_POSITION.POS_SID=SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
               sql.addSql("             and POS_SORT >= ( ");
               sql.addSql("               select POS_SORT as border ");
               sql.addSql("               from CMN_POSITION  ");
               sql.addSql("               where POS_SID = ( ");
               sql.addSql("                 select POS_SID  ");
               sql.addSql("                 from CMN_USRM_INF ");
               sql.addSql("                 where USR_SID=? ");
               sql.addIntValue(usrSid);
               sql.addSql("               ) ");
               sql.addSql("             ) ");
               sql.addSql("         ) ");
           }
           sql.addSql("        ) ");
           sql.addSql("    ");
           sql.addSql("   then 1 ");
           sql.addSql("   else 0 end as VAR, ");
           sql.addSql("   SBD_TARGET_KBN, ");
           sql.addSql("   SBD_TARGET_SID ");
           sql.addSql("   from SML_BAN_DEST ");
           sql.addSql("  ) as B ");
           sql.addSql("  where  ");
           sql.addSql("       B.SBD_TARGET_KBN = 2 ");
           if (destSids != null && destSids.size() > 0) {
               sql.addSql("   and ");
               sql.addSql(__whereInString("B.SBD_TARGET_SID", destSids));
           }
           sql.addSql("  group by SBD_TARGET_SID ");
           sql.addSql("  having sum(VAR) = 0 ");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.add(rs.getInt("SBD_TARGET_SID"));
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
    * <br>[機  能] 送信先アカウントSID一覧から送信制限が存在するSIDを取得
    * <br>[解  説]
    * <br>[備  考]
    * @param accSids 送信先ユーザSID
    * @return SID一覧
    * @throws SQLException SQL実行時例外
    */
    public List<Integer> getBanDestAccSidList(List<Integer> accSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        if (accSids == null || accSids.size() == 0) {
            return ret;
        }
        try {
            //SQL文
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select distinct ");
            sql.addSql("   SBD_TARGET_SID ");
            sql.addSql(" from ");
            sql.addSql(" SML_BAN_DEST ");
            sql.addSql(" where  ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN = 2 ");
            sql.addSql("    and ");
            sql.addSql(__whereInString("SML_BAN_DEST.SBD_TARGET_SID ", accSids));

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SBD_TARGET_SID"));
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
    * <br>[機  能] 送信先アカウントSID一覧からユーザに対して送信制限されていない一覧を取得
    * <br>[解  説]
    * <br>[備  考] 送信制限
    * @param accSids 送信先ユーザSID
    * @param sessionUsrSid ログインユーザSID
    * @return SID一覧
    * @throws SQLException SQL実行時例外
    */
    public List<Integer> getValiableDestAccSidList(List<Integer> accSids,
            int sessionUsrSid) throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (accSids == null || accSids.size() == 0) {
            return ret;
        }
        //制限されているSID一覧
        List<Integer> banDest = getBanDestAccSidList(accSids);
        for (int sid : accSids) {
            if (!banDest.contains(sid)) {
                ret.add(sid);
            }
        }
        if (banDest.size() == 0) {
            return ret;
        }
        //制限されているSIDのうち、ログインユーザに送信が許可されているSIDを返す
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql(" select distinct ");
            sql.addSql("   SBD_TARGET_SID ");
            sql.addSql(" from ");
            sql.addSql(" SML_BAN_DEST ");
            sql.addSql(" where  ");
            sql.addSql("    SML_BAN_DEST.SBD_TARGET_KBN = 2 ");
            sql.addSql("    and ");
            sql.addSql(__whereInString("SML_BAN_DEST.SBD_TARGET_SID", banDest));
            sql.addSql("    and SBC_SID in ( ");
            sql.addSql("      select  ");
            sql.addSql("        SBC_SID ");
            sql.addSql("      from SML_BAN_DEST_PERMIT ");
            sql.addSql("      where SBP_TARGET_KBN = 0 ");
            sql.addSql("        and SBP_TARGET_SID=? ");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("      union all ");
            sql.addSql("      select  ");
            sql.addSql("        SBC_SID ");
            sql.addSql("      from SML_BAN_DEST_PERMIT ");
            sql.addSql("      where SBP_TARGET_KBN = 1 ");
            sql.addSql("        and exists  ( ");
            sql.addSql("          select ");
            sql.addSql("            GRP_SID  ");
            sql.addSql("          from  ");
            sql.addSql("            CMN_BELONGM ");
            sql.addSql("          where CMN_BELONGM.USR_SID = ? ");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("            and CMN_BELONGM.GRP_SID = SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
            sql.addSql("        ) ");

            //セッションユーザが役職設定済みの場合
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usr = cuiDao.select(sessionUsrSid);
            if (usr.getPosSid() != 0) {
                sql.addSql("       union all ");
                sql.addSql("       select  ");
                sql.addSql("         SBC_SID ");
                sql.addSql("       from SML_BAN_DEST_PERMIT ");
                sql.addSql("       where SBP_TARGET_KBN = 2 ");
                sql.addSql("         and exists  ( ");
                sql.addSql("           select ");
                sql.addSql("             POS_SID  ");
                sql.addSql("           from  ");
                sql.addSql("             CMN_POSITION ");
                sql.addSql("           where  ");
                sql.addSql("             CMN_POSITION.POS_SID=SML_BAN_DEST_PERMIT.SBP_TARGET_SID ");
                sql.addSql("             and POS_SORT >= ( ");
                sql.addSql("               select POS_SORT as border ");
                sql.addSql("               from CMN_POSITION  ");
                sql.addSql("               where POS_SID = ( ");
                sql.addSql("                 select POS_SID  ");
                sql.addSql("                 from CMN_USRM_INF ");
                sql.addSql("                 where USR_SID=? ");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("               ) ");
                sql.addSql("             ) ");
                sql.addSql("         ) ");
            }
            sql.addSql("    ) ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SBD_TARGET_SID"));
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
