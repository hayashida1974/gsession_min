package jp.groupsession.v2.man.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_PCONF_EDIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnPconfEditDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnPconfEditDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnPconfEditDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnPconfEditDao(Connection con) {
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
            sql.addSql("drop table CMN_PCONF_EDIT");

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
            sql.addSql(" create table CMN_PCONF_EDIT (");
            sql.addSql("   CPE_PCONF_KBN integer not null,");
            sql.addSql("   CPE_PASSWORD_KBN integer not null,");
            sql.addSql("   CPE_AUID integer not null,");
            sql.addSql("   CPE_ADATE timestamp not null,");
            sql.addSql("   CPE_EUID integer not null,");
            sql.addSql("   CPE_EDATE timestamp not null,");
            sql.addSql("   CPE_OTPSEND_KBN integer not null");
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
     * <p>Insert CMN_PCONF_EDIT Data Bindding JavaBean
     * @param bean CMN_PCONF_EDIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnPconfEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PCONF_EDIT(");
            sql.addSql("   CPE_PCONF_KBN,");
            sql.addSql("   CPE_PASSWORD_KBN,");
            sql.addSql("   CPE_AUID,");
            sql.addSql("   CPE_ADATE,");
            sql.addSql("   CPE_EUID,");
            sql.addSql("   CPE_EDATE,");
            sql.addSql("   CPE_OTPSEND_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpePconfKbn());
            sql.addIntValue(bean.getCpePasswordKbn());
            sql.addIntValue(bean.getCpeAuid());
            sql.addDateValue(bean.getCpeAdate());
            sql.addIntValue(bean.getCpeEuid());
            sql.addDateValue(bean.getCpeEdate());
            sql.addIntValue(bean.getCpeOtpsendKbn());
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
     * <p>Update CMN_PCONF_EDIT Data Bindding JavaBean
     * @param bean CMN_PCONF_EDIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnPconfEditModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PCONF_EDIT");
            sql.addSql(" set ");
            sql.addSql("   CPE_PCONF_KBN=?,");
            sql.addSql("   CPE_PASSWORD_KBN=?,");
            sql.addSql("   CPE_AUID=?,");
            sql.addSql("   CPE_ADATE=?,");
            sql.addSql("   CPE_EUID=?,");
            sql.addSql("   CPE_EDATE=?,");
            sql.addSql("   CPE_OTPSEND_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpePconfKbn());
            sql.addIntValue(bean.getCpePasswordKbn());
            sql.addIntValue(bean.getCpeAuid());
            sql.addDateValue(bean.getCpeAdate());
            sql.addIntValue(bean.getCpeEuid());
            sql.addDateValue(bean.getCpeEdate());
            sql.addIntValue(bean.getCpeOtpsendKbn());

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
     * <p>Select CMN_PCONF_EDIT All Data
     * @return List in CMN_PCONF_EDITModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnPconfEditModel> select() throws SQLException {

        ArrayList<CmnPconfEditModel> ret = new ArrayList<CmnPconfEditModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CPE_PCONF_KBN,");
            sql.addSql("   CPE_PASSWORD_KBN,");
            sql.addSql("   CPE_AUID,");
            sql.addSql("   CPE_ADATE,");
            sql.addSql("   CPE_EUID,");
            sql.addSql("   CPE_EDATE,");
            sql.addSql("   CPE_OTPSEND_KBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_PCONF_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.add(__getCmnPconfEditFromRs(rs));
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
     * <p>Select CMN_PCONF_EDIT All Data
     * @return List in CMN_PCONF_EDITModel
     * @throws SQLException SQL実行例外
     */
    public CmnPconfEditModel selectConf() throws SQLException {

        CmnPconfEditModel ret = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CPE_PCONF_KBN,");
            sql.addSql("   CPE_PASSWORD_KBN,");
            sql.addSql("   CPE_AUID,");
            sql.addSql("   CPE_ADATE,");
            sql.addSql("   CPE_EUID,");
            sql.addSql("   CPE_EDATE,");
            sql.addSql("   CPE_OTPSEND_KBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_PCONF_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnPconfEditFromRs(rs);
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
     * <p>Create CMN_PCONF_EDIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnPconfEditModel
     * @throws SQLException SQL実行例外
     */
    private CmnPconfEditModel __getCmnPconfEditFromRs(ResultSet rs) throws SQLException {
        CmnPconfEditModel bean = new CmnPconfEditModel();
        bean.setCpePconfKbn(rs.getInt("CPE_PCONF_KBN"));
        bean.setCpePasswordKbn(rs.getInt("CPE_PASSWORD_KBN"));
        bean.setCpeAuid(rs.getInt("CPE_AUID"));
        bean.setCpeAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPE_ADATE")));
        bean.setCpeEuid(rs.getInt("CPE_EUID"));
        bean.setCpeEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPE_EDATE")));
        bean.setCpeOtpsendKbn(rs.getInt("CPE_OTPSEND_KBN"));
        return bean;
    }

    /**
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int selectCount() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CPE");
            sql.addSql(" from ");
            sql.addSql("   CMN_PCONF_EDIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CPE");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }
}
