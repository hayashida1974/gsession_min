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
import jp.groupsession.v2.bbs.model.BbsViewLogCountModel;

/**
 * <p>BBS_VIEW_LOG_COUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsViewLogCountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsViewLogCountDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsViewLogCountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsViewLogCountDao(Connection con) {
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
            sql.addSql("drop table BBS_VIEW_LOG_COUNT");

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
            sql.addSql(" create table BBS_VIEW_LOG_COUNT (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BTI_SID NUMBER(10,0) not null,");
            sql.addSql("   BVL_VIEW_DATE varchar(23) not null");
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
     * <p>Insert BBS_VIEW_LOG_COUNT Data Bindding JavaBean
     * @param bean BBS_VIEW_LOG_COUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsViewLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_VIEW_LOG_COUNT(");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BVL_VIEW_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addDateValue(bean.getBvlViewDate());
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
     * <p>Insert BBS_VIEW_LOG_COUNT DataList
     * @param beanList BBS_VIEW_LOG_COUNT DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<BbsViewLogCountModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_VIEW_LOG_COUNT(");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BVL_VIEW_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (BbsViewLogCountModel bean : beanList) {
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getBfiSid());
                sql.addIntValue(bean.getBtiSid());
                sql.addDateValue(bean.getBvlViewDate());
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
     * <p>Update BBS_VIEW_LOG_COUNT Data Bindding JavaBean
     * @param bean BBS_VIEW_LOG_COUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsViewLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_VIEW_LOG_COUNT");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   BFI_SID=?,");
            sql.addSql("   BTI_SID=?,");
            sql.addSql("   BVL_VIEW_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addDateValue(bean.getBvlViewDate());

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
     * <p>Select BBS_VIEW_LOG_COUNT All Data
     * @return List in BBS_VIEW_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsViewLogCountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsViewLogCountModel> ret = new ArrayList<BbsViewLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BVL_VIEW_DATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_VIEW_LOG_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsViewLogCountFromRs(rs));
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
     * <p>指定範囲の掲示板閲覧ログ集計データを取得する
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in BBS_VIEW_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsViewLogCountModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsViewLogCountModel> ret = new ArrayList<BbsViewLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BVL_VIEW_DATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_VIEW_LOG_COUNT");
            sql.addSql(" order by ");
            sql.addSql("   BVL_VIEW_DATE asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsViewLogCountFromRs(rs));
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
     * <p>全件数を取得する
     * @return List in BBS_VIEW_LOG_COUNT count
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
            sql.addSql("   BBS_VIEW_LOG_COUNT");

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
     * <br>[機  能] 削除を行う
     * <br>[解  説]
     * <br>[備  考] 前日までの集計データを削除する
     * @param date 現在日時
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        
        UDate preDay = date.cloneUDate();
        preDay.addDay(-1);
        preDay.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   BBS_VIEW_LOG_COUNT");
            sql.addSql(" where ");
            sql.addSql("   BVL_VIEW_DATE <= ?");
            sql.addDateValue(preDay);

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
     * <p>Create BBS_VIEW_LOG_COUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsViewLogCountModel
     * @throws SQLException SQL実行例外
     */
    private BbsViewLogCountModel __getBbsViewLogCountFromRs(ResultSet rs) throws SQLException {
        BbsViewLogCountModel bean = new BbsViewLogCountModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBvlViewDate(UDate.getInstanceTimestamp(rs.getTimestamp("BVL_VIEW_DATE")));
        return bean;
    }
}
