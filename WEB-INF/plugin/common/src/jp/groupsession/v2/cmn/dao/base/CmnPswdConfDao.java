package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_TDFK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPswdConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPswdConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPswdConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPswdConfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_TDFK Data Bindding JavaBean
     * @param bean CMN_TDFK Data Bindding JavaBean
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insert(CmnPswdConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PSWD_CONF(");
            sql.addSql("   PWC_DIGIT,");
            sql.addSql("   PWC_COE,");
            sql.addSql("   PWC_UPPERCASE,");
            sql.addSql("   PWC_LIMIT_DAY,");
            sql.addSql("   PWC_UIDPSWD,");
            sql.addSql("   PWC_OLDPSWD,");
            sql.addSql("   PWC_AUID,");
            sql.addSql("   PWC_ADATE,");
            sql.addSql("   PWC_EUID,");
            sql.addSql("   PWC_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPwcDigit());
            sql.addIntValue(bean.getPwcCoe());
            sql.addIntValue(bean.getPwcUppercase());
            sql.addIntValue(bean.getPwcLimitDay());
            sql.addIntValue(bean.getPwcUidPswd());
            sql.addIntValue(bean.getPwcOldPswd());
            sql.addIntValue(bean.getPwcAuid());
            sql.addDateValue(bean.getPwcAdate());
            sql.addIntValue(bean.getPwcEuid());
            sql.addDateValue(bean.getPwcEdate());

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
    public int update(CmnPswdConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PSWD_CONF");
            sql.addSql(" set ");
            sql.addSql("   PWC_DIGIT=?,");
            sql.addSql("   PWC_COE=?,");
            sql.addSql("   PWC_UPPERCASE=?,");
            sql.addSql("   PWC_LIMIT_DAY=?,");
            sql.addSql("   PWC_UIDPSWD=?,");
            sql.addSql("   PWC_OLDPSWD=?,");
            sql.addSql("   PWC_EUID=?,");
            sql.addSql("   PWC_EDATE=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPwcDigit());
            sql.addIntValue(bean.getPwcCoe());
            sql.addIntValue(bean.getPwcUppercase());
            sql.addIntValue(bean.getPwcLimitDay());
            sql.addIntValue(bean.getPwcUidPswd());
            sql.addIntValue(bean.getPwcOldPswd());
            sql.addIntValue(bean.getPwcEuid());
            sql.addDateValue(bean.getPwcEdate());

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
    public CmnPswdConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnPswdConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PWC_DIGIT,");
            sql.addSql("   PWC_COE,");
            sql.addSql("   PWC_UPPERCASE,");
            sql.addSql("   PWC_LIMIT_DAY,");
            sql.addSql("   PWC_UIDPSWD,");
            sql.addSql("   PWC_OLDPSWD,");
            sql.addSql("   PWC_AUID,");
            sql.addSql("   PWC_ADATE,");
            sql.addSql("   PWC_EUID,");
            sql.addSql("   PWC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_PSWD_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCmnPswdConfFromRs(rs);
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
     * <p>Create CMN_TDFK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnTdfkModel
     * @throws SQLException SQL実行例外
     */
    private CmnPswdConfModel __getCmnPswdConfFromRs(ResultSet rs) throws SQLException {
        CmnPswdConfModel bean = new CmnPswdConfModel();
        bean.setPwcDigit(rs.getInt("PWC_DIGIT"));
        bean.setPwcCoe(rs.getInt("PWC_COE"));
        bean.setPwcUppercase(rs.getInt("PWC_UPPERCASE"));
        bean.setPwcLimitDay(rs.getInt("PWC_LIMIT_DAY"));
        bean.setPwcUidPswd(rs.getInt("PWC_UIDPSWD"));
        bean.setPwcOldPswd(rs.getInt("PWC_OLDPSWD"));
        bean.setPwcAuid(rs.getInt("PWC_AUID"));
        bean.setPwcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PWC_ADATE")));
        bean.setPwcEuid(rs.getInt("PWC_EUID"));
        bean.setPwcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PWC_EDATE")));
        return bean;
    }
}
