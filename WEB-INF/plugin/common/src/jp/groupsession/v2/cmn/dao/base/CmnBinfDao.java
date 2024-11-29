package jp.groupsession.v2.cmn.dao.base;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <p>CMN_BINF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBinfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBinfDao.class);

    /** 添付UTIL */
    private ITempFileUtil tempUtil__ = null;

    /**
     * <p>Default Constructor
     */
    public CmnBinfDao() {
    }
    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBinfDao(Connection con) {
        super(con);
    }
    /**
     * <p>Blobからバイナリー情報を取得する場合に使用するコンストラクタ
     * @param tempUtil 添付Util
     * @param con Connection
     */
    public CmnBinfDao(ITempFileUtil tempUtil, Connection con) {
        super(con);
        tempUtil__ = tempUtil;
    }

    /**
     * <br>[機  能] バイナリー情報を登録する
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @param filePath ファイル保存先ディレクトリ
     * @throws SQLException SQL実行例外
     */
    public void insertBinInfo(CmnBinfModel bean, String filePath) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            __setFileData(bean, filePath);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BINF(");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
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

            pstmt.setLong(1, bean.getBinSid());
            pstmt.setString(2, bean.getBinFileName());
            pstmt.setString(3, bean.getBinFilePath());
            pstmt.setString(4, bean.getBinFileExtension());
            pstmt.setLong(5, bean.getBinFileSize());
            pstmt.setInt(6, bean.getBinAduser());
            pstmt.setTimestamp(7, JDBCUtil.getTimestamp(bean.getBinAddate()));
            pstmt.setInt(8, bean.getBinUpuser());
            pstmt.setTimestamp(9, JDBCUtil.getTimestamp(bean.getBinUpdate()));
            pstmt.setInt(10, bean.getBinFilekbn());
            pstmt.setInt(11, bean.getBinJkbn());

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
     * <br>[機  能] バイナリー情報を登録する
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertBinInfo(CmnBinfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BINF(");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
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

            pstmt.setLong(1, bean.getBinSid());
            pstmt.setString(2, bean.getBinFileName());
            pstmt.setString(3, bean.getBinFilePath());
            pstmt.setString(4, bean.getBinFileExtension());
            pstmt.setLong(5, bean.getBinFileSize());
            pstmt.setInt(6, bean.getBinAduser());
            pstmt.setTimestamp(7, JDBCUtil.getTimestamp(bean.getBinAddate()));
            pstmt.setInt(8, bean.getBinUpuser());
            pstmt.setTimestamp(9, JDBCUtil.getTimestamp(bean.getBinUpdate()));
            pstmt.setInt(10, bean.getBinFilekbn());
            pstmt.setInt(11, bean.getBinJkbn());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

//    /**
//     * <br>[機  能] バイナリー情報を登録する
//     * <br>[解  説] ファイル本体をDBに保存する。
//     * <br>[備  考]
//     * @param bean CMN_BINF Data Bindding JavaBean
//     * @throws SQLException SQL実行例外
//     * @throws IOException ファイル操作時例外
//     */
//    public void insertBinInfoDB(CmnBinfModel bean)
//    throws SQLException, IOException {
//
//        PreparedStatement pstmt = null;
//        Connection con = null;
//        con = getCon();
//        FileInputStream fis = null;
//
//        try {
//
//            //ファイル本体をDBに保存する場合
//            __setFileDataDB(bean);
//
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" insert ");
//            sql.addSql(" into ");
//            sql.addSql(" CMN_BINF(");
//            sql.addSql("   BIN_SID,");
//            sql.addSql("   BIN_FILE_NAME,");
//            sql.addSql("   BIN_FILE_PATH,");
//            sql.addSql("   BIN_FILE_EXTENSION,");
//            sql.addSql("   BIN_FILE_SIZE,");
//            sql.addSql("   BIN_ADUSER,");
//            sql.addSql("   BIN_ADDATE,");
//            sql.addSql("   BIN_UPUSER,");
//            sql.addSql("   BIN_UPDATE,");
//            sql.addSql("   BIN_FILEKBN,");
//            sql.addSql("   BIN_JKBN,");
//            sql.addSql("   BIN_FILE_DATA");
//            sql.addSql(" )");
//            sql.addSql(" values");
//            sql.addSql(" (");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   ?,");
//            sql.addSql("   lo_create(0)");
//            sql.addSql(" )");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//
//            sql.addValue(bean.getBinSid());
//            sql.addValue(bean.getBinFileName());
//            sql.addValue(bean.getBinFilePath());
//            sql.addValue(bean.getBinFileExtension());
//            sql.addValue(bean.getBinFileSize());
//            sql.addValue(bean.getBinAduser());
//            sql.addValue(bean.getBinAddate());
//            sql.addValue(bean.getBinUpuser());
//            sql.addValue(bean.getBinUpdate());
//            sql.addValue(bean.getBinFilekbn());
//            sql.addValue(bean.getBinJkbn());
//
//            pstmt.setInt(1, bean.getBinSid());
//            pstmt.setString(2, bean.getBinFileName());
//            pstmt.setString(3, bean.getBinFilePath());
//            pstmt.setString(4, bean.getBinFileExtension());
//            pstmt.setLong(5, bean.getBinFileSize());
//            pstmt.setInt(6, bean.getBinAduser());
//            pstmt.setTimestamp(7, JDBCUtil.getTimestamp(bean.getBinAddate()));
//            pstmt.setInt(8, bean.getBinUpuser());
//            pstmt.setTimestamp(9, JDBCUtil.getTimestamp(bean.getBinUpdate()));
//            pstmt.setInt(10, bean.getBinFilekbn());
//            pstmt.setInt(11, bean.getBinJkbn());
//
//            log__.info(sql.toLogString());
//
//            sql.setParameter(pstmt);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeStatement(pstmt);
//            if (fis != null) {
//                fis.close();
//            }
//        }
//
//    }

    /**
     * <br>[機  能] バイナリー情報を登録する
     * <br>[解  説] ファイル本体をDBに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    public void insertBinInfoDBWithOID(CmnBinfModel bean)
    throws SQLException, IOException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //ファイル本体をDBに保存する場合
            __setFileDataDB(bean);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BINF(");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN,");
            sql.addSql("   BIN_FILE_DATA");
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

            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getBinFileName());
            sql.addStrValue(bean.getBinFilePath());
            sql.addStrValue(bean.getBinFileExtension());
            sql.addLongValue(bean.getBinFileSize());
            sql.addIntValue(bean.getBinAduser());
            sql.addDateValue(bean.getBinAddate());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            sql.addIntValue(bean.getBinFilekbn());
            sql.addIntValue(bean.getBinJkbn());
            sql.addLongValue(bean.getBinFileDataOid());

            pstmt.setLong(1, bean.getBinSid());
            pstmt.setString(2, bean.getBinFileName());
            pstmt.setString(3, bean.getBinFilePath());
            pstmt.setString(4, bean.getBinFileExtension());
            pstmt.setLong(5, bean.getBinFileSize());
            pstmt.setInt(6, bean.getBinAduser());
            pstmt.setTimestamp(7, JDBCUtil.getTimestamp(bean.getBinAddate()));
            pstmt.setInt(8, bean.getBinUpuser());
            pstmt.setTimestamp(9, JDBCUtil.getTimestamp(bean.getBinUpdate()));
            pstmt.setInt(10, bean.getBinFilekbn());
            pstmt.setInt(11, bean.getBinJkbn());
            pstmt.setLong(12, bean.getBinFileDataOid());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

    }

//    /**
//     * <br>[機  能] 添付ファイルを更新する。
//     * <br>[解  説] ファイル本体をDBに保存する場合使用する。
//     * <br>[備  考]
//     * @param bean CMN_BINF Data Bindding JavaBean
//     * @throws SQLException SQL実行例外
//     * @throws IOException ファイル操作時例外
//     */
//    public void updateBinFileData(CmnBinfModel bean)
//    throws SQLException, IOException {
//
//        File file = bean.getBinFileData();
//        if (file == null || !file.exists()) {
//            return;
//        }
//
//        PreparedStatement pstmt = null;
//        Connection con = null;
//        con = getCon();
//        FileInputStream in = null;
//        BufferedOutputStream bos = null;
//        ResultSet rs = null;
//
//        try {
//
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select ");
//            sql.addSql("   BIN_FILE_DATA");
//            sql.addSql(" from ");
//            sql.addSql("   CMN_BINF");
//            sql.addSql(" where ");
//            sql.addSql("   BIN_SID = ?");
//            sql.addSql(" for update");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//
//            sql.addValue(bean.getBinSid());
//
//            log__.info(sql.toLogString());
//
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//
//
//            if (rs.next()) {
//                Blob fileData = rs.getBlob("BIN_FILE_DATA");
//                in = new FileInputStream(file);
//                bos = new BufferedOutputStream(fileData.setBinaryStream(1));
//
//                int len = 0;
//                byte[] buff = new byte[1024];
//                while ((len = in.read(buff)) != -1) {
//                    bos.write(buff, 0, len);
//                }
//                bos.flush();
//                buff = null;
//            }
//
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeStatement(pstmt);
//            if (in != null) {
//                in.close();
//                in = null;
//            }
//            if (bos != null) {
//                bos.close();
//                bos = null;
//            }
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//    }

    /**
     * <p>Update CMN_BINF
     * @param bean CMN_BINF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBinfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID = ?,");
            sql.addSql("   BIN_FILE_NAME = ?,");
            sql.addSql("   BIN_FILE_PATH = ?,");
            sql.addSql("   BIN_FILE_EXTENSION=?,");
            sql.addSql("   BIN_FILE_SIZE=?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getBinFileName());
            sql.addStrValue(bean.getBinFilePath());
            sql.addStrValue(bean.getBinFileExtension());
            sql.addLongValue(bean.getBinFileSize());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            sql.addIntValue(bean.getBinJkbn());
            //where
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] バイナリー情報を更新する
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @param filePath 保存するファイルのパス
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDirBin(CmnBinfModel bean, String filePath) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            __setFileData(bean, filePath);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_FILE_NAME = ?,");
            sql.addSql("   BIN_FILE_PATH = ?,");
            sql.addSql("   BIN_FILE_EXTENSION=?,");
            sql.addSql("   BIN_FILE_SIZE=?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addStrValue(bean.getBinFileName());
            sql.addStrValue(bean.getBinFilePath());
            sql.addStrValue(bean.getBinFileExtension());
            sql.addLongValue(bean.getBinFileSize());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());

            //where
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] バイナリー情報を更新する
     * <br>[解  説] ファイルを本体をDBに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    public int updateDirBinDB(CmnBinfModel bean) throws SQLException, IOException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            __setFileDataDB(bean);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_FILE_NAME = ?,");
            sql.addSql("   BIN_FILE_PATH = ?,");
            sql.addSql("   BIN_FILE_EXTENSION=?,");
            sql.addSql("   BIN_FILE_SIZE=?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            pstmt.setString(1, bean.getBinFileName());
            pstmt.setString(2, bean.getBinFilePath());
            pstmt.setString(3, bean.getBinFileExtension());
            pstmt.setLong(4, bean.getBinFileSize());
            pstmt.setInt(5, bean.getBinUpuser());
            pstmt.setTimestamp(6, JDBCUtil.getTimestamp(bean.getBinUpdate()));

            //where
            pstmt.setLong(7, bean.getBinSid());

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
     * <p>Update CMN_BINF
     * @param bean CMN_BINF Data Bindding JavaBean
     * @param filePath 保存するファイルのパス
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUserPict(CmnBinfModel bean, String filePath) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            __setFileData(bean, filePath);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_FILE_NAME = ?,");
            sql.addSql("   BIN_FILE_EXTENSION=?,");
            sql.addSql("   BIN_FILE_SIZE=?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addStrValue(bean.getBinFileName());
            sql.addStrValue(bean.getBinFileExtension());
            sql.addLongValue(bean.getBinFileSize());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            sql.addIntValue(bean.getBinJkbn());
            //where
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] バイナリー情報を更新する
     * <br>[解  説] ファイルを本体をDBに保存する。
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    public int updateFileName(CmnBinfModel bean) throws SQLException, IOException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_FILE_NAME = ?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addStrValue(bean.getBinFileName());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            //where
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
     * <p>バイナリー情報の論理削除を行う
     * @param bean CMN_BINF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeBinData(CmnBinfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            //where
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] 論理削除済みのバイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getDeleteFile() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_JKBN = ?");

            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRs(rs));
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
     * <br>[機  能] 論理削除済みのバイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getDeleteFileDB() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN,");
            sql.addSql("   BIN_FILE_DATA");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_JKBN = ?");

            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRsPlusFileOid(rs));
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
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリーSID
     * @return CMN_BINFModel
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfo(Long binSid) throws TempFileException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBinfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addLongValue(binSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCmnBinfFromRs(rs);
            }
        } catch (SQLException e) {
            throw new TempFileException(e.toString());
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説] 添付ファイルをDBに保存する場合使用する。
     * <br>[備  考]
     * @param binSid バイナリーSID
     * @param domain ドメイン
     * @return CMN_BINFModel
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfoDB(Long binSid, String domain)
                                                 throws TempFileException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBinfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN,");
            sql.addSql("   BIN_FILE_DATA");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addLongValue(binSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
                ret = __getCmnBinfFromRsPlusFile(rs, tempDir, date.getDateString(), fileNum,
                                                                    domain);
            }
        } catch (SQLException e) {
            throw new TempFileException(e.toString(), e);
        } catch (IOException e) {
            throw new TempFileException(e.toString(), e);
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説] 添付ファイルをDBに保存する場合使用する。
     * <br>[備  考]
     * @param binList バイナリーSIDリスト
     * @return CMN_BINFModelリスト
     * @throws SQLException SQL実行時例外
     */
    public List<CmnBinfModel> getBinInfoDBFileOid(String[] binList) throws SQLException {

        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        if (binList == null || binList.length == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN,");
            sql.addSql("   BIN_FILE_DATA");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            for (int i = 0; i < binList.length; i++) {
                String binSid = binList[i];

                sql.addSql("     ? ");
                sql.addLongValue(NullDefault.getLong(binSid, 0));

                if (i < binList.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRsPlusFileOid(rs));
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
     * <br>[機  能] バイナリSIDより添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRs(rs));
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
     * <br>[機  能] バイナリSIDの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] バイナリSIDより添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> selectLimit(int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" order by ");
            sql.addSql("   BIN_SID asc");

            sql.setPagingValue(offset, limit);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRs(rs));
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
     * <br>[機  能] バイナリSIDより添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param binList バイナリSIDリスト
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> select(String[] binList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            for (int i = 0; i < binList.length; i++) {
                String binSid = binList[i];

                sql.addSql("     ? ");
                sql.addLongValue(NullDefault.getLong(binSid, 0));

                if (i < binList.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBinfFromRs(rs));
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
     * <br>[機  能] バイナリSIDより添付ファイル情報を取得する
     * <br>[解  説] ファイルを本体をDBから取得する。
     * <br>[備  考]
     * @param binList バイナリSIDリスト
     * @param domain ドメイン
     * @return List in CMN_BINFModel
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作時例外
     */
    public List<CmnBinfModel> selectDB(String[] binList, String domain)
    throws SQLException, IOException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();

        if (binList == null || binList.length == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME,");
            sql.addSql("   BIN_FILE_PATH,");
            sql.addSql("   BIN_FILE_EXTENSION,");
            sql.addSql("   BIN_FILE_SIZE,");
            sql.addSql("   BIN_ADUSER,");
            sql.addSql("   BIN_ADDATE,");
            sql.addSql("   BIN_UPUSER,");
            sql.addSql("   BIN_UPDATE,");
            sql.addSql("   BIN_FILEKBN,");
            sql.addSql("   BIN_JKBN,");
            sql.addSql("   BIN_FILE_DATA");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            for (int i = 0; i < binList.length; i++) {
                String binSid = binList[i];

                sql.addSql("     ? ");
                sql.addLongValue(NullDefault.getLong(binSid, 0));

                if (i < binList.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            UDate date = new UDate();
            int fileNum = 0;
            String tempDir = "";
            if (tempUtil__ != null) {
                tempDir = tempUtil__.getTempFilePath(domain);
            }
            while (rs.next()) {
                fileNum++;
                ret.add(__getCmnBinfFromRsPlusFile(rs, tempDir, date.getDateString(), fileNum,
                                                                        domain));
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
     * <br>[機  能] 論理削除済みバイナリ情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteAllLogicalDeletedBinInf() throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_JKBN = ?");

            sql.addIntValue(GSConst.JTKBN_DELETE);

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
     * <br>[機  能] バイナリー情報を複数指定し、バイナリー情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリーSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinInf(Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID = ?");
            sql.addLongValue(binSid);

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
     * <br>[機  能] バイナリー情報を複数指定し、バイナリー情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param binList バイナリーSID(複数)
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinInf(String[] binList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (binList == null) {
            return count;
        }
        if (binList.length < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            for (int i = 0; i < binList.length; i++) {
                String binSid = binList[i];

                sql.addSql("     ? ");
                sql.addLongValue(NullDefault.getLong(binSid, 0));

                if (i < binList.length - 1) {
                    sql.addSql("     , ");
                }
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
     * <br>[機  能] バイナリSID(複数)を指定して状態区分を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CMN_BINF Data Bindding JavaBean
     * @param binSidList バイナリSIDリスト
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJKbn(CmnBinfModel bean, List<Long> binSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (binSidList == null || binSidList.size() < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_JKBN = ?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            sql.addIntValue(bean.getBinJkbn());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());

            for (int i = 0; i < binSidList.size(); i++) {
                sql.addSql("     ? ");
                sql.addLongValue(binSidList.get(i));
                if (i < binSidList.size() - 1) {
                    sql.addSql("     , ");
                }
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
     * <br>[機  能] 添付ファイルDBに保存している場合、ファイルの本体を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param oid 添付ファイルOID
     * @return 1:更新成功
     * @throws SQLException SQL実行例外
     */
    public int deleteObjectFile(long oid) throws SQLException {

        if (oid == 0) {
            log__.warn("削除対象のOIDに0が指定されました。");
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" /*REPLICATION*/ ");
            sql.addSql(" select ");
            sql.addSql("   lo_unlink(?)");
            sql.addSql(" where ");
            sql.addSql("   exists (");
            sql.addSql("     select 1 from pg_largeobject_metadata where oid = ?");
            sql.addSql("   )");

            sql.addLongValue(oid);
            sql.addLongValue(oid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] OIDを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリーSID
     * @return OID
     * @throws SQLException SQL実行時例外
     */
    public long getOid(long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long oid = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_FILE_DATA");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addLongValue(binSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                oid = rs.getLong("BIN_FILE_DATA");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return oid;
    }

    /**
     * <br>[機  能] ファイル情報の設定を行う
     * <br>[解  説] ファイル本体をファイルシステムに保存する場合使用する
     * <br>[備  考]
     * @param bean CmnBinfModel
     * @param filePath 保存するファイルのパス
     */
    private void __setFileData(CmnBinfModel bean, String filePath) {
        String extension = StringUtil.getExtension(bean.getBinFileName());
        if (extension != null && extension.length() > 20) {
            extension = extension.substring(0, 20);
        }

        bean.setBinFileExtension(extension);

        if (!StringUtil.isNullZeroString(filePath)) {
            File saveFile = new File(filePath);
            if (saveFile.exists()) {
                bean.setBinFileSize(saveFile.length());
            }
        }

    }

    /**
     * <br>[機  能] ファイル情報の設定を行う
     * <br>[解  説] ファイル本体をDBに保存する場合使用する
     * <br>[備  考]
     * @param bean CmnBinfModel
     */
    private void __setFileDataDB(CmnBinfModel bean) {

        File file = bean.getBinFileData();

        //拡張子
        String extension = StringUtil.getExtension(bean.getBinFileName());
        if (extension != null && extension.length() > 20) {
            extension = extension.substring(0, 20);
        }
        bean.setBinFileExtension(extension);
        //ファイルサイズ
        bean.setBinFileSize(file.length());

    }

    /**
     * <p>Create CMN_BINF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    private CmnBinfModel __getCmnBinfFromRs(ResultSet rs) throws SQLException {
        CmnBinfModel bean = new CmnBinfModel();
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
        bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        bean.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
        bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
        bean.setBinAduser(rs.getInt("BIN_ADUSER"));
        bean.setBinAddate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_ADDATE")));
        bean.setBinUpuser(rs.getInt("BIN_UPUSER"));
        bean.setBinUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_UPDATE")));
        bean.setBinJkbn(rs.getInt("BIN_JKBN"));
        bean.setBinFilekbn(rs.getInt("BIN_FILEKBN"));

        return bean;
    }

    /**
     * <p>Create CMN_BINF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param tempDir 保存先Dir
     * @param dateStr 日付文字列YYYYMMDD
     * @param fileNum ファイルの連番
     * @param domain ドメイン
     * @return created CmnBinfModel
     * @throws SQLException SQL実行例外
     * @throws IOException ファイル操作例外
     */
    private CmnBinfModel __getCmnBinfFromRsPlusFile(
            ResultSet rs, String tempDir, String dateStr, int fileNum,
            String domain)
    throws SQLException, IOException {
        UDate startDate = new UDate();

        CmnBinfModel bean = new CmnBinfModel();
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
        bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        bean.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
        bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
        bean.setBinAduser(rs.getInt("BIN_ADUSER"));
        bean.setBinAddate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_ADDATE")));
        bean.setBinUpuser(rs.getInt("BIN_UPUSER"));
        bean.setBinUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_UPDATE")));
        bean.setBinJkbn(rs.getInt("BIN_JKBN"));
        bean.setBinFilekbn(rs.getInt("BIN_FILEKBN"));

        if (tempUtil__ != null) {
            String fileFullPath = Cmn110Biz.getFilePath(
                    tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_SAVEFILE);
            log__.debug("添付ファイル一時保存先==>" + fileFullPath);
            File binFile = null;
            try {
                binFile = tempUtil__.readTempFileDataField(
                        rs, "BIN_FILE_DATA",
                        tempDir,
                        fileFullPath,
                        rs.getLong("BIN_FILE_SIZE"),
                        domain);
            } catch (IOException e) {
                log__.error("IOException binSid=" + bean.getBinSid());
                throw e;
            }
            bean.setBinFileData(
                    binFile);
        }

        __writeSlowLog(bean, startDate);
        startDate = null;

        return bean;
    }

    /**
     * <p>Create CMN_BINF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    private CmnBinfModel __getCmnBinfFromRsPlusFileOid(ResultSet rs) throws SQLException {
        CmnBinfModel bean = new CmnBinfModel();
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
        bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        bean.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
        bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
        bean.setBinAduser(rs.getInt("BIN_ADUSER"));
        bean.setBinAddate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_ADDATE")));
        bean.setBinUpuser(rs.getInt("BIN_UPUSER"));
        bean.setBinUpdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIN_UPDATE")));
        bean.setBinJkbn(rs.getInt("BIN_JKBN"));
        bean.setBinFilekbn(rs.getInt("BIN_FILEKBN"));
        bean.setBinFileDataOid(rs.getLong("BIN_FILE_DATA"));

        return bean;
    }

    /**
     * <br>[機  能] 添付ファイル情報読み込み時の遅延ログを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param binData 添付ファイル情報
     * @param startDate 開始日時
     */
    private void __writeSlowLog(CmnBinfModel binData, UDate startDate) {
        if (binData != null) {
            UDate endDate = new UDate();
            long time = endDate.getTimeMillis() - startDate.getTimeMillis();
            if (time >= GSConst.DELAY_LIMIT_FILEREAD) {
                log__.warn("---- Warning!!!"
                        + " slow load file " + GSConst.DELAY_LIMIT_FILEREAD
                        + " milli second over ("
                        + "time:" + time
                        + " | binSid:" + binData.getBinSid()
                        + " | fileSize:" + binData.getBinFileSize()
                        + " | fileName:" + binData.getBinFileName()
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