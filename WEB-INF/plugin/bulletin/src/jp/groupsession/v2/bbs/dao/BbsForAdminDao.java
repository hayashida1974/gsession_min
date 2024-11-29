package jp.groupsession.v2.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.model.BbsForAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_FOR_ADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsForAdminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsForAdminDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsForAdminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsForAdminDao(Connection con) {
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
            sql.addSql("drop table BBS_FOR_ADMIN");

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
            sql.addSql(" create table BBS_FOR_ADMIN (");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (BFI_SID,USR_SID)");
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
     * <p>Insert BBS_FOR_ADMIN Data Bindding JavaBean
     * @param bean BBS_FOR_ADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsForAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_FOR_ADMIN(");
            sql.addSql("   BFI_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Insert BBS_FOR_ADMIN Data Bindding JavaBean
     * @param bfiSid フォーラムSID
     * @param usrSidList ユーザリスト
     * @throws SQLException SQL実行例外
     */
    public void insert(int bfiSid, String[] usrSidList) throws SQLException {

        if (usrSidList == null || usrSidList.length <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_FOR_ADMIN(");
            sql.addSql("   BFI_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (String userSid : usrSidList) {
                String str = NullDefault.getString(userSid, "-1");
                //ユーザ
                userSid = str;
                sql.addIntValue(bfiSid);
                sql.addIntValue(Integer.parseInt(userSid));

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
     * <p>Update BBS_FOR_ADMIN Data Bindding JavaBean
     * @param bean BBS_FOR_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsForAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_ADMIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getBfiSid());
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
     * <p>Select BBS_FOR_ADMIN All Data
     * @return List in BBS_FOR_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsForAdminModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsForAdminModel> ret = new ArrayList<BbsForAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForAdminFromRs(rs));
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
     * <p>Select BBS_FOR_ADMIN
     * @param bfiSid BFI_SID
     * @param usrSid USR_SID
     * @return BBS_FOR_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public BbsForAdminModel select(int bfiSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsForAdminModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsForAdminFromRs(rs);
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
     * <p>指定されたフォーラムの管理者の一覧を取得します。
     * @param bfiSid BFI_SID
     * @return BBS_FOR_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsForAdminModel> getUsrData(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsForAdminModel> ret = new ArrayList<BbsForAdminModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForAdminFromRs(rs));
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
     * <p>Delete BBS_FOR_ADMIN
     * @param bfiSid BFI_SID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);

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
     * <p>Create BBS_FOR_ADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsForAdminModel
     * @throws SQLException SQL実行例外
     */
    private BbsForAdminModel __getBbsForAdminFromRs(ResultSet rs) throws SQLException {
        BbsForAdminModel bean = new BbsForAdminModel();
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
