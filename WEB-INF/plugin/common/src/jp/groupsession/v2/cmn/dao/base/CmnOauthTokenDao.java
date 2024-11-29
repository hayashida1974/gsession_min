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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthTokenModel;

/**
 * <p>CMN_OAUTH_TOKEN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOauthTokenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnOauthTokenDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnOauthTokenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnOauthTokenDao(Connection con) {
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
            sql.addSql("drop table CMN_OAUTH_TOKEN");

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
            sql.addSql(" create table CMN_OAUTH_TOKEN (");
            sql.addSql("   COT_SID integer not null,");
            sql.addSql("   COU_SID integer not null,");
            sql.addSql("   COT_ACCOUNTID varchar(300),");
            sql.addSql("   COT_AUTH_RTOKEN varchar(3000),");
            sql.addSql("   COT_AUTH_ATOKEN varchar(3000),");
            sql.addSql("   COT_AUTH_ATOKEN_DATE timestamp,");
            sql.addSql("   primary key (COT_SID)");
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
     * <p>Insert CMN_OAUTH_TOKEN Data Bindding JavaBean
     * @param bean CMN_OAUTH_TOKEN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnOauthTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_OAUTH_TOKEN(");
            sql.addSql("   COT_SID,");
            sql.addSql("   COU_SID,");
            sql.addSql("   COT_ACCOUNTID,");
            sql.addSql("   COT_AUTH_RTOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE");
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
            sql.addIntValue(bean.getCotSid());
            sql.addIntValue(bean.getCouSid());
            sql.addStrValue(bean.getCotAccountid());
            sql.addStrValue(bean.getCotAuthRtoken());
            sql.addStrValue(bean.getCotAuthAtoken());
            sql.addDateValue(bean.getCotAuthAtokenDate());
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
     * <p>Update CMN_OAUTH_TOKEN Data Bindding JavaBean
     * @param bean CMN_OAUTH_TOKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnOauthTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" set ");
            sql.addSql("   COU_SID=?,");
            sql.addSql("   COT_ACCOUNTID=?,");
            sql.addSql("   COT_AUTH_RTOKEN=?,");
            sql.addSql("   COT_AUTH_ATOKEN=?,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCouSid());
            sql.addStrValue(bean.getCotAccountid());
            sql.addStrValue(bean.getCotAuthRtoken());
            sql.addStrValue(bean.getCotAuthAtoken());
            sql.addDateValue(bean.getCotAuthAtokenDate());
            //where
            sql.addIntValue(bean.getCotSid());

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
     * <p>トークン情報を更新する
     * @param bean CMN_OAUTH_TOKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateToken(CmnOauthTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" set ");
            sql.addSql("   COT_AUTH_RTOKEN=?,");
            sql.addSql("   COT_AUTH_ATOKEN=?,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCotAuthRtoken());
            sql.addStrValue(bean.getCotAuthAtoken());
            sql.addDateValue(bean.getCotAuthAtokenDate());
            //where
            sql.addIntValue(bean.getCotSid());

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
     * <p>Select CMN_OAUTH_TOKEN All Data
     * @return List in CMN_OAUTH_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnOauthTokenModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnOauthTokenModel> ret = new ArrayList<CmnOauthTokenModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COT_SID,");
            sql.addSql("   COU_SID,");
            sql.addSql("   COT_ACCOUNTID,");
            sql.addSql("   COT_AUTH_RTOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH_TOKEN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnOauthTokenFromRs(rs));
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
     * <p>Select CMN_OAUTH_TOKEN
     * @param cotSid COT_SID
     * @return CMN_OAUTH_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public CmnOauthTokenModel select(int cotSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOauthTokenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COT_SID,");
            sql.addSql("   COU_SID,");
            sql.addSql("   COT_ACCOUNTID,");
            sql.addSql("   COT_AUTH_RTOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cotSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnOauthTokenFromRs(rs);
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
     * <br>[機  能] 指定したOAuth認証情報、アカウントIDと一致するトークン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param couSid OAuth認証情報SID
     * @param accountId アカウントID
     * @return トークン情報
     * @throws SQLException SQL実行時例外
     */
    public CmnOauthTokenModel getOauthToken(int couSid, String accountId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOauthTokenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COT_SID,");
            sql.addSql("   COU_SID,");
            sql.addSql("   COT_ACCOUNTID,");
            sql.addSql("   COT_AUTH_RTOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN,");
            sql.addSql("   COT_AUTH_ATOKEN_DATE");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");
            sql.addSql(" and ");
            sql.addSql("   COT_ACCOUNTID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(couSid);
            sql.addStrValue(accountId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnOauthTokenFromRs(rs);
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
     * <br>[機  能] 指定したアカウントが選択しているOAuth認証情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cotSid OAuth認証トークンSID
     * @return OAuth認証情報
     * @throws SQLException SQL実行時例外
     */
    public OauthDataModel getAuthData(int cotSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        OauthDataModel ret = new OauthDataModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_SID as COT_SID,");
            sql.addSql("   CMN_OAUTH_TOKEN.COU_SID as COU_SID,");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_ACCOUNTID as COT_ACCOUNTID,");
            sql.addSql("   CMN_OAUTH.COU_PROVIDER as COU_PROVIDER,");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_AUTH_RTOKEN as COT_AUTH_RTOKEN");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" left join ");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" on ");
            sql.addSql("   CMN_OAUTH_TOKEN.COU_SID = CMN_OAUTH.COU_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cotSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.setCotSid(rs.getInt("COT_SID"));
                ret.setCouSid(rs.getInt("COU_SID"));
                ret.setAccountId(rs.getString("COT_ACCOUNTID"));
                ret.setProvider(rs.getInt("COU_PROVIDER"));
                ret.setRefreshToken(rs.getString("COT_AUTH_RTOKEN"));
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
     * <p>Delete CMN_OAUTH_TOKEN
     * @param cotSid COT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int cotSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cotSid);

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
     * <br>[機  能] 削除対象となる認証情報を使用したトークン情報が登録されているかを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param couSid 認証情報SID
     * @return 存在の有無
     * @throws SQLException SQL実行時例外
     */
    public boolean existAuth(int couSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(COU_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where");
            sql.addSql("   COU_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(couSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 現在使用されていないトークン情報のSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableNameList OAuth認証トークンに関連するテーブル名の一覧
     * @return 現在使用されていないトークン情報のSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotUsedSidList(String[] tableNameList)
    throws SQLException {

        List<Integer> sidList = new ArrayList<Integer>();
        if (tableNameList == null || tableNameList.length == 0) {
            return sidList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COT_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_SID not in (");

            for (int idx = 0; idx < tableNameList.length; idx++) {
                if (idx > 0) {
                    sql.addSql("   union");
                }
                sql.addSql("     select COT_SID from " + tableNameList[idx]);
                sql.addSql("     where COT_SID is not null and COT_SID > 0");
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sidList.add(rs.getInt("COT_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sidList;
    }

    /**
     * <p>Delete CMN_OAUTH_TOKEN
     * @param sidList 削除対象SID一覧
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteToken(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            //100件づつ更新する
            for (int idx = 0; idx < sidList.size(); idx += 100) {

                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CMN_OAUTH_TOKEN");
                sql.addSql(" where ");
                sql.addSql("   COT_SID in (");

                for (int tokenIdx = idx;
                        tokenIdx < sidList.size() && tokenIdx < idx + 100; tokenIdx++) {
                    if (tokenIdx > idx) {
                        sql.addSql("     ,?");
                    } else {
                        sql.addSql("     ?");
                    }
                    sql.addIntValue(sidList.get(tokenIdx));
                }
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create CMN_OAUTH_TOKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnOauthTokenModel
     * @throws SQLException SQL実行例外
     */
    private CmnOauthTokenModel __getCmnOauthTokenFromRs(ResultSet rs) throws SQLException {
        CmnOauthTokenModel bean = new CmnOauthTokenModel();
        bean.setCotSid(rs.getInt("COT_SID"));
        bean.setCouSid(rs.getInt("COU_SID"));
        bean.setCotAccountid(rs.getString("COT_ACCOUNTID"));
        bean.setCotAuthRtoken(rs.getString("COT_AUTH_RTOKEN"));
        bean.setCotAuthAtoken(rs.getString("COT_AUTH_ATOKEN"));
        bean.setCotAuthAtokenDate(
                UDate.getInstanceTimestamp(rs.getTimestamp("COT_AUTH_ATOKEN_DATE")));
        return bean;
    }
}
