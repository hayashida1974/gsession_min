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
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;

/**
 * <p>CMN_GROUP_CLASS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnGroupClassDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnGroupClassDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnGroupClassDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnGroupClassDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_GROUP_CLASS Data Bindding JavaBean
     * @param bean CMN_GROUP_CLASS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnGroupClassModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_GROUP_CLASS(");
            sql.addSql("   GCL_SID1,");
            sql.addSql("   GCL_SID2,");
            sql.addSql("   GCL_SID3,");
            sql.addSql("   GCL_SID4,");
            sql.addSql("   GCL_SID5,");
            sql.addSql("   GCL_SID6,");
            sql.addSql("   GCL_SID7,");
            sql.addSql("   GCL_SID8,");
            sql.addSql("   GCL_SID9,");
            sql.addSql("   GCL_SID10,");
            sql.addSql("   GCL_AUID,");
            sql.addSql("   GCL_ADATE,");
            sql.addSql("   GCL_EUID,");
            sql.addSql("   GCL_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGclSid1());
            sql.addIntValue(bean.getGclSid2());
            sql.addIntValue(bean.getGclSid3());
            sql.addIntValue(bean.getGclSid4());
            sql.addIntValue(bean.getGclSid5());
            sql.addIntValue(bean.getGclSid6());
            sql.addIntValue(bean.getGclSid7());
            sql.addIntValue(bean.getGclSid8());
            sql.addIntValue(bean.getGclSid9());
            sql.addIntValue(bean.getGclSid10());
            sql.addIntValue(bean.getGclAuid());
            sql.addDateValue(bean.getGclAdate());
            sql.addIntValue(bean.getGclEuid());
            sql.addDateValue(bean.getGclEdate());
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
     * <p>Update CMN_GROUP_CLASS Data Bindding JavaBean
     * @param bean CMN_GROUP_CLASS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnGroupClassModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" set ");
            sql.addSql("   GCL_AUID=?,");
            sql.addSql("   GCL_ADATE=?,");
            sql.addSql("   GCL_EUID=?,");
            sql.addSql("   GCL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   GCL_SID1=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID2=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID3=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID4=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID5=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID6=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID7=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID8=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID9=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID10=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGclAuid());
            sql.addDateValue(bean.getGclAdate());
            sql.addIntValue(bean.getGclEuid());
            sql.addDateValue(bean.getGclEdate());
            //where
            sql.addIntValue(bean.getGclSid1());
            sql.addIntValue(bean.getGclSid2());
            sql.addIntValue(bean.getGclSid3());
            sql.addIntValue(bean.getGclSid4());
            sql.addIntValue(bean.getGclSid5());
            sql.addIntValue(bean.getGclSid6());
            sql.addIntValue(bean.getGclSid7());
            sql.addIntValue(bean.getGclSid8());
            sql.addIntValue(bean.getGclSid9());
            sql.addIntValue(bean.getGclSid10());

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
     * <p>Select CMN_GROUP_CLASS All Data
     * @return List in CMN_GROUP_CLASSModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnGroupClassModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupClassModel> ret = new ArrayList<CmnGroupClassModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GCL_SID1,");
            sql.addSql("   GCL_SID2,");
            sql.addSql("   GCL_SID3,");
            sql.addSql("   GCL_SID4,");
            sql.addSql("   GCL_SID5,");
            sql.addSql("   GCL_SID6,");
            sql.addSql("   GCL_SID7,");
            sql.addSql("   GCL_SID8,");
            sql.addSql("   GCL_SID9,");
            sql.addSql("   GCL_SID10,");
            sql.addSql("   GCL_AUID,");
            sql.addSql("   GCL_ADATE,");
            sql.addSql("   GCL_EUID,");
            sql.addSql("   GCL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUP_CLASS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnGroupClassFromRs(rs));
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
     * <p>Select CMN_GROUP_CLASS
     * @param bean CMN_GROUP_CLASS Model
     * @return CMN_GROUP_CLASSModel
     * @throws SQLException SQL実行例外
     */
    public CmnGroupClassModel select(CmnGroupClassModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnGroupClassModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GCL_SID1,");
            sql.addSql("   GCL_SID2,");
            sql.addSql("   GCL_SID3,");
            sql.addSql("   GCL_SID4,");
            sql.addSql("   GCL_SID5,");
            sql.addSql("   GCL_SID6,");
            sql.addSql("   GCL_SID7,");
            sql.addSql("   GCL_SID8,");
            sql.addSql("   GCL_SID9,");
            sql.addSql("   GCL_SID10,");
            sql.addSql("   GCL_AUID,");
            sql.addSql("   GCL_ADATE,");
            sql.addSql("   GCL_EUID,");
            sql.addSql("   GCL_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" where ");
            sql.addSql("   GCL_SID1=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID2=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID3=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID4=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID5=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID6=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID7=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID8=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID9=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID10=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGclSid1());
            sql.addIntValue(bean.getGclSid2());
            sql.addIntValue(bean.getGclSid3());
            sql.addIntValue(bean.getGclSid4());
            sql.addIntValue(bean.getGclSid5());
            sql.addIntValue(bean.getGclSid6());
            sql.addIntValue(bean.getGclSid7());
            sql.addIntValue(bean.getGclSid8());
            sql.addIntValue(bean.getGclSid9());
            sql.addIntValue(bean.getGclSid10());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnGroupClassFromRs(rs);
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
     * <p>指定されたグループが階層の末端かチェックする
     * @param gsid グループSID
     * @return ret true:末端である false:末端ではない
     * @throws SQLException SQL実行例外
     */
    public boolean isEndGroup(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" where ");
            sql.addSql("   GCL_SID1=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID2=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID3=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID4=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID5=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID6=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID7=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID8=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID9=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID10=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 1) {
                    ret = false;
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
     * <p>指定されたグループが何階層目かを取得する
     * @param gsid グループSID
     * @return ret 階層数
     * @throws SQLException SQL実行例外
     */
    public int isHierarchy(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    case");
            sql.addSql("      when GCL_SID1 = ? then 1");
            sql.addSql("      when GCL_SID2 = ? then 2");
            sql.addSql("      when GCL_SID3 = ? then 3");
            sql.addSql("      when GCL_SID4 = ? then 4");
            sql.addSql("      when GCL_SID5 = ? then 5");
            sql.addSql("      when GCL_SID6 = ? then 6");
            sql.addSql("      when GCL_SID7 = ? then 7");
            sql.addSql("      when GCL_SID8 = ? then 8");
            sql.addSql("      when GCL_SID9 = ? then 9");
            sql.addSql("      when GCL_SID10 = ? then 10");
            sql.addSql("      else 0");
            sql.addSql("    end KAISO");
            sql.addSql("  from");
            sql.addSql("    CMN_GROUP_CLASS");
            sql.addSql("  where");
            sql.addSql("    GCL_SID1 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID2 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID3 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID4 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID5 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID6 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID7 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID8 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID9 = ?");
            sql.addSql("  or");
            sql.addSql("    GCL_SID10 = ?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("KAISO");
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
     * <p>指定されたグループが階層の末端かチェックする
     * @param gsid グループSID
     * @param gpSidList 対象外グループSID
     * @return ret true:末端である false:末端ではない
     * @throws SQLException SQL実行例外
     */
    public boolean isEndGroup(int gsid, List<Integer> gpSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" where");
            sql.addSql("  ( ");
            sql.addSql("   GCL_SID1=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID2=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID3=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID4=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID5=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID6=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID7=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID8=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID9=?");
            sql.addSql(" or");
            sql.addSql("   GCL_SID10=?");
            sql.addSql("  ) ");

            if (gpSidList != null && !gpSidList.isEmpty()) {
                sql.addSql(" and");
                sql.addSql("   GCL_SID1 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID2 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID3 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID4 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID5 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID6 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID7 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID8 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID9 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   GCL_SID10 not in (");
                for (int i = 0; i < gpSidList.size(); i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(String.valueOf(gpSidList.get(i)));
                }
                sql.addSql("   )");
            }




            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 1) {
                    ret = false;
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
     * <p>Delete CMN_GROUP_CLASS
     * @param bean CMN_GROUP_CLASS Model
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnGroupClassModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" where ");
            sql.addSql("   GCL_SID1=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID2=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID3=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID4=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID5=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID6=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID7=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID8=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID9=?");
            sql.addSql(" and");
            sql.addSql("   GCL_SID10=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGclSid1());
            sql.addIntValue(bean.getGclSid2());
            sql.addIntValue(bean.getGclSid3());
            sql.addIntValue(bean.getGclSid4());
            sql.addIntValue(bean.getGclSid5());
            sql.addIntValue(bean.getGclSid6());
            sql.addIntValue(bean.getGclSid7());
            sql.addIntValue(bean.getGclSid8());
            sql.addIntValue(bean.getGclSid9());
            sql.addIntValue(bean.getGclSid10());

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
     * <p>削除されたグループの階層データを削除する
     * @param gsid グループSID
     * @throws SQLException SQL実行例外
     */
    public void deleteDelGroup(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUP_CLASS");
            sql.addSql(" where");
            sql.addSql(" (");
            sql.addSql("    GCL_SID1 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID2 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID3 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID2 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID3 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID5= -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID3 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID4 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID4 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID5 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID5 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID6 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID6 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID7 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID7 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID8 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID8 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID9 = -1");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID9 = ?");
            sql.addSql(" and");
            sql.addSql("    GCL_SID10 = -1");
            sql.addSql(" )");
            sql.addSql(" or");
            sql.addSql(" (");
            sql.addSql("    GCL_SID10 = ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);
            sql.addIntValue(gsid);

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
     * <p>Create CMN_GROUP_CLASS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnGroupClassModel
     * @throws SQLException SQL実行例外
     */
    private CmnGroupClassModel __getCmnGroupClassFromRs(ResultSet rs) throws SQLException {
        CmnGroupClassModel bean = new CmnGroupClassModel();
        bean.setGclSid1(rs.getInt("GCL_SID1"));
        bean.setGclSid2(rs.getInt("GCL_SID2"));
        bean.setGclSid3(rs.getInt("GCL_SID3"));
        bean.setGclSid4(rs.getInt("GCL_SID4"));
        bean.setGclSid5(rs.getInt("GCL_SID5"));
        bean.setGclSid6(rs.getInt("GCL_SID6"));
        bean.setGclSid7(rs.getInt("GCL_SID7"));
        bean.setGclSid8(rs.getInt("GCL_SID8"));
        bean.setGclSid9(rs.getInt("GCL_SID9"));
        bean.setGclSid10(rs.getInt("GCL_SID10"));
        bean.setGclAuid(rs.getInt("GCL_AUID"));
        bean.setGclAdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_ADATE")));
        bean.setGclEuid(rs.getInt("GCL_EUID"));
        bean.setGclEdate(UDate.getInstanceTimestamp(rs.getTimestamp("GCL_EDATE")));
        return bean;
    }
}
