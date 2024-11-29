package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.model.SmlAdminModel;

/**
 * <p>SML_ADMIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlAdminDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAdminDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlAdminDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAdminDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SML_ADMIN Data Bindding JavaBean
     * @param bean SML_ADMIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ADMIN(");
            sql.addSql("   SMA_MAILFW,");
            sql.addSql("   SMA_SMTPURL,");
            sql.addSql("   SMA_SMTP_PORT,");
            sql.addSql("   SMA_SMTP_USER,");
            sql.addSql("   SMA_SMTP_PASS,");
            sql.addSql("   SMA_FROM_ADD,");
            sql.addSql("   SMA_FWLMT_KBN,");
            sql.addSql("   SMA_AUID,");
            sql.addSql("   SMA_ADATE,");
            sql.addSql("   SMA_EUID,");
            sql.addSql("   SMA_EDATE,");
            sql.addSql("   SMA_SSL,");
            sql.addSql("   SMA_ACNT_MAKE,");
            sql.addSql("   SMA_AUTO_DEL_KBN,");
            sql.addSql("   SMA_ACNT_USER,");
            sql.addSql("   SMA_MAX_DSP_STYPE,");
            sql.addSql("   SMA_MAX_DSP,");
            sql.addSql("   SMA_RELOAD_STYPE,");
            sql.addSql("   SMA_RELOAD,");
            sql.addSql("   SMA_PHOTO_DSP_STYPE,");
            sql.addSql("   SMA_PHOTO_DSP,");
            sql.addSql("   SMA_ATTACH_DSP_STYPE,");
            sql.addSql("   SMA_ATTACH_DSP,");
            sql.addSql("   SMA_AUTH_TYPE,");
            sql.addSql("   COT_SID");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmaMailfw());
            sql.addStrValue(bean.getSmaSmtpurl());
            sql.addStrValue(bean.getSmaSmtpPort());
            sql.addStrValue(bean.getSmaSmtpUser());
            sql.addStrValue(bean.getSmaSmtpPass());
            sql.addStrValue(bean.getSmaFromAdd());
            sql.addIntValue(bean.getSmaFwlmtKbn());
            sql.addIntValue(bean.getSmaAuid());
            sql.addDateValue(bean.getSmaAdate());
            sql.addIntValue(bean.getSmaEuid());
            sql.addDateValue(bean.getSmaEdate());
            sql.addIntValue(bean.getSmaSsl());
            sql.addIntValue(bean.getSmaAcntMake());
            sql.addIntValue(bean.getSmaAutoDelKbn());
            sql.addIntValue(bean.getSmaAcntUser());
            sql.addIntValue(bean.getSmaMaxDspStype());
            sql.addIntValue(bean.getSmaMaxDsp());
            sql.addIntValue(bean.getSmaReloadDspStype());
            sql.addIntValue(bean.getSmaReloadDsp());
            sql.addIntValue(bean.getSmaPhotoDspStype());
            sql.addIntValue(bean.getSmaPhotoDsp());
            sql.addIntValue(bean.getSmaAttachDspStype());
            sql.addIntValue(bean.getSmaAttachDsp());
            sql.addIntValue(bean.getSmaAuthType());
            sql.addIntValue(bean.getCotSid());
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
     * <p>Update SML_ADMIN Data Bindding JavaBean
     * @param bean SML_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ADMIN");
            sql.addSql(" set ");
            sql.addSql("   SMA_MAILFW=?,");
            sql.addSql("   SMA_SMTPURL=?,");
            sql.addSql("   SMA_SMTP_PORT=?,");
            sql.addSql("   SMA_SMTP_USER=?,");
            sql.addSql("   SMA_SMTP_PASS=?,");
            sql.addSql("   SMA_FROM_ADD=?,");
            sql.addSql("   SMA_FWLMT_KBN=?,");
            sql.addSql("   SMA_EUID=?,");
            sql.addSql("   SMA_EDATE=?,");
            sql.addSql("   SMA_SSL=?,");
            sql.addSql("   SMA_AUTH_TYPE=?,");
            sql.addSql("   COT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmaMailfw());
            sql.addStrValue(bean.getSmaSmtpurl());
            sql.addStrValue(bean.getSmaSmtpPort());
            sql.addStrValue(bean.getSmaSmtpUser());
            sql.addStrValue(bean.getSmaSmtpPass());
            sql.addStrValue(bean.getSmaFromAdd());
            sql.addIntValue(bean.getSmaFwlmtKbn());
            sql.addIntValue(bean.getSmaEuid());
            sql.addDateValue(bean.getSmaEdate());
            sql.addIntValue(bean.getSmaSsl());
            sql.addIntValue(bean.getSmaAuthType());
            sql.addIntValue(bean.getCotSid());

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
     * <p>Update SML_ADMIN Data Bindding JavaBean
     * @param bean SML_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateActSetting(SmlAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ADMIN");
            sql.addSql(" set ");
            sql.addSql("   SMA_ACNT_MAKE=?,");
            sql.addSql("   SMA_AUTO_DEL_KBN=?,");
            sql.addSql("   SMA_ACNT_USER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmaAcntMake());
            sql.addIntValue(bean.getSmaAutoDelKbn());
            sql.addIntValue(bean.getSmaAcntUser());

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
     * <p>Select SML_ADMIN Data
     * @return List in SML_ADMINModel
     * @throws SQLException SQL実行例外
     */
    public SmlAdminModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAdminModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMA_MAILFW,");
            sql.addSql("   SMA_AUTH_TYPE,");
            sql.addSql("   SMA_SMTPURL,");
            sql.addSql("   SMA_SMTP_PORT,");
            sql.addSql("   SMA_SMTP_USER,");
            sql.addSql("   SMA_SMTP_PASS,");
            sql.addSql("   SMA_FROM_ADD,");
            sql.addSql("   SMA_FWLMT_KBN,");
            sql.addSql("   SMA_AUID,");
            sql.addSql("   SMA_ADATE,");
            sql.addSql("   SMA_EUID,");
            sql.addSql("   SMA_EDATE,");
            sql.addSql("   SMA_ACNT_MAKE,");
            sql.addSql("   SMA_SSL,");
            sql.addSql("   SMA_AUTO_DEL_KBN,");
            sql.addSql("   SMA_ACNT_USER,");
            sql.addSql("   SMA_MAX_DSP_STYPE,");
            sql.addSql("   SMA_MAX_DSP,");
            sql.addSql("   SMA_RELOAD_STYPE,");
            sql.addSql("   SMA_RELOAD,");
            sql.addSql("   SMA_PHOTO_DSP_STYPE,");
            sql.addSql("   SMA_PHOTO_DSP,");
            sql.addSql("   SMA_ATTACH_DSP_STYPE,");
            sql.addSql("   SMA_ATTACH_DSP,");
            sql.addSql("   COT_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ADMIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlAdminFromRs(rs);
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
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int selectCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   SML_ADMIN");

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
     * <p>Create SML_ADMIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAdminModel
     * @throws SQLException SQL実行例外
     */
    private SmlAdminModel __getSmlAdminFromRs(ResultSet rs) throws SQLException {
        SmlAdminModel bean = new SmlAdminModel();
        bean.setSmaMailfw(rs.getInt("SMA_MAILFW"));
        bean.setSmaAuthType(rs.getInt("SMA_AUTH_TYPE"));
        bean.setSmaSmtpurl(rs.getString("SMA_SMTPURL"));
        bean.setSmaSmtpPort(rs.getString("SMA_SMTP_PORT"));
        bean.setSmaSmtpUser(rs.getString("SMA_SMTP_USER"));
        bean.setSmaSmtpPass(rs.getString("SMA_SMTP_PASS"));
        bean.setSmaFromAdd(rs.getString("SMA_FROM_ADD"));
        bean.setSmaFwlmtKbn(rs.getInt("SMA_FWLMT_KBN"));
        bean.setSmaAuid(rs.getInt("SMA_AUID"));
        bean.setSmaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMA_ADATE")));
        bean.setSmaEuid(rs.getInt("SMA_EUID"));
        bean.setSmaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMA_EDATE")));
        bean.setSmaSsl(rs.getInt("SMA_SSL"));
        bean.setSmaAcntMake(rs.getInt("SMA_ACNT_MAKE"));
        bean.setSmaAutoDelKbn(rs.getInt("SMA_AUTO_DEL_KBN"));
        bean.setSmaAcntUser(rs.getInt("SMA_ACNT_USER"));
        bean.setSmaMaxDspStype(rs.getInt("SMA_MAX_DSP_STYPE"));
        bean.setSmaMaxDsp(rs.getInt("SMA_MAX_DSP"));
        bean.setSmaReloadDspStype(rs.getInt("SMA_RELOAD_STYPE"));
        bean.setSmaReloadDsp(rs.getInt("SMA_RELOAD"));
        bean.setSmaPhotoDspStype(rs.getInt("SMA_PHOTO_DSP_STYPE"));
        bean.setSmaPhotoDsp(rs.getInt("SMA_PHOTO_DSP"));
        bean.setSmaAttachDspStype(rs.getInt("SMA_ATTACH_DSP_STYPE"));
        bean.setSmaAttachDsp(rs.getInt("SMA_ATTACH_DSP"));
        bean.setCotSid(rs.getInt("COT_SID"));
        return bean;
    }


    /**
     * <p>管理者設定の表示設定を更新する
     * @param bean SML_ADMIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateDisplaySetting(SmlAdminModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ADMIN");
            sql.addSql(" set ");
            sql.addSql("   SMA_MAX_DSP_STYPE=?,");
            sql.addSql("   SMA_MAX_DSP=?,");
            sql.addSql("   SMA_RELOAD_STYPE=?,");
            sql.addSql("   SMA_RELOAD=?,");
            sql.addSql("   SMA_PHOTO_DSP_STYPE=?,");
            sql.addSql("   SMA_PHOTO_DSP=?,");
            sql.addSql("   SMA_ATTACH_DSP_STYPE=?,");
            sql.addSql("   SMA_ATTACH_DSP=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmaMaxDspStype());
            sql.addIntValue(bean.getSmaMaxDsp());
            sql.addIntValue(bean.getSmaReloadDspStype());
            sql.addIntValue(bean.getSmaReloadDsp());
            sql.addIntValue(bean.getSmaPhotoDspStype());
            sql.addIntValue(bean.getSmaPhotoDsp());
            sql.addIntValue(bean.getSmaAttachDspStype());
            sql.addIntValue(bean.getSmaAttachDsp());

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

}
