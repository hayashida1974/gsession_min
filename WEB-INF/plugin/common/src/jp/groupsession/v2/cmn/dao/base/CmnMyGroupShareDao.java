package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupShareModel;

/**
 * <p>CMN_MY_GROUP_SHARE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMyGroupShareDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMyGroupShareDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMyGroupShareDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMyGroupShareDao(Connection con) {
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
            sql.addSql("drop table CMN_MY_GROUP_SHARE");

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
            sql.addSql(" create table CMN_MY_GROUP_SHARE (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   MGP_SID NUMBER(10,0) not null,");
            sql.addSql("   MGS_USR_SID NUMBER(10,0) not null,");
            sql.addSql("   MGS_GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (USR_SID,MGP_SID,MGS_USR_SID,MGS_GRP_SID)");
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
     * <p>Insert CMN_MY_GROUP_SHARE Data Bindding JavaBean
     * @param bean CMN_MY_GROUP_SHARE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMyGroupShareModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MY_GROUP_SHARE(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGS_USR_SID,");
            sql.addSql("   MGS_GRP_SID");
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
            sql.addIntValue(bean.getMgpSid());
            sql.addIntValue(bean.getMgsUsrSid());
            sql.addIntValue(bean.getMgsGrpSid());
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
     * <p>Update CMN_MY_GROUP_SHARE Data Bindding JavaBean
     * @param bean CMN_MY_GROUP_SHARE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMyGroupShareModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGP_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMgpSid());
            sql.addIntValue(bean.getMgsUsrSid());
            sql.addIntValue(bean.getMgsGrpSid());

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
     *
     * <br>[機  能] マイグループSIDからマイグループ共有設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param mgpSid マイグループSID
     * @throws SQLException SQL実行時例外
     * @return マイグループ共有設定一覧
     */
    public List<CmnMyGroupShareModel> select(int mgpSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupShareModel> ret = new ArrayList<CmnMyGroupShareModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGS_USR_SID,");
            sql.addSql("   MGS_GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.addIntValue(mgpSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMyGroupShareFromRs(rs));
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
     * <p>Select CMN_MY_GROUP_SHARE All Data
     * @return List in CMN_MY_GROUP_SHAREModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMyGroupShareModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMyGroupShareModel> ret = new ArrayList<CmnMyGroupShareModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGS_USR_SID,");
            sql.addSql("   MGS_GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_MY_GROUP_SHARE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMyGroupShareFromRs(rs));
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
     * <p>Select CMN_MY_GROUP_SHARE
     * @param usrSid USR_SID
     * @param mgpSid MGP_SID
     * @param mgsUsrSid MGS_USR_SID
     * @param mgsGrpSid MGS_GRP_SID
     * @return CMN_MY_GROUP_SHAREModel
     * @throws SQLException SQL実行例外
     */
    public CmnMyGroupShareModel select(int usrSid,
            int mgpSid,
            int mgsUsrSid,
            int mgsGrpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMyGroupShareModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MGP_SID,");
            sql.addSql("   MGS_USR_SID,");
            sql.addSql("   MGS_GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGP_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mgpSid);
            sql.addIntValue(mgsUsrSid);
            sql.addIntValue(mgsGrpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMyGroupShareFromRs(rs);
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
     * <br>[機  能] ユーザSIDを指定してマイグループ共有設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteGroupShare(int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP_SHARE");
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
     *
     * <br>[機  能] ユーザSIDとマイグループSIDを指定してマイグループ共有設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param mgpSid マイグループSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteGroupShare(int usrSid, int mgpSid) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mgpSid);

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
     * <br>[機  能] マイグループSID(複数)を指定してマイグループ共有設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid マイグループSID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteGroupShare(String[] groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (groupSid == null) {
            return count;
        }
        if (groupSid.length < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" where ");
            sql.addSql("   MGP_SID in (");

            for (int i = 0; i < groupSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(groupSid[i], 0));

                if (i < groupSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

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
     * <p>Delete CMN_MY_GROUP_SHARE
     * @param usrSid USR_SID
     * @param mgpSid MGP_SID
     * @param mgsUsrSid MGS_USR_SID
     * @param mgsGrpSid MGS_GRP_SID
     * @throws SQLException SQL実行例外
     * @return 処理件数
     */
    public int delete(int usrSid,
            int mgpSid,
            int mgsUsrSid,
            int mgsGrpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MY_GROUP_SHARE");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGP_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MGS_GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mgpSid);
            sql.addIntValue(mgsUsrSid);
            sql.addIntValue(mgsGrpSid);

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
     * <p>Create CMN_MY_GROUP_SHARE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMyGroupShareModel
     * @throws SQLException SQL実行例外
     */
    private CmnMyGroupShareModel __getCmnMyGroupShareFromRs(ResultSet rs) throws SQLException {
        CmnMyGroupShareModel bean = new CmnMyGroupShareModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMgpSid(rs.getInt("MGP_SID"));
        bean.setMgsUsrSid(rs.getInt("MGS_USR_SID"));
        bean.setMgsGrpSid(rs.getInt("MGS_GRP_SID"));
        return bean;
    }
}
