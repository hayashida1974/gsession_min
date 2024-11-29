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
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_SIGN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountSignDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountSignDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountSignDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountSignDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_SIGN");

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
            sql.addSql(" create table WML_ACCOUNT_SIGN (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WSI_NO NUMBER(10,0) not null,");
            sql.addSql("   WSI_TITLE varchar(100) not null,");
            sql.addSql("   WSI_SIGN varchar(1000) not null,");
            sql.addSql("   WSI_DEF NUMBER(10,0) not null,");
            sql.addSql("   primary key (WAC_SID,WSI_NO)");
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
     * <p>Insert WML_ACCOUNT_SIGN Data Bindding JavaBean
     * @param bean WML_ACCOUNT_SIGN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountSignModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_SIGN(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWsiNo());
            sql.addStrValue(bean.getWsiTitle());
            sql.addStrValue(bean.getWsiSign());
            sql.addIntValue(bean.getWsiDef());
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
     * <p>Update WML_ACCOUNT_SIGN Data Bindding JavaBean
     * @param bean WML_ACCOUNT_SIGN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountSignModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" set ");
            sql.addSql("   WSI_TITLE=?,");
            sql.addSql("   WSI_SIGN=?,");
            sql.addSql("   WSI_DEF=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WSI_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWsiTitle());
            sql.addStrValue(bean.getWsiSign());
            sql.addIntValue(bean.getWsiDef());
            //where
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWsiNo());

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
     * <br>[機  能] デフォルトの署名を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param sign 署名
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateDefSign(int wacSid, String sign) throws SQLException {

        int count = 0;
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" set ");
            sql.addSql("   WSI_SIGN=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WSI_DEF=?");
            sql.addStrValue(sign);
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WSI_DEF_DEFAULT);

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
     * <p>Select WML_ACCOUNT_SIGN All Data
     * @return List in WML_ACCOUNT_SIGNModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountSignModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountSignModel> ret = new ArrayList<WmlAccountSignModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_SIGN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountSignFromRs(rs));
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
     * <p>Select WML_ACCOUNT_SIGN
     * @param wacSid WAC_SID
     * @param wsiNo WSI_NO
     * @return WML_ACCOUNT_SIGNModel
     * @throws SQLException SQL実行例外
     */
    public WmlAccountSignModel select(int wacSid, int wsiNo) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountSignModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WSI_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wsiNo);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountSignFromRs(rs);
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
     * <br>[機  能] 指定したアカウントの署名(デフォルト)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 署名情報
     * @throws SQLException SQL実行時例外
     */
    public WmlAccountSignModel getDefaultSign(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAccountSignModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WSI_DEF=?");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WSI_DEF_DEFAULT);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAccountSignFromRs(rs);
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
     * <br>[機  能] 指定したアカウントの署名情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 署名情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlAccountSignModel> getSignList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountSignModel> ret = new ArrayList<WmlAccountSignModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountSignFromRs(rs));
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
     * <br>[機  能] 指定したアカウント一覧の署名情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSidList アカウントSID一覧
     * @return 署名情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlAccountSignModel> getSignList(int[] wacSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountSignModel> ret = new ArrayList<WmlAccountSignModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSI_NO,");
            sql.addSql("   WSI_TITLE,");
            sql.addSql("   WSI_SIGN,");
            sql.addSql("   WSI_DEF");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID in (");
            for (int i = 0; i < wacSidList.length; i++) {
                sql.addSql((i == 0 ? "?" : ",?"));
                sql.addIntValue(wacSidList[i]);
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountSignFromRs(rs));
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
     * <p>Delete WML_ACCOUNT_SIGN
     * @param wacSid WAC_SID
     * @param wsiNo WSI_NO
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int wsiNo) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_SIGN");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WSI_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wsiNo);

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
     * <p>Delete WML_ACCOUNT_SIGN
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
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
            sql.addSql("   WML_ACCOUNT_SIGN");
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

    /**
     * <p>Create WML_ACCOUNT_SIGN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountSignModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountSignModel __getWmlAccountSignFromRs(ResultSet rs) throws SQLException {
        WmlAccountSignModel bean = new WmlAccountSignModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWsiNo(rs.getInt("WSI_NO"));
        bean.setWsiTitle(rs.getString("WSI_TITLE"));
        bean.setWsiSign(rs.getString("WSI_SIGN"));
        bean.setWsiDef(rs.getInt("WSI_DEF"));
        return bean;
    }
}
