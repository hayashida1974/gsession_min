package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;

/**
 * <p>CMN_LABEL_USR Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnLabelUsrDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnLabelUsrDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnLabelUsrDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnLabelUsrDao(Connection con) {
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
            sql.addSql("drop table CMN_LABEL_USR");

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
     * <p>Insert CMN_LABEL_USR Data Bindding JavaBean
     * @param bean CMN_LABEL_USR Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnLabelUsrModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_LABEL_USR(");
            sql.addSql("   LAB_SID,");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LAB_NAME,");
            sql.addSql("   LAB_BIKO,");
            sql.addSql("   LAB_AUID,");
            sql.addSql("   LAB_ADATE,");
            sql.addSql("   LAB_EUID,");
            sql.addSql("   LAB_EDATE,");
            sql.addSql("   LAB_SORT");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getLabSid());
            sql.addIntValue(bean.getLucSid());
            sql.addStrValue(bean.getLabName());
            sql.addStrValue(bean.getLabBiko());
            sql.addIntValue(bean.getLabAuid());
            sql.addDateValue(bean.getLabAdate());
            sql.addIntValue(bean.getLabEuid());
            sql.addDateValue(bean.getLabEdate());
            sql.addIntValue(bean.getLabSort());
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
     * <br>[機  能] 1件のラベル情報を更新します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean UsrLabelModel
     * @throws SQLException SQL実行例外
     */
    public void update(CmnLabelUsrModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" set ");
            sql.addSql("   LUC_SID=?,");
            sql.addSql("   LAB_NAME=?,");
            sql.addSql("   LAB_BIKO=?,");
            sql.addSql("   LAB_EUID=?,");
            sql.addSql("   LAB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   LAB_SID=?");

            sql.addIntValue(bean.getLucSid());
            sql.addStrValue(bean.getLabName());
            sql.addStrValue(bean.getLabBiko());
            sql.addIntValue(bean.getLabEuid());
            sql.addDateValue(bean.getLabEdate());
            sql.addIntValue(bean.getLabSid());

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
     * <br>[機  能] 1件のラベル情報を更新します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean UsrLabelModel
     * @throws SQLException SQL実行例外
     */
    public void updateCatMove(CmnLabelUsrModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" set ");
            sql.addSql("   LUC_SID=?,");
            sql.addSql("   LAB_NAME=?,");
            sql.addSql("   LAB_BIKO=?,");
            sql.addSql("   LAB_EUID=?,");
            sql.addSql("   LAB_EDATE=?,");
            sql.addSql("   LAB_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   LAB_SID=?");

            sql.addIntValue(bean.getLucSid());
            sql.addStrValue(bean.getLabName());
            sql.addStrValue(bean.getLabBiko());
            sql.addIntValue(bean.getLabEuid());
            sql.addDateValue(bean.getLabEdate());
            sql.addIntValue(bean.getLabSort());
            sql.addIntValue(bean.getLabSid());

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
     * <br>[機  能] カテゴリー下のラベル情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     *@param sid 選択カテゴリSID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLabelUsrModel> select(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CmnLabelUsrModel> ret = new ArrayList<CmnLabelUsrModel>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LAB_SID,");
            sql.addSql("   LAB_NAME,");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LAB_BIKO,");
            sql.addSql("   LAB_ADATE,");
            sql.addSql("   LAB_AUID,");
            sql.addSql("   LAB_EDATE,");
            sql.addSql("   LAB_EUID,");
            sql.addSql("   LAB_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where ");
            sql.addSql("   LUC_SID=?");
            sql.addSql(" order by");
            sql.addSql("   LAB_SORT");

            sql.addIntValue(sid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLabelUsrFormRs(rs));
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
     * <br>[機  能] ラベル情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ラベル情報一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLabelUsrModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CmnLabelUsrModel> ret = new ArrayList<CmnLabelUsrModel>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LAB_SID,");
            sql.addSql("   LAB_NAME,");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LAB_BIKO,");
            sql.addSql("   LAB_ADATE,");
            sql.addSql("   LAB_AUID,");
            sql.addSql("   LAB_EDATE,");
            sql.addSql("   LAB_EUID,");
            sql.addSql("   LAB_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" order by");
            sql.addSql("   LAB_SID");


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLabelUsrFormRs(rs));
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
     * <br>[機  能] SIDで指定したラベル除いたラベル情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList 除外対象SID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnLabelUsrModel> select(List<Integer> sidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CmnLabelUsrModel> ret = new ArrayList<CmnLabelUsrModel>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LAB_SID,");
            sql.addSql("   LAB_NAME,");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LAB_BIKO,");
            sql.addSql("   LAB_ADATE,");
            sql.addSql("   LAB_AUID,");
            sql.addSql("   LAB_EDATE,");
            sql.addSql("   LAB_EUID,");
            sql.addSql("   LAB_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR");
            if (sidList.size() > 0) {
                sql.addSql(" where ");
                sql.addSql("   LAB_SID not in (");
                boolean firstFlg = true;
                for (int sid : sidList) {
                    if (!firstFlg) {
                        sql.addSql("     ,");
                    } else {
                        firstFlg = false;
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(sid);
                }
                sql.addSql("   )");
            }
            sql.addSql(" order by");
            sql.addSql("   LAB_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLabelUsrFormRs(rs));
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
     * <br>[機  能] 1件のラベル情報を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid ラベルSID
     * @return UsrLabelModel UsrLabelModel
     * @throws SQLException SQL実行例外
     */
    public CmnLabelUsrModel selectOneLabel(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CmnLabelUsrModel ret = new CmnLabelUsrModel();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LAB_SID,");
            sql.addSql("   LUC_SID,");
            sql.addSql("   LAB_NAME,");
            sql.addSql("   LAB_BIKO,");
            sql.addSql("   LAB_AUID,");
            sql.addSql("   LAB_ADATE,");
            sql.addSql("   LAB_EUID,");
            sql.addSql("   LAB_EDATE,");
            sql.addSql("   LAB_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where");
            sql.addSql("   LAB_SID = ?");

            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCmnLabelUsrFormRs(rs);
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
     * <br>[機  能] 表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param motoSid 入れ替え元ラベルSID
     * @param motoSort 入れ替え元ソート順
     * @param sakiSid 入れ替え先ラベルSID
     * @param sakiSort 入れ替え先ソート順
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateSort(
        int motoSid,
        int motoSort,
        int sakiSid,
        int sakiSort,
        Connection con) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql("     set LAB_SORT = case when LAB_SID = ? then"
                           + " ?");
            sql.addSql("     when LAB_SID = ? then"
                           + " ?");
            sql.addSql("     else LAB_SORT end");

            sql.addIntValue(motoSid);
            sql.addIntValue(sakiSort);
            sql.addIntValue(sakiSid);
            sql.addIntValue(motoSort);

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
     * <p>Create CMN_LABEL_USR Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created UsrLabelModel
     * @throws SQLException SQL実行例外
     */
    private CmnLabelUsrModel __getCmnLabelUsrFormRs(ResultSet rs) throws SQLException {
        CmnLabelUsrModel bean = new CmnLabelUsrModel();
        bean.setLabSid(rs.getInt("LAB_SID"));
        bean.setLucSid(rs.getInt("LUC_SID"));
        bean.setLabName(rs.getString("LAB_NAME"));
        bean.setLabBiko(rs.getString("LAB_BIKO"));
        bean.setLabAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LAB_ADATE")));
        bean.setLabAuid(rs.getInt("LAB_AUID"));
        bean.setLabEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LAB_EDATE")));
        bean.setLabEuid(rs.getInt("LAB_EUID"));
        bean.setLabSort(rs.getInt("LAB_SORT"));

        return bean;
    }

    /**
     * <br>[機  能] カテゴリ内のソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sid カテゴリSID
     * @return int ソート順の最大値
     * @throws SQLException SQL実行例外
     */
    public int getSortMax(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(LAB_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where");
            sql.addSql("   LUC_SID=?");
            sql.addIntValue(sid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
            } else {
                ret = 0;
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
     * <br>[機  能] 指定ソート順以上のソート順のラベルSID取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sortNum ソート順
     * @param lucSid カテゴリSID
     * @return ラベルSID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectLabelSort(int sortNum, int lucSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   LAB_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where");
            sql.addSql("   LUC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   LAB_SORT > ?");

            sql.addIntValue(lucSid);
            sql.addIntValue(sortNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("LAB_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return list;
    }
    /**
     * <br>[機  能] ラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param labSid LAB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteSort(int labSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            CmnLabelUsrModel model = selectOneLabel(labSid);
            ArrayList<Integer> list = selectLabelSort(model.getLabSort(), model.getLucSid());
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where ");
            sql.addSql("   LAB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(labSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

            for (int i = 0; i < list.size(); i++) {
                sortArrange(list.get(i));

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <br>[機  能] ラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param labSid LAB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int labSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            CmnLabelUsrModel model = selectOneLabel(labSid);
            ArrayList<Integer> list = selectLabelSort(model.getLabSort(), model.getLucSid());
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" where ");
            sql.addSql("   LAB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(labSid);

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
     * <br>[機  能] カテゴリ内のラベルソート順を整理する
     * <br>[解  説]
     * <br>[備  考]
     * @param sid ラベルSID
     * @throws SQLException SQL実行例外
     */
    public void sortArrange(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_LABEL_USR");
            sql.addSql(" set");
            sql.addSql("   LAB_SORT=");
            sql.addSql("     (select LAB_SORT ");
            sql.addSql("      from");
            sql.addSql("      CMN_LABEL_USR ");
            sql.addSql("     where ");
            sql.addSql("     LAB_SID=?)-1");
            sql.addSql(" where");
            sql.addSql("   LAB_SID=?");
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