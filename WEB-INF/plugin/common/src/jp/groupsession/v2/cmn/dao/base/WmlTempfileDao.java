package jp.groupsession.v2.cmn.dao.base;

import java.io.FileInputStream;
import java.io.IOException;
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
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <p>WML_TEMPFILE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlTempfileDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlTempfileDao.class);
    /** 添付UTIL */
    private ITempFileUtil tempUtil__ = null;

    /**
     * <p>Default Constructor
     */
    public WmlTempfileDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlTempfileDao(Connection con) {
        super(con);
    }

    /**
     * <p>Set Connection
     * @param tempUtil ITempFileUtil
     * @param con Connection
     */
    public WmlTempfileDao(ITempFileUtil tempUtil, Connection con) {
        super(con);
        tempUtil__ = tempUtil;
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
            sql.addSql("drop table WML_TEMPFILE");

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
     * <p>Insert WML_TEMPFILE Data Bindding JavaBean
     * @param bean WML_TEMPFILE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlTempfileModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_TEMPFILE(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
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

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addLongValue(bean.getWtfSid());
            sql.addStrValue(bean.getWtfFileName());
            sql.addStrValue(bean.getWtfFilePath());
            sql.addStrValue(bean.getWtfFileExtension());
            sql.addLongValue(bean.getWtfFileSize());
            sql.addIntValue(bean.getWtfAuid());
            sql.addDateValue(bean.getWtfAdate());
            sql.addIntValue(bean.getWtfEuid());
            sql.addDateValue(bean.getWtfEdate());
            sql.addIntValue(bean.getWtfJkbn());
            sql.addIntValue(bean.getWtfHtmlmail());
            sql.addStrValue(bean.getWtfCharset());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
        }
    }

    /**
     * <br>[機  能] 添付ファイル情報を登録する
     * <br>[解  説] ファイル本体をDBに保存する。
     * <br>[備  考]
     * @param bean WML_TEMPFILE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    public void insertDBOid(WmlTempfileModel bean) throws SQLException, IOException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        FileInputStream fis = null;

        try {
            //SQL文
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_TEMPFILE(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET,");
            sql.addSql("   WTF_FILE_DATA");

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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = getCon().prepareStatement(sql.toSqlString());

//            pstmt.setLong(1, bean.getWmdMailnum());
//            pstmt.setLong(2, bean.getWtfSid());
//            pstmt.setString(3, bean.getWtfFileName());
//            pstmt.setString(4, bean.getWtfFilePath());
//            pstmt.setString(5, bean.getWtfFileExtension());
//            pstmt.setLong(6, bean.getWtfFileSize());
//            pstmt.setInt(7, bean.getWtfAuid());
//            pstmt.setTimestamp(8, JDBCUtil.getTimestamp(bean.getWtfAdate()));
//            pstmt.setInt(9, bean.getWtfEuid());
//            pstmt.setTimestamp(10, JDBCUtil.getTimestamp(bean.getWtfEdate()));
//            pstmt.setInt(11, bean.getWtfJkbn());
//            pstmt.setInt(12, bean.getWtfHtmlmail());
//            pstmt.setString(13, bean.getWtfCharset());

            sql.addLongValue(bean.getWmdMailnum());
            sql.addLongValue(bean.getWtfSid());
            sql.addStrValue(bean.getWtfFileName());
            sql.addStrValue(bean.getWtfFilePath());
            sql.addStrValue(bean.getWtfFileExtension());
            sql.addLongValue(bean.getWtfFileSize());
            sql.addIntValue(bean.getWtfAuid());
            sql.addDateValue(bean.getWtfAdate());
            sql.addIntValue(bean.getWtfEuid());
            sql.addDateValue(bean.getWtfEdate());
            sql.addIntValue(bean.getWtfJkbn());
            sql.addIntValue(bean.getWtfHtmlmail());
            sql.addStrValue(bean.getWtfCharset());
            sql.addLongValue(bean.getWtfFileDataOid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            pstmt = null;
            sql = null;
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * <p>Update WML_TEMPFILE Data Bindding JavaBean
     * @param bean WML_TEMPFILE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlTempfileModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" set ");
            sql.addSql("   WTF_FILE_NAME=?,");
            sql.addSql("   WTF_FILE_PATH=?,");
            sql.addSql("   WTF_FILE_EXTENSION=?,");
            sql.addSql("   WTF_FILE_SIZE=?,");
            sql.addSql("   WTF_AUID=?,");
            sql.addSql("   WTF_ADATE=?,");
            sql.addSql("   WTF_EUID=?,");
            sql.addSql("   WTF_EDATE=?,");
            sql.addSql("   WTF_JKBN=?,");
            sql.addSql("   WTF_HTMLMAIL=?,");
            sql.addSql("   WTF_CHARSET=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWtfFileName());
            sql.addStrValue(bean.getWtfFilePath());
            sql.addStrValue(bean.getWtfFileExtension());
            sql.addLongValue(bean.getWtfFileSize());
            sql.addIntValue(bean.getWtfAuid());
            sql.addDateValue(bean.getWtfAdate());
            sql.addIntValue(bean.getWtfEuid());
            sql.addDateValue(bean.getWtfEdate());
            sql.addIntValue(bean.getWtfJkbn());
            sql.addIntValue(bean.getWtfHtmlmail());
            sql.addStrValue(bean.getWtfCharset());
            //where
            sql.addLongValue(bean.getWmdMailnum());
            sql.addLongValue(bean.getWtfSid());

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
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public void deleteMail(long mailNum) throws SQLException {

        PreparedStatement pstmt = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" set ");
            sql.addSql("   WTF_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addIntValue(GSConstWebmail.TEMPFILE_STATUS_DELETE);
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
     * <p>Select WML_TEMPFILE All Data
     * @return List in WML_TEMPFILEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlTempfileModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlTempfileModel> ret = new ArrayList<WmlTempfileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlTempfileFromRs(rs));
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
     * <p>メッセージ番号を指定して添付情報を取得する
     * @param wmdMailnum メッセージ番号
     * @return List in WML_TEMPFILEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlTempfileModel> getTempFileList(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlTempfileModel> ret = new ArrayList<WmlTempfileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addLongValue(wmdMailnum);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlTempfileFromRs(rs));
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
     * <p>Select WML_TEMPFILE
     * @param wmdMailnum メッセージ番号
     * @param wtfSid WTF_SID
     * @return WML_TEMPFILEModel
     * @throws SQLException SQL実行例外
     */
    public WmlTempfileModel select(long wmdMailnum, long wtfSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlTempfileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and ");
            sql.addSql("   WTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addLongValue(wtfSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlTempfileFromRs(rs);
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
     * <p>Select WML_TEMPFILE
     * @param wtfSid WTF_SID
     * @return WML_TEMPFILEModel
     * @throws SQLException SQL実行例外
     */
    public WmlTempfileModel select(long wtfSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlTempfileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where ");
            sql.addSql("   WTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wtfSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlTempfileFromRs(rs);
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
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説] 添付ファイルをDBに保存する場合使用する。
     * <br>[備  考]
     * @param wmdMailnum メッセージ番号
     * @param wtfSid WTF_SID
     * @param domain ドメイン
     * @return WML_TEMPFILEModel
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel selectDB(long wmdMailnum,
                                     long wtfSid,
                                     String domain)
                                     throws TempFileException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlTempfileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET,");
            sql.addSql("   WTF_FILE_DATA");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where ");
            if (wmdMailnum > 0) {
                sql.addSql("   WMD_MAILNUM=?");
                sql.addLongValue(wmdMailnum);
                sql.addSql(" and ");
            }
            sql.addSql("   WTF_SID=?");
            sql.addLongValue(wtfSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            UDate date = new UDate();
            int fileNum = 0;
            String tempDir = "";
            if (tempUtil__ != null) {
                tempDir = tempUtil__.getTempFilePath(domain);
            }
            if (rs.next()) {
                fileNum++;
                ret = __getWmlTempfileFromRsPlusFile(rs, tempDir, date.getDateString(), fileNum,
                                                                            domain);
            }
        } catch (SQLException e) {
            throw new TempFileException(e.toString());
        } catch (IOException e) {
            throw new TempFileException(e.toString());
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 論理削除されたメール添付ファイルのメッセージ番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getMailNumOfDelTemp() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Long> mailNumList = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where ");
            sql.addSql("   WTF_JKBN=?");
            sql.addIntValue(GSConstWebmail.TEMPFILE_STATUS_DELETE);
            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mailNumList.add(rs.getLong("WMD_MAILNUM"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mailNumList;
    }

    /**
     * <br>[機  能] 指定したメール情報の添付ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return 添付ファイルパス
     * @throws SQLException SQL実行例外
     */
    public List<String> getTempFilePath(long[] mailNum) throws SQLException {

        List<String> filePathList = new ArrayList<String>();
        if (mailNum == null || mailNum.length == 0) {
            return filePathList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = null;
            for (int count = 0; count < mailNum.length; count += 1000) {
                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   WTF_FILE_PATH");
                sql.addSql(" from");
                sql.addSql("   WML_TEMPFILE");
                sql.addSql(" where ");
                sql.addSql("   WMD_MAILNUM in (");
                for (int index = count; index < count + 1000 && index < mailNum.length; index++) {
                    if (index == count) {
                        sql.addSql("     ?");
                    } else {
                        sql.addSql("     ,?");
                    }
                    sql.addLongValue(mailNum[index]);
                }
                sql.addSql("   )");

                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    filePathList.add(rs.getString("WTF_FILE_PATH"));
                }
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return filePathList;
    }

    /**
     * <br>[機  能] 指定したメール情報の添付ファイル バイナリーSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return バイナリーSID
     * @throws SQLException SQL実行例外
     */
    public List<Long> getWtfSid(long[] mailNum) throws SQLException {

        List<Long> wtfSidList = new ArrayList<Long>();
        if (mailNum == null || mailNum.length == 0) {
            return wtfSidList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //SQL文
            SqlBuffer sql = null;
            for (int count = 0; count < mailNum.length; count += 1000) {
                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   WTF_SID");
                sql.addSql(" from");
                sql.addSql("   WML_TEMPFILE");
                sql.addSql(" where ");
                sql.addSql("   WMD_MAILNUM in (");
                for (int index = count; index < count + 1000 && index < mailNum.length; index++) {
                    if (index == count) {
                        sql.addSql("     ?");
                    } else {
                        sql.addSql("     ,?");
                    }
                    sql.addLongValue(mailNum[index]);
                }
                sql.addSql("   )");

                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    wtfSidList.add(rs.getLong("WTF_SID"));
                }
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wtfSidList;
    }

    /**
     * <p>Delete WML_TEMPFILE
     * @param wmdMailnum WMD_MAILNUM
     * @param wtfSid WTF_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, long wtfSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WTF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addLongValue(wtfSid);

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
     * <br>[機  能] メール添付ファイルの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @throws SQLException SQL実行時例外
     */
    public void delete(long[] mailNum) throws SQLException {

        if (mailNum == null || mailNum.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = null;
            for (int count = 0; count < mailNum.length; count += 1000) {
                sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   WML_TEMPFILE");
                sql.addSql(" where ");
                sql.addSql("   WMD_MAILNUM in (");
                for (int index = count; index < count + 1000 && index < mailNum.length; index++) {
                    if (index == count) {
                        sql.addSql("     ?");
                    } else {
                        sql.addSql("     ,?");
                    }
                    sql.addLongValue(mailNum[index]);
                }
                sql.addSql("   )");

                pstmt = getCon().prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                JDBCUtil.closeStatement(pstmt);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param sessionUserSid ユーザSID
     * @param now 現在日時
     * @throws SQLException SQL実行例外
     */
    public void deleteTempFile(long mailNum, int sessionUserSid, UDate now)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   WML_TEMPFILE ");
            sql.addSql(" set ");
            sql.addSql("   WTF_EUID = ?, ");
            sql.addSql("   WTF_EDATE = ?, ");
            sql.addSql("   WTF_JKBN = ? ");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addIntValue(sessionUserSid);
            sql.addDateValue(now);
            sql.addIntValue(GSConstWebmail.WMD_STATUS_DUST);
            sql.addLongValue(mailNum);

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
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param wtfSidList 添付ファイルSID一覧
     * @param sessionUserSid ユーザSID
     * @param now 現在日時
     * @throws SQLException SQL実行例外
     */
    public void deleteTempFile(long mailNum, long[] wtfSidList, int sessionUserSid, UDate now)
        throws SQLException {

        if (wtfSidList == null || wtfSidList.length <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   WML_TEMPFILE ");
            sql.addSql(" set ");
            sql.addSql("   WTF_EUID = ?, ");
            sql.addSql("   WTF_EDATE = ?, ");
            sql.addSql("   WTF_JKBN = ? ");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addIntValue(sessionUserSid);
            sql.addDateValue(now);
            sql.addIntValue(GSConstWebmail.WMD_STATUS_DUST);
            sql.addLongValue(mailNum);

            sql.addSql(" and");
            sql.addSql("   WTF_SID in (");
            for (int fileIdx = 0; fileIdx < wtfSidList.length; fileIdx++) {
                if (fileIdx == 0) {
                    sql.addSql("   ?");
                } else {
                    sql.addSql("   ,?");
                }
                sql.addLongValue(wtfSidList[fileIdx]);
            }
            sql.addSql("   )");

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
     * <br>[機  能] 指定したメール添付ファイルのファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtfSidList バイナリーSID
     * @return ファイルパス
     * @throws SQLException SQL実行時例外
     */
    public List<String> getTempFilePathList(List<Long> wtfSidList)
    throws SQLException {

        List<String> filePathList = new ArrayList<String>();
        if (wtfSidList == null || wtfSidList.isEmpty()) {
            return filePathList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlBuffer sql = null;
        try {
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTF_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where");
            sql.addSql("   WTF_SID in (");
            sql.addSql("     ?");
            sql.addLongValue(wtfSidList.get(0).longValue());

            for (int i = 1; i < wtfSidList.size(); i++) {
                sql.addSql("     ,?");
                sql.addLongValue(wtfSidList.get(i).longValue());
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                filePathList.add(rs.getString("WTF_FILE_PATH"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            rs = null;
            pstmt = null;
            sql = null;
        }

        return filePathList;
    }

    /**
     * <p>Select WML_TEMPFILE All Data
     * @return List in WML_TEMPFILEModel
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
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select WML_TEMPFILE All Data
     * @param offset 取得するレコード位置
     * @param limit 取得する最大件数
     * @return List in WML_TEMPFILEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlTempfileModel> selectLimit(
            long offset, long limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlTempfileModel> ret = new ArrayList<WmlTempfileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WTF_SID,");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_PATH,");
            sql.addSql("   WTF_FILE_EXTENSION,");
            sql.addSql("   WTF_FILE_SIZE,");
            sql.addSql("   WTF_AUID,");
            sql.addSql("   WTF_ADATE,");
            sql.addSql("   WTF_EUID,");
            sql.addSql("   WTF_EDATE,");
            sql.addSql("   WTF_JKBN,");
            sql.addSql("   WTF_HTMLMAIL,");
            sql.addSql("   WTF_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc,");
            sql.addSql("   WTF_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlTempfileFromRs(rs));
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
     * <p>Create WML_TEMPFILE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlTempfileModel
     * @throws SQLException SQL実行例外
     */
    private WmlTempfileModel __getWmlTempfileFromRs(ResultSet rs) throws SQLException {
        WmlTempfileModel bean = new WmlTempfileModel();
        bean.setWmdMailnum(rs.getLong("WMD_MAILNUM"));
        bean.setWtfSid(rs.getLong("WTF_SID"));
        bean.setWtfFileName(rs.getString("WTF_FILE_NAME"));
        bean.setWtfFilePath(rs.getString("WTF_FILE_PATH"));
        bean.setWtfFileExtension(rs.getString("WTF_FILE_EXTENSION"));
        bean.setWtfFileSize(rs.getInt("WTF_FILE_SIZE"));
        bean.setWtfAuid(rs.getInt("WTF_AUID"));
        bean.setWtfAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTF_ADATE")));
        bean.setWtfEuid(rs.getInt("WTF_EUID"));
        bean.setWtfEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTF_EDATE")));
        bean.setWtfJkbn(rs.getInt("WTF_JKBN"));
        bean.setWtfHtmlmail(rs.getInt("WTF_HTMLMAIL"));
        bean.setWtfCharset(rs.getString("WTF_CHARSET"));
        return bean;
    }

    /**
     * <p>Create WML_TEMPFILE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param tempDir 保存先Dir
     * @param dateStr 日付文字列YYYYMMDD
     * @param fileNum ファイルの連番
     * @param domain ドメイン
     * @return created WmlTempfileModel
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    private WmlTempfileModel __getWmlTempfileFromRsPlusFile(
            ResultSet rs, String tempDir, String dateStr, int fileNum,
            String domain)
    throws SQLException, IOException {
        UDate startDate = new UDate();

        WmlTempfileModel bean = new WmlTempfileModel();
        bean.setWmdMailnum(rs.getLong("WMD_MAILNUM"));
        bean.setWtfSid(rs.getLong("WTF_SID"));
        bean.setWtfFileName(rs.getString("WTF_FILE_NAME"));
        bean.setWtfFilePath(rs.getString("WTF_FILE_PATH"));
        bean.setWtfFileExtension(rs.getString("WTF_FILE_EXTENSION"));
        bean.setWtfFileSize(rs.getInt("WTF_FILE_SIZE"));
        bean.setWtfAuid(rs.getInt("WTF_AUID"));
        bean.setWtfAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTF_ADATE")));
        bean.setWtfEuid(rs.getInt("WTF_EUID"));
        bean.setWtfEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTF_EDATE")));
        bean.setWtfJkbn(rs.getInt("WTF_JKBN"));
        bean.setWtfHtmlmail(rs.getInt("WTF_HTMLMAIL"));
        bean.setWtfCharset(rs.getString("WTF_CHARSET"));

        if (tempUtil__ != null) {
            String fileFullPath = Cmn110Biz.getFilePath(
                    tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_SAVEFILE);

            log__.info("添付ファイル一時保存先==>" + fileFullPath);
            bean.setWtfFileData(
                    tempUtil__.readTempFileDataField(
                    rs, "WTF_FILE_DATA",
                    tempDir,
                    fileFullPath,
                    rs.getLong("WTF_FILE_SIZE"),
                    domain));
        } else {
            log__.info("添付ファイル一時保存先==>" + rs.getString("WTF_FILE_PATH"));
        }

        __writeSlowLog(bean, startDate);
        startDate = null;

        return bean;
    }

    /**
     * <br>[機  能] 添付ファイル情報読み込み時の遅延ログを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param binData 添付ファイル情報
     * @param startDate 開始日時
     */
    private void __writeSlowLog(WmlTempfileModel binData, UDate startDate) {
        if (binData != null) {
            UDate endDate = new UDate();
            long time = endDate.getTimeMillis() - startDate.getTimeMillis();
            if (time >= GSConst.DELAY_LIMIT_FILEREAD) {
                log__.warn("---- Warning!!!"
                        + " slow load file(webmail) " + GSConst.DELAY_LIMIT_FILEREAD
                        + " milli second over ("
                        + "time:" + time
                        + " | wtfSid:" + binData.getWtfSid()
                        + " | fileSize:" + binData.getWtfFileSize()
                        + " | fileName:" + binData.getWtfFileName()
                        + " | start:" + UDateUtil.getSlashYYMD(startDate)
                                                + " " + UDateUtil.getSeparateHMS(startDate)
                        + " | end:" + UDateUtil.getSlashYYMD(endDate)
                                                + " " + UDateUtil.getSeparateHMS(endDate)
                        + ") ---------");
            }
            endDate = null;
        }
    }
}
