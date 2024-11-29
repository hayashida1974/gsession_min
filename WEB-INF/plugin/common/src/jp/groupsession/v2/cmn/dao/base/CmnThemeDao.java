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
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;

/**
 * <p>CMN_THEME Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnThemeDao extends AbstractDao {

    /** テーマSID Classicテーマ最終SID */
    private static final int CTM_SID_CLASSIC_LAST__ = 6;
    /** テーマSID Originalテーマ開始SID */
    private static final int CTM_SID_ORIGINAL_FIRST__ = 7;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnThemeDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnThemeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnThemeDao(Connection con) {
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
            sql.addSql("drop table CMN_THEME");

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
            sql.addSql(" create table CMN_THEME (");
            sql.addSql("   CTM_SID NUMBER(10,0) not null,");
            sql.addSql("   CTM_ID varchar(20) not null,");
            sql.addSql("   CTM_NAME varchar(50) not null,");
            sql.addSql("   CTM_PATH varchar(100) not null,");
            sql.addSql("   CTM_AUID NUMBER(10,0) not null,");
            sql.addSql("   CTM_ADATE varchar(23) not null,");
            sql.addSql("   CTM_EUID NUMBER(10,0) not null,");
            sql.addSql("   CTM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (CTM_SID)");
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
     * <p>Insert CMN_THEME Data Bindding JavaBean
     * @param bean CMN_THEME Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnThemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_THEME(");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_ID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH,");
            sql.addSql("   CTM_PATH_IMG,");
            sql.addSql("   CTM_AUID,");
            sql.addSql("   CTM_ADATE,");
            sql.addSql("   CTM_EUID,");
            sql.addSql("   CTM_EDATE");
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
            sql.addIntValue(bean.getCtmSid());
            sql.addStrValue(bean.getCtmId());
            sql.addStrValue(bean.getCtmName());
            sql.addStrValue(bean.getCtmPath());
            sql.addStrValue(bean.getCtmPathImg());
            sql.addIntValue(bean.getCtmAuid());
            sql.addDateValue(bean.getCtmAdate());
            sql.addIntValue(bean.getCtmEuid());
            sql.addDateValue(bean.getCtmEdate());
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
     * <p>Update CMN_THEME Data Bindding JavaBean
     * @param bean CMN_THEME Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnThemeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_THEME");
            sql.addSql(" set ");
            sql.addSql("   CTM_ID=?,");
            sql.addSql("   CTM_NAME=?,");
            sql.addSql("   CTM_PATH=?,");
            sql.addSql("   CTM_EUID=?,");
            sql.addSql("   CTM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCtmId());
            sql.addStrValue(bean.getCtmName());
            sql.addStrValue(bean.getCtmPath());
            sql.addIntValue(bean.getCtmEuid());
            sql.addDateValue(bean.getCtmEdate());
            //where
            sql.addIntValue(bean.getCtmSid());

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
     * <p>Select CMN_THEME All Data
     * @return List in CMN_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnThemeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnThemeModel> ret = new ArrayList<CmnThemeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_ID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH,");
            sql.addSql("   CTM_PATH_IMG,");
            sql.addSql("   CTM_AUID,");
            sql.addSql("   CTM_ADATE,");
            sql.addSql("   CTM_EUID,");
            sql.addSql("   CTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnThemeFromRs(rs));
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
     * <p>Select CMN_THEME
     * @param ctmSid CTM_SID
     * @return CMN_THEMEModel
     * @throws SQLException SQL実行例外
     */
    public CmnThemeModel select(int ctmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnThemeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_ID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH,");
            sql.addSql("   CTM_PATH_IMG,");
            sql.addSql("   CTM_AUID,");
            sql.addSql("   CTM_ADATE,");
            sql.addSql("   CTM_EUID,");
            sql.addSql("   CTM_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ctmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnThemeFromRs(rs);
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
     * <br>[機  能] テーマ情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考] Original、Classicの順にテーマ情報を取得する
     * @return テーマ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<CmnThemeModel> getThemeList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnThemeModel> ret = new ArrayList<CmnThemeModel>();
        con = getCon();

        try {
            //Orignalテーマのテーマ情報を取得
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_ID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH,");
            sql.addSql("   CTM_PATH_IMG,");
            sql.addSql("   CTM_AUID,");
            sql.addSql("   CTM_ADATE,");
            sql.addSql("   CTM_EUID,");
            sql.addSql("   CTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID >= ?");
            sql.addIntValue(CTM_SID_ORIGINAL_FIRST__);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnThemeFromRs(rs));
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            sql = null;

            //Classicテーマのテーマ情報を取得
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CTM_SID,");
            sql.addSql("   CTM_ID,");
            sql.addSql("   CTM_NAME,");
            sql.addSql("   CTM_PATH,");
            sql.addSql("   CTM_PATH_IMG,");
            sql.addSql("   CTM_AUID,");
            sql.addSql("   CTM_ADATE,");
            sql.addSql("   CTM_EUID,");
            sql.addSql("   CTM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID <= ?");
            sql.addIntValue(CTM_SID_CLASSIC_LAST__);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnThemeFromRs(rs));
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
     * <p>Delete CMN_THEME
     * @param ctmSid CTM_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int ctmSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where ");
            sql.addSql("   CTM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ctmSid);

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
     * <p>Create CMN_THEME Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnThemeModel
     * @throws SQLException SQL実行例外
     */
    private CmnThemeModel __getCmnThemeFromRs(ResultSet rs) throws SQLException {
        CmnThemeModel bean = new CmnThemeModel();
        bean.setCtmSid(rs.getInt("CTM_SID"));
        bean.setCtmId(rs.getString("CTM_ID"));
        bean.setCtmName(rs.getString("CTM_NAME"));
        bean.setCtmPath(rs.getString("CTM_PATH"));
        bean.setCtmPathImg(rs.getString("CTM_PATH_IMG"));
        bean.setCtmAuid(rs.getInt("CTM_AUID"));
        bean.setCtmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CTM_ADATE")));
        bean.setCtmEuid(rs.getInt("CTM_EUID"));
        bean.setCtmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CTM_EDATE")));
        return bean;
    }
}
