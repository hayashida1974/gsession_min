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
import jp.groupsession.v2.adr.model.AdrPositionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_POSITION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrPositionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrPositionDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrPositionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrPositionDao(Connection con) {
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
            sql.addSql("drop table ADR_POSITION");

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
            sql.addSql(" create table ADR_POSITION (");
            sql.addSql("   APS_SID NUMBER(10,0) not null,");
            sql.addSql("   APS_NAME varchar(30) not null,");
            sql.addSql("   APS_AUID NUMBER(10,0) not null,");
            sql.addSql("   APS_ADATE varchar(23) not null,");
            sql.addSql("   APS_EUID NUMBER(10,0) not null,");
            sql.addSql("   APS_EDATE varchar(23) not null,");
            sql.addSql("   primary key (APS_SID)");
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
     * <p>Insert ADR_POSITION Data Bindding JavaBean
     * @param bean ADR_POSITION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_POSITION(");
            sql.addSql("   APS_SID,");
            sql.addSql("   APS_NAME,");
            sql.addSql("   APS_BIKO,");
            sql.addSql("   APS_SORT,");
            sql.addSql("   APS_AUID,");
            sql.addSql("   APS_ADATE,");
            sql.addSql("   APS_EUID,");
            sql.addSql("   APS_EDATE");
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
            sql.addIntValue(bean.getApsSid());
            sql.addStrValue(bean.getApsName());
            sql.addStrValue(bean.getApsBiko());
            sql.addIntValue(bean.getApsSort());
            sql.addIntValue(bean.getApsAuid());
            sql.addDateValue(bean.getApsAdate());
            sql.addIntValue(bean.getApsEuid());
            sql.addDateValue(bean.getApsEdate());
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
     * <br>[機  能] 役職新規登録
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データ
     * @throws SQLException SQL実行例外
     */
    public void insertNewYakusyoku(AdrPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_POSITION(");
            sql.addSql("   APS_SID,");
            sql.addSql("   APS_NAME,");
            sql.addSql("   APS_AUID,");
            sql.addSql("   APS_ADATE,");
            sql.addSql("   APS_EUID,");
            sql.addSql("   APS_EDATE,");
            sql.addSql("   APS_BIKO,");
            sql.addSql("   APS_SORT");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   case when max(APS_SORT) is null then 1 "
                        + "else max(APS_SORT) + 1 end");
            sql.addSql(" from");
            sql.addSql("   ADR_POSITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getApsSid());
            sql.addStrValue(bean.getApsName());
            sql.addIntValue(bean.getApsAuid());
            sql.addDateValue(bean.getApsAdate());
            sql.addIntValue(bean.getApsEuid());
            sql.addDateValue(bean.getApsEdate());
            sql.addStrValue(bean.getApsBiko());

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
     * <p>Update ADR_POSITION Data Bindding JavaBean
     * @param bean ADR_POSITION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" set ");
            sql.addSql("   APS_NAME=?,");
            sql.addSql("   APS_AUID=?,");
            sql.addSql("   APS_ADATE=?,");
            sql.addSql("   APS_EUID=?,");
            sql.addSql("   APS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApsName());
            sql.addIntValue(bean.getApsAuid());
            sql.addDateValue(bean.getApsAdate());
            sql.addIntValue(bean.getApsEuid());
            sql.addDateValue(bean.getApsEdate());
            //where
            sql.addIntValue(bean.getApsSid());

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
     * <br>[機  能] 役職データを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新データ
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateNameAndBiko(AdrPositionModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" set ");
            sql.addSql("   APS_NAME=?,");
            sql.addSql("   APS_BIKO=?,");
            sql.addSql("   APS_EUID=?,");
            sql.addSql("   APS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getApsName());
            sql.addStrValue(bean.getApsBiko());
            sql.addIntValue(bean.getApsEuid());
            sql.addDateValue(bean.getApsEdate());
            //where
            sql.addIntValue(bean.getApsSid());

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
     * <p>Select ADR_POSITION All Data
     * @return List in ADR_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrPositionModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPositionModel> ret = new ArrayList<AdrPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APS_SID,");
            sql.addSql("   APS_NAME,");
            sql.addSql("   APS_AUID,");
            sql.addSql("   APS_ADATE,");
            sql.addSql("   APS_EUID,");
            sql.addSql("   APS_EDATE,");
            sql.addSql("   APS_BIKO,");
            sql.addSql("   APS_SORT");
            sql.addSql(" from ");
            sql.addSql("   ADR_POSITION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrPositionFromRs(rs));
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
     * <p>Select ADR_POSITION
     * @param apsSid APS_SID
     * @return ADR_POSITIONModel
     * @throws SQLException SQL実行例外
     */
    public AdrPositionModel select(int apsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrPositionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APS_SID,");
            sql.addSql("   APS_NAME,");
            sql.addSql("   APS_AUID,");
            sql.addSql("   APS_ADATE,");
            sql.addSql("   APS_EUID,");
            sql.addSql("   APS_EDATE,");
            sql.addSql("   APS_BIKO,");
            sql.addSql("   APS_SORT");
            sql.addSql(" from");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" where ");
            sql.addSql("   APS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(apsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrPositionFromRs(rs);
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
     * <br>[機  能] 役職一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret 役職一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<AdrPositionModel> selectPositionList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrPositionModel> ret = new ArrayList<AdrPositionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APS_SID,");
            sql.addSql("   APS_NAME,");
            sql.addSql("   APS_AUID,");
            sql.addSql("   APS_ADATE,");
            sql.addSql("   APS_EUID,");
            sql.addSql("   APS_EDATE,");
            sql.addSql("   APS_BIKO,");
            sql.addSql("   APS_SORT");
            sql.addSql(" from");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" order by ");
            sql.addSql("   APS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AdrPositionModel mdl = __getAdrPositionFromRs(rs);
                ret.add(mdl);
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
     * <p>Delete ADR_POSITION
     * @param apsSid APS_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int apsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" where ");
            sql.addSql("   APS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(apsSid);

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
     * <br>[機  能] 役職名が存在するか確認する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param posName 検索する役職名
     * @param posSid 除外する役職SID(自分自身など)
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistPositionName(String posName, int posSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(APS_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   ADR_POSITION");
            sql.addSql(" where ");
            sql.addSql("   APS_NAME = ?");

            sql.addStrValue(posName);

            if (posSid > 0) {
                sql.addSql(" and");
                sql.addSql("   APS_SID <> ?");
                sql.addIntValue(posSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <p>Create ADR_POSITION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrPositionModel
     * @throws SQLException SQL実行例外
     */
    private AdrPositionModel __getAdrPositionFromRs(ResultSet rs) throws SQLException {
        AdrPositionModel bean = new AdrPositionModel();
        bean.setApsSid(rs.getInt("APS_SID"));
        bean.setApsName(rs.getString("APS_NAME"));
        bean.setApsAuid(rs.getInt("APS_AUID"));
        bean.setApsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APS_ADATE")));
        bean.setApsEuid(rs.getInt("APS_EUID"));
        bean.setApsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APS_EDATE")));
        bean.setApsBiko(rs.getString("APS_BIKO"));
        bean.setApsSort(rs.getInt("APS_SORT"));
        return bean;
    }
}