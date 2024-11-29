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
import jp.groupsession.v2.rng.model.RngCopyKeiroStepModel;

/**
 * <p>RNG_COPY_KEIRO_STEP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCopyKeiroStepDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngCopyKeiroStepDao.class);

    /**
     * <p>Default Constructor
     */
    public RngCopyKeiroStepDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngCopyKeiroStepDao(Connection con) {
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
            sql.addSql("drop table RNG_COPY_KEIRO_STEP");

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
            sql.addSql(" create table RNG_COPY_KEIRO_STEP (");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   RCK_SORT NUMBER(10,0) not null,");
            sql.addSql("   RTK_SID NUMBER(10,0) not null,");
            sql.addSql("   RKS_BELONG_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (RKS_SID, RCK_SORT)");
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
     * <p>Insert RNG_COPY_KEIRO_STEP Data Bindding JavaBean
     * @param bean RNG_COPY_KEIRO_STEP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngCopyKeiroStepModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_COPY_KEIRO_STEP(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_BELONG_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRckSort());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRksBelongSid());
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
     * <p>Insert RNG_COPY_KEIRO_STEP Data Bindding JavaBean
     * @param rckList 稟議経路ステップモデル
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngCopyKeiroStepModel> rckList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_COPY_KEIRO_STEP(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_BELONG_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngCopyKeiroStepModel bean : rckList) {
                sql.addIntValue(bean.getRksSid());
                sql.addIntValue(bean.getRckSort());
                sql.addIntValue(bean.getRtkSid());
                sql.addIntValue(bean.getRksBelongSid());
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
     * <p>Update RNG_COPY_KEIRO_STEP Data Bindding JavaBean
     * @param bean RNG_COPY_KEIRO_STEP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngCopyKeiroStepModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_COPY_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RNG_SID=?,");
            sql.addSql("   RCK_SORT=?,");
            sql.addSql("   RTK_SID=?,");
            sql.addSql("   RKS_BELONG_SID=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRckSort());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRksBelongSid());

            //where
            sql.addIntValue(bean.getRksSid());

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
     * <p>Select RNG_COPY_KEIRO_STEP All Data
     * @return List in RNG_COPY_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public List<RngCopyKeiroStepModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeiroStepModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_BELONG_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_COPY_KEIRO_STEP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeiroStepFromRs(rs));
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
     * <p>Select RNG_COPY_KEIRO_STEP
     * @param rksSidList RKS_SID
     * @return RNG_COPY_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RngCopyKeiroStepModel> select(List<Integer> rksSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeiroStepModel> ret = new ArrayList<RngCopyKeiroStepModel>();
        con = getCon();

        if (rksSidList == null || rksSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_BELONG_SID");

            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIRO_STEP");
            sql.addSql(" where ");
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
            sql.addSql(" order by ");
            sql.addSql("   RCK_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeiroStepFromRs(rs));
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
     * <p>Delete RNG_COPY_KEIRO_STEP
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
            sql.addSql("   RNG_COPY_KEIRO_STEP");
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
     * <p>Select RNG_COPY_KEIRO_STEP All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngCopyKeiroStepModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngCopyKeiroStepModel> ret = new ArrayList<RngCopyKeiroStepModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RCK_SORT,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_BELONG_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIRO_STEP");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SID asc,");
            sql.addSql("   RCK_SORT asc");

            sql.setPagingValue(offset, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngCopyKeiroStepFromRs(rs));
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
     * <p>count RNG_COPY_KEIRO_STEP All Data
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
            sql.addSql("   RNG_COPY_KEIRO_STEP");

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
     * <p>Create RNG_COPY_KEIRO_STEP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngCopyKeiroStepModel
     * @throws SQLException SQL実行例外
     */
    private RngCopyKeiroStepModel __getRngCopyKeiroStepFromRs(ResultSet rs) throws SQLException {
        RngCopyKeiroStepModel bean = new RngCopyKeiroStepModel();
        bean.setRksSid(rs.getInt("RKS_SID"));
        bean.setRckSort(rs.getInt("RCK_SORT"));
        bean.setRtkSid(rs.getInt("RTK_SID"));
        bean.setRksBelongSid(rs.getInt("RKS_BELONG_SID"));
        return bean;
    }
}
