package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_USR_PLUGIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnUsrPluginDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnUsrPluginDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsrPluginDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsrPluginDao(Connection con) {
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
            sql.addSql("drop table CMN_USR_PLUGIN");

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
            sql.addSql(" create table CMN_USR_PLUGIN (");
            sql.addSql("   CUP_PID varchar(10) not null,");
            sql.addSql("   CUP_NAME varchar(10) not null,");
            sql.addSql("   CUP_URL varchar(1000) not null,");
            sql.addSql("   CUP_VIEW NUMBER(10,0) not null,");
            sql.addSql("   CUP_TARGET NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   CUP_PARAM_KBN NUMBER(10,0) not null,");
            sql.addSql("   CUP_SEND_KBN NUMBER(10,0) not null,");
            sql.addSql("   primary key (CUP_PID)");
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
     * <p>Insert CMN_USR_PLUGIN Data Bindding JavaBean
     * @param bean CMN_USR_PLUGIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnUsrPluginModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_USR_PLUGIN(");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CUP_NAME,");
            sql.addSql("   CUP_URL,");
            sql.addSql("   CUP_VIEW,");
            sql.addSql("   CUP_TARGET,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUP_PARAM_KBN,");
            sql.addSql("   CUP_SEND_KBN");
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
            sql.addStrValue(bean.getCupPid());
            sql.addStrValue(bean.getCupName());
            sql.addStrValue(bean.getCupUrl());
            sql.addIntValue(bean.getCupView());
            sql.addIntValue(bean.getCupTarget());
            sql.addLongValue(bean.getBinSid());

            sql.addIntValue(bean.getCupParamKbn());
            sql.addIntValue(bean.getCupSendKbn());

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
     * <p>Update CMN_USR_PLUGIN Data Bindding JavaBean
     * @param bean CMN_USR_PLUGIN Data Bindding JavaBean
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int update(CmnUsrPluginModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_USR_PLUGIN");
            sql.addSql(" set ");
            sql.addSql("   CUP_NAME=?,");
            sql.addSql("   CUP_URL=?,");
            sql.addSql("   CUP_VIEW=?,");
            sql.addSql("   CUP_TARGET=?,");
            sql.addSql("   BIN_SID=?,");

            sql.addSql("   CUP_PARAM_KBN=?,");
            sql.addSql("   CUP_SEND_KBN=?");

            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCupName());
            sql.addStrValue(bean.getCupUrl());
            sql.addIntValue(bean.getCupView());
            sql.addIntValue(bean.getCupTarget());
            sql.addLongValue(bean.getBinSid());

            sql.addIntValue(bean.getCupParamKbn());
            sql.addIntValue(bean.getCupSendKbn());

            //where
            sql.addStrValue(bean.getCupPid());

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
     * <p>Select CMN_USR_PLUGIN All Data
     * @return List in CMN_USR_PLUGINModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrPluginModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrPluginModel> ret = new ArrayList<CmnUsrPluginModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CUP_NAME,");
            sql.addSql("   CUP_URL,");
            sql.addSql("   CUP_VIEW,");
            sql.addSql("   CUP_TARGET,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUP_PARAM_KBN,");
            sql.addSql("   CUP_SEND_KBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USR_PLUGIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnUserPluginFromRs(rs));
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
     * <p>Select CMN_USR_PLUGIN
     * @param cupPid CUP_PID
     * @return CMN_USR_PLUGINModel
     * @throws SQLException SQL実行例外
     */
    public CmnUsrPluginModel select(String cupPid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrPluginModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_PID,");
            sql.addSql("   CUP_NAME,");
            sql.addSql("   CUP_URL,");
            sql.addSql("   CUP_VIEW,");
            sql.addSql("   CUP_TARGET,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUP_PARAM_KBN,");
            sql.addSql("   CUP_SEND_KBN");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_PLUGIN");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnUserPluginFromRs(rs);
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
     * <p>指定されたプラグインのバイナリSIDかチェックする。
     * @param cupPid プラグインID
     * @param binSid バイナリSID
     * @return CMN_USR_PLUGINModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckPluginImage(String cupPid, Long binSid) throws SQLException {

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
            sql.addSql("   CMN_USR_PLUGIN");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <p>Delete CMN_USR_PLUGIN
     * @param cupPid CUP_PID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(String cupPid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_USR_PLUGIN");
            sql.addSql(" where ");
            sql.addSql("   CUP_PID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cupPid);

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
     * <p>Create CMN_USR_PLUGIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUserPluginModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrPluginModel __getCmnUserPluginFromRs(ResultSet rs) throws SQLException {
        CmnUsrPluginModel bean = new CmnUsrPluginModel();
        bean.setCupPid(rs.getString("CUP_PID"));
        bean.setCupName(rs.getString("CUP_NAME"));
        bean.setCupUrl(rs.getString("CUP_URL"));
        bean.setCupView(rs.getInt("CUP_VIEW"));
        bean.setCupTarget(rs.getInt("CUP_TARGET"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        bean.setCupParamKbn(rs.getInt("CUP_PARAM_KBN"));
        bean.setCupSendKbn(rs.getInt("CUP_SEND_KBN"));
        return bean;
    }


    /**
     * プラグインアイコン情報を更新する
     * @param funcId 機能名
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateIconFlg(String funcId)  throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("  CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("   select");
            sql.addSql("     CMN_USR_PLUGIN.BIN_SID");
            sql.addSql("   from");
            sql.addSql("     CMN_USR_PLUGIN");
            sql.addSql("   where");
            sql.addSql("     CMN_USR_PLUGIN.CUP_PID = ?");
            sql.addSql("   )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addStrValue(funcId);

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
}
