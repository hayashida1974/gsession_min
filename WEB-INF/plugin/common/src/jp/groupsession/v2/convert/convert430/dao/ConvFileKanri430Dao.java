package jp.groupsession.v2.convert.convert430.dao;

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
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.convert.convert430.model.CvtFileDirectoryModel;
import jp.groupsession.v2.convert.convert430.model.CvtFileFileBinModel;
import jp.groupsession.v2.convert.convert430.model.CvtFileFileRekiModel;
import jp.groupsession.v2.convert.convert430.model.CvtFileInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvFileKanri430Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvFileKanri430Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvFileKanri430Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ファイル管理のフォルダに登録された添付ファイルを
     *            指定したフォルダのファイルとして登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void fileConv(MlCountMtController mtCon) throws SQLException {

        try {

            List<CvtFileInfModel> fileInfMdlList = null;

            //キャビネットに登録された添付ファイルを移動する
            fileInfMdlList = new ArrayList<CvtFileInfModel>();
            fileInfMdlList = __getFileCabinetFile();
            if (fileInfMdlList != null && !fileInfMdlList.isEmpty()) {
                __doMoveFiles(mtCon, fileInfMdlList);
            }
            __deleteFileCabinetBin();

            //フォルダに登録された添付ファイルを移動する
            fileInfMdlList = new ArrayList<CvtFileInfModel>();
            fileInfMdlList = __getFileDirectoryFile();
            if (fileInfMdlList != null && !fileInfMdlList.isEmpty()) {
                __doMoveFiles(mtCon, fileInfMdlList);
            }
            __deleteFileDirectoryBin();

        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * <br>[機  能] フォルダに登録された添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @param fileInfMdlList 採番List<CvtFileInfModel>
     * @throws SQLException SQL実行例外
     */
    private void __doMoveFiles(MlCountMtController mtCon,
                                    List<CvtFileInfModel> fileInfMdlList)
    throws SQLException {

        int version = 1;

        //フォルダに登録された添付ファイルを登録しなおす
        for (CvtFileInfModel bean : fileInfMdlList) {

            //ディレクトリSIDを採番する。
            int dirSid = (int) mtCon.getSaibanNumber(
                    "file",
                    "directory",
                    bean.getFdrAuid());

            //ディレクトリ情報モデルを設定する。
            CvtFileDirectoryModel dirModel = new CvtFileDirectoryModel();
            dirModel.setFdrVersion(version);
            dirModel.setFcbSid(bean.getFcbSid());
            dirModel.setFdrParentSid(bean.getFdrParentSid());
            dirModel.setFdrKbn(1);
            dirModel.setFdrVerKbn(bean.getFdrVerKbn());
            dirModel.setFdrLevel(bean.getFdrLevel());
            dirModel.setFdrBiko("");
            dirModel.setFdrJtkbn(0);
            dirModel.setFdrAuid(bean.getFdrAuid());
            dirModel.setFdrAdate(bean.getFdrAdate());
            dirModel.setFdrEuid(bean.getFdrEuid());
            dirModel.setFdrEdate(bean.getFdrEdate());
            dirModel.setFdrSid(dirSid);
            dirModel.setFdrName(bean.getFileName());
            __insertFileDir(dirModel);

            //ファイル情報モデルを設定する。
            CvtFileFileBinModel fileBinModel = new CvtFileFileBinModel();
            fileBinModel.setFdrVersion(version);
            fileBinModel.setFflLockKbn(0);
            fileBinModel.setFflLockUser(bean.getFdrAuid());
            fileBinModel.setFdrSid(dirSid);
            fileBinModel.setBinSid(bean.getBinSid());
            fileBinModel.setFflExt(bean.getFileExtension());
            fileBinModel.setFflFileSize(bean.getFflFileSize());
            __insertFileBin(fileBinModel);

            //ディレクトリ情報モデルを設定する。
            CvtFileFileRekiModel rekiModel = new CvtFileFileRekiModel();
            rekiModel.setFdrVersion(version);
            rekiModel.setFfrKbn(0);
            rekiModel.setFfrEuid(bean.getFdrAuid());
            rekiModel.setFfrEdate(bean.getFdrEdate());
            rekiModel.setFfrParentSid(bean.getFdrParentSid());
            rekiModel.setFfrUpCmt("");
            rekiModel.setFdrSid(dirSid);
            rekiModel.setFfrFname(bean.getFileName());
            __insertFileReki(rekiModel);
        }
    }

    /**
     * <br>[機  能] キャビネットに登録された添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ラベル情報
     * @throws SQLException SQL実行例外
     */
    private List<CvtFileInfModel> __getFileCabinetFile()
    throws SQLException {

        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CvtFileInfModel> ret = new ArrayList<CvtFileInfModel>();
        CvtFileInfModel fileInfMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET_TABLE.FCB_SID     as FCB_SID,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID       as FDR_SID,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN   as FDR_VER_KBN, ");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL + 1 as FDR_LEVEL,  ");
            sql.addSql("   CMN_BINF.BIN_SID             as BIN_SID,  ");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME       as FDR_NAME,  ");
            sql.addSql("   CMN_BINF.BIN_FILE_EXTENSION  as BIN_FILE_EXTENSION,  ");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE       as BIN_FILE_SIZE,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_AUID      as FDR_AUID,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_ADATE     as FDR_ADATE,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID      as FDR_EUID,  ");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE     as FDR_EDATE ");
            sql.addSql(" from  ");
            sql.addSql("   CMN_BINF,  ");
            sql.addSql("   FILE_DIRECTORY, ");
            sql.addSql("   ( ");
            sql.addSql("     select ");
            sql.addSql("       FILE_DIRECTORY.FDR_SID, ");
            sql.addSql("       FILE_DIRECTORY.FCB_SID, ");
            sql.addSql("       FILE_CABINET_BIN.BIN_SID ");
            sql.addSql("     from ");
            sql.addSql("       FILE_DIRECTORY, ");
            sql.addSql("       FILE_CABINET_BIN ");
            sql.addSql("     where ");
            sql.addSql("       FILE_DIRECTORY.FCB_SID = FILE_CABINET_BIN.FCB_SID ");
            sql.addSql("     and  ");
            sql.addSql("       FILE_DIRECTORY.FDR_PARENT_SID = 0 ");
            sql.addSql("     and ");
            sql.addSql("       FILE_DIRECTORY.FDR_KBN = 0 ");
            sql.addSql("     and ");
            sql.addSql("       FILE_DIRECTORY.FDR_JTKBN = 0 ");
            sql.addSql("     order by FILE_DIRECTORY.FDR_PARENT_SID ");
            sql.addSql("    ) FILE_CABINET_TABLE ");
            sql.addSql(" where  ");
            sql.addSql("   FILE_CABINET_TABLE.BIN_SID = CMN_BINF.BIN_SID  ");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET_TABLE.FDR_SID = FILE_DIRECTORY.FDR_SID ");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET_TABLE.FCB_SID = FILE_DIRECTORY.FCB_SID ");
            sql.addSql(" and  ");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID = 0 ");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN = 0 ");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = 0 ");
            sql.addSql(" and  ");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0  ");
            sql.addSql(" order by  ");
            sql.addSql("   CMN_BINF.BIN_SID asc  ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fileInfMdl = new CvtFileInfModel();
                fileInfMdl.setFcbSid(rs.getInt("FCB_SID"));
                fileInfMdl.setFdrParentSid(rs.getInt("FDR_SID"));
                fileInfMdl.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
                fileInfMdl.setFdrLevel(rs.getInt("FDR_LEVEL"));
                fileInfMdl.setBinSid(rs.getLong("BIN_SID"));
                fileInfMdl.setFileName(rs.getString("FDR_NAME"));
                fileInfMdl.setFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                fileInfMdl.setFflFileSize(rs.getLong("BIN_FILE_SIZE"));
                fileInfMdl.setFdrAuid(rs.getInt("FDR_AUID"));
                fileInfMdl.setFdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_ADATE")));
                fileInfMdl.setFdrEuid(rs.getInt("FDR_EUID"));
                fileInfMdl.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));
                ret.add(fileInfMdl);
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
     * <br>[機  能] フォルダに登録された添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ラベル情報
     * @throws SQLException SQL実行例外
     */
    private List<CvtFileInfModel> __getFileDirectoryFile()
    throws SQLException {

        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CvtFileInfModel> ret = new ArrayList<CvtFileInfModel>();
        CvtFileInfModel fileInfMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID       as FCB_SID, ");
            sql.addSql("   FILE_DIRECTORY_BIN.FDR_SID   as FDR_SID, ");
            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN   as FDR_VER_KBN, ");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL + 1 as FDR_LEVEL, ");
            sql.addSql("   CMN_BINF.BIN_SID             as BIN_SID, ");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME       as FDR_NAME, ");
            sql.addSql("   CMN_BINF.BIN_FILE_EXTENSION  as BIN_FILE_EXTENSION, ");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE       as BIN_FILE_SIZE, ");
            sql.addSql("   FILE_DIRECTORY.FDR_AUID      as FDR_AUID, ");
            sql.addSql("   FILE_DIRECTORY.FDR_ADATE     as FDR_ADATE, ");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID      as FDR_EUID, ");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE     as FDR_EDATE ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY_BIN, ");
            sql.addSql("   CMN_BINF, ");
            sql.addSql("   FILE_DIRECTORY ");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = FILE_DIRECTORY_BIN.FDR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0 ");
            sql.addSql(" order by ");
            sql.addSql("   CMN_BINF.BIN_SID asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fileInfMdl = new CvtFileInfModel();
                fileInfMdl.setFcbSid(rs.getInt("FCB_SID"));
                fileInfMdl.setFdrParentSid(rs.getInt("FDR_SID"));
                fileInfMdl.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
                fileInfMdl.setFdrLevel(rs.getInt("FDR_LEVEL"));
                fileInfMdl.setBinSid(rs.getLong("BIN_SID"));
                fileInfMdl.setFileName(rs.getString("FDR_NAME"));
                fileInfMdl.setFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                fileInfMdl.setFflFileSize(rs.getLong("BIN_FILE_SIZE"));
                fileInfMdl.setFdrAuid(rs.getInt("FDR_AUID"));
                fileInfMdl.setFdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_ADATE")));
                fileInfMdl.setFdrEuid(rs.getInt("FDR_EUID"));
                fileInfMdl.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));
                ret.add(fileInfMdl);
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
     * <p>Insert FILE_DIRECTORY Data Bindding JavaBean
     * @param bean FILE_DIRECTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    private void __insertFileDir(CvtFileDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_DIRECTORY(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FDR_PARENT_SID,");
            sql.addSql("   FDR_KBN,");
            sql.addSql("   FDR_VER_KBN,");
            sql.addSql("   FDR_LEVEL,");
            sql.addSql("   FDR_NAME,");
            sql.addSql("   FDR_BIKO,");
            sql.addSql("   FDR_JTKBN,");
            sql.addSql("   FDR_AUID,");
            sql.addSql("   FDR_ADATE,");
            sql.addSql("   FDR_EUID,");
            sql.addSql("   FDR_EDATE");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addIntValue(bean.getFcbSid());
            sql.addIntValue(bean.getFdrParentSid());
            sql.addIntValue(bean.getFdrKbn());
            sql.addIntValue(bean.getFdrVerKbn());
            sql.addIntValue(bean.getFdrLevel());
            sql.addStrValue(bean.getFdrName());
            sql.addStrValue(bean.getFdrBiko());
            sql.addIntValue(bean.getFdrJtkbn());
            sql.addIntValue(bean.getFdrAuid());
            sql.addDateValue(bean.getFdrAdate());
            sql.addIntValue(bean.getFdrEuid());
            sql.addDateValue(bean.getFdrEdate());
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
     * <p>Insert FILE_FILE_BIN Data Bindding JavaBean
     * @param bean FILE_FILE_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertFileBin(CvtFileFileBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_BIN(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   FFL_EXT,");
            sql.addSql("   FFL_FILE_SIZE,");
            sql.addSql("   FFL_LOCK_KBN,");
            sql.addSql("   FFL_LOCK_USER,");
            sql.addSql("   FFL_LOCK_DATE");
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
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getFflExt());
            sql.addLongValue(bean.getFflFileSize());
            sql.addIntValue(bean.getFflLockKbn());
            sql.addIntValue(bean.getFflLockUser());
            sql.addDateValue(bean.getFflLockDate());
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
     * <p>Insert FILE_FILE_REKI Data Bindding JavaBean
     * @param bean FILE_FILE_REKI Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertFileReki(CvtFileFileRekiModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_FILE_REKI(");
            sql.addSql("   FDR_SID,");
            sql.addSql("   FDR_VERSION,");
            sql.addSql("   FFR_FNAME,");
            sql.addSql("   FFR_KBN,");
            sql.addSql("   FFR_EUID,");
            sql.addSql("   FFR_EDATE,");
            sql.addSql("   FFR_PARENT_SID,");
            sql.addSql("   FFR_UP_CMT");
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
            sql.addIntValue(bean.getFdrSid());
            sql.addIntValue(bean.getFdrVersion());
            sql.addStrValue(bean.getFfrFname());
            sql.addIntValue(bean.getFfrKbn());
            sql.addIntValue(bean.getFfrEuid());
            sql.addDateValue(bean.getFfrEdate());
            sql.addIntValue(bean.getFfrParentSid());
            sql.addStrValue(bean.getFfrUpCmt());
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
     * <p>Delete FileDirectoryBin
     * @throws SQLException SQL実行例外
     */
    private void __deleteFileCabinetBin() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET_BIN ");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete FileDirectoryBin
     * @throws SQLException SQL実行例外
     */
    private void __deleteFileDirectoryBin() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY_BIN ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}