package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_CONTM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnContmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnContmDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnContmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnContmDao(Connection con) {
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
            sql.addSql("drop table CMN_CONTM");

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
            sql.addSql(" create table CMN_CONTM (");
            sql.addSql("   CNT_PXY_USE NUMBER(10,0),");
            sql.addSql("   CNT_PXY_URL varchar(200),");
            sql.addSql("   CNT_PXY_PORT NUMBER(10,0),");
            sql.addSql("   CNT_SESSION_TIME NUMBER(10,0),");
            sql.addSql("   CNT_MENU_STATIC NUMBER(10,0),");
            sql.addSql("   CNT_ID_SAVE NUMBER(10,0),");
            sql.addSql("   CNT_PASS_SAVE NUMBER(10,0),");
            sql.addSql("   CNT_GS_UID varchar(50),");
            sql.addSql("   CNT_PXY_AUTH NUMBER(10,0),");
            sql.addSql("   CNT_PXY_AUTH_ID varchar(256),");
            sql.addSql("   CNT_PXY_AUTH_PASS varchar(1200),");
            sql.addSql("   CNT_PXY_ADRKBN NUMBER(10,0)");
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
     * <p>Insert CMN_CONTM Data Bindding JavaBean
     * @param bean CMN_CONTM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_CONTM(");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_SESSION_TIME,");
            sql.addSql("   CNT_MENU_STATIC,");
            sql.addSql("   CNT_ID_SAVE,");
            sql.addSql("   CNT_PASS_SAVE,");
            sql.addSql("   CNT_GS_UID,");
            sql.addSql("   CNT_PXY_AUTH,");
            sql.addSql("   CNT_PXY_AUTH_ID,");
            sql.addSql("   CNT_PXY_AUTH_PASS,");
            sql.addSql("   CNT_PXY_ADRKBN");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCntPxyUse());
            sql.addStrValue(bean.getCntPxyUrl());
            sql.addIntValue(bean.getCntPxyPort());
            sql.addIntValue(bean.getCntSessionTime());
            sql.addIntValue(bean.getCntMenuStatic());
            sql.addIntValue(bean.getCntIdSave());
            sql.addIntValue(bean.getCntPassSave());
            sql.addStrValue(bean.getCntGsUid());
            sql.addIntValue(bean.getCntPxyAuth());
            sql.addStrValue(bean.getCntPxyAuthId());
            sql.addStrValue(bean.getCntPxyAuthPass());
            sql.addIntValue(bean.getCntPxyAdrkbn());
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
     * <br>[機  能] プロキシ情報登録
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録情報
     * @throws SQLException SQL実行例外
     */
    public void insertProxyData(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_CONTM(");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_PXY_AUTH,");
            sql.addSql("   CNT_PXY_AUTH_ID,");
            sql.addSql("   CNT_PXY_AUTH_PASS,");
            sql.addSql("   CNT_PXY_ADRKBN");
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
            sql.addIntValue(bean.getCntPxyUse());
            sql.addStrValue(bean.getCntPxyUrl());
            sql.addIntValue(bean.getCntPxyPort());
            sql.addIntValue(bean.getCntPxyAuth());
            sql.addStrValue(bean.getCntPxyAuthId());
            sql.addStrValue(bean.getCntPxyAuthPass());
            sql.addIntValue(bean.getCntPxyAdrkbn());
            log__.info(sql.toLogString());

            pstmt.setInt(1, bean.getCntPxyUse());
            pstmt.setString(2, bean.getCntPxyUrl());
            if (bean.getCntPxyPort() == -1) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, bean.getCntPxyPort());
            }

            pstmt.setInt(6, bean.getCntPxyAuth());
            pstmt.setString(7, bean.getCntPxyAuthId());
            pstmt.setString(8, bean.getCntPxyAuthPass());
            pstmt.setInt(9, bean.getCntPxyAdrkbn());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CMN_CONTM Data Bindding JavaBean
     * @param bean CMN_CONTM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_PXY_USE=?,");
            sql.addSql("   CNT_PXY_URL=?,");
            sql.addSql("   CNT_PXY_PORT=?,");
            sql.addSql("   CNT_SESSION_TIME=?,");
            sql.addSql("   CNT_MENU_STATIC=?,");
            sql.addSql("   CNT_ID_SAVE=?,");
            sql.addSql("   CNT_PASS_SAVE=?,");
            sql.addSql("   CNT_GS_UID=?,");
            sql.addSql("   CNT_PXY_AUTH=?,");
            sql.addSql("   CNT_PXY_AUTH_ID=?,");
            sql.addSql("   CNT_PXY_AUTH_PASS=?,");
            sql.addSql("   CNT_PXY_ADRKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCntPxyUse());
            sql.addStrValue(bean.getCntPxyUrl());
            sql.addIntValue(bean.getCntPxyPort());
            sql.addIntValue(bean.getCntSessionTime());
            sql.addIntValue(bean.getCntMenuStatic());
            sql.addIntValue(bean.getCntIdSave());
            sql.addIntValue(bean.getCntPassSave());
            sql.addStrValue(bean.getCntGsUid());
            sql.addIntValue(bean.getCntPxyAuth());
            sql.addStrValue(bean.getCntPxyAuthId());
            sql.addStrValue(bean.getCntPxyAuthPass());
            sql.addIntValue(bean.getCntPxyAdrkbn());

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
     * <br>[機  能] プロキシ情報更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新情報
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateProxyData(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_PXY_USE=?,");
            sql.addSql("   CNT_PXY_URL=?,");
            sql.addSql("   CNT_PXY_PORT=?,");
            sql.addSql("   CNT_PXY_AUTH=?,");
            sql.addSql("   CNT_PXY_AUTH_ID=?,");
            sql.addSql("   CNT_PXY_AUTH_PASS=?,");
            sql.addSql("   CNT_PXY_ADRKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCntPxyUse());
            sql.addStrValue(bean.getCntPxyUrl());
            sql.addIntValue(bean.getCntPxyPort());
            sql.addIntValue(bean.getCntPxyAuth());
            sql.addStrValue(bean.getCntPxyAuthId());
            sql.addStrValue(bean.getCntPxyAuthPass());
            sql.addIntValue(bean.getCntPxyAdrkbn());
            log__.info(sql.toLogString());

            pstmt.setInt(1, bean.getCntPxyUse());
            pstmt.setString(2, bean.getCntPxyUrl());
            if (bean.getCntPxyPort() == -1) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, bean.getCntPxyPort());
            }
            pstmt.setInt(4, bean.getCntPxyAuth());
            pstmt.setString(5, bean.getCntPxyAuthId());
            pstmt.setString(6, bean.getCntPxyAuthPass());
            pstmt.setInt(7, bean.getCntPxyAdrkbn());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] メニュー表示固定区分の更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param kbn メニュー固定区分
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMenuStatic(int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_MENU_STATIC=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(kbn);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] セッション保持時間の更新
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionTime セッション保持時間
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSessionTime(int sessionTime) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_SESSION_TIME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionTime);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] モバイルID・パスワード保存区分の更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param idSaveKbn モバイルID保存区分
     * @param passSaveKbn モバイルパスワード保存区分
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateIdPassKbn(int idSaveKbn, int passSaveKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_ID_SAVE = ?,");
            sql.addSql("   CNT_PASS_SAVE = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(idSaveKbn);
            sql.addIntValue(passSaveKbn);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] モバイルID・パスワード保存区分の更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param uid GSUID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateGsUid(String uid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_CONTM");
            sql.addSql(" set ");
            sql.addSql("   CNT_GS_UID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(uid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select CMN_CONTM All Data
     * @return ret CmnContmModel
     * @throws SQLException SQL実行例外
     */
    public CmnContmModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnContmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_SESSION_TIME,");
            sql.addSql("   CNT_MENU_STATIC,");
            sql.addSql("   CNT_ID_SAVE,");
            sql.addSql("   CNT_PASS_SAVE,");
            sql.addSql("   CNT_GS_UID,");
            sql.addSql("   CNT_PXY_AUTH,");
            sql.addSql("   CNT_PXY_AUTH_ID,");
            sql.addSql("   CNT_PXY_AUTH_PASS,");
            sql.addSql("   CNT_PXY_ADRKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnContmFromRs(rs);
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
     * <p>Select CMN_CONTM
     * @param bean CMN_CONTM Model
     * @return CMN_CONTMModel
     * @throws SQLException SQL実行例外
     */
    public CmnContmModel select(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnContmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_PXY_USE,");
            sql.addSql("   CNT_PXY_URL,");
            sql.addSql("   CNT_PXY_PORT,");
            sql.addSql("   CNT_SESSION_TIME,");
            sql.addSql("   CNT_MENU_STATIC,");
            sql.addSql("   CNT_ID_SAVE,");
            sql.addSql("   CNT_PASS_SAVE,");
            sql.addSql("   CNT_GS_UID,");
            sql.addSql("   CNT_PXY_AUTH,");
            sql.addSql("   CNT_PXY_AUTH_ID,");
            sql.addSql("   CNT_PXY_AUTH_PASS,");
            sql.addSql("   CNT_PXY_ADRKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnContmFromRs(rs);
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
     * <p>メニュー表示固定区分を取得する
     * @return メニュー表示固定区分
     * @throws SQLException SQL実行例外
     */
    public int getMenuStatic() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int menuStatic = GSConstMain.MENU_STATIC_NOT_USE;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_MENU_STATIC");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                menuStatic = rs.getInt("CNT_MENU_STATIC");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return menuStatic;
    }
    /**
     * <p>セッション保持時間を取得する
     * @return セッション保持時間 コントロールマスタが登録されていない場合は-1を返す
     * @throws SQLException SQL実行例外
     */
    public int getSessionTime() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sessionTime = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_SESSION_TIME");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sessionTime = rs.getInt("CNT_SESSION_TIME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sessionTime;
    }
    /**
     * <p>Select CMN_CONTM GSUID
     * @return ret String
     * @throws SQLException SQL実行例外
     */
    public String getGsUid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CNT_GS_UID");
            sql.addSql(" from ");
            sql.addSql("   CMN_CONTM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("CNT_GS_UID");
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
     * <p>Delete CMN_CONTM
     * @param bean CMN_CONTM Model
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(CmnContmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_CONTM");

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
     * <p>Create CMN_CONTM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnContmModel
     * @throws SQLException SQL実行例外
     */
    private CmnContmModel __getCmnContmFromRs(ResultSet rs) throws SQLException {
        CmnContmModel bean = new CmnContmModel();
        bean.setCntPxyUse(rs.getInt("CNT_PXY_USE"));
        bean.setCntPxyUrl(rs.getString("CNT_PXY_URL"));
        bean.setCntPxyPort(rs.getInt("CNT_PXY_PORT"));
        bean.setCntSessionTime(rs.getInt("CNT_SESSION_TIME"));
        bean.setCntMenuStatic(rs.getInt("CNT_MENU_STATIC"));
        bean.setCntIdSave(rs.getInt("CNT_ID_SAVE"));
        bean.setCntPassSave(rs.getInt("CNT_PASS_SAVE"));
        bean.setCntGsUid(rs.getString("CNT_GS_UID"));
        bean.setCntPxyAuth(rs.getInt("CNT_PXY_AUTH"));
        bean.setCntPxyAuthId(rs.getString("CNT_PXY_AUTH_ID"));
        bean.setCntPxyAuthPass(rs.getString("CNT_PXY_AUTH_PASS"));
        bean.setCntPxyAdrkbn(rs.getInt("CNT_PXY_ADRKBN"));
        return bean;
    }
}