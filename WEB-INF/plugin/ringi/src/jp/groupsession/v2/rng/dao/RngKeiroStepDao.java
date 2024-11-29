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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.rng030.Rng030KeiroParam;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>RNG_KEIRO_STEP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngKeiroStepDao extends AbstractDao {
    /**
     *
     * <br>[機  能] 詳細検索用オブジェクト
     * <br>[解  説] 内部にRngKeiroStepModelを持ち委譲メソッドで実装
     * <br>[備  考] 絞り込み条件が必要になった場合にメンテナンスする
     *
     * @author JTS
     */
    public static class SearchModel  {
        /** 実体オブジェクト */
        private RngKeiroStepModel model__ = new RngKeiroStepModel();
        /**
         * @return 稟議SID
         * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#getRngSid()
         */
        public int getRngSid() {
            return model__.getRngSid();
        }

        /**
         * @param rngSid 稟議SID
         * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#setRngSid(int)
         */
        public void setRngSid(int rngSid) {
            model__.setRngSid(rngSid);
        }

        /**
         * @return 経路ステップSID
         * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#getRksSid()
         */
        public int getRksSid() {
            return model__.getRksSid();
        }

        /**
         * @param rksSid 経路ステップSID
         * @see jp.groupsession.v2.rng.model.RngKeiroStepModel#setRksSid(int)
         */
        public void setRksSid(int rksSid) {
            model__.setRksSid(rksSid);
        }

    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngKeiroStepDao.class);

    /**
     * <p>Default Constructor
     */
    public RngKeiroStepDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngKeiroStepDao(Connection con) {
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
            sql.addSql("drop table RNG_KEIRO_STEP");

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
            sql.addSql(" create table RNG_KEIRO_STEP (");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   RNG_SID NUMBER(10,0) not null,");
            sql.addSql("   RKS_SORT NUMBER(10,0) not null,");
            sql.addSql("   RKS_STATUS NUMBER(10,0) not null,");
            sql.addSql("   RTK_SID NUMBER(10,0) not null,");
            sql.addSql("   RKS_ROLL_TYPE NUMBER(10,0) not null,");
            sql.addSql("   RKS_RCVDATE varchar(23),");
            sql.addSql("   RKS_CHKDATE varchar(23),");
            sql.addSql("   RKS_AUID NUMBER(10,0) not null,");
            sql.addSql("   RKS_ADATE varchar(23) not null,");
            sql.addSql("   RKS_EUID NUMBER(10,0) not null,");
            sql.addSql("   RKS_EDATE varchar(23) not null,");
            sql.addSql("   RKS_BELONG_SID integer not null,");
            sql.addSql("   RKS_KEIRO_KOETU integer not null,");
            sql.addSql("   RKS_KOETU_SIZI integer not null,");
            sql.addSql("   primary key (RKS_SID)");
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
     * <p>Insert RNG_KEIRO_STEP Data Bindding JavaBean
     * @param bean RNG_KEIRO_STEP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngKeiroStepModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_KEIRO_STEP(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID, ");
            sql.addSql("   RKS_KEIRO_KOETU, ");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getRksSort());
            sql.addIntValue(bean.getRksStatus());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRksRollType());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksAuid());
            sql.addDateValue(bean.getRksAdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            sql.addIntValue(bean.getRksBelongSid());
            sql.addIntValue(bean.getRksKeiroKoetu());
            sql.addIntValue(bean.getRksKoetuSizi());
            sql.addIntValue(bean.getRksAddstep());
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
     * <p>Insert RNG_KEIRO_STEP Data Bindding JavaBean
     * @param rksList 稟議経路ステップモデル
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngKeiroStepModel> rksList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_KEIRO_STEP(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID, ");
            sql.addSql("   RKS_KEIRO_KOETU, ");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngKeiroStepModel bean : rksList) {
                sql.addIntValue(bean.getRksSid());
                sql.addIntValue(bean.getRngSid());
                sql.addIntValue(bean.getRksSort());
                sql.addIntValue(bean.getRksStatus());
                sql.addIntValue(bean.getRtkSid());
                sql.addIntValue(bean.getRksRollType());
                sql.addDateValue(bean.getRksRcvdate());
                sql.addDateValue(bean.getRksChkdate());
                sql.addIntValue(bean.getRksAuid());
                sql.addDateValue(bean.getRksAdate());
                sql.addIntValue(bean.getRksEuid());
                sql.addDateValue(bean.getRksEdate());
                sql.addIntValue(bean.getRksBelongSid());
                sql.addIntValue(bean.getRksKeiroKoetu());
                sql.addIntValue(bean.getRksKoetuSizi());
                sql.addIntValue(bean.getRksAddstep());
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
     * <p>Update RNG_KEIRO_STEP Data Bindding JavaBean
     * @param bean RNG_KEIRO_STEP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngKeiroStepModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RNG_SID=?,");
            sql.addSql("   RKS_SORT=?,");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RTK_SID=?,");
            sql.addSql("   RKS_ROLL_TYPE=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_AUID=?,");
            sql.addSql("   RKS_ADATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?,");
            sql.addSql("   RKS_BELONG_SID=?,");
            sql.addSql("   RKS_KEIRO_KOETU=?,");
            sql.addSql("   RKS_KOETU_SIZI=?,");
            sql.addSql("   RKS_ADDSTEP=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getRksSort());
            sql.addIntValue(bean.getRksStatus());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRksRollType());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksAuid());
            sql.addDateValue(bean.getRksAdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            sql.addIntValue(bean.getRksBelongSid());
            sql.addIntValue(bean.getRksKeiroKoetu());
            sql.addIntValue(bean.getRksKoetuSizi());
            sql.addIntValue(bean.getRksAddstep());

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
     * <p>Select RNG_KEIRO_STEP All Data
     * @return List in RNG_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeiroStepModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeiroStepModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID,");
            sql.addSql("   RKS_KEIRO_KOETU,");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeiroStepFromRs(rs));
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
     * <p>Select RNG_KEIRO_STEP All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeiroStepModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeiroStepModel> ret = new ArrayList<RngKeiroStepModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID,");
            sql.addSql("   RKS_KEIRO_KOETU,");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SID asc");

            sql.setPagingValue(offset, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeiroStepFromRs(rs));
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
     * <p>count RNG_CHANNEL All Data
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
            sql.addSql("   RNG_KEIRO_STEP");

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
     * <p>Select RNG_KEIRO_STEP
     * @param rksSid RKS_SID
     * @return RNG_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public RngKeiroStepModel select(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngKeiroStepModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID,");
            sql.addSql("   RKS_KEIRO_KOETU,");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");

            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngKeiroStepFromRs(rs);
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
     * <p>Select RNG_KEIRO_STEP
     * @param searchMdl SearchMdl
     * @return RNG_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeiroStepModel> select(SearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeiroStepModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID,");
            sql.addSql("   RKS_KEIRO_KOETU,");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            //--------------where-------------------------------------
            sql.addSql(" where ");
            if (searchMdl.getRksSid() > 0) {
                sql.addSql("   RKS_SID = ?");
                sql.addIntValue(searchMdl.getRksSid());
            } else {
                sql.addSql("   RKS_SID <> 0");

            }
            if (searchMdl.getRngSid() > 0) {
                sql.addSql("and RNG_SID = ?");
                sql.addIntValue(searchMdl.getRngSid());
            }
            //--------------order by-------------------------------------
            sql.addSql(" order by ");
            sql.addSql("   RKS_ROLL_TYPE, RKS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeiroStepFromRs(rs));
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
     * <p>Select RNG_KEIRO_STEP
     * @param rngSid 稟議SID
     * @return RNG_KEIRO_STEPModel
     * @throws SQLException SQL実行例外
     */
    public List<RngKeiroStepModel> selectFromRngSid(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngKeiroStepModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_SORT,");
            sql.addSql("   RKS_STATUS,");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_RCVDATE,");
            sql.addSql("   RKS_CHKDATE,");
            sql.addSql("   RKS_AUID,");
            sql.addSql("   RKS_ADATE,");
            sql.addSql("   RKS_EUID,");
            sql.addSql("   RKS_EDATE,");
            sql.addSql("   RKS_BELONG_SID,");
            sql.addSql("   RKS_KEIRO_KOETU,");
            sql.addSql("   RKS_KOETU_SIZI,");
            sql.addSql("   RKS_ADDSTEP");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngKeiroStepFromRs(rs));
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
     * 稟議受信日時を取得します
     * @param rngSid 稟議SID
     * @return 受信日時
     * @throws SQLException SQL実行例外
     * */
    public UDate selectRsvDate(int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        UDate ret = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   RKS_RCVDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_STATUS = ?");

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = UDate.getInstanceTimestamp(rs.getTimestamp("RKS_RCVDATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete RNG_KEIRO_STEP
     * @param rksSid RKS_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);

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
     * <p>Delete RNG_KEIRO_STEP
     * @param rngSid RNG_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteRngSid(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

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
     * <br>[機  能] 承認条件数・却下条件数を達成しているか
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @param nStatus ステータス情報
     * @throws SQLException SQL実行例外
     * @return 0:未達成 1:承認達成 2:却下達成
     */
    public int checkApproval(RngKeiroStepModel bean, int nStatus)
    throws SQLException {

        PreparedStatement pstmt = null;
        int nRtn = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   case  when RNG_TEMPLATE_KEIRO.RTK_OUTCONDITION IS NULL then -1 ");
            sql.addSql("         else RNG_TEMPLATE_KEIRO.RTK_OUTCONDITION ");
            sql.addSql("   end RTK_OUTCONDITION , ");
            sql.addSql("   case  when RNG_TEMPLATE_KEIRO.RTK_OUTCOND_BORDER IS NULL then -1 ");
            sql.addSql("         else RNG_TEMPLATE_KEIRO.RTK_OUTCOND_BORDER ");
            sql.addSql("   end RTK_OUTCOND_BORDER,");
            sql.addSql("   SETTLEMENT_COUNT.SETTLEMENT_USER,");
            sql.addSql("   ALL_COUNT.ALL_USER,");
            sql.addSql("   NOSET_COUNT.NOSET_USER");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" left join");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" on");
            sql.addSql("   RNG_KEIRO_STEP.RTK_SID = RNG_TEMPLATE_KEIRO.RTK_SID");
            sql.addSql(" left join");
            sql.addSql("   ( ");
            sql.addSql("    select");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("      COUNT(RNG_SINGI.RSS_STATUS) as SETTLEMENT_USER ");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    left join");
            sql.addSql("      RNG_SINGI");
            sql.addSql("    on ");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql("    where");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = ?");
            sql.addSql("    and");
            sql.addSql("      RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("    group by");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("   ) SETTLEMENT_COUNT");
            sql.addSql(" on ");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = SETTLEMENT_COUNT.RKS_SID");
            sql.addSql(" left join");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("      COUNT(RNG_SINGI.RKS_SID) as NOSET_USER ");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    left join");
            sql.addSql("      RNG_SINGI");
            sql.addSql("    on ");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql("    where");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = ?");
            sql.addSql("    and");
            sql.addSql("      (");
            sql.addSql("         RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("       or");
            sql.addSql("         RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("      )");
            sql.addSql("    group by");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("   )NOSET_COUNT");
            sql.addSql(" on");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = NOSET_COUNT.RKS_SID");
            sql.addSql(" left join");
            sql.addSql("   ( ");
            sql.addSql("    select");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("      COUNT(RNG_SINGI.RKS_SID) as ALL_USER ");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    left join");
            sql.addSql("      RNG_SINGI");
            sql.addSql("    on ");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql("    where");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID = ?");
            sql.addSql("    group by");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("   ) ALL_COUNT");
            sql.addSql(" on ");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = ALL_COUNT.RKS_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = ?");


            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(nStatus);
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRksSid());
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int nCondition = 0;
                //経路進行条件又は経路進行条件閾値が設定されていない場合、{全員の承認}として考える
                if (rs.getInt("RTK_OUTCONDITION") == -1 || rs.getInt("RTK_OUTCOND_BORDER") == -1) {
                    nCondition = RngConst.RNG_OUT_CONDITION_APPROVAL;
                } else {
                    nCondition = rs.getInt("RTK_OUTCONDITION");
                }
                int nBorder = rs.getInt("RTK_OUTCOND_BORDER");
                int nSettlementUser = rs.getInt("SETTLEMENT_USER");
                int nAllUser = rs.getInt("ALL_USER");
                int nNosetUser = rs.getInt("NOSET_USER");

                //承認時の処理
                if (nStatus == RngConst.RNG_RNCSTATUS_APPR) {
                    if (nCondition == RngConst.RNG_OUT_CONDITION_DELIBERATION) {
                        //全員の審議
                        if (nNosetUser == 0) {
                            nRtn = 1;
                        }
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_APPROVAL) {
                        //全員の承認
                        if (nSettlementUser == nAllUser) {
                            //承認者数と経路上の全ユーザが同じであればtrue
                            nRtn = 1;
                        }
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_NUMBER) {
                        //承認数
                        if (nSettlementUser >= nBorder) {
                            nRtn = 1;
                        }
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_RATE) {
                        //承認割合
                        float fBorder = (float) nBorder;
                        fBorder = nAllUser * (fBorder / 100);
                        if (nSettlementUser >= fBorder) {
                            nRtn = 1;
                        }
                    }
                }

                //却下時の処理
                if (nStatus == RngConst.RNG_RNCSTATUS_DENIAL) {
                    if (nCondition == RngConst.RNG_OUT_CONDITION_DELIBERATION) {
                        //全員の審議
                        if (nNosetUser == 0) {
                            nRtn = 1;
                        }
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_APPROVAL) {
                        //全員の承認
                        nRtn = 2;
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_NUMBER) {
                        //承認数
                        if (nAllUser - nBorder < nSettlementUser) {
                            nRtn = 2;
                        }
                    } else if (nCondition == RngConst.RNG_OUT_CONDITION_RATE) {
                        //承認割合
                        float fBorder = Math.abs(((float) nBorder) - 100);
                        fBorder = nAllUser * (fBorder / 100);
                        if (nSettlementUser >= fBorder) {
                            nRtn = 2;
                        }
                    }
                }
                //確認時の処理
                if (nStatus == RngConst.RNG_RNCSTATUS_CONFIRMATION) {
                    if (nNosetUser == 0) {
                        nRtn = 1;
                    }
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return nRtn;
    }

    /**
     * <br>[機  能] 指定したユーザが却下した承認者の前の承認者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @return 判定結果 true:前の承認者 false:前の承認者ではない
     * @throws SQLException SQL実行例外
     */
    public boolean isBeforeApproval(int rngSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   count(USR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP,");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql(" and");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT < (");
            sql.addSql("     select max(RKS_SORT) from RNG_KEIRO_STEP");
            sql.addSql("     where RNG_SID = ?");
            sql.addSql("     and RKS_ROLL_TYPE = ?");
            sql.addSql("     and RKS_STATUS = ?");
            sql.addSql("     group by RNG_SID, RKS_ROLL_TYPE");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(userSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_DENIAL);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] 次又は前の承認経路のSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ksMdl RKS_SID取得用
     * @param nNextPrev 次のSID:1,前のSID:-1
     * @throws SQLException SQL実行例外
     * @return RKS_SID
     */
    public int lastAuthorizer(RngKeiroStepModel ksMdl, int nNextPrev)
    throws SQLException {

        PreparedStatement pstmt = null;
        int nRtn = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SORT = ");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RKS_SORT");
            sql.addSql("    from ");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    where ");
            sql.addSql("      RKS_SID = ? ");
            sql.addSql("   )");
            sql.addSql("   + ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            sql.addIntValue(ksMdl.getRksSid());
            sql.addIntValue(nNextPrev);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(ksMdl.getRngSid());
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                nRtn = rs.getInt("RKS_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return nRtn;
    }

    /**
     * <br>[機  能] 最終確認経路が存在するか取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return RKS_SID
     */
    public List<Integer> confirmerCheck(int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Integer> lRtn = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_ROLL_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
            sql.addIntValue(rngSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lRtn.add(rs.getInt("RKS_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return lRtn;
    }

    /**
     * <br>[機  能] 後閲ボタン表示時に現在の経路の前のユーザが後閲指示を受け付けるか
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param sortNo ソート番号
     * @throws SQLException SQL実行例外
     * @return flg 更新件数
     */
    public boolean getKoetuButtonFlg(int rngSid, int sortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        boolean bRtn = false;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_KEIRO_KOETU");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      MIN(RKS_SID) RKS_MIN");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    where");
            sql.addSql("      RKS_SORT < ?");
            sql.addSql("    and");
            sql.addSql("      RNG_SID = ?");
            sql.addSql("    and");
            sql.addSql("      RKS_ROLL_TYPE = ?");
            sql.addSql("    and");
            sql.addSql("      (");
            sql.addSql("         RKS_STATUS = ?");
            sql.addSql("       or");
            sql.addSql("         RKS_STATUS = ?");
            sql.addSql("      )");
            sql.addSql("    )");

            sql.addIntValue(sortNo);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("RKS_KEIRO_KOETU") == 0) {
                    bRtn = true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return bRtn;
    }

    /**
     * <br>[機  能] 承認ボタン表示時に指定経路の前のユーザが後閲指示を受け付けるか
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return flg 更新件数
     */
    public boolean getApprButtonFlg(int rksSid, int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        boolean bRtn = true;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        int confSort = getSortNo(rngSid);
        int toSort = getSortNoKeiro(rksSid, rngSid);


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RKS_SID) as COUNTRKS");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SORT < ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT >= ?");
            sql.addSql(" and");
            sql.addSql("   RKS_KEIRO_KOETU = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");

            sql.addIntValue(toSort);
            sql.addIntValue(confSort);
            sql.addIntValue(RngConst.RNG_KOETU_NO);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("COUNTRKS") > 0) {
                    bRtn = false;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return bRtn;
    }

    /**
     * <br>[機  能] 全ての最終確認者の受信日を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID(更新者SID)
     * @param now 現在日時
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateRcvdateForConfirmUser(int rngSid, int userSid, UDate now)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");

            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addDateValue(now);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);

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
     * <br>[機  能] 稟議経路ステップ情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateKeiroStep(RngKeiroStepModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(bean.getRksSid());

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
     * <br>[機  能] 稟議経路ステップ情報最終確認者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @param rksList 経路SIDリスト
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateConfirm(RngKeiroStepModel bean, List<Integer> rksList)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            if (rksList.size() == 1) {
                sql.addSql("   RKS_SID=?");
                sql.addIntValue(rksList.get(0));
            } else {
                sql.addSql("   RKS_SID IN (");
                for (int idx = 0; idx < rksList.size(); idx++) {
                    if (idx == 0) {
                        sql.addSql("   ?");
                    } else {
                        sql.addSql("  , ?");
                    }
                    sql.addIntValue(rksList.get(idx));
                }
                sql.addSql("   )");
            }
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
     * <br>[機  能] 申請者の稟議経路ステップ情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateKeiroStepAppl(RngKeiroStepModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_ROLL_TYPE = ? ");
            sql.addSql(" and ");
            sql.addSql("   RNG_SID = ? ");

            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
            sql.addIntValue(bean.getRngSid());

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
     * <br>[機  能] 稟議審議情報(承認者)の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateApprovalKeiroStep(RngKeiroStepModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID=?");

            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getRksSid());

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
     * <br>[機  能] 承認者(削除されたユーザ)の稟議経路ステップ情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteKeiroStepForDelUser(int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID in (select RKS_SID");
            sql.addSql("                from RNG_SINGI");
            sql.addSql("               where USR_SID not in (");
            sql.addSql("                                    select USR_SID from CMN_USRM");
            sql.addSql("                                     where USR_SID > ?");
            sql.addSql("                                       and USR_JKBN = ?");
            sql.addSql("                                    )");
            sql.addSql("             )");

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
     * <br>[機  能] 指定した稟議を確認中の経路ステップを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return 経路SID
     * @throws SQLException SQL実行例外
     */
    public int getApprovalKeiro(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int rksSid = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RKS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_STATUS = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                rksSid = rs.getInt("RKS_SID");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return rksSid;
    }

    /**
     * <br>[機  能] 承認者経路ステップSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getApprUserList(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RKS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE = ?");
            sql.addSql(" order by");
            sql.addSql("   RKS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("RKS_SID"));
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
     * <br>[機  能] 稟議経路ステップ情報 並び順の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param rksSid 経路ステップSID
     * @param sort 並び順
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateKeiroSort(int rngSid, int rksSid, int sort)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(sort);
            sql.addIntValue(rngSid);
            sql.addIntValue(rksSid);

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
     * <br>[機  能] ソート番号によりRKS_SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortNo ソートNo
     * @param rngSid 稟議SID
     * @return RKS_SID
     * @throws SQLException SQL実行例外
     */
    public int getSortRksSid(int sortNo, int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RKS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SORT = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(sortNo);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = rs.getInt("RKS_SID");
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
     * <br>[機  能] 稟議経路ステップのソート番号の審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param sortNo ソート番号
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSortKeiroStep(RngKeiroStepModel bean, int sortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT=?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(sortNo);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);

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
     * <br>[機  能] 稟議経路ステップのスキップされる審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param nSkipSortNo スキップするソートNo
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSkipKeiroStep(RngKeiroStepModel bean, int nSkipSortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT<=?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(nSkipSortNo);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);

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
     * <br>[機  能] 稟議経路ステップの後閲指示される審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param nStartSortNo スキップ開始ソートNo
     * @param nFinishSortNo スキップ終了ソートNo
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateKoetuKeiroStep(RngKeiroStepModel bean, int nStartSortNo, int nFinishSortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" set ");
            sql.addSql("   RKS_STATUS=?,");
            sql.addSql("   RKS_RCVDATE=?,");
            sql.addSql("   RKS_CHKDATE=?,");
            sql.addSql("   RKS_EUID=?,");
            sql.addSql("   RKS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT<=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SORT>=?");
            sql.addSql(" and");
            sql.addSql("   RKS_ROLL_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksStatus());
            sql.addDateValue(bean.getRksRcvdate());
            sql.addDateValue(bean.getRksChkdate());
            sql.addIntValue(bean.getRksEuid());
            sql.addDateValue(bean.getRksEdate());
            //where
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(nFinishSortNo);
            sql.addIntValue(nStartSortNo);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);

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
     * <br>[機  能] 稟議SIDとユーザSIDからRKS_SID,RKS_STATUS,後閲関係のフラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @return Rng030KeiroParamList
     * @throws SQLException SQL実行例外
     */
    public List<Rng030KeiroParam> getRksSid(int rngSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Rng030KeiroParam> ret = new ArrayList<Rng030KeiroParam>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_STATUS,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_KEIRO_KOETU,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_KOETU_SIZI,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" left join");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" on");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT");

            sql.addIntValue(rngSid);
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                Rng030KeiroParam mdl = new Rng030KeiroParam();
                mdl.setKeiroStepSid(rs.getInt("RKS_SID"));
                mdl.setKeiroStatus(rs.getInt("RKS_STATUS"));
                mdl.setKeiroKoetu(rs.getInt("RKS_KEIRO_KOETU"));
                mdl.setKeiroKoetuSizi(rs.getInt("RKS_KOETU_SIZI"));
                mdl.setKeiroSingi(rs.getInt("RKS_ROLL_TYPE"));
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
     * <br>[機  能] 稟議SIDから現在後閲指示を出すことができない最小のRKSSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public int getKeiroKoetu(int rngSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   MIN(RNG_KEIRO_STEP.RKS_SID) as SID");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" left join");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" on");
            sql.addSql("   RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_KEIRO_KOETU = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID <> ?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("    or");
            sql.addSql("      RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("    or");
            sql.addSql("      RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("   )");

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_KOETU_NO);
            sql.addIntValue(userSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_DENIAL);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("SID");
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
     * <br>[機  能] 稟議SIDから現在のソート番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public int getSortNo(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RKS_SORT");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_STATUS = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = rs.getInt("RKS_SORT");
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
     * <br>[機  能] 経路SIDからソート番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 稟議SID
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public int getSortNoKeiro(int rksSid, int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RKS_SORT");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rksSid);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = rs.getInt("RKS_SORT");
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
     * <br>[機  能] ソート番号間のユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param startSortNo 開始ソートNo
     * @param finishSortNo 終了ソートNo
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsrSidBySortNo(int startSortNo, int finishSortNo, int rngSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID IN ");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         RKS_SID");
            sql.addSql("       from");
            sql.addSql("         RNG_KEIRO_STEP");
            sql.addSql("       where");
            sql.addSql("         RNG_SID = ?");
            sql.addSql("       and");
            sql.addSql("         RKS_SORT <= ?");
            sql.addSql("       and");
            sql.addSql("         RKS_SORT >= ?");
            sql.addSql("     )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(finishSortNo);
            sql.addIntValue(startSortNo);

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
     * <br>[機  能] ソート番号間のユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getSmlUsrSid(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID IN ");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         RKS_SID");
            sql.addSql("       from");
            sql.addSql("         RNG_KEIRO_STEP");
            sql.addSql("       where");
            sql.addSql("         RNG_SID = ?");
            sql.addSql("       and");
            sql.addSql("         RKS_ROLL_TYPE <> ?");
            sql.addSql("     )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);

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
     * <br>[機  能] ソート番号間のユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsrSid(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID IN ");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         RKS_SID");
            sql.addSql("       from");
            sql.addSql("         RNG_KEIRO_STEP");
            sql.addSql("       where");
            sql.addSql("         RNG_SID = ?");
            sql.addSql("       and");
            sql.addSql("         RKS_ROLL_TYPE <> ?");
            sql.addSql("       and");
            sql.addSql("         RKS_STATUS <> ?");
            sql.addSql("     )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);

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
     * <br>[機  能] 経路の審議なし進行許可フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @return 審議なし進行許可フラグ
     * @throws SQLException SQL実行例外
     */
    public int getNoUser(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RNG_TEMPLATE_KEIRO.RTK_NOUSER");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql("   ,RNG_TEMPLATE_KEIRO");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addSql("   and RNG_TEMPLATE_KEIRO.RTK_SID = RNG_KEIRO_STEP.RTK_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rksSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RTK_NOUSER");
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
     * <br>[機  能] 経路上に削除されているユーザのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @return 審議なし進行許可フラグ
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDelUser(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RNG_SINGI.USR_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   RNG_SINGI.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_SINGI.RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");

            sql.addIntValue(rksSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create RNG_KEIRO_STEP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngKeiroStepModel
     * @throws SQLException SQL実行例外
     */
    private RngKeiroStepModel __getRngKeiroStepFromRs(ResultSet rs) throws SQLException {
        RngKeiroStepModel bean = new RngKeiroStepModel();
        bean.setRksSid(rs.getInt("RKS_SID"));
        bean.setRngSid(rs.getInt("RNG_SID"));
        bean.setRksSort(rs.getInt("RKS_SORT"));
        bean.setRksStatus(rs.getInt("RKS_STATUS"));
        bean.setRtkSid(rs.getInt("RTK_SID"));
        bean.setRksRollType(rs.getInt("RKS_ROLL_TYPE"));
        bean.setRksRcvdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_RCVDATE")));
        bean.setRksChkdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_CHKDATE")));
        bean.setRksAuid(rs.getInt("RKS_AUID"));
        bean.setRksAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_ADATE")));
        bean.setRksEuid(rs.getInt("RKS_EUID"));
        bean.setRksEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_EDATE")));
        bean.setRksBelongSid(rs.getInt("RKS_BELONG_SID"));
        bean.setRksKeiroKoetu(rs.getInt("RKS_KEIRO_KOETU"));
        bean.setRksKoetuSizi(rs.getInt("RKS_KOETU_SIZI"));
        bean.setRksAddstep(rs.getInt("RKS_ADDSTEP"));

        return bean;
    }
}
