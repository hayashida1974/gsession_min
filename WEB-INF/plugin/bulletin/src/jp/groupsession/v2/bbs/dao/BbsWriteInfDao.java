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
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;

/**
 * <p>BBS_WRITE_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsWriteInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsWriteInfDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsWriteInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsWriteInfDao(Connection con) {
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
            sql.addSql("drop table BBS_WRITE_INF");

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
            sql.addSql(" create table BBS_WRITE_INF (");
            sql.addSql("   BWI_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BTI_SID NUMBER(10,0) not null,");
            sql.addSql("   BWI_VALUE text");
            sql.addSql("   BWI_VALUE_PLAIN text,");
            sql.addSql("   BWI_TYPE NUMBER(10,0) not null,");
            sql.addSql("   BWI_AUID NUMBER(10,0) not null,");
            sql.addSql("   BWI_ADATE varchar(26) not null,");
            sql.addSql("   BWI_EUID NUMBER(10,0) not null,");
            sql.addSql("   BWI_EDATE varchar(26) not null,");
            sql.addSql("   BWI_AGID NUMBER(10,0),");
            sql.addSql("   BWI_EGID NUMBER(10,0),");
           sql.addSql("   primary key (BWI_SID)");
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
     * <p>Insert BBS_WRITE_INF Data Bindding JavaBean
     * @param bean BBS_WRITE_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsWriteInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_WRITE_INF(");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
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
            sql.addIntValue(bean.getBwiSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addStrValue(bean.getBwiValue());
            sql.addStrValue(bean.getBwiValuePlain());
            sql.addIntValue(bean.getBwiType());
            sql.addIntValue(bean.getBwiAuid());
            sql.addDateValue(bean.getBwiAdate());
            sql.addIntValue(bean.getBwiEuid());
            sql.addDateValue(bean.getBwiEdate());
            sql.addIntValue(bean.getBwiAgid());
            sql.addIntValue(bean.getBwiEgid());
            sql.addIntValue(bean.getBwiParentFlg());
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
     * <p>Insert BBS_WRITE_INF Data Bindding JavaBean
     * @param beanList BBS_WRITE_INF DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<BbsWriteInfModel> beanList) throws SQLException {

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
            sql.addSql(" BBS_WRITE_INF(");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
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

            for (BbsWriteInfModel bean : beanList) {
                sql.addIntValue(bean.getBwiSid());
                sql.addIntValue(bean.getBfiSid());
                sql.addIntValue(bean.getBtiSid());
                sql.addStrValue(bean.getBwiValue());
                sql.addStrValue(bean.getBwiValuePlain());
                sql.addIntValue(bean.getBwiType());
                sql.addIntValue(bean.getBwiAuid());
                sql.addDateValue(bean.getBwiAdate());
                sql.addIntValue(bean.getBwiEuid());
                sql.addDateValue(bean.getBwiEdate());
                sql.addIntValue(bean.getBwiAgid());
                sql.addIntValue(bean.getBwiEgid());
                sql.addIntValue(bean.getBwiParentFlg());
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
     * <p>Update BBS_WRITE_INF Data Bindding JavaBean
     * @param bean BBS_WRITE_INF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsWriteInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" set ");
            sql.addSql("   BFI_SID=?,");
            sql.addSql("   BTI_SID=?,");
            sql.addSql("   BWI_VALUE=?,");
            sql.addSql("   BWI_VALUE_PLAIN=?,");
            sql.addSql("   BWI_TYPE=?,");
            sql.addSql("   BWI_AUID=?,");
            sql.addSql("   BWI_ADATE=?,");
            sql.addSql("   BWI_EUID=?,");
            sql.addSql("   BWI_EDATE=?,");
            sql.addSql("   BWI_AGID=?,");
            sql.addSql("   BWI_EGID=?");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addStrValue(bean.getBwiValue());
            sql.addStrValue(bean.getBwiValuePlain());
            sql.addIntValue(bean.getBwiType());
            sql.addIntValue(bean.getBwiAuid());
            sql.addDateValue(bean.getBwiAdate());
            sql.addIntValue(bean.getBwiEuid());
            sql.addDateValue(bean.getBwiEdate());
            sql.addIntValue(bean.getBwiAgid());
            sql.addIntValue(bean.getBwiEgid());
            //where
            sql.addIntValue(bean.getBwiSid());

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
     * <p>投稿情報の更新を行う
     * @param bean 更新情報
     * @throws SQLException SQL実行例外
     */
    public void updateWriteInf(BbsWriteInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" set ");
            sql.addSql("   BWI_VALUE=?,");
            sql.addSql("   BWI_VALUE_PLAIN=?,");
            sql.addSql("   BWI_TYPE=?,");
            sql.addSql("   BWI_EUID=?,");
            if (bean.getBwiAdate() != null) {
                sql.addSql("   BWI_ADATE=?,");
            }
            sql.addSql("   BWI_EDATE=?,");
            sql.addSql("   BWI_EGID=?,");
            sql.addSql("   BWI_PARENT_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBwiValue());
            sql.addStrValue(bean.getBwiValuePlain());
            sql.addIntValue(bean.getBwiType());
            sql.addIntValue(bean.getBwiEuid());
            if (bean.getBwiAdate() != null) {
                sql.addDateValue(bean.getBwiAdate());
            }
            sql.addDateValue(bean.getBwiEdate());
            sql.addIntValue(bean.getBwiEgid());
            sql.addIntValue(bean.getBwiParentFlg());
           //where
            sql.addIntValue(bean.getBwiSid());

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
     * <p>Select BBS_WRITE_INF
     * @return BBS_WRITE_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsWriteInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsWriteInfModel> ret = new ArrayList<BbsWriteInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsWriteInfFromRs(rs));
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
     * <p>Select BBS_WRITE_INF
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return BBS_WRITE_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsWriteInfModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsWriteInfModel> ret = new ArrayList<BbsWriteInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" order by ");
            sql.addSql("   BWI_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsWriteInfFromRs(rs));
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
     * <p>全投稿件数を取得する
     * @return BBS_WRITE_INFModel
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
            sql.addSql("   BBS_WRITE_INF");

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
     * <p>Select BBS_WRITE_INF
     * @param bwiSid 投稿SID
     * @return BBS_WRITE_INFModel
     * @throws SQLException SQL実行例外
     */
    public BbsWriteInfModel select(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsWriteInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsWriteInfFromRs(rs);
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
     * <p>Select BBS_WRITE_INF
     * @param btiSid スレッドSID
     * @return BBS_WRITE_INFModel
     * @throws SQLException SQL実行例外
     */
    public BbsWriteInfModel selectByBti(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsWriteInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsWriteInfFromRs(rs);
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
     * <p>Select BBS_WRITE_INF
     * @param bwiSid 投稿SID
     * @return BBS_WRITE_INFModel
     * @throws SQLException SQL実行例外
     */
    public int countBwi(int bwiSid) throws SQLException {

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
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>フォーラム内の投稿数を取得する
     * @param bfiSid フォーラムSID
     * @param now 現在日時
     * @return 投稿数
     * @throws SQLException SQL実行例外
     */
    public int getWriteCountInForum(int bfiSid, UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BBS_WRITE_INF.BWI_SID) as writeCnt");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF,");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BBS_WRITE_INF.BFI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   BBS_WRITE_INF.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("        and");
            sql.addSql("          BBS_THRE_INF.BTI_LIMIT_DATE >= ?");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addIntValue(bfiSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);

            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("writeCnt");
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
     * <p>スレッド内の投稿数を取得する
     * @param btiSid スレッドSID
     * @return 投稿数
     * @throws SQLException SQL実行例外
     */
    public int getWriteCountInThread(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BWI_SID) as writeCnt");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("writeCnt");
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
     * <p>投稿の新規登録ユーザを取得する
     * @param bwiSid 投稿SID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     */
    public int getWriteAuid(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int auid = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_AUID");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                auid = rs.getInt("BWI_AUID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return auid;
    }
    /**
     * <br>[機  能] スレッド内の投稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 投稿情報
     * @throws SQLException SQL実行例外
     */
    public List<BbsWriteInfModel> getWriteList(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsWriteInfModel> ret = new ArrayList<BbsWriteInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BWI_VALUE,");
            sql.addSql("   BWI_VALUE_PLAIN,");
            sql.addSql("   BWI_TYPE,");
            sql.addSql("   BWI_AUID,");
            sql.addSql("   BWI_ADATE,");
            sql.addSql("   BWI_EUID,");
            sql.addSql("   BWI_EDATE,");
            sql.addSql("   BWI_AGID,");
            sql.addSql("   BWI_EGID,");
            sql.addSql("   BWI_PARENT_FLG");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where");
            sql.addSql("   BTI_SID=?");

            sql.addIntValue(btiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsWriteInfFromRs(rs));
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
     * <p>フォーラムSIDとスレッドSIDより最初の投稿SIDを取得する
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public int getFirstBwiSid(int bfiSid, int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int auid = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MIN(BWI_SID) as MIN");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BTI_SID=?");
            sql.addIntValue(bfiSid);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                auid = rs.getInt("MIN");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return auid;
    }

    /**
     * <br>[機  能] フォーラム内の最新更新日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    public UDate getForMaxEdate(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate maxEdate = null;
        UDate now = new UDate();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   Max(BWI_EDATE) as BWI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   BWI_ADATE < ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.addDateValue(now);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxEdate = UDate.getInstanceTimestamp(rs.getTimestamp("BWI_EDATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxEdate;
    }

    /**
     * <br>[機  能] スレッド内の最新更新日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return BBS_FOR_SUMModel
     * @throws SQLException SQL実行例外
     */
    public UDate getThreMaxEdate(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate now = new UDate();
        UDate maxEdate = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   case ");
            sql.addSql("     when ");
            sql.addSql("       MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) != null ");
            sql.addSql("       and MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) > ? ");
            sql.addSql("         then ");
            sql.addSql("           MAX(BBS_THRE_INF.BTI_LIMIT_FR_DATE) ");
            sql.addSql("     else ");
            sql.addSql("       MAX(BBS_WRITE_INF.BWI_EDATE) ");
            sql.addSql("   end TARGET ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_INF, ");
            sql.addSql("   BBS_WRITE_INF ");
            sql.addSql(" where ");
            sql.addSql("   BBS_WRITE_INF.BTI_SID = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(now);
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxEdate = UDate.getInstanceTimestamp(rs.getTimestamp("TARGET"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxEdate;
    }

    /**
     * <p>Delete BBS_WRITE_INF
     * @param bwiSid 投稿SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

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
     * <p>指定されたスレッド内の投稿情報を削除する
     * @param btiSid スレッドSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteWriteInThread(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
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
     * <p>Create BBS_WRITE_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsWriteInfModel
     * @throws SQLException SQL実行例外
     */
    private BbsWriteInfModel __getBbsWriteInfFromRs(ResultSet rs) throws SQLException {
        BbsWriteInfModel bean = new BbsWriteInfModel();
        bean.setBwiSid(rs.getInt("BWI_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBwiValue(rs.getString("BWI_VALUE"));
        bean.setBwiValuePlain(rs.getString("BWI_VALUE_PLAIN"));
        bean.setBwiType(rs.getInt("BWI_TYPE"));
        bean.setBwiAuid(rs.getInt("BWI_AUID"));
        bean.setBwiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BWI_ADATE")));
        bean.setBwiEuid(rs.getInt("BWI_EUID"));
        bean.setBwiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BWI_EDATE")));
        bean.setBwiAgid(rs.getInt("BWI_AGID"));
        bean.setBwiEgid(rs.getInt("BWI_EGID"));
        bean.setBwiParentFlg(rs.getInt("BWI_PARENT_FLG"));
        return bean;
    }

    /**
     * <br>[機  能] 指定された投稿のフォーラムを変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param forumSid 移動先フォーラムSID
     * @param threadSidList 移動スレッドSIDリスト
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int updateWriForMove(int forumSid, String[] threadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    BBS_WRITE_INF");
            sql.addSql("  set");
            sql.addSql("    BFI_SID = ?");
            sql.addIntValue(forumSid);
            sql.addSql("  where");
            sql.addSql("    BTI_SID in (");
            boolean sepFlg = false;
            for (String sid : threadSidList) {
                if (sepFlg) {
                    sql.addSql("    ,");
                } else {
                    sepFlg = true;
                }
                sql.addSql("    ?");
                sql.addIntValue(Integer.parseInt(sid));
            }
            sql.addSql("    )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <p>投稿SIDより投稿者を取得する
     * @param bwiSid 投稿SID
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public String getPostUser(int bwiSid) throws SQLException {

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
            sql.addSql("        when BWI_EGID = 0 ");
            sql.addSql("          THEN CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
            sql.addSql("        when BWI_EGID IS NULL ");
            sql.addSql("          THEN CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
            sql.addSql("        else CMN_GROUPM.GRP_NAME");
            sql.addSql("      end as POST_NAME");
            sql.addSql("    from");
            sql.addSql("      BBS_WRITE_INF");
            sql.addSql("    left join");
            sql.addSql("      CMN_USRM_INF");
            sql.addSql("    on");
            sql.addSql("      BBS_WRITE_INF.BWI_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("    left join");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    on");
            sql.addSql("      BBS_WRITE_INF.BWI_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("    where");
            sql.addSql("      BWI_SID = ?");
            sql.addIntValue(bwiSid);

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
}
