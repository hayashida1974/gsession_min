package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機 能] ユーザ認証に関係する操作を行うDAOクラス
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class AuthDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AuthDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public AuthDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public AuthDao(Connection con) {
        super(con);
    }

    /**
     * <p>ログイン
     * @param lgid ユーザID(ログインＩＤ)
     * @param pswd パスワード(暗号化後の状態)
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public BaseUserModel selectLoginNoPwd(String lgid, String pswd) throws SQLException {
        return selectLogin(lgid, pswd, false);
    }

    /**
     * <p>ログイン
     * @param lgid ユーザID(ログインＩＤ)
     * @param pswd パスワード(暗号化後の状態)
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public BaseUserModel selectLogin(String lgid, String pswd) throws SQLException {
        return selectLogin(lgid, pswd, true);
    }

    /**
     * <p>ログイン
     * @param lgid ユーザID(ログインＩＤ)
     * @param pswd パスワード(暗号化後の状態)
     * @param usePassword true:パスワードを使用する false:パスワードを使用しない
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public BaseUserModel selectLogin(String lgid, String pswd, boolean usePassword)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BaseUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __writeSelectLoginSql(sql);
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_LGID=?");
            sql.addStrValue(lgid);

            if (usePassword) {
                sql.addSql(" and");
                sql.addSql("   CMN_USRM.USR_PSWD=?");
                sql.addStrValue(pswd);
            }

            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = UserSearchDao.getBaseUserModelFromRs(rs);
                ret.setLgid(lgid);
                ret.setMblUse(rs.getInt("USI_MBL_USE"));
            } else {
                ret = null;
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
     * <p>SIDによるログインユーザ取得
     * @param sid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public BaseUserModel selectLogin(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BaseUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __writeSelectLoginSql(sql);
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = UserSearchDao.getBaseUserModelFromRs(rs);
                ret.setLgid(rs.getString("USR_LGID"));
                ret.setMblUse(rs.getInt("USI_MBL_USE"));
            } else {
                ret = null;
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
     *
     * <br>[機  能] ログイン情報のセレクト文を書き出す
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 書き込むSQLバッファ
     */
    private void __writeSelectLoginSql(SqlBuffer sql) {
        sql.addSql(" select");
        sql.addSql("   CMN_USRM.USR_LGID,");
        sql.addSql("   CMN_USRM.USR_SID,");
        sql.addSql("   CMN_USRM.USR_UKO_FLG,");
        sql.addSql("   CMN_USRM_INF.USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI,");
        sql.addSql("   CMN_USRM_INF.USI_MBL_USE");
        sql.addSql(" from");
        sql.addSql("   CMN_USRM,");
        sql.addSql("   CMN_USRM_INF");
    }
    /**
     * <p>最終ログイン時間のみ更新する
     * @param bean CMN_USRM_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLastLoginTime(CmnUsrmInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" set ");
            sql.addSql("   USI_LTLGIN=?,");
            sql.addSql("   USI_EUID=?,");
            sql.addSql("   USI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getUsiLtlgin());
            sql.addIntValue(bean.getUsiEuid());
            sql.addDateValue(bean.getUsiEdate());
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
}
