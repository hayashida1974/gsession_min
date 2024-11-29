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
import jp.groupsession.v2.sml.model.SmlUserModel;

/**
 * <p>SML_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlUserDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlUserDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ショートメール個人設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SML_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertSmlUser(SmlUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   SML_MAX_DSP,");
            sql.addSql("   SML_RELOAD,");
            sql.addSql("   SML_AUID,");
            sql.addSql("   SML_ADATE,");
            sql.addSql("   SML_EUID,");
            sql.addSql("   SML_EDATE,");
            sql.addSql("   SML_PHOTO_DSP,");
            sql.addSql("   SML_MAIN_KBN ,");
            sql.addSql("   SML_MAIN_CNT ,");
            sql.addSql("   SML_MAIN_SORT,");
            sql.addSql("   SML_TEMP_DSP");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSmlMaxDsp());
            sql.addIntValue(bean.getSmlReload());
            sql.addIntValue(bean.getSmlAuid());
            sql.addDateValue(bean.getSmlAdate());
            sql.addIntValue(bean.getSmlEuid());
            sql.addDateValue(bean.getSmlEdate());
            sql.addIntValue(bean.getSmlPhotoDsp());
            sql.addIntValue(bean.getSmlMainKbn());
            sql.addIntValue(bean.getSmlMainCnt());
            sql.addIntValue(bean.getSmlMainSort());
            sql.addIntValue(bean.getSmlTempDsp());

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
     * <p>Update SML_USER Data Bindding JavaBean
     * @param bean SML_USER Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(SmlUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_USER");
            sql.addSql(" set ");
            sql.addSql("   SML_MAX_DSP=?,");
            sql.addSql("   SML_RELOAD=?,");
            sql.addSql("   SML_EUID=?,");
            sql.addSql("   SML_EDATE=?,");
            sql.addSql("   SML_TEMP_DSP=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlMaxDsp());
            sql.addIntValue(bean.getSmlReload());
            sql.addIntValue(bean.getSmlEuid());
            sql.addDateValue(bean.getSmlEdate());
            sql.addIntValue(bean.getSmlTempDsp());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * 表示件数の更新を行う
     * @param bean SML_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDspCount(SmlUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_USER");
            sql.addSql(" set ");
            sql.addSql("   SML_MAX_DSP=?,");
            sql.addSql("   SML_EUID=?,");
            sql.addSql("   SML_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlMaxDsp());
            sql.addIntValue(bean.getSmlEuid());
            sql.addDateValue(bean.getSmlEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * メイン表示設定の更新を行う
     * @param bean SML_USER Data Bindding JavaBean
     * @param userSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMainDsp(SmlUserModel bean, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_USER");
            sql.addSql(" set ");
            sql.addSql("   SML_MAIN_KBN=?,");
            sql.addSql("   SML_MAIN_CNT=?,");
            sql.addSql("   SML_MAIN_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlMainKbn());
            sql.addIntValue(bean.getSmlMainCnt());
            sql.addIntValue(bean.getSmlMainSort());
            //where
            sql.addIntValue(userSid);

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
     * 表示件数・リロード時間・写真表示・添付画像表示設定の更新を行う
     * @param bean SML_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDsp(SmlUserModel bean) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_USER");
            sql.addSql(" set ");
            sql.addSql("   SML_MAX_DSP=?,");
            sql.addSql("   SML_RELOAD=?,");
            sql.addSql("   SML_PHOTO_DSP=?,");
            sql.addSql("   SML_TEMP_DSP=?,");
            sql.addSql("   SML_MAIN_KBN=?,");
            sql.addSql("   SML_MAIN_CNT=?,");
            sql.addSql("   SML_MAIN_SORT=?,");
            sql.addSql("   SML_EUID=?,");
            sql.addSql("   SML_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlMaxDsp());
            sql.addIntValue(bean.getSmlReload());
            sql.addIntValue(bean.getSmlPhotoDsp());
            sql.addIntValue(bean.getSmlTempDsp());
            sql.addIntValue(bean.getSmlMainKbn());
            sql.addIntValue(bean.getSmlMainCnt());
            sql.addIntValue(bean.getSmlMainSort());
            sql.addIntValue(bean.getSmlEuid());
            sql.addDateValue(bean.getSmlEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select SML_USER All Data
     * @return List in SML_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlUserModel> ret = new ArrayList<SmlUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   SML_MAX_DSP,");
            sql.addSql("   SML_RELOAD,");
            sql.addSql("   SML_MAIN_KBN,");
            sql.addSql("   SML_MAIN_CNT,");
            sql.addSql("   SML_MAIN_SORT,");
            sql.addSql("   SML_AUID,");
            sql.addSql("   SML_ADATE,");
            sql.addSql("   SML_EUID,");
            sql.addSql("   SML_EDATE,");
            sql.addSql("   SML_PHOTO_DSP,");
            sql.addSql("   SML_TEMP_DSP");
            sql.addSql(" from ");
            sql.addSql("   SML_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlUserFromRs(rs));
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
     * <br>[機  能] ユーザSIDからショートメール個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return SML_USERModel
     * @throws SQLException SQL実行例外
     */
    public SmlUserModel getSmlUserInfo(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SML_MAX_DSP,");
            sql.addSql("   SML_RELOAD,");
            sql.addSql("   SML_AUID,");
            sql.addSql("   SML_ADATE,");
            sql.addSql("   SML_EUID,");
            sql.addSql("   SML_EDATE,");
            sql.addSql("   SML_MAIN_KBN,");
            sql.addSql("   SML_MAIN_CNT,");
            sql.addSql("   SML_MAIN_SORT,");
            sql.addSql("   SML_PHOTO_DSP,");
            sql.addSql("   SML_TEMP_DSP");
            sql.addSql(" from");
            sql.addSql("   SML_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSmlUserFromRs(rs);
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
     * <p>Select SML_USER
     * @param bean SML_USER Model
     * @return SML_USERModel
     * @throws SQLException SQL実行例外
     */
    public SmlUserModel select(SmlUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SML_MAX_DSP,");
            sql.addSql("   SML_RELOAD,");
            sql.addSql("   SML_AUID,");
            sql.addSql("   SML_ADATE,");
            sql.addSql("   SML_EUID,");
            sql.addSql("   SML_EDATE,");
            sql.addSql("   SML_MAIN_KBN,");
            sql.addSql("   SML_MAIN_CNT,");
            sql.addSql("   SML_MAIN_SORT,");
            sql.addSql("   SML_PHOTO_DSP,");
            sql.addSql("   SML_TEMP_DSP");
            sql.addSql(" from");
            sql.addSql("   SML_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlUserFromRs(rs);
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
     * <br>[機  能] ショートメール個人設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteSmlUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(userSid);

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
     * <p>Create SML_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlUserModel
     * @throws SQLException SQL実行例外
     */
    private SmlUserModel __getSmlUserFromRs(ResultSet rs) throws SQLException {
        SmlUserModel bean = new SmlUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSmlMaxDsp(rs.getInt("SML_MAX_DSP"));
        bean.setSmlReload(rs.getInt("SML_RELOAD"));

        bean.setSmlAuid(rs.getInt("SML_AUID"));
        bean.setSmlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SML_ADATE")));
        bean.setSmlEuid(rs.getInt("SML_EUID"));
        bean.setSmlEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SML_EDATE")));
        bean.setSmlMainKbn(rs.getInt("SML_MAIN_KBN"));
        bean.setSmlMainCnt(rs.getInt("SML_MAIN_CNT"));
        bean.setSmlMainSort(rs.getInt("SML_MAIN_SORT"));
        bean.setSmlPhotoDsp(rs.getInt("SML_PHOTO_DSP"));
        bean.setSmlTempDsp(rs.getInt("SML_TEMP_DSP"));
        return bean;
    }
}