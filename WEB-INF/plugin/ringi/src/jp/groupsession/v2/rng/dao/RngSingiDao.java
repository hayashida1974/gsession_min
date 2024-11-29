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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngSingiModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>RNG_SINGI Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngSingiDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngSingiDao.class);

    /**
     * <p>Default Constructor
     */
    public RngSingiDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngSingiDao(Connection con) {
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
            sql.addSql("drop table RNG_SINGI");

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
            sql.addSql(" create table RNG_SINGI (");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   RNG_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID_KOETU NUMBER(10,0) not null,");
            sql.addSql("   USR_SID_DAIRI NUMBER(10,0) not null,");
            sql.addSql("   RSS_STATUS NUMBER(10,0) not null,");
            sql.addSql("   RSS_COMMENT varchar(300),");
            sql.addSql("   RSS_CHKDATE varchar(23),");
            sql.addSql("   RSS_AUID NUMBER(10,0) not null,");
            sql.addSql("   RSS_ADATE varchar(23) not null,");
            sql.addSql("   RSS_EUID NUMBER(10,0) not null,");
            sql.addSql("   RSS_EDATE varchar(23) not null,");
            sql.addSql("   primary key (RKS_SID,USR_SID)");
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
     * <p>Insert RNG_SINGI Data Bindding JavaBean
     * @param bean RNG_SINGI Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngSingiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_SINGI(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getRssStatus());
            sql.addStrValue(bean.getRssComment());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssAuid());
            sql.addDateValue(bean.getRssAdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
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
     * <p>Insert RNG_SINGI Data Bindding JavaBean
     * @param rssList 稟議審議モデルリスト
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngSingiModel> rssList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_SINGI(");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngSingiModel bean : rssList) {
                sql.addIntValue(bean.getRksSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getRngSid());
                sql.addIntValue(bean.getUsrSidKoetu());
                sql.addIntValue(bean.getUsrSidDairi());
                sql.addIntValue(bean.getRssStatus());
                sql.addStrValue(bean.getRssComment());
                sql.addDateValue(bean.getRssChkdate());
                sql.addIntValue(bean.getRssAuid());
                sql.addDateValue(bean.getRssAdate());
                sql.addIntValue(bean.getRssEuid());
                sql.addDateValue(bean.getRssEdate());
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
     * <p>Update RNG_SINGI Data Bindding JavaBean
     * @param bean RNG_SINGI Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngSingiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RNG_SID=?,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   RSS_COMMENT=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_AUID=?,");
            sql.addSql("   RSS_ADATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getRssStatus());
            sql.addStrValue(bean.getRssComment());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssAuid());
            sql.addDateValue(bean.getRssAdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select RNG_SINGI All Data
     * @return List in RNG_SINGIModel
     * @throws SQLException SQL実行例外
     */
    public List<RngSingiModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngSingiModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngSingiFromRs(rs));
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
     * <p>Select RNG_SINGI All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngSingiModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngSingiModel> ret = new ArrayList<RngSingiModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" order by ");
            sql.addSql("   RKS_SID asc");

            sql.setPagingValue(offset, limit);


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngSingiFromRs(rs));
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
     * <p>count  All Data
     * @return count
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
            sql.addSql("   RNG_SINGI");

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
     * <p>Select RNG_SINGI
     * @param rksSid RKS_SID
     * @param usrSid USR_SID
     * @return RNG_SINGIModel
     * @throws SQLException SQL実行例外
     */
    public RngSingiModel select(int rksSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngSingiModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngSingiFromRs(rs);
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
     * <br>[機  能] 審議情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public RngSingiModel singiSelect(int rksSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        RngSingiModel singiMdl = new RngSingiModel();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RNG_SID,");
            sql.addSql("   USR_SID_KOETU,");
            sql.addSql("   USR_SID_DAIRI,");
            sql.addSql("   RSS_STATUS,");
            sql.addSql("   RSS_COMMENT,");
            sql.addSql("   RSS_CHKDATE,");
            sql.addSql("   RSS_AUID,");
            sql.addSql("   RSS_ADATE,");
            sql.addSql("   RSS_EUID,");
            sql.addSql("   RSS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SINGI.RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID = ?");

            sql.addIntValue(rksSid);
            sql.addIntValue(usrSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                singiMdl = __getRngSingiFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return singiMdl;
    }

    /**
     * <p>Delete RNG_SINGI
     * @param rksSid RKS_SID
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rksSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rksSid);
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
     * <p>Delete RNG_SINGI
     * @param rngSid RKS_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteSelectedRingi(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
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
     * <br>[機  能] 稟議審議情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSingi(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   RSS_COMMENT=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");

            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addStrValue(bean.getRssComment());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <br>[機  能] 稟議審議情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateConfirm(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   RSS_COMMENT=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID IN");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RKS_SID");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    where");
            sql.addSql("      RNG_SID = ?");
            sql.addSql("    and");
            sql.addSql("      RKS_ROLL_TYPE = ?");
            sql.addSql("   )");

            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addStrValue(bean.getRssComment());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            //サブクエリ内
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);

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
     * <br>[機  能] 確認時経路上全員が確認をしているか
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param usrSid ユーザSID
     * @return
     * @return
     * @throws SQLException SQL実行例外
     * @return 達成済みのRKSSID
     */
    public List<Integer> checkConfirm(int rngSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Integer>  nRtn = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("    select");
            sql.addSql("      RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("      case  when COUNT.NOSET_USER IS NULL then 0");
            sql.addSql("            else COUNT.NOSET_USER ");
            sql.addSql("      end NOSET_USER ");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP, ");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         RKS_SID,");
            sql.addSql("         SUM(case RNG_SINGI.RSS_STATUS");
            sql.addSql("              when ? then 1 ");
            sql.addSql("              when ? then 1 ");
            sql.addSql("              else 0 end) as NOSET_USER ");
            sql.addSql("       from");
            sql.addSql("         RNG_SINGI");
            sql.addSql("       where");
            sql.addSql("         RKS_SID IN ");
            sql.addSql("         ( ");
            sql.addSql("          select ");
            sql.addSql("            RNG_SINGI.RKS_SID ");
            sql.addSql("          from ");
            sql.addSql("            RNG_SINGI ");
            sql.addSql("          where ");
            sql.addSql("            RNG_SINGI.RNG_SID = ? ");
            sql.addSql("          and ");
            sql.addSql("            RNG_SINGI.USR_SID = ? ");
            sql.addSql("         ) ");
            sql.addSql("       group by ");
            sql.addSql("         RKS_SID ");
            sql.addSql("      ) COUNT");
            sql.addSql("    where");
            sql.addSql("    RNG_KEIRO_STEP.RNG_SID = ?");
            sql.addSql("    and RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
            sql.addSql("    and RNG_KEIRO_STEP.RKS_SID = COUNT.RKS_SID");

            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(rngSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("NOSET_USER") == 0) {
                    nRtn.add(rs.getInt("RKS_SID"));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return nRtn;
    }

    /**
     * <br>[機  能] 確認時経路上全員が確認をしているか
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @return
     * @return
     * @throws SQLException SQL実行例外
     * @return 達成済みのRKSSID
     */
    public List<Integer> checkConfirm(int rksSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Integer>  nRtn = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("    select");
            sql.addSql("      SID.RKS_SID,");
            sql.addSql("      case  when COUNT.NOSET_USER IS NULL then 0");
            sql.addSql("            else COUNT.NOSET_USER ");
            sql.addSql("      end NOSET_USER ");
            sql.addSql("    from");
            sql.addSql("      (   ");
            sql.addSql("       select");
            sql.addSql("         RKS_SID");
            sql.addSql("       from");
            sql.addSql("         RNG_SINGI");
            sql.addSql("       where");
            sql.addSql("         RNG_SINGI.RKS_SID = ?");
            sql.addSql("      ) SID");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         RKS_SID,");
            sql.addSql("         COUNT(RNG_SINGI.RKS_SID) as NOSET_USER ");
            sql.addSql("       from");
            sql.addSql("         RNG_SINGI");
            sql.addSql("       where");
            sql.addSql("         (");
            sql.addSql("            RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("          or");
            sql.addSql("            RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("         )");
            sql.addSql("       and");
            sql.addSql("         RKS_SID IN ");
            sql.addSql("         ( ");
            sql.addSql("          select ");
            sql.addSql("            RNG_SINGI.RKS_SID ");
            sql.addSql("          from ");
            sql.addSql("            RNG_SINGI ");
            sql.addSql("          left join ");
            sql.addSql("            RNG_KEIRO_STEP ");
            sql.addSql("          on ");
            sql.addSql("            RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID ");
            sql.addSql("          where ");
            sql.addSql("            RNG_SINGI.RKS_SID = ? ");
            sql.addSql("         ) ");
            sql.addSql("       group by ");
            sql.addSql("         RKS_SID ");
            sql.addSql("      ) COUNT");
            sql.addSql("    on");
            sql.addSql("      SID.RKS_SID = COUNT.RKS_SID");

            sql.addIntValue(rksSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(rksSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("NOSET_USER") == 0) {
                    nRtn.add(rs.getInt("RKS_SID"));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return nRtn;
    }


    /**
     * <br>[機  能] 稟議審議情報の任意のソート番号の審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param sortNo ソート番号
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSortSingi(RngSingiModel bean, int sortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=(select RKS_SID from RNG_KEIRO_STEP ");
            sql.addSql("            where RKS_SORT = ? and RNG_SID = ?)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssStatus());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            // where
            sql.addIntValue(sortNo);
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
     * <br>[機  能] 稟議審議情報のスキップされる審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param nSkipNo スキップされるソートNo
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSkipSingi(RngSingiModel bean, int nSkipNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN (select");
            sql.addSql("                 RKS_SID");
            sql.addSql("               from");
            sql.addSql("                 RNG_KEIRO_STEP");
            sql.addSql("               where");
            sql.addSql("                 RKS_SORT <= ?");
            sql.addSql("               and");
            sql.addSql("                 RNG_SID = ?)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRssStatus());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            // where
            sql.addIntValue(nSkipNo);
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
     * <br>[機  能] 稟議審議情報の後閲指示される審議者の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param nStartSortNo  スキップ開始ソートNo
     * @param nFinishSortNo スキップ終了ソートNo
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateKoetuSingi(RngSingiModel bean, int nStartSortNo, int nFinishSortNo)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   USR_SID_KOETU=?,");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN (select");
            sql.addSql("                 RKS_SID");
            sql.addSql("               from");
            sql.addSql("                 RNG_KEIRO_STEP");
            sql.addSql("               where");
            sql.addSql("                 RKS_SORT <= ?");
            sql.addSql("               and");
            sql.addSql("                 RKS_SORT >= ?");
            sql.addSql("               and");
            sql.addSql("                 RNG_SID = ?)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(-1);
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addIntValue(bean.getRssStatus());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            // where
            sql.addIntValue(nFinishSortNo);
            sql.addIntValue(nStartSortNo);
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
     * <br>[機  能] 承認者(削除されたユーザ)の稟議経路ステップ情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int deleteKeiroStepForDelUser(int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   RKS_SID in (");
            sql.addSql("              select");
            sql.addSql("                RKS_SID");
            sql.addSql("              from");
            sql.addSql("                RNG_KEIRO_STEP");
            sql.addSql("              where");
            sql.addSql("                RNG_SID = ? ");
            sql.addSql("              and");
            sql.addSql("                RKS_ROLL_TYPE=?");
            sql.addSql("              )");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (");
            sql.addSql("    select USR_SID from CMN_USRM");
            sql.addSql("    where USR_SID > ?");
            sql.addSql("    and USR_JKBN = ?");
            sql.addSql("   )");

            sql.addIntValue(rngSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
     * <p>申請者USERSID取得
     * @param rngSid RNG_SID
     * @return RNG_SINGIModel
     * @throws SQLException SQL実行例外
     */
    public int selectUserSid(int rngSid) throws SQLException {

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
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=(");
            sql.addSql("   select");
            sql.addSql("     RKS_SID");
            sql.addSql("   from");
            sql.addSql("     RNG_KEIRO_STEP");
            sql.addSql("   where");
            sql.addSql("     RNG_SID = ?");
            sql.addSql("   and");
            sql.addSql("     RKS_ROLL_TYPE = ?)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
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

    /**
     * <p>対象の稟議を確認可能なユーザを取得(重複なし)
     * @param rngSid RNG_SID
     * @param apply true：申請者を対象に含める
     * @param koetsu true：後閲可能ユーザを対象に含める
     * @return 審議者ユーザSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectUsrsCanConfirmRingi(
            int rngSid, boolean apply, boolean koetsu)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM.USR_SID = RNG_SINGI.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   RKS_SID in (");
            sql.addSql("   select");
            sql.addSql("     RKS_SID");
            sql.addSql("   from");
            sql.addSql("     RNG_KEIRO_STEP");
            sql.addSql("   where");
            sql.addSql("     RNG_SID = ?");
            sql.addSql("   and((");
            sql.addSql("     RKS_STATUS <> 0");
            sql.addSql("   and");
            sql.addSql("     RKS_ROLL_TYPE = 0");
            sql.addSql("   ) ");
            if (apply) {
                sql.addSql("    or ");
                sql.addSql("     RKS_ROLL_TYPE = 2");
            }
            if (koetsu) {
                sql.addSql("     or ");
                sql.addSql("     RKS_KOETU_SIZI = 0");
            }
            sql.addSql("     ))");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 稟議審議情報(承認者)の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateApprovalSingi(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   USR_SID_KOETU=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");

            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getRksSid());
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
     * <br>[機  能] 稟議審議情報(承認者)の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSingiStautsNoset(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   USR_SID_KOETU=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSS_STATUS=?");

            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);

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
     * <br>[機  能] 稟議審議情報(削除済みユーザ)の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @param delList 削除リスト
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSingiDelUser(RngSingiModel bean, List<Integer> delList)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   USR_SID_KOETU=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID=?");
            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            sql.addIntValue(bean.getRksSid());
            if (delList.size() == 1) {
                sql.addSql(" and ");
                sql.addSql("   USR_SID=?");
                sql.addIntValue(delList.get(0));
            } else {
                sql.addSql(" and ");
                sql.addSql("   USR_SID IN (");
                for (int idx = 0; idx < delList.size(); idx++) {
                    if (idx == 0) {
                        sql.addSql("   ?");
                    } else {
                        sql.addSql("  , ?");
                    }
                    sql.addIntValue(delList.get(idx));
                }
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
     * <br>[機  能] 稟議審議情報の更新を行う(削除済みユーザ以外)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議経路情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateSingiNotDelUser(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?,");
            sql.addSql("   USR_SID_DAIRI=?,");
            sql.addSql("   USR_SID_KOETU=?,");
            sql.addSql("   RSS_CHKDATE=?,");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SINGI.RKS_SID=?");
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID IN ");
            sql.addSql("   ( ");
            sql.addSql("     select ");
            sql.addSql("       RNG_SINGI.USR_SID ");
            sql.addSql("     from ");
            sql.addSql("       RNG_SINGI ");
            sql.addSql("     left join ");
            sql.addSql("       CMN_USRM ");
            sql.addSql("     on ");
            sql.addSql("       RNG_SINGI.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql("     where ");
            sql.addSql("       RNG_SINGI.RKS_SID = ? ");
            sql.addSql("     and ");
            sql.addSql("       CMN_USRM.USR_JKBN = ? ");
            sql.addSql("    ) ");

            sql.addIntValue(bean.getRssStatus());
            sql.addIntValue(bean.getUsrSidDairi());
            sql.addIntValue(bean.getUsrSidKoetu());
            sql.addDateValue(bean.getRssChkdate());
            sql.addIntValue(bean.getRssEuid());
            sql.addDateValue(bean.getRssEdate());
            //where
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(bean.getRksSid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
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
     * <br>[機  能] 次の承認者を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return nRtn ユーザSID
     */
    public ArrayList<Integer> nextUser(int rksSid, int rngSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ArrayList<Integer> nRtn = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            sql.addIntValue(rksSid);
            sql.addIntValue(rngSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                nRtn.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return nRtn;
    }

    /**
     * <br>[機  能] 経路ステップ情報で他に確認していないユーザがいないかの判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 稟議審議情報
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int checkNextUser(RngSingiModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(*) as SINGICOUNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSS_CHKDATE is null");

            sql.addIntValue(bean.getRksSid());

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("SINGICOUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定した稟議の経路ステップ情報のサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public long getDataSize(List<Integer> rngSidList)
    throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSS_COMMENT)) as COMMENT_SIZE");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SID in (");

            for (int idx = 0; idx < rngSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(rngSidList.get(idx));
            }

            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("COMMENT_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }
    
    /**
     * <br>[機  能] 全ての最終確認者の受信日を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID(更新者SID)
     * @param now 現在日時
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateRcvdateForConfirmUser(int rngSid, int userSid, UDate now)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_EUID=?,");
            sql.addSql("   RSS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RKS_SID IN (");
            sql.addSql("              select");
            sql.addSql("                RKS_SID");
            sql.addSql("              from");
            sql.addSql("                RNG_KEIRO_STEP");
            sql.addSql("              where");
            sql.addSql("                RNG_SID = ?");
            sql.addSql("              and");
            sql.addSql("                RKS_ROLL_TYPE = ?)");

            sql.addIntValue(userSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(rngSid);
            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);

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
     * <br>[機  能] コメント編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param comment コメント
     * @param rksSid 経路SID
     * @param userSid ユーザSID(更新者SID)
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int commentEdit(String comment, int rksSid, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_COMMENT=?");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");

            sql.addStrValue(comment);
            sql.addIntValue(rksSid);
            sql.addIntValue(userSid);

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
     * <br>[機  能] 経路ステップの審議者のSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路ステップSID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public List<Integer> getSingiUser(int rksSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        List<Integer> ret  = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addIntValue(rksSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }


    /**
     * <br>[機  能] 指定経路の人数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @throws SQLException SQL実行例外
     * @return count 人数
     */
    public int keiroUserCount(int rksSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int userCount = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(RKS_SID) as USER_COUNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");

            sql.addIntValue(rksSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userCount = rs.getInt("USER_COUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return userCount;
    }

    /**
     * <br>[機  能] 指定経路の削除されていない人数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @throws SQLException SQL実行例外
     * @return count 人数
     */
    public int keiroUserDeleteCount(int rksSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        int userCount = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(RKS_SID) as USER_COUNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on ");
            sql.addSql("   RNG_SINGI.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_JKBN = ?");

            sql.addIntValue(rksSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userCount = rs.getInt("USER_COUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return userCount;
    }

    /**
     * <p>Create RNG_SINGI Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngSingiModel
     * @throws SQLException SQL実行例外
     */
    private RngSingiModel __getRngSingiFromRs(ResultSet rs) throws SQLException {
        RngSingiModel bean = new RngSingiModel();
        bean.setRksSid(rs.getInt("RKS_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRngSid(rs.getInt("RNG_SID"));
        bean.setUsrSidKoetu(rs.getInt("USR_SID_KOETU"));
        bean.setUsrSidDairi(rs.getInt("USR_SID_DAIRI"));
        bean.setRssStatus(rs.getInt("RSS_STATUS"));
        bean.setRssComment(rs.getString("RSS_COMMENT"));
        bean.setRssChkdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSS_CHKDATE")));
        bean.setRssAuid(rs.getInt("RSS_AUID"));
        bean.setRssAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSS_ADATE")));
        bean.setRssEuid(rs.getInt("RSS_EUID"));
        bean.setRssEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSS_EDATE")));
        return bean;
    }
    /**
     * <br>[機  能] 指定された稟議の経路内で稟議閲覧可能（受信済みと後閲権限をもつ）ユーザ一覧を取得する
     * <br>[解  説] 経路内でもまだ受信していないユーザは含まれない。
     * <br>[備  考] 送信者SIDも含む
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> getReceiveUserSids(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RKS_SID IN");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RKS_SID");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIRO_STEP");
            sql.addSql("    where");
            sql.addSql("      RNG_SID = ?");
            sql.addSql("    and");
            sql.addSql("      (");
            sql.addSql("         RKS_ROLL_TYPE = ?");
            sql.addSql("       or");
            sql.addSql("         RKS_KOETU_SIZI = 0");
            sql.addSql("       or");
            sql.addSql("         RKS_RCVDATE is not null");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RNG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(rngSid);
            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 指定した稟議の承認および後閲状態経路に存在するユーザのSIDを取得する
     * <br>[解  説]
     * <br>[備  考] ユーザSIDは重複しない
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @param status 審議ステータス
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int getRksSid(int rngSid, int userSid, int status)throws SQLException {

        PreparedStatement pstmt = null;
        int ret  = 0;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RKS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   RNG_SID =?");
            sql.addSql(" and");
            sql.addSql("   RSS_STATUS =?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(rngSid);
            sql.addIntValue(status);
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("RKS_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     : <br>[機  能] 削除されている最終確認者を確認にする
     * <br>[解  説]
     * <br>[備  考]
     * @param rksList 経路SID一覧
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int setDeleteUserConf(List<Integer> rksList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" set ");
            sql.addSql("   RSS_STATUS=?");
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRMATION);

            sql.addSql(" where ");
            if (rksList.size() == 1) {
                sql.addSql("   RKS_SID=?");
                sql.addIntValue(rksList.get(0));
            } else {
                sql.addSql("   RKS_SID IN (");
                for (int idx = 0; idx < rksList.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql("   ,");
                    }
                    sql.addSql("   ?");
                    sql.addIntValue(rksList.get(idx));
                }
                sql.addSql("   )");
            }
            sql.addSql(" and exists (");
            sql.addSql("   select");
            sql.addSql("     USR_SID");
            sql.addSql("   from");
            sql.addSql("    CMN_USRM");
            sql.addSql("   where");
            sql.addSql("    USR_SID = RNG_SINGI.USR_SID");
            sql.addSql("    and USR_JKBN = ?");
            sql.addSql("  )");
            sql.addIntValue(GSConst.JTKBN_DELETE);


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

}
