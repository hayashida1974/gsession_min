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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.model.SmlLogCountModel;

/**
 * <p>SML_LOG_COUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLogCountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlLogCountDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlLogCountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlLogCountDao(Connection con) {
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
            sql.addSql("drop table SML_LOG_COUNT");

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
            sql.addSql(" create table SML_LOG_COUNT (");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   SLC_KBN NUMBER(10,0) not null,");
            sql.addSql("   SLC_SYS_KBN NUMBER(10,0) not null,");
            sql.addSql("   SLC_CNT_TO NUMBER(10,0) not null,");
            sql.addSql("   SLC_CNT_CC NUMBER(10,0) not null,");
            sql.addSql("   SLC_CNT_BCC NUMBER(10,0) not null,");
            sql.addSql("   SLC_DATE varchar(23) not null");
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
     * <p>Insert SML_LOG_COUNT Data Bindding JavaBean
     * @param bean SML_LOG_COUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_LOG_COUNT(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   SLC_CNT_TO,");
            sql.addSql("   SLC_CNT_CC,");
            sql.addSql("   SLC_CNT_BCC,");
            sql.addSql("   SLC_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSlcKbn());
            sql.addIntValue(bean.getSlcSysKbn());
            sql.addIntValue(bean.getSlcCntTo());
            sql.addIntValue(bean.getSlcCntCc());
            sql.addIntValue(bean.getSlcCntBcc());
            sql.addDateValue(bean.getSlcDate());
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
     * <p>Insert SML_LOG_COUNT DataList
     * @param beanList SML_LOG_COUNT DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SmlLogCountModel> beanList) throws SQLException {

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
            sql.addSql(" SML_LOG_COUNT(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   SLC_CNT_TO,");
            sql.addSql("   SLC_CNT_CC,");
            sql.addSql("   SLC_CNT_BCC,");
            sql.addSql("   SLC_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (SmlLogCountModel bean : beanList) {
                sql.addIntValue(bean.getSacSid());
                sql.addIntValue(bean.getSlcKbn());
                sql.addIntValue(bean.getSlcSysKbn());
                sql.addIntValue(bean.getSlcCntTo());
                sql.addIntValue(bean.getSlcCntCc());
                sql.addIntValue(bean.getSlcCntBcc());
                sql.addDateValue(bean.getSlcDate());
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
     * <p>Update SML_LOG_COUNT Data Bindding JavaBean
     * @param bean SML_LOG_COUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LOG_COUNT");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SLC_KBN=?,");
            sql.addSql("   SLC_SYS_KBN=?,");
            sql.addSql("   SLC_CNT_TO=?,");
            sql.addSql("   SLC_CNT_CC=?,");
            sql.addSql("   SLC_CNT_BCC=?,");
            sql.addSql("   SLC_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSlcKbn());
            sql.addIntValue(bean.getSlcSysKbn());
            sql.addIntValue(bean.getSlcCntTo());
            sql.addIntValue(bean.getSlcCntCc());
            sql.addIntValue(bean.getSlcCntBcc());
            sql.addDateValue(bean.getSlcDate());

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
     * <p>Select SML_LOG_COUNT All Data
     * @return List in SML_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLogCountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLogCountModel> ret = new ArrayList<SmlLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   SLC_CNT_TO,");
            sql.addSql("   SLC_CNT_CC,");
            sql.addSql("   SLC_CNT_BCC,");
            sql.addSql("   SLC_DATE");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLogCountFromRs(rs));
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
     * <p>指定範囲のショートメールログ集計データを取得する
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in SML_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLogCountModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLogCountModel> ret = new ArrayList<SmlLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SLC_KBN,");
            sql.addSql("   SLC_SYS_KBN,");
            sql.addSql("   SLC_CNT_TO,");
            sql.addSql("   SLC_CNT_CC,");
            sql.addSql("   SLC_CNT_BCC,");
            sql.addSql("   SLC_DATE");
            sql.addSql(" from ");
            sql.addSql("   SML_LOG_COUNT");
            sql.addSql(" order by ");
            sql.addSql("   SLC_DATE asc");
            sql.setPagingValue(offset, limit);


            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLogCountFromRs(rs));
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
     * <p>全件数を取得する
     * @return count
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
            sql.addSql("   SML_LOG_COUNT");

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
     * <br>[機  能] 削除を行う
     * <br>[解  説]
     * <br>[備  考] 前日までの集計データを削除する
     * @param date 現在日時
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        
        UDate preDay = date.cloneUDate();
        preDay.addDay(-1);
        preDay.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   SML_LOG_COUNT");
            sql.addSql(" where ");
            sql.addSql("   SLC_DATE <= ?");
            sql.addDateValue(preDay);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create SML_LOG_COUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlLogCountModel
     * @throws SQLException SQL実行例外
     */
    private SmlLogCountModel __getSmlLogCountFromRs(ResultSet rs) throws SQLException {
        SmlLogCountModel bean = new SmlLogCountModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSlcKbn(rs.getInt("SLC_KBN"));
        bean.setSlcSysKbn(rs.getInt("SLC_SYS_KBN"));
        bean.setSlcCntTo(rs.getInt("SLC_CNT_TO"));
        bean.setSlcCntCc(rs.getInt("SLC_CNT_CC"));
        bean.setSlcCntBcc(rs.getInt("SLC_CNT_BCC"));
        bean.setSlcDate(UDate.getInstanceTimestamp(rs.getTimestamp("SLC_DATE")));
        return bean;
    }
}
