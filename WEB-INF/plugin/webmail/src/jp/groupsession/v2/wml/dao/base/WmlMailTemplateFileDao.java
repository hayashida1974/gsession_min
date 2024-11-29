package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateFileModel;

/**
 * <p>WML_MAIL_TEMPLATE_FILE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailTemplateFileDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailTemplateFileDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailTemplateFileDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailTemplateFileDao(Connection con) {
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
            sql.addSql("drop table WML_MAIL_TEMPLATE_FILE");

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
            sql.addSql(" create table WML_MAIL_TEMPLATE_FILE (");
            sql.addSql("   WTP_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID Date not null,");
            sql.addSql("   primary key (WTP_SID,BIN_SID)");
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
     * <p>Insert WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean
     * @param bean WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailTemplateFileModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_TEMPLATE_FILE(");
            sql.addSql("   WTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWtpSid());
            sql.addLongValue(bean.getBinSid());
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
     * <p>Update WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean
     * @param bean WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailTemplateFileModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getWtpSid());
            sql.addLongValue(bean.getBinSid());

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
     * <p>Select WML_MAIL_TEMPLATE_FILE All Data
     * @return List in WML_MAIL_TEMPLATE_FILEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailTemplateFileModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailTemplateFileModel> ret = new ArrayList<WmlMailTemplateFileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailTemplateFileFromRs(rs));
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
     * <p>Select WML_MAIL_TEMPLATE_FILE Data
     * @param wtpSid WTP_SID
     * @return List in WML_MAIL_TEMPLATE_FILEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailTemplateFileModel> select(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailTemplateFileModel> ret = new ArrayList<WmlMailTemplateFileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID = ?");
            sql.addIntValue(wtpSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailTemplateFileFromRs(rs));
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
     * <p>Select WML_MAIL_TEMPLATE_FILE
     * @param wtpSid WTP_SID
     * @param binSid BIN_SID
     * @return WML_MAIL_TEMPLATE_FILEModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailTemplateFileModel select(int wtpSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailTemplateFileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wtpSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailTemplateFileFromRs(rs);
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
     * <br>[機  能] 指定したメールテンプレートのバイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSid メールテンプレートSID
     * @return バイナリSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getBinSid(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> binSidList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");
            sql.addIntValue(wtpSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                binSidList.add(rs.getString("BIN_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return (String[]) binSidList.toArray(new String[binSidList.size()]);
    }

    /**
     * <br>[機  能] 指定したアカウントが使用可能なバイナリID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メールテンプレートに付属するバイナリID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getTemplateBinSidList(int wacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_MAIL_TEMPLATE.WTP_SID as WTP_SID,");
            sql.addSql("   WML_MAIL_TEMPLATE.WAC_SID as WAC_SID,");
            sql.addSql("   WML_MAIL_TEMPLATE.WTP_TYPE as WTP_TYPE,");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE.BIN_SID as BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" left join ");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" on WML_MAIL_TEMPLATE.WTP_SID = WML_MAIL_TEMPLATE_FILE.WTP_SID ");
            sql.addSql(" where ");
            sql.addSql("   WML_MAIL_TEMPLATE.WTP_TYPE = ?");
            sql.addSql(" or ");
            sql.addSql("   (");
            sql.addSql("     WML_MAIL_TEMPLATE.WTP_TYPE = ?");
            sql.addSql("   and ");
            sql.addSql("     WML_MAIL_TEMPLATE.WAC_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstWebmail.WTP_TYPE_COMMON);
            sql.addIntValue(GSConstWebmail.WTP_TYPE_ACCOUNT);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Long.valueOf(rs.getLong("BIN_SID")));
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
     * <p>Delete WML_MAIL_TEMPLATE_FILE
     * @param wtpSid WTP_SID
     * @param binSid BIN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wtpSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wtpSid);
            sql.addLongValue(binSid);

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
     * <p>Delete WML_MAIL_TEMPLATE_FILE
     * @param wtpSid WTP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wtpSid);

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
     * <br>[機  能] メールテンプレートに関連するバイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSid メールテンプレートSID
     * @param userSid 更新者SID
     * @param date 更新日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeTemplateBinData(int wtpSid, int userSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from WML_MAIL_TEMPLATE_FILE");
            sql.addSql("     where WTP_SID = ? ");
            sql.addSql("   )");
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(wtpSid);

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
     * <br>[機  能] 指定したメールテンプレート情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSidList メールテンプレートSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Integer> wtpSidList) throws SQLException {

        if (wtpSidList == null || wtpSidList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE.WTP_SID in (");

            for (int idx = 0; idx < wtpSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(wtpSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <p>Create WML_MAIL_TEMPLATE_FILE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailTemplateFileModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailTemplateFileModel __getWmlMailTemplateFileFromRs(ResultSet rs)
    throws SQLException {
        WmlMailTemplateFileModel bean = new WmlMailTemplateFileModel();
        bean.setWtpSid(rs.getInt("WTP_SID"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        return bean;
    }
}
