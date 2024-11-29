package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlFilterModel;

/**
 * <p>WML_FILTER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlFilterDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlFilterDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlFilterDao(Connection con) {
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
            sql.addSql("drop table WML_FILTER");

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
            sql.addSql(" create table WML_FILTER (");
            sql.addSql("   WFT_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   WFT_NAME varchar(100) not null,");
            sql.addSql("   WFT_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0),");
            sql.addSql("   WFT_TEMPFILE NUMBER(10,0) not null,");
            sql.addSql("   WFT_ACTION_LABEL NUMBER(10,0) not null,");
            sql.addSql("   WLB_SID NUMBER(10,0),");
            sql.addSql("   WFT_ACTION_READ NUMBER(10,0) not null,");
            sql.addSql("   WFT_ACTION_FOLDER NUMBER(10,0) not null,");
            sql.addSql("   WFT_ACTION_FORWARD NUMBER(10,0) not null,");
            sql.addSql("   WFT_ACTION_FWADDRESS varchar(768),");
            sql.addSql("   WFT_CONDITION NUMBER(10,0) not null,");
            sql.addSql("   primary key (WFT_SID)");
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
     * <p>Insert WML_FILTER Data Bindding JavaBean
     * @param bean WML_FILTER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlFilterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_FILTER(");
            sql.addSql("   WFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WFT_NAME,");
            sql.addSql("   WFT_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_TEMPFILE,");
            sql.addSql("   WFT_ACTION_LABEL,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WFT_ACTION_READ,");
            sql.addSql("   WFT_ACTION_FORWARD,");
            sql.addSql("   WFT_ACTION_FWADDRESS,");
            sql.addSql("   WFT_CONDITION,");
            sql.addSql("   WFT_ACTION_FOLDER");
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
            sql.addIntValue(bean.getWftSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getWftName());
            sql.addIntValue(bean.getWftType());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWftTempfile());
            sql.addIntValue(bean.getWftActionLabel());
            sql.addIntValue(bean.getWlbSid());
            sql.addIntValue(bean.getWftActionRead());
            sql.addIntValue(bean.getWftActionForward());
            sql.addStrValue(bean.getWftActionFwaddress());
            sql.addIntValue(bean.getWftCondition());
            sql.addIntValue(bean.getWftActionFolder());
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
     * <p>Update WML_FILTER Data Bindding JavaBean
     * @param bean WML_FILTER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlFilterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_FILTER");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   WFT_NAME=?,");
            sql.addSql("   WFT_TYPE=?,");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WFT_TEMPFILE=?,");
            sql.addSql("   WFT_ACTION_LABEL=?,");
            sql.addSql("   WLB_SID=?,");
            sql.addSql("   WFT_ACTION_READ=?,");
            sql.addSql("   WFT_ACTION_FOLDER=?,");
            sql.addSql("   WFT_ACTION_FORWARD=?,");
            sql.addSql("   WFT_ACTION_FWADDRESS=?,");
            sql.addSql("   WFT_CONDITION=?");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getWftName());
            sql.addIntValue(bean.getWftType());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWftTempfile());
            sql.addIntValue(bean.getWftActionLabel());
            sql.addIntValue(bean.getWlbSid());
            sql.addIntValue(bean.getWftActionRead());
            sql.addIntValue(bean.getWftActionFolder());
            sql.addIntValue(bean.getWftActionForward());
            sql.addStrValue(bean.getWftActionFwaddress());
            sql.addIntValue(bean.getWftCondition());
            //where
            sql.addIntValue(bean.getWftSid());

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
     * <p>Select WML_FILTER All Data
     * @return List in WML_FILTERModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlFilterModel> ret = new ArrayList<WmlFilterModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WFT_NAME,");
            sql.addSql("   WFT_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_TEMPFILE,");
            sql.addSql("   WFT_ACTION_LABEL,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WFT_ACTION_READ,");
            sql.addSql("   WFT_ACTION_FOLDER,");
            sql.addSql("   WFT_ACTION_FORWARD,");
            sql.addSql("   WFT_ACTION_FWADDRESS,");
            sql.addSql("   WFT_CONDITION");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterFromRs(rs));
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
     * <p>Select WML_FILTER
     * @param wftSid WFT_SID
     * @return WML_FILTERModel
     * @throws SQLException SQL実行例外
     */
    public WmlFilterModel select(int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlFilterModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WFT_NAME,");
            sql.addSql("   WFT_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_TEMPFILE,");
            sql.addSql("   WFT_ACTION_LABEL,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WFT_ACTION_READ,");
            sql.addSql("   WFT_ACTION_FOLDER,");
            sql.addSql("   WFT_ACTION_FORWARD,");
            sql.addSql("   WFT_ACTION_FWADDRESS,");
            sql.addSql("   WFT_CONDITION");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wftSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlFilterFromRs(rs);
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
     * <br>[機  能] 指定したユーザが作成したフィルターの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param wftType フィルター種別
     * @return フィルターの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getFilterList(int usrSid, int wftType) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFT_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   WFT_TYPE = ?");

            sql.addIntValue(usrSid);
            sql.addIntValue(wftType);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("WFT_SID"));
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
     * <p>Delete WML_FILTER
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
            sql.addSql("   WML_FILTER");
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
     * <p>Create WML_FILTER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterModel __getWmlFilterFromRs(ResultSet rs) throws SQLException {
        WmlFilterModel bean = new WmlFilterModel();
        bean.setWftSid(rs.getInt("WFT_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setWftName(rs.getString("WFT_NAME"));
        bean.setWftType(rs.getInt("WFT_TYPE"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWftTempfile(rs.getInt("WFT_TEMPFILE"));
        bean.setWftActionLabel(rs.getInt("WFT_ACTION_LABEL"));
        bean.setWlbSid(rs.getInt("WLB_SID"));
        bean.setWftActionRead(rs.getInt("WFT_ACTION_READ"));
        bean.setWftActionFolder(rs.getInt("WFT_ACTION_FOLDER"));
        bean.setWftActionForward(rs.getInt("WFT_ACTION_FORWARD"));
        bean.setWftActionFwaddress(rs.getString("WFT_ACTION_FWADDRESS"));
        bean.setWftCondition(rs.getInt("WFT_CONDITION"));
        return bean;
    }

    /**
     * <br>[機  能] フィルタ名を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param filterlSid フィルタSID
     * @return フィルタ名
     * @throws SQLException SQL実行例外
     */
    public String getWmlFilterName(int filterlSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER");
            sql.addSql(" where");
            sql.addSql("   WFT_SID = ?");

            sql.addIntValue(filterlSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("WFT_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}
