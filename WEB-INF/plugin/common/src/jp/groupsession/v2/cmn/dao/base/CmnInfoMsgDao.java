package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.man.man320.dao.Man320Dao;
import jp.groupsession.v2.man.man320.model.Man320DspModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_INFO_MSG Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnInfoMsgDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnInfoMsgDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnInfoMsgDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnInfoMsgDao(Connection con) {
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
            sql.addSql("drop table CMN_INFO_MSG");

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
            sql.addSql(" create table CMN_INFO_MSG (");
            sql.addSql("   IMS_SID NUMBER(10,0) not null,");
            sql.addSql("   IMS_MSG varchar(450),");
            sql.addSql("   IMS_VALUE varchar(3000),");
            sql.addSql("   IMS_FR_DATE varchar(23) not null,");
            sql.addSql("   IMS_TO_DATE varchar(23) not null,");
            sql.addSql("   IMS_JTKB NUMBER(10,0) not null,");
            sql.addSql("   IMS_KBN NUMBER(10,0) not null,");
            sql.addSql("   IMS_DWEEK1 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK2 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK3 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK4 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK5 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK6 NUMBER(10,0),");
            sql.addSql("   IMS_DWEEK7 NUMBER(10,0),");
            sql.addSql("   IMS_DAY NUMBER(10,0),");
            sql.addSql("   IMS_WEEK NUMBER(10,0),");
            sql.addSql("   IMS_AUID NUMBER(10,0) not null,");
            sql.addSql("   IMS_ADATE varchar(23) not null,");
            sql.addSql("   IMS_EUID NUMBER(10,0) not null,");
            sql.addSql("   IMS_EDATE varchar(23) not null,");
            sql.addSql("   primary key (IMS_SID)");
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
     * <p>Insert CMN_INFO_MSG Data Bindding JavaBean
     * @param bean CMN_INFO_MSG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnInfoMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_INFO_MSG(");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getImsSid());
            sql.addStrValue(bean.getImsMsg());
            sql.addStrValue(bean.getImsValue());
            sql.addDateValue(bean.getImsFrDate());
            sql.addDateValue(bean.getImsToDate());
            sql.addIntValue(bean.getImsJtkb());
            sql.addIntValue(bean.getImsKbn());
            sql.addIntValue(bean.getImsDweek1());
            sql.addIntValue(bean.getImsDweek2());
            sql.addIntValue(bean.getImsDweek3());
            sql.addIntValue(bean.getImsDweek4());
            sql.addIntValue(bean.getImsDweek5());
            sql.addIntValue(bean.getImsDweek6());
            sql.addIntValue(bean.getImsDweek7());
            sql.addIntValue(bean.getImsDay());
            sql.addIntValue(bean.getImsWeek());
            sql.addIntValue(bean.getImsAuid());
            sql.addDateValue(bean.getImsAdate());
            sql.addIntValue(bean.getImsEuid());
            sql.addDateValue(bean.getImsEdate());
            sql.addIntValue(bean.getImsHolkbn());
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
     * <p>Update CMN_INFO_MSG Data Bindding JavaBean
     * @param bean CMN_INFO_MSG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnInfoMsgModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" set ");
            sql.addSql("   IMS_MSG=?,");
            sql.addSql("   IMS_VALUE=?,");
            sql.addSql("   IMS_FR_DATE=?,");
            sql.addSql("   IMS_TO_DATE=?,");
            sql.addSql("   IMS_JTKB=?,");
            sql.addSql("   IMS_KBN=?,");
            sql.addSql("   IMS_DWEEK1=?,");
            sql.addSql("   IMS_DWEEK2=?,");
            sql.addSql("   IMS_DWEEK3=?,");
            sql.addSql("   IMS_DWEEK4=?,");
            sql.addSql("   IMS_DWEEK5=?,");
            sql.addSql("   IMS_DWEEK6=?,");
            sql.addSql("   IMS_DWEEK7=?,");
            sql.addSql("   IMS_DAY=?,");
            sql.addSql("   IMS_WEEK=?,");
            sql.addSql("   IMS_EUID=?,");
            sql.addSql("   IMS_EDATE=?,");
            sql.addSql("   IMS_HOLKBN=?");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getImsMsg());
            sql.addStrValue(bean.getImsValue());
            sql.addDateValue(bean.getImsFrDate());
            sql.addDateValue(bean.getImsToDate());
            sql.addIntValue(bean.getImsJtkb());
            sql.addIntValue(bean.getImsKbn());
            sql.addIntValue(bean.getImsDweek1());
            sql.addIntValue(bean.getImsDweek2());
            sql.addIntValue(bean.getImsDweek3());
            sql.addIntValue(bean.getImsDweek4());
            sql.addIntValue(bean.getImsDweek5());
            sql.addIntValue(bean.getImsDweek6());
            sql.addIntValue(bean.getImsDweek7());
            sql.addIntValue(bean.getImsDay());
            sql.addIntValue(bean.getImsWeek());
            sql.addIntValue(bean.getImsEuid());
            sql.addDateValue(bean.getImsEdate());
            sql.addIntValue(bean.getImsHolkbn());
            //where
            sql.addIntValue(bean.getImsSid());

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
     * <p>Select CMN_INFO_MSG All Data
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnInfoMsgModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoMsgModel> ret = new ArrayList<CmnInfoMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_MSG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoMsgFromRs(rs));
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
     * <p>Select CMN_INFO_MSG All Data
     * @param offset オフセット
     * @param limit 表示制限数
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param reqMdl リクエスト情報
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Man320DspModel> select(
            int offset, int limit, int sortKey, int orderKey,
            RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Man320DspModel> ret = new ArrayList<Man320DspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_INFO_MSG.IMS_SID,");
            sql.addSql("   CMN_INFO_MSG.IMS_MSG,");
            sql.addSql("   CMN_INFO_MSG.IMS_VALUE,");
            sql.addSql("   CMN_INFO_MSG.IMS_FR_DATE,");
            sql.addSql("   CMN_INFO_MSG.IMS_TO_DATE,");
            sql.addSql("   CMN_INFO_MSG.IMS_JTKB,");
            sql.addSql("   CMN_INFO_MSG.IMS_KBN,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK1,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK2,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK3,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK4,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK5,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK6,");
            sql.addSql("   CMN_INFO_MSG.IMS_DWEEK7,");
            sql.addSql("   CMN_INFO_MSG.IMS_DAY,");
            sql.addSql("   CMN_INFO_MSG.IMS_WEEK,");
            sql.addSql("   CMN_INFO_MSG.IMS_AUID,");
            sql.addSql("   CMN_INFO_MSG.IMS_ADATE,");
            sql.addSql("   CMN_INFO_MSG.IMS_EUID,");
            sql.addSql("   CMN_INFO_MSG.IMS_EDATE,");
            sql.addSql("   CMN_INFO_MSG.IMS_HOLKBN,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM.USR_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_MSG,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID=CMN_INFO_MSG.IMS_EUID");

            //オーダー
            sql.addSql(" order by");
            String orderStr = "";
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = "  desc";
            }
          //ソートカラム
            switch (sortKey) {
                //メッセージ
                case Man320Biz.SORT_KEY_MSG:
                    sql.addSql("  CMN_INFO_MSG.IMS_MSG");
                    sql.addSql(orderStr);
                    break;
                //開始
                case Man320Biz.SORT_KEY_FRDATE:
                    sql.addSql("  CMN_INFO_MSG.IMS_FR_DATE ");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  CMN_INFO_MSG.IMS_SID ");
                    sql.addSql(orderStr);
                    break;
                //終了
                case Man320Biz.SORT_KEY_TODATE:
                    sql.addSql("  CMN_INFO_MSG.IMS_TO_DATE ");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  CMN_INFO_MSG.IMS_SID ");
                    sql.addSql(orderStr);
                    break;
                //登録・更新者
                case Man320Biz.SORT_KEY_USR_NAME:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql(orderStr);
                    break;
                default:
                    break;
            }

            log__.info(sql.toLogString());
            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (offset > 1) {
                rs.absolute(offset - 1);
            }
            Man320Dao dao = new Man320Dao(con);
            Man320DspModel model = null;
            UDate now = new UDate();
            for (int i = 0; rs.next() && i < limit; i++) {
                model = __getMan280DspModelFromRs(rs, now, reqMdl);
                model.setTargetNameList(dao.getKoukaiTargetString(model.getImsSid()));
                ret.add(model);
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
     * <p>Select CMN_INFO_MSG All Data
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public int getAllCount() throws SQLException {

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
            sql.addSql("   CMN_INFO_MSG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <p>Select CMN_INFO_MSG All Data
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnInfoMsgModel> getActiveMsg() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoMsgModel> ret = new ArrayList<CmnInfoMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_JTKB=0");


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoMsgFromRs(rs));
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
     * <p>Select CMN_INFO_MSG All Data
     * @param list in CmnInfoTagModel
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnInfoMsgModel> getActiveMsg(
            ArrayList<CmnInfoTagModel> list) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnInfoMsgModel> ret = new ArrayList<CmnInfoMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID in(-1");
            for (CmnInfoTagModel bean : list) {
                sql.addSql(",?");
                sql.addIntValue(bean.getImsSid());
            }
            sql.addSql(" ) ");
            sql.addSql(" and ");
            sql.addSql("   IMS_JTKB=0");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoMsgFromRs(rs));
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
     * <p>Select CMN_INFO_MSG
     * @param imsSid IMS_SID
     * @return CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public CmnInfoMsgModel select(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnInfoMsgModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(imsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnInfoMsgFromRs(rs);
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
     * <p>選択したインフォメーションデータを取得
     * @param imsSids IMS_SID リスト
     * @return CMN_INFO_MSGModel in List
     * @throws SQLException SQL実行例外
     */
    public List<CmnInfoMsgModel> selectInfoSids(String[] imsSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnInfoMsgModel> ret = new ArrayList<CmnInfoMsgModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IMS_SID,");
            sql.addSql("   IMS_MSG,");
            sql.addSql("   IMS_VALUE,");
            sql.addSql("   IMS_FR_DATE,");
            sql.addSql("   IMS_TO_DATE,");
            sql.addSql("   IMS_JTKB,");
            sql.addSql("   IMS_KBN,");
            sql.addSql("   IMS_DWEEK1,");
            sql.addSql("   IMS_DWEEK2,");
            sql.addSql("   IMS_DWEEK3,");
            sql.addSql("   IMS_DWEEK4,");
            sql.addSql("   IMS_DWEEK5,");
            sql.addSql("   IMS_DWEEK6,");
            sql.addSql("   IMS_DWEEK7,");
            sql.addSql("   IMS_DAY,");
            sql.addSql("   IMS_WEEK,");
            sql.addSql("   IMS_AUID,");
            sql.addSql("   IMS_ADATE,");
            sql.addSql("   IMS_EUID,");
            sql.addSql("   IMS_EDATE,");
            sql.addSql("   IMS_HOLKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID in(");

            int cnt = imsSids.length;
            for (String imsSid : imsSids) {
                sql.addSql("   ?");
                if (cnt > 1) {
                    sql.addSql("   ,");
                }
                cnt--;
                sql.addIntValue(Integer.parseInt(imsSid));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnInfoMsgFromRs(rs));
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
     * <p>Delete CMN_INFO_MSG
     * @param imsSid IMS_SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int imsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(imsSid);

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
     * <p>Delete CMN_INFO_MSG
     * @param imsSids IMS_SIDの配列
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(String[] imsSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_INFO_MSG");
            sql.addSql(" where ");
            sql.addSql("   IMS_SID in(-1");

            for (String imsSid : imsSids) {
                sql.addSql("   ,?");
                sql.addIntValue(Integer.parseInt(imsSid));
            }
            sql.addSql("   )");
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
     * <p>Create CMN_INFO_MSG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnInfoMsgModel
     * @throws SQLException SQL実行例外
     */
    private CmnInfoMsgModel __getCmnInfoMsgFromRs(ResultSet rs) throws SQLException {
        CmnInfoMsgModel bean = new CmnInfoMsgModel();
        bean.setImsSid(rs.getInt("IMS_SID"));
        bean.setImsMsg(rs.getString("IMS_MSG"));
        bean.setImsValue(rs.getString("IMS_VALUE"));
        bean.setImsFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_FR_DATE")));
        bean.setImsToDate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_TO_DATE")));
        bean.setImsJtkb(rs.getInt("IMS_JTKB"));
        bean.setImsKbn(rs.getInt("IMS_KBN"));
        bean.setImsDweek1(rs.getInt("IMS_DWEEK1"));
        bean.setImsDweek2(rs.getInt("IMS_DWEEK2"));
        bean.setImsDweek3(rs.getInt("IMS_DWEEK3"));
        bean.setImsDweek4(rs.getInt("IMS_DWEEK4"));
        bean.setImsDweek5(rs.getInt("IMS_DWEEK5"));
        bean.setImsDweek6(rs.getInt("IMS_DWEEK6"));
        bean.setImsDweek7(rs.getInt("IMS_DWEEK7"));
        bean.setImsDay(rs.getInt("IMS_DAY"));
        bean.setImsWeek(rs.getInt("IMS_WEEK"));
        bean.setImsAuid(rs.getInt("IMS_AUID"));
        bean.setImsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_ADATE")));
        bean.setImsEuid(rs.getInt("IMS_EUID"));
        bean.setImsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_EDATE")));
        bean.setImsHolkbn(rs.getInt("IMS_HOLKBN"));
        return bean;
    }

    /**
     * <p>Create CMN_INFO_MSG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param date 現在日付
     * @param reqMdl リクエスト情報
     * @return created Man280DspModel
     * @throws SQLException SQL実行例外
     */
    private Man320DspModel __getMan280DspModelFromRs(ResultSet rs,
            UDate date, RequestModel reqMdl) throws SQLException {
        Man320DspModel bean = new Man320DspModel();
        bean.setImsSid(rs.getInt("IMS_SID"));
        bean.setImsMsg(rs.getString("IMS_MSG"));
        bean.setImsValue(rs.getString("IMS_VALUE"));
        bean.setImsFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_FR_DATE")));
        bean.setImsToDate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_TO_DATE")));
        //期限切れのインフォメーションかどうか
        if (bean.getImsToDate().compareDateYMDHM(date) == UDate.LARGE) {
            //期限切れ
            bean.setKigenChFlg(Man320Biz.ALERT_FLG_KIGEN_NG);
        }
        bean.setImsJtkb(rs.getInt("IMS_JTKB"));
        bean.setImsKbn(rs.getInt("IMS_KBN"));
        bean.setImsDweek1(rs.getInt("IMS_DWEEK1"));
        bean.setImsDweek2(rs.getInt("IMS_DWEEK2"));
        bean.setImsDweek3(rs.getInt("IMS_DWEEK3"));
        bean.setImsDweek4(rs.getInt("IMS_DWEEK4"));
        bean.setImsDweek5(rs.getInt("IMS_DWEEK5"));
        bean.setImsDweek6(rs.getInt("IMS_DWEEK6"));
        bean.setImsDweek7(rs.getInt("IMS_DWEEK7"));
        bean.setImsDay(rs.getInt("IMS_DAY"));
        bean.setImsWeek(rs.getInt("IMS_WEEK"));
        bean.setImsAuid(rs.getInt("IMS_AUID"));
        bean.setImsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_ADATE")));
        bean.setImsEuid(rs.getInt("IMS_EUID"));
        bean.setImsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IMS_EDATE")));
        //
        bean.setUsrNameSei(rs.getString("USI_SEI"));
        bean.setUsrNameMei(rs.getString("USI_MEI"));
        bean.setUsrJkbn(rs.getInt("USR_JKBN"));
        UDate frDate = UDate.getInstanceTimestamp(rs.getTimestamp("IMS_FR_DATE"));
        UDate toDate = UDate.getInstanceTimestamp(rs.getTimestamp("IMS_TO_DATE"));
        bean.setFrDate(UDateUtil.getSlashYYMD(frDate) + " " + UDateUtil.getSeparateHM(frDate));
        bean.setToDate(UDateUtil.getSlashYYMD(toDate) + " " + UDateUtil.getSeparateHM(toDate));

        //拡張文字列
        String exStr = Man320Biz.getInfoExString(reqMdl, bean);
        bean.setExString(exStr);

        return bean;
    }
}
