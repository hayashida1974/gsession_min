package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlFwlimitModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_FWLIMIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFwlimitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlFwlimitDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlFwlimitDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlFwlimitDao(Connection con) {
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
            sql.addSql("drop table WML_FWLIMIT");

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
            sql.addSql(" create table WML_FWLIMIT (");
            sql.addSql("   WFL_TEXT varchar(50) not null,");
            sql.addSql("   WFL_NO NUMBER(10,0) not null");
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
     * <p>Insert WML_FWLIMIT Data Bindding JavaBean
     * @param bean WML_FWLIMIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlFwlimitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_FWLIMIT(");
            sql.addSql("   WFL_TEXT,");
            sql.addSql("   WFL_NO");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWflText());
            sql.addIntValue(bean.getWflNo());
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
     * <p>Update WML_FWLIMIT Data Bindding JavaBean
     * @param bean WML_FWLIMIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlFwlimitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_FWLIMIT");
            sql.addSql(" set ");
            sql.addSql("   WFL_TEXT=?,");
            sql.addSql("   WFL_NO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWflText());
            sql.addIntValue(bean.getWflNo());

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
     * <p>Select WML_FWLIMIT All Data
     * @return List in WML_FWLIMITModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFwlimitModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlFwlimitModel> ret = new ArrayList<WmlFwlimitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFL_TEXT,");
            sql.addSql("   WFL_NO");
            sql.addSql(" from ");
            sql.addSql("   WML_FWLIMIT");
            sql.addSql(" order by ");
            sql.addSql("   WFL_NO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFwlimitFromRs(rs));
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
     * <br>[機  能] 転送先制限文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 転送先制限文字列
     * @throws SQLException SQL実行例外
     */
    public List<String> getFwTextList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> fwTextList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WFL_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_FWLIMIT");
            sql.addSql(" order by ");
            sql.addSql("   WFL_NO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fwTextList.add(rs.getString("WFL_TEXT"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fwTextList;
    }

    /**
     * <br>[機  能] WEBメール_転送制限の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   WML_FWLIMIT");

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
     * <p>Create WML_FWLIMIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlFwlimitModel
     * @throws SQLException SQL実行例外
     */
    private WmlFwlimitModel __getWmlFwlimitFromRs(ResultSet rs) throws SQLException {
        WmlFwlimitModel bean = new WmlFwlimitModel();
        bean.setWflText(rs.getString("WFL_TEXT"));
        bean.setWflNo(rs.getInt("WFL_NO"));
        return bean;
    }
}
