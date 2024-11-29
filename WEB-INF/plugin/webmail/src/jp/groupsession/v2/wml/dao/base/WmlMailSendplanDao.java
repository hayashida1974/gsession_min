package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlMailSendplanModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_MAIL_SENDPLAN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailSendplanDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailSendplanDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailSendplanDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailSendplanDao(Connection con) {
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
            sql.addSql("drop table WML_MAIL_SENDPLAN");

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
            sql.addSql(" create table WML_MAIL_SENDPLAN (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WSP_SENDKBN NUMBER(10,0) not null,");
            sql.addSql("   WSP_SENDDATE varchar(23) not null,");
            sql.addSql("   WSP_MAILTYPE NUMBER(10,0) not null,");
            sql.addSql("   WSP_COMPRESS_FILE NUMBER(10,0),");
            sql.addSql("   primary key (WMD_MAILNUM)");
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
     * <p>Insert WML_MAIL_SENDPLAN Data Bindding JavaBean
     * @param bean WML_MAIL_SENDPLAN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailSendplanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_SENDPLAN(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSP_SENDKBN,");
            sql.addSql("   WSP_SENDDATE,");
            sql.addSql("   WSP_MAILTYPE,");
            sql.addSql("   WSP_COMPRESS_FILE");
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
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWspSendkbn());
            sql.addDateValue(bean.getWspSenddate());
            sql.addIntValue(bean.getWspMailtype());
            sql.addIntValue(bean.getWspCompressFile());
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
     * <p>Update WML_MAIL_SENDPLAN Data Bindding JavaBean
     * @param bean WML_MAIL_SENDPLAN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailSendplanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql(" set ");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WSP_SENDKBN=?,");
            sql.addSql("   WSP_SENDDATE=?,");
            sql.addSql("   WSP_MAILTYPE=?,");
            sql.addSql("   WSP_COMPRESS_FILE=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getWspSendkbn());
            sql.addDateValue(bean.getWspSenddate());
            sql.addIntValue(bean.getWspMailtype());
            sql.addIntValue(bean.getWspCompressFile());
            //where
            sql.addLongValue(bean.getWmdMailnum());

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
     * <br>[機  能] 指定したメールの送信区分を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @param sendKbn 送信区分
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateSendKbn(long[] mailNumList, int sendKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql(" set ");
            sql.addSql("   WSP_SENDKBN=?");
            sql.addIntValue(sendKbn);

            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");
            for (int mailIdx = 0; mailIdx < mailNumList.length; mailIdx++) {
                if (mailIdx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[mailIdx]);
            }
            sql.addSql("   )");

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
     * <p>Select WML_MAIL_SENDPLAN All Data
     * @return List in WML_MAIL_SENDPLANModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailSendplanModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMailSendplanModel> ret = new ArrayList<WmlMailSendplanModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSP_SENDKBN,");
            sql.addSql("   WSP_SENDDATE,");
            sql.addSql("   WSP_MAILTYPE,");
            sql.addSql("   WSP_COMPRESS_FILE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_SENDPLAN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailSendplanFromRs(rs));
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
     * <p>Select WML_MAIL_SENDPLAN
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_MAIL_SENDPLANModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailSendplanModel select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailSendplanModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WSP_SENDKBN,");
            sql.addSql("   WSP_SENDDATE,");
            sql.addSql("   WSP_MAILTYPE,");
            sql.addSql("   WSP_COMPRESS_FILE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailSendplanFromRs(rs);
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
     * <p>Delete WML_MAIL_SENDPLAN
     * @param wmdMailnum WMD_MAILNUM
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

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
     * <br>[機  能] 送信対象(後から送信する)メールの送信予定情報を取得する
     * <br>[解  説] 送信予定日時が現在日時以前のメールを対象とする
     * <br>[備  考]
     * @return 送信対象メールの送信予定情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMailSendplanModel> getSendMessagePlanList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMailSendplanModel> ret = new ArrayList<WmlMailSendplanModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_MAIL_SENDPLAN.WMD_MAILNUM as WMD_MAILNUM,");
            sql.addSql("   WML_MAIL_SENDPLAN.WAC_SID as WAC_SID,");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDKBN as WSP_SENDKBN,");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDDATE as WSP_SENDDATE,");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_MAILTYPE as WSP_MAILTYPE,");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_COMPRESS_FILE as WSP_COMPRESS_FILE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_SENDPLAN,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDKBN = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDDATE <= ?");
            sql.addIntValue(GSConstWebmail.WSP_SENDKBN_SENDTARGET);
            sql.addDateValue(new UDate());

            //削除済みのアカウントは除外する
            sql.addSql(" and");
            sql.addSql("   WML_MAIL_SENDPLAN.WAC_SID = WML_ACCOUNT.WAC_SID");
            sql.addSql(" and");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN <> ?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_DELETE);

            //未送信(送信待ち)ディレクトリ内のメールのみを対象とする
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     select WML_DIRECTORY.WDR_TYPE from WML_MAILDATA,WML_DIRECTORY");
            sql.addSql("     where WML_MAIL_SENDPLAN.WMD_MAILNUM = WML_MAILDATA.WMD_MAILNUM");
            sql.addSql("     and WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");
            sql.addSql("   ) = ? ");
            sql.addIntValue(GSConstWebmail.DIR_TYPE_NOSEND);

            sql.addSql(" order by");
            sql.addSql("   WML_MAIL_SENDPLAN.WSP_SENDDATE asc");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlMailSendplanFromRs(rs));
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
     * <p>Create WML_MAIL_SENDPLAN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailSendplanModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailSendplanModel __getWmlMailSendplanFromRs(ResultSet rs) throws SQLException {
        WmlMailSendplanModel bean = new WmlMailSendplanModel();
        bean.setWmdMailnum(rs.getLong("WMD_MAILNUM"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWspSendkbn(rs.getInt("WSP_SENDKBN"));
        bean.setWspSenddate(UDate.getInstanceTimestamp(rs.getTimestamp("WSP_SENDDATE")));
        bean.setWspMailtype(rs.getInt("WSP_MAILTYPE"));
        bean.setWspCompressFile(rs.getInt("WSP_COMPRESS_FILE"));
        return bean;
    }
}
