package jp.groupsession.v2.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsSoukouModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <p>BBS_SOUKOU Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsSoukouDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsSoukouDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsSoukouDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsSoukouDao(Connection con) {
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
            sql.addSql("drop table BBS_SOUKOU");

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
            sql.addSql(" create table BBS_SOUKOU (");
            sql.addSql("   BSK_SID integer not null,");
            sql.addSql("   BFI_SID integer not null,");
            sql.addSql("   BTI_SID integer not null,");
            sql.addSql("   BSK_SOUKOU_TYPE integer not null,");
            sql.addSql("   BSK_TITLE varchar(150),");
            sql.addSql("   BSK_VALUE clob,");
            sql.addSql("   BSK_TYPE integer not null,");
            sql.addSql("   BSK_VALUE_PLAIN clob,");
            sql.addSql("   BSK_IMPORTANCE integer,");
            sql.addSql("   BSK_LIMIT_FR_DATE timestamp,");
            sql.addSql("   BSK_LIMIT integer not null,");
            sql.addSql("   BSK_LIMIT_DATE timestamp,");
            sql.addSql("   BSK_AGID integer,");
            sql.addSql("   BSK_AUID integer not null,");
            sql.addSql("   BSK_ADATE timestamp not null,");
            sql.addSql("   BSK_EUID integer not null,");
            sql.addSql("   BSK_EDATE timestamp not null,");
            sql.addSql("   primary key (BSK_SID)");
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
     * <p>Insert BBS_SOUKOU Data Bindding JavaBean
     * @param bean BBS_SOUKOU Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsSoukouModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_SOUKOU(");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BSK_SOUKOU_TYPE,");
            sql.addSql("   BSK_TITLE,");
            sql.addSql("   BSK_VALUE,");
            sql.addSql("   BSK_TYPE,");
            sql.addSql("   BSK_VALUE_PLAIN,");
            sql.addSql("   BSK_IMPORTANCE,");
            sql.addSql("   BSK_LIMIT_FR_DATE,");
            sql.addSql("   BSK_LIMIT,");
            sql.addSql("   BSK_LIMIT_DATE,");
            sql.addSql("   BSK_AGID,");
            sql.addSql("   BSK_AUID,");
            sql.addSql("   BSK_ADATE,");
            sql.addSql("   BSK_EUID,");
            sql.addSql("   BSK_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBskSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getBskSoukouType());
            sql.addStrValue(bean.getBskTitle());
            sql.addStrValue(bean.getBskValue());
            sql.addIntValue(bean.getBskType());
            sql.addStrValue(bean.getBskValuePlain());
            sql.addIntValue(bean.getBskImportance());
            sql.addDateValue(bean.getBskLimitFrDate());
            sql.addIntValue(bean.getBskLimit());
            sql.addDateValue(bean.getBskLimitDate());
            sql.addIntValue(bean.getBskAgid());
            sql.addIntValue(bean.getBskAuid());
            sql.addDateValue(bean.getBskAdate());
            sql.addIntValue(bean.getBskEuid());
            sql.addDateValue(bean.getBskEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update BBS_SOUKOU Data Bindding JavaBean
     * @param bean BBS_SOUKOU Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsSoukouModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" set ");
            sql.addSql("   BFI_SID=?,");
            sql.addSql("   BTI_SID=?,");
            sql.addSql("   BSK_SOUKOU_TYPE=?,");
            sql.addSql("   BSK_TITLE=?,");
            sql.addSql("   BSK_VALUE=?,");
            sql.addSql("   BSK_TYPE=?,");
            sql.addSql("   BSK_VALUE_PLAIN=?,");
            sql.addSql("   BSK_IMPORTANCE=?,");
            sql.addSql("   BSK_LIMIT_FR_DATE=?,");
            sql.addSql("   BSK_LIMIT=?,");
            sql.addSql("   BSK_LIMIT_DATE=?,");
            sql.addSql("   BSK_AGID=?,");
            sql.addSql("   BSK_EUID=?,");
            sql.addSql("   BSK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getBskSoukouType());
            sql.addStrValue(bean.getBskTitle());
            sql.addStrValue(bean.getBskValue());
            sql.addIntValue(bean.getBskType());
            sql.addStrValue(bean.getBskValuePlain());
            sql.addIntValue(bean.getBskImportance());
            sql.addDateValue(bean.getBskLimitFrDate());
            sql.addIntValue(bean.getBskLimit());
            sql.addDateValue(bean.getBskLimitDate());
            sql.addIntValue(bean.getBskAgid());
            sql.addIntValue(bean.getBskEuid());
            sql.addDateValue(bean.getBskEdate());
            //where
            sql.addIntValue(bean.getBskSid());
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
     * <p>Select BBS_SOUKOU All Data
     * @return List in BBS_SOUKOUModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsSoukouModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsSoukouModel> ret = new ArrayList<BbsSoukouModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BSK_SOUKOU_TYPE,");
            sql.addSql("   BSK_TITLE,");
            sql.addSql("   BSK_VALUE,");
            sql.addSql("   BSK_TYPE,");
            sql.addSql("   BSK_VALUE_PLAIN,");
            sql.addSql("   BSK_IMPORTANCE,");
            sql.addSql("   BSK_LIMIT_FR_DATE,");
            sql.addSql("   BSK_LIMIT,");
            sql.addSql("   BSK_LIMIT_DATE,");
            sql.addSql("   BSK_AGID,");
            sql.addSql("   BSK_AUID,");
            sql.addSql("   BSK_ADATE,");
            sql.addSql("   BSK_EUID,");
            sql.addSql("   BSK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsSoukouFromRs(rs));
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
     * <p>Select BBS_SOUKOU
     * @param bskSid BSK_SID
     * @return BBS_SOUKOUModel
     * @throws SQLException SQL実行例外
     */
    public BbsSoukouModel select(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsSoukouModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BSK_SOUKOU_TYPE,");
            sql.addSql("   BSK_TITLE,");
            sql.addSql("   BSK_VALUE,");
            sql.addSql("   BSK_TYPE,");
            sql.addSql("   BSK_VALUE_PLAIN,");
            sql.addSql("   BSK_IMPORTANCE,");
            sql.addSql("   BSK_LIMIT_FR_DATE,");
            sql.addSql("   BSK_LIMIT,");
            sql.addSql("   BSK_LIMIT_DATE,");
            sql.addSql("   BSK_AGID,");
            sql.addSql("   BSK_AUID,");
            sql.addSql("   BSK_ADATE,");
            sql.addSql("   BSK_EUID,");
            sql.addSql("   BSK_EDATE");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsSoukouFromRs(rs);
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
     * <p>Delete BBS_SOUKOU
     * @param bskSid BSK_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);


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
     * <p>ユーザSIDから草稿情報を削除する
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteSoukouUserSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" where ");
            sql.addSql("   BSK_AUID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);


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
     * <p>Select BBS_SOUKOU All Data
     * @param usrSid ユーザSID
     * @param sort sortキー
     * @param order orderキー
     * @return List in BBS_SOUKOUModel
     * @throws SQLException SQL実行例外
     */
    public List<BulletinSoukouModel> selectSoukouList(
            int usrSid,
            int sort,
            int order) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BulletinSoukouModel> ret = new ArrayList<BulletinSoukouModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_SOUKOU.BSK_SID,");
            sql.addSql("   BBS_SOUKOU.BFI_SID,");
            sql.addSql("   BBS_SOUKOU.BTI_SID,");
            sql.addSql("   BBS_SOUKOU.BSK_SOUKOU_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_TITLE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE,");
            sql.addSql("   BBS_SOUKOU.BSK_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE_PLAIN,");
            sql.addSql("   BBS_SOUKOU.BSK_IMPORTANCE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_FR_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_AGID,");
            sql.addSql("   BBS_SOUKOU.BSK_AUID,");
            sql.addSql("   BBS_SOUKOU.BSK_ADATE,");
            sql.addSql("   BBS_SOUKOU.BSK_EUID,");
            sql.addSql("   BBS_SOUKOU.BSK_EDATE,");
            sql.addSql("   BBS_THRE_INF.BTI_TITLE,");
            sql.addSql("   BBS_THRE_INF.BTI_IMPORTANCE,");
            sql.addSql("   BBS_FOR_INF.BFI_NAME,");
            sql.addSql("   BBS_FOR_INF.BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" left join ");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql(" left join ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BFI_SID = BBS_FOR_INF.BFI_SID");
            sql.addSql(" where ");
            sql.addSql("   BSK_AUID=?");
            __createOrderQuery(sql, sort, order);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBulletinSoukouFromRs(rs));
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
     * <p>Select BBS_SOUKOU All Data
     * @param usrSid ユーザSID
     * @param bskSid 草稿SID
     * @return List in BBS_SOUKOUModel
     * @throws SQLException SQL実行例外
     */
    public List<BulletinSoukouModel> getDeleteSoukouList(
            String[] bskSid,
            int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BulletinSoukouModel> ret = new ArrayList<BulletinSoukouModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_SOUKOU.BSK_SID,");
            sql.addSql("   BBS_SOUKOU.BFI_SID,");
            sql.addSql("   BBS_SOUKOU.BTI_SID,");
            sql.addSql("   BBS_SOUKOU.BSK_SOUKOU_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_TITLE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE,");
            sql.addSql("   BBS_SOUKOU.BSK_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE_PLAIN,");
            sql.addSql("   BBS_SOUKOU.BSK_IMPORTANCE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_FR_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_AGID,");
            sql.addSql("   BBS_SOUKOU.BSK_AUID,");
            sql.addSql("   BBS_SOUKOU.BSK_ADATE,");
            sql.addSql("   BBS_SOUKOU.BSK_EUID,");
            sql.addSql("   BBS_SOUKOU.BSK_EDATE,");
            sql.addSql("   BBS_THRE_INF.BTI_TITLE,");
            sql.addSql("   BBS_THRE_INF.BTI_IMPORTANCE,");
            sql.addSql("   BBS_FOR_INF.BFI_NAME,");
            sql.addSql("   BBS_FOR_INF.BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" left join ");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql(" left join ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BFI_SID = BBS_FOR_INF.BFI_SID");
            sql.addSql(" where ");
            sql.addSql("   BSK_AUID=?");
            sql.addIntValue(usrSid);
            sql.addSql(" and");
            sql.addSql("   BSK_SID in (");
            //where
            for (int i = 0; i < bskSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(bskSid[i], 0));

                if (i < bskSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");
            __createOrderQuery(sql, -1, -1);
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBulletinSoukouFromRs(rs));
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
     * <p>Select BBS_SOUKOU All Data
     * @param usrSid ユーザSID
     * @param bskSid 草稿SID
     * @return List in BBS_SOUKOUModel
     * @throws SQLException SQL実行例外
     */
    public BulletinSoukouModel selectSoukouInfo(
            int usrSid, int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BulletinSoukouModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_SOUKOU.BSK_SID,");
            sql.addSql("   BBS_SOUKOU.BFI_SID,");
            sql.addSql("   BBS_SOUKOU.BTI_SID,");
            sql.addSql("   BBS_SOUKOU.BSK_SOUKOU_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_TITLE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE,");
            sql.addSql("   BBS_SOUKOU.BSK_TYPE,");
            sql.addSql("   BBS_SOUKOU.BSK_VALUE_PLAIN,");
            sql.addSql("   BBS_SOUKOU.BSK_IMPORTANCE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_FR_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT,");
            sql.addSql("   BBS_SOUKOU.BSK_LIMIT_DATE,");
            sql.addSql("   BBS_SOUKOU.BSK_AGID,");
            sql.addSql("   BBS_SOUKOU.BSK_AUID,");
            sql.addSql("   BBS_SOUKOU.BSK_ADATE,");
            sql.addSql("   BBS_SOUKOU.BSK_EUID,");
            sql.addSql("   BBS_SOUKOU.BSK_EDATE,");
            sql.addSql("   BBS_THRE_INF.BTI_TITLE,");
            sql.addSql("   BBS_THRE_INF.BTI_IMPORTANCE,");
            sql.addSql("   BBS_FOR_INF.BFI_NAME,");
            sql.addSql("   BBS_FOR_INF.BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" left join ");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql(" left join ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" on");
            sql.addSql("   BBS_SOUKOU.BFI_SID = BBS_FOR_INF.BFI_SID");
            sql.addSql(" where ");
            sql.addSql("   BSK_AUID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU.BSK_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(bskSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBulletinSoukouFromRs(rs);
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
    * <br>[機  能] オーダー句の生成
    * <br>[解  説]
    * <br>[備  考]
    * @param sql 生成対象SQLバッファ
    * @param sort ソートキー
    * @param order オーダーキー
    */
   private void __createOrderQuery(
           SqlBuffer sql,
           int sort,
           int order
           ) {

           String orderStr = "";

           // オーダー
           if (order == GSConstBulletin.ORDER_KEY_ASC) {
               orderStr = "  asc";
           } else {
               orderStr = "  desc";
           }

           sql.addSql(" order by ");
           switch (sort) {
           case GSConstBulletin.SOUKOU_SORT_KEY_TYPE:
               sql.addSql("   BSK_SOUKOU_TYPE " + orderStr);
               break;
           case GSConstBulletin.SOUKOU_SORT_KEY_ADATE:
               sql.addSql("   BSK_ADATE " + orderStr);
               break;
           default:
               sql.addSql("   BSK_ADATE " + orderStr);
               break;
           }
   }

    /**
     * <p>Create BBS_SOUKOU Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsSoukouModel
     * @throws SQLException SQL実行例外
     */
    private BbsSoukouModel __getBbsSoukouFromRs(ResultSet rs) throws SQLException {
        BbsSoukouModel bean = new BbsSoukouModel();
        bean.setBskSid(rs.getInt("BSK_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBskSoukouType(rs.getInt("BSK_SOUKOU_TYPE"));
        bean.setBskTitle(rs.getString("BSK_TITLE"));
        bean.setBskValue(rs.getString("BSK_VALUE"));
        bean.setBskType(rs.getInt("BSK_TYPE"));
        bean.setBskValuePlain(rs.getString("BSK_VALUE_PLAIN"));
        bean.setBskImportance(rs.getInt("BSK_IMPORTANCE"));
        bean.setBskLimitFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_LIMIT_FR_DATE")));
        bean.setBskLimit(rs.getInt("BSK_LIMIT"));
        bean.setBskLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_LIMIT_DATE")));
        bean.setBskAgid(rs.getInt("BSK_AGID"));
        bean.setBskAuid(rs.getInt("BSK_AUID"));
        bean.setBskAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_ADATE")));
        bean.setBskEuid(rs.getInt("BSK_EUID"));
        bean.setBskEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_EDATE")));
        return bean;
    }

    /**
     * <p>Create BBS_SOUKOU Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsSoukouModel
     * @throws SQLException SQL実行例外
     */
    private BulletinSoukouModel __getBulletinSoukouFromRs(ResultSet rs) throws SQLException {
        BulletinSoukouModel bean = new BulletinSoukouModel();
        bean.setBskSid(rs.getInt("BSK_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBskSoukouType(rs.getInt("BSK_SOUKOU_TYPE"));
        bean.setBskTitle(rs.getString("BSK_TITLE"));
        bean.setBskValue(rs.getString("BSK_VALUE"));
        bean.setBskType(rs.getInt("BSK_TYPE"));
        bean.setBskValuePlain(rs.getString("BSK_VALUE_PLAIN"));
        bean.setBskImportance(rs.getInt("BSK_IMPORTANCE"));
        bean.setBskLimitFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_LIMIT_FR_DATE")));
        bean.setBskLimit(rs.getInt("BSK_LIMIT"));
        bean.setBskLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_LIMIT_DATE")));
        bean.setBskAgid(rs.getInt("BSK_AGID"));
        bean.setBskAuid(rs.getInt("BSK_AUID"));
        bean.setBskAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_ADATE")));
        bean.setBskEuid(rs.getInt("BSK_EUID"));
        bean.setBskEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BSK_EDATE")));
        bean.setForumName(rs.getString("BFI_NAME"));
        bean.setImgBinSid(rs.getLong("BIN_SID"));
        bean.setThreadName(rs.getString("BTI_TITLE"));
        bean.setJuyoKbn(rs.getInt("BTI_IMPORTANCE"));
        UDate sakuseiDate = UDate.getInstanceTimestamp(rs.getTimestamp("BSK_ADATE"));
        if (sakuseiDate != null) {
            bean.setStrDate(__createDateStr(sakuseiDate));
        }
        return bean;
    }



    /**
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateStr(UDate date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() - 1, date.getIntDay());
        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                strDate.append("( 日 )");
                break;
            case Calendar.MONDAY:
                strDate.append("( 月 )");
                break;
            case Calendar.TUESDAY:
                strDate.append("( 火 )");
                break;
            case Calendar.WEDNESDAY:
                strDate.append("( 水 )");
                break;
            case Calendar.THURSDAY:
                strDate.append("( 木 )");
                break;
            case Calendar.FRIDAY:
                strDate.append("( 金 )");
                break;
            case Calendar.SATURDAY:
                strDate.append("( 土 )");
                break;
            default:
                break;
        }
        strDate.append(" ");
        strDate.append(UDateUtil.getSeparateHMS(date));
        return strDate.toString();
    }




    /**
     * <br>[機  能] 指定された投稿草稿の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getSoukouTmpFileList(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_SOUKOU_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_SOUKOU_BIN.BSK_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_SOUKOU_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(bskSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);



            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long lngSize = rs.getInt("BIN_FILE_SIZE");
                binMdl.setBinFileSize(lngSize);
                String strSize = cmnBiz.getByteSizeString(lngSize);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <p>指定された草稿SIDが存在するかを確認する
     * @param bskSid 草稿SID
     * @param usrSid ユーザSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existSoukou(int bskSid,
            int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BBS_SOUKOU.BSK_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU.BSK_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU.BSK_AUID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            ret = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
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
    public int updateSouForMove(int forumSid, String[] threadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    BBS_SOUKOU");
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

}
