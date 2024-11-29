package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnProxyAddressModel;

/**
 * <p>CMN_PROXY_ADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnProxyAddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnProxyAddressDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnProxyAddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnProxyAddressDao(Connection con) {
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
            sql.addSql("drop table CMN_PROXY_ADDRESS");

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
            sql.addSql(" create table CMN_PROXY_ADDRESS (");
            sql.addSql("   CXA_ADDRESS varchar(200) not null,");
            sql.addSql("   CXA_NO NUMBER(10,0) not null,");
            sql.addSql("   primary key (CXA_ADDRESS)");
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
     * <p>Insert CMN_PROXY_ADDRESS Data Bindding JavaBean
     * @param bean CMN_PROXY_ADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnProxyAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_PROXY_ADDRESS(");
            sql.addSql("   CXA_ADDRESS,");
            sql.addSql("   CXA_NO");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCxaAddress());
            sql.addIntValue(bean.getCxaNo());
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
     * <p>Update CMN_PROXY_ADDRESS Data Bindding JavaBean
     * @param bean CMN_PROXY_ADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnProxyAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_PROXY_ADDRESS");
            sql.addSql(" set ");
            sql.addSql("   CXA_NO=?");
            sql.addSql(" where ");
            sql.addSql("   CXA_ADDRESS=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCxaNo());
            //where
            sql.addStrValue(bean.getCxaAddress());

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
     * <p>Select CMN_PROXY_ADDRESS All Data
     * @return List in CMN_PROXY_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnProxyAddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnProxyAddressModel> ret = new ArrayList<CmnProxyAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CXA_ADDRESS,");
            sql.addSql("   CXA_NO");
            sql.addSql(" from ");
            sql.addSql("   CMN_PROXY_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnProxyAddressFromRs(rs));
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
     * <p>Select CMN_PROXY_ADDRESS
     * @param cxaAddress CXA_ADDRESS
     * @return CMN_PROXY_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public CmnProxyAddressModel select(String cxaAddress) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnProxyAddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CXA_ADDRESS,");
            sql.addSql("   CXA_NO");
            sql.addSql(" from");
            sql.addSql("   CMN_PROXY_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   CXA_ADDRESS=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cxaAddress);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnProxyAddressFromRs(rs);
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
     * <p>Delete CMN_PROXY_ADDRESS
     * @param cxaAddress CXA_ADDRESS
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String cxaAddress) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PROXY_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   CXA_ADDRESS=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cxaAddress);

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
     * <br>[機  能] レコードを全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAll() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PROXY_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] アドレスの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アドレス一覧
     * @throws SQLException SQL実行例外
     */
    public String[] getAddressList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> addressList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CXA_ADDRESS");
            sql.addSql(" from ");
            sql.addSql("   CMN_PROXY_ADDRESS");
            sql.addSql(" order by ");
            sql.addSql("   CXA_NO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                addressList.add(rs.getString("CXA_ADDRESS"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addressList.toArray(new String[addressList.size()]);
    }

    /**
     * <p>Create CMN_PROXY_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnProxyAddressModel
     * @throws SQLException SQL実行例外
     */
    private CmnProxyAddressModel __getCmnProxyAddressFromRs(ResultSet rs) throws SQLException {
        CmnProxyAddressModel bean = new CmnProxyAddressModel();
        bean.setCxaAddress(rs.getString("CXA_ADDRESS"));
        bean.setCxaNo(rs.getInt("CXA_NO"));
        return bean;
    }
}
