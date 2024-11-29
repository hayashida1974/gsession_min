package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_HOLIDAY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnHolidayDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnHolidayDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnHolidayDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnHolidayDao(Connection con) {
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
            sql.addSql("drop table CMN_HOLIDAY");

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
            sql.addSql(" create table CMN_HOLIDAY (");
            sql.addSql("   HOL_DATE Date not null,");
            sql.addSql("   HOL_YEAR NUMBER(4,0) not null,");
            sql.addSql("   HOL_NAME varchar(20),");
            sql.addSql("   HOL_EX_MONTH NUMBER(4,0),");
            sql.addSql("   HOL_EX_WEEK_MONTH NUMBER(4,0),");
            sql.addSql("   HOL_EX_DAY_WEEK NUMBER(4,0),");
            sql.addSql("   HOL_EX_FURIKAE NUMBER(4,0),");
            sql.addSql("   HOL_KBN NUMBER(4,0),");
            sql.addSql("   HOL_ADUSER NUMBER(4,0) not null,");
            sql.addSql("   HOL_ADDATE varchar(8) not null,");
            sql.addSql("   HOL_UPUSER NUMBER(4,0) not null,");
            sql.addSql("   HOL_UPDATE varchar(8) not null,");
            sql.addSql("   primary key (HOL_DATE)");
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
     * <p>Insert CMN_HOLIDAY Data Bindding JavaBean
     * @param bean CMN_HOLIDAY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnHolidayModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_HOLIDAY(");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addValue(bean.getHolDate().getDateStringForSql());
            sql.addDateValue(bean.getHolDate());
            sql.addIntValue(bean.getHolYear());
            sql.addStrValue(bean.getHolName());
            sql.addIntValue(bean.getHolExMonth());
            sql.addIntValue(bean.getHolExWeekMonth());
            sql.addIntValue(bean.getHolExDayWeek());
            sql.addIntValue(bean.getHolExFurikae());
            sql.addIntValue(bean.getHolKbn());
            sql.addIntValue(bean.getHolTcd());
            sql.addIntValue(bean.getHolAduser());
            sql.addDateValue(bean.getHolAddate());
            sql.addIntValue(bean.getHolUpuser());
            sql.addDateValue(bean.getHolUpdate());
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
     * 休日情報を登録する
     * @param bean CMN_HOLIDAY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertHoliday(CmnHolidayModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_HOLIDAY(");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");

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
            sql.addDateValue(bean.getHolDate());
            sql.addIntValue(bean.getHolYear());
            sql.addStrValue(bean.getHolName());
            sql.addIntValue(0);
            sql.addIntValue(1);
            sql.addIntValue(bean.getHolAduser());
            sql.addDateValue(bean.getHolAddate());
            sql.addIntValue(bean.getHolUpuser());
            sql.addDateValue(bean.getHolUpdate());

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
     * <p>Update CMN_HOLIDAY Data Bindding JavaBean
     * @param bean CMN_HOLIDAY Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnHolidayModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" set ");
            sql.addSql("   HOL_YEAR=?,");
            sql.addSql("   HOL_NAME=?,");
            sql.addSql("   HOL_EX_MONTH=?,");
            sql.addSql("   HOL_EX_WEEK_MONTH=?,");
            sql.addSql("   HOL_EX_DAY_WEEK=?,");
            sql.addSql("   HOL_EX_FURIKAE=?,");
            sql.addSql("   HOL_KBN=?,");
            sql.addSql("   HOL_ADUSER=?,");
            sql.addSql("   HOL_ADDATE=?,");
            sql.addSql("   HOL_UPUSER=?,");
            sql.addSql("   HOL_UPDATE=?");
            sql.addSql(" where ");
            sql.addSql("   HOL_DATE=cast(? as date)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHolYear());
            sql.addStrValue(bean.getHolName());
            sql.addIntValue(bean.getHolExMonth());
            sql.addIntValue(bean.getHolExWeekMonth());
            sql.addIntValue(bean.getHolExDayWeek());
            sql.addIntValue(bean.getHolExFurikae());
            sql.addIntValue(bean.getHolKbn());
            sql.addIntValue(bean.getHolAduser());
            sql.addDateValue(bean.getHolAddate());
            sql.addIntValue(bean.getHolUpuser());
            sql.addDateValue(bean.getHolUpdate());
            //where
            sql.addStrValue(bean.getHolDate().getDateStringForSql());

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
     * 休日情報を更新する
     * @param bean CMN_HOLIDAY Data Bindding JavaBean
     * @param targetHolDate 更新対象の休日
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateHoliday(CmnHolidayModel bean, UDate targetHolDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" set ");
            sql.addSql("   HOL_DATE=?,");
            sql.addSql("   HOL_NAME=?,");
            sql.addSql("   HOL_UPUSER=?,");
            sql.addSql("   HOL_UPDATE=?");
            sql.addSql(" where ");
            sql.addSql("   HOL_DATE=cast(? as date)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getHolDate());
            sql.addStrValue(bean.getHolName());
            sql.addIntValue(bean.getHolUpuser());
            sql.addDateValue(bean.getHolUpdate());
            //where
            sql.addStrValue(targetHolDate.getDateStringForSql());

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
     * タイムカードでの休日情報を更新する
     * @param year 年
     * @param kbn ステータス 1=休日 0=休日から除外
     * @param target 更新対象の休日のリスト
     * @param usrSid 更新者SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateTcdHoliday(int year, int kbn, ArrayList<UDate> target, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" set ");
            sql.addSql("   HOL_TCD=?,");
            sql.addSql("   HOL_UPUSER=?,");
            sql.addSql("   HOL_UPDATE=?");
            sql.addIntValue(kbn);
            sql.addIntValue(usrSid);
            sql.addDateValue(new UDate());
            sql.addSql(" where ");
            sql.addSql("   HOL_YEAR=?");
            sql.addIntValue(year);
            if (target != null) {
                sql.addSql(" and ");
                sql.addSql("   HOL_DATE in(");
                for (int i = 0; i < target.size(); i++) {
                    UDate hol = target.get(i);
                    if (i == 0) {
                        sql.addSql("    cast(? as date)");
                    } else {
                        sql.addSql("   ,cast(? as date)");
                    }
                    sql.addStrValue(hol.getDateStringForSql());
                }
                sql.addSql("   )");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select CMN_HOLIDAY All Data
     * @return List in CMN_HOLIDAYModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnHolidayModel> ret = new ArrayList<CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayFromRs(rs));
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
     * <p>Select CMN_HOLIDAY
     * @param bean CMN_HOLIDAY Model
     * @return CMN_HOLIDAYModel
     * @throws SQLException SQL実行例外
     */
    public CmnHolidayModel select(CmnHolidayModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnHolidayModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where ");
            sql.addSql("   HOL_DATE=cast(? as date)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getHolDate().getDateStringForSql());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCmnHolidayFromRs(rs);
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
     * <p>Delete CMN_HOLIDAY
     * @param bean CMN_HOLIDAY Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public  int delete(CmnHolidayModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where ");
            sql.addSql("   HOL_DATE=cast(? as date)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getHolDate().getDateStringForSql());

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
     * 指定された日付の休日を削除する
     * @param dateList 削除対象の日付
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteHoliday(List < UDate > dateList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where ");
            if (dateList.size() == 1) {
                sql.addSql("   HOL_DATE = cast(? as date)");
                sql.addStrValue(dateList.get(0).getDateStringForSql());
            } else {
                sql.addSql("   HOL_DATE in (");

                for (int index = 0; index < dateList.size() - 1; index++) {
                    sql.addSql("     cast(? as date),");
                    sql.addStrValue(dateList.get(index).getDateStringForSql());
                }
                sql.addSql("     cast(? as date)");
                sql.addStrValue(dateList.get(dateList.size() - 1).getDateStringForSql());
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
     * 指定された年度の休日情報一覧を取得する
     * @param year 年度
     * @return List in CMN_HOLIDAY Model
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayModel> getHoliDayList(int year) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnHolidayModel> ret = new ArrayList<CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where");
            sql.addSql("   HOL_YEAR = ?");
            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(year);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayFromRs(rs));
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
     * 指定された日付の休日情報一覧を取得する
     * @param dateList 日付一覧
     * @return List in CMN_HOLIDAY Model
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayModel> getHoliDayList(List < UDate > dateList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnHolidayModel> ret = new ArrayList<CmnHolidayModel>();
        con = getCon();

        if (dateList == null || dateList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where");
            if (dateList.size() == 1) {
                sql.addSql("   HOL_DATE = cast(? as date)");
                sql.addStrValue(dateList.get(0).getDateStringForSql());
            } else {
                sql.addSql("   HOL_DATE in (");

                for (int index = 0; index < dateList.size() - 1; index++) {
                    sql.addSql("     cast(? as date),");
                    sql.addStrValue(dateList.get(index).getDateStringForSql());
                }
                sql.addSql("     cast(? as date)");
                sql.addStrValue(dateList.get(dateList.size() - 1).getDateStringForSql());
                sql.addSql("   )");
            }

            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayFromRs(rs));
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
     * 指定された日付間の休日情報一覧を取得する
     * @param from 開始日付
     * @param to 終了日付
     * @return HashMap < String, CmnHolidayModel >
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, CmnHolidayModel> getHoliDayList(UDate from, UDate to)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, CmnHolidayModel> ret = new HashMap<String, CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where");
            sql.addSql("   HOL_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   HOL_DATE <= ?");
            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            UDate holFrom = from.cloneUDate();
            holFrom.setZeroHhMmSs();
            sql.addDateValue(holFrom);
            UDate holTo = to.cloneUDate();
            holTo.setMaxHhMmSs();
            sql.addDateValue(holTo);
            log__.info(sql.toLogString());


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            UDate holDate = null;
            while (rs.next()) {
                holDate = UDate.getInstanceTimestamp(rs.getTimestamp("HOL_DATE"));
                ret.put(holDate.getDateString(), __getCmnHolidayFromRs(rs));
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
     * すべての休日情報一覧を取得する
     * @return HashMap < String, CmnHolidayModel >
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, CmnHolidayModel> getHoliDayList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, CmnHolidayModel> ret = new HashMap<String, CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            UDate holDate = null;
            while (rs.next()) {
                holDate = UDate.getInstanceTimestamp(rs.getTimestamp("HOL_DATE"));
                ret.put(holDate.getDateString(), __getCmnHolidayFromRs(rs));
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
     * タイムカードで休日として扱う指定された日付間の休日情報一覧を取得する
     * @param from 開始日付
     * @param to 終了日付
     * @return HashMap < String, CmnHolidayModel >
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, CmnHolidayModel> getHoliDayListFotTcd(UDate from, UDate to)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, CmnHolidayModel> ret = new HashMap<String, CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where");
            sql.addSql("   HOL_DATE between cast(? as date)");
            sql.addSql(" and");
            sql.addSql("    cast(? as date)");
            sql.addSql(" and");
            sql.addSql("   HOL_TCD=?");
            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addStrValue(from.getDateStringForSql());
            sql.addStrValue(to.getDateStringForSql());
            sql.addIntValue(1);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            UDate holDate = null;
            while (rs.next()) {
                holDate = UDate.getInstanceTimestamp(rs.getTimestamp("HOL_DATE"));
                ret.put(holDate.getDateString(), __getCmnHolidayFromRs(rs));
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
     * タイムカードで休日として扱う指定された日付間の休日情報一覧を取得する
     * @return HashMap < String, CmnHolidayModel >
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, CmnHolidayModel> getHoliDayListFotTcd()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, CmnHolidayModel> ret = new HashMap<String, CmnHolidayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HOL_DATE,");
            sql.addSql("   HOL_YEAR,");
            sql.addSql("   HOL_NAME,");
            sql.addSql("   HOL_EX_MONTH,");
            sql.addSql("   HOL_EX_WEEK_MONTH,");
            sql.addSql("   HOL_EX_DAY_WEEK,");
            sql.addSql("   HOL_EX_FURIKAE,");
            sql.addSql("   HOL_KBN,");
            sql.addSql("   HOL_TCD,");
            sql.addSql("   HOL_ADUSER,");
            sql.addSql("   HOL_ADDATE,");
            sql.addSql("   HOL_UPUSER,");
            sql.addSql("   HOL_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY");
            sql.addSql(" where");
            sql.addSql("   HOL_TCD=?");
            sql.addSql(" order by");
            sql.addSql("   HOL_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(1);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            UDate holDate = null;
            while (rs.next()) {
                holDate = UDate.getInstanceTimestamp(rs.getTimestamp("HOL_DATE"));
                ret.put(holDate.getDateString(), __getCmnHolidayFromRs(rs));
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
     * <p>Create CMN_HOLIDAY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnHolidayModel
     * @throws SQLException SQL実行例外
     */
    private CmnHolidayModel __getCmnHolidayFromRs(ResultSet rs) throws SQLException {
        CmnHolidayModel bean = new CmnHolidayModel();
        bean.setHolDate(UDate.getInstanceTimestamp(rs.getTimestamp("HOL_DATE")));
        bean.setHolYear(rs.getInt("HOL_YEAR"));
        bean.setHolName(rs.getString("HOL_NAME"));
        bean.setHolExMonth(rs.getInt("HOL_EX_MONTH"));
        bean.setHolExWeekMonth(rs.getInt("HOL_EX_WEEK_MONTH"));
        bean.setHolExDayWeek(rs.getInt("HOL_EX_DAY_WEEK"));
        bean.setHolExFurikae(rs.getInt("HOL_EX_FURIKAE"));
        bean.setHolKbn(rs.getInt("HOL_KBN"));
        bean.setHolTcd(rs.getInt("HOL_TCD"));
        bean.setHolAduser(rs.getInt("HOL_ADUSER"));
        bean.setHolAddate(UDate.getInstanceTimestamp(rs.getTimestamp("HOL_ADDATE")));
        bean.setHolUpuser(rs.getInt("HOL_UPUSER"));
        bean.setHolUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("HOL_UPDATE")));
        return bean;
    }

    /**
     * <p>指定したユーザーＩＤからユーザーSIDを取得する
     * @param loginId ログインID
     * @throws SQLException SQL実行例外
     * @return ret ユーザーSID
     */
    public int getUserSid(String loginId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_LGID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_JKBN<>?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(loginId);
            sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("USR_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

}
