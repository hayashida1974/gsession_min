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
import jp.groupsession.v2.cmn.cmn250.Cmn250DisplayModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;

/**
 * <p>CMN_OAUTH Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOauthDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnOauthDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnOauthDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnOauthDao(Connection con) {
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
            sql.addSql("drop table CMN_OAUTH");

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
            sql.addSql(" create table CMN_OAUTH (");
            sql.addSql("   COU_SID integer not null,");
            sql.addSql("   COU_PROVIDER integer not null,");
            sql.addSql("   COU_AUTH_ID varchar(1000) not null,");
            sql.addSql("   COU_AUTH_SECRET varchar(1000) not null,");
            sql.addSql("   COU_BIKO varchar(1000) not null,");
            sql.addSql("   COU_AUID integer not null,");
            sql.addSql("   COU_ADATE timestamp not null,");
            sql.addSql("   COU_EUID integer not null,");
            sql.addSql("   COU_EDATE timestamp not null,");
            sql.addSql("   primary key (COU_SID)");
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
     * <p>Insert CMN_OAUTH Data Bindding JavaBean
     * @param bean CMN_OAUTH Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnOauthModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_OAUTH(");
            sql.addSql("   COU_SID,");
            sql.addSql("   COU_PROVIDER,");
            sql.addSql("   COU_AUTH_ID,");
            sql.addSql("   COU_AUTH_SECRET,");
            sql.addSql("   COU_BIKO,");
            sql.addSql("   COU_AUID,");
            sql.addSql("   COU_ADATE,");
            sql.addSql("   COU_EUID,");
            sql.addSql("   COU_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCouSid());
            sql.addIntValue(bean.getCouProvider());
            sql.addStrValue(bean.getCouAuthId());
            sql.addStrValue(bean.getCouAuthSecret());
            sql.addStrValue(bean.getCouBiko());
            sql.addIntValue(bean.getCouAuid());
            sql.addDateValue(bean.getCouAdate());
            sql.addIntValue(bean.getCouEuid());
            sql.addDateValue(bean.getCouEdate());
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
     * <p>Update CMN_OAUTH Data Bindding JavaBean
     * @param bean CMN_OAUTH Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnOauthModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" set ");
            sql.addSql("   COU_PROVIDER=?,");
            sql.addSql("   COU_AUTH_ID=?,");
            sql.addSql("   COU_AUTH_SECRET=?,");
            sql.addSql("   COU_BIKO=?,");
            sql.addSql("   COU_AUID=?,");
            sql.addSql("   COU_ADATE=?,");
            sql.addSql("   COU_EUID=?,");
            sql.addSql("   COU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCouProvider());
            sql.addStrValue(bean.getCouAuthId());
            sql.addStrValue(bean.getCouAuthSecret());
            sql.addStrValue(bean.getCouBiko());
            sql.addIntValue(bean.getCouAuid());
            sql.addDateValue(bean.getCouAdate());
            sql.addIntValue(bean.getCouEuid());
            sql.addDateValue(bean.getCouEdate());
            //where
            sql.addIntValue(bean.getCouSid());

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
     * <p>Update CMN_OAUTH Data Bindding JavaBean
     * @param bean CMN_OAUTH Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateOAuth(CmnOauthModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" set ");
            sql.addSql("   COU_PROVIDER=?,");
            sql.addSql("   COU_AUTH_ID=?,");
            sql.addSql("   COU_AUTH_SECRET=?,");
            sql.addSql("   COU_BIKO=?,");
            sql.addSql("   COU_EUID=?,");
            sql.addSql("   COU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCouProvider());
            sql.addStrValue(bean.getCouAuthId());
            sql.addStrValue(bean.getCouAuthSecret());
            sql.addStrValue(bean.getCouBiko());
            sql.addIntValue(bean.getCouEuid());
            sql.addDateValue(bean.getCouEdate());
            //where
            sql.addIntValue(bean.getCouSid());

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
     * <p>Select CMN_OAUTH All Data
     * @return List in CMN_OAUTHModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnOauthModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnOauthModel> ret = new ArrayList<CmnOauthModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COU_SID,");
            sql.addSql("   COU_PROVIDER,");
            sql.addSql("   COU_AUTH_ID,");
            sql.addSql("   COU_AUTH_SECRET,");
            sql.addSql("   COU_BIKO,");
            sql.addSql("   COU_AUID,");
            sql.addSql("   COU_ADATE,");
            sql.addSql("   COU_EUID,");
            sql.addSql("   COU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnOauthFromRs(rs));
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
     * <p>Select CMN_OAUTH
     * @param couSid COU_SID
     * @return CMN_OAUTHModel
     * @throws SQLException SQL実行例外
     */
    public CmnOauthModel select(int couSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOauthModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COU_SID,");
            sql.addSql("   COU_PROVIDER,");
            sql.addSql("   COU_AUTH_ID,");
            sql.addSql("   COU_AUTH_SECRET,");
            sql.addSql("   COU_BIKO,");
            sql.addSql("   COU_AUID,");
            sql.addSql("   COU_ADATE,");
            sql.addSql("   COU_EUID,");
            sql.addSql("   COU_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(couSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnOauthFromRs(rs);
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
     * <p>Delete CMN_OAUTH
     * @param couSid COU_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int couSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(couSid);

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
     * <br>[機  能] 指定したプロバイダの認証情報SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param provider プロバイダ
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int getOAuthSid(int provider) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COU_SID as SID");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("   COU_PROVIDER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(provider);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("SID");
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
     * <br>[機  能] 認証情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 認証情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Cmn250DisplayModel> getAuthNameList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Cmn250DisplayModel> ret = new ArrayList<Cmn250DisplayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COU_SID, ");
            sql.addSql("   COU_PROVIDER");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cmn250DisplayModel model = new Cmn250DisplayModel();
                model.setCouSid(rs.getInt("COU_SID"));
                model.setCouProvider(rs.getInt("COU_PROVIDER"));

                ret.add(model);
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
     * <br>[機  能] 対象OAuth認証情報の存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param couSid Oauth認証情報SID
     * @return 存在の有無
     * @throws SQLException SQL実行時例外
     */
    public boolean existOauthData(int couSid) throws SQLException {

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
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("  COU_SID=?");
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
     * <br>[機  能] 認証情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param couSid 認証情報SID
     * @return 認証情報
     * @throws SQLException SQL実行時例外
     */
    public CmnOauthModel getAuth(int couSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnOauthModel couMdl = new CmnOauthModel();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COU_PROVIDER,");
            sql.addSql("   COU_AUTH_ID,");
            sql.addSql("   COU_AUTH_SECRET,");
            sql.addSql("   COU_BIKO");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("   COU_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(couSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                couMdl.setCouProvider(rs.getInt("COU_PROVIDER"));
                couMdl.setCouAuthId(rs.getString("COU_AUTH_ID"));
                couMdl.setCouAuthSecret(rs.getString("COU_AUTH_SECRET"));
                couMdl.setCouBiko(rs.getString("COU_BIKO"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return couMdl;
    }

    /**
     * <br>[機  能] 指定したアカウントが選択しているOAuth認証情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cotSid OAuth認証トークンSID
     * @return OAuth認証情報
     * @throws SQLException SQL実行時例外
     */
    public CmnOauthModel getAuthData(int cotSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOauthModel couMdl = new CmnOauthModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_OAUTH.COU_SID as COU_SID,");
            sql.addSql("   CMN_OAUTH.COU_PROVIDER as COU_PROVIDER");
            sql.addSql(" from ");
            sql.addSql("   CMN_OAUTH,");
            sql.addSql("   CMN_OAUTH_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   CMN_OAUTH_TOKEN.COT_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CMN_OAUTH.COU_SID = CMN_OAUTH_TOKEN.COU_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cotSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                couMdl.setCouSid(rs.getInt("COU_SID"));
                couMdl.setCouProvider(rs.getInt("COU_PROVIDER"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return couMdl;
    }

    /**
     * <br>[機  能] 対象プロバイダの存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param provider プロバイダ
     * @return 存在の有無
     * @throws SQLException SQL実行時例外
     */
    public boolean existProvider(int provider) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("  COU_PROVIDER=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(provider);

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
     * <br>[機  能] 対象OAuth認証情報の存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param provider プロバイダ
     * @param couSid OAuth認証情報SID
     * @return 存在の有無
     * @throws SQLException SQL実行時例外
     */
    public boolean existProvider(int provider, int couSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_OAUTH");
            sql.addSql(" where ");
            sql.addSql("  COU_PROVIDER = ?");
            sql.addSql(" and");
            sql.addSql("  COU_SID != ?");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(provider);
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
     * <p>Create CMN_OAUTH Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnOauthModel
     * @throws SQLException SQL実行例外
     */
    private CmnOauthModel __getCmnOauthFromRs(ResultSet rs) throws SQLException {
        CmnOauthModel bean = new CmnOauthModel();
        bean.setCouSid(rs.getInt("COU_SID"));
        bean.setCouProvider(rs.getInt("COU_PROVIDER"));
        bean.setCouAuthId(rs.getString("COU_AUTH_ID"));
        bean.setCouAuthSecret(rs.getString("COU_AUTH_SECRET"));
        bean.setCouBiko(rs.getString("COU_BIKO"));
        bean.setCouAuid(rs.getInt("COU_AUID"));
        bean.setCouAdate(UDate.getInstanceTimestamp(rs.getTimestamp("COU_ADATE")));
        bean.setCouEuid(rs.getInt("COU_EUID"));
        bean.setCouEdate(UDate.getInstanceTimestamp(rs.getTimestamp("COU_EDATE")));
        return bean;
    }
}
