package jp.groupsession.v2.wml.dao.base;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteModel;

/**
 * <p>WML_AUTODELETE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAutodeleteDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAutodeleteDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAutodeleteDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAutodeleteDao(Connection con) {
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
            sql.addSql("drop table WML_AUTODELETE");

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
            sql.addSql(" create table WML_AUTODELETE (");
            sql.addSql("   WAD_DUST_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_DUST_YEAR NUMBER(10,0),");
            sql.addSql("   WAD_DUST_MONTH NUMBER(10,0),");
            sql.addSql("   WAD_DUST_DAY NUMBER(10,0),");
            sql.addSql("   WAD_SEND_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_SEND_YEAR NUMBER(10,0),");
            sql.addSql("   WAD_SEND_MONTH NUMBER(10,0),");
            sql.addSql("   WAD_SEND_DAY NUMBER(10,0),");
            sql.addSql("   WAD_DRAFT_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_DRAFT_YEAR NUMBER(10,0),");
            sql.addSql("   WAD_DRAFT_MONTH NUMBER(10,0),");
            sql.addSql("   WAD_DRAFT_DAY NUMBER(10,0),");
            sql.addSql("   WAD_RESV_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_RESV_YEAR NUMBER(10,0),");
            sql.addSql("   WAD_RESV_MONTH NUMBER(10,0),");
            sql.addSql("   WAD_RESV_DAY NUMBER(10,0),");
            sql.addSql("   WAD_KEEP_KBN NUMBER(10,0) not null,");
            sql.addSql("   WAD_KEEP_YEAR NUMBER(10,0),");
            sql.addSql("   WAD_KEEP_MONTH NUMBER(10,0),");
            sql.addSql("   WAD_KEEP_DAY NUMBER(10,0),");
            sql.addSql("   WAD_AUID NUMBER(10,0) not null,");
            sql.addSql("   WAD_ADATE varchar(23) not null,");
            sql.addSql("   WAD_EUID NUMBER(10,0) not null,");
            sql.addSql("   WAD_EDATE varchar(23) not null");
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
     * <p>Insert WML_AUTODELETE Data Bindding JavaBean
     * @param bean WML_AUTODELETE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_AUTODELETE(");
            sql.addSql("   WAD_DUST_KBN,");
            sql.addSql("   WAD_DUST_YEAR,");
            sql.addSql("   WAD_DUST_MONTH,");
            sql.addSql("   WAD_DUST_DAY,");
            sql.addSql("   WAD_SEND_KBN,");
            sql.addSql("   WAD_SEND_YEAR,");
            sql.addSql("   WAD_SEND_MONTH,");
            sql.addSql("   WAD_SEND_DAY,");
            sql.addSql("   WAD_DRAFT_KBN,");
            sql.addSql("   WAD_DRAFT_YEAR,");
            sql.addSql("   WAD_DRAFT_MONTH,");
            sql.addSql("   WAD_DRAFT_DAY,");
            sql.addSql("   WAD_RESV_KBN,");
            sql.addSql("   WAD_RESV_YEAR,");
            sql.addSql("   WAD_RESV_MONTH,");
            sql.addSql("   WAD_RESV_DAY,");
            sql.addSql("   WAD_KEEP_KBN,");
            sql.addSql("   WAD_KEEP_YEAR,");
            sql.addSql("   WAD_KEEP_MONTH,");
            sql.addSql("   WAD_KEEP_DAY,");
            sql.addSql("   WAD_AUID,");
            sql.addSql("   WAD_ADATE,");
            sql.addSql("   WAD_EUID,");
            sql.addSql("   WAD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
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
            sql.addIntValue(bean.getWadDustKbn());
            sql.addIntValue(bean.getWadDustYear());
            sql.addIntValue(bean.getWadDustMonth());
            sql.addIntValue(bean.getWadDustDay());
            sql.addIntValue(bean.getWadSendKbn());
            sql.addIntValue(bean.getWadSendYear());
            sql.addIntValue(bean.getWadSendMonth());
            sql.addIntValue(bean.getWadSendDay());
            sql.addIntValue(bean.getWadDraftKbn());
            sql.addIntValue(bean.getWadDraftYear());
            sql.addIntValue(bean.getWadDraftMonth());
            sql.addIntValue(bean.getWadDraftDay());
            sql.addIntValue(bean.getWadResvKbn());
            sql.addIntValue(bean.getWadResvYear());
            sql.addIntValue(bean.getWadResvMonth());
            sql.addIntValue(bean.getWadResvDay());
            sql.addIntValue(bean.getWadKeepKbn());
            sql.addIntValue(bean.getWadKeepYear());
            sql.addIntValue(bean.getWadKeepMonth());
            sql.addIntValue(bean.getWadKeepDay());
            sql.addIntValue(bean.getWadAuid());
            sql.addDateValue(bean.getWadAdate());
            sql.addIntValue(bean.getWadEuid());
            sql.addDateValue(bean.getWadEdate());
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
     * <p>Update WML_AUTODELETE Data Bindding JavaBean
     * @param bean WML_AUTODELETE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAutodeleteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_AUTODELETE");
            sql.addSql(" set ");
            sql.addSql("   WAD_DUST_KBN=?,");
            sql.addSql("   WAD_DUST_YEAR=?,");
            sql.addSql("   WAD_DUST_MONTH=?,");
            sql.addSql("   WAD_DUST_DAY=?,");
            sql.addSql("   WAD_SEND_KBN=?,");
            sql.addSql("   WAD_SEND_YEAR=?,");
            sql.addSql("   WAD_SEND_MONTH=?,");
            sql.addSql("   WAD_SEND_DAY=?,");
            sql.addSql("   WAD_DRAFT_KBN=?,");
            sql.addSql("   WAD_DRAFT_YEAR=?,");
            sql.addSql("   WAD_DRAFT_MONTH=?,");
            sql.addSql("   WAD_DRAFT_DAY=?,");
            sql.addSql("   WAD_RESV_KBN=?,");
            sql.addSql("   WAD_RESV_YEAR=?,");
            sql.addSql("   WAD_RESV_MONTH=?,");
            sql.addSql("   WAD_RESV_DAY=?,");
            sql.addSql("   WAD_KEEP_KBN=?,");
            sql.addSql("   WAD_KEEP_YEAR=?,");
            sql.addSql("   WAD_KEEP_MONTH=?,");
            sql.addSql("   WAD_KEEP_DAY=?,");
            sql.addSql("   WAD_EUID=?,");
            sql.addSql("   WAD_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWadDustKbn());
            sql.addIntValue(bean.getWadDustYear());
            sql.addIntValue(bean.getWadDustMonth());
            sql.addIntValue(bean.getWadDustDay());
            sql.addIntValue(bean.getWadSendKbn());
            sql.addIntValue(bean.getWadSendYear());
            sql.addIntValue(bean.getWadSendMonth());
            sql.addIntValue(bean.getWadSendDay());
            sql.addIntValue(bean.getWadDraftKbn());
            sql.addIntValue(bean.getWadDraftYear());
            sql.addIntValue(bean.getWadDraftMonth());
            sql.addIntValue(bean.getWadDraftDay());
            sql.addIntValue(bean.getWadResvKbn());
            sql.addIntValue(bean.getWadResvYear());
            sql.addIntValue(bean.getWadResvMonth());
            sql.addIntValue(bean.getWadResvDay());
            sql.addIntValue(bean.getWadKeepKbn());
            sql.addIntValue(bean.getWadKeepYear());
            sql.addIntValue(bean.getWadKeepMonth());
            sql.addIntValue(bean.getWadKeepDay());
            sql.addIntValue(bean.getWadEuid());
            sql.addDateValue(bean.getWadEdate());

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
     * <br>[機  能] メール自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     * @return WmlAutodeleteModel
     */
    public WmlAutodeleteModel getAutoDelSetUp() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlAutodeleteModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAD_DUST_KBN,");
            sql.addSql("   WAD_DUST_YEAR,");
            sql.addSql("   WAD_DUST_MONTH,");
            sql.addSql("   WAD_DUST_DAY,");
            sql.addSql("   WAD_SEND_KBN,");
            sql.addSql("   WAD_SEND_YEAR,");
            sql.addSql("   WAD_SEND_MONTH,");
            sql.addSql("   WAD_SEND_DAY,");
            sql.addSql("   WAD_DRAFT_KBN,");
            sql.addSql("   WAD_DRAFT_YEAR,");
            sql.addSql("   WAD_DRAFT_MONTH,");
            sql.addSql("   WAD_DRAFT_DAY,");
            sql.addSql("   WAD_RESV_KBN,");
            sql.addSql("   WAD_RESV_YEAR,");
            sql.addSql("   WAD_RESV_MONTH,");
            sql.addSql("   WAD_RESV_DAY,");
            sql.addSql("   WAD_KEEP_KBN,");
            sql.addSql("   WAD_KEEP_YEAR,");
            sql.addSql("   WAD_KEEP_MONTH,");
            sql.addSql("   WAD_KEEP_DAY,");
            sql.addSql("   WAD_AUID,");
            sql.addSql("   WAD_ADATE,");
            sql.addSql("   WAD_EUID,");
            sql.addSql("   WAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_AUTODELETE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlAutodeleteFromRs(rs);
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
     * <br>[機  能] メール自動削除設定登録件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     * @return WmlAutodeleteModel
     */
    public int getAutoDelSetUpCnt() throws SQLException {

        int count = -1;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   WML_AUTODELETE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create WML_AUTODELETE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAutodeleteModel
     * @throws SQLException SQL実行例外
     */
    private WmlAutodeleteModel __getWmlAutodeleteFromRs(ResultSet rs) throws SQLException {
        WmlAutodeleteModel bean = new WmlAutodeleteModel();
        bean.setWadDustKbn(rs.getInt("WAD_DUST_KBN"));
        bean.setWadDustYear(rs.getInt("WAD_DUST_YEAR"));
        bean.setWadDustMonth(rs.getInt("WAD_DUST_MONTH"));
        bean.setWadDustDay(rs.getInt("WAD_DUST_DAY"));
        bean.setWadSendKbn(rs.getInt("WAD_SEND_KBN"));
        bean.setWadSendYear(rs.getInt("WAD_SEND_YEAR"));
        bean.setWadSendMonth(rs.getInt("WAD_SEND_MONTH"));
        bean.setWadSendDay(rs.getInt("WAD_SEND_DAY"));
        bean.setWadDraftKbn(rs.getInt("WAD_DRAFT_KBN"));
        bean.setWadDraftYear(rs.getInt("WAD_DRAFT_YEAR"));
        bean.setWadDraftMonth(rs.getInt("WAD_DRAFT_MONTH"));
        bean.setWadDraftDay(rs.getInt("WAD_DRAFT_DAY"));
        bean.setWadResvKbn(rs.getInt("WAD_RESV_KBN"));
        bean.setWadResvYear(rs.getInt("WAD_RESV_YEAR"));
        bean.setWadResvMonth(rs.getInt("WAD_RESV_MONTH"));
        bean.setWadResvDay(rs.getInt("WAD_RESV_DAY"));
        bean.setWadKeepKbn(rs.getInt("WAD_KEEP_KBN"));
        bean.setWadKeepYear(rs.getInt("WAD_KEEP_YEAR"));
        bean.setWadKeepMonth(rs.getInt("WAD_KEEP_MONTH"));
        bean.setWadKeepDay(rs.getInt("WAD_KEEP_DAY"));
        bean.setWadAuid(rs.getInt("WAD_AUID"));
        bean.setWadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WAD_ADATE")));
        bean.setWadEuid(rs.getInt("WAD_EUID"));
        bean.setWadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WAD_EDATE")));
        return bean;
    }
}
