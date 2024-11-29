package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnOtpTokenModel;

/**
 * <p>CMN_OTP_TOKEN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpTokenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnOtpTokenDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnOtpTokenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnOtpTokenDao(Connection con) {
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
            sql.addSql("drop table CMN_OTP_TOKEN");

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
            sql.addSql(" create table CMN_OTP_TOKEN (");
            sql.addSql("   COT_TOKEN varchar(256) not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   COT_PASS varchar(4),");
            sql.addSql("   COT_LIMIT_DATE timestamp,");
            sql.addSql("   COT_DATE timestamp,");
            sql.addSql("   primary key (COT_TOKEN)");
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
     * <p>Insert CMN_OTP_TOKEN Data Bindding JavaBean
     * @param bean CMN_OTP_TOKEN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnOtpTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_OTP_TOKEN(");
            sql.addSql("   COT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COT_PASS,");
            sql.addSql("   COT_LIMIT_DATE,");
            sql.addSql("   COT_DATE");
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
            sql.addStrValue(bean.getCotToken());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCotPass());
            sql.addDateValue(bean.getCotLimitDate());
            sql.addDateValue(bean.getCotDate());
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
     * <p>Update CMN_OTP_TOKEN Data Bindding JavaBean
     * @param bean CMN_OTP_TOKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnOtpTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OTP_TOKEN");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   COT_PASS=?,");
            sql.addSql("   COT_LIMIT_DATE=?,");
            sql.addSql("   COT_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   COT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCotPass());
            sql.addDateValue(bean.getCotLimitDate());
            sql.addDateValue(bean.getCotDate());
            //where
            sql.addStrValue(bean.getCotToken());

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
     * <p>Select CMN_OTP_TOKEN All Data
     * @return List in CMN_OTP_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnOtpTokenModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnOtpTokenModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COT_PASS,");
            sql.addSql("   COT_LIMIT_DATE,");
            sql.addSql("   COT_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_OTP_TOKEN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnOtpTokenFromRs(rs));
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
     * <p>Select CMN_OTP_TOKEN
     * @param cotToken COT_TOKEN
     * @return CMN_OTP_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public CmnOtpTokenModel select(String cotToken) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOtpTokenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COT_PASS,");
            sql.addSql("   COT_LIMIT_DATE,");
            sql.addSql("   COT_DATE");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cotToken);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnOtpTokenFromRs(rs);
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
     * <p>Delete CMN_OTP_TOKEN
     * @param cotToken COT_TOKEN
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(String cotToken) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cotToken);

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
     * <p>Create CMN_OTP_TOKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnOtpTokenModel
     * @throws SQLException SQL実行例外
     */
    private CmnOtpTokenModel __getCmnOtpTokenFromRs(ResultSet rs) throws SQLException {
        CmnOtpTokenModel bean = new CmnOtpTokenModel();
        bean.setCotToken(rs.getString("COT_TOKEN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCotPass(rs.getString("COT_PASS"));
        bean.setCotLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("COT_LIMIT_DATE")));
        bean.setCotDate(UDate.getInstanceTimestamp(rs.getTimestamp("COT_DATE")));
        return bean;
    }

    /**
    *
    * <br>[機  能] 有効期限切れのTokenの削除
    * <br>[解  説]
    * <br>[備  考]
    * @param now 現在時
    * @return 削除件数
    * @throws SQLException SQL実行時例外
    */
    public int deleteLimitOver(UDate now) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   COT_LIMIT_DATE < ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(now);

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
