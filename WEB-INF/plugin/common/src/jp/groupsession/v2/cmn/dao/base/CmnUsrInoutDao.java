package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_USR_INOUT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrInoutDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrInoutDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrInoutDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrInoutDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_USR_INOUT Data Bindding JavaBean
     * @param bean CMN_USR_INOUT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_INOUT(");
            sql.addSql("   UIO_SID,");
            sql.addSql("   UIO_STATUS,");
            sql.addSql("   UIO_BIKO,");
            sql.addSql("   UIO_AUID,");
            sql.addSql("   UIO_ADATE,");
            sql.addSql("   UIO_EUID,");
            sql.addSql("   UIO_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUioSid());
            sql.addIntValue(bean.getUioStatus());
            sql.addStrValue(bean.getUioBiko());
            sql.addIntValue(bean.getUioAuid());
            sql.addDateValue(bean.getUioAdate());
            sql.addIntValue(bean.getUioEuid());
            sql.addDateValue(bean.getUioEdate());
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
     * <p>Update CMN_USR_INOUT Data Bindding JavaBean
     * @param bean CMN_USR_INOUT Data Bindding JavaBean
     * @return count 処理件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" set ");
            sql.addSql("   UIO_STATUS=?,");
            sql.addSql("   UIO_BIKO=?,");
            sql.addSql("   UIO_EUID=?,");
            sql.addSql("   UIO_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   UIO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUioStatus());
            sql.addStrValue(bean.getUioBiko());
            sql.addIntValue(bean.getUioEuid());
            sql.addDateValue(bean.getUioEdate());
            sql.addIntValue(bean.getUioSid());

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
     * <p>在席ステータスのみを更新する
     * @param bean CMN_USR_INOUT Data Bindding JavaBean
     * @return count 処理件数
     * @throws SQLException SQL実行例外
     */
    public int updateStatusOnly(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" set ");
            sql.addSql("   UIO_STATUS=?,");
            sql.addSql("   UIO_EUID=?,");
            sql.addSql("   UIO_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   UIO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUioStatus());
            sql.addIntValue(bean.getUioEuid());
            sql.addDateValue(bean.getUioEdate());
            sql.addIntValue(bean.getUioSid());

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
     * <p>Update CMN_USR_INOUT Data Bindding JavaBean
     * @param bean CMN_USR_INOUT Data Bindding JavaBean
     * @return count 処理件数
     * @throws SQLException SQL実行例外
     */
    public int updateAll(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" set ");
            sql.addSql("   UIO_STATUS=?,");
            sql.addSql("   UIO_BIKO=?,");
            sql.addSql("   UIO_EUID=?,");
            sql.addSql("   UIO_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUioStatus());
            sql.addStrValue(bean.getUioBiko());
            sql.addIntValue(bean.getUioEuid());
            sql.addDateValue(bean.getUioEdate());

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
     * <p>Delete CMN_USR_INOUT
     * @param bean CMN_USR_INOUT Model
     * @return count 処理件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_INOUT");

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
     * <p>Delete CMN_USR_INOUT
     * @param usrSid ユーザSID
     * @return count 処理件数
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
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" where");
            sql.addSql("   UIO_SID = ?");

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
     * <p>Select CMN_USR_INOUT All Data
     * @return List in CMN_USR_INOUTModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrInoutModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrInoutModel> ret = new ArrayList<CmnUsrInoutModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   UIO_SID,");
            sql.addSql("   UIO_STATUS,");
            sql.addSql("   UIO_BIKO,");
            sql.addSql("   UIO_AUID,");
            sql.addSql("   UIO_ADATE,");
            sql.addSql("   UIO_EUID,");
            sql.addSql("   UIO_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_INOUT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUsrInoutFromRs(rs));
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
     * <p>Select CMN_USR_INOUT
     * @param bean CMN_USR_INOUT Model
     * @return CMN_USR_INOUTModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrInoutModel select(CmnUsrInoutModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrInoutModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   UIO_SID,");
            sql.addSql("   UIO_STATUS,");
            sql.addSql("   UIO_BIKO,");
            sql.addSql("   UIO_AUID,");
            sql.addSql("   UIO_ADATE,");
            sql.addSql("   UIO_EUID,");
            sql.addSql("   UIO_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" where ");
            sql.addSql("   UIO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUioSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrInoutFromRs(rs);
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
     * <p>Select CMN_USR_INOUT
     * @param usrSid ユーザSID
     * @return CMN_USR_INOUTModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrInoutModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrInoutModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   UIO_SID,");
            sql.addSql("   UIO_STATUS,");
            sql.addSql("   UIO_BIKO,");
            sql.addSql("   UIO_AUID,");
            sql.addSql("   UIO_ADATE,");
            sql.addSql("   UIO_EUID,");
            sql.addSql("   UIO_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" where ");
            sql.addSql("   UIO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUsrInoutFromRs(rs);
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
     * <p>SelCnt 該当ユーザのデータがあるか
     * @param uioSid 検索ユーザID
     * @return selcnt 該当件数
     * @throws SQLException SQL実行例外
     */
    public int selCnt(int uioSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(UIO_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_INOUT");
            sql.addSql(" where ");
            sql.addSql("   UIO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(uioSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <p>Create CMN_USR_INOUT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrInoutModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrInoutModel __getCmnUsrInoutFromRs(ResultSet rs) throws SQLException {
        CmnUsrInoutModel bean = new CmnUsrInoutModel();
        bean.setUioSid(rs.getInt("UIO_SID"));
        bean.setUioStatus(rs.getInt("UIO_STATUS"));
        bean.setUioBiko(rs.getString("UIO_BIKO"));
        bean.setUioAuid(rs.getInt("UIO_AUID"));
        bean.setUioAdate(UDate.getInstanceTimestamp(rs.getTimestamp("UIO_ADATE")));
        bean.setUioEuid(rs.getInt("UIO_EUID"));
        bean.setUioEdate(UDate.getInstanceTimestamp(rs.getTimestamp("UIO_EDATE")));
        return bean;
    }
}
