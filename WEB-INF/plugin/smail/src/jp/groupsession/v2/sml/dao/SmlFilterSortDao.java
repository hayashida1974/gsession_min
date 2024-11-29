package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlFilterSortModel;

/**
 * <p>SML_FILTER_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlFilterSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlFilterSortDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlFilterSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlFilterSortDao(Connection con) {
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
            sql.addSql("drop table SML_FILTER_SORT");

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
            sql.addSql(" create table SML_FILTER_SORT (");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   SFT_SID NUMBER(10,0) not null,");
            sql.addSql("   SFS_SORT Date not null,");
            sql.addSql("   primary key (SAC_SID,SFT_SID)");
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
     * <p>Insert SML_FILTER_SORT Data Bindding JavaBean
     * @param bean SML_FILTER_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlFilterSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_FILTER_SORT(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSftSid());
            sql.addLongValue(bean.getSfsSort());
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
     * <p>Update SML_FILTER_SORT Data Bindding JavaBean
     * @param bean SML_FILTER_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlFilterSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" set ");
            sql.addSql("   SFS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getSfsSort());
            //where
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSftSid());

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
     * <p>並び順を更新する
     * @param sftSod フィルターSID
     * @param sfsSort 並び順
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateOrder(int sftSod, int sfsSort) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" set ");
            sql.addSql("   SFS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sfsSort);
            sql.addIntValue(sftSod);

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
     * <p>Select SML_FILTER_SORT All Data
     * @return List in SML_FILTER_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlFilterSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlFilterSortModel> ret = new ArrayList<SmlFilterSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFS_SORT");
            sql.addSql(" from ");
            sql.addSql("   SML_FILTER_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlFilterSortFromRs(rs));
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
     * <p>Select SML_FILTER_SORT
     * @param sacSid SAC_SID
     * @param sftSid SFT_SID
     * @return SML_FILTER_SORTModel
     * @throws SQLException SQL実行例外
     */
    public SmlFilterSortModel select(int sacSid, int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlFilterSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_SID,");
            sql.addSql("   SFS_SORT");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(sftSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlFilterSortFromRs(rs);
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
     * <p>Delete SML_FILTER_SORT
     * @param sftSid SFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);

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
     * <p>Delete SML_FILTER_SORT
     * @param sacSid SAC_SID
     * @param sftSid SFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sacSid, int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(sftSid);

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
            sql.addSql(" MAX(SFS_SORT)");
            sql.addSql(" as MAXCNT");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_SORT");

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
            sql.addSql(" insert into SML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    SML_ACCOUNT.SAC_SID, ");
            sql.addSql("    ?, ");
            sql.addSql("    max(COALESCE(SML_FILTER_SORT.SFS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    SML_ACCOUNT");
            sql.addSql("    left join ");
            sql.addSql("      SML_ACCOUNT_USER ");
            sql.addSql("    on ");
            sql.addSql("      SML_ACCOUNT.SAC_SID = SML_ACCOUNT_USER.SAC_SID ");
            sql.addSql("    left join ");
            sql.addSql("      SML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      SML_ACCOUNT.SAC_SID = SML_FILTER_SORT.SAC_SID ");
            sql.addSql("  where ");
            sql.addSql("    SML_ACCOUNT.SAC_JKBN = ?");
            sql.addSql("  and ");
            sql.addSql("    SML_ACCOUNT.SAC_SID not in ( ");
            sql.addSql("      select SAC_SID from SML_FILTER_SORT ");
            sql.addSql("      where SFT_SID = ? )");
            sql.addSql("  group by");
            sql.addSql("    SML_ACCOUNT.SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fiSid);
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
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
            sql.addSql(" insert into SML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    SML_ACCOUNT.SAC_SID, ");
            sql.addSql("    ?, ");
            sql.addSql("    max(COALESCE(SML_FILTER_SORT.SFS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    SML_ACCOUNT");
            sql.addSql("    left join ");
            sql.addSql("      SML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      SML_ACCOUNT.SAC_SID = SML_FILTER_SORT.SAC_SID ");
            sql.addSql("  where");
            sql.addSql("    SML_ACCOUNT.SAC_JKBN = ?");
            sql.addSql("  group by ");
            sql.addSql("    SML_ACCOUNT.SAC_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(fiSaiSid);
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
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
     * @param sacSaiSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void setAcuntInsFilterSort(int sacSaiSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into SML_FILTER_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    SML_FILTER.SFT_SID, ");
            sql.addSql("    max(COALESCE(SML_FILTER_SORT.SFS_SORT, 0))");
            sql.addSql("  from ");
            sql.addSql("    SML_FILTER ");
            sql.addSql("    left join ");
            sql.addSql("      SML_FILTER_SORT ");
            sql.addSql("    on ");
            sql.addSql("      SML_FILTER.SFT_SID = SML_FILTER_SORT.SFT_SID ");
            sql.addSql("    where ");
            sql.addSql("      SML_FILTER.SFT_TYPE = 1 ");
            sql.addSql("  group by ");
            sql.addSql("    ?, ");
            sql.addSql("    SML_FILTER.SFT_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(sacSaiSid);
            sql.addIntValue(sacSaiSid);

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
     * <p>Create SML_FILTER_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlFilterSortModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterSortModel __getSmlFilterSortFromRs(ResultSet rs) throws SQLException {
        SmlFilterSortModel bean = new SmlFilterSortModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSftSid(rs.getInt("SFT_SID"));
        bean.setSfsSort(rs.getInt("SFS_SORT"));
        return bean;
    }
}
