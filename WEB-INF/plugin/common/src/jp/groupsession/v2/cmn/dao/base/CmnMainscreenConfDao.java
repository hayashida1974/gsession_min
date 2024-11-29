package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_MAINSCREEN_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnMainscreenConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnMainscreenConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnMainscreenConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnMainscreenConfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_MAINSCREEN_CONF Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnMainscreenConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_MAINSCREEN_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSC_ORDER,");
            sql.addSql("   MSC_AUID,");
            sql.addSql("   MSC_ADATE,");
            sql.addSql("   MSC_EUID,");
            sql.addSql("   MSC_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMscId());
            sql.addIntValue(bean.getMscPosition());
            sql.addIntValue(bean.getMscOrder());
            sql.addIntValue(bean.getMscAuid());
            sql.addDateValue(bean.getMscAdate());
            sql.addIntValue(bean.getMscEuid());
            sql.addDateValue(bean.getMscEdate());
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
     * <p>Update CMN_MAINSCREEN_CONF Data Bindding JavaBean
     * @param bean CMN_MAINSCREEN_CONF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnMainscreenConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_MAINSCREEN_CONF");
            sql.addSql(" set ");
            sql.addSql("   MSC_POSITION=?,");
            sql.addSql("   MSC_ORDER=?,");
            sql.addSql("   MSC_AUID=?,");
            sql.addSql("   MSC_ADATE=?,");
            sql.addSql("   MSC_EUID=?,");
            sql.addSql("   MSC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MSC_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMscPosition());
            sql.addIntValue(bean.getMscOrder());
            sql.addIntValue(bean.getMscAuid());
            sql.addDateValue(bean.getMscAdate());
            sql.addIntValue(bean.getMscEuid());
            sql.addDateValue(bean.getMscEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMscId());

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
     * <br>[機  能] メイン画面位置設定情報Mappingを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return メイン画面位置設定情報Mapping
     * @throws SQLException SQL実行例外
     */
    public Map<String, CmnMainscreenConfModel> getMainScreenMap(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<String, CmnMainscreenConfModel> map = new HashMap<String, CmnMainscreenConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSC_ORDER,");
            sql.addSql("   MSC_AUID,");
            sql.addSql("   MSC_ADATE,");
            sql.addSql("   MSC_EUID,");
            sql.addSql("   MSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MAINSCREEN_CONF");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnMainscreenConfModel model = __getCmnMainscreenConfFromRs(rs);
                map.put(model.getMscId(), model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return map;
    }

    /**
     * <p>Select CMN_MAINSCREEN_CONF
     * @param bean CMN_MAINSCREEN_CONF Model
     * @return CMN_MAINSCREEN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnMainscreenConfModel select(CmnMainscreenConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnMainscreenConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSC_ORDER,");
            sql.addSql("   MSC_AUID,");
            sql.addSql("   MSC_ADATE,");
            sql.addSql("   MSC_EUID,");
            sql.addSql("   MSC_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MSC_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMscId());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnMainscreenConfFromRs(rs);
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
     * <p>Select CMN_MAINSCREEN_CONF
     * @param bean CMN_MAINSCREEN_CONF Model
     * @return CMN_MAINSCREEN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public boolean selectCnt(CmnMainscreenConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   MSC_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMscId());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
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
     * <p>Delete CMN_MAINSCREEN_CONF
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_MAINSCREEN_CONF");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Select CMN_MAINSCREEN_CONF All Data
     * @return List in CMN_MAINSCREEN_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnMainscreenConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnMainscreenConfModel> ret = new ArrayList<CmnMainscreenConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   MSC_ID,");
            sql.addSql("   MSC_POSITION,");
            sql.addSql("   MSC_ORDER,");
            sql.addSql("   MSC_AUID,");
            sql.addSql("   MSC_ADATE,");
            sql.addSql("   MSC_EUID,");
            sql.addSql("   MSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_MAINSCREEN_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnMainscreenConfFromRs(rs));
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
     * <p>Create CMN_MAINSCREEN_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnMainscreenConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnMainscreenConfModel __getCmnMainscreenConfFromRs(ResultSet rs) throws SQLException {
        CmnMainscreenConfModel bean = new CmnMainscreenConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMscId(rs.getString("MSC_ID"));
        bean.setMscPosition(rs.getInt("MSC_POSITION"));
        bean.setMscOrder(rs.getInt("MSC_ORDER"));
        bean.setMscAuid(rs.getInt("MSC_AUID"));
        bean.setMscAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSC_ADATE")));
        bean.setMscEuid(rs.getInt("MSC_EUID"));
        bean.setMscEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MSC_EDATE")));
        return bean;
    }
}
