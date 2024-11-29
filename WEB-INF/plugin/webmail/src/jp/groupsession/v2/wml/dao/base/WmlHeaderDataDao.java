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
import jp.groupsession.v2.wml.model.base.WmlHeaderDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_HEADER_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlHeaderDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlHeaderDataDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlHeaderDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlHeaderDataDao(Connection con) {
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
            sql.addSql("drop table WML_HEADER_DATA");

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
            sql.addSql(" create table WML_HEADER_DATA (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WMH_NUM NUMBER(10,0) not null,");
            sql.addSql("   WMH_TYPE varchar(200) not null,");
            sql.addSql("   WMH_CONTENT varchar(500) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WMD_MAILNUM,WMH_NUM)");
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
     * <p>Insert WML_HEADER_DATA Data Bindding JavaBean
     * @param bean WML_HEADER_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlHeaderDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_HEADER_DATA(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
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
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWmhNum());
            sql.addStrValue(bean.getWmhType());
            sql.addStrValue(bean.getWmhContent());
            sql.addIntValue(bean.getWacSid());
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
     * <p>Insert WML_HEADER_DATA Data Bindding JavaBean
     * @param beanList List in WML_HEADER_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlHeaderDataModel> beanList) throws SQLException {

        if (beanList == null || beanList.isEmpty()) {
            return;
        }

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_HEADER_DATA(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");

            boolean first = true;
            for (WmlHeaderDataModel bean : beanList) {

                if (!first) {
                    sql.addSql(" ,");
                }

                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addLongValue(bean.getWmdMailnum());
                sql.addIntValue(bean.getWmhNum());
                sql.addStrValue(bean.getWmhType());
                sql.addStrValue(bean.getWmhContent());
                sql.addIntValue(bean.getWacSid());

                first = false;
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <p>Insert WML_HEADER_DATA Data Bindding JavaBean
     *        リストの件数が多いver
     * @param beanList List in WML_HEADER_DATA DataList
     * @throws SQLException SQL実行例外
     */
    public void insert2(List<WmlHeaderDataModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_HEADER_DATA(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
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

            for (WmlHeaderDataModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addIntValue(bean.getWmhNum());
                sql.addStrValue(bean.getWmhType());
                sql.addStrValue(bean.getWmhContent());
                sql.addIntValue(bean.getWacSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_HEADER_DATA Data Bindding JavaBean
     * @param bean WML_HEADER_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlHeaderDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" set ");
            sql.addSql("   WMH_TYPE=?,");
            sql.addSql("   WMH_CONTENT=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WMH_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWmhType());
            sql.addStrValue(bean.getWmhContent());
            //where
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWmhNum());

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
     * <p>全データ件数を取得する
     * @return データ件数
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_HEADER_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>全データ件数を取得する
     * @return データ件数
     * @throws SQLException SQL実行例外
     */
    public long maxMailNum() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WMD_MAILNUM) as MAX");
            sql.addSql(" from ");
            sql.addSql("   WML_HEADER_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("MAX");
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
     * <p>指定した範囲のメールヘッダー情報を取得する
     * @param from 開始
     * @param to 終了
     * @return List in WML_HEADER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlHeaderDataModel> selectPart(long from, long to) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlHeaderDataModel> ret = new ArrayList<WmlHeaderDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where ");

            sql.addSql("   WMD_MAILNUM >= ?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM <= ?");

            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc,");
            sql.addSql("   WMH_NUM asc");

            sql.addLongValue(from);
            sql.addLongValue(to);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlHeaderDataFromRs(rs));
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
     * <p>Select WML_HEADER_DATA All Data
     * @param wmdMailnum WMD_MAILNUM
     * @return List in WML_HEADER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlHeaderDataModel> select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlHeaderDataModel> ret = new ArrayList<WmlHeaderDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" order by");
            sql.addSql("   WMH_NUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlHeaderDataFromRs(rs));
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
     * <p>Select WML_HEADER_DATA
     * @param wmdMailnum WMD_MAILNUM
     * @param wmhNum WMH_NUM
     * @return WML_HEADER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public WmlHeaderDataModel select(long wmdMailnum, int wmhNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlHeaderDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMH_NUM,");
            sql.addSql("   WMH_TYPE,");
            sql.addSql("   WMH_CONTENT,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WMH_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wmhNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlHeaderDataFromRs(rs);
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
     * <p>Delete WML_HEADER_DATA
     * @param wmdMailnum WMD_MAILNUM
     * @param wmhNum WMH_NUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, int wmhNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WMH_NUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wmhNum);

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
     * <p>Delete WML_HEADER_DATA
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_HEADER_DATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

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
     * <p>Create WML_HEADER_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlHeaderDataModel
     * @throws SQLException SQL実行例外
     */
    private WmlHeaderDataModel __getWmlHeaderDataFromRs(ResultSet rs) throws SQLException {
        WmlHeaderDataModel bean = new WmlHeaderDataModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWmhNum(rs.getInt("WMH_NUM"));
        bean.setWmhType(rs.getString("WMH_TYPE"));
        bean.setWmhContent(rs.getString("WMH_CONTENT"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
