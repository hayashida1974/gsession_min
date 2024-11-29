package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_FILE_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnFileConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnFileConfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnFileConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnFileConfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_FILE_CONF Data Bindding JavaBean
     * @param bean CMN_FILE_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnFileConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_FILE_CONF(");
            sql.addSql("   FIC_MAX_SIZE,");
            sql.addSql("   FIC_AUID,");
            sql.addSql("   FIC_ADATE,");
            sql.addSql("   FIC_EUID,");
            sql.addSql("   FIC_EDATE,");
            sql.addSql("   FIC_PHOTO_SIZE");
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
            sql.addIntValue(bean.getFicMaxSize());
            sql.addIntValue(bean.getFicAuid());
            sql.addDateValue(bean.getFicAdate());
            sql.addIntValue(bean.getFicEuid());
            sql.addDateValue(bean.getFicEdate());
            sql.addStrValue(bean.getFicPhotoSize());
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
     * <p>Update CMN_FILE_CONF Data Bindding JavaBean
     * @param bean CMN_FILE_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnFileConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_FILE_CONF");
            sql.addSql(" set ");
            sql.addSql("   FIC_MAX_SIZE=?,");
            sql.addSql("   FIC_EUID=?,");
            sql.addSql("   FIC_EDATE=?,");
            sql.addSql("   FIC_PHOTO_SIZE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFicMaxSize());
            sql.addIntValue(bean.getFicEuid());
            sql.addDateValue(bean.getFicEdate());
            sql.addStrValue(bean.getFicPhotoSize());

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
     * <p>Select CMN_FILE_CONF
     * @return CMN_FILE_CONFModel
     * @throws SQLException SQL実行例外
     */
    public CmnFileConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnFileConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FIC_MAX_SIZE,");
            sql.addSql("   FIC_AUID,");
            sql.addSql("   FIC_ADATE,");
            sql.addSql("   FIC_EUID,");
            sql.addSql("   FIC_EDATE,");
            sql.addSql("   FIC_PHOTO_SIZE");
            sql.addSql(" from");
            sql.addSql("   CMN_FILE_CONF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnFileConfFromRs(rs);
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
     * <p>ファイル管理用のファイルサイズ制限を取得する
     * @return int ファイル最大サイズ(MB)
     * @throws SQLException SQL実行例外
     */
    public int getFileKanriFileSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FAC_FILE_SIZE");
            sql.addSql(" from ");
            sql.addSql("   FILE_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("FAC_FILE_SIZE");
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
     * <p>Delete CMN_FILE_CONF
     * @param bean CMN_FILE_CONF Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnFileConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_FILE_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create CMN_FILE_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnFileConfModel
     * @throws SQLException SQL実行例外
     */
    private CmnFileConfModel __getCmnFileConfFromRs(ResultSet rs) throws SQLException {
        CmnFileConfModel bean = new CmnFileConfModel();
        bean.setFicMaxSize(rs.getInt("FIC_MAX_SIZE"));
        bean.setFicAuid(rs.getInt("FIC_AUID"));
        bean.setFicAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FIC_ADATE")));
        bean.setFicEuid(rs.getInt("FIC_EUID"));
        bean.setFicEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FIC_EDATE")));
        bean.setFicPhotoSize(rs.getString("FIC_PHOTO_SIZE"));
        return bean;
    }
}
