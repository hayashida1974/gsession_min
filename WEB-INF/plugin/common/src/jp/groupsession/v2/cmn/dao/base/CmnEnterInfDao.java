package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_ENTER_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CmnEnterInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnEnterInfDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnEnterInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnEnterInfDao(Connection con) {
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
            sql.addSql("drop table CMN_ENTER_INF");

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
            sql.addSql(" create table CMN_ENTER_INF (");
            sql.addSql("   ENI_NAME varchar(50),");
            sql.addSql("   ENI_NAME_KN varchar(100),");
            sql.addSql("   ENI_KISYU NUMBER(10,0) not null,");
            sql.addSql("   ENI_URL varchar(100),");
            sql.addSql("   ENI_BIKO varchar(1000),");
            sql.addSql("   BIN_SID bigint,");
            sql.addSql("   MENU_BIN_SID bigint,");
            sql.addSql("   ENI_IMG_KBN integer not null,");
            sql.addSql("   ENI_MENU_IMG_KBN integer not null,");
            sql.addSql("   ENI_AUID NUMBER(10,0) not null,");
            sql.addSql("   ENI_ADATE varchar(23) not null,");
            sql.addSql("   ENI_EUID NUMBER(10,0) not null,");
            sql.addSql("   ENI_EDATE varchar(23) not null");
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
     * <p>Insert CMN_ENTER_INF Data Bindding JavaBean
     * @param bean CMN_ENTER_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnEnterInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_ENTER_INF(");
            sql.addSql("   ENI_NAME,");
            sql.addSql("   ENI_NAME_KN,");
            sql.addSql("   ENI_KISYU,");
            sql.addSql("   ENI_URL,");
            sql.addSql("   ENI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   MENU_BIN_SID,");
            sql.addSql("   ENI_IMG_KBN,");
            sql.addSql("   ENI_MENU_IMG_KBN,");
            sql.addSql("   ENI_AUID,");
            sql.addSql("   ENI_ADATE,");
            sql.addSql("   ENI_EUID,");
            sql.addSql("   ENI_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getEniName());
            sql.addStrValue(bean.getEniNameKn());
            sql.addIntValue(bean.getEniKisyu());
            sql.addStrValue(bean.getEniUrl());
            sql.addStrValue(bean.getEniBiko());
            sql.addLongValue(bean.getBinSid());
            sql.addLongValue(bean.getMenuBinSid());
            sql.addIntValue(bean.getEniImgKbn());
            sql.addIntValue(bean.getEniMenuImgKbn());
            sql.addIntValue(bean.getEniAuid());
            sql.addDateValue(bean.getEniAdate());
            sql.addIntValue(bean.getEniEuid());
            sql.addDateValue(bean.getEniEdate());
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
     * <p>企業情報を新規登録する
     * @param bean CMN_ENTER_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertEnterData(CmnEnterInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_ENTER_INF(");
            sql.addSql("   ENI_NAME,");
            sql.addSql("   ENI_NAME_KN,");
            sql.addSql("   ENI_KISYU,");
            sql.addSql("   ENI_URL,");
            sql.addSql("   ENI_BIKO,");
            if (bean.getEniImgKbn() == 1) {
                //ログイン画面 ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   BIN_SID,");
            }

            if (bean.getEniMenuImgKbn() == 1) {
                //メニュー ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   MENU_BIN_SID,");
            }

            sql.addSql("   ENI_IMG_KBN,");
            sql.addSql("   ENI_MENU_IMG_KBN,");
            sql.addSql("   ENI_AUID,");
            sql.addSql("   ENI_ADATE,");
            sql.addSql("   ENI_EUID,");
            sql.addSql("   ENI_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");

            if (bean.getEniImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   ?,");
            }

            if (bean.getEniMenuImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   ?,");
            }

            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getEniName());
            sql.addStrValue(bean.getEniNameKn());
            sql.addIntValue(bean.getEniKisyu());
            sql.addStrValue(bean.getEniUrl());
            sql.addStrValue(bean.getEniBiko());

            if (bean.getEniImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addLongValue(bean.getBinSid());
            }

            if (bean.getEniMenuImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addLongValue(bean.getMenuBinSid());
            }

            sql.addIntValue(bean.getEniImgKbn());
            sql.addIntValue(bean.getEniMenuImgKbn());
            sql.addIntValue(bean.getEniAuid());
            sql.addDateValue(bean.getEniAdate());
            sql.addIntValue(bean.getEniEuid());
            sql.addDateValue(bean.getEniEdate());
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
     * <p>Update CMN_ENTER_INF Data Bindding JavaBean
     * @param bean CMN_ENTER_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnEnterInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_ENTER_INF");
            sql.addSql(" set ");
            sql.addSql("   ENI_NAME=?,");
            sql.addSql("   ENI_NAME_KN=?,");
            sql.addSql("   ENI_KISYU=?,");
            sql.addSql("   ENI_URL=?,");
            sql.addSql("   ENI_BIKO=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   MENU_BIN_SID=?,");
            sql.addSql("   ENI_IMG_KBN=?,");
            sql.addSql("   ENI_MENU_IMG_KBN=?,");
            sql.addSql("   ENI_EUID=?,");
            sql.addSql("   ENI_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getEniName());
            sql.addStrValue(bean.getEniNameKn());
            sql.addIntValue(bean.getEniKisyu());
            sql.addStrValue(bean.getEniUrl());
            sql.addStrValue(bean.getEniBiko());
            sql.addLongValue(bean.getBinSid());
            sql.addLongValue(bean.getMenuBinSid());
            sql.addIntValue(bean.getEniImgKbn());
            sql.addIntValue(bean.getEniMenuImgKbn());
            sql.addIntValue(bean.getEniEuid());
            sql.addDateValue(bean.getEniEdate());

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
     * <p>企業情報を編集する
     * @param bean CMN_ENTER_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateEnterData(CmnEnterInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_ENTER_INF");
            sql.addSql(" set ");
            sql.addSql("   ENI_NAME=?,");
            sql.addSql("   ENI_NAME_KN=?,");
            sql.addSql("   ENI_KISYU=?,");
            sql.addSql("   ENI_URL=?,");
            sql.addSql("   ENI_BIKO=?,");
            if (bean.getEniImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   BIN_SID=?,");
            }

            if (bean.getEniMenuImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addSql("   MENU_BIN_SID=?,");
            }

            sql.addSql("   ENI_IMG_KBN=?,");
            sql.addSql("   ENI_MENU_IMG_KBN=?,");
            sql.addSql("   ENI_EUID=?,");
            sql.addSql("   ENI_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getEniName());
            sql.addStrValue(bean.getEniNameKn());
            sql.addIntValue(bean.getEniKisyu());
            sql.addStrValue(bean.getEniUrl());
            sql.addStrValue(bean.getEniBiko());

            if (bean.getEniImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addLongValue(bean.getBinSid());
            }

            if (bean.getEniMenuImgKbn() == 1) {
                //ロゴ表示区分がデフォルト時は更新しない
                sql.addLongValue(bean.getMenuBinSid());
            }

            sql.addIntValue(bean.getEniImgKbn());
            sql.addIntValue(bean.getEniMenuImgKbn());
            sql.addIntValue(bean.getEniEuid());
            sql.addDateValue(bean.getEniEdate());

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
     * <p>Select CMN_ENTER_INF All Data
     * @return List in CMN_ENTER_INFModel
     * @throws SQLException SQL実行例外
     */
    public CmnEnterInfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnEnterInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENI_NAME,");
            sql.addSql("   ENI_NAME_KN,");
            sql.addSql("   ENI_KISYU,");
            sql.addSql("   ENI_URL,");
            sql.addSql("   ENI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   MENU_BIN_SID,");
            sql.addSql("   ENI_IMG_KBN,");
            sql.addSql("   ENI_MENU_IMG_KBN,");
            sql.addSql("   ENI_AUID,");
            sql.addSql("   ENI_ADATE,");
            sql.addSql("   ENI_EUID,");
            sql.addSql("   ENI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CMN_ENTER_INF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnEnterInfFromRs(rs);
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
     * <p>ロゴバイナリSIDから企業情報モデルを返却します
     * @param logoBinSid ロゴバイナリSID
     * @return CmnEnterInfModel
     * @throws SQLException SQL実行例外
     */
    public CmnEnterInfModel getEntInfo(Long logoBinSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnEnterInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ENI_NAME,");
            sql.addSql("   ENI_NAME_KN,");
            sql.addSql("   ENI_KISYU,");
            sql.addSql("   ENI_URL,");
            sql.addSql("   ENI_BIKO,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   MENU_BIN_SID,");
            sql.addSql("   ENI_IMG_KBN,");
            sql.addSql("   ENI_MENU_IMG_KBN,");
            sql.addSql("   ENI_AUID,");
            sql.addSql("   ENI_ADATE,");
            sql.addSql("   ENI_EUID,");
            sql.addSql("   ENI_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_ENTER_INF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" and ");
            //オリジナルロゴ表示フラグON
            sql.addSql("   ENI_IMG_KBN=1");

            sql.addLongValue(logoBinSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnEnterInfFromRs(rs);
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
     * <br>[機  能] メニューのロゴ画像かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @throws SQLException SQL実行例外
     * @return true : メニューロゴ画像
     */
    public boolean isCheckMenuImage(Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_ENTER_INF");
            sql.addSql(" where ");
            sql.addSql("   MENU_BIN_SID=?");
            sql.addSql(" and ");
            //オリジナルロゴ表示フラグON
            sql.addSql("   ENI_MENU_IMG_KBN=1");

            sql.addLongValue(binSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <p>Create CMN_ENTER_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnEnterInfModel
     * @throws SQLException SQL実行例外
     */
    private CmnEnterInfModel __getCmnEnterInfFromRs(ResultSet rs) throws SQLException {
        CmnEnterInfModel bean = new CmnEnterInfModel();
        bean.setEniName(rs.getString("ENI_NAME"));
        bean.setEniNameKn(rs.getString("ENI_NAME_KN"));
        bean.setEniKisyu(rs.getInt("ENI_KISYU"));
        bean.setEniUrl(rs.getString("ENI_URL"));
        bean.setEniBiko(rs.getString("ENI_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setMenuBinSid(rs.getLong("MENU_BIN_SID"));
        bean.setEniImgKbn(rs.getInt("ENI_IMG_KBN"));
        bean.setEniMenuImgKbn(rs.getInt("ENI_MENU_IMG_KBN"));
        bean.setEniAuid(rs.getInt("ENI_AUID"));
        bean.setEniAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ENI_ADATE")));
        bean.setEniEuid(rs.getInt("ENI_EUID"));
        bean.setEniEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ENI_EDATE")));
        return bean;
    }
}
