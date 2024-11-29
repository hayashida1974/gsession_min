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
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnUsrmCountModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>CMN_USRM_COUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrmCountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrmCountDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrmCountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrmCountDao(Connection con) {
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
            sql.addSql("drop table CMN_USRM_COUNT");

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
            sql.addSql(" create table CMN_USRM_COUNT (");
            sql.addSql("   CUC_DATE Date not null,");
            sql.addSql("   CUC_MONTH NUMBER(10,0) not null,");
            sql.addSql("   CUC_CNT bigint not null,");
            sql.addSql("   CUC_LOGIN bigint not null,");
            sql.addSql("   CUC_USER bigint not null");
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
     * <p>Insert CMN_USRM_COUNT Data Bindding JavaBean
     * @param bean CMN_USRM_COUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrmCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USRM_COUNT(");
            sql.addSql("   CUC_DATE,");
            sql.addSql("   CUC_MONTH,");
            sql.addSql("   CUC_CNT,");
            sql.addSql("   CUC_LOGIN,");
            sql.addSql("   CUC_USER");
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
            sql.addDateValue(bean.getCucDate());
            sql.addIntValue(bean.getCucMonth());
            sql.addLongValue(bean.getCucCnt());
            sql.addLongValue(bean.getCucLogin());
            sql.addLongValue(bean.getCucUser());
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
     * <p>Update CMN_USRM_COUNT Data Bindding JavaBean
     * @param bean CMN_USRM_COUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrmCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_COUNT");
            sql.addSql(" set ");
            sql.addSql("   CUC_MONTH=?,");
            sql.addSql("   CUC_CNT=?,");
            sql.addSql("   CUC_LOGIN=?,");
            sql.addSql("   CUC_USER=?");
            sql.addSql(" where ");
            sql.addSql("   CUC_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCucMonth());
            sql.addLongValue(bean.getCucCnt());
            sql.addLongValue(bean.getCucLogin());
            sql.addLongValue(bean.getCucUser());
            sql.addDateValue(bean.getCucDate());

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
     * <p>Select CMN_USRM_COUNT All Data
     * @return List in CMN_USRM_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmCountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmCountModel> ret = new ArrayList<CmnUsrmCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUC_DATE,");
            sql.addSql("   CUC_MONTH,");
            sql.addSql("   CUC_CNT,");
            sql.addSql("   CUC_LOGIN,");
            sql.addSql("   CUC_USER");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrmCountFromRs(rs));
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
     * <br>[機  能] 指定した日付のユーザ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @return ユーザ件数
     * @throws SQLException SQL実行時例外
     */
    public CmnUsrmCountModel getUserCount(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmCountModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUC_DATE,");
            sql.addSql("   CUC_MONTH,");
            sql.addSql("   CUC_CNT,");
            sql.addSql("   CUC_LOGIN,");
            sql.addSql("   CUC_USER");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT");
            sql.addSql(" where ");
            sql.addSql("   CUC_DATE = ?");
            sql.addDateValue(date);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getCmnUsrmCountFromRs(rs);
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
     * <br>[機  能] ユーザ件数の更新を行う
     * <br>[解  説] ログイン履歴を集計して取得したログインユーザ数やログイン回数を"ユーザ数"へ登録する
     * <br>[備  考]
     * @param date 日付
     * @throws SQLException SQL実行時例外
     */
    public void updateUserCount(UDate date) throws SQLException {
        updateUserCount(date, false);
    }
    /**
     * <br>[機  能] ユーザ件数の更新を行う
     * <br>[解  説] ログイン履歴を集計して取得したログインユーザ数やログイン回数を"ユーザ数"へ登録する
     * <br>[備  考]
     * @param date 日付
     * @param useOldUserCnt 過去日のユーザ数を使用
     * @throws SQLException SQL実行時例外
     */
    public void updateUserCount(UDate date, boolean useOldUserCnt) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long userCount = 0;
        long loginNum = 0;
        long loginUserNum = 0;
        UDate dateZeroHhMmSs = date.cloneUDate();
        dateZeroHhMmSs.setZeroHhMmSs();
        UDate dateMaxHhMmSs = date.cloneUDate();
        dateMaxHhMmSs.setMaxHhMmSs();
        //ユーザ件数の取得
        CmnUsrmDao usrDao = new CmnUsrmDao(con);
        if (useOldUserCnt && !UDateUtil.isRange(dateZeroHhMmSs, dateMaxHhMmSs)) {
            CmnUsrmCountModel countMdl = getPrevUsrmCount(date);
            if (countMdl != null) {
                userCount = countMdl.getCucCnt();
            } else {
                userCount = usrDao.getActiveUserCount();
            }
        } else {
            userCount = usrDao.getActiveUserCount();
        }

        //ログイン回数とログインユーザ数の取得（削除ユーザを除く）
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as LOGIN,");
            sql.addSql("   count(distinct(CMN_LOGIN_HISTORY.USR_SID)) as USER");
            sql.addSql(" from ");
            sql.addSql("   CMN_LOGIN_HISTORY ");
            sql.addSql("     left join ");
            sql.addSql("       CMN_USRM ");
            sql.addSql("     on ");
            sql.addSql("       CMN_LOGIN_HISTORY.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID > ? ");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_UKO_FLG = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_LOGIN_HISTORY.CLH_EDATE between ? ");
            sql.addSql("    and ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConst.YUKOMUKO_YUKO);
            sql.addDateValue(dateZeroHhMmSs);
            sql.addDateValue(dateMaxHhMmSs);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                loginNum = rs.getLong("LOGIN");
                loginUserNum = rs.getLong("USER");
            }
            //ユーザ件数の更新
            CmnUsrmCountModel usrCountMdl = new CmnUsrmCountModel();
            date.setZeroHhMmSs();
            usrCountMdl.setCucDate(date);
            usrCountMdl.setCucMonth(
                    Integer.parseInt(date.getDateString().substring(0, 6)));
            usrCountMdl.setCucCnt(userCount);
            usrCountMdl.setCucLogin(loginNum);
            usrCountMdl.setCucUser(loginUserNum);
            if (update(usrCountMdl) == 0) {
                insert(usrCountMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <br>[機  能] 日付データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_USRM_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<UDate> getDate() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<UDate> ret = new ArrayList<UDate>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUC_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(UDate.getInstanceTimestamp(rs.getTimestamp("CUC_DATE")));
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
     * <p>Create CMN_USRM_COUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmCountModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrmCountModel __getCmnUsrmCountFromRs(ResultSet rs) throws SQLException {
        CmnUsrmCountModel bean = new CmnUsrmCountModel();
        bean.setCucDate(UDate.getInstanceTimestamp(rs.getTimestamp("CUC_DATE")));
        bean.setCucMonth(rs.getInt("CUC_MONTH"));
        bean.setCucCnt(rs.getInt("CUC_CNT"));
        bean.setCucLogin(rs.getInt("CUC_LOGIN"));
        bean.setCucUser(rs.getInt("CUC_USER"));
        return bean;
    }
    /**
    *
    * <br>[機  能] 指定日含む過去3日間の最終集計データを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param date 指定日
    * @throws SQLException SQL実行時例外
    * @return 指定日前の最終集計データ
    */
    public CmnUsrmCountModel getPrevUsrmCount(UDate date) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnUsrmCountModel ret = null;
        UDate dateMaxHhMmSs = date.cloneUDate();
        dateMaxHhMmSs.setMaxHhMmSs();
        UDate from = date.cloneUDate();
        from.addDay(-2);
        from.setZeroHhMmSs();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUC_DATE,");
            sql.addSql("   CUC_MONTH,");
            sql.addSql("   CUC_CNT,");
            sql.addSql("   CUC_LOGIN,");
            sql.addSql("   CUC_USER");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_COUNT");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_COUNT.CUC_DATE >= ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_COUNT.CUC_DATE <= ?");
            sql.addSql(" order by ");
            sql.addSql("   CMN_USRM_COUNT.CUC_DATE desc");
            sql.addDateValue(from);
            sql.addDateValue(dateMaxHhMmSs);
            sql.setPagingValue(0, 1);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrmCountFromRs(rs);
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
