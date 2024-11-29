package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>CMN_GROUPM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnGroupmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnGroupmDao.class);

    /** 正規グループ */
    public static final int GRP_JKBN_LIVING = 0;
    /** 削除グループ */
    public static final int GRP_JKBN_DELETED = 9;


    /**
     * <p>Default Constructor
     */
    public CmnGroupmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnGroupmDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_GROUPM Data Bindding JavaBean
     * @param bean CMN_GROUPM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnGroupmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_GROUPM(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getGrpId());
            sql.addStrValue(bean.getGrpName());
            sql.addStrValue(bean.getGrpNameKn());
            sql.addStrValue(bean.getGrpComment());
            sql.addIntValue(bean.getGrpAuid());
            sql.addDateValue(bean.getGrpAdate());
            sql.addIntValue(bean.getGrpEuid());
            sql.addDateValue(bean.getGrpEdate());
            sql.addIntValue(bean.getGrpSort());
            sql.addIntValue(bean.getGrpJkbn());
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
     * <p>Update CMN_GROUPM Data Bindding JavaBean
     * @param bean CMN_GROUPM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnGroupmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" set ");
            sql.addSql("   GRP_ID=?,");
            sql.addSql("   GRP_NAME=?,");
            sql.addSql("   GRP_NAME_KN=?,");
            sql.addSql("   GRP_COMMENT=?,");
            sql.addSql("   GRP_AUID=?,");
            sql.addSql("   GRP_ADATE=?,");
            sql.addSql("   GRP_EUID=?,");
            sql.addSql("   GRP_EDATE=?,");
            sql.addSql("   GRP_SORT=?,");
            sql.addSql("   GRP_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getGrpId());
            sql.addStrValue(bean.getGrpName());
            sql.addStrValue(bean.getGrpNameKn());
            sql.addStrValue(bean.getGrpComment());
            sql.addIntValue(bean.getGrpAuid());
            sql.addDateValue(bean.getGrpAdate());
            sql.addIntValue(bean.getGrpEuid());
            sql.addDateValue(bean.getGrpEdate());
            sql.addIntValue(bean.getGrpSort());
            sql.addIntValue(bean.getGrpJkbn());
            //where
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Update CMN_GROUPM Data Bindding JavaBean
     * @param bean CMN_GROUPM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnGroup(CmnGroupmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" set ");
            sql.addSql("   GRP_ID=?,");
            sql.addSql("   GRP_NAME=?,");
            sql.addSql("   GRP_NAME_KN=?,");
            sql.addSql("   GRP_COMMENT=?,");
            sql.addSql("   GRP_EUID=?,");
            sql.addSql("   GRP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getGrpId());
            sql.addStrValue(bean.getGrpName());
            sql.addStrValue(bean.getGrpNameKn());
            sql.addStrValue(bean.getGrpComment());
            sql.addIntValue(bean.getGrpEuid());
            sql.addDateValue(bean.getGrpEdate());
            //where
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Update CMN_GROUPM Data Bindding JavaBean
     * @param bean CMN_GROUPM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCmnGroupDel(CmnGroupmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" set ");
            sql.addSql("   GRP_ID=?,");
            sql.addSql("   GRP_NAME=?,");
            sql.addSql("   GRP_NAME_KN=?,");
            sql.addSql("   GRP_COMMENT=?,");
            sql.addSql("   GRP_EUID=?,");
            sql.addSql("   GRP_EDATE=?,");
            sql.addSql("   GRP_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getGrpId());
            sql.addStrValue(bean.getGrpName());
            sql.addStrValue(bean.getGrpNameKn());
            sql.addStrValue(bean.getGrpComment());
            sql.addIntValue(bean.getGrpEuid());
            sql.addDateValue(bean.getGrpEdate());
            sql.addIntValue(bean.getGrpJkbn());
            //where
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select CMN_GROUPM All Data
     * @return List in CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>グループSIDリストを取得する。
     * @return List in CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getGrpSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     * <p>Select CMN_GROUPM All Data
     * @return List in CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> selectSuvivers() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN =0");
            sql.addSql(" order by");
            sql.addSql("   GRP_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <br>[機  能] ユーザSIDからグループ名とグループIDを取得します
     * <br>[解  説] ユーザSIDを引数で与えると、グループ名とグループIDが取得できます
     * <br>[備  考]
     * @param usid ユーザSID
     * @return ret List GroupModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<CmnGroupmModel> selectBelongGroupList(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_GROUPM.GRP_SID as GRP_SID, ");
            sql.addSql("   CMN_GROUPM.GRP_ID as GRP_ID, ");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME, ");
            sql.addSql("   CMN_GROUPM.GRP_NAME_KN as GRP_NAME_KN,");
            sql.addSql("   CMN_GROUPM.GRP_COMMENT as GRP_COMMENT,");
            sql.addSql("   CMN_GROUPM.GRP_AUID as GRP_AUID,");
            sql.addSql("   CMN_GROUPM.GRP_ADATE as GRP_ADATE,");
            sql.addSql("   CMN_GROUPM.GRP_EUID as GRP_EUID,");
            sql.addSql("   CMN_GROUPM.GRP_EDATE as GRP_EDATE,");
            sql.addSql("   CMN_GROUPM.GRP_SORT as GRP_SORT,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN as BEG_GRPKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM, ");
            sql.addSql("   CMN_BELONGM ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.USR_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
            sql.addSql(" order by ");
            sql.addSql("   GRP_NAME ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>ユーザーが所属するグループでグループ管理者になっているグループモデルリストを返却します
     * @param usid ユーザーSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> selectGroupAdmin(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_GROUPM.GRP_SID,");
            sql.addSql("   CMN_GROUPM.GRP_ID,");
            sql.addSql("   CMN_GROUPM.GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_NAME_KN,");
            sql.addSql("   CMN_GROUPM.GRP_COMMENT,");
            sql.addSql("   CMN_GROUPM.GRP_AUID,");
            sql.addSql("   CMN_GROUPM.GRP_ADATE,");
            sql.addSql("   CMN_GROUPM.GRP_EUID,");
            sql.addSql("   CMN_GROUPM.GRP_EDATE,");
            sql.addSql("   CMN_GROUPM.GRP_SORT,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM,");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   CMN_GROUPM.GRP_SID=CMN_BELONGM.GRP_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = 0");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN=1");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID=?");

            sql.addIntValue(usid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <br>[機  能] ユーザSIDから所属するグループ名とグループIDを取得します(階層順)
     * <br>[解  説] ユーザSIDを引数で与えると、グループ名とグループIDが取得できます
     * <br>[備  考]
     * @param usid ユーザSID
     * @return ret List GroupModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> selectGroupAdminOrderbyClass(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<GroupModel> ret = new ArrayList<GroupModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CL.SID GROUPSID,");
            sql.addSql("   CL.GPRRNAME GROUPNAME");
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
            sql.addSql("    case when GI10.GRP_NAME is null then '' else GI10.GRP_NAME end s10,");
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
            sql.addSql("    end) GPRRNAME");
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
            sql.addSql(" or    GI6.GRP_JKBN = 0");
            sql.addSql(" or    GI7.GRP_JKBN = 0");
            sql.addSql(" or    GI8.GRP_JKBN = 0");
            sql.addSql(" or    GI9.GRP_JKBN = 0");
            sql.addSql(" or    GI10.GRP_JKBN = 0");
            sql.addSql(" ) CL,");
            sql.addSql("  CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("  CMN_BELONGM.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN=1");
            sql.addSql(" and");
            sql.addSql("  CMN_BELONGM.GRP_SID=CL.SID");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <p>グループSIDよりグループ情報を取得する。
     * @param gsid グループSID
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public CmnGroupmModel select(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_JKBN<>?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(GRP_JKBN_DELETED);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnGroupmFromRs(rs);
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
     * <p>グループSIDよりグループ情報を取得する。
     * <p>削除されたグループも対象とする
     * @param gsid グループSID
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public CmnGroupmModel selectGroup(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addIntValue(gsid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnGroupmFromRs(rs);
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
     * <p>グループ名よりグループ情報を取得する。
     * @param gname グループ名
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public CmnGroupmModel select(String gname) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_NAME=?");
            sql.addSql(" and");
            sql.addSql("   GRP_JKBN<>?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(gname);
            sql.addIntValue(GRP_JKBN_DELETED);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnGroupmFromRs(rs);
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
     * <p>グループIDよりグループ情報を取得する。
     * @param grpid グループID
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public CmnGroupmModel getGroupInf(String grpid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_ID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(grpid);
            sql.addIntValue(GRP_JKBN_LIVING);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnGroupmFromRs(rs);
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
     * <p>グループIDよりグループ情報を取得する(複数)
     * @param gids グループID
     * @param gjkbn 状態区分
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> selectGrpData(String[] gids, int gjkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN=?");
            sql.addSql(" and");

            StringBuilder buf = new StringBuilder();
            //1件目
            buf.append("'");
            buf.append(gids[0]);
            buf.append("'");
            for (int i = 1; i < gids.length; i++) {
                //2件目移行
                buf.append(",");
                buf.append("'");
                buf.append(gids[i]);
                buf.append("'");
            }

            sql.addSql("   GRP_ID in(" + buf.toString() + ")");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gjkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>グループSIDよりグループ情報を取得する(複数)
     * @param gsids グループSID
     * @param gjkbn 状態区分
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> selectFromSid(int[] gsids, int gjkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN=?");
            sql.addSql(" and");

            StringBuilder buf = new StringBuilder();
            //1件目
            buf.append(gsids[0]);
            for (int i = 1; i < gsids.length; i++) {
                //2件目移行
                buf.append(",");
                buf.append(gsids[i]);
            }

            sql.addSql("   GRP_SID in(" + buf.toString() + ")");
            sql.addSql(" order by ");
            sql.addSql("   GRP_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gjkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>グループSIDよりグループ情報を取得する(複数)
     * @param gsids グループSID
     * @return CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupmModel> selectFromSid(int[] gsids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            StringBuilder buf = new StringBuilder();
            //1件目
            buf.append(gsids[0]);
            for (int i = 1; i < gsids.length; i++) {
                //2件目移行
                buf.append(",");
                buf.append(gsids[i]);
            }

            sql.addSql("   GRP_SID in(" + buf.toString() + ")");
            sql.addSql(" order by ");
            sql.addSql("   GRP_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>状態区分を指定しグループの一覧を取得します
     * @param jtkb 状態区分
     * @return ArrayList in CMN_GROUPMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnGroupmModel> getGroupList(int jtkb) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_ID,");
            sql.addSql("   GRP_NAME,");
            sql.addSql("   GRP_NAME_KN,");
            sql.addSql("   GRP_COMMENT,");
            sql.addSql("   GRP_AUID,");
            sql.addSql("   GRP_ADATE,");
            sql.addSql("   GRP_EUID,");
            sql.addSql("   GRP_EDATE,");
            sql.addSql("   GRP_SORT,");
            sql.addSql("   GRP_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN = ?");
            sql.addSql(" order by ");
            sql.addSql("   GRP_NAME");
            sql.addIntValue(jtkb);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupmFromRs(rs));
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
     * <p>状態区分を指定しグループIDの一覧を取得します
     * @param jtkb 状態区分
     * @return ArrayList in グループID
     * @throws SQLException SQL実行例外
     */
    public List<String> getGroupIdList(int jtkb) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_ID");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN = ?");
            sql.addSql(" order by ");
            sql.addSql("   GRP_ID");
            sql.addIntValue(jtkb);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("GRP_ID"));
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
     * <br>[機  能] 管理者グループ以外のグループの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return int 管理者グループ以外のグループの件数
     * @throws SQLException SQL実行例外
     */
    public int getNotAdminGroupCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(GRP_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID != ?");
            sql.addSql(" and ");
            sql.addSql("   GRP_JKBN = ?");

            sql.addIntValue(GSConstUser.SID_ADMIN);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したグループＩＤが存在するかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param grpId グループID
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGroupid(String grpId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_ID");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_ID=?");
            sql.addSql(" and ");
            sql.addSql("   GRP_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(grpId);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
     * <p>指定したグループＩＤが存在するかチェックを行う
     * <p>引数で指定したグループSIDは除いてチェックを行う。(修正時に使用するため)
     * @param grpSid 除外するグループSID
     * @param grpId グループID
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGroupidEdit(int grpSid, String grpId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_ID");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID<>?");
            sql.addSql(" and ");
            sql.addSql("   GRP_ID=?");
            sql.addSql(" and ");
            sql.addSql("   GRP_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addStrValue(grpId);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
     * <p>指定したグループが存在するかチェックを行う
     * <p>引数で指定したグループSIDは除いてチェックを行う。(修正時に使用するため)
     * @param grpSid 除外するグループSID
     * @param grpId グループID
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGroupEdit(int grpSid, String grpId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID<>?");
            sql.addSql(" and ");
            sql.addSql("   GRP_ID=?");
            sql.addSql(" and ");
            sql.addSql("   GRP_JKBN=?");
            sql.addSql(" order by GRP_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            sql.addStrValue(grpId);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.setPagingValue(0, 1);
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
     * <p>Delete CMN_GROUPM
     * @param bean CMN_GROUPM Model
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnGroupmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_JKBN=0");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());

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
     * <br>[機  能] 削除されたグループかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @return true: 削除グループ false:通常グループ
     * @throws SQLException SQL実行例外
     */
    public boolean isDeleteGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_JKBN=?");
            sql.setPagingValue(0, 1);
            sql.addIntValue(grpSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Create CMN_GROUPM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnGroupmModel
     * @throws SQLException SQL実行例外
     */
    private CmnGroupmModel __getCmnGroupmFromRs(ResultSet rs) throws SQLException {
        CmnGroupmModel bean = new CmnGroupmModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setGrpId(rs.getString("GRP_ID"));
        bean.setGrpName(rs.getString("GRP_NAME"));
        bean.setGrpNameKn(rs.getString("GRP_NAME_KN"));
        bean.setGrpComment(rs.getString("GRP_COMMENT"));
        bean.setGrpAuid(rs.getInt("GRP_AUID"));
        bean.setGrpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GRP_ADATE")));
        bean.setGrpEuid(rs.getInt("GRP_EUID"));
        bean.setGrpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GRP_EDATE")));
        bean.setGrpSort(rs.getInt("GRP_SORT"));
        bean.setGrpJkbn(rs.getInt("GRP_JKBN"));
        return bean;
    }
    /**
     * <br>[機  能] グループ名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpId グループID
     * @return int 管理者グループ以外のグループの件数
     * @throws SQLException SQL実行例外
     */
    public String getGrpNm(String grpId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        String ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   GRP_ID = ?");
            sql.addSql(" and ");
            sql.addSql("   GRP_JKBN = ?");
            sql.addSql(" order by GRP_SID asc");
//            sql.addSql(" limit 1 offset 0");
            sql.setPagingValue(0, 1);

            sql.addStrValue(grpId);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getString("GRP_NAME");
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
