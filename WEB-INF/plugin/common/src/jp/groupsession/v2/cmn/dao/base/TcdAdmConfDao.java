package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;

/**
 * <p>TCD_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdAdmConfDao(Connection con) {
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
            sql.addSql("drop table TCD_ADM_CONF");

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
            sql.addSql(" create table TCD_ADM_CONF (");
            sql.addSql("   TAC_INTERVAL NUMBER(10,0) not null,");
            sql.addSql("   TAC_KANSAN NUMBER(10,0) not null,");
            sql.addSql("   TAC_SIMEBI NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_SUN NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_MON NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_TUE NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_WED NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_THU NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_FRI NUMBER(10,0) not null,");
            sql.addSql("   TAC_HOL_SAT NUMBER(10,0) not null,");
            sql.addSql("   TAC_LOCK_FLG NUMBER(10,0) not null,");
            sql.addSql("   TAC_LOCK_STRIKE NUMBER(10,0) not null,");
            sql.addSql("   TAC_LOCK_BIKO NUMBER(10,0) not null,");
            sql.addSql("   TAC_LOCK_LATE NUMBER(10,0) not null,");
            sql.addSql("   TAC_LOCK_HOLIDAY NUMBER(10,0) not null,");
            sql.addSql("   TAC_AUID NUMBER(10,0) not null,");
            sql.addSql("   TAC_ADATE varchar(26) not null,");
            sql.addSql("   TAC_EUID NUMBER(10,0) not null,");
            sql.addSql("   TAC_EDATE varchar(26) not null");
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
     * <p>Insert TCD_ADM_CONF Data Bindding JavaBean
     * @param bean TCD_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_ADM_CONF(");
            sql.addSql("   TAC_INTERVAL,");
            sql.addSql("   TAC_KANSAN,");
            sql.addSql("   TAC_SIMEBI,");
            sql.addSql("   TAC_HOL_SUN,");
            sql.addSql("   TAC_HOL_MON,");
            sql.addSql("   TAC_HOL_TUE,");
            sql.addSql("   TAC_HOL_WED,");
            sql.addSql("   TAC_HOL_THU,");
            sql.addSql("   TAC_HOL_FRI,");
            sql.addSql("   TAC_HOL_SAT,");
            sql.addSql("   TAC_LOCK_FLG,");
            sql.addSql("   TAC_LOCK_STRIKE,");
            sql.addSql("   TAC_LOCK_BIKO,");
            sql.addSql("   TAC_LOCK_LATE,");
            sql.addSql("   TAC_LOCK_HOLIDAY,");
            sql.addSql("   TAC_LOCK_TIMEZONE,");
            sql.addSql("   TAC_DEF_TIMEZONE,");
            sql.addSql("   TAC_WORKREPORT_KBN,");
            sql.addSql("   TAC_WORKREPORT_SID,");
            sql.addSql("   TAC_AUID,");
            sql.addSql("   TAC_ADATE,");
            sql.addSql("   TAC_EUID,");
            sql.addSql("   TAC_EDATE");
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
            sql.addIntValue(bean.getTacInterval());
            sql.addIntValue(bean.getTacKansan());
            sql.addIntValue(bean.getTacSimebi());
            sql.addIntValue(bean.getTacHolSun());
            sql.addIntValue(bean.getTacHolMon());
            sql.addIntValue(bean.getTacHolTue());
            sql.addIntValue(bean.getTacHolWed());
            sql.addIntValue(bean.getTacHolThu());
            sql.addIntValue(bean.getTacHolFri());
            sql.addIntValue(bean.getTacHolSat());
            sql.addIntValue(bean.getTacLockFlg());
            sql.addIntValue(bean.getTacLockStrike());
            sql.addIntValue(bean.getTacLockBiko());
            sql.addIntValue(bean.getTacLockLate());
            sql.addIntValue(bean.getTacLockHoliday());
            sql.addIntValue(bean.getTacLockTimezone());
            sql.addIntValue(bean.getTacDefTimezone());
            sql.addIntValue(bean.getTacWorkreportKbn());
            sql.addLongValue(bean.getTacWorkreportSid());
            sql.addIntValue(bean.getTacAuid());
            sql.addDateValue(bean.getTacAdate());
            sql.addIntValue(bean.getTacEuid());
            sql.addDateValue(bean.getTacEdate());
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
     * <p>Update TCD_ADM_CONF Data Bindding JavaBean
     * @param bean TCD_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int update(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   TAC_INTERVAL=?,");
            sql.addSql("   TAC_KANSAN=?,");
            sql.addSql("   TAC_SIMEBI=?,");
            sql.addSql("   TAC_HOL_SUN=?,");
            sql.addSql("   TAC_HOL_MON=?,");
            sql.addSql("   TAC_HOL_TUE=?,");
            sql.addSql("   TAC_HOL_WED=?,");
            sql.addSql("   TAC_HOL_THU=?,");
            sql.addSql("   TAC_HOL_FRI=?,");
            sql.addSql("   TAC_HOL_SAT=?,");
            sql.addSql("   TAC_LOCK_FLG=?,");
            sql.addSql("   TAC_LOCK_STRIKE=?,");
            sql.addSql("   TAC_LOCK_BIKO=?,");
            sql.addSql("   TAC_LOCK_LATE=?,");
            sql.addSql("   TAC_LOCK_HOLIDAY=?,");
            sql.addSql("   TAC_AUID=?,");
            sql.addSql("   TAC_ADATE=?,");
            sql.addSql("   TAC_EUID=?,");
            sql.addSql("   TAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTacInterval());
            sql.addIntValue(bean.getTacKansan());
            sql.addIntValue(bean.getTacSimebi());
            sql.addIntValue(bean.getTacHolSun());
            sql.addIntValue(bean.getTacHolMon());
            sql.addIntValue(bean.getTacHolTue());
            sql.addIntValue(bean.getTacHolWed());
            sql.addIntValue(bean.getTacHolThu());
            sql.addIntValue(bean.getTacHolFri());
            sql.addIntValue(bean.getTacHolSat());
            sql.addIntValue(bean.getTacLockFlg());
            sql.addIntValue(bean.getTacLockStrike());
            sql.addIntValue(bean.getTacLockBiko());
            sql.addIntValue(bean.getTacLockLate());
            sql.addIntValue(bean.getTacLockHoliday());
            sql.addIntValue(bean.getTacAuid());
            sql.addDateValue(bean.getTacAdate());
            sql.addIntValue(bean.getTacEuid());
            sql.addDateValue(bean.getTacEdate());

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
     * <p>基本設定の更新を行う
     * @param bean TCD_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int updateBase(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   TAC_INTERVAL=?,");
            sql.addSql("   TAC_KANSAN=?,");
            sql.addSql("   TAC_SIMEBI=?,");
            sql.addSql("   TAC_HOL_SUN=?,");
            sql.addSql("   TAC_HOL_MON=?,");
            sql.addSql("   TAC_HOL_TUE=?,");
            sql.addSql("   TAC_HOL_WED=?,");
            sql.addSql("   TAC_HOL_THU=?,");
            sql.addSql("   TAC_HOL_FRI=?,");
            sql.addSql("   TAC_HOL_SAT=?,");
            sql.addSql("   TAC_DEF_TIMEZONE=?,");
            sql.addSql("   TAC_EUID=?,");
            sql.addSql("   TAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTacInterval());
            sql.addIntValue(bean.getTacKansan());
            sql.addIntValue(bean.getTacSimebi());
            sql.addIntValue(bean.getTacHolSun());
            sql.addIntValue(bean.getTacHolMon());
            sql.addIntValue(bean.getTacHolTue());
            sql.addIntValue(bean.getTacHolWed());
            sql.addIntValue(bean.getTacHolThu());
            sql.addIntValue(bean.getTacHolFri());
            sql.addIntValue(bean.getTacHolSat());
            sql.addIntValue(bean.getTacDefTimezone());
            sql.addIntValue(bean.getTacEuid());
            sql.addDateValue(bean.getTacEdate());

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
     * <p>タイムカード編集権限の更新を行う
     * @param bean TCD_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int updateLockInf(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   TAC_LOCK_FLG=?,");
            sql.addSql("   TAC_LOCK_STRIKE=?,");
            sql.addSql("   TAC_LOCK_BIKO=?,");
            sql.addSql("   TAC_LOCK_LATE=?,");
            sql.addSql("   TAC_LOCK_HOLIDAY=?,");
            sql.addSql("   TAC_LOCK_TIMEZONE=?,");
            sql.addSql("   TAC_EUID=?,");
            sql.addSql("   TAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTacLockFlg());
            sql.addIntValue(bean.getTacLockStrike());
            sql.addIntValue(bean.getTacLockBiko());
            sql.addIntValue(bean.getTacLockLate());
            sql.addIntValue(bean.getTacLockHoliday());
            sql.addIntValue(bean.getTacLockTimezone());
            sql.addIntValue(bean.getTacEuid());
            sql.addDateValue(bean.getTacEdate());

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
     * <p>タイムカード勤務表の更新を行う
     * @param bean TCD_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int updateWorkReport(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   TAC_WORKREPORT_KBN=?,");
            sql.addSql("   TAC_WORKREPORT_SID=?,");
            sql.addSql("   TAC_EUID=?,");
            sql.addSql("   TAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTacWorkreportKbn());
            sql.addLongValue(bean.getTacWorkreportSid());
            sql.addIntValue(bean.getTacEuid());
            sql.addDateValue(bean.getTacEdate());

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
     * <p>Select TCD_ADM_CONF All Data
     * @return List in TCD_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public TcdAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TAC_INTERVAL,");
            sql.addSql("   TAC_KANSAN,");
            sql.addSql("   TAC_SIMEBI,");
            sql.addSql("   TAC_HOL_SUN,");
            sql.addSql("   TAC_HOL_MON,");
            sql.addSql("   TAC_HOL_TUE,");
            sql.addSql("   TAC_HOL_WED,");
            sql.addSql("   TAC_HOL_THU,");
            sql.addSql("   TAC_HOL_FRI,");
            sql.addSql("   TAC_HOL_SAT,");
            sql.addSql("   TAC_LOCK_FLG,");
            sql.addSql("   TAC_LOCK_STRIKE,");
            sql.addSql("   TAC_LOCK_BIKO,");
            sql.addSql("   TAC_LOCK_LATE,");
            sql.addSql("   TAC_LOCK_HOLIDAY,");
            sql.addSql("   TAC_LOCK_TIMEZONE,");
            sql.addSql("   TAC_DEF_TIMEZONE,");
            sql.addSql("   TAC_WORKREPORT_KBN,");
            sql.addSql("   TAC_WORKREPORT_SID,");
            sql.addSql("   TAC_AUID,");
            sql.addSql("   TAC_ADATE,");
            sql.addSql("   TAC_EUID,");
            sql.addSql("   TAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdAdmConfFromRs(rs);
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
     * <p>Delete TCD_ADM_CONF
     * @param bean TCD_ADM_CONF Model
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(TcdAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_ADM_CONF");

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
     * <p>Create TCD_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private TcdAdmConfModel __getTcdAdmConfFromRs(ResultSet rs) throws SQLException {
        TcdAdmConfModel bean = new TcdAdmConfModel();
        bean.setTacInterval(rs.getInt("TAC_INTERVAL"));
        bean.setTacKansan(rs.getInt("TAC_KANSAN"));
        bean.setTacSimebi(rs.getInt("TAC_SIMEBI"));
        bean.setTacHolSun(rs.getInt("TAC_HOL_SUN"));
        bean.setTacHolMon(rs.getInt("TAC_HOL_MON"));
        bean.setTacHolTue(rs.getInt("TAC_HOL_TUE"));
        bean.setTacHolWed(rs.getInt("TAC_HOL_WED"));
        bean.setTacHolThu(rs.getInt("TAC_HOL_THU"));
        bean.setTacHolFri(rs.getInt("TAC_HOL_FRI"));
        bean.setTacHolSat(rs.getInt("TAC_HOL_SAT"));
        bean.setTacLockFlg(rs.getInt("TAC_LOCK_FLG"));
        bean.setTacLockStrike(rs.getInt("TAC_LOCK_STRIKE"));
        bean.setTacLockBiko(rs.getInt("TAC_LOCK_BIKO"));
        bean.setTacLockLate(rs.getInt("TAC_LOCK_LATE"));
        bean.setTacLockHoliday(rs.getInt("TAC_LOCK_HOLIDAY"));
        bean.setTacLockTimezone(rs.getInt("TAC_LOCK_TIMEZONE"));
        bean.setTacDefTimezone(rs.getInt("TAC_DEF_TIMEZONE"));
        bean.setTacWorkreportKbn(rs.getInt("TAC_WORKREPORT_KBN"));
        bean.setTacWorkreportSid(rs.getLong("TAC_WORKREPORT_SID"));
        bean.setTacAuid(rs.getInt("TAC_AUID"));
        bean.setTacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TAC_ADATE")));
        bean.setTacEuid(rs.getInt("TAC_EUID"));
        bean.setTacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TAC_EDATE")));
        return bean;
    }
}
