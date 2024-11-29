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
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;

/**
 * <p>RNG_KEIROSTEP_SELECT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngKeirostepSelectDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngKeirostepSelectDao.class);

    /**
     * <p>Default Constructor
     */
    public RngKeirostepSelectDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngKeirostepSelectDao(Connection con) {
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
            sql.addSql("drop table RNG_KEIROSTEP_SELECT");

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
            sql.addSql(" create table RNG_KEIROSTEP_SELECT (");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   POS_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (RKS_SID,USR_SID,GRP_SID,POS_SID)");
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
     * <p>Insert RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param bean RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngKeirostepSelectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_KEIROSTEP_SELECT(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
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
     * <p>Insert RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param selectList RNG_KEIROSTEP_SELECT List Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngKeirostepSelectModel> selectList)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_KEIROSTEP_SELECT(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngKeirostepSelectModel bean : selectList) {
                sql.addIntValue(bean.getRksSid());
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
     * <p>Update RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param bean RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngKeirostepSelectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" set ");
            sql.addSql("   RKS_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   POS_SID=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //set
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());
            //where
            sql.addIntValue(bean.getRksSid());
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
     * <p>Update RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @param updateList RNG_KEIROSTEP_SELECT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAtList(List<RngKeirostepSelectModel> updateList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" set ");
            sql.addSql("   RKS_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   POS_SID=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngKeirostepSelectModel bean : updateList) {
                //set
                sql.addIntValue(bean.getRksSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getGrpSid());
                sql.addIntValue(bean.getPosSid());
                //where
                sql.addIntValue(bean.getRksSid());
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
        return count;
    }


    /**
     * <p>Select RNG_KEIROSTEP_SELECT All Data
     * @param rngSid RNG_SID
     * @return List in RNG_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeirostepSelectModel> select(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeirostepSelectModel> ret =
                new ArrayList<RngKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("    RNG_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID in (");
            sql.addSql("     select RKS_SID");
            sql.addSql("     from RNG_KEIRO_STEP");
            sql.addSql("     where RNG_SID=?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeirostepSelectFromRs(rs));
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
     * <p>Select RNG_KEIROSTEP_SELECT All Data
     * @return List in RNG_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeirostepSelectModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeirostepSelectModel> ret =
                new ArrayList<RngKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIROSTEP_SELECT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeirostepSelectFromRs(rs));
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
     * <p>Select RNG_KEIROSTEP_SELECT by RKS_SID
     * @param rksSid 経路ステップSID
     * @return List in RNG_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeirostepSelectModel> selectByRksSid(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeirostepSelectModel> ret =
                new ArrayList<RngKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql("  where");
            sql.addSql("   RKS_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeirostepSelectFromRs(rs));
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
     * <p>Select RNG_KEIROSTEP_SELECT All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeirostepSelectModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeirostepSelectModel> ret = new ArrayList<RngKeirostepSelectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SID asc");

            sql.setPagingValue(offset, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeirostepSelectFromRs(rs));
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
     * <p>count  All Data
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
            sql.addSql("   RNG_KEIROSTEP_SELECT");

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
     * <p>Select RNG_KEIROSTEP_SELECT
     * @param rksSid RKS_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @param posSid POS_SID
     * @return RNG_KEIROSTEP_SELECTModel
     * @throws SQLException SQL実行例外
     */
    public RngKeirostepSelectModel select(int rksSid,
            int usrSid,
            int grpSid,
            int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngKeirostepSelectModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngKeirostepSelectFromRs(rs);
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
     * <p>Delete RNG_KEIROSTEP_SELECT
     * @param rksSid RKS_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @param posSid POS_SID
     * @return 更新データ数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rksSid, int usrSid, int grpSid, int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);
            sql.addIntValue(posSid);

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
     * <p>Delete RNG_KEIROSTEP_SELECT
     * @param rksList 稟議経路ステップセレクトリスト

     * @return 更新データ数
     * @throws SQLException SQL実行例外
     */
    public int deleteAtList(List<RngKeirostepSelectModel> rksList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngKeirostepSelectModel bean : rksList) {
                sql.addIntValue(bean.getRksSid());
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
        return count;
    }

    /**
     * <p>Delete RNG_KEIROSTEP_SELECT
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
            sql.addSql("   RNG_KEIROSTEP_SELECT");
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
     * <p>Create RNG_KEIROSTEP_SELECT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngKeirostepSelectModel
     * @throws SQLException SQL実行例外
     */
    private RngKeirostepSelectModel __getRngKeirostepSelectFromRs(
            ResultSet rs) throws SQLException {
        RngKeirostepSelectModel bean = new RngKeirostepSelectModel();
        bean.setRksSid(rs.getInt("RKS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setPosSid(rs.getInt("POS_SID"));
        return bean;
    }
}
