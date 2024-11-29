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
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;

/**
 * <p>BBS_THRE_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsThreInfDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsThreInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsThreInfDao(Connection con) {
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
            sql.addSql("drop table BBS_THRE_INF");

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
            sql.addSql(" create table BBS_THRE_INF (");
            sql.addSql("   BTI_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BTI_TITLE varchar(150) not null,");
            sql.addSql("   BTI_AUID NUMBER(10,0) not null,");
            sql.addSql("   BTI_ADATE varchar(23) not null,");
            sql.addSql("   BTI_EUID NUMBER(10,0) not null,");
            sql.addSql("   BTI_EDATE varchar(23) not null,");
            sql.addSql("   BTI_LIMIT NUMBER(10,0) not null,");
            sql.addSql("   BTI_LIMIT_FR_DATE varchar(23),");
            sql.addSql("   BTI_LIMIT_DATE varchar(23),");
            sql.addSql("   BTI_AGID NUMBER(10,0),");
            sql.addSql("   BTI_EGID NUMBER(10,0),");
            sql.addSql("   primary key (BTI_SID)");
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
     * <p>Insert BBS_THRE_INF Data Bindding JavaBean
     * @param bean BBS_THRE_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsThreInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_INF(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
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
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addStrValue(bean.getBtiTitle());
            sql.addIntValue(bean.getBtiAuid());
            sql.addDateValue(bean.getBtiAdate());
            sql.addIntValue(bean.getBtiEuid());
            sql.addDateValue(bean.getBtiEdate());
            sql.addIntValue(bean.getBtiLimit());
            sql.addDateValue(bean.getBtiLimitFrDate());
            sql.addDateValue(bean.getBtiLimitDate());
            sql.addIntValue(bean.getBtiAgid());
            sql.addIntValue(bean.getBtiEgid());
            sql.addIntValue(bean.getBtiImportance());
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
     * <p>Insert BBS_THRE_INF Data Bindding JavaBean
     * @param beanList BBS_THRE_INF DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<BbsThreInfModel> beanList) throws SQLException {

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
            sql.addSql(" BBS_THRE_INF(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
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

            for (BbsThreInfModel bean : beanList) {
                sql.addIntValue(bean.getBtiSid());
                sql.addIntValue(bean.getBfiSid());
                sql.addStrValue(bean.getBtiTitle());
                sql.addIntValue(bean.getBtiAuid());
                sql.addDateValue(bean.getBtiAdate());
                sql.addIntValue(bean.getBtiEuid());
                sql.addDateValue(bean.getBtiEdate());
                sql.addIntValue(bean.getBtiLimit());
                sql.addDateValue(bean.getBtiLimitFrDate());
                sql.addDateValue(bean.getBtiLimitDate());
                sql.addIntValue(bean.getBtiAgid());
                sql.addIntValue(bean.getBtiEgid());
                sql.addIntValue(bean.getBtiImportance());
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
     * <p>Update BBS_THRE_INF Data Bindding JavaBean
     * @param bean BBS_THRE_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsThreInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" set ");
            sql.addSql("   BFI_SID=?,");
            sql.addSql("   BTI_TITLE=?,");
            sql.addSql("   BTI_AUID=?,");
            sql.addSql("   BTI_ADATE=?,");
            sql.addSql("   BTI_EUID=?,");
            sql.addSql("   BTI_EDATE=?,");
            sql.addSql("   BTI_LIMIT=?,");
            sql.addSql("   BTI_LIMIT_FR_DATE=?,");
            sql.addSql("   BTI_LIMIT_DATE=?,");
            sql.addSql("   BTI_AGID=?,");
            sql.addSql("   BTI_EGID=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());
            sql.addStrValue(bean.getBtiTitle());
            sql.addIntValue(bean.getBtiAuid());
            sql.addDateValue(bean.getBtiAdate());
            sql.addIntValue(bean.getBtiEuid());
            sql.addDateValue(bean.getBtiEdate());
            sql.addIntValue(bean.getBtiLimit());
            sql.addDateValue(bean.getBtiLimitFrDate());
            sql.addDateValue(bean.getBtiLimitDate());
            sql.addIntValue(bean.getBtiAgid());
            sql.addIntValue(bean.getBtiEgid());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <p>スレッド情報の更新を行う
     * @param bean 更新情報
     * @throws SQLException SQL実行例外
     */
    public void updateThreData(BbsThreInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" set ");
            sql.addSql("   BTI_TITLE=?,");
            sql.addSql("   BTI_LIMIT=?,");
            sql.addSql("   BTI_LIMIT_FR_DATE=?,");
            sql.addSql("   BTI_LIMIT_DATE=?,");
            sql.addSql("   BTI_EUID=?,");
            sql.addSql("   BTI_EDATE=?,");
            sql.addSql("   BTI_EGID=?,");
            sql.addSql("   BTI_IMPORTANCE=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBtiTitle());
            sql.addIntValue(bean.getBtiLimit());
            sql.addDateValue(bean.getBtiLimitFrDate());
            sql.addDateValue(bean.getBtiLimitDate());
            sql.addIntValue(bean.getBtiEuid());
            sql.addDateValue(bean.getBtiEdate());
            sql.addIntValue(bean.getBtiEgid());
            sql.addIntValue(bean.getBtiImportance());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <p>投稿者の更新を行う
     * @param bean 更新情報
     * @throws SQLException SQL実行例外
     */
    public void updateContributor(BbsThreInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" set ");
            sql.addSql("   BTI_EUID=?,");
            sql.addSql("   BTI_EDATE=?,");
            sql.addSql("   BTI_EGID=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtiEuid());
            sql.addDateValue(bean.getBtiEdate());
            sql.addIntValue(bean.getBtiEgid());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <p>指定日時より最新書込み日時が古いスレッド情報を取得する
     * @param date 基準日
     * @return ArrayList in BBS_THRE_INF Model
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsThreInfModel> getOldThreData(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreInfModel> ret = new ArrayList<BbsThreInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BBS_THRE_INF.BTI_SID as BTI_SID,");
            sql.addSql("   BBS_THRE_INF.BFI_SID as BFI_SID,");
            sql.addSql("   BBS_THRE_INF.BTI_TITLE as BTI_TITLE,");
            sql.addSql("   BBS_THRE_INF.BTI_AUID as BTI_AUID,");
            sql.addSql("   BBS_THRE_INF.BTI_ADATE as BTI_ADATE,");
            sql.addSql("   BBS_THRE_INF.BTI_EUID as BTI_EUID,");
            sql.addSql("   BBS_THRE_INF.BTI_EDATE as BTI_EDATE,");
            sql.addSql("   BBS_THRE_INF.BTI_LIMIT as BTI_LIMIT,");
            sql.addSql("   BBS_THRE_INF.BTI_LIMIT_FR_DATE as BTI_LIMIT_FR_DATE,");
            sql.addSql("   BBS_THRE_INF.BTI_LIMIT_DATE as BTI_LIMIT_DATE,");
            sql.addSql("   BBS_THRE_INF.BTI_AGID as BTI_AGID,");
            sql.addSql("   BBS_THRE_INF.BTI_EGID as BTI_EGID,");
            sql.addSql("   BBS_THRE_INF.BTI_IMPORTANCE as BTI_IMPORTANCE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF,");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" where ");
            sql.addSql("   BBS_THRE_INF.BTI_SID=BBS_THRE_SUM.BTI_SID");
            sql.addSql(" and ");
            String fromDateTmp = date.toSQLTimestamp().toString();
            sql.addSql("   BBS_THRE_SUM.BTS_WRT_DATE < cast('" + fromDateTmp + "'  as timestamp)");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreInfFromRs(rs));
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
     * <p>フォーラムごとに掲示期限を過ぎたスレッド情報を取得する
     * @param forSid フォーラムSID
     * @param delDate 削除基準日時
     * @return ArrayList in BBS_THRE_INF Model
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsThreInfModel> getOverLimitThreData(int forSid, UDate delDate)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreInfModel> ret = new ArrayList<BbsThreInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_LIMIT = ?");
            sql.addSql(" and ");
            sql.addSql("   BFI_SID = ?");


            UDate limitDate = UDate.getInstanceStr(delDate.getDateString());
            limitDate.setZeroHhMmSs();
            String limitDateTmp = delDate.toSQLTimestamp().toString();
            sql.addSql(" and ");
            sql.addSql("   BTI_LIMIT_DATE < cast('" + limitDateTmp + "' as timestamp)");

            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            sql.addIntValue(forSid);


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreInfFromRs(rs));
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
     * <p>Select BBS_THRE_INF
     * @param btiSid BTI_SID
     * @return BBS_THRE_INFModel
     * @throws SQLException SQL実行例外
     */
    public BbsThreInfModel select(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsThreInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsThreInfFromRs(rs);
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
     * <p>指定したスレッドが存在するかカウントする
     * @param btiSid BTI_SID
     * @return BBS_THRE_INFModel
     * @throws SQLException SQL実行例外
     */
    public int countBti(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>全てのスレッド情報を取得する
     * @return ArrayList in BBS_THRE_INF Model
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsThreInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreInfModel> ret = new ArrayList<BbsThreInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreInfFromRs(rs));
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
     * <p>全てのスレッド情報を取得する
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return ArrayList in BBS_THRE_INF Model
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BbsThreInfModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreInfModel> ret = new ArrayList<BbsThreInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID,");
            sql.addSql("   BTI_IMPORTANCE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" order by ");
            sql.addSql("   BTI_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreInfFromRs(rs));
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
     * @return ArrayList in BBS_THRE_INF Model
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
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");

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
     * <p>指定したフォーラムのスレッドSIDリストを取得する。
     * @param bfiSid フォーラムSID
     * @return BBS_THRE_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public String[] getThreList(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList < String > list = new ArrayList < String >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_TITLE,");
            sql.addSql("   BTI_AUID,");
            sql.addSql("   BTI_ADATE,");
            sql.addSql("   BTI_EUID,");
            sql.addSql("   BTI_EDATE,");
            sql.addSql("   BTI_LIMIT,");
            sql.addSql("   BTI_LIMIT_FR_DATE,");
            sql.addSql("   BTI_LIMIT_DATE,");
            sql.addSql("   BTI_AGID,");
            sql.addSql("   BTI_EGID");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(String.valueOf(rs.getInt("BTI_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * <p>Delete BBS_THRE_INF
     * @param btiSid スレッドSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

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
     * <p>スレッドSIDより投稿者を取得する
     * @param btiSid スレッドSID
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public String getThreUser(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("    select");
            sql.addSql("      case");
            sql.addSql("        when BTI_EGID = 0 ");
            sql.addSql("          THEN CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
            sql.addSql("        when BTI_EGID IS NULL ");
            sql.addSql("          THEN CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
            sql.addSql("        else CMN_GROUPM.GRP_NAME");
            sql.addSql("      end as POST_NAME");
            sql.addSql("    from");
            sql.addSql("      BBS_THRE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_USRM_INF");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_THRE_INF.BTI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    where");
            sql.addSql("      BTI_SID = ?");
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("POST_NAME");
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
     * <p>Create BBS_THRE_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsThreInfModel
     * @throws SQLException SQL実行例外
     */
    private BbsThreInfModel __getBbsThreInfFromRs(ResultSet rs) throws SQLException {
        BbsThreInfModel bean = new BbsThreInfModel();
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiTitle(rs.getString("BTI_TITLE"));
        bean.setBtiAuid(rs.getInt("BTI_AUID"));
        bean.setBtiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BTI_ADATE")));
        bean.setBtiEuid(rs.getInt("BTI_EUID"));
        bean.setBtiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BTI_EDATE")));
        bean.setBtiLimit(rs.getInt("BTI_LIMIT"));
        bean.setBtiLimitFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_FR_DATE")));
        bean.setBtiLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("BTI_LIMIT_DATE")));
        bean.setBtiAgid(rs.getInt("BTI_AGID"));
        bean.setBtiEgid(rs.getInt("BTI_EGID"));
        bean.setBtiImportance(rs.getInt("BTI_IMPORTANCE"));
        return bean;
    }
}
