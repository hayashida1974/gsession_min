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
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.model.AdrLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrLabelDao(Connection con) {
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
            sql.addSql("drop table ADR_LABEL");

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
            sql.addSql(" create table ADR_LABEL (");
            sql.addSql("   ALB_SID NUMBER(10,0) not null,");
            sql.addSql("   ALB_NAME varchar(20) not null,");
            sql.addSql("   ALB_BIKO varchar(300),");
            sql.addSql("   ALB_SORT NUMBER(10,0) not null,");
            sql.addSql("   ALB_AUID NUMBER(10,0) not null,");
            sql.addSql("   ALB_ADATE varchar(23) not null,");
            sql.addSql("   ALB_EUID NUMBER(10,0) not null,");
            sql.addSql("   ALB_EDATE varchar(23) not null,");
            sql.addSql("   ALC_SID integer not null,");
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
     * <p>Insert ADR_LABEL Data Bindding JavaBean
     * @param bean ADR_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_LABEL(");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
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
            sql.addIntValue(bean.getAlbSid());
            sql.addStrValue(bean.getAlbName());
            sql.addStrValue(bean.getAlbBiko());
            sql.addIntValue(bean.getAlbSort());
            sql.addIntValue(bean.getAlbAuid());
            sql.addDateValue(bean.getAlbAdate());
            sql.addIntValue(bean.getAlbEuid());
            sql.addDateValue(bean.getAlbEdate());
            sql.addIntValue(bean.getAlcSid());
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
     * <p>Insert ADR_LABEL Data Bindding JavaBean
     * @param bean ADR_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertNewLabel(AdrLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_LABEL(");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   coalesce(max(ALB_SORT), 0) + 1,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where");
            sql.addSql("   ALC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAlbSid());
            sql.addStrValue(bean.getAlbName());
            sql.addStrValue(bean.getAlbBiko());
            sql.addIntValue(bean.getAlbAuid());
            sql.addDateValue(bean.getAlbAdate());
            sql.addIntValue(bean.getAlbEuid());
            sql.addDateValue(bean.getAlbEdate());
            sql.addIntValue(bean.getAlcSid());
            sql.addIntValue(bean.getAlcSid());

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
     * <p>Update ADR_LABEL Data Bindding JavaBean
     * @param bean ADR_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" set ");
            sql.addSql("   ALB_NAME=?,");
            sql.addSql("   ALB_BIKO=?,");
            sql.addSql("   ALB_EUID=?,");
            sql.addSql("   ALB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAlbName());
            sql.addStrValue(bean.getAlbBiko());
            sql.addIntValue(bean.getAlbEuid());
            sql.addDateValue(bean.getAlbEdate());
            //where
            sql.addIntValue(bean.getAlbSid());

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
     * <p>Update ADR_LABEL Data Bindding JavaBean
     * @param bean ADR_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCatMove(AdrLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" set ");
            sql.addSql("   ALB_NAME=?,");
            sql.addSql("   ALB_BIKO=?,");
            sql.addSql("   ALB_EUID=?,");
            sql.addSql("   ALB_EDATE=?,");
            sql.addSql("   ALB_SORT=?,");
            sql.addSql("   ALC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAlbName());
            sql.addStrValue(bean.getAlbBiko());
            sql.addIntValue(bean.getAlbEuid());
            sql.addDateValue(bean.getAlbEdate());
            sql.addIntValue(bean.getAlbSort());
            sql.addIntValue(bean.getAlcSid());
            //where
            sql.addIntValue(bean.getAlbSid());

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
     * <p>Select ADR_LABEL All Data
     * @return List in ADR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrLabelModel> ret = new ArrayList<AdrLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" order by ");
            sql.addSql("   ALB_SORT");

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
     * <p>カテゴリ内のラベル一覧を取得
     * @param alcSid ラベルカテゴリSID
     * @return List in ADR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrLabelModel> getLabelInCategory(int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrLabelModel> ret = new ArrayList<AdrLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   ALB_SORT");

            sql.addIntValue(alcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>アドレス帳SIDから付与されているラベルリストを作成します。
     * @param adrSid アドレス帳SID
     * @return List in ADR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrLabelModel> selectBelongLabelList(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrLabelModel> ret = new ArrayList<AdrLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID");
            sql.addSql(" in (");
            sql.addSql("     select ");
            sql.addSql("       ALB_SID");
            sql.addSql("     from ");
            sql.addSql("       ADR_BELONG_LABEL");
            sql.addSql("     where ");
            sql.addSql("       ADR_SID=?");
            sql.addSql(" )");
            sql.addSql(" order by ");
            sql.addSql("   ALB_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

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
     * <p>Select ADR_LABEL
     * @param albSid ALB_SID
     * @return ADR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public AdrLabelModel select(int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ALB_NAME,");
            sql.addSql("   ALB_BIKO,");
            sql.addSql("   ALB_SORT,");
            sql.addSql("   ALB_AUID,");
            sql.addSql("   ALB_ADATE,");
            sql.addSql("   ALB_EUID,");
            sql.addSql("   ALB_EDATE,");
            sql.addSql("   ALC_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(albSid);

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
     * @param sid ラベルカテゴリSID
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
            sql.addSql("   max(ALB_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where");
            sql.addSql("   ALC_SID=?");

            log__.info(sql.toLogString());
            sql.addIntValue(sid);

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
     * <p>Delete ADR_LABEL
     * @param albSid ALB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            AdrLabelModel model = select(albSid);
            ArrayList<Integer> list = selectLabelSort(model.getAlbSort(), model.getAlcSid());

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(albSid);

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
     * <br>[機  能] 指定したカテゴリSIDに格納されているラベルのうち、
     *              アドレスに付与されている件数をラベルSIDごとに取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param catSid カテゴリSID
     * @return 社員情報ごとのラベル付与件数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrLabelModel> getCountLabBelongCat(int catSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrLabelModel> modelList = new ArrayList<AdrLabelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    LAB_SID,");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from ADR_LABEL");
            sql.addSql("  where LAB_SID in (");
            sql.addSql("    select LAB_SID from ADR_LABEL");
            sql.addSql("    where ALC_SID = ? )");
            sql.addSql("  group by");
            sql.addSql("    LAB_SID");
            sql.addIntValue(catSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AdrLabelModel model = new AdrLabelModel();
                model.setAlbSid(rs.getInt("LAB_SID"));
                model.setCount(rs.getInt("CNT"));
                modelList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return modelList;
    }

    /**
     * <br>[機  能] 指定カテゴリ内のラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param alcSid ラベルカテゴリSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteInCategory(int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(alcSid);

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
            sql.addSql("   ADR_LABEL");
            sql.addSql(" set");
            sql.addSql("   ALB_SORT=");
            sql.addSql("     (select ALB_SORT ");
            sql.addSql("      from");
            sql.addSql("      ADR_LABEL ");
            sql.addSql("     where ");
            sql.addSql("     ALB_SID=?)-1");
            sql.addSql(" where");
            sql.addSql("   ALB_SID=?");
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
     * <br>[機  能] 指定ソート順以上のソート順のラベルSID取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sortNum ソート順
     * @param alcSid ラベルカテゴリSID
     * @return ラベルSID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectLabelSort(int sortNum, int alcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALB_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where");
            sql.addSql("   ALC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ALB_SORT > ?");

            sql.addIntValue(alcSid);
            sql.addIntValue(sortNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ALB_SID"));
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
     * <p>削除されたカテゴリ内のラベルを「未選択」カテゴリに移動します。
     * @param lbMdl ラベルモデル
     * @param sort ソート順
     * @throws SQLException SQL実行例外
     */
    public void deleteCatAndLab(AdrLabelModel lbMdl, int sort) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //カテゴリを「未設定」のカテゴリに変更
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" update");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" set ");
            sql.addSql("   ALB_SORT=?,");
            sql.addSql("   ALC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   ALB_SID=?");

            sql.addIntValue(sort);
            sql.addIntValue(GSConstAddress.LABEL_CATEGORY_NOSET);
            sql.addIntValue(lbMdl.getAlbSid());

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
     * <p>Create ADR_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrLabelModel
     * @throws SQLException SQL実行例外
     */
    private AdrLabelModel __getAdrLabelFromRs(ResultSet rs) throws SQLException {
        AdrLabelModel bean = new AdrLabelModel();
        bean.setAlbSid(rs.getInt("ALB_SID"));
        bean.setAlbName(rs.getString("ALB_NAME"));
        bean.setAlbBiko(rs.getString("ALB_BIKO"));
        bean.setAlbSort(rs.getInt("ALB_SORT"));
        bean.setAlbAuid(rs.getInt("ALB_AUID"));
        bean.setAlbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ALB_ADATE")));
        bean.setAlbEuid(rs.getInt("ALB_EUID"));
        bean.setAlbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ALB_EDATE")));
        bean.setAlcSid(rs.getInt("ALC_SID"));

        return bean;
    }
}
