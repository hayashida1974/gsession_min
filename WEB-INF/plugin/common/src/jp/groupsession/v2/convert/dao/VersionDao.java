package jp.groupsession.v2.convert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.convert.model.VersionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_VER_INFO Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class VersionDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(VersionDao.class);

    /**
     * <p>Default Constructor
     */
    public VersionDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public VersionDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] バージョン情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param version バージョン
     * @return int 登録件数
     * @throws SQLException SQL実行例外
     */
    public int insert(String version) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_VER_INFO(");
            sql.addSql("   VER_VERSION");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addStrValue(version);

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
     * <br>[機  能] バージョン情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param version バージョン
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(String version) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_VER_INFO");
            sql.addSql(" set ");
            sql.addSql("   VER_VERSION=?");

            sql.addStrValue(version);

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
     * <br>[機  能] バージョン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return CMN_VER_INFOModel
     * @throws SQLException SQL実行例外
     */
    public VersionModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        VersionModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   VER_VERSION");
            sql.addSql(" from");
            sql.addSql("   CMN_VER_INFO");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnVerInfoFromRs(rs);
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
     * <p>Create CMN_VER_INFO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnVerInfoModel
     * @throws SQLException SQL実行例外
     */
    private VersionModel __getCmnVerInfoFromRs(ResultSet rs) throws SQLException {
        VersionModel bean = new VersionModel();
        bean.setVerVersion(rs.getString("VER_VERSION"));
        return bean;
    }
}
