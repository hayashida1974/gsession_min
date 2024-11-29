package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlLogCountSumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlLogCountSumDao(Connection con) {
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
            sql.addSql("drop table WML_LOG_COUNT_SUM");

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
            sql.addSql(" create table WML_LOG_COUNT_SUM (");
            sql.addSql("   WLS_KBN NUMBER(10,0) not null,");
            sql.addSql("   WLS_CNT Date not null,");
            sql.addSql("   WLS_CNT_TO Date not null,");
            sql.addSql("   WLS_CNT_CC Date not null,");
            sql.addSql("   WLS_CNT_BCC Date not null,");
            sql.addSql("   WLS_DATE Date not null,");
            sql.addSql("   WLS_MONTH NUMBER(10,0) not null,");
            sql.addSql("   WLS_EDATE varchar(23) not null");
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
     * <p>Insert WML_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean WML_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_LOG_COUNT_SUM(");
            sql.addSql("   WLS_KBN,");
            sql.addSql("   WLS_CNT,");
            sql.addSql("   WLS_CNT_TO,");
            sql.addSql("   WLS_CNT_CC,");
            sql.addSql("   WLS_CNT_BCC,");
            sql.addSql("   WLS_DATE,");
            sql.addSql("   WLS_MONTH,");
            sql.addSql("   WLS_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWlsKbn());
            sql.addLongValue(bean.getWlsCnt());
            sql.addLongValue(bean.getWlsCntTo());
            sql.addLongValue(bean.getWlsCntCc());
            sql.addLongValue(bean.getWlsCntBcc());
            sql.addDateValue(bean.getWlsDate());
            sql.addIntValue(bean.getWlsMonth());
            sql.addDateValue(bean.getWlsEdate());
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
     * <p>Update WML_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean WML_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   WLS_CNT=?,");
            sql.addSql("   WLS_CNT_TO=?,");
            sql.addSql("   WLS_CNT_CC=?,");
            sql.addSql("   WLS_CNT_BCC=?,");
            sql.addSql("   WLS_MONTH=?,");
            sql.addSql("   WLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WLS_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   WLS_DATE=?");
            sql.addLongValue(bean.getWlsCnt());
            sql.addLongValue(bean.getWlsCntTo());
            sql.addLongValue(bean.getWlsCntCc());
            sql.addLongValue(bean.getWlsCntBcc());
            sql.addIntValue(bean.getWlsMonth());
            sql.addDateValue(bean.getWlsEdate());

            sql.addIntValue(bean.getWlsKbn());
            sql.addDateValue(bean.getWlsDate());

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
     * <p>Select WML_LOG_COUNT_SUM All Data
     * @return List in WML_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlLogCountSumModel> ret = new ArrayList<WmlLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WLS_KBN,");
            sql.addSql("   WLS_CNT,");
            sql.addSql("   WLS_CNT_TO,");
            sql.addSql("   WLS_CNT_CC,");
            sql.addSql("   WLS_CNT_BCC,");
            sql.addSql("   WLS_DATE,");
            sql.addSql("   WLS_MONTH,");
            sql.addSql("   WLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlLogCountSumFromRs(rs));
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
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<WmlLogCountSumModel> getSumLogCount(UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLogCountSumModel> sumList = new ArrayList<WmlLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLC_KBN,");
            sql.addSql("   cast(WLC_DATE as date) as LOG_DATE,");
            sql.addSql("   count(*) as CNT_SUM,");
            sql.addSql("   sum(WLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("   sum(WLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("   sum(WLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("   max(WLC_DATE) as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_LOG_COUNT");
            sql.addSql(" where");
            sql.addSql("   WLC_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   WLC_DATE <= ?");
            sql.addSql(" group by");
            sql.addSql("   WLC_KBN,");
            sql.addSql("   LOG_DATE");

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
            while (rs.next()) {
                sumList.add(__getWmlLogSumData(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sumList;
    }

    /**
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wlcKbn ログ区分
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public WmlLogCountSumModel getSumLogCount(int wlcKbn, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlLogCountSumModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLC_KBN,");
            sql.addSql("   cast(WLC_DATE as date) as LOG_DATE,");
            sql.addSql("   count(*) as CNT_SUM,");
            sql.addSql("   sum(WLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("   sum(WLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("   sum(WLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("   max(WLC_DATE) as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_LOG_COUNT");
            sql.addSql(" where");
            sql.addSql("   WLC_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   WLC_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   WLC_DATE <= ?");
            sql.addSql(" group by");
            sql.addSql("   WLC_KBN,");
            sql.addSql("   LOG_DATE");

            UDate frDate = date.cloneUDate();
            frDate.setZeroHhMmSs();
            UDate toDate = date.cloneUDate();
            toDate.setMaxHhMmSs();
            sql.addIntValue(wlcKbn);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getWmlLogSumData(rs);
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
     * <br>[解  説] 集計結果(WML_LOG_COUNT_SUM)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<WmlLogCountSumModel> getNonRegisteredList(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLogCountSumModel> logSumList = new ArrayList<WmlLogCountSumModel>();
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
            sql.addSql("   LOG_SUM.WLC_KBN as WLC_KBN,");
            sql.addSql("   LOG_SUM.LOG_DATE as LOG_DATE,");
            sql.addSql("   LOG_SUM.CNT_SUM as CNT_SUM,");
            sql.addSql("   LOG_SUM.CNT_TO_SUM as CNT_TO_SUM,");
            sql.addSql("   LOG_SUM.CNT_CC_SUM as CNT_CC_SUM,");
            sql.addSql("   LOG_SUM.CNT_BCC_SUM as CNT_BCC_SUM,");
            sql.addSql("   LOG_SUM.MAX_EDATE as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      WLC_KBN,");
            sql.addSql("      cast(WLC_DATE as date) as LOG_DATE,");
            sql.addSql("      count(*) as CNT_SUM,");
            sql.addSql("      sum(WLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("      sum(WLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("      sum(WLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("      max(WLC_DATE) as MAX_EDATE");
            sql.addSql("    from");
            sql.addSql("      WML_LOG_COUNT");
            sql.addSql("    where ");
            sql.addSql("      WLC_DATE >= ?");
            sql.addDateValue(from);
            sql.addSql("    and");
            sql.addSql("      WLC_DATE <= ?");
            sql.addDateValue(to);
            sql.addSql("    group by");
            sql.addSql("      WLC_KBN,");
            sql.addSql("      LOG_DATE");
            sql.addSql("   ) LOG_SUM");
            sql.addSql(" where");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from WML_LOG_COUNT_SUM");
            sql.addSql("     where");
            sql.addSql("      LOG_SUM.WLC_KBN = WML_LOG_COUNT_SUM.WLS_KBN");
            sql.addSql("     and");
            sql.addSql("      LOG_SUM.LOG_DATE = WML_LOG_COUNT_SUM.WLS_DATE");
            sql.addSql("     and");
            sql.addSql("      LOG_SUM.MAX_EDATE = WML_LOG_COUNT_SUM.WLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE,");
            sql.addSql("   LOG_SUM.WLC_KBN");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                logSumList.add(__getWmlLogSumData(rs));
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
     * <p>Create WML_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private WmlLogCountSumModel __getWmlLogCountSumFromRs(ResultSet rs) throws SQLException {
        WmlLogCountSumModel bean = new WmlLogCountSumModel();
        bean.setWlsKbn(rs.getInt("WLS_KBN"));
        bean.setWlsCnt(rs.getLong("WLS_CNT"));
        bean.setWlsCntTo(rs.getLong("WLS_CNT_TO"));
        bean.setWlsCntCc(rs.getLong("WLS_CNT_CC"));
        bean.setWlsCntBcc(rs.getLong("WLS_CNT_BCC"));
        bean.setWlsDate(UDate.getInstanceTimestamp(rs.getTimestamp("WLS_DATE")));
        bean.setWlsMonth(rs.getInt("WLS_MONTH"));
        bean.setWlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WLS_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] メールログ集計の集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return メールログ集計の集計結果
     * @throws SQLException SQL実行例外
     */
    private WmlLogCountSumModel __getWmlLogSumData(ResultSet rs) throws SQLException {
        WmlLogCountSumModel model = new WmlLogCountSumModel();
        model.setWlsKbn(rs.getInt("WLC_KBN"));
        model.setWlsCnt(rs.getLong("CNT_SUM"));
        if (model.getWlsKbn() == GSConstWebmail.LOG_COUNT_KBN_JMAIL) {
            model.setWlsCntTo(1);
            model.setWlsCntCc(0);
            model.setWlsCntBcc(0);
        } else {
            model.setWlsCntTo(rs.getLong("CNT_TO_SUM"));
            model.setWlsCntCc(rs.getLong("CNT_CC_SUM"));
            model.setWlsCntBcc(rs.getLong("CNT_BCC_SUM"));
        }
        UDate wlsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
        model.setWlsDate(wlsDate);
        model.setWlsMonth(Integer.parseInt(wlsDate.getDateString().substring(0, 6)));
        model.setWlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_EDATE")));
        return model;
    }
    /**
    *
    * <br>[機  能] 指定日の統計情報の集計を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param date 集計対象日
    * @throws SQLException SQL実行時例外
    */
   public void updateLogSum(UDate date) throws SQLException {
       List<WmlLogCountSumModel> logSumList = getSumLogCount(date);
       if (logSumList != null && !logSumList.isEmpty()) {
           for (WmlLogCountSumModel logSumMdl : logSumList) {
               if (update(logSumMdl) == 0) {
                   insert(logSumMdl);
               }
           }
       }

   }

}
