package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngRndataModel;

/**
 * <p>RNG_RNDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngRndataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngRndataDao.class);

    /**
     * <p>Default Constructor
     */
    public RngRndataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngRndataDao(Connection con) {
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
            sql.addSql("drop table RNG_RNDATA");

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
            sql.addSql(" create table RNG_RNDATA (");
            sql.addSql("   RNG_SID NUMBER(10,0) not null,");
            sql.addSql("   RNG_TITLE varchar(100) not null,");
            sql.addSql("   RNG_MAKEDATE varchar(26) not null,");
            sql.addSql("   RNG_APPLICATE NUMBER(10,0),");
            sql.addSql("   RNG_APPLDATE varchar(26),");
            sql.addSql("   RNG_STATUS NUMBER(10,0) not null,");
            sql.addSql("   RNG_COMPFLG NUMBER(10,0) not null,");
            sql.addSql("   RNG_ADMCOMMENT varchar(300),");
            sql.addSql("   RNG_AUID NUMBER(10,0) not null,");
            sql.addSql("   RNG_ADATE varchar(26) not null,");
            sql.addSql("   RNG_EUID NUMBER(10,0) not null,");
            sql.addSql("   RNG_EDATE varchar(26) not null,");
            sql.addSql("   RNG_ID         varchar(120),");
            sql.addSql("   RTP_SID        integer        not null,");
            sql.addSql("   RTP_VER        integer        not null,");
            sql.addSql("   RCT_VER        integer        not null,");
            sql.addSql("   primary key (RNG_SID)");
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
     * <p>Insert RNG_RNDATA Data Bindding JavaBean
     * @param bean RNG_RNDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngRndataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_RNDATA(");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addStrValue(bean.getRngTitle());
            sql.addDateValue(bean.getRngMakedate());
            sql.addIntValue(bean.getRngApplicate());
            sql.addDateValue(bean.getRngAppldate());
            sql.addIntValue(bean.getRngStatus());
            sql.addIntValue(bean.getRngCompflg());
            sql.addStrValue(bean.getRngAdmcomment());
            sql.addIntValue(bean.getRngAuid());
            sql.addDateValue(bean.getRngAdate());
            sql.addIntValue(bean.getRngEuid());
            sql.addDateValue(bean.getRngEdate());
            sql.addStrValue(bean.getRngId());
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRctVer());

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
     * <p>Insert RNG_RNDATA Data Bindding JavaBean
     * @param beanList RNG_RNDATA DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngRndataModel> beanList) throws SQLException {

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
            sql.addSql(" RNG_RNDATA(");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngRndataModel bean : beanList) {
                sql.addIntValue(bean.getRngSid());
                sql.addStrValue(bean.getRngTitle());
                sql.addDateValue(bean.getRngMakedate());
                sql.addIntValue(bean.getRngApplicate());
                sql.addDateValue(bean.getRngAppldate());
                sql.addIntValue(bean.getRngStatus());
                sql.addIntValue(bean.getRngCompflg());
                sql.addStrValue(bean.getRngAdmcomment());
                sql.addStrValue(bean.getRngId());
                sql.addIntValue(bean.getRtpSid());
                sql.addIntValue(bean.getRtpVer());
                sql.addIntValue(bean.getRctVer());
                sql.addIntValue(bean.getRngAuid());
                sql.addDateValue(bean.getRngAdate());
                sql.addIntValue(bean.getRngEuid());
                sql.addDateValue(bean.getRngEdate());
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
     * <p>Update RNG_RNDATA Data Bindding JavaBean
     * @param bean RNG_RNDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int update(RngRndataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" set ");
            sql.addSql("   RNG_TITLE=?,");
            sql.addSql("   RNG_MAKEDATE=?,");
            sql.addSql("   RNG_APPLICATE=?,");
            sql.addSql("   RNG_APPLDATE=?,");
            sql.addSql("   RNG_STATUS=?,");
            sql.addSql("   RNG_COMPFLG=?,");
            sql.addSql("   RNG_ADMCOMMENT=?,");
            sql.addSql("   RNG_EUID=?,");
            sql.addSql("   RNG_EDATE=?,");
            sql.addSql("   RNG_ID=?,");
            sql.addSql("   RTP_SID=?,");
            sql.addSql("   RTP_VER=?,");
            sql.addSql("   RCT_VER=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRngTitle());
            sql.addDateValue(bean.getRngMakedate());
            sql.addIntValue(bean.getRngApplicate());
            sql.addDateValue(bean.getRngAppldate());
            sql.addIntValue(bean.getRngStatus());
            sql.addIntValue(bean.getRngCompflg());
            sql.addStrValue(bean.getRngAdmcomment());
            sql.addIntValue(bean.getRngEuid());
            sql.addDateValue(bean.getRngEdate());
            sql.addStrValue(bean.getRngId());
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRctVer());
            //where
            sql.addIntValue(bean.getRngSid());

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
     * <p>Select RNG_RNDATA All Data
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngRndataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngRndataModel> ret = new ArrayList<RngRndataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_RNDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngRndataFromRs(rs));
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
     * <p>Select RNG_RNDATA All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngRndataModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngRndataModel> ret = new ArrayList<RngRndataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" order by ");
            sql.addSql("   RNG_SID asc");

            sql.setPagingValue(offset, limit);


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngRndataFromRs(rs));
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
     * <p>count RNG_RNDATA All Data
     * @return 件数
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
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_RNDATA");

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
     * <p>Select RNG_RNDATA
     * @param rngSid 稟議SID
     * @return RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public RngRndataModel select(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngRndataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngRndataFromRs(rs);
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
     * <p>Select RNG_RNDATA
     * @param rngSids 稟議SID一覧
     * @return RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RngRndataModel> selectList(List<Integer> rngSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngRndataModel> ret = new ArrayList<RngRndataModel>();
        con = getCon();

        if (rngSids == null || rngSids.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_MAKEDATE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   RNG_APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RNG_ID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   RNG_AUID,");
            sql.addSql("   RNG_ADATE,");
            sql.addSql("   RNG_EUID,");
            sql.addSql("   RNG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            int idx = 0;
            for (Integer rngSid : rngSids) {
                sql.addSql((idx > 0 ? "     ,?" : "     ?"));
                sql.addIntValue(rngSid.intValue());
                idx++;
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getRngRndataFromRs(rs));
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
     * <p>Delete RNG_RNDATA
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

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
     * <br>[機  能] 稟議を完了状態に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議情報
     * @param isUpdStatus 状態の更新を行うか否か true:更新する false:更新しない
     * @throws SQLException SQL実行例外
     */
    public void completeRingi(RngRndataModel bean, boolean isUpdStatus)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" set ");
            if (isUpdStatus) {
                sql.addSql("   RNG_STATUS=?,");
                sql.addIntValue(bean.getRngStatus());
            }
            sql.addSql("   RNG_COMPFLG=?,");
            sql.addSql("   RNG_ADMCOMMENT=?,");
            sql.addSql("   RNG_EUID=?,");
            sql.addSql("   RNG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
            sql.addStrValue(bean.getRngAdmcomment());
            sql.addIntValue(bean.getRngEuid());
            sql.addDateValue(bean.getRngEdate());
            //where
            sql.addIntValue(bean.getRngSid());

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
     * <br>[機  能] 稟議の状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議情報
     * @throws SQLException SQL実行例外
     */
    public void updateRingiStatus(RngRndataModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" set");
            sql.addSql("   RNG_STATUS=?,");
            sql.addSql("   RNG_EUID=?,");
            sql.addSql("   RNG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(bean.getRngStatus());
            sql.addIntValue(bean.getRngEuid());
            sql.addDateValue(bean.getRngEdate());
            //where
            sql.addIntValue(bean.getRngSid());

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
     * <br>[機  能] 稟議の状態を取り消しする
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param usrSid ユーザSID
     * @param date   変更日
     * @throws SQLException SQL実行例外
     */
    public void updateTorisage(int rngSid, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" set");
            sql.addSql("   RNG_STATUS=?,");
            sql.addSql("   RNG_COMPFLG=?,");
            sql.addSql("   RNG_EUID=?,");
            sql.addSql("   RNG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            //where
            sql.addIntValue(RngConst.RNG_STATUS_TORISAGE);
            sql.addIntValue(1);
            sql.addIntValue(usrSid);
            sql.addDateValue(date);

            sql.addIntValue(rngSid);

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
     * <p>Select RNG_RNDATA
     * @param usrSid ユーザSID
     * @return RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public boolean nextDelete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_JKBN = 9");
            sql.addSql(" and ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("USR_SID") > 0) {
                    ret = true;
                }
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
     * <p>Create RNG_RNDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngRndataModel
     * @throws SQLException SQL実行例外
     */
    private RngRndataModel __getRngRndataFromRs(ResultSet rs) throws SQLException {
        RngRndataModel bean = new RngRndataModel();
        bean.setRngSid(rs.getInt("RNG_SID"));
        bean.setRngTitle(rs.getString("RNG_TITLE"));
        bean.setRngMakedate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_MAKEDATE")));
        bean.setRngApplicate(rs.getInt("RNG_APPLICATE"));
        bean.setRngAppldate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_APPLDATE")));
        bean.setRngStatus(rs.getInt("RNG_STATUS"));
        bean.setRngCompflg(rs.getInt("RNG_COMPFLG"));
        bean.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
        bean.setRngAuid(rs.getInt("RNG_AUID"));
        bean.setRngAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_ADATE")));
        bean.setRngEuid(rs.getInt("RNG_EUID"));
        bean.setRngEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_EDATE")));
        bean.setRngId(rs.getString("RNG_ID"));
        bean.setRtpSid(rs.getInt("RTP_SID"));
        bean.setRtpVer(rs.getInt("RTP_VER"));
        bean.setRctVer(rs.getInt("RCT_VER"));

        return bean;
    }
}
