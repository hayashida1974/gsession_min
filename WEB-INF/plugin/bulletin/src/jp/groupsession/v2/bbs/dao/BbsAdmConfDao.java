package jp.groupsession.v2.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;

/**
 * <p>BBS_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsAdmConfDao(Connection con) {
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
            sql.addSql("drop table BBS_ADM_CONF");

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
            sql.addSql(" create table BBS_ADM_CONF (");
            sql.addSql("   BAC_ATDEL_FLG NUMBER(10,0) not null,");
            sql.addSql("   BAC_ATDEL_Y NUMBER(10,0),");
            sql.addSql("   BAC_ATDEL_M NUMBER(10,0),");
            sql.addSql("   BAC_AUID NUMBER(10,0) not null,");
            sql.addSql("   BAC_ADATE varchar(23) not null,");
            sql.addSql("   BAC_EUID NUMBER(10,0) not null,");
            sql.addSql("   BAC_EDATE varchar(23) not null,");
            sql.addSql("   BAC_SML_NTF NUMBER(10,0) not null,");
            sql.addSql("   BAC_SML_NTF_KBN NUMBER(10,0),");
            sql.addSql("   BAC_INI_POST_TYPE NUMBER(10,0) not null,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN NUMBER(10,0) not null");
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
     * <p>Insert BBS_ADM_CONF Data Bindding JavaBean
     * @param bean BBS_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_ADM_CONF(");
            sql.addSql("   BAC_ATDEL_FLG,");
            sql.addSql("   BAC_ATDEL_Y,");
            sql.addSql("   BAC_ATDEL_M,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE,");
            sql.addSql("   BAC_SML_NTF,");
            sql.addSql("   BAC_SML_NTF_KBN,");
            sql.addSql("   BAC_INI_POST_TYPE,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacAtdelFlg());
            sql.addIntValue(bean.getBacAtdelY());
            sql.addIntValue(bean.getBacAtdelM());
            sql.addIntValue(bean.getBacAuid());
            sql.addDateValue(bean.getBacAdate());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());
            sql.addIntValue(bean.getBacSmlNtf());
            sql.addIntValue(bean.getBacSmlNtfKbn());
            sql.addIntValue(bean.getBacIniPostType());
            sql.addIntValue(bean.getBacIniPostTypeKbn());
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
     * <p>Update BBS_ADM_CONF Data Bindding JavaBean
     * @param bean BBS_ADM_CONF Data Bindding JavaBean
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int update(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_ATDEL_FLG=?,");
            sql.addSql("   BAC_ATDEL_Y=?,");
            sql.addSql("   BAC_ATDEL_M=?,");
            sql.addSql("   BAC_AUID=?,");
            sql.addSql("   BAC_ADATE=?,");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?,");
            sql.addSql("   BAC_SML_NTF=?,");
            sql.addSql("   BAC_SML_NTF_KBN=?,");
            sql.addSql("   BAC_INI_POST_TYPE=?,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacAtdelFlg());
            sql.addIntValue(bean.getBacAtdelY());
            sql.addIntValue(bean.getBacAtdelM());
            sql.addIntValue(bean.getBacAuid());
            sql.addDateValue(bean.getBacAdate());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());
            sql.addIntValue(bean.getBacSmlNtf());
            sql.addIntValue(bean.getBacSmlNtfKbn());
            sql.addIntValue(bean.getBacIniPostType());
            sql.addIntValue(bean.getBacIniPostTypeKbn());

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
     * <p>自動削除設定をアップデートする
     * @param bean BBS_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAutoDelete(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_ATDEL_FLG=?,");
            sql.addSql("   BAC_ATDEL_Y=?,");
            sql.addSql("   BAC_ATDEL_M=?,");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacAtdelFlg());
            sql.addIntValue(bean.getBacAtdelY());
            sql.addIntValue(bean.getBacAtdelM());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());

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
     * <p>初期値設定をアップデートする
     * @param bean BBS_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateInitSetting(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN=?,");
            sql.addSql("   BAC_INI_POST_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());
            sql.addIntValue(bean.getBacIniPostTypeKbn());
            sql.addIntValue(bean.getBacIniPostType());

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
     * <p>ショートメール通知設定をアップデートする
     * @param bean BBS_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSmailSetting(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   BAC_EUID=?,");
            sql.addSql("   BAC_EDATE=?,");
            sql.addSql("   BAC_SML_NTF=?,");
            sql.addSql("   BAC_SML_NTF_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBacEuid());
            sql.addDateValue(bean.getBacEdate());
            sql.addIntValue(bean.getBacSmlNtf());
            sql.addIntValue(bean.getBacSmlNtfKbn());

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
     * <p>Select BBS_ADM_CONF All Data
     * @return List in BBS_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public BbsAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BAC_ATDEL_FLG,");
            sql.addSql("   BAC_ATDEL_Y,");
            sql.addSql("   BAC_ATDEL_M,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE,");
            sql.addSql("   BAC_SML_NTF,");
            sql.addSql("   BAC_SML_NTF_KBN,");
            sql.addSql("   BAC_INI_POST_TYPE,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN");
            sql.addSql(" from ");
            sql.addSql("   BBS_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsAdmConfFromRs(rs);
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
     * <p>Select BBS_ADM_CONF
     * @param bean BBS_ADM_CONF Model
     * @return BBS_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public BbsAdmConfModel select(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BAC_ATDEL_FLG,");
            sql.addSql("   BAC_ATDEL_Y,");
            sql.addSql("   BAC_ATDEL_M,");
            sql.addSql("   BAC_AUID,");
            sql.addSql("   BAC_ADATE,");
            sql.addSql("   BAC_EUID,");
            sql.addSql("   BAC_EDATE,");
            sql.addSql("   BAC_SML_NTF,");
            sql.addSql("   BAC_SML_NTF_KBN,");
            sql.addSql("   BAC_INI_POST_TYPE,");
            sql.addSql("   BAC_INI_POST_TYPE_KBN");
            sql.addSql(" from");
            sql.addSql("   BBS_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsAdmConfFromRs(rs);
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
     * <p>Delete BBS_ADM_CONF
     * @param bean BBS_ADM_CONF Model
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(BbsAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_ADM_CONF");

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
     * <p>Create BBS_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private BbsAdmConfModel __getBbsAdmConfFromRs(ResultSet rs) throws SQLException {
        BbsAdmConfModel bean = new BbsAdmConfModel();
        bean.setBacAtdelFlg(rs.getInt("BAC_ATDEL_FLG"));
        bean.setBacAtdelY(rs.getInt("BAC_ATDEL_Y"));
        bean.setBacAtdelM(rs.getInt("BAC_ATDEL_M"));
        bean.setBacAuid(rs.getInt("BAC_AUID"));
        bean.setBacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BAC_ADATE")));
        bean.setBacEuid(rs.getInt("BAC_EUID"));
        bean.setBacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BAC_EDATE")));
        bean.setBacSmlNtf(rs.getInt("BAC_SML_NTF"));
        bean.setBacSmlNtfKbn(rs.getInt("BAC_SML_NTF_KBN"));
        bean.setBacIniPostType(rs.getInt("BAC_INI_POST_TYPE"));
        bean.setBacIniPostTypeKbn(rs.getInt("BAC_INI_POST_TYPE_KBN"));
        return bean;
    }
}
