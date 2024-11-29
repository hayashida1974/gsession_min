package jp.groupsession.v2.bbs.dao;


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
import jp.groupsession.v2.bbs.model.BbsThreSumModel;

/**
 * <p>BBS_THRE_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsThreSumDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsThreSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsThreSumDao(Connection con) {
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
            sql.addSql("drop table BBS_THRE_SUM");

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
            sql.addSql(" create table BBS_THRE_SUM (");
            sql.addSql("   BTI_SID NUMBER(10,0) not null,");
            sql.addSql("   BTS_WRT_CNT NUMBER(10,0),");
            sql.addSql("   BTS_WRT_DATE varchar(26),");
            sql.addSql("   BTS_AUID NUMBER(10,0) not null,");
            sql.addSql("   BTS_ADATE varchar(26) not null,");
            sql.addSql("   BTS_EUID NUMBER(10,0) not null,");
            sql.addSql("   BTS_EDATE varchar(26) not null,");
            sql.addSql("   BTS_SIZE bigint not null,");
            sql.addSql("   BTS_TEMPFLG NUMBER(10,0) not null,");
            sql.addSql("   primary key (BTI_SID)");
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
     * <p>Insert BBS_THRE_SUM Data Bindding JavaBean
     * @param bean BBS_THRE_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsThreSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_SUM(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BTS_WRT_CNT,");
            sql.addSql("   BTS_WRT_DATE,");
            sql.addSql("   BTS_AUID,");
            sql.addSql("   BTS_ADATE,");
            sql.addSql("   BTS_EUID,");
            sql.addSql("   BTS_EDATE,");
            sql.addSql("   BTS_SIZE,");
            sql.addSql("   BTS_TEMPFLG");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getBtsWrtCnt());
            sql.addDateValue(bean.getBtsWrtDate());
            sql.addIntValue(bean.getBtsAuid());
            sql.addDateValue(bean.getBtsAdate());
            sql.addIntValue(bean.getBtsEuid());
            sql.addDateValue(bean.getBtsEdate());
            sql.addLongValue(bean.getBtsSize());
            sql.addIntValue(bean.getBtsTempflg());
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
     * <p>Update BBS_THRE_SUM Data Bindding JavaBean
     * @param bean BBS_THRE_SUM Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsThreSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" set ");
            sql.addSql("   BTS_WRT_CNT=?,");
            sql.addSql("   BTS_WRT_DATE=?,");
            sql.addSql("   BTS_EUID=?,");
            sql.addSql("   BTS_EDATE=?,");
            sql.addSql("   BTS_SIZE=?,");
            sql.addSql("   BTS_TEMPFLG=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtsWrtCnt());
            sql.addDateValue(bean.getBtsWrtDate());
            sql.addIntValue(bean.getBtsEuid());
            sql.addDateValue(bean.getBtsEdate());
            sql.addLongValue(bean.getBtsSize());
            sql.addIntValue(bean.getBtsTempflg());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <br>[機  能] スレッド集計情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean スレッド集計情報モデル
     * @throws SQLException SQL実行例外
     */
    public void updateThreSumData(BbsThreSumModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" set ");
            sql.addSql("   BTS_WRT_CNT=?,");
            sql.addSql("   BTS_WRT_DATE=?,");
            sql.addSql("   BTS_EUID=?,");
            sql.addSql("   BTS_EDATE=?,");
            sql.addSql("   BTS_SIZE=?,");
            sql.addSql("   BTS_TEMPFLG=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtsWrtCnt());
            BbsWriteInfDao bwiDao = new BbsWriteInfDao(con);
            sql.addDateValue(bwiDao.getThreMaxEdate(bean.getBtiSid()));
            sql.addIntValue(bean.getBtsEuid());
            sql.addDateValue(bean.getBtsEdate());
            sql.addLongValue(bean.getBtsSize());
            sql.addIntValue(bean.getBtsTempflg());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <p>Select BBS_THRE_SUM
     * @param bean BBS_THRE_SUM Model
     * @return BBS_THRE_SUMModel
     * @throws SQLException SQL実行例外
     */
    public BbsThreSumModel select(BbsThreSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsThreSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BTS_WRT_CNT,");
            sql.addSql("   BTS_WRT_DATE,");
            sql.addSql("   BTS_AUID,");
            sql.addSql("   BTS_ADATE,");
            sql.addSql("   BTS_EUID,");
            sql.addSql("   BTS_EDATE,");
            sql.addSql("   BTS_SIZE,");
            sql.addSql("   BTS_TEMPFLG");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtiSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsThreSumFromRs(rs);
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
     * <p>Select BBS_THRE_SUM
     * @return BBS_THRE_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsThreSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreSumModel> ret = new ArrayList<BbsThreSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BTS_WRT_CNT,");
            sql.addSql("   BTS_WRT_DATE,");
            sql.addSql("   BTS_AUID,");
            sql.addSql("   BTS_ADATE,");
            sql.addSql("   BTS_EUID,");
            sql.addSql("   BTS_EDATE,");
            sql.addSql("   BTS_SIZE,");
            sql.addSql("   BTS_TEMPFLG");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreSumFromRs(rs));
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
     * <p>Select BBS_THRE_SUM
     * @param threadSidList スレッドSIDリスト
     * @return BBS_THRE_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsThreSumModel> select(List<Integer> threadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreSumModel> ret = new ArrayList<BbsThreSumModel>();
        con = getCon();

        if (threadSidList.isEmpty() || threadSidList.size() < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BTS_WRT_CNT,");
            sql.addSql("   BTS_WRT_DATE,");
            sql.addSql("   BTS_AUID,");
            sql.addSql("   BTS_ADATE,");
            sql.addSql("   BTS_EUID,");
            sql.addSql("   BTS_EDATE,");
            sql.addSql("   BTS_SIZE,");
            sql.addSql("   BTS_TEMPFLG");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where");
            sql.addSql("   BTI_SID in (");
            for (int i = 0; i < threadSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("   ,");
                }
                sql.addSql("   ?");
                sql.addIntValue(threadSidList.get(i));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreSumFromRs(rs));
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
     * <p>Select BBS_THRE_SUM
     * @param btiSid スレッドSID
     * @return BBS_THRE_SUMModel
     * @throws SQLException SQL実行例外
     */
    public BbsThreSumModel select(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsThreSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BTS_WRT_CNT,");
            sql.addSql("   BTS_WRT_DATE,");
            sql.addSql("   BTS_AUID,");
            sql.addSql("   BTS_ADATE,");
            sql.addSql("   BTS_EUID,");
            sql.addSql("   BTS_EDATE,");
            sql.addSql("   BTS_SIZE,");
            sql.addSql("   BTS_TEMPFLG");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsThreSumFromRs(rs);
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
     * <br>[機  能] 指定したフォーラム内のスレッドディスクサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return ディスクサイズの合計
     * @throws SQLException SQL実行時例外
     */
    public long getTotalThreadSize(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long size = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(BBS_THRE_SUM.BTS_SIZE) as THRE_SIZE");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_INF,");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where");
            sql.addSql("   BBS_THRE_INF.BFI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_INF.BTI_SID = BBS_THRE_SUM.BTI_SID");

            sql.addIntValue(bfiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                size = rs.getLong("THRE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return size;
    }

    /**
     * <p>Delete BBS_THRE_SUM
     * @param btiSid スレッドSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

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
     * <br>[機  能]全投稿数を集計する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean BBS_THRE_SUM Model
     * @return 全投稿数
     * @throws SQLException SQL実行例外
     */
    public int getTotalUpCnt(BbsThreSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int upCnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(BTS_WRT_CNT) as UP_CNT");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BTS_WRT_CNT=?");

            sql.addIntValue(bean.getBtsWrtCnt());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                upCnt = rs.getInt("UP_CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return upCnt;
    }

    /**
     * <br>[機  能]全スレッド数を集計する。
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 全投稿数
     * @throws SQLException SQL実行例外
     */
    public int getThreCnt(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int threCnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(BTI_SID) as THRE_CNT");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                threCnt = rs.getInt("THRE_CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return threCnt;
    }
    /**
     * <p>Create BBS_THRE_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsThreSumModel
     * @throws SQLException SQL実行例外
     */
    private BbsThreSumModel __getBbsThreSumFromRs(ResultSet rs) throws SQLException {
        BbsThreSumModel bean = new BbsThreSumModel();
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBtsWrtCnt(rs.getInt("BTS_WRT_CNT"));
        bean.setBtsWrtDate(UDate.getInstanceTimestamp(rs.getTimestamp("BTS_WRT_DATE")));
        bean.setBtsAuid(rs.getInt("BTS_AUID"));
        bean.setBtsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BTS_ADATE")));
        bean.setBtsEuid(rs.getInt("BTS_EUID"));
        bean.setBtsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BTS_EDATE")));
        bean.setBtsSize(rs.getLong("BTS_SIZE"));
        bean.setBtsTempflg(rs.getInt("BTS_TEMPFLG"));
        return bean;
    }
}
