package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlLogCountModel;
import jp.groupsession.v2.sml.model.SmlLogCountSumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlLogCountSumDao(Connection con) {
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
            sql.addSql("drop table SML_LOG_COUNT_SUM");

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
            sql.addSql(" create table SML_LOG_COUNT_SUM (");
            sql.addSql("   SLS_KBN NUMBER(10,0) not null,");
            sql.addSql("   SLS_SYS_KBN NUMBER(10,0) not null,");
            sql.addSql("   SLS_CNT Date not null,");
            sql.addSql("   SLS_CNT_TO Date not null,");
            sql.addSql("   SLS_CNT_CC Date not null,");
            sql.addSql("   SLS_CNT_BCC Date not null,");
            sql.addSql("   SLS_DATE Date not null,");
            sql.addSql("   SLS_MONTH NUMBER(10,0) not null,");
            sql.addSql("   SLS_EDATE varchar(23) not null");
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
     * <p>Insert SML_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean SML_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_LOG_COUNT_SUM(");
            sql.addSql("   SLS_KBN,");
            sql.addSql("   SLS_SYS_KBN,");
            sql.addSql("   SLS_CNT,");
            sql.addSql("   SLS_CNT_TO,");
            sql.addSql("   SLS_CNT_CC,");
            sql.addSql("   SLS_CNT_BCC,");
            sql.addSql("   SLS_DATE,");
            sql.addSql("   SLS_MONTH,");
            sql.addSql("   SLS_EDATE");
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
            sql.addIntValue(bean.getSlsKbn());
            sql.addIntValue(bean.getSlsSysKbn());
            sql.addLongValue(bean.getSlsCnt());
            sql.addLongValue(bean.getSlsCntTo());
            sql.addLongValue(bean.getSlsCntCc());
            sql.addLongValue(bean.getSlsCntBcc());
            sql.addDateValue(bean.getSlsDate());
            sql.addIntValue(bean.getSlsMonth());
            sql.addDateValue(bean.getSlsEdate());
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
     * <p>Update SML_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean SML_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   SLS_CNT=?,");
            sql.addSql("   SLS_CNT_TO=?,");
            sql.addSql("   SLS_CNT_CC=?,");
            sql.addSql("   SLS_CNT_BCC=?,");
            sql.addSql("   SLS_MONTH=?,");
            sql.addSql("   SLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SLS_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   SLS_SYS_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   SLS_DATE=?");

            sql.addLongValue(bean.getSlsCnt());
            sql.addLongValue(bean.getSlsCntTo());
            sql.addLongValue(bean.getSlsCntCc());
            sql.addLongValue(bean.getSlsCntBcc());
            sql.addIntValue(bean.getSlsMonth());
            sql.addDateValue(bean.getSlsEdate());
            sql.addIntValue(bean.getSlsKbn());
            sql.addIntValue(bean.getSlsSysKbn());
            sql.addDateValue(bean.getSlsDate());

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
     * <p>Select SML_LOG_COUNT_SUM All Data
     * @return List in SML_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLogCountSumModel> ret = new ArrayList<SmlLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLS_KBN,");
            sql.addSql("   SLS_SYS_KBN,");
            sql.addSql("   SLS_CNT,");
            sql.addSql("   SLS_CNT_TO,");
            sql.addSql("   SLS_CNT_CC,");
            sql.addSql("   SLS_CNT_BCC,");
            sql.addSql("   SLS_DATE,");
            sql.addSql("   SLS_MONTH,");
            sql.addSql("   SLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLogCountSumFromRs(rs));
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
    public List<SmlLogCountSumModel> getSumLogCount(UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlLogCountSumModel> logSumList = new ArrayList<SmlLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   cast(SLC_DATE as date) as LOG_DATE,");
            sql.addSql("   count(*) as CNT_SUM,");
            sql.addSql("   sum(SLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("   sum(SLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("   sum(SLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("   max(SLC_DATE) as MAX_EDATE,");
            sql.addSql("   count(*) as SMAIL_COUNT");
            sql.addSql(" from");
            sql.addSql("   SML_LOG_COUNT");
            sql.addSql(" where");
            sql.addSql("   SLC_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   SLC_DATE <= ?");
            sql.addSql(" group by");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
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
                SmlLogCountSumModel model = __getSmlLogSumData(rs);
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param logMdl 集計ログ情報
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public SmlLogCountSumModel getSumLogCount(SmlLogCountModel logMdl, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlLogCountSumModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   cast(SLC_DATE as date) as LOG_DATE,");
            sql.addSql("   count(*) as CNT_SUM,");
            sql.addSql("   sum(SLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("   sum(SLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("   sum(SLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("   max(SLC_DATE) as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_LOG_COUNT");
            sql.addSql(" where");
            sql.addSql("   SLC_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   SLC_SYS_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   SLC_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   SLC_DATE <= ?");
            sql.addSql(" group by");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   LOG_DATE");

            UDate frDate = date.cloneUDate();
            frDate.setZeroHhMmSs();
            UDate toDate = date.cloneUDate();
            toDate.setMaxHhMmSs();
            sql.addIntValue(logMdl.getSlcKbn());
            sql.addIntValue(logMdl.getSlcSysKbn());
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getSmlLogSumData(rs);
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
     * <br>[解  説] 集計結果(SML_LOG_COUNT_SUM)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<SmlLogCountSumModel> getNonRegisteredList(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlLogCountSumModel> logSumList = new ArrayList<SmlLogCountSumModel>();
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
            sql.addSql("   LOG_SUM.SLC_KBN as SLC_KBN,");
            sql.addSql("   LOG_SUM.SLC_SYS_KBN as SLC_SYS_KBN,");
            sql.addSql("   LOG_SUM.LOG_DATE as LOG_DATE,");
            sql.addSql("   LOG_SUM.CNT_SUM as CNT_SUM,");
            sql.addSql("   LOG_SUM.CNT_TO_SUM as CNT_TO_SUM,");
            sql.addSql("   LOG_SUM.CNT_CC_SUM as CNT_CC_SUM,");
            sql.addSql("   LOG_SUM.CNT_BCC_SUM as CNT_BCC_SUM,");
            sql.addSql("   LOG_SUM.MAX_EDATE as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      SLC_KBN,");
            sql.addSql("      SLC_SYS_KBN,");
            sql.addSql("      cast(SLC_DATE as date) as LOG_DATE,");
            sql.addSql("      count(*) as CNT_SUM,");
            sql.addSql("      sum(SLC_CNT_TO) as CNT_TO_SUM,");
            sql.addSql("      sum(SLC_CNT_CC) as CNT_CC_SUM,");
            sql.addSql("      sum(SLC_CNT_BCC) as CNT_BCC_SUM,");
            sql.addSql("      max(SLC_DATE) as MAX_EDATE");
            sql.addSql("    from");
            sql.addSql("      SML_LOG_COUNT");
            sql.addSql("    where ");
            sql.addSql("      SLC_DATE >= ?");
            sql.addDateValue(from);
            sql.addSql("    and");
            sql.addSql("      SLC_DATE <= ?");
            sql.addDateValue(to);
            sql.addSql("    group by");
            sql.addSql("      SLC_KBN,");
            sql.addSql("      SLC_SYS_KBN,");
            sql.addSql("      LOG_DATE");
            sql.addSql("   ) LOG_SUM");
            sql.addSql(" where");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from SML_LOG_COUNT_SUM");
            sql.addSql("     where");
            sql.addSql("      LOG_SUM.SLC_KBN = SML_LOG_COUNT_SUM.SLS_KBN");
            sql.addSql("     and");
            sql.addSql("      LOG_SUM.SLC_SYS_KBN = SML_LOG_COUNT_SUM.SLS_SYS_KBN");
            sql.addSql("     and");
            sql.addSql("      LOG_SUM.LOG_DATE = SML_LOG_COUNT_SUM.SLS_DATE");
            sql.addSql("     and");
            sql.addSql("      LOG_SUM.MAX_EDATE = SML_LOG_COUNT_SUM.SLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE,");
            sql.addSql("   LOG_SUM.SLC_KBN,");
            sql.addSql("   LOG_SUM.SLC_SYS_KBN");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlLogCountSumModel model = __getSmlLogSumData(rs);
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
     * <p>Create SML_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private SmlLogCountSumModel __getSmlLogCountSumFromRs(ResultSet rs) throws SQLException {
        SmlLogCountSumModel bean = new SmlLogCountSumModel();
        bean.setSlsKbn(rs.getInt("SLS_KBN"));
        bean.setSlsSysKbn(rs.getInt("SLS_SYS_KBN"));
        bean.setSlsCnt(rs.getLong("SLS_CNT"));
        bean.setSlsCntTo(rs.getLong("SLS_CNT_TO"));
        bean.setSlsCntCc(rs.getLong("SLS_CNT_CC"));
        bean.setSlsCntBcc(rs.getLong("SLS_CNT_BCC"));
        bean.setSlsDate(UDate.getInstanceTimestamp(rs.getTimestamp("SLS_DATE")));
        bean.setSlsMonth(rs.getInt("SLS_MONTH"));
        bean.setSlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SLS_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] ショートメール集計データの集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return ショートメール集計データの集計結果の集計結果
     * @throws SQLException SQL実行例外
     */
    private SmlLogCountSumModel __getSmlLogSumData(ResultSet rs) throws SQLException {
        SmlLogCountSumModel model = new SmlLogCountSumModel();
        model.setSlsKbn(rs.getInt("SLC_KBN"));
        model.setSlsSysKbn(rs.getInt("SLC_SYS_KBN"));
        model.setSlsCnt(rs.getLong("CNT_SUM"));
        if (model.getSlsKbn() == GSConstSmail.LOG_COUNT_KBN_JMAIL) {
            model.setSlsCntTo(1);
            model.setSlsCntCc(0);
            model.setSlsCntBcc(0);
        } else {
            model.setSlsCntTo(rs.getLong("CNT_TO_SUM"));
            model.setSlsCntCc(rs.getLong("CNT_CC_SUM"));
            model.setSlsCntBcc(rs.getLong("CNT_BCC_SUM"));
        }
        UDate slsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
        model.setSlsDate(slsDate);
        model.setSlsMonth(Integer.parseInt(slsDate.getDateString().substring(0, 6)));
        model.setSlsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_EDATE")));
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
    public void updateLogSum(UDate date)
            throws SQLException {
        List<SmlLogCountSumModel> logSumList = getSumLogCount(date);
        if (logSumList != null && !logSumList.isEmpty()) {
            for (SmlLogCountSumModel logSumMdl : logSumList) {
                if (update(logSumMdl) == 0) {
                    insert(logSumMdl);
                }
            }
        }

    }

}
