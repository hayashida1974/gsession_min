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
import jp.groupsession.v2.wml.model.base.WmlDestlistAddressModel;

/**
 * <p>WML_DESTLIST_ADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlDestlistAddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDestlistAddressDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDestlistAddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDestlistAddressDao(Connection con) {
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
            sql.addSql("drop table WML_DESTLIST_ADDRESS");

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
            sql.addSql(" create table WML_DESTLIST_ADDRESS (");
            sql.addSql("   WDL_SID NUMBER(10,0) not null,");
            sql.addSql("   WDA_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WDA_SID NUMBER(10,0) not null,");
            sql.addSql("   WDA_ADRNO NUMBER(10,0) not null,");
            sql.addSql("   primary key (WDL_SID,WDA_TYPE,WDA_SID,WDA_ADRNO)");
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
     * <p>Insert WML_DESTLIST_ADDRESS Data Bindding JavaBean
     * @param bean WML_DESTLIST_ADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDestlistAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DESTLIST_ADDRESS(");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDA_TYPE,");
            sql.addSql("   WDA_SID,");
            sql.addSql("   WDA_ADRNO");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addIntValue(bean.getWdlSid());
            sql.addIntValue(bean.getWdaType());
            sql.addIntValue(bean.getWdaSid());
            sql.addIntValue(bean.getWdaAdrno());
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_DESTLIST_ADDRESS Data Bindding JavaBean
     * @param bean WML_DESTLIST_ADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDestlistAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DESTLIST_ADDRESS");
            sql.addSql(" set ");
            sql.addSql("   WDL_SID=?,");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDA_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   WDA_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDA_ADRNO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWdlSid());
            //where
            sql.addIntValue(bean.getWdlSid());
            sql.addIntValue(bean.getWdaType());
            sql.addIntValue(bean.getWdaSid());
            sql.addIntValue(bean.getWdaAdrno());

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
     * <p>Select WML_DESTLIST_ADDRESS All Data
     * @return List in WML_DESTLIST_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDestlistAddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDestlistAddressModel> ret = new ArrayList<WmlDestlistAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDA_TYPE,");
            sql.addSql("   WDA_SID,");
            sql.addSql("   WDA_ADRNO");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDestlistAddressFromRs(rs));
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
     * <p>Select WML_DESTLIST_ADDRESS
     * @param wdlSid WDL_SID
     * @param wdaType WDA_TYPE
     * @param wdaSid WDA_SID
     * @param wdaAdrno WDA_ADRNO
     * @return WML_DESTLIST_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public WmlDestlistAddressModel select(int wdlSid, int wdaType, int wdaSid, int wdaAdrno)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDestlistAddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDA_TYPE,");
            sql.addSql("   WDA_SID,");
            sql.addSql("   WDA_ADRNO");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDA_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   WDA_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDA_ADRNO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);
            sql.addIntValue(wdaType);
            sql.addIntValue(wdaSid);
            sql.addIntValue(wdaAdrno);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDestlistAddressFromRs(rs);
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
     * <br>[機  能] 指定した送信先リストの送信先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdlSid 送信先リストSID
     * @param wdaType 種別
     * @return 送信先
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDestlistAddressModel> getDestlistAddress(int wdlSid, int wdaType)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDestlistAddressModel> ret = new ArrayList<WmlDestlistAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_SID,");
            sql.addSql("   WDA_TYPE,");
            sql.addSql("   WDA_SID,");
            sql.addSql("   WDA_ADRNO");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID = ?");
            sql.addIntValue(wdlSid);
            if (wdaType >= 0) {
                sql.addSql(" and ");
                sql.addSql("   WDA_TYPE = ?");
                sql.addIntValue(wdaType);
            }
            sql.addSql(" order by ");
            sql.addSql("   WDA_SID,");
            sql.addSql("   WDA_ADRNO");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDestlistAddressFromRs(rs));
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
     * <p>Delete WML_DESTLIST_ADDRESS
     * @param wdlSid WDL_SID
     * @param wdaType WDA_TYPE
     * @param wdaSid WDA_SID
     * @param wdaAdrno WDA_ADRNO
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wdlSid, int wdaType, int wdaSid, int wdaAdrno) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WDA_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   WDA_SID=?");
            sql.addSql(" and");
            sql.addSql("   WDA_ADRNO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);
            sql.addIntValue(wdaType);
            sql.addIntValue(wdaSid);
            sql.addIntValue(wdaAdrno);

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
     * <p>Delete WML_DESTLIST_ADDRESS
     * @param wdlSid WDL_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wdlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DESTLIST_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wdlSid);

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
     * <p>Create WML_DESTLIST_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDestlistAddressModel
     * @throws SQLException SQL実行例外
     */
    private WmlDestlistAddressModel __getWmlDestlistAddressFromRs(ResultSet rs)
    throws SQLException {
        WmlDestlistAddressModel bean = new WmlDestlistAddressModel();
        bean.setWdlSid(rs.getInt("WDL_SID"));
        bean.setWdaType(rs.getInt("WDA_TYPE"));
        bean.setWdaSid(rs.getInt("WDA_SID"));
        bean.setWdaAdrno(rs.getInt("WDA_ADRNO"));
        return bean;
    }
}
