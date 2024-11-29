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
import jp.groupsession.v2.bbs.model.BbsForSumModel;

/**
 * <p>BBS_FOR_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsForSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsForSumDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsForSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsForSumDao(Connection con) {
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
            sql.addSql("drop table BBS_FOR_SUM");

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
            sql.addSql(" create table BBS_FOR_SUM (");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BFS_THRE_CNT NUMBER(10,0),");
            sql.addSql("   BFS_WRT_CNT NUMBER(10,0),");
            sql.addSql("   BFS_WRT_DATE varchar(26),");
            sql.addSql("   BFS_AUID NUMBER(10,0) not null,");
            sql.addSql("   BFS_ADATE varchar(26) not null,");
            sql.addSql("   BFS_EUID NUMBER(10,0) not null,");
            sql.addSql("   BFS_EDATE varchar(26) not null,");
            sql.addSql("   BFS_SIZE bigint not null,");
            sql.addSql("   primary key (BFI_SID)");
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
     * <p>Insert BBS_FOR_SUM Data Bindding JavaBean
     * @param bean BBS_FOR_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsForSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_FOR_SUM(");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFS_THRE_CNT,");
            sql.addSql("   BFS_WRT_CNT,");
            sql.addSql("   BFS_WRT_DATE,");
            sql.addSql("   BFS_AUID,");
            sql.addSql("   BFS_ADATE,");
            sql.addSql("   BFS_EUID,");
            sql.addSql("   BFS_EDATE,");
            sql.addSql("   BFS_SIZE");
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
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBfsThreCnt());
            sql.addIntValue(bean.getBfsWrtCnt());
            sql.addDateValue(bean.getBfsWrtDate());
            sql.addIntValue(bean.getBfsAuid());
            sql.addDateValue(bean.getBfsAdate());
            sql.addIntValue(bean.getBfsEuid());
            sql.addDateValue(bean.getBfsEdate());
            sql.addLongValue(bean.getBfsSize());
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
     * <br>[機  能] フォーラム集計情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean フォーラム集計情報モデル
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsForSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_SUM");
            sql.addSql(" set ");
            sql.addSql("   BFS_THRE_CNT=?,");
            sql.addSql("   BFS_WRT_CNT=?,");
            sql.addSql("   BFS_WRT_DATE=?,");
            sql.addSql("   BFS_EUID=?,");
            sql.addSql("   BFS_EDATE=?,");
            sql.addSql("   BFS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfsThreCnt());
            sql.addIntValue(bean.getBfsWrtCnt());
            //フォーラム内にスレッドが存在しない時
            if (bean.getBfsThreCnt() == 0) {
                sql.addDateValue(null);
            }
            //フォーラム内にスレッドが存在するとき
            if (bean.getBfsThreCnt() > 0) {
                BbsWriteInfDao bwiDao = new BbsWriteInfDao(con);
                sql.addDateValue(bwiDao.getForMaxEdate(bean.getBfiSid()));
            }
            sql.addIntValue(bean.getBfsEuid());
            sql.addDateValue(bean.getBfsEdate());
            sql.addLongValue(bean.getBfsSize());
            //where
            sql.addIntValue(bean.getBfiSid());

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
     * <p>Select BBS_FOR_SUM All Data
     * @return List in BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsForSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList < BbsForSumModel > ret = new ArrayList < BbsForSumModel >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFS_THRE_CNT,");
            sql.addSql("   BFS_WRT_CNT,");
            sql.addSql("   BFS_WRT_DATE,");
            sql.addSql("   BFS_AUID,");
            sql.addSql("   BFS_ADATE,");
            sql.addSql("   BFS_EUID,");
            sql.addSql("   BFS_EDATE,");
            sql.addSql("   BFS_SIZE");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForSumFromRs(rs));
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
     * <p>Select BBS_FOR_SUM
     * @param bean BBS_FOR_SUM Model
     * @return BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    public BbsForSumModel select(BbsForSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsForSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFS_THRE_CNT,");
            sql.addSql("   BFS_WRT_CNT,");
            sql.addSql("   BFS_WRT_DATE,");
            sql.addSql("   BFS_AUID,");
            sql.addSql("   BFS_ADATE,");
            sql.addSql("   BFS_EUID,");
            sql.addSql("   BFS_EDATE,");
            sql.addSql("   BFS_SIZE");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_SUM");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsForSumFromRs(rs);
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
     * <br>[機  能] フォーラムのディスク使用量合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return フォーラムのディスク使用量合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalUseDiskSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long useDiskSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(BFS_SIZE) as TOTAL_SIZE");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                useDiskSize = rs.getLong("TOTAL_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return useDiskSize;
    }

    /**
     * <br>[機  能] 全スレッド件数と全投稿件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    public BbsForSumModel get() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsForSumModel model = new BbsForSumModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(BFS_THRE_CNT) as THRE_CNT,");
            sql.addSql("   sum(BFS_WRT_CNT) as WRT_CNT");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model.setBfsThreCnt(rs.getInt("THRE_CNT"));
                model.setBfsWrtCnt(rs.getInt("WRT_CNT"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }
    /**
     * <p>Delete BBS_FOR_SUM
     * @param bean BBS_FOR_SUM Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(BbsForSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_SUM");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());

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
     * <p>Create BBS_FOR_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsForSumModel
     * @throws SQLException SQL実行例外
     */
    private BbsForSumModel __getBbsForSumFromRs(ResultSet rs) throws SQLException {
        BbsForSumModel bean = new BbsForSumModel();
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBfsThreCnt(rs.getInt("BFS_THRE_CNT"));
        bean.setBfsWrtCnt(rs.getInt("BFS_WRT_CNT"));
        bean.setBfsWrtDate(UDate.getInstanceTimestamp(rs.getTimestamp("BFS_WRT_DATE")));
        bean.setBfsAuid(rs.getInt("BFS_AUID"));
        bean.setBfsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFS_ADATE")));
        bean.setBfsEuid(rs.getInt("BFS_EUID"));
        bean.setBfsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFS_EDATE")));
        bean.setBfsSize(rs.getLong("BFS_SIZE"));
        return bean;
    }
}
