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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnBelongServerModel;

/**
 * <p>CMN_BELONG_SERVER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnBelongServerDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBelongServerDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnBelongServerDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBelongServerDao(Connection con) {
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
            sql.addSql("drop table CMN_BELONG_SERVER");

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
            sql.addSql(" create table CMN_BELONG_SERVER (");
            sql.addSql("   USR_SID varchar(10) not null,");
            sql.addSql("   CBS_IP varchar(30) not null,");
            sql.addSql("   primary key (USR_SID)");
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
     * <p>Insert CMN_BELONG_SERVER Data Bindding JavaBean
     * @param bean CMN_BELONG_SERVER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnBelongServerModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BELONG_SERVER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   CBS_IP");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getUsrSid());
            sql.addStrValue(bean.getCbsIp());
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
     * <p>Update CMN_BELONG_SERVER Data Bindding JavaBean
     * @param bean CMN_BELONG_SERVER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBelongServerModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BELONG_SERVER");
            sql.addSql(" set ");
            sql.addSql("   CBS_IP=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCbsIp());
            //where
            sql.addStrValue(bean.getUsrSid());

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
     * <p>Select CMN_BELONG_SERVER All Data
     * @return List in CMN_BELONG_SERVERModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBelongServerModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongServerModel> ret = new ArrayList<CmnBelongServerModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CBS_IP");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONG_SERVER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnBelongServerFromRs(rs));
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
     * <p>指定したユーザが所属しているサーバのIPアドレスを取得(重複なし)
     * @param usrSids ユーザSID
     * @return List in CMN_BELONG_SERVERModel
     * @throws SQLException SQL実行例外
     */
    public List<String> getIpAddress(List<String> usrSids)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct ");
            sql.addSql("   CBS_IP");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONG_SERVER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in ( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql("   ,");
                }
                sql.addSql("   ?");
                sql.addStrValue(usrSids.get(i));
            }
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("CBS_IP"));
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
     * <p>Select CMN_BELONG_SERVER
     * @param usrSid USR_SID
     * @return CMN_BELONG_SERVERModel
     * @throws SQLException SQL実行例外
     */
    public CmnBelongServerModel select(String usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBelongServerModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CBS_IP");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONG_SERVER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnBelongServerFromRs(rs);
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
     * <p>Delete CMN_BELONG_SERVER
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(String usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONG_SERVER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(usrSid);

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
     * <p>Create CMN_BELONG_SERVER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBelongServerModel
     * @throws SQLException SQL実行例外
     */
    private CmnBelongServerModel __getCmnBelongServerFromRs(ResultSet rs) throws SQLException {
        CmnBelongServerModel bean = new CmnBelongServerModel();
        bean.setUsrSid(rs.getString("USR_SID"));
        bean.setCbsIp(rs.getString("CBS_IP"));
        return bean;
    }
}
