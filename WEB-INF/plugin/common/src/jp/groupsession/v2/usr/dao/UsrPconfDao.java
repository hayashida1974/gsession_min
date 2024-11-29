package jp.groupsession.v2.usr.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.usr.model.UsrPconfModel;

/**
 * <p>USR_PCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class UsrPconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UsrPconfDao.class);

    /**
     * <p>Default Constructor
     */
    public UsrPconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public UsrPconfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert USR_PCONF Data Bindding JavaBean
     * @param bean USR_PCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(UsrPconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" USR_PCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPC_MAX_DSP,");
            sql.addSql("   UPC_AUID,");
            sql.addSql("   UPC_ADATE,");
            sql.addSql("   UPC_EUID,");
            sql.addSql("   UPC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getUpcMaxDsp());
            sql.addIntValue(bean.getUpcAuid());
            sql.addDateValue(bean.getUpcAdate());
            sql.addIntValue(bean.getUpcEuid());
            sql.addDateValue(bean.getUpcEdate());
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
     * <p>Update USR_PCONF Data Bindding JavaBean
     * @param bean USR_PCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(UsrPconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   USR_PCONF");
            sql.addSql(" set ");
            sql.addSql("   UPC_MAX_DSP=?,");
            sql.addSql("   UPC_AUID=?,");
            sql.addSql("   UPC_ADATE=?,");
            sql.addSql("   UPC_EUID=?,");
            sql.addSql("   UPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUpcMaxDsp());
            sql.addIntValue(bean.getUpcAuid());
            sql.addDateValue(bean.getUpcAdate());
            sql.addIntValue(bean.getUpcEuid());
            sql.addDateValue(bean.getUpcEdate());
            //where
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
     * <p>Update USR_PCONF Data Bindding JavaBean
     * @param bean USR_PCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateEdit(UsrPconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   USR_PCONF");
            sql.addSql(" set ");
            sql.addSql("   UPC_MAX_DSP=?,");
            sql.addSql("   UPC_EUID=?,");
            sql.addSql("   UPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUpcMaxDsp());
            sql.addIntValue(bean.getUpcEuid());
            sql.addDateValue(bean.getUpcEdate());
            //where
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
     * <p>Select USR_PCONF All Data
     * @return List in USR_PCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<UsrPconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<UsrPconfModel> ret = new ArrayList<UsrPconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPC_MAX_DSP,");
            sql.addSql("   UPC_AUID,");
            sql.addSql("   UPC_ADATE,");
            sql.addSql("   UPC_EUID,");
            sql.addSql("   UPC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   USR_PCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getUsrPconfFromRs(rs));
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
     * <p>Select USR_PCONF
     * @param usrSid USR_SID
     * @return USR_PCONFModel
     * @throws SQLException SQL実行例外
     */
    public UsrPconfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UsrPconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   UPC_MAX_DSP,");
            sql.addSql("   UPC_AUID,");
            sql.addSql("   UPC_ADATE,");
            sql.addSql("   UPC_EUID,");
            sql.addSql("   UPC_EDATE");
            sql.addSql(" from");
            sql.addSql("   USR_PCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getUsrPconfFromRs(rs);
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
     * <p>Delete USR_PCONF
     * @param usrSid USR_SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
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
            sql.addSql("   USR_PCONF");
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
     * <p>Create USR_PCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created UsrPconfModel
     * @throws SQLException SQL実行例外
     */
    private UsrPconfModel __getUsrPconfFromRs(ResultSet rs) throws SQLException {
        UsrPconfModel bean = new UsrPconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUpcMaxDsp(rs.getInt("UPC_MAX_DSP"));
        bean.setUpcAuid(rs.getInt("UPC_AUID"));
        bean.setUpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UPC_ADATE")));
        bean.setUpcEuid(rs.getInt("UPC_EUID"));
        bean.setUpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UPC_EDATE")));
        return bean;
    }
}
