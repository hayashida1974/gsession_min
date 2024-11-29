package jp.groupsession.v2.wml.dao.base;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.model.mail.WmlMailLabelModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSendAddrModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.wml010.Wml010Const;

/**
 * <p>WML_MAILDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMaildataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMaildataDao.class);

    /** キーワード種別 件名 */
    private static final int KEYWORDTYPE_TITLE__ = 0;
    /** キーワード種別 本文 */
    private static final int KEYWORDTYPE_BODY__ = 1;

    /**
     * <p>Default Constructor
     */
    public WmlMaildataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMaildataDao(Connection con) {
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
            sql.addSql("drop table WML_MAILDATA");

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
     * <p>Insert WML_MAILDATA Data Bindding JavaBean
     * @param bean WML_MAILDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMaildataModel bean) throws SQLException {

        PreparedStatement pstmt = null;

        SqlBuffer sql = new SqlBuffer();
        StringReader reader = null;
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAILDATA(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addStrValue(bean.getWmdTitle());
            sql.addDateValue(bean.getWmdSdate());
            sql.addStrValue(bean.getWmdFrom());
            sql.addIntValue(bean.getWmdTempflg());
            sql.addIntValue(bean.getWmdStatus());
            sql.addIntValue(bean.getWmdReply());
            sql.addIntValue(bean.getWmdForward());
            sql.addIntValue(bean.getWmdReaded());
            sql.addLongValue(bean.getWdrSid());
            sql.addLongValue(bean.getWmdSize());
            sql.addIntValue(bean.getWacSid());
            sql.addDateValue(bean.getWmdAdate());
            sql.addLongValue(bean.getWmdOrigin());
            sql.addIntValue(bean.getWmdEditType());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }

            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <p>Insert WML_MAILDATA Data Bindding JavaBean
     * @param beanList WML_MAILDATA Data List
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlMaildataModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAILDATA(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = getCon().prepareStatement(sql.toSqlString());

            for (WmlMaildataModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addStrValue(bean.getWmdTitle());
                sql.addDateValue(bean.getWmdSdate());
                sql.addStrValue(bean.getWmdFrom());
                sql.addIntValue(bean.getWmdTempflg());
                sql.addIntValue(bean.getWmdStatus());
                sql.addIntValue(bean.getWmdReply());
                sql.addIntValue(bean.getWmdForward());
                sql.addIntValue(bean.getWmdReaded());
                sql.addLongValue(bean.getWdrSid());
                sql.addLongValue(bean.getWmdSize());
                sql.addIntValue(bean.getWacSid());
                sql.addDateValue(bean.getWmdAdate());
                sql.addLongValue(bean.getWmdOrigin());
                sql.addIntValue(bean.getWmdEditType());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {

            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <p>Update WML_MAILDATA Data Bindding JavaBean
     * @param bean WML_MAILDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMaildataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" set ");
            sql.addSql("   WMD_TITLE=?,");
            sql.addSql("   WMD_SDATE=?,");
            sql.addSql("   WMD_FROM=?,");
            sql.addSql("   WMD_TEMPFLG=?,");
            sql.addSql("   WMD_STATUS=?,");
            sql.addSql("   WMD_REPLY=?,");
            sql.addSql("   WMD_FORWARD=?,");
            sql.addSql("   WMD_READED=?,");
            sql.addSql("   WDR_SID=?,");
            sql.addSql("   WMD_SIZE=?,");
            sql.addSql("   WMD_ADATE=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWmdTitle());
            sql.addDateValue(bean.getWmdSdate());
            sql.addStrValue(bean.getWmdFrom());
            sql.addIntValue(bean.getWmdTempflg());
            sql.addIntValue(bean.getWmdStatus());
            sql.addIntValue(bean.getWmdReply());
            sql.addIntValue(bean.getWmdForward());
            sql.addIntValue(bean.getWmdReaded());
            sql.addLongValue(bean.getWdrSid());
            sql.addLongValue(bean.getWmdSize());
            sql.addDateValue(bean.getWmdAdate());
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
     * <br>[機  能] 添付ファイルに関する情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean WML_MAILDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateTempFileData(WmlMaildataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" set ");
            sql.addSql("   WMD_TEMPFLG=?,");
            sql.addSql("   WMD_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWmdTempflg());
            sql.addLongValue(bean.getWmdSize());
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
     * <p>Select WML_MAILDATA All Data
     * @return List in WML_MAILDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataModel> ret = new ArrayList<WmlMaildataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataFromRs(rs));
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
     * <p>Select WML_MAILDATA limit Data
     * @param from 開始
     * @param to 終了
     * @return List in WML_MAILDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> selectPart(
            long from, long to) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataModel> ret = new ArrayList<WmlMaildataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM >= ?");
            sql.addSql(" and");
            sql.addSql("   WMD_MAILNUM <= ?");

            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc");

            sql.addLongValue(from);
            sql.addLongValue(to);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataFromRs(rs));
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
     * <p>ディレクトリSIDを指定してメール一覧を取得します
     * @param dirSid ディレクトリSID
     * @return List in WML_MAILDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> getMailDataOfDir(long dirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataModel> ret = new ArrayList<WmlMaildataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");
            sql.addLongValue(dirSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataFromRs(rs));
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
     * <p>ディレクトリSIDを指定してメール一覧を取得します
     * @param dirSid ディレクトリSID
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in WML_MAILDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> getMailDataOfDirLimit(
            long dirSid, int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMaildataModel> ret = new ArrayList<WmlMaildataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc");

            sql.addLongValue(dirSid);
            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMaildataFromRs(rs));
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
     * <p>Select WML_MAILDATA
     * @param wmdMailnum WMD_MAILNUM
     * @return WML_MAILDATAModel
     * @throws SQLException SQL実行例外
     */
    public WmlMaildataModel select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMaildataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMaildataFromRs(rs);
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
     * <p>Delete WML_MAILDATA
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
            sql.addSql("   WML_MAILDATA");
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
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wmdMailnum メッセージ番号
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int selectMailCnt(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from WML_MAILDATA");
            sql.addSql(" where WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定したディレクトリ内のメールの件数を取得するレコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrSid ディレクトリSID
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int selectMailCntInDir(int wacSid, long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(WAC_SID) as CNT from WML_MAILDATA");
            sql.addSql(" where WAC_SID=?");
            sql.addSql(" and WDR_SID=?");
            sql.addLongValue(wacSid);
            sql.addLongValue(wdrSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 送信日時を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wmdMailnum メッセージ番号
     * @return 送信日時
     * @throws SQLException SQL実行例外
     */
    public UDate getSdate(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate sdate = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_SDATE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sdate = UDate.getInstanceTimestamp(rs.getTimestamp("WMD_SDATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sdate;
    }

    /**
     * <br>[機  能] 指定したメールの件名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return 指定したメールの件名
     * @throws SQLException SQL実行例外
     */
    public List<String> getMailSubject(long[] mailNumList) throws SQLException {

        List<String> mailSubject = new ArrayList<String>();
        if (mailNumList == null || mailNumList.length == 0) {
            return mailSubject;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_TITLE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");

            for (int idx = 0; idx < mailNumList.length; idx++) {
                if (idx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[idx]);
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailSubject.add(rs.getString("WMD_TITLE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailSubject;
    }
    
    /**
     * <br>[機  能] 指定したアカウント，メッセージ番号のメール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WEBメールアカウントSID
     * @param mailNumList メッセージ番号
     * @return メール情報リスト
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> getMailDataList(
            int wacSid, long[] mailNumList) throws SQLException {

        List<WmlMaildataModel> mailList = new ArrayList<WmlMaildataModel>();
        if (mailNumList == null || mailNumList.length == 0) {
            return mailList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);
            sql.addSql(" and ");
            sql.addSql("   WMD_MAILNUM in (");

            for (int idx = 0; idx < mailNumList.length; idx++) {
                if (idx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[idx]);
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailList.add(__getWmlMaildataFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailList;
    }

    /**
     * <br>[機  能] 指定した種別メールの件名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param type メッセージ区分
     * @param wacSid アカウントSID
     * @return 指定したメールの件名
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getKbnMailList(int type, int wacSid) throws SQLException {

        ArrayList<String> mailList = new ArrayList<String>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_TITLE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");
            sql.addSql("   and WML_MAILDATA.WAC_SID = ?");
            sql.addIntValue(wacSid);
            sql.addSql("   and WML_DIRECTORY.WDR_TYPE = ?");
            sql.addIntValue(type);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailList.add(rs.getString("WMD_TITLE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailList;
    }

    /**
     * <p>メールの件数を取得します
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>メールの件数を取得します
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public long maxMailNum() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WMD_MAILNUM) as MAX");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("MAX");
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
     * <br>[機  能] 指定したアカウントの未読メール件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @return アカウント情報
     * @throws SQLException SQL実行時例外
     */
    public long getNotReadCount(int accountSid) throws SQLException {

        long ret = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as NOREAD_COUNT");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID in (");
            sql.addSql("     select WDR_SID from WML_DIRECTORY");
            sql.addSql("     where WML_DIRECTORY.WAC_SID = ?");
            sql.addSql("     and WML_DIRECTORY.WDR_TYPE <> ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   WMD_READED = ?");

            sql.addIntValue(accountSid);
            sql.addIntValue(accountSid);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_DUST);
            sql.addIntValue(GSConstWebmail.READEDFLG_NOREAD);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getLong("NOREAD_COUNT");
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
     * <br>[機  能] 指定したアカウント，メッセージ番号のメール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid WEBメールアカウントSID
     * @param mailNumList メッセージ番号
     * @return メール情報リスト
     * @throws SQLException SQL実行例外
     */
    public List<WmlMaildataModel> getMailDataList(
            int wacSid) throws SQLException {

        List<WmlMaildataModel> mailList = new ArrayList<WmlMaildataModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WMD_SDATE,");
            sql.addSql("   WMD_FROM,");
            sql.addSql("   WMD_TEMPFLG,");
            sql.addSql("   WMD_STATUS,");
            sql.addSql("   WMD_REPLY,");
            sql.addSql("   WMD_FORWARD,");
            sql.addSql("   WMD_READED,");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WMD_SIZE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WMD_ADATE,");
            sql.addSql("   WMD_ORIGIN,");
            sql.addSql("   WMD_EDIT_TYPE");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailList.add(__getWmlMaildataFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailList;
    }

    /**
     * <br>[機  能] 指定したメール情報を「返信済み」に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public void setReply(long mailNum) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_MAILDATA");
            sql.addSql(" set WMD_REPLY = ?");
            sql.addSql(" where WMD_MAILNUM = ?");
            sql.addIntValue(GSConstWebmail.WMD_REPLY_REPLY);
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したメール情報を「転送済み」に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public void setForward(long mailNum) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_MAILDATA");
            sql.addSql(" set WMD_FORWARD = ?");
            sql.addSql(" where WMD_MAILNUM = ?");
            sql.addIntValue(GSConstWebmail.WMD_FORWARD_FORWARD);
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したメールのディレクトリ種別を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return ディレクトリ種別
     * @throws SQLException SQL実行時例外
     */
    public int getDirType(long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int dirType = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE as WDR_TYPE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");

            sql.addLongValue(mailNum);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dirType = rs.getInt("WDR_TYPE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dirType;
    }

    /**
     * <br>[機  能] メール情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param bodyLimitLen メール本文の最大文字数
     * @return メール情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMailResultModel> getMailList(WmlMailSearchModel searchMdl, int bodyLimitLen)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailResultModel> mailList = new ArrayList<WmlMailResultModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MAILDATA.WMD_MAILNUM as WMD_MAILNUM,");
            sql.addSql("   MAILDATA.WMD_TITLE as WMD_TITLE,");
            sql.addSql("   MAILDATA.WMD_SDATE as WMD_SDATE,");
            sql.addSql("   MAILDATA.WMD_FROM as WMD_FROM,");
            sql.addSql("   MAILDATA.WMD_TEMPFLG as WMD_TEMPFLG,");
            sql.addSql("   MAILDATA.WMD_STATUS as WMD_STATUS,");
            sql.addSql("   MAILDATA.WMD_REPLY as WMD_REPLY,");
            sql.addSql("   MAILDATA.WMD_FORWARD as WMD_FORWARD,");
            sql.addSql("   MAILDATA.WMD_READED as WMD_READED,");
            sql.addSql("   MAILDATA.WMD_SIZE as WMD_SIZE,");
            sql.addSql("   MAILDATA.WDR_SID as WDR_SID,");
            sql.addSql("   MAILDATA.WDR_TYPE as WDR_TYPE");

            sql = __setWhereSql(sql, searchMdl);

            String order = "";
            if (searchMdl.getOrder() == GSConstWebmail.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            switch (searchMdl.getSortKey()) {
                case GSConstWebmail.SORTKEY_TEMPFILE:
                    sql.addSql("   MAILDATA.WMD_TEMPFLG" + order);
                    sql.addSql("   ,MAILDATA.WMD_SDATE desc");
                    break;
                case GSConstWebmail.SORTKEY_TITLE:
                    sql.addSql("   MAILDATA.WMD_TITLE" + order);
                    break;
                case GSConstWebmail.SORTKEY_MAILADDRESS:
                    if (WmlBiz.isSendDirType(searchMdl.getDirectoryType())) {
                        sql.addSql("   MAILDATA.WSA_ADDRESS" + order);
                    } else {
                        sql.addSql("   MAILDATA.WMD_FROM" + order);
                    }
                    sql.addSql("   ,MAILDATA.WMD_SDATE desc");
                    break;
                case GSConstWebmail.SORTKEY_SDATE:
                    sql.addSql("   MAILDATA.WMD_SDATE" + order);
                    break;
                case GSConstWebmail.SORTKEY_READED:
                    sql.addSql("   MAILDATA.WMD_READED" + order);
                    sql.addSql("   ,MAILDATA.WMD_SDATE desc");
                    break;
                case GSConstWebmail.SORTKEY_SIZE:
                    sql.addSql("   MAILDATA.WMD_SIZE" + order);
                    sql.addSql("   ,MAILDATA.WMD_SDATE desc");
                    break;
                default:
                    sql.addSql("   MAILDATA.WMD_SDATE");
            }

            sql.setPagingValue(searchMdl.getStart() - 1, searchMdl.getMaxCount());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            WebmailDao wmlDao = new WebmailDao(con);

            while (rs.next()) {
                WmlMailResultModel mailData = new WmlMailResultModel();

                mailData.setMailNum(rs.getLong("WMD_MAILNUM"));
                mailData.setDirSid(rs.getLong("WDR_SID"));
                mailData.setDirType(rs.getInt("WDR_TYPE"));
                Timestamp sdate = rs.getTimestamp("WMD_SDATE");
                if (sdate != null) {
                    mailData.setDate(UDate.getInstanceTimestamp(sdate));
                }
                mailData.setReaded(rs.getInt("WMD_REPLY") == GSConstWebmail.WMD_READED_YES);
                mailData.setFrom(rs.getString("WMD_FROM"));
                mailData.setSubject(StringUtil.replaceReturnCode(rs.getString("WMD_TITLE"), ""));
                mailData.setReaded(rs.getInt("WMD_READED") == GSConstWebmail.READEDFLG_READED);
                mailData.setAttach(rs.getInt("WMD_TEMPFLG") == GSConstWebmail.TEMPFLG_EXIST);
                mailData.setReply(rs.getInt("WMD_REPLY") == GSConstWebmail.WMD_REPLY_REPLY);
                mailData.setForward(rs.getInt("WMD_FORWARD") == GSConstWebmail.WMD_FORWARD_FORWARD);
                mailData.setMailSize(rs.getLong("WMD_SIZE"));

                int wdrType = rs.getInt("WDR_TYPE");
                mailData.setCanEditMail(
                        wdrType == GSConstWebmail.DIR_TYPE_SENDED
                        || wdrType == GSConstWebmail.DIR_TYPE_DRAFT
                        || wdrType == GSConstWebmail.DIR_TYPE_NOSEND);
                mailData.setSendWaitMail(wdrType == GSConstWebmail.DIR_TYPE_NOSEND);

                mailList.add(mailData);
            }

            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);

            for (int index = 0; index < mailList.size(); index++) {
                long mailNum = mailList.get(index).getMailNum();
                int wacSid = searchMdl.getAccountSid();

                //メール本文を取得
                mailList.get(index).setBody(__getMailBody(wacSid, mailNum, bodyLimitLen));

                //ラベル名称を設定
                mailList.get(index).setLabelList(__getLabelData(wacSid, mailNum));

                //送信先メールアドレスを設定
                mailList.get(index).setSendAddress(getSendAddress(wacSid, mailNum));

                //添付ファイル情報を設定
                mailList.get(index).setTempFileList(wmlDao.getTempFileList(mailNum));

                //メール情報_送信予定を設定
                __setSendPlan(mailList.get(index));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailList;
    }

    /**
     * <br>[機  能] メール情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @return メール情報一覧
     * @throws SQLException SQL実行時例外
     */
    public long getMailCount(WmlMailSearchModel searchMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(MAILDATA.WMD_MAILNUM) as CNT");
            sql = __setWhereSql(sql, searchMdl);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getLong("CNT");
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
     * <br>[機  能] 指定したメールの送信先メールアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum メッセージ番号
     * @return 送信先メールアドレス
     * @throws SQLException SQL実行時例外
     */
    public WmlMailSendAddrModel getSendAddress(int wacSid, long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailSendAddrModel addrModel = new WmlMailSendAddrModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WSA_TYPE,");
            sql.addSql("   WSA_NUM,");
            sql.addSql("   WSA_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where");
            if (wacSid > 0) {
                sql.addSql("   WAC_SID  = ?");
                sql.addSql(" and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addSql(" order by");
            sql.addSql("   WSA_NUM");
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int type = rs.getInt("WSA_TYPE");
                String address = rs.getString("WSA_ADDRESS");
                addrModel.addRenban(rs.getInt("WSA_NUM"));
                if (type == GSConstWebmail.WSA_TYPE_TO) {
                    addrModel.addToAddress(address);
                } else if (type == GSConstWebmail.WSA_TYPE_CC) {
                    addrModel.addCcAddress(address);
                } else if (type == GSConstWebmail.WSA_TYPE_BCC) {
                    addrModel.addBccAddress(address);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return addrModel;
    }

    /**
     * <br>[機  能] 検索条件SQLを指定されたSqlBufferへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     * @return SqlBuffer
     */
    private SqlBuffer __setWhereSql(SqlBuffer sql, WmlMailSearchModel searchMdl) {

        sql.addSql(" from");
        sql.addSql("   (");
        if (StringUtil.isNullZeroString(searchMdl.getKeyword())) {
            __setWhereSql(sql, searchMdl, -1);
        } else {
            __setWhereSql(sql, searchMdl, KEYWORDTYPE_TITLE__);
            sql.addSql("  union");
            __setWhereSql(sql, searchMdl, KEYWORDTYPE_BODY__);
        }
        sql.addSql("   ) MAILDATA");
        return sql;
    }

    /**
     * <br>[機  能] 検索条件SQLを指定されたSqlBufferへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     * @param keywordType 検索条件:キーワード の対象(0:件名 1:本文)
     * @return SqlBuffer
     */
    private SqlBuffer __setWhereSql(SqlBuffer sql, WmlMailSearchModel searchMdl,
                                    int keywordType) {
        int wacSid = searchMdl.getAccountSid();
        boolean existAccount = wacSid > 0;


        sql.addSql("    select");
        sql.addSql("      WML_MAILDATA.*,");
        sql.addSql("      WML_DIRECTORY.WDR_TYPE");
        if (WmlBiz.isSendDirType(searchMdl.getDirectoryType())) {
            sql.addSql("      ,SENDADDRESS.WSA_ADDRESS as WSA_ADDRESS");
        }

        sql.addSql("    from");
        sql.addSql("      WML_DIRECTORY,");
        sql.addSql("      WML_MAILDATA");

        if (WmlBiz.isSendDirType(searchMdl.getDirectoryType())) {
            sql.addSql("      left join");
            sql.addSql("        (");
            sql.addSql("          select WMD_MAILNUM, WSA_ADDRESS from WML_SENDADDRESS");
            sql.addSql("          where");
            if (existAccount) {
                sql.addSql("            WAC_SID = ?");
                sql.addSql("          and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("           WSA_NUM = ?");
            sql.addSql("        ) SENDADDRESS");
            sql.addSql("      on");
            sql.addSql("        WML_MAILDATA.WMD_MAILNUM = SENDADDRESS.WMD_MAILNUM");
            sql.addIntValue(1);
        }

        sql.addSql("    where");

        boolean searchFlg = false;

        //メッセージ番号
        if (searchMdl.getMailNum() > 0) {
            sql.addSql("      WML_MAILDATA.WMD_MAILNUM = ?");
            sql.addLongValue(searchMdl.getMailNum());
            searchFlg = true;
        }
        if (searchMdl.getMailNumArray() != null && searchMdl.getMailNumArray().length > 0) {
            if (searchFlg) {
                sql.addSql("    and");
            }
            sql.addSql("      WMD_MAILNUM in (");
            for (int idx = 0; idx < searchMdl.getMailNumArray().length; idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addLongValue(searchMdl.getMailNumArray()[idx]);
            }
            sql.addSql("      )");

            searchFlg = true;
        }

        //ディレクトリ
        if (searchMdl.getDirectorySid() > 0) {
            if (searchFlg) {
                sql.addSql("    and");
            }
            sql.addSql("      WML_DIRECTORY.WDR_SID = ?");
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WDR_SID = ?");
            sql.addLongValue(searchMdl.getDirectorySid());
            sql.addLongValue(searchMdl.getDirectorySid());
            searchFlg = true;
        }

        //アカウント
        if (existAccount) {
            if (searchFlg) {
                sql.addSql("    and");
            }
            sql.addSql("      WML_DIRECTORY.WAC_SID = ?");
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WAC_SID = ?");
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);
            searchFlg = true;
        }

        //メール情報とディレクトリ情報を連結
        if (searchFlg) {
            sql.addSql("    and");
        }
        sql.addSql("      WML_DIRECTORY.WDR_SID = WML_MAILDATA.WDR_SID");

        //ラベル
        if (searchMdl.getLabelSid() > 0) {

            //削除メール(ゴミ箱フォルダ内のメール)を含めるか
            if (searchMdl.getDirectorySid() <= 0) {
                sql.addSql("    and");
                sql.addSql("      WML_DIRECTORY.WDR_TYPE <> ?");
                sql.addIntValue(GSConstWebmail.DIR_TYPE_DUST);
            }

            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_MAILNUM in (");
            sql.addSql("        select WMD_MAILNUM from WML_LABEL_RELATION");
            sql.addSql("        where");
            if (existAccount) {
                sql.addSql("          WAC_SID = ?");
                sql.addSql("        and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("          WLB_SID = ?");
            sql.addIntValue(searchMdl.getLabelSid());

            sql.addSql("      )");
            searchFlg = true;
        }

        //from
        if (!StringUtil.isNullZeroString(searchMdl.getFrom())) {
            sql.addSql("    and");
            sql.addSql("      upper(WML_MAILDATA.WMD_FROM) like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getFrom().toUpperCase())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //to
        if (!StringUtil.isNullZeroString(searchMdl.getDestination())) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_MAILNUM in (");
            sql.addSql("        select WMD_MAILNUM");
            sql.addSql("        from");
            sql.addSql("          (");
            sql.addSql("            select WMD_MAILNUM, WSA_ADDRESS_SRH from WML_SENDADDRESS");
            sql.addSql("            where");
            if (existAccount) {
                sql.addSql("              WML_SENDADDRESS.WAC_SID = ?");
                sql.addSql("            and");
                sql.addIntValue(wacSid);
            }

            //TO, CC, BCC を選択
            boolean destFlg = false;
            sql.addSql("              (");
            if (searchMdl.getDestinationTo()) {
                sql.addSql("                  WML_SENDADDRESS.WSA_TYPE = ?");
                sql.addIntValue(GSConstWebmail.WSA_TYPE_TO);
                destFlg = true;
            }
            if (searchMdl.getDestinationCc()) {
                if (destFlg) {
                    sql.addSql("              or");
                }
                sql.addSql("                  WML_SENDADDRESS.WSA_TYPE = ?");
                sql.addIntValue(GSConstWebmail.WSA_TYPE_CC);
                destFlg = true;
            }
            if (searchMdl.getDestinationBcc()) {
                if (destFlg) {
                    sql.addSql("              or");
                }
                sql.addSql("                  WML_SENDADDRESS.WSA_TYPE = ?");
                sql.addIntValue(GSConstWebmail.WSA_TYPE_BCC);
            }
            sql.addSql("              )");
            sql.addSql("          ) SENDADDRESS");
            sql.addSql("        where");
            sql.addSql("          WSA_ADDRESS_SRH like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getDestination().toLowerCase())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("      )");
        }

        //キーワード
        if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
            if (keywordType == KEYWORDTYPE_TITLE__) {
                sql.addSql("    and");
                sql.addSql("      WML_MAILDATA.WMD_TITLE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");

            } else if (keywordType == KEYWORDTYPE_BODY__) {
                sql.addSql("    and");

                IDbUtil dbUtil = DBUtilFactory.getInstance();
                if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
                    //H2 全文検索の場合のみ
                    sql.addSql("      WML_DIRECTORY.WDR_SID <> -1");

                    if (!StringUtil.isNullZeroString(searchMdl.getKeyword())
                    && keywordType == KEYWORDTYPE_BODY__) {
                        sql.addSql("   and");
                        sql.addSql("     WML_MAILDATA.WMD_MAILNUM in (");
                        sql.addSql("       select FT.KEYS[0] from FTL_SEARCH_DATA('"
                                + JDBCUtil.encFullStringH2Lucene(searchMdl.getKeyword())
                                + "', 0, 0) FT");
                        sql.addSql("       where FT.TABLE = 'WML_MAIL_BODY'");
                        sql.addSql("     )");
                    }

                } else if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES
                        && CommonBiz.isPGroongaUse()) {
                    //PostgreSQL(PGroonga使用)
                    sql.addSql("      exists (");
                    sql.addSql("        select 1 from WML_MAIL_BODY");
                    sql.addSql("        where");
                    sql.addSql("          WML_MAIL_BODY.WMB_BODY &@ '"
                            + JDBCUtil.encFullTextSearch(searchMdl.getKeyword())
                            + "'");
                    sql.addSql("        and");
                    sql.addSql("          WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
                    sql.addSql("        and");
                    sql.addSql("          WML_DIRECTORY.WDR_SID <> -1");
                    sql.addSql("     )");

                } else {
                    //H2,PostgreSQL(PGroonga使用) 以外
                    sql.addSql("      exists (");
                    sql.addSql("        select 1 from WML_MAIL_BODY");
                    sql.addSql("        where");
                    sql.addSql("          WML_MAIL_BODY.WMB_BODY like '%"
                            + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("        and");
                    sql.addSql("          WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
                    sql.addSql("        and");
                    sql.addSql("          WML_DIRECTORY.WDR_SID <> -1");
                    sql.addSql("     )");
                }
            }
        }

        //日付 受信日 From
        if (searchMdl.getResvDateFrom() != null) {
            if (searchMdl.getResvDateTo() == null) {
                sql.addSql("    and");
                sql.addSql("      WML_MAILDATA.WMD_SDATE >= ?");
                sql.addDateValue(searchMdl.getResvDateFrom());
            }
        }

        //日付 受信日 To
        if (searchMdl.getResvDateTo() != null) {
            sql.addSql("    and");
            if (searchMdl.getResvDateFrom() == null) {
                sql.addSql("      WML_MAILDATA.WMD_SDATE <= ?");
                sql.addDateValue(searchMdl.getResvDateTo());
            } else {
                sql.addSql("      WML_MAILDATA.WMD_SDATE between ?");
                sql.addSql("                             and ?");
                sql.addDateValue(searchMdl.getResvDateFrom());
                sql.addDateValue(searchMdl.getResvDateTo());
            }
        }

        //添付ファイル
        if (searchMdl.isTempFile()) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_TEMPFLG = ?");
            sql.addIntValue(GSConstWebmail.TEMPFLG_EXIST);
        }

        //未読/既読
        if (searchMdl.getReadKbn() == Wml010Const.SEARCH_READKBN_NOREAD) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_READED = ?");
            sql.addIntValue(GSConstWebmail.READEDFLG_NOREAD);
        } else if (searchMdl.getReadKbn() == Wml010Const.SEARCH_READKBN_READED) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_READED = ?");
            sql.addIntValue(GSConstWebmail.READEDFLG_READED);
        }

        return sql;
    }

    /**
     * <br>[機  能] 指定したメールの本文を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum メッセージ番号
     * @param bodyLimitLen メール本文の最大文字数
     * @return メールの本文
     * @throws SQLException SQL実行時例外
     */
    private String __getMailBody(int wacSid, long mailNum, int bodyLimitLen) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String body = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            if (bodyLimitLen > 0) {
                sql.addSql("   case");
                sql.addSql("     length(COALESCE(WMB_BODY, '')) > ?");
                sql.addSql("   when true then substr(WMB_BODY, 1, ?)");
                sql.addSql("   else WMB_BODY");
                sql.addSql("   end as BODY");
                sql.addIntValue(bodyLimitLen);
                sql.addIntValue(bodyLimitLen);
            } else {
                sql.addSql("   WMB_BODY as BODY");
            }
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_BODY");

            sql.addSql(" where");
            if (wacSid > 0) {
                sql.addSql("   WAC_SID = ?");
                sql.addSql(" and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                body = rs.getString("BODY");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return body;
    }

    /**
     * <br>[機  能] 指定したメールのラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mailNum メッセージ番号
     * @return ラベル情報
     * @throws SQLException SQL実行時例外
     */
    private List<WmlMailLabelModel> __getLabelData(int wacSid, long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMailLabelModel> labelList = new ArrayList<WmlMailLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_LABEL.WLB_SID as WLB_SID,");
            sql.addSql("   WML_LABEL.WLB_NAME as WLB_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL,");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where");
            if (wacSid > 0) {
                sql.addSql("   WML_LABEL_RELATION.WAC_SID = ?");
                sql.addIntValue(wacSid);
                sql.addSql(" and");
            }
            sql.addSql("   WML_LABEL_RELATION.WMD_MAILNUM = ?");
            sql.addSql(" and");
            sql.addSql("   WML_LABEL.WLB_SID = WML_LABEL_RELATION.WLB_SID");
            sql.addLongValue(mailNum);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WmlMailLabelModel labelModel = new WmlMailLabelModel();
                labelModel.setId(rs.getInt("WLB_SID"));
                labelModel.setName(rs.getString("WLB_NAME"));

                labelList.add(labelModel);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return labelList;
    }

    /**
     * <br>[機  能] 指定したメール情報の「未読/既読」を変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param readType メール未読/既読
     * @throws SQLException SQL実行時例外
     */
    public void changeMailReaded(long[] mailNum, int readType) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_MAILDATA");
            sql.addSql(" set WMD_READED = ?");
            sql.addIntValue(readType);

            sql.addSql(" where WMD_MAILNUM in (");
            sql.addSql("   ?");
            sql.addLongValue(mailNum[0]);
            for (int i = 1; i < mailNum.length; i++) {
                sql.addSql("  ,?");
                sql.addLongValue(mailNum[i]);
            }
            sql.addSql(" )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したメールの送信予定を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailData メール情報情報
     * @throws SQLException SQL実行時例外
     */
    private void __setSendPlan(WmlMailResultModel mailData) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WSP_SENDKBN,");
            sql.addSql("   WSP_SENDDATE,");
            sql.addSql("   WSP_COMPRESS_FILE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_SENDPLAN");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailData.getMailNum());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                mailData.setSendPlanKbn(rs.getInt("WSP_SENDKBN"));
                mailData.setSendPlanDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("WSP_SENDDATE")));
                mailData.setSendPlanCompressFile(rs.getInt("WSP_COMPRESS_FILE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create WML_MAILDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMaildataModel
     * @throws SQLException SQL実行例外
     */
    private WmlMaildataModel __getWmlMaildataFromRs(ResultSet rs) throws SQLException {
        WmlMaildataModel bean = new WmlMaildataModel();
        bean.setWmdMailnum(rs.getLong("WMD_MAILNUM"));
        bean.setWmdTitle(rs.getString("WMD_TITLE"));
        bean.setWmdSdate(UDate.getInstanceTimestamp(rs.getTimestamp("WMD_SDATE")));
        bean.setWmdFrom(rs.getString("WMD_FROM"));
        bean.setWmdTempflg(rs.getInt("WMD_TEMPFLG"));
        bean.setWmdStatus(rs.getInt("WMD_STATUS"));
        bean.setWmdReply(rs.getInt("WMD_REPLY"));
        bean.setWmdForward(rs.getInt("WMD_FORWARD"));
        bean.setWmdReaded(rs.getInt("WMD_READED"));
        bean.setWdrSid(rs.getLong("WDR_SID"));
        bean.setWmdSize(rs.getLong("WMD_SIZE"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWmdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WMD_ADATE")));
        bean.setWmdOrigin(rs.getLong("WMD_ORIGIN"));
        bean.setWmdEditType(rs.getInt("WMD_EDIT_TYPE"));
        return bean;
    }
}
