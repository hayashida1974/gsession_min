package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_TDFK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnTdfkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnTdfkDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnTdfkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnTdfkDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_TDFK Data Bindding JavaBean
     * @param bean CMN_TDFK Data Bindding JavaBean
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insert(CmnTdfkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_TDFK(");
            sql.addSql("   TDF_SID,");
            sql.addSql("   TDF_NAME,");
            sql.addSql("   TDF_AUID,");
            sql.addSql("   TDF_ADATE,");
            sql.addSql("   TDF_EUID,");
            sql.addSql("   TDF_EDATE");
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
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getTdfName());
            sql.addIntValue(bean.getTdfAuid());
            sql.addDateValue(bean.getTdfAdate());
            sql.addIntValue(bean.getTdfEuid());
            sql.addDateValue(bean.getTdfEdate());
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
     * <p>Update CMN_TDFK Data Bindding JavaBean
     * @param bean CMN_TDFK Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnTdfkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" set ");
            sql.addSql("   TDF_NAME=?,");
            sql.addSql("   TDF_AUID=?,");
            sql.addSql("   TDF_ADATE=?,");
            sql.addSql("   TDF_EUID=?,");
            sql.addSql("   TDF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   TDF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getTdfName());
            sql.addIntValue(bean.getTdfAuid());
            sql.addDateValue(bean.getTdfAdate());
            sql.addIntValue(bean.getTdfEuid());
            sql.addDateValue(bean.getTdfEdate());
            //where
            sql.addIntValue(bean.getTdfSid());

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
     * <p>Select CMN_TDFK All Data
     * @return List in CMN_TDFKModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnTdfkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnTdfkModel> ret = new ArrayList<CmnTdfkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TDF_SID,");
            sql.addSql("   TDF_NAME,");
            sql.addSql("   TDF_AUID,");
            sql.addSql("   TDF_ADATE,");
            sql.addSql("   TDF_EUID,");
            sql.addSql("   TDF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" order by ");
            sql.addSql("   TDF_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnTdfkFromRs(rs));
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
     * <br>[機  能] 全ての都道府県情報をHashMapで取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 全ての都道府県情報
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, CmnTdfkModel> getTdfkMap() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        HashMap<Integer, CmnTdfkModel> ret = new HashMap<Integer, CmnTdfkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TDF_SID,");
            sql.addSql("   TDF_NAME,");
            sql.addSql("   TDF_AUID,");
            sql.addSql("   TDF_ADATE,");
            sql.addSql("   TDF_EUID,");
            sql.addSql("   TDF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" order by ");
            sql.addSql("   TDF_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("TDF_SID"), __getCmnTdfkFromRs(rs));
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
     * <p>Select CMN_TDFK
     * @param bean CMN_TDFK Model
     * @return CMN_TDFKModel
     * @throws SQLException SQL実行例外
     */
    public CmnTdfkModel select(CmnTdfkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnTdfkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TDF_SID,");
            sql.addSql("   TDF_NAME,");
            sql.addSql("   TDF_AUID,");
            sql.addSql("   TDF_ADATE,");
            sql.addSql("   TDF_EUID,");
            sql.addSql("   TDF_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" where ");
            sql.addSql("   TDF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTdfSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnTdfkFromRs(rs);
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
     * <br>都道府県SIDを指定し都道府県情報を取得します。
     * @param sid 都道府県SID
     * @return CMN_TDFKModel
     * @throws SQLException SQL実行例外
     */
    public CmnTdfkModel select(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnTdfkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TDF_SID,");
            sql.addSql("   TDF_NAME,");
            sql.addSql("   TDF_AUID,");
            sql.addSql("   TDF_ADATE,");
            sql.addSql("   TDF_EUID,");
            sql.addSql("   TDF_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" where ");
            sql.addSql("   TDF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnTdfkFromRs(rs);
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
     * <p>Delete CMN_TDFK
     * @param bean CMN_TDFK Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(CmnTdfkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_TDFK");
            sql.addSql(" where ");
            sql.addSql("   TDF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTdfSid());

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
     * <p>Create CMN_TDFK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnTdfkModel
     * @throws SQLException SQL実行例外
     */
    private CmnTdfkModel __getCmnTdfkFromRs(ResultSet rs) throws SQLException {
        CmnTdfkModel bean = new CmnTdfkModel();
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setTdfName(rs.getString("TDF_NAME"));
        bean.setTdfAuid(rs.getInt("TDF_AUID"));
        bean.setTdfAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TDF_ADATE")));
        bean.setTdfEuid(rs.getInt("TDF_EUID"));
        bean.setTdfEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TDF_EDATE")));
        return bean;
    }
}
