package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlDestlistAccessConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_DESTLIST_ACCESS_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistAccessConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDestlistAccessConfDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDestlistAccessConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDestlistAccessConfDao(Connection con) {
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
            sql.addSql("drop table WML_DESTLIST_ACCESS_CONF");

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
            sql.addSql(" create table WML_DESTLIST_ACCESS_CONF (");
            sql.addSql("   WDL_SID NUMBER(10,0) not null,");
            sql.addSql("   WLA_KBN NUMBER(10,0) not null,");
            sql.addSql("   WLA_USR_SID NUMBER(10,0) not null,");
            sql.addSql("   WLA_AUTH NUMBER(10,0) not null,");
            sql.addSql("   primary key (WDL_SID,WLA_KBN,WLA_USR_SID)");
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
     * <p>Insert WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean
     * @param bean WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDestlistAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DESTLIST_ACCESS_CONF(");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WLA_KBN,");
            sql.addSql("   WLA_USR_SID,");
            sql.addSql("   WLA_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWdlSid());
            sql.addIntValue(bean.getWlaKbn());
            sql.addIntValue(bean.getWlaUsrSid());
            sql.addIntValue(bean.getWlaAuth());
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
     * <p>Update WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean
     * @param bean WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDestlistAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" set ");
            sql.addSql("   WLA_AUTH=?");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLA_KBN=?");
            sql.addSql(" and");
            sql.addSql("   WLA_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWlaAuth());
            //where
            sql.addIntValue(bean.getWdlSid());
            sql.addIntValue(bean.getWlaKbn());
            sql.addIntValue(bean.getWlaUsrSid());

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
     * <p>Select WML_DESTLIST_ACCESS_CONF All Data
     * @return List in WML_DESTLIST_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDestlistAccessConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDestlistAccessConfModel> ret = new ArrayList<WmlDestlistAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WLA_KBN,");
            sql.addSql("   WLA_USR_SID,");
            sql.addSql("   WLA_AUTH");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDestlistAccessConfFromRs(rs));
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
     * <p>Select WML_DESTLIST_ACCESS_CONF
     * @param wdlSid WDL_SID
     * @param wlaKbn WLA_KBN
     * @param wlaUsrSid WLA_USR_SID
     * @return WML_DESTLIST_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public WmlDestlistAccessConfModel select(int wdlSid, int wlaKbn, int wlaUsrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDestlistAccessConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WLA_KBN,");
            sql.addSql("   WLA_USR_SID,");
            sql.addSql("   WLA_AUTH");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLA_KBN=?");
            sql.addSql(" and");
            sql.addSql("   WLA_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);
            sql.addIntValue(wlaKbn);
            sql.addIntValue(wlaUsrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDestlistAccessConfFromRs(rs);
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
     * <br>[機  能] 送信先リスト アクセス設定の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdlSid 送信先リストSID
     * @param wlaAuth 権限区分
     * @return 送信先リスト アクセス設定の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDestlistAccessConfModel> getAccessConfList(int wdlSid, int wlaAuth)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDestlistAccessConfModel> ret = new ArrayList<WmlDestlistAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WLA_KBN,");
            sql.addSql("   WLA_USR_SID,");
            sql.addSql("   WLA_AUTH");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   WLA_AUTH = ?");
            sql.addIntValue(wdlSid);
            sql.addIntValue(wlaAuth);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDestlistAccessConfFromRs(rs));
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
     * <p>Delete WML_DESTLIST_ACCESS_CONF
     * @param wdlSid WDL_SID
     * @param wlaKbn WLA_KBN
     * @param wlaUsrSid WLA_USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wdlSid, int wlaKbn, int wlaUsrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and");
            sql.addSql("   WLA_KBN=?");
            sql.addSql(" and");
            sql.addSql("   WLA_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);
            sql.addIntValue(wlaKbn);
            sql.addIntValue(wlaUsrSid);

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
     * <p>Delete WML_DESTLIST_ACCESS_CONF
     * @param wdlSid WDL_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wdlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);

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
     * <br>[機  能] 送信先リストのアクセス設定を確認する
     * <br>[解  説]
     * <br>[備  考] 権限区分 = 未設定(-1) の場合、権限区分を参照しない
     * @param wdlSid 送信先リストSID
     * @param userSid ユーザSID
     * @param wlaAuth 権限区分
     * @return true: 正常、false: 不正
     * @throws SQLException SQL実行時例外
     */
    public boolean checkDestlistAuth(int wdlSid, int userSid, int wlaAuth) throws SQLException {

        boolean result = false;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID = ?");
            sql.addIntValue(wdlSid);
            if (wlaAuth != -1) {
                sql.addSql(" and ");
                sql.addSql("   WLA_AUTH = ?");
                sql.addIntValue(wlaAuth);
            }

            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("       WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       WML_DESTLIST_ACCESS_CONF.WLA_USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       WML_DESTLIST_ACCESS_CONF.WLA_USR_SID in (");
            sql.addSql("         select GRP_SID from CMN_BELONGM");
            sql.addSql("         where CMN_BELONGM.USR_SID = ?");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.setPagingValue(0, 1);

            sql.addIntValue(GSConstWebmail.WLA_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WLA_KBN_GROUP);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Create WML_DESTLIST_ACCESS_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDestlistAccessConfModel
     * @throws SQLException SQL実行例外
     */
    private WmlDestlistAccessConfModel __getWmlDestlistAccessConfFromRs(ResultSet rs)
    throws SQLException {
        WmlDestlistAccessConfModel bean = new WmlDestlistAccessConfModel();
        bean.setWdlSid(rs.getInt("WDL_SID"));
        bean.setWlaKbn(rs.getInt("WLA_KBN"));
        bean.setWlaUsrSid(rs.getInt("WLA_USR_SID"));
        bean.setWlaAuth(rs.getInt("WLA_AUTH"));
        return bean;
    }
}
