package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.model.RngCopyKeirostepSelectModel;

/**
 * <p>RNG_COPY_KEIROSTEP_SELECT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCopyKeirostepSelectDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngCopyKeirostepSelectDao.class);

    /**
     * <p>Default Constructor
     */
    public RngCopyKeirostepSelectDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngCopyKeirostepSelectDao(Connection con) {
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
            sql.addSql("drop table RNG_COPY_KEIROSTEP_SELECT");

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
            sql.addSql(" create table RNG_COPY_KEIROSTEP_SELECT (");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   RCK_SORT NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   POS_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (RKS_SID,RCK_SORT,USR_SID,GRP_SID,POS_SID)");
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
     * <p>Insert RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param bean RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngCopyKeirostepSelectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_COPY_KEIROSTEP_SELECT(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRckSort());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());
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
     * <p>Insert RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param selectList RNG_COPY_KEIROSTEP_SELECT List Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngCopyKeirostepSelectModel> selectList)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_COPY_KEIROSTEP_SELECT(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngCopyKeirostepSelectModel bean : selectList) {
                sql.addIntValue(bean.getRksSid());
                sql.addIntValue(bean.getRckSort());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getGrpSid());
                sql.addIntValue(bean.getPosSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <p>Update RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param bean RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngCopyKeirostepSelectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql(" set ");
            sql.addSql("   RKS_SID=?,");
            sql.addSql("   RCK_SORT=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   POS_SID=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   RCK_SORT=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //set
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRckSort());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());
            //where
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRckSort());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());

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
     * <p>Select RNG_COPY_KEIROSTEP_SELECT All Data
     * @return List in RNG_COPY_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public List<RngCopyKeirostepSelectModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeirostepSelectModel> ret =
                new ArrayList<RngCopyKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeirostepSelectFromRs(rs));
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
     * <p>Select RNG_COPY_KEIROSTEP_SELECT by RKS_SID
     * @param rksSidList 経路ステップSID一覧
     * @return List in RNG_COPY_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RngCopyKeirostepSelectModel> selectByRksSid(List<Integer> rksSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeirostepSelectModel> ret =
                new ArrayList<RngCopyKeirostepSelectModel>();
        con = getCon();

        if (rksSidList == null || rksSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql(" where");
            sql.addSql("   RKS_SID in (");

            for (int i = 0; i < rksSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addIntValue(rksSidList.get(i).intValue());
            }
            sql.addSql("   )");

            sql.addSql(" order by RCK_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeirostepSelectFromRs(rs));
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
     * <p>Delete RNG_COPY_KEIROSTEP_SELECT
     * @param rngSid 稟議SID
     * @return 更新データ数
     * @throws SQLException SQL実行例外
     */
    public int deleteRng(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN");
            sql.addSql("     (");
            sql.addSql("      select");
            sql.addSql("        RKS_SID");
            sql.addSql("      from");
            sql.addSql("        RNG_KEIRO_STEP");
            sql.addSql("      where");
            sql.addSql("        RNG_SID = ?");
            sql.addSql("     )");

            sql.addIntValue(rngSid);
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
     * <p>Select RNG_COPY_KEIROSTEP_SELECT All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngCopyKeirostepSelectModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeirostepSelectModel> ret = new ArrayList<RngCopyKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SID asc,");
            sql.addSql("   RCK_SORT asc");

            sql.setPagingValue(offset, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeirostepSelectFromRs(rs));
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
     * <p>count RNG_COPY_KEIROSTEP_SELECT All Data
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     * <p>Create RNG_COPY_KEIROSTEP_SELECT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngCopyKeirostepSelectModel
     * @throws SQLException SQL実行例外
     */
    private RngCopyKeirostepSelectModel __getRngCopyKeirostepSelectFromRs(
            ResultSet rs) throws SQLException {
        RngCopyKeirostepSelectModel bean = new RngCopyKeirostepSelectModel();
        bean.setRksSid(rs.getInt("RKS_SID"));
        bean.setRckSort(rs.getInt("RCK_SORT"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setPosSid(rs.getInt("POS_SID"));
        return bean;
    }
}
