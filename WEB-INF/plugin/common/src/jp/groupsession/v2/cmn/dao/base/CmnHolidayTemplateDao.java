package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;

/**
 * <p>CMN_HOLIDAY_TEMPLATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnHolidayTemplateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnHolidayTemplateDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnHolidayTemplateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnHolidayTemplateDao(Connection con) {
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
            sql.addSql("drop table CMN_HOLIDAY_TEMPLATE");

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
            sql.addSql(" create table CMN_HOLIDAY_TEMPLATE (");
            sql.addSql("   HLT_SID NUMBER(4,0) not null,");
            sql.addSql("   HLT_DATE_MONTH NUMBER(4,0) not null,");
            sql.addSql("   HLT_DATE_DAY NUMBER(4,0),");
            sql.addSql("   HLT_NAME varchar(20),");
            sql.addSql("   HLT_EX_MONTH NUMBER(4,0),");
            sql.addSql("   HLT_EX_WEEK_MONTH NUMBER(4,0),");
            sql.addSql("   HLT_EX_DAY_WEEK NUMBER(4,0),");
            sql.addSql("   HLT_EX_FURIKAE NUMBER(4,0),");
            sql.addSql("   HLT_EXFLG NUMBER(4,0) not null,");
            sql.addSql("   HLT_ADUSER NUMBER(4,0) not null,");
            sql.addSql("   HLT_ADDATE varchar(8) not null,");
            sql.addSql("   HLT_UPUSER NUMBER(4,0) not null,");
            sql.addSql("   HLT_UPDATE varchar(8) not null,");
            sql.addSql("   primary key (HLT_SID)");
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
     * <p>Insert CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @param bean CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnHolidayTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_HOLIDAY_TEMPLATE(");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
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
            sql.addIntValue(bean.getHltSid());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());
            sql.addStrValue(bean.getHltName());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());
            sql.addIntValue(bean.getHltExFurikae());
            sql.addIntValue(bean.getHltExflg());
            sql.addIntValue(bean.getHltAduser());
            sql.addDateValue(bean.getHltAddate());
            sql.addIntValue(bean.getHltUpuser());
            sql.addDateValue(bean.getHltUpdate());
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
     * <p>Update CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @param bean CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnHolidayTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   HLT_DATE_MONTH=?,");
            sql.addSql("   HLT_DATE_DAY=?,");
            sql.addSql("   HLT_NAME=?,");
            sql.addSql("   HLT_EX_MONTH=?,");
            sql.addSql("   HLT_EX_WEEK_MONTH=?,");
            sql.addSql("   HLT_EX_DAY_WEEK=?,");
            sql.addSql("   HLT_EX_FURIKAE=?,");
            sql.addSql("   HLT_EXFLG=?,");
            sql.addSql("   HLT_ADUSER=?,");
            sql.addSql("   HLT_ADDATE=?,");
            sql.addSql("   HLT_UPUSER=?,");
            sql.addSql("   HLT_UPDATE=?");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());
            sql.addStrValue(bean.getHltName());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());
            sql.addIntValue(bean.getHltExFurikae());
            sql.addIntValue(bean.getHltExflg());
            sql.addIntValue(bean.getHltAduser());
            sql.addDateValue(bean.getHltAddate());
            sql.addIntValue(bean.getHltUpuser());
            sql.addDateValue(bean.getHltUpdate());
            //where
            sql.addIntValue(bean.getHltSid());

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
     * <p>Update CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @param bean CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int updateCmnTemplate(CmnHolidayTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   HLT_DATE_MONTH=?,");
            sql.addSql("   HLT_DATE_DAY=?,");
            sql.addSql("   HLT_NAME=?,");
            sql.addSql("   HLT_EX_MONTH=?,");
            sql.addSql("   HLT_EX_WEEK_MONTH=?,");
            sql.addSql("   HLT_EX_DAY_WEEK=?,");
            sql.addSql("   HLT_EX_FURIKAE=?,");
            sql.addSql("   HLT_EXFLG=?,");
            sql.addSql("   HLT_UPUSER=?,");
            sql.addSql("   HLT_UPDATE=?");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());
            sql.addStrValue(bean.getHltName());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());
            sql.addIntValue(bean.getHltExFurikae());
            sql.addIntValue(bean.getHltExflg());
            sql.addIntValue(bean.getHltUpuser());
            sql.addDateValue(bean.getHltUpdate());
            //where
            sql.addIntValue(bean.getHltSid());

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
     * <p>Select CMN_HOLIDAY_TEMPLATE All Data
     * @return List in CMN_HOLIDAY_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayTemplateModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnHolidayTemplateModel> ret = new ArrayList<CmnHolidayTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayTemplateFromRs(rs));
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
     * <p>Select CMN_HOLIDAY_TEMPLATE
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return CMN_HOLIDAY_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public CmnHolidayTemplateModel select(CmnHolidayTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnHolidayTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnHolidayTemplateFromRs(rs);
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
     * <p>Select CMN_HOLIDAY_TEMPLATE
     * @param HLT_SID
     * @return CMN_HOLIDAY_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public CmnHolidayTemplateModel select(int hltSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnHolidayTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(hltSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnHolidayTemplateFromRs(rs);
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
     * <p>指定のテンプレート情報を返す
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return CmnHolidayTemplateModel 実行結果
     * @throws SQLException SQL実行例外
     */
    public CmnHolidayTemplateModel isSelectDate(CmnHolidayTemplateModel bean
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnHolidayTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_DATE_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_DATE_DAY=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_WEEK_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_DAY_WEEK=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnHolidayTemplateFromRs(rs);
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
     * <p>パラーメタの指定日付のデータが存在するかの判定を行う
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return boolean true:存在 false:非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExistSelectDate(CmnHolidayTemplateModel bean
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_DATE_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_DATE_DAY=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>パラーメタの指定日付のデータが存在するかの判定を行う
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return boolean true:存在 false:非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExistSelectDateSid(CmnHolidayTemplateModel bean
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_DATE_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_DATE_DAY=?");
            sql.addSql(" and");
            sql.addSql("   HLT_SID <> ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltDateMonth());
            sql.addIntValue(bean.getHltDateDay());
            sql.addIntValue(bean.getHltSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>パラーメタの指定拡張日付日付のデータが存在するかの判定を行う
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return boolean true:存在 false:非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExistSelectDateEx(CmnHolidayTemplateModel bean
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_EX_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_WEEK_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_DAY_WEEK=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>パラーメタの指定拡張日付日付のデータが存在するかの判定を行う
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return boolean true:存在 false:非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExistSelectDateExSid(CmnHolidayTemplateModel bean
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_EX_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_WEEK_MONTH=?");
            sql.addSql(" and");
            sql.addSql("   HLT_EX_DAY_WEEK=?");
            sql.addSql(" and");
            sql.addSql("   HLT_SID <> ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltExMonth());
            sql.addIntValue(bean.getHltExWeekMonth());
            sql.addIntValue(bean.getHltExDayWeek());
            sql.addIntValue(bean.getHltSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>休日テンプレート取得
     * @return List in CMN_HOLIDAY_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayTemplateModel> selectSortedHoliday() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnHolidayTemplateModel> ret = new ArrayList<CmnHolidayTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" order by ");
            sql.addSql("   (HLT_DATE_MONTH + HLT_EX_MONTH) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayTemplateFromRs(rs));
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
     * <p>休日テンプレート取得
     * @param  hltSids テンプレートSID配列
     * @return List in CMN_HOLIDAY_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnHolidayTemplateModel> selectSortedHoliday(int[] hltSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnHolidayTemplateModel> ret = new ArrayList<CmnHolidayTemplateModel>();
        con = getCon();

        // IN条件作成
        StringBuilder bufHltSid = new StringBuilder();
        //1件目
        bufHltSid.append(Integer.toString(hltSids[0]));
        for (int i = 1; i < hltSids.length; i++) {
            //2件目移行
            bufHltSid.append(",");
            bufHltSid.append(Integer.toString(hltSids[i]));
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   HLT_SID,");
            sql.addSql("   HLT_DATE_MONTH,");
            sql.addSql("   HLT_DATE_DAY,");
            sql.addSql("   HLT_NAME,");
            sql.addSql("   HLT_EX_MONTH,");
            sql.addSql("   HLT_EX_WEEK_MONTH,");
            sql.addSql("   HLT_EX_DAY_WEEK,");
            sql.addSql("   HLT_EX_FURIKAE,");
            sql.addSql("   HLT_EXFLG,");
            sql.addSql("   HLT_ADUSER,");
            sql.addSql("   HLT_ADDATE,");
            sql.addSql("   HLT_UPUSER,");
            sql.addSql("   HLT_UPDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID in (" + bufHltSid.toString() + ")");
            sql.addSql(" order by ");
            sql.addSql("   (HLT_DATE_MONTH + HLT_EX_MONTH) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnHolidayTemplateFromRs(rs));
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
     *
     * <br>[機  能]休日テンプレートのSIDの最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     * @return 休日テンプレート最大SID
     */
    public int getMaxSid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnHolidayTemplateModel ret = new CmnHolidayTemplateModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(HLT_SID) as HLT_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret.setHltSid(rs.getInt("HLT_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.getHltSid();
    }

    /**
     * <p>Delete CMN_HOLIDAY_TEMPLATE
     * @param bean CMN_HOLIDAY_TEMPLATE Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public  int delete(CmnHolidayTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getHltSid());

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
     * <p>CMN_HOLIDAY_TEMPLATEを全て削除します
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public  int deleteAllTemplate() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");

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
     * <p>ホリデーテンプレートを削除します
     * @param hltSids 削除対象テンプレート
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public  int delete(int[] hltSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        // IN条件作成
        StringBuilder bufHltSid = new StringBuilder();
        //1件目
        log__.debug("■hltSids.length :" + hltSids.length);
        bufHltSid.append(Integer.toString(hltSids[0]));
        for (int i = 1; i < hltSids.length; i++) {
            //2件目移行
            bufHltSid.append(",");
            bufHltSid.append(Integer.toString(hltSids[i]));
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_HOLIDAY_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   HLT_SID in (" + bufHltSid.toString() + ")");

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
     * <p>Create CMN_HOLIDAY_TEMPLATE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnHolidayTemplateModel
     * @throws SQLException SQL実行例外
     */
    private CmnHolidayTemplateModel __getCmnHolidayTemplateFromRs(ResultSet rs)
    throws SQLException {
        CmnHolidayTemplateModel bean = new CmnHolidayTemplateModel();
        bean.setHltSid(rs.getInt("HLT_SID"));
        bean.setHltDateMonth(rs.getInt("HLT_DATE_MONTH"));
        bean.setHltDateDay(rs.getInt("HLT_DATE_DAY"));
        bean.setHltName(rs.getString("HLT_NAME"));
        bean.setHltExMonth(rs.getInt("HLT_EX_MONTH"));
        bean.setHltExWeekMonth(rs.getInt("HLT_EX_WEEK_MONTH"));
        bean.setHltExDayWeek(rs.getInt("HLT_EX_DAY_WEEK"));
        bean.setHltExFurikae(rs.getInt("HLT_EX_FURIKAE"));
        bean.setHltExflg(rs.getInt("HLT_EXFLG"));
        bean.setHltAduser(rs.getInt("HLT_ADUSER"));
        bean.setHltAddate(UDate.getInstanceTimestamp(rs.getTimestamp("HLT_ADDATE")));
        bean.setHltUpuser(rs.getInt("HLT_UPUSER"));
        bean.setHltUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("HLT_UPDATE")));
        return bean;
    }
}
