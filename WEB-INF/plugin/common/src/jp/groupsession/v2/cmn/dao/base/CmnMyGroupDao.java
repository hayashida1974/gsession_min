package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <p>CMN_MY_GROUP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMyGroupDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMyGroupDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMyGroupDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMyGroupDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] マイグループ情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_MY_GROUP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertMyGroup(CmnMyGroupModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MY_GROUP(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMgpSid());
            sql.addStrValue(bean.getMgpName());
            sql.addStrValue(bean.getMgpMemo());
            sql.addIntValue(bean.getMgpAuid());
            sql.addDateValue(bean.getMgpAdate());
            sql.addIntValue(bean.getMgpEuid());
            sql.addDateValue(bean.getMgpEdate());

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

    /**
     * <br>[機  能] マイグループ情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_MY_GROUP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int updateMyGroup(CmnMyGroupModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" set ");
            sql.addSql("   MGP_NAME = ?,");
            sql.addSql("   MGP_MEMO = ?,");
            sql.addSql("   MGP_EUID = ?,");
            sql.addSql("   MGP_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID = ?");

            sql.addStrValue(bean.getMgpName());
            sql.addStrValue(bean.getMgpMemo());
            sql.addIntValue(bean.getMgpEuid());
            sql.addDateValue(bean.getMgpEdate());
            //where
            sql.addIntValue(bean.getMgpSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ユーザSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMyGroupModel> getMyGroupList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_MY_GROUP.USR_SID as USR_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_SID as MGP_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME as MGP_NAME,");
            sql.addSql("   CMN_MY_GROUP.MGP_MEMO as MGP_MEMO,");
            sql.addSql("   CMN_MY_GROUP.MGP_AUID as MGP_AUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_ADATE as MGP_ADATE,");
            sql.addSql("   CMN_MY_GROUP.MGP_EUID as MGP_EUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_EDATE as MGP_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("   CMN_MY_GROUP.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("   or");
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
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_MY_GROUP.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addSql(" order by ");
            sql.addSql("   MGP_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnMyGroupModel mdl = __getCmnMyGroupFromRs(rs);
                if (mdl.getUsrSid() != userSid) {
                    StringBuilder sb = new StringBuilder(mdl.getMgpName());
                    sb.append("<");
                    sb.append(rs.getString("USI_SEI"));
                    sb.append(" ");
                    sb.append(rs.getString("USI_MEI"));
                    sb.append(">");
                    mdl.setMgpName(sb.toString());
                }
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
     * <br>[機  能] ユーザSID、マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupSid マイグループSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnMyGroupModel> getMyGroupList(int userSid,
                                 String groupSid) throws SQLException {
        List<String> gsidList = new ArrayList<String>();
        gsidList.add(groupSid);
        return getMyGroupList(userSid, gsidList);
    }
    /**
     * <br>[機  能] ユーザSID、マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupSid マイグループSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnMyGroupModel> getMyGroupList(int userSid,
                                 List<String> groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_MY_GROUP.USR_SID as USR_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_SID as MGP_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME as MGP_NAME,");
            sql.addSql("   CMN_MY_GROUP.MGP_MEMO as MGP_MEMO,");
            sql.addSql("   CMN_MY_GROUP.MGP_AUID as MGP_AUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_ADATE as MGP_ADATE,");
            sql.addSql("   CMN_MY_GROUP.MGP_EUID as MGP_EUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_EDATE as MGP_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("   CMN_MY_GROUP.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("   or");
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
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   MGP_SID in (");


            if (!groupSid.isEmpty()) {
                for (int i = 0; i < groupSid.size(); i++) {
                    sql.addSql("     ? ");
                    sql.addIntValue(NullDefault.getInt(groupSid.get(i), 0));

                    if (i < groupSid.size() - 1) {
                        sql.addSql("     , ");
                    }
                }
            } else {
                sql.addSql("-1");
            }
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_MY_GROUP.USR_SID = CMN_USRM_INF.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnMyGroupModel mdl = __getCmnMyGroupFromRs(rs);
                if (mdl.getUsrSid() != userSid) {
                    StringBuilder sb = new StringBuilder(mdl.getMgpName());
                    sb.append("<");
                    sb.append(rs.getString("USI_SEI"));
                    sb.append(" ");
                    sb.append(rs.getString("USI_MEI"));
                    sb.append(">");
                    mdl.setMgpName(sb.toString());
                }
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
     * <br>[機  能] ユーザSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMyGroupModel> getMyGroupExistMemberList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_MY_GROUP.USR_SID as USR_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_SID as MGP_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME as MGP_NAME,");
            sql.addSql("   CMN_MY_GROUP.MGP_MEMO as MGP_MEMO,");
            sql.addSql("   CMN_MY_GROUP.MGP_AUID as MGP_AUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_ADATE as MGP_ADATE,");
            sql.addSql("   CMN_MY_GROUP.MGP_EUID as MGP_EUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_EDATE as MGP_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP,");
            sql.addSql("   CMN_MY_GROUP_MS,");
            sql.addSql("   CMN_USRM_INF");

            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("   CMN_MY_GROUP.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("   or");
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
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_MY_GROUP.MGP_SID = CMN_MY_GROUP_MS.MGP_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_MY_GROUP.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" group by ");
            sql.addSql("   CMN_MY_GROUP.USR_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME,");
            sql.addSql("   CMN_MY_GROUP.MGP_MEMO,");
            sql.addSql("   CMN_MY_GROUP.MGP_AUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_ADATE,");
            sql.addSql("   CMN_MY_GROUP.MGP_EUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI");
            sql.addSql(" order by ");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnMyGroupModel mdl = __getCmnMyGroupFromRs(rs);
                if (mdl.getUsrSid() != userSid) {
                    StringBuilder sb = new StringBuilder(mdl.getMgpName());
                    sb.append("<");
                    sb.append(rs.getString("USI_SEI"));
                    sb.append(" ");
                    sb.append(rs.getString("USI_MEI"));
                    sb.append(">");
                    mdl.setMgpName(sb.toString());
                }
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
     * <br>[機  能] ユーザSIDからマイグループSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getMyGroupSidExistMemberList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_MY_GROUP.MGP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP,");
            sql.addSql("   CMN_MY_GROUP_MS");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("   CMN_MY_GROUP.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("   or");
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
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_MY_GROUP.MGP_SID = CMN_MY_GROUP_MS.MGP_SID");
            sql.addSql(" group by ");
            sql.addSql("   CMN_MY_GROUP.MGP_SID");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("MGP_SID"));
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
     * <br>[機  能] マイグループSID(複数)からマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid マイグループSID
     * @return List in CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMyGroupModel> getMyGroupListFromGroupSid(String[] groupSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        if (groupSid == null) {
            return ret;
        }
        if (groupSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID in (");

            for (int i = 0; i < groupSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(groupSid[i], 0));

                if (i < groupSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   MGP_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnMyGroupFromRs(rs));
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
     * <br>[機  能] マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid マイグループSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public CmnMyGroupModel getMyGroupInfo(int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMyGroupModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID = ?");

            sql.addIntValue(groupSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnMyGroupFromRs(rs);
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
     * <br>[機  能] マイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMyGroupModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMyGroupFromRs(rs));
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
     * <br>[機  能] ユーザSID、マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupSid マイグループSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public CmnMyGroupModel select(int userSid, int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMyGroupModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   MGP_SID = ?");

            sql.addIntValue(userSid);
            sql.addIntValue(groupSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnMyGroupFromRs(rs);
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
     * <br>[機  能] ユーザSID、マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupSid マイグループSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnMyGroupModel> select(int userSid,
                                 ArrayList<String> groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupModel> ret = new ArrayList<CmnMyGroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGP_NAME,");
            sql.addSql("   MGP_MEMO,");
            sql.addSql("   MGP_AUID,");
            sql.addSql("   MGP_ADATE,");
            sql.addSql("   MGP_EUID,");
            sql.addSql("   MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   MGP_SID in (");

            sql.addIntValue(userSid);

            if (!groupSid.isEmpty()) {
                for (int i = 0; i < groupSid.size(); i++) {
                    sql.addSql("     ? ");
                    sql.addIntValue(NullDefault.getInt(groupSid.get(i), 0));

                    if (i < groupSid.size() - 1) {
                        sql.addSql("     , ");
                    }
                }
            } else {
                sql.addSql("-1");
            }


            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnMyGroupFromRs(rs));
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
     * <br>[機  能] マイグループSID(複数)を指定してマイグループ情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid マイグループSID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteGroup(String[] groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (groupSid == null) {
            return count;
        }
        if (groupSid.length < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID in (");

            for (int i = 0; i < groupSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(groupSid[i], 0));

                if (i < groupSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ユーザSIDを指定してマイグループ情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteGroup(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ユーザSID、マイグループSIDからマイグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param myGrpSid マイグループSID
     * @param usrSid ユーザSID
     * @return CMN_MY_GROUPModel
     * @throws SQLException SQL実行例外
     */
    public CmnMyGroupModel selectShareMyGroup(int myGrpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMyGroupModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_MY_GROUP.USR_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_SID,");
            sql.addSql("   CMN_MY_GROUP.MGP_NAME,");
            sql.addSql("   CMN_MY_GROUP.MGP_MEMO,");
            sql.addSql("   CMN_MY_GROUP.MGP_AUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_ADATE,");
            sql.addSql("   CMN_MY_GROUP.MGP_EUID,");
            sql.addSql("   CMN_MY_GROUP.MGP_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP");
            sql.addSql(" left join");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" on");
            sql.addSql("   CMN_MY_GROUP.MGP_SID = CMN_MY_GROUP_SHARE.MGP_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_MY_GROUP.MGP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     CMN_MY_GROUP_SHARE.USR_SID = ?");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       CMN_MY_GROUP_SHARE.MGS_USR_SID = ?");
            sql.addSql("     and");
            sql.addSql("       CMN_MY_GROUP_SHARE.MGS_GRP_SID = ?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       CMN_MY_GROUP_SHARE.MGS_USR_SID = ?");
            sql.addSql("     and");
            sql.addSql("       CMN_MY_GROUP_SHARE.MGS_GRP_SID in (");
            sql.addSql("         select");
            sql.addSql("           GRP_SID");
            sql.addSql("         from");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where");
            sql.addSql("           CMN_BELONGM.USR_SID = ?");
            sql.addSql("         )");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(myGrpSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstMain.PCM_MEMSID_NOSET);
            sql.addIntValue(GSConstMain.PCM_MEMSID_NOSET);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnMyGroupFromRs(rs);
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
    private CmnMyGroupModel __getCmnMyGroupFromRs(ResultSet rs) throws SQLException {
        CmnMyGroupModel bean = new CmnMyGroupModel();
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
}
