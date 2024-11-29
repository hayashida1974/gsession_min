package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;

/**
 * <p>CMN_LABEL_USR_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLabelUsrCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLabelUsrCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLabelUsrCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLabelUsrCategoryDao(Connection con) {
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
            sql.addSql("drop table CMN_LABEL_USR_CATEGORY");

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
     * <p>カテゴリを削除します。
     * @param sid 削除対象カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void deleteSort(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> list = new ArrayList<Integer>();

        try {
            list = sortNumArrange(sid);
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   LUC_SID=?");

            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            for (int i = 0; i < list.size(); i++) {
                sortUpdate(list.get(i));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>カテゴリを削除します。
     * @param sid 削除対象カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void delete(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> list = new ArrayList<Integer>();

        try {
            list = sortNumArrange(sid);
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   LUC_SID=?");

            sql.addIntValue(sid);

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
     * <p>カテゴリをラベルごと削除します。
     * @param sid 削除対象カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void deleteCatAndLab(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            deleteSort(sid);

            SqlBuffer sql2 = new SqlBuffer();
            sql2.addSql(" delete from");
            sql2.addSql("   CMN_LABEL_USR");
            sql2.addSql(" where ");
            sql2.addSql("   LUC_SID=?");

            sql2.addIntValue(sid);


            log__.info(sql2.toLogString());

            pstmt2 = con.prepareStatement(sql2.toSqlString());
            sql2.setParameter(pstmt2);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeStatement(pstmt2);
        }
    }

    /**
     * <p>Insert CMN_LABEL_USR_CATEGORY Data Bindding JavaBean
     * @param bean CMN_LABEL_USR_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLabelUsrCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LABEL_USR_CATEGORY(");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LUC_NAME,");
            sql.addSql("   LUC_BIKO,");
            sql.addSql("   LUC_AUID,");
            sql.addSql("   LUC_ADATE,");
            sql.addSql("   LUC_EUID,");
            sql.addSql("   LUC_EDATE,");
            sql.addSql("   LUC_SORT");
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
            sql.addIntValue(bean.getLucSid());
            sql.addStrValue(bean.getLucName());
            sql.addStrValue(bean.getLucBiko());
            sql.addIntValue(bean.getLucAuid());
            sql.addDateValue(bean.getLucAdate());
            sql.addIntValue(bean.getLucEuid());
            sql.addDateValue(bean.getLucEdate());
            sql.addIntValue(bean.getLucSort());
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
     * <p>カテゴリを更新します。
     * @param bean USR_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void update(CmnLabelUsrCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   LUC_NAME=?,");
            sql.addSql("   LUC_BIKO=?,");
            sql.addSql("   LUC_EUID=?,");
            sql.addSql("   LUC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   LUC_SID=?");

            sql.addStrValue(bean.getLucName());
            sql.addStrValue(bean.getLucBiko());
            sql.addIntValue(bean.getLucEuid());
            sql.addDateValue(bean.getLucEdate());
            sql.addIntValue(bean.getLucSid());

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
     * <p>count CMN_LABEL_USR_CATEGORY
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");

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
     * <p>Select CMN_LABEL_USR_CATEGORY
     * @return List in USR_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLabelUsrCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CmnLabelUsrCategoryModel> ret = new ArrayList<CmnLabelUsrCategoryModel>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LUC_NAME,");
            sql.addSql("   LUC_BIKO,");
            sql.addSql("   LUC_SORT,");
            sql.addSql("   LUC_AUID,");
            sql.addSql("   LUC_ADATE,");
            sql.addSql("   LUC_EUID,");
            sql.addSql("   LUC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" order by");
            sql.addSql("   LUC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLabelUsrCategoryFormRs(rs));
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
     * <p>Select CMN_LABEL_USR_CATEGORY
     * @return List in UsrLabCategoryModel
     * @param sid カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public CmnLabelUsrCategoryModel select(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CmnLabelUsrCategoryModel ret = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LUC_NAME,");
            sql.addSql("   LUC_BIKO,");
            sql.addSql("   LUC_SORT,");
            sql.addSql("   LUC_AUID,");
            sql.addSql("   LUC_ADATE,");
            sql.addSql("   LUC_EUID,");
            sql.addSql("   LUC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" where");
            sql.addSql("   LUC_SID = ?");

            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnLabelUsrCategoryFormRs(rs);
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
     * <p>Create CMN_LABEL_USR_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnLabelUsrCategoryModel
     * @throws SQLException SQL実行例外
     */
    private CmnLabelUsrCategoryModel __getCmnLabelUsrCategoryFormRs(ResultSet rs)
                                                            throws SQLException {
        CmnLabelUsrCategoryModel bean = new CmnLabelUsrCategoryModel();
        bean.setLucSid(rs.getInt("LUC_SID"));
        bean.setLucName(rs.getString("LUC_NAME"));
        bean.setLucBiko(rs.getString("LUC_BIKO"));
        bean.setLucSort(rs.getInt("LUC_SORT"));
        bean.setLucAuid(rs.getInt("LUC_AUID"));
        bean.setLucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LUC_ADATE")));
        bean.setLucEuid(rs.getInt("LUC_EUID"));
        bean.setLucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LUC_EDATE")));
        return bean;
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
            sql.addSql("   max(LUC_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");

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
            CmnLabelUsrCategoryModel model = select(sid);
            if (model == null) {
                return ret;
            }

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LUC_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" where");
            sql.addSql("   LUC_SORT > ?");
            sql.addIntValue(model.getLucSort());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("LUC_SID"));
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
            sql.addSql("   CMN_LABEL_USR_CATEGORY");
            sql.addSql(" set");
            sql.addSql("   LUC_SORT=");
            sql.addSql("     (select LUC_SORT ");
            sql.addSql("      from");
            sql.addSql("      CMN_LABEL_USR_CATEGORY ");
            sql.addSql("     where ");
            sql.addSql("     LUC_SID=?)-1");
            sql.addSql(" where");
            sql.addSql("   LUC_SID=?");
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

}