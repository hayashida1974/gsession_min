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
import jp.groupsession.v2.adr.model.AdrBelongLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_BELONG_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrBelongLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrBelongLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrBelongLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrBelongLabelDao(Connection con) {
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
            sql.addSql("drop table ADR_BELONG_LABEL");

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
            sql.addSql(" create table ADR_BELONG_LABEL (");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ALB_SID NUMBER(10,0) not null,");
            sql.addSql("   ABL_AUID NUMBER(10,0) not null,");
            sql.addSql("   ABL_ADATE varchar(23) not null,");
            sql.addSql("   ABL_EUID NUMBER(10,0) not null,");
            sql.addSql("   ABL_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADR_SID,ALB_SID)");
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
     * <p>Insert ADR_BELONG_LABEL Data Bindding JavaBean
     * @param bean ADR_BELONG_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_BELONG_LABEL(");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ABL_AUID,");
            sql.addSql("   ABL_ADATE,");
            sql.addSql("   ABL_EUID,");
            sql.addSql("   ABL_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getAlbSid());
            sql.addIntValue(bean.getAblAuid());
            sql.addDateValue(bean.getAblAdate());
            sql.addIntValue(bean.getAblEuid());
            sql.addDateValue(bean.getAblEdate());
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
     * <p>Update ADR_BELONG_LABEL Data Bindding JavaBean
     * @param bean ADR_BELONG_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" set ");
            sql.addSql("   ABL_AUID=?,");
            sql.addSql("   ABL_ADATE=?,");
            sql.addSql("   ABL_EUID=?,");
            sql.addSql("   ABL_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAblAuid());
            sql.addDateValue(bean.getAblAdate());
            sql.addIntValue(bean.getAblEuid());
            sql.addDateValue(bean.getAblEdate());
            //where
            sql.addIntValue(bean.getAdrSid());
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
     * <p>Select ADR_BELONG_LABEL All Data
     * @return List in ADR_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrBelongLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrBelongLabelModel> ret = new ArrayList<AdrBelongLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ABL_AUID,");
            sql.addSql("   ABL_ADATE,");
            sql.addSql("   ABL_EUID,");
            sql.addSql("   ABL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_BELONG_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrBelongLabelFromRs(rs));
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
     * <br>[機  能] 指定したアドレス帳のラベルSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return ラベルSID一覧
     * @throws SQLException SQL実行時例外
     */
    public String[] getLabelSidList(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> labelSidList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALB_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID = ?");

            sql.addIntValue(adrSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                labelSidList.add(rs.getString("ALB_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return labelSidList.toArray(new String[labelSidList.size()]);
    }

    /**
     * <p>Select ADR_BELONG_LABEL
     * @param adrSid ADR_SID
     * @param albSid ALB_SID
     * @return ADR_BELONG_LABELModel
     * @throws SQLException SQL実行例外
     */
    public AdrBelongLabelModel select(int adrSid, int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrBelongLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ALB_SID,");
            sql.addSql("   ABL_AUID,");
            sql.addSql("   ABL_ADATE,");
            sql.addSql("   ABL_EUID,");
            sql.addSql("   ABL_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(albSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrBelongLabelFromRs(rs);
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
     * <br>[機  能] 指定したラベルSIDに付与されているアドレス帳の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ablSid ラベルSID
     * @return int アドレス帳の件数
     * @throws SQLException SQL実行例外
     */
    public int getIndCount(int ablSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where");
            sql.addSql("   ALB_SID = ?");

            sql.addIntValue(ablSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>Delete ADR_BELONG_LABEL
     * @param adrSid ADR_SID
     * @param albSid ALB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int adrSid, int albSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");
            sql.addSql(" and");
            sql.addSql("   ALB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);
            sql.addIntValue(albSid);

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
     * <br>[機  能] 指定したアドレス帳のラベル付与情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteToAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

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
     * <br>[機  能] 指定したアドレス帳のラベル付与情報を削除する(複数編集)
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int deleteToAddressMulti(String[] adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ADR_SID in");
            sql.addSql("   ( ?");

            for (int i = 0; i < adrSid.length - 1; i++) {
                sql.addSql("  , ?");
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (int n = 0; n < adrSid.length; n++) {
                sql.addStrValue(adrSid[n]);
            }
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
     * <br>[機  能] 指定したカテゴリSIDに格納されているラベルのうち、
     *              アドレスに付与されている件数をラベルSIDごとに取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param catSid ラベルSID
     * @return ユーザ情報ごとのラベル付与件数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrBelongLabelModel> getCountLabBelongCat(int catSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        ArrayList<AdrBelongLabelModel> modelList = new ArrayList<AdrBelongLabelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    ALB_SID,");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from ADR_BELONG_LABEL");
            sql.addSql("  where ALB_SID in (");
            sql.addSql("    select ALB_SID from ADR_LABEL");
            sql.addSql("    where ALC_SID = ? )");
            sql.addSql("  group by");
            sql.addSql("    ALB_SID");
            sql.addIntValue(catSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AdrBelongLabelModel model = new AdrBelongLabelModel();
                model.setAlbSid(rs.getInt("ALB_SID"));
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
     * <p>Create ADR_BELONG_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrBelongLabelModel
     * @throws SQLException SQL実行例外
     */
    private AdrBelongLabelModel __getAdrBelongLabelFromRs(ResultSet rs) throws SQLException {
        AdrBelongLabelModel bean = new AdrBelongLabelModel();
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAlbSid(rs.getInt("ALB_SID"));
        bean.setAblAuid(rs.getInt("ABL_AUID"));
        bean.setAblAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABL_ADATE")));
        bean.setAblEuid(rs.getInt("ABL_EUID"));
        bean.setAblEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABL_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] ラベルの複数編集
     * <br>[解  説]
     * <br>[備  考]
     * @param bean AdrBelongLabelModel
     * @throws SQLException SQL実行時例外
     */
    public void insertMulti(AdrBelongLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            String[] checkSid = getLabelSidList(bean.getAdrSid());
            boolean checkFlg = false;
            for (String str : checkSid) {
                if (bean.getAlbSid() == Integer.parseInt(str) && checkFlg == false) {
                    checkFlg = true;
                }
            }

            if (checkFlg == false) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" ADR_BELONG_LABEL(");
                sql.addSql("   ADR_SID,");
                sql.addSql("   ALB_SID,");
                sql.addSql("   ABL_AUID,");
                sql.addSql("   ABL_ADATE,");
                sql.addSql("   ABL_EUID,");
                sql.addSql("   ABL_EDATE");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getAdrSid());
                sql.addIntValue(bean.getAlbSid());
                sql.addIntValue(bean.getAblAuid());
                sql.addDateValue(bean.getAblAdate());
                sql.addIntValue(bean.getAblEuid());
                sql.addDateValue(bean.getAblEdate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

}
