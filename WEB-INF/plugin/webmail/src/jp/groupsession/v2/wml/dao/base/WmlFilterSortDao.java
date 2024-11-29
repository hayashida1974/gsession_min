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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlFilterSortModel;

/**
 * <p>WML_FILTER_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlFilterSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlFilterSortDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlFilterSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlFilterSortDao(Connection con) {
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
            sql.addSql("drop table WML_FILTER_SORT");

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
            sql.addSql(" create table WML_FILTER_SORT (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WFT_SID NUMBER(10,0) not null,");
            sql.addSql("   WFS_SORT Date not null,");
            sql.addSql("   primary key (WAC_SID,WFT_SID)");
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
     * <p>Insert WML_FILTER_SORT Data Bindding JavaBean
     * @param bean WML_FILTER_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlFilterSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_FILTER_SORT(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWftSid());
            sql.addLongValue(bean.getWfsSort());
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
     * <p>Update WML_FILTER_SORT Data Bindding JavaBean
     * @param bean WML_FILTER_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlFilterSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_FILTER_SORT");
            sql.addSql(" set ");
            sql.addSql("   WFS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWfsSort());
            //where
            sql.addIntValue(bean.getWacSid());
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
     * <p>Select WML_FILTER_SORT All Data
     * @return List in WML_FILTER_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlFilterSortModel> ret = new ArrayList<WmlFilterSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFS_SORT");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterSortFromRs(rs));
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
     * <p>Select WML_FILTER_SORT
     * @param wacSid WAC_SID
     * @param wftSid WFT_SID
     * @return WML_FILTER_SORTModel
     * @throws SQLException SQL実行例外
     */
    public WmlFilterSortModel select(int wacSid, int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlFilterSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFS_SORT");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wftSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlFilterSortFromRs(rs);
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
     * <p>Select WML_FILTER_SORT
     * @param wacSid WAC_SID
     * @return WML_FILTER_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlFilterSortModel> selectList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlFilterSortModel> ret = new ArrayList<WmlFilterSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFS_SORT");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   WFS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlFilterSortFromRs(rs));
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
     * <p>Delete WML_FILTER_SORT
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
            sql.addSql("   WML_FILTER_SORT");
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
     * <p>Delete WML_FILTER_SORT
     * @param wacSid WAC_SID
     * @param wftSid WFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int wftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   WFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
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
     * <br>[機  能] フィルターの並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return フィルター最大値
     * @throws SQLException SQL実行時例外
     */
    public int maxSortNumber()
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql(" MAX(WFS_SORT)");
            sql.addSql(" as MAXCNT");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAXCNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }

    /**
     * <br>[機  能] フィルターの適用順の更新用の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fiSid フィルタSID
     * @throws SQLException SQL実行時例外
     */
    public void updateFilterSort(int fiSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    WML_ACCOUNT.WAC_SID, ");
            sql.addSql("    ?, ");
            sql.addSql("    max(COALESCE(WML_FILTER_SORT.WFS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    WML_ACCOUNT");
            sql.addSql("    left join ");
            sql.addSql("      WML_ACCOUNT_USER ");
            sql.addSql("    on ");
            sql.addSql("      WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("    left join ");
            sql.addSql("      WML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      WML_ACCOUNT.WAC_SID = WML_FILTER_SORT.WAC_SID ");
            sql.addSql("  where ");
            sql.addSql("    WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql("  and ");
            sql.addSql("    WML_ACCOUNT.WAC_SID not in ( ");
            sql.addSql("      select WAC_SID from WML_FILTER_SORT ");
            sql.addSql("      where WFT_SID = ? )");
            sql.addSql("  group by");
            sql.addSql("    WML_ACCOUNT.WAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fiSid);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            sql.addIntValue(fiSid);
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
     * <br>[機  能] （フィルタ登録時）フィルターの適用順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fiSaiSid フィルタSID
     * @throws SQLException SQL実行時例外
     */
    public void insertFilterSort(int fiSaiSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    WML_ACCOUNT.WAC_SID, ");
            sql.addSql("    ?, ");
            sql.addSql("    max(COALESCE(WML_FILTER_SORT.WFS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    WML_ACCOUNT");
            sql.addSql("    left join ");
            sql.addSql("      WML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      WML_ACCOUNT.WAC_SID = WML_FILTER_SORT.WAC_SID ");
            sql.addSql("  where");
            sql.addSql("    WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql("  group by ");
            sql.addSql("    WML_ACCOUNT.WAC_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(fiSaiSid);
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
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
     * <br>[機  能] （アカウント登録時）フィルターの適用順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSaiSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void setAcuntInsFilterSort(int wacSaiSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    WML_FILTER.WFT_SID, ");
            sql.addSql("    max(COALESCE(WML_FILTER_SORT.WFS_SORT, 0))");
            sql.addSql("  from ");
            sql.addSql("    WML_FILTER ");
            sql.addSql("    left join ");
            sql.addSql("      WML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      WML_FILTER.WFT_SID = WML_FILTER_SORT.WFT_SID ");
            sql.addSql("    where ");
            sql.addSql("      WML_FILTER.WFT_TYPE = 1 ");
            sql.addSql("  group by ");
            sql.addSql("    ?, ");
            sql.addSql("    WML_FILTER.WFT_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(wacSaiSid);
            sql.addIntValue(wacSaiSid);

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
     * <p>Create WML_FILTER_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlFilterSortModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterSortModel __getWmlFilterSortFromRs(ResultSet rs) throws SQLException {
        WmlFilterSortModel bean = new WmlFilterSortModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWftSid(rs.getInt("WFT_SID"));
        bean.setWfsSort(rs.getInt("WFS_SORT"));
        return bean;
    }
}
