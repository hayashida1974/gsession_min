package jp.groupsession.v2.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsLogCountSumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsLogCountSumDao(Connection con) {
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
            sql.addSql("drop table BBS_LOG_COUNT_SUM");

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
            sql.addSql(" create table BBS_LOG_COUNT_SUM (");
            sql.addSql("   BLS_KBN NUMBER(10,0) not null,");
            sql.addSql("   BLS_CNT Date not null,");
            sql.addSql("   BLS_DATE Date not null,");
            sql.addSql("   BLS_MONTH NUMBER(10,0) not null,");
            sql.addSql("   BLS_EDATE varchar(23) not null");
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
     * <p>Insert BBS_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean BBS_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_LOG_COUNT_SUM(");
            sql.addSql("   BLS_KBN,");
            sql.addSql("   BLS_CNT,");
            sql.addSql("   BLS_DATE,");
            sql.addSql("   BLS_MONTH,");
            sql.addSql("   BLS_EDATE");
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
            sql.addIntValue(bean.getBlsKbn());
            sql.addLongValue(bean.getBlsCnt());
            sql.addDateValue(bean.getBlsDate());
            sql.addIntValue(bean.getBlsMonth());
            sql.addDateValue(bean.getBlsEdate());
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
     * <p>Update BBS_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean BBS_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   BLS_CNT=?,");
            sql.addSql("   BLS_MONTH=?,");
            sql.addSql("   BLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BLS_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   BLS_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBlsCnt());
            sql.addIntValue(bean.getBlsMonth());
            sql.addDateValue(bean.getBlsEdate());
            sql.addIntValue(bean.getBlsKbn());
            sql.addDateValue(bean.getBlsDate());

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
     * <p>Select BBS_LOG_COUNT_SUM All Data
     * @return List in BBS_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsLogCountSumModel> ret = new ArrayList<BbsLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BLS_KBN,");
            sql.addSql("   BLS_CNT,");
            sql.addSql("   BLS_DATE,");
            sql.addSql("   BLS_MONTH,");
            sql.addSql("   BLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsLogCountSumFromRs(rs));
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param blsKbn ログ区分
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public BbsLogCountSumModel getSumLogCount(int blsKbn, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsLogCountSumModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (blsKbn == GSConstBulletin.BLS_KBN_VIEW) {
                //閲覧集計データの集計
                sql.addSql(" select");
                sql.addSql("   cast(BVL_VIEW_DATE as date) as LOG_DATE,");
                sql.addSql("   count(*) as CNT,");
                sql.addSql("   max(BVL_VIEW_DATE) as MAX_DATE");
                sql.addSql(" from");
                sql.addSql("   BBS_VIEW_LOG_COUNT");
                sql.addSql(" where");
                sql.addSql("   BVL_VIEW_DATE >= ?");
                sql.addSql(" and");
                sql.addSql("   BVL_VIEW_DATE <= ?");
                sql.addSql(" group by");
                sql.addSql("   LOG_DATE");
            } else {
                //投稿集計データの集計
                sql.addSql(" select");
                sql.addSql("   cast(BWL_WRT_DATE as date) as LOG_DATE,");
                sql.addSql("   count(*) as CNT,");
                sql.addSql("   max(BWL_WRT_DATE) as MAX_DATE");
                sql.addSql(" from");
                sql.addSql("   BBS_WRITE_LOG_COUNT");
                sql.addSql(" where");
                sql.addSql("   BWL_WRT_DATE >= ?");
                sql.addSql(" and");
                sql.addSql("   BWL_WRT_DATE <= ?");
                sql.addSql(" group by");
                sql.addSql("   LOG_DATE");
            }

            UDate frDate = date.cloneUDate();
            frDate.setZeroHhMmSs();
            UDate toDate = date.cloneUDate();
            toDate.setMaxHhMmSs();
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = new BbsLogCountSumModel();
                model.setBlsKbn(blsKbn);

                UDate blsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
                model.setBlsDate(blsDate);
                model.setBlsMonth(Integer.parseInt(blsDate.getDateString().substring(0, 6)));
                model.setBlsCnt(rs.getLong("CNT"));
                model.setBlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE")));
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説] 集計結果(BBS_LOG_COUNT_SUM)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param blsKbn ログ区分
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<BbsLogCountSumModel> getNonRegisteredList(int blsKbn, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsLogCountSumModel> logSumList = new ArrayList<BbsLogCountSumModel>();
        con = getCon();
        UDate from = date.cloneUDate();
        from.addDay(-2);
        from.setZeroHhMmSs();
        UDate to = date.cloneUDate();
        to.setMaxHhMmSs();


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LOG_SUM.LOG_DATE as LOG_DATE,");
            sql.addSql("   LOG_SUM.CNT as CNT,");
            sql.addSql("   LOG_SUM.MAX_DATE as MAX_DATE");
            sql.addSql(" from");

            if (blsKbn == GSConstBulletin.BLS_KBN_VIEW) {
                //閲覧集計データの集計
                sql.addSql("   (");
                sql.addSql("    select");
                sql.addSql("      cast(BVL_VIEW_DATE as date) as LOG_DATE,");
                sql.addSql("      count(*) as CNT,");
                sql.addSql("      max(BVL_VIEW_DATE) as MAX_DATE");
                sql.addSql("    from");
                sql.addSql("      BBS_VIEW_LOG_COUNT");
                sql.addSql("    where ");
                sql.addSql("      BVL_VIEW_DATE >= ?");
                sql.addDateValue(from);
                sql.addSql("    and");
                sql.addSql("      BVL_VIEW_DATE <= ?");
                sql.addDateValue(to);
                sql.addSql("    group by");
                sql.addSql("      LOG_DATE");
                sql.addSql("   ) LOG_SUM");
            } else {
                //投稿集計データの集計
                sql.addSql("   (");
                sql.addSql("    select");
                sql.addSql("      cast(BWL_WRT_DATE as date) as LOG_DATE,");
                sql.addSql("      count(*) as CNT,");
                sql.addSql("      max(BWL_WRT_DATE) as MAX_DATE");
                sql.addSql("    from");
                sql.addSql("      BBS_WRITE_LOG_COUNT");
                sql.addSql("    where ");
                sql.addSql("      BWL_WRT_DATE >= ?");
                sql.addDateValue(from);
                sql.addSql("    and");
                sql.addSql("      BWL_WRT_DATE <= ?");
                sql.addDateValue(to);
                sql.addSql("    group by");
                sql.addSql("      LOG_DATE");
                sql.addSql("   ) LOG_SUM");
            }

            sql.addSql(" where");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from BBS_LOG_COUNT_SUM");
            sql.addSql("     where");
            sql.addSql("       BBS_LOG_COUNT_SUM.BLS_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.LOG_DATE = BBS_LOG_COUNT_SUM.BLS_DATE");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.MAX_DATE = BBS_LOG_COUNT_SUM.BLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE");

            sql.addIntValue(blsKbn);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BbsLogCountSumModel model = new BbsLogCountSumModel();
                model.setBlsKbn(blsKbn);

                UDate blsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
                model.setBlsDate(blsDate);
                model.setBlsMonth(Integer.parseInt(blsDate.getDateString().substring(0, 6)));
                model.setBlsCnt(rs.getLong("CNT"));
                model.setBlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_DATE")));

                logSumList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return logSumList;
    }

    /**
     * <p>Create BBS_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private BbsLogCountSumModel __getBbsLogCountSumFromRs(ResultSet rs) throws SQLException {
        BbsLogCountSumModel bean = new BbsLogCountSumModel();
        bean.setBlsKbn(rs.getInt("BLS_KBN"));
        bean.setBlsCnt(rs.getInt("BLS_CNT"));
        bean.setBlsDate(UDate.getInstanceTimestamp(rs.getTimestamp("BLS_DATE")));
        bean.setBlsMonth(rs.getInt("BLS_MONTH"));
        bean.setBlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BLS_EDATE")));
        return bean;
    }
}
