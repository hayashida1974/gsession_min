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
import jp.groupsession.v2.cmn.model.base.CmnOtpAtokenModel;

/**
 * <p>CMN_OTP_ATOKEN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnOtpAtokenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnOtpAtokenDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnOtpAtokenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnOtpAtokenDao(Connection con) {
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
            sql.addSql("drop table CMN_OTP_ATOKEN");

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
            sql.addSql(" create table CMN_OTP_ATOKEN (");
            sql.addSql("   COA_TOKEN varchar(256) not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   COA_PASS varchar(4),");
            sql.addSql("   COA_ADDRESS varchar(256),");
            sql.addSql("   COA_LIMIT_DATE timestamp,");
            sql.addSql("   COA_DATE timestamp,");
            sql.addSql("   primary key (COA_TOKEN)");
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
     * <p>Insert CMN_OTP_ATOKEN Data Bindding JavaBean
     * @param bean CMN_OTP_ATOKEN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnOtpAtokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_OTP_ATOKEN(");
            sql.addSql("   COA_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COA_PASS,");
            sql.addSql("   COA_ADDRESS,");
            sql.addSql("   COA_LIMIT_DATE,");
            sql.addSql("   COA_DATE");
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
            sql.addStrValue(bean.getCoaToken());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCoaPass());
            sql.addStrValue(bean.getCoaAddress());
            sql.addDateValue(bean.getCoaLimitDate());
            sql.addDateValue(bean.getCoaDate());
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
     * <p>Update CMN_OTP_ATOKEN Data Bindding JavaBean
     * @param bean CMN_OTP_ATOKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnOtpAtokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_OTP_ATOKEN");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   COA_PASS=?,");
            sql.addSql("   COA_ADDRESS=?,");
            sql.addSql("   COA_LIMIT_DATE=?,");
            sql.addSql("   COA_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   COA_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCoaPass());
            sql.addStrValue(bean.getCoaAddress());
            sql.addDateValue(bean.getCoaLimitDate());
            sql.addDateValue(bean.getCoaDate());
            //where
            sql.addStrValue(bean.getCoaToken());

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
     * <p>Select CMN_OTP_ATOKEN All Data
     * @return List in CMN_OTP_ATOKENModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnOtpAtokenModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnOtpAtokenModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COA_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COA_PASS,");
            sql.addSql("   COA_ADDRESS,");
            sql.addSql("   COA_LIMIT_DATE,");
            sql.addSql("   COA_DATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_OTP_ATOKEN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnOtpAtokenFromRs(rs));
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
     * <p>Select CMN_OTP_ATOKEN
     * @param coaToken COA_TOKEN
     * @return CMN_OTP_ATOKENModel
     * @throws SQLException SQL実行例外
     */
    public CmnOtpAtokenModel select(String coaToken) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnOtpAtokenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COA_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   COA_PASS,");
            sql.addSql("   COA_ADDRESS,");
            sql.addSql("   COA_LIMIT_DATE,");
            sql.addSql("   COA_DATE");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_ATOKEN");
            sql.addSql(" where ");
            sql.addSql("   COA_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(coaToken);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnOtpAtokenFromRs(rs);
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
     * <p>Delete CMN_OTP_ATOKEN
     * @param coaToken COA_TOKEN
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(String coaToken) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_ATOKEN");
            sql.addSql(" where ");
            sql.addSql("   COA_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(coaToken);

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
     * <p>Create CMN_OTP_ATOKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnOtpAtokenModel
     * @throws SQLException SQL実行例外
     */
    private CmnOtpAtokenModel __getCmnOtpAtokenFromRs(ResultSet rs) throws SQLException {
        CmnOtpAtokenModel bean = new CmnOtpAtokenModel();
        bean.setCoaToken(rs.getString("COA_TOKEN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCoaPass(rs.getString("COA_PASS"));
        bean.setCoaAddress(rs.getString("COA_ADDRESS"));
        bean.setCoaLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("COA_LIMIT_DATE")));
        bean.setCoaDate(UDate.getInstanceTimestamp(rs.getTimestamp("COA_DATE")));
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
            sql.addSql("   CMN_OTP_ATOKEN");
            sql.addSql(" where ");
            sql.addSql("   COA_LIMIT_DATE < ?");

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
