package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_LABEL_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrLabelCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrLabelCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrLabelCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrLabelCategoryDao(Connection con) {
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
            sql.addSql("drop table ADR_LABEL_CATEGORY");

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
            sql.addSql(" create table ADR_LABEL_CATEGORY (");
            sql.addSql("   ALC_SID   integer     not null,");
            sql.addSql("   ALC_NAME  varchar(20) not null,");
            sql.addSql("   ALC_BIKO  varchar(300),");
            sql.addSql("   ALC_SORT  integer     not null,");
            sql.addSql("   ALC_AUID  integer     not null,");
            sql.addSql("   ALC_ADATE timestamp   not null,");
            sql.addSql("   ALC_EUID  integer     not null,");
            sql.addSql("   ALC_EDATE timestamp   not null,");
            sql.addSql("   primary key (ALB_SID)");
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
     * <p>Insert ADR_LABEL_CATEGORY Data Bindding JavaBean
     * @param bean ADR_LABEL_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrLabelCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_LABEL_CATEGORY(");
            sql.addSql("   ALC_SID,");
            sql.addSql("   ALC_NAME,");
            sql.addSql("   ALC_BIKO,");
            sql.addSql("   ALC_SORT,");
            sql.addSql("   ALC_AUID,");
            sql.addSql("   ALC_ADATE,");
            sql.addSql("   ALC_EUID,");
            sql.addSql("   ALC_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAlcSid());
            sql.addStrValue(bean.getAlcName());
            sql.addStrValue(bean.getAlcBiko());
            sql.addIntValue(bean.getAlcSort());
            sql.addIntValue(bean.getAlcAuid());
            sql.addDateValue(bean.getAlcAdate());
            sql.addIntValue(bean.getAlcEuid());
            sql.addDateValue(bean.getAlcEdate());
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
     * <p>Update ADR_LABEL_CATEGORY Data Bindding JavaBean
     * @param bean ADR_LABEL_CATEGORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrLabelCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   ALC_NAME=?,");
            sql.addSql("   ALC_BIKO=?,");
            sql.addSql("   ALC_EUID=?,");
            sql.addSql("   ALC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAlcName());
            sql.addStrValue(bean.getAlcBiko());
            sql.addIntValue(bean.getAlcEuid());
            sql.addDateValue(bean.getAlcEdate());
            //where
            sql.addIntValue(bean.getAlcSid());

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
     * <br>[機  能] ソート順のアップデート
     * <br>[解  説]
     * <br>[備  考]
     * @param sid カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void sortUpdate(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" set");
            sql.addSql("   ALC_SORT=");
            sql.addSql("     (select ALC_SORT ");
            sql.addSql("      from");
            sql.addSql("      ADR_LABEL_CATEGORY ");
            sql.addSql("     where ");
            sql.addSql("     ALC_SID=?)-1");
            sql.addSql(" where");
            sql.addSql("   ALC_SID=?");
            sql.addIntValue(sid);
            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
    
    /**
     * <br>[機  能] ソート順のアップデート
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ADR_LABEL_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void updateOnlySort(AdrLabelCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" set");
            sql.addSql("   ALC_SORT = ?");
            sql.addSql(" where");
            sql.addSql("   ALC_SID=?");
            sql.addIntValue(bean.getAlcSort());
            sql.addIntValue(bean.getAlcSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select ADR_LABEL_CATEGORY All Data
     * @return List in ADR_LABEL_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrLabelCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrLabelCategoryModel> ret = new ArrayList<AdrLabelCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALC_SID,");
            sql.addSql("   ALC_NAME,");
            sql.addSql("   ALC_BIKO,");
            sql.addSql("   ALC_SORT,");
            sql.addSql("   ALC_AUID,");
            sql.addSql("   ALC_ADATE,");
            sql.addSql("   ALC_EUID,");
            sql.addSql("   ALC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" order by ");
            sql.addSql("   ALC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrLabelFromRs(rs));
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
     * <p>Select ADR_LABEL_CATEGORY All Data
     * @return List in ADR_LABEL_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL_CATEGORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>指定されたカテゴリSID内のラベルリストを作成します。
     * @param albSid カテゴリSID
     * @return List in ADR_LABEL_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrLabelCategoryModel> getLabelLInCategory(int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrLabelCategoryModel> ret = new ArrayList<AdrLabelCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALC_SID,");
            sql.addSql("   ALC_NAME,");
            sql.addSql("   ALC_BIKO,");
            sql.addSql("   ALC_SORT,");
            sql.addSql("   ALC_AUID,");
            sql.addSql("   ALC_ADATE,");
            sql.addSql("   ALC_EUID,");
            sql.addSql("   ALC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID");
            sql.addSql(" in (");
            sql.addSql("     select ");
            sql.addSql("       ALB_SID");
            sql.addSql("     from ");
            sql.addSql("       ADR_LABEL");
            sql.addSql("     where ");
            sql.addSql("       ALC_SID=?");
            sql.addSql(" )");
            sql.addSql(" order by ");
            sql.addSql("   ALB_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(albSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getAdrLabelFromRs(rs));
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
     * <p>Select ADR_LABEL_CATEGORY
     * @param alcSid ALB_SID
     * @return ADR_LABEL_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public AdrLabelCategoryModel select(int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrLabelCategoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ALC_SID,");
            sql.addSql("   ALC_NAME,");
            sql.addSql("   ALC_BIKO,");
            sql.addSql("   ALC_SORT,");
            sql.addSql("   ALC_AUID,");
            sql.addSql("   ALC_ADATE,");
            sql.addSql("   ALC_EUID,");
            sql.addSql("   ALC_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(alcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrLabelFromRs(rs);
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
     * <br>[機  能] ソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return int ソート順の最大値
     * @throws SQLException SQL実行例外
     */
    public int getSortMax() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(ALC_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL_CATEGORY");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
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
     * <p>Delete ADR_LABEL_CATEGORY
     * @param alcSid ALB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> list = new ArrayList<Integer>();


        try {
            list = sortNumArrange(alcSid);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(alcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

            for (int i = 0; i < list.size(); i++) {
                sortUpdate(list.get(i));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 削除カテゴリソート順以下のカテゴリSID取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sid カテゴリSID
     * @return カテゴリSIDのList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> sortNumArrange(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            AdrLabelCategoryModel model = select(sid);
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ALC_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL_CATEGORY");
            sql.addSql(" where");
            sql.addSql("   ALC_SORT > ?");
            sql.addIntValue(model.getAlcSort());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("ALC_SID"));
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
     * <p>Create ADR_LABEL_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrLabelCategoryModel
     * @throws SQLException SQL実行例外
     */
    private AdrLabelCategoryModel __getAdrLabelFromRs(ResultSet rs) throws SQLException {
        AdrLabelCategoryModel bean = new AdrLabelCategoryModel();
        bean.setAlcSid(rs.getInt("ALC_SID"));
        bean.setAlcName(rs.getString("ALC_NAME"));
        bean.setAlcBiko(rs.getString("ALC_BIKO"));
        bean.setAlcSort(rs.getInt("ALC_SORT"));
        bean.setAlcAuid(rs.getInt("ALC_AUID"));
        bean.setAlcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ALC_ADATE")));
        bean.setAlcEuid(rs.getInt("ALC_EUID"));
        bean.setAlcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ALC_EDATE")));
        return bean;
    }
}
