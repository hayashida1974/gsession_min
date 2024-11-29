package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlUidlTempModel;

/**
 * <p>WML_UIDL_TEMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlUidlTempDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlUidlTempDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlUidlTempDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlUidlTempDao(Connection con) {
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
            sql.addSql("drop table WML_UIDL_TEMP");

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
            sql.addSql(" create table WML_UIDL_TEMP (");
            sql.addSql("   WAC_SID integer not null,");
            sql.addSql("   WUD_UID varchar(1000) not null,");
            sql.addSql("   primary key (WAC_SID,WUD_UID)");
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
     * <p>Insert WML_UIDL_TEMP Data Bindding JavaBean
     * @param bean WML_UIDL_TEMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlUidlTempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_UIDL_TEMP(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_UID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWudUid());
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
     *
     * <br>[機  能] 「マルチテーブル・インサート」による一括登録を行う
     * <br>[解  説]
     * <br>[備  考] 登録は1000件毎に実施する (SQL実行速度の低下が確認されたため)
     * @param wacSid アカウントSID
     * @param uidlSet UIDL
     * @throws SQLException SQL実行時例外
     */
    public void insertIkkatu(int wacSid, Collection<String> uidlSet) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        Iterator<String> it  = uidlSet.iterator();
        int cnt = 0;

        //SQL文
        final String  sqlbase = " insert "
                + " into "
                + " WML_UIDL_TEMP("
                + "   WAC_SID,"
                + "   WUD_UID"
                + " )"
                + " values";

        //SQL文
        SqlBuffer sql = null;
        while (it.hasNext()) {
            if (sql == null) {
                sql = new SqlBuffer();
                sql.addSql(sqlbase);
            }

            String uidl = it.next();
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addIntValue(wacSid);
            sql.addStrValue(uidl);

            cnt++;
            boolean send = false;
            if (cnt >= 1000) {
                send = true;
                cnt = 0;
            }
            if (!it.hasNext()) {
                send = true;
            }
            if (!send) {
                sql.addSql(", ");
                continue;
            }
            try {
                //SQL文
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql = null;
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeStatement(pstmt);
            }

        }
    }

    /**
     * <p>Update WML_UIDL_TEMP Data Bindding JavaBean
     * @param bean WML_UIDL_TEMP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlUidlTempModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_UIDL_TEMP");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWudUid());

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
     * <p>Select WML_UIDL_TEMP All Data
     * @return List in WML_UIDL_TEMPModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlUidlTempModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlUidlTempModel> ret = new ArrayList<WmlUidlTempModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_UID");
            sql.addSql(" from ");
            sql.addSql("   WML_UIDL_TEMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlUidlTempFromRs(rs));
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
     * <p>Select WML_UIDL_TEMP
     * @param wacSid WAC_SID
     * @param wudUid WUD_UID
     * @return WML_UIDL_TEMPModel
     * @throws SQLException SQL実行例外
     */
    public WmlUidlTempModel select(int wacSid, String wudUid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlUidlTempModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WUD_UID");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addStrValue(wudUid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlUidlTempFromRs(rs);
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
     * <p>Delete WML_UIDL_TEMP
     * @param wacSid WAC_SID
     * @param wudUid WUD_UID
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, String wudUid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WUD_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addStrValue(wudUid);

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
     * <p>Create WML_UIDL_TEMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlUidlTempModel
     * @throws SQLException SQL実行例外
     */
    private WmlUidlTempModel __getWmlUidlTempFromRs(ResultSet rs) throws SQLException {
        WmlUidlTempModel bean = new WmlUidlTempModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWudUid(rs.getString("WUD_UID"));
        return bean;
    }
    /**
     *
     * <br>[機  能] アカウントに紐づくデータを削除
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int delete(int wacSid) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_UIDL_TEMP");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
