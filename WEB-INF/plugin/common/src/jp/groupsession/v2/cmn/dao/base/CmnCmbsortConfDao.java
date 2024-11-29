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
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;

/**
 * <p>CMN_CMBSORT_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnCmbsortConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnCmbsortConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnCmbsortConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnCmbsortConfDao(Connection con) {
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
            sql.addSql("drop table CMN_CMBSORT_CONF");

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
            sql.addSql(" create table CMN_CMBSORT_CONF (");
            sql.addSql("   CSC_USER_SKEY1 NUMBER(10,0) not null,");
            sql.addSql("   CSC_USER_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   CSC_USER_SKEY2 NUMBER(10,0) not null,");
            sql.addSql("   CSC_USER_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   CSC_GROUP_SKBN NUMBER(10,0) not null,");
            sql.addSql("   CSC_GROUP_SKEY1 NUMBER(10,0) not null,");
            sql.addSql("   CSC_GROUP_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   CSC_GROUP_SKEY2 NUMBER(10,0) not null,");
            sql.addSql("   CSC_GROUP_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   CSC_AUID NUMBER(10,0) not null,");
            sql.addSql("   CSC_ADATE varchar(23) not null,");
            sql.addSql("   CSC_EUID NUMBER(10,0) not null,");
            sql.addSql("   CSC_EDATE varchar(23) not null");
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
     * <p>Insert CMN_CMBSORT_CONF Data Bindding JavaBean
     * @param bean CMN_CMBSORT_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnCmbsortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_CMBSORT_CONF(");
            sql.addSql("   CSC_USER_SKEY1,");
            sql.addSql("   CSC_USER_ORDER1,");
            sql.addSql("   CSC_USER_SKEY2,");
            sql.addSql("   CSC_USER_ORDER2,");
            sql.addSql("   CSC_GROUP_SKBN,");
            sql.addSql("   CSC_GROUP_SKEY1,");
            sql.addSql("   CSC_GROUP_ORDER1,");
            sql.addSql("   CSC_GROUP_SKEY2,");
            sql.addSql("   CSC_GROUP_ORDER2,");
            sql.addSql("   CSC_AUID,");
            sql.addSql("   CSC_ADATE,");
            sql.addSql("   CSC_EUID,");
            sql.addSql("   CSC_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCscUserSkey1());
            sql.addIntValue(bean.getCscUserOrder1());
            sql.addIntValue(bean.getCscUserSkey2());
            sql.addIntValue(bean.getCscUserOrder2());
            sql.addIntValue(bean.getCscGroupSkbn());
            sql.addIntValue(bean.getCscGroupSkey1());
            sql.addIntValue(bean.getCscGroupOrder1());
            sql.addIntValue(bean.getCscGroupSkey2());
            sql.addIntValue(bean.getCscGroupOrder2());
            sql.addIntValue(bean.getCscAuid());
            sql.addDateValue(bean.getCscAdate());
            sql.addIntValue(bean.getCscEuid());
            sql.addDateValue(bean.getCscEdate());
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
     * <p>Update CMN_CMBSORT_CONF Data Bindding JavaBean
     * @param bean CMN_CMBSORT_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnCmbsortConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CMBSORT_CONF");
            sql.addSql(" set ");
            sql.addSql("   CSC_USER_SKEY1=?,");
            sql.addSql("   CSC_USER_ORDER1=?,");
            sql.addSql("   CSC_USER_SKEY2=?,");
            sql.addSql("   CSC_USER_ORDER2=?,");
            sql.addSql("   CSC_GROUP_SKBN=?,");
            sql.addSql("   CSC_GROUP_SKEY1=?,");
            sql.addSql("   CSC_GROUP_ORDER1=?,");
            sql.addSql("   CSC_GROUP_SKEY2=?,");
            sql.addSql("   CSC_GROUP_ORDER2=?,");
            sql.addSql("   CSC_EUID=?,");
            sql.addSql("   CSC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCscUserSkey1());
            sql.addIntValue(bean.getCscUserOrder1());
            sql.addIntValue(bean.getCscUserSkey2());
            sql.addIntValue(bean.getCscUserOrder2());
            sql.addIntValue(bean.getCscGroupSkbn());
            sql.addIntValue(bean.getCscGroupSkey1());
            sql.addIntValue(bean.getCscGroupOrder1());
            sql.addIntValue(bean.getCscGroupSkey2());
            sql.addIntValue(bean.getCscGroupOrder2());
            sql.addIntValue(bean.getCscEuid());
            sql.addDateValue(bean.getCscEdate());

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
     * <p>Select CMN_CMBSORT_CONF All Data
     * @return CMN_CMBSORT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnCmbsortConfModel getCmbSortData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnCmbsortConfModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CSC_USER_SKEY1,");
            sql.addSql("   CSC_USER_ORDER1,");
            sql.addSql("   CSC_USER_SKEY2,");
            sql.addSql("   CSC_USER_ORDER2,");
            sql.addSql("   CSC_GROUP_SKBN,");
            sql.addSql("   CSC_GROUP_SKEY1,");
            sql.addSql("   CSC_GROUP_ORDER1,");
            sql.addSql("   CSC_GROUP_SKEY2,");
            sql.addSql("   CSC_GROUP_ORDER2,");
            sql.addSql("   CSC_AUID,");
            sql.addSql("   CSC_ADATE,");
            sql.addSql("   CSC_EUID,");
            sql.addSql("   CSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_CMBSORT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getCmnCmbsortConfFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }

    /**
     * <p>Select CMN_CMBSORT_CONF All Data
     * @return List in CMN_CMBSORT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnCmbsortConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnCmbsortConfModel> ret = new ArrayList<CmnCmbsortConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CSC_USER_SKEY1,");
            sql.addSql("   CSC_USER_ORDER1,");
            sql.addSql("   CSC_USER_SKEY2,");
            sql.addSql("   CSC_USER_ORDER2,");
            sql.addSql("   CSC_GROUP_SKBN,");
            sql.addSql("   CSC_GROUP_SKEY1,");
            sql.addSql("   CSC_GROUP_ORDER1,");
            sql.addSql("   CSC_GROUP_SKEY2,");
            sql.addSql("   CSC_GROUP_ORDER2,");
            sql.addSql("   CSC_AUID,");
            sql.addSql("   CSC_ADATE,");
            sql.addSql("   CSC_EUID,");
            sql.addSql("   CSC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_CMBSORT_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnCmbsortConfFromRs(rs));
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
     * <p>Create CMN_CMBSORT_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnCmbsortConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnCmbsortConfModel __getCmnCmbsortConfFromRs(ResultSet rs) throws SQLException {
        CmnCmbsortConfModel bean = new CmnCmbsortConfModel();
        bean.setCscUserSkey1(rs.getInt("CSC_USER_SKEY1"));
        bean.setCscUserOrder1(rs.getInt("CSC_USER_ORDER1"));
        bean.setCscUserSkey2(rs.getInt("CSC_USER_SKEY2"));
        bean.setCscUserOrder2(rs.getInt("CSC_USER_ORDER2"));
        bean.setCscGroupSkbn(rs.getInt("CSC_GROUP_SKBN"));
        bean.setCscGroupSkey1(rs.getInt("CSC_GROUP_SKEY1"));
        bean.setCscGroupOrder1(rs.getInt("CSC_GROUP_ORDER1"));
        bean.setCscGroupSkey2(rs.getInt("CSC_GROUP_SKEY2"));
        bean.setCscGroupOrder2(rs.getInt("CSC_GROUP_ORDER2"));
        bean.setCscAuid(rs.getInt("CSC_AUID"));
        bean.setCscAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CSC_ADATE")));
        bean.setCscEuid(rs.getInt("CSC_EUID"));
        bean.setCscEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CSC_EDATE")));
        return bean;
    }
}
