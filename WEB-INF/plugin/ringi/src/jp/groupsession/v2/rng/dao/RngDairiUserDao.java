package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RngDairiUserModel;

/**
 * <p>RNG_DAIRI_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngDairiUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngDairiUserDao.class);

    /**
     * <p>Default Constructor
     */
    public RngDairiUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngDairiUserDao(Connection con) {
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
            sql.addSql("drop table RNG_DAIRI_USER");

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
            sql.addSql(" create table RNG_DAIRI_USER (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID_DAIRI NUMBER(10,0) not null,");
            sql.addSql("   RDU_START varchar(23) not null,");
            sql.addSql("   RDU_END varchar(23),");
            sql.addSql("   primary key (USR_SID,USR_SID_DAIRI)");
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
     * <p>Insert RNG_DAIRI_USER Data Bindding JavaBean
     * @param bean RNG_DAIRI_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngDairiUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_DAIRI_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
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
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addDateValue(bean.getRduStart());
            sql.addDateValue(bean.getRduEnd());
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
     * <p>Insert RNG_DAIRI_USER Data Bindding JavaBean
     * @param bean RNG_DAIRI_USER Data Bindding JavaBean
     * @param dairi String[] 代理SID
     * @throws SQLException SQL実行例外
     */
    public void insert(RngDairiUserModel bean, String[] dairi) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_DAIRI_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
            sql.addSql(" )");
            sql.addSql(" values");

            for (int idx = 0; idx < dairi.length; idx++) {
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                if (idx + 1 < dairi.length) {
                    sql.addSql(" ),");
                } else {
                    sql.addSql(" )");
                }
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(NullDefault.getInt(dairi[idx], -1));
                sql.addDateValue(bean.getRduStart());
                sql.addDateValue(bean.getRduEnd());
            }

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Update RNG_DAIRI_USER Data Bindding JavaBean
     * @param bean RNG_DAIRI_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngDairiUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" set ");
            sql.addSql("   RDU_START=?,");
            sql.addSql("   RDU_END=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID_DAIRI=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getRduStart());
            sql.addDateValue(bean.getRduEnd());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUsrSidDairi());

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
     * <p>Select RNG_DAIRI_USER All Data
     * @return List in RNG_DAIRI_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RngDairiUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngDairiUserModel> ret = new ArrayList<RngDairiUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
            sql.addSql(" from ");
            sql.addSql("   RNG_DAIRI_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngDairiUserFromRs(rs));
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
     * <p>Select RNG_DAIRI_USER
     * @param usrSid USR_SID
     * @return RNG_DAIRI_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RngDairiUserModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngDairiUserModel> ret = new ArrayList<RngDairiUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngDairiUserFromRs(rs));
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
     * <p>Select RNG_DAIRI_USER
     * @param usrSid USR_SID
     * @param start 開始期間
     * @param end 終了期間
     * @return RNG_DAIRI_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<AccountDataModel> select(int usrSid, UDate start, UDate end) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AccountDataModel> ret = new ArrayList<AccountDataModel>();
        con = getCon();

        if (start != null) {
            // 複製して 23:59:59 にセット
            start = start.cloneUDate();
            start.setMaxHhMmSs();
        }
        if (end != null) {
            // 複製して 00:00:00 にセット
            end   = end.cloneUDate();
            end.setZeroHhMmSs();
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RNG_DAIRI_USER.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   case when JUSINCNT is null ");
            sql.addSql("        then 0");
            sql.addSql("        else JUSINCNT end COUNT");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM_INF.USR_SID = RNG_DAIRI_USER.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM.USR_SID = RNG_DAIRI_USER.USR_SID");
            sql.addSql(" left join");

            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_SINGI.USR_SID,");
            sql.addSql("      COUNT(distinct RNG_RNDATA.RNG_SID) as JUSINCNT");
            sql.addSql("    from");
            sql.addSql("      RNG_RNDATA,");
            sql.addSql("      RNG_KEIRO_STEP,");
            sql.addSql("      RNG_SINGI");
            sql.addSql("    where");
            sql.addSql("      RNG_RNDATA.RNG_STATUS <> ?");
            sql.addIntValue(RngConst.RNG_STATUS_DRAFT);

            sql.addSql("    and");
            sql.addSql("      RNG_RNDATA.RNG_SID = RNG_KEIRO_STEP.RNG_SID");
            sql.addSql("    and");
            sql.addSql("      RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("    and");
            sql.addSql("      RNG_SINGI.USR_SID IN ");
            sql.addSql("      (");
            sql.addSql("             select");
            sql.addSql("               USR_SID");
            sql.addSql("             from");
            sql.addSql("               RNG_DAIRI_USER");
            sql.addSql("             where");
            sql.addSql("               USR_SID_DAIRI = ?");
            sql.addSql("             and ");
            sql.addSql("               RDU_START <= ?");
            sql.addSql("             and ");
            sql.addSql("               (");
            sql.addSql("                  RDU_END >= ?");
            sql.addSql("                or");
            sql.addSql("                  RDU_END IS NULL");
            sql.addSql("                )");
            sql.addSql("      )");
            sql.addIntValue(usrSid);
            sql.addDateValue(start);
            sql.addDateValue(end);

            sql.addSql("    and");
            sql.addSql("      (");
            sql.addSql("        (");
            sql.addSql("            RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_RNDATA.RNG_COMPFLG = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("        )");
            sql.addSql("        or");
            sql.addSql("        (");
            sql.addSql("            RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_RNDATA.RNG_STATUS = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_RNDATA.RNG_COMPFLG = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_SINGI.RSS_STATUS = ?");

            sql.addSql("        )");
            sql.addSql("        or");
            sql.addSql("        (");
            sql.addSql("            RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_RNDATA.RNG_STATUS = ?");
            sql.addSql("          and");
            sql.addSql("            RNG_RNDATA.RNG_COMPFLG = ?");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_STATUS_SETTLED);
            sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);

            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_STATUS_REJECT);
            sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);

            sql.addSql("     group by");
            sql.addSql("       RNG_SINGI.USR_SID");
            sql.addSql("    ) CNT");
            sql.addSql(" on");
            sql.addSql("   RNG_DAIRI_USER.USR_SID = CNT.USR_SID");


/*
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_SID_USR.USR_SID,");
            sql.addSql("      COUNT(RNG_SID_USR.RNG_SID) as JUSINCNT");
            sql.addSql("    from");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         RNG_SINGI.USR_SID,");
            sql.addSql("         RNG_SINGI.RNG_SID");
            sql.addSql("       from");
            sql.addSql("         RNG_KEIRO_STEP");
            sql.addSql("       left join");
            sql.addSql("         RNG_SINGI");
            sql.addSql("       on");
            sql.addSql("         RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql("       left join");
            sql.addSql("         RNG_RNDATA");
            sql.addSql("       on");
            sql.addSql("         RNG_RNDATA.RNG_SID = RNG_SINGI.RNG_SID");
            sql.addSql("       where");
            sql.addSql("         RKS_STATUS = ?");
            sql.addSql("       and");
            sql.addSql("         (");
            sql.addSql("          (");
            sql.addSql("             RNG_RNDATA.RNG_COMPFLG = ?");
            sql.addSql("           and");
            sql.addSql("             RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("           and ");
            sql.addSql("             RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("           and ");
            sql.addSql("             RNG_KEIRO_STEP.RKS_STATUS = ?");
            sql.addSql("          )");
            sql.addSql("         or");
            sql.addSql("          (");
            sql.addSql("             RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("           and");
            sql.addSql("             RNG_RNDATA.RNG_STATUS = ?");
            sql.addSql("           and");
            sql.addSql("             RNG_RNDATA.RNG_COMPFLG = ?");
            sql.addSql("           and");
            sql.addSql("             RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("          )");
            sql.addSql("         )");
            sql.addSql("        and");
            sql.addSql("          RNG_SINGI.USR_SID IN ");
            sql.addSql("            (");
            sql.addSql("             select");
            sql.addSql("               USR_SID");
            sql.addSql("             from");
            sql.addSql("               RNG_DAIRI_USER");
            sql.addSql("             where");
            sql.addSql("               USR_SID_DAIRI = ?");
            sql.addSql("             and ");
            sql.addSql("               RDU_START <= ?");
            sql.addSql("             and ");
            sql.addSql("               (");
            sql.addSql("                  RDU_END >= ?");
            sql.addSql("                or");
            sql.addSql("                  RDU_END IS NULL");
            sql.addSql("                )");
            sql.addSql("            )");
            sql.addSql("          group by");
            sql.addSql("            RNG_SINGI.USR_SID,");
            sql.addSql("            RNG_SINGI.RNG_SID");
            sql.addSql("       ) RNG_SID_USR");
            sql.addSql("     group by");
            sql.addSql("       RNG_SID_USR.USR_SID");
            sql.addSql("    ) CNT");
            sql.addSql(" on");
            sql.addSql("   RNG_DAIRI_USER.USR_SID = CNT.USR_SID");
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);

            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
            sql.addIntValue(RngConst.RNG_STATUS_SETTLED);
            sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);

            sql.addIntValue(usrSid);
            sql.addDateValue(start);
            sql.addDateValue(end);
            */



            sql.addSql(" where");
            sql.addSql("   RNG_DAIRI_USER.USR_SID_DAIRI = ?");
            sql.addSql(" and ");
            sql.addSql("   RNG_DAIRI_USER.USR_SID <> RNG_DAIRI_USER.USR_SID_DAIRI");
            sql.addSql(" and ");
            sql.addSql("   RNG_DAIRI_USER.RDU_START <= ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      RNG_DAIRI_USER.RDU_END >= ?");
            sql.addSql("    or");
            sql.addSql("      RNG_DAIRI_USER.RDU_END IS NULL");
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN <> ? ");
            sql.addSql(" order by ");
            sql.addSql("   RNG_DAIRI_USER.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrSid);
            sql.addDateValue(start);
            sql.addDateValue(end);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AccountDataModel mdl = new AccountDataModel();
                mdl.setAccountSid(rs.getInt("USR_SID"));
                mdl.setAccountName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                mdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                mdl.setAccountCount(rs.getInt("COUNT"));
                ret.add(mdl);
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
     * <p>指定日に代理人であるユーザのSIDを取得する
     * @param usrSid ユーザSID
     * @param date 指定日
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDairiSid(int usrSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID_DAIRI");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            sql.addSql(" and");
            sql.addSql("   RDU_START <= ?");
            sql.addDateValue(date);
            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   RDU_END >= ?");
            sql.addDateValue(date);
            sql.addSql(" or");
            sql.addSql("   RDU_END is null");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID_DAIRI"));
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
     * <p>指定日に代理人であるユーザのSIDを取得する
     * @param usrSidList ユーザSIDリスト
     * @param date 指定日
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDairiSid(List<Integer> usrSidList, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID_DAIRI");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where");
            for (int i = 0; i < usrSidList.size(); i++) {
                sql.addSql("   USR_SID=?");
                sql.addSql(" and");
                sql.addIntValue(usrSidList.get(i));
            }
            sql.addSql("   RDU_START <= ?");
            sql.addDateValue(date);
            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   RDU_END >= ?");
            sql.addDateValue(date);
            sql.addSql(" or");
            sql.addSql("   RDU_END is null");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID_DAIRI"));
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
     * <p>指定日に代理人であるユーザのSIDを取得する
     * @param usrSidList ユーザSIDリスト
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<RngDairiUserModel> getDairiModel(
            List<Integer> usrSidList) throws SQLException {

        ArrayList<RngDairiUserModel> ret = new ArrayList<RngDairiUserModel>();
        if (usrSidList.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate dateStart = new UDate();
        UDate dateFinish = new UDate();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where");
            sql.addSql("   (");
            for (int i = 0; i < usrSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" or");
                }
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSidList.get(i));
            }
            sql.addSql("   )");
            if (!usrSidList.isEmpty() && usrSidList.size() > 0) {
                sql.addSql(" and");
            } else {
                sql.addSql("   0=1");
                sql.addSql(" and");
            }
            sql.addSql("   RDU_START <= ?");
            dateStart.setMaxHhMmSs();
            sql.addDateValue(dateStart);
            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   RDU_END >= ?");
            dateFinish.setZeroHhMmSs();
            sql.addDateValue(dateFinish);
            sql.addSql(" or");
            sql.addSql("   RDU_END is null");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RngDairiUserModel rduMdl = new RngDairiUserModel();
                rduMdl.setUsrSid(rs.getInt("USR_SID"));
                rduMdl.setUsrSidDairi(rs.getInt("USR_SID_DAIRI"));
                rduMdl.setRduStart(UDate.getInstanceTimestamp(rs.getTimestamp("RDU_START")));
                rduMdl.setRduEnd(UDate.getInstanceTimestamp(rs.getTimestamp("RDU_END")));
                ret.add(rduMdl);
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
     * <p>Select RNG_DAIRI_USER
     * @param usrSid USR_SID
     * @param usrSidDairi USR_SID_DAIRI
     * @return RNG_DAIRI_USERModel
     * @throws SQLException SQL実行例外
     */
    public RngDairiUserModel select(int usrSid, int usrSidDairi) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngDairiUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RDU_START,");
            sql.addSql("   RDU_END");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID_DAIRI=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSidDairi);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngDairiUserFromRs(rs);
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
     * <p>Delete RNG_DAIRI_USER
     * @param usrSid USR_SID
     * @param usrSidDairi USR_SID_DAIRI
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int usrSid, int usrSidDairi) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID_DAIRI=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSidDairi);

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
     * <p>Delete RNG_DAIRI_USER
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_DAIRI_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Create RNG_DAIRI_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngDairiUserModel
     * @throws SQLException SQL実行例外
     */
    private RngDairiUserModel __getRngDairiUserFromRs(ResultSet rs) throws SQLException {
        RngDairiUserModel bean = new RngDairiUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrSidDairi(rs.getInt("USR_SID_DAIRI"));
        bean.setRduStart(UDate.getInstanceTimestamp(rs.getTimestamp("RDU_START")));
        bean.setRduEnd(UDate.getInstanceTimestamp(rs.getTimestamp("RDU_END")));
        return bean;
    }
}
