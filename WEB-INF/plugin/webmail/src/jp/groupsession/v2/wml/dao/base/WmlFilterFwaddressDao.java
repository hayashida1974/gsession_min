package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlFilterFwaddressModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_FILTER_FWADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterFwaddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlFilterFwaddressDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlFilterFwaddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlFilterFwaddressDao(Connection con) {
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
            sql.addSql("drop table WML_FILTER_FWADDRESS");

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
            sql.addSql(" create table WML_FILTER_FWADDRESS (");
            sql.addSql("   WFT_SID NUMBER(10,0) not null,");
            sql.addSql("   WFA_NO NUMBER(10,0) not null,");
            sql.addSql("   WFA_ADDRESS varchar(256) not null,");
            sql.addSql("   primary key (WFT_SID,WFA_NO)");
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
     * <p>Insert WML_FILTER_FWADDRESS Data Bindding JavaBean
     * @param bean WML_FILTER_FWADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlFilterFwaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_FILTER_FWADDRESS(");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFA_NO,");
            sql.addSql("   WFA_ADDRESS");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWftSid());
            sql.addIntValue(bean.getWfaNo());
            sql.addStrValue(bean.getWfaAddress());
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
     * <p>Update WML_FILTER_FWADDRESS Data Bindding JavaBean
     * @param bean WML_FILTER_FWADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlFilterFwaddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_FILTER_FWADDRESS");
            sql.addSql(" set ");
            sql.addSql("   WFA_ADDRESS=?");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFA_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWfaAddress());
            //where
            sql.addIntValue(bean.getWftSid());
            sql.addIntValue(bean.getWfaNo());

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
     * <p>Select WML_FILTER_FWADDRESS All Data
     * @return List in WML_FILTER_FWADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterFwaddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlFilterFwaddressModel> ret = new ArrayList<WmlFilterFwaddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFA_NO,");
            sql.addSql("   WFA_ADDRESS");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_FWADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterFwaddressFromRs(rs));
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
     * <p>Select WML_FILTER_FWADDRESS
     * @param wftSid WFT_SID
     * @param wfaNo WFA_NO
     * @return WML_FILTER_FWADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public WmlFilterFwaddressModel select(int wftSid, int wfaNo) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlFilterFwaddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFA_NO,");
            sql.addSql("   WFA_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFA_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);
            sql.addIntValue(wfaNo);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlFilterFwaddressFromRs(rs);
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
     * <p>Delete WML_FILTER_FWADDRESS
     * @param wftSid WFT_SID
     * @param wfaNo WFA_NO
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wftSid, int wfaNo) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFA_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);
            sql.addIntValue(wfaNo);

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
     * <p>Delete WML_FILTER_FWADDRESS
     * @param wftSid WFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);

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
     * <br>[機  能] フィルター_転送先メールアドレス情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteAll() throws SQLException {

        Statement stmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS");
            log__.info(sql.toLogString());

            stmt = con.createStatement();
            count = stmt.executeUpdate(sql.toSqlString());
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(stmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したフィルターの転送先メールアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wftSid フィルターSID
     * @return 転送先メールアドレス
     * @throws SQLException SQL実行例外
     */
    public List<String> getAddressList(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> addressList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFA_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_FWADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                addressList.add(rs.getString("WFA_ADDRESS"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addressList;
    }

    /**
     * <p>Create WML_FILTER_FWADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlFilterFwaddressModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterFwaddressModel __getWmlFilterFwaddressFromRs(ResultSet rs)
    throws SQLException {
        WmlFilterFwaddressModel bean = new WmlFilterFwaddressModel();
        bean.setWftSid(rs.getInt("WFT_SID"));
        bean.setWfaNo(rs.getInt("WFA_NO"));
        bean.setWfaAddress(rs.getString("WFA_ADDRESS"));
        return bean;
    }
}
